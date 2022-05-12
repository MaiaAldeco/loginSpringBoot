package com.tutorial.crud.security.jwt;

import com.tutorial.crud.security.service.UserDetailsServiceImpl;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;


public class JwtTokenFilter extends OncePerRequestFilter{ //se ejecuta por cada peticion 
    
    //prueba en caso de error
    private final static Logger logger = LoggerFactory.getLogger(JwtTokenFilter.class);
    
    @Autowired
    JwtProvider jwtProvider;
    
    @Autowired
    UserDetailsServiceImpl userDetailsServiceImpl;

    //COMPRUEBA QUE EL TOKEN SEA VALIDO (JWTPROVIDE) EN CASO DE VALIDO PERMITE ACCESO AL RECURSO. TAMBIEN DICE CUAL ES EL USUARIO AUTENTICADO
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try{
            String token = getToken(request);
            if(token!=null&&jwtProvider.validateToken(token)){
                String nombreUsuario = jwtProvider.getNombreUsuarioFromToken(token); //obtenemos el usuario
                UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(nombreUsuario); //crea userDetails y lo autenticamos
                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }catch(Exception e){
            logger.error("Fail método doFilter" + e.getMessage());
        }
        filterChain.doFilter(request, response);
    }
    
    //EXTRAE TOKEN
    private String getToken(HttpServletRequest request){
        String header = request.getHeader("Authorization");
        if(header!=null && header.startsWith("Bearer")){
            return header.replace("Bearer ", "");
        } else {
        return null;
        }
    }  
}

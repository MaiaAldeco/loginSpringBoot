package com.tutorial.crud.util;

import com.tutorial.crud.security.entity.Rol;
import com.tutorial.crud.security.enums.RolNombre;
import com.tutorial.crud.security.service.RolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CreateRoles implements CommandLineRunner{
    
//    ESTA CLASE SOLO SE EJECUTARA UNA VEZ PARA CREAR LOS ROLES. UNA VEZ CREADOS SE DEBERÁ ELIMINAR O COMENTAR
    
    @Autowired
    RolService rolService;

    @Override
    public void run(String... args) throws Exception {
        //CREAR ROLES
//        Rol rolAdmin = new Rol(RolNombre.ROLE_ADMIN);
//        Rol rolUser = new Rol(RolNombre.ROLE_USER);
//        rolService.save(rolUser);
//        rolService.save(rolAdmin);
    }
    
}

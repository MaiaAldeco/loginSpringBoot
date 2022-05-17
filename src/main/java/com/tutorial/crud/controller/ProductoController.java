
package com.tutorial.crud.controller;

import com.tutorial.crud.dto.Mensaje;
import com.tutorial.crud.dto.ProductoDto;
import com.tutorial.crud.entity.Producto;
import com.tutorial.crud.service.ProductoService;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/producto")
@CrossOrigin(origins = "*")
public class ProductoController {
    
    @Autowired
    ProductoService prodService;
    
    @ApiOperation("Muestra una lista de productos")
    @GetMapping("/lista")
    public ResponseEntity<List<Producto>> list(){
        List<Producto> list = prodService.list();
        return new ResponseEntity<List<Producto>>(list, HttpStatus.OK);
    }
    
//    @ApiIgnore si no quiero que se vea
    @GetMapping("/detail/{id}")
    public ResponseEntity<Producto> getById(@PathVariable("id") long id){
        if(!prodService.existsById(id)){
            return new ResponseEntity(new Mensaje("no existe"), HttpStatus.NOT_FOUND);
        } else {
            Producto producto = prodService.getOne(id).get();
            return new ResponseEntity<Producto>(producto,HttpStatus.OK);
        }
    }
    
    @GetMapping("/detailname/{nombre}")
    public ResponseEntity<Producto> getByNombre(@PathVariable("nombre") String nombre){
        if(!prodService.existsByNombre(nombre)){
            return new ResponseEntity(new Mensaje("no existe"), HttpStatus.NOT_FOUND);
        } else {
            Producto producto = prodService.getByNombre(nombre).get();
            return new ResponseEntity<Producto>(producto,HttpStatus.OK);
        }
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody ProductoDto productoDto){
        if(StringUtils.isBlank(productoDto.getNombre())){ //common lang
            return new ResponseEntity(new Mensaje("el nombre es obligatorio"), HttpStatus.BAD_REQUEST);
        } 
        if(productoDto.getPrecio() == null || productoDto.getPrecio()<0){
            return new ResponseEntity(new Mensaje("el precio debe ser mayor a 0"), HttpStatus.BAD_REQUEST);
        }
        if(prodService.existsByNombre(productoDto.getNombre())){
            return new ResponseEntity(new Mensaje("el nombre ya existe"), HttpStatus.BAD_REQUEST);
        }
        Producto producto = new Producto(productoDto.getNombre(),productoDto.getPrecio());
        prodService.save(producto);
        return new ResponseEntity(new Mensaje("producto creado con éxito"), HttpStatus.OK);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable("id") long id, @RequestBody ProductoDto productoDto){
        if(!prodService.existsById(id)){
            return new ResponseEntity(new Mensaje("no existe"), HttpStatus.NOT_FOUND);
        }
        if(prodService.existsByNombre(productoDto.getNombre()) && prodService.getByNombre(productoDto.getNombre()).get().getId() != id){
            return new ResponseEntity(new Mensaje("el nombre ya existe"), HttpStatus.BAD_REQUEST);
        }
        if(StringUtils.isBlank(productoDto.getNombre())){ //common lang
            return new ResponseEntity(new Mensaje("el nombre es obligatorio"), HttpStatus.BAD_REQUEST);
        } 
        if(productoDto.getPrecio() == null || productoDto.getPrecio()<0){
            return new ResponseEntity(new Mensaje("el precio debe ser mayor a 0"), HttpStatus.BAD_REQUEST);
        }
        
        Producto producto = prodService.getOne(id).get();
        producto.setNombre(productoDto.getNombre());
        producto.setPrecio(productoDto.getPrecio());
        prodService.save(producto);
        return new ResponseEntity(new Mensaje("producto actualizado con éxito"), HttpStatus.OK);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") long id){
        if(!prodService.existsById(id)){
            return new ResponseEntity(new Mensaje("no existe"), HttpStatus.NOT_FOUND);
        }
        prodService.delete(id);
        return new ResponseEntity(new Mensaje("eliminado con éxito"), HttpStatus.OK);
    }
}

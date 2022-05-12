
package com.tutorial.crud.service;

import com.tutorial.crud.entity.Producto;
import com.tutorial.crud.repository.ProductoRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ProductoService {
    
    @Autowired
    ProductoRepository productoRepo;
    
    public List<Producto> list(){
        return productoRepo.findAll();
    }
    
    public Optional<Producto> getOne(long id){
        return productoRepo.findById(id);
    }
    
    public Optional<Producto> getByNombre(String nombre){
        return productoRepo.findByNombre(nombre);
    }
    
    public void save(Producto producto){
        productoRepo.save(producto);
    }
    
    public void delete(long id){
        productoRepo.deleteById(id);
    }
    
    public boolean existsById(long id){
        return productoRepo.existsById(id);
    }
    
    public boolean existsByNombre(String nombre){
        return productoRepo.existsByNombre(nombre);
    }
}

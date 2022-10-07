package com.gmv2.pdv.controller;

import com.gmv2.pdv.dto.ResponseDTO;
import com.gmv2.pdv.entity.Product;
import com.gmv2.pdv.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/product")
public class ProductController {
    private ProductRepository productRepository;

    public ProductController(@Autowired ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    @GetMapping()
    public ResponseEntity getAll(){
        return new ResponseEntity<>(productRepository.findAll(), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity post(@Valid @RequestBody Product product){
        try {
            return new ResponseEntity<>(productRepository.save(product), HttpStatus.CREATED);
        } catch (Exception error){
            return new ResponseEntity<>(new ResponseDTO(error.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PutMapping()
    public ResponseEntity put(@Valid @RequestBody Product product){

        Optional<Product> productToEdit = productRepository.findById(product.getId());
        try {
            if (productToEdit.isPresent()){
                productRepository.save(product);
            }
            return new ResponseEntity<>(product, HttpStatus.OK);
        } catch (Exception error){
            return new ResponseEntity<>(new ResponseDTO(error.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id){
        try {
           productRepository.deleteById(id);
            return new ResponseEntity<>( new ResponseDTO( "Pruduto deletado com sucesso"), HttpStatus.OK);
        } catch (Exception error){
            return new ResponseEntity<>(new ResponseDTO(error.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}

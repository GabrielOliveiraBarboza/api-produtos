package com.example.springboot.controllers;

import com.example.springboot.dtos.ProdutoRecordDto;
import com.example.springboot.models.ProdutoModel;
import com.example.springboot.service.ProdutoService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class ProdutoController {


    final ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @PostMapping ("/produtos")
    public ResponseEntity<ProdutoModel> saveProduto(@RequestBody @Valid ProdutoRecordDto produtoRecordDto) {
        var produtoModel = new ProdutoModel();
        BeanUtils.copyProperties(produtoRecordDto, produtoModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(produtoService.save(produtoModel));
    }

    @GetMapping("/produtos")
    public ResponseEntity<List<ProdutoModel>> getAllProdutos(){
        List<ProdutoModel> produtoList= produtoService.findAll();
        if(!produtoList.isEmpty()){
            for(ProdutoModel produto: produtoList){
                UUID id= produto.getIdprodutos();
                produto.add(linkTo(methodOn(ProdutoController.class).getOneProdutos(id)).withSelfRel());
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body(produtoList);

    }

    @GetMapping("/produtos/{id}")
    public ResponseEntity<Object> getOneProdutos(@PathVariable(value="id") UUID id){
        Optional<ProdutoModel> produto0 = produtoService.findById(id);
        if(produto0.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto not found.");
        }
        produto0.get().add(linkTo(methodOn(ProdutoController.class).getAllProdutos()).withRel("Product list"));
        return ResponseEntity.status(HttpStatus.OK).body(produto0.get());
    }

    @PutMapping("/produtos/{id}")
    public ResponseEntity<Object> updateProdutos(@PathVariable(value="id")UUID id,
                                                 @RequestBody @Valid ProdutoRecordDto produtoRecordDto){
        Optional<ProdutoModel> produto0 = produtoService.findById(id);
        if(produto0.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto not found.");
        }
        var produtoModel=produto0.get();
        BeanUtils.copyProperties(produtoRecordDto, produtoModel);
        return ResponseEntity.status(HttpStatus.OK).body(produtoService.save(produtoModel));
    }
    @DeleteMapping("/produtos/{id}")
    public ResponseEntity<Object> deleteProdutos(@PathVariable(value="id")UUID id){
        Optional<ProdutoModel> produto0 = produtoService.findById(id);
        if(produto0.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found.");
        }
        produtoService.delete(produto0.get());
        return ResponseEntity.status(HttpStatus.OK).body("product deleted successfully");
    }

}

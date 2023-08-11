package com.example.springboot.service;


import com.example.springboot.controllers.ProdutoController;
import com.example.springboot.models.ProdutoModel;
import com.example.springboot.repositories.ProdutoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@Service
public class ProdutoService {


    final ProdutoRepository produtoRepository;

    public ProdutoService(ProdutoRepository produtoRepository){

        this.produtoRepository= produtoRepository;
    }

    @Transactional
    public ProdutoModel save(ProdutoModel produtoModel) {

        return produtoRepository.save(produtoModel);
    }

    public List<ProdutoModel> findAll() {
        List<ProdutoModel> produtoList= produtoRepository.findAll();
        if(!produtoList.isEmpty()){
            for(ProdutoModel produto: produtoList){
                UUID id= produto.getIdprodutos();
                produto.add(linkTo(methodOn(ProdutoController.class).getOneProdutos(id)).withSelfRel());
            }
        }
        return produtoRepository.findAll();
    }

    public ResponseEntity<Object> buscaProdutos(@PathVariable(value="id") UUID id){
        Optional<ProdutoModel> produto0 = produtoRepository.findById(id);
        if(produto0.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto not found.");
        }
        produto0.get().add(linkTo(methodOn(ProdutoController.class).getAllProdutos()).withRel("Product list"));
        return ResponseEntity.status(HttpStatus.OK).body(produto0.get());
    }

    public Optional<ProdutoModel> findById(UUID id) {

        return produtoRepository.findById(id);
    }

    public void delete(ProdutoModel produtoModel) {
        produtoRepository.delete(produtoModel);
    }

    public Page<ProdutoModel> getProdutowithPagination(int offset, int pageSize){
        return produtoRepository.findAll(PageRequest.of(offset, pageSize));
    }
}

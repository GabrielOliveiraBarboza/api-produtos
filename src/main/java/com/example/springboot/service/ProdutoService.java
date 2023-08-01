package com.example.springboot.service;


import com.example.springboot.models.ProdutoModel;
import com.example.springboot.repositories.ProdutoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


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
        return produtoRepository.findAll();
    }

    public Optional<ProdutoModel> findById(UUID id) {
        return produtoRepository.findById(id);
    }

    public void delete(ProdutoModel produtoModel) {
        produtoRepository.delete(produtoModel);
    }
}

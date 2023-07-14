package com.example.springboot.repositories;


import com.example.springboot.models.ProdutoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProdutoRepository extends JpaRepository <ProdutoModel, UUID>{


}

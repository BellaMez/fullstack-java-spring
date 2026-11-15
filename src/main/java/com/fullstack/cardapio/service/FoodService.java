package com.fullstack.cardapio.service;

import com.fullstack.cardapio.entity.Food;
import com.fullstack.cardapio.repository.FoodRepository;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.List;

@Service
public class FoodService {

    private final FoodRepository repository;

    public FoodService(FoodRepository repository) {
        this.repository = repository;
    }

    public List<Food> listar() {
        return repository.findAll();
    }

    public Food cadastrar(Food food) {
        if (food == null) {
            throw new IllegalArgumentException("Alimento não informado");
        }
        if (food.getTitle() == null || food.getTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("Nome obrigatório");
        }
        if (food.getPrice() == null || food.getPrice() < 0) {
            throw new IllegalArgumentException("Preço inválido");
        }
        if (food.getImage() == null) {
            throw new IllegalArgumentException("Imagem obrigatória");
        }
        if (!isUrlValida(food.getImage())) {
            throw new IllegalArgumentException("URL inválida");
        }
        return repository.save(food);
    }

private boolean isUrlValida(String url) {
        if (url.isBlank()) { 
            return false;
        }
        try {
            URI uri = URI.create(url);
            String scheme = uri.getScheme();
            String host = uri.getHost();
            return (scheme != null && (scheme.equalsIgnoreCase("http") || scheme.equalsIgnoreCase("https")))
                    && (host != null && !host.isBlank());
        } catch (IllegalArgumentException e) { 
            return false;
        }
    }
} 
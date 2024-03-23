package exercise.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

import exercise.model.Product;
import exercise.repository.ProductRepository;
import exercise.exception.ResourceNotFoundException;

@RestController
@RequestMapping("/products")
public class ProductsController {

    @Autowired
    private ProductRepository productRepository;

    // BEGIN
    @GetMapping(path = "")
    public List<Product> index(@RequestParam Optional<Integer> min,
                               @RequestParam Optional<Integer> max) {
        if (max.isPresent()) {
            if (min.isPresent()) {
                return productRepository.findByPriceBetweenOrderByPriceAsc(min.get(), max.get());
            } else {
                return productRepository.findByPriceLessThanEqualOrderByPriceAsc(max.get());
            }
        } else if (min.isPresent()) {
            return productRepository.findByPriceGreaterThanEqualOrderByPriceAsc(min.get());
        } else {
            return productRepository.findAllByOrderByPriceAsc();
        }
    }
    // END

    @GetMapping(path = "/{id}")
    public Product show(@PathVariable long id) {

        var product =  productRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Product with id " + id + " not found"));

        return product;
    }
}

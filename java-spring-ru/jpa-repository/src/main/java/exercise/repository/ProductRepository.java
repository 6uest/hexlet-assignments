package exercise.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import exercise.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    // BEGIN
    List<Product> findByPriceLessThan(int price);
    List<Product> findByPriceLessThanEqualOrderByPriceAsc(int price);
    List<Product> findByPriceGreaterThanEqualOrderByPriceAsc(int price);
    List<Product> findByPriceBetweenOrderByPriceAsc(int startPrice, int endPrice);
    List<Product> findAllByOrderByPriceAsc();
    List<Product> findByPriceIn(Collection<Integer> prices);
    // END
}

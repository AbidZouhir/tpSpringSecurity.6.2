package ma.work.springsecurity6.repository;

import ma.work.springsecurity6.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {

}

package com.service.product.Repositories;

import com.service.product.Entities.ProductsRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductsRequestRepository extends JpaRepository<ProductsRequest,String> {
}

package dev.jstec.demo.domain.repository;


import dev.jstec.core.repository.JsTecRepository;
import dev.jstec.demo.domain.model.Sku;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface SkuRepository extends JsTecRepository <Sku, Long> {

        List <Sku> findByDescriptionContainsIgnoreCaseOrderByDescription(String description);
       // @Query("SELECT s FROM Sku s JOIN SkuStock ss ORDER BY ss.stock DESC")
       // List <Sku> getTopByStock( Pageable pageable );

        @Query(value = "SELECT s.id, s.barcode, s.description, s.price, sk.stock, (s.price * sk.stock) AS total_cost " +
                "               FROM sku s INNER JOIN  sku_stock sk " +
                "                               ON (s.id = sk.sku_id) " +
                "               ORDER BY total_cost DESC " +
                "               limit :max", nativeQuery = true)
        List<Object[]> getMaxStock(@Param("max") Integer max);

}

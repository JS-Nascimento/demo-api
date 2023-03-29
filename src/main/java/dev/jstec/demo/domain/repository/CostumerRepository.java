package dev.jstec.demo.domain.repository;

import dev.jstec.core.repository.JsTecRepository;
import dev.jstec.demo.domain.model.Costumer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CostumerRepository extends JsTecRepository <Costumer, Long> {

    @Query(value = "SELECT c.id, c.name, c.cnpj, c.tax_identity FROM costumer c ORDER BY c.name", nativeQuery = true)
    List <Object[]> getAllForList();

}

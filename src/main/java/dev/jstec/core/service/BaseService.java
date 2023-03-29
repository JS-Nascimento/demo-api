package dev.jstec.core.service;

import dev.jstec.demo.domain.model.Sku;

import java.util.List;
import java.util.Optional;

public interface BaseService<T> {

    List <T> getAll();

    <T> T getById( Long id);

}

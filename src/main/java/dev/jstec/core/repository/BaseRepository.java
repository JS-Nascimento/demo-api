package dev.jstec.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BaseRepository<T , ID> extends JpaRepository <T, ID>  {



}

package com.telnetar.services.repositories;

import org.springframework.data.repository.CrudRepository;

import com.telnetar.model.Part;

public interface PartRepository extends CrudRepository<Part, Long>, PartRepositoryCustom{

}

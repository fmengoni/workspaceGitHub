package com.telnetar.services.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.telnetar.model.Country;
import com.telnetar.model.Province;
import com.telnetar.model.User;

public interface ProvinceRepository extends CrudRepository<Province, Long>, ProvinceRepositoryCustom{
	public List<Province> findProvinceByUserByCountry(User user, Country country);
}

package com.telnetar.services.repositories;

import java.util.List;

import com.telnetar.model.Country;
import com.telnetar.model.Province;
import com.telnetar.model.User;

public interface ProvinceRepositoryCustom {
	public List<Province> findProvinceByUserByCountry(User user, Country country);
}

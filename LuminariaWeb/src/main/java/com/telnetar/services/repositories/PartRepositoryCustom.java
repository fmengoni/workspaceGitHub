package com.telnetar.services.repositories;

import java.util.List;

import com.telnetar.model.Part;
import com.telnetar.model.Province;
import com.telnetar.model.User;

public interface PartRepositoryCustom {
	public List<Part> findPartByUserByProvince(User user, Province province);
}

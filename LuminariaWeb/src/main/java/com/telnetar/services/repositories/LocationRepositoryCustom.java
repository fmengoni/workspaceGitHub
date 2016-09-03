package com.telnetar.services.repositories;

import java.util.List;

import com.telnetar.model.Location;
import com.telnetar.model.Part;
import com.telnetar.model.User;

public interface LocationRepositoryCustom {
	public List<Location> findLocationByUserByPart(User user, Part part);
}

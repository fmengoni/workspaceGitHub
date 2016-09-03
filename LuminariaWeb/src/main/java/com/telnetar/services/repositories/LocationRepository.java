package com.telnetar.services.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.telnetar.model.Location;
import com.telnetar.model.Part;
import com.telnetar.model.User;

public interface LocationRepository extends CrudRepository<Location, Long>, LocationRepositoryCustom{
	public List<Location> findLocationByUserByPart(User user, Part part);
}

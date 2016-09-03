package com.telnetar.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.telnetar.model.LuminariaAuditoria;
import com.telnetar.services.repositories.LuminariaAuditoriaRepository;

@Service
public class LuminariaAuditService {
	@Autowired
	LuminariaAuditoriaRepository luminariaAuditoriaRepository;
	
	public LuminariaAuditoria obtenerUltimaAuditoria(Integer hightByte, Integer lowByte){
		try{
			return luminariaAuditoriaRepository.obtenerUltimaAuditoria(hightByte, lowByte);	
		}catch (EmptyResultDataAccessException e) {
			return null;
		}
		
	}
}

package com.telnetar.services.repositories.impl;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;

import com.telnetar.model.LuminariaAuditoria;

public class LuminariaAuditoriaRepositoryImpl {
	@Autowired
	EntityManager em;
	
	public LuminariaAuditoria obtenerUltimaAuditoria(Integer hightByte, Integer lowByte){
		Query query = em.createNamedQuery("LuminariaAuditoria.obtenerUltimaAuditoria")
			.setParameter("param1", hightByte)
			.setParameter("param2", lowByte);
		return (LuminariaAuditoria) query.getSingleResult();
	};
}

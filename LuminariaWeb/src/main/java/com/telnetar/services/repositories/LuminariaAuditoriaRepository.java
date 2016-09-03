package com.telnetar.services.repositories;

import org.springframework.data.repository.CrudRepository;

import com.telnetar.model.LuminariaAuditoria;

public interface LuminariaAuditoriaRepository extends CrudRepository<LuminariaAuditoria, Long>{
	public LuminariaAuditoria obtenerUltimaAuditoria(Integer hightByte, Integer lowByte);
}

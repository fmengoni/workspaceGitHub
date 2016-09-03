package com.telnetar.services.repositories;

import com.telnetar.model.LuminariaAuditoria;

public interface LuminariaAuditoriaRepositoryCustom {
	public LuminariaAuditoria obtenerUltimaAuditoria(Integer hightByte, Integer lowByte);
}

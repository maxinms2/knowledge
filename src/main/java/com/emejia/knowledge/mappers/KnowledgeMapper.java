package com.emejia.knowledge.mappers;

import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.emejia.knowledge.model.dtos.KnowledgeDTO;
import com.emejia.knowledge.persistence.entities.Knowledge;
import com.emejia.knowledge.services.IKnowledgeService;

@Component
public class KnowledgeMapper {

	private final IKnowledgeService service;

	public KnowledgeMapper(IKnowledgeService service) {
		this.service = service;
	}

	public KnowledgeDTO EntityToDTO(Knowledge db) {
		KnowledgeDTO dto = new KnowledgeDTO();
		dto.setId(db.getId());
		dto.setChildrenIds(db.getChildren()==null?new ArrayList<Long>():
			db.getChildren().stream().map(x -> x.getId()).collect(Collectors.toList()));
		dto.setContent(db.getContent());
		dto.setParentId(db.getParent().getId());
		dto.setTitle(db.getTitle());
		dto.setCreatedAt(db.getCreatedAt());
		dto.setUpdatedAt(db.getUpdatedAt());
		return dto;
	}

	public Knowledge EntityToDTO(KnowledgeDTO dto) {
		Knowledge db=null;
		db.setId(dto.getId());
		db.setContent(db.getContent());
		db.setTitle(db.getTitle());
		db.setChildren(service.getTree(dto.getId()));
		db.setCreatedAt(dto.getCreatedAt());
		db.setParent(service.getKnowledge(dto.getId()).orElse(service.nullObject()));
		db.setUpdatedAt(dto.getUpdatedAt());
		return db;
	}
	
}

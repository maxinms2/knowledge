package com.emejia.knowledge.services;

import java.util.List;
import java.util.Optional;

import com.emejia.knowledge.model.dtos.KnowledgeDTO;
import com.emejia.knowledge.persistence.entities.Knowledge;

public interface IKnowledgeService {
	
	Knowledge createKnowledge(KnowledgeDTO dto);
	
	List<Knowledge> getTree(Long rootId);
	
	Optional<Knowledge> getKnowledge(Long id);
	
	Knowledge nullObject();
}

package com.emejia.knowledge.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.emejia.knowledge.model.dtos.KnowledgeDTO;
import com.emejia.knowledge.model.utils.PositionTree;
import com.emejia.knowledge.persistence.entities.Knowledge;

public interface IKnowledgeService {
	
	Knowledge createKnowledge(KnowledgeDTO dto);
	
	List<Knowledge> getTree(Long rootId);
	
	Optional<Knowledge> getKnowledge(Long id);
	
	Map<PositionTree,List<Knowledge>> getKnowledge(PositionTree positionTree);
	
	Knowledge nullObject();
}

package com.emejia.knowledge.services.impl;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.emejia.knowledge.model.dtos.KnowledgeDTO;
import com.emejia.knowledge.model.utils.PositionTree;
import com.emejia.knowledge.persistence.entities.Knowledge;
import com.emejia.knowledge.persistence.repositories.KnowledgeRepository;
import com.emejia.knowledge.services.IKnowledgeService;

import jakarta.persistence.EntityNotFoundException;

@Service
public class KnowledgeServiceImpl implements IKnowledgeService{

    private final KnowledgeRepository repository;
    
    public KnowledgeServiceImpl(KnowledgeRepository repository) {
		this.repository = repository;
	}

	@Transactional
    public Knowledge createKnowledge(KnowledgeDTO dto) {
        Knowledge knowledge = new Knowledge();
        knowledge.setTitle(dto.getTitle());
        knowledge.setContent(dto.getContent());
        knowledge.setCreatedAt(dto.getCreatedAt());
        knowledge.setUpdatedAt(dto.getUpdatedAt());
        if (dto.getParentId() != null) {
            Knowledge parent = repository.findById(dto.getParentId())
                .orElseThrow(() -> new EntityNotFoundException("Parent not found"));
            knowledge.setParent(parent);
        }
        return repository.save(knowledge);
    }

    @Transactional(readOnly = true)
    public List<Knowledge> getTree(Long rootId) {
        return repository.findByParentId(rootId).stream().
        		filter(k->k.getParent().getId()!=k.getId()).collect(Collectors.toList());
    }

	@Override
	@Transactional(readOnly = true)
	public Optional<Knowledge> getKnowledge(Long id) {
		return repository.findById(id);
	}
	
	

	@Override
	public Knowledge nullObject() {
		// TODO Auto-generated method stub
		Knowledge nullObj= new Knowledge();
		nullObj.setId(0l);
		nullObj.setChildren(new ArrayList<>());
		nullObj.setContent("");
		nullObj.setCreatedAt(new Date(1970,1,1));
		nullObj.setUpdatedAt(new Date(1970,1,1));
		nullObj.setParent(null);
		return nullObj;
	}

	@Override
	@Transactional(readOnly = true)
	public Map<PositionTree,List<Knowledge>> getKnowledge(PositionTree positionTree) {
		Map<PositionTree,List<Knowledge>> map = new HashMap<>();
		map=getKnowledge(positionTree,map);
		return map;
	}
	
	private Map<PositionTree,List<Knowledge>> getKnowledge(PositionTree positionTree, Map<PositionTree,List<Knowledge>> map) {
		List<Knowledge> Knowledges=getTree(positionTree.getId());
		map.put(new PositionTree(positionTree.getDeep(),positionTree.getId()), Knowledges);
		if(positionTree.getDeep()==0 || Knowledges.isEmpty()) {
			return map;
		}		
		Knowledges.forEach(k->getKnowledge(new PositionTree(positionTree.getDeep()-1, k.getId()), map));
		return map;
	}

}

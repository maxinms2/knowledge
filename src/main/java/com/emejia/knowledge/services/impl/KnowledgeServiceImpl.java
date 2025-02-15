package com.emejia.knowledge.services.impl;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.emejia.knowledge.mappers.KnowledgeMapper;
import com.emejia.knowledge.model.dtos.KnowledgeDTO;
import com.emejia.knowledge.model.utils.PositionTree;
import com.emejia.knowledge.persistence.entities.Knowledge;
import com.emejia.knowledge.persistence.repositories.KnowledgeRepository;
import com.emejia.knowledge.services.IKnowledgeService;

import jakarta.persistence.EntityNotFoundException;

@Service
public class KnowledgeServiceImpl implements IKnowledgeService{

    private final KnowledgeRepository repository;
    private final KnowledgeMapper mapper;
    
    public KnowledgeServiceImpl(KnowledgeRepository repository,KnowledgeMapper mapper) {
		this.repository = repository;
		this.mapper=mapper;
	}

	@Transactional
    public KnowledgeDTO createKnowledge(KnowledgeDTO dto) {
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
        return mapper.entityToDTO(repository.save(knowledge));
    }

    @Transactional(readOnly = true)
    public List<Knowledge> getTree(Long rootId) {
        return repository.findByParentId(rootId).stream().
        		filter(k->k.getParent().getId()!=k.getId()).collect(Collectors.toList());
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
	public KnowledgeDTO getKnowledge(PositionTree positionTree) {
		Optional<Knowledge> knowledge=repository.findById(positionTree.getId());
		KnowledgeDTO dto=getKnowledge(positionTree, entityToDTO(knowledge.get()));
		return dto;
	}

	
	private KnowledgeDTO getKnowledge(PositionTree positionTree, KnowledgeDTO dto) {
		List<Knowledge> Knowledges=getTree(positionTree.getId());
		if(positionTree.getDeep()==0 || Knowledges.isEmpty()) {
			return dto;
		}		
		Knowledges.forEach(k->dto.getChildren().add(getKnowledge(new PositionTree(positionTree.getDeep()-1,k.getId()), entityToDTO(k))));
		return dto;
	}
	
	private KnowledgeDTO entityToDTO(Knowledge knowledge) {
		KnowledgeDTO dto=new KnowledgeDTO();
		dto.setChildren(new ArrayList<>());
		dto.setContent(knowledge.getContent());
		dto.setCreatedAt(knowledge.getCreatedAt());
		dto.setId(knowledge.getId());
		dto.setParentId(knowledge.getParent().getId());
		dto.setTitle(knowledge.getTitle());
		dto.setUpdatedAt(knowledge.getUpdatedAt());
		return dto;
	}

}

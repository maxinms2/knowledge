package com.emejia.knowledge.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.emejia.knowledge.mappers.KnowledgeMapper;
import com.emejia.knowledge.model.dtos.KnowledgeDTO;
import com.emejia.knowledge.services.IKnowledgeService;

@RestController
@RequestMapping("/api/knowledge")

public class KnowledgeController {
	private final IKnowledgeService service;
	private final KnowledgeMapper mapper;

	public KnowledgeController(IKnowledgeService service, KnowledgeMapper mapper) {
		this.service = service;
		this.mapper = mapper;
	}

	@GetMapping("/{id}")
	public ResponseEntity<KnowledgeDTO> getById(@PathVariable Long id) {
		return ResponseEntity.ok(mapper.EntityToDTO(service.getKnowledge(id).orElse(service.nullObject())));
	}

	@PostMapping
	public ResponseEntity<KnowledgeDTO> create(@RequestBody KnowledgeDTO dto) {
		return ResponseEntity.ok(mapper.EntityToDTO(service.createKnowledge(dto)));
	}

	@GetMapping("/{id}/children")
	public ResponseEntity<List<KnowledgeDTO>> getChildren(@PathVariable Long id) {
		return ResponseEntity.ok(service.getTree(id).stream().map(x->mapper.EntityToDTO(x)).collect(Collectors.toList()));
	}
}

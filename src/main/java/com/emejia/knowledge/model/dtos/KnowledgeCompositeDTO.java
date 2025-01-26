package com.emejia.knowledge.model.dtos;

import java.util.Date;
import java.util.List;

public class KnowledgeCompositeDTO {
	private Long id;
	private String title;
	private String content;
	private Long parentId;
	private List<KnowledgeCompositeDTO> children;
	private Date createdAt;
	private Date updatedAt;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	public List<KnowledgeCompositeDTO> getChildren() {
		return children;
	}
	public void setChildren(List<KnowledgeCompositeDTO> children) {
		this.children = children;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	public Date getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
	
}

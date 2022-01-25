package com.bjpowernode.crm.workbench.domain;

public class ClueActivityRelationVo {
	
	private String relationId;
	private String name;
	private String startDate;
	private String endDate;
	private String owner;


	@Override
	public String toString() {
		return "ClueActivityRelationVo{" +
				"relationId='" + relationId + '\'' +
				", name='" + name + '\'' +
				", startDate='" + startDate + '\'' +
				", endDate='" + endDate + '\'' +
				", owner='" + owner + '\'' +
				'}';
	}

	public String getRelationId() {
		return relationId;
	}

	public void setRelationId(String relationId) {
		this.relationId = relationId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}
}

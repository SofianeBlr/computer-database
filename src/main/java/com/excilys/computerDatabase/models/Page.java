package com.excilys.computerDatabase.models;

public class Page {
	private int page=1;
	private int numberPerPage =10;
	private String search;
	private String orderBy; 
	
	
	public Page(int page, int numberPerPage, String search, String orderBy) {
		super();
		this.page = page;
		this.numberPerPage = numberPerPage;
		this.search = search;
		this.orderBy = orderBy;
	}
	public Page(int page, int numberPerPage) {
		super();
		this.page = page;
		this.numberPerPage = numberPerPage;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getNumberPerPage() {
		return numberPerPage;
	}
	public void setNumberPerPage(int numberPerPage) {
		this.numberPerPage = numberPerPage;
	}
	public String getSearch() {
		return search;
	}
	public void setSearch(String search) {
		this.search = search;
	}
	public String getOrderBy() {
		return orderBy;
	}
	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}
}

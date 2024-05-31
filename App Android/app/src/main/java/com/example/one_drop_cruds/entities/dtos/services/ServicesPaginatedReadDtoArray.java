package com.example.one_drop_cruds.entities.dtos.services;

import java.util.List;

public class ServicesPaginatedReadDtoArray {
    private List registros;
    private Long total_results;
    private Integer results_per_page;
    private Integer current_page;
    private Integer pages;
    private String sort_by;

    public ServicesPaginatedReadDtoArray(List registros, Long total_results, Integer results_per_page, Integer current_page, Integer pages, String sort_by) {
        this.registros = registros;
        this.total_results = total_results;
        this.results_per_page = results_per_page;
        this.current_page = current_page;
        this.pages = pages;
        this.sort_by = sort_by;
    }

    public List getServices() {
        return registros;
    }

    public void setServices(List registros) {
        this.registros = registros;
    }

    public Long getTotal_results() {
        return total_results;
    }

    public void setTotal_results(Long total_results) {
        this.total_results = total_results;
    }

    public Integer getResults_per_page() {
        return results_per_page;
    }

    public void setResults_per_page(Integer results_per_page) {
        this.results_per_page = results_per_page;
    }

    public Integer getCurrent_page() {
        return current_page;
    }

    public void setCurrent_page(Integer current_page) {
        this.current_page = current_page;
    }

    public Integer getPages() {
        return pages;
    }

    public void setPages(Integer pages) {
        this.pages = pages;
    }

    public String getSort_by() {
        return sort_by;
    }

    public void setSort_by(String sort_by) {
        this.sort_by = sort_by;
    }

    @Override
    public String toString() {
        return "ServicesPaginatedReadDtoArray{" +
                "registros=" + registros +
                ", total_results=" + total_results +
                ", results_per_page=" + results_per_page +
                ", current_page=" + current_page +
                ", pages=" + pages +
                ", sort_by='" + sort_by + '\'' +
                '}';
    }
}

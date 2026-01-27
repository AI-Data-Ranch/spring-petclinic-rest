/*
 * Copyright 2016-2024 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.samples.petclinic.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Generic wrapper for paginated responses.
 * Provides consistent pagination metadata across all API endpoints.
 *
 * @param <T> The type of content in the page
 */
public class PagedResponse<T> {

    @JsonProperty("content")
    private List<T> content;

    @JsonProperty("page")
    private int page;

    @JsonProperty("size")
    private int size;

    @JsonProperty("totalElements")
    private long totalElements;

    @JsonProperty("totalPages")
    private int totalPages;

    @JsonProperty("first")
    private boolean first;

    @JsonProperty("last")
    private boolean last;

    @JsonProperty("numberOfElements")
    private int numberOfElements;

    @JsonProperty("empty")
    private boolean empty;

    public PagedResponse() {
    }

    public PagedResponse(List<T> content, int page, int size, long totalElements, int totalPages,
                         boolean first, boolean last, int numberOfElements, boolean empty) {
        this.content = content;
        this.page = page;
        this.size = size;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
        this.first = first;
        this.last = last;
        this.numberOfElements = numberOfElements;
        this.empty = empty;
    }

    /**
     * Creates a PagedResponse from a Spring Data Page object.
     *
     * @param page The Spring Data Page
     * @param <T> The type of content
     * @return A new PagedResponse instance
     */
    public static <T> PagedResponse<T> of(Page<T> page) {
        return new PagedResponse<>(
            page.getContent(),
            page.getNumber(),
            page.getSize(),
            page.getTotalElements(),
            page.getTotalPages(),
            page.isFirst(),
            page.isLast(),
            page.getNumberOfElements(),
            page.isEmpty()
        );
    }

    public List<T> getContent() {
        return content;
    }

    public void setContent(List<T> content) {
        this.content = content;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(long totalElements) {
        this.totalElements = totalElements;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public boolean isFirst() {
        return first;
    }

    public void setFirst(boolean first) {
        this.first = first;
    }

    public boolean isLast() {
        return last;
    }

    public void setLast(boolean last) {
        this.last = last;
    }

    public int getNumberOfElements() {
        return numberOfElements;
    }

    public void setNumberOfElements(int numberOfElements) {
        this.numberOfElements = numberOfElements;
    }

    public boolean isEmpty() {
        return empty;
    }

    public void setEmpty(boolean empty) {
        this.empty = empty;
    }
}

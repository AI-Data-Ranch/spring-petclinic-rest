package org.springframework.samples.petclinic.rest.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Generated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

/**
 * A paginated list of pet owners with metadata.
 */
@Schema(name = "PagedOwners", description = "A paginated list of pet owners with metadata.")
@JsonTypeName("PagedOwners")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", comments = "Manual creation for pagination support")
public class PagedOwnersDto {

    @Valid
    private List<OwnerDto> content = new ArrayList<>();

    private Long totalElements;

    private Integer totalPages;

    private Integer size;

    private Integer number;

    private Integer numberOfElements;

    private Boolean first;

    private Boolean last;

    private Boolean empty;

    public PagedOwnersDto() {
        super();
    }

    public PagedOwnersDto content(List<OwnerDto> content) {
        this.content = content;
        return this;
    }

    public PagedOwnersDto addContentItem(OwnerDto contentItem) {
        if (this.content == null) {
            this.content = new ArrayList<>();
        }
        this.content.add(contentItem);
        return this;
    }

    @Schema(name = "content", description = "The list of owners on the current page.", requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonProperty("content")
    @NotNull
    @Valid
    public List<OwnerDto> getContent() {
        return content;
    }

    public void setContent(List<OwnerDto> content) {
        this.content = content;
    }

    public PagedOwnersDto totalElements(Long totalElements) {
        this.totalElements = totalElements;
        return this;
    }

    @Schema(name = "totalElements", example = "100", description = "The total number of owners across all pages.", requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonProperty("totalElements")
    @NotNull
    @Min(0)
    public Long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(Long totalElements) {
        this.totalElements = totalElements;
    }

    public PagedOwnersDto totalPages(Integer totalPages) {
        this.totalPages = totalPages;
        return this;
    }

    @Schema(name = "totalPages", example = "5", description = "The total number of pages.", requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonProperty("totalPages")
    @NotNull
    @Min(0)
    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public PagedOwnersDto size(Integer size) {
        this.size = size;
        return this;
    }

    @Schema(name = "size", example = "20", description = "The number of items per page.", requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonProperty("size")
    @NotNull
    @Min(0)
    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public PagedOwnersDto number(Integer number) {
        this.number = number;
        return this;
    }

    @Schema(name = "number", example = "0", description = "The current page number (zero-based).", requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonProperty("number")
    @NotNull
    @Min(0)
    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public PagedOwnersDto numberOfElements(Integer numberOfElements) {
        this.numberOfElements = numberOfElements;
        return this;
    }

    @Schema(name = "numberOfElements", example = "20", description = "The number of items in the current page.", requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonProperty("numberOfElements")
    @NotNull
    @Min(0)
    public Integer getNumberOfElements() {
        return numberOfElements;
    }

    public void setNumberOfElements(Integer numberOfElements) {
        this.numberOfElements = numberOfElements;
    }

    public PagedOwnersDto first(Boolean first) {
        this.first = first;
        return this;
    }

    @Schema(name = "first", example = "true", description = "Whether this is the first page.", requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonProperty("first")
    @NotNull
    public Boolean getFirst() {
        return first;
    }

    public void setFirst(Boolean first) {
        this.first = first;
    }

    public PagedOwnersDto last(Boolean last) {
        this.last = last;
        return this;
    }

    @Schema(name = "last", example = "false", description = "Whether this is the last page.", requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonProperty("last")
    @NotNull
    public Boolean getLast() {
        return last;
    }

    public void setLast(Boolean last) {
        this.last = last;
    }

    public PagedOwnersDto empty(Boolean empty) {
        this.empty = empty;
        return this;
    }

    @Schema(name = "empty", example = "false", description = "Whether the page is empty.", requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonProperty("empty")
    @NotNull
    public Boolean getEmpty() {
        return empty;
    }

    public void setEmpty(Boolean empty) {
        this.empty = empty;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PagedOwnersDto pagedOwners = (PagedOwnersDto) o;
        return Objects.equals(this.content, pagedOwners.content) &&
            Objects.equals(this.totalElements, pagedOwners.totalElements) &&
            Objects.equals(this.totalPages, pagedOwners.totalPages) &&
            Objects.equals(this.size, pagedOwners.size) &&
            Objects.equals(this.number, pagedOwners.number) &&
            Objects.equals(this.numberOfElements, pagedOwners.numberOfElements) &&
            Objects.equals(this.first, pagedOwners.first) &&
            Objects.equals(this.last, pagedOwners.last) &&
            Objects.equals(this.empty, pagedOwners.empty);
    }

    @Override
    public int hashCode() {
        return Objects.hash(content, totalElements, totalPages, size, number, numberOfElements, first, last, empty);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class PagedOwnersDto {\n");
        sb.append("    content: ").append(toIndentedString(content)).append("\n");
        sb.append("    totalElements: ").append(toIndentedString(totalElements)).append("\n");
        sb.append("    totalPages: ").append(toIndentedString(totalPages)).append("\n");
        sb.append("    size: ").append(toIndentedString(size)).append("\n");
        sb.append("    number: ").append(toIndentedString(number)).append("\n");
        sb.append("    numberOfElements: ").append(toIndentedString(numberOfElements)).append("\n");
        sb.append("    first: ").append(toIndentedString(first)).append("\n");
        sb.append("    last: ").append(toIndentedString(last)).append("\n");
        sb.append("    empty: ").append(toIndentedString(empty)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    private String toIndentedString(Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }
}

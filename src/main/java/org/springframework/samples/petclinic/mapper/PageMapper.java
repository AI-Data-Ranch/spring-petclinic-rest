package org.springframework.samples.petclinic.mapper;

import org.springframework.data.domain.Page;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.rest.dto.PagedOwnersDto;

/**
 * Utility class for converting Spring Data Page to PagedOwnersDto
 */
public class PageMapper {

    private PageMapper() {
        // Utility class
    }

    public static PagedOwnersDto toPagedOwnersDto(Page<Owner> page, OwnerMapper ownerMapper) {
        PagedOwnersDto pagedOwnersDto = new PagedOwnersDto();
        pagedOwnersDto.setContent(ownerMapper.toOwnerDtoCollection(page.getContent()));
        pagedOwnersDto.setTotalElements(page.getTotalElements());
        pagedOwnersDto.setTotalPages(page.getTotalPages());
        pagedOwnersDto.setSize(page.getSize());
        pagedOwnersDto.setNumber(page.getNumber());
        pagedOwnersDto.setNumberOfElements(page.getNumberOfElements());
        pagedOwnersDto.setFirst(page.isFirst());
        pagedOwnersDto.setLast(page.isLast());
        pagedOwnersDto.setEmpty(page.isEmpty());
        return pagedOwnersDto;
    }
}

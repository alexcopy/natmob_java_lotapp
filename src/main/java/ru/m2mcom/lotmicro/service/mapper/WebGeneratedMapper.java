package ru.m2mcom.lotmicro.service.mapper;

import ru.m2mcom.lotmicro.domain.*;
import ru.m2mcom.lotmicro.service.dto.WebGeneratedDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity WebGenerated and its DTO WebGeneratedDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface WebGeneratedMapper {

    WebGeneratedDTO webGeneratedToWebGeneratedDTO(WebGenerated webGenerated);

    List<WebGeneratedDTO> webGeneratedsToWebGeneratedDTOs(List<WebGenerated> webGenerateds);

    WebGenerated webGeneratedDTOToWebGenerated(WebGeneratedDTO webGeneratedDTO);

    List<WebGenerated> webGeneratedDTOsToWebGenerateds(List<WebGeneratedDTO> webGeneratedDTOs);
    /**
     * generating the fromId for all mappers if the databaseType is sql, as the class has relationship to it might need it, instead of
     * creating a new attribute to know if the entity has any relationship from some other entity
     *
     * @param id id of the entity
     * @return the entity instance
     */
     
    default WebGenerated webGeneratedFromId(Long id) {
        if (id == null) {
            return null;
        }
        WebGenerated webGenerated = new WebGenerated();
        webGenerated.setId(id);
        return webGenerated;
    }
    

}

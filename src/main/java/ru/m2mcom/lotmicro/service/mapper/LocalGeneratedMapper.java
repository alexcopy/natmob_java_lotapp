package ru.m2mcom.lotmicro.service.mapper;

import ru.m2mcom.lotmicro.domain.*;
import ru.m2mcom.lotmicro.service.dto.LocalGeneratedDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity LocalGenerated and its DTO LocalGeneratedDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface LocalGeneratedMapper {

    LocalGeneratedDTO localGeneratedToLocalGeneratedDTO(LocalGenerated localGenerated);

    List<LocalGeneratedDTO> localGeneratedsToLocalGeneratedDTOs(List<LocalGenerated> localGenerateds);

    LocalGenerated localGeneratedDTOToLocalGenerated(LocalGeneratedDTO localGeneratedDTO);

    List<LocalGenerated> localGeneratedDTOsToLocalGenerateds(List<LocalGeneratedDTO> localGeneratedDTOs);
    /**
     * generating the fromId for all mappers if the databaseType is sql, as the class has relationship to it might need it, instead of
     * creating a new attribute to know if the entity has any relationship from some other entity
     *
     * @param id id of the entity
     * @return the entity instance
     */
     
    default LocalGenerated localGeneratedFromId(Long id) {
        if (id == null) {
            return null;
        }
        LocalGenerated localGenerated = new LocalGenerated();
        localGenerated.setId(id);
        return localGenerated;
    }
    

}

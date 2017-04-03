package ru.m2mcom.lotmicro.service.mapper;

import ru.m2mcom.lotmicro.domain.*;
import ru.m2mcom.lotmicro.service.dto.JpInHistoryDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity JpInHistory and its DTO JpInHistoryDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface JpInHistoryMapper {

    JpInHistoryDTO jpInHistoryToJpInHistoryDTO(JpInHistory jpInHistory);

    List<JpInHistoryDTO> jpInHistoriesToJpInHistoryDTOs(List<JpInHistory> jpInHistories);

    JpInHistory jpInHistoryDTOToJpInHistory(JpInHistoryDTO jpInHistoryDTO);

    List<JpInHistory> jpInHistoryDTOsToJpInHistories(List<JpInHistoryDTO> jpInHistoryDTOs);
    /**
     * generating the fromId for all mappers if the databaseType is sql, as the class has relationship to it might need it, instead of
     * creating a new attribute to know if the entity has any relationship from some other entity
     *
     * @param id id of the entity
     * @return the entity instance
     */
     
    default JpInHistory jpInHistoryFromId(Long id) {
        if (id == null) {
            return null;
        }
        JpInHistory jpInHistory = new JpInHistory();
        jpInHistory.setId(id);
        return jpInHistory;
    }
    

}

package ru.m2mcom.lotmicro.service.mapper;

import ru.m2mcom.lotmicro.domain.*;
import ru.m2mcom.lotmicro.service.dto.HistoryDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity History and its DTO HistoryDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface HistoryMapper {

    HistoryDTO historyToHistoryDTO(History history);

    List<HistoryDTO> historiesToHistoryDTOs(List<History> histories);

    History historyDTOToHistory(HistoryDTO historyDTO);

    List<History> historyDTOsToHistories(List<HistoryDTO> historyDTOs);
    /**
     * generating the fromId for all mappers if the databaseType is sql, as the class has relationship to it might need it, instead of
     * creating a new attribute to know if the entity has any relationship from some other entity
     *
     * @param id id of the entity
     * @return the entity instance
     */
     
    default History historyFromId(Long id) {
        if (id == null) {
            return null;
        }
        History history = new History();
        history.setId(id);
        return history;
    }
    

}

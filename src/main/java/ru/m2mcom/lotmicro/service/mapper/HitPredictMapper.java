package ru.m2mcom.lotmicro.service.mapper;

import ru.m2mcom.lotmicro.domain.*;
import ru.m2mcom.lotmicro.service.dto.HitPredictDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity HitPredict and its DTO HitPredictDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface HitPredictMapper {

    HitPredictDTO hitPredictToHitPredictDTO(HitPredict hitPredict);

    List<HitPredictDTO> hitPredictsToHitPredictDTOs(List<HitPredict> hitPredicts);

    HitPredict hitPredictDTOToHitPredict(HitPredictDTO hitPredictDTO);

    List<HitPredict> hitPredictDTOsToHitPredicts(List<HitPredictDTO> hitPredictDTOs);
    /**
     * generating the fromId for all mappers if the databaseType is sql, as the class has relationship to it might need it, instead of
     * creating a new attribute to know if the entity has any relationship from some other entity
     *
     * @param id id of the entity
     * @return the entity instance
     */
     
    default HitPredict hitPredictFromId(Long id) {
        if (id == null) {
            return null;
        }
        HitPredict hitPredict = new HitPredict();
        hitPredict.setId(id);
        return hitPredict;
    }
    

}

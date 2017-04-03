package ru.m2mcom.lotmicro.service.mapper;

import ru.m2mcom.lotmicro.domain.*;
import ru.m2mcom.lotmicro.service.dto.MathExpectationDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity MathExpectation and its DTO MathExpectationDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface MathExpectationMapper {

    MathExpectationDTO mathExpectationToMathExpectationDTO(MathExpectation mathExpectation);

    List<MathExpectationDTO> mathExpectationsToMathExpectationDTOs(List<MathExpectation> mathExpectations);

    MathExpectation mathExpectationDTOToMathExpectation(MathExpectationDTO mathExpectationDTO);

    List<MathExpectation> mathExpectationDTOsToMathExpectations(List<MathExpectationDTO> mathExpectationDTOs);
    /**
     * generating the fromId for all mappers if the databaseType is sql, as the class has relationship to it might need it, instead of
     * creating a new attribute to know if the entity has any relationship from some other entity
     *
     * @param id id of the entity
     * @return the entity instance
     */
     
    default MathExpectation mathExpectationFromId(Long id) {
        if (id == null) {
            return null;
        }
        MathExpectation mathExpectation = new MathExpectation();
        mathExpectation.setId(id);
        return mathExpectation;
    }
    

}

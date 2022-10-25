package br.com.history.service.mapper;

import br.com.history.domain.*;
import br.com.history.service.dto.QuizDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Quiz} and its DTO {@link QuizDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface QuizMapper extends EntityMapper<QuizDTO, Quiz> {
    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    QuizDTO toDtoId(Quiz address);
}

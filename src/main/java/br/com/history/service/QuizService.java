package br.com.history.service;


import br.com.history.domain.Quiz;
import br.com.history.repository.QuizRepository;
import br.com.history.service.dto.QuizDTO;
import br.com.history.service.mapper.QuizMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Quiz}.
 */
@Service
@Transactional
public class QuizService {

    private final Logger log = LoggerFactory.getLogger(QuizService.class);

    private final QuizRepository quizRepository;

    private final QuizMapper quizMapper;

    public QuizService(QuizRepository quizRepository, QuizMapper quizMapper) {
        this.quizRepository = quizRepository;
        this.quizMapper = quizMapper;
    }

    /**
     * Save a Quiz.
     *
     * @param quizDTO the entity to save.
     * @return the persisted entity.
     */
    public QuizDTO save(QuizDTO quizDTO) {
        log.debug("Request to save Quiz : {}", quizDTO);
        Quiz quiz = quizMapper.toEntity(quizDTO);
        quiz = quizRepository.save(quiz);
        return quizMapper.toDto(quiz);
    }

    /**
     * Partially update a Quiz.
     *
     * @param quizDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<QuizDTO> partialUpdate(QuizDTO quizDTO) {
        log.debug("Request to partially update Quiz : {}", quizDTO);

        return quizRepository
            .findById(quizDTO.getId())
            .map(
                existingQuiz -> {
                    quizMapper.partialUpdate(existingQuiz, quizDTO);
                    return existingQuiz;
                }
            )
            .map(quizRepository::save)
            .map(quizMapper::toDto);
    }

    /**
     * Get all the Quizes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<QuizDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Quizes");
        return quizRepository.findAll(pageable).map(quizMapper::toDto);
    }

    /**
     * Get one Quiz by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<QuizDTO> findOne(Long id) {
        log.debug("Request to get Quiz : {}", id);
        return quizRepository.findById(id).map(quizMapper::toDto);
    }

    /**
     * Delete the Quiz by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Quiz : {}", id);
        quizRepository.deleteById(id);
    }
}


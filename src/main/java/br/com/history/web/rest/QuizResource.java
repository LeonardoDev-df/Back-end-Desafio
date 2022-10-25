package br.com.history.web.rest;


import br.com.history.repository.QuizRepository;
import br.com.history.service.QuizService;
import br.com.history.service.dto.QuizDTO;
import br.com.history.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link br.com.history.domain.Quiz}.
 */
@RestController
@RequestMapping("/api")
public class QuizResource {

    private final Logger log = LoggerFactory.getLogger(QuizResource.class);

    private static final String ENTITY_NAME = "quiz";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final QuizService quizService;

    private final QuizRepository quizRepository;

    public QuizResource(QuizService quizService, QuizRepository quizRepository) {
        this.quizService = quizService;
        this.quizRepository = quizRepository;
    }

    /**
     * {@code POST  /quiz} : Create a new Quiz.
     *
     * @param quizDTO the QuizDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new QuizDTO, or with status {@code 400 (Bad Request)} if the Quiz has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/quizes")
    public ResponseEntity<QuizDTO> createQuiz(@RequestBody QuizDTO quizDTO) throws URISyntaxException {
        log.debug("REST request to save Quiz : {}", quizDTO);
        if (quizDTO.getId() != null) {
            throw new BadRequestAlertException("A new Quiz cannot already have an ID", ENTITY_NAME, "idexists");
        }
        QuizDTO result = quizService.save(quizDTO);
        return ResponseEntity
            .created(new URI("/api/quizes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /Quizes/:id} : Updates an existing Quiz.
     *
     * @param id the id of the QuizDTO to save.
     * @param quizDTO the QuizDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated QuizDTO,
     * or with status {@code 400 (Bad Request)} if the QuizDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the QuizDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/quizes/{id}")
    public ResponseEntity<QuizDTO> updateQuiz(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody QuizDTO quizDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Quiz : {}, {}", id, quizDTO);
        if (quizDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, quizDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!quizRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        QuizDTO result = quizService.save(quizDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, quizDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /Quizes/:id} : Partial updates given fields of an existing Quiz, field will ignore if it is null
     *
     * @param id the id of the QuizDTO to save.
     * @param quizDTO the QuizDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated QuizDTO,
     * or with status {@code 400 (Bad Request)} if the QuizDTO is not valid,
     * or with status {@code 404 (Not Found)} if the QuizDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the QuizDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/quizes/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<QuizDTO> partialUpdateQuiz(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody QuizDTO quizDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Quiz partially : {}, {}", id, quizDTO);
        if (quizDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, quizDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!quizRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<QuizDTO> result = quizService.partialUpdate(quizDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, quizDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /Quizes} : get all the Quizes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of Quizes in body.
     */
    @GetMapping("/quizes")
    public ResponseEntity<List<QuizDTO>> getAllQuizes(Pageable pageable) {
        log.debug("REST request to get a page of Quizes");
        Page<QuizDTO> page = quizService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /Quizes/:id} : get the "id" Quiz.
     *
     * @param id the id of the QuizDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the QuizDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/quizes/{id}")
    public ResponseEntity<QuizDTO> getQuiz(@PathVariable Long id) {
        log.debug("REST request to get Quiz : {}", id);
        Optional<QuizDTO> quizDTO = quizService.findOne(id);
        return ResponseUtil.wrapOrNotFound(quizDTO);
    }

    /**
     * {@code DELETE  /quizes/:id} : delete the "id" quiz.
     *
     * @param id the id of the QuizDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/quizes/{id}")
    public ResponseEntity<Void> deleteQuiz(@PathVariable Long id) {
        log.debug("REST request to delete Quiz : {}", id);
        quizService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}

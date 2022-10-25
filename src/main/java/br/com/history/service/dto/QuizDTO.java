package br.com.history.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link br.com.history.domain.Quiz} entity.
 */
public class QuizDTO implements Serializable {

    private Long id;

    private String category;

    private String title;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof QuizDTO)) {
            return false;
        }

        QuizDTO quizDTO = (QuizDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, quizDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "QuizDTO{" +
            "id=" + getId() +
            ", category='" + getCategory() + "'" +
            ", title='" + getTitle() + "'" +
            "}";
    }
}

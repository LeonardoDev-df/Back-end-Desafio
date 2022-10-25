package br.com.history.service.dto;

import java.io.Serializable;

public class QuizMapDTO implements Serializable {

    private String category;
    private String title;

    public QuizMapDTO(String category, String title) {
        this.category = category;
        this.title = title;
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
}

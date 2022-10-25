package br.com.history.service.dto;

import br.com.history.domain.enumeration.StatusEnum;

public class HistoricalSiteUpdateStatusDTO {

    private String comment;

    private StatusEnum status;

    private String link;

    public HistoricalSiteUpdateStatusDTO() {}

    public HistoricalSiteUpdateStatusDTO(String comment, StatusEnum status, String link) {
        this.comment = comment;
        this.status = status;
        this.link = link;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public StatusEnum getStatus() {
        return status;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}

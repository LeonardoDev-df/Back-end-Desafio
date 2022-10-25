package br.com.history.service.dto;

import javax.persistence.Lob;
import java.io.Serializable;

public class SiteImageMapDTO implements Serializable {

    @Lob
    private byte[] imagePreview;

    public SiteImageMapDTO() {
    }

    public SiteImageMapDTO(byte[] imagePreview) {
        this.imagePreview = imagePreview;
    }

    public byte[] getImagePreview() {
        return imagePreview;
    }

    public void setImagePreview(byte[] imagePreview) {
        this.imagePreview = imagePreview;
    }
}

package br.com.history.service.dto;

import br.com.history.domain.ButtonPosition;

import javax.persistence.Lob;

public class SiteImage3DViewDTO {

    private Integer numberImage;

    @Lob
    private byte[] image3D;

    @Lob
    private byte[] imagePreview;

    private ButtonPosition buttonPosition;

    private String buttonColor;

    public SiteImage3DViewDTO() {
    }

    public SiteImage3DViewDTO(Integer numberImage, byte[] image3D, byte[] imagePreview, ButtonPosition buttonPosition, String buttonColor) {
        this.numberImage = numberImage;
        this.image3D = image3D;
        this.imagePreview = imagePreview;
        this.buttonPosition = buttonPosition;
        this.buttonColor = buttonColor;
    }

    public Integer getNumberImage() {
        return numberImage;
    }

    public void setNumberImage(Integer numberImage) {
        this.numberImage = numberImage;
    }

    public byte[] getImage3D() {
        return image3D;
    }

    public void setImage3D(byte[] image3D) {
        this.image3D = image3D;
    }

    public byte[] getImagePreview() {
        return imagePreview;
    }

    public void setImagePreview(byte[] imagePreview) {
        this.imagePreview = imagePreview;
    }

    public ButtonPosition getButtonPosition() {
        return buttonPosition;
    }

    public void setButtonPosition(ButtonPosition buttonPosition) {
        this.buttonPosition = buttonPosition;
    }

    public String getButtonColor() {
        return buttonColor;
    }

    public void setButtonColor(String buttonColor) {
        this.buttonColor = buttonColor;
    }
}

package br.com.history.domain;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@AttributeOverrides({
    @AttributeOverride( name = "x", column = @Column(name = "buttonPosition_x")),
    @AttributeOverride( name = "y", column = @Column(name = "buttonPosition_y")),
    @AttributeOverride( name = "z", column = @Column(name = "buttonPosition_z"))
})
public class ButtonPosition {

    private Integer x;
    private Integer y;
    private Integer z;

    public ButtonPosition() {
    }

    public ButtonPosition(Integer x, Integer y, Integer z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Integer getX() {
        return x;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public Integer getY() {
        return y;
    }

    public void setY(Integer y) {
        this.y = y;
    }

    public Integer getZ() {
        return z;
    }

    public void setZ(Integer z) {
        this.z = z;
    }
}

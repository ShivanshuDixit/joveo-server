package entity.error;

import java.io.Serializable;

import lombok.Data;

@Data
public class ErrorModel implements Serializable {

    private static final long serialVersionUID = 1L;
    private String error;

    public ErrorModel(String error) {
        this.error = error;
    }
}

package mini.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties
public class ResponseDto {

    private List<String> errors = new ArrayList<>();

    public ResponseDto(List<String> errors){
        this.setErrors(errors);
    }

    public ResponseDto(){
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }
}

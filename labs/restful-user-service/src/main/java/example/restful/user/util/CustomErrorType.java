package example.restful.user.util;

import lombok.Data;

public @Data class CustomErrorType {
    private String errorMessage;

    public CustomErrorType(String errorMessage){
        this.errorMessage = errorMessage;
    }
}

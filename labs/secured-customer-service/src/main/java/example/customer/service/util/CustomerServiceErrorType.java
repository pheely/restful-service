package example.customer.service.util;

import lombok.Data;

public @Data class CustomerServiceErrorType {
    private String errorMessage;

    public CustomerServiceErrorType(String errorMessage){
        this.errorMessage = errorMessage;
    }
}

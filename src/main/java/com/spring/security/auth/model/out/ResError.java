package com.spring.security.auth.model.out;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResError {

    private List<ErrorInfos> errors;
    private String status;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class ErrorInfos {
        private String code;
        private String message;
    }
}

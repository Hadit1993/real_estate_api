package com.hadit1993.realestate.api.utils.templetes;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;




@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class ResponseTemplate<T> {

    @Builder.Default
    private T data = null;

    @Builder.Default
    private boolean success = true;

    @Builder.Default
    private String message = "The operation was successful";

    public ResponseEntity<ResponseTemplate<T>> convertToResponse() {
        return ResponseEntity.ok(this);
    }
    public ResponseEntity<ResponseTemplate<T>> convertToResponse(HttpStatus status) {
        return new ResponseEntity<>(this, status);
    }
}

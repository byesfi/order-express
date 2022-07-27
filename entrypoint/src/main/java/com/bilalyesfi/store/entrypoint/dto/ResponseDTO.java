package com.bilalyesfi.store.entrypoint.dto;

import org.springframework.http.HttpStatus;

public class ResponseDTO<T> {
  private HttpStatus status;

  private String message = "Success!";

  private T body;

  public ResponseDTO(HttpStatus status, String message, T body) {
    this.status = status;
    this.message = message;
    this.body = body;
  }

  public ResponseDTO() {
  }

  public HttpStatus getStatus() {
    return status;
  }

  public void setStatus(HttpStatus status) {
    this.status = status;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public T getBody() {
    return body;
  }

  public void setBody(T body) {
    this.body = body;
  }

  public static <T> Builder<T> builder() {
    return new Builder<>();
  }

  public static class Builder<T>{

    private HttpStatus status;

    private String message = "Success!";

    private T body;

     public Builder<T> status(HttpStatus status){
       this.status = status;
       return this;
     }

     public Builder<T> message(String message){
       this.message = message;
       return this;
     }

     public Builder<T> body(T body){
       this.body = body;
       return this;
     }

     public ResponseDTO<T> build(){
       return new ResponseDTO<>(status, message, body);
     }
  }
}
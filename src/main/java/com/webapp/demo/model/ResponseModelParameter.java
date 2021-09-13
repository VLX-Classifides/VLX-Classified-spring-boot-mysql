package com.webapp.demo.model;

public class ResponseModelParameter<T> extends ResponseModel {

    private T result;

    public ResponseModelParameter(boolean success, T result) {
        super(success);
        this.result=result;
    }

    public ResponseModelParameter(boolean success, String message, T result) {
        super(success, message);
        this.result=result;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }
}
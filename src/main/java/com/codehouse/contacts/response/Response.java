package com.codehouse.contacts.response;


import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response<T> {
    private T result;

    public static <T> Response<T> of(T result) {
        var response = new Response<T>();
        response.result = result;
        return response;
    }

    public T getResult() {
        return result;
    }
}

package com.example.demo.exception;

public class MovieNotFoundExeption extends Throwable{
    public MovieNotFoundExeption(String message) {
        super(message);
    }
}

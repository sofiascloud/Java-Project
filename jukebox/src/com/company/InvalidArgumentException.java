package com.company;

//custom exception
    public class InvalidArgumentException extends Exception {
        public InvalidArgumentException(String message) {
            //call the parent class's constructor.
            super(message);
        }
    }


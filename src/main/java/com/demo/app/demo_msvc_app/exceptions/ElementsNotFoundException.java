package com.demo.app.demo_msvc_app.exceptions;

public class ElementsNotFoundException extends RuntimeException {
    public ElementsNotFoundException(String message) {
        super(message);
    }
}

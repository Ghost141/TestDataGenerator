package com.topcoder.contest.assembly;

import org.springframework.stereotype.Component;

@Component
public class HelloService {
    public String sayHello() {
        return "Hello world!";
    }

    public static void main(String[] args) {
    }
}

package com.microservice.restservice.auxillary;

@FunctionalInterface
public interface PostRequest<T, U, P> {
    void sendAndCheck(T t, U u, P p) throws Exception;
}

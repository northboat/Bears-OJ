package com.northboat.remotecontrollerserver.service;

public interface CommandService {

    String shutdown(String token);

    String clean(String token);

    String ipconfig(String token);
}

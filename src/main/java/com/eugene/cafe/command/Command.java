package com.eugene.cafe.command;

import jakarta.servlet.http.HttpServletRequest;

public interface Command {

    // todo: need to replace String
    String execute(HttpServletRequest request);
}

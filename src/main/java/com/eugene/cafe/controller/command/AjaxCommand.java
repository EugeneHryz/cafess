package com.eugene.cafe.controller.command;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public interface AjaxCommand {

    void execute(HttpServletRequest request, HttpServletResponse response);
}

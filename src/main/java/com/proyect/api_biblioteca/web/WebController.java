package com.proyect.api_biblioteca.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controlador web simple para mostrar información de la API
 */
@Controller
public class WebController {

    /**
     * Página de inicio simple
     */
    @GetMapping
    public String home() {
        return "home";
    }
}
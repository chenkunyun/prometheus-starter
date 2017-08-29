package com.kchen.prometheus.demo.controller;

import io.prometheus.client.Counter;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/")
public class HomeController {

    private static final Counter counters = Counter.build()
            .name("http_requests_total")
            .labelNames("app", "uri", "method")
            .help("counter the request")
            .register();

    @RequestMapping("/")
    public String home(HttpServletRequest request) {
        counters.labels("demo", request.getRequestURI(), request.getMethod()).inc();
        return "home";
    }
}

package com.example.triangleapi;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//@RestController tells Spring Boot that this class handles API requests.
//@PostMapping("/classify") creates an endpoint that accepts POST requests at /classify.
//@RequestBody tells Spring Boot to turn the incoming JSON into a Triangle object.
//The method returns a message confirming that the data was received.

@RestController
public class TriangleController {

    private final TriangleService triangleService;

    public TriangleController(TriangleService triangleService) {
        this.triangleService = triangleService;
    }

    @PostMapping("/classify")
    public ResponseEntity<Map<String, Object>> classifyTriangle(
            @RequestBody Triangle triangle) {

        Map<String, Object> result = triangleService.classify(triangle);

        if (!((Boolean) result.get("valid"))) {
            return ResponseEntity.badRequest().body(result);
        }

        return ResponseEntity.ok(result);
    }
}


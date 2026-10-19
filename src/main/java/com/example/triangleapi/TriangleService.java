package com.example.triangleapi;

import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class TriangleService {
    public Map<String, Object> classify(Triangle triangle) {
        if (triangle == null || triangle.getSideA() == null || triangle.getSideB() == null || triangle.getSideC() == null) {
            return Map.of("valid", false, "error", "All three sides are required.");
        }

        double a = triangle.getSideA();
        double b = triangle.getSideB();
        double c = triangle.getSideC();

        // All measurements must be positive and finite.
        if (!Double.isFinite(a) || !Double.isFinite(b) || !Double.isFinite(c) || a <= 0 || b <= 0 || c <= 0) {
            return Map.of("valid", false, "error", "Sides must be positive, finite numbers.");
        }

        // Sort the sides so c is the largest.
        double[] sides = {a, b, c};
        java.util.Arrays.sort(sides);

        a = sides[0];
        b = sides[1];
        c = sides[2];

        // Triangle inequality: the two shorter sides
        // must add up to more than the longest side.
        if (a <= c - b) {
            return Map.of("valid", false, "error", "Triangle inequality violated.");
        }

        String sideType;

        if (a == c) {
            sideType = "Equilateral";
        } else if (a == b || b == c) {
            sideType = "Isosceles";
        } else {
            sideType = "Scalene";
        }

        // Scale the sides to reduce overflow risk.
        double x = a / c;
        double y = b / c;
        double sumOfSquares = x * x + y * y;
        double difference = sumOfSquares - 1.0;

        String angleType;
        double tolerance = 1e-12;

        if (Math.abs(difference) <= tolerance) {
            angleType = "Right";
        } else if (difference > 0) {
            angleType = "Acute";
        } else {
            angleType = "Obtuse";
        }

        return Map.of(
                "valid", true,
                "sideType", sideType,
                "angleType", angleType
        );
    }
}

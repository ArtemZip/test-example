package org.example;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.SynchronousQueue;

@RestController
@RequestMapping("/hello")
public class ServiceController {
    private final SynchronousQueue<String> queue = new SynchronousQueue<>();

    @GetMapping
    ResponseEntity<String> getHello() {
        var e = queue.poll();
        return ResponseEntity.ok(e);
    }

    @GetMapping("/size")
    ResponseEntity<Integer> getSize() {
        var size = queue.size();
        return ResponseEntity.ok(size);
    }

    @PostMapping
    ResponseEntity<String> postHello(@RequestParam("hello") String hello) {
        if (hello == null) {
            return ResponseEntity.badRequest().build();
        }

        try {
            queue.put(hello);
        } catch (InterruptedException e) {
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok(hello);
    }
}

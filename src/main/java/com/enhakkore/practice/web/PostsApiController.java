package com.enhakkore.practice.web;

import com.enhakkore.practice.service.posts.PostsService;
import com.enhakkore.practice.web.dto.PostsResponseDto;
import com.enhakkore.practice.web.dto.PostsSaveRequestDto;
import com.enhakkore.practice.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class PostsApiController {

    private final PostsService postsService;

    @PostMapping("/api/v1/posts")
    public Long save(@RequestBody PostsSaveRequestDto requestDto) {
        return postsService.save(requestDto);
    }

    @PutMapping("/api/v1/posts/{id}")
    public Long Update(@PathVariable Long id, @RequestBody PostsUpdateRequestDto requestDto) {
        return postsService.update(id, requestDto);
    }

    @GetMapping("/api/v1/posts/{id}")
    public PostsResponseDto findBydId (@PathVariable Long id) {
        return postsService.findById(id);
    }
}

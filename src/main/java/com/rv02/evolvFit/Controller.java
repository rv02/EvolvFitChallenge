package com.rv02.evolvFit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class Controller {
    private final BlogRepository blogRepository;
    private final CommentRepository commentRepository;

    @Autowired
    public Controller(BlogRepository blogRepository, CommentRepository commentRepository) {
        this.blogRepository = blogRepository;
        this.commentRepository = commentRepository;
    }

    @PostMapping(value = "/blogs", consumes = "application/json")
    @ResponseBody
    public Blog createBlog(@Valid @RequestBody Blog blog) {
        return blogRepository.save(blog);
    }

    @GetMapping(path = "/blogs")
    public List<Blog> getBlogs() {
        return blogRepository.findAll();
    }

    @GetMapping(path = "/blogs/{id}")
    public Blog getBlog(@PathVariable int id) {
        Blog blog = blogRepository.findById(id)
                .orElseThrow(() -> new BlogNotFoundException());
        return blog;
    }

    @DeleteMapping(path = "/blogs/{id}")
    public Blog deleteBlog(@PathVariable int id) {
        Blog blog = blogRepository.findById(id)
                .orElseThrow(() -> new BlogNotFoundException());
        blogRepository.deleteById(id);
        return blog;
    }

    @PutMapping(path = "/blogs/{id}")
    public ResponseEntity<Blog> updateBlog(@PathVariable int id,
                @Valid @RequestBody Blog newBlog) {
        Blog blog = blogRepository.findById(id)
                .orElseThrow(() -> new BlogNotFoundException());
        blog.setTitle(newBlog.getTitle());
        blog.setText(newBlog.getText());
        return ResponseEntity.ok(blogRepository.save(blog));
    }

    @PostMapping(path = "/blogs/{id}/comment")
    @ResponseBody
    public ResponseEntity<Comment> postComment(@PathVariable int id,
            @Valid @RequestBody Comment comment) {
        Blog blog = blogRepository.findById(id)
                .orElseThrow(() -> new BlogNotFoundException());
        Comment newComment = new Comment(comment.getText(), blog);
        newComment = commentRepository.save(newComment);
        List<Comment> commentList = blog.getComments();
        commentList.add(newComment);
        blog.setComments(commentList);
        blogRepository.save(blog);
        return ResponseEntity.ok(newComment);
    }

    @GetMapping(path = "/blogs/{id}/comment")
    public List<Comment> getComments(@PathVariable int id) {
        Blog blog = blogRepository.findById(id)
                .orElseThrow(() -> new BlogNotFoundException());
        return blog.getComments();
    }
}

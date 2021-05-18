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
    private final ParentChildCommentRepository parentChildCommentRepository;

    @Autowired
    public Controller(BlogRepository blogRepository,
                      CommentRepository commentRepository,
                      ParentChildCommentRepository pccRepository) {
        this.blogRepository = blogRepository;
        this.commentRepository = commentRepository;
        this.parentChildCommentRepository = pccRepository;
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
        return blogRepository.findById(id)
                .orElseThrow(DataNotFoundException::new);
    }

    @DeleteMapping(path = "/blogs/{id}")
    public Blog deleteBlog(@PathVariable int id) {
        Blog blog = blogRepository.findById(id)
                .orElseThrow(DataNotFoundException::new);
        blogRepository.deleteById(id);
        return blog;
    }

    @PutMapping(path = "/blogs/{id}")
    public ResponseEntity<Blog> updateBlog(@PathVariable int id,
                @Valid @RequestBody Blog newBlog) {
        Blog blog = blogRepository.findById(id)
                .orElseThrow(DataNotFoundException::new);
        blog.setTitle(newBlog.getTitle());
        blog.setText(newBlog.getText());
        return ResponseEntity.ok(blogRepository.save(blog));
    }

    @PostMapping(path = "/blogs/{id}/comment")
    @ResponseBody
    public ResponseEntity<Comment> postComment(@PathVariable int id,
            @Valid @RequestBody Comment comment) {
        Blog blog = blogRepository.findById(id)
                .orElseThrow(DataNotFoundException::new);
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
                .orElseThrow(DataNotFoundException::new);
        return blog.getComments();
    }

    @PostMapping(path = "/comment/{id}")
    @ResponseBody
    public ResponseEntity<Comment> postReply(@PathVariable int id,
            @Valid @RequestBody Comment reply) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(DataNotFoundException::new);
        reply.setBlog(comment.getBlog());
        reply = commentRepository.save(reply);
        parentChildCommentRepository.save(
                new ParentChildComment(comment, reply));
        for (Integer i : parentChildCommentRepository.findAllParents(comment.getId())) {
            Comment c = commentRepository.findById(i)
                    .orElseThrow(DataNotFoundException::new);
            parentChildCommentRepository.save(
                    new ParentChildComment(c , reply));
        }
        return ResponseEntity.ok(reply);
    }

    @GetMapping(path = "/comment/{id}")
    public List<Comment> getReplies(@PathVariable int id) {
        return commentRepository.findAllChidren(id);
    }
}

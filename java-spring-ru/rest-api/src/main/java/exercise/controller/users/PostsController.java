package exercise.controller.users;

import exercise.Data;
import exercise.model.Post;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

// BEGIN
@RestController
@RequestMapping("/api")
public class PostsController {
    private List<Post> posts = Data.getPosts();

    @GetMapping("/users/{id}/posts")
    public List<Post> getAllUserPosts(@PathVariable Integer id) {
        var result = posts.stream().filter(p -> p.getUserId() == id).toList();

        return result;
    }

    @PostMapping("/users/{id}/posts")
    @ResponseStatus(HttpStatus.CREATED)
    public Post createNewPost(@PathVariable Integer id, @RequestBody Post body) {
        var post = new Post();
        post.setUserId(id);
        post.setTitle(body.getTitle());
        post.setBody(body.getBody());
        post.setSlug(body.getSlug());

        posts.add(post);

        return post;
    }
}
// END

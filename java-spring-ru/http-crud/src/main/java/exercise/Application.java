package exercise;

import java.util.List;
import java.util.Optional;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import exercise.model.Post;

@SpringBootApplication
@RestController
public class Application {
    // Хранилище добавленных постов
    private List<Post> posts = Data.getPosts();

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    // BEGIN
    @GetMapping("/posts")
    public List<Post> index(@RequestParam(defaultValue = "10") Integer limit,
                            @RequestParam(defaultValue = "0") Integer page) {
        return posts.stream()
                .skip((long) limit * page)
                .limit(limit).toList();
    }

    @GetMapping("/posts/{id}")
    public Optional<Post> show(@PathVariable String id) {
        var post = posts.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst();
        return post;
    }

    @PostMapping("/posts")
    public Post create(@RequestBody Post data) {
        posts.add(data);
        return data;
    }

    @PutMapping("/posts/{id}")
    public Post update(@PathVariable String id, @RequestBody Post data) {
        var maybePost = posts.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst();
        if (maybePost.isPresent()) {
            var post = maybePost.get();
            post.setBody(data.getBody());
            post.setTitle(data.getTitle());
        }
        return data;
    }

    @DeleteMapping("/posts/{id}")
    public void delete(@PathVariable String id) {
        posts.removeIf(post -> post.getId().equals(id));
    }
    // END
}

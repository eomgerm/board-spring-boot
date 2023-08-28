package eomgerm.simpleboard.post.domain;

import eomgerm.simpleboard.global.base.BaseEntity;
import eomgerm.simpleboard.user.domain.User;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "posts")
public class Post extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;

    private String title;

    private String content;

    private Long likeCount;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "author")
    private User author;

    private Post(String title, String content, User author) {
        this.title = title;
        this.content = content;
        this.author = author;
        this.likeCount = 0L;
    }

    public static Post createPost(String title, String content, User author) {
        return new Post(title, content, author);
    }

}

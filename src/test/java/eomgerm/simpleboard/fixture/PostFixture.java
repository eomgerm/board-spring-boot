package eomgerm.simpleboard.fixture;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PostFixture {
    DOG("I Like Dogs!", "Dog is so cute!");


    private final String title;
    private final String content;
}

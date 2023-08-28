package eomgerm.simpleboard.post.domain;

import eomgerm.simpleboard.global.base.BaseException;
import eomgerm.simpleboard.post.exception.PostErrorCode;
import java.util.HashMap;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SearchType {
    TITLE("title"),
    CONTENT("content"),
    AUTHOR("author");

    private final String value;
    private static final Map<String, SearchType> map = new HashMap<>();

    static {
        for (SearchType searchType : values()) {
            map.put(searchType.value, searchType);
        }
    }

    public static SearchType findSearchTypeByValue(String searchTypeValue) {
        if (map.containsKey(searchTypeValue)) {
            return map.get(searchTypeValue);
        } else {
            throw BaseException.of(PostErrorCode.INVALID_SEARCH_TYPE);
        }
    }

}

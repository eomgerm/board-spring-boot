package eomgerm.simpleboard.post.domain;

import eomgerm.simpleboard.global.base.BaseException;
import eomgerm.simpleboard.post.exception.PostErrorCode;
import java.util.HashMap;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SortCondition {
    RECENT("recent"),
    LIKE("like");

    private final String value;
    private static final Map<String, SortCondition> map = new HashMap<>();

    static {
        for (SortCondition sortCondition : values()) {
            map.put(sortCondition.value, sortCondition);
        }
    }

    public static SortCondition findSortConditionByValue(String sortConditionValue) {
        if (map.containsKey(sortConditionValue)) {
            return map.get(sortConditionValue);
        } else {
            throw BaseException.of(PostErrorCode.INVALID_SORT_CONDITION);
        }
    }
}

package education.sapios.Sapios.entity;

import jakarta.annotation.Nullable;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum SocialMedia {
    FACEBOOK("https://www.facebook.com/", 0),
    INSTAGRAM("https://www.instagram.com/", 1),
    LINKEDIN("https://www.linkedin.com/", 2),
    TWITTER("https://twitter.com/", 3),
    YOUTUBE("https://www.youtube.com/", 4),
    TIKTOK("https://www.tiktok.com/", 5),
    SNAPCHAT("https://www.snapchat.com/", 6),
    REDDIT("https://www.reddit.com/", 7);

    private static final Map<String, SocialMedia> byName = Arrays.stream(SocialMedia.values())
            .toList()
            .stream()
            .collect(Collectors.toMap(SocialMedia::name, Function.identity()));

    private static final Map<Integer, SocialMedia> byOrder = Arrays.stream(SocialMedia.values())
            .toList()
            .stream()
            .collect(Collectors.toMap(SocialMedia::getOrder, Function.identity()));
    private final String url;
    private final int order;

    SocialMedia(String url,
                int order) {
        this.url = url;
        this.order = order;
    }

    @Nullable
    public static SocialMedia getByName(String name) {
        return byName.get(name);
    }

    @Nullable
    public static SocialMedia getByOrder(int order) {
        return byOrder.get(order);
    }

    public String getUrl() {
        return url;
    }

    public int getOrder() {
        return order;
    }
}

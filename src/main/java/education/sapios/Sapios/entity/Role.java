package education.sapios.Sapios.entity;

import jakarta.annotation.Nullable;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum Role {
    STUDENT(0),
    TEACHER(1),
    ADMINISTRATOR(2);

    private static final Map<String, Role> byName = Arrays.stream(Role.values())
            .toList()
            .stream()
            .collect(Collectors.toMap(Role::name, Function.identity()));

    private static final Map<Integer, Role> byOrder = Arrays.stream(Role.values())
            .toList()
            .stream()
            .collect(Collectors.toMap(Role::getOrder, Function.identity()));

    private final int order;

    Role(int order) {
        this.order = order;
    }

    @Nullable
    public static Role getByOrder(int order) {
        return byOrder.get(order);
    }

    @Nullable
    public static Role getByName(String name) {
        return byName.get(name);
    }

    public int getOrder() {
        return order;
    }
}

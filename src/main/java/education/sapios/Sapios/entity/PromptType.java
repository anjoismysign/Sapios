package education.sapios.Sapios.entity;

import jakarta.annotation.Nullable;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum PromptType {
    ANALOGY(0),
    EXAMPLE_LIST(1),
    DEFINITION(2);

    private static final Map<String, PromptType> byName = Arrays.stream(PromptType.values())
            .toList()
            .stream()
            .collect(Collectors.toMap(PromptType::name, Function.identity()));

    private static final Map<Integer, PromptType> byOrder = Arrays.stream(PromptType.values())
            .toList()
            .stream()
            .collect(Collectors.toMap(PromptType::getOrder, Function.identity()));

    private final int order;

    PromptType(int order) {
        this.order = order;
    }

    @Nullable
    public static PromptType getByOrder(int order) {
        return byOrder.get(order);
    }

    @Nullable
    public static PromptType getByName(String name) {
        return byName.get(name);
    }

    public int getOrder() {
        return order;
    }
}

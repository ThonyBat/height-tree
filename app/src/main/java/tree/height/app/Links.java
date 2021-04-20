package tree.height.app;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.*;

@JsonDeserialize(using = LinksDeserializer.class)
public class Links {
    private static Map<String, Collection<String>> MAP = new HashMap<>();
    private static Map<String, Integer> HEIGHT = new HashMap<>();

    public void addLink(String parent, String child) {
        addParent(parent);
        Collection<String> children = Links.MAP.get(parent);
        addChild(children, child);
        updateHeight(child, Links.HEIGHT.get(parent));
    }

    public void addLink(String parent, Collection<String> children) {
        addParent(parent);
        Collection<String> oldChildren = Links.MAP.get(parent);
        children.stream().forEach(c -> {
            addChild(oldChildren, c);
            updateHeight(c, Links.HEIGHT.get(parent));
        });
    }

    private void addParent(String parent) {
        if (!Links.MAP.containsKey(parent))
            Links.MAP.put(parent, new HashSet<>());
        addNode(parent);
    }

    private void addChild(Collection<String> children, String child) {
        if (!children.contains(child))
            children.add(child);
        addNode(child);
    }

    private void addNode(String node) {
        if (!Links.HEIGHT.containsKey(node))
            Links.HEIGHT.put(node, 0);
    }

    private void updateHeight(String node, Integer heightParent) {
        if (!(Links.HEIGHT.get(node) > heightParent))
            Links.HEIGHT.put(node, heightParent + 1);
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        Links.MAP.forEach((key, values) -> {
            result.append(key + " :\n");
            values.forEach(value -> {
                result.append("\t" + value + "\n");
            });
        });
        Links.HEIGHT.forEach((key, value) -> {
            result.append(key + " : " + value + "\n");
        });
        return result.toString();
    }
}

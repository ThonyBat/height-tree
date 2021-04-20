package tree.height.app;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.util.Arrays;

public class LinksDeserializer extends StdDeserializer<Links> {
    protected LinksDeserializer() {
        super(Links.class);
    }

    @Override
    public Links deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        Links links = new Links();
        p.readValuesAs(Link[].class).forEachRemaining(linker -> {
            Arrays.stream(linker).forEach(link -> {
                if (link.parent != null && link.parent != ""){
                    if (link.child != null && link.child != "")
                        links.addLink(link.parent, link.child);
                    if (link.children != null && link.children.size() != 0)
                        links.addLink(link.parent, link.children);
                }
            });
        });
        return links;
    }
}

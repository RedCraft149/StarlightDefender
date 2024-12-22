package com.redcraft.communication.fields;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class GFieldRegistry {

    Map<UUID, Map<String,GField<?>>> fields;

    public GFieldRegistry() {
        fields = new HashMap<>();
    }

    public GField<?> getField(UUID owner, String name) {
        Map<String,GField<?>> container = fields.get(owner);
        if(container == null) return null;
        return container.get(name);
    }

    public void addField(UUID owner, String name, GField<?> field) {
        Map<String,GField<?>> container = fields.get(owner);
        if(container == null) {
            container = new HashMap<>();
            fields.put(owner,container);
        }
        container.put(name,field);
    }

}

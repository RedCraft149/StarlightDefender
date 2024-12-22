package com.redcraft.rlib.keyless;

import com.redcraft.rlib.function.Predicate;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

/**
 * A HashMap in which a value contains its own key.
 * @param <K> Key type
 * @param <V> Value type
 */
public class KeylessMap<K, V extends KeyProvider<K>> extends HashMap<K,V> {
    public void add(V v) {
        put(v.getKey(),v);
    }
    public void removeValue(V v) {
        super.remove(v.getKey());
    }
    public void removeIf(Predicate<V> predicate) {
        Set<Entry<K,V>> entries = entrySet();
        Iterator<Entry<K,V>> itr = entries.iterator();
        while (itr.hasNext()) {
            if(predicate.test(itr.next().getValue())) itr.remove();
        }
    }
}

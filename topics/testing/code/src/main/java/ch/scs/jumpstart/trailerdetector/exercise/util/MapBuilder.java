package ch.scs.jumpstart.trailerdetector.exercise.util;

import java.util.LinkedHashMap;
import java.util.Map;

public class MapBuilder<K, V> {
  public static <U, V> MapBuilder<U, V> aLinkedHashMap() {
    return new MapBuilder<>(new LinkedHashMap<>());
  }

  private final Map<K, V> map;

  private MapBuilder(Map<K, V> emptyMap) {
    map = emptyMap;
  }

  public MapBuilder<K, V> with(K key, V value) {
    map.put(key, value);
    return this;
  }

  public Map<K, V> build() {
    return map;
  }
}

package ch.scs.jumpstart.pattern.examples.checkers.util;

import java.util.AbstractMap;

public class Tuple<K, V> extends AbstractMap.SimpleImmutableEntry<K, V> {
  public static <K, V> Tuple<K, V> of(K key, V value) {
    return new Tuple<>(key, value);
  }

  private Tuple(K key, V value) {
    super(key, value);
  }
}

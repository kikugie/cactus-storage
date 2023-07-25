package dev.kikugie.cactus_storage.util;

import dev.kikugie.cactus_storage.mixin.Object2LongOpenHashMapAccessor;
import it.unimi.dsi.fastutil.objects.Object2LongOpenHashMap;

import java.util.Iterator;
import java.util.Random;

public class RandomizedIterator<E> implements Iterator<E> {
    private static final Random random = new Random();
    private final E[] elements;
    private final int offset;
    private int index;

    public RandomizedIterator(E[] elements) {
        this.elements = elements;
        this.offset = random.nextInt(elements.length);
    }

    @SuppressWarnings("unchecked")
    public static <E> RandomizedIterator<E> from(Object2LongOpenHashMap<E> map) {
        return new RandomizedIterator<>((E[]) ((Object2LongOpenHashMapAccessor) map).getKeys());
    }

    @Override
    public boolean hasNext() {
        return this.index < this.elements.length;
    }

    @Override
    public E next() {
        return this.elements[(this.index++ + this.offset) % this.elements.length];
    }
}

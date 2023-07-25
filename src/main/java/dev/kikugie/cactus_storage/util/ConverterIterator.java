package dev.kikugie.cactus_storage.util;

import java.util.Iterator;
import java.util.function.Function;

public class ConverterIterator<E, T> implements Iterator<E> {
    private final Iterator<T> delegate;
    private final Function<T, E> converter;

    public ConverterIterator(Iterator<T> delegate, Function<T, E> converter) {
        this.delegate = delegate;
        this.converter = converter;
    }

    @Override
    public boolean hasNext() {
        return this.delegate.hasNext();
    }

    @Override
    public E next() {
        return this.converter.apply(this.delegate.next());
    }
}

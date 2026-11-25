package org.jetbrains.kotlin.org.jline.reader;

import java.io.IOException;
import java.time.Instant;
import java.util.Iterator;
import java.util.ListIterator;

/* JADX INFO: loaded from: classes4.dex */
public interface History extends Iterable<Entry> {

    public interface Entry {
        int index();

        String line();

        Instant time();
    }

    void add(Instant instant, String str);

    void attach(LineReader lineReader);

    String current();

    int first();

    String get(int i);

    int index();

    ListIterator<Entry> iterator(int i);

    int last();

    void load() throws IOException;

    boolean moveTo(int i);

    void moveToEnd();

    boolean moveToFirst();

    boolean moveToLast();

    boolean next();

    boolean previous();

    void purge() throws IOException;

    void save() throws IOException;

    int size();

    default boolean isEmpty() {
        return size() == 0;
    }

    default void add(String str) {
        add(Instant.now(), str);
    }

    @Override // java.lang.Iterable
    default Iterator<Entry> iterator() {
        return iterator(first());
    }
}

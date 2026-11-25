package org.jetbrains.kotlin.one.util.streamex;

import java.util.Spliterator;
import java.util.stream.BaseStream;
import org.jetbrains.kotlin.one.util.streamex.BaseStreamEx;

/* JADX INFO: loaded from: classes2.dex */
abstract class BaseStreamEx<T, S extends BaseStream<T, S>, SPLTR extends Spliterator<T>, B extends BaseStreamEx<T, S, SPLTR, B>> implements BaseStream<T, S> {
    StreamContext context;
    SPLTR spliterator;
    private S stream;

    abstract S createStream();

    BaseStreamEx(S s, StreamContext streamContext) {
        this.stream = s;
        this.context = streamContext;
    }

    BaseStreamEx(SPLTR spltr, StreamContext streamContext) {
        this.spliterator = spltr;
        this.context = streamContext;
    }

    final S stream() {
        S s = this.stream;
        if (s != null) {
            return s;
        }
        if (this.spliterator == null) {
            throw new IllegalStateException("Stream is already consumed");
        }
        S s2 = (S) createStream();
        this.stream = s2;
        this.spliterator = null;
        return s2;
    }

    @Override // java.util.stream.BaseStream, java.lang.Iterable
    public SPLTR spliterator() {
        S s = this.stream;
        if (s != null) {
            return (SPLTR) s.spliterator();
        }
        SPLTR spltr = this.spliterator;
        if (spltr != null) {
            this.spliterator = null;
            return spltr;
        }
        throw new IllegalStateException("Stream is already consumed");
    }

    @Override // java.util.stream.BaseStream
    public boolean isParallel() {
        return this.context.parallel;
    }

    @Override // java.util.stream.BaseStream
    public S sequential() {
        this.context = this.context.sequential();
        S s = this.stream;
        if (s != null) {
            this.stream = (S) s.sequential();
        }
        return this;
    }

    @Override // java.util.stream.BaseStream
    public S parallel() {
        this.context = this.context.parallel();
        S s = this.stream;
        if (s != null) {
            this.stream = (S) s.parallel();
        }
        return this;
    }

    @Override // java.util.stream.BaseStream
    public S unordered() {
        this.stream = (S) stream().unordered();
        return this;
    }

    @Override // java.util.stream.BaseStream
    public S onClose(Runnable runnable) {
        this.context = this.context.onClose(runnable);
        return this;
    }

    @Override // java.util.stream.BaseStream, java.lang.AutoCloseable
    public void close() {
        this.context.close();
    }
}

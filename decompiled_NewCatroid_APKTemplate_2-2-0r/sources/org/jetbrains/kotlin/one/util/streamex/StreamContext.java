package org.jetbrains.kotlin.one.util.streamex;

import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.ForkJoinPool;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.BaseStream;

/* JADX INFO: loaded from: classes2.dex */
class StreamContext {
    Runnable closeHandler;
    ForkJoinPool fjp;
    boolean parallel;
    static final StreamContext SEQUENTIAL = new StreamContext(false);
    static final StreamContext PARALLEL = new StreamContext(true);

    private StreamContext(boolean z) {
        this.parallel = z;
    }

    <T> T terminate(final Supplier<T> supplier) {
        ForkJoinPool forkJoinPool = this.fjp;
        Objects.requireNonNull(supplier);
        return forkJoinPool.submit((Callable) new Callable() { // from class: org.jetbrains.kotlin.one.util.streamex.StreamContext$$ExternalSyntheticLambda0
            @Override // java.util.concurrent.Callable
            public final Object call() {
                return supplier.get();
            }
        }).join();
    }

    <T, U> T terminate(final U u, final Function<U, T> function) {
        return this.fjp.submit((Callable) new Callable() { // from class: org.jetbrains.kotlin.one.util.streamex.StreamContext$$ExternalSyntheticLambda1
            @Override // java.util.concurrent.Callable
            public final Object call() {
                return function.apply(u);
            }
        }).join();
    }

    StreamContext parallel() {
        if (this == SEQUENTIAL) {
            return PARALLEL;
        }
        this.parallel = true;
        this.fjp = null;
        return this;
    }

    StreamContext sequential() {
        if (this == PARALLEL) {
            return SEQUENTIAL;
        }
        this.parallel = false;
        this.fjp = null;
        return this;
    }

    StreamContext detach() {
        return (this == PARALLEL || this == SEQUENTIAL) ? new StreamContext(this.parallel) : this;
    }

    StreamContext onClose(Runnable runnable) {
        StreamContext streamContextDetach = detach();
        streamContextDetach.closeHandler = compose(streamContextDetach.closeHandler, runnable);
        return streamContextDetach;
    }

    void close() {
        Runnable runnable = this.closeHandler;
        if (runnable != null) {
            this.closeHandler = null;
            runnable.run();
        }
    }

    static Runnable compose(final Runnable runnable, final Runnable runnable2) {
        return runnable == null ? runnable2 : new Runnable() { // from class: org.jetbrains.kotlin.one.util.streamex.StreamContext$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                StreamContext.lambda$compose$1(runnable, runnable2);
            }
        };
    }

    static /* synthetic */ void lambda$compose$1(Runnable runnable, Runnable runnable2) {
        try {
            runnable.run();
            runnable2.run();
        } catch (Throwable th) {
            try {
                runnable2.run();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    static StreamContext of(final BaseStream<?, ?> baseStream) {
        if (baseStream instanceof BaseStreamEx) {
            return ((BaseStreamEx) baseStream).context;
        }
        StreamContext streamContext = new StreamContext(baseStream.isParallel());
        Objects.requireNonNull(baseStream);
        return streamContext.onClose(new Runnable() { // from class: org.jetbrains.kotlin.one.util.streamex.StreamContext$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                baseStream.close();
            }
        });
    }
}

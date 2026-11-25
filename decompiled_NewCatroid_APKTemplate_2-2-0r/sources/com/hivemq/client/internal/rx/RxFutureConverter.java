package com.hivemq.client.internal.rx;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.Maybe;
import io.reactivex.MaybeObserver;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;

/* JADX INFO: loaded from: classes10.dex */
public final class RxFutureConverter {
    private static final int INITIAL = 0;
    private static final int SUBSCRIBED_AND_COMPLETE_OR_CANCELLED = 2;
    private static final int SUBSCRIBED_OR_COMPLETE = 1;

    public static CompletableFuture<Void> toFuture(Completable completable) {
        return new RxCompletableFuture(completable);
    }

    public static <T> CompletableFuture<Optional<T>> toFuture(Maybe<T> maybe) {
        return new RxMaybeFuture(maybe);
    }

    public static <T> CompletableFuture<T> toFuture(Single<T> single) {
        return new RxSingleFuture(single);
    }

    private static abstract class RxFuture<T> extends CompletableFuture<T> {
        volatile boolean cancelled;
        volatile Disposable disposable;

        private RxFuture() {
        }

        public void onSubscribe(Disposable disposable) {
            this.disposable = disposable;
            if (this.cancelled) {
                disposable.dispose();
            }
        }

        public void onError(Throwable th) {
            if (this.cancelled) {
                return;
            }
            completeExceptionally(th);
        }

        @Override // java.util.concurrent.CompletableFuture, java.util.concurrent.Future
        public boolean cancel(boolean z) {
            this.cancelled = true;
            Disposable disposable = this.disposable;
            if (disposable != null) {
                disposable.dispose();
            }
            return super.cancel(z);
        }
    }

    private static class RxCompletableFuture extends RxFuture<Void> implements CompletableObserver {
        RxCompletableFuture(Completable completable) {
            super();
            completable.subscribe(this);
        }

        @Override // io.reactivex.CompletableObserver
        public void onComplete() {
            if (this.cancelled) {
                return;
            }
            complete(null);
        }
    }

    private static class RxMaybeFuture<T> extends RxFuture<Optional<T>> implements MaybeObserver<T> {
        RxMaybeFuture(Maybe<T> maybe) {
            super();
            maybe.subscribe(this);
        }

        @Override // io.reactivex.MaybeObserver
        public void onSuccess(T t) {
            if (this.cancelled) {
                return;
            }
            complete(Optional.of(t));
        }

        @Override // io.reactivex.MaybeObserver
        public void onComplete() {
            if (this.cancelled) {
                return;
            }
            complete(Optional.empty());
        }
    }

    private static class RxSingleFuture<T> extends RxFuture<T> implements SingleObserver<T> {
        RxSingleFuture(Single<T> single) {
            super();
            single.subscribe(this);
        }

        @Override // io.reactivex.SingleObserver
        public void onSuccess(T t) {
            if (this.cancelled) {
                return;
            }
            complete(t);
        }
    }

    public static Completable toCompletable(CompletableFuture<?> completableFuture) {
        return new FutureCompletable(completableFuture);
    }

    public static <T> Maybe<T> toMaybe(CompletableFuture<Optional<T>> completableFuture) {
        return new FutureMaybe(completableFuture);
    }

    public static <T> Single<T> toSingle(CompletableFuture<T> completableFuture) {
        return new FutureSingle(completableFuture);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean checkComplete(AtomicInteger atomicInteger) {
        return !atomicInteger.compareAndSet(0, 1) && atomicInteger.compareAndSet(1, 2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void dispose(AtomicInteger atomicInteger, CompletableFuture<?> completableFuture) {
        atomicInteger.set(2);
        completableFuture.cancel(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isDisposed(AtomicInteger atomicInteger) {
        return atomicInteger.get() == 2;
    }

    private static class FutureCompletable extends Completable implements Disposable, BiConsumer<Object, Throwable> {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private final AtomicInteger done = new AtomicInteger(0);
        private final CompletableFuture<?> future;
        private volatile CompletableObserver observer;
        private volatile Throwable throwable;

        FutureCompletable(CompletableFuture<?> completableFuture) {
            this.future = completableFuture;
            completableFuture.whenComplete((BiConsumer<? super Object, ? super Throwable>) this);
        }

        @Override // io.reactivex.Completable
        protected void subscribeActual(CompletableObserver completableObserver) {
            this.observer = completableObserver;
            completableObserver.onSubscribe(this);
            if (RxFutureConverter.checkComplete(this.done)) {
                complete(completableObserver, this.throwable);
            }
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            RxFutureConverter.dispose(this.done, this.future);
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return RxFutureConverter.isDisposed(this.done);
        }

        @Override // java.util.function.BiConsumer
        public void accept(Object obj, Throwable th) {
            this.throwable = th;
            if (RxFutureConverter.checkComplete(this.done)) {
                complete(this.observer, th);
            }
        }

        private static void complete(CompletableObserver completableObserver, Throwable th) {
            if (th == null) {
                completableObserver.onComplete();
            } else {
                completableObserver.onError(th);
            }
        }
    }

    private static class FutureMaybe<T> extends Maybe<T> implements Disposable, BiConsumer<Optional<T>, Throwable> {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private final AtomicInteger done = new AtomicInteger(0);
        private final CompletableFuture<Optional<T>> future;
        private volatile MaybeObserver<? super T> observer;
        private volatile T t;
        private volatile Throwable throwable;

        FutureMaybe(CompletableFuture<Optional<T>> completableFuture) {
            this.future = completableFuture;
            completableFuture.whenComplete((BiConsumer<? super Optional<T>, ? super Throwable>) this);
        }

        @Override // io.reactivex.Maybe
        protected void subscribeActual(MaybeObserver<? super T> maybeObserver) {
            this.observer = maybeObserver;
            maybeObserver.onSubscribe(this);
            if (RxFutureConverter.checkComplete(this.done)) {
                complete(maybeObserver, this.t, this.throwable);
            }
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            RxFutureConverter.dispose(this.done, this.future);
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return RxFutureConverter.isDisposed(this.done);
        }

        @Override // java.util.function.BiConsumer
        public void accept(Optional<T> optional, Throwable th) {
            T tOrElse = null;
            if (th == null) {
                if (optional == null) {
                    th = new NullPointerException();
                } else {
                    th = null;
                    tOrElse = optional.orElse(null);
                }
            }
            this.t = tOrElse;
            this.throwable = th;
            if (RxFutureConverter.checkComplete(this.done)) {
                complete(this.observer, tOrElse, th);
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        private static <T> void complete(MaybeObserver<? super T> maybeObserver, T t, Throwable th) {
            if (th != null) {
                maybeObserver.onError(th);
            } else if (t != 0) {
                maybeObserver.onSuccess(t);
            } else {
                maybeObserver.onComplete();
            }
        }
    }

    private static class FutureSingle<T> extends Single<T> implements Disposable, BiConsumer<T, Throwable> {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private final AtomicInteger done = new AtomicInteger(0);
        private final CompletableFuture<T> future;
        private volatile SingleObserver<? super T> observer;
        private volatile T t;
        private volatile Throwable throwable;

        FutureSingle(CompletableFuture<T> completableFuture) {
            this.future = completableFuture;
            completableFuture.whenComplete((BiConsumer) this);
        }

        @Override // io.reactivex.Single
        protected void subscribeActual(SingleObserver<? super T> singleObserver) {
            this.observer = singleObserver;
            singleObserver.onSubscribe(this);
            if (RxFutureConverter.checkComplete(this.done)) {
                complete(singleObserver, this.t, this.throwable);
            }
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            RxFutureConverter.dispose(this.done, this.future);
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return RxFutureConverter.isDisposed(this.done);
        }

        @Override // java.util.function.BiConsumer
        public void accept(T t, Throwable th) {
            this.t = t;
            this.throwable = th;
            if (RxFutureConverter.checkComplete(this.done)) {
                complete(this.observer, t, th);
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        private static <T> void complete(SingleObserver<? super T> singleObserver, T t, Throwable th) {
            if (t != 0) {
                singleObserver.onSuccess(t);
                return;
            }
            if (th == null) {
                th = new NullPointerException();
            }
            singleObserver.onError(th);
        }
    }

    private RxFutureConverter() {
    }
}

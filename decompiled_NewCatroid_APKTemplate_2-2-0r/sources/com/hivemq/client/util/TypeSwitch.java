package com.hivemq.client.util;

import java.util.function.Consumer;

/* JADX INFO: loaded from: classes10.dex */
public interface TypeSwitch<T> {
    /* JADX INFO: renamed from: is */
    <I extends T> TypeSwitch<T> is2(Class<I> cls, Consumer<I> consumer);

    static <T> TypeSwitch<T> never() {
        return Never.INSTANCE;
    }

    static <T> TypeSwitch<T> when(T t) {
        return t == null ? never() : new Default(t);
    }

    public static class Default<T> implements TypeSwitch<T> {
        private final T t;

        Default(T t) {
            this.t = t;
        }

        @Override // com.hivemq.client.util.TypeSwitch
        /* JADX INFO: renamed from: is */
        public <I extends T> TypeSwitch<T> is2(Class<I> cls, Consumer<I> consumer) {
            if (!cls.isInstance(this.t)) {
                return this;
            }
            consumer.accept(this.t);
            return TypeSwitch.never();
        }
    }

    public static class Never implements TypeSwitch<Object> {
        static final Never INSTANCE = new Never();

        @Override // com.hivemq.client.util.TypeSwitch
        /* JADX INFO: renamed from: is, reason: merged with bridge method [inline-methods] */
        public <I> TypeSwitch<Object> is2(Class<I> cls, Consumer<I> consumer) {
            return this;
        }

        private Never() {
        }
    }
}

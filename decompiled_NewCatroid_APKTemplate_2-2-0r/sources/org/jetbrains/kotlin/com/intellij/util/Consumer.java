package org.jetbrains.kotlin.com.intellij.util;

/* JADX INFO: loaded from: classes3.dex */
public interface Consumer<T> {

    @Deprecated
    public static final Consumer<Object> EMPTY_CONSUMER = new Consumer<Object>() { // from class: org.jetbrains.kotlin.com.intellij.util.Consumer.1
        @Override // org.jetbrains.kotlin.com.intellij.util.Consumer
        public void consume(Object obj) {
        }
    };

    void consume(T t);
}

package org.jetbrains.kotlin.com.intellij.util;

/* JADX INFO: loaded from: classes3.dex */
public interface Function<Param, Result> {

    public interface Mono<T> extends Function<T, T> {
    }

    Result fun(Param param);
}

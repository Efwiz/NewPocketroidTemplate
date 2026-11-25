package org.jetbrains.kotlin.com.intellij.util;

/* JADX INFO: loaded from: classes3.dex */
@FunctionalInterface
public interface QueryExecutor<Result, Param> {
    boolean execute(Param param, Processor<? super Result> processor);
}

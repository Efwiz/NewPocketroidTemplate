package com.opencsv.bean.function;

import java.lang.reflect.InvocationTargetException;

/* JADX INFO: loaded from: classes10.dex */
@FunctionalInterface
public interface AccessorInvoker<T, R> {
    R invoke(T t) throws IllegalAccessException, InvocationTargetException;
}

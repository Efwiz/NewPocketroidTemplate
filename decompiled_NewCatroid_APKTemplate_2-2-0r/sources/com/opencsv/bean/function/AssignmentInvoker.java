package com.opencsv.bean.function;

import java.lang.reflect.InvocationTargetException;

/* JADX INFO: loaded from: classes10.dex */
@FunctionalInterface
public interface AssignmentInvoker<T, U> {
    void invoke(T t, U u) throws IllegalAccessException, InvocationTargetException;
}

package com.beust.jcommander;

/* JADX INFO: loaded from: classes8.dex */
public interface IValueValidator<T> {
    void validate(String str, T t) throws ParameterException;
}

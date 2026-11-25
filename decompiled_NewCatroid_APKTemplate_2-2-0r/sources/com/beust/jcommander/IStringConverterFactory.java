package com.beust.jcommander;

/* JADX INFO: loaded from: classes8.dex */
public interface IStringConverterFactory {
    <T> Class<? extends IStringConverter<T>> getConverter(Class<T> cls);
}

package org.apache.commons.beanutils;

/* JADX INFO: loaded from: classes12.dex */
public interface Converter {
    <T> T convert(Class<T> cls, Object obj);
}

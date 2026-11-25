package com.opencsv.bean;

import java.lang.Comparable;
import java.util.Locale;

/* JADX INFO: loaded from: classes10.dex */
public interface ComplexFieldMapEntry<I, K extends Comparable<K>, T> {
    boolean contains(K k);

    BeanField<T, K> getBeanField();

    I getInitializer();

    void setErrorLocale(Locale locale);
}

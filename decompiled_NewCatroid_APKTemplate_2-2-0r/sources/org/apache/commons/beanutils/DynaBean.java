package org.apache.commons.beanutils;

/* JADX INFO: loaded from: classes12.dex */
public interface DynaBean {
    boolean contains(String str, String str2);

    Object get(String str);

    Object get(String str, int i);

    Object get(String str, String str2);

    DynaClass getDynaClass();

    void remove(String str, String str2);

    void set(String str, int i, Object obj);

    void set(String str, Object obj);

    void set(String str, String str2, Object obj);
}

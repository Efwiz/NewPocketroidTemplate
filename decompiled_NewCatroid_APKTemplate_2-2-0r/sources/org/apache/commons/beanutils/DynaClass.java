package org.apache.commons.beanutils;

/* JADX INFO: loaded from: classes12.dex */
public interface DynaClass {
    DynaProperty[] getDynaProperties();

    DynaProperty getDynaProperty(String str);

    String getName();

    DynaBean newInstance() throws IllegalAccessException, InstantiationException;
}

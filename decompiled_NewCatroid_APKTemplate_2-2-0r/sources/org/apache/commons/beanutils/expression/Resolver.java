package org.apache.commons.beanutils.expression;

/* JADX INFO: loaded from: classes12.dex */
public interface Resolver {
    int getIndex(String str);

    String getKey(String str);

    String getProperty(String str);

    boolean hasNested(String str);

    boolean isIndexed(String str);

    boolean isMapped(String str);

    String next(String str);

    String remove(String str);
}

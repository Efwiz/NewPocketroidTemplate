package org.jetbrains.kotlin.com.intellij.pom;

/* JADX INFO: loaded from: classes3.dex */
public interface PomRenameableTarget<T> extends PomNamedTarget {
    boolean isWritable();

    T setName(String str);
}

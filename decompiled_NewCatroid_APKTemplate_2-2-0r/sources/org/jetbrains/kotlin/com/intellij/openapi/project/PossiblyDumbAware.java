package org.jetbrains.kotlin.com.intellij.openapi.project;

/* JADX INFO: loaded from: classes3.dex */
public interface PossiblyDumbAware {
    default boolean isDumbAware() {
        return this instanceof DumbAware;
    }
}

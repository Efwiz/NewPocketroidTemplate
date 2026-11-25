package org.jetbrains.kotlin.com.intellij.pom.event;

import org.jetbrains.kotlin.com.intellij.pom.PomModelAspect;

/* JADX INFO: loaded from: classes3.dex */
public interface PomChangeSet {
    default void beforeNestedTransaction() {
    }

    PomModelAspect getAspect();

    void merge(PomChangeSet pomChangeSet);
}

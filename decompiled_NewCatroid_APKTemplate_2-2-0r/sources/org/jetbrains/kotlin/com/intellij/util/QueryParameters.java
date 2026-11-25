package org.jetbrains.kotlin.com.intellij.util;

import org.jetbrains.kotlin.com.intellij.openapi.project.Project;

/* JADX INFO: loaded from: classes3.dex */
public interface QueryParameters {
    default Project getProject() {
        return null;
    }

    default boolean isQueryValid() {
        return true;
    }
}

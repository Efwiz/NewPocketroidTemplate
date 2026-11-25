package org.jetbrains.kotlin.com.intellij.openapi.application.ex;

import org.jetbrains.kotlin.com.intellij.openapi.application.ApplicationManager;

/* JADX INFO: loaded from: classes3.dex */
public final class ApplicationManagerEx extends ApplicationManager {
    private static volatile boolean inStressTest;

    public static ApplicationEx getApplicationEx() {
        return (ApplicationEx) ourApplication;
    }

    public static boolean isInStressTest() {
        return inStressTest;
    }
}

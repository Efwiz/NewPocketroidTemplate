package org.jetbrains.kotlin.com.intellij.openapi.application.ex;

import org.jetbrains.kotlin.com.intellij.openapi.application.ApplicationInfo;
import org.jetbrains.kotlin.com.intellij.openapi.util.BuildNumber;

/* JADX INFO: loaded from: classes3.dex */
public abstract class ApplicationInfoEx extends ApplicationInfo {

    public interface UpdateUrls {
    }

    public abstract BuildNumber getApiVersionAsNumber();

    public abstract boolean isEssentialPlugin(String str);
}

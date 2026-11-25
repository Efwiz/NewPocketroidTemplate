package org.jetbrains.kotlin.com.intellij.openapi.wm.ex;

import org.jetbrains.kotlin.com.intellij.openapi.progress.ProgressIndicator;
import org.jetbrains.kotlin.com.intellij.openapi.progress.TaskInfo;

/* JADX INFO: loaded from: classes3.dex */
public interface ProgressIndicatorEx extends ProgressIndicator {
    void addStateDelegate(ProgressIndicatorEx progressIndicatorEx);

    void finish(TaskInfo taskInfo);

    void initStateFrom(ProgressIndicator progressIndicator);

    void processFinish();
}

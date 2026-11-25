package org.jetbrains.kotlin.com.intellij.pom.event;

import java.util.EventListener;
import org.jetbrains.kotlin.com.intellij.pom.PomModelAspect;

/* JADX INFO: loaded from: classes3.dex */
public interface PomModelListener extends EventListener {
    boolean isAspectChangeInteresting(PomModelAspect pomModelAspect);

    void modelChanged(PomModelEvent pomModelEvent);
}

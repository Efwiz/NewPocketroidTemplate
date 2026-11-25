package org.jetbrains.kotlin.com.intellij.openapi.util;

import java.util.concurrent.atomic.AtomicLongFieldUpdater;
import org.jetbrains.kotlin.com.intellij.util.xmlb.annotations.Transient;

/* JADX INFO: loaded from: classes3.dex */
@Transient
public class SimpleModificationTracker implements ModificationTracker {
    private static final AtomicLongFieldUpdater<SimpleModificationTracker> UPDATER = AtomicLongFieldUpdater.newUpdater(SimpleModificationTracker.class, "myCounter");
    private volatile long myCounter;

    @Override // org.jetbrains.kotlin.com.intellij.openapi.util.ModificationTracker
    public long getModificationCount() {
        return this.myCounter;
    }

    public void incModificationCount() {
        UPDATER.incrementAndGet(this);
    }
}

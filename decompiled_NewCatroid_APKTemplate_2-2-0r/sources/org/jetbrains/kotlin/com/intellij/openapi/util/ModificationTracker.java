package org.jetbrains.kotlin.com.intellij.openapi.util;

/* JADX INFO: loaded from: classes3.dex */
@FunctionalInterface
public interface ModificationTracker {
    public static final ModificationTracker EVER_CHANGED = new ModificationTracker() { // from class: org.jetbrains.kotlin.com.intellij.openapi.util.ModificationTracker.1
        private long myCounter;

        @Override // org.jetbrains.kotlin.com.intellij.openapi.util.ModificationTracker
        public long getModificationCount() {
            long j = this.myCounter;
            this.myCounter = 1 + j;
            return j;
        }
    };
    public static final ModificationTracker NEVER_CHANGED = new ModificationTracker() { // from class: org.jetbrains.kotlin.com.intellij.openapi.util.ModificationTracker$$ExternalSyntheticLambda0
        @Override // org.jetbrains.kotlin.com.intellij.openapi.util.ModificationTracker
        public final long getModificationCount() {
            return ModificationTracker.lambda$static$0();
        }
    };

    static /* synthetic */ long lambda$static$0() {
        return 0L;
    }

    long getModificationCount();
}

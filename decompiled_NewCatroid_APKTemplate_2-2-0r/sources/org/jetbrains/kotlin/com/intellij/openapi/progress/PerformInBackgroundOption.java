package org.jetbrains.kotlin.com.intellij.openapi.progress;

/* JADX INFO: loaded from: classes3.dex */
public interface PerformInBackgroundOption {
    public static final PerformInBackgroundOption DEAF = new PerformInBackgroundOption() { // from class: org.jetbrains.kotlin.com.intellij.openapi.progress.PerformInBackgroundOption$$ExternalSyntheticLambda0
        @Override // org.jetbrains.kotlin.com.intellij.openapi.progress.PerformInBackgroundOption
        public final boolean shouldStartInBackground() {
            return PerformInBackgroundOption.lambda$static$0();
        }
    };
    public static final PerformInBackgroundOption ALWAYS_BACKGROUND = new PerformInBackgroundOption() { // from class: org.jetbrains.kotlin.com.intellij.openapi.progress.PerformInBackgroundOption$$ExternalSyntheticLambda1
        @Override // org.jetbrains.kotlin.com.intellij.openapi.progress.PerformInBackgroundOption
        public final boolean shouldStartInBackground() {
            return PerformInBackgroundOption.lambda$static$1();
        }
    };

    static /* synthetic */ boolean lambda$static$0() {
        return false;
    }

    static /* synthetic */ boolean lambda$static$1() {
        return true;
    }

    boolean shouldStartInBackground();
}

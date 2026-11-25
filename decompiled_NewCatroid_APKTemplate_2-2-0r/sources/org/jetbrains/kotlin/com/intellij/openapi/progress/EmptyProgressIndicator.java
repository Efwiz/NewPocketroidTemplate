package org.jetbrains.kotlin.com.intellij.openapi.progress;

/* JADX INFO: loaded from: classes3.dex */
public class EmptyProgressIndicator extends EmptyProgressIndicatorBase implements StandardProgressIndicator {
    private volatile boolean myIsCanceled;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 1 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[i != 1 ? 3 : 2];
        if (i != 1) {
            objArr[0] = "modalityState";
        } else {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/openapi/progress/EmptyProgressIndicator";
        }
        if (i != 1) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/openapi/progress/EmptyProgressIndicator";
        } else {
            objArr[1] = "notNullize";
        }
        if (i != 1) {
            objArr[2] = "<init>";
        }
        String str2 = String.format(str, objArr);
        if (i == 1) {
            throw new IllegalStateException(str2);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.progress.EmptyProgressIndicatorBase, org.jetbrains.kotlin.com.intellij.openapi.progress.ProgressIndicator
    public void start() {
        super.start();
        this.myIsCanceled = false;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.progress.ProgressIndicator
    public final void cancel() {
        this.myIsCanceled = true;
        ProgressManager.canceled(this);
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.progress.ProgressIndicator
    public final boolean isCanceled() {
        return this.myIsCanceled;
    }

    public static ProgressIndicator notNullize(ProgressIndicator progressIndicator) {
        if (progressIndicator == null) {
            return new EmptyProgressIndicator();
        }
        if (progressIndicator == null) {
            $$$reportNull$$$0(1);
        }
        return progressIndicator;
    }
}

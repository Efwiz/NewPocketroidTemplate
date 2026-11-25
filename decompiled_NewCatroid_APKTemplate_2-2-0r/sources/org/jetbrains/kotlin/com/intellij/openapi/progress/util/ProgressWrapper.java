package org.jetbrains.kotlin.com.intellij.openapi.progress.util;

import org.jetbrains.kotlin.com.intellij.openapi.application.ModalityState;
import org.jetbrains.kotlin.com.intellij.openapi.progress.ProgressIndicator;
import org.jetbrains.kotlin.com.intellij.openapi.progress.StandardProgressIndicator;
import org.jetbrains.kotlin.com.intellij.openapi.progress.WrappedProgressIndicator;

/* JADX INFO: loaded from: classes3.dex */
public class ProgressWrapper extends AbstractProgressIndicatorBase implements StandardProgressIndicator, WrappedProgressIndicator {
    private final boolean myCheckCanceledForMe;
    private final ProgressIndicator myOriginal;
    private final int nested;

    /* JADX WARN: Code duplicated, block: B:20:0x002f  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 2 || i == 3 || i == 5) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 2 || i == 3 || i == 5) ? 2 : 3];
        if (i == 2 || i == 3) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/openapi/progress/util/ProgressWrapper";
        } else if (i == 4) {
            objArr[0] = "indicator";
        } else if (i != 5) {
            objArr[0] = "original";
        } else {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/openapi/progress/util/ProgressWrapper";
        }
        if (i == 2) {
            objArr[1] = "getModalityState";
        } else if (i == 3) {
            objArr[1] = "getOriginalProgressIndicator";
        } else if (i != 5) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/openapi/progress/util/ProgressWrapper";
        } else {
            objArr[1] = "unwrapAll";
        }
        if (i != 2 && i != 3) {
            if (i == 4) {
                objArr[2] = "unwrapAll";
            } else if (i != 5) {
                objArr[2] = "<init>";
            }
        }
        String str2 = String.format(str, objArr);
        if (i != 2 && i != 3 && i != 5) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.progress.util.AbstractProgressIndicatorBase, org.jetbrains.kotlin.com.intellij.openapi.progress.ProgressIndicator
    public final void cancel() {
        super.cancel();
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.progress.util.AbstractProgressIndicatorBase, org.jetbrains.kotlin.com.intellij.openapi.progress.ProgressIndicator
    public final boolean isCanceled() {
        ProgressWrapper progressWrapper = this;
        while (true) {
            if (progressWrapper.myCheckCanceledForMe && progressWrapper.isCanceledRaw()) {
                return true;
            }
            ProgressIndicator originalProgressIndicator = progressWrapper.getOriginalProgressIndicator();
            if (originalProgressIndicator instanceof ProgressWrapper) {
                progressWrapper = (ProgressWrapper) originalProgressIndicator;
            } else {
                return originalProgressIndicator.isCanceled();
            }
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.progress.util.AbstractProgressIndicatorBase
    protected Throwable getCancellationTrace() {
        ProgressIndicator progressIndicator = this.myOriginal;
        if (progressIndicator instanceof AbstractProgressIndicatorBase) {
            return ((AbstractProgressIndicatorBase) progressIndicator).getCancellationTrace();
        }
        return super.getCancellationTrace();
    }

    private boolean isCanceledRaw() {
        return super.isCanceled();
    }

    private void checkCanceledRaw() {
        super.checkCanceled();
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.progress.util.AbstractProgressIndicatorBase, org.jetbrains.kotlin.com.intellij.openapi.progress.ProgressIndicator
    public final void checkCanceled() {
        ProgressWrapper progressWrapper = this;
        while (true) {
            if (progressWrapper.isCanceledRaw()) {
                progressWrapper.checkCanceledRaw();
            }
            ProgressIndicator originalProgressIndicator = progressWrapper.getOriginalProgressIndicator();
            if (originalProgressIndicator instanceof ProgressWrapper) {
                progressWrapper = (ProgressWrapper) originalProgressIndicator;
            } else {
                originalProgressIndicator.checkCanceled();
                return;
            }
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.progress.util.AbstractProgressIndicatorBase, org.jetbrains.kotlin.com.intellij.openapi.progress.ProgressIndicator
    public void setText(String str) {
        super.setText(str);
        this.myOriginal.setText(str);
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.progress.util.AbstractProgressIndicatorBase, org.jetbrains.kotlin.com.intellij.openapi.progress.ProgressIndicator
    public void setText2(String str) {
        super.setText2(str);
        this.myOriginal.setText2(str);
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.progress.util.AbstractProgressIndicatorBase, org.jetbrains.kotlin.com.intellij.openapi.progress.ProgressIndicator
    public void setFraction(double d) {
        super.setFraction(d);
        this.myOriginal.setFraction(d);
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.progress.util.AbstractProgressIndicatorBase, org.jetbrains.kotlin.com.intellij.openapi.progress.ProgressIndicator
    public void setIndeterminate(boolean z) {
        this.myOriginal.setIndeterminate(z);
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.progress.util.AbstractProgressIndicatorBase, org.jetbrains.kotlin.com.intellij.openapi.progress.ProgressIndicator
    public boolean isIndeterminate() {
        return this.myOriginal.isIndeterminate();
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.progress.util.AbstractProgressIndicatorBase, org.jetbrains.kotlin.com.intellij.openapi.progress.ProgressIndicator
    public ModalityState getModalityState() {
        ModalityState modalityState = this.myOriginal.getModalityState();
        if (modalityState == null) {
            $$$reportNull$$$0(2);
        }
        return modalityState;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.progress.WrappedProgressIndicator
    public ProgressIndicator getOriginalProgressIndicator() {
        ProgressIndicator progressIndicator = this.myOriginal;
        if (progressIndicator == null) {
            $$$reportNull$$$0(3);
        }
        return progressIndicator;
    }

    public static ProgressIndicator unwrap(ProgressIndicator progressIndicator) {
        return progressIndicator instanceof ProgressWrapper ? ((ProgressWrapper) progressIndicator).getOriginalProgressIndicator() : progressIndicator;
    }
}

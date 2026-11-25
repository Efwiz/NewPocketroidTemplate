package org.catrobat.catroid.transfers.project;

import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import java.lang.ref.WeakReference;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/transfers/project/ResultReceiverWrapper.class
  classes14.dex
 */
/* JADX INFO: compiled from: ResultReceiverWrapper.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u001a\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\b\u0010\u000e\u001a\u0004\u0018\u00010\u000fH\u0014R\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00030\u0007¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\t¨\u0006\u0010"}, d2 = {"Lorg/catrobat/catroid/transfers/project/ResultReceiverWrapper;", "Landroid/os/ResultReceiver;", "resultReceiverWrapperInterface", "Lorg/catrobat/catroid/transfers/project/ResultReceiverWrapperInterface;", "handler", "Landroid/os/Handler;", "(Lorg/catrobat/catroid/transfers/project/ResultReceiverWrapperInterface;Landroid/os/Handler;)V", "Ljava/lang/ref/WeakReference;", "getResultReceiverWrapperInterface", "()Ljava/lang/ref/WeakReference;", "onReceiveResult", "", "resultCode", "", "resultData", "Landroid/os/Bundle;", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ResultReceiverWrapper extends ResultReceiver {
    private final WeakReference<ResultReceiverWrapperInterface> resultReceiverWrapperInterface;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ResultReceiverWrapper(ResultReceiverWrapperInterface resultReceiverWrapperInterface, Handler handler) {
        super(handler);
        Intrinsics.checkNotNullParameter(resultReceiverWrapperInterface, "resultReceiverWrapperInterface");
        Intrinsics.checkNotNullParameter(handler, "handler");
        this.resultReceiverWrapperInterface = new WeakReference<>(resultReceiverWrapperInterface);
    }

    public final WeakReference<ResultReceiverWrapperInterface> getResultReceiverWrapperInterface() {
        return this.resultReceiverWrapperInterface;
    }

    @Override // android.os.ResultReceiver
    protected void onReceiveResult(int resultCode, Bundle resultData) {
        ResultReceiverWrapperInterface resultReceiverWrapperInterface = this.resultReceiverWrapperInterface.get();
        if (resultReceiverWrapperInterface != null) {
            resultReceiverWrapperInterface.onReceiveResult(resultCode, resultData);
        }
    }
}

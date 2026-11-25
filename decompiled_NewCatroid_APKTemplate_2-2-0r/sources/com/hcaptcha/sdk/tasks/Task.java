package com.hcaptcha.sdk.tasks;

import android.os.Handler;
import android.os.Looper;
import com.hcaptcha.sdk.HCaptchaError;
import com.hcaptcha.sdk.HCaptchaException;
import com.hcaptcha.sdk.HCaptchaLog;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;

/* JADX INFO: compiled from: r8-map-id-071b77f92f575d77028d4852bce4d6e5f9d65cb60a14a6342bd357f80a49e8cf */
/* JADX INFO: loaded from: classes10.dex */
public abstract class Task<R> {
    private boolean complete;
    private HCaptchaException hCaptchaException;
    private R result;
    private boolean successful;
    protected final Handler handler = new Handler(Looper.getMainLooper());
    private final CopyOnWriteArrayList<OnSuccessListener<R>> onSuccessListeners = new CopyOnWriteArrayList<>();
    private final CopyOnWriteArrayList<OnFailureListener> onFailureListeners = new CopyOnWriteArrayList<>();
    private final CopyOnWriteArrayList<OnOpenListener> onOpenListeners = new CopyOnWriteArrayList<>();
    private final CopyOnWriteArrayList<OnLoadedListener> onLoadedListeners = new CopyOnWriteArrayList<>();

    public Task() {
        reset();
    }

    private void reset() {
        this.complete = false;
        this.successful = false;
        this.result = null;
        this.hCaptchaException = null;
    }

    private void tryCb() {
        boolean z = false;
        if (getResult() != null) {
            Iterator<OnSuccessListener<R>> it = this.onSuccessListeners.iterator();
            while (it.hasNext()) {
                it.next().onSuccess(getResult());
                z = true;
            }
        }
        if (getException() != null) {
            Iterator<OnFailureListener> it2 = this.onFailureListeners.iterator();
            while (it2.hasNext()) {
                it2.next().onFailure(getException());
                z = true;
            }
        }
        if (z) {
            reset();
        }
    }

    public Task<R> addOnFailureListener(OnFailureListener onFailureListener) {
        this.onFailureListeners.add(onFailureListener);
        tryCb();
        return this;
    }

    public Task<R> addOnLoadedListener(OnLoadedListener onLoadedListener) {
        this.onLoadedListeners.add(onLoadedListener);
        tryCb();
        return this;
    }

    public Task<R> addOnOpenListener(OnOpenListener onOpenListener) {
        this.onOpenListeners.add(onOpenListener);
        tryCb();
        return this;
    }

    public Task<R> addOnSuccessListener(OnSuccessListener<R> onSuccessListener) {
        this.onSuccessListeners.add(onSuccessListener);
        tryCb();
        return this;
    }

    public void captchaLoaded() {
        Iterator<OnLoadedListener> it = this.onLoadedListeners.iterator();
        while (it.hasNext()) {
            it.next().onLoaded();
        }
    }

    public void captchaOpened() {
        Iterator<OnOpenListener> it = this.onOpenListeners.iterator();
        while (it.hasNext()) {
            it.next().onOpen();
        }
    }

    public HCaptchaException getException() {
        return this.hCaptchaException;
    }

    public R getResult() {
        return this.result;
    }

    public boolean isComplete() {
        return this.complete;
    }

    public boolean isSuccessful() {
        return this.successful;
    }

    /* JADX INFO: renamed from: lambda$scheduleCaptchaExpired$0$com-hcaptcha-sdk-tasks-Task, reason: not valid java name */
    /* synthetic */ void m2671lambda$scheduleCaptchaExpired$0$comhcaptchasdktasksTask() {
        Iterator<OnFailureListener> it = this.onFailureListeners.iterator();
        while (it.hasNext()) {
            it.next().onFailure(new HCaptchaException(HCaptchaError.TOKEN_TIMEOUT));
        }
    }

    public Task<R> removeAllListeners() {
        this.onSuccessListeners.clear();
        this.onFailureListeners.clear();
        this.onOpenListeners.clear();
        this.onLoadedListeners.clear();
        return this;
    }

    public Task<R> removeOnFailureListener(OnFailureListener onFailureListener) {
        if (!this.onFailureListeners.remove(onFailureListener)) {
            HCaptchaLog.d("removeOnFailureListener: %1 not found and cannot be removed", onFailureListener);
        }
        return this;
    }

    public Task<R> removeOnLoadedListener(OnLoadedListener onLoadedListener) {
        if (!this.onLoadedListeners.remove(onLoadedListener)) {
            HCaptchaLog.d("removeOnLoadedListener: %1 not found and cannot be removed", onLoadedListener);
        }
        return this;
    }

    public Task<R> removeOnOpenListener(OnOpenListener onOpenListener) {
        if (!this.onOpenListeners.remove(onOpenListener)) {
            HCaptchaLog.d("removeOnOpenListener: %1 not found and cannot be removed", onOpenListener);
        }
        return this;
    }

    public Task<R> removeOnSuccessListener(OnSuccessListener<R> onSuccessListener) {
        if (!this.onSuccessListeners.remove(onSuccessListener)) {
            HCaptchaLog.d("removeOnSuccessListener: %1 not found and cannot be removed", onSuccessListener);
        }
        return this;
    }

    public void scheduleCaptchaExpired(long j) {
        this.handler.postDelayed(new Runnable() { // from class: com.hcaptcha.sdk.tasks.Task$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m2671lambda$scheduleCaptchaExpired$0$comhcaptchasdktasksTask();
            }
        }, TimeUnit.SECONDS.toMillis(j));
    }

    public void setException(HCaptchaException hCaptchaException) {
        this.hCaptchaException = hCaptchaException;
        this.successful = false;
        this.complete = true;
        tryCb();
    }

    public void setResult(R r) {
        this.result = r;
        this.successful = true;
        this.complete = true;
        tryCb();
    }
}

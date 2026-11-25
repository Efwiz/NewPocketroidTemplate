package io.netty.handler.codec;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.huawei.hms.mlsdk.common.internal.client.event.MonitorResult;
import io.netty.util.Signal;
import io.netty.util.internal.ObjectUtil;

/* JADX INFO: loaded from: classes11.dex */
public class DecoderResult {
    protected static final Signal SIGNAL_SUCCESS;
    protected static final Signal SIGNAL_UNFINISHED;
    public static final DecoderResult SUCCESS;
    public static final DecoderResult UNFINISHED;
    private final Throwable cause;

    static {
        Signal signalValueOf = Signal.valueOf(DecoderResult.class, "UNFINISHED");
        SIGNAL_UNFINISHED = signalValueOf;
        Signal signalValueOf2 = Signal.valueOf(DecoderResult.class, MonitorResult.SUCCESS);
        SIGNAL_SUCCESS = signalValueOf2;
        UNFINISHED = new DecoderResult(signalValueOf);
        SUCCESS = new DecoderResult(signalValueOf2);
    }

    public static DecoderResult failure(Throwable th) {
        return new DecoderResult((Throwable) ObjectUtil.checkNotNull(th, "cause"));
    }

    protected DecoderResult(Throwable th) {
        this.cause = (Throwable) ObjectUtil.checkNotNull(th, "cause");
    }

    public boolean isFinished() {
        return this.cause != SIGNAL_UNFINISHED;
    }

    public boolean isSuccess() {
        return this.cause == SIGNAL_SUCCESS;
    }

    public boolean isFailure() {
        Throwable th = this.cause;
        return (th == SIGNAL_SUCCESS || th == SIGNAL_UNFINISHED) ? false : true;
    }

    public Throwable cause() {
        if (isFailure()) {
            return this.cause;
        }
        return null;
    }

    public String toString() {
        if (isFinished()) {
            if (isSuccess()) {
                return FirebaseAnalytics.Param.SUCCESS;
            }
            String string = cause().toString();
            return new StringBuilder(string.length() + 17).append("failure(").append(string).append(')').toString();
        }
        return "unfinished";
    }
}

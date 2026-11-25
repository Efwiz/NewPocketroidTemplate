package io.netty.channel;

import io.netty.util.internal.PlatformDependent;

/* JADX INFO: loaded from: classes11.dex */
public class ChannelException extends RuntimeException {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final long serialVersionUID = 2908618315971075004L;

    public ChannelException() {
    }

    public ChannelException(String str, Throwable th) {
        super(str, th);
    }

    public ChannelException(String str) {
        super(str);
    }

    public ChannelException(Throwable th) {
        super(th);
    }

    protected ChannelException(String str, Throwable th, boolean z) {
        super(str, th, false, true);
    }

    static ChannelException newStatic(String str, Throwable th) {
        if (PlatformDependent.javaVersion() >= 7) {
            return new ChannelException(str, th, true);
        }
        return new ChannelException(str, th);
    }
}

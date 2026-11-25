package io.netty.handler.ssl;

/* JADX INFO: loaded from: classes11.dex */
public final class SniCompletionEvent extends SslCompletionEvent {
    private final String hostname;

    SniCompletionEvent(String str) {
        this.hostname = str;
    }

    SniCompletionEvent(String str, Throwable th) {
        super(th);
        this.hostname = str;
    }

    SniCompletionEvent(Throwable th) {
        this(null, th);
    }

    public String hostname() {
        return this.hostname;
    }

    @Override // io.netty.handler.ssl.SslCompletionEvent
    public String toString() {
        Throwable thCause = cause();
        if (thCause == null) {
            return getClass().getSimpleName() + "(SUCCESS='" + this.hostname + "'\")";
        }
        return getClass().getSimpleName() + '(' + thCause + ')';
    }
}

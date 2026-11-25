package com.koushikdutta.async.future;

/* JADX INFO: loaded from: classes10.dex */
public class SimpleCancellable implements DependentCancellable {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public static final Cancellable COMPLETED = new SimpleCancellable() { // from class: com.koushikdutta.async.future.SimpleCancellable.1
        {
            setComplete();
        }

        @Override // com.koushikdutta.async.future.SimpleCancellable, com.koushikdutta.async.future.DependentCancellable
        public /* bridge */ /* synthetic */ DependentCancellable setParent(Cancellable cancellable) {
            return super.setParent(cancellable);
        }
    };
    boolean cancelled;
    boolean complete;
    private Cancellable parent;

    protected void cancelCleanup() {
    }

    protected void cleanup() {
    }

    protected void completeCleanup() {
    }

    @Override // com.koushikdutta.async.future.Cancellable
    public boolean isDone() {
        return this.complete;
    }

    public boolean setComplete() {
        synchronized (this) {
            if (this.cancelled) {
                return false;
            }
            if (this.complete) {
                return true;
            }
            this.complete = true;
            this.parent = null;
            completeCleanup();
            cleanup();
            return true;
        }
    }

    @Override // com.koushikdutta.async.future.Cancellable
    public boolean cancel() {
        synchronized (this) {
            if (this.complete) {
                return false;
            }
            if (this.cancelled) {
                return true;
            }
            this.cancelled = true;
            Cancellable cancellable = this.parent;
            this.parent = null;
            if (cancellable != null) {
                cancellable.cancel();
            }
            cancelCleanup();
            cleanup();
            return true;
        }
    }

    @Override // com.koushikdutta.async.future.DependentCancellable
    public SimpleCancellable setParent(Cancellable cancellable) {
        synchronized (this) {
            if (!isDone()) {
                this.parent = cancellable;
            }
        }
        return this;
    }

    @Override // com.koushikdutta.async.future.Cancellable
    public boolean isCancelled() {
        boolean z;
        Cancellable cancellable;
        synchronized (this) {
            z = this.cancelled || ((cancellable = this.parent) != null && cancellable.isCancelled());
        }
        return z;
    }

    public Cancellable reset() {
        cancel();
        this.complete = false;
        this.cancelled = false;
        return this;
    }
}

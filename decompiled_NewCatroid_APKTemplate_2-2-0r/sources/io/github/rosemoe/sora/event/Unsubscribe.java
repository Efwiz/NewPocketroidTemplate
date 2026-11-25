package io.github.rosemoe.sora.event;

/* JADX INFO: loaded from: classes11.dex */
public class Unsubscribe {
    private boolean unsubscribeFlag = false;

    public void unsubscribe() {
        this.unsubscribeFlag = true;
    }

    public boolean isUnsubscribed() {
        return this.unsubscribeFlag;
    }

    public void reset() {
        this.unsubscribeFlag = false;
    }
}

package com.koushikdutta.async.callback;

/* JADX INFO: loaded from: classes10.dex */
public interface CompletedCallback {

    public static class NullCompletedCallback implements CompletedCallback {
        @Override // com.koushikdutta.async.callback.CompletedCallback
        public void onCompleted(Exception exc) {
        }
    }

    void onCompleted(Exception exc);
}

package org.jetbrains.kotlin.com.intellij.openapi.application;

/* JADX INFO: loaded from: classes3.dex */
public abstract class AccessToken implements AutoCloseable {
    public static final AccessToken EMPTY_ACCESS_TOKEN = new AccessToken() { // from class: org.jetbrains.kotlin.com.intellij.openapi.application.AccessToken.1
        @Override // org.jetbrains.kotlin.com.intellij.openapi.application.AccessToken
        public void finish() {
        }
    };

    public abstract void finish();

    @Override // java.lang.AutoCloseable
    public final void close() {
        finish();
    }
}

package org.jetbrains.kotlin.com.intellij.openapi;

/* JADX INFO: loaded from: classes3.dex */
public interface Disposable {

    public interface Parent extends Disposable {
        void beforeTreeDispose();
    }

    void dispose();
}

package org.jetbrains.kotlin.com.intellij.pom;

import org.jetbrains.kotlin.com.intellij.openapi.util.UserDataHolder;
import org.jetbrains.kotlin.com.intellij.util.IncorrectOperationException;

/* JADX INFO: loaded from: classes3.dex */
public interface PomModel extends UserDataHolder {
    <T extends PomModelAspect> T getModelAspect(Class<T> cls);

    void runTransaction(PomTransaction pomTransaction) throws IncorrectOperationException;
}

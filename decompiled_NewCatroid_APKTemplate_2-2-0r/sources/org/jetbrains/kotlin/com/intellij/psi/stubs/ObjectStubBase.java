package org.jetbrains.kotlin.com.intellij.psi.stubs;

import org.jetbrains.kotlin.com.intellij.openapi.util.Key;
import org.jetbrains.kotlin.com.intellij.openapi.util.UserDataHolderBase;
import org.jetbrains.kotlin.com.intellij.psi.stubs.Stub;

/* JADX INFO: loaded from: classes3.dex */
public abstract class ObjectStubBase<T extends Stub> extends UserDataHolderBase implements Stub {
    private static final Key<Boolean> DANGLING_STUB = Key.create("DIRECT_PARENT_IS_STUBBED");

    /* JADX INFO: renamed from: id, reason: collision with root package name */
    int f717id;
    protected final T myParent;

    public ObjectStubBase(T t) {
        this.myParent = t;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.Stub
    public T getParentStub() {
        return this.myParent;
    }

    public boolean isDangling() {
        return Boolean.TRUE.equals(getUserData(DANGLING_STUB));
    }

    public void markDangling() {
        putUserData(DANGLING_STUB, true);
    }

    public int getStubId() {
        return this.f717id;
    }
}

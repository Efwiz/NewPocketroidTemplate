package org.jetbrains.kotlin.com.intellij.psi.stubs;

import java.util.List;

/* JADX INFO: loaded from: classes3.dex */
public interface Stub {
    List<? extends Stub> getChildrenStubs();

    Stub getParentStub();

    ObjectStubSerializer<?, ? extends Stub> getStubType();
}

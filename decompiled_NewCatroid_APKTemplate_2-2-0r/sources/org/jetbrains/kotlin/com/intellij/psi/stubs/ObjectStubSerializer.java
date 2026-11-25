package org.jetbrains.kotlin.com.intellij.psi.stubs;

import java.io.IOException;
import org.jetbrains.kotlin.com.intellij.psi.stubs.Stub;

/* JADX INFO: loaded from: classes3.dex */
public interface ObjectStubSerializer<T extends Stub, P extends Stub> {
    T deserialize(StubInputStream stubInputStream, P p) throws IOException;

    String getExternalId();

    void indexStub(T t, IndexSink indexSink);

    void serialize(T t, StubOutputStream stubOutputStream) throws IOException;
}

package org.apache.commons.io.function;

import java.util.Objects;
import java.util.stream.BaseStream;
import net.bytebuddy.implementation.MethodDelegation;
import org.apache.commons.io.function.IOBaseStream;

/* JADX INFO: loaded from: classes13.dex */
abstract class IOBaseStreamAdapter<T, S extends IOBaseStream<T, S, B>, B extends BaseStream<T, B>> implements IOBaseStream<T, S, B> {
    private final B delegate;

    IOBaseStreamAdapter(B b) {
        this.delegate = (B) Objects.requireNonNull(b, MethodDelegation.ImplementationDelegate.FIELD_NAME_PREFIX);
    }

    @Override // org.apache.commons.io.function.IOBaseStream
    public B unwrap() {
        return this.delegate;
    }
}

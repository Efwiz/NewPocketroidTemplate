package org.jetbrains.kotlin.com.google.common.hash;

import java.io.Serializable;
import org.jetbrains.kotlin.com.google.errorprone.annotations.DoNotMock;

/* JADX INFO: loaded from: classes3.dex */
@DoNotMock("Implement with a lambda")
public interface Funnel<T> extends Serializable {
    void funnel(T t, PrimitiveSink primitiveSink);
}

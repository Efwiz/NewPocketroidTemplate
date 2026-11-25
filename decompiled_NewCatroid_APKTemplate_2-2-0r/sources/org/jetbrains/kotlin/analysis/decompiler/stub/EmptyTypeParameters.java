package org.jetbrains.kotlin.analysis.decompiler.stub;

import kotlin.Metadata;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: ClsStubBuilderContext.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0011\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0096\u0002¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/analysis/decompiler/stub/EmptyTypeParameters;", "Lorg/jetbrains/kotlin/analysis/decompiler/stub/TypeParameters;", "()V", "get", "Lorg/jetbrains/kotlin/name/Name;", "id", "", "decompiler-to-stubs"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class EmptyTypeParameters implements TypeParameters {
    public static final EmptyTypeParameters INSTANCE = new EmptyTypeParameters();

    private EmptyTypeParameters() {
    }

    @Override // org.jetbrains.kotlin.analysis.decompiler.stub.TypeParameters
    public Name get(int id2) {
        throw new IllegalStateException("Unknown type parameter with id = " + id2);
    }
}

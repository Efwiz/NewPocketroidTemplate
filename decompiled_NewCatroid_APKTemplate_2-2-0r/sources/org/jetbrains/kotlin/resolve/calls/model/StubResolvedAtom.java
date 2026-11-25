package org.jetbrains.kotlin.resolve.calls.model;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.types.TypeConstructor;

/* JADX INFO: compiled from: ResolutionAtoms.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0016\u0010\u0005\u001a\u0004\u0018\u00010\u00068VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/model/StubResolvedAtom;", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedAtom;", "typeVariable", "Lorg/jetbrains/kotlin/types/TypeConstructor;", "(Lorg/jetbrains/kotlin/types/TypeConstructor;)V", "atom", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolutionAtom;", "getAtom", "()Lorg/jetbrains/kotlin/resolve/calls/model/ResolutionAtom;", "getTypeVariable", "()Lorg/jetbrains/kotlin/types/TypeConstructor;", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class StubResolvedAtom extends ResolvedAtom {
    private final TypeConstructor typeVariable;

    @Override // org.jetbrains.kotlin.resolve.calls.model.ResolvedAtom
    public ResolutionAtom getAtom() {
        return null;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public StubResolvedAtom(TypeConstructor typeVariable) {
        super(null);
        Intrinsics.checkNotNullParameter(typeVariable, "typeVariable");
        this.typeVariable = typeVariable;
    }

    public final TypeConstructor getTypeVariable() {
        return this.typeVariable;
    }
}

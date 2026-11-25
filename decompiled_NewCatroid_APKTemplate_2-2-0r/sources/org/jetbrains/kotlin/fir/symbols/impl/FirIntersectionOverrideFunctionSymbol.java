package org.jetbrains.kotlin.fir.symbols.impl;

import java.util.Collection;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.name.CallableId;

/* JADX INFO: compiled from: FirFunctionSymbol.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u00012\u00020\u0002B\u001f\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0010\u0010\u0005\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00070\u0006¢\u0006\u0002\u0010\bR\u001e\u0010\u0005\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00070\u0006X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/fir/symbols/impl/FirIntersectionOverrideFunctionSymbol;", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirNamedFunctionSymbol;", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirIntersectionCallableSymbol;", "callableId", "Lorg/jetbrains/kotlin/name/CallableId;", "intersections", "", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirCallableSymbol;", "(Lorg/jetbrains/kotlin/name/CallableId;Ljava/util/Collection;)V", "getIntersections", "()Ljava/util/Collection;", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirIntersectionOverrideFunctionSymbol extends FirNamedFunctionSymbol implements FirIntersectionCallableSymbol {
    private final Collection<FirCallableSymbol<?>> intersections;

    @Override // org.jetbrains.kotlin.fir.symbols.impl.FirIntersectionCallableSymbol
    public Collection<FirCallableSymbol<?>> getIntersections() {
        return this.intersections;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    public FirIntersectionOverrideFunctionSymbol(CallableId callableId, Collection<? extends FirCallableSymbol<?>> intersections) {
        super(callableId);
        Intrinsics.checkNotNullParameter(callableId, "callableId");
        Intrinsics.checkNotNullParameter(intersections, "intersections");
        this.intersections = intersections;
    }
}

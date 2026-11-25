package org.jetbrains.kotlin.fir.symbols.impl;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.fir.declarations.FirConstructor;
import org.jetbrains.kotlin.fir.declarations.FirResolvePhase;
import org.jetbrains.kotlin.fir.expressions.FirDelegatedConstructorCall;
import org.jetbrains.kotlin.fir.references.FirReference;
import org.jetbrains.kotlin.fir.references.FirReferenceUtilsKt;
import org.jetbrains.kotlin.fir.symbols.FirLazyDeclarationResolverKt;
import org.jetbrains.kotlin.mpp.ConstructorSymbolMarker;
import org.jetbrains.kotlin.name.CallableId;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqNamesUtilKt;

/* JADX INFO: compiled from: FirFunctionSymbol.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u00012\u00020\u0003B\u000f\b\u0016\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006B\r\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tR\u0011\u0010\n\u001a\u00020\u000b8F¢\u0006\u0006\u001a\u0004\b\f\u0010\rR\u0011\u0010\u000e\u001a\u00020\u000b8F¢\u0006\u0006\u001a\u0004\b\u000e\u0010\rR\u0013\u0010\u000f\u001a\u0004\u0018\u00010\u00008F¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u0011R\u0013\u0010\u0012\u001a\u0004\u0018\u00010\u00138F¢\u0006\u0006\u001a\u0004\b\u0014\u0010\u0015¨\u0006\u0016"}, d2 = {"Lorg/jetbrains/kotlin/fir/symbols/impl/FirConstructorSymbol;", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirFunctionSymbol;", "Lorg/jetbrains/kotlin/fir/declarations/FirConstructor;", "Lorg/jetbrains/kotlin/mpp/ConstructorSymbolMarker;", "classId", "Lorg/jetbrains/kotlin/name/ClassId;", "(Lorg/jetbrains/kotlin/name/ClassId;)V", "callableId", "Lorg/jetbrains/kotlin/name/CallableId;", "(Lorg/jetbrains/kotlin/name/CallableId;)V", "delegatedConstructorCallIsThis", "", "getDelegatedConstructorCallIsThis", "()Z", "isPrimary", "resolvedDelegatedConstructor", "getResolvedDelegatedConstructor", "()Lorg/jetbrains/kotlin/fir/symbols/impl/FirConstructorSymbol;", "resolvedDelegatedConstructorCall", "Lorg/jetbrains/kotlin/fir/expressions/FirDelegatedConstructorCall;", "getResolvedDelegatedConstructorCall", "()Lorg/jetbrains/kotlin/fir/expressions/FirDelegatedConstructorCall;", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirConstructorSymbol extends FirFunctionSymbol<FirConstructor> implements ConstructorSymbolMarker {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FirConstructorSymbol(CallableId callableId) {
        super(callableId, null);
        Intrinsics.checkNotNullParameter(callableId, "callableId");
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public FirConstructorSymbol(ClassId classId) {
        this(FqNamesUtilKt.callableIdForConstructor(classId));
        Intrinsics.checkNotNullParameter(classId, "classId");
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final boolean isPrimary() {
        return ((FirConstructor) getFir()).isPrimary();
    }

    public final FirConstructorSymbol getResolvedDelegatedConstructor() {
        FirReference calleeReference;
        FirDelegatedConstructorCall resolvedDelegatedConstructorCall = getResolvedDelegatedConstructorCall();
        if (resolvedDelegatedConstructorCall == null || (calleeReference = resolvedDelegatedConstructorCall.getCalleeReference()) == null) {
            return null;
        }
        return FirReferenceUtilsKt.toResolvedConstructorSymbol$default(calleeReference, false, 1, null);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final FirDelegatedConstructorCall getResolvedDelegatedConstructorCall() {
        if (((FirConstructor) getFir()).getDelegatedConstructor() == null) {
            return null;
        }
        FirLazyDeclarationResolverKt.lazyResolveToPhase(this, FirResolvePhase.BODY_RESOLVE);
        return ((FirConstructor) getFir()).getDelegatedConstructor();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final boolean getDelegatedConstructorCallIsThis() {
        FirDelegatedConstructorCall delegatedConstructor = ((FirConstructor) getFir()).getDelegatedConstructor();
        return delegatedConstructor != null && delegatedConstructor.getIsThis();
    }
}

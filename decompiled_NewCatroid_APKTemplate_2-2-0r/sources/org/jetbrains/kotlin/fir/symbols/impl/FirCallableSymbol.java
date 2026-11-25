package org.jetbrains.kotlin.fir.symbols.impl;

import io.netty.util.internal.StringUtil;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.fir.declarations.DeprecationsPerUseSite;
import org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirContextReceiver;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationStatus;
import org.jetbrains.kotlin.fir.declarations.FirReceiverParameter;
import org.jetbrains.kotlin.fir.declarations.FirResolvePhase;
import org.jetbrains.kotlin.fir.declarations.FirResolvedDeclarationStatus;
import org.jetbrains.kotlin.fir.declarations.FirTypeParameterRef;
import org.jetbrains.kotlin.fir.symbols.FirBasedSymbol;
import org.jetbrains.kotlin.fir.symbols.FirLazyDeclarationResolverKt;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.ConeSimpleKotlinType;
import org.jetbrains.kotlin.fir.types.FirImplicitTypeRef;
import org.jetbrains.kotlin.fir.types.FirResolvedTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeUtilsKt;
import org.jetbrains.kotlin.mpp.CallableSymbolMarker;
import org.jetbrains.kotlin.name.CallableId;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: FirCallableSymbol.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u008a\u0001\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\b&\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u00022\b\u0012\u0004\u0012\u0002H\u00010\u00032\u00020\u0004B\u0005¢\u0006\u0002\u0010\u0005J\n\u00100\u001a\u0004\u0018\u00010 H\u0002J\u0006\u00101\u001a\u000202J\u0012\u00103\u001a\u0002022\b\u00104\u001a\u0004\u0018\u000105H\u0002J\u0010\u00106\u001a\u0004\u0018\u0001072\u0006\u00108\u001a\u000209J\b\u0010:\u001a\u00020;H\u0016R\u0012\u0010\u0006\u001a\u00020\u0007X¦\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\tR\u0013\u0010\n\u001a\u0004\u0018\u00010\u000b8F¢\u0006\u0006\u001a\u0004\b\f\u0010\rR\u0011\u0010\u000e\u001a\u00020\u000f8F¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u0011R\u0011\u0010\u0012\u001a\u00020\u00138F¢\u0006\u0006\u001a\u0004\b\u0014\u0010\u0015R\u0013\u0010\u0016\u001a\u0004\u0018\u00010\u00178F¢\u0006\u0006\u001a\u0004\b\u0018\u0010\u0019R\u0017\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u001c0\u001b8F¢\u0006\u0006\u001a\u0004\b\u001d\u0010\u001eR\u0013\u0010\u001f\u001a\u0004\u0018\u00010 8F¢\u0006\u0006\u001a\u0004\b!\u0010\"R\u0011\u0010#\u001a\u00020$8F¢\u0006\u0006\u001a\u0004\b%\u0010&R\u0011\u0010'\u001a\u00020 8F¢\u0006\u0006\u001a\u0004\b(\u0010\"R\u0011\u0010)\u001a\u00020*8F¢\u0006\u0006\u001a\u0004\b+\u0010,R\u0017\u0010-\u001a\b\u0012\u0004\u0012\u00020.0\u001b8F¢\u0006\u0006\u001a\u0004\b/\u0010\u001e¨\u0006<"}, d2 = {"Lorg/jetbrains/kotlin/fir/symbols/impl/FirCallableSymbol;", "D", "Lorg/jetbrains/kotlin/fir/declarations/FirCallableDeclaration;", "Lorg/jetbrains/kotlin/fir/symbols/FirBasedSymbol;", "Lorg/jetbrains/kotlin/mpp/CallableSymbolMarker;", "()V", "callableId", "Lorg/jetbrains/kotlin/name/CallableId;", "getCallableId", "()Lorg/jetbrains/kotlin/name/CallableId;", "dispatchReceiverType", "Lorg/jetbrains/kotlin/fir/types/ConeSimpleKotlinType;", "getDispatchReceiverType", "()Lorg/jetbrains/kotlin/fir/types/ConeSimpleKotlinType;", "name", "Lorg/jetbrains/kotlin/name/Name;", "getName", "()Lorg/jetbrains/kotlin/name/Name;", "rawStatus", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationStatus;", "getRawStatus", "()Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationStatus;", "receiverParameter", "Lorg/jetbrains/kotlin/fir/declarations/FirReceiverParameter;", "getReceiverParameter", "()Lorg/jetbrains/kotlin/fir/declarations/FirReceiverParameter;", "resolvedContextReceivers", "", "Lorg/jetbrains/kotlin/fir/declarations/FirContextReceiver;", "getResolvedContextReceivers", "()Ljava/util/List;", "resolvedReceiverTypeRef", "Lorg/jetbrains/kotlin/fir/types/FirResolvedTypeRef;", "getResolvedReceiverTypeRef", "()Lorg/jetbrains/kotlin/fir/types/FirResolvedTypeRef;", "resolvedReturnType", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "getResolvedReturnType", "()Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "resolvedReturnTypeRef", "getResolvedReturnTypeRef", "resolvedStatus", "Lorg/jetbrains/kotlin/fir/declarations/FirResolvedDeclarationStatus;", "getResolvedStatus", "()Lorg/jetbrains/kotlin/fir/declarations/FirResolvedDeclarationStatus;", "typeParameterSymbols", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirTypeParameterSymbol;", "getTypeParameterSymbols", "calculateReceiverTypeRef", "calculateReturnType", "", "ensureType", "typeRef", "Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "getDeprecation", "Lorg/jetbrains/kotlin/fir/declarations/DeprecationsPerUseSite;", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "toString", "", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class FirCallableSymbol<D extends FirCallableDeclaration> extends FirBasedSymbol<D> implements CallableSymbolMarker {
    public abstract CallableId getCallableId();

    public final FirResolvedTypeRef getResolvedReturnTypeRef() {
        calculateReturnType();
        FirTypeRef returnTypeRef = getFir().getReturnTypeRef();
        Intrinsics.checkNotNull(returnTypeRef, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.types.FirResolvedTypeRef");
        return (FirResolvedTypeRef) returnTypeRef;
    }

    public final void calculateReturnType() {
        ensureType(getFir().getReturnTypeRef());
        FirTypeRef returnTypeRef = getFir().getReturnTypeRef();
        if (returnTypeRef instanceof FirResolvedTypeRef) {
            return;
        }
        UtilsKt.errorInLazyResolve(this, "returnTypeRef", Reflection.getOrCreateKotlinClass(returnTypeRef.getClass()), Reflection.getOrCreateKotlinClass(FirResolvedTypeRef.class));
        throw null;
    }

    public final ConeKotlinType getResolvedReturnType() {
        return FirTypeUtilsKt.getConeType(getResolvedReturnTypeRef());
    }

    public final FirResolvedTypeRef getResolvedReceiverTypeRef() {
        return calculateReceiverTypeRef();
    }

    private final FirResolvedTypeRef calculateReceiverTypeRef() {
        FirReceiverParameter receiverParameter = getFir().getReceiverParameter();
        if (receiverParameter == null) {
            return null;
        }
        ensureType(receiverParameter.getTypeRef());
        FirTypeRef typeRef = receiverParameter.getTypeRef();
        if (!(typeRef instanceof FirResolvedTypeRef)) {
            UtilsKt.errorInLazyResolve(this, "receiverTypeRef", Reflection.getOrCreateKotlinClass(typeRef.getClass()), Reflection.getOrCreateKotlinClass(FirResolvedTypeRef.class));
            throw null;
        }
        return (FirResolvedTypeRef) typeRef;
    }

    public final FirReceiverParameter getReceiverParameter() {
        calculateReceiverTypeRef();
        return getFir().getReceiverParameter();
    }

    public final List<FirContextReceiver> getResolvedContextReceivers() {
        if (getFir().getContextReceivers().isEmpty()) {
            return CollectionsKt.emptyList();
        }
        FirLazyDeclarationResolverKt.lazyResolveToPhase(this, FirResolvePhase.TYPES);
        return getFir().getContextReceivers();
    }

    public final FirResolvedDeclarationStatus getResolvedStatus() {
        return UtilsKt.resolvedStatus(getFir());
    }

    public final FirDeclarationStatus getRawStatus() {
        return getFir().getStatus();
    }

    public final List<FirTypeParameterSymbol> getTypeParameterSymbols() {
        List<FirTypeParameterRef> typeParameters = getFir().getTypeParameters();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(typeParameters, 10));
        Iterator<T> it = typeParameters.iterator();
        while (it.hasNext()) {
            arrayList.add(((FirTypeParameterRef) it.next()).getSymbol());
        }
        return arrayList;
    }

    public final ConeSimpleKotlinType getDispatchReceiverType() {
        return getFir().getDispatchReceiverType();
    }

    public final Name getName() {
        return getCallableId().getCallableName();
    }

    public final DeprecationsPerUseSite getDeprecation(LanguageVersionSettings languageVersionSettings) {
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        FirLazyDeclarationResolverKt.lazyResolveToPhase(this, FirResolvePhase.COMPILER_REQUIRED_ANNOTATIONS);
        return getFir().getDeprecationsProvider().getDeprecationsInfo(languageVersionSettings);
    }

    private final void ensureType(FirTypeRef typeRef) {
        if (typeRef == null ? true : typeRef instanceof FirResolvedTypeRef) {
            return;
        }
        if (typeRef instanceof FirImplicitTypeRef) {
            FirLazyDeclarationResolverKt.lazyResolveToPhase(this, FirResolvePhase.IMPLICIT_TYPES_BODY_RESOLVE);
        } else {
            FirLazyDeclarationResolverKt.lazyResolveToPhase(this, FirResolvePhase.TYPES);
        }
    }

    public String toString() {
        return Reflection.getOrCreateKotlinClass(getClass()).getSimpleName() + StringUtil.SPACE + getCallableId();
    }
}

package org.jetbrains.kotlin.fir.serialization;

import kotlin.Metadata;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;
import org.jetbrains.kotlin.fir.resolve.ScopeSession;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassSymbol;

/* JADX INFO: compiled from: FirProvidedDeclarationsForMetadataService.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
/* synthetic */ class FirProvidedDeclarationsForMetadataServiceImpl$membersCache$1 extends FunctionReference implements Function2<FirClassSymbol<?>, ScopeSession, FirProvidedDeclarationsForMetadataServiceImpl.ClassDeclarations> {
    FirProvidedDeclarationsForMetadataServiceImpl$membersCache$1(Object obj) {
        super(2, obj);
    }

    @Override // kotlin.jvm.internal.CallableReference
    public final String getSignature() {
        return "computeMemberDeclarations(Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassSymbol;Lorg/jetbrains/kotlin/fir/resolve/ScopeSession;)Lorg/jetbrains/kotlin/fir/serialization/FirProvidedDeclarationsForMetadataServiceImpl$ClassDeclarations;";
    }

    @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
    public final String getName() {
        return "computeMemberDeclarations";
    }

    @Override // kotlin.jvm.internal.CallableReference
    public final KDeclarationContainer getOwner() {
        return Reflection.getOrCreateKotlinClass(FirProvidedDeclarationsForMetadataServiceImpl.class);
    }

    @Override // kotlin.jvm.functions.Function2
    public final FirProvidedDeclarationsForMetadataServiceImpl.ClassDeclarations invoke(FirClassSymbol<?> p0, ScopeSession p1) {
        Intrinsics.checkNotNullParameter(p0, "p0");
        Intrinsics.checkNotNullParameter(p1, "p1");
        return ((FirProvidedDeclarationsForMetadataServiceImpl) this.receiver).computeMemberDeclarations(p0, p1);
    }
}

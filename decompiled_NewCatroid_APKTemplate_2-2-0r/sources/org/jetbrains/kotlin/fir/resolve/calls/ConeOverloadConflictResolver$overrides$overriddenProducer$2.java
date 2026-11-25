package org.jetbrains.kotlin.fir.resolve.calls;

import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;
import org.jetbrains.kotlin.fir.scopes.FirTypeScope;
import org.jetbrains.kotlin.fir.scopes.FirTypeScopeKt;
import org.jetbrains.kotlin.fir.scopes.ProcessorAction;
import org.jetbrains.kotlin.fir.symbols.impl.FirPropertySymbol;

/* JADX INFO: compiled from: ConeOverloadConflictResolver.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
/* synthetic */ class ConeOverloadConflictResolver$overrides$overriddenProducer$2 extends FunctionReference implements Function3<FirTypeScope, FirPropertySymbol, Function1<? super FirPropertySymbol, ? extends ProcessorAction>, ProcessorAction> {
    public static final ConeOverloadConflictResolver$overrides$overriddenProducer$2 INSTANCE = new ConeOverloadConflictResolver$overrides$overriddenProducer$2();

    ConeOverloadConflictResolver$overrides$overriddenProducer$2() {
        super(3);
    }

    @Override // kotlin.jvm.internal.CallableReference
    public final String getSignature() {
        return "processOverriddenProperties(Lorg/jetbrains/kotlin/fir/scopes/FirTypeScope;Lorg/jetbrains/kotlin/fir/symbols/impl/FirPropertySymbol;Lkotlin/jvm/functions/Function1;)Lorg/jetbrains/kotlin/fir/scopes/ProcessorAction;";
    }

    @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
    public final String getName() {
        return "processOverriddenProperties";
    }

    @Override // kotlin.jvm.internal.CallableReference
    public final KDeclarationContainer getOwner() {
        return Reflection.getOrCreateKotlinPackage(FirTypeScopeKt.class, "resolve");
    }

    @Override // kotlin.jvm.functions.Function3
    public final ProcessorAction invoke(FirTypeScope p0, FirPropertySymbol p1, Function1<? super FirPropertySymbol, ? extends ProcessorAction> p2) {
        Intrinsics.checkNotNullParameter(p0, "p0");
        Intrinsics.checkNotNullParameter(p1, "p1");
        Intrinsics.checkNotNullParameter(p2, "p2");
        return FirTypeScopeKt.processOverriddenProperties(p0, p1, p2);
    }
}

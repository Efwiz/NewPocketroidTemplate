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
import org.jetbrains.kotlin.fir.symbols.impl.FirNamedFunctionSymbol;

/* JADX INFO: compiled from: ConeOverloadConflictResolver.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
/* synthetic */ class ConeOverloadConflictResolver$overrides$overriddenProducer$1 extends FunctionReference implements Function3<FirTypeScope, FirNamedFunctionSymbol, Function1<? super FirNamedFunctionSymbol, ? extends ProcessorAction>, ProcessorAction> {
    public static final ConeOverloadConflictResolver$overrides$overriddenProducer$1 INSTANCE = new ConeOverloadConflictResolver$overrides$overriddenProducer$1();

    ConeOverloadConflictResolver$overrides$overriddenProducer$1() {
        super(3);
    }

    @Override // kotlin.jvm.internal.CallableReference
    public final String getSignature() {
        return "processOverriddenFunctions(Lorg/jetbrains/kotlin/fir/scopes/FirTypeScope;Lorg/jetbrains/kotlin/fir/symbols/impl/FirNamedFunctionSymbol;Lkotlin/jvm/functions/Function1;)Lorg/jetbrains/kotlin/fir/scopes/ProcessorAction;";
    }

    @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
    public final String getName() {
        return "processOverriddenFunctions";
    }

    @Override // kotlin.jvm.internal.CallableReference
    public final KDeclarationContainer getOwner() {
        return Reflection.getOrCreateKotlinPackage(FirTypeScopeKt.class, "resolve");
    }

    @Override // kotlin.jvm.functions.Function3
    public final ProcessorAction invoke(FirTypeScope p0, FirNamedFunctionSymbol p1, Function1<? super FirNamedFunctionSymbol, ? extends ProcessorAction> p2) {
        Intrinsics.checkNotNullParameter(p0, "p0");
        Intrinsics.checkNotNullParameter(p1, "p1");
        Intrinsics.checkNotNullParameter(p2, "p2");
        return FirTypeScopeKt.processOverriddenFunctions(p0, p1, p2);
    }
}

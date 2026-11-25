package org.jetbrains.kotlin.fir.scopes.impl;

import kotlin.Metadata;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;
import org.jetbrains.kotlin.fir.scopes.FirTypeScope;
import org.jetbrains.kotlin.fir.scopes.ProcessorAction;
import org.jetbrains.kotlin.fir.symbols.impl.FirPropertySymbol;

/* JADX INFO: compiled from: FirTypeIntersectionScopeContext.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
/* synthetic */ class FirTypeIntersectionScopeContext$chooseIntersectionOverrideModality$processDirectOverridden$2 extends FunctionReference implements Function3<FirTypeScope, FirPropertySymbol, Function2<? super FirPropertySymbol, ? super FirTypeScope, ? extends ProcessorAction>, ProcessorAction> {
    public static final FirTypeIntersectionScopeContext$chooseIntersectionOverrideModality$processDirectOverridden$2 INSTANCE = new FirTypeIntersectionScopeContext$chooseIntersectionOverrideModality$processDirectOverridden$2();

    FirTypeIntersectionScopeContext$chooseIntersectionOverrideModality$processDirectOverridden$2() {
        super(3);
    }

    @Override // kotlin.jvm.internal.CallableReference
    public final String getSignature() {
        return "processDirectOverriddenPropertiesWithBaseScope(Lorg/jetbrains/kotlin/fir/symbols/impl/FirPropertySymbol;Lkotlin/jvm/functions/Function2;)Lorg/jetbrains/kotlin/fir/scopes/ProcessorAction;";
    }

    @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
    public final String getName() {
        return "processDirectOverriddenPropertiesWithBaseScope";
    }

    @Override // kotlin.jvm.internal.CallableReference
    public final KDeclarationContainer getOwner() {
        return Reflection.getOrCreateKotlinClass(FirTypeScope.class);
    }

    @Override // kotlin.jvm.functions.Function3
    public final ProcessorAction invoke(FirTypeScope p0, FirPropertySymbol p1, Function2<? super FirPropertySymbol, ? super FirTypeScope, ? extends ProcessorAction> p2) {
        Intrinsics.checkNotNullParameter(p0, "p0");
        Intrinsics.checkNotNullParameter(p1, "p1");
        Intrinsics.checkNotNullParameter(p2, "p2");
        return p0.processDirectOverriddenPropertiesWithBaseScope(p1, p2);
    }
}

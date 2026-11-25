package org.jetbrains.kotlin.fir.resolve.providers.impl;

import kotlin.Metadata;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;
import org.jetbrains.kotlin.builtins.functions.FunctionTypeKind;
import org.jetbrains.kotlin.fir.symbols.impl.FirRegularClassSymbol;
import org.jetbrains.kotlin.name.ClassId;

/* JADX INFO: compiled from: FirExtensionSyntheticFunctionInterfaceProvider.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
/* synthetic */ class FirSyntheticFunctionInterfaceProviderBase$cache$1 extends FunctionReference implements Function2<ClassId, FunctionTypeKind, FirRegularClassSymbol> {
    FirSyntheticFunctionInterfaceProviderBase$cache$1(Object obj) {
        super(2, obj);
    }

    @Override // kotlin.jvm.internal.CallableReference
    public final String getSignature() {
        return "createSyntheticFunctionInterface(Lorg/jetbrains/kotlin/name/ClassId;Lorg/jetbrains/kotlin/builtins/functions/FunctionTypeKind;)Lorg/jetbrains/kotlin/fir/symbols/impl/FirRegularClassSymbol;";
    }

    @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
    public final String getName() {
        return "createSyntheticFunctionInterface";
    }

    @Override // kotlin.jvm.internal.CallableReference
    public final KDeclarationContainer getOwner() {
        return Reflection.getOrCreateKotlinClass(FirSyntheticFunctionInterfaceProviderBase.class);
    }

    @Override // kotlin.jvm.functions.Function2
    public final FirRegularClassSymbol invoke(ClassId p0, FunctionTypeKind p1) {
        Intrinsics.checkNotNullParameter(p0, "p0");
        Intrinsics.checkNotNullParameter(p1, "p1");
        return ((FirSyntheticFunctionInterfaceProviderBase) this.receiver).createSyntheticFunctionInterface(p0, p1);
    }
}

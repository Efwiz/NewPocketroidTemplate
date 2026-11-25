package org.jetbrains.kotlin.fir.resolve.providers.impl;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.functions.FunctionTypeKind;
import org.jetbrains.kotlin.builtins.functions.FunctionTypeKindKt;
import org.jetbrains.kotlin.fir.FirModuleData;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.scopes.FirKotlinScopeProvider;

/* JADX INFO: compiled from: FirExtensionSyntheticFunctionInterfaceProvider.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\f\u0010\t\u001a\u00020\n*\u00020\u000bH\u0014¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/providers/impl/FirBuiltinSyntheticFunctionInterfaceProvider;", "Lorg/jetbrains/kotlin/fir/resolve/providers/impl/FirSyntheticFunctionInterfaceProviderBase;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "moduleData", "Lorg/jetbrains/kotlin/fir/FirModuleData;", "kotlinScopeProvider", "Lorg/jetbrains/kotlin/fir/scopes/FirKotlinScopeProvider;", "(Lorg/jetbrains/kotlin/fir/FirSession;Lorg/jetbrains/kotlin/fir/FirModuleData;Lorg/jetbrains/kotlin/fir/scopes/FirKotlinScopeProvider;)V", "isAcceptable", "", "Lorg/jetbrains/kotlin/builtins/functions/FunctionTypeKind;", "providers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirBuiltinSyntheticFunctionInterfaceProvider extends FirSyntheticFunctionInterfaceProviderBase {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FirBuiltinSyntheticFunctionInterfaceProvider(FirSession session, FirModuleData moduleData, FirKotlinScopeProvider kotlinScopeProvider) {
        super(session, moduleData, kotlinScopeProvider);
        Intrinsics.checkNotNullParameter(session, "session");
        Intrinsics.checkNotNullParameter(moduleData, "moduleData");
        Intrinsics.checkNotNullParameter(kotlinScopeProvider, "kotlinScopeProvider");
    }

    @Override // org.jetbrains.kotlin.fir.resolve.providers.impl.FirSyntheticFunctionInterfaceProviderBase
    protected boolean isAcceptable(FunctionTypeKind functionTypeKind) {
        Intrinsics.checkNotNullParameter(functionTypeKind, "<this>");
        return FunctionTypeKindKt.isBuiltin(functionTypeKind);
    }
}

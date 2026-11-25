package org.jetbrains.kotlin.fir.scopes;

import com.google.android.gms.analytics.ecommerce.ProductAction;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;
import org.jetbrains.kotlin.fir.symbols.impl.FirConstructorSymbol;

/* JADX INFO: compiled from: FirScope.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
/* synthetic */ class FirScopeKt$getDeclaredConstructors$1$1 extends FunctionReference implements Function1<FirConstructorSymbol, Unit> {
    FirScopeKt$getDeclaredConstructors$1$1(Object obj) {
        super(1, obj);
    }

    @Override // kotlin.jvm.internal.CallableReference
    public final String getSignature() {
        return "getDeclaredConstructors$lambda$6$add$5(Ljava/util/List;Lorg/jetbrains/kotlin/fir/symbols/impl/FirConstructorSymbol;)V";
    }

    @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
    public final String getName() {
        return ProductAction.ACTION_ADD;
    }

    @Override // kotlin.jvm.internal.CallableReference
    public final KDeclarationContainer getOwner() {
        return Reflection.getOrCreateKotlinClass(Intrinsics.Kotlin.class);
    }

    @Override // kotlin.jvm.functions.Function1
    public /* bridge */ /* synthetic */ Unit invoke(FirConstructorSymbol firConstructorSymbol) {
        invoke2(firConstructorSymbol);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
    public final void invoke2(FirConstructorSymbol p0) {
        Intrinsics.checkNotNullParameter(p0, "p0");
        ((List) this.receiver).add(p0);
    }
}

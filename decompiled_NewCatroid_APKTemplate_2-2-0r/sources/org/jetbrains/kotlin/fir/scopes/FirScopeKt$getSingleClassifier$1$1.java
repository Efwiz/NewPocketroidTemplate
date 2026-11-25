package org.jetbrains.kotlin.fir.scopes;

import com.google.android.gms.analytics.ecommerce.ProductAction;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassifierSymbol;

/* JADX INFO: compiled from: FirScope.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
/* synthetic */ class FirScopeKt$getSingleClassifier$1$1 extends FunctionReference implements Function1<FirClassifierSymbol<?>, Unit> {
    FirScopeKt$getSingleClassifier$1$1(Object obj) {
        super(1, obj);
    }

    @Override // kotlin.jvm.internal.CallableReference
    public final String getSignature() {
        return "getSingleClassifier$lambda$0$add(Ljava/util/Set;Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassifierSymbol;)V";
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
    public /* bridge */ /* synthetic */ Unit invoke(FirClassifierSymbol<?> firClassifierSymbol) {
        invoke2(firClassifierSymbol);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
    public final void invoke2(FirClassifierSymbol<?> p0) {
        Intrinsics.checkNotNullParameter(p0, "p0");
        ((Set) this.receiver).add(p0);
    }
}

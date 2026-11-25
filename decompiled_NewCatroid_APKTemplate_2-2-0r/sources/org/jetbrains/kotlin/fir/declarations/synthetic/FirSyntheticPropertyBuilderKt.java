package org.jetbrains.kotlin.fir.declarations.synthetic;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.Constants;

/* JADX INFO: compiled from: FirSyntheticPropertyBuilder.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\u001a\u001f\u0010\u0000\u001a\u00020\u00012\u0017\u0010\u0002\u001a\u0013\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003¢\u0006\u0002\b\u0006¨\u0006\u0007"}, d2 = {"buildSyntheticProperty", "Lorg/jetbrains/kotlin/fir/declarations/synthetic/FirSyntheticProperty;", "f", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/fir/declarations/synthetic/FirSyntheticPropertyBuilder;", "", "Lkotlin/ExtensionFunctionType;", Constants.TYPE_TREE}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class FirSyntheticPropertyBuilderKt {
    public static final FirSyntheticProperty buildSyntheticProperty(Function1<? super FirSyntheticPropertyBuilder, Unit> f) {
        Intrinsics.checkNotNullParameter(f, "f");
        FirSyntheticPropertyBuilder firSyntheticPropertyBuilder = new FirSyntheticPropertyBuilder();
        f.invoke(firSyntheticPropertyBuilder);
        return firSyntheticPropertyBuilder.build();
    }
}

package org.jetbrains.kotlin.fir.types.builder;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.fir.types.FirPlaceholderProjection;

/* JADX INFO: compiled from: FirPlaceholderProjectionBuilder.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\u001a4\u0010\u0000\u001a\u00020\u00012\u0019\b\u0002\u0010\u0002\u001a\u0013\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003¢\u0006\u0002\b\u0006H\u0086\bø\u0001\u0000\u0082\u0002\n\n\b\b\u0001\u0012\u0002\u0010\u0001 \u0001\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006\u0007"}, d2 = {"buildPlaceholderProjection", "Lorg/jetbrains/kotlin/fir/types/FirPlaceholderProjection;", ConfigConstants.CONFIG_INIT_SECTION, "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/fir/types/builder/FirPlaceholderProjectionBuilder;", "", "Lkotlin/ExtensionFunctionType;", Constants.TYPE_TREE}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class FirPlaceholderProjectionBuilderKt {
    public static /* synthetic */ FirPlaceholderProjection buildPlaceholderProjection$default(Function1 init, int i, Object obj) {
        if ((i & 1) != 0) {
            init = new Function1<FirPlaceholderProjectionBuilder, Unit>() { // from class: org.jetbrains.kotlin.fir.types.builder.FirPlaceholderProjectionBuilderKt.buildPlaceholderProjection.1
                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(FirPlaceholderProjectionBuilder firPlaceholderProjectionBuilder) {
                    Intrinsics.checkNotNullParameter(firPlaceholderProjectionBuilder, "$this$null");
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(FirPlaceholderProjectionBuilder firPlaceholderProjectionBuilder) {
                    invoke2(firPlaceholderProjectionBuilder);
                    return Unit.INSTANCE;
                }
            };
        }
        Intrinsics.checkNotNullParameter(init, "init");
        FirPlaceholderProjectionBuilder firPlaceholderProjectionBuilder = new FirPlaceholderProjectionBuilder();
        init.invoke(firPlaceholderProjectionBuilder);
        return firPlaceholderProjectionBuilder.build();
    }

    public static final FirPlaceholderProjection buildPlaceholderProjection(Function1<? super FirPlaceholderProjectionBuilder, Unit> init) {
        Intrinsics.checkNotNullParameter(init, "init");
        FirPlaceholderProjectionBuilder firPlaceholderProjectionBuilder = new FirPlaceholderProjectionBuilder();
        init.invoke(firPlaceholderProjectionBuilder);
        return firPlaceholderProjectionBuilder.build();
    }
}

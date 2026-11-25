package org.jetbrains.kotlin.fir.expressions.builder;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.fir.expressions.FirArgumentList;

/* JADX INFO: compiled from: FirArgumentListBuilder.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\u001a4\u0010\u0000\u001a\u00020\u00012\u0019\b\u0002\u0010\u0002\u001a\u0013\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003¢\u0006\u0002\b\u0006H\u0086\bø\u0001\u0000\u0082\u0002\n\n\b\b\u0001\u0012\u0002\u0010\u0001 \u0001\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006\u0007"}, d2 = {"buildArgumentList", "Lorg/jetbrains/kotlin/fir/expressions/FirArgumentList;", ConfigConstants.CONFIG_INIT_SECTION, "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/fir/expressions/builder/FirArgumentListBuilder;", "", "Lkotlin/ExtensionFunctionType;", Constants.TYPE_TREE}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class FirArgumentListBuilderKt {
    public static /* synthetic */ FirArgumentList buildArgumentList$default(Function1 init, int i, Object obj) {
        if ((i & 1) != 0) {
            init = new Function1<FirArgumentListBuilder, Unit>() { // from class: org.jetbrains.kotlin.fir.expressions.builder.FirArgumentListBuilderKt.buildArgumentList.1
                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(FirArgumentListBuilder firArgumentListBuilder) {
                    Intrinsics.checkNotNullParameter(firArgumentListBuilder, "$this$null");
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(FirArgumentListBuilder firArgumentListBuilder) {
                    invoke2(firArgumentListBuilder);
                    return Unit.INSTANCE;
                }
            };
        }
        Intrinsics.checkNotNullParameter(init, "init");
        FirArgumentListBuilder firArgumentListBuilder = new FirArgumentListBuilder();
        init.invoke(firArgumentListBuilder);
        return firArgumentListBuilder.build();
    }

    public static final FirArgumentList buildArgumentList(Function1<? super FirArgumentListBuilder, Unit> init) {
        Intrinsics.checkNotNullParameter(init, "init");
        FirArgumentListBuilder firArgumentListBuilder = new FirArgumentListBuilder();
        init.invoke(firArgumentListBuilder);
        return firArgumentListBuilder.build();
    }
}

package org.jetbrains.kotlin.fir.declarations.builder;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.fir.declarations.FirCodeFragment;
import org.jetbrains.kotlin.fir.declarations.FirResolveStateKt;

/* JADX INFO: compiled from: FirCodeFragmentBuilder.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u001c\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u001a2\u0010\u0000\u001a\u00020\u00012\u0017\u0010\u0002\u001a\u0013\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003¢\u0006\u0002\b\u0006H\u0086\bø\u0001\u0000\u0082\u0002\n\n\b\b\u0001\u0012\u0002\u0010\u0001 \u0001\u001a:\u0010\u0007\u001a\u00020\u00012\u0006\u0010\b\u001a\u00020\u00012\u0017\u0010\u0002\u001a\u0013\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003¢\u0006\u0002\b\u0006H\u0086\bø\u0001\u0000\u0082\u0002\n\n\b\b\u0001\u0012\u0002\u0010\u0002 \u0001\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006\t"}, d2 = {"buildCodeFragment", "Lorg/jetbrains/kotlin/fir/declarations/FirCodeFragment;", ConfigConstants.CONFIG_INIT_SECTION, "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/fir/declarations/builder/FirCodeFragmentBuilder;", "", "Lkotlin/ExtensionFunctionType;", "buildCodeFragmentCopy", "original", Constants.TYPE_TREE}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class FirCodeFragmentBuilderKt {
    public static final FirCodeFragment buildCodeFragment(Function1<? super FirCodeFragmentBuilder, Unit> init) {
        Intrinsics.checkNotNullParameter(init, "init");
        FirCodeFragmentBuilder firCodeFragmentBuilder = new FirCodeFragmentBuilder();
        init.invoke(firCodeFragmentBuilder);
        return firCodeFragmentBuilder.mo6810build();
    }

    public static final FirCodeFragment buildCodeFragmentCopy(FirCodeFragment original, Function1<? super FirCodeFragmentBuilder, Unit> init) {
        Intrinsics.checkNotNullParameter(original, "original");
        Intrinsics.checkNotNullParameter(init, "init");
        FirCodeFragmentBuilder firCodeFragmentBuilder = new FirCodeFragmentBuilder();
        firCodeFragmentBuilder.setSource(original.getSource());
        firCodeFragmentBuilder.setResolvePhase(FirResolveStateKt.getResolvePhase(original));
        firCodeFragmentBuilder.getAnnotations().addAll(original.getAnnotations());
        firCodeFragmentBuilder.setModuleData(original.getModuleData());
        firCodeFragmentBuilder.setOrigin(original.getOrigin());
        firCodeFragmentBuilder.setAttributes(original.getAttributes().copy());
        firCodeFragmentBuilder.setSymbol(original.getSymbol());
        firCodeFragmentBuilder.setBlock(original.getBlock());
        init.invoke(firCodeFragmentBuilder);
        return firCodeFragmentBuilder.mo6810build();
    }
}

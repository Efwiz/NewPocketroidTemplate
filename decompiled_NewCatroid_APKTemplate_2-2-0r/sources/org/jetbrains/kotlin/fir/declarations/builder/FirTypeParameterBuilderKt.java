package org.jetbrains.kotlin.fir.declarations.builder;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.fir.declarations.FirResolveStateKt;
import org.jetbrains.kotlin.fir.declarations.FirTypeParameter;

/* JADX INFO: compiled from: FirTypeParameterBuilder.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u001c\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u001a2\u0010\u0000\u001a\u00020\u00012\u0017\u0010\u0002\u001a\u0013\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003¢\u0006\u0002\b\u0006H\u0086\bø\u0001\u0000\u0082\u0002\n\n\b\b\u0001\u0012\u0002\u0010\u0001 \u0001\u001a:\u0010\u0007\u001a\u00020\u00012\u0006\u0010\b\u001a\u00020\u00012\u0017\u0010\u0002\u001a\u0013\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003¢\u0006\u0002\b\u0006H\u0086\bø\u0001\u0000\u0082\u0002\n\n\b\b\u0001\u0012\u0002\u0010\u0002 \u0001\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006\t"}, d2 = {"buildTypeParameter", "Lorg/jetbrains/kotlin/fir/declarations/FirTypeParameter;", ConfigConstants.CONFIG_INIT_SECTION, "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/fir/declarations/builder/FirTypeParameterBuilder;", "", "Lkotlin/ExtensionFunctionType;", "buildTypeParameterCopy", "original", Constants.TYPE_TREE}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class FirTypeParameterBuilderKt {
    public static final FirTypeParameter buildTypeParameter(Function1<? super FirTypeParameterBuilder, Unit> init) {
        Intrinsics.checkNotNullParameter(init, "init");
        FirTypeParameterBuilder firTypeParameterBuilder = new FirTypeParameterBuilder();
        init.invoke(firTypeParameterBuilder);
        return firTypeParameterBuilder.mo6809build();
    }

    public static final FirTypeParameter buildTypeParameterCopy(FirTypeParameter original, Function1<? super FirTypeParameterBuilder, Unit> init) {
        Intrinsics.checkNotNullParameter(original, "original");
        Intrinsics.checkNotNullParameter(init, "init");
        FirTypeParameterBuilder firTypeParameterBuilder = new FirTypeParameterBuilder();
        firTypeParameterBuilder.setSource(original.getSource());
        firTypeParameterBuilder.setResolvePhase(FirResolveStateKt.getResolvePhase(original));
        firTypeParameterBuilder.setModuleData(original.getModuleData());
        firTypeParameterBuilder.setOrigin(original.getOrigin());
        firTypeParameterBuilder.setAttributes(original.getAttributes().copy());
        firTypeParameterBuilder.setName(original.getName());
        firTypeParameterBuilder.setSymbol(original.getSymbol());
        firTypeParameterBuilder.setContainingDeclarationSymbol(original.getContainingDeclarationSymbol());
        firTypeParameterBuilder.setVariance(original.getVariance());
        firTypeParameterBuilder.setReified(original.getIsReified());
        firTypeParameterBuilder.getBounds().addAll(original.getBounds());
        firTypeParameterBuilder.getAnnotations().addAll(original.getAnnotations());
        init.invoke(firTypeParameterBuilder);
        return firTypeParameterBuilder.mo6809build();
    }
}

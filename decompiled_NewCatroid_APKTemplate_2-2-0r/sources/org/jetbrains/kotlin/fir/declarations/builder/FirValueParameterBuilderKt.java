package org.jetbrains.kotlin.fir.declarations.builder;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.fir.declarations.FirResolveStateKt;
import org.jetbrains.kotlin.fir.declarations.FirValueParameter;

/* JADX INFO: compiled from: FirValueParameterBuilder.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u001c\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u001a2\u0010\u0000\u001a\u00020\u00012\u0017\u0010\u0002\u001a\u0013\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003¢\u0006\u0002\b\u0006H\u0086\bø\u0001\u0000\u0082\u0002\n\n\b\b\u0001\u0012\u0002\u0010\u0001 \u0001\u001a:\u0010\u0007\u001a\u00020\u00012\u0006\u0010\b\u001a\u00020\u00012\u0017\u0010\u0002\u001a\u0013\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003¢\u0006\u0002\b\u0006H\u0086\bø\u0001\u0000\u0082\u0002\n\n\b\b\u0001\u0012\u0002\u0010\u0002 \u0001\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006\t"}, d2 = {"buildValueParameter", "Lorg/jetbrains/kotlin/fir/declarations/FirValueParameter;", ConfigConstants.CONFIG_INIT_SECTION, "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/fir/declarations/builder/FirValueParameterBuilder;", "", "Lkotlin/ExtensionFunctionType;", "buildValueParameterCopy", "original", Constants.TYPE_TREE}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class FirValueParameterBuilderKt {
    public static final FirValueParameter buildValueParameter(Function1<? super FirValueParameterBuilder, Unit> init) {
        Intrinsics.checkNotNullParameter(init, "init");
        FirValueParameterBuilder firValueParameterBuilder = new FirValueParameterBuilder();
        init.invoke(firValueParameterBuilder);
        return firValueParameterBuilder.mo6809build();
    }

    public static final FirValueParameter buildValueParameterCopy(FirValueParameter original, Function1<? super FirValueParameterBuilder, Unit> init) {
        Intrinsics.checkNotNullParameter(original, "original");
        Intrinsics.checkNotNullParameter(init, "init");
        FirValueParameterBuilder firValueParameterBuilder = new FirValueParameterBuilder();
        firValueParameterBuilder.setSource(original.getSource());
        firValueParameterBuilder.setResolvePhase(FirResolveStateKt.getResolvePhase(original));
        firValueParameterBuilder.setModuleData(original.getModuleData());
        firValueParameterBuilder.setOrigin(original.getOrigin());
        firValueParameterBuilder.setAttributes(original.getAttributes().copy());
        firValueParameterBuilder.setReturnTypeRef(original.getReturnTypeRef());
        firValueParameterBuilder.setDeprecationsProvider(original.getDeprecationsProvider());
        firValueParameterBuilder.setContainerSource(original.getContainerSource());
        firValueParameterBuilder.setDispatchReceiverType(original.getDispatchReceiverType());
        firValueParameterBuilder.getContextReceivers().addAll(original.getContextReceivers());
        firValueParameterBuilder.setName(original.getName());
        firValueParameterBuilder.setBackingField(original.getBackingField());
        firValueParameterBuilder.getAnnotations().addAll(original.getAnnotations());
        firValueParameterBuilder.setSymbol(original.getSymbol());
        firValueParameterBuilder.setDefaultValue(original.getDefaultValue());
        firValueParameterBuilder.setContainingFunctionSymbol(original.getContainingFunctionSymbol());
        firValueParameterBuilder.setCrossinline(original.getIsCrossinline());
        firValueParameterBuilder.setNoinline(original.getIsNoinline());
        firValueParameterBuilder.setVararg(original.getIsVararg());
        init.invoke(firValueParameterBuilder);
        return firValueParameterBuilder.mo6809build();
    }
}

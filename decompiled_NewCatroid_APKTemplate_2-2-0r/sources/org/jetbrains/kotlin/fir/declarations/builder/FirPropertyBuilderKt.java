package org.jetbrains.kotlin.fir.declarations.builder;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.fir.declarations.FirProperty;
import org.jetbrains.kotlin.fir.declarations.FirResolveStateKt;

/* JADX INFO: compiled from: FirPropertyBuilder.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u001c\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u001a2\u0010\u0000\u001a\u00020\u00012\u0017\u0010\u0002\u001a\u0013\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003¢\u0006\u0002\b\u0006H\u0086\bø\u0001\u0000\u0082\u0002\n\n\b\b\u0001\u0012\u0002\u0010\u0001 \u0001\u001a:\u0010\u0007\u001a\u00020\u00012\u0006\u0010\b\u001a\u00020\u00012\u0017\u0010\u0002\u001a\u0013\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003¢\u0006\u0002\b\u0006H\u0086\bø\u0001\u0000\u0082\u0002\n\n\b\b\u0001\u0012\u0002\u0010\u0002 \u0001\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006\t"}, d2 = {"buildProperty", "Lorg/jetbrains/kotlin/fir/declarations/FirProperty;", ConfigConstants.CONFIG_INIT_SECTION, "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/fir/declarations/builder/FirPropertyBuilder;", "", "Lkotlin/ExtensionFunctionType;", "buildPropertyCopy", "original", Constants.TYPE_TREE}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class FirPropertyBuilderKt {
    public static final FirProperty buildProperty(Function1<? super FirPropertyBuilder, Unit> init) {
        Intrinsics.checkNotNullParameter(init, "init");
        FirPropertyBuilder firPropertyBuilder = new FirPropertyBuilder();
        init.invoke(firPropertyBuilder);
        return firPropertyBuilder.mo6809build();
    }

    public static final FirProperty buildPropertyCopy(FirProperty original, Function1<? super FirPropertyBuilder, Unit> init) {
        Intrinsics.checkNotNullParameter(original, "original");
        Intrinsics.checkNotNullParameter(init, "init");
        FirPropertyBuilder firPropertyBuilder = new FirPropertyBuilder();
        firPropertyBuilder.setSource(original.getSource());
        firPropertyBuilder.setResolvePhase(FirResolveStateKt.getResolvePhase(original));
        firPropertyBuilder.setModuleData(original.getModuleData());
        firPropertyBuilder.setOrigin(original.getOrigin());
        firPropertyBuilder.setAttributes(original.getAttributes().copy());
        firPropertyBuilder.setStatus(original.getStatus());
        firPropertyBuilder.setReturnTypeRef(original.getReturnTypeRef());
        firPropertyBuilder.setReceiverParameter(original.getReceiverParameter());
        firPropertyBuilder.setDeprecationsProvider(original.getDeprecationsProvider());
        firPropertyBuilder.setContainerSource(original.getContainerSource());
        firPropertyBuilder.setDispatchReceiverType(original.getDispatchReceiverType());
        firPropertyBuilder.setName(original.getName());
        firPropertyBuilder.setInitializer(original.getInitializer());
        firPropertyBuilder.setDelegate(original.getDelegate());
        firPropertyBuilder.setVar(original.getIsVar());
        firPropertyBuilder.setGetter(original.getGetter());
        firPropertyBuilder.setSetter(original.getSetter());
        firPropertyBuilder.setBackingField(original.getBackingField());
        firPropertyBuilder.getAnnotations().addAll(original.getAnnotations());
        firPropertyBuilder.getContextReceivers().addAll(original.getContextReceivers());
        firPropertyBuilder.setSymbol(original.getSymbol());
        firPropertyBuilder.setDelegateFieldSymbol(original.getDelegateFieldSymbol());
        firPropertyBuilder.setLocal(original.getIsLocal());
        firPropertyBuilder.setBodyResolveState(original.getBodyResolveState());
        firPropertyBuilder.getTypeParameters().addAll(original.getTypeParameters());
        init.invoke(firPropertyBuilder);
        return firPropertyBuilder.mo6809build();
    }
}

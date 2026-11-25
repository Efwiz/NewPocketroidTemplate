package org.jetbrains.kotlin.fir.declarations.builder;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.fir.declarations.FirConstructor;
import org.jetbrains.kotlin.fir.declarations.FirResolveStateKt;

/* JADX INFO: compiled from: FirConstructorBuilder.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u001c\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u001a2\u0010\u0000\u001a\u00020\u00012\u0017\u0010\u0002\u001a\u0013\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003¢\u0006\u0002\b\u0006H\u0086\bø\u0001\u0000\u0082\u0002\n\n\b\b\u0001\u0012\u0002\u0010\u0001 \u0001\u001a:\u0010\u0007\u001a\u00020\u00012\u0006\u0010\b\u001a\u00020\u00012\u0017\u0010\u0002\u001a\u0013\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003¢\u0006\u0002\b\u0006H\u0086\bø\u0001\u0000\u0082\u0002\n\n\b\b\u0001\u0012\u0002\u0010\u0002 \u0001\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006\t"}, d2 = {"buildConstructor", "Lorg/jetbrains/kotlin/fir/declarations/FirConstructor;", ConfigConstants.CONFIG_INIT_SECTION, "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/fir/declarations/builder/FirConstructorBuilder;", "", "Lkotlin/ExtensionFunctionType;", "buildConstructorCopy", "original", Constants.TYPE_TREE}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class FirConstructorBuilderKt {
    public static final FirConstructor buildConstructor(Function1<? super FirConstructorBuilder, Unit> init) {
        Intrinsics.checkNotNullParameter(init, "init");
        FirConstructorBuilder firConstructorBuilder = new FirConstructorBuilder();
        init.invoke(firConstructorBuilder);
        return firConstructorBuilder.mo6809build();
    }

    public static final FirConstructor buildConstructorCopy(FirConstructor original, Function1<? super FirConstructorBuilder, Unit> init) {
        Intrinsics.checkNotNullParameter(original, "original");
        Intrinsics.checkNotNullParameter(init, "init");
        FirConstructorBuilder firConstructorBuilder = new FirConstructorBuilder();
        firConstructorBuilder.setSource(original.getSource());
        firConstructorBuilder.setResolvePhase(FirResolveStateKt.getResolvePhase(original));
        firConstructorBuilder.setModuleData(original.getModuleData());
        firConstructorBuilder.setOrigin(original.getOrigin());
        firConstructorBuilder.setAttributes(original.getAttributes().copy());
        firConstructorBuilder.getTypeParameters().addAll(original.getTypeParameters());
        firConstructorBuilder.setStatus(original.getStatus());
        firConstructorBuilder.setReturnTypeRef(original.getReturnTypeRef());
        firConstructorBuilder.setReceiverParameter(original.getReceiverParameter());
        firConstructorBuilder.setDeprecationsProvider(original.getDeprecationsProvider());
        firConstructorBuilder.setContainerSource(original.getContainerSource());
        firConstructorBuilder.setDispatchReceiverType(original.getDispatchReceiverType());
        firConstructorBuilder.getContextReceivers().addAll(original.getContextReceivers());
        firConstructorBuilder.getValueParameters().addAll(original.getValueParameters());
        firConstructorBuilder.setContractDescription(original.getContractDescription());
        firConstructorBuilder.getAnnotations().addAll(original.getAnnotations());
        firConstructorBuilder.setSymbol(original.getSymbol());
        firConstructorBuilder.setDelegatedConstructor(original.getDelegatedConstructor());
        firConstructorBuilder.setBody(original.getBody());
        init.invoke(firConstructorBuilder);
        return firConstructorBuilder.mo6809build();
    }
}

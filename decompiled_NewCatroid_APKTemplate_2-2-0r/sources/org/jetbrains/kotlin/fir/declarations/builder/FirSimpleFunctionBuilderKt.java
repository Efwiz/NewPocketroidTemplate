package org.jetbrains.kotlin.fir.declarations.builder;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.fir.declarations.FirResolveStateKt;
import org.jetbrains.kotlin.fir.declarations.FirSimpleFunction;

/* JADX INFO: compiled from: FirSimpleFunctionBuilder.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u001c\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u001a2\u0010\u0000\u001a\u00020\u00012\u0017\u0010\u0002\u001a\u0013\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003¢\u0006\u0002\b\u0006H\u0086\bø\u0001\u0000\u0082\u0002\n\n\b\b\u0001\u0012\u0002\u0010\u0001 \u0001\u001a:\u0010\u0007\u001a\u00020\u00012\u0006\u0010\b\u001a\u00020\u00012\u0017\u0010\u0002\u001a\u0013\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003¢\u0006\u0002\b\u0006H\u0086\bø\u0001\u0000\u0082\u0002\n\n\b\b\u0001\u0012\u0002\u0010\u0002 \u0001\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006\t"}, d2 = {"buildSimpleFunction", "Lorg/jetbrains/kotlin/fir/declarations/FirSimpleFunction;", ConfigConstants.CONFIG_INIT_SECTION, "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/fir/declarations/builder/FirSimpleFunctionBuilder;", "", "Lkotlin/ExtensionFunctionType;", "buildSimpleFunctionCopy", "original", Constants.TYPE_TREE}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class FirSimpleFunctionBuilderKt {
    public static final FirSimpleFunction buildSimpleFunction(Function1<? super FirSimpleFunctionBuilder, Unit> init) {
        Intrinsics.checkNotNullParameter(init, "init");
        FirSimpleFunctionBuilder firSimpleFunctionBuilder = new FirSimpleFunctionBuilder();
        init.invoke(firSimpleFunctionBuilder);
        return firSimpleFunctionBuilder.mo6809build();
    }

    public static final FirSimpleFunction buildSimpleFunctionCopy(FirSimpleFunction original, Function1<? super FirSimpleFunctionBuilder, Unit> init) {
        Intrinsics.checkNotNullParameter(original, "original");
        Intrinsics.checkNotNullParameter(init, "init");
        FirSimpleFunctionBuilder firSimpleFunctionBuilder = new FirSimpleFunctionBuilder();
        firSimpleFunctionBuilder.setSource(original.getSource());
        firSimpleFunctionBuilder.setResolvePhase(FirResolveStateKt.getResolvePhase(original));
        firSimpleFunctionBuilder.setModuleData(original.getModuleData());
        firSimpleFunctionBuilder.setOrigin(original.getOrigin());
        firSimpleFunctionBuilder.setAttributes(original.getAttributes().copy());
        firSimpleFunctionBuilder.setStatus(original.getStatus());
        firSimpleFunctionBuilder.setReturnTypeRef(original.getReturnTypeRef());
        firSimpleFunctionBuilder.setReceiverParameter(original.getReceiverParameter());
        firSimpleFunctionBuilder.setDeprecationsProvider(original.getDeprecationsProvider());
        firSimpleFunctionBuilder.setContainerSource(original.getContainerSource());
        firSimpleFunctionBuilder.setDispatchReceiverType(original.getDispatchReceiverType());
        firSimpleFunctionBuilder.getContextReceivers().addAll(original.getContextReceivers());
        firSimpleFunctionBuilder.getValueParameters().addAll(original.getValueParameters());
        firSimpleFunctionBuilder.setBody(original.getBody());
        firSimpleFunctionBuilder.setContractDescription(original.getContractDescription());
        firSimpleFunctionBuilder.setName(original.getName());
        firSimpleFunctionBuilder.setSymbol(original.getSymbol());
        firSimpleFunctionBuilder.getAnnotations().addAll(original.getAnnotations());
        firSimpleFunctionBuilder.getTypeParameters().addAll(original.getTypeParameters());
        init.invoke(firSimpleFunctionBuilder);
        return firSimpleFunctionBuilder.mo6809build();
    }
}

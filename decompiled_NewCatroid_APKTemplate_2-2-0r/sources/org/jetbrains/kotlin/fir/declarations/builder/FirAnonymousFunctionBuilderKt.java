package org.jetbrains.kotlin.fir.declarations.builder;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.fir.declarations.FirAnonymousFunction;
import org.jetbrains.kotlin.fir.declarations.FirResolveStateKt;

/* JADX INFO: compiled from: FirAnonymousFunctionBuilder.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u001c\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u001a2\u0010\u0000\u001a\u00020\u00012\u0017\u0010\u0002\u001a\u0013\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003¢\u0006\u0002\b\u0006H\u0086\bø\u0001\u0000\u0082\u0002\n\n\b\b\u0001\u0012\u0002\u0010\u0001 \u0001\u001a:\u0010\u0007\u001a\u00020\u00012\u0006\u0010\b\u001a\u00020\u00012\u0017\u0010\u0002\u001a\u0013\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003¢\u0006\u0002\b\u0006H\u0086\bø\u0001\u0000\u0082\u0002\n\n\b\b\u0001\u0012\u0002\u0010\u0002 \u0001\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006\t"}, d2 = {"buildAnonymousFunction", "Lorg/jetbrains/kotlin/fir/declarations/FirAnonymousFunction;", ConfigConstants.CONFIG_INIT_SECTION, "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/fir/declarations/builder/FirAnonymousFunctionBuilder;", "", "Lkotlin/ExtensionFunctionType;", "buildAnonymousFunctionCopy", "original", Constants.TYPE_TREE}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class FirAnonymousFunctionBuilderKt {
    public static final FirAnonymousFunction buildAnonymousFunction(Function1<? super FirAnonymousFunctionBuilder, Unit> init) {
        Intrinsics.checkNotNullParameter(init, "init");
        FirAnonymousFunctionBuilder firAnonymousFunctionBuilder = new FirAnonymousFunctionBuilder();
        init.invoke(firAnonymousFunctionBuilder);
        return firAnonymousFunctionBuilder.mo6809build();
    }

    public static final FirAnonymousFunction buildAnonymousFunctionCopy(FirAnonymousFunction original, Function1<? super FirAnonymousFunctionBuilder, Unit> init) {
        Intrinsics.checkNotNullParameter(original, "original");
        Intrinsics.checkNotNullParameter(init, "init");
        FirAnonymousFunctionBuilder firAnonymousFunctionBuilder = new FirAnonymousFunctionBuilder();
        firAnonymousFunctionBuilder.setSource(original.getSource());
        firAnonymousFunctionBuilder.setResolvePhase(FirResolveStateKt.getResolvePhase(original));
        firAnonymousFunctionBuilder.getAnnotations().addAll(original.getAnnotations());
        firAnonymousFunctionBuilder.setModuleData(original.getModuleData());
        firAnonymousFunctionBuilder.setOrigin(original.getOrigin());
        firAnonymousFunctionBuilder.setAttributes(original.getAttributes().copy());
        firAnonymousFunctionBuilder.setStatus(original.getStatus());
        firAnonymousFunctionBuilder.setReturnTypeRef(original.getReturnTypeRef());
        firAnonymousFunctionBuilder.setReceiverParameter(original.getReceiverParameter());
        firAnonymousFunctionBuilder.setDeprecationsProvider(original.getDeprecationsProvider());
        firAnonymousFunctionBuilder.setContainerSource(original.getContainerSource());
        firAnonymousFunctionBuilder.setDispatchReceiverType(original.getDispatchReceiverType());
        firAnonymousFunctionBuilder.getContextReceivers().addAll(original.getContextReceivers());
        firAnonymousFunctionBuilder.setControlFlowGraphReference(original.getControlFlowGraphReference());
        firAnonymousFunctionBuilder.getValueParameters().addAll(original.getValueParameters());
        firAnonymousFunctionBuilder.setBody(original.getBody());
        firAnonymousFunctionBuilder.setContractDescription(original.getContractDescription());
        firAnonymousFunctionBuilder.setSymbol(original.getSymbol());
        firAnonymousFunctionBuilder.setLabel(original.getLabel());
        firAnonymousFunctionBuilder.setInvocationKind(original.getInvocationKind());
        firAnonymousFunctionBuilder.setInlineStatus(original.getInlineStatus());
        firAnonymousFunctionBuilder.setLambda(original.getIsLambda());
        firAnonymousFunctionBuilder.setHasExplicitParameterList(original.getHasExplicitParameterList());
        firAnonymousFunctionBuilder.getTypeParameters().addAll(original.getTypeParameters());
        firAnonymousFunctionBuilder.setTypeRef(original.getTypeRef());
        init.invoke(firAnonymousFunctionBuilder);
        return firAnonymousFunctionBuilder.mo6809build();
    }
}

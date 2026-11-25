package org.jetbrains.kotlin.fir.declarations.builder;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.fir.declarations.FirField;
import org.jetbrains.kotlin.fir.declarations.FirResolveStateKt;

/* JADX INFO: compiled from: FirFieldBuilder.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u001c\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u001a2\u0010\u0000\u001a\u00020\u00012\u0017\u0010\u0002\u001a\u0013\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003¢\u0006\u0002\b\u0006H\u0086\bø\u0001\u0000\u0082\u0002\n\n\b\b\u0001\u0012\u0002\u0010\u0001 \u0001\u001a:\u0010\u0007\u001a\u00020\u00012\u0006\u0010\b\u001a\u00020\u00012\u0017\u0010\u0002\u001a\u0013\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003¢\u0006\u0002\b\u0006H\u0086\bø\u0001\u0000\u0082\u0002\n\n\b\b\u0001\u0012\u0002\u0010\u0002 \u0001\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006\t"}, d2 = {"buildField", "Lorg/jetbrains/kotlin/fir/declarations/FirField;", ConfigConstants.CONFIG_INIT_SECTION, "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/fir/declarations/builder/FirFieldBuilder;", "", "Lkotlin/ExtensionFunctionType;", "buildFieldCopy", "original", Constants.TYPE_TREE}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class FirFieldBuilderKt {
    public static final FirField buildField(Function1<? super FirFieldBuilder, Unit> init) {
        Intrinsics.checkNotNullParameter(init, "init");
        FirFieldBuilder firFieldBuilder = new FirFieldBuilder();
        init.invoke(firFieldBuilder);
        return firFieldBuilder.mo6809build();
    }

    public static final FirField buildFieldCopy(FirField original, Function1<? super FirFieldBuilder, Unit> init) {
        Intrinsics.checkNotNullParameter(original, "original");
        Intrinsics.checkNotNullParameter(init, "init");
        FirFieldBuilder firFieldBuilder = new FirFieldBuilder();
        firFieldBuilder.setSource(original.getSource());
        firFieldBuilder.setResolvePhase(FirResolveStateKt.getResolvePhase(original));
        firFieldBuilder.setModuleData(original.getModuleData());
        firFieldBuilder.setOrigin(original.getOrigin());
        firFieldBuilder.setAttributes(original.getAttributes().copy());
        firFieldBuilder.getTypeParameters().addAll(original.getTypeParameters());
        firFieldBuilder.setStatus(original.getStatus());
        firFieldBuilder.setReturnTypeRef(original.getReturnTypeRef());
        firFieldBuilder.setDeprecationsProvider(original.getDeprecationsProvider());
        firFieldBuilder.setContainerSource(original.getContainerSource());
        firFieldBuilder.setDispatchReceiverType(original.getDispatchReceiverType());
        firFieldBuilder.getContextReceivers().addAll(original.getContextReceivers());
        firFieldBuilder.setName(original.getName());
        firFieldBuilder.setInitializer(original.getInitializer());
        firFieldBuilder.setVar(original.getIsVar());
        firFieldBuilder.setBackingField(original.getBackingField());
        firFieldBuilder.getAnnotations().addAll(original.getAnnotations());
        firFieldBuilder.setSymbol(original.getSymbol());
        init.invoke(firFieldBuilder);
        return firFieldBuilder.mo6809build();
    }
}

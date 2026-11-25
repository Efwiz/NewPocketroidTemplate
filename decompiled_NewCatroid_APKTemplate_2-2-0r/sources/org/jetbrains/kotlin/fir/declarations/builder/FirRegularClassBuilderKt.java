package org.jetbrains.kotlin.fir.declarations.builder;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.fir.declarations.FirRegularClass;
import org.jetbrains.kotlin.fir.declarations.FirResolveStateKt;

/* JADX INFO: compiled from: FirRegularClassBuilder.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u001c\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u001a2\u0010\u0000\u001a\u00020\u00012\u0017\u0010\u0002\u001a\u0013\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003¢\u0006\u0002\b\u0006H\u0086\bø\u0001\u0000\u0082\u0002\n\n\b\b\u0001\u0012\u0002\u0010\u0001 \u0001\u001a:\u0010\u0007\u001a\u00020\u00012\u0006\u0010\b\u001a\u00020\u00012\u0017\u0010\u0002\u001a\u0013\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003¢\u0006\u0002\b\u0006H\u0086\bø\u0001\u0000\u0082\u0002\n\n\b\b\u0001\u0012\u0002\u0010\u0002 \u0001\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006\t"}, d2 = {"buildRegularClass", "Lorg/jetbrains/kotlin/fir/declarations/FirRegularClass;", ConfigConstants.CONFIG_INIT_SECTION, "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/fir/declarations/builder/FirRegularClassBuilder;", "", "Lkotlin/ExtensionFunctionType;", "buildRegularClassCopy", "original", Constants.TYPE_TREE}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class FirRegularClassBuilderKt {
    public static final FirRegularClass buildRegularClass(Function1<? super FirRegularClassBuilder, Unit> init) {
        Intrinsics.checkNotNullParameter(init, "init");
        FirRegularClassBuilder firRegularClassBuilder = new FirRegularClassBuilder();
        init.invoke(firRegularClassBuilder);
        return firRegularClassBuilder.mo6809build();
    }

    public static final FirRegularClass buildRegularClassCopy(FirRegularClass original, Function1<? super FirRegularClassBuilder, Unit> init) {
        Intrinsics.checkNotNullParameter(original, "original");
        Intrinsics.checkNotNullParameter(init, "init");
        FirRegularClassBuilder firRegularClassBuilder = new FirRegularClassBuilder();
        firRegularClassBuilder.setSource(original.getSource());
        firRegularClassBuilder.setResolvePhase(FirResolveStateKt.getResolvePhase(original));
        firRegularClassBuilder.setModuleData(original.getModuleData());
        firRegularClassBuilder.setOrigin(original.getOrigin());
        firRegularClassBuilder.setAttributes(original.getAttributes().copy());
        firRegularClassBuilder.getTypeParameters().addAll(original.getTypeParameters());
        firRegularClassBuilder.setStatus(original.getStatus());
        firRegularClassBuilder.setDeprecationsProvider(original.getDeprecationsProvider());
        firRegularClassBuilder.setClassKind(original.getClassKind());
        firRegularClassBuilder.getDeclarations().addAll(original.getDeclarations());
        firRegularClassBuilder.getAnnotations().addAll(original.getAnnotations());
        firRegularClassBuilder.setScopeProvider(original.getScopeProvider());
        firRegularClassBuilder.setName(original.getName());
        firRegularClassBuilder.setSymbol(original.getSymbol());
        firRegularClassBuilder.setCompanionObjectSymbol(original.getCompanionObjectSymbol());
        firRegularClassBuilder.getSuperTypeRefs().addAll(original.getSuperTypeRefs());
        firRegularClassBuilder.getContextReceivers().addAll(original.getContextReceivers());
        init.invoke(firRegularClassBuilder);
        return firRegularClassBuilder.mo6809build();
    }
}

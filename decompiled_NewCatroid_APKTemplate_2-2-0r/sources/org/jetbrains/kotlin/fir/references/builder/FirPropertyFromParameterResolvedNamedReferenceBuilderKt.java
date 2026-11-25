package org.jetbrains.kotlin.fir.references.builder;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.fir.references.FirResolvedNamedReference;

/* JADX INFO: compiled from: FirPropertyFromParameterResolvedNamedReferenceBuilder.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\u001a2\u0010\u0000\u001a\u00020\u00012\u0017\u0010\u0002\u001a\u0013\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003¢\u0006\u0002\b\u0006H\u0086\bø\u0001\u0000\u0082\u0002\n\n\b\b\u0001\u0012\u0002\u0010\u0001 \u0001\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006\u0007"}, d2 = {"buildPropertyFromParameterResolvedNamedReference", "Lorg/jetbrains/kotlin/fir/references/FirResolvedNamedReference;", ConfigConstants.CONFIG_INIT_SECTION, "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/fir/references/builder/FirPropertyFromParameterResolvedNamedReferenceBuilder;", "", "Lkotlin/ExtensionFunctionType;", Constants.TYPE_TREE}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class FirPropertyFromParameterResolvedNamedReferenceBuilderKt {
    public static final FirResolvedNamedReference buildPropertyFromParameterResolvedNamedReference(Function1<? super FirPropertyFromParameterResolvedNamedReferenceBuilder, Unit> init) {
        Intrinsics.checkNotNullParameter(init, "init");
        FirPropertyFromParameterResolvedNamedReferenceBuilder firPropertyFromParameterResolvedNamedReferenceBuilder = new FirPropertyFromParameterResolvedNamedReferenceBuilder();
        init.invoke(firPropertyFromParameterResolvedNamedReferenceBuilder);
        return firPropertyFromParameterResolvedNamedReferenceBuilder.build();
    }
}

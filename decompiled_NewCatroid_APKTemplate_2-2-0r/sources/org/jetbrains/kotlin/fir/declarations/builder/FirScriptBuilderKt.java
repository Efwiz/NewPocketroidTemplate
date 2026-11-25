package org.jetbrains.kotlin.fir.declarations.builder;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.fir.declarations.FirResolveStateKt;
import org.jetbrains.kotlin.fir.declarations.FirScript;

/* JADX INFO: compiled from: FirScriptBuilder.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u001c\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u001a2\u0010\u0000\u001a\u00020\u00012\u0017\u0010\u0002\u001a\u0013\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003¢\u0006\u0002\b\u0006H\u0086\bø\u0001\u0000\u0082\u0002\n\n\b\b\u0001\u0012\u0002\u0010\u0001 \u0001\u001a:\u0010\u0007\u001a\u00020\u00012\u0006\u0010\b\u001a\u00020\u00012\u0017\u0010\u0002\u001a\u0013\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003¢\u0006\u0002\b\u0006H\u0086\bø\u0001\u0000\u0082\u0002\n\n\b\b\u0001\u0012\u0002\u0010\u0002 \u0001\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006\t"}, d2 = {"buildScript", "Lorg/jetbrains/kotlin/fir/declarations/FirScript;", ConfigConstants.CONFIG_INIT_SECTION, "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/fir/declarations/builder/FirScriptBuilder;", "", "Lkotlin/ExtensionFunctionType;", "buildScriptCopy", "original", Constants.TYPE_TREE}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class FirScriptBuilderKt {
    public static final FirScript buildScript(Function1<? super FirScriptBuilder, Unit> init) {
        Intrinsics.checkNotNullParameter(init, "init");
        FirScriptBuilder firScriptBuilder = new FirScriptBuilder();
        init.invoke(firScriptBuilder);
        return firScriptBuilder.mo6809build();
    }

    public static final FirScript buildScriptCopy(FirScript original, Function1<? super FirScriptBuilder, Unit> init) {
        Intrinsics.checkNotNullParameter(original, "original");
        Intrinsics.checkNotNullParameter(init, "init");
        FirScriptBuilder firScriptBuilder = new FirScriptBuilder();
        firScriptBuilder.setSource(original.getSource());
        firScriptBuilder.setResolvePhase(FirResolveStateKt.getResolvePhase(original));
        firScriptBuilder.getAnnotations().addAll(original.getAnnotations());
        firScriptBuilder.setModuleData(original.getModuleData());
        firScriptBuilder.setOrigin(original.getOrigin());
        firScriptBuilder.setAttributes(original.getAttributes().copy());
        firScriptBuilder.setName(original.getName());
        firScriptBuilder.getStatements().addAll(original.getStatements());
        firScriptBuilder.setSymbol(original.getSymbol());
        firScriptBuilder.getParameters().addAll(original.getParameters());
        firScriptBuilder.getContextReceivers().addAll(original.getContextReceivers());
        firScriptBuilder.setResultPropertyName(original.getResultPropertyName());
        init.invoke(firScriptBuilder);
        return firScriptBuilder.mo6809build();
    }
}

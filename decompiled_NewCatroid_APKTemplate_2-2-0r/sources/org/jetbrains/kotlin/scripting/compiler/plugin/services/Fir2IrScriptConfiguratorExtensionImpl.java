package org.jetbrains.kotlin.scripting.compiler.plugin.services;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.script.experimental.api.ScriptCompilationConfiguration;
import kotlin.script.experimental.api.SourceCode;
import kotlin.script.experimental.host.ScriptingHostConfiguration;
import org.jetbrains.kotlin.KtSourceFile;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.backend.Fir2IrScriptConfiguratorExtension;
import org.jetbrains.kotlin.fir.declarations.FirFile;
import org.jetbrains.kotlin.fir.declarations.FirScript;
import org.jetbrains.kotlin.fir.extensions.FirExtension;
import org.jetbrains.kotlin.fir.resolve.providers.FirProvider;
import org.jetbrains.kotlin.fir.resolve.providers.FirProviderKt;
import org.jetbrains.kotlin.fir.symbols.impl.FirScriptSymbol;
import org.jetbrains.kotlin.ir.declarations.IrScript;
import org.jetbrains.kotlin.ir.symbols.IrScriptSymbol;
import org.jetbrains.kotlin.scripting.resolve.RefineCompilationConfigurationKt;
import org.jetbrains.kotlin.utils.PathUtil;

/* JADX INFO: compiled from: Fir2IrScriptConfiguratorExtensionImpl.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 \u00102\u00020\u0001:\u0001\u0010B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J*\u0010\u0007\u001a\u00020\b*\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0014\u0010\f\u001a\u0010\u0012\u0004\u0012\u00020\u000e\u0012\u0006\u0012\u0004\u0018\u00010\u000f0\rH\u0016¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/scripting/compiler/plugin/services/Fir2IrScriptConfiguratorExtensionImpl;", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrScriptConfiguratorExtension;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "hostConfiguration", "Lkotlin/script/experimental/host/ScriptingHostConfiguration;", "(Lorg/jetbrains/kotlin/fir/FirSession;Lkotlin/script/experimental/host/ScriptingHostConfiguration;)V", "configure", "", "Lorg/jetbrains/kotlin/ir/declarations/IrScript;", "script", "Lorg/jetbrains/kotlin/fir/declarations/FirScript;", "getIrScriptByFirSymbol", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirScriptSymbol;", "Lorg/jetbrains/kotlin/ir/symbols/IrScriptSymbol;", "Companion", PathUtil.KOTLIN_SCRIPTING_COMPILER_PLUGIN_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class Fir2IrScriptConfiguratorExtensionImpl extends Fir2IrScriptConfiguratorExtension {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public Fir2IrScriptConfiguratorExtensionImpl(FirSession session, ScriptingHostConfiguration hostConfiguration) {
        super(session);
        Intrinsics.checkNotNullParameter(session, "session");
        Intrinsics.checkNotNullParameter(hostConfiguration, "hostConfiguration");
    }

    @Override // org.jetbrains.kotlin.fir.backend.Fir2IrScriptConfiguratorExtension
    public void configure(IrScript irScript, FirScript script, Function1<? super FirScriptSymbol, ? extends IrScriptSymbol> getIrScriptByFirSymbol) {
        KtSourceFile sourceFile;
        SourceCode sourceCode;
        ScriptCompilationConfiguration scriptCompilationConfiguration;
        List<SourceCode> list;
        Intrinsics.checkNotNullParameter(irScript, "<this>");
        Intrinsics.checkNotNullParameter(script, "script");
        Intrinsics.checkNotNullParameter(getIrScriptByFirSymbol, "getIrScriptByFirSymbol");
        FirFile firScriptContainerFile = FirProviderKt.getFirProvider(getSession()).getFirScriptContainerFile(script.getSymbol());
        if (firScriptContainerFile == null || (sourceFile = firScriptContainerFile.getSourceFile()) == null || (sourceCode = FirScriptConfigurationExtensionImplKt.toSourceCode(sourceFile)) == null || (scriptCompilationConfiguration = ScriptConfigurationUtilsKt.getScriptCompilationConfiguration(getSession(), sourceCode, new Function1<FirScriptDefinitionProviderService, ScriptCompilationConfiguration>() { // from class: org.jetbrains.kotlin.scripting.compiler.plugin.services.Fir2IrScriptConfiguratorExtensionImpl$configure$compilationConfiguration$1
            @Override // kotlin.jvm.functions.Function1
            public final ScriptCompilationConfiguration invoke(FirScriptDefinitionProviderService getScriptCompilationConfiguration) {
                Intrinsics.checkNotNullParameter(getScriptCompilationConfiguration, "$this$getScriptCompilationConfiguration");
                return null;
            }
        })) == null || (list = (List) scriptCompilationConfiguration.get(RefineCompilationConfigurationKt.getResolvedImportScripts(ScriptCompilationConfiguration.INSTANCE))) == null) {
            return;
        }
        if (list.isEmpty()) {
            list = null;
        }
        if (list == null) {
            return;
        }
        ArrayList arrayList = new ArrayList();
        for (SourceCode sourceCode2 : list) {
            FirProvider firProvider = FirProviderKt.getFirProvider(getSession());
            String locationId = sourceCode2.getLocationId();
            Intrinsics.checkNotNull(locationId);
            FirScriptSymbol firScriptByFilePath = firProvider.getFirScriptByFilePath(locationId);
            if (firScriptByFilePath != null) {
                arrayList.add(firScriptByFilePath);
            }
        }
        ArrayList arrayList2 = arrayList;
        ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList2, 10));
        Iterator it = arrayList2.iterator();
        while (it.hasNext()) {
            IrScriptSymbol irScriptSymbolInvoke = getIrScriptByFirSymbol.invoke((FirScriptSymbol) it.next());
            Intrinsics.checkNotNull(irScriptSymbolInvoke);
            arrayList3.add(irScriptSymbolInvoke);
        }
        ArrayList arrayList4 = arrayList3;
        irScript.setImportedScripts(arrayList4.isEmpty() ? null : arrayList4);
    }

    /* JADX INFO: compiled from: Fir2IrScriptConfiguratorExtensionImpl.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/scripting/compiler/plugin/services/Fir2IrScriptConfiguratorExtensionImpl$Companion;", "", "()V", "getFactory", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrScriptConfiguratorExtension$Factory;", "hostConfiguration", "Lkotlin/script/experimental/host/ScriptingHostConfiguration;", PathUtil.KOTLIN_SCRIPTING_COMPILER_PLUGIN_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final Fir2IrScriptConfiguratorExtension getFactory$lambda$0(ScriptingHostConfiguration hostConfiguration, FirSession session) {
            Intrinsics.checkNotNullParameter(hostConfiguration, "$hostConfiguration");
            Intrinsics.checkNotNullParameter(session, "session");
            return new Fir2IrScriptConfiguratorExtensionImpl(session, hostConfiguration);
        }

        public final Fir2IrScriptConfiguratorExtension.Factory getFactory(final ScriptingHostConfiguration hostConfiguration) {
            Intrinsics.checkNotNullParameter(hostConfiguration, "hostConfiguration");
            return new Fir2IrScriptConfiguratorExtension.Factory() { // from class: org.jetbrains.kotlin.scripting.compiler.plugin.services.Fir2IrScriptConfiguratorExtensionImpl$Companion$$ExternalSyntheticLambda0
                @Override // org.jetbrains.kotlin.fir.extensions.FirExtension.Factory
                public final FirExtension create(FirSession firSession) {
                    return Fir2IrScriptConfiguratorExtensionImpl.Companion.getFactory$lambda$0(hostConfiguration, firSession);
                }
            };
        }
    }
}

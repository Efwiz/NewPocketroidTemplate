package org.jetbrains.kotlin.scripting.compiler.plugin.services;

import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.mock.MockProject;
import org.jetbrains.kotlin.com.intellij.openapi.util.Disposer;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.extensions.FirExtension;
import org.jetbrains.kotlin.fir.extensions.FirExtensionSessionComponent;
import org.jetbrains.kotlin.scripting.compiler.plugin.definitions.CliScriptDefinitionProvider;
import org.jetbrains.kotlin.scripting.compiler.plugin.definitions.CliScriptDependenciesProvider;
import org.jetbrains.kotlin.scripting.definitions.ScriptDefinition;
import org.jetbrains.kotlin.scripting.definitions.ScriptDefinitionProvider;
import org.jetbrains.kotlin.scripting.definitions.ScriptDefinitionsSource;
import org.jetbrains.kotlin.scripting.definitions.ScriptDependenciesProvider;
import org.jetbrains.kotlin.utils.PathUtil;

/* JADX INFO: compiled from: FirScriptDefinitionProviderService.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0010\u0018\u0000 \u00172\u00020\u0001:\u0001\u0017B)\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u0012\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\b0\u0005¢\u0006\u0002\u0010\tR\u0010\u0010\n\u001a\u0004\u0018\u00010\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u000b\u001a\u0004\u0018\u00010\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R(\u0010\r\u001a\u0004\u0018\u00010\b2\b\u0010\f\u001a\u0004\u0018\u00010\b8F@FX\u0086\u000e¢\u0006\f\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011R(\u0010\u0012\u001a\u0004\u0018\u00010\u00062\b\u0010\f\u001a\u0004\u0018\u00010\u00068F@FX\u0086\u000e¢\u0006\f\u001a\u0004\b\u0013\u0010\u0014\"\u0004\b\u0015\u0010\u0016R\u0014\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\b0\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0018"}, d2 = {"Lorg/jetbrains/kotlin/scripting/compiler/plugin/services/FirScriptDefinitionProviderService;", "Lorg/jetbrains/kotlin/fir/extensions/FirExtensionSessionComponent;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "makeDefaultDefinitionProvider", "Lkotlin/Function0;", "Lorg/jetbrains/kotlin/scripting/definitions/ScriptDefinitionProvider;", "makeDefaultConfigurationProvider", "Lorg/jetbrains/kotlin/scripting/definitions/ScriptDependenciesProvider;", "(Lorg/jetbrains/kotlin/fir/FirSession;Lkotlin/jvm/functions/Function0;Lkotlin/jvm/functions/Function0;)V", "_configurationProvider", "_definitionProvider", "value", "configurationProvider", "getConfigurationProvider", "()Lorg/jetbrains/kotlin/scripting/definitions/ScriptDependenciesProvider;", "setConfigurationProvider", "(Lorg/jetbrains/kotlin/scripting/definitions/ScriptDependenciesProvider;)V", "definitionProvider", "getDefinitionProvider", "()Lorg/jetbrains/kotlin/scripting/definitions/ScriptDefinitionProvider;", "setDefinitionProvider", "(Lorg/jetbrains/kotlin/scripting/definitions/ScriptDefinitionProvider;)V", "Companion", PathUtil.KOTLIN_SCRIPTING_COMPILER_PLUGIN_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirScriptDefinitionProviderService extends FirExtensionSessionComponent {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private ScriptDependenciesProvider _configurationProvider;
    private ScriptDefinitionProvider _definitionProvider;
    private final Function0<ScriptDependenciesProvider> makeDefaultConfigurationProvider;
    private final Function0<ScriptDefinitionProvider> makeDefaultDefinitionProvider;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    public FirScriptDefinitionProviderService(FirSession session, Function0<? extends ScriptDefinitionProvider> makeDefaultDefinitionProvider, Function0<? extends ScriptDependenciesProvider> makeDefaultConfigurationProvider) {
        super(session);
        Intrinsics.checkNotNullParameter(session, "session");
        Intrinsics.checkNotNullParameter(makeDefaultDefinitionProvider, "makeDefaultDefinitionProvider");
        Intrinsics.checkNotNullParameter(makeDefaultConfigurationProvider, "makeDefaultConfigurationProvider");
        this.makeDefaultDefinitionProvider = makeDefaultDefinitionProvider;
        this.makeDefaultConfigurationProvider = makeDefaultConfigurationProvider;
    }

    public final ScriptDefinitionProvider getDefinitionProvider() {
        ScriptDefinitionProvider scriptDefinitionProvider;
        synchronized (this) {
            if (this._definitionProvider == null) {
                this._definitionProvider = this.makeDefaultDefinitionProvider.invoke();
            }
            scriptDefinitionProvider = this._definitionProvider;
        }
        return scriptDefinitionProvider;
    }

    public final void setDefinitionProvider(ScriptDefinitionProvider scriptDefinitionProvider) {
        synchronized (this) {
            this._definitionProvider = scriptDefinitionProvider;
            Unit unit = Unit.INSTANCE;
        }
    }

    public final ScriptDependenciesProvider getConfigurationProvider() {
        ScriptDependenciesProvider scriptDependenciesProvider;
        synchronized (this) {
            if (this._configurationProvider == null) {
                this._configurationProvider = this.makeDefaultConfigurationProvider.invoke();
            }
            scriptDependenciesProvider = this._configurationProvider;
        }
        return scriptDependenciesProvider;
    }

    public final void setConfigurationProvider(ScriptDependenciesProvider scriptDependenciesProvider) {
        synchronized (this) {
            this._configurationProvider = scriptDependenciesProvider;
            Unit unit = Unit.INSTANCE;
        }
    }

    /* JADX INFO: compiled from: FirScriptDefinitionProviderService.kt */
    @Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J:\u0010\u0003\u001a\u00020\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u00062\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\t0\u00062\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u000b2\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\r¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/scripting/compiler/plugin/services/FirScriptDefinitionProviderService$Companion;", "", "()V", "getFactory", "Lorg/jetbrains/kotlin/fir/extensions/FirExtensionSessionComponent$Factory;", "definitions", "", "Lorg/jetbrains/kotlin/scripting/definitions/ScriptDefinition;", "definitionSources", "Lorg/jetbrains/kotlin/scripting/definitions/ScriptDefinitionsSource;", "definitionProvider", "Lorg/jetbrains/kotlin/scripting/definitions/ScriptDefinitionProvider;", "configurationProvider", "Lorg/jetbrains/kotlin/scripting/definitions/ScriptDependenciesProvider;", PathUtil.KOTLIN_SCRIPTING_COMPILER_PLUGIN_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public static /* synthetic */ FirExtensionSessionComponent.Factory getFactory$default(Companion companion, List list, List list2, ScriptDefinitionProvider scriptDefinitionProvider, ScriptDependenciesProvider scriptDependenciesProvider, int i, Object obj) {
            if ((i & 4) != 0) {
                scriptDefinitionProvider = null;
            }
            if ((i & 8) != 0) {
                scriptDependenciesProvider = null;
            }
            return companion.getFactory(list, list2, scriptDefinitionProvider, scriptDependenciesProvider);
        }

        public final FirExtensionSessionComponent.Factory getFactory(final List<? extends ScriptDefinition> definitions, final List<? extends ScriptDefinitionsSource> definitionSources, final ScriptDefinitionProvider definitionProvider, final ScriptDependenciesProvider configurationProvider) {
            Intrinsics.checkNotNullParameter(definitions, "definitions");
            Intrinsics.checkNotNullParameter(definitionSources, "definitionSources");
            final Function0<CliScriptDefinitionProvider> function0 = definitionProvider != null ? (Function0) new Function0<ScriptDefinitionProvider>() { // from class: org.jetbrains.kotlin.scripting.compiler.plugin.services.FirScriptDefinitionProviderService$Companion$getFactory$makeDefinitionsProvider$1$1
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final ScriptDefinitionProvider invoke() {
                    return definitionProvider;
                }
            } : new Function0<CliScriptDefinitionProvider>() { // from class: org.jetbrains.kotlin.scripting.compiler.plugin.services.FirScriptDefinitionProviderService$Companion$getFactory$makeDefinitionsProvider$2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                /* JADX WARN: Multi-variable type inference failed */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final CliScriptDefinitionProvider invoke() {
                    CliScriptDefinitionProvider cliScriptDefinitionProvider = new CliScriptDefinitionProvider();
                    List<ScriptDefinitionsSource> list = definitionSources;
                    List<ScriptDefinition> list2 = definitions;
                    cliScriptDefinitionProvider.setScriptDefinitionsSources(list);
                    cliScriptDefinitionProvider.setScriptDefinitions(list2);
                    return cliScriptDefinitionProvider;
                }
            };
            final Function0 function1 = configurationProvider != null ? new Function0<ScriptDependenciesProvider>() { // from class: org.jetbrains.kotlin.scripting.compiler.plugin.services.FirScriptDefinitionProviderService$Companion$getFactory$makeConfigurationProvider$1$1
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final ScriptDependenciesProvider invoke() {
                    return configurationProvider;
                }
            } : new Function0<CliScriptDependenciesProvider>() { // from class: org.jetbrains.kotlin.scripting.compiler.plugin.services.FirScriptDefinitionProviderService$Companion$getFactory$makeConfigurationProvider$2
                @Override // kotlin.jvm.functions.Function0
                public final CliScriptDependenciesProvider invoke() {
                    return new CliScriptDependenciesProvider(new MockProject(null, Disposer.newDisposable()));
                }
            };
            return new FirExtensionSessionComponent.Factory() { // from class: org.jetbrains.kotlin.scripting.compiler.plugin.services.FirScriptDefinitionProviderService$Companion$$ExternalSyntheticLambda0
                @Override // org.jetbrains.kotlin.fir.extensions.FirExtension.Factory
                public final FirExtension create(FirSession firSession) {
                    return FirScriptDefinitionProviderService.Companion.getFactory$lambda$2(function0, function1, firSession);
                }
            };
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final FirExtensionSessionComponent getFactory$lambda$2(Function0 makeDefinitionsProvider, Function0 makeConfigurationProvider, FirSession session) {
            Intrinsics.checkNotNullParameter(makeDefinitionsProvider, "$makeDefinitionsProvider");
            Intrinsics.checkNotNullParameter(makeConfigurationProvider, "$makeConfigurationProvider");
            Intrinsics.checkNotNullParameter(session, "session");
            return new FirScriptDefinitionProviderService(session, makeDefinitionsProvider, makeConfigurationProvider);
        }
    }
}

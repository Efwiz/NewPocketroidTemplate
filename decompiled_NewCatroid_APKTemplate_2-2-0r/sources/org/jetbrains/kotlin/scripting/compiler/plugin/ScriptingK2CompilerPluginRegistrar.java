package org.jetbrains.kotlin.scripting.compiler.plugin;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.script.experimental.host.ScriptingHostConfiguration;
import kotlin.script.experimental.jvm.JvmScriptingHostConfigurationKt;
import org.jetbrains.kotlin.compiler.plugin.CompilerPluginRegistrar;
import org.jetbrains.kotlin.config.CompilerConfiguration;
import org.jetbrains.kotlin.fir.extensions.FirExtensionRegistrarAdapter;
import org.jetbrains.kotlin.utils.PathUtil;

/* JADX INFO: compiled from: pluginRegisrar.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 \f2\u00020\u0001:\u0001\fB\u0005¢\u0006\u0002\u0010\u0002J\u0014\u0010\u0007\u001a\u00020\b*\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016R\u0014\u0010\u0003\u001a\u00020\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/scripting/compiler/plugin/ScriptingK2CompilerPluginRegistrar;", "Lorg/jetbrains/kotlin/compiler/plugin/CompilerPluginRegistrar;", "()V", "supportsK2", "", "getSupportsK2", "()Z", "registerExtensions", "", "Lorg/jetbrains/kotlin/compiler/plugin/CompilerPluginRegistrar$ExtensionStorage;", "configuration", "Lorg/jetbrains/kotlin/config/CompilerConfiguration;", "Companion", PathUtil.KOTLIN_SCRIPTING_COMPILER_PLUGIN_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ScriptingK2CompilerPluginRegistrar extends CompilerPluginRegistrar {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);

    @Override // org.jetbrains.kotlin.compiler.plugin.CompilerPluginRegistrar
    public boolean getSupportsK2() {
        return true;
    }

    /* JADX INFO: compiled from: pluginRegisrar.kt */
    @Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0016\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/scripting/compiler/plugin/ScriptingK2CompilerPluginRegistrar$Companion;", "", "()V", "registerComponents", "", "extensionStorage", "Lorg/jetbrains/kotlin/compiler/plugin/CompilerPluginRegistrar$ExtensionStorage;", "compilerConfiguration", "Lorg/jetbrains/kotlin/config/CompilerConfiguration;", PathUtil.KOTLIN_SCRIPTING_COMPILER_PLUGIN_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final void registerComponents(CompilerPluginRegistrar.ExtensionStorage extensionStorage, CompilerConfiguration compilerConfiguration) {
            Intrinsics.checkNotNullParameter(extensionStorage, "extensionStorage");
            Intrinsics.checkNotNullParameter(compilerConfiguration, "compilerConfiguration");
            extensionStorage.registerExtension(FirExtensionRegistrarAdapter.INSTANCE, new FirScriptingCompilerExtensionRegistrar(new ScriptingHostConfiguration(new ScriptingHostConfiguration[]{JvmScriptingHostConfigurationKt.getDefaultJvmScriptingHostConfiguration()}, new Function1<ScriptingHostConfiguration.Builder, Unit>() { // from class: org.jetbrains.kotlin.scripting.compiler.plugin.ScriptingK2CompilerPluginRegistrar$Companion$registerComponents$1$hostConfiguration$1
                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(ScriptingHostConfiguration.Builder $receiver) {
                    Intrinsics.checkNotNullParameter($receiver, "$this$$receiver");
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(ScriptingHostConfiguration.Builder builder) {
                    invoke2(builder);
                    return Unit.INSTANCE;
                }
            }), compilerConfiguration));
            extensionStorage.registerExtension(FirExtensionRegistrarAdapter.INSTANCE, new FirScriptingSamWithReceiverExtensionRegistrar());
        }
    }

    @Override // org.jetbrains.kotlin.compiler.plugin.CompilerPluginRegistrar
    public void registerExtensions(CompilerPluginRegistrar.ExtensionStorage extensionStorage, CompilerConfiguration configuration) {
        Intrinsics.checkNotNullParameter(extensionStorage, "<this>");
        Intrinsics.checkNotNullParameter(configuration, "configuration");
        INSTANCE.registerComponents(extensionStorage, configuration);
    }
}

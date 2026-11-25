package org.jetbrains.kotlin.compiler.plugin;

import java.util.List;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.mock.MockProject;
import org.jetbrains.kotlin.config.CompilerConfiguration;
import org.jetbrains.kotlin.config.CompilerConfigurationKey;

/* JADX INFO: compiled from: ComponentRegistrar.kt */
/* JADX INFO: loaded from: classes3.dex */
@Deprecated(level = DeprecationLevel.WARNING, message = "ComponentRegistrar is deprecated. Please use CompilerPluginRegistrar instead. Check https://youtrack.jetbrains.com/issue/KT-52665 for more details", replaceWith = @ReplaceWith(expression = "CompilerPluginRegistrar", imports = {"org.jetbrains.kotlin.compiler.plugin.CompilerPluginRegistrar"}))
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bg\u0018\u0000 \f2\u00020\u0001:\u0001\fJ\u0018\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH&R\u0014\u0010\u0002\u001a\u00020\u00038VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\rÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/compiler/plugin/ComponentRegistrar;", "", "supportsK2", "", "getSupportsK2", "()Z", "registerProjectComponents", "", "project", "Lorg/jetbrains/kotlin/com/intellij/mock/MockProject;", "configuration", "Lorg/jetbrains/kotlin/config/CompilerConfiguration;", "Companion", "plugin-api"}, k = 1, mv = {1, 9, 0}, xi = 48)
@ExperimentalCompilerApi
public interface ComponentRegistrar {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = Companion.$$INSTANCE;

    default boolean getSupportsK2() {
        return false;
    }

    void registerProjectComponents(MockProject project, CompilerConfiguration configuration);

    /* JADX INFO: compiled from: ComponentRegistrar.kt */
    @Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R#\u0010\u0003\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00060\u00050\u0004¢\u0006\u000e\n\u0000\u0012\u0004\b\u0007\u0010\u0002\u001a\u0004\b\b\u0010\t¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/compiler/plugin/ComponentRegistrar$Companion;", "", "()V", "PLUGIN_COMPONENT_REGISTRARS", "Lorg/jetbrains/kotlin/config/CompilerConfigurationKey;", "", "Lorg/jetbrains/kotlin/compiler/plugin/ComponentRegistrar;", "getPLUGIN_COMPONENT_REGISTRARS$annotations", "getPLUGIN_COMPONENT_REGISTRARS", "()Lorg/jetbrains/kotlin/config/CompilerConfigurationKey;", "plugin-api"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        static final /* synthetic */ Companion $$INSTANCE = new Companion();
        private static final CompilerConfigurationKey<List<ComponentRegistrar>> PLUGIN_COMPONENT_REGISTRARS;

        public static /* synthetic */ void getPLUGIN_COMPONENT_REGISTRARS$annotations() {
        }

        private Companion() {
        }

        public final CompilerConfigurationKey<List<ComponentRegistrar>> getPLUGIN_COMPONENT_REGISTRARS() {
            return PLUGIN_COMPONENT_REGISTRARS;
        }

        static {
            CompilerConfigurationKey<List<ComponentRegistrar>> compilerConfigurationKeyCreate = CompilerConfigurationKey.create("plugin component registrars");
            Intrinsics.checkNotNullExpressionValue(compilerConfigurationKeyCreate, "create(...)");
            PLUGIN_COMPONENT_REGISTRARS = compilerConfigurationKeyCreate;
        }
    }
}

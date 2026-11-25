package org.jetbrains.kotlin.compiler.plugin;

import androidx.exifinterface.media.ExifInterface;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.jetbrains.kotlin.config.CompilerConfiguration;
import org.jetbrains.kotlin.config.CompilerConfigurationKey;
import org.jetbrains.kotlin.extensions.ProjectExtensionDescriptor;

/* JADX INFO: compiled from: CompilerPluginRegistrar.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b'\u0018\u0000 \f2\u00020\u0001:\u0002\f\rB\u0005¢\u0006\u0002\u0010\u0002J\u0014\u0010\u0007\u001a\u00020\b*\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH&R\u0012\u0010\u0003\u001a\u00020\u0004X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/compiler/plugin/CompilerPluginRegistrar;", "", "()V", "supportsK2", "", "getSupportsK2", "()Z", "registerExtensions", "", "Lorg/jetbrains/kotlin/compiler/plugin/CompilerPluginRegistrar$ExtensionStorage;", "configuration", "Lorg/jetbrains/kotlin/config/CompilerConfiguration;", "Companion", "ExtensionStorage", "plugin-api"}, k = 1, mv = {1, 9, 0}, xi = 48)
@ExperimentalCompilerApi
public abstract class CompilerPluginRegistrar {
    private static final CompilerConfigurationKey<List<CompilerPluginRegistrar>> COMPILER_PLUGIN_REGISTRARS;

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);

    public abstract boolean getSupportsK2();

    public abstract void registerExtensions(ExtensionStorage extensionStorage, CompilerConfiguration compilerConfiguration);

    /* JADX INFO: compiled from: CompilerPluginRegistrar.kt */
    @Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u001d\u0010\u0003\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00060\u00050\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/compiler/plugin/CompilerPluginRegistrar$Companion;", "", "()V", "COMPILER_PLUGIN_REGISTRARS", "Lorg/jetbrains/kotlin/config/CompilerConfigurationKey;", "", "Lorg/jetbrains/kotlin/compiler/plugin/CompilerPluginRegistrar;", "getCOMPILER_PLUGIN_REGISTRARS", "()Lorg/jetbrains/kotlin/config/CompilerConfigurationKey;", "plugin-api"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final CompilerConfigurationKey<List<CompilerPluginRegistrar>> getCOMPILER_PLUGIN_REGISTRARS() {
            return CompilerPluginRegistrar.COMPILER_PLUGIN_REGISTRARS;
        }
    }

    static {
        CompilerConfigurationKey<List<CompilerPluginRegistrar>> compilerConfigurationKeyCreate = CompilerConfigurationKey.create("Compiler plugin registrars");
        Intrinsics.checkNotNullExpressionValue(compilerConfigurationKeyCreate, "create(...)");
        COMPILER_PLUGIN_REGISTRARS = compilerConfigurationKeyCreate;
    }

    /* JADX INFO: compiled from: CompilerPluginRegistrar.kt */
    @Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\u0010!\n\u0000\n\u0002\u0010$\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J'\u0010\f\u001a\u00020\r\"\b\b\u0000\u0010\u000e*\u00020\u0001*\b\u0012\u0004\u0012\u0002H\u000e0\u00052\u0006\u0010\u000f\u001a\u0002H\u000e¢\u0006\u0002\u0010\u0010R$\u0010\u0003\u001a\u0018\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u0005\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00010\u00060\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R'\u0010\u0007\u001a\u0018\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u0005\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00010\t0\b8F¢\u0006\u0006\u001a\u0004\b\n\u0010\u000b¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/compiler/plugin/CompilerPluginRegistrar$ExtensionStorage;", "", "()V", "_registeredExtensions", "", "Lorg/jetbrains/kotlin/extensions/ProjectExtensionDescriptor;", "", "registeredExtensions", "", "", "getRegisteredExtensions", "()Ljava/util/Map;", "registerExtension", "", ExifInterface.GPS_DIRECTION_TRUE, SchemaSymbols.ATTVAL_EXTENSION, "(Lorg/jetbrains/kotlin/extensions/ProjectExtensionDescriptor;Ljava/lang/Object;)V", "plugin-api"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class ExtensionStorage {
        private final Map<ProjectExtensionDescriptor<?>, List<Object>> _registeredExtensions = new LinkedHashMap();

        public final Map<ProjectExtensionDescriptor<?>, List<Object>> getRegisteredExtensions() {
            return this._registeredExtensions;
        }

        public final <T> void registerExtension(ProjectExtensionDescriptor<T> projectExtensionDescriptor, T extension) {
            Intrinsics.checkNotNullParameter(projectExtensionDescriptor, "<this>");
            Intrinsics.checkNotNullParameter(extension, "extension");
            Map<ProjectExtensionDescriptor<?>, List<Object>> map = this._registeredExtensions;
            ArrayList arrayList = map.get(projectExtensionDescriptor);
            if (arrayList == null) {
                arrayList = new ArrayList();
                map.put(projectExtensionDescriptor, arrayList);
            }
            arrayList.add(extension);
        }
    }
}

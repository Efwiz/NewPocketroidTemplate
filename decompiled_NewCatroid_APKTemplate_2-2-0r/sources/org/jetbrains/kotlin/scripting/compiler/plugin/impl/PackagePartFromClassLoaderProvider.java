package org.jetbrains.kotlin.scripting.compiler.plugin.impl;

import java.io.InputStream;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.io.ByteStreamsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.script.experimental.jvm.util.JvmClassLoaderUtilKt;
import kotlin.text.StringsKt;
import org.jetbrains.kotlin.cli.common.messages.MessageCollector;
import org.jetbrains.kotlin.cli.jvm.compiler.JvmPackagePartProviderKt;
import org.jetbrains.kotlin.com.intellij.util.SmartList;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.load.kotlin.JvmPackagePartProviderBase;
import org.jetbrains.kotlin.metadata.jvm.deserialization.ModuleMapping;
import org.jetbrains.kotlin.resolve.jvm.JvmCompilerDeserializationConfiguration;
import org.jetbrains.kotlin.utils.PathUtil;

/* JADX INFO: compiled from: PackagePartFromClassLoaderProvider.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u001d\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tR\u0014\u0010\n\u001a\u00020\u000bX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR \u0010\u000e\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00020\u00100\u000fX\u0094\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012¨\u0006\u0013"}, d2 = {"Lorg/jetbrains/kotlin/scripting/compiler/plugin/impl/PackagePartFromClassLoaderProvider;", "Lorg/jetbrains/kotlin/load/kotlin/JvmPackagePartProviderBase;", "", "classLoader", "Ljava/lang/ClassLoader;", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "messageCollector", "Lorg/jetbrains/kotlin/cli/common/messages/MessageCollector;", "(Ljava/lang/ClassLoader;Lorg/jetbrains/kotlin/config/LanguageVersionSettings;Lorg/jetbrains/kotlin/cli/common/messages/MessageCollector;)V", "deserializationConfiguration", "Lorg/jetbrains/kotlin/resolve/jvm/JvmCompilerDeserializationConfiguration;", "getDeserializationConfiguration", "()Lorg/jetbrains/kotlin/resolve/jvm/JvmCompilerDeserializationConfiguration;", "loadedModules", "", "Lorg/jetbrains/kotlin/load/kotlin/JvmPackagePartProviderBase$ModuleMappingInfo;", "getLoadedModules", "()Ljava/util/List;", PathUtil.KOTLIN_SCRIPTING_COMPILER_PLUGIN_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class PackagePartFromClassLoaderProvider extends JvmPackagePartProviderBase<String> {
    private final JvmCompilerDeserializationConfiguration deserializationConfiguration;
    private final List<JvmPackagePartProviderBase.ModuleMappingInfo<String>> loadedModules;

    public PackagePartFromClassLoaderProvider(ClassLoader classLoader, LanguageVersionSettings languageVersionSettings, final MessageCollector messageCollector) {
        Intrinsics.checkNotNullParameter(classLoader, "classLoader");
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        Intrinsics.checkNotNullParameter(messageCollector, "messageCollector");
        this.deserializationConfiguration = new JvmCompilerDeserializationConfiguration(languageVersionSettings);
        this.loadedModules = new SmartList();
        JvmClassLoaderUtilKt.forAllMatchingFiles(classLoader, "META-INF/*.kotlin_module", new String[0], new Function2<String, InputStream, Unit>() { // from class: org.jetbrains.kotlin.scripting.compiler.plugin.impl.PackagePartFromClassLoaderProvider.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public /* bridge */ /* synthetic */ Unit invoke(String str, InputStream inputStream) {
                invoke2(str, inputStream);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(String name2, final InputStream stream) {
                Intrinsics.checkNotNullParameter(name2, "name");
                Intrinsics.checkNotNullParameter(stream, "stream");
                ModuleMapping moduleMappingTryLoadModuleMapping = JvmPackagePartProviderKt.tryLoadModuleMapping(new Function0<byte[]>() { // from class: org.jetbrains.kotlin.scripting.compiler.plugin.impl.PackagePartFromClassLoaderProvider.1.1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    public final byte[] invoke() {
                        return ByteStreamsKt.readBytes(stream);
                    }
                }, name2, name2, PackagePartFromClassLoaderProvider.this.getDeserializationConfiguration(), messageCollector);
                if (moduleMappingTryLoadModuleMapping != null) {
                    PackagePartFromClassLoaderProvider.this.getLoadedModules().add(new JvmPackagePartProviderBase.ModuleMappingInfo<>(name2, moduleMappingTryLoadModuleMapping, StringsKt.removeSuffix(StringsKt.removePrefix(name2, (CharSequence) "META-INF/"), (CharSequence) ".kotlin_module")));
                }
            }
        });
    }

    @Override // org.jetbrains.kotlin.load.kotlin.JvmPackagePartProviderBase
    public JvmCompilerDeserializationConfiguration getDeserializationConfiguration() {
        return this.deserializationConfiguration;
    }

    @Override // org.jetbrains.kotlin.load.kotlin.JvmPackagePartProviderBase
    protected List<JvmPackagePartProviderBase.ModuleMappingInfo<String>> getLoadedModules() {
        return this.loadedModules;
    }
}

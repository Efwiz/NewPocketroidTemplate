package org.jetbrains.kotlin.cli.jvm.compiler;

import java.io.IOException;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.kotlin.cli.common.messages.MessageCollector;
import org.jetbrains.kotlin.cli.jvm.index.JavaRoot;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiTreeChangeEvent;
import org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope;
import org.jetbrains.kotlin.com.intellij.util.SmartList;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.load.kotlin.JvmPackagePartProviderBase;
import org.jetbrains.kotlin.metadata.jvm.deserialization.ModuleMapping;
import org.jetbrains.kotlin.resolve.jvm.JvmCompilerDeserializationConfiguration;

/* JADX INFO: compiled from: JvmPackagePartProvider.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0015\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\u001c\u0010\u0011\u001a\u00020\u00122\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00150\u00142\u0006\u0010\u0016\u001a\u00020\u0017R\u0014\u0010\b\u001a\u00020\tX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR \u0010\f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00020\u000e0\rX\u0094\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0018"}, d2 = {"Lorg/jetbrains/kotlin/cli/jvm/compiler/JvmPackagePartProvider;", "Lorg/jetbrains/kotlin/load/kotlin/JvmPackagePartProviderBase;", "Lorg/jetbrains/kotlin/com/intellij/openapi/vfs/VirtualFile;", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "scope", "Lorg/jetbrains/kotlin/com/intellij/psi/search/GlobalSearchScope;", "(Lorg/jetbrains/kotlin/config/LanguageVersionSettings;Lcom/intellij/psi/search/GlobalSearchScope;)V", "deserializationConfiguration", "Lorg/jetbrains/kotlin/resolve/jvm/JvmCompilerDeserializationConfiguration;", "getDeserializationConfiguration", "()Lorg/jetbrains/kotlin/resolve/jvm/JvmCompilerDeserializationConfiguration;", "loadedModules", "", "Lorg/jetbrains/kotlin/load/kotlin/JvmPackagePartProviderBase$ModuleMappingInfo;", "getLoadedModules", "()Ljava/util/List;", "addRoots", "", PsiTreeChangeEvent.PROP_ROOTS, "", "Lorg/jetbrains/kotlin/cli/jvm/index/JavaRoot;", "messageCollector", "Lorg/jetbrains/kotlin/cli/common/messages/MessageCollector;", "cli-base"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JvmPackagePartProvider extends JvmPackagePartProviderBase<VirtualFile> {
    private final JvmCompilerDeserializationConfiguration deserializationConfiguration;
    private final List<JvmPackagePartProviderBase.ModuleMappingInfo<VirtualFile>> loadedModules;
    private final GlobalSearchScope scope;

    public JvmPackagePartProvider(LanguageVersionSettings languageVersionSettings, GlobalSearchScope scope) {
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        Intrinsics.checkNotNullParameter(scope, "scope");
        this.scope = scope;
        this.deserializationConfiguration = new JvmCompilerDeserializationConfiguration(languageVersionSettings);
        this.loadedModules = new SmartList();
    }

    @Override // org.jetbrains.kotlin.load.kotlin.JvmPackagePartProviderBase
    public JvmCompilerDeserializationConfiguration getDeserializationConfiguration() {
        return this.deserializationConfiguration;
    }

    @Override // org.jetbrains.kotlin.load.kotlin.JvmPackagePartProviderBase
    protected List<JvmPackagePartProviderBase.ModuleMappingInfo<VirtualFile>> getLoadedModules() {
        return this.loadedModules;
    }

    public final void addRoots(List<JavaRoot> roots, MessageCollector messageCollector) {
        VirtualFile virtualFileFindChild;
        Intrinsics.checkNotNullParameter(roots, "roots");
        Intrinsics.checkNotNullParameter(messageCollector, "messageCollector");
        for (JavaRoot javaRoot : roots) {
            VirtualFile file = javaRoot.getFile();
            if (javaRoot.getType() == JavaRoot.RootType.BINARY && this.scope.contains(file) && (virtualFileFindChild = file.findChild("META-INF")) != null) {
                VirtualFile[] children = virtualFileFindChild.getChildren();
                Intrinsics.checkNotNullExpressionValue(children, "getChildren(...)");
                for (final VirtualFile virtualFile : children) {
                    String name2 = virtualFile.getName();
                    Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
                    if (StringsKt.endsWith$default(name2, "kotlin_module", false, 2, (Object) null)) {
                        Function0<byte[]> function0 = new Function0<byte[]>() { // from class: org.jetbrains.kotlin.cli.jvm.compiler.JvmPackagePartProvider.addRoots.1
                            {
                                super(0);
                            }

                            @Override // kotlin.jvm.functions.Function0
                            public final byte[] invoke() throws IOException {
                                byte[] bArrContentsToByteArray = virtualFile.contentsToByteArray();
                                Intrinsics.checkNotNullExpressionValue(bArrContentsToByteArray, "contentsToByteArray(...)");
                                return bArrContentsToByteArray;
                            }
                        };
                        String string = virtualFile.toString();
                        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
                        String path = virtualFile.getPath();
                        Intrinsics.checkNotNullExpressionValue(path, "getPath(...)");
                        ModuleMapping moduleMappingTryLoadModuleMapping = JvmPackagePartProviderKt.tryLoadModuleMapping(function0, string, path, getDeserializationConfiguration(), messageCollector);
                        if (moduleMappingTryLoadModuleMapping != null) {
                            List<JvmPackagePartProviderBase.ModuleMappingInfo<VirtualFile>> loadedModules = getLoadedModules();
                            String nameWithoutExtension = virtualFile.getNameWithoutExtension();
                            Intrinsics.checkNotNullExpressionValue(nameWithoutExtension, "getNameWithoutExtension(...)");
                            loadedModules.add(new JvmPackagePartProviderBase.ModuleMappingInfo<>(file, moduleMappingTryLoadModuleMapping, nameWithoutExtension));
                        }
                    }
                }
            }
        }
    }
}

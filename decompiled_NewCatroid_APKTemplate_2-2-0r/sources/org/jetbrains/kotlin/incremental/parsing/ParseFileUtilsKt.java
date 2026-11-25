package org.jetbrains.kotlin.incremental.parsing;

import java.io.File;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Deque;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.kotlin.cli.common.CLIConfigurationKeys;
import org.jetbrains.kotlin.cli.common.messages.MessageCollector;
import org.jetbrains.kotlin.cli.jvm.compiler.EnvironmentConfigFiles;
import org.jetbrains.kotlin.cli.jvm.compiler.KotlinCoreEnvironment;
import org.jetbrains.kotlin.cli.jvm.config.JvmContentRootsKt;
import org.jetbrains.kotlin.com.intellij.openapi.Disposable;
import org.jetbrains.kotlin.com.intellij.openapi.util.Disposer;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFileManager;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFileSystem;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.local.CoreLocalFileSystem;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiManager;
import org.jetbrains.kotlin.com.intellij.psi.SingleRootFileViewProvider;
import org.jetbrains.kotlin.config.CompilerConfiguration;
import org.jetbrains.kotlin.config.JVMConfigurationKeys;
import org.jetbrains.kotlin.idea.KotlinFileType;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.psi.KtClassOrObject;
import org.jetbrains.kotlin.psi.KtDeclaration;
import org.jetbrains.kotlin.psi.KtFile;

/* JADX INFO: compiled from: parseFileUtils.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00002\n\u0000\n\u0002\u0010\"\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u001a$\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u00012\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\u0006\u0010\u0006\u001a\u00020\u0007H\u0002\u001a\u001a\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u00012\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00050\u0001\u001a \u0010\t\u001a\u00020\n*\b\u0012\u0004\u0012\u00020\u000b0\u00042\f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000e0\rH\u0002¨\u0006\u000f"}, d2 = {"classesFqNames", "", "", "kotlinFiles", "", "Ljava/io/File;", "disposable", "Lorg/jetbrains/kotlin/com/intellij/openapi/Disposable;", "files", "filterClassesTo", "", "Lorg/jetbrains/kotlin/psi/KtDeclaration;", "classes", "Ljava/util/Deque;", "Lorg/jetbrains/kotlin/psi/KtClassOrObject;", "incremental-compilation-impl"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class ParseFileUtilsKt {
    public static final Set<String> classesFqNames(Set<? extends File> files) {
        Intrinsics.checkNotNullParameter(files, "files");
        ArrayList arrayList = new ArrayList();
        for (Object obj : files) {
            File file = (File) obj;
            String name2 = file.getName();
            Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
            if (StringsKt.endsWith(name2, KotlinFileType.DOT_DEFAULT_EXTENSION, true) && file.isFile()) {
                arrayList.add(obj);
            }
        }
        ArrayList arrayList2 = arrayList;
        if (arrayList2.isEmpty()) {
            return SetsKt.emptySet();
        }
        Disposable disposableNewDisposable = Disposer.newDisposable();
        Intrinsics.checkNotNullExpressionValue(disposableNewDisposable, "newDisposable(...)");
        try {
            return classesFqNames(arrayList2, disposableNewDisposable);
        } finally {
            Disposer.dispose(disposableNewDisposable);
        }
    }

    private static final Set<String> classesFqNames(Collection<? extends File> collection, Disposable disposable) {
        CompilerConfiguration compilerConfiguration = new CompilerConfiguration();
        compilerConfiguration.put(JVMConfigurationKeys.NO_JDK, true);
        compilerConfiguration.put(CLIConfigurationKeys.MESSAGE_COLLECTOR_KEY, MessageCollector.INSTANCE.getNONE());
        JvmContentRootsKt.configureJdkClasspathRoots(compilerConfiguration);
        PsiManager psiManager = PsiManager.getInstance(KotlinCoreEnvironment.INSTANCE.createForProduction(disposable, compilerConfiguration, EnvironmentConfigFiles.JVM_CONFIG_FILES).getProject());
        Intrinsics.checkNotNullExpressionValue(psiManager, "getInstance(...)");
        VirtualFileManager virtualFileManager = VirtualFileManager.getInstance();
        Intrinsics.checkNotNullExpressionValue(virtualFileManager, "getInstance(...)");
        VirtualFileSystem fileSystem = virtualFileManager.getFileSystem("file");
        Intrinsics.checkNotNull(fileSystem, "null cannot be cast to non-null type com.intellij.openapi.vfs.local.CoreLocalFileSystem");
        CoreLocalFileSystem coreLocalFileSystem = (CoreLocalFileSystem) fileSystem;
        HashSet hashSet = new HashSet();
        Iterator<? extends File> it = collection.iterator();
        while (it.hasNext()) {
            VirtualFile virtualFileFindFileByIoFile = coreLocalFileSystem.findFileByIoFile(it.next());
            Intrinsics.checkNotNull(virtualFileFindFileByIoFile);
            for (PsiFile psiFile : new SingleRootFileViewProvider(psiManager, virtualFileFindFileByIoFile).getAllFiles()) {
                if (psiFile instanceof KtFile) {
                    ArrayDeque arrayDeque = new ArrayDeque();
                    ArrayDeque arrayDeque2 = arrayDeque;
                    filterClassesTo(((KtFile) psiFile).getDeclarations(), arrayDeque2);
                    while (!arrayDeque.isEmpty()) {
                        KtClassOrObject ktClassOrObject = (KtClassOrObject) arrayDeque.pollFirst();
                        FqName fqName = ktClassOrObject.mo7391getFqName();
                        if (fqName != null) {
                            hashSet.add(fqName.asString());
                        }
                        filterClassesTo(ktClassOrObject.getDeclarations(), arrayDeque2);
                    }
                }
            }
        }
        return hashSet;
    }

    private static final void filterClassesTo(Collection<? extends KtDeclaration> collection, Deque<KtClassOrObject> deque) {
        for (Object obj : collection) {
            if (obj instanceof KtClassOrObject) {
                deque.add(obj);
            }
        }
    }
}

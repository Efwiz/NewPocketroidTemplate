package org.jetbrains.kotlin.cli.common;

import java.io.File;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import kotlin.Metadata;
import kotlin.io.FilesKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.eclipse.jdt.internal.compiler.util.SuffixConstants;
import org.jetbrains.kotlin.KtSourceFile;
import org.jetbrains.kotlin.KtVirtualFileSourceFile;
import org.jetbrains.kotlin.cli.common.config.ContentRootsKt;
import org.jetbrains.kotlin.cli.common.config.KotlinSourceRoot;
import org.jetbrains.kotlin.cli.common.messages.CompilerMessageLocation;
import org.jetbrains.kotlin.cli.common.messages.CompilerMessageSeverity;
import org.jetbrains.kotlin.cli.common.messages.MessageCollector;
import org.jetbrains.kotlin.cli.jvm.compiler.CoreEnvironmentUtilsKt;
import org.jetbrains.kotlin.cli.jvm.compiler.KotlinCoreEnvironment;
import org.jetbrains.kotlin.cli.jvm.compiler.VfsBasedProjectEnvironment;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFileManager;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFileSystem;
import org.jetbrains.kotlin.config.CommonConfigurationKeys;
import org.jetbrains.kotlin.config.CompilerConfiguration;
import org.jetbrains.kotlin.config.JVMConfigurationKeys;
import org.jetbrains.kotlin.extensions.PreprocessedFileCreator;
import org.jetbrains.kotlin.idea.KotlinFileType;

/* JADX INFO: compiled from: GroupedKtSources.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u00002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u001e\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r\u001a\u001e\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\f\u001a\u00020\r\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0082T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0002\u001a\u00020\u0001X\u0082T¢\u0006\u0002\n\u0000\"\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0010"}, d2 = {"javaFileExtensionWithDot", "", "kotlinFileExtensionWithDot", "ktSourceFileComparator", "Ljava/util/Comparator;", "Lorg/jetbrains/kotlin/KtSourceFile;", "collectSources", "Lorg/jetbrains/kotlin/cli/common/GroupedKtSources;", "compilerConfiguration", "Lorg/jetbrains/kotlin/config/CompilerConfiguration;", "project", "Lorg/jetbrains/kotlin/com/intellij/openapi/project/Project;", "messageCollector", "Lorg/jetbrains/kotlin/cli/common/messages/MessageCollector;", "projectEnvironment", "Lorg/jetbrains/kotlin/cli/jvm/compiler/VfsBasedProjectEnvironment;", "cli"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class GroupedKtSourcesKt {
    private static final Comparator<KtSourceFile> ktSourceFileComparator = new Comparator() { // from class: org.jetbrains.kotlin.cli.common.GroupedKtSourcesKt$$ExternalSyntheticLambda0
        @Override // java.util.Comparator
        public final int compare(Object obj, Object obj2) {
            return GroupedKtSourcesKt.ktSourceFileComparator$lambda$0((KtSourceFile) obj, (KtSourceFile) obj2);
        }
    };

    public static final GroupedKtSources collectSources(CompilerConfiguration compilerConfiguration, VfsBasedProjectEnvironment projectEnvironment, MessageCollector messageCollector) {
        Intrinsics.checkNotNullParameter(compilerConfiguration, "compilerConfiguration");
        Intrinsics.checkNotNullParameter(projectEnvironment, "projectEnvironment");
        Intrinsics.checkNotNullParameter(messageCollector, "messageCollector");
        return collectSources(compilerConfiguration, projectEnvironment.getProject(), messageCollector);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final int ktSourceFileComparator$lambda$0(KtSourceFile ktSourceFile, KtSourceFile ktSourceFile2) {
        String path = ktSourceFile.getPath();
        if (path == null) {
            throw new IllegalStateException("Expected a file with a well-defined path".toString());
        }
        String path2 = ktSourceFile2.getPath();
        if (path2 != null) {
            return path.compareTo(path2);
        }
        throw new IllegalStateException("Expected a file with a well-defined path".toString());
    }

    /* JADX WARN: Code duplicated, block: B:60:0x01b6  */
    public static final GroupedKtSources collectSources(CompilerConfiguration compilerConfiguration, Project project, MessageCollector messageCollector) {
        Iterator<File> it;
        CompilerMessageLocation compilerMessageLocation;
        CompilerConfiguration compilerConfiguration2 = compilerConfiguration;
        Project project2 = project;
        Intrinsics.checkNotNullParameter(compilerConfiguration2, "compilerConfiguration");
        Intrinsics.checkNotNullParameter(project2, "project");
        Intrinsics.checkNotNullParameter(messageCollector, "messageCollector");
        Comparator<KtSourceFile> comparator = ktSourceFileComparator;
        TreeSet treeSet = new TreeSet(comparator);
        TreeSet treeSet2 = new TreeSet(comparator);
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        boolean z = compilerConfiguration2.getBoolean(CommonConfigurationKeys.USE_FIR) && compilerConfiguration2.getBoolean(CommonConfigurationKeys.USE_LIGHT_TREE);
        List<KotlinSourceRoot> kotlinSourceRoots = ContentRootsKt.getKotlinSourceRoots(compilerConfiguration);
        VirtualFileSystem fileSystem = VirtualFileManager.getInstance().getFileSystem("file");
        HashSet hashSet = new HashSet();
        PreprocessedFileCreator preprocessedFileCreator = new PreprocessedFileCreator(project2);
        Iterator<KotlinSourceRoot> it2 = kotlinSourceRoots.iterator();
        boolean z2 = false;
        boolean z3 = false;
        while (it2.hasNext()) {
            KotlinSourceRoot next = it2.next();
            String path = next.getPath();
            boolean isCommon = next.getIsCommon();
            String hmppModuleName = next.getHmppModuleName();
            File file = new File(path);
            VirtualFile virtualFileFindFileByPath = fileSystem.findFileByPath(FilesKt.normalize(file).getPath());
            Iterator<KotlinSourceRoot> it3 = it2;
            if (virtualFileFindFileByPath == null) {
                String str = "Source file or directory not found: " + path;
                File file2 = (File) compilerConfiguration2.get(JVMConfigurationKeys.MODULE_XML_FILE);
                if (file2 == null || !Logger.isInitialized()) {
                    compilerMessageLocation = null;
                } else {
                    compilerMessageLocation = null;
                    Logger.getInstance((Class<?>) KotlinCoreEnvironment.class).warn(str + "\n\nbuild file path: " + file2 + "\ncontent:\n" + FilesKt.readText$default(file2, null, 1, null));
                }
                CoreEnvironmentUtilsKt.report(compilerConfiguration2, CompilerMessageSeverity.ERROR, str, compilerMessageLocation);
            } else {
                z2 = z2;
                if (!virtualFileFindFileByPath.isDirectory() && !Intrinsics.areEqual(virtualFileFindFileByPath.getExtension(), KotlinFileType.EXTENSION)) {
                    if (!z3) {
                        CoreEnvironmentUtilsKt.registerPluginsSuppliedExtensionsIfNeeded(virtualFileFindFileByPath, project2);
                        z3 = true;
                    }
                    if (!Intrinsics.areEqual(virtualFileFindFileByPath.getFileType(), KotlinFileType.INSTANCE)) {
                        CoreEnvironmentUtilsKt.report(compilerConfiguration2, CompilerMessageSeverity.ERROR, "Source entry is not a Kotlin file: " + path, null);
                    }
                    it2 = it3;
                }
                Iterator<File> it4 = FilesKt.walkTopDown(file).iterator();
                z2 = z2;
                while (it4.hasNext()) {
                    File next2 = it4.next();
                    if (next2.isFile()) {
                        File absoluteFile = next2.getAbsoluteFile();
                        Intrinsics.checkNotNullExpressionValue(absoluteFile, "getAbsoluteFile(...)");
                        VirtualFile virtualFileFindFileByPath2 = fileSystem.findFileByPath(FilesKt.normalize(absoluteFile).getPath());
                        VirtualFile virtualFileCreate = virtualFileFindFileByPath2 != null ? preprocessedFileCreator.create(virtualFileFindFileByPath2) : null;
                        if (virtualFileCreate == null || !hashSet.add(virtualFileCreate)) {
                            it = it4;
                        } else {
                            if (!z3) {
                                CoreEnvironmentUtilsKt.registerPluginsSuppliedExtensionsIfNeeded(virtualFileCreate, project2);
                                z3 = true;
                            }
                            KtVirtualFileSourceFile ktVirtualFileSourceFile = new KtVirtualFileSourceFile(virtualFileCreate);
                            it = it4;
                            if (!StringsKt.endsWith$default(ktVirtualFileSourceFile.getPath(), SuffixConstants.SUFFIX_STRING_java, false, 2, (Object) null)) {
                                if (StringsKt.endsWith$default(ktVirtualFileSourceFile.getPath(), KotlinFileType.DOT_DEFAULT_EXTENSION, false, 2, (Object) null) || !z) {
                                    if (isCommon) {
                                        treeSet2.add(ktVirtualFileSourceFile);
                                    } else {
                                        treeSet.add(ktVirtualFileSourceFile);
                                    }
                                    if (hmppModuleName != null) {
                                        Object obj = linkedHashMap.get(hmppModuleName);
                                        if (obj == null) {
                                            obj = (Set) new LinkedHashSet();
                                            linkedHashMap.put(hmppModuleName, obj);
                                        }
                                        ((Set) obj).add(ktVirtualFileSourceFile);
                                    }
                                } else {
                                    z2 = true;
                                }
                            }
                        }
                    } else {
                        it = it4;
                    }
                    project2 = project;
                    it4 = it;
                }
                compilerConfiguration2 = compilerConfiguration;
                project2 = project;
                it2 = it3;
            }
            z2 = z2;
            it2 = it3;
        }
        if (z2) {
            MessageCollector.report$default(messageCollector, CompilerMessageSeverity.STRONG_WARNING, "Scripts are not yet supported with K2 in LightTree mode, consider using K1 or disable LightTree mode with -Xuse-fir-lt=false", null, 4, null);
        }
        return new GroupedKtSources(treeSet, treeSet2, linkedHashMap);
    }
}

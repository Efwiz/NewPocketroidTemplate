package org.jetbrains.kotlin.cli.jvm.compiler;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import java.io.File;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.io.FilesKt;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.CLIConfigurationKeys;
import org.jetbrains.kotlin.cli.common.config.KotlinSourceRoot;
import org.jetbrains.kotlin.cli.common.messages.CompilerMessageLocation;
import org.jetbrains.kotlin.cli.common.messages.CompilerMessageSeverity;
import org.jetbrains.kotlin.cli.common.messages.MessageCollector;
import org.jetbrains.kotlin.com.intellij.ide.highlighter.JavaClassFileType;
import org.jetbrains.kotlin.com.intellij.ide.highlighter.JavaFileType;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFileManager;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFileSystem;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiManager;
import org.jetbrains.kotlin.config.CompilerConfiguration;
import org.jetbrains.kotlin.config.CompilerConfigurationKey;
import org.jetbrains.kotlin.config.JVMConfigurationKeys;
import org.jetbrains.kotlin.extensions.CompilerConfigurationExtension;
import org.jetbrains.kotlin.extensions.PreprocessedFileCreator;
import org.jetbrains.kotlin.idea.KotlinFileType;
import org.jetbrains.kotlin.modules.Module;
import org.jetbrains.kotlin.psi.KtFile;
import org.jetbrains.kotlin.resolve.multiplatform.IsCommonSourceKt;

/* JADX INFO: compiled from: coreEnvironmentUtils.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000h\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\u001a6\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u00062\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000e0\r2\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\u0010\u001a\u001c\u0010\u0011\u001a\u00020\t*\u00020\t2\u0006\u0010\u0012\u001a\u00020\u00132\b\u0010\u0014\u001a\u0004\u0018\u00010\u0015\u001ac\u0010\u0016\u001a\u00020\u0017*\b\u0012\u0004\u0012\u00020\u000e0\r2\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\u00102/\u0010\u0018\u001a+\u0012\u0004\u0012\u00020\u001a\u0012\u0004\u0012\u00020\u001b\u0012\u0015\u0012\u0013\u0018\u00010\u001c¢\u0006\f\b\u001d\u0012\b\b\u001e\u0012\u0004\b\b(\u001f\u0012\u0004\u0012\u00020\u00170\u0019H\u0086\bø\u0001\u0000\u001a\u0012\u0010 \u001a\u00020\u0017*\u00020\u001a2\u0006\u0010\n\u001a\u00020\u000b\u001a&\u0010!\u001a\u00020\u0017*\u00020\t2\u0006\u0010\"\u001a\u00020#2\u0006\u0010$\u001a\u00020\u001c2\n\b\u0002\u0010%\u001a\u0004\u0018\u00010\u0010\"\u0015\u0010\u0000\u001a\u00020\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006&"}, d2 = {"messageCollector", "Lorg/jetbrains/kotlin/cli/common/messages/MessageCollector;", "Lorg/jetbrains/kotlin/cli/jvm/compiler/KotlinCoreEnvironment;", "getMessageCollector", "(Lorg/jetbrains/kotlin/cli/jvm/compiler/KotlinCoreEnvironment;)Lorg/jetbrains/kotlin/cli/common/messages/MessageCollector;", "createSourceFilesFromSourceRoots", "", "Lorg/jetbrains/kotlin/psi/KtFile;", "configuration", "Lorg/jetbrains/kotlin/config/CompilerConfiguration;", "project", "Lorg/jetbrains/kotlin/com/intellij/openapi/project/Project;", "sourceRoots", "", "Lorg/jetbrains/kotlin/cli/common/config/KotlinSourceRoot;", "reportLocation", "Lorg/jetbrains/kotlin/cli/common/messages/CompilerMessageLocation;", "applyModuleProperties", "module", "Lorg/jetbrains/kotlin/modules/Module;", "buildFile", "Ljava/io/File;", "forAllFiles", "", "body", "Lkotlin/Function3;", "Lorg/jetbrains/kotlin/com/intellij/openapi/vfs/VirtualFile;", "", "", "Lkotlin/ParameterName;", "name", "moduleName", "registerPluginsSuppliedExtensionsIfNeeded", "report", "severity", "Lorg/jetbrains/kotlin/cli/common/messages/CompilerMessageSeverity;", CrashHianalyticsData.MESSAGE, FirebaseAnalytics.Param.LOCATION, "cli-base"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class CoreEnvironmentUtilsKt {
    public static /* synthetic */ void report$default(CompilerConfiguration compilerConfiguration, CompilerMessageSeverity compilerMessageSeverity, String str, CompilerMessageLocation compilerMessageLocation, int i, Object obj) {
        if ((i & 4) != 0) {
            compilerMessageLocation = null;
        }
        report(compilerConfiguration, compilerMessageSeverity, str, compilerMessageLocation);
    }

    public static final void report(CompilerConfiguration compilerConfiguration, CompilerMessageSeverity severity, String message, CompilerMessageLocation compilerMessageLocation) {
        Intrinsics.checkNotNullParameter(compilerConfiguration, "<this>");
        Intrinsics.checkNotNullParameter(severity, "severity");
        Intrinsics.checkNotNullParameter(message, "message");
        MessageCollector messageCollector = (MessageCollector) compilerConfiguration.get(CLIConfigurationKeys.MESSAGE_COLLECTOR_KEY);
        if (messageCollector != null) {
            messageCollector.report(severity, message, compilerMessageLocation);
        }
    }

    /* JADX WARN: Code duplicated, block: B:26:0x00f6 A[PHI: r9
      0x00f6: PHI (r9v3 boolean) = (r9v1 boolean), (r9v1 boolean), (r9v9 boolean) binds: [B:18:0x00c1, B:20:0x00cd, B:24:0x00df] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Code duplicated, block: B:29:0x0104  */
    /* JADX WARN: Code duplicated, block: B:33:0x0127  */
    /* JADX WARN: Code duplicated, block: B:34:0x012c  */
    /* JADX WARN: Code duplicated, block: B:50:0x0110 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:54:0x00fe A[SYNTHETIC] */
    public static /* synthetic */ void forAllFiles$default(List list, CompilerConfiguration configuration, Project project, CompilerMessageLocation compilerMessageLocation, Function3 body, int i, Object obj) {
        VirtualFile virtualFileFindFileByPath;
        VirtualFile virtualFileCreate;
        Charset charset = null;
        CompilerMessageLocation compilerMessageLocation2 = (i & 4) != 0 ? null : compilerMessageLocation;
        Intrinsics.checkNotNullParameter(list, "<this>");
        Intrinsics.checkNotNullParameter(configuration, "configuration");
        Intrinsics.checkNotNullParameter(project, "project");
        Intrinsics.checkNotNullParameter(body, "body");
        VirtualFileSystem fileSystem = VirtualFileManager.getInstance().getFileSystem("file");
        HashSet hashSet = new HashSet();
        PreprocessedFileCreator preprocessedFileCreator = new PreprocessedFileCreator(project);
        Iterator it = list.iterator();
        boolean z = false;
        while (it.hasNext()) {
            KotlinSourceRoot kotlinSourceRoot = (KotlinSourceRoot) it.next();
            String path = kotlinSourceRoot.getPath();
            boolean isCommon = kotlinSourceRoot.getIsCommon();
            String hmppModuleName = kotlinSourceRoot.getHmppModuleName();
            File file = new File(path);
            VirtualFile virtualFileFindFileByPath2 = fileSystem.findFileByPath(FilesKt.normalize(file).getPath());
            if (virtualFileFindFileByPath2 == null) {
                String str = "Source file or directory not found: " + path;
                File file2 = (File) configuration.get(JVMConfigurationKeys.MODULE_XML_FILE);
                if (file2 != null && Logger.isInitialized()) {
                    Logger.getInstance((Class<?>) KotlinCoreEnvironment.class).warn(str + "\n\nbuild file path: " + file2 + "\ncontent:\n" + FilesKt.readText$default(file2, charset, 1, charset));
                }
                report(configuration, CompilerMessageSeverity.ERROR, str, compilerMessageLocation2);
            } else {
                if (!virtualFileFindFileByPath2.isDirectory() && !Intrinsics.areEqual(virtualFileFindFileByPath2.getExtension(), KotlinFileType.EXTENSION)) {
                    if (!z) {
                        registerPluginsSuppliedExtensionsIfNeeded(virtualFileFindFileByPath2, project);
                        z = true;
                    }
                    if (!Intrinsics.areEqual(virtualFileFindFileByPath2.getFileType(), KotlinFileType.INSTANCE)) {
                        report(configuration, CompilerMessageSeverity.ERROR, "Source entry is not a Kotlin file: " + path, compilerMessageLocation2);
                    } else {
                        for (File file3 : FilesKt.walkTopDown(file)) {
                            if (file3.isFile()) {
                                File absoluteFile = file3.getAbsoluteFile();
                                Intrinsics.checkNotNullExpressionValue(absoluteFile, "getAbsoluteFile(...)");
                                virtualFileFindFileByPath = fileSystem.findFileByPath(FilesKt.normalize(absoluteFile).getPath());
                                if (virtualFileFindFileByPath != null) {
                                    virtualFileCreate = preprocessedFileCreator.create(virtualFileFindFileByPath);
                                } else {
                                    virtualFileCreate = null;
                                }
                                if (virtualFileCreate == null) {
                                }
                            }
                        }
                    }
                } else {
                    while (r4.hasNext()) {
                        if (file3.isFile()) {
                            File absoluteFile2 = file3.getAbsoluteFile();
                            Intrinsics.checkNotNullExpressionValue(absoluteFile2, "getAbsoluteFile(...)");
                            virtualFileFindFileByPath = fileSystem.findFileByPath(FilesKt.normalize(absoluteFile2).getPath());
                            if (virtualFileFindFileByPath != null) {
                                virtualFileCreate = preprocessedFileCreator.create(virtualFileFindFileByPath);
                            } else {
                                virtualFileCreate = null;
                            }
                            if (virtualFileCreate == null && hashSet.add(virtualFileCreate)) {
                                if (!z) {
                                    registerPluginsSuppliedExtensionsIfNeeded(virtualFileCreate, project);
                                    z = true;
                                }
                                body.invoke(virtualFileCreate, Boolean.valueOf(isCommon), hmppModuleName);
                            }
                        }
                    }
                }
                charset = null;
            }
        }
    }

    public static final void forAllFiles(List<KotlinSourceRoot> list, CompilerConfiguration configuration, Project project, CompilerMessageLocation compilerMessageLocation, Function3<? super VirtualFile, ? super Boolean, ? super String, Unit> body) {
        Intrinsics.checkNotNullParameter(list, "<this>");
        Intrinsics.checkNotNullParameter(configuration, "configuration");
        Intrinsics.checkNotNullParameter(project, "project");
        Intrinsics.checkNotNullParameter(body, "body");
        VirtualFileSystem fileSystem = VirtualFileManager.getInstance().getFileSystem("file");
        HashSet hashSet = new HashSet();
        PreprocessedFileCreator preprocessedFileCreator = new PreprocessedFileCreator(project);
        boolean z = false;
        for (KotlinSourceRoot kotlinSourceRoot : list) {
            String path = kotlinSourceRoot.getPath();
            boolean isCommon = kotlinSourceRoot.getIsCommon();
            String hmppModuleName = kotlinSourceRoot.getHmppModuleName();
            File file = new File(path);
            VirtualFile virtualFileFindFileByPath = fileSystem.findFileByPath(FilesKt.normalize(file).getPath());
            if (virtualFileFindFileByPath == null) {
                String str = "Source file or directory not found: " + path;
                File file2 = (File) configuration.get(JVMConfigurationKeys.MODULE_XML_FILE);
                if (file2 != null && Logger.isInitialized()) {
                    Logger.getInstance((Class<?>) KotlinCoreEnvironment.class).warn(str + "\n\nbuild file path: " + file2 + "\ncontent:\n" + FilesKt.readText$default(file2, null, 1, null));
                }
                report(configuration, CompilerMessageSeverity.ERROR, str, compilerMessageLocation);
            } else {
                if (!virtualFileFindFileByPath.isDirectory() && !Intrinsics.areEqual(virtualFileFindFileByPath.getExtension(), KotlinFileType.EXTENSION)) {
                    if (!z) {
                        registerPluginsSuppliedExtensionsIfNeeded(virtualFileFindFileByPath, project);
                        z = true;
                    }
                    if (!Intrinsics.areEqual(virtualFileFindFileByPath.getFileType(), KotlinFileType.INSTANCE)) {
                        report(configuration, CompilerMessageSeverity.ERROR, "Source entry is not a Kotlin file: " + path, compilerMessageLocation);
                    }
                }
                for (File file3 : FilesKt.walkTopDown(file)) {
                    if (file3.isFile()) {
                        File absoluteFile = file3.getAbsoluteFile();
                        Intrinsics.checkNotNullExpressionValue(absoluteFile, "getAbsoluteFile(...)");
                        VirtualFile virtualFileFindFileByPath2 = fileSystem.findFileByPath(FilesKt.normalize(absoluteFile).getPath());
                        VirtualFile virtualFileCreate = virtualFileFindFileByPath2 != null ? preprocessedFileCreator.create(virtualFileFindFileByPath2) : null;
                        if (virtualFileCreate != null && hashSet.add(virtualFileCreate)) {
                            if (!z) {
                                registerPluginsSuppliedExtensionsIfNeeded(virtualFileCreate, project);
                                z = true;
                            }
                            body.invoke(virtualFileCreate, Boolean.valueOf(isCommon), hmppModuleName);
                        }
                    }
                }
            }
        }
    }

    public static final void registerPluginsSuppliedExtensionsIfNeeded(VirtualFile virtualFile, Project project) {
        Intrinsics.checkNotNullParameter(virtualFile, "<this>");
        Intrinsics.checkNotNullParameter(project, "project");
        if (virtualFile.getExtension() == null || Intrinsics.areEqual(virtualFile.getExtension(), KotlinFileType.EXTENSION) || Intrinsics.areEqual(virtualFile.getExtension(), JavaFileType.INSTANCE.getDefaultExtension()) || Intrinsics.areEqual(virtualFile.getExtension(), JavaClassFileType.INSTANCE.getDefaultExtension()) || Intrinsics.areEqual(virtualFile.getFileType(), KotlinFileType.INSTANCE)) {
            return;
        }
        Iterator<CompilerConfigurationExtension> it = CompilerConfigurationExtension.INSTANCE.getInstances(project).iterator();
        while (it.hasNext()) {
            it.next().updateFileRegistry();
        }
    }

    public static /* synthetic */ List createSourceFilesFromSourceRoots$default(CompilerConfiguration compilerConfiguration, Project project, List list, CompilerMessageLocation compilerMessageLocation, int i, Object obj) {
        if ((i & 8) != 0) {
            compilerMessageLocation = null;
        }
        return createSourceFilesFromSourceRoots(compilerConfiguration, project, list, compilerMessageLocation);
    }

    public static final List<KtFile> createSourceFilesFromSourceRoots(CompilerConfiguration configuration, Project project, List<KotlinSourceRoot> sourceRoots, CompilerMessageLocation compilerMessageLocation) {
        Iterator<KotlinSourceRoot> it;
        VirtualFile virtualFileCreate;
        Intrinsics.checkNotNullParameter(configuration, "configuration");
        Intrinsics.checkNotNullParameter(project, "project");
        Intrinsics.checkNotNullParameter(sourceRoots, "sourceRoots");
        PsiManager psiManager = PsiManager.getInstance(project);
        Intrinsics.checkNotNullExpressionValue(psiManager, "getInstance(...)");
        ArrayList arrayList = new ArrayList();
        VirtualFileSystem fileSystem = VirtualFileManager.getInstance().getFileSystem("file");
        HashSet hashSet = new HashSet();
        PreprocessedFileCreator preprocessedFileCreator = new PreprocessedFileCreator(project);
        Iterator<KotlinSourceRoot> it2 = sourceRoots.iterator();
        boolean z = false;
        while (it2.hasNext()) {
            KotlinSourceRoot next = it2.next();
            String path = next.getPath();
            boolean isCommon = next.getIsCommon();
            String hmppModuleName = next.getHmppModuleName();
            File file = new File(path);
            VirtualFile virtualFileFindFileByPath = fileSystem.findFileByPath(FilesKt.normalize(file).getPath());
            if (virtualFileFindFileByPath == null) {
                String str = "Source file or directory not found: " + path;
                File file2 = (File) configuration.get(JVMConfigurationKeys.MODULE_XML_FILE);
                if (file2 != null && Logger.isInitialized()) {
                    Logger.getInstance((Class<?>) KotlinCoreEnvironment.class).warn(str + "\n\nbuild file path: " + file2 + "\ncontent:\n" + FilesKt.readText$default(file2, null, 1, null));
                }
                report(configuration, CompilerMessageSeverity.ERROR, str, compilerMessageLocation);
            } else {
                if (virtualFileFindFileByPath.isDirectory()) {
                    it = it2;
                } else {
                    it = it2;
                    if (!Intrinsics.areEqual(virtualFileFindFileByPath.getExtension(), KotlinFileType.EXTENSION)) {
                        if (!z) {
                            registerPluginsSuppliedExtensionsIfNeeded(virtualFileFindFileByPath, project);
                            z = true;
                        }
                        if (!Intrinsics.areEqual(virtualFileFindFileByPath.getFileType(), KotlinFileType.INSTANCE)) {
                            report(configuration, CompilerMessageSeverity.ERROR, "Source entry is not a Kotlin file: " + path, compilerMessageLocation);
                        }
                    }
                    it2 = it;
                }
                for (File file3 : FilesKt.walkTopDown(file)) {
                    if (file3.isFile()) {
                        File absoluteFile = file3.getAbsoluteFile();
                        Intrinsics.checkNotNullExpressionValue(absoluteFile, "getAbsoluteFile(...)");
                        VirtualFile virtualFileFindFileByPath2 = fileSystem.findFileByPath(FilesKt.normalize(absoluteFile).getPath());
                        if (virtualFileFindFileByPath2 != null) {
                            Intrinsics.checkNotNull(virtualFileFindFileByPath2);
                            virtualFileCreate = preprocessedFileCreator.create(virtualFileFindFileByPath2);
                        } else {
                            virtualFileCreate = null;
                        }
                        if (virtualFileCreate != null && hashSet.add(virtualFileCreate)) {
                            if (!z) {
                                registerPluginsSuppliedExtensionsIfNeeded(virtualFileCreate, project);
                                z = true;
                            }
                            PsiFile psiFileFindFile = psiManager.findFile(virtualFileCreate);
                            if (psiFileFindFile != null && (psiFileFindFile instanceof KtFile)) {
                                KtFile ktFile = (KtFile) psiFileFindFile;
                                IsCommonSourceKt.setCommonSource(ktFile, Boolean.valueOf(isCommon));
                                if (hmppModuleName != null) {
                                    IsCommonSourceKt.setHmppModuleName(ktFile, hmppModuleName);
                                }
                                arrayList.add(psiFileFindFile);
                            }
                        }
                    }
                }
                it2 = it;
            }
        }
        return arrayList;
    }

    public static final MessageCollector getMessageCollector(KotlinCoreEnvironment kotlinCoreEnvironment) {
        Intrinsics.checkNotNullParameter(kotlinCoreEnvironment, "<this>");
        Object notNull = kotlinCoreEnvironment.getConfiguration().getNotNull(CLIConfigurationKeys.MESSAGE_COLLECTOR_KEY);
        Intrinsics.checkNotNullExpressionValue(notNull, "getNotNull(...)");
        return (MessageCollector) notNull;
    }

    public static final CompilerConfiguration applyModuleProperties(CompilerConfiguration compilerConfiguration, Module module, File file) {
        Intrinsics.checkNotNullParameter(compilerConfiguration, "<this>");
        Intrinsics.checkNotNullParameter(module, "module");
        CompilerConfiguration compilerConfigurationCopy = compilerConfiguration.copy();
        if (file != null) {
            CompilerConfigurationKey<File> OUTPUT_DIRECTORY = JVMConfigurationKeys.OUTPUT_DIRECTORY;
            Intrinsics.checkNotNullExpressionValue(OUTPUT_DIRECTORY, "OUTPUT_DIRECTORY");
            compilerConfigurationCopy.get(OUTPUT_DIRECTORY);
            CompilerConfigurationKey<File> OUTPUT_JAR = JVMConfigurationKeys.OUTPUT_JAR;
            Intrinsics.checkNotNullExpressionValue(OUTPUT_JAR, "OUTPUT_JAR");
            compilerConfigurationCopy.get(OUTPUT_JAR);
            compilerConfigurationCopy.put(JVMConfigurationKeys.OUTPUT_DIRECTORY, new File(module.getOutputDir()));
        }
        Intrinsics.checkNotNullExpressionValue(compilerConfigurationCopy, "apply(...)");
        return compilerConfigurationCopy;
    }
}

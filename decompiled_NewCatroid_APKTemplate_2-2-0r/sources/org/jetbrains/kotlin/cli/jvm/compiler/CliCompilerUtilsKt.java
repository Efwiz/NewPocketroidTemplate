package org.jetbrains.kotlin.cli.jvm.compiler;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.io.CloseableKt;
import kotlin.io.FilesKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.SequencesKt;
import kotlin.text.StringsKt;
import org.eclipse.jdt.internal.compiler.util.SuffixConstants;
import org.jetbrains.kotlin.backend.common.output.OutputFile;
import org.jetbrains.kotlin.backend.common.output.OutputFileCollection;
import org.jetbrains.kotlin.backend.common.output.SimpleOutputFileCollection;
import org.jetbrains.kotlin.cli.common.CLIConfigurationKeys;
import org.jetbrains.kotlin.cli.common.arguments.K2JVMCompilerArguments;
import org.jetbrains.kotlin.cli.common.messages.CompilerMessageSeverity;
import org.jetbrains.kotlin.cli.common.messages.MessageCollector;
import org.jetbrains.kotlin.cli.common.messages.OutputMessageUtil;
import org.jetbrains.kotlin.cli.common.modules.ModuleBuilder;
import org.jetbrains.kotlin.cli.common.output.OutputUtilsKt;
import org.jetbrains.kotlin.cli.jvm.config.JvmContentRootsKt;
import org.jetbrains.kotlin.codegen.state.GenerationState;
import org.jetbrains.kotlin.codegen.state.GenerationStateEventCallback;
import org.jetbrains.kotlin.codegen.state.GenerationStateKt;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VfsUtilCore;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFileSystem;
import org.jetbrains.kotlin.config.CommonConfigurationKeys;
import org.jetbrains.kotlin.config.CompilerConfiguration;
import org.jetbrains.kotlin.config.JVMConfigurationKeys;
import org.jetbrains.kotlin.fir.BinaryModuleData;
import org.jetbrains.kotlin.fir.DependencyListForCliModule;
import org.jetbrains.kotlin.fir.session.IncrementalCompilationContext;
import org.jetbrains.kotlin.fir.session.environment.AbstractProjectEnvironment;
import org.jetbrains.kotlin.fir.session.environment.AbstractProjectFileSearchScope;
import org.jetbrains.kotlin.javac.JavacWrapper;
import org.jetbrains.kotlin.load.kotlin.PackagePartProvider;
import org.jetbrains.kotlin.load.kotlin.incremental.IncrementalPackagePartProvider;
import org.jetbrains.kotlin.load.kotlin.incremental.components.IncrementalCompilationComponents;
import org.jetbrains.kotlin.modules.JavaRootPath;
import org.jetbrains.kotlin.modules.Module;
import org.jetbrains.kotlin.modules.TargetId;
import org.jetbrains.kotlin.modules.TargetIdKt;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.platform.jvm.JvmPlatforms;
import org.jetbrains.kotlin.progress.ProgressIndicatorAndCompilationCanceledStatus;
import org.jetbrains.kotlin.psi.KtFile;
import org.jetbrains.kotlin.resolve.jvm.platform.JvmPlatformAnalyzerServices;

/* JADX INFO: compiled from: cliCompilerUtils.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u0098\u0001\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a:\u0010\u0000\u001a\u0004\u0018\u00010\u00012\u0006\u0010\u0002\u001a\u00020\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u000e\u0010\b\u001a\n\u0012\u0004\u0012\u00020\n\u0018\u00010\t2\u0006\u0010\u000b\u001a\u00020\f\u001a$\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00072\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00100\t\u001a\u000e\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0011\u001a\u00020\u0007\u001a$\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00100\t2\b\u0010\u0016\u001a\u0004\u0018\u00010\u00172\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00100\t\u001a \u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u0011\u001a\u00020\u00072\u0006\u0010\u001b\u001a\u00020\u001c2\b\u0010\u001d\u001a\u0004\u0018\u00010\u001e\u001a>\u0010\u001f\u001a\u00020 2\b\u0010!\u001a\u0004\u0018\u00010\"2\u0006\u0010#\u001a\u00020\u00072\f\u0010$\u001a\b\u0012\u0004\u0012\u00020%0\t2\f\u0010&\u001a\b\u0012\u0004\u0012\u00020'0\t2\b\u0010\u001d\u001a\u0004\u0018\u00010\u001e\u001a\u0012\u0010(\u001a\u00020\u001a*\u00020)2\u0006\u0010*\u001a\u00020+\u001a:\u0010,\u001a\b\u0012\u0004\u0012\u00020-0\t*\u00020%2\f\u0010.\u001a\b\u0012\u0004\u0012\u00020-0\t2\b\u0010/\u001a\u0004\u0018\u0001002\u0006\u00101\u001a\u00020 2\b\u0010\u0016\u001a\u0004\u0018\u00010\u0017\u001a\u0014\u00102\u001a\u000203*\u0002032\b\u00104\u001a\u0004\u0018\u00010%¨\u00065"}, d2 = {"createContextForIncrementalCompilation", "Lorg/jetbrains/kotlin/fir/session/IncrementalCompilationContext;", "projectEnvironment", "Lorg/jetbrains/kotlin/fir/session/environment/AbstractProjectEnvironment;", "incrementalComponents", "Lorg/jetbrains/kotlin/load/kotlin/incremental/components/IncrementalCompilationComponents;", "moduleConfiguration", "Lorg/jetbrains/kotlin/config/CompilerConfiguration;", "targetIds", "", "Lorg/jetbrains/kotlin/modules/TargetId;", "sourceScope", "Lorg/jetbrains/kotlin/fir/session/environment/AbstractProjectFileSearchScope;", "createLibraryListForJvm", "Lorg/jetbrains/kotlin/fir/DependencyListForCliModule;", "moduleName", "", "configuration", "friendPaths", "createOutputFilesFlushingCallbackIfPossible", "Lorg/jetbrains/kotlin/codegen/state/GenerationStateEventCallback;", "getBuildFilePaths", "buildFile", "Ljava/io/File;", "sourceFilePaths", "writeOutput", "", "outputFiles", "Lorg/jetbrains/kotlin/backend/common/output/OutputFileCollection;", "mainClassFqName", "Lorg/jetbrains/kotlin/name/FqName;", "writeOutputsIfNeeded", "", "project", "Lorg/jetbrains/kotlin/com/intellij/openapi/project/Project;", "projectConfiguration", "chunk", "Lorg/jetbrains/kotlin/modules/Module;", "outputs", "Lorg/jetbrains/kotlin/codegen/state/GenerationState;", "configureFromArgs", "Lorg/jetbrains/kotlin/cli/common/modules/ModuleBuilder;", "args", "Lorg/jetbrains/kotlin/cli/common/arguments/K2JVMCompilerArguments;", "getSourceFiles", "Lorg/jetbrains/kotlin/psi/KtFile;", "allSourceFiles", "localFileSystem", "Lorg/jetbrains/kotlin/com/intellij/openapi/vfs/VirtualFileSystem;", "multiModuleChunk", "withModule", "Lorg/jetbrains/kotlin/codegen/state/GenerationState$Builder;", "module", "cli"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class CliCompilerUtilsKt {
    /* JADX WARN: Multi-variable type inference failed */
    public static final List<KtFile> getSourceFiles(Module module, List<? extends KtFile> allSourceFiles, VirtualFileSystem virtualFileSystem, boolean z, File file) {
        Intrinsics.checkNotNullParameter(module, "<this>");
        Intrinsics.checkNotNullParameter(allSourceFiles, "allSourceFiles");
        if (!z) {
            return allSourceFiles;
        }
        List<String> buildFilePaths = getBuildFilePaths(file, module.getSourceFiles());
        Intrinsics.checkNotNull(virtualFileSystem);
        ArrayList arrayList = new ArrayList();
        Iterator<T> it = buildFilePaths.iterator();
        while (it.hasNext()) {
            VirtualFile virtualFileFindFileByPath = virtualFileSystem.findFileByPath((String) it.next());
            if (virtualFileFindFileByPath != null) {
                arrayList.add(virtualFileFindFileByPath);
            }
        }
        ArrayList arrayList2 = new ArrayList();
        ArrayList arrayList3 = new ArrayList();
        for (Object obj : arrayList) {
            if (((VirtualFile) obj).isDirectory()) {
                arrayList2.add(obj);
            } else {
                arrayList3.add(obj);
            }
        }
        Pair pair = new Pair(arrayList2, arrayList3);
        List list = (List) pair.component1();
        List list2 = (List) pair.component2();
        ArrayList arrayList4 = new ArrayList();
        for (Object obj2 : allSourceFiles) {
            VirtualFile virtualFile = ((KtFile) obj2).getVirtualFile();
            if (!list2.contains(virtualFile)) {
                List list3 = list;
                if (!(list3 instanceof Collection) || !list3.isEmpty()) {
                    Iterator it2 = list3.iterator();
                    while (it2.hasNext()) {
                        if (VfsUtilCore.isAncestor((VirtualFile) it2.next(), virtualFile, true)) {
                        }
                    }
                }
            }
            arrayList4.add(obj2);
        }
        return arrayList4;
    }

    public static final List<String> getBuildFilePaths(File file, List<String> sourceFilePaths) {
        Intrinsics.checkNotNullParameter(sourceFilePaths, "sourceFilePaths");
        if (file == null) {
            return sourceFilePaths;
        }
        List<String> list = sourceFilePaths;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        for (String str : list) {
            File file2 = new File(str);
            if (!file2.isAbsolute()) {
                file2 = null;
            }
            if (file2 == null) {
                file2 = FilesKt.resolveSibling(file, str);
            }
            arrayList.add(file2.getAbsolutePath());
        }
        return arrayList;
    }

    public static final GenerationState.Builder withModule(GenerationState.Builder builder, Module module) {
        Intrinsics.checkNotNullParameter(builder, "<this>");
        if (module != null) {
            builder.targetId(TargetIdKt.TargetId(module));
            builder.moduleName(module.getName());
            builder.outDirectory(new File(module.getOutputDir()));
        }
        return builder;
    }

    public static final GenerationStateEventCallback createOutputFilesFlushingCallbackIfPossible(final CompilerConfiguration configuration) {
        Intrinsics.checkNotNullParameter(configuration, "configuration");
        if (configuration.get(JVMConfigurationKeys.OUTPUT_DIRECTORY) == null) {
            return GenerationStateEventCallback.INSTANCE.getDO_NOTHING();
        }
        return GenerationStateKt.GenerationStateEventCallback(new Function1<GenerationState, Unit>() { // from class: org.jetbrains.kotlin.cli.jvm.compiler.CliCompilerUtilsKt.createOutputFilesFlushingCallbackIfPossible.1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(GenerationState generationState) throws Throwable {
                invoke2(generationState);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(GenerationState state) throws Throwable {
                Intrinsics.checkNotNullParameter(state, "state");
                List<OutputFile> currentOutput = state.getFactory().getCurrentOutput();
                Intrinsics.checkNotNullExpressionValue(currentOutput, "getCurrentOutput(...)");
                CliCompilerUtilsKt.writeOutput(configuration, new SimpleOutputFileCollection(currentOutput), null);
                if (((Boolean) configuration.get(JVMConfigurationKeys.RETAIN_OUTPUT_IN_MEMORY, false)).booleanValue()) {
                    return;
                }
                state.getFactory().releaseGeneratedOutput();
            }
        });
    }

    /* JADX WARN: Code duplicated, block: B:20:0x00d8  */
    public static final void writeOutput(CompilerConfiguration configuration, OutputFileCollection outputFiles, FqName fqName) throws Throwable {
        Intrinsics.checkNotNullParameter(configuration, "configuration");
        Intrinsics.checkNotNullParameter(outputFiles, "outputFiles");
        boolean z = configuration.getBoolean(CommonConfigurationKeys.REPORT_OUTPUT_FILES);
        File file = (File) configuration.get(JVMConfigurationKeys.OUTPUT_JAR);
        Object obj = configuration.get(CLIConfigurationKeys.MESSAGE_COLLECTOR_KEY, MessageCollector.INSTANCE.getNONE());
        Intrinsics.checkNotNullExpressionValue(obj, "get(...)");
        MessageCollector messageCollector = (MessageCollector) obj;
        if (file != null) {
            Object obj2 = configuration.get(JVMConfigurationKeys.INCLUDE_RUNTIME, false);
            Intrinsics.checkNotNullExpressionValue(obj2, "get(...)");
            boolean zBooleanValue = ((Boolean) obj2).booleanValue();
            Object obj3 = configuration.get(JVMConfigurationKeys.NO_REFLECT, false);
            Intrinsics.checkNotNullExpressionValue(obj3, "get(...)");
            CompileEnvironmentUtil.writeToJar(file, zBooleanValue, ((Boolean) obj3).booleanValue(), !((Boolean) configuration.get(JVMConfigurationKeys.NO_RESET_JAR_TIMESTAMPS, false)).booleanValue(), fqName, outputFiles, messageCollector);
            if (z) {
                List<OutputFile> listAsList = outputFiles.asList();
                ArrayList arrayList = new ArrayList();
                Iterator<T> it = listAsList.iterator();
                while (it.hasNext()) {
                    CollectionsKt.addAll(arrayList, ((OutputFile) it.next()).getSourceFiles());
                }
                String outputMessage = OutputMessageUtil.formatOutputMessage(CollectionsKt.distinct(arrayList), file);
                Intrinsics.checkNotNullExpressionValue(outputMessage, "formatOutputMessage(...)");
                MessageCollector.report$default(messageCollector, CompilerMessageSeverity.OUTPUT, outputMessage, null, 4, null);
                return;
            }
            return;
        }
        File file2 = (File) configuration.get(JVMConfigurationKeys.OUTPUT_DIRECTORY);
        if (file2 != null) {
            String path = file2.getPath();
            Intrinsics.checkNotNullExpressionValue(path, "getPath(...)");
            if (StringsKt.isBlank(path)) {
                file2 = null;
            }
            if (file2 == null) {
                file2 = new File(".");
            }
        } else {
            file2 = new File(".");
        }
        OutputUtilsKt.writeAll(outputFiles, file2, messageCollector, z);
    }

    public static final boolean writeOutputsIfNeeded(Project project, CompilerConfiguration projectConfiguration, List<? extends Module> chunk, List<GenerationState> outputs, FqName fqName) {
        Intrinsics.checkNotNullParameter(projectConfiguration, "projectConfiguration");
        Intrinsics.checkNotNullParameter(chunk, "chunk");
        Intrinsics.checkNotNullParameter(outputs, "outputs");
        if (((MessageCollector) projectConfiguration.getNotNull(CLIConfigurationKeys.MESSAGE_COLLECTOR_KEY)).getHasErrors()) {
            return false;
        }
        try {
            for (GenerationState generationState : outputs) {
                ProgressIndicatorAndCompilationCanceledStatus.checkCanceled();
                writeOutput(generationState.getConfiguration(), generationState.getFactory(), fqName);
            }
            Iterator<T> it = outputs.iterator();
            while (it.hasNext()) {
                ((GenerationState) it.next()).destroy();
            }
            if (!projectConfiguration.getBoolean(JVMConfigurationKeys.COMPILE_JAVA)) {
                return true;
            }
            Module module = (Module) CollectionsKt.singleOrNull((List) chunk);
            if (module == null) {
                Object notNull = projectConfiguration.getNotNull(CLIConfigurationKeys.MESSAGE_COLLECTOR_KEY);
                Intrinsics.checkNotNullExpressionValue(notNull, "getNotNull(...)");
                MessageCollector.report$default((MessageCollector) notNull, CompilerMessageSeverity.WARNING, "A chunk contains multiple modules (" + CollectionsKt.joinToString$default(chunk, null, null, null, 0, null, new Function1<Module, CharSequence>() { // from class: org.jetbrains.kotlin.cli.jvm.compiler.CliCompilerUtilsKt.writeOutputsIfNeeded.3
                    @Override // kotlin.jvm.functions.Function1
                    public final CharSequence invoke(Module it2) {
                        Intrinsics.checkNotNullParameter(it2, "it");
                        return it2.getName();
                    }
                }, 31, null) + "). -Xuse-javac option couldn't be used to compile java files", null, 4, null);
                return true;
            }
            JavacWrapper.Companion companion = JavacWrapper.INSTANCE;
            Intrinsics.checkNotNull(project);
            JavacWrapper companion2 = companion.getInstance(project);
            try {
                boolean zCompile = companion2.compile(new File(module.getOutputDir()));
                CloseableKt.closeFinally(companion2, null);
                return zCompile;
            } catch (Throwable th) {
                try {
                    throw th;
                } catch (Throwable th2) {
                    CloseableKt.closeFinally(companion2, th);
                    throw th2;
                }
            }
        } catch (Throwable th3) {
            Iterator<T> it2 = outputs.iterator();
            while (it2.hasNext()) {
                ((GenerationState) it2.next()).destroy();
            }
            throw th3;
        }
    }

    public static final void configureFromArgs(ModuleBuilder moduleBuilder, K2JVMCompilerArguments args) {
        Intrinsics.checkNotNullParameter(moduleBuilder, "<this>");
        Intrinsics.checkNotNullParameter(args, "args");
        String[] friendPaths = args.getFriendPaths();
        if (friendPaths != null) {
            for (String str : friendPaths) {
                moduleBuilder.addFriendDir(str);
            }
        }
        String classpath = args.getClasspath();
        if (classpath != null) {
            String str2 = classpath;
            String pathSeparator = File.pathSeparator;
            Intrinsics.checkNotNullExpressionValue(pathSeparator, "pathSeparator");
            List listSplit$default = StringsKt.split$default((CharSequence) str2, new String[]{pathSeparator}, false, 0, 6, (Object) null);
            if (listSplit$default != null) {
                Iterator it = listSplit$default.iterator();
                while (it.hasNext()) {
                    moduleBuilder.addClasspathEntry((String) it.next());
                }
            }
        }
        String[] javaSourceRoots = args.getJavaSourceRoots();
        if (javaSourceRoots != null) {
            for (String str3 : javaSourceRoots) {
                moduleBuilder.addJavaSourceRoot(new JavaRootPath(str3, args.getJavaPackagePrefix()));
            }
        }
        String[] commonSources = args.getCommonSources();
        Set set = commonSources != null ? ArraysKt.toSet(commonSources) : null;
        if (set == null) {
            set = SetsKt.emptySet();
        }
        for (String str4 : args.getFreeArgs()) {
            if (StringsKt.endsWith$default(str4, SuffixConstants.SUFFIX_STRING_java, false, 2, (Object) null)) {
                moduleBuilder.addJavaSourceRoot(new JavaRootPath(str4, args.getJavaPackagePrefix()));
            } else {
                moduleBuilder.addSourceFiles(str4);
                if (set.contains(str4)) {
                    moduleBuilder.addCommonSourceFiles(str4);
                }
                if (new File(str4).isDirectory()) {
                    moduleBuilder.addJavaSourceRoot(new JavaRootPath(str4, args.getJavaPackagePrefix()));
                }
            }
        }
    }

    public static final IncrementalCompilationContext createContextForIncrementalCompilation(AbstractProjectEnvironment projectEnvironment, IncrementalCompilationComponents incrementalCompilationComponents, CompilerConfiguration moduleConfiguration, List<TargetId> list, AbstractProjectFileSearchScope sourceScope) {
        File file;
        Intrinsics.checkNotNullParameter(projectEnvironment, "projectEnvironment");
        Intrinsics.checkNotNullParameter(moduleConfiguration, "moduleConfiguration");
        Intrinsics.checkNotNullParameter(sourceScope, "sourceScope");
        if (list == null || incrementalCompilationComponents == null || (file = (File) moduleConfiguration.get(JVMConfigurationKeys.OUTPUT_DIRECTORY)) == null) {
            return null;
        }
        AbstractProjectFileSearchScope searchScopeByIoFiles$default = AbstractProjectEnvironment.getSearchScopeByIoFiles$default(projectEnvironment, SequencesKt.asIterable(SequencesKt.filter(FilesKt.walk$default(file, null, 1, null), new Function1<File, Boolean>() { // from class: org.jetbrains.kotlin.cli.jvm.compiler.CliCompilerUtilsKt$createContextForIncrementalCompilation$incrementalCompilationScope$1
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(File it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return Boolean.valueOf(Intrinsics.areEqual(FilesKt.getExtension(it), "class"));
            }
        })), false, 2, null);
        if (searchScopeByIoFiles$default.isEmpty()) {
            searchScopeByIoFiles$default = null;
        }
        if (searchScopeByIoFiles$default == null) {
            return null;
        }
        PackagePartProvider packagePartProvider = projectEnvironment.getPackagePartProvider(sourceScope);
        List<TargetId> list2 = list;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list2, 10));
        Iterator<T> it = list2.iterator();
        while (it.hasNext()) {
            arrayList.add(incrementalCompilationComponents.getIncrementalCache((TargetId) it.next()));
        }
        return new IncrementalCompilationContext(CollectionsKt.emptyList(), new IncrementalPackagePartProvider(packagePartProvider, arrayList), searchScopeByIoFiles$default);
    }

    public static final DependencyListForCliModule createLibraryListForJvm(String moduleName, CompilerConfiguration configuration, List<String> friendPaths) {
        Intrinsics.checkNotNullParameter(moduleName, "moduleName");
        Intrinsics.checkNotNullParameter(configuration, "configuration");
        Intrinsics.checkNotNullParameter(friendPaths, "friendPaths");
        BinaryModuleData.Companion companion = BinaryModuleData.INSTANCE;
        Name nameIdentifier = Name.identifier(moduleName);
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
        BinaryModuleData binaryModuleDataInitialize = companion.initialize(nameIdentifier, JvmPlatforms.INSTANCE.getUnspecifiedJvmPlatform(), JvmPlatformAnalyzerServices.INSTANCE);
        DependencyListForCliModule.Companion companion2 = DependencyListForCliModule.INSTANCE;
        DependencyListForCliModule.Builder builder = new DependencyListForCliModule.Builder(binaryModuleDataInitialize);
        List<File> jvmClasspathRoots = JvmContentRootsKt.getJvmClasspathRoots(configuration);
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(jvmClasspathRoots, 10));
        Iterator<T> it = jvmClasspathRoots.iterator();
        while (it.hasNext()) {
            arrayList.add(((File) it.next()).toPath());
        }
        builder.dependencies(arrayList);
        List<File> jvmModularRoots = JvmContentRootsKt.getJvmModularRoots(configuration);
        ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(jvmModularRoots, 10));
        Iterator<T> it2 = jvmModularRoots.iterator();
        while (it2.hasNext()) {
            arrayList2.add(((File) it2.next()).toPath());
        }
        builder.dependencies(arrayList2);
        List listEmptyList = (List) configuration.get(JVMConfigurationKeys.FRIEND_PATHS);
        if (listEmptyList == null) {
            listEmptyList = CollectionsKt.emptyList();
        } else {
            Intrinsics.checkNotNull(listEmptyList);
        }
        builder.friendDependenciesString(listEmptyList);
        builder.friendDependenciesString(friendPaths);
        return builder.build();
    }
}

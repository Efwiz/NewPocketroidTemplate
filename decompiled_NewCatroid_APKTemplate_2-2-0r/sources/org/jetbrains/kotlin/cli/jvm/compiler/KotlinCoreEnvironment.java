package org.jetbrains.kotlin.cli.jvm.compiler;

import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import java.io.File;
import java.lang.reflect.Field;
import java.net.URL;
import java.nio.file.FileSystems;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.zip.ZipFile;
import kotlin.Deprecated;
import kotlin.ExceptionsKt;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.ReplaceWith;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.io.CloseableKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.InlineMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;
import kotlin.sequences.SequencesKt;
import kotlin.text.StringsKt;
import net.bytebuddy.implementation.auxiliary.TypeProxy;
import org.eclipse.jdt.internal.compiler.util.SuffixConstants;
import org.jetbrains.kotlin.asJava.KotlinAsJavaSupport;
import org.jetbrains.kotlin.asJava.LightClassGenerationSupport;
import org.jetbrains.kotlin.asJava.finder.JavaElementFinder;
import org.jetbrains.kotlin.backend.common.extensions.IrGenerationExtension;
import org.jetbrains.kotlin.backend.jvm.extensions.ClassGeneratorExtension;
import org.jetbrains.kotlin.cli.common.CLIConfigurationKeys;
import org.jetbrains.kotlin.cli.common.CliModuleVisibilityManagerImpl;
import org.jetbrains.kotlin.cli.common.CompilerSystemProperties;
import org.jetbrains.kotlin.cli.common.PropertiesKt;
import org.jetbrains.kotlin.cli.common.config.ContentRoot;
import org.jetbrains.kotlin.cli.common.config.ContentRootsKt;
import org.jetbrains.kotlin.cli.common.config.KotlinSourceRoot;
import org.jetbrains.kotlin.cli.common.environment.UtilKt;
import org.jetbrains.kotlin.cli.common.extensions.ScriptEvaluationExtension;
import org.jetbrains.kotlin.cli.common.extensions.ShellExtension;
import org.jetbrains.kotlin.cli.common.messages.CompilerMessageSeverity;
import org.jetbrains.kotlin.cli.common.messages.MessageCollector;
import org.jetbrains.kotlin.cli.jvm.compiler.jarfs.FastJarFileSystem;
import org.jetbrains.kotlin.cli.jvm.config.JavaSourceRoot;
import org.jetbrains.kotlin.cli.jvm.config.JvmClasspathRoot;
import org.jetbrains.kotlin.cli.jvm.config.JvmContentRoot;
import org.jetbrains.kotlin.cli.jvm.config.JvmContentRootBase;
import org.jetbrains.kotlin.cli.jvm.config.JvmContentRootsKt;
import org.jetbrains.kotlin.cli.jvm.config.JvmModulePathRoot;
import org.jetbrains.kotlin.cli.jvm.config.VirtualJvmClasspathRoot;
import org.jetbrains.kotlin.cli.jvm.index.JavaRoot;
import org.jetbrains.kotlin.cli.jvm.index.JvmDependenciesDynamicCompoundIndex;
import org.jetbrains.kotlin.cli.jvm.index.JvmDependenciesIndex;
import org.jetbrains.kotlin.cli.jvm.index.JvmDependenciesIndexImpl;
import org.jetbrains.kotlin.cli.jvm.index.SingleJavaFileRootsIndex;
import org.jetbrains.kotlin.cli.jvm.javac.JavacWrapperRegistrar;
import org.jetbrains.kotlin.cli.jvm.modules.CliJavaModuleFinder;
import org.jetbrains.kotlin.cli.jvm.modules.CliJavaModuleResolver;
import org.jetbrains.kotlin.codegen.extensions.ClassBuilderInterceptorExtension;
import org.jetbrains.kotlin.codegen.extensions.ClassFileFactoryFinalizerExtension;
import org.jetbrains.kotlin.codegen.extensions.ExpressionCodegenExtension;
import org.jetbrains.kotlin.com.intellij.codeInsight.ExternalAnnotationsManager;
import org.jetbrains.kotlin.com.intellij.codeInsight.InferredAnnotationsManager;
import org.jetbrains.kotlin.com.intellij.core.CoreApplicationEnvironment;
import org.jetbrains.kotlin.com.intellij.core.CoreJavaFileManager;
import org.jetbrains.kotlin.com.intellij.core.JavaCoreProjectEnvironment;
import org.jetbrains.kotlin.com.intellij.ide.highlighter.JavaFileType;
import org.jetbrains.kotlin.com.intellij.lang.java.JavaParserDefinition;
import org.jetbrains.kotlin.com.intellij.mock.MockProject;
import org.jetbrains.kotlin.com.intellij.openapi.Disposable;
import org.jetbrains.kotlin.com.intellij.openapi.application.ApplicationManager;
import org.jetbrains.kotlin.com.intellij.openapi.application.TransactionGuard;
import org.jetbrains.kotlin.com.intellij.openapi.application.TransactionGuardImpl;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.openapi.extensions.ExtensionsArea;
import org.jetbrains.kotlin.com.intellij.openapi.extensions.impl.ExtensionsAreaImpl;
import org.jetbrains.kotlin.com.intellij.openapi.fileTypes.PlainTextFileType;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.openapi.util.Disposer;
import org.jetbrains.kotlin.com.intellij.openapi.util.io.FileUtilRt;
import org.jetbrains.kotlin.com.intellij.openapi.util.text.StringUtil;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.PersistentFSConstants;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VfsUtilCore;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFileSystem;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.impl.ZipHandler;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementFinder;
import org.jetbrains.kotlin.com.intellij.psi.PsiManager;
import org.jetbrains.kotlin.com.intellij.psi.impl.JavaClassSupersImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementFinderImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.PsiTreeChangePreprocessor;
import org.jetbrains.kotlin.com.intellij.psi.impl.file.impl.JavaFileManager;
import org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope;
import org.jetbrains.kotlin.com.intellij.psi.util.JavaClassSupers;
import org.jetbrains.kotlin.com.intellij.util.Processor;
import org.jetbrains.kotlin.com.intellij.util.lang.UrlClassLoader;
import org.jetbrains.kotlin.compiler.plugin.CompilerPluginRegistrar;
import org.jetbrains.kotlin.compiler.plugin.CompilerPluginRegistrarKt;
import org.jetbrains.kotlin.compiler.plugin.ComponentRegistrar;
import org.jetbrains.kotlin.config.AppendJavaSourceRootsHandlerKeyKt;
import org.jetbrains.kotlin.config.CommonConfigurationKeys;
import org.jetbrains.kotlin.config.CommonConfigurationKeysKt;
import org.jetbrains.kotlin.config.CompilerConfiguration;
import org.jetbrains.kotlin.config.CompilerConfigurationKey;
import org.jetbrains.kotlin.config.JVMConfigurationKeys;
import org.jetbrains.kotlin.extensions.CollectAdditionalSourcesExtension;
import org.jetbrains.kotlin.extensions.CompilerConfigurationExtension;
import org.jetbrains.kotlin.extensions.DeclarationAttributeAltererExtension;
import org.jetbrains.kotlin.extensions.PreprocessedVirtualFileFactoryExtension;
import org.jetbrains.kotlin.extensions.ProcessSourcesBeforeCompilingExtension;
import org.jetbrains.kotlin.extensions.StorageComponentContainerContributor;
import org.jetbrains.kotlin.extensions.TypeAttributeTranslatorExtension;
import org.jetbrains.kotlin.extensions.internal.CandidateInterceptor;
import org.jetbrains.kotlin.extensions.internal.TypeResolutionInterceptor;
import org.jetbrains.kotlin.fir.extensions.FirAnalysisHandlerExtension;
import org.jetbrains.kotlin.fir.extensions.FirExtensionRegistrarAdapter;
import org.jetbrains.kotlin.idea.KotlinFileType;
import org.jetbrains.kotlin.js.translate.extensions.JsSyntheticTranslateExtension;
import org.jetbrains.kotlin.load.java.structure.impl.source.JavaElementSourceFactory;
import org.jetbrains.kotlin.load.java.structure.impl.source.JavaFixedElementSourceFactory;
import org.jetbrains.kotlin.load.kotlin.KotlinBinaryClassCache;
import org.jetbrains.kotlin.load.kotlin.MetadataFinderFactory;
import org.jetbrains.kotlin.load.kotlin.ModuleVisibilityManager;
import org.jetbrains.kotlin.load.kotlin.VirtualFileFinderFactory;
import org.jetbrains.kotlin.modules.Module;
import org.jetbrains.kotlin.parsing.KotlinParserDefinition;
import org.jetbrains.kotlin.psi.KtFile;
import org.jetbrains.kotlin.resolve.CodeAnalyzerInitializer;
import org.jetbrains.kotlin.resolve.ModuleAnnotationsResolver;
import org.jetbrains.kotlin.resolve.extensions.AssignResolutionAltererExtension;
import org.jetbrains.kotlin.resolve.extensions.ExtraImportsProviderExtension;
import org.jetbrains.kotlin.resolve.extensions.SyntheticResolveExtension;
import org.jetbrains.kotlin.resolve.jvm.KotlinJavaPsiFacade;
import org.jetbrains.kotlin.resolve.jvm.extensions.AnalysisHandlerExtension;
import org.jetbrains.kotlin.resolve.jvm.extensions.PackageFragmentProviderExtension;
import org.jetbrains.kotlin.resolve.jvm.extensions.SyntheticJavaResolveExtension;
import org.jetbrains.kotlin.resolve.jvm.modules.JavaModule;
import org.jetbrains.kotlin.resolve.jvm.modules.JavaModuleResolver;
import org.jetbrains.kotlin.resolve.lazy.declarations.CliDeclarationProviderFactoryService;
import org.jetbrains.kotlin.resolve.lazy.declarations.DeclarationProviderFactoryService;
import org.jetbrains.kotlin.serialization.DescriptorSerializerPlugin;
import org.jetbrains.kotlin.utils.PathUtil;

/* JADX INFO: compiled from: KotlinCoreEnvironment.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000Î\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u0000 V2\u00020\u0001:\u0002VWB\u001f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\u0014\u0010+\u001a\u00020,2\f\u0010-\u001a\b\u0012\u0004\u0012\u00020\u000b0\nJ\u0010\u0010.\u001a\u00020,2\u0006\u0010\u001d\u001a\u00020/H\u0002J\u0012\u00100\u001a\u0004\u0018\u00010(2\u0006\u00101\u001a\u000202H\u0002J\u0014\u00103\u001a\u0002042\f\u0010%\u001a\b\u0012\u0004\u0012\u00020&0\nJ\u000e\u00105\u001a\u00020\u001c2\u0006\u00106\u001a\u000207J\u001a\u00108\u001a\u0004\u0018\u00010(2\u0006\u00101\u001a\u0002092\u0006\u0010:\u001a\u00020;H\u0002J\u0012\u0010<\u001a\u0004\u0018\u00010(2\u0006\u0010=\u001a\u00020\u000bH\u0002J\u0010\u0010>\u001a\u0004\u0018\u00010(2\u0006\u0010?\u001a\u00020;J\f\u0010@\u001a\b\u0012\u0004\u0012\u00020&0\nJ\u000e\u0010A\u001a\b\u0012\u0004\u0012\u00020B0\nH\u0002Ja\u0010C\u001a\u00020D2\u000e\b\u0002\u0010'\u001a\b\u0012\u0004\u0012\u00020\u000b0\n2\u000e\b\u0002\u0010E\u001a\b\u0012\u0004\u0012\u00020&0\n2\u0010\b\u0002\u0010F\u001a\n\u0012\u0004\u0012\u00020;\u0018\u00010G2\u0010\b\u0002\u0010H\u001a\n\u0012\u0004\u0012\u00020\u000b\u0018\u00010\n2\u0010\b\u0002\u0010I\u001a\n\u0012\u0004\u0012\u00020\u000b\u0018\u00010\n¢\u0006\u0002\u0010JJ\u001d\u0010K\u001a\u00020,2\u0006\u0010L\u001a\u00020M2\u0006\u0010N\u001a\u00020;H\u0000¢\u0006\u0002\bOJ\u001c\u0010P\u001a\n\u0012\u0004\u0012\u00020\u000b\u0018\u00010\n2\f\u0010Q\u001a\b\u0012\u0004\u0012\u00020R0\nJ\u0010\u0010S\u001a\u00020,2\u0006\u0010T\u001a\u00020UH\u0002R\u001a\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\n8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\rR\u0014\u0010\u000e\u001a\u00020\u000f8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u0011R\u000e\u0010\u0012\u001a\u00020\u0013X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u001e\u0010\u0016\u001a\u0012\u0012\u0004\u0012\u00020\u00180\u0017j\b\u0012\u0004\u0012\u00020\u0018`\u0019X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u001c0\u001bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u001d\u001a\u00020\u001e8F¢\u0006\u0006\u001a\u0004\b\u001f\u0010 R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b!\u0010\"R\u000e\u0010#\u001a\u00020$X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010%\u001a\b\u0012\u0004\u0012\u00020&0\u001bX\u0082\u0004¢\u0006\u0002\n\u0000R\u001e\u0010'\u001a\b\u0012\u0004\u0012\u00020(0\n*\u00020(8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b)\u0010*¨\u0006X"}, d2 = {"Lorg/jetbrains/kotlin/cli/jvm/compiler/KotlinCoreEnvironment;", "", "projectEnvironment", "Lorg/jetbrains/kotlin/cli/jvm/compiler/KotlinCoreEnvironment$ProjectEnvironment;", "configuration", "Lorg/jetbrains/kotlin/config/CompilerConfiguration;", "configFiles", "Lorg/jetbrains/kotlin/cli/jvm/compiler/EnvironmentConfigFiles;", "(Lorg/jetbrains/kotlin/cli/jvm/compiler/KotlinCoreEnvironment$ProjectEnvironment;Lorg/jetbrains/kotlin/config/CompilerConfiguration;Lorg/jetbrains/kotlin/cli/jvm/compiler/EnvironmentConfigFiles;)V", "allJavaFiles", "", "Ljava/io/File;", "getAllJavaFiles", "()Ljava/util/List;", "applicationEnvironment", "Lorg/jetbrains/kotlin/com/intellij/core/CoreApplicationEnvironment;", "getApplicationEnvironment", "()Lcom/intellij/core/CoreApplicationEnvironment;", "classpathRootsResolver", "Lorg/jetbrains/kotlin/cli/jvm/compiler/ClasspathRootsResolver;", "getConfiguration", "()Lorg/jetbrains/kotlin/config/CompilerConfiguration;", "initialRoots", "Ljava/util/ArrayList;", "Lorg/jetbrains/kotlin/cli/jvm/index/JavaRoot;", "Lkotlin/collections/ArrayList;", "packagePartProviders", "", "Lorg/jetbrains/kotlin/cli/jvm/compiler/JvmPackagePartProvider;", "project", "Lorg/jetbrains/kotlin/com/intellij/openapi/project/Project;", "getProject", "()Lcom/intellij/openapi/project/Project;", "getProjectEnvironment", "()Lorg/jetbrains/kotlin/cli/jvm/compiler/KotlinCoreEnvironment$ProjectEnvironment;", "rootsIndex", "Lorg/jetbrains/kotlin/cli/jvm/index/JvmDependenciesDynamicCompoundIndex;", "sourceFiles", "Lorg/jetbrains/kotlin/psi/KtFile;", "javaFiles", "Lorg/jetbrains/kotlin/com/intellij/openapi/vfs/VirtualFile;", "getJavaFiles", "(Lcom/intellij/openapi/vfs/VirtualFile;)Ljava/util/List;", "addKotlinSourceRoots", "", "rootDirs", "collectAdditionalSources", "Lorg/jetbrains/kotlin/com/intellij/mock/MockProject;", "contentRootToVirtualFile", "root", "Lorg/jetbrains/kotlin/cli/jvm/config/JvmContentRootBase;", "countLinesOfCode", "", "createPackagePartProvider", "scope", "Lorg/jetbrains/kotlin/com/intellij/psi/search/GlobalSearchScope;", "findExistingRoot", "Lorg/jetbrains/kotlin/cli/jvm/config/JvmContentRoot;", "rootDescription", "", "findJarRoot", "file", "findLocalFile", "path", "getSourceFiles", "getSourceRootsCheckingForDuplicates", "Lorg/jetbrains/kotlin/cli/common/config/KotlinSourceRoot;", "registerJavac", "", "kotlinFiles", "arguments", "", "bootClasspath", "sourcePath", "(Ljava/util/List;Ljava/util/List;[Ljava/lang/String;Ljava/util/List;Ljava/util/List;)Z", "report", "severity", "Lorg/jetbrains/kotlin/cli/common/messages/CompilerMessageSeverity;", CrashHianalyticsData.MESSAGE, "report$cli_base", "updateClasspath", "contentRoots", "Lorg/jetbrains/kotlin/cli/common/config/ContentRoot;", "updateClasspathFromRootsIndex", "index", "Lorg/jetbrains/kotlin/cli/jvm/index/JvmDependenciesIndex;", "Companion", "ProjectEnvironment", "cli-base"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KotlinCoreEnvironment {
    private static final Object APPLICATION_LOCK;

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final Logger LOG;
    private static KotlinCoreApplicationEnvironment ourApplicationEnvironment;
    private static int ourProjectCount;
    private final ClasspathRootsResolver classpathRootsResolver;
    private final CompilerConfiguration configuration;
    private final ArrayList<JavaRoot> initialRoots;
    private final List<JvmPackagePartProvider> packagePartProviders;
    private final ProjectEnvironment projectEnvironment;
    private final JvmDependenciesDynamicCompoundIndex rootsIndex;
    private final List<KtFile> sourceFiles;

    public /* synthetic */ KotlinCoreEnvironment(ProjectEnvironment projectEnvironment, CompilerConfiguration compilerConfiguration, EnvironmentConfigFiles environmentConfigFiles, DefaultConstructorMarker defaultConstructorMarker) {
        this(projectEnvironment, compilerConfiguration, environmentConfigFiles);
    }

    @JvmStatic
    public static final void configureProjectEnvironment(ProjectEnvironment projectEnvironment, CompilerConfiguration compilerConfiguration, EnvironmentConfigFiles environmentConfigFiles) throws IllegalAccessException, NoSuchFieldException {
        INSTANCE.configureProjectEnvironment(projectEnvironment, compilerConfiguration, environmentConfigFiles);
    }

    @JvmStatic
    public static final KotlinCoreEnvironment createForParallelTests(Disposable disposable, CompilerConfiguration compilerConfiguration, EnvironmentConfigFiles environmentConfigFiles) {
        return INSTANCE.createForParallelTests(disposable, compilerConfiguration, environmentConfigFiles);
    }

    @JvmStatic
    public static final KotlinCoreEnvironment createForProduction(ProjectEnvironment projectEnvironment, CompilerConfiguration compilerConfiguration, EnvironmentConfigFiles environmentConfigFiles) {
        return INSTANCE.createForProduction(projectEnvironment, compilerConfiguration, environmentConfigFiles);
    }

    @JvmStatic
    public static final KotlinCoreEnvironment createForProduction(Disposable disposable, CompilerConfiguration compilerConfiguration, EnvironmentConfigFiles environmentConfigFiles) {
        return INSTANCE.createForProduction(disposable, compilerConfiguration, environmentConfigFiles);
    }

    @JvmStatic
    public static final KotlinCoreEnvironment createForTests(ProjectEnvironment projectEnvironment, CompilerConfiguration compilerConfiguration, EnvironmentConfigFiles environmentConfigFiles) {
        return INSTANCE.createForTests(projectEnvironment, compilerConfiguration, environmentConfigFiles);
    }

    @JvmStatic
    public static final KotlinCoreEnvironment createForTests(Disposable disposable, CompilerConfiguration compilerConfiguration, EnvironmentConfigFiles environmentConfigFiles) {
        return INSTANCE.createForTests(disposable, compilerConfiguration, environmentConfigFiles);
    }

    @JvmStatic
    public static final void disposeApplicationEnvironment() {
        INSTANCE.disposeApplicationEnvironment();
    }

    @JvmStatic
    public static final void registerApplicationServices(KotlinCoreApplicationEnvironment kotlinCoreApplicationEnvironment) {
        INSTANCE.registerApplicationServices(kotlinCoreApplicationEnvironment);
    }

    @JvmStatic
    public static final void registerKotlinLightClassSupport(MockProject mockProject) {
        INSTANCE.registerKotlinLightClassSupport(mockProject);
    }

    @JvmStatic
    public static final void registerPluginExtensionPoints(MockProject mockProject) {
        INSTANCE.registerPluginExtensionPoints(mockProject);
    }

    @JvmStatic
    public static final void registerProjectExtensionPoints(ExtensionsArea extensionsArea) {
        INSTANCE.registerProjectExtensionPoints(extensionsArea);
    }

    @Deprecated(message = "Use registerProjectServices(project) instead.", replaceWith = @ReplaceWith(expression = "registerProjectServices(projectEnvironment.project)", imports = {}))
    @JvmStatic
    public static final void registerProjectServices(JavaCoreProjectEnvironment javaCoreProjectEnvironment, MessageCollector messageCollector) {
        INSTANCE.registerProjectServices(javaCoreProjectEnvironment, messageCollector);
    }

    @JvmStatic
    public static final void registerProjectServices(MockProject mockProject) {
        INSTANCE.registerProjectServices(mockProject);
    }

    private KotlinCoreEnvironment(ProjectEnvironment projectEnvironment, CompilerConfiguration compilerConfiguration, EnvironmentConfigFiles environmentConfigFiles) throws IllegalAccessException, NoSuchFieldException {
        String absolutePath;
        Module module;
        this.projectEnvironment = projectEnvironment;
        this.configuration = compilerConfiguration;
        ArrayList arrayList = new ArrayList();
        this.sourceFiles = arrayList;
        this.packagePartProviders = new ArrayList();
        ArrayList<JavaRoot> arrayList2 = new ArrayList<>();
        this.initialRoots = arrayList2;
        INSTANCE.configureProjectEnvironment(projectEnvironment, compilerConfiguration, environmentConfigFiles);
        MockProject project = projectEnvironment.getProject();
        Intrinsics.checkNotNullExpressionValue(project, "getProject(...)");
        project.registerService((Class<CliDeclarationProviderFactoryService>) DeclarationProviderFactoryService.class, new CliDeclarationProviderFactoryService(arrayList));
        MockProject mockProject = project;
        CollectionsKt.addAll(arrayList, CoreEnvironmentUtilsKt.createSourceFilesFromSourceRoots$default(compilerConfiguration, mockProject, getSourceRootsCheckingForDuplicates(), null, 8, null));
        collectAdditionalSources(project);
        if (arrayList.size() > 1) {
            CollectionsKt.sortWith(arrayList, new Comparator() { // from class: org.jetbrains.kotlin.cli.jvm.compiler.KotlinCoreEnvironment$special$$inlined$sortBy$1
                /* JADX WARN: Multi-variable type inference failed */
                @Override // java.util.Comparator
                public final int compare(T t, T t2) {
                    return ComparisonsKt.compareValues(((KtFile) t).getVirtualFile().getPath(), ((KtFile) t2).getVirtualFile().getPath());
                }
            });
        }
        Object service = project.getService(CoreJavaFileManager.class);
        Intrinsics.checkNotNull(service, "null cannot be cast to non-null type org.jetbrains.kotlin.cli.jvm.compiler.KotlinCliJavaFileManagerImpl");
        KotlinCliJavaFileManagerImpl kotlinCliJavaFileManagerImpl = (KotlinCliJavaFileManagerImpl) service;
        MessageCollector messageCollector = (MessageCollector) compilerConfiguration.get(CLIConfigurationKeys.MESSAGE_COLLECTOR_KEY);
        File file = (File) compilerConfiguration.get(JVMConfigurationKeys.JDK_HOME);
        Integer num = (Integer) compilerConfiguration.get(JVMConfigurationKeys.JDK_RELEASE);
        KotlinCliJavaFileManagerImpl kotlinCliJavaFileManagerImpl2 = kotlinCliJavaFileManagerImpl;
        CliJavaModuleFinder cliJavaModuleFinder = new CliJavaModuleFinder(file, messageCollector, kotlinCliJavaFileManagerImpl2, mockProject, num);
        List list = (List) compilerConfiguration.get(JVMConfigurationKeys.MODULES);
        if (list == null || (module = (Module) CollectionsKt.singleOrNull(list)) == null || (absolutePath = module.getOutputDir()) == null) {
            File file2 = (File) compilerConfiguration.get(JVMConfigurationKeys.OUTPUT_DIRECTORY);
            absolutePath = file2 != null ? file2.getAbsolutePath() : null;
        }
        PsiManager psiManager = PsiManager.getInstance(mockProject);
        Intrinsics.checkNotNullExpressionValue(psiManager, "getInstance(...)");
        List list2 = compilerConfiguration.getList(JVMConfigurationKeys.ADDITIONAL_JAVA_MODULES);
        Intrinsics.checkNotNullExpressionValue(list2, "getList(...)");
        ClasspathRootsResolver classpathRootsResolver = new ClasspathRootsResolver(psiManager, messageCollector, list2, new AnonymousClass2(this), cliJavaModuleFinder, !compilerConfiguration.getBoolean(CLIConfigurationKeys.ALLOW_KOTLIN_PACKAGE), absolutePath != null ? findLocalFile(absolutePath) : null, kotlinCliJavaFileManagerImpl2, num);
        this.classpathRootsResolver = classpathRootsResolver;
        List<? extends ContentRoot> list3 = compilerConfiguration.getList(CLIConfigurationKeys.CONTENT_ROOTS);
        Intrinsics.checkNotNullExpressionValue(list3, "getList(...)");
        ClasspathRootsResolver.RootsAndModules rootsAndModulesConvertClasspathRoots = classpathRootsResolver.convertClasspathRoots(list3);
        List<JavaRoot> listComponent1 = rootsAndModulesConvertClasspathRoots.component1();
        List<JavaModule> listComponent2 = rootsAndModulesConvertClasspathRoots.component2();
        arrayList2.addAll(listComponent1);
        ArrayList arrayList3 = new ArrayList();
        ArrayList arrayList4 = new ArrayList();
        for (Object obj : listComponent1) {
            VirtualFile virtualFileComponent1 = ((JavaRoot) obj).getFile();
            if (virtualFileComponent1.isDirectory() || !Intrinsics.areEqual(virtualFileComponent1.getExtension(), SuffixConstants.EXTENSION_java)) {
                arrayList3.add(obj);
            } else {
                arrayList4.add(obj);
            }
        }
        Pair pair = new Pair(arrayList3, arrayList4);
        List list4 = (List) pair.component1();
        List list5 = (List) pair.component2();
        JvmDependenciesDynamicCompoundIndex jvmDependenciesDynamicCompoundIndex = new JvmDependenciesDynamicCompoundIndex();
        jvmDependenciesDynamicCompoundIndex.addIndex(new JvmDependenciesIndexImpl(list4));
        updateClasspathFromRootsIndex(jvmDependenciesDynamicCompoundIndex);
        this.rootsIndex = jvmDependenciesDynamicCompoundIndex;
        kotlinCliJavaFileManagerImpl.initialize(jvmDependenciesDynamicCompoundIndex, this.packagePartProviders, new SingleJavaFileRootsIndex(list5), this.configuration.getBoolean(JVMConfigurationKeys.USE_PSI_CLASS_FILES_READING));
        project.registerService((Class<CliJavaModuleResolver>) JavaModuleResolver.class, new CliJavaModuleResolver(this.classpathRootsResolver.getJavaModuleGraph(), listComponent2, SequencesKt.toList(cliJavaModuleFinder.getSystemModules()), mockProject));
        CliVirtualFileFinderFactory cliVirtualFileFinderFactory = new CliVirtualFileFinderFactory(jvmDependenciesDynamicCompoundIndex, num != null);
        project.registerService((Class<CliVirtualFileFinderFactory>) MetadataFinderFactory.class, cliVirtualFileFinderFactory);
        project.registerService((Class<CliVirtualFileFinderFactory>) VirtualFileFinderFactory.class, cliVirtualFileFinderFactory);
        project.putUserData(AppendJavaSourceRootsHandlerKeyKt.getAPPEND_JAVA_SOURCE_ROOTS_HANDLER_KEY(), new Function1<List<? extends File>, Unit>() { // from class: org.jetbrains.kotlin.cli.jvm.compiler.KotlinCoreEnvironment.6
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(List<? extends File> list6) {
                invoke2(list6);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(List<? extends File> roots) {
                Intrinsics.checkNotNullParameter(roots, "roots");
                KotlinCoreEnvironment kotlinCoreEnvironment = KotlinCoreEnvironment.this;
                List<? extends File> list6 = roots;
                ArrayList arrayList5 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list6, 10));
                Iterator<T> it = list6.iterator();
                while (it.hasNext()) {
                    arrayList5.add(new JavaSourceRoot((File) it.next(), null));
                }
                kotlinCoreEnvironment.updateClasspath(arrayList5);
            }
        });
        JavaLanguageLevelKt.setupHighestLanguageLevel(mockProject);
    }

    public final ProjectEnvironment getProjectEnvironment() {
        return this.projectEnvironment;
    }

    public final CompilerConfiguration getConfiguration() {
        return this.configuration;
    }

    /* JADX INFO: compiled from: KotlinCoreEnvironment.kt */
    @Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\b\u0010\u000f\u001a\u00020\u0010H\u0014J\u000e\u0010\u0011\u001a\u00020\u00102\u0006\u0010\u0006\u001a\u00020\u0007J\b\u0010\u0012\u001a\u00020\u0010H\u0014R\u000e\u0010\t\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u0011\u0010\u000b\u001a\u00020\f¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e¨\u0006\u0013"}, d2 = {"Lorg/jetbrains/kotlin/cli/jvm/compiler/KotlinCoreEnvironment$ProjectEnvironment;", "Lorg/jetbrains/kotlin/cli/jvm/compiler/KotlinCoreProjectEnvironment;", "disposable", "Lorg/jetbrains/kotlin/com/intellij/openapi/Disposable;", "applicationEnvironment", "Lorg/jetbrains/kotlin/cli/jvm/compiler/KotlinCoreApplicationEnvironment;", "configuration", "Lorg/jetbrains/kotlin/config/CompilerConfiguration;", "(Lcom/intellij/openapi/Disposable;Lorg/jetbrains/kotlin/cli/jvm/compiler/KotlinCoreApplicationEnvironment;Lorg/jetbrains/kotlin/config/CompilerConfiguration;)V", "extensionRegistered", "", "jarFileSystem", "Lorg/jetbrains/kotlin/com/intellij/openapi/vfs/VirtualFileSystem;", "getJarFileSystem", "()Lcom/intellij/openapi/vfs/VirtualFileSystem;", "preregisterServices", "", "registerExtensionsFromPlugins", "registerJavaPsiFacade", "cli-base"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class ProjectEnvironment extends KotlinCoreProjectEnvironment {
        private boolean extensionRegistered;
        private final VirtualFileSystem jarFileSystem;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public ProjectEnvironment(Disposable disposable, KotlinCoreApplicationEnvironment applicationEnvironment, CompilerConfiguration configuration) {
            VirtualFileSystem jarFileSystem;
            super(disposable, applicationEnvironment);
            Intrinsics.checkNotNullParameter(disposable, "disposable");
            Intrinsics.checkNotNullParameter(applicationEnvironment, "applicationEnvironment");
            Intrinsics.checkNotNullParameter(configuration, "configuration");
            MessageCollector messageCollector = (MessageCollector) configuration.get(CLIConfigurationKeys.MESSAGE_COLLECTOR_KEY);
            UtilKt.setIdeaIoUseFallback();
            if (configuration.getBoolean(JVMConfigurationKeys.USE_FAST_JAR_FILE_SYSTEM) && messageCollector != null) {
                MessageCollector.report$default(messageCollector, CompilerMessageSeverity.STRONG_WARNING, "Using new faster version of JAR FS: it should make your build faster, but the new implementation is experimental", null, 4, null);
            }
            if (configuration.getBoolean(JVMConfigurationKeys.USE_PSI_CLASS_FILES_READING)) {
                jarFileSystem = applicationEnvironment.getJarFileSystem();
                Intrinsics.checkNotNull(jarFileSystem);
            } else if (configuration.getBoolean(JVMConfigurationKeys.USE_FAST_JAR_FILE_SYSTEM) || configuration.getBoolean(CommonConfigurationKeys.USE_FIR)) {
                FastJarFileSystem fastJarFileSystem = applicationEnvironment.getFastJarFileSystem();
                if (fastJarFileSystem != null) {
                    jarFileSystem = fastJarFileSystem;
                } else {
                    if (messageCollector != null) {
                        MessageCollector.report$default(messageCollector, CompilerMessageSeverity.STRONG_WARNING, "Your JDK doesn't seem to support mapped buffer unmapping, so the slower (old) version of JAR FS will be used", null, 4, null);
                    }
                    jarFileSystem = applicationEnvironment.getJarFileSystem();
                }
                Intrinsics.checkNotNull(jarFileSystem);
            } else {
                jarFileSystem = applicationEnvironment.getJarFileSystem();
                Intrinsics.checkNotNullExpressionValue(jarFileSystem, "getJarFileSystem(...)");
            }
            this.jarFileSystem = jarFileSystem;
        }

        public final VirtualFileSystem getJarFileSystem() {
            return this.jarFileSystem;
        }

        @Override // org.jetbrains.kotlin.com.intellij.core.CoreProjectEnvironment
        protected void preregisterServices() {
            Companion companion = KotlinCoreEnvironment.INSTANCE;
            ExtensionsAreaImpl extensionArea = getProject().getExtensionArea();
            Intrinsics.checkNotNullExpressionValue(extensionArea, "getExtensionArea(...)");
            companion.registerProjectExtensionPoints(extensionArea);
        }

        public final void registerExtensionsFromPlugins(CompilerConfiguration configuration) {
            Intrinsics.checkNotNullParameter(configuration, "configuration");
            if (this.extensionRegistered) {
                return;
            }
            Companion companion = KotlinCoreEnvironment.INSTANCE;
            MockProject project = getProject();
            Intrinsics.checkNotNullExpressionValue(project, "getProject(...)");
            companion.registerPluginExtensionPoints(project);
            Companion companion2 = KotlinCoreEnvironment.INSTANCE;
            MockProject project2 = getProject();
            Intrinsics.checkNotNullExpressionValue(project2, "getProject(...)");
            companion2.registerExtensionsFromPlugins$cli_base(project2, configuration);
            this.extensionRegistered = true;
        }

        @Override // org.jetbrains.kotlin.com.intellij.core.JavaCoreProjectEnvironment
        protected void registerJavaPsiFacade() {
            MockProject project = getProject();
            Object service = project.getService(JavaFileManager.class);
            Intrinsics.checkNotNull(service, "null cannot be cast to non-null type com.intellij.core.CoreJavaFileManager");
            project.registerService((Class<CoreJavaFileManager>) CoreJavaFileManager.class, (CoreJavaFileManager) service);
            Companion companion = KotlinCoreEnvironment.INSTANCE;
            MockProject project2 = getProject();
            Intrinsics.checkNotNullExpressionValue(project2, "getProject(...)");
            companion.registerKotlinLightClassSupport(project2);
            project.registerService((Class<MockExternalAnnotationsManager>) ExternalAnnotationsManager.class, new MockExternalAnnotationsManager());
            project.registerService((Class<MockInferredAnnotationsManager>) InferredAnnotationsManager.class, new MockInferredAnnotationsManager());
            super.registerJavaPsiFacade();
        }
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.cli.jvm.compiler.KotlinCoreEnvironment$2, reason: invalid class name */
    /* JADX INFO: compiled from: KotlinCoreEnvironment.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    /* synthetic */ class AnonymousClass2 extends FunctionReference implements Function1<JvmContentRootBase, VirtualFile> {
        AnonymousClass2(Object obj) {
            super(1, obj);
        }

        @Override // kotlin.jvm.internal.CallableReference
        public final String getSignature() {
            return "contentRootToVirtualFile(Lorg/jetbrains/kotlin/cli/jvm/config/JvmContentRootBase;)Lcom/intellij/openapi/vfs/VirtualFile;";
        }

        @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
        public final String getName() {
            return "contentRootToVirtualFile";
        }

        @Override // kotlin.jvm.internal.CallableReference
        public final KDeclarationContainer getOwner() {
            return Reflection.getOrCreateKotlinClass(KotlinCoreEnvironment.class);
        }

        @Override // kotlin.jvm.functions.Function1
        public final VirtualFile invoke(JvmContentRootBase p0) {
            Intrinsics.checkNotNullParameter(p0, "p0");
            return ((KotlinCoreEnvironment) this.receiver).contentRootToVirtualFile(p0);
        }
    }

    private final void collectAdditionalSources(MockProject project) {
        List<KtFile> listDistinct = this.sourceFiles;
        HashSet hashSet = new HashSet();
        HashMap map = new HashMap();
        int i = 0;
        while (!listDistinct.isEmpty()) {
            int i2 = i + 1;
            if (i > 10) {
                throw new IllegalStateException("Unable to collect additional sources in reasonable number of iterations");
            }
            hashSet.addAll(listDistinct);
            ArrayList arrayList = new ArrayList();
            MockProject mockProject = project;
            for (CollectAdditionalSourcesExtension collectAdditionalSourcesExtension : CollectAdditionalSourcesExtension.INSTANCE.getInstances(mockProject)) {
                List<KtFile> list = listDistinct;
                Iterable iterableEmptyList = (Collection) map.get(collectAdditionalSourcesExtension);
                if (iterableEmptyList == null) {
                    iterableEmptyList = CollectionsKt.emptyList();
                }
                Collection<KtFile> collectionCollectAdditionalSourcesAndUpdateConfiguration = collectAdditionalSourcesExtension.collectAdditionalSourcesAndUpdateConfiguration(CollectionsKt.minus((Iterable) list, iterableEmptyList), this.configuration, mockProject);
                if (!collectionCollectAdditionalSourcesAndUpdateConfiguration.isEmpty()) {
                    arrayList.addAll(collectionCollectAdditionalSourcesAndUpdateConfiguration);
                    map.put(collectAdditionalSourcesExtension, collectionCollectAdditionalSourcesAndUpdateConfiguration);
                }
            }
            ArrayList arrayList2 = new ArrayList();
            for (Object obj : arrayList) {
                if (!hashSet.contains((KtFile) obj)) {
                    arrayList2.add(obj);
                }
            }
            listDistinct = CollectionsKt.distinct(arrayList2);
            CollectionsKt.addAll(this.sourceFiles, listDistinct);
            i = i2;
        }
    }

    public final void addKotlinSourceRoots(List<? extends File> rootDirs) {
        Intrinsics.checkNotNullParameter(rootDirs, "rootDirs");
        List<? extends File> list = rootDirs;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            String absolutePath = ((File) it.next()).getAbsolutePath();
            Intrinsics.checkNotNullExpressionValue(absolutePath, "getAbsolutePath(...)");
            arrayList.add(new KotlinSourceRoot(absolutePath, false, null));
        }
        CollectionsKt.addAll(this.sourceFiles, SetsKt.minus(CollectionsKt.toSet(CoreEnvironmentUtilsKt.createSourceFilesFromSourceRoots$default(this.configuration, getProject(), arrayList, null, 8, null)), (Iterable) this.sourceFiles));
    }

    public final JvmPackagePartProvider createPackagePartProvider(GlobalSearchScope scope) {
        Intrinsics.checkNotNullParameter(scope, "scope");
        JvmPackagePartProvider jvmPackagePartProvider = new JvmPackagePartProvider(CommonConfigurationKeysKt.getLanguageVersionSettings(this.configuration), scope);
        ArrayList<JavaRoot> arrayList = this.initialRoots;
        Object notNull = this.configuration.getNotNull(CLIConfigurationKeys.MESSAGE_COLLECTOR_KEY);
        Intrinsics.checkNotNullExpressionValue(notNull, "getNotNull(...)");
        jvmPackagePartProvider.addRoots(arrayList, (MessageCollector) notNull);
        this.packagePartProviders.add(jvmPackagePartProvider);
        ModuleAnnotationsResolver companion = ModuleAnnotationsResolver.INSTANCE.getInstance(getProject());
        Intrinsics.checkNotNull(companion, "null cannot be cast to non-null type org.jetbrains.kotlin.cli.jvm.compiler.CliModuleAnnotationsResolver");
        ((CliModuleAnnotationsResolver) companion).addPackagePartProvider(jvmPackagePartProvider);
        return jvmPackagePartProvider;
    }

    private final List<VirtualFile> getJavaFiles(VirtualFile virtualFile) throws Throwable {
        final ArrayList arrayList = new ArrayList();
        final Function1<VirtualFile, Boolean> function1 = new Function1<VirtualFile, Boolean>() { // from class: org.jetbrains.kotlin.cli.jvm.compiler.KotlinCoreEnvironment$javaFiles$1$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(VirtualFile virtualFile2) {
                if (Intrinsics.areEqual(virtualFile2.getExtension(), SuffixConstants.EXTENSION_java) || Intrinsics.areEqual(virtualFile2.getFileType(), JavaFileType.INSTANCE)) {
                    List<VirtualFile> list = arrayList;
                    Intrinsics.checkNotNull(virtualFile2);
                    list.add(virtualFile2);
                }
                return true;
            }
        };
        VfsUtilCore.processFilesRecursively(virtualFile, new Processor() { // from class: org.jetbrains.kotlin.cli.jvm.compiler.KotlinCoreEnvironment$$ExternalSyntheticLambda0
            @Override // org.jetbrains.kotlin.com.intellij.util.Processor
            public final boolean process(Object obj) {
                return KotlinCoreEnvironment._get_javaFiles_$lambda$7$lambda$6(function1, obj);
            }
        });
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean _get_javaFiles_$lambda$7$lambda$6(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        return ((Boolean) tmp0.invoke(obj)).booleanValue();
    }

    private final List<File> getAllJavaFiles() {
        Set<String> javaSourceRoots = JvmContentRootsKt.getJavaSourceRoots(this.configuration);
        ArrayList arrayList = new ArrayList();
        Iterator<T> it = javaSourceRoots.iterator();
        while (it.hasNext()) {
            VirtualFile virtualFileFindLocalFile = findLocalFile((String) it.next());
            if (virtualFileFindLocalFile != null) {
                arrayList.add(virtualFileFindLocalFile);
            }
        }
        ArrayList arrayList2 = new ArrayList();
        Iterator it2 = arrayList.iterator();
        while (it2.hasNext()) {
            CollectionsKt.addAll(arrayList2, getJavaFiles((VirtualFile) it2.next()));
        }
        ArrayList arrayList3 = arrayList2;
        ArrayList arrayList4 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList3, 10));
        Iterator it3 = arrayList3.iterator();
        while (it3.hasNext()) {
            arrayList4.add(new File(((VirtualFile) it3.next()).getCanonicalPath()));
        }
        return arrayList4;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ boolean registerJavac$default(KotlinCoreEnvironment kotlinCoreEnvironment, List list, List list2, String[] strArr, List list3, List list4, int i, Object obj) {
        if ((i & 1) != 0) {
            list = kotlinCoreEnvironment.getAllJavaFiles();
        }
        if ((i & 2) != 0) {
            list2 = kotlinCoreEnvironment.sourceFiles;
        }
        return kotlinCoreEnvironment.registerJavac(list, list2, (i & 4) != 0 ? null : strArr, (i & 8) != 0 ? null : list3, (i & 16) == 0 ? list4 : null);
    }

    public final boolean registerJavac(List<? extends File> javaFiles, List<? extends KtFile> kotlinFiles, String[] arguments, List<? extends File> bootClasspath, List<? extends File> sourcePath) {
        Intrinsics.checkNotNullParameter(javaFiles, "javaFiles");
        Intrinsics.checkNotNullParameter(kotlinFiles, "kotlinFiles");
        JavacWrapperRegistrar javacWrapperRegistrar = JavacWrapperRegistrar.INSTANCE;
        MockProject project = this.projectEnvironment.getProject();
        Intrinsics.checkNotNullExpressionValue(project, "getProject(...)");
        return javacWrapperRegistrar.registerJavac(project, this.configuration, javaFiles, kotlinFiles, arguments, bootClasspath, sourcePath, LightClassGenerationSupport.INSTANCE.getInstance(getProject()), this.packagePartProviders);
    }

    private final CoreApplicationEnvironment getApplicationEnvironment() {
        CoreApplicationEnvironment environment = this.projectEnvironment.getEnvironment();
        Intrinsics.checkNotNullExpressionValue(environment, "getEnvironment(...)");
        return environment;
    }

    public final Project getProject() {
        MockProject project = this.projectEnvironment.getProject();
        Intrinsics.checkNotNullExpressionValue(project, "getProject(...)");
        return project;
    }

    public final int countLinesOfCode(List<? extends KtFile> sourceFiles) {
        Intrinsics.checkNotNullParameter(sourceFiles, "sourceFiles");
        Iterator<T> it = sourceFiles.iterator();
        int lineBreakCount = 0;
        while (it.hasNext()) {
            String text = ((KtFile) it.next()).getText();
            lineBreakCount += StringUtil.getLineBreakCount(text) + (!StringUtil.endsWithLineBreak(text) ? 1 : 0);
        }
        return lineBreakCount;
    }

    private final void updateClasspathFromRootsIndex(JvmDependenciesIndex index) {
        Iterator<JavaRoot> it = index.getIndexedRoots().iterator();
        while (it.hasNext()) {
            this.projectEnvironment.addSourcesToClasspath(it.next().getFile());
        }
    }

    public final List<File> updateClasspath(List<? extends ContentRoot> contentRoots) {
        List<File> list;
        Intrinsics.checkNotNullParameter(contentRoots, "contentRoots");
        List<JavaRoot> listMinus = CollectionsKt.minus((Iterable) this.classpathRootsResolver.convertClasspathRoots(contentRoots).getRoots(), (Iterable) this.initialRoots);
        if (this.packagePartProviders.isEmpty()) {
            this.initialRoots.addAll(listMinus);
        } else {
            for (JvmPackagePartProvider jvmPackagePartProvider : this.packagePartProviders) {
                Object notNull = this.configuration.getNotNull(CLIConfigurationKeys.MESSAGE_COLLECTOR_KEY);
                Intrinsics.checkNotNullExpressionValue(notNull, "getNotNull(...)");
                jvmPackagePartProvider.addRoots(listMinus, (MessageCollector) notNull);
            }
        }
        CompilerConfiguration compilerConfiguration = this.configuration;
        CompilerConfigurationKey<List<ContentRoot>> compilerConfigurationKey = CLIConfigurationKeys.CONTENT_ROOTS;
        List list2 = this.configuration.getList(CLIConfigurationKeys.CONTENT_ROOTS);
        Intrinsics.checkNotNullExpressionValue(list2, "getList(...)");
        compilerConfiguration.addAll(compilerConfigurationKey, CollectionsKt.minus((Iterable) contentRoots, (Iterable) list2));
        JvmDependenciesIndex jvmDependenciesIndexAddNewIndexForRoots = this.rootsIndex.addNewIndexForRoots(listMinus);
        if (jvmDependenciesIndexAddNewIndexForRoots != null) {
            updateClasspathFromRootsIndex(jvmDependenciesIndexAddNewIndexForRoots);
            list = SequencesKt.toList(SequencesKt.mapNotNull(jvmDependenciesIndexAddNewIndexForRoots.getIndexedRoots(), new Function1<JavaRoot, File>() { // from class: org.jetbrains.kotlin.cli.jvm.compiler.KotlinCoreEnvironment$updateClasspath$1$1
                @Override // kotlin.jvm.functions.Function1
                public final File invoke(JavaRoot javaRoot) {
                    Intrinsics.checkNotNullParameter(javaRoot, "<name for destructuring parameter 0>");
                    VirtualFile file = javaRoot.getFile();
                    VirtualFile virtualFileForJar = VfsUtilCore.getVirtualFileForJar(file);
                    if (virtualFileForJar != null) {
                        file = virtualFileForJar;
                    }
                    return VfsUtilCore.virtualToIoFile(file);
                }
            }));
        } else {
            list = null;
        }
        return list == null ? CollectionsKt.emptyList() : list;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final VirtualFile contentRootToVirtualFile(JvmContentRootBase root) {
        if (root instanceof JvmClasspathRoot) {
            JvmClasspathRoot jvmClasspathRoot = (JvmClasspathRoot) root;
            return jvmClasspathRoot.getFile().isFile() ? findJarRoot(jvmClasspathRoot.getFile()) : findExistingRoot((JvmContentRoot) root, "Classpath entry");
        }
        if (root instanceof JvmModulePathRoot) {
            JvmModulePathRoot jvmModulePathRoot = (JvmModulePathRoot) root;
            return jvmModulePathRoot.getFile().isFile() ? findJarRoot(jvmModulePathRoot.getFile()) : findExistingRoot((JvmContentRoot) root, "Java module root");
        }
        if (root instanceof JavaSourceRoot) {
            return findExistingRoot((JvmContentRoot) root, "Java source root");
        }
        if (root instanceof VirtualJvmClasspathRoot) {
            return ((VirtualJvmClasspathRoot) root).getFile();
        }
        throw new IllegalStateException("Unexpected root: " + root);
    }

    public final VirtualFile findLocalFile(String path) {
        Intrinsics.checkNotNullParameter(path, "path");
        return getApplicationEnvironment().getLocalFileSystem().findFileByPath(path);
    }

    private final VirtualFile findExistingRoot(JvmContentRoot root, String rootDescription) {
        String absolutePath = root.getFile().getAbsolutePath();
        Intrinsics.checkNotNullExpressionValue(absolutePath, "getAbsolutePath(...)");
        VirtualFile virtualFileFindLocalFile = findLocalFile(absolutePath);
        if (virtualFileFindLocalFile == null) {
            report$cli_base(CompilerMessageSeverity.STRONG_WARNING, rootDescription + " points to a non-existent location: " + root.getFile());
        }
        return virtualFileFindLocalFile;
    }

    private final VirtualFile findJarRoot(File file) {
        return this.projectEnvironment.getJarFileSystem().findFileByPath(file + "!/");
    }

    private final List<KotlinSourceRoot> getSourceRootsCheckingForDuplicates() {
        HashSet hashSet = new HashSet();
        ArrayList arrayList = new ArrayList();
        for (KotlinSourceRoot kotlinSourceRoot : ContentRootsKt.getKotlinSourceRoots(this.configuration)) {
            if (!hashSet.add(kotlinSourceRoot.getPath())) {
                report$cli_base(CompilerMessageSeverity.STRONG_WARNING, "Duplicate source root: " + kotlinSourceRoot.getPath());
            }
            arrayList.add(kotlinSourceRoot);
        }
        return arrayList;
    }

    public final List<KtFile> getSourceFiles() {
        List<ProcessSourcesBeforeCompilingExtension> instances = ProcessSourcesBeforeCompilingExtension.INSTANCE.getInstances(getProject());
        List<KtFile> list = this.sourceFiles;
        Intrinsics.checkNotNull(list, "null cannot be cast to non-null type kotlin.collections.Collection<org.jetbrains.kotlin.psi.KtFile>");
        List<KtFile> listProcessSources = list;
        Iterator<T> it = instances.iterator();
        while (it.hasNext()) {
            listProcessSources = ((ProcessSourcesBeforeCompilingExtension) it.next()).processSources(listProcessSources, this.configuration);
        }
        return CollectionsKt.toList(listProcessSources);
    }

    public final void report$cli_base(CompilerMessageSeverity severity, String message) {
        Intrinsics.checkNotNullParameter(severity, "severity");
        Intrinsics.checkNotNullParameter(message, "message");
        CoreEnvironmentUtilsKt.report$default(this.configuration, severity, message, null, 4, null);
    }

    /* JADX INFO: compiled from: KotlinCoreEnvironment.kt */
    @Metadata(d1 = {"\u0000\u008c\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J \u0010\u0012\u001a\u00020\f2\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0018H\u0002J \u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u001b\u001a\u00020\u00162\u0006\u0010\u001c\u001a\u00020\u001dH\u0007J \u0010\u001e\u001a\u00020\u001a2\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u001f\u001a\u00020\u001dH\u0007J \u0010\u001e\u001a\u00020\u001a2\u0006\u0010 \u001a\u00020!2\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u001f\u001a\u00020\u001dH\u0007J \u0010\"\u001a\u00020\u001a2\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u001b\u001a\u00020\u00162\u0006\u0010\u001c\u001a\u00020\u001dH\u0007J \u0010\"\u001a\u00020\u001a2\u0006\u0010 \u001a\u00020!2\u0006\u0010\u001b\u001a\u00020\u00162\u0006\u0010\u001c\u001a\u00020\u001dH\u0007J\u0016\u0010#\u001a\u00020!2\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0016J\b\u0010$\u001a\u00020%H\u0007J \u0010&\u001a\u00020\f2\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0018H\u0002J\u0016\u0010'\u001a\u00020\f2\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0016J\u0016\u0010(\u001a\u00020\f2\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0016J\u0018\u0010)\u001a\u00020%2\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010*\u001a\u00020+H\u0002J\u0010\u0010,\u001a\u00020%2\u0006\u0010\u000b\u001a\u00020\fH\u0007J\u0010\u0010-\u001a\u00020%2\u0006\u0010\u000b\u001a\u00020\fH\u0002J\u001d\u0010.\u001a\u00020%2\u0006\u0010/\u001a\u0002002\u0006\u0010\u0015\u001a\u00020\u0016H\u0000¢\u0006\u0002\b1J\u0010\u00102\u001a\u00020%2\u0006\u0010/\u001a\u000200H\u0007J\u0010\u00103\u001a\u00020%2\u0006\u0010/\u001a\u000200H\u0007J\u0010\u00104\u001a\u00020%2\u0006\u00105\u001a\u000206H\u0007J\u001a\u00107\u001a\u00020%2\u0006\u0010 \u001a\u0002082\b\u00109\u001a\u0004\u0018\u00010:H\u0007J\u0010\u00107\u001a\u00020%2\u0006\u0010/\u001a\u000200H\u0007J\u000e\u0010;\u001a\u00020%2\u0006\u0010 \u001a\u000208J%\u0010<\u001a\u0002H=\"\u0004\b\u0000\u0010=2\f\u0010>\u001a\b\u0012\u0004\u0012\u0002H=0?H\u0086\bø\u0001\u0000¢\u0006\u0002\u0010@J\u001c\u0010A\u001a\u00020%*\u00020!2\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u001f\u001a\u00020\u001dH\u0007R\u001c\u0010\u0003\u001a\u00020\u00048\u0000X\u0081\u0004¢\u0006\u000e\n\u0000\u0012\u0004\b\u0005\u0010\u0002\u001a\u0004\b\u0006\u0010\u0007R\u0013\u0010\b\u001a\u00070\t¢\u0006\u0002\b\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u0013\u0010\u000b\u001a\u0004\u0018\u00010\f8F¢\u0006\u0006\u001a\u0004\b\r\u0010\u000eR\u0010\u0010\u000f\u001a\u0004\u0018\u00010\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u000e¢\u0006\u0002\n\u0000\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006B"}, d2 = {"Lorg/jetbrains/kotlin/cli/jvm/compiler/KotlinCoreEnvironment$Companion;", "", "()V", "APPLICATION_LOCK", TypeProxy.SilentConstruction.Appender.JAVA_LANG_OBJECT_DESCRIPTOR, "getAPPLICATION_LOCK$annotations", "getAPPLICATION_LOCK", "()Ljava/lang/Object;", "LOG", "Lorg/jetbrains/kotlin/com/intellij/openapi/diagnostic/Logger;", "Lorg/jetbrains/annotations/NotNull;", "applicationEnvironment", "Lorg/jetbrains/kotlin/cli/jvm/compiler/KotlinCoreApplicationEnvironment;", "getApplicationEnvironment", "()Lorg/jetbrains/kotlin/cli/jvm/compiler/KotlinCoreApplicationEnvironment;", "ourApplicationEnvironment", "ourProjectCount", "", "createApplicationEnvironment", "parentDisposable", "Lorg/jetbrains/kotlin/com/intellij/openapi/Disposable;", "configuration", "Lorg/jetbrains/kotlin/config/CompilerConfiguration;", "unitTestMode", "", "createForParallelTests", "Lorg/jetbrains/kotlin/cli/jvm/compiler/KotlinCoreEnvironment;", "initialConfiguration", "extensionConfigs", "Lorg/jetbrains/kotlin/cli/jvm/compiler/EnvironmentConfigFiles;", "createForProduction", "configFiles", "projectEnvironment", "Lorg/jetbrains/kotlin/cli/jvm/compiler/KotlinCoreEnvironment$ProjectEnvironment;", "createForTests", "createProjectEnvironmentForTests", "disposeApplicationEnvironment", "", "getOrCreateApplicationEnvironment", "getOrCreateApplicationEnvironmentForProduction", "getOrCreateApplicationEnvironmentForTests", "registerApplicationExtensionPointsAndExtensionsFrom", "configFilePath", "", "registerApplicationServices", "registerApplicationServicesForCLI", "registerExtensionsFromPlugins", "project", "Lorg/jetbrains/kotlin/com/intellij/mock/MockProject;", "registerExtensionsFromPlugins$cli_base", "registerKotlinLightClassSupport", "registerPluginExtensionPoints", "registerProjectExtensionPoints", "area", "Lorg/jetbrains/kotlin/com/intellij/openapi/extensions/ExtensionsArea;", "registerProjectServices", "Lorg/jetbrains/kotlin/com/intellij/core/JavaCoreProjectEnvironment;", "messageCollector", "Lorg/jetbrains/kotlin/cli/common/messages/MessageCollector;", "registerProjectServicesForCLI", "underApplicationLock", "R", "action", "Lkotlin/Function0;", "(Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "configureProjectEnvironment", "cli-base"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public static /* synthetic */ void getAPPLICATION_LOCK$annotations() {
        }

        public final void registerProjectServicesForCLI(JavaCoreProjectEnvironment projectEnvironment) {
            Intrinsics.checkNotNullParameter(projectEnvironment, "projectEnvironment");
        }

        private Companion() {
        }

        public final Object getAPPLICATION_LOCK() {
            return KotlinCoreEnvironment.APPLICATION_LOCK;
        }

        public final <R> R underApplicationLock(Function0<? extends R> action) {
            R rInvoke;
            Intrinsics.checkNotNullParameter(action, "action");
            synchronized (getAPPLICATION_LOCK()) {
                try {
                    rInvoke = action.invoke();
                    InlineMarker.finallyStart(1);
                } finally {
                    InlineMarker.finallyStart(1);
                    InlineMarker.finallyEnd(1);
                }
            }
            return rInvoke;
        }

        @JvmStatic
        public final KotlinCoreEnvironment createForProduction(Disposable parentDisposable, CompilerConfiguration configuration, EnvironmentConfigFiles configFiles) {
            Intrinsics.checkNotNullParameter(parentDisposable, "parentDisposable");
            Intrinsics.checkNotNullParameter(configuration, "configuration");
            Intrinsics.checkNotNullParameter(configFiles, "configFiles");
            CompatKt.setupIdeaStandaloneExecution();
            return new KotlinCoreEnvironment(new ProjectEnvironment(parentDisposable, getOrCreateApplicationEnvironmentForProduction(parentDisposable, configuration), configuration), configuration, configFiles, null);
        }

        @JvmStatic
        public final KotlinCoreEnvironment createForProduction(ProjectEnvironment projectEnvironment, CompilerConfiguration configuration, EnvironmentConfigFiles configFiles) {
            Intrinsics.checkNotNullParameter(projectEnvironment, "projectEnvironment");
            Intrinsics.checkNotNullParameter(configuration, "configuration");
            Intrinsics.checkNotNullParameter(configFiles, "configFiles");
            return new KotlinCoreEnvironment(projectEnvironment, configuration, configFiles, null);
        }

        @JvmStatic
        public final KotlinCoreEnvironment createForTests(Disposable parentDisposable, CompilerConfiguration initialConfiguration, EnvironmentConfigFiles extensionConfigs) {
            Intrinsics.checkNotNullParameter(parentDisposable, "parentDisposable");
            Intrinsics.checkNotNullParameter(initialConfiguration, "initialConfiguration");
            Intrinsics.checkNotNullParameter(extensionConfigs, "extensionConfigs");
            CompilerConfiguration compilerConfigurationCopy = initialConfiguration.copy();
            Intrinsics.checkNotNull(compilerConfigurationCopy);
            return new KotlinCoreEnvironment(new ProjectEnvironment(parentDisposable, createApplicationEnvironment(parentDisposable, compilerConfigurationCopy, true), compilerConfigurationCopy), compilerConfigurationCopy, extensionConfigs, null);
        }

        @JvmStatic
        public final KotlinCoreEnvironment createForParallelTests(Disposable parentDisposable, CompilerConfiguration initialConfiguration, EnvironmentConfigFiles extensionConfigs) {
            Intrinsics.checkNotNullParameter(parentDisposable, "parentDisposable");
            Intrinsics.checkNotNullParameter(initialConfiguration, "initialConfiguration");
            Intrinsics.checkNotNullParameter(extensionConfigs, "extensionConfigs");
            CompilerConfiguration compilerConfigurationCopy = initialConfiguration.copy();
            Intrinsics.checkNotNull(compilerConfigurationCopy);
            return new KotlinCoreEnvironment(new ProjectEnvironment(parentDisposable, getOrCreateApplicationEnvironmentForTests(parentDisposable, compilerConfigurationCopy), compilerConfigurationCopy), compilerConfigurationCopy, extensionConfigs, null);
        }

        @JvmStatic
        public final KotlinCoreEnvironment createForTests(ProjectEnvironment projectEnvironment, CompilerConfiguration initialConfiguration, EnvironmentConfigFiles extensionConfigs) {
            Intrinsics.checkNotNullParameter(projectEnvironment, "projectEnvironment");
            Intrinsics.checkNotNullParameter(initialConfiguration, "initialConfiguration");
            Intrinsics.checkNotNullParameter(extensionConfigs, "extensionConfigs");
            return new KotlinCoreEnvironment(projectEnvironment, initialConfiguration, extensionConfigs, null);
        }

        public final ProjectEnvironment createProjectEnvironmentForTests(Disposable parentDisposable, CompilerConfiguration configuration) {
            Intrinsics.checkNotNullParameter(parentDisposable, "parentDisposable");
            Intrinsics.checkNotNullParameter(configuration, "configuration");
            return new ProjectEnvironment(parentDisposable, createApplicationEnvironment(parentDisposable, configuration, true), configuration);
        }

        public final KotlinCoreApplicationEnvironment getApplicationEnvironment() {
            return KotlinCoreEnvironment.ourApplicationEnvironment;
        }

        public final KotlinCoreApplicationEnvironment getOrCreateApplicationEnvironmentForProduction(Disposable parentDisposable, CompilerConfiguration configuration) {
            Intrinsics.checkNotNullParameter(parentDisposable, "parentDisposable");
            Intrinsics.checkNotNullParameter(configuration, "configuration");
            return getOrCreateApplicationEnvironment(parentDisposable, configuration, false);
        }

        public final KotlinCoreApplicationEnvironment getOrCreateApplicationEnvironmentForTests(Disposable parentDisposable, CompilerConfiguration configuration) {
            Intrinsics.checkNotNullParameter(parentDisposable, "parentDisposable");
            Intrinsics.checkNotNullParameter(configuration, "configuration");
            return getOrCreateApplicationEnvironment(parentDisposable, configuration, true);
        }

        private final KotlinCoreApplicationEnvironment getOrCreateApplicationEnvironment(Disposable parentDisposable, CompilerConfiguration configuration, boolean unitTestMode) {
            KotlinCoreApplicationEnvironment kotlinCoreApplicationEnvironment;
            synchronized (getAPPLICATION_LOCK()) {
                if (KotlinCoreEnvironment.ourApplicationEnvironment == null) {
                    Disposable disposableNewDisposable = Disposer.newDisposable("Disposable for the KotlinCoreApplicationEnvironment");
                    Intrinsics.checkNotNullExpressionValue(disposableNewDisposable, "newDisposable(...)");
                    Companion companion = KotlinCoreEnvironment.INSTANCE;
                    KotlinCoreEnvironment.ourApplicationEnvironment = KotlinCoreEnvironment.INSTANCE.createApplicationEnvironment(disposableNewDisposable, configuration, unitTestMode);
                    Companion companion2 = KotlinCoreEnvironment.INSTANCE;
                    KotlinCoreEnvironment.ourProjectCount = 0;
                    Disposer.register(disposableNewDisposable, new Disposable() { // from class: org.jetbrains.kotlin.cli.jvm.compiler.KotlinCoreEnvironment$Companion$$ExternalSyntheticLambda0
                        @Override // org.jetbrains.kotlin.com.intellij.openapi.Disposable
                        public final void dispose() {
                            KotlinCoreEnvironment.Companion.getOrCreateApplicationEnvironment$lambda$3$lambda$2();
                        }
                    });
                }
                try {
                    final boolean z = !Intrinsics.areEqual((Object) PropertiesKt.toBooleanLenient(CompilerSystemProperties.KOTLIN_COMPILER_ENVIRONMENT_KEEPALIVE_PROPERTY.getValue()), (Object) true);
                    Disposer.register(parentDisposable, new Disposable() { // from class: org.jetbrains.kotlin.cli.jvm.compiler.KotlinCoreEnvironment$Companion$getOrCreateApplicationEnvironment$1$2
                        @Override // org.jetbrains.kotlin.com.intellij.openapi.Disposable
                        public void dispose() {
                            Object application_lock = KotlinCoreEnvironment.INSTANCE.getAPPLICATION_LOCK();
                            boolean z2 = z;
                            synchronized (application_lock) {
                                KotlinCoreEnvironment.Companion companion3 = KotlinCoreEnvironment.INSTANCE;
                                KotlinCoreEnvironment.ourProjectCount--;
                                if (KotlinCoreEnvironment.ourProjectCount <= 0) {
                                    if (!z2) {
                                        KotlinCoreApplicationEnvironment kotlinCoreApplicationEnvironment2 = KotlinCoreEnvironment.ourApplicationEnvironment;
                                        if (kotlinCoreApplicationEnvironment2 != null) {
                                            kotlinCoreApplicationEnvironment2.idleCleanup();
                                        }
                                    } else {
                                        KotlinCoreEnvironment.INSTANCE.disposeApplicationEnvironment();
                                    }
                                }
                                Unit unit = Unit.INSTANCE;
                            }
                        }
                    });
                    Companion companion3 = KotlinCoreEnvironment.INSTANCE;
                    KotlinCoreEnvironment.ourProjectCount++;
                    kotlinCoreApplicationEnvironment = KotlinCoreEnvironment.ourApplicationEnvironment;
                    Intrinsics.checkNotNull(kotlinCoreApplicationEnvironment);
                } catch (Throwable th) {
                    Companion companion4 = KotlinCoreEnvironment.INSTANCE;
                    KotlinCoreEnvironment.ourProjectCount++;
                    throw th;
                }
            }
            return kotlinCoreApplicationEnvironment;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void getOrCreateApplicationEnvironment$lambda$3$lambda$2() {
            synchronized (KotlinCoreEnvironment.INSTANCE.getAPPLICATION_LOCK()) {
                Companion companion = KotlinCoreEnvironment.INSTANCE;
                KotlinCoreEnvironment.ourApplicationEnvironment = null;
                Unit unit = Unit.INSTANCE;
            }
        }

        @JvmStatic
        public final void disposeApplicationEnvironment() {
            synchronized (getAPPLICATION_LOCK()) {
                KotlinCoreApplicationEnvironment kotlinCoreApplicationEnvironment = KotlinCoreEnvironment.ourApplicationEnvironment;
                if (kotlinCoreApplicationEnvironment == null) {
                    return;
                }
                Companion companion = KotlinCoreEnvironment.INSTANCE;
                KotlinCoreEnvironment.ourApplicationEnvironment = null;
                Disposer.dispose(kotlinCoreApplicationEnvironment.getParentDisposable());
                ZipHandler.clearFileAccessorCache();
                Unit unit = Unit.INSTANCE;
            }
        }

        @JvmStatic
        public final void configureProjectEnvironment(ProjectEnvironment projectEnvironment, CompilerConfiguration configuration, EnvironmentConfigFiles configFiles) throws IllegalAccessException, NoSuchFieldException {
            Intrinsics.checkNotNullParameter(projectEnvironment, "<this>");
            Intrinsics.checkNotNullParameter(configuration, "configuration");
            Intrinsics.checkNotNullParameter(configFiles, "configFiles");
            Field declaredField = PersistentFSConstants.class.getDeclaredField("ourMaxIntellisenseFileSize");
            declaredField.setAccessible(true);
            declaredField.setInt(null, FileUtilRt.LARGE_FOR_CONTENT_LOADING);
            projectEnvironment.registerExtensionsFromPlugins(configuration);
            projectEnvironment.getProject().registerService((Class<CliModuleVisibilityManagerImpl>) ModuleVisibilityManager.class, new CliModuleVisibilityManagerImpl(configFiles == EnvironmentConfigFiles.JVM_CONFIG_FILES));
            registerProjectServicesForCLI(projectEnvironment);
            MockProject project = projectEnvironment.getProject();
            Intrinsics.checkNotNullExpressionValue(project, "getProject(...)");
            registerProjectServices(project);
            CompilerConfigurationExtension.Companion companion = CompilerConfigurationExtension.INSTANCE;
            MockProject project2 = projectEnvironment.getProject();
            Intrinsics.checkNotNullExpressionValue(project2, "getProject(...)");
            Iterator<CompilerConfigurationExtension> it = companion.getInstances(project2).iterator();
            while (it.hasNext()) {
                it.next().updateConfiguration(configuration);
            }
        }

        private final KotlinCoreApplicationEnvironment createApplicationEnvironment(Disposable parentDisposable, CompilerConfiguration configuration, boolean unitTestMode) {
            KotlinCoreApplicationEnvironment kotlinCoreApplicationEnvironmentCreate = KotlinCoreApplicationEnvironment.INSTANCE.create(parentDisposable, unitTestMode);
            registerApplicationExtensionPointsAndExtensionsFrom(configuration, "extensions/compiler.xml");
            registerApplicationServicesForCLI(kotlinCoreApplicationEnvironmentCreate);
            registerApplicationServices(kotlinCoreApplicationEnvironmentCreate);
            return kotlinCoreApplicationEnvironmentCreate;
        }

        private static final boolean registerApplicationExtensionPointsAndExtensionsFrom$hasConfigFile(File file, String str) {
            if (file.isDirectory()) {
                return new File(file, "META-INF" + File.separator + str).exists();
            }
            try {
                ZipFile zipFile = new ZipFile(file);
                try {
                    boolean z = zipFile.getEntry(new StringBuilder("META-INF/").append(str).toString()) != null;
                    CloseableKt.closeFinally(zipFile, null);
                    return z;
                } catch (Throwable th) {
                    try {
                        throw th;
                    } catch (Throwable th2) {
                        CloseableKt.closeFinally(zipFile, th);
                        throw th2;
                    }
                }
            } catch (Throwable unused) {
                return false;
            }
        }

        /* JADX WARN: Code duplicated, block: B:32:0x009e A[PHI: r0
          0x009e: PHI (r0v6 java.io.File) = (r0v5 java.io.File), (r0v8 java.io.File) binds: [B:9:0x0022, B:14:0x0033] A[DONT_GENERATE, DONT_INLINE]] */
        private final void registerApplicationExtensionPointsAndExtensionsFrom(CompilerConfiguration configuration, String configFilePath) {
            File file;
            List<URL> urls;
            String str = (String) configuration.get(CLIConfigurationKeys.INTELLIJ_PLUGIN_ROOT);
            if (str != null) {
                file = new File(str);
            } else {
                File resourcePathForClass = PathUtil.getResourcePathForClass(getClass());
                String strJoinToString$default = null;
                if (!registerApplicationExtensionPointsAndExtensionsFrom$hasConfigFile(resourcePathForClass, configFilePath)) {
                    resourcePathForClass = null;
                }
                if (resourcePathForClass == null) {
                    resourcePathForClass = new File("compiler/cli/cli-common/resources");
                    if (!registerApplicationExtensionPointsAndExtensionsFrom$hasConfigFile(resourcePathForClass, configFilePath)) {
                        resourcePathForClass = null;
                    }
                    if (resourcePathForClass == null) {
                        file = (File) configuration.get(CLIConfigurationKeys.PATH_TO_KOTLIN_COMPILER_JAR);
                        if (file == null || !registerApplicationExtensionPointsAndExtensionsFrom$hasConfigFile(file, configFilePath)) {
                            file = null;
                        }
                        if (file == null) {
                            StringBuilder sbAppend = new StringBuilder("Unable to find extension point configuration ").append(configFilePath).append(" (cp:\n  ");
                            ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
                            UrlClassLoader urlClassLoader = contextClassLoader instanceof UrlClassLoader ? (UrlClassLoader) contextClassLoader : null;
                            if (urlClassLoader != null && (urls = urlClassLoader.getUrls()) != null) {
                                strJoinToString$default = CollectionsKt.joinToString$default(urls, "\n  ", null, null, 0, null, new Function1<URL, CharSequence>() { // from class: org.jetbrains.kotlin.cli.jvm.compiler.KotlinCoreEnvironment$Companion$registerApplicationExtensionPointsAndExtensionsFrom$pluginRoot$5
                                    @Override // kotlin.jvm.functions.Function1
                                    public final CharSequence invoke(URL url) {
                                        String file2 = url.getFile();
                                        Intrinsics.checkNotNullExpressionValue(file2, "getFile(...)");
                                        return file2;
                                    }
                                }, 30, null);
                            }
                            throw new IllegalStateException(sbAppend.append(strJoinToString$default).append(')').toString());
                        }
                    } else {
                        file = resourcePathForClass;
                    }
                } else {
                    file = resourcePathForClass;
                }
            }
            CoreApplicationEnvironment.registerExtensionPointAndExtensions(FileSystems.getDefault().getPath(file.getPath(), new String[0]), configFilePath, ApplicationManager.getApplication().getExtensionArea());
        }

        @JvmStatic
        public final void registerPluginExtensionPoints(MockProject project) {
            Intrinsics.checkNotNullParameter(project, "project");
            MockProject mockProject = project;
            ExpressionCodegenExtension.INSTANCE.registerExtensionPoint(mockProject);
            SyntheticResolveExtension.INSTANCE.registerExtensionPoint(mockProject);
            SyntheticJavaResolveExtension.INSTANCE.registerExtensionPoint(mockProject);
            ClassBuilderInterceptorExtension.INSTANCE.registerExtensionPoint(mockProject);
            ClassGeneratorExtension.INSTANCE.registerExtensionPoint(mockProject);
            ClassFileFactoryFinalizerExtension.INSTANCE.registerExtensionPoint(mockProject);
            AnalysisHandlerExtension.INSTANCE.registerExtensionPoint(mockProject);
            PackageFragmentProviderExtension.INSTANCE.registerExtensionPoint(mockProject);
            StorageComponentContainerContributor.INSTANCE.registerExtensionPoint(mockProject);
            DeclarationAttributeAltererExtension.INSTANCE.registerExtensionPoint(mockProject);
            PreprocessedVirtualFileFactoryExtension.INSTANCE.registerExtensionPoint(mockProject);
            JsSyntheticTranslateExtension.INSTANCE.registerExtensionPoint(mockProject);
            CompilerConfigurationExtension.INSTANCE.registerExtensionPoint(mockProject);
            CollectAdditionalSourcesExtension.INSTANCE.registerExtensionPoint(mockProject);
            ProcessSourcesBeforeCompilingExtension.INSTANCE.registerExtensionPoint(mockProject);
            ExtraImportsProviderExtension.INSTANCE.registerExtensionPoint(mockProject);
            IrGenerationExtension.INSTANCE.registerExtensionPoint(mockProject);
            ScriptEvaluationExtension.INSTANCE.registerExtensionPoint(mockProject);
            ShellExtension.INSTANCE.registerExtensionPoint(mockProject);
            TypeResolutionInterceptor.INSTANCE.registerExtensionPoint(mockProject);
            CandidateInterceptor.INSTANCE.registerExtensionPoint(mockProject);
            DescriptorSerializerPlugin.INSTANCE.registerExtensionPoint(mockProject);
            FirExtensionRegistrarAdapter.INSTANCE.registerExtensionPoint(mockProject);
            TypeAttributeTranslatorExtension.INSTANCE.registerExtensionPoint(mockProject);
            AssignResolutionAltererExtension.INSTANCE.registerExtensionPoint(mockProject);
            FirAnalysisHandlerExtension.INSTANCE.registerExtensionPoint(mockProject);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final String registerExtensionsFromPlugins$createErrorMessage(Object obj) {
            return "The provided plugin " + obj.getClass().getName() + " is not compatible with this version of compiler";
        }

        public final void registerExtensionsFromPlugins$cli_base(MockProject project, CompilerConfiguration configuration) {
            Intrinsics.checkNotNullParameter(project, "project");
            Intrinsics.checkNotNullParameter(configuration, "configuration");
            MessageCollector messageCollector = (MessageCollector) configuration.get(CLIConfigurationKeys.MESSAGE_COLLECTOR_KEY);
            for (ComponentRegistrar componentRegistrar : configuration.getList(ComponentRegistrar.INSTANCE.getPLUGIN_COMPONENT_REGISTRARS())) {
                try {
                    componentRegistrar.registerProjectComponents(project, configuration);
                } catch (AbstractMethodError e) {
                    Intrinsics.checkNotNull(componentRegistrar);
                    String strRegisterExtensionsFromPlugins$createErrorMessage = registerExtensionsFromPlugins$createErrorMessage(componentRegistrar);
                    if (!Intrinsics.areEqual(componentRegistrar.getClass().getSimpleName(), "ScriptingCompilerConfigurationComponentRegistrar")) {
                        String str = strRegisterExtensionsFromPlugins$createErrorMessage + ".\n" + CollectionsKt.joinToString$default(CollectionsKt.take(StringsKt.lines(ExceptionsKt.stackTraceToString(e)), 6), "\n", null, null, 0, null, null, 62, null);
                        if (messageCollector != null) {
                            MessageCollector.report$default(messageCollector, CompilerMessageSeverity.ERROR, str, null, 4, null);
                        }
                    } else if (messageCollector != null) {
                        MessageCollector.report$default(messageCollector, CompilerMessageSeverity.STRONG_WARNING, "Default scripting plugin is disabled: " + strRegisterExtensionsFromPlugins$createErrorMessage, null, 4, null);
                    }
                }
            }
            CompilerPluginRegistrar.ExtensionStorage extensionStorage = new CompilerPluginRegistrar.ExtensionStorage();
            Iterator it = configuration.getList(CompilerPluginRegistrar.INSTANCE.getCOMPILER_PLUGIN_REGISTRARS()).iterator();
            while (it.hasNext()) {
                ((CompilerPluginRegistrar) it.next()).registerExtensions(extensionStorage, configuration);
            }
            CompilerPluginRegistrarKt.registerInProject(extensionStorage, project, new Function1<Object, String>() { // from class: org.jetbrains.kotlin.cli.jvm.compiler.KotlinCoreEnvironment$Companion$registerExtensionsFromPlugins$2
                @Override // kotlin.jvm.functions.Function1
                public final String invoke(Object it2) {
                    Intrinsics.checkNotNullParameter(it2, "it");
                    return KotlinCoreEnvironment.Companion.registerExtensionsFromPlugins$createErrorMessage(it2);
                }
            });
        }

        private final void registerApplicationServicesForCLI(KotlinCoreApplicationEnvironment applicationEnvironment) {
            applicationEnvironment.registerFileType(PlainTextFileType.INSTANCE, "xml");
            applicationEnvironment.registerParserDefinition(new JavaParserDefinition());
        }

        @JvmStatic
        public final void registerApplicationServices(KotlinCoreApplicationEnvironment applicationEnvironment) {
            Intrinsics.checkNotNullParameter(applicationEnvironment, "applicationEnvironment");
            applicationEnvironment.registerFileType(KotlinFileType.INSTANCE, KotlinFileType.EXTENSION);
            applicationEnvironment.registerFileType(KotlinFileType.INSTANCE, KotlinParserDefinition.STD_SCRIPT_SUFFIX);
            applicationEnvironment.registerParserDefinition(new KotlinParserDefinition());
            applicationEnvironment.getApplication().registerService((Class<KotlinBinaryClassCache>) KotlinBinaryClassCache.class, new KotlinBinaryClassCache());
            applicationEnvironment.getApplication().registerService(JavaClassSupers.class, JavaClassSupersImpl.class);
            applicationEnvironment.getApplication().registerService(TransactionGuard.class, TransactionGuardImpl.class);
        }

        @JvmStatic
        public final void registerProjectExtensionPoints(ExtensionsArea area) {
            Intrinsics.checkNotNullParameter(area, "area");
            CoreApplicationEnvironment.registerExtensionPoint(area, PsiTreeChangePreprocessor.EP.getName(), PsiTreeChangePreprocessor.class);
            CoreApplicationEnvironment.registerExtensionPoint(area, PsiElementFinder.EP.getName(), PsiElementFinder.class);
            IdeaExtensionPoints.INSTANCE.registerVersionSpecificProjectExtensionPoints(area);
        }

        @Deprecated(message = "Use registerProjectServices(project) instead.", replaceWith = @ReplaceWith(expression = "registerProjectServices(projectEnvironment.project)", imports = {}))
        @JvmStatic
        public final void registerProjectServices(JavaCoreProjectEnvironment projectEnvironment, MessageCollector messageCollector) {
            Intrinsics.checkNotNullParameter(projectEnvironment, "projectEnvironment");
            MockProject project = projectEnvironment.getProject();
            Intrinsics.checkNotNullExpressionValue(project, "getProject(...)");
            registerProjectServices(project);
        }

        @JvmStatic
        public final void registerProjectServices(MockProject project) {
            Intrinsics.checkNotNullParameter(project, "project");
            project.registerService(JavaElementSourceFactory.class, JavaFixedElementSourceFactory.class);
            project.registerService((Class<KotlinJavaPsiFacade>) KotlinJavaPsiFacade.class, new KotlinJavaPsiFacade(project));
            project.registerService((Class<CliModuleAnnotationsResolver>) ModuleAnnotationsResolver.class, new CliModuleAnnotationsResolver());
        }

        @JvmStatic
        public final void registerKotlinLightClassSupport(MockProject project) {
            Intrinsics.checkNotNullParameter(project, "project");
            CliTraceHolder cliTraceHolder = new CliTraceHolder();
            MockProject mockProject = project;
            CliLightClassGenerationSupport cliLightClassGenerationSupport = new CliLightClassGenerationSupport(cliTraceHolder, mockProject);
            CliKotlinAsJavaSupport cliKotlinAsJavaSupport = new CliKotlinAsJavaSupport(mockProject, cliTraceHolder);
            project.registerService((Class<CliLightClassGenerationSupport>) LightClassGenerationSupport.class, cliLightClassGenerationSupport);
            project.registerService((Class<CliLightClassGenerationSupport>) CliLightClassGenerationSupport.class, cliLightClassGenerationSupport);
            project.registerService((Class<CliKotlinAsJavaSupport>) KotlinAsJavaSupport.class, cliKotlinAsJavaSupport);
            project.registerService((Class<CliTraceHolder>) CodeAnalyzerInitializer.class, cliTraceHolder);
            MockProject mockProject2 = project;
            PsiElementFinder.EP.getPoint(mockProject2).registerExtension(new JavaElementFinder(mockProject));
            PsiElementFinder.EP.getPoint(mockProject2).registerExtension(new PsiElementFinderImpl(mockProject));
        }
    }

    static {
        Logger logger = Logger.getInstance((Class<?>) KotlinCoreEnvironment.class);
        Intrinsics.checkNotNullExpressionValue(logger, "getInstance(...)");
        LOG = logger;
        APPLICATION_LOCK = new Object();
    }
}

package org.jetbrains.kotlin.cli.jvm.compiler;

import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.jar.Attributes;
import java.util.jar.Manifest;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.io.FilesKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.SequencesKt;
import kotlin.text.StringsKt;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.cli.common.config.ContentRoot;
import org.jetbrains.kotlin.cli.common.messages.CompilerMessageLocation;
import org.jetbrains.kotlin.cli.common.messages.CompilerMessageSeverity;
import org.jetbrains.kotlin.cli.common.messages.MessageCollector;
import org.jetbrains.kotlin.cli.common.messages.MessageUtil;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.cli.jvm.config.JavaSourceRoot;
import org.jetbrains.kotlin.cli.jvm.config.JvmClasspathRootBase;
import org.jetbrains.kotlin.cli.jvm.config.JvmContentRootBase;
import org.jetbrains.kotlin.cli.jvm.config.JvmModulePathRoot;
import org.jetbrains.kotlin.cli.jvm.index.JavaRoot;
import org.jetbrains.kotlin.cli.jvm.modules.CliJavaModuleFinder;
import org.jetbrains.kotlin.cli.jvm.modules.JavaModuleGraph;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VfsUtilCore;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiJavaModule;
import org.jetbrains.kotlin.com.intellij.psi.PsiManager;
import org.jetbrains.kotlin.com.intellij.psi.PsiTreeChangeEvent;
import org.jetbrains.kotlin.com.intellij.psi.impl.light.LightJavaModule;
import org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.library.KotlinLibraryLayoutKt;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.FqNamesUtilKt;
import org.jetbrains.kotlin.resolve.jvm.KotlinCliJavaFileManager;
import org.jetbrains.kotlin.resolve.jvm.modules.JavaModule;
import org.jetbrains.kotlin.resolve.jvm.modules.JavaModuleInfo;
import org.jetbrains.kotlin.resolve.jvm.modules.JavaModuleKt;

/* JADX INFO: compiled from: ClasspathRootsResolver.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000²\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u0000 B2\u00020\u0001:\u0003BCDBg\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007\u0012\u0014\u0010\t\u001a\u0010\u0012\u0004\u0012\u00020\u000b\u0012\u0006\u0012\u0004\u0018\u00010\f0\n\u0012\u0006\u0010\r\u001a\u00020\u000e\u0012\u0006\u0010\u000f\u001a\u00020\u0010\u0012\b\u0010\u0011\u001a\u0004\u0018\u00010\f\u0012\u0006\u0010\u0012\u001a\u00020\u0013\u0012\b\u0010\u0014\u001a\u0004\u0018\u00010\u0015¢\u0006\u0002\u0010\u0016J$\u0010\u001f\u001a\u00020 2\f\u0010!\u001a\b\u0012\u0004\u0012\u00020\"0\u00072\f\u0010#\u001a\b\u0012\u0004\u0012\u00020%0$H\u0002J2\u0010&\u001a\u00020'2\f\u0010(\u001a\b\u0012\u0004\u0012\u00020)0\u00072\f\u0010*\u001a\b\u0012\u0004\u0012\u00020\f0\u00072\f\u0010+\u001a\b\u0012\u0004\u0012\u00020\f0\u0007H\u0002J\u0014\u0010,\u001a\u00020'2\f\u0010-\u001a\b\u0012\u0004\u0012\u00020.0\u0007J\u001e\u0010/\u001a\u0010\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u000201\u0018\u0001002\u0006\u00102\u001a\u00020\fH\u0002J\u0012\u00103\u001a\u0004\u0018\u00010\"2\u0006\u00102\u001a\u00020\fH\u0002J\u001a\u00104\u001a\u0004\u0018\u0001052\u0006\u00102\u001a\u00020\f2\u0006\u00106\u001a\u00020\u0010H\u0002J\u0012\u00107\u001a\u0004\u0018\u0001082\u0006\u00109\u001a\u00020\fH\u0002J$\u0010:\u001a\u00020 2\u0006\u0010;\u001a\u00020<2\u0006\u0010=\u001a\u00020\b2\n\b\u0002\u0010>\u001a\u0004\u0018\u00010\fH\u0002J\"\u0010?\u001a\u0004\u0018\u00010\f2\u0006\u00102\u001a\u00020\f2\u000e\u0010@\u001a\n\u0012\u0006\u0012\u0004\u0018\u0001080AH\u0002R\u0014\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u001c\u0010\t\u001a\u0010\u0012\u0004\u0012\u00020\u000b\u0012\u0006\u0012\u0004\u0018\u00010\f0\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0013X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0017\u001a\u00020\u0018¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001aR\u0012\u0010\u0014\u001a\u0004\u0018\u00010\u0015X\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u001bR\u0010\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0011\u001a\u0004\u0018\u00010\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u0004¢\u0006\u0002\n\u0000R\u0013\u0010\u001c\u001a\u00070\u001d¢\u0006\u0002\b\u001eX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006E"}, d2 = {"Lorg/jetbrains/kotlin/cli/jvm/compiler/ClasspathRootsResolver;", "", "psiManager", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiManager;", "messageCollector", "Lorg/jetbrains/kotlin/cli/common/messages/MessageCollector;", "additionalModules", "", "", "contentRootToVirtualFile", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/cli/jvm/config/JvmContentRootBase;", "Lorg/jetbrains/kotlin/com/intellij/openapi/vfs/VirtualFile;", "javaModuleFinder", "Lorg/jetbrains/kotlin/cli/jvm/modules/CliJavaModuleFinder;", "requireStdlibModule", "", "outputDirectory", "javaFileManager", "Lorg/jetbrains/kotlin/resolve/jvm/KotlinCliJavaFileManager;", "jdkRelease", "", "(Lcom/intellij/psi/PsiManager;Lorg/jetbrains/kotlin/cli/common/messages/MessageCollector;Ljava/util/List;Lkotlin/jvm/functions/Function1;Lorg/jetbrains/kotlin/cli/jvm/modules/CliJavaModuleFinder;ZLcom/intellij/openapi/vfs/VirtualFile;Lorg/jetbrains/kotlin/resolve/jvm/KotlinCliJavaFileManager;Ljava/lang/Integer;)V", "javaModuleGraph", "Lorg/jetbrains/kotlin/cli/jvm/modules/JavaModuleGraph;", "getJavaModuleGraph", "()Lorg/jetbrains/kotlin/cli/jvm/modules/JavaModuleGraph;", "Ljava/lang/Integer;", "searchScope", "Lorg/jetbrains/kotlin/com/intellij/psi/search/GlobalSearchScope;", "Lorg/jetbrains/annotations/NotNull;", "addModularRoots", "", "modules", "Lorg/jetbrains/kotlin/resolve/jvm/modules/JavaModule;", "result", "", "Lorg/jetbrains/kotlin/cli/jvm/index/JavaRoot;", "computeRoots", "Lorg/jetbrains/kotlin/cli/jvm/compiler/ClasspathRootsResolver$RootsAndModules;", ModuleXmlParser.JAVA_SOURCE_ROOTS, "Lorg/jetbrains/kotlin/cli/jvm/compiler/ClasspathRootsResolver$RootWithPrefix;", "jvmClasspathRoots", "jvmModulePathRoots", "convertClasspathRoots", "contentRoots", "Lorg/jetbrains/kotlin/cli/common/config/ContentRoot;", "findSourceModuleInfo", "Lkotlin/Pair;", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiJavaModule;", "root", "modularBinaryRoot", "modularSourceRoot", "Lorg/jetbrains/kotlin/resolve/jvm/modules/JavaModule$Explicit;", "hasOutputDirectoryInClasspath", "readManifestAttributes", "Ljava/util/jar/Attributes;", "jarRoot", "report", "severity", "Lorg/jetbrains/kotlin/cli/common/messages/CompilerMessageSeverity;", CrashHianalyticsData.MESSAGE, "file", "tryLoadVersionSpecificModuleInfo", KotlinLibraryLayoutKt.KLIB_MANIFEST_FILE_NAME, "Lkotlin/Lazy;", "Companion", "RootWithPrefix", "RootsAndModules", "cli-base"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ClasspathRootsResolver {

    @Deprecated
    public static final String AUTOMATIC_MODULE_NAME = "Automatic-Module-Name";
    private static final Companion Companion = new Companion(null);

    @Deprecated
    public static final String IS_MULTI_RELEASE = "Multi-Release";
    private final List<String> additionalModules;
    private final Function1<JvmContentRootBase, VirtualFile> contentRootToVirtualFile;
    private final KotlinCliJavaFileManager javaFileManager;
    private final CliJavaModuleFinder javaModuleFinder;
    private final JavaModuleGraph javaModuleGraph;
    private final Integer jdkRelease;
    private final MessageCollector messageCollector;
    private final VirtualFile outputDirectory;
    private final PsiManager psiManager;
    private final boolean requireStdlibModule;
    private final GlobalSearchScope searchScope;

    /* JADX WARN: Multi-variable type inference failed */
    public ClasspathRootsResolver(PsiManager psiManager, MessageCollector messageCollector, List<String> additionalModules, Function1<? super JvmContentRootBase, ? extends VirtualFile> contentRootToVirtualFile, CliJavaModuleFinder javaModuleFinder, boolean z, VirtualFile virtualFile, KotlinCliJavaFileManager javaFileManager, Integer num) {
        Intrinsics.checkNotNullParameter(psiManager, "psiManager");
        Intrinsics.checkNotNullParameter(additionalModules, "additionalModules");
        Intrinsics.checkNotNullParameter(contentRootToVirtualFile, "contentRootToVirtualFile");
        Intrinsics.checkNotNullParameter(javaModuleFinder, "javaModuleFinder");
        Intrinsics.checkNotNullParameter(javaFileManager, "javaFileManager");
        this.psiManager = psiManager;
        this.messageCollector = messageCollector;
        this.additionalModules = additionalModules;
        this.contentRootToVirtualFile = contentRootToVirtualFile;
        this.javaModuleFinder = javaModuleFinder;
        this.requireStdlibModule = z;
        this.outputDirectory = virtualFile;
        this.javaFileManager = javaFileManager;
        this.jdkRelease = num;
        this.javaModuleGraph = new JavaModuleGraph(javaModuleFinder);
        GlobalSearchScope globalSearchScopeAllScope = GlobalSearchScope.allScope(psiManager.getProject());
        Intrinsics.checkNotNullExpressionValue(globalSearchScopeAllScope, "allScope(...)");
        this.searchScope = globalSearchScopeAllScope;
    }

    public final JavaModuleGraph getJavaModuleGraph() {
        return this.javaModuleGraph;
    }

    /* JADX INFO: compiled from: ClasspathRootsResolver.kt */
    @Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B!\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00060\u0003¢\u0006\u0002\u0010\u0007J\u000f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003HÆ\u0003J\u000f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00060\u0003HÆ\u0003J)\u0010\r\u001a\u00020\u00002\u000e\b\u0002\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\u000e\b\u0002\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00060\u0003HÆ\u0001J\u0013\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0011\u001a\u00020\u0012HÖ\u0001J\t\u0010\u0013\u001a\u00020\u0014HÖ\u0001R\u0017\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00060\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\t¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/cli/jvm/compiler/ClasspathRootsResolver$RootsAndModules;", "", PsiTreeChangeEvent.PROP_ROOTS, "", "Lorg/jetbrains/kotlin/cli/jvm/index/JavaRoot;", "modules", "Lorg/jetbrains/kotlin/resolve/jvm/modules/JavaModule;", "(Ljava/util/List;Ljava/util/List;)V", "getModules", "()Ljava/util/List;", "getRoots", "component1", "component2", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "", "cli-base"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final /* data */ class RootsAndModules {
        private final List<JavaModule> modules;
        private final List<JavaRoot> roots;

        /* JADX WARN: Multi-variable type inference failed */
        public static /* synthetic */ RootsAndModules copy$default(RootsAndModules rootsAndModules, List list, List list2, int i, Object obj) {
            if ((i & 1) != 0) {
                list = rootsAndModules.roots;
            }
            if ((i & 2) != 0) {
                list2 = rootsAndModules.modules;
            }
            return rootsAndModules.copy(list, list2);
        }

        public final List<JavaRoot> component1() {
            return this.roots;
        }

        public final List<JavaModule> component2() {
            return this.modules;
        }

        public final RootsAndModules copy(List<JavaRoot> roots, List<? extends JavaModule> modules) {
            Intrinsics.checkNotNullParameter(roots, "roots");
            Intrinsics.checkNotNullParameter(modules, "modules");
            return new RootsAndModules(roots, modules);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof RootsAndModules)) {
                return false;
            }
            RootsAndModules rootsAndModules = (RootsAndModules) other;
            return Intrinsics.areEqual(this.roots, rootsAndModules.roots) && Intrinsics.areEqual(this.modules, rootsAndModules.modules);
        }

        public int hashCode() {
            return (this.roots.hashCode() * 31) + this.modules.hashCode();
        }

        public String toString() {
            return "RootsAndModules(roots=" + this.roots + ", modules=" + this.modules + ')';
        }

        /* JADX WARN: Multi-variable type inference failed */
        public RootsAndModules(List<JavaRoot> roots, List<? extends JavaModule> modules) {
            Intrinsics.checkNotNullParameter(roots, "roots");
            Intrinsics.checkNotNullParameter(modules, "modules");
            this.roots = roots;
            this.modules = modules;
        }

        public final List<JavaModule> getModules() {
            return this.modules;
        }

        public final List<JavaRoot> getRoots() {
            return this.roots;
        }
    }

    /* JADX INFO: compiled from: ClasspathRootsResolver.kt */
    @Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0082\b\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\u0002\u0010\u0006J\t\u0010\u000b\u001a\u00020\u0003HÆ\u0003J\u000b\u0010\f\u001a\u0004\u0018\u00010\u0005HÆ\u0003J\u001f\u0010\r\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005HÆ\u0001J\u0013\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0011\u001a\u00020\u0012HÖ\u0001J\t\u0010\u0013\u001a\u00020\u0005HÖ\u0001R\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u0014"}, d2 = {"Lorg/jetbrains/kotlin/cli/jvm/compiler/ClasspathRootsResolver$RootWithPrefix;", "", "root", "Lorg/jetbrains/kotlin/com/intellij/openapi/vfs/VirtualFile;", ModuleXmlParser.JAVA_SOURCE_PACKAGE_PREFIX, "", "(Lcom/intellij/openapi/vfs/VirtualFile;Ljava/lang/String;)V", "getPackagePrefix", "()Ljava/lang/String;", "getRoot", "()Lcom/intellij/openapi/vfs/VirtualFile;", "component1", "component2", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "cli-base"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final /* data */ class RootWithPrefix {
        private final String packagePrefix;
        private final VirtualFile root;

        public static /* synthetic */ RootWithPrefix copy$default(RootWithPrefix rootWithPrefix, VirtualFile virtualFile, String str, int i, Object obj) {
            if ((i & 1) != 0) {
                virtualFile = rootWithPrefix.root;
            }
            if ((i & 2) != 0) {
                str = rootWithPrefix.packagePrefix;
            }
            return rootWithPrefix.copy(virtualFile, str);
        }

        /* JADX INFO: renamed from: component1, reason: from getter */
        public final VirtualFile getRoot() {
            return this.root;
        }

        /* JADX INFO: renamed from: component2, reason: from getter */
        public final String getPackagePrefix() {
            return this.packagePrefix;
        }

        public final RootWithPrefix copy(VirtualFile root, String packagePrefix) {
            Intrinsics.checkNotNullParameter(root, "root");
            return new RootWithPrefix(root, packagePrefix);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof RootWithPrefix)) {
                return false;
            }
            RootWithPrefix rootWithPrefix = (RootWithPrefix) other;
            return Intrinsics.areEqual(this.root, rootWithPrefix.root) && Intrinsics.areEqual(this.packagePrefix, rootWithPrefix.packagePrefix);
        }

        public int hashCode() {
            int iHashCode = this.root.hashCode() * 31;
            String str = this.packagePrefix;
            return iHashCode + (str == null ? 0 : str.hashCode());
        }

        public String toString() {
            return "RootWithPrefix(root=" + this.root + ", packagePrefix=" + this.packagePrefix + ')';
        }

        public RootWithPrefix(VirtualFile root, String str) {
            Intrinsics.checkNotNullParameter(root, "root");
            this.root = root;
            this.packagePrefix = str;
        }

        public final String getPackagePrefix() {
            return this.packagePrefix;
        }

        public final VirtualFile getRoot() {
            return this.root;
        }
    }

    public final RootsAndModules convertClasspathRoots(List<? extends ContentRoot> contentRoots) {
        VirtualFile virtualFileInvoke;
        Intrinsics.checkNotNullParameter(contentRoots, "contentRoots");
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        ArrayList arrayList3 = new ArrayList();
        for (ContentRoot contentRoot : contentRoots) {
            if ((contentRoot instanceof JvmContentRootBase) && (virtualFileInvoke = this.contentRootToVirtualFile.invoke((JvmContentRootBase) contentRoot)) != null) {
                if (contentRoot instanceof JavaSourceRoot) {
                    arrayList.add(new RootWithPrefix(virtualFileInvoke, ((JavaSourceRoot) contentRoot).getPackagePrefix()));
                } else if (contentRoot instanceof JvmClasspathRootBase) {
                    arrayList2.add(virtualFileInvoke);
                } else {
                    if (!(contentRoot instanceof JvmModulePathRoot)) {
                        throw new IllegalStateException(("Unknown root type: " + contentRoot).toString());
                    }
                    arrayList3.add(virtualFileInvoke);
                }
            }
        }
        return computeRoots(arrayList, arrayList2, arrayList3);
    }

    private final RootsAndModules computeRoots(List<RootWithPrefix> javaSourceRoots, List<? extends VirtualFile> jvmClasspathRoots, List<? extends VirtualFile> jvmModulePathRoots) {
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        boolean z = true;
        boolean z2 = CollectionsKt.contains(jvmClasspathRoots, this.outputDirectory) || CollectionsKt.contains(jvmModulePathRoots, this.outputDirectory);
        for (RootWithPrefix rootWithPrefix : javaSourceRoots) {
            VirtualFile root = rootWithPrefix.getRoot();
            String packagePrefix = rootWithPrefix.getPackagePrefix();
            JavaModule.Explicit explicitModularSourceRoot = modularSourceRoot(root, z2);
            if (explicitModularSourceRoot != null) {
                arrayList2.add(explicitModularSourceRoot);
            } else {
                ArrayList arrayList3 = arrayList;
                JavaRoot.RootType rootType = JavaRoot.RootType.SOURCE;
                FqName fqName = null;
                if (packagePrefix != null) {
                    if (FqNamesUtilKt.isValidJavaFqName(packagePrefix)) {
                        fqName = new FqName(packagePrefix);
                    } else {
                        report$default(this, CompilerMessageSeverity.STRONG_WARNING, "Invalid package prefix name is ignored: " + packagePrefix, null, 4, null);
                    }
                }
                arrayList3.add(new JavaRoot(root, rootType, fqName));
            }
        }
        Iterator<? extends VirtualFile> it = jvmClasspathRoots.iterator();
        while (it.hasNext()) {
            arrayList.add(new JavaRoot(it.next(), JavaRoot.RootType.BINARY, null, 4, null));
        }
        ArrayList arrayList4 = arrayList2;
        if ((arrayList4 instanceof Collection) && arrayList4.isEmpty()) {
            z = false;
            break;
        }
        Iterator it2 = arrayList4.iterator();
        loop3: while (true) {
            if (!it2.hasNext()) {
                z = false;
                break;
            }
            List<JavaModule.Root> moduleRoots = ((JavaModule) it2.next()).getModuleRoots();
            if (!(moduleRoots instanceof Collection) || !moduleRoots.isEmpty()) {
                Iterator<T> it3 = moduleRoots.iterator();
                while (it3.hasNext()) {
                    if (Intrinsics.areEqual(((JavaModule.Root) it3.next()).getFile(), this.outputDirectory)) {
                        break loop3;
                    }
                }
            }
        }
        for (VirtualFile virtualFile : jvmModulePathRoots) {
            if (!z || !Intrinsics.areEqual(virtualFile, this.outputDirectory)) {
                JavaModule javaModuleModularBinaryRoot = modularBinaryRoot(virtualFile);
                if (javaModuleModularBinaryRoot != null) {
                    arrayList2.add(javaModuleModularBinaryRoot);
                }
            }
        }
        Integer num = this.jdkRelease;
        if (num == null || num.intValue() >= 9) {
            addModularRoots(arrayList2, arrayList);
        } else {
            arrayList.add(new JavaRoot(this.javaModuleFinder.getNonModuleRoot().getFile(), JavaRoot.RootType.BINARY_SIG, null, 4, null));
        }
        return new RootsAndModules(arrayList, arrayList2);
    }

    private final Pair<VirtualFile, PsiJavaModule> findSourceModuleInfo(VirtualFile root) {
        PsiFile psiFileFindFile;
        if (root.isDirectory()) {
            root = root.findChild(PsiJavaModule.MODULE_INFO_FILE);
        } else if (!Intrinsics.areEqual(root.getName(), PsiJavaModule.MODULE_INFO_FILE)) {
            root = null;
        }
        if (root == null || (psiFileFindFile = this.psiManager.findFile(root)) == null) {
            return null;
        }
        PsiElement[] children = psiFileFindFile.getChildren();
        Intrinsics.checkNotNullExpressionValue(children, "getChildren(...)");
        PsiElement[] psiElementArr = children;
        int length = psiElementArr.length;
        int i = 0;
        PsiElement psiElement = null;
        boolean z = false;
        while (true) {
            if (i >= length) {
                if (!z) {
                    break;
                }
                break;
            }
            PsiElement psiElement2 = psiElementArr[i];
            if (psiElement2 instanceof PsiJavaModule) {
                if (!z) {
                    z = true;
                    psiElement = psiElement2;
                }
            }
            i++;
            psiElement = null;
            break;
        }
        PsiJavaModule psiJavaModule = psiElement instanceof PsiJavaModule ? (PsiJavaModule) psiElement : null;
        if (psiJavaModule == null) {
            return null;
        }
        return TuplesKt.to(root, psiJavaModule);
    }

    private final JavaModule.Explicit modularSourceRoot(VirtualFile root, boolean hasOutputDirectoryInClasspath) {
        List listListOf;
        Pair<VirtualFile, PsiJavaModule> pairFindSourceModuleInfo = findSourceModuleInfo(root);
        if (pairFindSourceModuleInfo == null) {
            return null;
        }
        VirtualFile virtualFileComponent1 = pairFindSourceModuleInfo.component1();
        PsiJavaModule psiJavaModuleComponent2 = pairFindSourceModuleInfo.component2();
        JavaModule.Root root2 = new JavaModule.Root(root, false, false, 4, null);
        if (hasOutputDirectoryInClasspath) {
            VirtualFile virtualFile = this.outputDirectory;
            Intrinsics.checkNotNull(virtualFile);
            listListOf = CollectionsKt.listOf((Object[]) new JavaModule.Root[]{root2, new JavaModule.Root(virtualFile, true, false, 4, null)});
        } else {
            listListOf = CollectionsKt.listOf(root2);
        }
        return new JavaModule.Explicit(JavaModuleInfo.INSTANCE.create(psiJavaModuleComponent2), listListOf, virtualFileComponent1, false, 8, null);
    }

    private final JavaModule modularBinaryRoot(final VirtualFile root) {
        VirtualFile virtualFile;
        boolean zAreEqual = Intrinsics.areEqual(root.getFileSystem().getProtocol(), "jar");
        Lazy<? extends Attributes> lazy = LazyKt.lazy(LazyThreadSafetyMode.NONE, (Function0) new Function0<Attributes>() { // from class: org.jetbrains.kotlin.cli.jvm.compiler.ClasspathRootsResolver$modularBinaryRoot$manifest$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Attributes invoke() {
                return this.this$0.readManifestAttributes(root);
            }
        });
        VirtualFile virtualFileFindChild = root.findChild("module-info.class");
        if (virtualFileFindChild != null) {
            virtualFile = virtualFileFindChild;
        } else if (zAreEqual) {
            virtualFileFindChild = tryLoadVersionSpecificModuleInfo(root, lazy);
            virtualFile = virtualFileFindChild;
        } else {
            virtualFile = null;
        }
        if (virtualFile != null) {
            JavaModuleInfo javaModuleInfo = JavaModuleInfo.INSTANCE.read(virtualFile, this.javaFileManager, this.searchScope);
            if (javaModuleInfo == null) {
                return null;
            }
            return new JavaModule.Explicit(javaModuleInfo, CollectionsKt.listOf(new JavaModule.Root(root, true, false, 4, null)), virtualFile, false, 8, null);
        }
        if (!zAreEqual) {
            return null;
        }
        List listListOf = CollectionsKt.listOf(new JavaModule.Root(root, true, false, 4, null));
        Attributes value = lazy.getValue();
        String value2 = value != null ? value.getValue("Automatic-Module-Name") : null;
        if (value2 != null) {
            return new JavaModule.Automatic(value2, listListOf);
        }
        File fileVirtualToIoFile = VfsUtilCore.virtualToIoFile(root);
        Intrinsics.checkNotNullExpressionValue(fileVirtualToIoFile, "virtualToIoFile(...)");
        String strModuleName = LightJavaModule.moduleName(FilesKt.getNameWithoutExtension(fileVirtualToIoFile));
        Intrinsics.checkNotNullExpressionValue(strModuleName, "moduleName(...)");
        if (strModuleName.length() == 0) {
            CompilerMessageSeverity compilerMessageSeverity = CompilerMessageSeverity.ERROR;
            VirtualFile virtualFileForJar = VfsUtilCore.getVirtualFileForJar(root);
            if (virtualFileForJar != null) {
                root = virtualFileForJar;
            }
            report(compilerMessageSeverity, "Cannot infer automatic module name for the file", root);
            return null;
        }
        return new JavaModule.Automatic(strModuleName, listListOf);
    }

    private final VirtualFile tryLoadVersionSpecificModuleInfo(VirtualFile root, Lazy<? extends Attributes> manifest) {
        VirtualFile virtualFileFindChild;
        String value;
        VirtualFile virtualFileFindChild2 = root.findChild("META-INF");
        if (virtualFileFindChild2 != null && (virtualFileFindChild = virtualFileFindChild2.findChild("versions")) != null) {
            Attributes value2 = manifest.getValue();
            if (!Intrinsics.areEqual((Object) ((value2 == null || (value = value2.getValue(IS_MULTI_RELEASE)) == null) ? null : Boolean.valueOf(StringsKt.equals(value, "true", true))), (Object) true)) {
                return null;
            }
            VirtualFile[] children = virtualFileFindChild.getChildren();
            Intrinsics.checkNotNullExpressionValue(children, "getChildren(...)");
            ArrayList arrayList = new ArrayList();
            for (VirtualFile virtualFile : children) {
                String name2 = virtualFile.getName();
                Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
                Integer intOrNull = StringsKt.toIntOrNull(name2);
                if (intOrNull != null && intOrNull.intValue() >= 9) {
                    arrayList.add(virtualFile);
                }
            }
            Iterator it = CollectionsKt.sortedWith(arrayList, new Comparator() { // from class: org.jetbrains.kotlin.cli.jvm.compiler.ClasspathRootsResolver$tryLoadVersionSpecificModuleInfo$$inlined$sortedBy$1
                /* JADX WARN: Multi-variable type inference failed */
                @Override // java.util.Comparator
                public final int compare(T t, T t2) {
                    String name3 = ((VirtualFile) t).getName();
                    Intrinsics.checkNotNullExpressionValue(name3, "getName(...)");
                    Integer numValueOf = Integer.valueOf(Integer.parseInt(name3));
                    String name4 = ((VirtualFile) t2).getName();
                    Intrinsics.checkNotNullExpressionValue(name4, "getName(...)");
                    return ComparisonsKt.compareValues(numValueOf, Integer.valueOf(Integer.parseInt(name4)));
                }
            }).iterator();
            while (it.hasNext()) {
                VirtualFile virtualFileFindChild3 = ((VirtualFile) it.next()).findChild("module-info.class");
                if (virtualFileFindChild3 != null) {
                    return virtualFileFindChild3;
                }
            }
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Attributes readManifestAttributes(VirtualFile jarRoot) {
        VirtualFile virtualFileFindChild = jarRoot.findChild("META-INF");
        VirtualFile virtualFileFindChild2 = virtualFileFindChild != null ? virtualFileFindChild.findChild("MANIFEST.MF") : null;
        if (virtualFileFindChild2 == null) {
            return null;
        }
        try {
            InputStream inputStream = virtualFileFindChild2.getInputStream();
            if (inputStream != null) {
                return new Manifest(inputStream).getMainAttributes();
            }
            return null;
        } catch (IOException unused) {
            return null;
        }
    }

    private final void addModularRoots(List<? extends JavaModule> modules, List<JavaRoot> result) {
        ArrayList arrayListPlus;
        List<? extends JavaModule> list = modules;
        ArrayList arrayList = new ArrayList();
        for (Object obj : list) {
            if (obj instanceof JavaModule.Explicit) {
                arrayList.add(obj);
            }
        }
        ArrayList arrayList2 = new ArrayList();
        for (Object obj2 : arrayList) {
            if (((JavaModule) obj2).isSourceModule()) {
                arrayList2.add(obj2);
            }
        }
        ArrayList arrayList3 = arrayList2;
        if (arrayList3.size() > 1) {
            Iterator it = arrayList3.iterator();
            while (it.hasNext()) {
                report(CompilerMessageSeverity.ERROR, "Too many source module declarations found", ((JavaModule.Explicit) it.next()).getModuleInfoFile());
            }
            return;
        }
        for (JavaModule javaModule : modules) {
            JavaModule javaModuleFindModule = this.javaModuleFinder.findModule(javaModule.getName());
            if (javaModuleFindModule == null) {
                this.javaModuleFinder.addUserModule(javaModule);
            } else if (!Intrinsics.areEqual(javaModule.getModuleRoots(), javaModuleFindModule.getModuleRoots())) {
                VirtualFile virtualFileAddModularRoots$getRootFile = addModularRoots$getRootFile(javaModule);
                VirtualFile virtualFileAddModularRoots$getRootFile2 = addModularRoots$getRootFile(javaModuleFindModule);
                report(CompilerMessageSeverity.STRONG_WARNING, "The root is ignored because a module with the same name '" + javaModule.getName() + "' has been found earlier on the module path" + (virtualFileAddModularRoots$getRootFile2 == null ? "" : " at: " + virtualFileAddModularRoots$getRootFile2.getPath()), virtualFileAddModularRoots$getRootFile);
            }
        }
        if (SequencesKt.none(this.javaModuleFinder.getAllObservableModules())) {
            return;
        }
        JavaModule.Explicit explicit = (JavaModule.Explicit) CollectionsKt.singleOrNull((List) arrayList3);
        boolean zContains = this.additionalModules.contains("ALL-MODULE-PATH");
        if (zContains && explicit != null) {
            report$default(this, CompilerMessageSeverity.ERROR, "-Xadd-modules=ALL-MODULE-PATH can only be used when compiling the unnamed module", null, 4, null);
            return;
        }
        if (explicit != null) {
            arrayListPlus = CollectionsKt.plus((Collection) CollectionsKt.listOf(explicit.getName()), (Iterable) this.additionalModules);
        } else if (!zContains) {
            arrayListPlus = CollectionsKt.plus((Collection) CliJavaModuleUtilsKt.computeDefaultRootModules(this.javaModuleFinder), (Iterable) this.additionalModules);
        } else {
            ArrayList arrayList4 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
            Iterator<T> it2 = list.iterator();
            while (it2.hasNext()) {
                arrayList4.add(((JavaModule) it2.next()).getName());
            }
            arrayListPlus = arrayList4;
        }
        LinkedHashSet<String> allDependencies = this.javaModuleGraph.getAllDependencies(arrayListPlus);
        LinkedHashSet<String> linkedHashSet = allDependencies;
        if (!(linkedHashSet instanceof Collection) || !linkedHashSet.isEmpty()) {
            Iterator<T> it3 = linkedHashSet.iterator();
            while (it3.hasNext()) {
                if (this.javaModuleFinder.findModule((String) it3.next()) instanceof JavaModule.Automatic) {
                    for (JavaModule javaModule2 : modules) {
                        if (javaModule2 instanceof JavaModule.Automatic) {
                            allDependencies.add(javaModule2.getName());
                        }
                    }
                    break;
                }
            }
        }
        report$default(this, CompilerMessageSeverity.LOGGING, "Loading modules: " + allDependencies, null, 4, null);
        for (String str : allDependencies) {
            CliJavaModuleFinder cliJavaModuleFinder = this.javaModuleFinder;
            Intrinsics.checkNotNull(str);
            JavaModule javaModuleFindModule2 = cliJavaModuleFinder.findModule(str);
            if (javaModuleFindModule2 == null) {
                report$default(this, CompilerMessageSeverity.ERROR, "Module " + str + " cannot be found in the module graph", null, 4, null);
            } else {
                result.addAll(CliJavaModuleUtilsKt.getJavaModuleRoots(javaModuleFindModule2));
            }
        }
        if (!this.requireStdlibModule || explicit == null || this.javaModuleGraph.reads(explicit.getName(), JavaModuleKt.KOTLIN_STDLIB_MODULE_NAME)) {
            return;
        }
        report(CompilerMessageSeverity.ERROR, "The Kotlin standard library is not found in the module graph. Please ensure you have the 'requires kotlin.stdlib' clause in your module definition", explicit.getModuleInfoFile());
    }

    private static final VirtualFile addModularRoots$getRootFile(JavaModule javaModule) {
        VirtualFile file;
        JavaModule.Root root = (JavaModule.Root) CollectionsKt.firstOrNull((List) javaModule.getModuleRoots());
        if (root == null || (file = root.getFile()) == null) {
            return null;
        }
        VirtualFile virtualFileForJar = VfsUtilCore.getVirtualFileForJar(file);
        return virtualFileForJar == null ? file : virtualFileForJar;
    }

    static /* synthetic */ void report$default(ClasspathRootsResolver classpathRootsResolver, CompilerMessageSeverity compilerMessageSeverity, String str, VirtualFile virtualFile, int i, Object obj) {
        if ((i & 4) != 0) {
            virtualFile = null;
        }
        classpathRootsResolver.report(compilerMessageSeverity, str, virtualFile);
    }

    private final void report(CompilerMessageSeverity severity, String message, VirtualFile file) {
        MessageCollector messageCollector = this.messageCollector;
        if (messageCollector == null) {
            throw new IllegalStateException((file != null ? file.getPath() + ':' : "") + severity + ": " + message + " (no MessageCollector configured)");
        }
        messageCollector.report(severity, message, file == null ? null : CompilerMessageLocation.INSTANCE.create(MessageUtil.virtualFileToPath(file)));
    }

    /* JADX INFO: compiled from: ClasspathRootsResolver.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0082\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000¨\u0006\u0006"}, d2 = {"Lorg/jetbrains/kotlin/cli/jvm/compiler/ClasspathRootsResolver$Companion;", "", "()V", "AUTOMATIC_MODULE_NAME", "", "IS_MULTI_RELEASE", "cli-base"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }
}

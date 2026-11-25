package org.jetbrains.kotlin.cli.jvm.modules;

import com.beust.jcommander.Parameters;
import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import io.netty.util.internal.StringUtil;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.NoSuchElementException;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.IntRange;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import kotlin.text.CharsKt;
import kotlin.text.StringsKt;
import org.jetbrains.kotlin.cli.common.messages.CompilerMessageSeverity;
import org.jetbrains.kotlin.cli.common.messages.MessageCollector;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.StandardFileSystems;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFileManager;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFileSystem;
import org.jetbrains.kotlin.com.intellij.psi.PsiTreeChangeEvent;
import org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.resolve.jvm.KotlinCliJavaFileManager;
import org.jetbrains.kotlin.resolve.jvm.modules.JavaModule;
import org.jetbrains.kotlin.resolve.jvm.modules.JavaModuleFinder;
import org.jetbrains.kotlin.resolve.jvm.modules.JavaModuleInfo;

/* JADX INFO: compiled from: CliJavaModuleFinder.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u009e\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010$\n\u0002\u0010\u000e\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010%\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\n\u0018\u00002\u00020\u0001B3\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\b\u0010\n\u001a\u0004\u0018\u00010\u000b¢\u0006\u0002\u0010\fJ\u000e\u0010C\u001a\u00020D2\u0006\u0010E\u001a\u00020\u000fJ\u0010\u0010F\u001a\u00020!2\u0006\u0010G\u001a\u00020\u000bH\u0002J\u0014\u0010H\u001a\u000e\u0012\u0004\u0012\u00020!\u0012\u0004\u0012\u00020\u001b0 H\u0002J \u0010I\u001a\u0002052\u0006\u0010J\u001a\u00020)2\u0006\u0010K\u001a\u00020)2\u0006\u0010L\u001a\u00020MH\u0002J\u0016\u0010I\u001a\b\u0012\u0004\u0012\u0002050/2\u0006\u0010L\u001a\u00020MH\u0002J\u0012\u0010N\u001a\u0004\u0018\u00010\u000f2\u0006\u0010O\u001a\u00020!H\u0016J\u001c\u0010P\u001a\u0004\u0018\u00010:2\u0006\u0010Q\u001a\u00020\u001b2\b\b\u0002\u0010R\u001a\u00020)H\u0002J\u0018\u0010S\u001a\u00020)2\u0006\u0010T\u001a\u00020!2\u0006\u0010G\u001a\u00020\u000bH\u0002J\u0012\u0010U\u001a\u0004\u0018\u00010\u001b2\u0006\u0010V\u001a\u00020!H\u0002R\u0017\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000e8F¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u0011R\u0013\u0010\u0012\u001a\u00070\u0013¢\u0006\u0002\b\u0014X\u0082\u0004¢\u0006\u0002\n\u0000R\u001b\u0010\u0015\u001a\u00020\u000b8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0018\u0010\u0019\u001a\u0004\b\u0016\u0010\u0017R\u001d\u0010\u001a\u001a\u0004\u0018\u00010\u001b8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u001e\u0010\u0019\u001a\u0004\b\u001c\u0010\u001dR'\u0010\u001f\u001a\u000e\u0012\u0004\u0012\u00020!\u0012\u0004\u0012\u00020\u001b0 8FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b$\u0010\u0019\u001a\u0004\b\"\u0010#R\u001d\u0010%\u001a\u0004\u0018\u00010\u001b8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b'\u0010\u0019\u001a\u0004\b&\u0010\u001dR\u0014\u0010(\u001a\u00020)8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b(\u0010*R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0002\u001a\u0004\u0018\u00010\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0012\u0010\n\u001a\u0004\u0018\u00010\u000bX\u0082\u0004¢\u0006\u0004\n\u0002\u0010+R\u0015\u0010,\u001a\t\u0018\u00010\u001b¢\u0006\u0002\b-X\u0082\u0004¢\u0006\u0002\n\u0000R!\u0010.\u001a\b\u0012\u0004\u0012\u00020\u001b0/8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b2\u0010\u0019\u001a\u0004\b0\u00101R\u0010\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0015\u00103\u001a\t\u0018\u00010\u001b¢\u0006\u0002\b-X\u0082\u0004¢\u0006\u0002\n\u0000R\u001b\u00104\u001a\u0002058FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b8\u0010\u0019\u001a\u0004\b6\u00107R\u0017\u00109\u001a\b\u0012\u0004\u0012\u00020:0\u000e8F¢\u0006\u0006\u001a\u0004\b;\u0010\u0011R\u001a\u0010<\u001a\u000e\u0012\u0004\u0012\u00020!\u0012\u0004\u0012\u00020:0=X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010>\u001a\u00020)8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b?\u0010*R*\u0010@\u001a\u001e\u0012\u0004\u0012\u00020!\u0012\u0004\u0012\u00020\u000f0Aj\u000e\u0012\u0004\u0012\u00020!\u0012\u0004\u0012\u00020\u000f`BX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006W"}, d2 = {"Lorg/jetbrains/kotlin/cli/jvm/modules/CliJavaModuleFinder;", "Lorg/jetbrains/kotlin/resolve/jvm/modules/JavaModuleFinder;", "jdkHome", "Ljava/io/File;", "messageCollector", "Lorg/jetbrains/kotlin/cli/common/messages/MessageCollector;", "javaFileManager", "Lorg/jetbrains/kotlin/resolve/jvm/KotlinCliJavaFileManager;", "project", "Lorg/jetbrains/kotlin/com/intellij/openapi/project/Project;", "jdkRelease", "", "(Ljava/io/File;Lorg/jetbrains/kotlin/cli/common/messages/MessageCollector;Lorg/jetbrains/kotlin/resolve/jvm/KotlinCliJavaFileManager;Lcom/intellij/openapi/project/Project;Ljava/lang/Integer;)V", "allObservableModules", "Lkotlin/sequences/Sequence;", "Lorg/jetbrains/kotlin/resolve/jvm/modules/JavaModule;", "getAllObservableModules", "()Lkotlin/sequences/Sequence;", "allScope", "Lorg/jetbrains/kotlin/com/intellij/psi/search/GlobalSearchScope;", "Lorg/jetbrains/annotations/NotNull;", "compilationJdkVersion", "getCompilationJdkVersion", "()I", "compilationJdkVersion$delegate", "Lkotlin/Lazy;", "ctSymFile", "Lorg/jetbrains/kotlin/com/intellij/openapi/vfs/VirtualFile;", "getCtSymFile", "()Lcom/intellij/openapi/vfs/VirtualFile;", "ctSymFile$delegate", "ctSymModules", "", "", "getCtSymModules", "()Ljava/util/Map;", "ctSymModules$delegate", "ctSymRootFolder", "getCtSymRootFolder", "ctSymRootFolder$delegate", "isCompilationJDK12OrLater", "", "()Z", "Ljava/lang/Integer;", "jrtFileSystemRoot", "Lorg/jetbrains/annotations/Nullable;", "listFoldersForRelease", "", "getListFoldersForRelease", "()Ljava/util/List;", "listFoldersForRelease$delegate", "modulesRoot", "nonModuleRoot", "Lorg/jetbrains/kotlin/resolve/jvm/modules/JavaModule$Root;", "getNonModuleRoot", "()Lorg/jetbrains/kotlin/resolve/jvm/modules/JavaModule$Root;", "nonModuleRoot$delegate", "systemModules", "Lorg/jetbrains/kotlin/resolve/jvm/modules/JavaModule$Explicit;", "getSystemModules", "systemModulesCache", "", "useLastJdkApi", "getUseLastJdkApi", "userModules", "Ljava/util/LinkedHashMap;", "Lkotlin/collections/LinkedHashMap;", "addUserModule", "", "module", "codeFor", "release", "collectModuleRoots", "createModuleFromSignature", "filterPackages", "filterModules", "moduleInfo", "Lorg/jetbrains/kotlin/resolve/jvm/modules/JavaModuleInfo;", "findModule", "name", "findSystemModule", "moduleRoot", "useSig", "matchesRelease", PsiTreeChangeEvent.PROP_FILE_NAME, "reportError", CrashHianalyticsData.MESSAGE, "cli-base"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CliJavaModuleFinder implements JavaModuleFinder {
    private final GlobalSearchScope allScope;

    /* JADX INFO: renamed from: compilationJdkVersion$delegate, reason: from kotlin metadata */
    private final Lazy compilationJdkVersion;

    /* JADX INFO: renamed from: ctSymFile$delegate, reason: from kotlin metadata */
    private final Lazy ctSymFile;

    /* JADX INFO: renamed from: ctSymModules$delegate, reason: from kotlin metadata */
    private final Lazy ctSymModules;

    /* JADX INFO: renamed from: ctSymRootFolder$delegate, reason: from kotlin metadata */
    private final Lazy ctSymRootFolder;
    private final KotlinCliJavaFileManager javaFileManager;
    private final File jdkHome;
    private final Integer jdkRelease;
    private final VirtualFile jrtFileSystemRoot;

    /* JADX INFO: renamed from: listFoldersForRelease$delegate, reason: from kotlin metadata */
    private final Lazy listFoldersForRelease;
    private final MessageCollector messageCollector;
    private final VirtualFile modulesRoot;

    /* JADX INFO: renamed from: nonModuleRoot$delegate, reason: from kotlin metadata */
    private final Lazy nonModuleRoot;
    private final Map<String, JavaModule.Explicit> systemModulesCache;
    private final LinkedHashMap<String, JavaModule> userModules;

    public CliJavaModuleFinder(File file, MessageCollector messageCollector, KotlinCliJavaFileManager javaFileManager, Project project, Integer num) {
        String path;
        VirtualFileSystem fileSystem;
        Intrinsics.checkNotNullParameter(javaFileManager, "javaFileManager");
        Intrinsics.checkNotNullParameter(project, "project");
        this.jdkHome = file;
        this.messageCollector = messageCollector;
        this.javaFileManager = javaFileManager;
        this.jdkRelease = num;
        VirtualFile virtualFileFindFileByPath = (file == null || (path = file.getPath()) == null || (fileSystem = VirtualFileManager.getInstance().getFileSystem(StandardFileSystems.JRT_PROTOCOL)) == null) ? null : fileSystem.findFileByPath(path + "!/");
        this.jrtFileSystemRoot = virtualFileFindFileByPath;
        this.modulesRoot = virtualFileFindFileByPath != null ? virtualFileFindFileByPath.findChild("modules") : null;
        this.userModules = new LinkedHashMap<>();
        GlobalSearchScope globalSearchScopeAllScope = GlobalSearchScope.allScope(project);
        Intrinsics.checkNotNullExpressionValue(globalSearchScopeAllScope, "allScope(...)");
        this.allScope = globalSearchScopeAllScope;
        this.ctSymFile = LazyKt.lazy(new Function0<VirtualFile>() { // from class: org.jetbrains.kotlin.cli.jvm.modules.CliJavaModuleFinder$ctSymFile$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final VirtualFile invoke() {
                if (this.this$0.jdkHome == null) {
                    return this.this$0.reportError("JDK_HOME path is not specified in compiler configuration");
                }
                VirtualFile virtualFileFindFileByPath2 = StandardFileSystems.local().findFileByPath(this.this$0.jdkHome.getPath());
                if (virtualFileFindFileByPath2 == null) {
                    return this.this$0.reportError("Can't create virtual file for JDK root under " + this.this$0.jdkHome.getPath());
                }
                VirtualFile virtualFileFindChild = virtualFileFindFileByPath2.findChild("lib");
                if (virtualFileFindChild == null) {
                    return this.this$0.reportError("Can't find `lib` folder under JDK root: " + this.this$0.jdkHome.getPath());
                }
                VirtualFile virtualFileFindChild2 = virtualFileFindChild.findChild("ct.sym");
                return virtualFileFindChild2 == null ? this.this$0.reportError("This JDK does not have the 'ct.sym' file required for the '-Xjdk-release=" + this.this$0.jdkRelease + "' option: " + this.this$0.jdkHome.getPath()) : virtualFileFindChild2;
            }
        });
        this.ctSymRootFolder = LazyKt.lazy(new Function0<VirtualFile>() { // from class: org.jetbrains.kotlin.cli.jvm.modules.CliJavaModuleFinder$ctSymRootFolder$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final VirtualFile invoke() {
                if (this.this$0.getCtSymFile() == null) {
                    return null;
                }
                VirtualFileSystem virtualFileSystemJar = StandardFileSystems.jar();
                if (virtualFileSystemJar != null) {
                    StringBuilder sb = new StringBuilder();
                    VirtualFile ctSymFile = this.this$0.getCtSymFile();
                    VirtualFile virtualFileFindFileByPath2 = virtualFileSystemJar.findFileByPath(sb.append(ctSymFile != null ? ctSymFile.getPath() : null).append("!/").toString());
                    if (virtualFileFindFileByPath2 != null) {
                        return virtualFileFindFileByPath2;
                    }
                }
                CliJavaModuleFinder cliJavaModuleFinder = this.this$0;
                StringBuilder sb2 = new StringBuilder("Can't open `ct.sym` as jar file, file path: ");
                VirtualFile ctSymFile2 = this.this$0.getCtSymFile();
                return cliJavaModuleFinder.reportError(sb2.append(ctSymFile2 != null ? ctSymFile2.getPath() : null).append(StringUtil.SPACE).toString());
            }
        });
        this.compilationJdkVersion = LazyKt.lazy(new Function0<Integer>() { // from class: org.jetbrains.kotlin.cli.jvm.modules.CliJavaModuleFinder$compilationJdkVersion$2
            {
                super(0);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            /* JADX WARN: Type inference failed for: r13v1, types: [kotlin.collections.IntIterator] */
            /* JADX WARN: Type inference failed for: r2v4, types: [kotlin.collections.IntIterator] */
            /* JADX WARN: Type inference failed for: r4v5, types: [kotlin.collections.IntIterator] */
            @Override // kotlin.jvm.functions.Function0
            public final Integer invoke() {
                int i;
                VirtualFile[] children;
                int iIntValue;
                int i2;
                VirtualFile ctSymRootFolder = this.this$0.getCtSymRootFolder();
                if (ctSymRootFolder == null || (children = ctSymRootFolder.getChildren()) == null) {
                    i = -1;
                } else {
                    if (children.length == 0) {
                        throw new NoSuchElementException();
                    }
                    VirtualFile virtualFile = children[0];
                    if (Intrinsics.areEqual(virtualFile.getName(), "META-INF")) {
                        iIntValue = -1;
                    } else {
                        String name2 = virtualFile.getName();
                        Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
                        String strSubstringBeforeLast$default = StringsKt.substringBeforeLast$default(name2, "-modules", (String) null, 2, (Object) null);
                        if (strSubstringBeforeLast$default.length() == 0) {
                            throw new NoSuchElementException();
                        }
                        Integer intOrNull = StringsKt.toIntOrNull(String.valueOf(strSubstringBeforeLast$default.charAt(0)), 36);
                        iIntValue = intOrNull != null ? intOrNull.intValue() : -1;
                        ?? it = new IntRange(1, StringsKt.getLastIndex(strSubstringBeforeLast$default)).iterator();
                        while (it.hasNext()) {
                            Integer intOrNull2 = StringsKt.toIntOrNull(String.valueOf(strSubstringBeforeLast$default.charAt(it.nextInt())), 36);
                            int iIntValue2 = intOrNull2 != null ? intOrNull2.intValue() : -1;
                            if (iIntValue < iIntValue2) {
                                iIntValue = iIntValue2;
                            }
                        }
                    }
                    ?? it2 = new IntRange(1, ArraysKt.getLastIndex(children)).iterator();
                    while (it2.hasNext()) {
                        VirtualFile virtualFile2 = children[it2.nextInt()];
                        if (Intrinsics.areEqual(virtualFile2.getName(), "META-INF")) {
                            i2 = -1;
                        } else {
                            String name3 = virtualFile2.getName();
                            Intrinsics.checkNotNullExpressionValue(name3, "getName(...)");
                            String strSubstringBeforeLast$default2 = StringsKt.substringBeforeLast$default(name3, "-modules", (String) null, 2, (Object) null);
                            if (strSubstringBeforeLast$default2.length() == 0) {
                                throw new NoSuchElementException();
                            }
                            Integer intOrNull3 = StringsKt.toIntOrNull(String.valueOf(strSubstringBeforeLast$default2.charAt(0)), 36);
                            int iIntValue3 = intOrNull3 != null ? intOrNull3.intValue() : -1;
                            ?? it3 = new IntRange(1, StringsKt.getLastIndex(strSubstringBeforeLast$default2)).iterator();
                            while (it3.hasNext()) {
                                Integer intOrNull4 = StringsKt.toIntOrNull(String.valueOf(strSubstringBeforeLast$default2.charAt(it3.nextInt())), 36);
                                int iIntValue4 = intOrNull4 != null ? intOrNull4.intValue() : -1;
                                if (iIntValue3 < iIntValue4) {
                                    iIntValue3 = iIntValue4;
                                }
                            }
                            i2 = iIntValue3;
                        }
                        if (iIntValue < i2) {
                            iIntValue = i2;
                        }
                    }
                    i = iIntValue;
                }
                return Integer.valueOf(i);
            }
        });
        this.ctSymModules = LazyKt.lazy(new Function0<Map<String, ? extends VirtualFile>>() { // from class: org.jetbrains.kotlin.cli.jvm.modules.CliJavaModuleFinder$ctSymModules$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Map<String, ? extends VirtualFile> invoke() {
                return this.this$0.collectModuleRoots();
            }
        });
        this.systemModulesCache = new LinkedHashMap();
        this.nonModuleRoot = LazyKt.lazy(new Function0<JavaModule.Root>() { // from class: org.jetbrains.kotlin.cli.jvm.modules.CliJavaModuleFinder$nonModuleRoot$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final JavaModule.Root invoke() {
                return this.this$0.createModuleFromSignature(false, false, new JavaModuleInfo("*", CollectionsKt.emptyList(), CollectionsKt.emptyList(), CollectionsKt.emptyList()));
            }
        });
        this.listFoldersForRelease = LazyKt.lazy(new Function0<List<? extends VirtualFile>>() { // from class: org.jetbrains.kotlin.cli.jvm.modules.CliJavaModuleFinder$listFoldersForRelease$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final List<? extends VirtualFile> invoke() {
                List listListOf;
                if (this.this$0.getCtSymRootFolder() == null) {
                    return CollectionsKt.emptyList();
                }
                VirtualFile ctSymRootFolder = this.this$0.getCtSymRootFolder();
                Intrinsics.checkNotNull(ctSymRootFolder);
                VirtualFile[] children = ctSymRootFolder.getChildren();
                Intrinsics.checkNotNullExpressionValue(children, "getChildren(...)");
                CliJavaModuleFinder cliJavaModuleFinder = this.this$0;
                ArrayList<VirtualFile> arrayList = new ArrayList();
                for (VirtualFile virtualFile : children) {
                    String name2 = virtualFile.getName();
                    Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
                    Integer num2 = cliJavaModuleFinder.jdkRelease;
                    Intrinsics.checkNotNull(num2);
                    if (cliJavaModuleFinder.matchesRelease(name2, num2.intValue())) {
                        arrayList.add(virtualFile);
                    }
                }
                CliJavaModuleFinder cliJavaModuleFinder2 = this.this$0;
                ArrayList arrayList2 = new ArrayList();
                for (VirtualFile virtualFile2 : arrayList) {
                    if (cliJavaModuleFinder2.isCompilationJDK12OrLater()) {
                        VirtualFile[] children2 = virtualFile2.getChildren();
                        Intrinsics.checkNotNullExpressionValue(children2, "getChildren(...)");
                        listListOf = ArraysKt.toList(children2);
                    } else {
                        listListOf = CollectionsKt.listOf(virtualFile2);
                    }
                    CollectionsKt.addAll(arrayList2, listListOf);
                }
                ArrayList arrayList3 = arrayList2;
                CliJavaModuleFinder cliJavaModuleFinder3 = this.this$0;
                if (!arrayList3.isEmpty()) {
                    return arrayList3;
                }
                StringBuilder sbAppend = new StringBuilder("'-Xjdk-release=").append(cliJavaModuleFinder3.jdkRelease).append("' option is not supported by used JDK: ");
                File file2 = cliJavaModuleFinder3.jdkHome;
                cliJavaModuleFinder3.reportError(sbAppend.append(file2 != null ? file2.getPath() : null).toString());
                return arrayList3;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final VirtualFile getCtSymFile() {
        return (VirtualFile) this.ctSymFile.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final VirtualFile getCtSymRootFolder() {
        return (VirtualFile) this.ctSymRootFolder.getValue();
    }

    private final int getCompilationJdkVersion() {
        return ((Number) this.compilationJdkVersion.getValue()).intValue();
    }

    public final Map<String, VirtualFile> getCtSymModules() {
        return (Map) this.ctSymModules.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean isCompilationJDK12OrLater() {
        return getCompilationJdkVersion() >= 12;
    }

    private final boolean getUseLastJdkApi() {
        Integer num = this.jdkRelease;
        if (num != null) {
            int compilationJdkVersion = getCompilationJdkVersion();
            if (num == null || num.intValue() != compilationJdkVersion) {
                return false;
            }
        }
        return true;
    }

    public final void addUserModule(JavaModule module) {
        Intrinsics.checkNotNullParameter(module, "module");
        this.userModules.putIfAbsent(module.getName(), module);
    }

    public final Sequence<JavaModule> getAllObservableModules() {
        Sequence<JavaModule.Explicit> systemModules = getSystemModules();
        Collection<JavaModule> collectionValues = this.userModules.values();
        Intrinsics.checkNotNullExpressionValue(collectionValues, "<get-values>(...)");
        return SequencesKt.plus((Sequence) systemModules, (Iterable) collectionValues);
    }

    public final Sequence<JavaModule.Explicit> getSystemModules() {
        if (getUseLastJdkApi()) {
            VirtualFile virtualFile = this.modulesRoot;
            VirtualFile[] children = virtualFile != null ? virtualFile.getChildren() : null;
            if (children == null) {
                children = new VirtualFile[0];
            }
            return SequencesKt.mapNotNull(ArraysKt.asSequence(children), new CliJavaModuleFinder$systemModules$1(this));
        }
        return SequencesKt.mapNotNull(CollectionsKt.asSequence(getCtSymModules().values()), new Function1<VirtualFile, JavaModule.Explicit>() { // from class: org.jetbrains.kotlin.cli.jvm.modules.CliJavaModuleFinder$systemModules$2
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final JavaModule.Explicit invoke(VirtualFile it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return this.this$0.findSystemModule(it, true);
            }
        });
    }

    @Override // org.jetbrains.kotlin.resolve.jvm.modules.JavaModuleFinder
    public JavaModule findModule(String name2) {
        VirtualFile virtualFileFindChild;
        Intrinsics.checkNotNullParameter(name2, "name");
        JavaModule.Explicit explicitFindSystemModule = null;
        if (getUseLastJdkApi()) {
            VirtualFile virtualFile = this.modulesRoot;
            if (virtualFile != null && (virtualFileFindChild = virtualFile.findChild(name2)) != null) {
                explicitFindSystemModule = findSystemModule$default(this, virtualFileFindChild, false, 2, null);
            }
        } else {
            VirtualFile virtualFile2 = getCtSymModules().get(name2);
            if (virtualFile2 != null) {
                explicitFindSystemModule = findSystemModule(virtualFile2, true);
            }
        }
        if (explicitFindSystemModule != null) {
            return explicitFindSystemModule;
        }
        return this.userModules.get(name2);
    }

    static /* synthetic */ JavaModule.Explicit findSystemModule$default(CliJavaModuleFinder cliJavaModuleFinder, VirtualFile virtualFile, boolean z, int i, Object obj) {
        if ((i & 2) != 0) {
            z = false;
        }
        return cliJavaModuleFinder.findSystemModule(virtualFile, z);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final JavaModule.Explicit findSystemModule(VirtualFile moduleRoot, boolean useSig) {
        JavaModuleInfo javaModuleInfo;
        List<JavaModule.Root> listCreateModuleFromSignature;
        VirtualFile virtualFileFindChild = moduleRoot.findChild(useSig ? "module-info.sig" : "module-info.class");
        if (virtualFileFindChild == null || (javaModuleInfo = JavaModuleInfo.INSTANCE.read(virtualFileFindChild, this.javaFileManager, this.allScope)) == null) {
            return null;
        }
        Map<String, JavaModule.Explicit> map = this.systemModulesCache;
        String moduleName = javaModuleInfo.getModuleName();
        JavaModule.Explicit explicit = map.get(moduleName);
        if (explicit == null) {
            if (getUseLastJdkApi()) {
                listCreateModuleFromSignature = CollectionsKt.listOf(new JavaModule.Root(moduleRoot, true, useSig));
            } else if (useSig) {
                listCreateModuleFromSignature = createModuleFromSignature(javaModuleInfo);
            } else {
                throw new IllegalStateException(("Can't find " + moduleRoot.getPath() + " module").toString());
            }
            explicit = new JavaModule.Explicit(javaModuleInfo, listCreateModuleFromSignature, virtualFileFindChild, !getUseLastJdkApi() && useSig);
            map.put(moduleName, explicit);
        }
        return explicit;
    }

    private final List<JavaModule.Root> createModuleFromSignature(JavaModuleInfo moduleInfo) {
        return CollectionsKt.listOf(createModuleFromSignature(!isCompilationJDK12OrLater(), isCompilationJDK12OrLater(), moduleInfo));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final JavaModule.Root createModuleFromSignature(boolean filterPackages, boolean filterModules, JavaModuleInfo moduleInfo) {
        HashMap mapEmptyMap;
        List<VirtualFile> listFoldersForRelease;
        if (!filterPackages) {
            mapEmptyMap = MapsKt.emptyMap();
        } else {
            HashMap map = new HashMap();
            Iterator<T> it = moduleInfo.getExports().iterator();
            while (it.hasNext()) {
                Iterator it2 = SequencesKt.generateSequence(((JavaModuleInfo.Exports) it.next()).getPackageFqName(), new Function1<FqName, FqName>() { // from class: org.jetbrains.kotlin.cli.jvm.modules.CliJavaModuleFinder$createModuleFromSignature$packageParts$1$1$1
                    @Override // kotlin.jvm.functions.Function1
                    public final FqName invoke(FqName part) {
                        Intrinsics.checkNotNullParameter(part, "part");
                        if (part.isRoot()) {
                            return null;
                        }
                        return part.parent();
                    }
                }).iterator();
                while (it2.hasNext()) {
                    String strAsString = ((FqName) it2.next()).asString();
                    Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
                    map.put(strAsString, false);
                }
            }
            Iterator<T> it3 = moduleInfo.getExports().iterator();
            while (it3.hasNext()) {
                String strAsString2 = ((JavaModuleInfo.Exports) it3.next()).getPackageFqName().asString();
                Intrinsics.checkNotNullExpressionValue(strAsString2, "asString(...)");
                map.put(strAsString2, true);
            }
            mapEmptyMap = map;
        }
        Map map2 = mapEmptyMap;
        if (filterModules) {
            List<VirtualFile> listFoldersForRelease2 = getListFoldersForRelease();
            ArrayList arrayList = new ArrayList();
            for (Object obj : listFoldersForRelease2) {
                if (Intrinsics.areEqual(((VirtualFile) obj).getName(), moduleInfo.getModuleName())) {
                    arrayList.add(obj);
                }
            }
            listFoldersForRelease = arrayList;
        } else {
            listFoldersForRelease = getListFoldersForRelease();
        }
        VirtualFile ctSymRootFolder = getCtSymRootFolder();
        if (ctSymRootFolder == null) {
            ctSymRootFolder = getCtSymFile();
        }
        return new JavaModule.Root(new CtSymDirectoryContainer(ctSymRootFolder, listFoldersForRelease, map2, "", moduleInfo.getModuleName(), !filterPackages), true, true);
    }

    private final String codeFor(int release) {
        String string = Integer.toString(release, CharsKt.checkRadix(36));
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        String upperCase = string.toUpperCase(Locale.ROOT);
        Intrinsics.checkNotNullExpressionValue(upperCase, "toUpperCase(...)");
        return upperCase;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean matchesRelease(String fileName, int release) {
        String str = fileName;
        return !StringsKt.contains$default((CharSequence) str, (CharSequence) Parameters.DEFAULT_OPTION_PREFIXES, false, 2, (Object) null) && StringsKt.contains$default((CharSequence) str, (CharSequence) codeFor(release), false, 2, (Object) null);
    }

    public final JavaModule.Root getNonModuleRoot() {
        return (JavaModule.Root) this.nonModuleRoot.getValue();
    }

    private final List<VirtualFile> getListFoldersForRelease() {
        return (List) this.listFoldersForRelease.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Map<String, VirtualFile> collectModuleRoots() {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        if (!isCompilationJDK12OrLater()) {
            Integer num = this.jdkRelease;
            Intrinsics.checkNotNull(num);
            if (num.intValue() > 8 && getCtSymRootFolder() != null) {
                VirtualFile ctSymRootFolder = getCtSymRootFolder();
                Intrinsics.checkNotNull(ctSymRootFolder);
                VirtualFile virtualFileFindChild = ctSymRootFolder.findChild(codeFor(this.jdkRelease.intValue()) + (!isCompilationJDK12OrLater() ? "-modules" : ""));
                if (virtualFileFindChild != null) {
                    VirtualFile[] children = virtualFileFindChild.getChildren();
                    Intrinsics.checkNotNullExpressionValue(children, "getChildren(...)");
                    for (VirtualFile virtualFile : children) {
                        String name2 = virtualFile.getName();
                        Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
                        Intrinsics.checkNotNull(virtualFile);
                        linkedHashMap.put(name2, virtualFile);
                    }
                } else {
                    StringBuilder sbAppend = new StringBuilder("Can't find modules signatures in `ct.sym` file for `-Xjdk-release=").append(this.jdkRelease).append("` in ");
                    VirtualFile ctSymFile = getCtSymFile();
                    Intrinsics.checkNotNull(ctSymFile);
                    reportError(sbAppend.append(ctSymFile.getPath()).toString());
                }
            }
        } else {
            for (VirtualFile virtualFile2 : getListFoldersForRelease()) {
                if (virtualFile2.findChild("module-info.sig") != null) {
                    String name3 = virtualFile2.getName();
                    Intrinsics.checkNotNullExpressionValue(name3, "getName(...)");
                    linkedHashMap.put(name3, virtualFile2);
                }
            }
        }
        return linkedHashMap;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final VirtualFile reportError(String message) {
        MessageCollector messageCollector = this.messageCollector;
        if (messageCollector == null) {
            return null;
        }
        MessageCollector.report$default(messageCollector, CompilerMessageSeverity.ERROR, message, null, 4, null);
        return null;
    }
}

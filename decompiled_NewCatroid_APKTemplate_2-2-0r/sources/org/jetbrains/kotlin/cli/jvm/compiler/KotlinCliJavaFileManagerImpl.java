package org.jetbrains.kotlin.cli.jvm.compiler;

import com.reandroid.apkeditor.Options;
import com.reandroid.archive.block.ApkSignatureBlock;
import gnu.trove.THashMap;
import gnu.trove.THashSet;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlin.text.StringsKt;
import org.eclipse.jdt.internal.compiler.util.SuffixConstants;
import org.jetbrains.kotlin.cli.jvm.index.JavaRoot;
import org.jetbrains.kotlin.cli.jvm.index.JvmDependenciesIndex;
import org.jetbrains.kotlin.cli.jvm.index.SingleJavaFileRootsIndex;
import org.jetbrains.kotlin.com.intellij.core.CoreJavaFileManager;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.openapi.util.text.StringUtil;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiClassOwner;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiJavaModule;
import org.jetbrains.kotlin.com.intellij.psi.PsiManager;
import org.jetbrains.kotlin.com.intellij.psi.PsiPackage;
import org.jetbrains.kotlin.com.intellij.psi.impl.file.PsiPackageImpl;
import org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope;
import org.jetbrains.kotlin.load.java.JavaClassFinder;
import org.jetbrains.kotlin.load.java.structure.JavaClass;
import org.jetbrains.kotlin.load.java.structure.impl.JavaClassImpl;
import org.jetbrains.kotlin.load.java.structure.impl.classFiles.BinaryClassSignatureParser;
import org.jetbrains.kotlin.load.java.structure.impl.classFiles.BinaryJavaClass;
import org.jetbrains.kotlin.load.java.structure.impl.classFiles.ClassifierResolutionContext;
import org.jetbrains.kotlin.load.java.structure.impl.classFiles.OtherKt;
import org.jetbrains.kotlin.load.java.structure.impl.source.JavaElementPsiSource;
import org.jetbrains.kotlin.load.java.structure.impl.source.JavaElementSourceFactory;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.resolve.jvm.KotlinCliJavaFileManager;
import org.jetbrains.kotlin.util.PerformanceCounter;

/* JADX INFO: compiled from: KotlinCliJavaFileManagerImpl.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000¸\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0011\n\u0002\b\u0002\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\"\n\u0002\b\u0004\u0018\u0000 E2\u00020\u00012\u00020\u0002:\u0001EB\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J\u0010\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020 H\u0002J\u001a\u0010!\u001a\u0004\u0018\u00010 2\u0006\u0010\"\u001a\u00020#2\u0006\u0010$\u001a\u00020\u0007H\u0016J\u001a\u0010!\u001a\u0004\u0018\u00010\f2\u0006\u0010%\u001a\u00020&2\u0006\u0010'\u001a\u00020\u0007H\u0016J\u0018\u0010!\u001a\u0004\u0018\u00010\f2\u0006\u0010(\u001a\u00020\u000b2\u0006\u0010'\u001a\u00020\u0007J#\u0010)\u001a\b\u0012\u0004\u0012\u00020 0*2\u0006\u0010\"\u001a\u00020#2\u0006\u0010$\u001a\u00020\u0007H\u0016¢\u0006\u0002\u0010+J\u001e\u0010,\u001a\b\u0012\u0004\u0012\u00020.0-2\u0006\u0010/\u001a\u00020#2\u0006\u0010$\u001a\u00020\u0007H\u0016J\u0012\u00100\u001a\u0004\u0018\u0001012\u0006\u00102\u001a\u00020#H\u0016J\u001a\u00103\u001a\u0004\u0018\u00010 2\u0006\u0010(\u001a\u00020\u000b2\u0006\u0010'\u001a\u00020\u0007H\u0002J\u001a\u00104\u001a\u0004\u0018\u00010\u001a2\u0006\u0010(\u001a\u00020\u000b2\u0006\u0010'\u001a\u00020\u0007H\u0002J\"\u00105\u001a\u0004\u0018\u00010\u001a2\u0006\u00106\u001a\u00020\u001a2\u0006\u00107\u001a\u00020#2\u0006\u00108\u001a\u000209H\u0002J%\u0010:\u001a\u00020;2\u0006\u0010<\u001a\u00020#2\u0012\u0010=\u001a\u000e\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020;0>H\u0082\bJ\u000e\u0010?\u001a\b\u0012\u0004\u0012\u00020#0-H\u0016J,\u0010@\u001a\u00020;2\u0006\u0010\r\u001a\u00020\u000e2\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00110\u00102\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u001b\u001a\u00020\u001cJ\u0016\u0010A\u001a\b\u0012\u0004\u0012\u00020#0B2\u0006\u0010C\u001a\u00020\u0019H\u0016J\u0016\u0010D\u001a\u0004\u0018\u00010 *\u00020\u001a2\u0006\u00107\u001a\u00020#H\u0002R\u0013\u0010\u0006\u001a\u00070\u0007¢\u0006\u0002\b\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u001c\u0010\t\u001a\u0010\u0012\u0004\u0012\u00020\u000b\u0012\u0006\u0012\u0004\u0018\u00010\f0\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00110\u0010X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0013X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0015X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0017X\u0082.¢\u0006\u0002\n\u0000R\u001c\u0010\u0018\u001a\u0010\u0012\u0004\u0012\u00020\u0019\u0012\u0006\u0012\u0004\u0018\u00010\u001a0\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001b\u001a\u00020\u001cX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006F"}, d2 = {"Lorg/jetbrains/kotlin/cli/jvm/compiler/KotlinCliJavaFileManagerImpl;", "Lorg/jetbrains/kotlin/com/intellij/core/CoreJavaFileManager;", "Lorg/jetbrains/kotlin/resolve/jvm/KotlinCliJavaFileManager;", "myPsiManager", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiManager;", "(Lcom/intellij/psi/PsiManager;)V", "allScope", "Lorg/jetbrains/kotlin/com/intellij/psi/search/GlobalSearchScope;", "Lorg/jetbrains/annotations/NotNull;", "binaryCache", "", "Lorg/jetbrains/kotlin/name/ClassId;", "Lorg/jetbrains/kotlin/load/java/structure/JavaClass;", "index", "Lorg/jetbrains/kotlin/cli/jvm/index/JvmDependenciesIndex;", "packagePartProviders", "", "Lorg/jetbrains/kotlin/cli/jvm/compiler/JvmPackagePartProvider;", "perfCounter", "Lorg/jetbrains/kotlin/util/PerformanceCounter;", "signatureParsingComponent", "Lorg/jetbrains/kotlin/load/java/structure/impl/classFiles/BinaryClassSignatureParser;", "singleJavaFileRootsIndex", "Lorg/jetbrains/kotlin/cli/jvm/index/SingleJavaFileRootsIndex;", "topLevelClassesCache", "Lorg/jetbrains/kotlin/name/FqName;", "Lorg/jetbrains/kotlin/com/intellij/openapi/vfs/VirtualFile;", "usePsiClassFilesReading", "", "createJavaClassByPsiClass", "Lorg/jetbrains/kotlin/load/java/structure/impl/JavaClassImpl;", "psiClass", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiClass;", "findClass", "qName", "", "scope", "request", "Lorg/jetbrains/kotlin/load/java/JavaClassFinder$Request;", "searchScope", "classId", "findClasses", "", "(Ljava/lang/String;Lcom/intellij/psi/search/GlobalSearchScope;)[Lcom/intellij/psi/PsiClass;", "findModules", "", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiJavaModule;", "moduleName", "findPackage", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiPackage;", "packageName", "findPsiClass", "findVirtualFileForTopLevelClass", "findVirtualFileGivenPackage", "packageDir", "classNameWithInnerClasses", "rootType", "Lorg/jetbrains/kotlin/cli/jvm/index/JavaRoot$RootType;", "forEachClassId", "", "fqName", "block", "Lkotlin/Function1;", "getNonTrivialPackagePrefixes", "initialize", "knownClassNamesInPackage", "", "packageFqName", "findPsiClassInVirtualFile", "Companion", "cli-base"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KotlinCliJavaFileManagerImpl extends CoreJavaFileManager implements KotlinCliJavaFileManager {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final Logger LOG;
    private final GlobalSearchScope allScope;
    private final Map<ClassId, JavaClass> binaryCache;
    private JvmDependenciesIndex index;
    private final PsiManager myPsiManager;
    private List<JvmPackagePartProvider> packagePartProviders;
    private final PerformanceCounter perfCounter;
    private final BinaryClassSignatureParser signatureParsingComponent;
    private SingleJavaFileRootsIndex singleJavaFileRootsIndex;
    private final Map<FqName, VirtualFile> topLevelClassesCache;
    private boolean usePsiClassFilesReading;

    /* JADX INFO: compiled from: KotlinCliJavaFileManagerImpl.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[JavaRoot.RootType.values().length];
            try {
                iArr[JavaRoot.RootType.BINARY.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[JavaRoot.RootType.BINARY_SIG.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[JavaRoot.RootType.SOURCE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KotlinCliJavaFileManagerImpl(PsiManager myPsiManager) {
        super(myPsiManager);
        Intrinsics.checkNotNullParameter(myPsiManager, "myPsiManager");
        this.myPsiManager = myPsiManager;
        this.perfCounter = PerformanceCounter.Companion.create$default(PerformanceCounter.INSTANCE, "Find Java class", false, 2, null);
        this.topLevelClassesCache = new THashMap();
        GlobalSearchScope globalSearchScopeAllScope = GlobalSearchScope.allScope(myPsiManager.getProject());
        Intrinsics.checkNotNullExpressionValue(globalSearchScopeAllScope, "allScope(...)");
        this.allScope = globalSearchScopeAllScope;
        this.binaryCache = new THashMap();
        this.signatureParsingComponent = new BinaryClassSignatureParser();
    }

    public final void initialize(JvmDependenciesIndex index, List<JvmPackagePartProvider> packagePartProviders, SingleJavaFileRootsIndex singleJavaFileRootsIndex, boolean usePsiClassFilesReading) {
        Intrinsics.checkNotNullParameter(index, "index");
        Intrinsics.checkNotNullParameter(packagePartProviders, "packagePartProviders");
        Intrinsics.checkNotNullParameter(singleJavaFileRootsIndex, "singleJavaFileRootsIndex");
        this.index = index;
        this.packagePartProviders = packagePartProviders;
        this.singleJavaFileRootsIndex = singleJavaFileRootsIndex;
        this.usePsiClassFilesReading = usePsiClassFilesReading;
    }

    private final PsiClass findPsiClass(final ClassId classId, final GlobalSearchScope searchScope) {
        return (PsiClass) this.perfCounter.time(new Function0<PsiClass>() { // from class: org.jetbrains.kotlin.cli.jvm.compiler.KotlinCliJavaFileManagerImpl.findPsiClass.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final PsiClass invoke() {
                VirtualFile virtualFileFindVirtualFileForTopLevelClass = KotlinCliJavaFileManagerImpl.this.findVirtualFileForTopLevelClass(classId, searchScope);
                if (virtualFileFindVirtualFileForTopLevelClass == null) {
                    return null;
                }
                KotlinCliJavaFileManagerImpl kotlinCliJavaFileManagerImpl = KotlinCliJavaFileManagerImpl.this;
                String strAsString = classId.getRelativeClassName().asString();
                Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
                return kotlinCliJavaFileManagerImpl.findPsiClassInVirtualFile(virtualFileFindVirtualFileForTopLevelClass, strAsString);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final VirtualFile findVirtualFileForTopLevelClass(ClassId classId, GlobalSearchScope searchScope) {
        JvmDependenciesIndex jvmDependenciesIndex;
        final String strAsString = classId.getRelativeClassName().asString();
        Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
        FqName packageFqName = classId.getPackageFqName();
        List<Name> listPathSegments = classId.getRelativeClassName().pathSegments();
        Intrinsics.checkNotNullExpressionValue(listPathSegments, "pathSegments(...)");
        FqName fqNameChild = packageFqName.child((Name) CollectionsKt.first((List) listPathSegments));
        Intrinsics.checkNotNullExpressionValue(fqNameChild, "child(...)");
        Map<FqName, VirtualFile> map = this.topLevelClassesCache;
        VirtualFile virtualFileFindJavaSourceClass = map.get(fqNameChild);
        if (virtualFileFindJavaSourceClass == null) {
            ClassId classId2 = ClassId.topLevel(fqNameChild);
            Intrinsics.checkNotNullExpressionValue(classId2, "topLevel(...)");
            SingleJavaFileRootsIndex singleJavaFileRootsIndex = this.singleJavaFileRootsIndex;
            if (singleJavaFileRootsIndex == null) {
                Intrinsics.throwUninitializedPropertyAccessException("singleJavaFileRootsIndex");
                singleJavaFileRootsIndex = null;
            }
            virtualFileFindJavaSourceClass = singleJavaFileRootsIndex.findJavaSourceClass(classId2);
            if (virtualFileFindJavaSourceClass == null) {
                JvmDependenciesIndex jvmDependenciesIndex2 = this.index;
                if (jvmDependenciesIndex2 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("index");
                    jvmDependenciesIndex = null;
                } else {
                    jvmDependenciesIndex = jvmDependenciesIndex2;
                }
                virtualFileFindJavaSourceClass = (VirtualFile) JvmDependenciesIndex.findClass$default(jvmDependenciesIndex, classId2, null, new Function2<VirtualFile, JavaRoot.RootType, VirtualFile>() { // from class: org.jetbrains.kotlin.cli.jvm.compiler.KotlinCliJavaFileManagerImpl$findVirtualFileForTopLevelClass$1$1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(2);
                    }

                    @Override // kotlin.jvm.functions.Function2
                    public final VirtualFile invoke(VirtualFile dir, JavaRoot.RootType type) {
                        Intrinsics.checkNotNullParameter(dir, "dir");
                        Intrinsics.checkNotNullParameter(type, "type");
                        return this.this$0.findVirtualFileGivenPackage(dir, strAsString, type);
                    }
                }, 2, null);
            }
            map.put(fqNameChild, virtualFileFindJavaSourceClass);
        }
        VirtualFile virtualFile = virtualFileFindJavaSourceClass;
        if (virtualFile == null || !searchScope.contains(virtualFile)) {
            return null;
        }
        return virtualFile;
    }

    public final JavaClass findClass(ClassId classId, GlobalSearchScope searchScope) {
        Intrinsics.checkNotNullParameter(classId, "classId");
        Intrinsics.checkNotNullParameter(searchScope, "searchScope");
        return findClass(new JavaClassFinder.Request(classId, null, null, 6, null), searchScope);
    }

    @Override // org.jetbrains.kotlin.resolve.jvm.KotlinCliJavaFileManager
    public JavaClass findClass(JavaClassFinder.Request request, GlobalSearchScope searchScope) throws IOException {
        JavaClass javaClassFindInnerClass;
        Intrinsics.checkNotNullParameter(request, "request");
        Intrinsics.checkNotNullParameter(searchScope, "searchScope");
        ClassId classId = request.getClassId();
        byte[] previouslyFoundClassFileContent = request.getPreviouslyFoundClassFileContent();
        JavaClass outerClass = request.getOuterClass();
        VirtualFile virtualFileFindVirtualFileForTopLevelClass = findVirtualFileForTopLevelClass(classId, searchScope);
        BinaryJavaClass binaryJavaClass = null;
        if (virtualFileFindVirtualFileForTopLevelClass == null) {
            return null;
        }
        if (!this.usePsiClassFilesReading && (Intrinsics.areEqual(virtualFileFindVirtualFileForTopLevelClass.getExtension(), "class") || Intrinsics.areEqual(virtualFileFindVirtualFileForTopLevelClass.getExtension(), Options.TYPE_SIG))) {
            Map<ClassId, JavaClass> map = this.binaryCache;
            JavaClass javaClass = map.get(classId);
            if (javaClass == null) {
                ClassId outerClassId = classId.getOuterClassId();
                if (outerClassId != null) {
                    if (outerClass == null) {
                        Intrinsics.checkNotNull(outerClassId);
                        outerClass = findClass(outerClassId, searchScope);
                    }
                    if (outerClass instanceof BinaryJavaClass) {
                        Name shortClassName = classId.getShortClassName();
                        Intrinsics.checkNotNullExpressionValue(shortClassName, "getShortClassName(...)");
                        javaClassFindInnerClass = ((BinaryJavaClass) outerClass).findInnerClass(shortClassName, previouslyFoundClassFileContent);
                    } else if (outerClass != null) {
                        Name shortClassName2 = classId.getShortClassName();
                        Intrinsics.checkNotNullExpressionValue(shortClassName2, "getShortClassName(...)");
                        javaClassFindInnerClass = outerClass.findInnerClass(shortClassName2);
                    }
                    binaryJavaClass = javaClassFindInnerClass;
                } else {
                    if (previouslyFoundClassFileContent == null) {
                        previouslyFoundClassFileContent = virtualFileFindVirtualFileForTopLevelClass.contentsToByteArray();
                        Intrinsics.checkNotNullExpressionValue(previouslyFoundClassFileContent, "contentsToByteArray(...)");
                    }
                    byte[] bArr = previouslyFoundClassFileContent;
                    String nameWithoutExtension = virtualFileFindVirtualFileForTopLevelClass.getNameWithoutExtension();
                    Intrinsics.checkNotNullExpressionValue(nameWithoutExtension, "getNameWithoutExtension(...)");
                    if (!StringsKt.contains$default((CharSequence) nameWithoutExtension, (CharSequence) "$", false, 2, (Object) null) || !OtherKt.isNotTopLevelClass(bArr)) {
                        ClassifierResolutionContext classifierResolutionContext = new ClassifierResolutionContext(new Function1<ClassId, JavaClass>() { // from class: org.jetbrains.kotlin.cli.jvm.compiler.KotlinCliJavaFileManagerImpl$findClass$1$resolver$1
                            {
                                super(1);
                            }

                            @Override // kotlin.jvm.functions.Function1
                            public final JavaClass invoke(ClassId it) {
                                Intrinsics.checkNotNullParameter(it, "it");
                                KotlinCliJavaFileManagerImpl kotlinCliJavaFileManagerImpl = this.this$0;
                                return kotlinCliJavaFileManagerImpl.findClass(it, kotlinCliJavaFileManagerImpl.allScope);
                            }
                        });
                        FqName fqNameAsSingleFqName = classId.asSingleFqName();
                        Intrinsics.checkNotNullExpressionValue(fqNameAsSingleFqName, "asSingleFqName(...)");
                        binaryJavaClass = new BinaryJavaClass(virtualFileFindVirtualFileForTopLevelClass, fqNameAsSingleFqName, classifierResolutionContext, this.signatureParsingComponent, 0, null, bArr, 16, null);
                    }
                }
                map.put(classId, binaryJavaClass);
                javaClass = binaryJavaClass;
            }
            return javaClass;
        }
        String strAsString = classId.getRelativeClassName().asString();
        Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
        PsiClass psiClassFindPsiClassInVirtualFile = findPsiClassInVirtualFile(virtualFileFindVirtualFileForTopLevelClass, strAsString);
        return psiClassFindPsiClassInVirtualFile != null ? createJavaClassByPsiClass(psiClassFindPsiClassInVirtualFile) : null;
    }

    private final JavaClassImpl createJavaClassByPsiClass(PsiClass psiClass) {
        Project project = this.myPsiManager.getProject();
        Intrinsics.checkNotNullExpressionValue(project, "getProject(...)");
        return new JavaClassImpl((JavaElementPsiSource<PsiClass>) JavaElementSourceFactory.INSTANCE.getInstance(project).createPsiSource(psiClass));
    }

    @Override // org.jetbrains.kotlin.com.intellij.core.CoreJavaFileManager, org.jetbrains.kotlin.com.intellij.psi.impl.file.impl.JavaFileManager
    public PsiClass[] findClasses(final String qName, final GlobalSearchScope scope) {
        Intrinsics.checkNotNullParameter(qName, "qName");
        Intrinsics.checkNotNullParameter(scope, "scope");
        return (PsiClass[]) this.perfCounter.time(new Function0<PsiClass[]>() { // from class: org.jetbrains.kotlin.cli.jvm.compiler.KotlinCliJavaFileManagerImpl.findClasses.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            /* JADX WARN: Code duplicated, block: B:16:0x004e  */
            @Override // kotlin.jvm.functions.Function0
            public final PsiClass[] invoke() {
                PsiClass psiClassFindPsiClassInVirtualFile;
                JvmDependenciesIndex jvmDependenciesIndex;
                final ArrayList arrayList = new ArrayList(1);
                final KotlinCliJavaFileManagerImpl kotlinCliJavaFileManagerImpl = KotlinCliJavaFileManagerImpl.this;
                String str = qName;
                final GlobalSearchScope globalSearchScope = scope;
                ClassId safeTopLevelClassId = KotlinCliJavaFileManagerImplKt.toSafeTopLevelClassId(str);
                if (safeTopLevelClassId != null) {
                    while (true) {
                        final String strAsString = safeTopLevelClassId.getRelativeClassName().asString();
                        Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
                        ArrayList arrayList2 = arrayList;
                        SingleJavaFileRootsIndex singleJavaFileRootsIndex = kotlinCliJavaFileManagerImpl.singleJavaFileRootsIndex;
                        if (singleJavaFileRootsIndex == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("singleJavaFileRootsIndex");
                            singleJavaFileRootsIndex = null;
                        }
                        VirtualFile virtualFileFindJavaSourceClass = singleJavaFileRootsIndex.findJavaSourceClass(safeTopLevelClassId);
                        if (virtualFileFindJavaSourceClass == null) {
                            psiClassFindPsiClassInVirtualFile = null;
                        } else {
                            if (!Boolean.valueOf(globalSearchScope.contains(virtualFileFindJavaSourceClass)).booleanValue()) {
                                virtualFileFindJavaSourceClass = null;
                            }
                            if (virtualFileFindJavaSourceClass != null) {
                                psiClassFindPsiClassInVirtualFile = kotlinCliJavaFileManagerImpl.findPsiClassInVirtualFile(virtualFileFindJavaSourceClass, strAsString);
                            } else {
                                psiClassFindPsiClassInVirtualFile = null;
                            }
                        }
                        org.jetbrains.kotlin.utils.CollectionsKt.addIfNotNull(arrayList2, psiClassFindPsiClassInVirtualFile);
                        JvmDependenciesIndex jvmDependenciesIndex2 = kotlinCliJavaFileManagerImpl.index;
                        if (jvmDependenciesIndex2 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("index");
                            jvmDependenciesIndex = null;
                        } else {
                            jvmDependenciesIndex = jvmDependenciesIndex2;
                        }
                        FqName packageFqName = safeTopLevelClassId.getPackageFqName();
                        Intrinsics.checkNotNullExpressionValue(packageFqName, "getPackageFqName(...)");
                        JvmDependenciesIndex.traverseDirectoriesInPackage$default(jvmDependenciesIndex, packageFqName, null, new Function2<VirtualFile, JavaRoot.RootType, Boolean>() { // from class: org.jetbrains.kotlin.cli.jvm.compiler.KotlinCliJavaFileManagerImpl$findClasses$1$1$2
                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            {
                                super(2);
                            }

                            @Override // kotlin.jvm.functions.Function2
                            public final Boolean invoke(VirtualFile dir, JavaRoot.RootType rootType) {
                                Intrinsics.checkNotNullParameter(dir, "dir");
                                Intrinsics.checkNotNullParameter(rootType, "rootType");
                                VirtualFile virtualFileFindVirtualFileGivenPackage = kotlinCliJavaFileManagerImpl.findVirtualFileGivenPackage(dir, strAsString, rootType);
                                PsiClass psiClassFindPsiClassInVirtualFile2 = null;
                                if (virtualFileFindVirtualFileGivenPackage != null) {
                                    if (!globalSearchScope.contains(virtualFileFindVirtualFileGivenPackage)) {
                                        virtualFileFindVirtualFileGivenPackage = null;
                                    }
                                    if (virtualFileFindVirtualFileGivenPackage != null) {
                                        psiClassFindPsiClassInVirtualFile2 = kotlinCliJavaFileManagerImpl.findPsiClassInVirtualFile(virtualFileFindVirtualFileGivenPackage, strAsString);
                                    }
                                }
                                if (psiClassFindPsiClassInVirtualFile2 != null) {
                                    arrayList.add(psiClassFindPsiClassInVirtualFile2);
                                }
                                return true;
                            }
                        }, 2, null);
                        if (!arrayList2.isEmpty()) {
                            return (PsiClass[]) arrayList2.toArray(new PsiClass[0]);
                        }
                        FqName packageFqName2 = safeTopLevelClassId.getPackageFqName();
                        Intrinsics.checkNotNullExpressionValue(packageFqName2, "getPackageFqName(...)");
                        if (!packageFqName2.isRoot()) {
                            safeTopLevelClassId = new ClassId(packageFqName2.parent(), new FqName(packageFqName2.shortName().asString() + '.' + safeTopLevelClassId.getRelativeClassName().asString()), false);
                        }
                    }
                }
                PsiClass[] EMPTY_ARRAY = PsiClass.EMPTY_ARRAY;
                Intrinsics.checkNotNullExpressionValue(EMPTY_ARRAY, "EMPTY_ARRAY");
                return EMPTY_ARRAY;
            }
        });
    }

    @Override // org.jetbrains.kotlin.com.intellij.core.CoreJavaFileManager, org.jetbrains.kotlin.com.intellij.psi.impl.file.impl.JavaFileManager
    public PsiPackage findPackage(String packageName) {
        Intrinsics.checkNotNullParameter(packageName, "packageName");
        final Ref.BooleanRef booleanRef = new Ref.BooleanRef();
        FqName safeFqName = KotlinCliJavaFileManagerImplKt.toSafeFqName(packageName);
        if (safeFqName == null) {
            return null;
        }
        JvmDependenciesIndex jvmDependenciesIndex = this.index;
        if (jvmDependenciesIndex == null) {
            Intrinsics.throwUninitializedPropertyAccessException("index");
            jvmDependenciesIndex = null;
        }
        JvmDependenciesIndex.traverseDirectoriesInPackage$default(jvmDependenciesIndex, safeFqName, null, new Function2<VirtualFile, JavaRoot.RootType, Boolean>() { // from class: org.jetbrains.kotlin.cli.jvm.compiler.KotlinCliJavaFileManagerImpl.findPackage.1
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Boolean invoke(VirtualFile virtualFile, JavaRoot.RootType rootType) {
                Intrinsics.checkNotNullParameter(virtualFile, "<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter(rootType, "<anonymous parameter 1>");
                booleanRef.element = true;
                return false;
            }
        }, 2, null);
        if (!booleanRef.element) {
            List<JvmPackagePartProvider> list = this.packagePartProviders;
            if (list == null) {
                Intrinsics.throwUninitializedPropertyAccessException("packagePartProviders");
                list = null;
            }
            List<JvmPackagePartProvider> list2 = list;
            boolean z = false;
            if (!(list2 instanceof Collection) || !list2.isEmpty()) {
                Iterator<T> it = list2.iterator();
                while (it.hasNext()) {
                    if (!((JvmPackagePartProvider) it.next()).findPackageParts(packageName).isEmpty()) {
                        z = true;
                        break;
                    }
                }
            }
            booleanRef.element = z;
        }
        if (!booleanRef.element) {
            SingleJavaFileRootsIndex singleJavaFileRootsIndex = this.singleJavaFileRootsIndex;
            if (singleJavaFileRootsIndex == null) {
                Intrinsics.throwUninitializedPropertyAccessException("singleJavaFileRootsIndex");
                singleJavaFileRootsIndex = null;
            }
            booleanRef.element = !singleJavaFileRootsIndex.findJavaSourceClasses(safeFqName).isEmpty();
        }
        if (booleanRef.element) {
            return new PsiPackageImpl(packageName, this.myPsiManager) { // from class: org.jetbrains.kotlin.cli.jvm.compiler.KotlinCliJavaFileManagerImpl.findPackage.3
                @Override // org.jetbrains.kotlin.com.intellij.psi.impl.file.PsiPackageImpl, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement, org.jetbrains.kotlin.com.intellij.pom.PomTarget
                public boolean isValid() {
                    return true;
                }
            };
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final VirtualFile findVirtualFileGivenPackage(VirtualFile packageDir, String classNameWithInnerClasses, JavaRoot.RootType rootType) {
        VirtualFile virtualFileFindChild;
        String strSubstringBefore$default = StringsKt.substringBefore$default(classNameWithInnerClasses, '.', (String) null, 2, (Object) null);
        int i = WhenMappings.$EnumSwitchMapping$0[rootType.ordinal()];
        if (i == 1) {
            virtualFileFindChild = packageDir.findChild(strSubstringBefore$default + ".class");
        } else if (i == 2) {
            virtualFileFindChild = packageDir.findChild(strSubstringBefore$default + ApkSignatureBlock.FILE_EXT);
        } else {
            if (i != 3) {
                throw new NoWhenBranchMatchedException();
            }
            virtualFileFindChild = packageDir.findChild(strSubstringBefore$default + SuffixConstants.SUFFIX_STRING_java);
        }
        if (virtualFileFindChild == null) {
            return null;
        }
        if (virtualFileFindChild.isValid()) {
            return virtualFileFindChild;
        }
        LOG.error("Invalid child of valid parent: " + virtualFileFindChild.getPath() + "; " + packageDir.isValid() + " path=" + packageDir.getPath());
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final PsiClass findPsiClassInVirtualFile(VirtualFile virtualFile, String str) {
        PsiFile psiFileFindFile = this.myPsiManager.findFile(virtualFile);
        PsiClassOwner psiClassOwner = psiFileFindFile instanceof PsiClassOwner ? (PsiClassOwner) psiFileFindFile : null;
        if (psiClassOwner == null) {
            return null;
        }
        return INSTANCE.findClassInPsiFile(str, psiClassOwner);
    }

    @Override // org.jetbrains.kotlin.resolve.jvm.KotlinCliJavaFileManager
    public Set<String> knownClassNamesInPackage(FqName packageFqName) {
        JvmDependenciesIndex jvmDependenciesIndex;
        Intrinsics.checkNotNullParameter(packageFqName, "packageFqName");
        final THashSet tHashSet = new THashSet();
        JvmDependenciesIndex jvmDependenciesIndex2 = this.index;
        SingleJavaFileRootsIndex singleJavaFileRootsIndex = null;
        if (jvmDependenciesIndex2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("index");
            jvmDependenciesIndex = null;
        } else {
            jvmDependenciesIndex = jvmDependenciesIndex2;
        }
        JvmDependenciesIndex.traverseDirectoriesInPackage$default(jvmDependenciesIndex, packageFqName, null, new Function2<VirtualFile, JavaRoot.RootType, Boolean>() { // from class: org.jetbrains.kotlin.cli.jvm.compiler.KotlinCliJavaFileManagerImpl.knownClassNamesInPackage.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Boolean invoke(VirtualFile dir, JavaRoot.RootType rootType) {
                Intrinsics.checkNotNullParameter(dir, "dir");
                Intrinsics.checkNotNullParameter(rootType, "<anonymous parameter 1>");
                VirtualFile[] children = dir.getChildren();
                Intrinsics.checkNotNullExpressionValue(children, "getChildren(...)");
                for (VirtualFile virtualFile : children) {
                    if (Intrinsics.areEqual(virtualFile.getExtension(), "class") || Intrinsics.areEqual(virtualFile.getExtension(), SuffixConstants.EXTENSION_java) || Intrinsics.areEqual(virtualFile.getExtension(), Options.TYPE_SIG)) {
                        tHashSet.add(virtualFile.getNameWithoutExtension());
                    }
                }
                return true;
            }
        }, 2, null);
        SingleJavaFileRootsIndex singleJavaFileRootsIndex2 = this.singleJavaFileRootsIndex;
        if (singleJavaFileRootsIndex2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("singleJavaFileRootsIndex");
        } else {
            singleJavaFileRootsIndex = singleJavaFileRootsIndex2;
        }
        for (ClassId classId : singleJavaFileRootsIndex.findJavaSourceClasses(packageFqName)) {
            classId.isNestedClass();
            tHashSet.add(classId.getShortClassName().asString());
        }
        return tHashSet;
    }

    @Override // org.jetbrains.kotlin.com.intellij.core.CoreJavaFileManager, org.jetbrains.kotlin.com.intellij.psi.impl.file.impl.JavaFileManager
    public Collection<PsiJavaModule> findModules(String moduleName, GlobalSearchScope scope) {
        Intrinsics.checkNotNullParameter(moduleName, "moduleName");
        Intrinsics.checkNotNullParameter(scope, "scope");
        return SetsKt.emptySet();
    }

    @Override // org.jetbrains.kotlin.com.intellij.core.CoreJavaFileManager, org.jetbrains.kotlin.com.intellij.psi.impl.file.impl.JavaFileManager
    public Collection<String> getNonTrivialPackagePrefixes() {
        return CollectionsKt.emptyList();
    }

    /* JADX INFO: compiled from: KotlinCliJavaFileManagerImpl.kt */
    @Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u001a\u0010\u0006\u001a\u0004\u0018\u00010\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u0007H\u0002J\u001a\u0010\u000b\u001a\u0004\u0018\u00010\u00072\u0006\u0010\f\u001a\u00020\t2\u0006\u0010\r\u001a\u00020\u000eH\u0002R\u0013\u0010\u0003\u001a\u00070\u0004¢\u0006\u0002\b\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/cli/jvm/compiler/KotlinCliJavaFileManagerImpl$Companion;", "", "()V", "LOG", "Lorg/jetbrains/kotlin/com/intellij/openapi/diagnostic/Logger;", "Lorg/jetbrains/annotations/NotNull;", "findClassByTopLevelClass", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiClass;", "className", "", "topLevelClass", "findClassInPsiFile", "classNameWithInnerClassesDotSeparated", "file", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiClassOwner;", "cli-base"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final PsiClass findClassInPsiFile(String classNameWithInnerClassesDotSeparated, PsiClassOwner file) {
            PsiClass[] classes = file.getClasses();
            Intrinsics.checkNotNullExpressionValue(classes, "getClasses(...)");
            for (PsiClass psiClass : classes) {
                Intrinsics.checkNotNull(psiClass);
                PsiClass psiClassFindClassByTopLevelClass = findClassByTopLevelClass(classNameWithInnerClassesDotSeparated, psiClass);
                if (psiClassFindClassByTopLevelClass != null) {
                    return psiClassFindClassByTopLevelClass;
                }
            }
            return null;
        }

        private final PsiClass findClassByTopLevelClass(String className, PsiClass topLevelClass) {
            if (StringsKt.indexOf$default((CharSequence) className, '.', 0, false, 6, (Object) null) < 0) {
                if (Intrinsics.areEqual(className, topLevelClass.getMyName())) {
                    return topLevelClass;
                }
                return null;
            }
            Iterator<String> it = StringUtil.split(className, ".").iterator();
            if (!it.hasNext() || !Intrinsics.areEqual(it.next(), topLevelClass.getMyName())) {
                return null;
            }
            while (it.hasNext()) {
                topLevelClass = topLevelClass.findInnerClassByName(it.next(), false);
                if (topLevelClass == null) {
                    return null;
                }
            }
            return topLevelClass;
        }
    }

    static {
        Logger logger = Logger.getInstance((Class<?>) KotlinCliJavaFileManagerImpl.class);
        Intrinsics.checkNotNullExpressionValue(logger, "getInstance(...)");
        LOG = logger;
    }

    @Override // org.jetbrains.kotlin.com.intellij.core.CoreJavaFileManager, org.jetbrains.kotlin.com.intellij.psi.impl.file.impl.JavaFileManager
    public PsiClass findClass(String qName, GlobalSearchScope scope) {
        Intrinsics.checkNotNullParameter(qName, "qName");
        Intrinsics.checkNotNullParameter(scope, "scope");
        ClassId safeTopLevelClassId = KotlinCliJavaFileManagerImplKt.toSafeTopLevelClassId(qName);
        if (safeTopLevelClassId == null) {
            return null;
        }
        while (true) {
            PsiClass psiClassFindPsiClass = findPsiClass(safeTopLevelClassId, scope);
            if (psiClassFindPsiClass != null) {
                return psiClassFindPsiClass;
            }
            FqName packageFqName = safeTopLevelClassId.getPackageFqName();
            Intrinsics.checkNotNullExpressionValue(packageFqName, "getPackageFqName(...)");
            if (packageFqName.isRoot()) {
                return null;
            }
            safeTopLevelClassId = new ClassId(packageFqName.parent(), new FqName(packageFqName.shortName().asString() + '.' + safeTopLevelClassId.getRelativeClassName().asString()), false);
        }
    }
}

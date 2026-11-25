package org.jetbrains.kotlin.resolve.jvm.modules;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlin.text.StringsKt;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiAnnotation;
import org.jetbrains.kotlin.com.intellij.psi.PsiJavaModule;
import org.jetbrains.kotlin.com.intellij.psi.PsiKeyword;
import org.jetbrains.kotlin.com.intellij.psi.PsiPackageAccessibilityStatement;
import org.jetbrains.kotlin.com.intellij.psi.PsiRequiresStatement;
import org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.load.java.JavaClassFinder;
import org.jetbrains.kotlin.load.java.structure.JavaAnnotation;
import org.jetbrains.kotlin.load.java.structure.JavaClass;
import org.jetbrains.kotlin.load.java.structure.impl.JavaAnnotationImpl;
import org.jetbrains.kotlin.load.java.structure.impl.classFiles.BinaryClassSignatureParser;
import org.jetbrains.kotlin.load.java.structure.impl.classFiles.BinaryJavaAnnotation;
import org.jetbrains.kotlin.load.java.structure.impl.classFiles.ClassifierResolutionContext;
import org.jetbrains.kotlin.load.java.structure.impl.source.JavaElementSourceFactory;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.resolve.jvm.KotlinCliJavaFileManager;
import org.jetbrains.org.objectweb.asm.AnnotationVisitor;
import org.jetbrains.org.objectweb.asm.ClassReader;
import org.jetbrains.org.objectweb.asm.ClassVisitor;
import org.jetbrains.org.objectweb.asm.ModuleVisitor;

/* JADX INFO: compiled from: JavaModuleInfo.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\f\u0018\u0000 \u00132\u00020\u0001:\u0003\u0013\u0014\u0015B7\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u0012\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\b0\u0005\u0012\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\n0\u0005¢\u0006\u0002\u0010\u000bJ\b\u0010\u0012\u001a\u00020\u0003H\u0016R\u0017\u0010\t\u001a\b\u0012\u0004\u0012\u00020\n0\u0005¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0017\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\b0\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\rR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\r¨\u0006\u0016"}, d2 = {"Lorg/jetbrains/kotlin/resolve/jvm/modules/JavaModuleInfo;", "", "moduleName", "", PsiKeyword.REQUIRES, "", "Lorg/jetbrains/kotlin/resolve/jvm/modules/JavaModuleInfo$Requires;", PsiKeyword.EXPORTS, "Lorg/jetbrains/kotlin/resolve/jvm/modules/JavaModuleInfo$Exports;", "annotations", "Lorg/jetbrains/kotlin/load/java/structure/JavaAnnotation;", "(Ljava/lang/String;Ljava/util/List;Ljava/util/List;Ljava/util/List;)V", "getAnnotations", "()Ljava/util/List;", "getExports", "getModuleName", "()Ljava/lang/String;", "getRequires", "toString", "Companion", "Exports", "Requires", "frontend.java"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JavaModuleInfo {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final List<JavaAnnotation> annotations;
    private final List<Exports> exports;
    private final String moduleName;
    private final List<Requires> requires;

    /* JADX WARN: Multi-variable type inference failed */
    public JavaModuleInfo(String moduleName, List<Requires> requires, List<Exports> exports, List<? extends JavaAnnotation> annotations) {
        Intrinsics.checkNotNullParameter(moduleName, "moduleName");
        Intrinsics.checkNotNullParameter(requires, "requires");
        Intrinsics.checkNotNullParameter(exports, "exports");
        Intrinsics.checkNotNullParameter(annotations, "annotations");
        this.moduleName = moduleName;
        this.requires = requires;
        this.exports = exports;
        this.annotations = annotations;
    }

    public final String getModuleName() {
        return this.moduleName;
    }

    public final List<Requires> getRequires() {
        return this.requires;
    }

    public final List<Exports> getExports() {
        return this.exports;
    }

    public final List<JavaAnnotation> getAnnotations() {
        return this.annotations;
    }

    /* JADX INFO: compiled from: JavaModuleInfo.kt */
    @Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\b\n\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\t\u0010\n\u001a\u00020\u0003HÆ\u0003J\t\u0010\u000b\u001a\u00020\u0005HÆ\u0003J\u001d\u0010\f\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005HÆ\u0001J\u0013\u0010\r\u001a\u00020\u00052\b\u0010\u000e\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u000f\u001a\u00020\u0010HÖ\u0001J\t\u0010\u0011\u001a\u00020\u0003HÖ\u0001R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0004\u0010\u0007R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\t¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/resolve/jvm/modules/JavaModuleInfo$Requires;", "", "moduleName", "", "isTransitive", "", "(Ljava/lang/String;Z)V", "()Z", "getModuleName", "()Ljava/lang/String;", "component1", "component2", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "other", "hashCode", "", "toString", "frontend.java"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final /* data */ class Requires {
        private final boolean isTransitive;
        private final String moduleName;

        public static /* synthetic */ Requires copy$default(Requires requires, String str, boolean z, int i, Object obj) {
            if ((i & 1) != 0) {
                str = requires.moduleName;
            }
            if ((i & 2) != 0) {
                z = requires.isTransitive;
            }
            return requires.copy(str, z);
        }

        /* JADX INFO: renamed from: component1, reason: from getter */
        public final String getModuleName() {
            return this.moduleName;
        }

        /* JADX INFO: renamed from: component2, reason: from getter */
        public final boolean getIsTransitive() {
            return this.isTransitive;
        }

        public final Requires copy(String moduleName, boolean isTransitive) {
            Intrinsics.checkNotNullParameter(moduleName, "moduleName");
            return new Requires(moduleName, isTransitive);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof Requires)) {
                return false;
            }
            Requires requires = (Requires) other;
            return Intrinsics.areEqual(this.moduleName, requires.moduleName) && this.isTransitive == requires.isTransitive;
        }

        public int hashCode() {
            return (this.moduleName.hashCode() * 31) + Boolean.hashCode(this.isTransitive);
        }

        public String toString() {
            return "Requires(moduleName=" + this.moduleName + ", isTransitive=" + this.isTransitive + ')';
        }

        public Requires(String moduleName, boolean z) {
            Intrinsics.checkNotNullParameter(moduleName, "moduleName");
            this.moduleName = moduleName;
            this.isTransitive = z;
        }

        public final String getModuleName() {
            return this.moduleName;
        }

        public final boolean isTransitive() {
            return this.isTransitive;
        }
    }

    /* JADX INFO: compiled from: JavaModuleInfo.kt */
    @Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\u001b\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\u0002\u0010\u0007J\t\u0010\f\u001a\u00020\u0003HÆ\u0003J\u000f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005HÆ\u0003J#\u0010\u000e\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\u000e\b\u0002\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005HÆ\u0001J\u0013\u0010\u000f\u001a\u00020\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0012\u001a\u00020\u0013HÖ\u0001J\t\u0010\u0014\u001a\u00020\u0006HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/resolve/jvm/modules/JavaModuleInfo$Exports;", "", "packageFqName", "Lorg/jetbrains/kotlin/name/FqName;", "toModules", "", "", "(Lorg/jetbrains/kotlin/name/FqName;Ljava/util/List;)V", "getPackageFqName", "()Lorg/jetbrains/kotlin/name/FqName;", "getToModules", "()Ljava/util/List;", "component1", "component2", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "frontend.java"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final /* data */ class Exports {
        private final FqName packageFqName;
        private final List<String> toModules;

        /* JADX WARN: Multi-variable type inference failed */
        public static /* synthetic */ Exports copy$default(Exports exports, FqName fqName, List list, int i, Object obj) {
            if ((i & 1) != 0) {
                fqName = exports.packageFqName;
            }
            if ((i & 2) != 0) {
                list = exports.toModules;
            }
            return exports.copy(fqName, list);
        }

        /* JADX INFO: renamed from: component1, reason: from getter */
        public final FqName getPackageFqName() {
            return this.packageFqName;
        }

        public final List<String> component2() {
            return this.toModules;
        }

        public final Exports copy(FqName packageFqName, List<String> toModules) {
            Intrinsics.checkNotNullParameter(packageFqName, "packageFqName");
            Intrinsics.checkNotNullParameter(toModules, "toModules");
            return new Exports(packageFqName, toModules);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof Exports)) {
                return false;
            }
            Exports exports = (Exports) other;
            return Intrinsics.areEqual(this.packageFqName, exports.packageFqName) && Intrinsics.areEqual(this.toModules, exports.toModules);
        }

        public int hashCode() {
            return (this.packageFqName.hashCode() * 31) + this.toModules.hashCode();
        }

        public String toString() {
            return "Exports(packageFqName=" + this.packageFqName + ", toModules=" + this.toModules + ')';
        }

        public Exports(FqName packageFqName, List<String> toModules) {
            Intrinsics.checkNotNullParameter(packageFqName, "packageFqName");
            Intrinsics.checkNotNullParameter(toModules, "toModules");
            this.packageFqName = packageFqName;
            this.toModules = toModules;
        }

        public final FqName getPackageFqName() {
            return this.packageFqName;
        }

        public final List<String> getToModules() {
            return this.toModules;
        }
    }

    public String toString() {
        return "Module " + this.moduleName + " (" + this.requires.size() + " requires, " + this.exports.size() + " exports)";
    }

    /* JADX INFO: compiled from: JavaModuleInfo.kt */
    @Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006J \u0010\u0007\u001a\u0004\u0018\u00010\u00042\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/resolve/jvm/modules/JavaModuleInfo$Companion;", "", "()V", CoroutineCodegenUtilKt.SUSPEND_FUNCTION_CREATE_METHOD_NAME, "Lorg/jetbrains/kotlin/resolve/jvm/modules/JavaModuleInfo;", "psiJavaModule", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiJavaModule;", "read", "file", "Lorg/jetbrains/kotlin/com/intellij/openapi/vfs/VirtualFile;", "javaFileManager", "Lorg/jetbrains/kotlin/resolve/jvm/KotlinCliJavaFileManager;", "searchScope", "Lorg/jetbrains/kotlin/com/intellij/psi/search/GlobalSearchScope;", "frontend.java"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final JavaModuleInfo create(PsiJavaModule psiJavaModule) {
            ArrayList arrayListEmptyList;
            Exports exports;
            Intrinsics.checkNotNullParameter(psiJavaModule, "psiJavaModule");
            String name2 = psiJavaModule.getName();
            Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
            Iterable<PsiRequiresStatement> requires = psiJavaModule.getRequires();
            Intrinsics.checkNotNullExpressionValue(requires, "getRequires(...)");
            ArrayList arrayList = new ArrayList();
            Iterator<PsiRequiresStatement> it = requires.iterator();
            while (true) {
                Requires requires2 = null;
                if (!it.hasNext()) {
                    break;
                }
                PsiRequiresStatement next = it.next();
                String moduleName = next.getModuleName();
                if (moduleName != null) {
                    Intrinsics.checkNotNull(moduleName);
                    requires2 = new Requires(moduleName, next.hasModifierProperty("transitive"));
                }
                if (requires2 != null) {
                    arrayList.add(requires2);
                }
            }
            ArrayList arrayList2 = arrayList;
            Iterable<PsiPackageAccessibilityStatement> exports2 = psiJavaModule.getExports();
            Intrinsics.checkNotNullExpressionValue(exports2, "getExports(...)");
            ArrayList arrayList3 = new ArrayList();
            for (PsiPackageAccessibilityStatement psiPackageAccessibilityStatement : exports2) {
                String packageName = psiPackageAccessibilityStatement.getPackageName();
                if (packageName != null) {
                    FqName fqName = new FqName(packageName);
                    List<String> moduleNames = psiPackageAccessibilityStatement.getModuleNames();
                    Intrinsics.checkNotNullExpressionValue(moduleNames, "getModuleNames(...)");
                    exports = new Exports(fqName, moduleNames);
                } else {
                    exports = null;
                }
                if (exports != null) {
                    arrayList3.add(exports);
                }
            }
            ArrayList arrayList4 = arrayList3;
            PsiAnnotation[] annotations = psiJavaModule.getAnnotations();
            Intrinsics.checkNotNullExpressionValue(annotations, "getAnnotations(...)");
            PsiAnnotation[] psiAnnotationArr = annotations;
            int length = psiAnnotationArr.length;
            if (length == 0) {
                arrayListEmptyList = CollectionsKt.emptyList();
            } else if (length != 1) {
                ArrayList arrayList5 = new ArrayList(psiAnnotationArr.length);
                for (PsiAnnotation psiAnnotation : psiAnnotationArr) {
                    JavaElementSourceFactory.Companion companion = JavaElementSourceFactory.INSTANCE;
                    Project project = psiJavaModule.getProject();
                    Intrinsics.checkNotNullExpressionValue(project, "getProject(...)");
                    arrayList5.add(new JavaAnnotationImpl(companion.getInstance(project).createPsiSource(psiAnnotation)));
                }
                arrayListEmptyList = arrayList5;
            } else {
                PsiAnnotation psiAnnotation2 = (PsiAnnotation) ArraysKt.first(psiAnnotationArr);
                JavaElementSourceFactory.Companion companion2 = JavaElementSourceFactory.INSTANCE;
                Project project2 = psiJavaModule.getProject();
                Intrinsics.checkNotNullExpressionValue(project2, "getProject(...)");
                arrayListEmptyList = CollectionsKt.listOf(new JavaAnnotationImpl(companion2.getInstance(project2).createPsiSource(psiAnnotation2)));
            }
            return new JavaModuleInfo(name2, arrayList2, arrayList4, arrayListEmptyList);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public final JavaModuleInfo read(VirtualFile file, final KotlinCliJavaFileManager javaFileManager, final GlobalSearchScope searchScope) {
            Intrinsics.checkNotNullParameter(file, "file");
            Intrinsics.checkNotNullParameter(javaFileManager, "javaFileManager");
            Intrinsics.checkNotNullParameter(searchScope, "searchScope");
            try {
                byte[] bArrContentsToByteArray = file.contentsToByteArray();
                Intrinsics.checkNotNull(bArrContentsToByteArray);
                final Ref.ObjectRef objectRef = new Ref.ObjectRef();
                final ArrayList arrayList = new ArrayList();
                final ArrayList arrayList2 = new ArrayList();
                final ArrayList arrayList3 = new ArrayList();
                try {
                    new ClassReader(bArrContentsToByteArray).accept(new ClassVisitor() { // from class: org.jetbrains.kotlin.resolve.jvm.modules.JavaModuleInfo$Companion$read$1
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(589824);
                        }

                        /* JADX WARN: Multi-variable type inference failed */
                        @Override // org.jetbrains.org.objectweb.asm.ClassVisitor
                        public ModuleVisitor visitModule(String name2, int access, String version) {
                            Intrinsics.checkNotNullParameter(name2, "name");
                            objectRef.element = name2;
                            final ArrayList<JavaModuleInfo.Requires> arrayList4 = arrayList;
                            final ArrayList<JavaModuleInfo.Exports> arrayList5 = arrayList2;
                            return new ModuleVisitor() { // from class: org.jetbrains.kotlin.resolve.jvm.modules.JavaModuleInfo$Companion$read$1$visitModule$1
                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                {
                                    super(589824);
                                }

                                @Override // org.jetbrains.org.objectweb.asm.ModuleVisitor
                                public void visitRequire(String module, int access2, String version2) {
                                    Intrinsics.checkNotNullParameter(module, "module");
                                    arrayList4.add(new JavaModuleInfo.Requires(module, (access2 & 32) != 0));
                                }

                                @Override // org.jetbrains.org.objectweb.asm.ModuleVisitor
                                public void visitExport(String packageFqName, int access2, String[] modules) {
                                    Intrinsics.checkNotNullParameter(packageFqName, "packageFqName");
                                    ArrayList<JavaModuleInfo.Exports> arrayList6 = arrayList5;
                                    FqName fqName = new FqName(StringsKt.replace$default(packageFqName, '/', '.', false, 4, (Object) null));
                                    List list = modules != null ? ArraysKt.toList(modules) : null;
                                    if (list == null) {
                                        list = CollectionsKt.emptyList();
                                    }
                                    arrayList6.add(new JavaModuleInfo.Exports(fqName, list));
                                }
                            };
                        }

                        @Override // org.jetbrains.org.objectweb.asm.ClassVisitor
                        public AnnotationVisitor visitAnnotation(String descriptor, boolean visible) {
                            if (descriptor == null) {
                                return null;
                            }
                            BinaryJavaAnnotation.Companion companion = BinaryJavaAnnotation.INSTANCE;
                            final KotlinCliJavaFileManager kotlinCliJavaFileManager = javaFileManager;
                            final GlobalSearchScope globalSearchScope = searchScope;
                            Pair<JavaAnnotation, AnnotationVisitor> pairCreateAnnotationAndVisitor = companion.createAnnotationAndVisitor(descriptor, new ClassifierResolutionContext(new Function1<ClassId, JavaClass>() { // from class: org.jetbrains.kotlin.resolve.jvm.modules.JavaModuleInfo$Companion$read$1$visitAnnotation$1
                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                {
                                    super(1);
                                }

                                @Override // kotlin.jvm.functions.Function1
                                public final JavaClass invoke(ClassId it) {
                                    Intrinsics.checkNotNullParameter(it, "it");
                                    return kotlinCliJavaFileManager.findClass(new JavaClassFinder.Request(it, null, null, 6, null), globalSearchScope);
                                }
                            }), new BinaryClassSignatureParser(), true);
                            JavaAnnotation javaAnnotationComponent1 = pairCreateAnnotationAndVisitor.component1();
                            AnnotationVisitor annotationVisitorComponent2 = pairCreateAnnotationAndVisitor.component2();
                            arrayList3.add(javaAnnotationComponent1);
                            return annotationVisitorComponent2;
                        }
                    }, 7);
                    String str = (String) objectRef.element;
                    if (str != null) {
                        return new JavaModuleInfo(str, org.jetbrains.kotlin.utils.CollectionsKt.compact(arrayList), org.jetbrains.kotlin.utils.CollectionsKt.compact(arrayList2), arrayList3);
                    }
                    return null;
                } catch (Exception e) {
                    throw new IllegalStateException("Could not load module definition from: " + file + ". The file might be broken by incorrect post-processing via bytecode tools. Please remove this file from the classpath.", e);
                }
            } catch (IOException unused) {
                return null;
            }
        }
    }
}

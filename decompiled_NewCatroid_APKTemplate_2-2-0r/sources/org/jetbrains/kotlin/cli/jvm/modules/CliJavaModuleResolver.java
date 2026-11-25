package org.jetbrains.kotlin.cli.jvm.modules;

import com.reandroid.apkeditor.Options;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.ide.highlighter.JavaClassFileType;
import org.jetbrains.kotlin.com.intellij.ide.highlighter.JavaFileType;
import org.jetbrains.kotlin.com.intellij.openapi.fileTypes.FileType;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.StandardFileSystems;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VfsUtilCore;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.idea.KotlinFileType;
import org.jetbrains.kotlin.load.java.structure.JavaAnnotation;
import org.jetbrains.kotlin.load.kotlin.VirtualFileFinder;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.resolve.jvm.modules.JavaModule;
import org.jetbrains.kotlin.resolve.jvm.modules.JavaModuleInfo;
import org.jetbrains.kotlin.resolve.jvm.modules.JavaModuleResolver;

/* JADX INFO: compiled from: CliJavaModuleResolver.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000Z\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\u0018\u0000 \"2\u00020\u0001:\u0001\"B1\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u0012\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\b0\u0005\u0012\u0006\u0010\t\u001a\u00020\n¢\u0006\u0002\u0010\u000bJ&\u0010\u0013\u001a\u0004\u0018\u00010\u00142\b\u0010\u0015\u001a\u0004\u0018\u00010\u00162\u0006\u0010\u0017\u001a\u00020\u00162\b\u0010\u0018\u001a\u0004\u0018\u00010\u0019H\u0016J\u0012\u0010\u001a\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u001b\u001a\u00020\u0016H\u0002J\u0018\u0010\u001c\u001a\n\u0012\u0004\u0012\u00020\u001d\u0018\u00010\u00052\u0006\u0010\u001e\u001a\u00020\u001fH\u0016J\u0015\u0010 \u001a\u00020!*\u00020\u00062\u0006\u0010\u001b\u001a\u00020\u0016H\u0082\u0002R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\f\u001a\u0004\u0018\u00010\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\b0\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u001b\u0010\r\u001a\u00020\u000e8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0011\u0010\u0012\u001a\u0004\b\u000f\u0010\u0010¨\u0006#"}, d2 = {"Lorg/jetbrains/kotlin/cli/jvm/modules/CliJavaModuleResolver;", "Lorg/jetbrains/kotlin/resolve/jvm/modules/JavaModuleResolver;", "moduleGraph", "Lorg/jetbrains/kotlin/cli/jvm/modules/JavaModuleGraph;", "userModules", "", "Lorg/jetbrains/kotlin/resolve/jvm/modules/JavaModule;", "systemModules", "Lorg/jetbrains/kotlin/resolve/jvm/modules/JavaModule$Explicit;", "project", "Lorg/jetbrains/kotlin/com/intellij/openapi/project/Project;", "(Lorg/jetbrains/kotlin/cli/jvm/modules/JavaModuleGraph;Ljava/util/List;Ljava/util/List;Lcom/intellij/openapi/project/Project;)V", "sourceModule", "virtualFileFinder", "Lorg/jetbrains/kotlin/load/kotlin/VirtualFileFinder;", "getVirtualFileFinder", "()Lorg/jetbrains/kotlin/load/kotlin/VirtualFileFinder;", "virtualFileFinder$delegate", "Lkotlin/Lazy;", "checkAccessibility", "Lorg/jetbrains/kotlin/resolve/jvm/modules/JavaModuleResolver$AccessError;", "fileFromOurModule", "Lorg/jetbrains/kotlin/com/intellij/openapi/vfs/VirtualFile;", "referencedFile", "referencedPackage", "Lorg/jetbrains/kotlin/name/FqName;", "findJavaModule", "file", "getAnnotationsForModuleOwnerOfClass", "Lorg/jetbrains/kotlin/load/java/structure/JavaAnnotation;", "classId", "Lorg/jetbrains/kotlin/name/ClassId;", "contains", "", "Companion", "cli-base"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CliJavaModuleResolver implements JavaModuleResolver {
    private final JavaModuleGraph moduleGraph;
    private final Project project;
    private final JavaModule sourceModule;
    private final List<JavaModule.Explicit> systemModules;
    private final List<JavaModule> userModules;

    /* JADX INFO: renamed from: virtualFileFinder$delegate, reason: from kotlin metadata */
    private final Lazy virtualFileFinder;

    /* JADX WARN: Multi-variable type inference failed */
    public CliJavaModuleResolver(JavaModuleGraph moduleGraph, List<? extends JavaModule> userModules, List<JavaModule.Explicit> systemModules, Project project) {
        Intrinsics.checkNotNullParameter(moduleGraph, "moduleGraph");
        Intrinsics.checkNotNullParameter(userModules, "userModules");
        Intrinsics.checkNotNullParameter(systemModules, "systemModules");
        Intrinsics.checkNotNullParameter(project, "project");
        this.moduleGraph = moduleGraph;
        this.userModules = userModules;
        this.systemModules = systemModules;
        this.project = project;
        List<? extends JavaModule> list = userModules;
        if (!(list instanceof Collection) || !list.isEmpty()) {
            Iterator it = list.iterator();
            int i = 0;
            while (it.hasNext()) {
                if (((JavaModule) it.next()).isSourceModule() && (i = i + 1) < 0) {
                    CollectionsKt.throwCountOverflow();
                }
            }
        }
        this.virtualFileFinder = LazyKt.lazy(new Function0<VirtualFileFinder>() { // from class: org.jetbrains.kotlin.cli.jvm.modules.CliJavaModuleResolver$virtualFileFinder$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final VirtualFileFinder invoke() {
                return VirtualFileFinder.INSTANCE.getInstance(this.this$0.project);
            }
        });
        for (Object obj : this.userModules) {
            if (((JavaModule) obj).isSourceModule()) {
                this.sourceModule = (JavaModule) obj;
            }
        }
        obj = null;
        this.sourceModule = (JavaModule) obj;
    }

    private final VirtualFileFinder getVirtualFileFinder() {
        return (VirtualFileFinder) this.virtualFileFinder.getValue();
    }

    @Override // org.jetbrains.kotlin.load.java.JavaModuleAnnotationsProvider
    public List<JavaAnnotation> getAnnotationsForModuleOwnerOfClass(ClassId classId) {
        JavaModuleInfo moduleInfo;
        Intrinsics.checkNotNullParameter(classId, "classId");
        VirtualFile virtualFileFindSourceOrBinaryVirtualFile = getVirtualFileFinder().findSourceOrBinaryVirtualFile(classId);
        if (virtualFileFindSourceOrBinaryVirtualFile == null) {
            return null;
        }
        JavaModule javaModuleFindJavaModule = findJavaModule(virtualFileFindSourceOrBinaryVirtualFile);
        JavaModule.Explicit explicit = javaModuleFindJavaModule instanceof JavaModule.Explicit ? (JavaModule.Explicit) javaModuleFindJavaModule : null;
        if (explicit == null || (moduleInfo = explicit.getModuleInfo()) == null) {
            return null;
        }
        return moduleInfo.getAnnotations();
    }

    private final JavaModule findJavaModule(VirtualFile file) {
        Object obj = null;
        if (!Intrinsics.areEqual(file.getFileSystem().getProtocol(), StandardFileSystems.JRT_PROTOCOL) && !Intrinsics.areEqual(file.getExtension(), Options.TYPE_SIG)) {
            FileType fileType = file.getFileType();
            if (Intrinsics.areEqual(fileType, KotlinFileType.INSTANCE) ? true : Intrinsics.areEqual(fileType, JavaFileType.INSTANCE)) {
                return this.sourceModule;
            }
            if (!Intrinsics.areEqual(fileType, JavaClassFileType.INSTANCE)) {
                return null;
            }
            for (Object obj2 : this.userModules) {
                if (contains((JavaModule) obj2, file)) {
                    obj = obj2;
                    break;
                }
            }
            return (JavaModule) obj;
        }
        for (Object obj3 : this.systemModules) {
            if (contains((JavaModule.Explicit) obj3, file)) {
                obj = obj3;
                break;
            }
        }
        return (JavaModule) obj;
    }

    private final boolean contains(JavaModule javaModule, VirtualFile virtualFile) {
        Intrinsics.checkNotNullParameter(javaModule, "<this>");
        List<JavaModule.Root> moduleRoots = javaModule.getModuleRoots();
        if ((moduleRoots instanceof Collection) && moduleRoots.isEmpty()) {
            return false;
        }
        for (JavaModule.Root root : moduleRoots) {
            VirtualFile file = root.getFile();
            if (root.getIsBinary() && VfsUtilCore.isAncestor(file, virtualFile, false)) {
                return true;
            }
        }
        return false;
    }

    @Override // org.jetbrains.kotlin.resolve.jvm.modules.JavaModuleResolver
    public JavaModuleResolver.AccessError checkAccessibility(VirtualFile fileFromOurModule, VirtualFile referencedFile, FqName referencedPackage) {
        Intrinsics.checkNotNullParameter(referencedFile, "referencedFile");
        JavaModule javaModuleFindJavaModule = fileFromOurModule != null ? findJavaModule(fileFromOurModule) : null;
        JavaModule javaModuleFindJavaModule2 = findJavaModule(referencedFile);
        if (Intrinsics.areEqual(javaModuleFindJavaModule != null ? javaModuleFindJavaModule.getName() : null, javaModuleFindJavaModule2 != null ? javaModuleFindJavaModule2.getName() : null)) {
            return null;
        }
        if (javaModuleFindJavaModule2 == null) {
            return JavaModuleResolver.AccessError.ModuleDoesNotReadUnnamedModule.INSTANCE;
        }
        if (javaModuleFindJavaModule != null && !this.moduleGraph.reads(javaModuleFindJavaModule.getName(), javaModuleFindJavaModule2.getName())) {
            return new JavaModuleResolver.AccessError.ModuleDoesNotReadModule(javaModuleFindJavaModule2.getName());
        }
        if (referencedPackage == null || javaModuleFindJavaModule2.exports(referencedPackage) || (javaModuleFindJavaModule != null && javaModuleFindJavaModule2.exportsTo(referencedPackage, javaModuleFindJavaModule.getName()))) {
            return null;
        }
        return new JavaModuleResolver.AccessError.ModuleDoesNotExportPackage(javaModuleFindJavaModule2.getName());
    }
}

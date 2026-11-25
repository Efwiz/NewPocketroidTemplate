package org.jetbrains.kotlin.load.kotlin;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.load.java.structure.JavaClass;
import org.jetbrains.kotlin.load.java.structure.impl.VirtualFileBoundJavaClass;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmMetadataVersion;
import org.jetbrains.kotlin.name.ClassId;

/* JADX INFO: compiled from: VirtualFileFinder.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b&\u0018\u0000 \u00112\u00020\u0001:\u0001\u0011B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0002J\u001a\u0010\u0007\u001a\u0004\u0018\u00010\b2\u0006\u0010\t\u001a\u00020\u00062\u0006\u0010\n\u001a\u00020\u000bH\u0016J\u001a\u0010\u0007\u001a\u0004\u0018\u00010\b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\n\u001a\u00020\u000bH\u0016J\u0012\u0010\u000e\u001a\u0004\u0018\u00010\u000f2\u0006\u0010\f\u001a\u00020\rH&J\u0012\u0010\u0010\u001a\u0004\u0018\u00010\u000f2\u0006\u0010\f\u001a\u00020\rH&¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/load/kotlin/VirtualFileFinder;", "Lorg/jetbrains/kotlin/load/kotlin/KotlinClassFinder;", "()V", "classFileName", "", "jClass", "Lorg/jetbrains/kotlin/load/java/structure/JavaClass;", "findKotlinClassOrContent", "Lorg/jetbrains/kotlin/load/kotlin/KotlinClassFinder$Result;", "javaClass", "jvmMetadataVersion", "Lorg/jetbrains/kotlin/metadata/jvm/deserialization/JvmMetadataVersion;", "classId", "Lorg/jetbrains/kotlin/name/ClassId;", "findSourceOrBinaryVirtualFile", "Lorg/jetbrains/kotlin/com/intellij/openapi/vfs/VirtualFile;", "findVirtualFileWithHeader", "SERVICE", "frontend.java"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class VirtualFileFinder implements KotlinClassFinder {

    /* JADX INFO: renamed from: SERVICE, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);

    public abstract VirtualFile findSourceOrBinaryVirtualFile(ClassId classId);

    public abstract VirtualFile findVirtualFileWithHeader(ClassId classId);

    @Override // org.jetbrains.kotlin.load.kotlin.KotlinClassFinder
    public KotlinClassFinder.Result findKotlinClassOrContent(ClassId classId, JvmMetadataVersion jvmMetadataVersion) {
        Intrinsics.checkNotNullParameter(classId, "classId");
        Intrinsics.checkNotNullParameter(jvmMetadataVersion, "jvmMetadataVersion");
        VirtualFile virtualFileFindVirtualFileWithHeader = findVirtualFileWithHeader(classId);
        if (virtualFileFindVirtualFileWithHeader == null) {
            return null;
        }
        return KotlinBinaryClassCache.Companion.getKotlinBinaryClassOrClassFileContent$default(KotlinBinaryClassCache.INSTANCE, virtualFileFindVirtualFileWithHeader, jvmMetadataVersion, null, 4, null);
    }

    @Override // org.jetbrains.kotlin.load.kotlin.KotlinClassFinder
    public KotlinClassFinder.Result findKotlinClassOrContent(JavaClass javaClass, JvmMetadataVersion jvmMetadataVersion) {
        VirtualFile virtualFile;
        Intrinsics.checkNotNullParameter(javaClass, "javaClass");
        Intrinsics.checkNotNullParameter(jvmMetadataVersion, "jvmMetadataVersion");
        VirtualFileBoundJavaClass virtualFileBoundJavaClass = javaClass instanceof VirtualFileBoundJavaClass ? (VirtualFileBoundJavaClass) javaClass : null;
        if (virtualFileBoundJavaClass == null || (virtualFile = virtualFileBoundJavaClass.getVirtualFile()) == null) {
            return null;
        }
        if (javaClass.getOuterClass() != null) {
            VirtualFile parent = virtualFile.getParent();
            Intrinsics.checkNotNull(parent);
            virtualFile = parent.findChild(classFileName(javaClass) + ".class");
            if (virtualFile == null) {
                throw new AssertionError("Virtual file not found for " + javaClass);
            }
        }
        return KotlinBinaryClassCache.Companion.getKotlinBinaryClassOrClassFileContent$default(KotlinBinaryClassCache.INSTANCE, virtualFile, jvmMetadataVersion, null, 4, null);
    }

    private final String classFileName(JavaClass jClass) {
        String strAsString = jClass.getName().asString();
        Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
        JavaClass outerClass = jClass.getOuterClass();
        return outerClass == null ? strAsString : classFileName(outerClass) + '$' + strAsString;
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.load.kotlin.VirtualFileFinder$SERVICE, reason: from kotlin metadata */
    /* JADX INFO: compiled from: VirtualFileFinder.kt */
    @Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006J\u0016\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/load/kotlin/VirtualFileFinder$SERVICE;", "", "()V", "getInstance", "Lorg/jetbrains/kotlin/load/kotlin/VirtualFileFinder;", "project", "Lorg/jetbrains/kotlin/com/intellij/openapi/project/Project;", "module", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "frontend.java"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final VirtualFileFinder getInstance(Project project, ModuleDescriptor module) {
            Intrinsics.checkNotNullParameter(project, "project");
            Intrinsics.checkNotNullParameter(module, "module");
            return VirtualFileFinderFactory.INSTANCE.getInstance(project).create(project, module);
        }

        public final VirtualFileFinder getInstance(Project project) {
            Intrinsics.checkNotNullParameter(project, "project");
            VirtualFileFinderFactory companion = VirtualFileFinderFactory.INSTANCE.getInstance(project);
            GlobalSearchScope globalSearchScopeAllScope = GlobalSearchScope.allScope(project);
            Intrinsics.checkNotNullExpressionValue(globalSearchScopeAllScope, "allScope(...)");
            return companion.create(globalSearchScopeAllScope);
        }
    }
}

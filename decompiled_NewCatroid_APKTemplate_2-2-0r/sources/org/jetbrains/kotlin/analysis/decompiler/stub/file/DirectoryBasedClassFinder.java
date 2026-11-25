package org.jetbrains.kotlin.analysis.decompiler.stub.file;

import java.io.InputStream;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.load.java.structure.JavaClass;
import org.jetbrains.kotlin.load.java.structure.JavaElementsKt;
import org.jetbrains.kotlin.load.kotlin.KotlinClassFinder;
import org.jetbrains.kotlin.load.kotlin.KotlinJvmBinaryClass;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmMetadataVersion;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: DirectoryBasedClassFinder.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0012\u0010\u000b\u001a\u0004\u0018\u00010\f2\u0006\u0010\r\u001a\u00020\u0005H\u0016J\u001a\u0010\u000e\u001a\u0004\u0018\u00010\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0013H\u0016J\u001a\u0010\u000e\u001a\u0004\u0018\u00010\u000f2\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0012\u001a\u00020\u0013H\u0016J\u0012\u0010\u0016\u001a\u0004\u0018\u00010\f2\u0006\u0010\u0014\u001a\u00020\u0015H\u0016J\u0018\u0010\u0017\u001a\n\u0012\u0004\u0012\u00020\u0019\u0018\u00010\u00182\u0006\u0010\r\u001a\u00020\u0005H\u0016J\u0010\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u0005H\u0016R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u001d"}, d2 = {"Lorg/jetbrains/kotlin/analysis/decompiler/stub/file/DirectoryBasedClassFinder;", "Lorg/jetbrains/kotlin/load/kotlin/KotlinClassFinder;", "packageDirectory", "Lorg/jetbrains/kotlin/com/intellij/openapi/vfs/VirtualFile;", "directoryPackageFqName", "Lorg/jetbrains/kotlin/name/FqName;", "(Lcom/intellij/openapi/vfs/VirtualFile;Lorg/jetbrains/kotlin/name/FqName;)V", "getDirectoryPackageFqName", "()Lorg/jetbrains/kotlin/name/FqName;", "getPackageDirectory", "()Lcom/intellij/openapi/vfs/VirtualFile;", "findBuiltInsData", "Ljava/io/InputStream;", "packageFqName", "findKotlinClassOrContent", "Lorg/jetbrains/kotlin/load/kotlin/KotlinClassFinder$Result;", "javaClass", "Lorg/jetbrains/kotlin/load/java/structure/JavaClass;", "jvmMetadataVersion", "Lorg/jetbrains/kotlin/metadata/jvm/deserialization/JvmMetadataVersion;", "classId", "Lorg/jetbrains/kotlin/name/ClassId;", "findMetadata", "findMetadataTopLevelClassesInPackage", "", "", "hasMetadataPackage", "", "fqName", "decompiler-to-file-stubs"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class DirectoryBasedClassFinder implements KotlinClassFinder {
    private final FqName directoryPackageFqName;
    private final VirtualFile packageDirectory;

    @Override // org.jetbrains.kotlin.serialization.deserialization.KotlinMetadataFinder
    public InputStream findBuiltInsData(FqName packageFqName) {
        Intrinsics.checkNotNullParameter(packageFqName, "packageFqName");
        return null;
    }

    @Override // org.jetbrains.kotlin.serialization.deserialization.KotlinMetadataFinder
    public InputStream findMetadata(ClassId classId) {
        Intrinsics.checkNotNullParameter(classId, "classId");
        return null;
    }

    @Override // org.jetbrains.kotlin.serialization.deserialization.KotlinMetadataFinder
    public Set<String> findMetadataTopLevelClassesInPackage(FqName packageFqName) {
        Intrinsics.checkNotNullParameter(packageFqName, "packageFqName");
        return null;
    }

    @Override // org.jetbrains.kotlin.serialization.deserialization.KotlinMetadataFinder
    public boolean hasMetadataPackage(FqName fqName) {
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        return false;
    }

    public DirectoryBasedClassFinder(VirtualFile packageDirectory, FqName directoryPackageFqName) {
        Intrinsics.checkNotNullParameter(packageDirectory, "packageDirectory");
        Intrinsics.checkNotNullParameter(directoryPackageFqName, "directoryPackageFqName");
        this.packageDirectory = packageDirectory;
        this.directoryPackageFqName = directoryPackageFqName;
    }

    public final VirtualFile getPackageDirectory() {
        return this.packageDirectory;
    }

    public final FqName getDirectoryPackageFqName() {
        return this.directoryPackageFqName;
    }

    @Override // org.jetbrains.kotlin.load.kotlin.KotlinClassFinder
    public KotlinClassFinder.Result findKotlinClassOrContent(JavaClass javaClass, JvmMetadataVersion jvmMetadataVersion) {
        Intrinsics.checkNotNullParameter(javaClass, "javaClass");
        Intrinsics.checkNotNullParameter(jvmMetadataVersion, "jvmMetadataVersion");
        ClassId classId = JavaElementsKt.getClassId(javaClass);
        Intrinsics.checkNotNull(classId);
        return findKotlinClassOrContent(classId, jvmMetadataVersion);
    }

    @Override // org.jetbrains.kotlin.load.kotlin.KotlinClassFinder
    public KotlinClassFinder.Result findKotlinClassOrContent(ClassId classId, JvmMetadataVersion jvmMetadataVersion) {
        Intrinsics.checkNotNullParameter(classId, "classId");
        Intrinsics.checkNotNullParameter(jvmMetadataVersion, "jvmMetadataVersion");
        if (!Intrinsics.areEqual(classId.getPackageFqName(), this.directoryPackageFqName)) {
            return null;
        }
        List<Name> listPathSegments = classId.getRelativeClassName().pathSegments();
        Intrinsics.checkNotNullExpressionValue(listPathSegments, "pathSegments(...)");
        VirtualFile virtualFileFindChild = this.packageDirectory.findChild(CollectionsKt.joinToString$default(listPathSegments, "$", null, ".class", 0, null, null, 58, null));
        if (virtualFileFindChild == null || !DirectoryBasedClassFinderKt.isKotlinWithCompatibleAbiVersion(virtualFileFindChild, jvmMetadataVersion)) {
            return null;
        }
        KotlinJvmBinaryClass kotlinBinaryClass$default = ClsKotlinBinaryClassCache.getKotlinBinaryClass$default(ClsKotlinBinaryClassCache.INSTANCE.getInstance(), virtualFileFindChild, null, 2, null);
        return kotlinBinaryClass$default != null ? new KotlinClassFinder.Result.KotlinClass(kotlinBinaryClass$default, null, 2, null) : null;
    }
}

package org.jetbrains.kotlin.descriptors.runtime.components;

import java.io.InputStream;
import java.util.Set;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.StandardNames;
import org.jetbrains.kotlin.load.java.structure.JavaClass;
import org.jetbrains.kotlin.load.kotlin.KotlinClassFinder;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmMetadataVersion;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.serialization.deserialization.builtins.BuiltInSerializerProtocol;
import org.jetbrains.kotlin.serialization.deserialization.builtins.BuiltInsResourceLoader;

/* JADX INFO: compiled from: ReflectKotlinClassFinder.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000R\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\"\n\u0000\n\u0002\u0010\u000b\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0012\u0010\u0007\u001a\u0004\u0018\u00010\b2\u0006\u0010\t\u001a\u00020\nH\u0016J\u0012\u0010\u000b\u001a\u0004\u0018\u00010\f2\u0006\u0010\r\u001a\u00020\u000eH\u0002J\u001a\u0010\u000f\u001a\u0004\u0018\u00010\f2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0013H\u0016J\u001a\u0010\u000f\u001a\u0004\u0018\u00010\f2\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0012\u001a\u00020\u0013H\u0016J\u0012\u0010\u0016\u001a\u0004\u0018\u00010\b2\u0006\u0010\u0014\u001a\u00020\u0015H\u0016J\u0018\u0010\u0017\u001a\n\u0012\u0004\u0012\u00020\u000e\u0018\u00010\u00182\u0006\u0010\t\u001a\u00020\nH\u0016J\u0010\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\r\u001a\u00020\nH\u0016R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001b"}, d2 = {"Lorg/jetbrains/kotlin/descriptors/runtime/components/ReflectKotlinClassFinder;", "Lorg/jetbrains/kotlin/load/kotlin/KotlinClassFinder;", "classLoader", "Ljava/lang/ClassLoader;", "(Ljava/lang/ClassLoader;)V", "builtInsResourceLoader", "Lorg/jetbrains/kotlin/serialization/deserialization/builtins/BuiltInsResourceLoader;", "findBuiltInsData", "Ljava/io/InputStream;", "packageFqName", "Lorg/jetbrains/kotlin/name/FqName;", "findKotlinClass", "Lorg/jetbrains/kotlin/load/kotlin/KotlinClassFinder$Result;", "fqName", "", "findKotlinClassOrContent", "javaClass", "Lorg/jetbrains/kotlin/load/java/structure/JavaClass;", "jvmMetadataVersion", "Lorg/jetbrains/kotlin/metadata/jvm/deserialization/JvmMetadataVersion;", "classId", "Lorg/jetbrains/kotlin/name/ClassId;", "findMetadata", "findMetadataTopLevelClassesInPackage", "", "hasMetadataPackage", "", "descriptors.runtime"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ReflectKotlinClassFinder implements KotlinClassFinder {
    private final BuiltInsResourceLoader builtInsResourceLoader;
    private final ClassLoader classLoader;

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

    public ReflectKotlinClassFinder(ClassLoader classLoader) {
        Intrinsics.checkNotNullParameter(classLoader, "classLoader");
        this.classLoader = classLoader;
        this.builtInsResourceLoader = new BuiltInsResourceLoader();
    }

    private final KotlinClassFinder.Result findKotlinClass(String fqName) {
        ReflectKotlinClass reflectKotlinClassCreate;
        Class<?> clsTryLoadClass = ReflectJavaClassFinderKt.tryLoadClass(this.classLoader, fqName);
        KotlinClassFinder.Result.KotlinClass kotlinClass = null;
        if (clsTryLoadClass != null && (reflectKotlinClassCreate = ReflectKotlinClass.INSTANCE.create(clsTryLoadClass)) != null) {
            kotlinClass = new KotlinClassFinder.Result.KotlinClass(reflectKotlinClassCreate, null, 2, null);
        }
        return kotlinClass;
    }

    @Override // org.jetbrains.kotlin.load.kotlin.KotlinClassFinder
    public KotlinClassFinder.Result findKotlinClassOrContent(ClassId classId, JvmMetadataVersion jvmMetadataVersion) {
        Intrinsics.checkNotNullParameter(classId, "classId");
        Intrinsics.checkNotNullParameter(jvmMetadataVersion, "jvmMetadataVersion");
        return findKotlinClass(ReflectKotlinClassFinderKt.toRuntimeFqName(classId));
    }

    @Override // org.jetbrains.kotlin.load.kotlin.KotlinClassFinder
    public KotlinClassFinder.Result findKotlinClassOrContent(JavaClass javaClass, JvmMetadataVersion jvmMetadataVersion) {
        String strAsString;
        Intrinsics.checkNotNullParameter(javaClass, "javaClass");
        Intrinsics.checkNotNullParameter(jvmMetadataVersion, "jvmMetadataVersion");
        FqName fqName = javaClass.getFqName();
        if (fqName == null || (strAsString = fqName.asString()) == null) {
            return null;
        }
        return findKotlinClass(strAsString);
    }

    @Override // org.jetbrains.kotlin.serialization.deserialization.KotlinMetadataFinder
    public InputStream findBuiltInsData(FqName packageFqName) {
        Intrinsics.checkNotNullParameter(packageFqName, "packageFqName");
        if (packageFqName.startsWith(StandardNames.BUILT_INS_PACKAGE_NAME)) {
            return this.builtInsResourceLoader.loadResource(BuiltInSerializerProtocol.INSTANCE.getBuiltInsFilePath(packageFqName));
        }
        return null;
    }
}

package org.jetbrains.kotlin.load.kotlin;

import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.serialization.deserialization.ClassData;

/* JADX INFO: compiled from: PackagePartProvider.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\"\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001:\u0001\u000fJ\u000e\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003H&J\u0016\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00040\u00062\u0006\u0010\u0007\u001a\u00020\u0004H&J\u000e\u0010\b\u001a\b\u0012\u0004\u0012\u00020\t0\u0006H&J\u0016\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u000b0\u00062\u0006\u0010\f\u001a\u00020\u0004H&J\b\u0010\r\u001a\u00020\u000eH&ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0010À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/load/kotlin/PackagePartProvider;", "", "computePackageSetWithNonClassDeclarations", "", "", "findPackageParts", "", "packageFqName", "getAllOptionalAnnotationClasses", "Lorg/jetbrains/kotlin/serialization/deserialization/ClassData;", "getAnnotationsOnBinaryModule", "Lorg/jetbrains/kotlin/name/ClassId;", "moduleName", "mayHaveOptionalAnnotationClasses", "", "Empty", "deserialization.common.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface PackagePartProvider {
    Set<String> computePackageSetWithNonClassDeclarations();

    List<String> findPackageParts(String packageFqName);

    List<ClassData> getAllOptionalAnnotationClasses();

    List<ClassId> getAnnotationsOnBinaryModule(String moduleName);

    boolean mayHaveOptionalAnnotationClasses();

    /* JADX INFO: compiled from: PackagePartProvider.kt */
    @Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004H\u0016J\u0016\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00050\u00072\u0006\u0010\b\u001a\u00020\u0005H\u0016J\u000e\u0010\t\u001a\b\u0012\u0004\u0012\u00020\n0\u0007H\u0016J\u0016\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\f0\u00072\u0006\u0010\r\u001a\u00020\u0005H\u0016J\b\u0010\u000e\u001a\u00020\u000fH\u0016¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/load/kotlin/PackagePartProvider$Empty;", "Lorg/jetbrains/kotlin/load/kotlin/PackagePartProvider;", "()V", "computePackageSetWithNonClassDeclarations", "", "", "findPackageParts", "", "packageFqName", "getAllOptionalAnnotationClasses", "Lorg/jetbrains/kotlin/serialization/deserialization/ClassData;", "getAnnotationsOnBinaryModule", "Lorg/jetbrains/kotlin/name/ClassId;", "moduleName", "mayHaveOptionalAnnotationClasses", "", "deserialization.common.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Empty implements PackagePartProvider {
        public static final Empty INSTANCE = new Empty();

        @Override // org.jetbrains.kotlin.load.kotlin.PackagePartProvider
        public boolean mayHaveOptionalAnnotationClasses() {
            return false;
        }

        private Empty() {
        }

        @Override // org.jetbrains.kotlin.load.kotlin.PackagePartProvider
        public List<String> findPackageParts(String packageFqName) {
            Intrinsics.checkNotNullParameter(packageFqName, "packageFqName");
            return CollectionsKt.emptyList();
        }

        @Override // org.jetbrains.kotlin.load.kotlin.PackagePartProvider
        public List<ClassId> getAnnotationsOnBinaryModule(String moduleName) {
            Intrinsics.checkNotNullParameter(moduleName, "moduleName");
            return CollectionsKt.emptyList();
        }

        @Override // org.jetbrains.kotlin.load.kotlin.PackagePartProvider
        public List<ClassData> getAllOptionalAnnotationClasses() {
            return CollectionsKt.emptyList();
        }

        @Override // org.jetbrains.kotlin.load.kotlin.PackagePartProvider
        public Set<String> computePackageSetWithNonClassDeclarations() {
            return SetsKt.emptySet();
        }
    }
}

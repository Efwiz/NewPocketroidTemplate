package org.jetbrains.kotlin.library;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.konan.file.File;

/* JADX INFO: compiled from: KotlinLibraryLayout.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000e\n\u0002\b\u0004\bf\u0018\u00002\u00020\u0001J\u0018\u0010\b\u001a\u00020\u00032\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\nH\u0016J\u0010\u0010\f\u001a\u00020\u00032\u0006\u0010\r\u001a\u00020\nH\u0016R\u0014\u0010\u0002\u001a\u00020\u00038VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005R\u0014\u0010\u0006\u001a\u00020\u00038VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\u0005¨\u0006\u000eÀ\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/library/MetadataKotlinLibraryLayout;", "Lorg/jetbrains/kotlin/library/KotlinLibraryLayout;", "metadataDir", "Lorg/jetbrains/kotlin/konan/file/File;", "getMetadataDir", "()Lorg/jetbrains/kotlin/konan/file/File;", "moduleHeaderFile", "getModuleHeaderFile", "packageFragmentFile", "packageFqName", "", "partName", "packageFragmentsDir", "packageName", "kotlin-util-klib"}, k = 1, mv = {1, 5, 1}, xi = 48)
public interface MetadataKotlinLibraryLayout extends KotlinLibraryLayout {

    /* JADX INFO: compiled from: KotlinLibraryLayout.kt */
    @Metadata(k = 3, mv = {1, 5, 1}, xi = 48)
    public static final class DefaultImpls {
        @Deprecated
        public static File getComponentDir(MetadataKotlinLibraryLayout metadataKotlinLibraryLayout) {
            return MetadataKotlinLibraryLayout.super.getComponentDir();
        }

        @Deprecated
        public static String getLibraryName(MetadataKotlinLibraryLayout metadataKotlinLibraryLayout) {
            return MetadataKotlinLibraryLayout.super.getLibraryName();
        }

        @Deprecated
        public static File getManifestFile(MetadataKotlinLibraryLayout metadataKotlinLibraryLayout) {
            return MetadataKotlinLibraryLayout.super.getManifestFile();
        }

        @Deprecated
        public static File getPre_1_4_manifest(MetadataKotlinLibraryLayout metadataKotlinLibraryLayout) {
            return MetadataKotlinLibraryLayout.super.getPre_1_4_manifest();
        }

        @Deprecated
        public static File getResourcesDir(MetadataKotlinLibraryLayout metadataKotlinLibraryLayout) {
            return MetadataKotlinLibraryLayout.super.getResourcesDir();
        }

        @Deprecated
        public static File getMetadataDir(MetadataKotlinLibraryLayout metadataKotlinLibraryLayout) {
            return MetadataKotlinLibraryLayout.super.getMetadataDir();
        }

        @Deprecated
        public static File getModuleHeaderFile(MetadataKotlinLibraryLayout metadataKotlinLibraryLayout) {
            return MetadataKotlinLibraryLayout.super.getModuleHeaderFile();
        }

        @Deprecated
        public static File packageFragmentsDir(MetadataKotlinLibraryLayout metadataKotlinLibraryLayout, String packageName) {
            Intrinsics.checkNotNullParameter(packageName, "packageName");
            return MetadataKotlinLibraryLayout.super.packageFragmentsDir(packageName);
        }

        @Deprecated
        public static File packageFragmentFile(MetadataKotlinLibraryLayout metadataKotlinLibraryLayout, String packageFqName, String partName) {
            Intrinsics.checkNotNullParameter(packageFqName, "packageFqName");
            Intrinsics.checkNotNullParameter(partName, "partName");
            return MetadataKotlinLibraryLayout.super.packageFragmentFile(packageFqName, partName);
        }
    }

    default File getMetadataDir() {
        return new File(getComponentDir(), "linkdata");
    }

    default File getModuleHeaderFile() {
        return new File(getMetadataDir(), "module");
    }

    default File packageFragmentsDir(String packageName) {
        Intrinsics.checkNotNullParameter(packageName, "packageName");
        return new File(getMetadataDir(), Intrinsics.areEqual(packageName, "") ? "root_package" : "package_" + packageName);
    }

    default File packageFragmentFile(String packageFqName, String partName) {
        Intrinsics.checkNotNullParameter(packageFqName, "packageFqName");
        Intrinsics.checkNotNullParameter(partName, "partName");
        return new File(packageFragmentsDir(packageFqName), partName + KotlinLibraryUtilsKt.KLIB_METADATA_FILE_EXTENSION_WITH_DOT);
    }
}

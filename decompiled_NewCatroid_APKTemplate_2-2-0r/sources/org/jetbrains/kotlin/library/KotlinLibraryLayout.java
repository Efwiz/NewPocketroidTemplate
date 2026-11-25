package org.jetbrains.kotlin.library;

import com.reandroid.apk.XmlHelper;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.konan.file.File;

/* JADX INFO: compiled from: KotlinLibraryLayout.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\r\bf\u0018\u00002\u00020\u0001R\u0014\u0010\u0002\u001a\u0004\u0018\u00010\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005R\u0014\u0010\u0006\u001a\u00020\u00078VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\tR\u0012\u0010\n\u001a\u00020\u0007X¦\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\tR\u0014\u0010\f\u001a\u00020\u00038VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\u0005R\u0014\u0010\u000e\u001a\u00020\u00078VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000f\u0010\tR\u0014\u0010\u0010\u001a\u00020\u00078VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0011\u0010\tR\u0014\u0010\u0012\u001a\u00020\u00078VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0013\u0010\t¨\u0006\u0014À\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/library/KotlinLibraryLayout;", "", "component", "", "getComponent", "()Ljava/lang/String;", "componentDir", "Lorg/jetbrains/kotlin/konan/file/File;", "getComponentDir", "()Lorg/jetbrains/kotlin/konan/file/File;", "libFile", "getLibFile", "libraryName", "getLibraryName", "manifestFile", "getManifestFile", "pre_1_4_manifest", "getPre_1_4_manifest", "resourcesDir", "getResourcesDir", "kotlin-util-klib"}, k = 1, mv = {1, 5, 1}, xi = 48)
public interface KotlinLibraryLayout {
    String getComponent();

    File getLibFile();

    /* JADX INFO: compiled from: KotlinLibraryLayout.kt */
    @Metadata(k = 3, mv = {1, 5, 1}, xi = 48)
    public static final class DefaultImpls {
        @Deprecated
        public static String getLibraryName(KotlinLibraryLayout kotlinLibraryLayout) {
            return KotlinLibraryLayout.super.getLibraryName();
        }

        @Deprecated
        public static File getComponentDir(KotlinLibraryLayout kotlinLibraryLayout) {
            return KotlinLibraryLayout.super.getComponentDir();
        }

        @Deprecated
        public static File getManifestFile(KotlinLibraryLayout kotlinLibraryLayout) {
            return KotlinLibraryLayout.super.getManifestFile();
        }

        @Deprecated
        public static File getResourcesDir(KotlinLibraryLayout kotlinLibraryLayout) {
            return KotlinLibraryLayout.super.getResourcesDir();
        }

        @Deprecated
        public static File getPre_1_4_manifest(KotlinLibraryLayout kotlinLibraryLayout) {
            return KotlinLibraryLayout.super.getPre_1_4_manifest();
        }
    }

    default String getLibraryName() {
        return getLibFile().getPath();
    }

    default File getComponentDir() {
        File libFile = getLibFile();
        String component = getComponent();
        Intrinsics.checkNotNull(component);
        return new File(libFile, component);
    }

    default File getManifestFile() {
        return new File(getComponentDir(), KotlinLibraryLayoutKt.KLIB_MANIFEST_FILE_NAME);
    }

    default File getResourcesDir() {
        return new File(getComponentDir(), XmlHelper.RESOURCES_TAG);
    }

    default File getPre_1_4_manifest() {
        return new File(getLibFile(), KotlinLibraryLayoutKt.KLIB_MANIFEST_FILE_NAME);
    }
}

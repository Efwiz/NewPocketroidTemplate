package org.jetbrains.kotlin.library.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.konan.file.File;
import org.jetbrains.kotlin.konan.file.ZipFileSystemAccessor;
import org.jetbrains.kotlin.library.KotlinLibrary;
import org.jetbrains.kotlin.library.SingleFileResolveKt;

/* JADX INFO: compiled from: KotlinLibraryImpl.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u00000\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0000\u001a6\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00022\u0006\u0010\u0007\u001a\u00020\b2\b\b\u0002\u0010\t\u001a\u00020\u00012\b\b\u0002\u0010\n\u001a\u00020\u00012\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\f\u001a*\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00050\u000e2\u0006\u0010\u0006\u001a\u00020\u00022\b\b\u0002\u0010\t\u001a\u00020\u00012\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\f\u001a\u000e\u0010\u000f\u001a\u00020\u00012\u0006\u0010\u0006\u001a\u00020\u0010\u001a\u000e\u0010\u000f\u001a\u00020\u00012\u0006\u0010\u0006\u001a\u00020\u0002\"\u0015\u0010\u0000\u001a\u00020\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0000\u0010\u0003¨\u0006\u0011"}, d2 = {"isPre_1_4_Library", "", "Lorg/jetbrains/kotlin/konan/file/File;", "(Lorg/jetbrains/kotlin/konan/file/File;)Z", "createKotlinLibrary", "Lorg/jetbrains/kotlin/library/KotlinLibrary;", "libraryFile", "component", "", "isDefault", "perFile", "zipAccessor", "Lorg/jetbrains/kotlin/konan/file/ZipFileSystemAccessor;", "createKotlinLibraryComponents", "", "isKotlinLibrary", "Ljava/io/File;", "kotlin-util-klib"}, k = 2, mv = {1, 5, 1}, xi = 48)
public final class KotlinLibraryImplKt {
    public static /* synthetic */ KotlinLibrary createKotlinLibrary$default(File file, String str, boolean z, boolean z2, ZipFileSystemAccessor zipFileSystemAccessor, int i, Object obj) {
        if ((i & 4) != 0) {
            z = false;
        }
        if ((i & 8) != 0) {
            z2 = false;
        }
        if ((i & 16) != 0) {
            zipFileSystemAccessor = null;
        }
        return createKotlinLibrary(file, str, z, z2, zipFileSystemAccessor);
    }

    public static final KotlinLibrary createKotlinLibrary(File libraryFile, String component, boolean z, boolean z2, ZipFileSystemAccessor zipFileSystemAccessor) {
        Intrinsics.checkNotNullParameter(libraryFile, "libraryFile");
        Intrinsics.checkNotNullParameter(component, "component");
        BaseLibraryAccess baseLibraryAccess = new BaseLibraryAccess(libraryFile, component, zipFileSystemAccessor);
        MetadataLibraryAccess metadataLibraryAccess = new MetadataLibraryAccess(libraryFile, component, zipFileSystemAccessor);
        IrLibraryAccess irLibraryAccess = new IrLibraryAccess(libraryFile, component, zipFileSystemAccessor);
        return new KotlinLibraryImpl(new BaseKotlinLibraryImpl(baseLibraryAccess, z), new MetadataLibraryImpl(metadataLibraryAccess), z2 ? new IrPerFileLibraryImpl(irLibraryAccess) : new IrMonoliticLibraryImpl(irLibraryAccess));
    }

    public static /* synthetic */ List createKotlinLibraryComponents$default(File file, boolean z, ZipFileSystemAccessor zipFileSystemAccessor, int i, Object obj) {
        if ((i & 2) != 0) {
            z = true;
        }
        if ((i & 4) != 0) {
            zipFileSystemAccessor = null;
        }
        return createKotlinLibraryComponents(file, z, zipFileSystemAccessor);
    }

    public static final List<KotlinLibrary> createKotlinLibraryComponents(File libraryFile, boolean z, ZipFileSystemAccessor zipFileSystemAccessor) {
        Intrinsics.checkNotNullParameter(libraryFile, "libraryFile");
        List<String> componentList = new BaseKotlinLibraryImpl(new BaseLibraryAccess(libraryFile, null, zipFileSystemAccessor), z).getComponentList();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(componentList, 10));
        Iterator<T> it = componentList.iterator();
        while (it.hasNext()) {
            arrayList.add(createKotlinLibrary$default(libraryFile, (String) it.next(), z, false, zipFileSystemAccessor, 8, null));
        }
        return arrayList;
    }

    public static final boolean isKotlinLibrary(File libraryFile) {
        Intrinsics.checkNotNullParameter(libraryFile, "libraryFile");
        try {
            SingleFileResolveKt.resolveSingleFileKlib$default(libraryFile, null, null, 6, null);
            return true;
        } catch (Throwable unused) {
            return false;
        }
    }

    public static final boolean isKotlinLibrary(java.io.File libraryFile) {
        Intrinsics.checkNotNullParameter(libraryFile, "libraryFile");
        String absolutePath = libraryFile.getAbsolutePath();
        Intrinsics.checkNotNullExpressionValue(absolutePath, "libraryFile.absolutePath");
        return isKotlinLibrary(new File(absolutePath));
    }

    public static final boolean isPre_1_4_Library(File file) {
        Intrinsics.checkNotNullParameter(file, "<this>");
        return new BaseKotlinLibraryImpl(new BaseLibraryAccess(file, null, null, 4, null), false).getHas_pre_1_4_manifest();
    }
}

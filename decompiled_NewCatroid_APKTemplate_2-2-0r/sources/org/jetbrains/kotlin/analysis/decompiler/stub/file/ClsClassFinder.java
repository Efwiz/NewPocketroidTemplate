package org.jetbrains.kotlin.analysis.decompiler.stub.file;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.com.intellij.psi.ClassFileViewProvider;
import org.jetbrains.kotlin.load.kotlin.KotlinClassFinderKt;
import org.jetbrains.kotlin.load.kotlin.KotlinJvmBinaryClass;
import org.jetbrains.kotlin.load.kotlin.header.KotlinClassHeader;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmMetadataVersion;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: ClsClassFinder.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0012\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J*\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u000b0\u0004J\u001a\u0010\f\u001a\u00020\r2\u0006\u0010\u0006\u001a\u00020\u00072\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u000fJ\u001a\u0010\u0010\u001a\u00020\r2\u0006\u0010\u0006\u001a\u00020\u00072\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u000fJ\u0018\u0010\u0011\u001a\u00020\r*\u00020\u00072\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u000fH\u0002¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/analysis/decompiler/stub/file/ClsClassFinder;", "", "()V", "findMultifileClassParts", "", "Lorg/jetbrains/kotlin/load/kotlin/KotlinJvmBinaryClass;", "file", "Lorg/jetbrains/kotlin/com/intellij/openapi/vfs/VirtualFile;", "classId", "Lorg/jetbrains/kotlin/name/ClassId;", "partNames", "", "isKotlinInternalCompiledFile", "", "fileContent", "", "isMultifileClassPartFile", "isValidAndExists", "decompiler-to-file-stubs"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ClsClassFinder {
    public static final ClsClassFinder INSTANCE = new ClsClassFinder();

    private ClsClassFinder() {
    }

    public final List<KotlinJvmBinaryClass> findMultifileClassParts(VirtualFile file, ClassId classId, List<String> partNames) {
        Intrinsics.checkNotNullParameter(file, "file");
        Intrinsics.checkNotNullParameter(classId, "classId");
        Intrinsics.checkNotNullParameter(partNames, "partNames");
        FqName packageFqName = classId.getPackageFqName();
        Intrinsics.checkNotNullExpressionValue(packageFqName, "getPackageFqName(...)");
        VirtualFile parent = file.getParent();
        Intrinsics.checkNotNull(parent);
        DirectoryBasedClassFinder directoryBasedClassFinder = new DirectoryBasedClassFinder(parent, packageFqName);
        ArrayList arrayList = new ArrayList();
        Iterator<T> it = partNames.iterator();
        while (it.hasNext()) {
            KotlinJvmBinaryClass kotlinJvmBinaryClassFindKotlinClass = KotlinClassFinderKt.findKotlinClass(directoryBasedClassFinder, new ClassId(packageFqName, Name.identifier(StringsKt.substringAfterLast$default((String) it.next(), '/', (String) null, 2, (Object) null))), JvmMetadataVersion.INSTANCE);
            if (kotlinJvmBinaryClassFindKotlinClass != null) {
                arrayList.add(kotlinJvmBinaryClassFindKotlinClass);
            }
        }
        return arrayList;
    }

    public static /* synthetic */ boolean isKotlinInternalCompiledFile$default(ClsClassFinder clsClassFinder, VirtualFile virtualFile, byte[] bArr, int i, Object obj) {
        if ((i & 2) != 0) {
            bArr = null;
        }
        return clsClassFinder.isKotlinInternalCompiledFile(virtualFile, bArr);
    }

    public final boolean isKotlinInternalCompiledFile(VirtualFile file, byte[] fileContent) throws Exception {
        boolean zIsInnerClass;
        Intrinsics.checkNotNullParameter(file, "file");
        if (!isValidAndExists(file, fileContent)) {
            return false;
        }
        ClsKotlinBinaryClassCache companion = ClsKotlinBinaryClassCache.INSTANCE.getInstance();
        if (!companion.isKotlinJvmCompiledFile(file, fileContent)) {
            return false;
        }
        try {
            if (fileContent == null) {
                zIsInnerClass = ClassFileViewProvider.isInnerClass(file);
            } else {
                zIsInnerClass = ClassFileViewProvider.isInnerClass(file, fileContent);
            }
            if (zIsInnerClass) {
                return true;
            }
            ClsKotlinBinaryClassCache.KotlinBinaryClassHeaderData kotlinBinaryClassHeaderData = companion.getKotlinBinaryClassHeaderData(file, fileContent);
            if (kotlinBinaryClassHeaderData == null) {
                return false;
            }
            if (kotlinBinaryClassHeaderData.getClassId().isLocal()) {
                return true;
            }
            return kotlinBinaryClassHeaderData.getKind() == KotlinClassHeader.Kind.SYNTHETIC_CLASS || kotlinBinaryClassHeaderData.getKind() == KotlinClassHeader.Kind.MULTIFILE_CLASS_PART;
        } catch (Exception e) {
            Logger.getInstance("org.jetbrains.kotlin.analysis.decompiler.stub.file.ClsClassFinder.isKotlinInternalCompiledFile").debug(file.getPath(), e);
            return false;
        }
    }

    public static /* synthetic */ boolean isMultifileClassPartFile$default(ClsClassFinder clsClassFinder, VirtualFile virtualFile, byte[] bArr, int i, Object obj) {
        if ((i & 2) != 0) {
            bArr = null;
        }
        return clsClassFinder.isMultifileClassPartFile(virtualFile, bArr);
    }

    public final boolean isMultifileClassPartFile(VirtualFile file, byte[] fileContent) throws Exception {
        Intrinsics.checkNotNullParameter(file, "file");
        if (!isValidAndExists(file, fileContent)) {
            return false;
        }
        ClsKotlinBinaryClassCache.KotlinBinaryClassHeaderData kotlinBinaryClassHeaderData = ClsKotlinBinaryClassCache.INSTANCE.getInstance().getKotlinBinaryClassHeaderData(file, fileContent);
        return (kotlinBinaryClassHeaderData != null ? kotlinBinaryClassHeaderData.getKind() : null) == KotlinClassHeader.Kind.MULTIFILE_CLASS_PART;
    }

    private final boolean isValidAndExists(VirtualFile virtualFile, byte[] bArr) {
        return virtualFile.isValid() && (bArr == null || bArr.length != 0) && virtualFile.exists();
    }
}

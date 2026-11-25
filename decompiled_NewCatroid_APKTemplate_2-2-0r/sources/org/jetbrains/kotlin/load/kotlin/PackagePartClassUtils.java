package org.jetbrains.kotlin.load.kotlin;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.kotlin.com.intellij.openapi.util.io.FileUtil;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiTreeChangeEvent;
import org.jetbrains.kotlin.idea.KotlinFileType;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.name.NameUtils;
import org.jetbrains.kotlin.psi.KtFile;

/* JADX INFO: compiled from: PackagePartClassUtils.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0006\u001a\u00020\u0004H\u0003J\u0018\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\b2\u0006\u0010\n\u001a\u00020\u000bH\u0007J\u0012\u0010\f\u001a\u0004\u0018\u00010\u00042\u0006\u0010\r\u001a\u00020\u0004H\u0007J\u0010\u0010\u000e\u001a\u00020\u00042\u0006\u0010\u000f\u001a\u00020\u0004H\u0007J\u001c\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00120\u00112\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00120\u0014H\u0007J\u0018\u0010\u0015\u001a\u00020\b2\u0006\u0010\u0016\u001a\u00020\b2\u0006\u0010\u000f\u001a\u00020\u0004H\u0007J\u0010\u0010\u0017\u001a\u00020\u00182\u0006\u0010\n\u001a\u00020\u000bH\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000¨\u0006\u0019"}, d2 = {"Lorg/jetbrains/kotlin/load/kotlin/PackagePartClassUtils;", "", "()V", "PART_CLASS_NAME_SUFFIX", "", "decapitalizeAsJavaClassName", "str", "getDefaultPartFqName", "Lorg/jetbrains/kotlin/name/FqName;", "facadeClassFqName", "file", "Lorg/jetbrains/kotlin/com/intellij/openapi/vfs/VirtualFile;", "getFileNameByFacadeName", "facadeClassName", "getFilePartShortName", PsiTreeChangeEvent.PROP_FILE_NAME, "getFilesWithCallables", "", "Lorg/jetbrains/kotlin/psi/KtFile;", "files", "", "getPackagePartFqName", "packageFqName", "getPathHashCode", "", "frontend.common.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class PackagePartClassUtils {
    public static final PackagePartClassUtils INSTANCE = new PackagePartClassUtils();

    private PackagePartClassUtils() {
    }

    @JvmStatic
    public static final int getPathHashCode(VirtualFile file) {
        Intrinsics.checkNotNullParameter(file, "file");
        String path = file.getPath();
        Intrinsics.checkNotNullExpressionValue(path, "getPath(...)");
        String lowerCase = path.toLowerCase(Locale.ROOT);
        Intrinsics.checkNotNullExpressionValue(lowerCase, "toLowerCase(...)");
        return lowerCase.hashCode();
    }

    @JvmStatic
    private static final String decapitalizeAsJavaClassName(String str) {
        if (!Character.isJavaIdentifierStart(str.charAt(0))) {
            if (str.charAt(0) != '_') {
                return str;
            }
            String strSubstring = str.substring(1);
            Intrinsics.checkNotNullExpressionValue(strSubstring, "substring(...)");
            return strSubstring;
        }
        StringBuilder sb = new StringBuilder();
        String strSubstring2 = str.substring(0, 1);
        Intrinsics.checkNotNullExpressionValue(strSubstring2, "substring(...)");
        String lowerCase = strSubstring2.toLowerCase(Locale.ROOT);
        Intrinsics.checkNotNullExpressionValue(lowerCase, "toLowerCase(...)");
        StringBuilder sbAppend = sb.append(lowerCase);
        String strSubstring3 = str.substring(1);
        Intrinsics.checkNotNullExpressionValue(strSubstring3, "substring(...)");
        return sbAppend.append(strSubstring3).toString();
    }

    @JvmStatic
    public static final FqName getDefaultPartFqName(FqName facadeClassFqName, VirtualFile file) {
        Intrinsics.checkNotNullParameter(facadeClassFqName, "facadeClassFqName");
        Intrinsics.checkNotNullParameter(file, "file");
        FqName fqNameParent = facadeClassFqName.parent();
        Intrinsics.checkNotNullExpressionValue(fqNameParent, "parent(...)");
        String name2 = file.getName();
        Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
        return getPackagePartFqName(fqNameParent, name2);
    }

    @JvmStatic
    public static final FqName getPackagePartFqName(FqName packageFqName, String fileName) {
        Intrinsics.checkNotNullParameter(packageFqName, "packageFqName");
        Intrinsics.checkNotNullParameter(fileName, "fileName");
        FqName fqNameChild = packageFqName.child(Name.identifier(getFilePartShortName(fileName)));
        Intrinsics.checkNotNullExpressionValue(fqNameChild, "child(...)");
        return fqNameChild;
    }

    @JvmStatic
    public static final List<KtFile> getFilesWithCallables(Collection<? extends KtFile> files) {
        Intrinsics.checkNotNullParameter(files, "files");
        ArrayList arrayList = new ArrayList();
        for (Object obj : files) {
            if (((KtFile) obj).hasTopLevelCallables()) {
                arrayList.add(obj);
            }
        }
        return arrayList;
    }

    @JvmStatic
    public static final String getFilePartShortName(String fileName) {
        Intrinsics.checkNotNullParameter(fileName, "fileName");
        StringBuilder sb = new StringBuilder();
        String nameWithoutExtension = FileUtil.getNameWithoutExtension(fileName);
        Intrinsics.checkNotNullExpressionValue(nameWithoutExtension, "getNameWithoutExtension(...)");
        return sb.append(NameUtils.getPackagePartClassNamePrefix(nameWithoutExtension)).append("Kt").toString();
    }

    @JvmStatic
    public static final String getFileNameByFacadeName(String facadeClassName) {
        Intrinsics.checkNotNullParameter(facadeClassName, "facadeClassName");
        if (!StringsKt.endsWith$default(facadeClassName, "Kt", false, 2, (Object) null)) {
            return null;
        }
        String strSubstring = facadeClassName.substring(0, facadeClassName.length() - 2);
        Intrinsics.checkNotNullExpressionValue(strSubstring, "substring(...)");
        if (Intrinsics.areEqual(strSubstring, "_")) {
            return null;
        }
        return decapitalizeAsJavaClassName(strSubstring) + KotlinFileType.DOT_DEFAULT_EXTENSION;
    }
}

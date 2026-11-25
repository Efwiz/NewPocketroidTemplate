package org.jetbrains.kotlin.utils.fileUtils;

import com.reandroid.apk.ApkUtil;
import java.io.File;
import kotlin.Metadata;
import kotlin.io.FilesKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;

/* JADX INFO: compiled from: fileUtils.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0002\u001a\u0012\u0010\u0000\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0001\u001a\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0001*\u00020\u00012\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0005¨\u0006\u0007"}, d2 = {"descendantRelativeTo", "Ljava/io/File;", ApkUtil.DEF_MODULE_NAME, "withReplacedExtensionOrNull", "oldExt", "", "newExt", "util"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class FileUtilsKt {
    public static final File withReplacedExtensionOrNull(File file, String oldExt, String newExt) {
        Intrinsics.checkNotNullParameter(file, "<this>");
        Intrinsics.checkNotNullParameter(oldExt, "oldExt");
        Intrinsics.checkNotNullParameter(newExt, "newExt");
        String name2 = file.getName();
        Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
        if (!StringsKt.endsWith$default(name2, oldExt, false, 2, (Object) null)) {
            return null;
        }
        String path = file.getPath();
        Intrinsics.checkNotNull(path);
        String strSubstring = path.substring(0, path.length() - oldExt.length());
        Intrinsics.checkNotNullExpressionValue(strSubstring, "substring(...)");
        return new File(strSubstring + newExt);
    }

    public static final File descendantRelativeTo(File file, File base) {
        Intrinsics.checkNotNullParameter(file, "<this>");
        Intrinsics.checkNotNullParameter(base, "base");
        base.isAbsolute();
        base.isDirectory();
        File fileNormalize = FilesKt.normalize(base);
        File absoluteFile = file.getAbsoluteFile();
        Intrinsics.checkNotNullExpressionValue(absoluteFile, "getAbsoluteFile(...)");
        File fileNormalize2 = FilesKt.normalize(absoluteFile);
        return FilesKt.startsWith(fileNormalize2, fileNormalize) ? FilesKt.relativeTo(fileNormalize2, fileNormalize) : file;
    }
}

package org.jetbrains.kotlin.com.intellij.openapi.fileTypes;

import com.google.firebase.analytics.FirebaseAnalytics;
import javax.swing.Icon;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;

/* JADX INFO: loaded from: classes3.dex */
public interface FileType {
    public static final FileType[] EMPTY_ARRAY = new FileType[0];

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i != 1) {
            objArr[0] = "file";
        } else {
            objArr[0] = FirebaseAnalytics.Param.CONTENT;
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/openapi/fileTypes/FileType";
        objArr[2] = "getCharset";
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    default String getCharset(VirtualFile virtualFile, byte[] bArr) {
        if (virtualFile == null) {
            $$$reportNull$$$0(0);
        }
        if (bArr != null) {
            return null;
        }
        $$$reportNull$$$0(1);
        return null;
    }

    String getDefaultExtension();

    String getDescription();

    Icon getIcon();

    String getName();

    boolean isBinary();
}

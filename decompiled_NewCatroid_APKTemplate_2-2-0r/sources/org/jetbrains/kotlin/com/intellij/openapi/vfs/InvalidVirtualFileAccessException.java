package org.jetbrains.kotlin.com.intellij.openapi.vfs;

import com.beust.jcommander.Parameters;
import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import java.io.File;

/* JADX INFO: loaded from: classes3.dex */
public class InvalidVirtualFileAccessException extends RuntimeException {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i != 1) {
            objArr[0] = "file";
        } else {
            objArr[0] = CrashHianalyticsData.MESSAGE;
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/openapi/vfs/InvalidVirtualFileAccessException";
        if (i != 2) {
            objArr[2] = "<init>";
        } else {
            objArr[2] = "composeMessage";
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public InvalidVirtualFileAccessException(VirtualFile virtualFile) {
        super(composeMessage(virtualFile));
        if (virtualFile == null) {
            $$$reportNull$$$0(0);
        }
    }

    private static String composeMessage(VirtualFile virtualFile) {
        String str;
        if (virtualFile == null) {
            $$$reportNull$$$0(2);
        }
        String url = virtualFile.getUrl();
        String str2 = "Accessing invalid virtual file: " + url;
        try {
            str2 = str2 + "; original:" + hashCode(virtualFile) + "; found:" + hashCode(VirtualFileManager.getInstance().findFileByUrl(url));
            if (virtualFile.isInLocalFileSystem()) {
                str = str2 + "; File.exists()=" + new File(virtualFile.getPath()).exists();
            } else {
                str = str2 + "; file system=" + virtualFile.getFileSystem();
            }
            return str;
        } catch (Throwable th) {
            return str2 + "; lookup failed: " + th.getMessage();
        }
    }

    private static String hashCode(Object obj) {
        return obj == null ? Parameters.DEFAULT_OPTION_PREFIXES : String.valueOf(obj.hashCode());
    }
}

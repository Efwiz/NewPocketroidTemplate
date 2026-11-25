package org.jetbrains.kotlin.com.intellij.util;

import org.jetbrains.kotlin.com.intellij.openapi.application.PathManager;
import org.jetbrains.kotlin.com.intellij.openapi.util.io.FileUtil;
import org.jetbrains.kotlin.com.intellij.openapi.util.text.StringUtil;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFileManager;
import org.jetbrains.kotlin.com.intellij.psi.PsiTreeChangeEvent;

/* JADX INFO: loaded from: classes3.dex */
public final class PathUtil {
    static final /* synthetic */ boolean $assertionsDisabled = false;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 1 || i == 3 || i == 6 || i == 9 || i == 11 || i == 13 || i == 20 || i == 17 || i == 18 || i == 22 || i == 23) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 1 || i == 3 || i == 6 || i == 9 || i == 11 || i == 13 || i == 20 || i == 17 || i == 18 || i == 22 || i == 23) ? 2 : 3];
        switch (i) {
            case 1:
            case 3:
            case 6:
            case 9:
            case 11:
            case 13:
            case 17:
            case 18:
            case 20:
            case 22:
            case 23:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/util/PathUtil";
                break;
            case 2:
                objArr[0] = "aClass";
                break;
            case 4:
                objArr[0] = "url";
                break;
            case 5:
            case 8:
            case 16:
            default:
                objArr[0] = "path";
                break;
            case 7:
            case 19:
                objArr[0] = "name";
                break;
            case 10:
            case 12:
                objArr[0] = "text";
                break;
            case 14:
            case 15:
                objArr[0] = PsiTreeChangeEvent.PROP_FILE_NAME;
                break;
            case 21:
                objArr[0] = "file";
                break;
        }
        if (i == 1) {
            objArr[1] = "getLocalPath";
        } else if (i == 3) {
            objArr[1] = "getJarPathForClass";
        } else if (i == 6) {
            objArr[1] = "getFileName";
        } else if (i == 9) {
            objArr[1] = "getParentPath";
        } else if (i == 11 || i == 13) {
            objArr[1] = "suggestFileName";
        } else if (i == 20) {
            objArr[1] = "makeFileName";
        } else if (i == 17 || i == 18) {
            objArr[1] = "driveLetterToLowerCase";
        } else if (i == 22 || i == 23) {
            objArr[1] = "getLocalFile";
        } else {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/util/PathUtil";
        }
        switch (i) {
            case 1:
            case 3:
            case 6:
            case 9:
            case 11:
            case 13:
            case 17:
            case 18:
            case 20:
            case 22:
            case 23:
                break;
            case 2:
                objArr[2] = "getJarPathForClass";
                break;
            case 4:
                objArr[2] = "toPresentableUrl";
                break;
            case 5:
                objArr[2] = "getFileName";
                break;
            case 7:
                objArr[2] = "getFileExtension";
                break;
            case 8:
                objArr[2] = "getParentPath";
                break;
            case 10:
            case 12:
                objArr[2] = "suggestFileName";
                break;
            case 14:
            case 15:
                objArr[2] = "isValidFileName";
                break;
            case 16:
                objArr[2] = "driveLetterToLowerCase";
                break;
            case 19:
                objArr[2] = "makeFileName";
                break;
            case 21:
                objArr[2] = "getLocalFile";
                break;
            default:
                objArr[2] = "getLocalPath";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 1 && i != 3 && i != 6 && i != 9 && i != 11 && i != 13 && i != 20 && i != 17 && i != 18 && i != 22 && i != 23) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    private PathUtil() {
    }

    public static String getLocalPath(String str) {
        if (str == null) {
            $$$reportNull$$$0(0);
        }
        String systemDependentName = FileUtil.toSystemDependentName(StringUtil.trimEnd(str, "!/"));
        if (systemDependentName == null) {
            $$$reportNull$$$0(1);
        }
        return systemDependentName;
    }

    public static String getJarPathForClass(Class<?> cls) {
        if (cls == null) {
            $$$reportNull$$$0(2);
        }
        String jarPathForClass = PathManager.getJarPathForClass(cls);
        if (jarPathForClass == null) {
            $$$reportNull$$$0(3);
        }
        return jarPathForClass;
    }

    public static String toPresentableUrl(String str) {
        if (str == null) {
            $$$reportNull$$$0(4);
        }
        return getLocalPath(VirtualFileManager.extractPath(str));
    }

    public static String getFileName(String str) {
        if (str == null) {
            $$$reportNull$$$0(5);
        }
        String fileName = PathUtilRt.getFileName(str);
        if (fileName == null) {
            $$$reportNull$$$0(6);
        }
        return fileName;
    }
}

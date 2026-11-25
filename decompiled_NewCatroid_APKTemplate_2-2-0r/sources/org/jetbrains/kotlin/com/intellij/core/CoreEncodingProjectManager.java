package org.jetbrains.kotlin.com.intellij.core;

import java.nio.charset.Charset;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.CharsetToolkit;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.encoding.EncodingProjectManager;

/* JADX INFO: loaded from: classes3.dex */
public class CoreEncodingProjectManager extends EncodingProjectManager {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 1 || i == 2 || i == 3 || i == 4) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 1 || i == 2 || i == 3 || i == 4) ? 2 : 3];
        switch (i) {
            case 1:
            case 2:
            case 3:
            case 4:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/core/CoreEncodingProjectManager";
                break;
            case 5:
                objArr[0] = "document";
                break;
            case 6:
                objArr[0] = "name";
                break;
            default:
                objArr[0] = "virtualFile";
                break;
        }
        if (i == 1) {
            objArr[1] = "getDefaultCharset";
        } else if (i == 2) {
            objArr[1] = "getFavorites";
        } else if (i == 3) {
            objArr[1] = "getDefaultCharsetName";
        } else if (i != 4) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/core/CoreEncodingProjectManager";
        } else {
            objArr[1] = "getDefaultConsoleEncoding";
        }
        switch (i) {
            case 1:
            case 2:
            case 3:
            case 4:
                break;
            case 5:
                objArr[2] = "getCachedCharsetFromContent";
                break;
            case 6:
                objArr[2] = "setDefaultCharsetName";
                break;
            default:
                objArr[2] = "isNative2Ascii";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 1 && i != 2 && i != 3 && i != 4) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.vfs.encoding.EncodingRegistry
    public void setEncoding(VirtualFile virtualFile, Charset charset) {
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.vfs.encoding.EncodingRegistry
    public Charset getDefaultCharset() {
        Charset defaultSystemCharset = CharsetToolkit.getDefaultSystemCharset();
        if (defaultSystemCharset == null) {
            $$$reportNull$$$0(1);
        }
        return defaultSystemCharset;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.vfs.encoding.EncodingRegistry
    public Charset getEncoding(VirtualFile virtualFile, boolean z) {
        return getDefaultCharset();
    }
}

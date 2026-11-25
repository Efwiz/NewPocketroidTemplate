package org.jetbrains.kotlin.com.intellij.openapi.vfs.encoding;

import org.jetbrains.kotlin.com.intellij.openapi.application.ApplicationManager;

/* JADX INFO: loaded from: classes3.dex */
public abstract class EncodingManager extends EncodingRegistry {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 1 ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[i != 1 ? 2 : 3];
        if (i != 1) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/openapi/vfs/encoding/EncodingManager";
        } else {
            objArr[0] = "name";
        }
        if (i != 1) {
            objArr[1] = "getInstance";
        } else {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/openapi/vfs/encoding/EncodingManager";
        }
        if (i == 1) {
            objArr[2] = "setDefaultCharsetName";
        }
        String str2 = String.format(str, objArr);
        if (i == 1) {
            throw new IllegalArgumentException(str2);
        }
    }

    public static EncodingManager getInstance() {
        EncodingManager encodingManager = (EncodingManager) ApplicationManager.getApplication().getService(EncodingManager.class);
        if (encodingManager == null) {
            $$$reportNull$$$0(0);
        }
        return encodingManager;
    }
}

package org.jetbrains.kotlin.com.intellij.openapi.vfs.encoding;

import java.nio.charset.Charset;
import org.jetbrains.kotlin.com.intellij.openapi.util.Comparing;
import org.jetbrains.kotlin.com.intellij.openapi.util.ThrowableComputable;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;

/* JADX INFO: loaded from: classes3.dex */
public abstract class EncodingRegistry {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i != 1) {
            objArr[0] = "fileBefore";
        } else {
            objArr[0] = "action";
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/openapi/vfs/encoding/EncodingRegistry";
        objArr[2] = "doActionAndRestoreEncoding";
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    public abstract Charset getDefaultCharset();

    public abstract Charset getEncoding(VirtualFile virtualFile, boolean z);

    public abstract void setEncoding(VirtualFile virtualFile, Charset charset);

    public static EncodingRegistry getInstance() {
        return EncodingManager.getInstance();
    }

    public static <E extends Throwable> VirtualFile doActionAndRestoreEncoding(VirtualFile virtualFile, ThrowableComputable<? extends VirtualFile, E> throwableComputable) throws Throwable {
        if (virtualFile == null) {
            $$$reportNull$$$0(0);
        }
        if (throwableComputable == null) {
            $$$reportNull$$$0(1);
        }
        EncodingRegistry encodingRegistry = getInstance();
        Charset encoding = encodingRegistry.getEncoding(virtualFile, true);
        VirtualFile virtualFileCompute = throwableComputable.compute();
        if (virtualFileCompute != null && !Comparing.equal(encodingRegistry.getEncoding(virtualFileCompute, true), encoding)) {
            encodingRegistry.setEncoding(virtualFileCompute, encoding);
        }
        return virtualFileCompute;
    }
}

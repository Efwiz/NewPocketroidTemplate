package org.jetbrains.kotlin.com.intellij.openapi.util.io;

import java.io.File;
import java.io.IOException;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.Manifest;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;

/* JADX INFO: loaded from: classes3.dex */
public final class JarUtil {
    private static final Logger LOG = Logger.getInstance((Class<?>) JarUtil.class);

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        switch (i) {
            case 1:
                objArr[0] = "className";
                break;
            case 2:
            case 3:
            case 5:
            case 8:
            case 10:
                objArr[0] = "file";
                break;
            case 4:
            case 7:
            case 9:
                objArr[0] = "attribute";
                break;
            case 6:
            case 11:
                objArr[0] = "entryName";
                break;
            default:
                objArr[0] = "jarPath";
                break;
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/openapi/util/io/JarUtil";
        switch (i) {
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
                objArr[2] = "getJarAttribute";
                break;
            case 8:
            case 9:
                objArr[2] = "getJarAttributeImpl";
                break;
            case 10:
            case 11:
                objArr[2] = "loadProperties";
                break;
            default:
                objArr[2] = "containsClass";
                break;
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    public static String getJarAttribute(File file, Attributes.Name name2) {
        if (file == null) {
            $$$reportNull$$$0(3);
        }
        if (name2 == null) {
            $$$reportNull$$$0(4);
        }
        return getJarAttributeImpl(file, null, name2);
    }

    private static String getJarAttributeImpl(File file, String str, Attributes.Name name2) {
        if (file == null) {
            $$$reportNull$$$0(8);
        }
        if (name2 == null) {
            $$$reportNull$$$0(9);
        }
        if (!file.canRead()) {
            return null;
        }
        try {
            JarFile jarFile = new JarFile(file);
            try {
                Manifest manifest = jarFile.getManifest();
                if (manifest == null) {
                    jarFile.close();
                    return null;
                }
                String value = (str != null ? manifest.getAttributes(str) : manifest.getMainAttributes()).getValue(name2);
                jarFile.close();
                return value;
            } catch (Throwable th) {
                try {
                    jarFile.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
                throw th;
            }
        } catch (IOException e) {
            LOG.debug(e);
            return null;
        }
        LOG.debug(e);
        return null;
    }
}

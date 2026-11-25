package org.jetbrains.kotlin.com.intellij.util.lang;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.security.CodeSource;
import java.security.Permissions;
import java.security.ProtectionDomain;
import java.util.jar.JarEntry;

/* JADX INFO: loaded from: classes3.dex */
final class SecureJarLoader extends JarLoader {
    private volatile ProtectionDomain protectionDomain;
    private final Object protectionDomainMonitor;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i == 1) {
            objArr[0] = "configuration";
        } else if (i != 2) {
            objArr[0] = "file";
        } else {
            objArr[0] = "entry";
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/util/lang/SecureJarLoader";
        if (i != 2) {
            objArr[2] = "<init>";
        } else {
            objArr[2] = "getProtectionDomain";
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    SecureJarLoader(Path path, ClassPath classPath) throws IOException {
        super(path, classPath, new JdkZipResourceFile(path, classPath.lockJars, true));
        if (path == null) {
            $$$reportNull$$$0(0);
        }
        if (classPath == null) {
            $$$reportNull$$$0(1);
        }
        this.protectionDomainMonitor = new Object();
    }

    ProtectionDomain getProtectionDomain(JarEntry jarEntry, URL url) {
        if (jarEntry == null) {
            $$$reportNull$$$0(2);
        }
        ProtectionDomain protectionDomain = this.protectionDomain;
        if (protectionDomain != null) {
            return protectionDomain;
        }
        synchronized (this.protectionDomainMonitor) {
            ProtectionDomain protectionDomain2 = this.protectionDomain;
            if (protectionDomain2 != null) {
                return protectionDomain2;
            }
            ProtectionDomain protectionDomain3 = new ProtectionDomain(new CodeSource(url, jarEntry.getCodeSigners()), new Permissions());
            this.protectionDomain = protectionDomain3;
            return protectionDomain3;
        }
    }
}

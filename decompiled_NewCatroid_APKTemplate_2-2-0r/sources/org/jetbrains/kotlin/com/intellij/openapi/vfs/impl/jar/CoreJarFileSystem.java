package org.jetbrains.kotlin.com.intellij.openapi.vfs.impl.jar;

import java.util.Map;
import org.jetbrains.kotlin.com.intellij.openapi.util.Couple;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.DeprecatedVirtualFileSystem;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.com.intellij.util.Function;
import org.jetbrains.kotlin.com.intellij.util.containers.ConcurrentFactoryMap;

/* JADX INFO: loaded from: classes3.dex */
public final class CoreJarFileSystem extends DeprecatedVirtualFileSystem {
    private final Map<String, CoreJarHandler> myHandlers = ConcurrentFactoryMap.createMap(new Function() { // from class: org.jetbrains.kotlin.com.intellij.openapi.vfs.impl.jar.CoreJarFileSystem$$ExternalSyntheticLambda0
        @Override // org.jetbrains.kotlin.com.intellij.util.Function
        public final Object fun(Object obj) {
            return this.f$0.m6514xa273e9a2((String) obj);
        }
    });

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 2 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[i != 2 ? 3 : 2];
        if (i != 2) {
            objArr[0] = "path";
        } else {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/openapi/vfs/impl/jar/CoreJarFileSystem";
        }
        if (i != 2) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/openapi/vfs/impl/jar/CoreJarFileSystem";
        } else {
            objArr[1] = "splitPath";
        }
        if (i == 1) {
            objArr[2] = "splitPath";
        } else if (i != 2) {
            if (i != 3) {
                objArr[2] = "findFileByPath";
            } else {
                objArr[2] = "refreshAndFindFileByPath";
            }
        }
        String str2 = String.format(str, objArr);
        if (i == 2) {
            throw new IllegalStateException(str2);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFileSystem
    public void refresh(boolean z) {
    }

    /* JADX INFO: renamed from: lambda$new$0$org-jetbrains-kotlin-com-intellij-openapi-vfs-impl-jar-CoreJarFileSystem, reason: not valid java name */
    /* synthetic */ CoreJarHandler m6514xa273e9a2(String str) {
        return new CoreJarHandler(this, str);
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFileSystem
    public String getProtocol() {
        return "jar";
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFileSystem
    public VirtualFile findFileByPath(String str) {
        if (str == null) {
            $$$reportNull$$$0(0);
        }
        Couple<String> coupleSplitPath = splitPath(str);
        return this.myHandlers.get(coupleSplitPath.first).findFileByPath((String) coupleSplitPath.second);
    }

    static Couple<String> splitPath(String str) {
        if (str == null) {
            $$$reportNull$$$0(1);
        }
        int iIndexOf = str.indexOf("!/");
        if (iIndexOf < 0) {
            throw new IllegalArgumentException("Path in JarFileSystem must contain a separator: " + str);
        }
        Couple<String> coupleOf = Couple.of(str.substring(0, iIndexOf), str.substring(iIndexOf + 2));
        if (coupleOf == null) {
            $$$reportNull$$$0(2);
        }
        return coupleOf;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFileSystem
    public VirtualFile refreshAndFindFileByPath(String str) {
        if (str == null) {
            $$$reportNull$$$0(3);
        }
        return findFileByPath(str);
    }

    public void clearHandlersCache() {
        this.myHandlers.clear();
    }
}

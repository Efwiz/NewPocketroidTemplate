package org.jetbrains.kotlin.com.intellij.openapi.vfs.impl.jar;

import com.reandroid.arsc.chunk.TypeBlock;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.impl.ArchiveHandler;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.impl.ZipHandler;
import org.jetbrains.kotlin.com.intellij.util.Function;
import org.jetbrains.kotlin.com.intellij.util.containers.FactoryMap;

/* JADX INFO: loaded from: classes3.dex */
final class CoreJarHandler extends ZipHandler {
    private final CoreJarFileSystem myFileSystem;
    private final VirtualFile myRoot;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 4 || i == 6) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 4 || i == 6) ? 2 : 3];
        switch (i) {
            case 1:
                objArr[0] = "path";
                break;
            case 2:
                objArr[0] = CompilerOptions.INFO;
                break;
            case 3:
                objArr[0] = TypeBlock.NAME_entries;
                break;
            case 4:
            case 6:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/openapi/vfs/impl/jar/CoreJarHandler";
                break;
            case 5:
                objArr[0] = "pathInJar";
                break;
            default:
                objArr[0] = "fileSystem";
                break;
        }
        if (i == 4) {
            objArr[1] = "getOrCreateFile";
        } else if (i != 6) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/openapi/vfs/impl/jar/CoreJarHandler";
        } else {
            objArr[1] = "getFileSystem";
        }
        if (i == 2 || i == 3) {
            objArr[2] = "getOrCreateFile";
        } else if (i != 4) {
            if (i == 5) {
                objArr[2] = "findFileByPath";
            } else if (i != 6) {
                objArr[2] = "<init>";
            }
        }
        String str2 = String.format(str, objArr);
        if (i != 4 && i != 6) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    CoreJarHandler(CoreJarFileSystem coreJarFileSystem, String str) {
        super(str);
        if (coreJarFileSystem == null) {
            $$$reportNull$$$0(0);
        }
        if (str == null) {
            $$$reportNull$$$0(1);
        }
        this.myFileSystem = coreJarFileSystem;
        HashMap map = new HashMap();
        Map<String, ArchiveHandler.EntryInfo> entriesMap = getEntriesMap();
        Map mapCreate = FactoryMap.create(new Function() { // from class: org.jetbrains.kotlin.com.intellij.openapi.vfs.impl.jar.CoreJarHandler$$ExternalSyntheticLambda0
            @Override // org.jetbrains.kotlin.com.intellij.util.Function
            public final Object fun(Object obj) {
                return CoreJarHandler.lambda$new$0((CoreJarVirtualFile) obj);
            }
        });
        Iterator<ArchiveHandler.EntryInfo> it = entriesMap.values().iterator();
        while (it.hasNext()) {
            CoreJarVirtualFile orCreateFile = getOrCreateFile(it.next(), map);
            VirtualFile parent = orCreateFile.getParent();
            if (parent != null) {
                ((List) mapCreate.get(parent)).add(orCreateFile);
            }
        }
        ArchiveHandler.EntryInfo entryInfo = getEntryInfo("");
        this.myRoot = entryInfo != null ? getOrCreateFile(entryInfo, map) : null;
        for (Map.Entry entry : mapCreate.entrySet()) {
            ((CoreJarVirtualFile) entry.getKey()).setChildren((VirtualFile[]) ((List) entry.getValue()).toArray(VirtualFile.EMPTY_ARRAY));
        }
    }

    static /* synthetic */ List lambda$new$0(CoreJarVirtualFile coreJarVirtualFile) {
        return new ArrayList();
    }

    private CoreJarVirtualFile getOrCreateFile(ArchiveHandler.EntryInfo entryInfo, Map<ArchiveHandler.EntryInfo, CoreJarVirtualFile> map) {
        if (entryInfo == null) {
            $$$reportNull$$$0(2);
        }
        if (map == null) {
            $$$reportNull$$$0(3);
        }
        CoreJarVirtualFile coreJarVirtualFile = map.get(entryInfo);
        if (coreJarVirtualFile == null) {
            ArchiveHandler.EntryInfo entryInfo2 = entryInfo.parent;
            CoreJarVirtualFile coreJarVirtualFile2 = new CoreJarVirtualFile(this, entryInfo.shortName, entryInfo.isDirectory ? -1L : entryInfo.length, entryInfo.timestamp, entryInfo2 != null ? getOrCreateFile(entryInfo2, map) : null);
            map.put(entryInfo, coreJarVirtualFile2);
            coreJarVirtualFile = coreJarVirtualFile2;
        }
        if (coreJarVirtualFile == null) {
            $$$reportNull$$$0(4);
        }
        return coreJarVirtualFile;
    }

    VirtualFile findFileByPath(String str) {
        if (str == null) {
            $$$reportNull$$$0(5);
        }
        VirtualFile virtualFile = this.myRoot;
        if (virtualFile != null) {
            return virtualFile.findFileByRelativePath(str);
        }
        return null;
    }

    CoreJarFileSystem getFileSystem() {
        CoreJarFileSystem coreJarFileSystem = this.myFileSystem;
        if (coreJarFileSystem == null) {
            $$$reportNull$$$0(6);
        }
        return coreJarFileSystem;
    }
}

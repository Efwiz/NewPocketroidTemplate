package org.jetbrains.kotlin.cli.jvm.compiler.jarfs;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.io.CloseableKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.openapi.util.text.StringUtil;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.com.intellij.util.io.FileAccessorCache;

/* JADX INFO: compiled from: FastJarHandler.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0012\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010%\n\u0002\b\u0004\n\u0002\u0010\r\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u000e\u0010\u0011\u001a\u00020\b2\u0006\u0010\u0012\u001a\u00020\u0013J$\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u00132\u0012\u0010\u0017\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00150\u0018H\u0002J\u0010\u0010\u0019\u001a\u0004\u0018\u00010\u00102\u0006\u0010\u001a\u001a\u00020\u0005J$\u0010\u001b\u001a\u00020\u00152\u0006\u0010\u001c\u001a\u00020\u001d2\u0012\u0010\u0017\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00150\u0018H\u0002J\u0018\u0010\u001e\u001a\u000e\u0012\u0004\u0012\u00020\u001d\u0012\u0004\u0012\u00020\u001d0\u001f*\u00020\u001dH\u0002R\u0010\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\t\u001a\u00020\nX\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0010\u0010\u000f\u001a\u0004\u0018\u00010\u0010X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006 "}, d2 = {"Lorg/jetbrains/kotlin/cli/jvm/compiler/jarfs/FastJarHandler;", "", "fileSystem", "Lorg/jetbrains/kotlin/cli/jvm/compiler/jarfs/FastJarFileSystem;", "path", "", "(Lorg/jetbrains/kotlin/cli/jvm/compiler/jarfs/FastJarFileSystem;Ljava/lang/String;)V", "cachedManifest", "", "file", "Ljava/io/File;", "getFile$cli_base", "()Ljava/io/File;", "getFileSystem", "()Lorg/jetbrains/kotlin/cli/jvm/compiler/jarfs/FastJarFileSystem;", "myRoot", "Lorg/jetbrains/kotlin/com/intellij/openapi/vfs/VirtualFile;", "contentsToByteArray", "zipEntryDescription", "Lorg/jetbrains/kotlin/cli/jvm/compiler/jarfs/ZipEntryDescription;", "createFile", "Lorg/jetbrains/kotlin/cli/jvm/compiler/jarfs/FastJarVirtualFile;", "entry", "directories", "", "findFileByPath", "pathInJar", "getOrCreateDirectory", "entryName", "", "splitPath", "Lkotlin/Pair;", "cli-base"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FastJarHandler {
    private final byte[] cachedManifest;
    private final File file;
    private final FastJarFileSystem fileSystem;
    private final VirtualFile myRoot;

    public FastJarHandler(FastJarFileSystem fileSystem, String path) {
        List<ZipEntryDescription> listEmptyList;
        Iterator<T> it;
        boolean z;
        Object obj;
        byte[] bArrContentsToByteArray;
        Intrinsics.checkNotNullParameter(fileSystem, "fileSystem");
        Intrinsics.checkNotNullParameter(path, "path");
        this.fileSystem = fileSystem;
        File file = new File(path);
        this.file = file;
        RandomAccessFile randomAccessFile = new RandomAccessFile(file, "r");
        try {
            RandomAccessFile randomAccessFile2 = randomAccessFile;
            MappedByteBuffer map = randomAccessFile2.getChannel().map(FileChannel.MapMode.READ_ONLY, 0L, randomAccessFile2.length());
            try {
                try {
                    Intrinsics.checkNotNull(map);
                    listEmptyList = ZipImplementationKt.parseCentralDirectory(map);
                    while (true) {
                        if (!it.hasNext()) {
                            if (z) {
                                break;
                            } else {
                                break;
                            }
                        } else {
                            Object next = it.next();
                            if (StringUtil.equals("META-INF/MANIFEST.MF", ((ZipEntryDescription) next).getRelativePath())) {
                                if (!z) {
                                    z = true;
                                    obj = next;
                                }
                            }
                        }
                        obj = null;
                        break;
                    }
                } catch (Exception e) {
                    Logger.getInstance(getClass()).warn("Error while reading zip file: " + this.file.getPath() + ": " + e, e);
                    listEmptyList = CollectionsKt.emptyList();
                }
                it = listEmptyList.iterator();
                z = false;
                obj = null;
                ZipEntryDescription zipEntryDescription = (ZipEntryDescription) obj;
                if (zipEntryDescription != null) {
                    Intrinsics.checkNotNull(map);
                    bArrContentsToByteArray = ZipImplementationKt.contentsToByteArray(map, zipEntryDescription);
                } else {
                    bArrContentsToByteArray = null;
                }
                this.cachedManifest = bArrContentsToByteArray;
                Function1<MappedByteBuffer, Unit> unmapBuffer$cli_base = this.fileSystem.getUnmapBuffer$cli_base();
                Intrinsics.checkNotNull(map);
                unmapBuffer$cli_base.invoke(map);
                Unit unit = Unit.INSTANCE;
                CloseableKt.closeFinally(randomAccessFile, null);
                FastJarVirtualFile fastJarVirtualFile = new FastJarVirtualFile(this, "", -1, null, null);
                this.myRoot = fastJarVirtualFile;
                HashMap map2 = new HashMap(listEmptyList.size());
                HashMap map3 = map2;
                map3.put("", fastJarVirtualFile);
                for (ZipEntryDescription zipEntryDescription2 : listEmptyList) {
                    if (!zipEntryDescription2.isDirectory()) {
                        createFile(zipEntryDescription2, map3);
                    } else {
                        getOrCreateDirectory(zipEntryDescription2.getRelativePath(), map3);
                    }
                }
                Iterator it2 = map2.values().iterator();
                while (it2.hasNext()) {
                    ((FastJarVirtualFile) it2.next()).initChildrenArrayFromList();
                }
            } catch (Throwable th) {
                Function1<MappedByteBuffer, Unit> unmapBuffer$cli_base2 = this.fileSystem.getUnmapBuffer$cli_base();
                Intrinsics.checkNotNull(map);
                unmapBuffer$cli_base2.invoke(map);
                throw th;
            }
        } catch (Throwable th2) {
            try {
                throw th2;
            } catch (Throwable th3) {
                CloseableKt.closeFinally(randomAccessFile, th2);
                throw th3;
            }
        }
    }

    public final FastJarFileSystem getFileSystem() {
        return this.fileSystem;
    }

    /* JADX INFO: renamed from: getFile$cli_base, reason: from getter */
    public final File getFile() {
        return this.file;
    }

    private final FastJarVirtualFile createFile(ZipEntryDescription entry, Map<String, FastJarVirtualFile> directories) {
        Pair<CharSequence, CharSequence> pairSplitPath = splitPath(entry.getRelativePath());
        CharSequence charSequenceComponent1 = pairSplitPath.component1();
        CharSequence charSequenceComponent2 = pairSplitPath.component2();
        FastJarVirtualFile orCreateDirectory = getOrCreateDirectory(charSequenceComponent1, directories);
        if (Intrinsics.areEqual(".", charSequenceComponent2)) {
            return orCreateDirectory;
        }
        return new FastJarVirtualFile(this, charSequenceComponent2, entry.isDirectory() ? -1 : entry.getUncompressedSize(), orCreateDirectory, entry);
    }

    private final FastJarVirtualFile getOrCreateDirectory(CharSequence entryName, Map<String, FastJarVirtualFile> directories) {
        String string = entryName.toString();
        FastJarVirtualFile fastJarVirtualFile = directories.get(string);
        if (fastJarVirtualFile == null) {
            Pair<CharSequence, CharSequence> pairSplitPath = splitPath(entryName);
            fastJarVirtualFile = new FastJarVirtualFile(this, pairSplitPath.component2(), -1, getOrCreateDirectory(pairSplitPath.component1(), directories), null);
            directories.put(string, fastJarVirtualFile);
        }
        return fastJarVirtualFile;
    }

    private final Pair<CharSequence, CharSequence> splitPath(CharSequence charSequence) {
        int length = charSequence.length() - 1;
        while (length >= 0 && charSequence.charAt(length) != '/') {
            length--;
        }
        if (length == -1) {
            return new Pair<>("", charSequence);
        }
        return new Pair<>(charSequence.subSequence(0, length), charSequence.subSequence(length + 1, charSequence.length()));
    }

    public final VirtualFile findFileByPath(String pathInJar) {
        Intrinsics.checkNotNullParameter(pathInJar, "pathInJar");
        VirtualFile virtualFile = this.myRoot;
        if (virtualFile != null) {
            return virtualFile.findFileByRelativePath(pathInJar);
        }
        return null;
    }

    public final byte[] contentsToByteArray(ZipEntryDescription zipEntryDescription) throws FileNotFoundException {
        byte[] bArrContentsToByteArray;
        Intrinsics.checkNotNullParameter(zipEntryDescription, "zipEntryDescription");
        CharSequence relativePath = zipEntryDescription.getRelativePath();
        if (StringUtil.equals(relativePath, "META-INF/MANIFEST.MF")) {
            byte[] bArr = this.cachedManifest;
            if (bArr != null) {
                return bArr;
            }
            throw new FileNotFoundException(this.file + "!/" + ((Object) relativePath));
        }
        FileAccessorCache.Handle<Pair<RandomAccessFile, MappedByteBuffer>> handle = this.fileSystem.getCachedOpenFileHandles$cli_base().get(this.file);
        try {
            FileAccessorCache.Handle<Pair<RandomAccessFile, MappedByteBuffer>> handle2 = handle;
            Intrinsics.checkNotNull(handle2);
            synchronized (handle2) {
                bArrContentsToByteArray = ZipImplementationKt.contentsToByteArray(handle2.get().getSecond(), zipEntryDescription);
            }
            CloseableKt.closeFinally(handle, null);
            return bArrContentsToByteArray;
        } catch (Throwable th) {
            try {
                throw th;
            } catch (Throwable th2) {
                CloseableKt.closeFinally(handle, th);
                throw th2;
            }
        }
    }
}

package org.jetbrains.kotlin.cli.jvm.modules;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.io.CloseableKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.openapi.util.io.FileUtil;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VfsUtilCore;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFileSystem;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: CoreJrtVirtualFile.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000t\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0011\n\u0002\b\u0006\n\u0002\u0010\u0012\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\b\u0000\u0018\u00002\u00020\u0001B'\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\b\u0010\b\u001a\u0004\u0018\u00010\u0000¢\u0006\u0002\u0010\tJ\u0015\u0010\u0014\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00010\u000fH\u0002¢\u0006\u0002\u0010\u0011J\b\u0010\u0015\u001a\u00020\u0016H\u0016J\u0013\u0010\u0017\u001a\u00020\u00182\b\u0010\u0019\u001a\u0004\u0018\u00010\u001aH\u0096\u0002J\u0015\u0010\u001b\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00010\u000fH\u0016¢\u0006\u0002\u0010\u0011J\b\u0010\u001c\u001a\u00020\u001dH\u0016J\b\u0010\u001e\u001a\u00020\u001fH\u0016J\b\u0010 \u001a\u00020!H\u0016J\b\u0010\"\u001a\u00020!H\u0016J\b\u0010#\u001a\u00020\u0005H\u0016J \u0010$\u001a\u00020%2\u0006\u0010&\u001a\u00020\u001a2\u0006\u0010'\u001a\u00020!2\u0006\u0010(\u001a\u00020!H\u0016J\n\u0010)\u001a\u0004\u0018\u00010\u0001H\u0016J\b\u0010*\u001a\u00020\u0005H\u0016J\b\u0010+\u001a\u00020!H\u0016J\b\u0010,\u001a\u00020-H\u0016J\b\u0010.\u001a\u00020\u0018H\u0016J\b\u0010/\u001a\u00020\u0018H\u0016J\b\u00100\u001a\u00020\u0018H\u0016J\"\u00101\u001a\u0002022\u0006\u00103\u001a\u00020\u00182\u0006\u00104\u001a\u00020\u00182\b\u00105\u001a\u0004\u0018\u000106H\u0016R\u0014\u0010\n\u001a\u00020\u000b8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\rR\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R#\u0010\u000e\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00010\u000f8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0012\u0010\u0013\u001a\u0004\b\u0010\u0010\u0011R\u0010\u0010\b\u001a\u0004\u0018\u00010\u0000X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u00067"}, d2 = {"Lorg/jetbrains/kotlin/cli/jvm/modules/CoreJrtVirtualFile;", "Lorg/jetbrains/kotlin/com/intellij/openapi/vfs/VirtualFile;", "virtualFileSystem", "Lorg/jetbrains/kotlin/cli/jvm/modules/CoreJrtFileSystem;", "jdkHomePath", "", "path", "Ljava/nio/file/Path;", "parent", "(Lorg/jetbrains/kotlin/cli/jvm/modules/CoreJrtFileSystem;Ljava/lang/String;Ljava/nio/file/Path;Lorg/jetbrains/kotlin/cli/jvm/modules/CoreJrtVirtualFile;)V", "attributes", "Ljava/nio/file/attribute/BasicFileAttributes;", "getAttributes", "()Ljava/nio/file/attribute/BasicFileAttributes;", "myChildren", "", "getMyChildren", "()[Lcom/intellij/openapi/vfs/VirtualFile;", "myChildren$delegate", "Lkotlin/Lazy;", "computeChildren", "contentsToByteArray", "", Namer.EQUALS_METHOD_NAME, "", "other", "", "getChildren", "getFileSystem", "Lorg/jetbrains/kotlin/com/intellij/openapi/vfs/VirtualFileSystem;", "getInputStream", "Ljava/io/InputStream;", "getLength", "", "getModificationStamp", "getName", "getOutputStream", "Ljava/io/OutputStream;", "requestor", "newModificationStamp", "newTimeStamp", "getParent", "getPath", "getTimeStamp", "hashCode", "", "isDirectory", "isValid", "isWritable", "refresh", "", "asynchronous", "recursive", "postRunnable", "Ljava/lang/Runnable;", "cli-base"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CoreJrtVirtualFile extends VirtualFile {
    private final String jdkHomePath;

    /* JADX INFO: renamed from: myChildren$delegate, reason: from kotlin metadata */
    private final Lazy myChildren;
    private final CoreJrtVirtualFile parent;
    private final Path path;
    private final CoreJrtFileSystem virtualFileSystem;

    @Override // org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile
    public long getModificationStamp() {
        return 0L;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile
    public boolean isValid() {
        return true;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile
    public boolean isWritable() {
        return false;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile
    public void refresh(boolean asynchronous, boolean recursive, Runnable postRunnable) {
    }

    public CoreJrtVirtualFile(CoreJrtFileSystem virtualFileSystem, String jdkHomePath, Path path, CoreJrtVirtualFile coreJrtVirtualFile) {
        Intrinsics.checkNotNullParameter(virtualFileSystem, "virtualFileSystem");
        Intrinsics.checkNotNullParameter(jdkHomePath, "jdkHomePath");
        Intrinsics.checkNotNullParameter(path, "path");
        this.virtualFileSystem = virtualFileSystem;
        this.jdkHomePath = jdkHomePath;
        this.path = path;
        this.parent = coreJrtVirtualFile;
        this.myChildren = LazyKt.lazy(new Function0<VirtualFile[]>() { // from class: org.jetbrains.kotlin.cli.jvm.modules.CoreJrtVirtualFile$myChildren$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final VirtualFile[] invoke() {
                return this.this$0.computeChildren();
            }
        });
    }

    private final BasicFileAttributes getAttributes() throws IOException {
        BasicFileAttributes attributes = Files.readAttributes(this.path, (Class<BasicFileAttributes>) BasicFileAttributes.class, new LinkOption[0]);
        Intrinsics.checkNotNullExpressionValue(attributes, "readAttributes(...)");
        return attributes;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile
    public VirtualFileSystem getFileSystem() {
        return this.virtualFileSystem;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile
    public String getName() {
        return this.path.getFileName().toString();
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile
    public String getPath() {
        String systemIndependentName = FileUtil.toSystemIndependentName(this.jdkHomePath + "!/" + this.path);
        Intrinsics.checkNotNullExpressionValue(systemIndependentName, "toSystemIndependentName(...)");
        return systemIndependentName;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile
    public boolean isDirectory() {
        return Files.isDirectory(this.path, new LinkOption[0]);
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile
    public VirtualFile getParent() {
        return this.parent;
    }

    private final VirtualFile[] getMyChildren() {
        return (VirtualFile[]) this.myChildren.getValue();
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile
    public VirtualFile[] getChildren() {
        return getMyChildren();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final VirtualFile[] computeChildren() {
        List listEmptyList;
        try {
            DirectoryStream<Path> directoryStreamNewDirectoryStream = Files.newDirectoryStream(this.path);
            try {
                listEmptyList = CollectionsKt.toList(directoryStreamNewDirectoryStream);
                CloseableKt.closeFinally(directoryStreamNewDirectoryStream, null);
            } catch (Throwable th) {
                try {
                    throw th;
                } catch (Throwable th2) {
                    CloseableKt.closeFinally(directoryStreamNewDirectoryStream, th);
                    throw th2;
                }
            }
        } catch (IOException unused) {
            listEmptyList = CollectionsKt.emptyList();
        }
        if (listEmptyList.isEmpty()) {
            VirtualFile[] EMPTY_ARRAY = VirtualFile.EMPTY_ARRAY;
            Intrinsics.checkNotNullExpressionValue(EMPTY_ARRAY, "EMPTY_ARRAY");
            return EMPTY_ARRAY;
        }
        List list = listEmptyList;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        Iterator it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(new CoreJrtVirtualFile(this.virtualFileSystem, this.jdkHomePath, (Path) it.next(), this));
        }
        return (VirtualFile[]) arrayList.toArray(new CoreJrtVirtualFile[0]);
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile
    public OutputStream getOutputStream(Object requestor, long newModificationStamp, long newTimeStamp) {
        Intrinsics.checkNotNullParameter(requestor, "requestor");
        throw new UnsupportedOperationException();
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile
    public byte[] contentsToByteArray() throws IOException {
        byte[] allBytes = Files.readAllBytes(this.path);
        Intrinsics.checkNotNullExpressionValue(allBytes, "readAllBytes(...)");
        return allBytes;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile
    public long getTimeStamp() {
        return getAttributes().lastModifiedTime().toMillis();
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile
    public long getLength() {
        return getAttributes().size();
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile
    public InputStream getInputStream() throws IOException {
        InputStream inputStreamNewInputStream = Files.newInputStream(this.path, new OpenOption[0]);
        Intrinsics.checkNotNullExpressionValue(inputStreamNewInputStream, "newInputStream(...)");
        InputStream inputStreamInputStreamSkippingBOM = VfsUtilCore.inputStreamSkippingBOM(inputStreamNewInputStream instanceof BufferedInputStream ? (BufferedInputStream) inputStreamNewInputStream : new BufferedInputStream(inputStreamNewInputStream, 8192), this);
        Intrinsics.checkNotNullExpressionValue(inputStreamInputStreamSkippingBOM, "inputStreamSkippingBOM(...)");
        return inputStreamInputStreamSkippingBOM;
    }

    public boolean equals(Object other) {
        if (other instanceof CoreJrtVirtualFile) {
            CoreJrtVirtualFile coreJrtVirtualFile = (CoreJrtVirtualFile) other;
            if (Intrinsics.areEqual(this.path, coreJrtVirtualFile.path) && Intrinsics.areEqual(getFileSystem(), coreJrtVirtualFile.getFileSystem())) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return this.path.hashCode();
    }
}

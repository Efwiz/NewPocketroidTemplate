package org.jetbrains.kotlin.cli.jvm.compiler.jarfs;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.openapi.util.io.BufferExposingByteArrayInputStream;
import org.jetbrains.kotlin.com.intellij.openapi.util.io.FileUtil;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFileSystem;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;

/* JADX INFO: compiled from: FastJarVirtualFile.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000z\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\r\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\b\u0003\n\u0002\u0010!\n\u0000\n\u0002\u0010\u0012\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\b\u0000\u0018\u00002\u00020\u0001B1\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\b\u0010\b\u001a\u0004\u0018\u00010\u0000\u0012\b\u0010\t\u001a\u0004\u0018\u00010\n¢\u0006\u0002\u0010\u000bJ\b\u0010\u0012\u001a\u00020\u0013H\u0016J\u0013\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00010\rH\u0016¢\u0006\u0002\u0010\u0015J\b\u0010\u0016\u001a\u00020\u0017H\u0016J\b\u0010\u0018\u001a\u00020\u0019H\u0016J\b\u0010\u001a\u001a\u00020\u001bH\u0016J\b\u0010\u001c\u001a\u00020\u001bH\u0016J\b\u0010\u001d\u001a\u00020\u001eH\u0016J\b\u0010\u001f\u001a\u00020\u0005H\u0016J \u0010 \u001a\u00020!2\u0006\u0010\"\u001a\u00020#2\u0006\u0010$\u001a\u00020\u001b2\u0006\u0010%\u001a\u00020\u001bH\u0016J\n\u0010&\u001a\u0004\u0018\u00010\u0001H\u0016J\b\u0010'\u001a\u00020\u001eH\u0016J\b\u0010(\u001a\u00020\u001bH\u0016J\u0006\u0010)\u001a\u00020*J\b\u0010+\u001a\u00020,H\u0016J\b\u0010-\u001a\u00020,H\u0016J\b\u0010.\u001a\u00020,H\u0016J\"\u0010/\u001a\u00020*2\u0006\u00100\u001a\u00020,2\u0006\u00101\u001a\u00020,2\b\u00102\u001a\u0004\u0018\u000103H\u0016R\u0010\u0010\t\u001a\u0004\u0018\u00010\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R6\u0010\f\u001a(\u0012\f\u0012\n \u000e*\u0004\u0018\u00010\u00010\u0001 \u000e*\u0014\u0012\u000e\b\u0001\u0012\n \u000e*\u0004\u0018\u00010\u00010\u0001\u0018\u00010\r0\rX\u0082\u000e¢\u0006\u0004\n\u0002\u0010\u000fR\u0014\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00010\u0011X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\b\u001a\u0004\u0018\u00010\u0000X\u0082\u0004¢\u0006\u0002\n\u0000¨\u00064"}, d2 = {"Lorg/jetbrains/kotlin/cli/jvm/compiler/jarfs/FastJarVirtualFile;", "Lorg/jetbrains/kotlin/com/intellij/openapi/vfs/VirtualFile;", "handler", "Lorg/jetbrains/kotlin/cli/jvm/compiler/jarfs/FastJarHandler;", "name", "", "length", "", "parent", "entryDescription", "Lorg/jetbrains/kotlin/cli/jvm/compiler/jarfs/ZipEntryDescription;", "(Lorg/jetbrains/kotlin/cli/jvm/compiler/jarfs/FastJarHandler;Ljava/lang/CharSequence;ILorg/jetbrains/kotlin/cli/jvm/compiler/jarfs/FastJarVirtualFile;Lorg/jetbrains/kotlin/cli/jvm/compiler/jarfs/ZipEntryDescription;)V", "myChildrenArray", "", JvmProtoBufUtil.PLATFORM_TYPE_ID, "[Lorg/jetbrains/kotlin/com/intellij/openapi/vfs/VirtualFile;", "myChildrenList", "", "contentsToByteArray", "", "getChildren", "()[Lcom/intellij/openapi/vfs/VirtualFile;", "getFileSystem", "Lorg/jetbrains/kotlin/com/intellij/openapi/vfs/VirtualFileSystem;", "getInputStream", "Ljava/io/InputStream;", "getLength", "", "getModificationStamp", "getName", "", "getNameSequence", "getOutputStream", "Ljava/io/OutputStream;", "requestor", "", "newModificationStamp", "newTimeStamp", "getParent", "getPath", "getTimeStamp", "initChildrenArrayFromList", "", "isDirectory", "", "isValid", "isWritable", "refresh", "asynchronous", "recursive", "postRunnable", "Ljava/lang/Runnable;", "cli-base"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FastJarVirtualFile extends VirtualFile {
    private final ZipEntryDescription entryDescription;
    private final FastJarHandler handler;
    private final int length;
    private VirtualFile[] myChildrenArray;
    private final List<VirtualFile> myChildrenList;
    private final CharSequence name;
    private final FastJarVirtualFile parent;

    @Override // org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile
    public long getModificationStamp() {
        return 0L;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile
    public long getTimeStamp() {
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

    public FastJarVirtualFile(FastJarHandler handler, CharSequence name2, int i, FastJarVirtualFile fastJarVirtualFile, ZipEntryDescription zipEntryDescription) {
        List<VirtualFile> list;
        Intrinsics.checkNotNullParameter(handler, "handler");
        Intrinsics.checkNotNullParameter(name2, "name");
        this.handler = handler;
        this.name = name2;
        this.length = i;
        this.parent = fastJarVirtualFile;
        this.entryDescription = zipEntryDescription;
        this.myChildrenArray = VirtualFile.EMPTY_ARRAY;
        this.myChildrenList = new ArrayList();
        if (fastJarVirtualFile == null || (list = fastJarVirtualFile.myChildrenList) == null) {
            return;
        }
        list.add(this);
    }

    public final void initChildrenArrayFromList() {
        this.myChildrenArray = (VirtualFile[]) this.myChildrenList.toArray(new VirtualFile[0]);
        this.myChildrenList.clear();
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile
    public String getName() {
        return this.name.toString();
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile
    /* JADX INFO: renamed from: getNameSequence, reason: from getter */
    public CharSequence getName() {
        return this.name;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile
    public VirtualFileSystem getFileSystem() {
        return this.handler.getFileSystem();
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile
    public String getPath() {
        FastJarVirtualFile fastJarVirtualFile = this.parent;
        if (fastJarVirtualFile == null) {
            return FileUtil.toSystemIndependentName(this.handler.getFile().getPath()) + "!/";
        }
        String path = fastJarVirtualFile.getPath();
        StringBuilder sb = new StringBuilder(path.length() + 1 + this.name.length());
        sb.append(path);
        if (sb.charAt(sb.length() - 1) != '/') {
            sb.append('/');
        }
        sb.append(this.name);
        String string = sb.toString();
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        return string;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile
    public boolean isDirectory() {
        return this.length < 0;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile
    public VirtualFile getParent() {
        return this.parent;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile
    public VirtualFile[] getChildren() {
        VirtualFile[] myChildrenArray = this.myChildrenArray;
        Intrinsics.checkNotNullExpressionValue(myChildrenArray, "myChildrenArray");
        return myChildrenArray;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile
    public OutputStream getOutputStream(Object requestor, long newModificationStamp, long newTimeStamp) throws IOException {
        Intrinsics.checkNotNullParameter(requestor, "requestor");
        throw new UnsupportedOperationException("JarFileSystem is read-only");
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile
    public byte[] contentsToByteArray() throws IOException {
        ZipEntryDescription zipEntryDescription = this.entryDescription;
        if (zipEntryDescription == null) {
            return FastJarVirtualFileKt.EMPTY_BYTE_ARRAY;
        }
        return this.handler.contentsToByteArray(zipEntryDescription);
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile
    public long getLength() {
        return this.length;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile
    public InputStream getInputStream() throws IOException {
        return new BufferExposingByteArrayInputStream(contentsToByteArray());
    }
}

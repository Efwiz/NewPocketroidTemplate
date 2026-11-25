package org.jetbrains.kotlin.cli.jvm.modules;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.ide.highlighter.JavaClassFileType;
import org.jetbrains.kotlin.com.intellij.openapi.fileTypes.FileType;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFileSystem;

/* JADX INFO: compiled from: CtSymClassVirtualFile.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000j\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0012\n\u0000\n\u0002\u0010\u0011\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0017\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0001\u0012\u0006\u0010\u0003\u001a\u00020\u0001¢\u0006\u0002\u0010\u0004J\b\u0010\u0005\u001a\u00020\u0006H\u0016J\u0015\u0010\u0007\u001a\n\u0012\u0004\u0012\u00020\u0001\u0018\u00010\bH\u0016¢\u0006\u0002\u0010\tJ\r\u0010\n\u001a\u00070\u000b¢\u0006\u0002\b\fH\u0016J\b\u0010\r\u001a\u00020\u000eH\u0016J\b\u0010\u000f\u001a\u00020\u0010H\u0016J\b\u0010\u0011\u001a\u00020\u0012H\u0016J\b\u0010\u0013\u001a\u00020\u0012H\u0016J\u0012\u0010\u0014\u001a\f0\u0015¢\u0006\u0002\b\f¢\u0006\u0002\b\u0016H\u0016J'\u0010\u0017\u001a\u00070\u0018¢\u0006\u0002\b\f2\b\u0010\u0019\u001a\u0004\u0018\u00010\u001a2\u0006\u0010\u001b\u001a\u00020\u00122\u0006\u0010\u001c\u001a\u00020\u0012H\u0016J\n\u0010\u001d\u001a\u0004\u0018\u00010\u0001H\u0016J\u0012\u0010\u001e\u001a\f0\u0015¢\u0006\u0002\b\u001f¢\u0006\u0002\b\fH\u0016J\b\u0010 \u001a\u00020\u0012H\u0016J\b\u0010!\u001a\u00020\"H\u0016J\b\u0010#\u001a\u00020\"H\u0016J\b\u0010$\u001a\u00020\"H\u0016J\"\u0010%\u001a\u00020&2\u0006\u0010\u0019\u001a\u00020\"2\u0006\u0010\u001b\u001a\u00020\"2\b\u0010\u001c\u001a\u0004\u0018\u00010'H\u0016R\u000e\u0010\u0003\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0002\u001a\u0004\u0018\u00010\u0001X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006("}, d2 = {"Lorg/jetbrains/kotlin/cli/jvm/modules/CtSymClassVirtualFile;", "Lorg/jetbrains/kotlin/com/intellij/openapi/vfs/VirtualFile;", "parent", "file", "(Lcom/intellij/openapi/vfs/VirtualFile;Lcom/intellij/openapi/vfs/VirtualFile;)V", "contentsToByteArray", "", "getChildren", "", "()[Lcom/intellij/openapi/vfs/VirtualFile;", "getFileSystem", "Lorg/jetbrains/kotlin/com/intellij/openapi/vfs/VirtualFileSystem;", "Lorg/jetbrains/annotations/NotNull;", "getFileType", "Lorg/jetbrains/kotlin/com/intellij/openapi/fileTypes/FileType;", "getInputStream", "Ljava/io/InputStream;", "getLength", "", "getModificationStamp", "getName", "", "Lorg/jetbrains/kotlin/com/intellij/openapi/util/NlsSafe;", "getOutputStream", "Ljava/io/OutputStream;", "p0", "", "p1", "p2", "getParent", "getPath", "Lorg/jetbrains/annotations/NonNls;", "getTimeStamp", "isDirectory", "", "isValid", "isWritable", "refresh", "", "Ljava/lang/Runnable;", "cli-base"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CtSymClassVirtualFile extends VirtualFile {
    private final VirtualFile file;
    private final VirtualFile parent;

    @Override // org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile
    public VirtualFile[] getChildren() {
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile
    public boolean isDirectory() {
        return false;
    }

    public CtSymClassVirtualFile(VirtualFile virtualFile, VirtualFile file) {
        Intrinsics.checkNotNullParameter(file, "file");
        this.parent = virtualFile;
        this.file = file;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile
    public String getName() {
        String name2 = this.file.getName();
        Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
        return name2;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile
    public VirtualFileSystem getFileSystem() {
        VirtualFileSystem fileSystem = this.file.getFileSystem();
        Intrinsics.checkNotNullExpressionValue(fileSystem, "getFileSystem(...)");
        return fileSystem;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile
    public String getPath() {
        String path = this.file.getPath();
        Intrinsics.checkNotNullExpressionValue(path, "getPath(...)");
        return path;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile
    public boolean isWritable() {
        return this.file.isWritable();
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile
    public boolean isValid() {
        return this.file.isValid();
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile
    public VirtualFile getParent() {
        return this.parent;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile
    public OutputStream getOutputStream(Object p0, long p1, long p2) throws IOException {
        OutputStream outputStream = this.file.getOutputStream(p0, p1, p2);
        Intrinsics.checkNotNullExpressionValue(outputStream, "getOutputStream(...)");
        return outputStream;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile
    public byte[] contentsToByteArray() throws IOException {
        byte[] bArrContentsToByteArray = this.file.contentsToByteArray();
        Intrinsics.checkNotNullExpressionValue(bArrContentsToByteArray, "contentsToByteArray(...)");
        return bArrContentsToByteArray;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile
    public long getTimeStamp() {
        return this.file.getTimeStamp();
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile
    public long getLength() {
        return this.file.getLength();
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile
    public void refresh(boolean p0, boolean p1, Runnable p2) {
        this.file.refresh(p0, p1, p2);
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile
    public InputStream getInputStream() throws IOException {
        InputStream inputStream = this.file.getInputStream();
        Intrinsics.checkNotNullExpressionValue(inputStream, "getInputStream(...)");
        return inputStream;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile
    public FileType getFileType() {
        JavaClassFileType INSTANCE = JavaClassFileType.INSTANCE;
        Intrinsics.checkNotNullExpressionValue(INSTANCE, "INSTANCE");
        return INSTANCE;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile
    public long getModificationStamp() {
        return this.file.getModificationStamp();
    }
}

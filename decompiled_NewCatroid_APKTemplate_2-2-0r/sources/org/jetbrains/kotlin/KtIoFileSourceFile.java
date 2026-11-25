package org.jetbrains.kotlin;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.openapi.util.io.FileUtilRt;

/* JADX INFO: compiled from: KtSourceFile.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\r\u001a\u00020\u000eH\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0014\u0010\u0007\u001a\u00020\b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\nR\u0014\u0010\u000b\u001a\u00020\b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\n¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/KtIoFileSourceFile;", "Lorg/jetbrains/kotlin/KtSourceFile;", "file", "Ljava/io/File;", "(Ljava/io/File;)V", "getFile", "()Ljava/io/File;", "name", "", "getName", "()Ljava/lang/String;", "path", "getPath", "getContentsAsStream", "Ljava/io/InputStream;", "frontend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KtIoFileSourceFile implements KtSourceFile {
    private final File file;

    public KtIoFileSourceFile(File file) {
        Intrinsics.checkNotNullParameter(file, "file");
        this.file = file;
    }

    public final File getFile() {
        return this.file;
    }

    @Override // org.jetbrains.kotlin.KtSourceFile
    public String getName() {
        String name2 = this.file.getName();
        Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
        return name2;
    }

    @Override // org.jetbrains.kotlin.KtSourceFile
    public String getPath() {
        String systemIndependentName = FileUtilRt.toSystemIndependentName(this.file.getPath());
        Intrinsics.checkNotNullExpressionValue(systemIndependentName, "toSystemIndependentName(...)");
        return systemIndependentName;
    }

    @Override // org.jetbrains.kotlin.KtSourceFile
    public InputStream getContentsAsStream() {
        return new FileInputStream(this.file);
    }
}

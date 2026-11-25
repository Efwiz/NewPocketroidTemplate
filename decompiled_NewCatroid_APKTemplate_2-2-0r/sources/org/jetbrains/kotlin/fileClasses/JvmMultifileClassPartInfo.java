package org.jetbrains.kotlin.fileClasses;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.name.FqName;

/* JADX INFO: compiled from: JvmFileClassInfo.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003¢\u0006\u0002\u0010\u0005R\u0014\u0010\u0004\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0007R\u0014\u0010\t\u001a\u00020\n8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\fR\u0014\u0010\r\u001a\u00020\n8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000e\u0010\f¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/fileClasses/JvmMultifileClassPartInfo;", "Lorg/jetbrains/kotlin/fileClasses/JvmFileClassInfo;", "fileClassFqName", "Lorg/jetbrains/kotlin/name/FqName;", "facadeClassFqName", "(Lorg/jetbrains/kotlin/name/FqName;Lorg/jetbrains/kotlin/name/FqName;)V", "getFacadeClassFqName", "()Lorg/jetbrains/kotlin/name/FqName;", "getFileClassFqName", "withJvmMultifileClass", "", "getWithJvmMultifileClass", "()Z", "withJvmName", "getWithJvmName", "frontend.common.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JvmMultifileClassPartInfo implements JvmFileClassInfo {
    private final FqName facadeClassFqName;
    private final FqName fileClassFqName;

    @Override // org.jetbrains.kotlin.fileClasses.JvmFileClassInfo
    public boolean getWithJvmMultifileClass() {
        return true;
    }

    @Override // org.jetbrains.kotlin.fileClasses.JvmFileClassInfo
    public boolean getWithJvmName() {
        return true;
    }

    public JvmMultifileClassPartInfo(FqName fileClassFqName, FqName facadeClassFqName) {
        Intrinsics.checkNotNullParameter(fileClassFqName, "fileClassFqName");
        Intrinsics.checkNotNullParameter(facadeClassFqName, "facadeClassFqName");
        this.fileClassFqName = fileClassFqName;
        this.facadeClassFqName = facadeClassFqName;
    }

    @Override // org.jetbrains.kotlin.fileClasses.JvmFileClassInfo
    public FqName getFileClassFqName() {
        return this.fileClassFqName;
    }

    @Override // org.jetbrains.kotlin.fileClasses.JvmFileClassInfo
    public FqName getFacadeClassFqName() {
        return this.facadeClassFqName;
    }
}

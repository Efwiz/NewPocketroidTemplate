package org.jetbrains.kotlin.fileClasses;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.name.FqName;

/* JADX INFO: compiled from: JvmFileClassInfo.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\n\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006R\u0014\u0010\u0007\u001a\u00020\u00038VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\tR\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\tR\u0014\u0010\u000b\u001a\u00020\u00058VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\rR\u0014\u0010\u0004\u001a\u00020\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\r¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/fileClasses/JvmSimpleFileClassInfo;", "Lorg/jetbrains/kotlin/fileClasses/JvmFileClassInfo;", "fileClassFqName", "Lorg/jetbrains/kotlin/name/FqName;", "withJvmName", "", "(Lorg/jetbrains/kotlin/name/FqName;Z)V", "facadeClassFqName", "getFacadeClassFqName", "()Lorg/jetbrains/kotlin/name/FqName;", "getFileClassFqName", "withJvmMultifileClass", "getWithJvmMultifileClass", "()Z", "getWithJvmName", "frontend.common.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JvmSimpleFileClassInfo implements JvmFileClassInfo {
    private final FqName fileClassFqName;
    private final boolean withJvmName;

    @Override // org.jetbrains.kotlin.fileClasses.JvmFileClassInfo
    public boolean getWithJvmMultifileClass() {
        return false;
    }

    public JvmSimpleFileClassInfo(FqName fileClassFqName, boolean z) {
        Intrinsics.checkNotNullParameter(fileClassFqName, "fileClassFqName");
        this.fileClassFqName = fileClassFqName;
        this.withJvmName = z;
    }

    @Override // org.jetbrains.kotlin.fileClasses.JvmFileClassInfo
    public FqName getFileClassFqName() {
        return this.fileClassFqName;
    }

    @Override // org.jetbrains.kotlin.fileClasses.JvmFileClassInfo
    public boolean getWithJvmName() {
        return this.withJvmName;
    }

    @Override // org.jetbrains.kotlin.fileClasses.JvmFileClassInfo
    public FqName getFacadeClassFqName() {
        return getFileClassFqName();
    }
}

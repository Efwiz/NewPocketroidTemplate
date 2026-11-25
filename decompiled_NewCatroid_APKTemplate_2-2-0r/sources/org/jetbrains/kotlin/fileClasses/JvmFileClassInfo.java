package org.jetbrains.kotlin.fileClasses;

import kotlin.Metadata;
import org.jetbrains.kotlin.name.FqName;

/* JADX INFO: compiled from: JvmFileClassInfo.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0005\bf\u0018\u00002\u00020\u0001R\u0012\u0010\u0002\u001a\u00020\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005R\u0012\u0010\u0006\u001a\u00020\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\u0005R\u0012\u0010\b\u001a\u00020\tX¦\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\u000bR\u0012\u0010\f\u001a\u00020\tX¦\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\u000bø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u000eÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/fileClasses/JvmFileClassInfo;", "", "facadeClassFqName", "Lorg/jetbrains/kotlin/name/FqName;", "getFacadeClassFqName", "()Lorg/jetbrains/kotlin/name/FqName;", "fileClassFqName", "getFileClassFqName", "withJvmMultifileClass", "", "getWithJvmMultifileClass", "()Z", "withJvmName", "getWithJvmName", "frontend.common.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface JvmFileClassInfo {
    FqName getFacadeClassFqName();

    FqName getFileClassFqName();

    boolean getWithJvmMultifileClass();

    boolean getWithJvmName();
}

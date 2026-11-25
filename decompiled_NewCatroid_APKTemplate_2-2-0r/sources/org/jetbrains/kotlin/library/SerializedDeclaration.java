package org.jetbrains.kotlin.library;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: KotlinLibraryWriter.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0012\n\u0002\b\n\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bR\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0011\u0010\u000f\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u000e¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/library/SerializedDeclaration;", "", "id", "", "declarationName", "", "bytes", "", "(ILjava/lang/String;[B)V", "getBytes", "()[B", "getDeclarationName", "()Ljava/lang/String;", "getId", "()I", "size", "getSize", "kotlin-util-klib"}, k = 1, mv = {1, 5, 1}, xi = 48)
public final class SerializedDeclaration {
    private final byte[] bytes;
    private final String declarationName;
    private final int id;
    private final int size;

    public SerializedDeclaration(int i, String declarationName, byte[] bytes) {
        Intrinsics.checkNotNullParameter(declarationName, "declarationName");
        Intrinsics.checkNotNullParameter(bytes, "bytes");
        this.id = i;
        this.declarationName = declarationName;
        this.bytes = bytes;
        this.size = bytes.length;
    }

    public final byte[] getBytes() {
        return this.bytes;
    }

    public final String getDeclarationName() {
        return this.declarationName;
    }

    public final int getId() {
        return this.id;
    }

    public final int getSize() {
        return this.size;
    }
}

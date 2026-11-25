package org.jetbrains.kotlin.library;

import com.reandroid.arsc.container.SpecTypePair;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: KotlinLibraryWriter.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0012\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0016\u0018\u00002\u00020\u0001BY\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005\u0012\u0006\u0010\u0007\u001a\u00020\u0003\u0012\u0006\u0010\b\u001a\u00020\u0003\u0012\u0006\u0010\t\u001a\u00020\u0003\u0012\u0006\u0010\n\u001a\u00020\u0003\u0012\u0006\u0010\u000b\u001a\u00020\u0003\u0012\b\u0010\f\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\r\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\u000eR\u0013\u0010\r\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0011\u0010\n\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0010R\u0013\u0010\f\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0010R\u0011\u0010\u000b\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0010R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0010R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u0011\u0010\u0006\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0016R\u0011\u0010\b\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0010R\u0011\u0010\t\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u0010R\u0011\u0010\u0007\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u0010¨\u0006\u001b"}, d2 = {"Lorg/jetbrains/kotlin/library/SerializedIrFile;", "", "fileData", "", "fqName", "", "path", SpecTypePair.NAME_types, "signatures", "strings", "bodies", "declarations", "debugInfo", "backendSpecificMetadata", "([BLjava/lang/String;Ljava/lang/String;[B[B[B[B[B[B[B)V", "getBackendSpecificMetadata", "()[B", "getBodies", "getDebugInfo", "getDeclarations", "getFileData", "getFqName", "()Ljava/lang/String;", "getPath", "getSignatures", "getStrings", "getTypes", "kotlin-util-klib"}, k = 1, mv = {1, 5, 1}, xi = 48)
public final class SerializedIrFile {
    private final byte[] backendSpecificMetadata;
    private final byte[] bodies;
    private final byte[] debugInfo;
    private final byte[] declarations;
    private final byte[] fileData;
    private final String fqName;
    private final String path;
    private final byte[] signatures;
    private final byte[] strings;
    private final byte[] types;

    public SerializedIrFile(byte[] fileData, String fqName, String path, byte[] types, byte[] signatures, byte[] strings, byte[] bodies, byte[] declarations, byte[] bArr, byte[] bArr2) {
        Intrinsics.checkNotNullParameter(fileData, "fileData");
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        Intrinsics.checkNotNullParameter(path, "path");
        Intrinsics.checkNotNullParameter(types, "types");
        Intrinsics.checkNotNullParameter(signatures, "signatures");
        Intrinsics.checkNotNullParameter(strings, "strings");
        Intrinsics.checkNotNullParameter(bodies, "bodies");
        Intrinsics.checkNotNullParameter(declarations, "declarations");
        this.fileData = fileData;
        this.fqName = fqName;
        this.path = path;
        this.types = types;
        this.signatures = signatures;
        this.strings = strings;
        this.bodies = bodies;
        this.declarations = declarations;
        this.debugInfo = bArr;
        this.backendSpecificMetadata = bArr2;
    }

    public final byte[] getFileData() {
        return this.fileData;
    }

    public final String getFqName() {
        return this.fqName;
    }

    public final String getPath() {
        return this.path;
    }

    public final byte[] getTypes() {
        return this.types;
    }

    public final byte[] getSignatures() {
        return this.signatures;
    }

    public final byte[] getStrings() {
        return this.strings;
    }

    public final byte[] getBodies() {
        return this.bodies;
    }

    public final byte[] getDeclarations() {
        return this.declarations;
    }

    public final byte[] getDebugInfo() {
        return this.debugInfo;
    }

    public final byte[] getBackendSpecificMetadata() {
        return this.backendSpecificMetadata;
    }
}

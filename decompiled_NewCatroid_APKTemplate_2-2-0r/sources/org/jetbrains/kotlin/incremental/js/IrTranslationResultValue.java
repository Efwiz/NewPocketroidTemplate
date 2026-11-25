package org.jetbrains.kotlin.incremental.js;

import com.reandroid.arsc.container.SpecTypePair;
import java.util.Arrays;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: TranslationResultValue.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0012\n\u0002\b\u001e\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001BO\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0003\u0012\u0006\u0010\u0007\u001a\u00020\u0003\u0012\u0006\u0010\b\u001a\u00020\u0003\u0012\u0006\u0010\t\u001a\u00020\u0003\u0012\u0006\u0010\n\u001a\u00020\u0003\u0012\b\u0010\u000b\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\fJ\t\u0010\u0017\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0018\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0019\u001a\u00020\u0003HÆ\u0003J\t\u0010\u001a\u001a\u00020\u0003HÆ\u0003J\t\u0010\u001b\u001a\u00020\u0003HÆ\u0003J\t\u0010\u001c\u001a\u00020\u0003HÆ\u0003J\t\u0010\u001d\u001a\u00020\u0003HÆ\u0003J\t\u0010\u001e\u001a\u00020\u0003HÆ\u0003J\u000b\u0010\u001f\u001a\u0004\u0018\u00010\u0003HÆ\u0003Je\u0010 \u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u00032\b\b\u0002\u0010\u0007\u001a\u00020\u00032\b\b\u0002\u0010\b\u001a\u00020\u00032\b\b\u0002\u0010\t\u001a\u00020\u00032\b\b\u0002\u0010\n\u001a\u00020\u00032\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\u0003HÆ\u0001J\u0013\u0010!\u001a\u00020\"2\b\u0010#\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010$\u001a\u00020%HÖ\u0001J\t\u0010&\u001a\u00020'HÖ\u0001R\u0011\u0010\b\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0013\u0010\u000b\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u000eR\u0011\u0010\u0007\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u000eR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u000eR\u0011\u0010\n\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u000eR\u0011\u0010\t\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u000eR\u0011\u0010\u0005\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u000eR\u0011\u0010\u0006\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u000eR\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u000e¨\u0006("}, d2 = {"Lorg/jetbrains/kotlin/incremental/js/IrTranslationResultValue;", "", "fileData", "", SpecTypePair.NAME_types, "signatures", "strings", "declarations", "bodies", "fqn", "fileMetadata", "debugInfo", "([B[B[B[B[B[B[B[B[B)V", "getBodies", "()[B", "getDebugInfo", "getDeclarations", "getFileData", "getFileMetadata", "getFqn", "getSignatures", "getStrings", "getTypes", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "", "js.config"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class IrTranslationResultValue {
    private final byte[] bodies;
    private final byte[] debugInfo;
    private final byte[] declarations;
    private final byte[] fileData;
    private final byte[] fileMetadata;
    private final byte[] fqn;
    private final byte[] signatures;
    private final byte[] strings;
    private final byte[] types;

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final byte[] getFileData() {
        return this.fileData;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final byte[] getTypes() {
        return this.types;
    }

    /* JADX INFO: renamed from: component3, reason: from getter */
    public final byte[] getSignatures() {
        return this.signatures;
    }

    /* JADX INFO: renamed from: component4, reason: from getter */
    public final byte[] getStrings() {
        return this.strings;
    }

    /* JADX INFO: renamed from: component5, reason: from getter */
    public final byte[] getDeclarations() {
        return this.declarations;
    }

    /* JADX INFO: renamed from: component6, reason: from getter */
    public final byte[] getBodies() {
        return this.bodies;
    }

    /* JADX INFO: renamed from: component7, reason: from getter */
    public final byte[] getFqn() {
        return this.fqn;
    }

    /* JADX INFO: renamed from: component8, reason: from getter */
    public final byte[] getFileMetadata() {
        return this.fileMetadata;
    }

    /* JADX INFO: renamed from: component9, reason: from getter */
    public final byte[] getDebugInfo() {
        return this.debugInfo;
    }

    public final IrTranslationResultValue copy(byte[] fileData, byte[] types, byte[] signatures, byte[] strings, byte[] declarations, byte[] bodies, byte[] fqn, byte[] fileMetadata, byte[] debugInfo) {
        Intrinsics.checkNotNullParameter(fileData, "fileData");
        Intrinsics.checkNotNullParameter(types, "types");
        Intrinsics.checkNotNullParameter(signatures, "signatures");
        Intrinsics.checkNotNullParameter(strings, "strings");
        Intrinsics.checkNotNullParameter(declarations, "declarations");
        Intrinsics.checkNotNullParameter(bodies, "bodies");
        Intrinsics.checkNotNullParameter(fqn, "fqn");
        Intrinsics.checkNotNullParameter(fileMetadata, "fileMetadata");
        return new IrTranslationResultValue(fileData, types, signatures, strings, declarations, bodies, fqn, fileMetadata, debugInfo);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof IrTranslationResultValue)) {
            return false;
        }
        IrTranslationResultValue irTranslationResultValue = (IrTranslationResultValue) other;
        return Intrinsics.areEqual(this.fileData, irTranslationResultValue.fileData) && Intrinsics.areEqual(this.types, irTranslationResultValue.types) && Intrinsics.areEqual(this.signatures, irTranslationResultValue.signatures) && Intrinsics.areEqual(this.strings, irTranslationResultValue.strings) && Intrinsics.areEqual(this.declarations, irTranslationResultValue.declarations) && Intrinsics.areEqual(this.bodies, irTranslationResultValue.bodies) && Intrinsics.areEqual(this.fqn, irTranslationResultValue.fqn) && Intrinsics.areEqual(this.fileMetadata, irTranslationResultValue.fileMetadata) && Intrinsics.areEqual(this.debugInfo, irTranslationResultValue.debugInfo);
    }

    public int hashCode() {
        int iHashCode = ((((((((((((((Arrays.hashCode(this.fileData) * 31) + Arrays.hashCode(this.types)) * 31) + Arrays.hashCode(this.signatures)) * 31) + Arrays.hashCode(this.strings)) * 31) + Arrays.hashCode(this.declarations)) * 31) + Arrays.hashCode(this.bodies)) * 31) + Arrays.hashCode(this.fqn)) * 31) + Arrays.hashCode(this.fileMetadata)) * 31;
        byte[] bArr = this.debugInfo;
        return iHashCode + (bArr == null ? 0 : Arrays.hashCode(bArr));
    }

    public String toString() {
        return "IrTranslationResultValue(fileData=" + Arrays.toString(this.fileData) + ", types=" + Arrays.toString(this.types) + ", signatures=" + Arrays.toString(this.signatures) + ", strings=" + Arrays.toString(this.strings) + ", declarations=" + Arrays.toString(this.declarations) + ", bodies=" + Arrays.toString(this.bodies) + ", fqn=" + Arrays.toString(this.fqn) + ", fileMetadata=" + Arrays.toString(this.fileMetadata) + ", debugInfo=" + Arrays.toString(this.debugInfo) + ')';
    }

    public IrTranslationResultValue(byte[] fileData, byte[] types, byte[] signatures, byte[] strings, byte[] declarations, byte[] bodies, byte[] fqn, byte[] fileMetadata, byte[] bArr) {
        Intrinsics.checkNotNullParameter(fileData, "fileData");
        Intrinsics.checkNotNullParameter(types, "types");
        Intrinsics.checkNotNullParameter(signatures, "signatures");
        Intrinsics.checkNotNullParameter(strings, "strings");
        Intrinsics.checkNotNullParameter(declarations, "declarations");
        Intrinsics.checkNotNullParameter(bodies, "bodies");
        Intrinsics.checkNotNullParameter(fqn, "fqn");
        Intrinsics.checkNotNullParameter(fileMetadata, "fileMetadata");
        this.fileData = fileData;
        this.types = types;
        this.signatures = signatures;
        this.strings = strings;
        this.declarations = declarations;
        this.bodies = bodies;
        this.fqn = fqn;
        this.fileMetadata = fileMetadata;
        this.debugInfo = bArr;
    }

    public final byte[] getFileData() {
        return this.fileData;
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

    public final byte[] getDeclarations() {
        return this.declarations;
    }

    public final byte[] getBodies() {
        return this.bodies;
    }

    public final byte[] getFqn() {
        return this.fqn;
    }

    public final byte[] getFileMetadata() {
        return this.fileMetadata;
    }

    public final byte[] getDebugInfo() {
        return this.debugInfo;
    }
}

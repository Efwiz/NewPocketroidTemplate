package org.jetbrains.kotlin.incremental.js;

import java.util.Arrays;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: TranslationResultValue.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0012\n\u0002\b\f\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003¢\u0006\u0002\u0010\u0006J\t\u0010\u000b\u001a\u00020\u0003HÆ\u0003J\t\u0010\f\u001a\u00020\u0003HÆ\u0003J\t\u0010\r\u001a\u00020\u0003HÆ\u0003J'\u0010\u000e\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\u000f\u001a\u00020\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0012\u001a\u00020\u0013HÖ\u0001J\t\u0010\u0014\u001a\u00020\u0015HÖ\u0001R\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0005\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\b¨\u0006\u0016"}, d2 = {"Lorg/jetbrains/kotlin/incremental/js/TranslationResultValue;", "", "metadata", "", "binaryAst", "inlineData", "([B[B[B)V", "getBinaryAst", "()[B", "getInlineData", "getMetadata", "component1", "component2", "component3", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "", "js.config"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class TranslationResultValue {
    private final byte[] binaryAst;
    private final byte[] inlineData;
    private final byte[] metadata;

    public static /* synthetic */ TranslationResultValue copy$default(TranslationResultValue translationResultValue, byte[] bArr, byte[] bArr2, byte[] bArr3, int i, Object obj) {
        if ((i & 1) != 0) {
            bArr = translationResultValue.metadata;
        }
        if ((i & 2) != 0) {
            bArr2 = translationResultValue.binaryAst;
        }
        if ((i & 4) != 0) {
            bArr3 = translationResultValue.inlineData;
        }
        return translationResultValue.copy(bArr, bArr2, bArr3);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final byte[] getMetadata() {
        return this.metadata;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final byte[] getBinaryAst() {
        return this.binaryAst;
    }

    /* JADX INFO: renamed from: component3, reason: from getter */
    public final byte[] getInlineData() {
        return this.inlineData;
    }

    public final TranslationResultValue copy(byte[] metadata, byte[] binaryAst, byte[] inlineData) {
        Intrinsics.checkNotNullParameter(metadata, "metadata");
        Intrinsics.checkNotNullParameter(binaryAst, "binaryAst");
        Intrinsics.checkNotNullParameter(inlineData, "inlineData");
        return new TranslationResultValue(metadata, binaryAst, inlineData);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof TranslationResultValue)) {
            return false;
        }
        TranslationResultValue translationResultValue = (TranslationResultValue) other;
        return Intrinsics.areEqual(this.metadata, translationResultValue.metadata) && Intrinsics.areEqual(this.binaryAst, translationResultValue.binaryAst) && Intrinsics.areEqual(this.inlineData, translationResultValue.inlineData);
    }

    public int hashCode() {
        return (((Arrays.hashCode(this.metadata) * 31) + Arrays.hashCode(this.binaryAst)) * 31) + Arrays.hashCode(this.inlineData);
    }

    public String toString() {
        return "TranslationResultValue(metadata=" + Arrays.toString(this.metadata) + ", binaryAst=" + Arrays.toString(this.binaryAst) + ", inlineData=" + Arrays.toString(this.inlineData) + ')';
    }

    public TranslationResultValue(byte[] metadata, byte[] binaryAst, byte[] inlineData) {
        Intrinsics.checkNotNullParameter(metadata, "metadata");
        Intrinsics.checkNotNullParameter(binaryAst, "binaryAst");
        Intrinsics.checkNotNullParameter(inlineData, "inlineData");
        this.metadata = metadata;
        this.binaryAst = binaryAst;
        this.inlineData = inlineData;
    }

    public final byte[] getBinaryAst() {
        return this.binaryAst;
    }

    public final byte[] getInlineData() {
        return this.inlineData;
    }

    public final byte[] getMetadata() {
        return this.metadata;
    }
}

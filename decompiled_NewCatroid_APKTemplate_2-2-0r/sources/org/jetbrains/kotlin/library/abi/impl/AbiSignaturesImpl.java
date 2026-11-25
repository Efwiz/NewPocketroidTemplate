package org.jetbrains.kotlin.library.abi.impl;

import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.library.abi.AbiSignatureVersion;
import org.jetbrains.kotlin.library.abi.AbiSignatures;
import org.jetbrains.kotlin.library.abi.ExperimentalLibraryAbiReader;

/* JADX INFO: compiled from: LibraryAbiImpl.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u0081\b\u0018\u00002\u00020\u0001B\u0019\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\u0005J\u000b\u0010\u0006\u001a\u0004\u0018\u00010\u0003HÂ\u0003J\u000b\u0010\u0007\u001a\u0004\u0018\u00010\u0003HÂ\u0003J!\u0010\b\u001a\u00020\u00002\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0003HÆ\u0001J\u0013\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\fHÖ\u0003J\u0013\u0010\r\u001a\u0004\u0018\u00010\u00032\u0006\u0010\u000e\u001a\u00020\u000fH\u0096\u0002J\t\u0010\u0010\u001a\u00020\u0011HÖ\u0001J\t\u0010\u0012\u001a\u00020\u0003HÖ\u0001R\u0010\u0010\u0002\u001a\u0004\u0018\u00010\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0004\u001a\u0004\u0018\u00010\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0013"}, d2 = {"Lorg/jetbrains/kotlin/library/abi/impl/AbiSignaturesImpl;", "Lorg/jetbrains/kotlin/library/abi/AbiSignatures;", "signatureV1", "", "signatureV2", "(Ljava/lang/String;Ljava/lang/String;)V", "component1", "component2", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "", "get", "signatureVersion", "Lorg/jetbrains/kotlin/library/abi/AbiSignatureVersion;", "hashCode", "", "toString", "kotlin-util-klib-abi"}, k = 1, mv = {1, 9, 0}, xi = 48)
@ExperimentalLibraryAbiReader
public final /* data */ class AbiSignaturesImpl implements AbiSignatures {
    private final String signatureV1;
    private final String signatureV2;

    /* JADX INFO: compiled from: LibraryAbiImpl.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[AbiSignatureVersions.Supported.values().length];
            try {
                iArr[AbiSignatureVersions.Supported.V1.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[AbiSignatureVersions.Supported.V2.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public static /* synthetic */ AbiSignaturesImpl copy$default(AbiSignaturesImpl abiSignaturesImpl, String str, String str2, int i, Object obj) {
        if ((i & 1) != 0) {
            str = abiSignaturesImpl.signatureV1;
        }
        if ((i & 2) != 0) {
            str2 = abiSignaturesImpl.signatureV2;
        }
        return abiSignaturesImpl.copy(str, str2);
    }

    public final AbiSignaturesImpl copy(String signatureV1, String signatureV2) {
        return new AbiSignaturesImpl(signatureV1, signatureV2);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof AbiSignaturesImpl)) {
            return false;
        }
        AbiSignaturesImpl abiSignaturesImpl = (AbiSignaturesImpl) other;
        return Intrinsics.areEqual(this.signatureV1, abiSignaturesImpl.signatureV1) && Intrinsics.areEqual(this.signatureV2, abiSignaturesImpl.signatureV2);
    }

    public int hashCode() {
        String str = this.signatureV1;
        int iHashCode = (str == null ? 0 : str.hashCode()) * 31;
        String str2 = this.signatureV2;
        return iHashCode + (str2 != null ? str2.hashCode() : 0);
    }

    public String toString() {
        return "AbiSignaturesImpl(signatureV1=" + this.signatureV1 + ", signatureV2=" + this.signatureV2 + ')';
    }

    public AbiSignaturesImpl(String str, String str2) {
        this.signatureV1 = str;
        this.signatureV2 = str2;
    }

    @Override // org.jetbrains.kotlin.library.abi.AbiSignatures
    public String get(AbiSignatureVersion signatureVersion) {
        Intrinsics.checkNotNullParameter(signatureVersion, "signatureVersion");
        if (!(signatureVersion instanceof AbiSignatureVersions.Supported)) {
            throw new IllegalStateException(("Unsupported signature version: " + signatureVersion).toString());
        }
        int i = WhenMappings.$EnumSwitchMapping$0[((AbiSignatureVersions.Supported) signatureVersion).ordinal()];
        if (i == 1) {
            return this.signatureV1;
        }
        if (i == 2) {
            return this.signatureV2;
        }
        throw new NoWhenBranchMatchedException();
    }
}

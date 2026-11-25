package org.jetbrains.kotlin.incremental.storage;

import java.util.Arrays;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: values.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0012\n\u0000\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u000f\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B#\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007¢\u0006\u0002\u0010\tJ\t\u0010\u0010\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0011\u001a\u00020\u0005HÆ\u0003J\u0014\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\b0\u0007HÆ\u0003¢\u0006\u0002\u0010\u000eJ2\u0010\u0013\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\u000e\b\u0002\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007HÆ\u0001¢\u0006\u0002\u0010\u0014J\u0013\u0010\u0015\u001a\u00020\u00032\b\u0010\u0016\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0017\u001a\u00020\u0018HÖ\u0001J\t\u0010\u0019\u001a\u00020\bHÖ\u0001R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0002\u0010\fR\u0019\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007¢\u0006\n\n\u0002\u0010\u000f\u001a\u0004\b\r\u0010\u000e¨\u0006\u001a"}, d2 = {"Lorg/jetbrains/kotlin/incremental/storage/ProtoMapValue;", "", "isPackageFacade", "", "bytes", "", "strings", "", "", "(Z[B[Ljava/lang/String;)V", "getBytes", "()[B", "()Z", "getStrings", "()[Ljava/lang/String;", "[Ljava/lang/String;", "component1", "component2", "component3", ConfigConstants.CONFIG_RENAMELIMIT_COPY, "(Z[B[Ljava/lang/String;)Lorg/jetbrains/kotlin/incremental/storage/ProtoMapValue;", Namer.EQUALS_METHOD_NAME, "other", "hashCode", "", "toString", "kotlin-build-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class ProtoMapValue {
    private final byte[] bytes;
    private final boolean isPackageFacade;
    private final String[] strings;

    public static /* synthetic */ ProtoMapValue copy$default(ProtoMapValue protoMapValue, boolean z, byte[] bArr, String[] strArr, int i, Object obj) {
        if ((i & 1) != 0) {
            z = protoMapValue.isPackageFacade;
        }
        if ((i & 2) != 0) {
            bArr = protoMapValue.bytes;
        }
        if ((i & 4) != 0) {
            strArr = protoMapValue.strings;
        }
        return protoMapValue.copy(z, bArr, strArr);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final boolean getIsPackageFacade() {
        return this.isPackageFacade;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final byte[] getBytes() {
        return this.bytes;
    }

    /* JADX INFO: renamed from: component3, reason: from getter */
    public final String[] getStrings() {
        return this.strings;
    }

    public final ProtoMapValue copy(boolean isPackageFacade, byte[] bytes, String[] strings) {
        Intrinsics.checkNotNullParameter(bytes, "bytes");
        Intrinsics.checkNotNullParameter(strings, "strings");
        return new ProtoMapValue(isPackageFacade, bytes, strings);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ProtoMapValue)) {
            return false;
        }
        ProtoMapValue protoMapValue = (ProtoMapValue) other;
        return this.isPackageFacade == protoMapValue.isPackageFacade && Intrinsics.areEqual(this.bytes, protoMapValue.bytes) && Intrinsics.areEqual(this.strings, protoMapValue.strings);
    }

    public int hashCode() {
        return (((Boolean.hashCode(this.isPackageFacade) * 31) + Arrays.hashCode(this.bytes)) * 31) + Arrays.hashCode(this.strings);
    }

    public String toString() {
        return "ProtoMapValue(isPackageFacade=" + this.isPackageFacade + ", bytes=" + Arrays.toString(this.bytes) + ", strings=" + Arrays.toString(this.strings) + ')';
    }

    public ProtoMapValue(boolean z, byte[] bytes, String[] strings) {
        Intrinsics.checkNotNullParameter(bytes, "bytes");
        Intrinsics.checkNotNullParameter(strings, "strings");
        this.isPackageFacade = z;
        this.bytes = bytes;
        this.strings = strings;
    }

    public final byte[] getBytes() {
        return this.bytes;
    }

    public final String[] getStrings() {
        return this.strings;
    }

    public final boolean isPackageFacade() {
        return this.isPackageFacade;
    }
}

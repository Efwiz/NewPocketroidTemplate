package org.jetbrains.kotlin.codegen;

import com.reandroid.arsc.chunk.OverlayablePolicy;
import com.reandroid.dex.sections.Marker;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: SyntheticInnerClassInfo.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0086\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\t\u0010\u000b\u001a\u00020\u0003HÆ\u0003J\t\u0010\f\u001a\u00020\u0005HÆ\u0003J\u001d\u0010\r\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005HÆ\u0001J\u0013\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0011\u001a\u00020\u0005HÖ\u0001J\t\u0010\u0012\u001a\u00020\u0003HÖ\u0001R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u0013"}, d2 = {"Lorg/jetbrains/kotlin/codegen/SyntheticInnerClassInfo;", "", "internalName", "", OverlayablePolicy.NAME_flags, "", "(Ljava/lang/String;I)V", "getFlags", "()I", "getInternalName", "()Ljava/lang/String;", "component1", "component2", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "toString", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class SyntheticInnerClassInfo {
    private final int flags;
    private final String internalName;

    public static /* synthetic */ SyntheticInnerClassInfo copy$default(SyntheticInnerClassInfo syntheticInnerClassInfo, String str, int i, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            str = syntheticInnerClassInfo.internalName;
        }
        if ((i2 & 2) != 0) {
            i = syntheticInnerClassInfo.flags;
        }
        return syntheticInnerClassInfo.copy(str, i);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final String getInternalName() {
        return this.internalName;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final int getFlags() {
        return this.flags;
    }

    public final SyntheticInnerClassInfo copy(String internalName, int flags) {
        Intrinsics.checkNotNullParameter(internalName, "internalName");
        return new SyntheticInnerClassInfo(internalName, flags);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof SyntheticInnerClassInfo)) {
            return false;
        }
        SyntheticInnerClassInfo syntheticInnerClassInfo = (SyntheticInnerClassInfo) other;
        return Intrinsics.areEqual(this.internalName, syntheticInnerClassInfo.internalName) && this.flags == syntheticInnerClassInfo.flags;
    }

    public int hashCode() {
        return (this.internalName.hashCode() * 31) + Integer.hashCode(this.flags);
    }

    public String toString() {
        return "SyntheticInnerClassInfo(internalName=" + this.internalName + ", flags=" + this.flags + ')';
    }

    public SyntheticInnerClassInfo(String internalName, int i) {
        Intrinsics.checkNotNullParameter(internalName, "internalName");
        this.internalName = internalName;
        this.flags = i;
    }

    public final int getFlags() {
        return this.flags;
    }

    public final String getInternalName() {
        return this.internalName;
    }
}

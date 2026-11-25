package org.jetbrains.kotlin.backend.common.lower;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: SpecialBridgeMethods.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\u0019\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003¢\u0006\u0002\u0010\u0005R\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0004\u0010\u0006R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\u0006¨\u0006\b"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/lower/BuiltInWithDifferentJvmName;", "", "needsGenericSignature", "", "isOverriding", "(ZZ)V", "()Z", "getNeedsGenericSignature", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class BuiltInWithDifferentJvmName {
    private final boolean isOverriding;
    private final boolean needsGenericSignature;

    /* JADX WARN: Illegal instructions before constructor call */
    public BuiltInWithDifferentJvmName() {
        boolean z = false;
        this(z, z, 3, null);
    }

    public BuiltInWithDifferentJvmName(boolean z, boolean z2) {
        this.needsGenericSignature = z;
        this.isOverriding = z2;
    }

    public /* synthetic */ BuiltInWithDifferentJvmName(boolean z, boolean z2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? false : z, (i & 2) != 0 ? true : z2);
    }

    public final boolean getNeedsGenericSignature() {
        return this.needsGenericSignature;
    }

    /* JADX INFO: renamed from: isOverriding, reason: from getter */
    public final boolean getIsOverriding() {
        return this.isOverriding;
    }
}

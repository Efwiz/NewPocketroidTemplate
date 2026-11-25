package com.android.tools.r8.internal;

/* JADX INFO: renamed from: com.android.tools.r8.internal.ui, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C3152ui extends AbstractC3305wi {
    public C3152ui(int i) {
        super(i, 1, 0);
    }

    public final int a(Boolean bool) {
        if (bool.booleanValue()) {
            return 1 << this.a;
        }
        return 0;
    }

    @Override // com.android.tools.r8.internal.AbstractC3305wi
    public final /* bridge */ /* synthetic */ Object a(int i) {
        throw null;
    }

    public final Boolean b(int i) {
        Boolean boolValueOf = Boolean.valueOf((i & (1 << this.a)) != 0);
        if (boolValueOf != null) {
            return boolValueOf;
        }
        throw new IllegalStateException(String.format("@NotNull method %s.%s must not return null", "kotlinx/metadata/internal/metadata/deserialization/Flags$BooleanFlagField", "get"));
    }
}

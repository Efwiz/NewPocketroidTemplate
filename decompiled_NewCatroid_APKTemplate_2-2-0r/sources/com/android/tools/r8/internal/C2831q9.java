package com.android.tools.r8.internal;

/* JADX INFO: renamed from: com.android.tools.r8.internal.q9, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2831q9 extends AbstractC2509m2 {
    private final boolean b;
    private boolean c;

    C2831q9(AbstractC2509m2 abstractC2509m2, boolean z) {
        super(589824, abstractC2509m2);
        this.b = z;
    }

    @Override // com.android.tools.r8.internal.AbstractC2509m2
    public final void a(String str, Object obj) {
        if (this.c) {
            throw new IllegalStateException("Cannot call a visit method after visitEnd has been called");
        }
        if (this.b && str == null) {
            throw new IllegalArgumentException("Annotation value name must not be null");
        }
        if (!(obj instanceof Byte) && !(obj instanceof Boolean) && !(obj instanceof Character) && !(obj instanceof Short) && !(obj instanceof Integer) && !(obj instanceof Long) && !(obj instanceof Float) && !(obj instanceof Double) && !(obj instanceof String) && !(obj instanceof CZ) && !(obj instanceof byte[]) && !(obj instanceof boolean[]) && !(obj instanceof char[]) && !(obj instanceof short[]) && !(obj instanceof int[]) && !(obj instanceof long[]) && !(obj instanceof float[]) && !(obj instanceof double[])) {
            throw new IllegalArgumentException("Invalid annotation value");
        }
        if ((obj instanceof CZ) && ((CZ) obj).e() == 11) {
            throw new IllegalArgumentException("Invalid annotation value");
        }
        super.a(str, obj);
    }

    @Override // com.android.tools.r8.internal.AbstractC2509m2
    public final AbstractC2509m2 a(String str, String str2) {
        if (!this.c) {
            if (this.b && str == null) {
                throw new IllegalArgumentException("Annotation value name must not be null");
            }
            B9.d(49, str2);
            return new C2831q9(super.a(str, str2), true);
        }
        throw new IllegalStateException("Cannot call a visit method after visitEnd has been called");
    }

    @Override // com.android.tools.r8.internal.AbstractC2509m2
    public final AbstractC2509m2 a(String str) {
        if (!this.c) {
            if (this.b && str == null) {
                throw new IllegalArgumentException("Annotation value name must not be null");
            }
            return new C2831q9(super.a(str), false);
        }
        throw new IllegalStateException("Cannot call a visit method after visitEnd has been called");
    }

    @Override // com.android.tools.r8.internal.AbstractC2509m2
    public final void a() {
        if (!this.c) {
            this.c = true;
            super.a();
            return;
        }
        throw new IllegalStateException("Cannot call a visit method after visitEnd has been called");
    }

    @Override // com.android.tools.r8.internal.AbstractC2509m2
    public final void a(String str, String str2, String str3) {
        if (!this.c) {
            if (this.b && str == null) {
                throw new IllegalArgumentException("Annotation value name must not be null");
            }
            B9.d(49, str2);
            if (str3 != null) {
                super.a(str, str2, str3);
                return;
            }
            throw new IllegalArgumentException("Invalid enum value");
        }
        throw new IllegalStateException("Cannot call a visit method after visitEnd has been called");
    }
}

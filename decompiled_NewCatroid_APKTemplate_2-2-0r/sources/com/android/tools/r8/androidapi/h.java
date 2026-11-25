package com.android.tools.r8.androidapi;

import com.android.tools.r8.utils.AbstractC3938n2;
import com.android.tools.r8.utils.EnumC3891f;
import com.android.tools.r8.utils.structural.k;
import java.util.Objects;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public interface h extends k<h> {

    /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
    public static class a implements h {
        private final EnumC3891f a;
        static final /* synthetic */ boolean d = true;
        private static final a b = new a(EnumC3891f.ANDROID_PLATFORM);
        private static final a c = new a(EnumC3891f.MASTER);

        a(EnumC3891f enumC3891f) {
            this.a = enumC3891f;
        }

        public EnumC3891f a() {
            return this.a;
        }

        @Override // com.android.tools.r8.androidapi.h
        public final AbstractC3938n2 b(EnumC3891f enumC3891f) {
            return AbstractC3938n2.a(this.a.d(enumC3891f));
        }

        @Override // com.android.tools.r8.androidapi.h
        public final a b0() {
            return this;
        }

        @Override // com.android.tools.r8.androidapi.h
        public final AbstractC3938n2 d(h hVar) {
            if (hVar.y()) {
                return a(hVar.b0().a());
            }
            if (d || hVar.H()) {
                return AbstractC3938n2.c;
            }
            throw new AssertionError("Cannot compute relationship for not set");
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof a) && this.a == ((a) obj).a;
        }

        public final int hashCode() {
            return Objects.hash(this.a);
        }

        public final String toString() {
            return this.a.toString();
        }

        @Override // com.android.tools.r8.androidapi.h
        public final boolean y() {
            return true;
        }

        @Override // com.android.tools.r8.androidapi.h
        public final AbstractC3938n2 a(EnumC3891f enumC3891f) {
            return AbstractC3938n2.a(this.a.b(enumC3891f));
        }
    }

    static {
        boolean z = g.a;
    }

    static a S() {
        return a.b;
    }

    static j f() {
        return j.a;
    }

    static a n() {
        return a.c;
    }

    static i w() {
        return i.a;
    }

    default boolean H() {
        return false;
    }

    default boolean J() {
        return false;
    }

    AbstractC3938n2 a(EnumC3891f enumC3891f);

    default boolean a(h hVar) {
        boolean z = g.a;
        if (!z && (J() || hVar.J())) {
            throw new AssertionError("Cannot compute relationship for not set");
        }
        if (hVar.H()) {
            return false;
        }
        if (H()) {
            return true;
        }
        if (z || (y() && hVar.y())) {
            return b0().a().d(hVar.b0().a());
        }
        throw new AssertionError();
    }

    AbstractC3938n2 b(EnumC3891f enumC3891f);

    @Override // com.android.tools.r8.utils.structural.k
    /* JADX INFO: renamed from: b, reason: merged with bridge method [inline-methods] */
    default boolean isEqualTo(h hVar) {
        return equals(hVar);
    }

    default a b0() {
        return null;
    }

    default boolean c(h hVar) {
        if (g.a || !(J() || hVar.J())) {
            return hVar.equals(this) || a(hVar);
        }
        throw new AssertionError("Cannot compute relationship for not set");
    }

    AbstractC3938n2 d(h hVar);

    default h e(h hVar) {
        return c(hVar) ? this : hVar;
    }

    default boolean y() {
        return false;
    }
}

package com.android.tools.r8.utils;

import com.android.tools.r8.BaseCompilerCommand;
import com.android.tools.r8.ClassFileConsumer;
import com.android.tools.r8.DexFilePerClassFileConsumer;
import com.android.tools.r8.DexIndexedConsumer;
import com.android.tools.r8.ProgramConsumer;
import com.android.tools.r8.naming.C3640k0;
import com.android.tools.r8.naming.C3642l0;

/* JADX INFO: renamed from: com.android.tools.r8.utils.y, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes8.dex */
public class C3989y {
    static final /* synthetic */ boolean e = true;
    private final C3945p.a a = C3945p.b();
    private boolean b = false;
    private ProgramConsumer c = null;
    private C3642l0 d = null;

    public C3989y() {
    }

    public ProgramConsumer a(ProgramConsumer programConsumer) {
        boolean z = e;
        if (!z && this.c != null) {
            throw new AssertionError();
        }
        if (programConsumer instanceof ClassFileConsumer) {
            a((ClassFileConsumer) programConsumer);
        } else if (programConsumer instanceof DexIndexedConsumer) {
            a((DexIndexedConsumer) programConsumer);
        } else if (programConsumer instanceof DexFilePerClassFileConsumer) {
            DexFilePerClassFileConsumer dexFilePerClassFileConsumer = (DexFilePerClassFileConsumer) programConsumer;
            if (!z && this.c != null) {
                throw new AssertionError();
            }
            this.c = new C3969u(this, dexFilePerClassFileConsumer, dexFilePerClassFileConsumer);
        } else {
            if (!z && programConsumer != null) {
                throw new AssertionError();
            }
            a((DexIndexedConsumer) null);
        }
        if (z || this.c != null) {
            return this.c;
        }
        throw new AssertionError();
    }

    public C3989y(BaseCompilerCommand.Builder builder) {
        builder.setProgramConsumer(a(builder.getProgramConsumer()));
    }

    public C3989y(A1 a1) {
        a1.f = a(a1.f);
        a1.x1 = a(a1.e, a1.x1);
    }

    public final com.android.tools.r8.T a(O2 o2, com.android.tools.r8.T t) {
        if (!e && this.d != null) {
            throw new AssertionError();
        }
        if (t != null) {
            C3640k0 c3640k0A = new C3640k0().a(t);
            int i = com.android.tools.r8.naming.I0.d;
            this.d = c3640k0A.a(new com.android.tools.r8.naming.H0().a(new C3950q(this)).a(o2).a()).a();
        }
        return this.d;
    }

    public DexIndexedConsumer a(DexIndexedConsumer dexIndexedConsumer) {
        if (!e && this.c != null) {
            throw new AssertionError();
        }
        C3961s c3961s = new C3961s(this, dexIndexedConsumer, dexIndexedConsumer);
        this.c = c3961s;
        return c3961s;
    }

    public ClassFileConsumer a(ClassFileConsumer classFileConsumer) {
        if (!e && this.c != null) {
            throw new AssertionError();
        }
        C3980w c3980w = new C3980w(this, classFileConsumer, classFileConsumer);
        this.c = c3980w;
        return c3980w;
    }

    public C3945p a() {
        if (e || this.b) {
            return this.a.a();
        }
        throw new AssertionError();
    }
}

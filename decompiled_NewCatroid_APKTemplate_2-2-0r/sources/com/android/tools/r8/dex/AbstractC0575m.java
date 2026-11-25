package com.android.tools.r8.dex;

import com.android.tools.r8.ProgramResource;
import com.android.tools.r8.origin.Origin;
import com.android.tools.r8.utils.Q1;
import com.android.tools.r8.utils.W2;

/* JADX INFO: renamed from: com.android.tools.r8.dex.m, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC0575m {
    static final /* synthetic */ boolean c = true;
    protected final Origin a;
    protected final C0581s b;

    protected AbstractC0575m(ProgramResource programResource) {
        this(programResource.getOrigin(), W2.a(programResource.getByteStream()));
    }

    final int a(int i) {
        int iB = this.b.b(i);
        if (c || iB >= 0) {
            return iB;
        }
        throw new AssertionError();
    }

    void b(int i) {
        this.b.c(i);
    }

    public final int c() {
        return this.b.f();
    }

    public final Origin d() {
        return this.a;
    }

    final int e() {
        int iF = this.b.f();
        if (c || iF >= 0) {
            return iF;
        }
        throw new AssertionError();
    }

    final int f() {
        return Q1.b(this);
    }

    final int g() {
        return Q1.b(this) - 1;
    }

    final int h() {
        int iG = this.b.g() & 65535;
        if (c || iG >= 0) {
            return iG;
        }
        throw new AssertionError();
    }

    final int i() {
        return this.b.c().position();
    }

    protected AbstractC0575m(Origin origin, byte[] bArr) {
        if (!c && origin == null) {
            throw new AssertionError();
        }
        this.a = origin;
        this.b = C0581s.b(bArr);
    }

    public final byte b() {
        return this.b.c().get();
    }

    final int a() {
        return this.b.d();
    }
}

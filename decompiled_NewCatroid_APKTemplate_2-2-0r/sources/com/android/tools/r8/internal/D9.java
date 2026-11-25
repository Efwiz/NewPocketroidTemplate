package com.android.tools.r8.internal;

import org.catrobat.catroid.common.Constants;
import org.jetbrains.kotlin.com.intellij.psi.PsiJavaModule;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class D9 extends AbstractC3116uF {
    private final boolean b;
    private final C9 c;
    private final C9 d;
    private final C9 e;
    private final C9 f;
    private final C9 g;
    int h;
    private boolean i;

    protected D9(int i, AbstractC3116uF abstractC3116uF, boolean z) {
        super(i, abstractC3116uF);
        this.c = new C9("Modules requires");
        this.d = new C9("Module exports");
        this.e = new C9("Module opens");
        this.f = new C9("Module uses");
        this.g = new C9("Module provides");
        this.b = z;
    }

    @Override // com.android.tools.r8.internal.AbstractC3116uF
    public final void a(String str) {
        B9.a(53, str, "module main class");
        super.a(str);
    }

    @Override // com.android.tools.r8.internal.AbstractC3116uF
    public final void b(String str) {
        B9.a(53, str, "module package");
        super.b(str);
    }

    @Override // com.android.tools.r8.internal.AbstractC3116uF
    public final void c(String str) {
        b();
        B9.a(53, str, "service");
        this.f.a(str);
        super.c(str);
    }

    @Override // com.android.tools.r8.internal.AbstractC3116uF
    public final void a(int i, String str, String str2) {
        b();
        AbstractC3187v9.b(53, str, "required module");
        this.c.a(str);
        AbstractC3187v9.a(i, 36960);
        if (this.h >= 54 && str.equals(PsiJavaModule.JAVA_BASE) && (i & 96) != 0) {
            throw new IllegalArgumentException("Invalid access flags: " + i + " java.base can not be declared ACC_TRANSITIVE or ACC_STATIC_PHASE");
        }
        super.a(i, str, str2);
    }

    @Override // com.android.tools.r8.internal.AbstractC3116uF
    public final void b(String str, int i, String... strArr) {
        b();
        if (!this.b) {
            B9.a(53, str, "package name");
            this.e.a(str);
            AbstractC3187v9.a(i, 36864);
            if (strArr != null) {
                for (String str2 : strArr) {
                    AbstractC3187v9.b(53, str2, "module open to");
                }
            }
            super.b(str, i, strArr);
            return;
        }
        throw new UnsupportedOperationException("An open module can not use open directive");
    }

    private void b() {
        if (this.i) {
            throw new IllegalStateException("Cannot call a visit method after visitEnd has been called");
        }
    }

    @Override // com.android.tools.r8.internal.AbstractC3116uF
    public final void a(String str, int i, String... strArr) {
        b();
        B9.a(53, str, "package name");
        this.d.a(str);
        AbstractC3187v9.a(i, 36864);
        if (strArr != null) {
            for (String str2 : strArr) {
                AbstractC3187v9.b(53, str2, "module export to");
            }
        }
        super.a(str, i, strArr);
    }

    @Override // com.android.tools.r8.internal.AbstractC3116uF
    public final void a(String str, String... strArr) {
        b();
        B9.a(53, str, "service");
        this.g.a(str);
        if (strArr.length != 0) {
            for (String str2 : strArr) {
                B9.a(53, str2, Constants.EXTRA_PROVIDER);
            }
            super.a(str, strArr);
            return;
        }
        throw new IllegalArgumentException("Providers cannot be null or empty");
    }

    @Override // com.android.tools.r8.internal.AbstractC3116uF
    public final void a() {
        b();
        this.i = true;
        super.a();
    }
}

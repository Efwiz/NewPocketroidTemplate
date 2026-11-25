package com.android.tools.r8.dex;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
abstract class a0 extends c0 {
    public a0(d0 d0Var, String str) {
        super(d0Var, str);
    }

    @Override // com.android.tools.r8.dex.c0
    public boolean a() {
        return !(this instanceof b0);
    }

    @Override // com.android.tools.r8.dex.c0
    public char b() {
        return '/';
    }

    @Override // com.android.tools.r8.dex.c0
    public final boolean a(char c) {
        return !Character.isLetterOrDigit(c);
    }

    @Override // com.android.tools.r8.dex.c0
    protected final boolean a(int i, int i2) {
        if (!a(i2) && this.a.charAt(i2) != '/') {
            return c(i, i2);
        }
        String strSubstring = this.a.substring(i, i2);
        if (b() != '/') {
            strSubstring = strSubstring.replace(b(), '/');
        }
        String strA = this.g.d.a(this.g.a.E().a(strSubstring));
        if (strSubstring.equals(strA)) {
            return false;
        }
        int i3 = ((c0) this).d;
        if (i3 < i) {
            ((c0) this).b.append((CharSequence) this.a, i3, i);
        }
        if (b() != '/') {
            strA = strA.replace('/', b());
        }
        ((c0) this).b.append(strA);
        ((c0) this).d = i2;
        ((c0) this).c = true;
        return true;
    }
}

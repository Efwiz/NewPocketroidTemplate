package com.android.tools.r8.internal;

import androidx.core.view.InputDeviceCompat;
import androidx.core.view.ViewCompat;
import com.android.dx.rop.code.AccessFlags;
import com.badlogic.gdx.physics.bullet.collision.ContactCallbackEvent;
import io.netty.util.internal.StringUtil;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.implementation.auxiliary.TypeProxy;
import org.apache.commons.compress.compressors.bzip2.BZip2Constants;
import org.eclipse.jdt.internal.compiler.util.Util;
import org.jetbrains.kotlin.backend.common.serialization.mangle.MangleConstant;
import org.jetbrains.kotlin.com.intellij.psi.PsiJavaModule;

/* JADX INFO: renamed from: com.android.tools.r8.internal.v9, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC3187v9 extends AbstractC1256Pa {
    private boolean c;
    private int d;
    private boolean e;
    private boolean f;
    private boolean g;
    private boolean h;
    private boolean i;
    private String j;
    private boolean k;
    private HashMap l;

    protected AbstractC3187v9(C3220va c3220va) {
        super(589824, c3220va);
        this.l = new HashMap();
        this.c = true;
    }

    public static void d(String str) {
        int iB = b(0, str);
        if (iB != str.length()) {
            throw new IllegalArgumentException(str + ": error at index " + iB);
        }
    }

    private static String e(String str) {
        int iLastIndexOf = str.lastIndexOf(47);
        if (iLastIndexOf == -1) {
            return str;
        }
        int length = str.length();
        if (str.charAt(length - 1) == ';') {
            length--;
        }
        int iLastIndexOf2 = str.lastIndexOf(91);
        return iLastIndexOf2 == -1 ? str.substring(iLastIndexOf + 1, length) : str.substring(0, iLastIndexOf2 + 1) + str.substring(iLastIndexOf + 1, length);
    }

    @Override // com.android.tools.r8.internal.AbstractC1256Pa
    public final void a(int i, int i2, String str, String str2, String str3, String[] strArr) {
        int i3;
        if (this.e) {
            throw new IllegalStateException("visit must be called only once");
        }
        this.e = true;
        b();
        a(i2, 259633);
        if (str == null) {
            throw new IllegalArgumentException("Illegal class name (null)");
        }
        if (!str.endsWith("package-info") && !str.endsWith(PsiJavaModule.MODULE_INFO_CLASS)) {
            B9.a(i, str, "class name");
        }
        if (TypeProxy.SilentConstruction.Appender.JAVA_LANG_OBJECT_INTERNAL_NAME.equals(str)) {
            if (str3 != null) {
                throw new IllegalArgumentException("The super class name of the Object class must be 'null'");
            }
        } else if (!str.endsWith(PsiJavaModule.MODULE_INFO_CLASS)) {
            B9.a(i, str3, "super class name");
        } else if (str3 != null) {
            throw new IllegalArgumentException("The super class name of a module-info class must be 'null'");
        }
        if (str2 != null) {
            if ((str2.length() > 0 ? str2.charAt(0) : (char) 0) == '<') {
                int iA = a(str2, ':', c(a(str2, '<', 0), str2));
                if ("L[T".indexOf(iA < str2.length() ? str2.charAt(iA) : (char) 0) != -1) {
                    iA = b(iA, str2);
                }
                while (true) {
                    if ((iA < str2.length() ? str2.charAt(iA) : (char) 0) != ':') {
                        break;
                    } else {
                        iA = b(iA + 1, str2);
                    }
                }
                while (true) {
                    if ((iA < str2.length() ? str2.charAt(iA) : (char) 0) == '>') {
                        break;
                    }
                    iA = a(str2, ':', c(iA, str2));
                    if ("L[T".indexOf(iA < str2.length() ? str2.charAt(iA) : (char) 0) != -1) {
                        iA = b(iA, str2);
                    }
                    while (true) {
                        if ((iA < str2.length() ? str2.charAt(iA) : (char) 0) == ':') {
                            iA = b(iA + 1, str2);
                        }
                    }
                }
                i3 = iA + 1;
            } else {
                i3 = 0;
            }
            int iA2 = a(i3, str2);
            while (true) {
                if ((iA2 < str2.length() ? str2.charAt(iA2) : (char) 0) != 'L') {
                    break;
                } else {
                    iA2 = a(iA2, str2);
                }
            }
            if (iA2 != str2.length()) {
                throw new IllegalArgumentException(str2 + ": error at index " + iA2);
            }
        }
        if ((i2 & 512) != 0 && !TypeProxy.SilentConstruction.Appender.JAVA_LANG_OBJECT_INTERNAL_NAME.equals(str3)) {
            throw new IllegalArgumentException("The super class name of interfaces must be 'java/lang/Object'");
        }
        for (int i4 = 0; i4 < strArr.length; i4++) {
            B9.a(i, strArr[i4], AbstractC1857dg.a("interface name at index ", i4));
        }
        this.d = i;
        super.a(i, i2, str, str2, str3, strArr);
    }

    @Override // com.android.tools.r8.internal.AbstractC1256Pa
    public final void b(String str) {
        b();
        B9.a(this.d, str, "nestMember");
        if (this.i) {
            throw new IllegalStateException("visitMemberOfNest and visitNestHost are mutually exclusive.");
        }
        int iLastIndexOf = str.lastIndexOf(47);
        String strSubstring = iLastIndexOf == -1 ? "" : str.substring(0, iLastIndexOf);
        String str2 = this.j;
        if (str2 == null) {
            this.j = strSubstring;
        } else if (!str2.equals(strSubstring)) {
            throw new IllegalStateException("nest member " + str + " should be in the package " + this.j);
        }
        super.b(str);
    }

    @Override // com.android.tools.r8.internal.AbstractC1256Pa
    public final void c(String str) {
        b();
        B9.a(this.d, str, "permittedSubclass");
        super.c(str);
    }

    private static int c(int i, String str) {
        int iOffsetByCodePoints = i;
        while (iOffsetByCodePoints < str.length() && ".;[/<>:".indexOf(str.codePointAt(iOffsetByCodePoints)) == -1) {
            iOffsetByCodePoints = str.offsetByCodePoints(iOffsetByCodePoints, 1);
        }
        if (iOffsetByCodePoints != i) {
            return iOffsetByCodePoints;
        }
        throw new IllegalArgumentException(str + ": identifier expected at index " + i);
    }

    @Override // com.android.tools.r8.internal.AbstractC1256Pa
    public final CQ b(String str, String str2, String str3) {
        b();
        B9.a(this.d, 0, -1, str, "record component name");
        B9.d(this.d, str2);
        if (str3 != null) {
            d(str3);
        }
        return new G9(this.a, super.b(str, str2, str3));
    }

    private void b() {
        if (this.e) {
            if (this.k) {
                throw new IllegalStateException("Cannot visit member after visitEnd has been called.");
            }
            return;
        }
        throw new IllegalStateException("Cannot visit member before visit has been called.");
    }

    static void b(int i, String str, String str2) {
        int i2 = 0;
        while (true) {
            try {
                int iIndexOf = str.indexOf(46, i2 + 1);
                if (iIndexOf != -1) {
                    B9.a(i, i2, iIndexOf, str, (String) null);
                    i2 = iIndexOf + 1;
                } else {
                    B9.a(i, i2, str.length(), str, (String) null);
                    return;
                }
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Invalid " + str2 + " (must be a fully qualified name): " + str, e);
            }
        }
    }

    private static int b(int i, String str) {
        char cCharAt = i < str.length() ? str.charAt(i) : (char) 0;
        if (cCharAt == 'L') {
            return a(i, str);
        }
        if (cCharAt != '[') {
            return a(str, ';', c(a(str, Util.C_TYPE_VARIABLE, i), str));
        }
        int i2 = i + 1;
        char cCharAt2 = i2 < str.length() ? str.charAt(i2) : (char) 0;
        if (cCharAt2 != 'F' && cCharAt2 != 'S' && cCharAt2 != 'Z' && cCharAt2 != 'I' && cCharAt2 != 'J') {
            switch (cCharAt2) {
                case 'B':
                case 'C':
                case 'D':
                    break;
                default:
                    return b(i2, str);
            }
        }
        return i + 2;
    }

    @Override // com.android.tools.r8.internal.AbstractC1256Pa
    public final void a(String str, String str2) {
        b();
        if (!this.g) {
            this.g = true;
            super.a(str, str2);
            return;
        }
        throw new IllegalStateException("visitSource can be called only once.");
    }

    @Override // com.android.tools.r8.internal.AbstractC1256Pa
    public final AbstractC3116uF a(int i, String str, String str2) {
        b();
        if (!this.f) {
            this.f = true;
            b(this.d, str, "module name");
            a(i, 36896);
            D9 d9 = new D9(this.a, super.a(i, str, str2), (i & 32) != 0);
            d9.h = this.d;
            return d9;
        }
        throw new IllegalStateException("visitModule can be called only once.");
    }

    @Override // com.android.tools.r8.internal.AbstractC1256Pa
    public final void a(String str) {
        b();
        B9.a(this.d, str, "nestHost");
        if (!this.i) {
            if (this.j == null) {
                this.i = true;
                super.a(str);
                return;
            }
            throw new IllegalStateException("visitNestHost and visitNestMember are mutually exclusive.");
        }
        throw new IllegalStateException("visitNestHost can be called only once.");
    }

    @Override // com.android.tools.r8.internal.AbstractC1256Pa
    public final void a(String str, String str2, String str3) {
        b();
        if (!this.h) {
            this.h = true;
            if (str != null) {
                if (str3 != null) {
                    B9.e(this.d, str3);
                }
                super.a(str, str2, str3);
                return;
            }
            throw new IllegalArgumentException("Illegal outer class owner");
        }
        throw new IllegalStateException("visitOuterClass can be called only once.");
    }

    @Override // com.android.tools.r8.internal.AbstractC1256Pa
    public final void a(int i, String str, String str2, String str3) {
        b();
        B9.a(this.d, str, "class name");
        if (str2 != null) {
            B9.a(this.d, str2, "outer class name");
        }
        if (str3 != null) {
            int i2 = 0;
            while (i2 < str3.length() && Character.isDigit(str3.charAt(i2))) {
                i2++;
            }
            if (i2 == 0 || i2 < str3.length()) {
                B9.a(this.d, i2, -1, str3, "inner class name");
            }
        }
        a(i, AccessFlags.INNER_CLASS_FLAGS);
        super.a(i, str, str2, str3);
    }

    @Override // com.android.tools.r8.internal.AbstractC1256Pa
    public final AbstractC2252ii a(int i, String str, String str2, String str3, Object obj) {
        b();
        a(i, 184543);
        B9.a(this.d, 0, -1, str, "field name");
        B9.d(this.d, str2);
        if (str3 != null) {
            d(str3);
        }
        if (obj != null && !(obj instanceof Integer) && !(obj instanceof Float) && !(obj instanceof Long) && !(obj instanceof Double) && !(obj instanceof String)) {
            throw new IllegalArgumentException("Invalid constant: " + obj);
        }
        return new C3262w9(this.a, super.a(i, str, str2, str3, obj));
    }

    /* JADX WARN: Code duplicated, block: B:93:0x0154  */
    @Override // com.android.tools.r8.internal.AbstractC1256Pa
    public final AbstractC1899eF a(int i, String str, String str2, String str3, String[] strArr) {
        B9 b9;
        int i2;
        int iA;
        b();
        int i3 = this.d;
        a(i, 171519);
        if ((i3 & 65535) < 61 && Integer.bitCount(i & ContactCallbackEvent.ON_DESTROYED) > 1) {
            throw new IllegalArgumentException(AbstractC1857dg.a("strictfp and abstract are mutually exclusive: ", i));
        }
        if (!"<init>".equals(str) && !MethodDescription.TYPE_INITIALIZER_INTERNAL_NAME.equals(str)) {
            B9.b(this.d, str, "method name");
        }
        B9.e(this.d, str2);
        if (str3 != null) {
            if ((str3.length() > 0 ? str3.charAt(0) : (char) 0) == '<') {
                int iA2 = a(str3, ':', c(a(str3, '<', 0), str3));
                if ("L[T".indexOf(iA2 < str3.length() ? str3.charAt(iA2) : (char) 0) != -1) {
                    iA2 = b(iA2, str3);
                }
                while (true) {
                    if ((iA2 < str3.length() ? str3.charAt(iA2) : (char) 0) != ':') {
                        break;
                    }
                    iA2 = b(iA2 + 1, str3);
                }
                while (true) {
                    if ((iA2 < str3.length() ? str3.charAt(iA2) : (char) 0) == '>') {
                        break;
                    }
                    iA2 = a(str3, ':', c(iA2, str3));
                    if ("L[T".indexOf(iA2 < str3.length() ? str3.charAt(iA2) : (char) 0) != -1) {
                        iA2 = b(iA2, str3);
                    }
                    while (true) {
                        if ((iA2 < str3.length() ? str3.charAt(iA2) : (char) 0) == ':') {
                            iA2 = b(iA2 + 1, str3);
                        }
                    }
                }
                i2 = iA2 + 1;
            } else {
                i2 = 0;
            }
            int iA3 = a(str3, '(', i2);
            while (true) {
                if ("ZCBSIFJDL[T".indexOf(iA3 < str3.length() ? str3.charAt(iA3) : (char) 0) != -1) {
                    char cCharAt = iA3 < str3.length() ? str3.charAt(iA3) : (char) 0;
                    if (cCharAt != 'F' && cCharAt != 'S' && cCharAt != 'Z' && cCharAt != 'I' && cCharAt != 'J') {
                        switch (cCharAt) {
                            case 'B':
                            case 'C':
                            case 'D':
                                break;
                            default:
                                iA3 = b(iA3, str3);
                                continue;
                        }
                    }
                    iA3++;
                } else {
                    int iA4 = a(str3, ')', iA3);
                    if ((iA4 < str3.length() ? str3.charAt(iA4) : (char) 0) != 'V') {
                        char cCharAt2 = iA4 < str3.length() ? str3.charAt(iA4) : (char) 0;
                        if (cCharAt2 != 'F' && cCharAt2 != 'S' && cCharAt2 != 'Z' && cCharAt2 != 'I' && cCharAt2 != 'J') {
                            switch (cCharAt2) {
                                case 'B':
                                case 'C':
                                case 'D':
                                    iA = iA4 + 1;
                                    break;
                                default:
                                    iA = b(iA4, str3);
                                    break;
                            }
                        } else {
                            iA = iA4 + 1;
                        }
                    } else {
                        iA = iA4 + 1;
                    }
                    while (true) {
                        if ((iA < str3.length() ? str3.charAt(iA) : (char) 0) == '^') {
                            int i4 = iA + 1;
                            if ((i4 < str3.length() ? str3.charAt(i4) : (char) 0) == 'L') {
                                iA = a(i4, str3);
                            } else {
                                iA = a(str3, ';', c(a(str3, Util.C_TYPE_VARIABLE, i4), str3));
                            }
                        } else if (iA != str3.length()) {
                            throw new IllegalArgumentException(str3 + ": error at index " + iA);
                        }
                    }
                }
            }
        }
        if (strArr != null) {
            for (int i5 = 0; i5 < strArr.length; i5++) {
                B9.a(this.d, strArr[i5], AbstractC1857dg.a("exception name at index ", i5));
            }
        }
        AbstractC1899eF abstractC1899eFA = super.a(i, str, str2, str3, strArr);
        if (this.c) {
            AbstractC1256Pa abstractC1256Pa = this.b;
            b9 = new B9(this.a, i, str, str2, abstractC1256Pa instanceof C1549Za ? new A9(this.a, this.d, (C1549Za) abstractC1256Pa, abstractC1899eFA) : abstractC1899eFA, this.l);
        } else {
            b9 = new B9(this.a, abstractC1899eFA, this.l);
        }
        b9.j = this.d;
        return b9;
    }

    @Override // com.android.tools.r8.internal.AbstractC1256Pa
    public final AbstractC2509m2 a(String str, boolean z) {
        b();
        B9.d(this.d, str);
        return new C2831q9(super.a(str, z), true);
    }

    @Override // com.android.tools.r8.internal.AbstractC1256Pa
    public final AbstractC2509m2 a(int i, C00 c00, String str, boolean z) {
        b();
        int iC = new D00(i).c();
        if (iC != 0 && iC != 17 && iC != 16) {
            throw new IllegalArgumentException("Invalid type reference sort 0x" + Integer.toHexString(iC));
        }
        a(i);
        B9.d(this.d, str);
        return new C2831q9(super.a(i, c00, str, z), true);
    }

    @Override // com.android.tools.r8.internal.AbstractC1256Pa
    public final void a(T3 t3) {
        b();
        super.a(t3);
    }

    @Override // com.android.tools.r8.internal.AbstractC1256Pa
    public final void a() {
        b();
        this.k = true;
        super.a();
    }

    static void a(int i, int i2) {
        if (((~i2) & i) == 0) {
            if (Integer.bitCount(i & 7) <= 1) {
                if (Integer.bitCount(i & 1040) > 1) {
                    throw new IllegalArgumentException(AbstractC1857dg.a("final and abstract are mutually exclusive: ", i));
                }
                return;
            }
            throw new IllegalArgumentException(AbstractC1857dg.a("public, protected and private are mutually exclusive: ", i));
        }
        throw new IllegalArgumentException(AbstractC1857dg.a("Invalid access flags: ", i));
    }

    /* JADX WARN: Code restructure failed: missing block: B:80:0x0057, code lost:
    
        r8 = r8 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:81:0x00c2, code lost:
    
        r8 = r8 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:82:0x00ec, code lost:
    
        r8 = r8 + 1;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private static int a(int r8, java.lang.String r9) {
        /*
            Method dump skipped, instruction units count: 246
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.tools.r8.internal.AbstractC3187v9.a(int, java.lang.String):int");
    }

    private static int a(String str, char c, int i) {
        if ((i < str.length() ? str.charAt(i) : (char) 0) == c) {
            return i + 1;
        }
        throw new IllegalArgumentException(str + ": '" + c + "' expected at index " + i);
    }

    /* JADX WARN: Code duplicated, block: B:10:0x0013  */
    /* JADX WARN: Code duplicated, block: B:11:0x0016  */
    /* JADX WARN: Code duplicated, block: B:12:0x0019  */
    static void a(int i) {
        int i2;
        int i3 = i >>> 24;
        if (i3 != 0 && i3 != 1) {
            switch (i3) {
                case 16:
                case 17:
                case 18:
                case 23:
                    i2 = InputDeviceCompat.SOURCE_ANY;
                    break;
                case 19:
                case 20:
                case 21:
                    i2 = ViewCompat.MEASURED_STATE_MASK;
                    break;
                case 22:
                    i2 = -65536;
                    break;
                default:
                    switch (i3) {
                        case 64:
                        case 65:
                        case 67:
                        case 68:
                        case 69:
                        case 70:
                            i2 = ViewCompat.MEASURED_STATE_MASK;
                            break;
                        case 66:
                            i2 = InputDeviceCompat.SOURCE_ANY;
                            break;
                        case 71:
                        case 72:
                        case 73:
                        case 74:
                        case 75:
                            i2 = -16776961;
                            break;
                        default:
                            i2 = 0;
                            break;
                    }
                    break;
            }
        } else {
            i2 = -65536;
        }
        if (i2 == 0 || ((~i2) & i) != 0) {
            throw new IllegalArgumentException("Invalid type reference 0x" + Integer.toHexString(i));
        }
    }

    static void a(ME me, V1 v1, PrintWriter printWriter) {
        ZY zy = new ZY(589824);
        C2314jZ c2314jZ = new C2314jZ(zy);
        printWriter.println(me.k + me.l);
        for (int i = 0; i < me.x.size(); i++) {
            me.x.j(i).a(c2314jZ);
            StringBuilder sb = new StringBuilder();
            C1238Oi c1238Oi = v1.a()[i];
            if (c1238Oi == null) {
                sb.append(MangleConstant.Q_MARK);
            } else {
                for (int i2 = 0; i2 < c1238Oi.b(); i2++) {
                    sb.append(e(((E4) c1238Oi.a(i2)).toString())).append(StringUtil.SPACE);
                }
                sb.append(" : ");
                for (int i3 = 0; i3 < c1238Oi.c(); i3++) {
                    sb.append(e(((E4) c1238Oi.b(i3)).toString())).append(StringUtil.SPACE);
                }
            }
            while (sb.length() < me.z + me.A + 1) {
                sb.append(StringUtil.SPACE);
            }
            printWriter.print(Integer.toString(BZip2Constants.BASEBLOCKSIZE + i).substring(1));
            StringBuilder sbAppend = new StringBuilder(" ").append((Object) sb).append(" : ");
            ArrayList arrayList = zy.c;
            printWriter.print(sbAppend.append(arrayList.get(arrayList.size() - 1)).toString());
        }
        Iterator it = me.y.iterator();
        while (it.hasNext()) {
            ((C3443yZ) it.next()).a(c2314jZ);
            StringBuilder sb2 = new StringBuilder(" ");
            ArrayList arrayList2 = zy.c;
            printWriter.print(sb2.append(arrayList2.get(arrayList2.size() - 1)).toString());
        }
        printWriter.println();
    }
}

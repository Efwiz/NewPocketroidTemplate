package com.android.tools.r8.internal;

import androidx.exifinterface.media.ExifInterface;
import com.huawei.hms.ml.language.common.utils.Constant;
import io.netty.util.internal.StringUtil;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import org.eclipse.jdt.internal.compiler.util.Util;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class ZY extends ZM {
    private static final List l = Collections.unmodifiableList(Arrays.asList(ExifInterface.GPS_DIRECTION_TRUE, "I", "F", "D", "J", "N", "U"));
    protected String f;
    protected String g;
    protected String h;
    protected String i;
    protected HashMap j;
    private int k;

    protected ZY(int i) {
        super(i);
        this.f = "  ";
        this.g = "    ";
        this.h = "      ";
        this.i = "   ";
    }

    private void c(String str) {
        this.b.setLength(0);
        int i = this.k;
        this.k = i + 1;
        b(i);
        if (str != null) {
            this.b.append(str).append('=');
        }
    }

    @Override // com.android.tools.r8.internal.ZM
    public final void a(String str, Object obj) {
        c(str);
        if (obj instanceof String) {
            ZM.a(this.b, (String) obj);
        } else if (obj instanceof CZ) {
            this.b.append(((CZ) obj).a()).append(".class");
        } else if (obj instanceof Byte) {
            this.b.append("(byte)").append((int) ((Byte) obj).byteValue());
        } else if (obj instanceof Boolean) {
            this.b.append(((Boolean) obj).booleanValue());
        } else if (obj instanceof Short) {
            this.b.append("(short)").append((int) ((Short) obj).shortValue());
        } else if (obj instanceof Character) {
            this.b.append("(char)").append((int) ((Character) obj).charValue());
        } else if (obj instanceof Integer) {
            this.b.append(((Integer) obj).intValue());
        } else if (obj instanceof Float) {
            this.b.append(((Float) obj).floatValue()).append(Util.C_FLOAT);
        } else if (obj instanceof Long) {
            this.b.append(((Long) obj).longValue()).append('L');
        } else if (obj instanceof Double) {
            this.b.append(((Double) obj).doubleValue()).append(Util.C_DOUBLE);
        } else if (obj.getClass().isArray()) {
            this.b.append('{');
            int i = 0;
            if (obj instanceof byte[]) {
                byte[] bArr = (byte[]) obj;
                while (i < bArr.length) {
                    b(i);
                    this.b.append("(byte)").append((int) bArr[i]);
                    i++;
                }
            } else if (obj instanceof boolean[]) {
                boolean[] zArr = (boolean[]) obj;
                while (i < zArr.length) {
                    b(i);
                    this.b.append(zArr[i]);
                    i++;
                }
            } else if (obj instanceof short[]) {
                short[] sArr = (short[]) obj;
                while (i < sArr.length) {
                    b(i);
                    this.b.append("(short)").append((int) sArr[i]);
                    i++;
                }
            } else if (obj instanceof char[]) {
                char[] cArr = (char[]) obj;
                while (i < cArr.length) {
                    b(i);
                    this.b.append("(char)").append((int) cArr[i]);
                    i++;
                }
            } else if (obj instanceof int[]) {
                int[] iArr = (int[]) obj;
                while (i < iArr.length) {
                    b(i);
                    this.b.append(iArr[i]);
                    i++;
                }
            } else if (obj instanceof long[]) {
                long[] jArr = (long[]) obj;
                while (i < jArr.length) {
                    b(i);
                    this.b.append(jArr[i]).append('L');
                    i++;
                }
            } else if (obj instanceof float[]) {
                float[] fArr = (float[]) obj;
                while (i < fArr.length) {
                    b(i);
                    this.b.append(fArr[i]).append(Util.C_FLOAT);
                    i++;
                }
            } else if (obj instanceof double[]) {
                double[] dArr = (double[]) obj;
                while (i < dArr.length) {
                    b(i);
                    this.b.append(dArr[i]).append(Util.C_DOUBLE);
                    i++;
                }
            }
            this.b.append('}');
        }
        this.c.add(this.b.toString());
    }

    public final ZY b(int i, C00 c00, String str, boolean z) {
        this.b.setLength(0);
        this.b.append(this.f).append('@');
        a(1, str);
        this.b.append('(');
        this.c.add(this.b.toString());
        this.b.setLength(0);
        this.b.append(") : ");
        a(i);
        this.b.append(", ").append(c00);
        this.b.append(z ? "\n" : " // invisible\n");
        return b(this.b.toString());
    }

    private void b(int i) {
        if (i > 0) {
            this.b.append(", ");
        }
    }

    private ZY b(String str) {
        ZY zy = new ZY(this.a);
        this.c.add(zy.c);
        if (str != null) {
            this.c.add(str);
        }
        return zy;
    }

    @Override // com.android.tools.r8.internal.ZM
    public final void a(String str, String str2, String str3) {
        c(str);
        a(1, str2);
        this.b.append('.').append(str3);
        this.c.add(this.b.toString());
    }

    @Override // com.android.tools.r8.internal.ZM
    public final ZY a(String str, String str2) {
        c(str);
        this.b.append('@');
        a(1, str2);
        this.b.append('(');
        this.c.add(this.b.toString());
        return b(Constant.AFTER_QUTO);
    }

    @Override // com.android.tools.r8.internal.ZM
    public final ZY a(String str) {
        c(str);
        this.b.append('{');
        this.c.add(this.b.toString());
        return b("}");
    }

    @Override // com.android.tools.r8.internal.ZM
    public final ZY a() {
        this.c.add(this.g + "default=");
        return b("\n");
    }

    @Override // com.android.tools.r8.internal.ZM
    public final ZY a(int i, String str, boolean z) {
        this.b.setLength(0);
        this.b.append(this.g).append('@');
        a(1, str);
        this.b.append('(');
        this.c.add(this.b.toString());
        this.b.setLength(0);
        this.b.append(z ? ") // parameter " : ") // invisible, parameter ").append(i).append('\n');
        return b(this.b.toString());
    }

    @Override // com.android.tools.r8.internal.ZM
    public final void a(int i, int i2, Object[] objArr, int i3, Object[] objArr2) {
        this.b.setLength(0);
        this.b.append(this.i);
        this.b.append("FRAME ");
        if (i == -1 || i == 0) {
            this.b.append("FULL [");
            a(i2, objArr);
            this.b.append("] [");
            a(i3, objArr2);
            this.b.append(']');
        } else if (i == 1) {
            this.b.append("APPEND [");
            a(i2, objArr);
            this.b.append(']');
        } else if (i == 2) {
            this.b.append("CHOP ").append(i2);
        } else if (i == 3) {
            this.b.append("SAME");
        } else {
            if (i != 4) {
                throw new IllegalArgumentException();
            }
            this.b.append("SAME1 ");
            a(1, objArr2);
        }
        this.b.append('\n');
        this.c.add(this.b.toString());
    }

    @Override // com.android.tools.r8.internal.ZM
    public final void a(int i, String str, String str2, String str3, boolean z) {
        this.b.setLength(0);
        this.b.append(this.g).append(ZM.d[i]).append(StringUtil.SPACE);
        a(0, str);
        this.b.append('.').append(str2).append(StringUtil.SPACE);
        a(3, str3);
        if (z) {
            this.b.append(" (itf)");
        }
        this.b.append('\n');
        this.c.add(this.b.toString());
    }

    @Override // com.android.tools.r8.internal.ZM
    public final ZY a(int i, C00 c00, String str, boolean z) {
        this.b.setLength(0);
        this.b.append(this.g).append("TRYCATCHBLOCK @");
        a(1, str);
        this.b.append('(');
        this.c.add(this.b.toString());
        this.b.setLength(0);
        this.b.append(") : ");
        a(i);
        this.b.append(", ").append(c00);
        this.b.append(z ? "\n" : " // invisible\n");
        return b(this.b.toString());
    }

    @Override // com.android.tools.r8.internal.ZM
    public final ZY a(int i, C00 c00, C3263wA[] c3263wAArr, C3263wA[] c3263wAArr2, int[] iArr, String str, boolean z) {
        this.b.setLength(0);
        this.b.append(this.g).append("LOCALVARIABLE @");
        a(1, str);
        this.b.append('(');
        this.c.add(this.b.toString());
        this.b.setLength(0);
        this.b.append(") : ");
        a(i);
        this.b.append(", ").append(c00);
        for (int i2 = 0; i2 < c3263wAArr.length; i2++) {
            this.b.append(" [ ");
            a(c3263wAArr[i2]);
            this.b.append(" - ");
            a(c3263wAArr2[i2]);
            this.b.append(" - ").append(iArr[i2]).append(" ]");
        }
        this.b.append(z ? "\n" : " // invisible\n");
        return b(this.b.toString());
    }

    @Override // com.android.tools.r8.internal.ZM
    public final ZY a(String str, boolean z) {
        this.b.setLength(0);
        this.b.append(this.f).append('@');
        a(1, str);
        this.b.append('(');
        this.c.add(this.b.toString());
        return b(z ? ")\n" : ") // invisible\n");
    }

    protected final void a(int i, String str) {
        if (i != 5 && i != 2 && i != 4) {
            this.b.append(str);
        } else if (str != null) {
            this.b.append("// signature ").append(str).append('\n');
        }
    }

    protected final void a(C3263wA c3263wA) {
        if (this.j == null) {
            this.j = new HashMap();
        }
        String str = (String) this.j.get(c3263wA);
        if (str == null) {
            str = "L" + this.j.size();
            this.j.put(c3263wA, str);
        }
        this.b.append(str);
    }

    /* JADX WARN: Code duplicated, block: B:18:0x0093  */
    /* JADX WARN: Code duplicated, block: B:21:0x00a5  */
    /* JADX WARN: Code duplicated, block: B:24:0x00b2  */
    /* JADX WARN: Code duplicated, block: B:26:? A[RETURN, SYNTHETIC] */
    protected final void a(C1944ek c1944ek) {
        int iD = c1944ek.d();
        this.b.append("// handle kind 0x").append(Integer.toHexString(iD)).append(" : ");
        boolean z = true;
        switch (iD) {
            case 1:
                this.b.append("GETFIELD");
                z = false;
                this.b.append('\n');
                this.b.append(this.h);
                a(0, c1944ek.c());
                this.b.append('.');
                this.b.append(c1944ek.b());
                if (!z) {
                    this.b.append('(');
                }
                a(9, c1944ek.a());
                if (!z) {
                    this.b.append(')');
                }
                if (c1944ek.e()) {
                    this.b.append(" itf");
                    return;
                }
                return;
            case 2:
                this.b.append("GETSTATIC");
                z = false;
                this.b.append('\n');
                this.b.append(this.h);
                a(0, c1944ek.c());
                this.b.append('.');
                this.b.append(c1944ek.b());
                if (!z) {
                    this.b.append('(');
                }
                a(9, c1944ek.a());
                if (!z) {
                    this.b.append(')');
                }
                if (c1944ek.e()) {
                    this.b.append(" itf");
                    return;
                }
                return;
            case 3:
                this.b.append("PUTFIELD");
                z = false;
                this.b.append('\n');
                this.b.append(this.h);
                a(0, c1944ek.c());
                this.b.append('.');
                this.b.append(c1944ek.b());
                if (!z) {
                    this.b.append('(');
                }
                a(9, c1944ek.a());
                if (!z) {
                    this.b.append(')');
                }
                if (c1944ek.e()) {
                    this.b.append(" itf");
                    return;
                }
                return;
            case 4:
                this.b.append("PUTSTATIC");
                z = false;
                this.b.append('\n');
                this.b.append(this.h);
                a(0, c1944ek.c());
                this.b.append('.');
                this.b.append(c1944ek.b());
                if (!z) {
                    this.b.append('(');
                }
                a(9, c1944ek.a());
                if (!z) {
                    this.b.append(')');
                }
                if (c1944ek.e()) {
                    this.b.append(" itf");
                    return;
                }
                return;
            case 5:
                this.b.append("INVOKEVIRTUAL");
                this.b.append('\n');
                this.b.append(this.h);
                a(0, c1944ek.c());
                this.b.append('.');
                this.b.append(c1944ek.b());
                if (!z) {
                    this.b.append('(');
                }
                a(9, c1944ek.a());
                if (!z) {
                    this.b.append(')');
                }
                if (c1944ek.e()) {
                    this.b.append(" itf");
                    return;
                }
                return;
            case 6:
                this.b.append("INVOKESTATIC");
                this.b.append('\n');
                this.b.append(this.h);
                a(0, c1944ek.c());
                this.b.append('.');
                this.b.append(c1944ek.b());
                if (!z) {
                    this.b.append('(');
                }
                a(9, c1944ek.a());
                if (!z) {
                    this.b.append(')');
                }
                if (c1944ek.e()) {
                    this.b.append(" itf");
                    return;
                }
                return;
            case 7:
                this.b.append("INVOKESPECIAL");
                this.b.append('\n');
                this.b.append(this.h);
                a(0, c1944ek.c());
                this.b.append('.');
                this.b.append(c1944ek.b());
                if (!z) {
                    this.b.append('(');
                }
                a(9, c1944ek.a());
                if (!z) {
                    this.b.append(')');
                }
                if (c1944ek.e()) {
                    this.b.append(" itf");
                    return;
                }
                return;
            case 8:
                this.b.append("NEWINVOKESPECIAL");
                this.b.append('\n');
                this.b.append(this.h);
                a(0, c1944ek.c());
                this.b.append('.');
                this.b.append(c1944ek.b());
                if (!z) {
                    this.b.append('(');
                }
                a(9, c1944ek.a());
                if (!z) {
                    this.b.append(')');
                }
                if (c1944ek.e()) {
                    this.b.append(" itf");
                    return;
                }
                return;
            case 9:
                this.b.append("INVOKEINTERFACE");
                this.b.append('\n');
                this.b.append(this.h);
                a(0, c1944ek.c());
                this.b.append('.');
                this.b.append(c1944ek.b());
                if (!z) {
                    this.b.append('(');
                }
                a(9, c1944ek.a());
                if (!z) {
                    this.b.append(')');
                }
                if (c1944ek.e()) {
                    this.b.append(" itf");
                    return;
                }
                return;
            default:
                throw new IllegalArgumentException();
        }
    }

    private void a(int i) {
        D00 d00 = new D00(i);
        int iC = d00.c();
        if (iC == 0) {
            this.b.append("CLASS_TYPE_PARAMETER ").append(d00.h());
            return;
        }
        if (iC != 1) {
            switch (iC) {
                case 16:
                    this.b.append("CLASS_EXTENDS ").append(d00.d());
                    return;
                case 17:
                    this.b.append("CLASS_TYPE_PARAMETER_BOUND ").append(d00.h()).append(", ").append(d00.g());
                    return;
                case 18:
                    this.b.append("METHOD_TYPE_PARAMETER_BOUND ").append(d00.h()).append(", ").append(d00.g());
                    return;
                case 19:
                    this.b.append("FIELD");
                    return;
                case 20:
                    this.b.append("METHOD_RETURN");
                    return;
                case 21:
                    this.b.append("METHOD_RECEIVER");
                    return;
                case 22:
                    this.b.append("METHOD_FORMAL_PARAMETER ").append(d00.b());
                    return;
                case 23:
                    this.b.append("THROWS ").append(d00.a());
                    return;
                default:
                    switch (iC) {
                        case 64:
                            this.b.append("LOCAL_VARIABLE");
                            return;
                        case 65:
                            this.b.append("RESOURCE_VARIABLE");
                            return;
                        case 66:
                            this.b.append("EXCEPTION_PARAMETER ").append(d00.e());
                            return;
                        case 67:
                            this.b.append("INSTANCEOF");
                            return;
                        case 68:
                            this.b.append("NEW");
                            return;
                        case 69:
                            this.b.append("CONSTRUCTOR_REFERENCE");
                            return;
                        case 70:
                            this.b.append("METHOD_REFERENCE");
                            return;
                        case 71:
                            this.b.append("CAST ").append(d00.f());
                            return;
                        case 72:
                            this.b.append("CONSTRUCTOR_INVOCATION_TYPE_ARGUMENT ").append(d00.f());
                            return;
                        case 73:
                            this.b.append("METHOD_INVOCATION_TYPE_ARGUMENT ").append(d00.f());
                            return;
                        case 74:
                            this.b.append("CONSTRUCTOR_REFERENCE_TYPE_ARGUMENT ").append(d00.f());
                            return;
                        case 75:
                            this.b.append("METHOD_REFERENCE_TYPE_ARGUMENT ").append(d00.f());
                            return;
                        default:
                            throw new IllegalArgumentException();
                    }
            }
        }
        this.b.append("METHOD_TYPE_PARAMETER ").append(d00.h());
    }

    private void a(int i, Object[] objArr) {
        for (int i2 = 0; i2 < i; i2++) {
            if (i2 > 0) {
                this.b.append(StringUtil.SPACE);
            }
            Object obj = objArr[i2];
            if (obj instanceof String) {
                String str = (String) obj;
                if (str.charAt(0) == '[') {
                    a(1, str);
                } else {
                    a(0, str);
                }
            } else if (obj instanceof Integer) {
                this.b.append((String) l.get(((Integer) obj).intValue()));
            } else {
                a((C3263wA) obj);
            }
        }
    }
}

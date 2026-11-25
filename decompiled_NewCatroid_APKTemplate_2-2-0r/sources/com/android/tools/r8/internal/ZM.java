package com.android.tools.r8.internal;

import java.util.ArrayList;
import net.mgsx.gltf.scene3d.attributes.PBRFloatAttribute;
import org.apache.commons.compress.archivers.tar.TarConstants;
import org.eclipse.jdt.internal.compiler.classfmt.ExternalAnnotationProvider;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class ZM {
    public static final String[] d = {"NOP", "ACONST_NULL", "ICONST_M1", "ICONST_0", "ICONST_1", "ICONST_2", "ICONST_3", "ICONST_4", "ICONST_5", "LCONST_0", "LCONST_1", "FCONST_0", "FCONST_1", "FCONST_2", "DCONST_0", "DCONST_1", "BIPUSH", "SIPUSH", "LDC", "LDC_W", "LDC2_W", "ILOAD", "LLOAD", "FLOAD", "DLOAD", "ALOAD", "ILOAD_0", "ILOAD_1", "ILOAD_2", "ILOAD_3", "LLOAD_0", "LLOAD_1", "LLOAD_2", "LLOAD_3", "FLOAD_0", "FLOAD_1", "FLOAD_2", "FLOAD_3", "DLOAD_0", "DLOAD_1", "DLOAD_2", "DLOAD_3", "ALOAD_0", "ALOAD_1", "ALOAD_2", "ALOAD_3", "IALOAD", "LALOAD", "FALOAD", "DALOAD", "AALOAD", "BALOAD", "CALOAD", "SALOAD", "ISTORE", "LSTORE", "FSTORE", "DSTORE", "ASTORE", "ISTORE_0", "ISTORE_1", "ISTORE_2", "ISTORE_3", "LSTORE_0", "LSTORE_1", "LSTORE_2", "LSTORE_3", "FSTORE_0", "FSTORE_1", "FSTORE_2", "FSTORE_3", "DSTORE_0", "DSTORE_1", "DSTORE_2", "DSTORE_3", "ASTORE_0", "ASTORE_1", "ASTORE_2", "ASTORE_3", "IASTORE", "LASTORE", "FASTORE", "DASTORE", "AASTORE", "BASTORE", "CASTORE", "SASTORE", "POP", "POP2", "DUP", "DUP_X1", "DUP_X2", "DUP2", "DUP2_X1", "DUP2_X2", "SWAP", "IADD", "LADD", "FADD", "DADD", "ISUB", "LSUB", "FSUB", "DSUB", "IMUL", "LMUL", "FMUL", "DMUL", "IDIV", "LDIV", "FDIV", "DDIV", "IREM", "LREM", "FREM", "DREM", "INEG", "LNEG", "FNEG", "DNEG", "ISHL", "LSHL", "ISHR", "LSHR", "IUSHR", "LUSHR", "IAND", "LAND", PBRFloatAttribute.IORAlias, "LOR", "IXOR", "LXOR", "IINC", "I2L", "I2F", "I2D", "L2I", "L2F", "L2D", "F2I", "F2L", "F2D", "D2I", "D2L", "D2F", "I2B", "I2C", "I2S", "LCMP", "FCMPL", "FCMPG", "DCMPL", "DCMPG", "IFEQ", "IFNE", "IFLT", "IFGE", "IFGT", "IFLE", "IF_ICMPEQ", "IF_ICMPNE", "IF_ICMPLT", "IF_ICMPGE", "IF_ICMPGT", "IF_ICMPLE", "IF_ACMPEQ", "IF_ACMPNE", "GOTO", "JSR", "RET", "TABLESWITCH", "LOOKUPSWITCH", "IRETURN", "LRETURN", "FRETURN", "DRETURN", "ARETURN", "RETURN", "GETSTATIC", "PUTSTATIC", "GETFIELD", "PUTFIELD", "INVOKEVIRTUAL", "INVOKESPECIAL", "INVOKESTATIC", "INVOKEINTERFACE", "INVOKEDYNAMIC", "NEW", "NEWARRAY", "ANEWARRAY", "ARRAYLENGTH", "ATHROW", "CHECKCAST", "INSTANCEOF", "MONITORENTER", "MONITOREXIT", "WIDE", "MULTIANEWARRAY", "IFNULL", "IFNONNULL"};
    public static final String[] e = {"", "", "", "", "T_BOOLEAN", "T_CHAR", "T_FLOAT", "T_DOUBLE", "T_BYTE", "T_SHORT", "T_INT", "T_LONG"};
    protected final int a;
    protected final StringBuilder b = new StringBuilder();
    public final ArrayList c = new ArrayList();

    protected ZM(int i) {
        this.a = i;
    }

    public static void a(StringBuilder sb, String str) {
        sb.append('\"');
        for (int i = 0; i < str.length(); i++) {
            char cCharAt = str.charAt(i);
            if (cCharAt == '\n') {
                sb.append("\\n");
            } else if (cCharAt == '\r') {
                sb.append("\\r");
            } else if (cCharAt == '\\') {
                sb.append("\\\\");
            } else if (cCharAt == '\"') {
                sb.append("\\\"");
            } else if (cCharAt < ' ' || cCharAt > 127) {
                sb.append("\\u");
                if (cCharAt < 16) {
                    sb.append("000");
                } else if (cCharAt < 256) {
                    sb.append(TarConstants.VERSION_POSIX);
                } else if (cCharAt < 4096) {
                    sb.append(ExternalAnnotationProvider.NULLABLE);
                }
                sb.append(Integer.toString(cCharAt, 16));
            } else {
                sb.append(cCharAt);
            }
        }
        sb.append('\"');
    }

    public abstract ZY a();

    public abstract ZY a(int i, C00 c00, String str, boolean z);

    public abstract ZY a(int i, C00 c00, C3263wA[] c3263wAArr, C3263wA[] c3263wAArr2, int[] iArr, String str, boolean z);

    public abstract ZY a(int i, String str, boolean z);

    public abstract ZY a(String str);

    public abstract ZY a(String str, String str2);

    public abstract ZY a(String str, boolean z);

    public abstract void a(int i, int i2, Object[] objArr, int i3, Object[] objArr2);

    public abstract void a(int i, String str, String str2, String str3, boolean z);

    public abstract void a(String str, Object obj);

    public abstract void a(String str, String str2, String str3);
}

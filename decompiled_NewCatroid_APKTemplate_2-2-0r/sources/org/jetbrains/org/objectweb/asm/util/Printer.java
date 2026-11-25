package org.jetbrains.org.objectweb.asm.util;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import net.mgsx.gltf.scene3d.attributes.PBRFloatAttribute;
import org.apache.commons.compress.archivers.tar.TarConstants;
import org.eclipse.jdt.internal.compiler.classfmt.ExternalAnnotationProvider;
import org.jetbrains.org.objectweb.asm.Attribute;
import org.jetbrains.org.objectweb.asm.Handle;
import org.jetbrains.org.objectweb.asm.Label;
import org.jetbrains.org.objectweb.asm.TypePath;

/* JADX INFO: loaded from: classes6.dex */
public abstract class Printer {
    protected final int api;
    protected final StringBuilder stringBuilder = new StringBuilder();
    public final List<Object> text = new ArrayList();
    public static final String[] OPCODES = {"NOP", "ACONST_NULL", "ICONST_M1", "ICONST_0", "ICONST_1", "ICONST_2", "ICONST_3", "ICONST_4", "ICONST_5", "LCONST_0", "LCONST_1", "FCONST_0", "FCONST_1", "FCONST_2", "DCONST_0", "DCONST_1", "BIPUSH", "SIPUSH", "LDC", "LDC_W", "LDC2_W", "ILOAD", "LLOAD", "FLOAD", "DLOAD", "ALOAD", "ILOAD_0", "ILOAD_1", "ILOAD_2", "ILOAD_3", "LLOAD_0", "LLOAD_1", "LLOAD_2", "LLOAD_3", "FLOAD_0", "FLOAD_1", "FLOAD_2", "FLOAD_3", "DLOAD_0", "DLOAD_1", "DLOAD_2", "DLOAD_3", "ALOAD_0", "ALOAD_1", "ALOAD_2", "ALOAD_3", "IALOAD", "LALOAD", "FALOAD", "DALOAD", "AALOAD", "BALOAD", "CALOAD", "SALOAD", "ISTORE", "LSTORE", "FSTORE", "DSTORE", "ASTORE", "ISTORE_0", "ISTORE_1", "ISTORE_2", "ISTORE_3", "LSTORE_0", "LSTORE_1", "LSTORE_2", "LSTORE_3", "FSTORE_0", "FSTORE_1", "FSTORE_2", "FSTORE_3", "DSTORE_0", "DSTORE_1", "DSTORE_2", "DSTORE_3", "ASTORE_0", "ASTORE_1", "ASTORE_2", "ASTORE_3", "IASTORE", "LASTORE", "FASTORE", "DASTORE", "AASTORE", "BASTORE", "CASTORE", "SASTORE", "POP", "POP2", "DUP", "DUP_X1", "DUP_X2", "DUP2", "DUP2_X1", "DUP2_X2", "SWAP", "IADD", "LADD", "FADD", "DADD", "ISUB", "LSUB", "FSUB", "DSUB", "IMUL", "LMUL", "FMUL", "DMUL", "IDIV", "LDIV", "FDIV", "DDIV", "IREM", "LREM", "FREM", "DREM", "INEG", "LNEG", "FNEG", "DNEG", "ISHL", "LSHL", "ISHR", "LSHR", "IUSHR", "LUSHR", "IAND", "LAND", PBRFloatAttribute.IORAlias, "LOR", "IXOR", "LXOR", "IINC", "I2L", "I2F", "I2D", "L2I", "L2F", "L2D", "F2I", "F2L", "F2D", "D2I", "D2L", "D2F", "I2B", "I2C", "I2S", "LCMP", "FCMPL", "FCMPG", "DCMPL", "DCMPG", "IFEQ", "IFNE", "IFLT", "IFGE", "IFGT", "IFLE", "IF_ICMPEQ", "IF_ICMPNE", "IF_ICMPLT", "IF_ICMPGE", "IF_ICMPGT", "IF_ICMPLE", "IF_ACMPEQ", "IF_ACMPNE", "GOTO", "JSR", "RET", "TABLESWITCH", "LOOKUPSWITCH", "IRETURN", "LRETURN", "FRETURN", "DRETURN", "ARETURN", "RETURN", "GETSTATIC", "PUTSTATIC", "GETFIELD", "PUTFIELD", "INVOKEVIRTUAL", "INVOKESPECIAL", "INVOKESTATIC", "INVOKEINTERFACE", "INVOKEDYNAMIC", "NEW", "NEWARRAY", "ANEWARRAY", "ARRAYLENGTH", "ATHROW", "CHECKCAST", "INSTANCEOF", "MONITORENTER", "MONITOREXIT", "WIDE", "MULTIANEWARRAY", "IFNULL", "IFNONNULL"};
    public static final String[] TYPES = {"", "", "", "", "T_BOOLEAN", "T_CHAR", "T_FLOAT", "T_DOUBLE", "T_BYTE", "T_SHORT", "T_INT", "T_LONG"};
    public static final String[] HANDLE_TAG = {"", "H_GETFIELD", "H_GETSTATIC", "H_PUTFIELD", "H_PUTSTATIC", "H_INVOKEVIRTUAL", "H_INVOKESTATIC", "H_INVOKESPECIAL", "H_NEWINVOKESPECIAL", "H_INVOKEINTERFACE"};

    public abstract void visit(int i, int i2, String str, String str2, String str3, String[] strArr);

    public abstract void visit(String str, Object obj);

    public abstract Printer visitAnnotation(String str, String str2);

    public abstract Printer visitAnnotationDefault();

    public abstract void visitAnnotationEnd();

    public abstract Printer visitArray(String str);

    public abstract Printer visitClassAnnotation(String str, boolean z);

    public abstract void visitClassAttribute(Attribute attribute);

    public abstract void visitClassEnd();

    public abstract void visitCode();

    public abstract void visitEnum(String str, String str2, String str3);

    public abstract Printer visitField(int i, String str, String str2, String str3, Object obj);

    public abstract Printer visitFieldAnnotation(String str, boolean z);

    public abstract void visitFieldAttribute(Attribute attribute);

    public abstract void visitFieldEnd();

    public abstract void visitFieldInsn(int i, String str, String str2, String str3);

    public abstract void visitFrame(int i, int i2, Object[] objArr, int i3, Object[] objArr2);

    public abstract void visitIincInsn(int i, int i2);

    public abstract void visitInnerClass(String str, String str2, String str3, int i);

    public abstract void visitInsn(int i);

    public abstract void visitIntInsn(int i, int i2);

    public abstract void visitInvokeDynamicInsn(String str, String str2, Handle handle, Object... objArr);

    public abstract void visitJumpInsn(int i, Label label);

    public abstract void visitLabel(Label label);

    public abstract void visitLdcInsn(Object obj);

    public abstract void visitLineNumber(int i, Label label);

    public abstract void visitLocalVariable(String str, String str2, String str3, Label label, Label label2, int i);

    public abstract void visitLookupSwitchInsn(Label label, int[] iArr, Label[] labelArr);

    public abstract void visitMaxs(int i, int i2);

    public abstract Printer visitMethod(int i, String str, String str2, String str3, String[] strArr);

    public abstract Printer visitMethodAnnotation(String str, boolean z);

    public abstract void visitMethodAttribute(Attribute attribute);

    public abstract void visitMethodEnd();

    public abstract void visitMultiANewArrayInsn(String str, int i);

    public abstract void visitOuterClass(String str, String str2, String str3);

    public abstract Printer visitParameterAnnotation(int i, String str, boolean z);

    public abstract void visitSource(String str, String str2);

    public abstract void visitTableSwitchInsn(int i, int i2, Label label, Label... labelArr);

    public abstract void visitTryCatchBlock(Label label, Label label2, Label label3, String str);

    public abstract void visitTypeInsn(int i, String str);

    public abstract void visitVarInsn(int i, int i2);

    protected Printer(int i) {
        this.api = i;
    }

    public Printer visitModule(String str, int i, String str2) {
        throw new UnsupportedOperationException("Must be overridden");
    }

    public void visitNestHost(String str) {
        throw new UnsupportedOperationException("Must be overridden");
    }

    public Printer visitClassTypeAnnotation(int i, TypePath typePath, String str, boolean z) {
        throw new UnsupportedOperationException("Must be overridden");
    }

    public void visitNestMember(String str) {
        throw new UnsupportedOperationException("Must be overridden");
    }

    public void visitPermittedSubclass(String str) {
        throw new UnsupportedOperationException("Must be overridden");
    }

    public Printer visitRecordComponent(String str, String str2, String str3) {
        throw new UnsupportedOperationException("Must be overridden");
    }

    public void visitMainClass(String str) {
        throw new UnsupportedOperationException("Must be overridden");
    }

    public void visitPackage(String str) {
        throw new UnsupportedOperationException("Must be overridden");
    }

    public void visitRequire(String str, int i, String str2) {
        throw new UnsupportedOperationException("Must be overridden");
    }

    public void visitExport(String str, int i, String... strArr) {
        throw new UnsupportedOperationException("Must be overridden");
    }

    public void visitOpen(String str, int i, String... strArr) {
        throw new UnsupportedOperationException("Must be overridden");
    }

    public void visitUse(String str) {
        throw new UnsupportedOperationException("Must be overridden");
    }

    public void visitProvide(String str, String... strArr) {
        throw new UnsupportedOperationException("Must be overridden");
    }

    public void visitModuleEnd() {
        throw new UnsupportedOperationException("Must be overridden");
    }

    public Printer visitRecordComponentAnnotation(String str, boolean z) {
        throw new UnsupportedOperationException("Must be overridden");
    }

    public Printer visitRecordComponentTypeAnnotation(int i, TypePath typePath, String str, boolean z) {
        throw new UnsupportedOperationException("Must be overridden");
    }

    public void visitRecordComponentAttribute(Attribute attribute) {
        throw new UnsupportedOperationException("Must be overridden");
    }

    public void visitRecordComponentEnd() {
        throw new UnsupportedOperationException("Must be overridden");
    }

    public Printer visitFieldTypeAnnotation(int i, TypePath typePath, String str, boolean z) {
        throw new UnsupportedOperationException("Must be overridden");
    }

    public void visitParameter(String str, int i) {
        throw new UnsupportedOperationException("Must be overridden");
    }

    public Printer visitMethodTypeAnnotation(int i, TypePath typePath, String str, boolean z) {
        throw new UnsupportedOperationException("Must be overridden");
    }

    public Printer visitAnnotableParameterCount(int i, boolean z) {
        throw new UnsupportedOperationException("Must be overridden");
    }

    @Deprecated
    public void visitMethodInsn(int i, String str, String str2, String str3) {
        visitMethodInsn(i, str, str2, str3, i == 185);
    }

    public void visitMethodInsn(int i, String str, String str2, String str3, boolean z) {
        throw new UnsupportedOperationException("Must be overridden");
    }

    public Printer visitInsnAnnotation(int i, TypePath typePath, String str, boolean z) {
        throw new UnsupportedOperationException("Must be overridden");
    }

    public Printer visitTryCatchAnnotation(int i, TypePath typePath, String str, boolean z) {
        throw new UnsupportedOperationException("Must be overridden");
    }

    public Printer visitLocalVariableAnnotation(int i, TypePath typePath, Label[] labelArr, Label[] labelArr2, int[] iArr, String str, boolean z) {
        throw new UnsupportedOperationException("Must be overridden");
    }

    public List<Object> getText() {
        return this.text;
    }

    public void print(PrintWriter printWriter) {
        printList(printWriter, this.text);
    }

    static void printList(PrintWriter printWriter, List<?> list) {
        for (Object obj : list) {
            if (obj instanceof List) {
                printList(printWriter, (List) obj);
            } else {
                printWriter.print(obj.toString());
            }
        }
    }

    public static void appendString(StringBuilder sb, String str) {
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
}

package org.jetbrains.org.objectweb.asm.util;

import androidx.exifinterface.media.ExifInterface;
import com.badlogic.gdx.graphics.GL20;
import com.huawei.hms.ml.language.common.utils.Constant;
import io.netty.util.internal.StringUtil;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.bytebuddy.implementation.auxiliary.TypeProxy;
import org.eclipse.jdt.internal.compiler.classfmt.ExternalAnnotationProvider;
import org.eclipse.jdt.internal.compiler.util.Util;
import org.jetbrains.org.objectweb.asm.Attribute;
import org.jetbrains.org.objectweb.asm.Handle;
import org.jetbrains.org.objectweb.asm.Label;
import org.jetbrains.org.objectweb.asm.Type;
import org.jetbrains.org.objectweb.asm.TypePath;
import org.jetbrains.org.objectweb.asm.TypeReference;
import org.jetbrains.org.objectweb.asm.signature.SignatureReader;

/* JADX INFO: loaded from: classes6.dex */
public class Textifier extends Printer {
    private static final List<String> FRAME_TYPES = Collections.unmodifiableList(Arrays.asList(ExifInterface.GPS_DIRECTION_TRUE, "I", "F", "D", "J", "N", "U"));
    private int access;
    protected Map<Label, String> labelNames;
    protected String ltab;
    private int numAnnotationValues;
    protected String tab;
    protected String tab2;
    protected String tab3;

    @Override // org.jetbrains.org.objectweb.asm.util.Printer
    public void visitAnnotationEnd() {
    }

    @Override // org.jetbrains.org.objectweb.asm.util.Printer
    public void visitCode() {
    }

    @Override // org.jetbrains.org.objectweb.asm.util.Printer
    public void visitFieldEnd() {
    }

    @Override // org.jetbrains.org.objectweb.asm.util.Printer
    public void visitMethodEnd() {
    }

    @Override // org.jetbrains.org.objectweb.asm.util.Printer
    public void visitModuleEnd() {
    }

    @Override // org.jetbrains.org.objectweb.asm.util.Printer
    public void visitRecordComponentEnd() {
    }

    public Textifier() {
        this(589824);
        if (getClass() != Textifier.class) {
            throw new IllegalStateException();
        }
    }

    protected Textifier(int i) {
        super(i);
        this.tab = "  ";
        this.tab2 = "    ";
        this.tab3 = "      ";
        this.ltab = "   ";
    }

    @Override // org.jetbrains.org.objectweb.asm.util.Printer
    public void visit(int i, int i2, String str, String str2, String str3, String[] strArr) {
        if ((32768 & i2) != 0) {
            return;
        }
        this.access = i2;
        this.stringBuilder.setLength(0);
        this.stringBuilder.append("// class version ").append(65535 & i).append('.').append(i >>> 16).append(" (").append(i).append(")\n");
        if ((131072 & i2) != 0) {
            this.stringBuilder.append("// DEPRECATED\n");
        }
        if ((65536 & i2) != 0) {
            this.stringBuilder.append("// RECORD\n");
        }
        appendRawAccess(i2);
        appendDescriptor(5, str2);
        if (str2 != null) {
            appendJavaDeclaration(str, str2);
        }
        appendAccess((-32801) & i2);
        if ((i2 & 8192) != 0) {
            this.stringBuilder.append("@interface ");
        } else if ((i2 & 512) != 0) {
            this.stringBuilder.append("interface ");
        } else if ((i2 & 16384) == 0) {
            this.stringBuilder.append(ExternalAnnotationProvider.CLASS_PREFIX);
        }
        appendDescriptor(0, str);
        if (str3 != null && !TypeProxy.SilentConstruction.Appender.JAVA_LANG_OBJECT_INTERNAL_NAME.equals(str3)) {
            this.stringBuilder.append(" extends ");
            appendDescriptor(0, str3);
        }
        if (strArr != null && strArr.length > 0) {
            this.stringBuilder.append(" implements ");
            for (int i3 = 0; i3 < strArr.length; i3++) {
                appendDescriptor(0, strArr[i3]);
                if (i3 != strArr.length - 1) {
                    this.stringBuilder.append(StringUtil.SPACE);
                }
            }
        }
        this.stringBuilder.append(" {\n\n");
        this.text.add(this.stringBuilder.toString());
    }

    @Override // org.jetbrains.org.objectweb.asm.util.Printer
    public void visitSource(String str, String str2) {
        this.stringBuilder.setLength(0);
        if (str != null) {
            this.stringBuilder.append(this.tab).append("// compiled from: ").append(str).append('\n');
        }
        if (str2 != null) {
            this.stringBuilder.append(this.tab).append("// debug info: ").append(str2).append('\n');
        }
        if (this.stringBuilder.length() > 0) {
            this.text.add(this.stringBuilder.toString());
        }
    }

    @Override // org.jetbrains.org.objectweb.asm.util.Printer
    public Printer visitModule(String str, int i, String str2) {
        this.stringBuilder.setLength(0);
        if ((i & 32) != 0) {
            this.stringBuilder.append("open ");
        }
        this.stringBuilder.append("module ").append(str).append(" { ").append(str2 == null ? "" : "// " + str2).append("\n\n");
        this.text.add(this.stringBuilder.toString());
        return addNewTextifier(null);
    }

    @Override // org.jetbrains.org.objectweb.asm.util.Printer
    public void visitNestHost(String str) {
        this.stringBuilder.setLength(0);
        this.stringBuilder.append(this.tab).append("NESTHOST ");
        appendDescriptor(0, str);
        this.stringBuilder.append('\n');
        this.text.add(this.stringBuilder.toString());
    }

    @Override // org.jetbrains.org.objectweb.asm.util.Printer
    public void visitOuterClass(String str, String str2, String str3) {
        this.stringBuilder.setLength(0);
        this.stringBuilder.append(this.tab).append("OUTERCLASS ");
        appendDescriptor(0, str);
        this.stringBuilder.append(StringUtil.SPACE);
        if (str2 != null) {
            this.stringBuilder.append(str2).append(StringUtil.SPACE);
        }
        appendDescriptor(3, str3);
        this.stringBuilder.append('\n');
        this.text.add(this.stringBuilder.toString());
    }

    @Override // org.jetbrains.org.objectweb.asm.util.Printer
    public Textifier visitClassAnnotation(String str, boolean z) {
        this.text.add("\n");
        return visitAnnotation(str, z);
    }

    @Override // org.jetbrains.org.objectweb.asm.util.Printer
    public Printer visitClassTypeAnnotation(int i, TypePath typePath, String str, boolean z) {
        this.text.add("\n");
        return visitTypeAnnotation(i, typePath, str, z);
    }

    @Override // org.jetbrains.org.objectweb.asm.util.Printer
    public void visitClassAttribute(Attribute attribute) {
        this.text.add("\n");
        visitAttribute(attribute);
    }

    @Override // org.jetbrains.org.objectweb.asm.util.Printer
    public void visitNestMember(String str) {
        this.stringBuilder.setLength(0);
        this.stringBuilder.append(this.tab).append("NESTMEMBER ");
        appendDescriptor(0, str);
        this.stringBuilder.append('\n');
        this.text.add(this.stringBuilder.toString());
    }

    @Override // org.jetbrains.org.objectweb.asm.util.Printer
    public void visitPermittedSubclass(String str) {
        this.stringBuilder.setLength(0);
        this.stringBuilder.append(this.tab).append("PERMITTEDSUBCLASS ");
        appendDescriptor(0, str);
        this.stringBuilder.append('\n');
        this.text.add(this.stringBuilder.toString());
    }

    @Override // org.jetbrains.org.objectweb.asm.util.Printer
    public void visitInnerClass(String str, String str2, String str3, int i) {
        this.stringBuilder.setLength(0);
        this.stringBuilder.append(this.tab);
        appendRawAccess(i & (-33));
        this.stringBuilder.append(this.tab);
        appendAccess(i);
        this.stringBuilder.append("INNERCLASS ");
        appendDescriptor(0, str);
        this.stringBuilder.append(StringUtil.SPACE);
        appendDescriptor(0, str2);
        this.stringBuilder.append(StringUtil.SPACE);
        appendDescriptor(0, str3);
        this.stringBuilder.append('\n');
        this.text.add(this.stringBuilder.toString());
    }

    @Override // org.jetbrains.org.objectweb.asm.util.Printer
    public Printer visitRecordComponent(String str, String str2, String str3) {
        this.stringBuilder.setLength(0);
        this.stringBuilder.append(this.tab).append("RECORDCOMPONENT ");
        if (str3 != null) {
            this.stringBuilder.append(this.tab);
            appendDescriptor(2, str3);
            this.stringBuilder.append(this.tab);
            appendJavaDeclaration(str, str3);
        }
        this.stringBuilder.append(this.tab);
        appendDescriptor(1, str2);
        this.stringBuilder.append(StringUtil.SPACE).append(str);
        this.stringBuilder.append('\n');
        this.text.add(this.stringBuilder.toString());
        return addNewTextifier(null);
    }

    @Override // org.jetbrains.org.objectweb.asm.util.Printer
    public Textifier visitField(int i, String str, String str2, String str3, Object obj) {
        this.stringBuilder.setLength(0);
        this.stringBuilder.append('\n');
        if ((131072 & i) != 0) {
            this.stringBuilder.append(this.tab).append("// DEPRECATED\n");
        }
        this.stringBuilder.append(this.tab);
        appendRawAccess(i);
        if (str3 != null) {
            this.stringBuilder.append(this.tab);
            appendDescriptor(2, str3);
            this.stringBuilder.append(this.tab);
            appendJavaDeclaration(str, str3);
        }
        this.stringBuilder.append(this.tab);
        appendAccess(i);
        appendDescriptor(1, str2);
        this.stringBuilder.append(StringUtil.SPACE).append(str);
        if (obj != null) {
            this.stringBuilder.append(" = ");
            if (obj instanceof String) {
                this.stringBuilder.append('\"').append(obj).append('\"');
            } else {
                this.stringBuilder.append(obj);
            }
        }
        this.stringBuilder.append('\n');
        this.text.add(this.stringBuilder.toString());
        return addNewTextifier(null);
    }

    @Override // org.jetbrains.org.objectweb.asm.util.Printer
    public Textifier visitMethod(int i, String str, String str2, String str3, String[] strArr) {
        this.stringBuilder.setLength(0);
        this.stringBuilder.append('\n');
        if ((131072 & i) != 0) {
            this.stringBuilder.append(this.tab).append("// DEPRECATED\n");
        }
        this.stringBuilder.append(this.tab);
        appendRawAccess(i);
        if (str3 != null) {
            this.stringBuilder.append(this.tab);
            appendDescriptor(4, str3);
            this.stringBuilder.append(this.tab);
            appendJavaDeclaration(str, str3);
        }
        this.stringBuilder.append(this.tab);
        appendAccess(i & (-193));
        if ((i & 256) != 0) {
            this.stringBuilder.append("native ");
        }
        if ((i & 128) != 0) {
            this.stringBuilder.append("varargs ");
        }
        if ((i & 64) != 0) {
            this.stringBuilder.append("bridge ");
        }
        if ((this.access & 512) != 0 && (i & GL20.GL_FRONT_AND_BACK) == 0) {
            this.stringBuilder.append("default ");
        }
        this.stringBuilder.append(str);
        appendDescriptor(3, str2);
        if (strArr != null && strArr.length > 0) {
            this.stringBuilder.append(" throws ");
            for (String str4 : strArr) {
                appendDescriptor(0, str4);
                this.stringBuilder.append(StringUtil.SPACE);
            }
        }
        this.stringBuilder.append('\n');
        this.text.add(this.stringBuilder.toString());
        return addNewTextifier(null);
    }

    @Override // org.jetbrains.org.objectweb.asm.util.Printer
    public void visitClassEnd() {
        this.text.add("}\n");
    }

    @Override // org.jetbrains.org.objectweb.asm.util.Printer
    public void visitMainClass(String str) {
        this.stringBuilder.setLength(0);
        this.stringBuilder.append("  // main class ").append(str).append('\n');
        this.text.add(this.stringBuilder.toString());
    }

    @Override // org.jetbrains.org.objectweb.asm.util.Printer
    public void visitPackage(String str) {
        this.stringBuilder.setLength(0);
        this.stringBuilder.append("  // package ").append(str).append('\n');
        this.text.add(this.stringBuilder.toString());
    }

    @Override // org.jetbrains.org.objectweb.asm.util.Printer
    public void visitRequire(String str, int i, String str2) {
        this.stringBuilder.setLength(0);
        this.stringBuilder.append(this.tab).append("requires ");
        if ((i & 32) != 0) {
            this.stringBuilder.append("transitive ");
        }
        if ((i & 64) != 0) {
            this.stringBuilder.append("static ");
        }
        this.stringBuilder.append(str).append(';');
        appendRawAccess(i);
        if (str2 != null) {
            this.stringBuilder.append("  // version ").append(str2).append('\n');
        }
        this.text.add(this.stringBuilder.toString());
    }

    @Override // org.jetbrains.org.objectweb.asm.util.Printer
    public void visitExport(String str, int i, String... strArr) {
        visitExportOrOpen("exports ", str, i, strArr);
    }

    @Override // org.jetbrains.org.objectweb.asm.util.Printer
    public void visitOpen(String str, int i, String... strArr) {
        visitExportOrOpen("opens ", str, i, strArr);
    }

    private void visitExportOrOpen(String str, String str2, int i, String... strArr) {
        int i2 = 0;
        this.stringBuilder.setLength(0);
        this.stringBuilder.append(this.tab).append(str);
        this.stringBuilder.append(str2);
        if (strArr != null && strArr.length > 0) {
            this.stringBuilder.append(" to");
        } else {
            this.stringBuilder.append(';');
        }
        appendRawAccess(i);
        if (strArr != null && strArr.length > 0) {
            while (i2 < strArr.length) {
                this.stringBuilder.append(this.tab2).append(strArr[i2]);
                this.stringBuilder.append(i2 != strArr.length + (-1) ? ",\n" : ";\n");
                i2++;
            }
        }
        this.text.add(this.stringBuilder.toString());
    }

    @Override // org.jetbrains.org.objectweb.asm.util.Printer
    public void visitUse(String str) {
        this.stringBuilder.setLength(0);
        this.stringBuilder.append(this.tab).append("uses ");
        appendDescriptor(0, str);
        this.stringBuilder.append(";\n");
        this.text.add(this.stringBuilder.toString());
    }

    @Override // org.jetbrains.org.objectweb.asm.util.Printer
    public void visitProvide(String str, String... strArr) {
        this.stringBuilder.setLength(0);
        this.stringBuilder.append(this.tab).append("provides ");
        appendDescriptor(0, str);
        this.stringBuilder.append(" with\n");
        int i = 0;
        while (i < strArr.length) {
            this.stringBuilder.append(this.tab2);
            appendDescriptor(0, strArr[i]);
            this.stringBuilder.append(i != strArr.length + (-1) ? ",\n" : ";\n");
            i++;
        }
        this.text.add(this.stringBuilder.toString());
    }

    @Override // org.jetbrains.org.objectweb.asm.util.Printer
    public void visit(String str, Object obj) {
        visitAnnotationValue(str);
        if (obj instanceof String) {
            visitString((String) obj);
        } else if (obj instanceof Type) {
            visitType((Type) obj);
        } else if (obj instanceof Byte) {
            visitByte(((Byte) obj).byteValue());
        } else if (obj instanceof Boolean) {
            visitBoolean(((Boolean) obj).booleanValue());
        } else if (obj instanceof Short) {
            visitShort(((Short) obj).shortValue());
        } else if (obj instanceof Character) {
            visitChar(((Character) obj).charValue());
        } else if (obj instanceof Integer) {
            visitInt(((Integer) obj).intValue());
        } else if (obj instanceof Float) {
            visitFloat(((Float) obj).floatValue());
        } else if (obj instanceof Long) {
            visitLong(((Long) obj).longValue());
        } else if (obj instanceof Double) {
            visitDouble(((Double) obj).doubleValue());
        } else if (obj.getClass().isArray()) {
            this.stringBuilder.append('{');
            int i = 0;
            if (obj instanceof byte[]) {
                byte[] bArr = (byte[]) obj;
                while (i < bArr.length) {
                    maybeAppendComma(i);
                    visitByte(bArr[i]);
                    i++;
                }
            } else if (obj instanceof boolean[]) {
                boolean[] zArr = (boolean[]) obj;
                while (i < zArr.length) {
                    maybeAppendComma(i);
                    visitBoolean(zArr[i]);
                    i++;
                }
            } else if (obj instanceof short[]) {
                short[] sArr = (short[]) obj;
                while (i < sArr.length) {
                    maybeAppendComma(i);
                    visitShort(sArr[i]);
                    i++;
                }
            } else if (obj instanceof char[]) {
                char[] cArr = (char[]) obj;
                while (i < cArr.length) {
                    maybeAppendComma(i);
                    visitChar(cArr[i]);
                    i++;
                }
            } else if (obj instanceof int[]) {
                int[] iArr = (int[]) obj;
                while (i < iArr.length) {
                    maybeAppendComma(i);
                    visitInt(iArr[i]);
                    i++;
                }
            } else if (obj instanceof long[]) {
                long[] jArr = (long[]) obj;
                while (i < jArr.length) {
                    maybeAppendComma(i);
                    visitLong(jArr[i]);
                    i++;
                }
            } else if (obj instanceof float[]) {
                float[] fArr = (float[]) obj;
                while (i < fArr.length) {
                    maybeAppendComma(i);
                    visitFloat(fArr[i]);
                    i++;
                }
            } else if (obj instanceof double[]) {
                double[] dArr = (double[]) obj;
                while (i < dArr.length) {
                    maybeAppendComma(i);
                    visitDouble(dArr[i]);
                    i++;
                }
            }
            this.stringBuilder.append('}');
        }
        this.text.add(this.stringBuilder.toString());
    }

    private void visitInt(int i) {
        this.stringBuilder.append(i);
    }

    private void visitLong(long j) {
        this.stringBuilder.append(j).append('L');
    }

    private void visitFloat(float f) {
        this.stringBuilder.append(f).append(Util.C_FLOAT);
    }

    private void visitDouble(double d) {
        this.stringBuilder.append(d).append(Util.C_DOUBLE);
    }

    private void visitChar(char c) {
        this.stringBuilder.append("(char)").append((int) c);
    }

    private void visitShort(short s) {
        this.stringBuilder.append("(short)").append((int) s);
    }

    private void visitByte(byte b) {
        this.stringBuilder.append("(byte)").append((int) b);
    }

    private void visitBoolean(boolean z) {
        this.stringBuilder.append(z);
    }

    private void visitString(String str) {
        appendString(this.stringBuilder, str);
    }

    private void visitType(Type type) {
        this.stringBuilder.append(type.getClassName()).append(".class");
    }

    @Override // org.jetbrains.org.objectweb.asm.util.Printer
    public void visitEnum(String str, String str2, String str3) {
        visitAnnotationValue(str);
        appendDescriptor(1, str2);
        this.stringBuilder.append('.').append(str3);
        this.text.add(this.stringBuilder.toString());
    }

    @Override // org.jetbrains.org.objectweb.asm.util.Printer
    public Textifier visitAnnotation(String str, String str2) {
        visitAnnotationValue(str);
        this.stringBuilder.append('@');
        appendDescriptor(1, str2);
        this.stringBuilder.append('(');
        this.text.add(this.stringBuilder.toString());
        return addNewTextifier(Constant.AFTER_QUTO);
    }

    @Override // org.jetbrains.org.objectweb.asm.util.Printer
    public Textifier visitArray(String str) {
        visitAnnotationValue(str);
        this.stringBuilder.append('{');
        this.text.add(this.stringBuilder.toString());
        return addNewTextifier("}");
    }

    private void visitAnnotationValue(String str) {
        this.stringBuilder.setLength(0);
        int i = this.numAnnotationValues;
        this.numAnnotationValues = i + 1;
        maybeAppendComma(i);
        if (str != null) {
            this.stringBuilder.append(str).append('=');
        }
    }

    @Override // org.jetbrains.org.objectweb.asm.util.Printer
    public Textifier visitRecordComponentAnnotation(String str, boolean z) {
        return visitAnnotation(str, z);
    }

    @Override // org.jetbrains.org.objectweb.asm.util.Printer
    public Printer visitRecordComponentTypeAnnotation(int i, TypePath typePath, String str, boolean z) {
        return visitTypeAnnotation(i, typePath, str, z);
    }

    @Override // org.jetbrains.org.objectweb.asm.util.Printer
    public void visitRecordComponentAttribute(Attribute attribute) {
        visitAttribute(attribute);
    }

    @Override // org.jetbrains.org.objectweb.asm.util.Printer
    public Textifier visitFieldAnnotation(String str, boolean z) {
        return visitAnnotation(str, z);
    }

    @Override // org.jetbrains.org.objectweb.asm.util.Printer
    public Printer visitFieldTypeAnnotation(int i, TypePath typePath, String str, boolean z) {
        return visitTypeAnnotation(i, typePath, str, z);
    }

    @Override // org.jetbrains.org.objectweb.asm.util.Printer
    public void visitFieldAttribute(Attribute attribute) {
        visitAttribute(attribute);
    }

    @Override // org.jetbrains.org.objectweb.asm.util.Printer
    public void visitParameter(String str, int i) {
        this.stringBuilder.setLength(0);
        this.stringBuilder.append(this.tab2).append("// parameter ");
        appendAccess(i);
        StringBuilder sbAppend = this.stringBuilder.append(StringUtil.SPACE);
        if (str == null) {
            str = "<no name>";
        }
        sbAppend.append(str).append('\n');
        this.text.add(this.stringBuilder.toString());
    }

    @Override // org.jetbrains.org.objectweb.asm.util.Printer
    public Textifier visitAnnotationDefault() {
        this.text.add(this.tab2 + "default=");
        return addNewTextifier("\n");
    }

    @Override // org.jetbrains.org.objectweb.asm.util.Printer
    public Textifier visitMethodAnnotation(String str, boolean z) {
        return visitAnnotation(str, z);
    }

    @Override // org.jetbrains.org.objectweb.asm.util.Printer
    public Printer visitMethodTypeAnnotation(int i, TypePath typePath, String str, boolean z) {
        return visitTypeAnnotation(i, typePath, str, z);
    }

    @Override // org.jetbrains.org.objectweb.asm.util.Printer
    public Textifier visitAnnotableParameterCount(int i, boolean z) {
        this.stringBuilder.setLength(0);
        this.stringBuilder.append(this.tab2).append("// annotable parameter count: ");
        this.stringBuilder.append(i);
        this.stringBuilder.append(z ? " (visible)\n" : " (invisible)\n");
        this.text.add(this.stringBuilder.toString());
        return this;
    }

    @Override // org.jetbrains.org.objectweb.asm.util.Printer
    public Textifier visitParameterAnnotation(int i, String str, boolean z) {
        this.stringBuilder.setLength(0);
        this.stringBuilder.append(this.tab2).append('@');
        appendDescriptor(1, str);
        this.stringBuilder.append('(');
        this.text.add(this.stringBuilder.toString());
        this.stringBuilder.setLength(0);
        this.stringBuilder.append(z ? ") // parameter " : ") // invisible, parameter ").append(i).append('\n');
        return addNewTextifier(this.stringBuilder.toString());
    }

    @Override // org.jetbrains.org.objectweb.asm.util.Printer
    public void visitMethodAttribute(Attribute attribute) {
        visitAttribute(attribute);
    }

    @Override // org.jetbrains.org.objectweb.asm.util.Printer
    public void visitFrame(int i, int i2, Object[] objArr, int i3, Object[] objArr2) {
        this.stringBuilder.setLength(0);
        this.stringBuilder.append(this.ltab);
        this.stringBuilder.append("FRAME ");
        if (i == -1 || i == 0) {
            this.stringBuilder.append("FULL [");
            appendFrameTypes(i2, objArr);
            this.stringBuilder.append("] [");
            appendFrameTypes(i3, objArr2);
            this.stringBuilder.append(']');
        } else if (i == 1) {
            this.stringBuilder.append("APPEND [");
            appendFrameTypes(i2, objArr);
            this.stringBuilder.append(']');
        } else if (i == 2) {
            this.stringBuilder.append("CHOP ").append(i2);
        } else if (i == 3) {
            this.stringBuilder.append("SAME");
        } else if (i == 4) {
            this.stringBuilder.append("SAME1 ");
            appendFrameTypes(1, objArr2);
        } else {
            throw new IllegalArgumentException();
        }
        this.stringBuilder.append('\n');
        this.text.add(this.stringBuilder.toString());
    }

    @Override // org.jetbrains.org.objectweb.asm.util.Printer
    public void visitInsn(int i) {
        this.stringBuilder.setLength(0);
        this.stringBuilder.append(this.tab2).append(OPCODES[i]).append('\n');
        this.text.add(this.stringBuilder.toString());
    }

    @Override // org.jetbrains.org.objectweb.asm.util.Printer
    public void visitIntInsn(int i, int i2) {
        this.stringBuilder.setLength(0);
        this.stringBuilder.append(this.tab2).append(OPCODES[i]).append(StringUtil.SPACE).append(i == 188 ? TYPES[i2] : Integer.toString(i2)).append('\n');
        this.text.add(this.stringBuilder.toString());
    }

    @Override // org.jetbrains.org.objectweb.asm.util.Printer
    public void visitVarInsn(int i, int i2) {
        this.stringBuilder.setLength(0);
        this.stringBuilder.append(this.tab2).append(OPCODES[i]).append(StringUtil.SPACE).append(i2).append('\n');
        this.text.add(this.stringBuilder.toString());
    }

    @Override // org.jetbrains.org.objectweb.asm.util.Printer
    public void visitTypeInsn(int i, String str) {
        this.stringBuilder.setLength(0);
        this.stringBuilder.append(this.tab2).append(OPCODES[i]).append(StringUtil.SPACE);
        appendDescriptor(0, str);
        this.stringBuilder.append('\n');
        this.text.add(this.stringBuilder.toString());
    }

    @Override // org.jetbrains.org.objectweb.asm.util.Printer
    public void visitFieldInsn(int i, String str, String str2, String str3) {
        this.stringBuilder.setLength(0);
        this.stringBuilder.append(this.tab2).append(OPCODES[i]).append(StringUtil.SPACE);
        appendDescriptor(0, str);
        this.stringBuilder.append('.').append(str2).append(" : ");
        appendDescriptor(1, str3);
        this.stringBuilder.append('\n');
        this.text.add(this.stringBuilder.toString());
    }

    @Override // org.jetbrains.org.objectweb.asm.util.Printer
    public void visitMethodInsn(int i, String str, String str2, String str3, boolean z) {
        this.stringBuilder.setLength(0);
        this.stringBuilder.append(this.tab2).append(OPCODES[i]).append(StringUtil.SPACE);
        appendDescriptor(0, str);
        this.stringBuilder.append('.').append(str2).append(StringUtil.SPACE);
        appendDescriptor(3, str3);
        if (z) {
            this.stringBuilder.append(" (itf)");
        }
        this.stringBuilder.append('\n');
        this.text.add(this.stringBuilder.toString());
    }

    @Override // org.jetbrains.org.objectweb.asm.util.Printer
    public void visitInvokeDynamicInsn(String str, String str2, Handle handle, Object... objArr) {
        this.stringBuilder.setLength(0);
        this.stringBuilder.append(this.tab2).append("INVOKEDYNAMIC").append(StringUtil.SPACE);
        this.stringBuilder.append(str);
        appendDescriptor(3, str2);
        this.stringBuilder.append(" [");
        this.stringBuilder.append('\n');
        this.stringBuilder.append(this.tab3);
        appendHandle(handle);
        this.stringBuilder.append('\n');
        this.stringBuilder.append(this.tab3).append("// arguments:");
        if (objArr.length == 0) {
            this.stringBuilder.append(" none");
        } else {
            this.stringBuilder.append('\n');
            for (Object obj : objArr) {
                this.stringBuilder.append(this.tab3);
                if (obj instanceof String) {
                    Printer.appendString(this.stringBuilder, (String) obj);
                } else if (obj instanceof Type) {
                    Type type = (Type) obj;
                    if (type.getSort() == 11) {
                        appendDescriptor(3, type.getDescriptor());
                    } else {
                        visitType(type);
                    }
                } else if (obj instanceof Handle) {
                    appendHandle((Handle) obj);
                } else {
                    this.stringBuilder.append(obj);
                }
                this.stringBuilder.append(", \n");
            }
            this.stringBuilder.setLength(this.stringBuilder.length() - 3);
        }
        this.stringBuilder.append('\n');
        this.stringBuilder.append(this.tab2).append("]\n");
        this.text.add(this.stringBuilder.toString());
    }

    @Override // org.jetbrains.org.objectweb.asm.util.Printer
    public void visitJumpInsn(int i, Label label) {
        this.stringBuilder.setLength(0);
        this.stringBuilder.append(this.tab2).append(OPCODES[i]).append(StringUtil.SPACE);
        appendLabel(label);
        this.stringBuilder.append('\n');
        this.text.add(this.stringBuilder.toString());
    }

    @Override // org.jetbrains.org.objectweb.asm.util.Printer
    public void visitLabel(Label label) {
        this.stringBuilder.setLength(0);
        this.stringBuilder.append(this.ltab);
        appendLabel(label);
        this.stringBuilder.append('\n');
        this.text.add(this.stringBuilder.toString());
    }

    @Override // org.jetbrains.org.objectweb.asm.util.Printer
    public void visitLdcInsn(Object obj) {
        this.stringBuilder.setLength(0);
        this.stringBuilder.append(this.tab2).append("LDC ");
        if (obj instanceof String) {
            Printer.appendString(this.stringBuilder, (String) obj);
        } else if (obj instanceof Type) {
            this.stringBuilder.append(((Type) obj).getDescriptor()).append(".class");
        } else {
            this.stringBuilder.append(obj);
        }
        this.stringBuilder.append('\n');
        this.text.add(this.stringBuilder.toString());
    }

    @Override // org.jetbrains.org.objectweb.asm.util.Printer
    public void visitIincInsn(int i, int i2) {
        this.stringBuilder.setLength(0);
        this.stringBuilder.append(this.tab2).append("IINC ").append(i).append(StringUtil.SPACE).append(i2).append('\n');
        this.text.add(this.stringBuilder.toString());
    }

    @Override // org.jetbrains.org.objectweb.asm.util.Printer
    public void visitTableSwitchInsn(int i, int i2, Label label, Label... labelArr) {
        this.stringBuilder.setLength(0);
        this.stringBuilder.append(this.tab2).append("TABLESWITCH\n");
        for (int i3 = 0; i3 < labelArr.length; i3++) {
            this.stringBuilder.append(this.tab3).append(i + i3).append(": ");
            appendLabel(labelArr[i3]);
            this.stringBuilder.append('\n');
        }
        this.stringBuilder.append(this.tab3).append("default: ");
        appendLabel(label);
        this.stringBuilder.append('\n');
        this.text.add(this.stringBuilder.toString());
    }

    @Override // org.jetbrains.org.objectweb.asm.util.Printer
    public void visitLookupSwitchInsn(Label label, int[] iArr, Label[] labelArr) {
        this.stringBuilder.setLength(0);
        this.stringBuilder.append(this.tab2).append("LOOKUPSWITCH\n");
        for (int i = 0; i < labelArr.length; i++) {
            this.stringBuilder.append(this.tab3).append(iArr[i]).append(": ");
            appendLabel(labelArr[i]);
            this.stringBuilder.append('\n');
        }
        this.stringBuilder.append(this.tab3).append("default: ");
        appendLabel(label);
        this.stringBuilder.append('\n');
        this.text.add(this.stringBuilder.toString());
    }

    @Override // org.jetbrains.org.objectweb.asm.util.Printer
    public void visitMultiANewArrayInsn(String str, int i) {
        this.stringBuilder.setLength(0);
        this.stringBuilder.append(this.tab2).append("MULTIANEWARRAY ");
        appendDescriptor(1, str);
        this.stringBuilder.append(StringUtil.SPACE).append(i).append('\n');
        this.text.add(this.stringBuilder.toString());
    }

    @Override // org.jetbrains.org.objectweb.asm.util.Printer
    public Printer visitInsnAnnotation(int i, TypePath typePath, String str, boolean z) {
        return visitTypeAnnotation(i, typePath, str, z);
    }

    @Override // org.jetbrains.org.objectweb.asm.util.Printer
    public void visitTryCatchBlock(Label label, Label label2, Label label3, String str) {
        this.stringBuilder.setLength(0);
        this.stringBuilder.append(this.tab2).append("TRYCATCHBLOCK ");
        appendLabel(label);
        this.stringBuilder.append(StringUtil.SPACE);
        appendLabel(label2);
        this.stringBuilder.append(StringUtil.SPACE);
        appendLabel(label3);
        this.stringBuilder.append(StringUtil.SPACE);
        appendDescriptor(0, str);
        this.stringBuilder.append('\n');
        this.text.add(this.stringBuilder.toString());
    }

    @Override // org.jetbrains.org.objectweb.asm.util.Printer
    public Printer visitTryCatchAnnotation(int i, TypePath typePath, String str, boolean z) {
        this.stringBuilder.setLength(0);
        this.stringBuilder.append(this.tab2).append("TRYCATCHBLOCK @");
        appendDescriptor(1, str);
        this.stringBuilder.append('(');
        this.text.add(this.stringBuilder.toString());
        this.stringBuilder.setLength(0);
        this.stringBuilder.append(") : ");
        appendTypeReference(i);
        this.stringBuilder.append(", ").append(typePath);
        this.stringBuilder.append(z ? "\n" : " // invisible\n");
        return addNewTextifier(this.stringBuilder.toString());
    }

    @Override // org.jetbrains.org.objectweb.asm.util.Printer
    public void visitLocalVariable(String str, String str2, String str3, Label label, Label label2, int i) {
        this.stringBuilder.setLength(0);
        this.stringBuilder.append(this.tab2).append("LOCALVARIABLE ").append(str).append(StringUtil.SPACE);
        appendDescriptor(1, str2);
        this.stringBuilder.append(StringUtil.SPACE);
        appendLabel(label);
        this.stringBuilder.append(StringUtil.SPACE);
        appendLabel(label2);
        this.stringBuilder.append(StringUtil.SPACE).append(i).append('\n');
        if (str3 != null) {
            this.stringBuilder.append(this.tab2);
            appendDescriptor(2, str3);
            this.stringBuilder.append(this.tab2);
            appendJavaDeclaration(str, str3);
        }
        this.text.add(this.stringBuilder.toString());
    }

    @Override // org.jetbrains.org.objectweb.asm.util.Printer
    public Printer visitLocalVariableAnnotation(int i, TypePath typePath, Label[] labelArr, Label[] labelArr2, int[] iArr, String str, boolean z) {
        this.stringBuilder.setLength(0);
        this.stringBuilder.append(this.tab2).append("LOCALVARIABLE @");
        appendDescriptor(1, str);
        this.stringBuilder.append('(');
        this.text.add(this.stringBuilder.toString());
        this.stringBuilder.setLength(0);
        this.stringBuilder.append(") : ");
        appendTypeReference(i);
        this.stringBuilder.append(", ").append(typePath);
        for (int i2 = 0; i2 < labelArr.length; i2++) {
            this.stringBuilder.append(" [ ");
            appendLabel(labelArr[i2]);
            this.stringBuilder.append(" - ");
            appendLabel(labelArr2[i2]);
            this.stringBuilder.append(" - ").append(iArr[i2]).append(" ]");
        }
        this.stringBuilder.append(z ? "\n" : " // invisible\n");
        return addNewTextifier(this.stringBuilder.toString());
    }

    @Override // org.jetbrains.org.objectweb.asm.util.Printer
    public void visitLineNumber(int i, Label label) {
        this.stringBuilder.setLength(0);
        this.stringBuilder.append(this.tab2).append("LINENUMBER ").append(i).append(StringUtil.SPACE);
        appendLabel(label);
        this.stringBuilder.append('\n');
        this.text.add(this.stringBuilder.toString());
    }

    @Override // org.jetbrains.org.objectweb.asm.util.Printer
    public void visitMaxs(int i, int i2) {
        this.stringBuilder.setLength(0);
        this.stringBuilder.append(this.tab2).append("MAXSTACK = ").append(i).append('\n');
        this.text.add(this.stringBuilder.toString());
        this.stringBuilder.setLength(0);
        this.stringBuilder.append(this.tab2).append("MAXLOCALS = ").append(i2).append('\n');
        this.text.add(this.stringBuilder.toString());
    }

    public Textifier visitAnnotation(String str, boolean z) {
        this.stringBuilder.setLength(0);
        this.stringBuilder.append(this.tab).append('@');
        appendDescriptor(1, str);
        this.stringBuilder.append('(');
        this.text.add(this.stringBuilder.toString());
        return addNewTextifier(z ? ")\n" : ") // invisible\n");
    }

    public Textifier visitTypeAnnotation(int i, TypePath typePath, String str, boolean z) {
        this.stringBuilder.setLength(0);
        this.stringBuilder.append(this.tab).append('@');
        appendDescriptor(1, str);
        this.stringBuilder.append('(');
        this.text.add(this.stringBuilder.toString());
        this.stringBuilder.setLength(0);
        this.stringBuilder.append(") : ");
        appendTypeReference(i);
        this.stringBuilder.append(", ").append(typePath);
        this.stringBuilder.append(z ? "\n" : " // invisible\n");
        return addNewTextifier(this.stringBuilder.toString());
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void visitAttribute(Attribute attribute) {
        this.stringBuilder.setLength(0);
        this.stringBuilder.append(this.tab).append("ATTRIBUTE ");
        appendDescriptor(-1, attribute.type);
        if (attribute instanceof TextifierSupport) {
            if (this.labelNames == null) {
                this.labelNames = new HashMap();
            }
            ((TextifierSupport) attribute).textify(this.stringBuilder, this.labelNames);
        } else {
            this.stringBuilder.append(" : unknown\n");
        }
        this.text.add(this.stringBuilder.toString());
    }

    private void appendAccess(int i) {
        if ((i & 1) != 0) {
            this.stringBuilder.append("public ");
        }
        if ((i & 2) != 0) {
            this.stringBuilder.append("private ");
        }
        if ((i & 4) != 0) {
            this.stringBuilder.append("protected ");
        }
        if ((i & 16) != 0) {
            this.stringBuilder.append("final ");
        }
        if ((i & 8) != 0) {
            this.stringBuilder.append("static ");
        }
        if ((i & 32) != 0) {
            this.stringBuilder.append("synchronized ");
        }
        if ((i & 64) != 0) {
            this.stringBuilder.append("volatile ");
        }
        if ((i & 128) != 0) {
            this.stringBuilder.append("transient ");
        }
        if ((i & 1024) != 0) {
            this.stringBuilder.append("abstract ");
        }
        if ((i & 2048) != 0) {
            this.stringBuilder.append("strictfp ");
        }
        if ((i & 4096) != 0) {
            this.stringBuilder.append("synthetic ");
        }
        if ((32768 & i) != 0) {
            this.stringBuilder.append("mandated ");
        }
        if ((i & 16384) != 0) {
            this.stringBuilder.append("enum ");
        }
    }

    private void appendRawAccess(int i) {
        this.stringBuilder.append("// access flags 0x").append(Integer.toHexString(i).toUpperCase()).append('\n');
    }

    protected void appendDescriptor(int i, String str) {
        if (i != 5 && i != 2 && i != 4) {
            this.stringBuilder.append(str);
        } else if (str != null) {
            this.stringBuilder.append("// signature ").append(str).append('\n');
        }
    }

    private void appendJavaDeclaration(String str, String str2) {
        TraceSignatureVisitor traceSignatureVisitor = new TraceSignatureVisitor(this.access);
        new SignatureReader(str2).accept(traceSignatureVisitor);
        this.stringBuilder.append("// declaration: ");
        if (traceSignatureVisitor.getReturnType() != null) {
            this.stringBuilder.append(traceSignatureVisitor.getReturnType());
            this.stringBuilder.append(StringUtil.SPACE);
        }
        this.stringBuilder.append(str);
        this.stringBuilder.append(traceSignatureVisitor.getDeclaration());
        if (traceSignatureVisitor.getExceptions() != null) {
            this.stringBuilder.append(" throws ").append(traceSignatureVisitor.getExceptions());
        }
        this.stringBuilder.append('\n');
    }

    protected void appendLabel(Label label) {
        if (this.labelNames == null) {
            this.labelNames = new HashMap();
        }
        String str = this.labelNames.get(label);
        if (str == null) {
            str = "L" + this.labelNames.size();
            this.labelNames.put(label, str);
        }
        this.stringBuilder.append(str);
    }

    /* JADX WARN: Code duplicated, block: B:18:0x0093  */
    /* JADX WARN: Code duplicated, block: B:21:0x00a5  */
    /* JADX WARN: Code duplicated, block: B:24:0x00b2  */
    /* JADX WARN: Code duplicated, block: B:26:? A[RETURN, SYNTHETIC] */
    protected void appendHandle(Handle handle) {
        int tag = handle.getTag();
        this.stringBuilder.append("// handle kind 0x").append(Integer.toHexString(tag)).append(" : ");
        boolean z = true;
        switch (tag) {
            case 1:
                this.stringBuilder.append("GETFIELD");
                z = false;
                this.stringBuilder.append('\n');
                this.stringBuilder.append(this.tab3);
                appendDescriptor(0, handle.getOwner());
                this.stringBuilder.append('.');
                this.stringBuilder.append(handle.getName());
                if (!z) {
                    this.stringBuilder.append('(');
                }
                appendDescriptor(9, handle.getDesc());
                if (!z) {
                    this.stringBuilder.append(')');
                }
                if (handle.isInterface()) {
                    this.stringBuilder.append(" itf");
                    return;
                }
                return;
            case 2:
                this.stringBuilder.append("GETSTATIC");
                z = false;
                this.stringBuilder.append('\n');
                this.stringBuilder.append(this.tab3);
                appendDescriptor(0, handle.getOwner());
                this.stringBuilder.append('.');
                this.stringBuilder.append(handle.getName());
                if (!z) {
                    this.stringBuilder.append('(');
                }
                appendDescriptor(9, handle.getDesc());
                if (!z) {
                    this.stringBuilder.append(')');
                }
                if (handle.isInterface()) {
                    this.stringBuilder.append(" itf");
                    return;
                }
                return;
            case 3:
                this.stringBuilder.append("PUTFIELD");
                z = false;
                this.stringBuilder.append('\n');
                this.stringBuilder.append(this.tab3);
                appendDescriptor(0, handle.getOwner());
                this.stringBuilder.append('.');
                this.stringBuilder.append(handle.getName());
                if (!z) {
                    this.stringBuilder.append('(');
                }
                appendDescriptor(9, handle.getDesc());
                if (!z) {
                    this.stringBuilder.append(')');
                }
                if (handle.isInterface()) {
                    this.stringBuilder.append(" itf");
                    return;
                }
                return;
            case 4:
                this.stringBuilder.append("PUTSTATIC");
                z = false;
                this.stringBuilder.append('\n');
                this.stringBuilder.append(this.tab3);
                appendDescriptor(0, handle.getOwner());
                this.stringBuilder.append('.');
                this.stringBuilder.append(handle.getName());
                if (!z) {
                    this.stringBuilder.append('(');
                }
                appendDescriptor(9, handle.getDesc());
                if (!z) {
                    this.stringBuilder.append(')');
                }
                if (handle.isInterface()) {
                    this.stringBuilder.append(" itf");
                    return;
                }
                return;
            case 5:
                this.stringBuilder.append("INVOKEVIRTUAL");
                this.stringBuilder.append('\n');
                this.stringBuilder.append(this.tab3);
                appendDescriptor(0, handle.getOwner());
                this.stringBuilder.append('.');
                this.stringBuilder.append(handle.getName());
                if (!z) {
                    this.stringBuilder.append('(');
                }
                appendDescriptor(9, handle.getDesc());
                if (!z) {
                    this.stringBuilder.append(')');
                }
                if (handle.isInterface()) {
                    this.stringBuilder.append(" itf");
                    return;
                }
                return;
            case 6:
                this.stringBuilder.append("INVOKESTATIC");
                this.stringBuilder.append('\n');
                this.stringBuilder.append(this.tab3);
                appendDescriptor(0, handle.getOwner());
                this.stringBuilder.append('.');
                this.stringBuilder.append(handle.getName());
                if (!z) {
                    this.stringBuilder.append('(');
                }
                appendDescriptor(9, handle.getDesc());
                if (!z) {
                    this.stringBuilder.append(')');
                }
                if (handle.isInterface()) {
                    this.stringBuilder.append(" itf");
                    return;
                }
                return;
            case 7:
                this.stringBuilder.append("INVOKESPECIAL");
                this.stringBuilder.append('\n');
                this.stringBuilder.append(this.tab3);
                appendDescriptor(0, handle.getOwner());
                this.stringBuilder.append('.');
                this.stringBuilder.append(handle.getName());
                if (!z) {
                    this.stringBuilder.append('(');
                }
                appendDescriptor(9, handle.getDesc());
                if (!z) {
                    this.stringBuilder.append(')');
                }
                if (handle.isInterface()) {
                    this.stringBuilder.append(" itf");
                    return;
                }
                return;
            case 8:
                this.stringBuilder.append("NEWINVOKESPECIAL");
                this.stringBuilder.append('\n');
                this.stringBuilder.append(this.tab3);
                appendDescriptor(0, handle.getOwner());
                this.stringBuilder.append('.');
                this.stringBuilder.append(handle.getName());
                if (!z) {
                    this.stringBuilder.append('(');
                }
                appendDescriptor(9, handle.getDesc());
                if (!z) {
                    this.stringBuilder.append(')');
                }
                if (handle.isInterface()) {
                    this.stringBuilder.append(" itf");
                    return;
                }
                return;
            case 9:
                this.stringBuilder.append("INVOKEINTERFACE");
                this.stringBuilder.append('\n');
                this.stringBuilder.append(this.tab3);
                appendDescriptor(0, handle.getOwner());
                this.stringBuilder.append('.');
                this.stringBuilder.append(handle.getName());
                if (!z) {
                    this.stringBuilder.append('(');
                }
                appendDescriptor(9, handle.getDesc());
                if (!z) {
                    this.stringBuilder.append(')');
                }
                if (handle.isInterface()) {
                    this.stringBuilder.append(" itf");
                    return;
                }
                return;
            default:
                throw new IllegalArgumentException();
        }
    }

    private void maybeAppendComma(int i) {
        if (i > 0) {
            this.stringBuilder.append(", ");
        }
    }

    private void appendTypeReference(int i) {
        TypeReference typeReference = new TypeReference(i);
        int sort = typeReference.getSort();
        if (sort == 0) {
            this.stringBuilder.append("CLASS_TYPE_PARAMETER ").append(typeReference.getTypeParameterIndex());
            return;
        }
        if (sort == 1) {
            this.stringBuilder.append("METHOD_TYPE_PARAMETER ").append(typeReference.getTypeParameterIndex());
            return;
        }
        switch (sort) {
            case 16:
                this.stringBuilder.append("CLASS_EXTENDS ").append(typeReference.getSuperTypeIndex());
                return;
            case 17:
                this.stringBuilder.append("CLASS_TYPE_PARAMETER_BOUND ").append(typeReference.getTypeParameterIndex()).append(", ").append(typeReference.getTypeParameterBoundIndex());
                return;
            case 18:
                this.stringBuilder.append("METHOD_TYPE_PARAMETER_BOUND ").append(typeReference.getTypeParameterIndex()).append(", ").append(typeReference.getTypeParameterBoundIndex());
                return;
            case 19:
                this.stringBuilder.append("FIELD");
                return;
            case 20:
                this.stringBuilder.append("METHOD_RETURN");
                return;
            case 21:
                this.stringBuilder.append("METHOD_RECEIVER");
                return;
            case 22:
                this.stringBuilder.append("METHOD_FORMAL_PARAMETER ").append(typeReference.getFormalParameterIndex());
                return;
            case 23:
                this.stringBuilder.append("THROWS ").append(typeReference.getExceptionIndex());
                return;
            default:
                switch (sort) {
                    case 64:
                        this.stringBuilder.append("LOCAL_VARIABLE");
                        return;
                    case 65:
                        this.stringBuilder.append("RESOURCE_VARIABLE");
                        return;
                    case 66:
                        this.stringBuilder.append("EXCEPTION_PARAMETER ").append(typeReference.getTryCatchBlockIndex());
                        return;
                    case 67:
                        this.stringBuilder.append("INSTANCEOF");
                        return;
                    case 68:
                        this.stringBuilder.append("NEW");
                        return;
                    case 69:
                        this.stringBuilder.append("CONSTRUCTOR_REFERENCE");
                        return;
                    case 70:
                        this.stringBuilder.append("METHOD_REFERENCE");
                        return;
                    case 71:
                        this.stringBuilder.append("CAST ").append(typeReference.getTypeArgumentIndex());
                        return;
                    case 72:
                        this.stringBuilder.append("CONSTRUCTOR_INVOCATION_TYPE_ARGUMENT ").append(typeReference.getTypeArgumentIndex());
                        return;
                    case 73:
                        this.stringBuilder.append("METHOD_INVOCATION_TYPE_ARGUMENT ").append(typeReference.getTypeArgumentIndex());
                        return;
                    case 74:
                        this.stringBuilder.append("CONSTRUCTOR_REFERENCE_TYPE_ARGUMENT ").append(typeReference.getTypeArgumentIndex());
                        return;
                    case 75:
                        this.stringBuilder.append("METHOD_REFERENCE_TYPE_ARGUMENT ").append(typeReference.getTypeArgumentIndex());
                        return;
                    default:
                        throw new IllegalArgumentException();
                }
        }
    }

    private void appendFrameTypes(int i, Object[] objArr) {
        for (int i2 = 0; i2 < i; i2++) {
            if (i2 > 0) {
                this.stringBuilder.append(StringUtil.SPACE);
            }
            Object obj = objArr[i2];
            if (obj instanceof String) {
                String str = (String) obj;
                if (str.charAt(0) == '[') {
                    appendDescriptor(1, str);
                } else {
                    appendDescriptor(0, str);
                }
            } else if (obj instanceof Integer) {
                this.stringBuilder.append(FRAME_TYPES.get(((Integer) objArr[i2]).intValue()));
            } else {
                appendLabel((Label) obj);
            }
        }
    }

    private Textifier addNewTextifier(String str) {
        Textifier textifierCreateTextifier = createTextifier();
        this.text.add(textifierCreateTextifier.getText());
        if (str != null) {
            this.text.add(str);
        }
        return textifierCreateTextifier;
    }

    protected Textifier createTextifier() {
        return new Textifier(this.api);
    }
}

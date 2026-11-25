package org.mozilla.classfile;

import androidx.exifinterface.media.ExifInterface;
import com.android.dx.cf.attrib.AttBootstrapMethods;
import io.netty.util.internal.StringUtil;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import org.eclipse.jdt.internal.compiler.codegen.Opcodes;
import org.eclipse.jdt.internal.compiler.util.Util;
import org.mozilla.javascript.ObjArray;
import org.mozilla.javascript.UintMap;

/* JADX INFO: loaded from: classes6.dex */
public class ClassFileWriter {
    public static final short ACC_ABSTRACT = 1024;
    public static final short ACC_FINAL = 16;
    public static final short ACC_NATIVE = 256;
    public static final short ACC_PRIVATE = 2;
    public static final short ACC_PROTECTED = 4;
    public static final short ACC_PUBLIC = 1;
    public static final short ACC_STATIC = 8;
    public static final short ACC_SUPER = 32;
    public static final short ACC_SYNCHRONIZED = 32;
    public static final short ACC_TRANSIENT = 128;
    public static final short ACC_VOLATILE = 64;
    private static final boolean DEBUGCODE = false;
    private static final boolean DEBUGLABELS = false;
    private static final boolean DEBUGSTACK = false;
    private static final int ExceptionTableSize = 4;
    private static final int FileHeaderConstant = -889275714;
    private static final boolean GenerateStackMap;
    private static final int LineNumberTableSize = 16;
    private static final int MIN_FIXUP_TABLE_SIZE = 40;
    private static final int MIN_LABEL_TABLE_SIZE = 32;
    private static final int MajorVersion;
    private static final int MinorVersion;
    private static final int SuperBlockStartsSize = 4;
    private String generatedClassName;
    private ObjArray itsBootstrapMethods;
    private int itsCodeBufferTop;
    private ConstantPool itsConstantPool;
    private ClassFileMethod itsCurrentMethod;
    private ExceptionTableEntry[] itsExceptionTable;
    private int itsExceptionTableTop;
    private long[] itsFixupTable;
    private int itsFixupTableTop;
    private short itsFlags;
    private int[] itsLabelTable;
    private int itsLabelTableTop;
    private int[] itsLineNumberTable;
    private int itsLineNumberTableTop;
    private short itsMaxLocals;
    private short itsMaxStack;
    private short itsSourceFileNameIndex;
    private short itsStackTop;
    private short itsSuperClassIndex;
    private short itsThisClassIndex;
    private ObjArray itsVarDescriptors;
    private int[] itsSuperBlockStarts = null;
    private int itsSuperBlockStartsTop = 0;
    private UintMap itsJumpFroms = null;
    private byte[] itsCodeBuffer = new byte[256];
    private ObjArray itsMethods = new ObjArray();
    private ObjArray itsFields = new ObjArray();
    private ObjArray itsInterfaces = new ObjArray();
    private int itsBootstrapMethodsLength = 0;
    private char[] tmpCharBuffer = new char[64];

    static /* synthetic */ int access$410(ClassFileWriter classFileWriter) {
        int i = classFileWriter.itsExceptionTableTop;
        classFileWriter.itsExceptionTableTop = i - 1;
        return i;
    }

    public static class ClassFileFormatException extends RuntimeException {
        private static final long serialVersionUID = 1263998431033790599L;

        ClassFileFormatException(String str) {
            super(str);
        }
    }

    public ClassFileWriter(String str, String str2, String str3) {
        this.generatedClassName = str;
        ConstantPool constantPool = new ConstantPool(this);
        this.itsConstantPool = constantPool;
        this.itsThisClassIndex = constantPool.addClass(str);
        this.itsSuperClassIndex = this.itsConstantPool.addClass(str2);
        if (str3 != null) {
            this.itsSourceFileNameIndex = this.itsConstantPool.addUtf8(str3);
        }
        this.itsFlags = (short) 33;
    }

    public final String getClassName() {
        return this.generatedClassName;
    }

    public void addInterface(String str) {
        this.itsInterfaces.add(Short.valueOf(this.itsConstantPool.addClass(str)));
    }

    public void setFlags(short s) {
        this.itsFlags = s;
    }

    static String getSlashedForm(String str) {
        return str.replace('.', '/');
    }

    public static String classNameToSignature(String str) {
        int length = str.length();
        int i = length + 1;
        int i2 = length + 2;
        char[] cArr = new char[i2];
        cArr[0] = 'L';
        cArr[i] = ';';
        str.getChars(0, length, cArr, 1);
        for (int i3 = 1; i3 != i; i3++) {
            if (cArr[i3] == '.') {
                cArr[i3] = '/';
            }
        }
        return new String(cArr, 0, i2);
    }

    public void addField(String str, String str2, short s) {
        this.itsFields.add(new ClassFileField(this.itsConstantPool.addUtf8(str), this.itsConstantPool.addUtf8(str2), s));
    }

    public void addField(String str, String str2, short s, int i) {
        ClassFileField classFileField = new ClassFileField(this.itsConstantPool.addUtf8(str), this.itsConstantPool.addUtf8(str2), s);
        classFileField.setAttributes(this.itsConstantPool.addUtf8("ConstantValue"), (short) 0, (short) 0, this.itsConstantPool.addConstant(i));
        this.itsFields.add(classFileField);
    }

    public void addField(String str, String str2, short s, long j) {
        ClassFileField classFileField = new ClassFileField(this.itsConstantPool.addUtf8(str), this.itsConstantPool.addUtf8(str2), s);
        classFileField.setAttributes(this.itsConstantPool.addUtf8("ConstantValue"), (short) 0, (short) 2, this.itsConstantPool.addConstant(j));
        this.itsFields.add(classFileField);
    }

    public void addField(String str, String str2, short s, double d) {
        ClassFileField classFileField = new ClassFileField(this.itsConstantPool.addUtf8(str), this.itsConstantPool.addUtf8(str2), s);
        classFileField.setAttributes(this.itsConstantPool.addUtf8("ConstantValue"), (short) 0, (short) 2, this.itsConstantPool.addConstant(d));
        this.itsFields.add(classFileField);
    }

    public void addVariableDescriptor(String str, String str2, int i, int i2) {
        int[] iArr = {this.itsConstantPool.addUtf8(str), this.itsConstantPool.addUtf8(str2), i, i2};
        if (this.itsVarDescriptors == null) {
            this.itsVarDescriptors = new ObjArray();
        }
        this.itsVarDescriptors.add(iArr);
    }

    public void startMethod(String str, String str2, short s) {
        this.itsCurrentMethod = new ClassFileMethod(str, this.itsConstantPool.addUtf8(str), str2, this.itsConstantPool.addUtf8(str2), s);
        this.itsJumpFroms = new UintMap();
        this.itsMethods.add(this.itsCurrentMethod);
        addSuperBlockStart(0);
    }

    public void stopMethod(short s) {
        StackMapTable stackMapTable;
        int iPutInt16;
        int iComputeWriteSize;
        if (this.itsCurrentMethod == null) {
            throw new IllegalStateException("No method to stop");
        }
        fixLabelGotos();
        this.itsMaxLocals = s;
        if (GenerateStackMap) {
            finalizeSuperBlockStarts();
            stackMapTable = new StackMapTable();
            stackMapTable.generate();
        } else {
            stackMapTable = null;
        }
        int i = this.itsLineNumberTable != null ? (this.itsLineNumberTableTop * 4) + 8 : 0;
        ObjArray objArray = this.itsVarDescriptors;
        int size = objArray != null ? (objArray.size() * 10) + 8 : 0;
        int i2 = (stackMapTable == null || (iComputeWriteSize = stackMapTable.computeWriteSize()) <= 0) ? 0 : iComputeWriteSize + 6;
        int i3 = this.itsCodeBufferTop + 16 + (this.itsExceptionTableTop * 8) + 2 + i + size + i2;
        if (i3 > 65536) {
            throw new ClassFileFormatException("generated bytecode for method exceeds 64K limit.");
        }
        byte[] bArr = new byte[i3];
        int iPutInt32 = putInt32(this.itsCodeBufferTop, bArr, putInt16(this.itsMaxLocals, bArr, putInt16(this.itsMaxStack, bArr, putInt32(i3 - 6, bArr, putInt16(this.itsConstantPool.addUtf8("Code"), bArr, 0)))));
        System.arraycopy(this.itsCodeBuffer, 0, bArr, iPutInt32, this.itsCodeBufferTop);
        int i4 = iPutInt32 + this.itsCodeBufferTop;
        int i5 = this.itsExceptionTableTop;
        if (i5 > 0) {
            iPutInt16 = putInt16(i5, bArr, i4);
            for (int i6 = 0; i6 < this.itsExceptionTableTop; i6++) {
                ExceptionTableEntry exceptionTableEntry = this.itsExceptionTable[i6];
                int labelPC = getLabelPC(exceptionTableEntry.itsStartLabel);
                int labelPC2 = getLabelPC(exceptionTableEntry.itsEndLabel);
                int labelPC3 = getLabelPC(exceptionTableEntry.itsHandlerLabel);
                short s2 = exceptionTableEntry.itsCatchType;
                if (labelPC == -1) {
                    throw new IllegalStateException("start label not defined");
                }
                if (labelPC2 == -1) {
                    throw new IllegalStateException("end label not defined");
                }
                if (labelPC3 == -1) {
                    throw new IllegalStateException("handler label not defined");
                }
                iPutInt16 = putInt16(s2, bArr, putInt16(labelPC3, bArr, putInt16(labelPC2, bArr, putInt16(labelPC, bArr, iPutInt16))));
            }
        } else {
            iPutInt16 = putInt16(0, bArr, i4);
        }
        int i7 = this.itsLineNumberTable != null ? 1 : 0;
        if (this.itsVarDescriptors != null) {
            i7++;
        }
        if (i2 > 0) {
            i7++;
        }
        int iPutInt17 = putInt16(i7, bArr, iPutInt16);
        if (this.itsLineNumberTable != null) {
            iPutInt17 = putInt16(this.itsLineNumberTableTop, bArr, putInt32((this.itsLineNumberTableTop * 4) + 2, bArr, putInt16(this.itsConstantPool.addUtf8("LineNumberTable"), bArr, iPutInt17)));
            for (int i8 = 0; i8 < this.itsLineNumberTableTop; i8++) {
                iPutInt17 = putInt32(this.itsLineNumberTable[i8], bArr, iPutInt17);
            }
        }
        if (this.itsVarDescriptors != null) {
            int iPutInt18 = putInt16(this.itsConstantPool.addUtf8("LocalVariableTable"), bArr, iPutInt17);
            int size2 = this.itsVarDescriptors.size();
            iPutInt17 = putInt16(size2, bArr, putInt32((size2 * 10) + 2, bArr, iPutInt18));
            for (int i9 = 0; i9 < size2; i9++) {
                int[] iArr = (int[]) this.itsVarDescriptors.get(i9);
                int i10 = iArr[0];
                int i11 = iArr[1];
                int i12 = iArr[2];
                iPutInt17 = putInt16(iArr[3], bArr, putInt16(i11, bArr, putInt16(i10, bArr, putInt16(this.itsCodeBufferTop - i12, bArr, putInt16(i12, bArr, iPutInt17)))));
            }
        }
        if (i2 > 0) {
            stackMapTable.write(bArr, putInt16(this.itsConstantPool.addUtf8("StackMapTable"), bArr, iPutInt17));
        }
        this.itsCurrentMethod.setCodeAttribute(bArr);
        this.itsExceptionTable = null;
        this.itsExceptionTableTop = 0;
        this.itsLineNumberTableTop = 0;
        this.itsCodeBufferTop = 0;
        this.itsCurrentMethod = null;
        this.itsMaxStack = (short) 0;
        this.itsStackTop = (short) 0;
        this.itsLabelTableTop = 0;
        this.itsFixupTableTop = 0;
        this.itsVarDescriptors = null;
        this.itsSuperBlockStarts = null;
        this.itsSuperBlockStartsTop = 0;
        this.itsJumpFroms = null;
    }

    public void add(int i) {
        if (opcodeCount(i) != 0) {
            throw new IllegalArgumentException("Unexpected operands");
        }
        int iStackChange = this.itsStackTop + stackChange(i);
        if (iStackChange < 0 || 32767 < iStackChange) {
            badStack(iStackChange);
        }
        addToCodeBuffer(i);
        short s = (short) iStackChange;
        this.itsStackTop = s;
        if (iStackChange > this.itsMaxStack) {
            this.itsMaxStack = s;
        }
        if (i == 191) {
            addSuperBlockStart(this.itsCodeBufferTop);
        }
    }

    /* JADX WARN: Code duplicated, block: B:23:0x0044 A[FALL_THROUGH] */
    /* JADX WARN: Code duplicated, block: B:56:0x00ba A[FALL_THROUGH] */
    /* JADX WARN: Code duplicated, block: B:66:0x00d7  */
    /* JADX WARN: Code duplicated, block: B:67:0x00e4  */
    /* JADX WARN: Code duplicated, block: B:69:0x00eb  */
    /* JADX WARN: Code duplicated, block: B:70:0x00f9  */
    public void add(int i, int i2) {
        int i3;
        int i4;
        int labelPC;
        int iStackChange = this.itsStackTop + stackChange(i);
        if (iStackChange < 0 || 32767 < iStackChange) {
            badStack(iStackChange);
        }
        if (i == 180 || i == 181) {
            if (i2 < 0 || i2 >= 65536) {
                throw new IllegalArgumentException("out of range field");
            }
            addToCodeBuffer(i);
            addToCodeInt16(i2);
        } else if (i == 188) {
            if (i2 < 0 || i2 >= 256) {
                throw new IllegalArgumentException("out of range index");
            }
            addToCodeBuffer(i);
            addToCodeBuffer(i2);
        } else if (i == 198 || i == 199) {
            i3 = i2 & Integer.MIN_VALUE;
            if (i3 == Integer.MIN_VALUE && (i2 < 0 || i2 > 65535)) {
                throw new IllegalArgumentException("Bad label for branch");
            }
            i4 = this.itsCodeBufferTop;
            addToCodeBuffer(i);
            if (i3 != Integer.MIN_VALUE) {
                addToCodeInt16(i2);
                int i5 = i2 + i4;
                addSuperBlockStart(i5);
                this.itsJumpFroms.put(i5, i4);
            } else {
                labelPC = getLabelPC(i2);
                if (labelPC != -1) {
                    addToCodeInt16(labelPC - i4);
                    addSuperBlockStart(labelPC);
                    this.itsJumpFroms.put(labelPC, i4);
                } else {
                    addLabelFixup(i2, i4 + 1);
                    addToCodeInt16(0);
                }
            }
        } else {
            switch (i) {
                case 16:
                    byte b = (byte) i2;
                    if (b != i2) {
                        throw new IllegalArgumentException("out of range byte");
                    }
                    addToCodeBuffer(i);
                    addToCodeBuffer(b);
                    break;
                    break;
                case 17:
                    if (((short) i2) != i2) {
                        throw new IllegalArgumentException("out of range short");
                    }
                    addToCodeBuffer(i);
                    addToCodeInt16(i2);
                    break;
                    break;
                case 18:
                case 19:
                case 20:
                    if (i2 < 0 || i2 >= 65536) {
                        throw new ClassFileFormatException("out of range index");
                    }
                    if (i2 >= 256 || i == 19 || i == 20) {
                        if (i == 18) {
                            addToCodeBuffer(19);
                        } else {
                            addToCodeBuffer(i);
                        }
                        addToCodeInt16(i2);
                    } else {
                        addToCodeBuffer(i);
                        addToCodeBuffer(i2);
                    }
                    break;
                default:
                    switch (i) {
                        default:
                            switch (i) {
                                case 167:
                                    addSuperBlockStart(this.itsCodeBufferTop + 3);
                                case 153:
                                case 154:
                                case 155:
                                case 156:
                                case 157:
                                case 158:
                                case 159:
                                case 160:
                                case 161:
                                case 162:
                                case 163:
                                case 164:
                                case 165:
                                case 166:
                                case 168:
                                    i3 = i2 & Integer.MIN_VALUE;
                                    if (i3 == Integer.MIN_VALUE) {
                                    }
                                    i4 = this.itsCodeBufferTop;
                                    addToCodeBuffer(i);
                                    if (i3 != Integer.MIN_VALUE) {
                                        addToCodeInt16(i2);
                                        int i6 = i2 + i4;
                                        addSuperBlockStart(i6);
                                        this.itsJumpFroms.put(i6, i4);
                                    } else {
                                        labelPC = getLabelPC(i2);
                                        if (labelPC != -1) {
                                            addToCodeInt16(labelPC - i4);
                                            addSuperBlockStart(labelPC);
                                            this.itsJumpFroms.put(labelPC, i4);
                                        } else {
                                            addLabelFixup(i2, i4 + 1);
                                            addToCodeInt16(0);
                                        }
                                    }
                                    break;
                                case 169:
                                    break;
                                default:
                                    throw new IllegalArgumentException("Unexpected opcode for 1 operand");
                            }
                        case 54:
                        case 55:
                        case 56:
                        case 57:
                        case 58:
                            if (i2 >= 0 || 65536 <= i2) {
                                throw new ClassFileFormatException("out of range variable");
                            }
                            if (i2 >= 256) {
                                addToCodeBuffer(196);
                                addToCodeBuffer(i);
                                addToCodeInt16(i2);
                            } else {
                                addToCodeBuffer(i);
                                addToCodeBuffer(i2);
                            }
                            break;
                    }
                case 21:
                case 22:
                case 23:
                case 24:
                case 25:
                    if (i2 >= 0) {
                    }
                    throw new ClassFileFormatException("out of range variable");
            }
        }
        short s = (short) iStackChange;
        this.itsStackTop = s;
        if (iStackChange > this.itsMaxStack) {
            this.itsMaxStack = s;
        }
    }

    public void addLoadConstant(int i) {
        if (i == 0) {
            add(3);
            return;
        }
        if (i == 1) {
            add(4);
            return;
        }
        if (i == 2) {
            add(5);
            return;
        }
        if (i == 3) {
            add(6);
            return;
        }
        if (i == 4) {
            add(7);
        } else if (i == 5) {
            add(8);
        } else {
            add(18, this.itsConstantPool.addConstant(i));
        }
    }

    public void addLoadConstant(long j) {
        add(20, this.itsConstantPool.addConstant(j));
    }

    public void addLoadConstant(float f) {
        add(18, this.itsConstantPool.addConstant(f));
    }

    public void addLoadConstant(double d) {
        add(20, this.itsConstantPool.addConstant(d));
    }

    public void addLoadConstant(String str) {
        add(18, this.itsConstantPool.addConstant(str));
    }

    public void add(int i, int i2, int i3) {
        int iStackChange = this.itsStackTop + stackChange(i);
        if (iStackChange < 0 || 32767 < iStackChange) {
            badStack(iStackChange);
        }
        if (i == 132) {
            if (i2 < 0 || 65536 <= i2) {
                throw new ClassFileFormatException("out of range variable");
            }
            if (i3 < 0 || 65536 <= i3) {
                throw new ClassFileFormatException("out of range increment");
            }
            if (i2 > 255 || i3 > 127) {
                addToCodeBuffer(196);
                addToCodeBuffer(132);
                addToCodeInt16(i2);
                addToCodeInt16(i3);
            } else {
                addToCodeBuffer(132);
                addToCodeBuffer(i2);
                addToCodeBuffer(i3);
            }
        } else {
            if (i != 197) {
                throw new IllegalArgumentException("Unexpected opcode for 2 operands");
            }
            if (i2 < 0 || i2 >= 65536) {
                throw new IllegalArgumentException("out of range index");
            }
            if (i3 < 0 || i3 >= 256) {
                throw new IllegalArgumentException("out of range dimensions");
            }
            addToCodeBuffer(197);
            addToCodeInt16(i2);
            addToCodeBuffer(i3);
        }
        short s = (short) iStackChange;
        this.itsStackTop = s;
        if (iStackChange > this.itsMaxStack) {
            this.itsMaxStack = s;
        }
    }

    public void add(int i, String str) {
        int iStackChange = this.itsStackTop + stackChange(i);
        if (iStackChange < 0 || 32767 < iStackChange) {
            badStack(iStackChange);
        }
        if (i == 187 || i == 189 || i == 192 || i == 193) {
            short sAddClass = this.itsConstantPool.addClass(str);
            addToCodeBuffer(i);
            addToCodeInt16(sAddClass);
            short s = (short) iStackChange;
            this.itsStackTop = s;
            if (iStackChange > this.itsMaxStack) {
                this.itsMaxStack = s;
                return;
            }
            return;
        }
        throw new IllegalArgumentException("bad opcode for class reference");
    }

    public void add(int i, String str, String str2, String str3) {
        int i2;
        int iStackChange = this.itsStackTop + stackChange(i);
        char cCharAt = str3.charAt(0);
        int i3 = (cCharAt == 'J' || cCharAt == 'D') ? 2 : 1;
        switch (i) {
            case 178:
            case 180:
                i2 = iStackChange + i3;
                break;
            case 179:
            case 181:
                i2 = iStackChange - i3;
                break;
            default:
                throw new IllegalArgumentException("bad opcode for field reference");
        }
        if (i2 < 0 || 32767 < i2) {
            badStack(i2);
        }
        short sAddFieldRef = this.itsConstantPool.addFieldRef(str, str2, str3);
        addToCodeBuffer(i);
        addToCodeInt16(sAddFieldRef);
        short s = (short) i2;
        this.itsStackTop = s;
        if (i2 > this.itsMaxStack) {
            this.itsMaxStack = s;
        }
    }

    public void addInvoke(int i, String str, String str2, String str3) {
        int iSizeOfParameters = sizeOfParameters(str3);
        int i2 = iSizeOfParameters >>> 16;
        int iStackChange = this.itsStackTop + ((short) iSizeOfParameters) + stackChange(i);
        if (iStackChange < 0 || 32767 < iStackChange) {
            badStack(iStackChange);
        }
        switch (i) {
            case 182:
            case 183:
            case 184:
            case 185:
                addToCodeBuffer(i);
                if (i == 185) {
                    addToCodeInt16(this.itsConstantPool.addInterfaceMethodRef(str, str2, str3));
                    addToCodeBuffer(i2 + 1);
                    addToCodeBuffer(0);
                } else {
                    addToCodeInt16(this.itsConstantPool.addMethodRef(str, str2, str3));
                }
                short s = (short) iStackChange;
                this.itsStackTop = s;
                if (iStackChange > this.itsMaxStack) {
                    this.itsMaxStack = s;
                    return;
                }
                return;
            default:
                throw new IllegalArgumentException("bad opcode for method reference");
        }
    }

    public void addInvokeDynamic(String str, String str2, MHandle mHandle, Object... objArr) {
        if (MajorVersion < 51) {
            throw new RuntimeException("Please build and run with JDK 1.7 for invokedynamic support");
        }
        int iSizeOfParameters = this.itsStackTop + ((short) sizeOfParameters(str2));
        if (iSizeOfParameters < 0 || 32767 < iSizeOfParameters) {
            badStack(iSizeOfParameters);
        }
        BootstrapEntry bootstrapEntry = new BootstrapEntry(mHandle, objArr);
        if (this.itsBootstrapMethods == null) {
            this.itsBootstrapMethods = new ObjArray();
        }
        int iIndexOf = this.itsBootstrapMethods.indexOf(bootstrapEntry);
        if (iIndexOf == -1) {
            iIndexOf = this.itsBootstrapMethods.size();
            this.itsBootstrapMethods.add(bootstrapEntry);
            this.itsBootstrapMethodsLength += bootstrapEntry.code.length;
        }
        short sAddInvokeDynamic = this.itsConstantPool.addInvokeDynamic(str, str2, iIndexOf);
        addToCodeBuffer(186);
        addToCodeInt16(sAddInvokeDynamic);
        addToCodeInt16(0);
        short s = (short) iSizeOfParameters;
        this.itsStackTop = s;
        if (iSizeOfParameters > this.itsMaxStack) {
            this.itsMaxStack = s;
        }
    }

    public void addPush(int i) {
        byte b = (byte) i;
        if (b != i) {
            short s = (short) i;
            if (s == i) {
                add(17, s);
                return;
            } else {
                addLoadConstant(i);
                return;
            }
        }
        if (i == -1) {
            add(2);
        } else if (i >= 0 && i <= 5) {
            add((byte) (i + 3));
        } else {
            add(16, b);
        }
    }

    public void addPush(boolean z) {
        add(z ? 4 : 3);
    }

    public void addPush(long j) {
        int i = (int) j;
        if (i == j) {
            addPush(i);
            add(133);
        } else {
            addLoadConstant(j);
        }
    }

    public void addPush(double d) {
        if (d == 0.0d) {
            add(14);
            if (1.0d / d < 0.0d) {
                add(119);
                return;
            }
            return;
        }
        if (d == 1.0d || d == -1.0d) {
            add(15);
            if (d < 0.0d) {
                add(119);
                return;
            }
            return;
        }
        addLoadConstant(d);
    }

    public void addPush(String str) {
        int length = str.length();
        int i = 0;
        int utfEncodingLimit = this.itsConstantPool.getUtfEncodingLimit(str, 0, length);
        if (utfEncodingLimit == length) {
            addLoadConstant(str);
            return;
        }
        add(187, "java/lang/StringBuilder");
        add(89);
        addPush(length);
        addInvoke(183, "java/lang/StringBuilder", "<init>", "(I)V");
        while (true) {
            add(89);
            addLoadConstant(str.substring(i, utfEncodingLimit));
            addInvoke(182, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;");
            add(87);
            if (utfEncodingLimit == length) {
                addInvoke(182, "java/lang/StringBuilder", "toString", "()Ljava/lang/String;");
                return;
            } else {
                i = utfEncodingLimit;
                utfEncodingLimit = this.itsConstantPool.getUtfEncodingLimit(str, utfEncodingLimit, length);
            }
        }
    }

    public boolean isUnderStringSizeLimit(String str) {
        return this.itsConstantPool.isUnderUtfEncodingLimit(str);
    }

    public void addIStore(int i) {
        xop(59, 54, i);
    }

    public void addLStore(int i) {
        xop(63, 55, i);
    }

    public void addFStore(int i) {
        xop(67, 56, i);
    }

    public void addDStore(int i) {
        xop(71, 57, i);
    }

    public void addAStore(int i) {
        xop(75, 58, i);
    }

    public void addILoad(int i) {
        xop(26, 21, i);
    }

    public void addLLoad(int i) {
        xop(30, 22, i);
    }

    public void addFLoad(int i) {
        xop(34, 23, i);
    }

    public void addDLoad(int i) {
        xop(38, 24, i);
    }

    public void addALoad(int i) {
        xop(42, 25, i);
    }

    public void addLoadThis() {
        add(42);
    }

    private void xop(int i, int i2, int i3) {
        if (i3 == 0) {
            add(i);
            return;
        }
        if (i3 == 1) {
            add(i + 1);
            return;
        }
        if (i3 == 2) {
            add(i + 2);
        } else if (i3 == 3) {
            add(i + 3);
        } else {
            add(i2, i3);
        }
    }

    public int addTableSwitch(int i, int i2) {
        if (i > i2) {
            throw new ClassFileFormatException("Bad bounds: " + i + StringUtil.SPACE + i2);
        }
        int iStackChange = this.itsStackTop + stackChange(170);
        if (iStackChange < 0 || 32767 < iStackChange) {
            badStack(iStackChange);
        }
        int i3 = (~this.itsCodeBufferTop) & 3;
        int iAddReservedCodeSpace = addReservedCodeSpace(i3 + 1 + (((i2 - i) + 4) * 4));
        int i4 = iAddReservedCodeSpace + 1;
        this.itsCodeBuffer[iAddReservedCodeSpace] = Opcodes.OPC_tableswitch;
        while (i3 != 0) {
            this.itsCodeBuffer[i4] = 0;
            i3--;
            i4++;
        }
        putInt32(i2, this.itsCodeBuffer, putInt32(i, this.itsCodeBuffer, i4 + 4));
        short s = (short) iStackChange;
        this.itsStackTop = s;
        if (iStackChange > this.itsMaxStack) {
            this.itsMaxStack = s;
        }
        return iAddReservedCodeSpace;
    }

    public final void markTableSwitchDefault(int i) {
        addSuperBlockStart(this.itsCodeBufferTop);
        this.itsJumpFroms.put(this.itsCodeBufferTop, i);
        setTableSwitchJump(i, -1, this.itsCodeBufferTop);
    }

    public final void markTableSwitchCase(int i, int i2) {
        addSuperBlockStart(this.itsCodeBufferTop);
        this.itsJumpFroms.put(this.itsCodeBufferTop, i);
        setTableSwitchJump(i, i2, this.itsCodeBufferTop);
    }

    public final void markTableSwitchCase(int i, int i2, int i3) {
        if (i3 < 0 || i3 > this.itsMaxStack) {
            throw new IllegalArgumentException("Bad stack index: " + i3);
        }
        this.itsStackTop = (short) i3;
        addSuperBlockStart(this.itsCodeBufferTop);
        this.itsJumpFroms.put(this.itsCodeBufferTop, i);
        setTableSwitchJump(i, i2, this.itsCodeBufferTop);
    }

    public void setTableSwitchJump(int i, int i2, int i3) {
        int i4;
        if (i3 < 0 || (i4 = this.itsCodeBufferTop) < i3) {
            throw new IllegalArgumentException("Bad jump target: " + i3);
        }
        if (i2 < -1) {
            throw new IllegalArgumentException("Bad case index: " + i2);
        }
        int i5 = (~i) & 3;
        int i6 = i2 < 0 ? i + 1 + i5 : i + 1 + i5 + ((i2 + 3) * 4);
        if (i < 0 || ((i4 - 16) - i5) - 1 < i) {
            throw new IllegalArgumentException(i + " is outside a possible range of tableswitch in already generated code");
        }
        byte[] bArr = this.itsCodeBuffer;
        if ((bArr[i] & 255) != 170) {
            throw new IllegalArgumentException(i + " is not offset of tableswitch statement");
        }
        if (i6 < 0 || i4 < i6 + 4) {
            throw new ClassFileFormatException("Too big case index: " + i2);
        }
        putInt32(i3 - i, bArr, i6);
    }

    public int acquireLabel() {
        int i = this.itsLabelTableTop;
        int[] iArr = this.itsLabelTable;
        if (iArr == null || i == iArr.length) {
            if (iArr == null) {
                this.itsLabelTable = new int[32];
            } else {
                int[] iArr2 = new int[iArr.length * 2];
                System.arraycopy(iArr, 0, iArr2, 0, i);
                this.itsLabelTable = iArr2;
            }
        }
        this.itsLabelTableTop = i + 1;
        this.itsLabelTable[i] = -1;
        return i | Integer.MIN_VALUE;
    }

    public void markLabel(int i) {
        if (i >= 0) {
            throw new IllegalArgumentException("Bad label, no biscuit");
        }
        int i2 = i & Integer.MAX_VALUE;
        if (i2 > this.itsLabelTableTop) {
            throw new IllegalArgumentException("Bad label");
        }
        int[] iArr = this.itsLabelTable;
        if (iArr[i2] != -1) {
            throw new IllegalStateException("Can only mark label once");
        }
        iArr[i2] = this.itsCodeBufferTop;
    }

    public void markLabel(int i, short s) {
        markLabel(i);
        this.itsStackTop = s;
    }

    public void markHandler(int i) {
        this.itsStackTop = (short) 1;
        markLabel(i);
    }

    public int getLabelPC(int i) {
        if (i >= 0) {
            throw new IllegalArgumentException("Bad label, no biscuit");
        }
        int i2 = i & Integer.MAX_VALUE;
        if (i2 >= this.itsLabelTableTop) {
            throw new IllegalArgumentException("Bad label");
        }
        return this.itsLabelTable[i2];
    }

    private void addLabelFixup(int i, int i2) {
        if (i >= 0) {
            throw new IllegalArgumentException("Bad label, no biscuit");
        }
        int i3 = i & Integer.MAX_VALUE;
        if (i3 >= this.itsLabelTableTop) {
            throw new IllegalArgumentException("Bad label");
        }
        int i4 = this.itsFixupTableTop;
        long[] jArr = this.itsFixupTable;
        if (jArr == null || i4 == jArr.length) {
            if (jArr == null) {
                this.itsFixupTable = new long[40];
            } else {
                long[] jArr2 = new long[jArr.length * 2];
                System.arraycopy(jArr, 0, jArr2, 0, i4);
                this.itsFixupTable = jArr2;
            }
        }
        this.itsFixupTableTop = i4 + 1;
        this.itsFixupTable[i4] = ((long) i2) | (((long) i3) << 32);
    }

    private void fixLabelGotos() {
        byte[] bArr = this.itsCodeBuffer;
        for (int i = 0; i < this.itsFixupTableTop; i++) {
            long j = this.itsFixupTable[i];
            int i2 = (int) (j >> 32);
            int i3 = (int) j;
            int i4 = this.itsLabelTable[i2];
            if (i4 == -1) {
                throw new RuntimeException("unlocated label");
            }
            addSuperBlockStart(i4);
            int i5 = i3 - 1;
            this.itsJumpFroms.put(i4, i5);
            int i6 = i4 - i5;
            if (((short) i6) != i6) {
                throw new ClassFileFormatException("Program too complex: too big jump offset");
            }
            bArr[i3] = (byte) (i6 >> 8);
            bArr[i3 + 1] = (byte) i6;
        }
        this.itsFixupTableTop = 0;
    }

    public int getCurrentCodeOffset() {
        return this.itsCodeBufferTop;
    }

    public short getStackTop() {
        return this.itsStackTop;
    }

    public void setStackTop(short s) {
        this.itsStackTop = s;
    }

    public void adjustStackTop(int i) {
        int i2 = this.itsStackTop + i;
        if (i2 < 0 || 32767 < i2) {
            badStack(i2);
        }
        short s = (short) i2;
        this.itsStackTop = s;
        if (i2 > this.itsMaxStack) {
            this.itsMaxStack = s;
        }
    }

    private void addToCodeBuffer(int i) {
        this.itsCodeBuffer[addReservedCodeSpace(1)] = (byte) i;
    }

    private void addToCodeInt16(int i) {
        putInt16(i, this.itsCodeBuffer, addReservedCodeSpace(2));
    }

    private int addReservedCodeSpace(int i) {
        if (this.itsCurrentMethod == null) {
            throw new IllegalArgumentException("No method to add to");
        }
        int i2 = this.itsCodeBufferTop;
        int i3 = i + i2;
        byte[] bArr = this.itsCodeBuffer;
        if (i3 > bArr.length) {
            int length = bArr.length * 2;
            if (i3 > length) {
                length = i3;
            }
            byte[] bArr2 = new byte[length];
            System.arraycopy(bArr, 0, bArr2, 0, i2);
            this.itsCodeBuffer = bArr2;
        }
        this.itsCodeBufferTop = i3;
        return i2;
    }

    public void addExceptionHandler(int i, int i2, int i3, String str) {
        if ((i & Integer.MIN_VALUE) != Integer.MIN_VALUE) {
            throw new IllegalArgumentException("Bad startLabel");
        }
        if ((i2 & Integer.MIN_VALUE) != Integer.MIN_VALUE) {
            throw new IllegalArgumentException("Bad endLabel");
        }
        if ((i3 & Integer.MIN_VALUE) != Integer.MIN_VALUE) {
            throw new IllegalArgumentException("Bad handlerLabel");
        }
        ExceptionTableEntry exceptionTableEntry = new ExceptionTableEntry(i, i2, i3, str == null ? (short) 0 : this.itsConstantPool.addClass(str));
        int i4 = this.itsExceptionTableTop;
        if (i4 == 0) {
            this.itsExceptionTable = new ExceptionTableEntry[4];
        } else {
            ExceptionTableEntry[] exceptionTableEntryArr = this.itsExceptionTable;
            if (i4 == exceptionTableEntryArr.length) {
                ExceptionTableEntry[] exceptionTableEntryArr2 = new ExceptionTableEntry[i4 * 2];
                System.arraycopy(exceptionTableEntryArr, 0, exceptionTableEntryArr2, 0, i4);
                this.itsExceptionTable = exceptionTableEntryArr2;
            }
        }
        this.itsExceptionTable[i4] = exceptionTableEntry;
        this.itsExceptionTableTop = i4 + 1;
    }

    public void addLineNumberEntry(short s) {
        if (this.itsCurrentMethod == null) {
            throw new IllegalArgumentException("No method to stop");
        }
        int i = this.itsLineNumberTableTop;
        if (i == 0) {
            this.itsLineNumberTable = new int[16];
        } else {
            int[] iArr = this.itsLineNumberTable;
            if (i == iArr.length) {
                int[] iArr2 = new int[i * 2];
                System.arraycopy(iArr, 0, iArr2, 0, i);
                this.itsLineNumberTable = iArr2;
            }
        }
        this.itsLineNumberTable[i] = (this.itsCodeBufferTop << 16) + s;
        this.itsLineNumberTableTop = i + 1;
    }

    final class StackMapTable {
        static final boolean DEBUGSTACKMAP = false;
        private SuperBlock[] superBlocks = null;
        private int[] stack = null;
        private int[] locals = null;
        private SuperBlock[] workList = null;
        private byte[] rawStackMap = null;
        private int localsTop = 0;
        private int stackTop = 0;
        private int workListTop = 0;
        private int rawStackMapTop = 0;
        private boolean wide = false;

        private boolean isBranch(int i) {
            switch (i) {
                case 153:
                case 154:
                case 155:
                case 156:
                case 157:
                case 158:
                case 159:
                case 160:
                case 161:
                case 162:
                case 163:
                case 164:
                case 165:
                case 166:
                case 167:
                    return true;
                default:
                    switch (i) {
                        case 198:
                        case 199:
                        case 200:
                            return true;
                        default:
                            return false;
                    }
            }
        }

        private boolean isSuperBlockEnd(int i) {
            if (i == 167 || i == 191 || i == 200 || i == 176 || i == 177) {
                return true;
            }
            switch (i) {
                case 170:
                case 171:
                case 172:
                case 173:
                case 174:
                    return true;
                default:
                    return false;
            }
        }

        StackMapTable() {
        }

        void generate() {
            this.superBlocks = new SuperBlock[ClassFileWriter.this.itsSuperBlockStartsTop];
            int[] iArrCreateInitialLocals = ClassFileWriter.this.createInitialLocals();
            int i = 0;
            while (i < ClassFileWriter.this.itsSuperBlockStartsTop) {
                this.superBlocks[i] = new SuperBlock(i, ClassFileWriter.this.itsSuperBlockStarts[i], i == ClassFileWriter.this.itsSuperBlockStartsTop + (-1) ? ClassFileWriter.this.itsCodeBufferTop : ClassFileWriter.this.itsSuperBlockStarts[i + 1], iArrCreateInitialLocals);
                i++;
            }
            verify();
        }

        private SuperBlock getSuperBlockFromOffset(int i) {
            SuperBlock superBlock;
            int i2 = 0;
            while (true) {
                SuperBlock[] superBlockArr = this.superBlocks;
                if (i2 >= superBlockArr.length || (superBlock = superBlockArr[i2]) == null) {
                    break;
                }
                if (i >= superBlock.getStart() && i < superBlock.getEnd()) {
                    return superBlock;
                }
                i2++;
            }
            throw new IllegalArgumentException("bad offset: " + i);
        }

        private SuperBlock[] getSuperBlockDependencies() {
            SuperBlock[] superBlockArr = new SuperBlock[this.superBlocks.length];
            for (int i = 0; i < ClassFileWriter.this.itsExceptionTableTop; i++) {
                ExceptionTableEntry exceptionTableEntry = ClassFileWriter.this.itsExceptionTable[i];
                superBlockArr[getSuperBlockFromOffset(ClassFileWriter.this.getLabelPC(exceptionTableEntry.itsHandlerLabel)).getIndex()] = getSuperBlockFromOffset(ClassFileWriter.this.getLabelPC(exceptionTableEntry.itsStartLabel));
            }
            for (int i2 : ClassFileWriter.this.itsJumpFroms.getKeys()) {
                superBlockArr[getSuperBlockFromOffset(i2).getIndex()] = getSuperBlockFromOffset(ClassFileWriter.this.itsJumpFroms.getInt(i2, -1));
            }
            return superBlockArr;
        }

        private SuperBlock getBranchTarget(int i) {
            int operand;
            if ((ClassFileWriter.this.itsCodeBuffer[i] & 255) == 200) {
                operand = getOperand(i + 1, 4);
            } else {
                operand = (short) getOperand(i + 1, 2);
            }
            return getSuperBlockFromOffset(i + operand);
        }

        private int getOperand(int i) {
            return getOperand(i, 1);
        }

        private int getOperand(int i, int i2) {
            if (i2 > 4) {
                throw new IllegalArgumentException("bad operand size");
            }
            int i3 = 0;
            for (int i4 = 0; i4 < i2; i4++) {
                i3 = (i3 << 8) | (ClassFileWriter.this.itsCodeBuffer[i + i4] & 255);
            }
            return i3;
        }

        private void verify() {
            int[] iArrCreateInitialLocals = ClassFileWriter.this.createInitialLocals();
            int i = 0;
            this.superBlocks[0].merge(iArrCreateInitialLocals, iArrCreateInitialLocals.length, new int[0], 0, ClassFileWriter.this.itsConstantPool);
            this.workList = new SuperBlock[]{this.superBlocks[0]};
            this.workListTop = 1;
            executeWorkList();
            while (true) {
                SuperBlock[] superBlockArr = this.superBlocks;
                if (i < superBlockArr.length) {
                    SuperBlock superBlock = superBlockArr[i];
                    if (!superBlock.isInitialized()) {
                        killSuperBlock(superBlock);
                    }
                    i++;
                } else {
                    executeWorkList();
                    return;
                }
            }
        }

        private void killSuperBlock(SuperBlock superBlock) {
            int[] locals = new int[0];
            int[] iArr = {TypeInfo.OBJECT("java/lang/Throwable", ClassFileWriter.this.itsConstantPool)};
            for (int i = 0; i < ClassFileWriter.this.itsExceptionTableTop; i++) {
                ExceptionTableEntry exceptionTableEntry = ClassFileWriter.this.itsExceptionTable[i];
                int labelPC = ClassFileWriter.this.getLabelPC(exceptionTableEntry.itsStartLabel);
                int labelPC2 = ClassFileWriter.this.getLabelPC(exceptionTableEntry.itsEndLabel);
                SuperBlock superBlockFromOffset = getSuperBlockFromOffset(ClassFileWriter.this.getLabelPC(exceptionTableEntry.itsHandlerLabel));
                if ((superBlock.getStart() > labelPC && superBlock.getStart() < labelPC2) || (labelPC > superBlock.getStart() && labelPC < superBlock.getEnd() && superBlockFromOffset.isInitialized())) {
                    locals = superBlockFromOffset.getLocals();
                    break;
                }
            }
            int[] iArr2 = locals;
            int i2 = 0;
            while (i2 < ClassFileWriter.this.itsExceptionTableTop) {
                if (ClassFileWriter.this.getLabelPC(ClassFileWriter.this.itsExceptionTable[i2].itsStartLabel) == superBlock.getStart()) {
                    for (int i3 = i2 + 1; i3 < ClassFileWriter.this.itsExceptionTableTop; i3++) {
                        ClassFileWriter.this.itsExceptionTable[i3 - 1] = ClassFileWriter.this.itsExceptionTable[i3];
                    }
                    ClassFileWriter.access$410(ClassFileWriter.this);
                    i2--;
                }
                i2++;
            }
            superBlock.merge(iArr2, iArr2.length, iArr, 1, ClassFileWriter.this.itsConstantPool);
            int end = superBlock.getEnd() - 1;
            ClassFileWriter.this.itsCodeBuffer[end] = -65;
            for (int start = superBlock.getStart(); start < end; start++) {
                ClassFileWriter.this.itsCodeBuffer[start] = 0;
            }
        }

        private void executeWorkList() {
            while (true) {
                int i = this.workListTop;
                if (i <= 0) {
                    return;
                }
                SuperBlock[] superBlockArr = this.workList;
                int i2 = i - 1;
                this.workListTop = i2;
                SuperBlock superBlock = superBlockArr[i2];
                superBlock.setInQueue(false);
                this.locals = superBlock.getLocals();
                int[] stack = superBlock.getStack();
                this.stack = stack;
                this.localsTop = this.locals.length;
                this.stackTop = stack.length;
                executeBlock(superBlock);
            }
        }

        private void executeBlock(SuperBlock superBlock) {
            int iOBJECT;
            int start = superBlock.getStart();
            int i = 0;
            while (start < superBlock.getEnd()) {
                i = ClassFileWriter.this.itsCodeBuffer[start] & 255;
                int iExecute = execute(start);
                if (isBranch(i)) {
                    flowInto(getBranchTarget(start));
                } else if (i == 170) {
                    int i2 = start + 1 + ((~start) & 3);
                    flowInto(getSuperBlockFromOffset(getOperand(i2, 4) + start));
                    int operand = (getOperand(i2 + 8, 4) - getOperand(i2 + 4, 4)) + 1;
                    int i3 = i2 + 12;
                    for (int i4 = 0; i4 < operand; i4++) {
                        flowInto(getSuperBlockFromOffset(getOperand((i4 * 4) + i3, 4) + start));
                    }
                }
                for (int i5 = 0; i5 < ClassFileWriter.this.itsExceptionTableTop; i5++) {
                    ExceptionTableEntry exceptionTableEntry = ClassFileWriter.this.itsExceptionTable[i5];
                    int labelPC = ClassFileWriter.this.getLabelPC(exceptionTableEntry.itsStartLabel);
                    int labelPC2 = ClassFileWriter.this.getLabelPC(exceptionTableEntry.itsEndLabel);
                    if (start >= labelPC && start < labelPC2) {
                        SuperBlock superBlockFromOffset = getSuperBlockFromOffset(ClassFileWriter.this.getLabelPC(exceptionTableEntry.itsHandlerLabel));
                        if (exceptionTableEntry.itsCatchType == 0) {
                            iOBJECT = TypeInfo.OBJECT(ClassFileWriter.this.itsConstantPool.addClass("java/lang/Throwable"));
                        } else {
                            iOBJECT = TypeInfo.OBJECT(exceptionTableEntry.itsCatchType);
                        }
                        superBlockFromOffset.merge(this.locals, this.localsTop, new int[]{iOBJECT}, 1, ClassFileWriter.this.itsConstantPool);
                        addToWorkList(superBlockFromOffset);
                    }
                }
                start += iExecute;
            }
            if (isSuperBlockEnd(i)) {
                return;
            }
            int index = superBlock.getIndex() + 1;
            SuperBlock[] superBlockArr = this.superBlocks;
            if (index < superBlockArr.length) {
                flowInto(superBlockArr[index]);
            }
        }

        private void flowInto(SuperBlock superBlock) {
            if (superBlock.merge(this.locals, this.localsTop, this.stack, this.stackTop, ClassFileWriter.this.itsConstantPool)) {
                addToWorkList(superBlock);
            }
        }

        private void addToWorkList(SuperBlock superBlock) {
            if (superBlock.isInQueue()) {
                return;
            }
            superBlock.setInQueue(true);
            superBlock.setInitialized(true);
            int i = this.workListTop;
            SuperBlock[] superBlockArr = this.workList;
            if (i == superBlockArr.length) {
                SuperBlock[] superBlockArr2 = new SuperBlock[i * 2];
                System.arraycopy(superBlockArr, 0, superBlockArr2, 0, i);
                this.workList = superBlockArr2;
            }
            SuperBlock[] superBlockArr3 = this.workList;
            int i2 = this.workListTop;
            this.workListTop = i2 + 1;
            superBlockArr3[i2] = superBlock;
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        /* JADX WARN: Code duplicated, block: B:121:0x03a7  */
        /* JADX WARN: Code duplicated, block: B:124:0x03b1  */
        private int execute(int i) {
            int iOpcodeLength;
            int operand;
            int iPop;
            int tag;
            int i2 = ClassFileWriter.this.itsCodeBuffer[i] & 255;
            switch (i2) {
                case 0:
                case 132:
                case 167:
                case 200:
                    iOpcodeLength = 0;
                    if (iOpcodeLength == 0) {
                        iOpcodeLength = ClassFileWriter.opcodeLength(i2, this.wide);
                    }
                    if (this.wide && i2 != 196) {
                        this.wide = false;
                    }
                    return iOpcodeLength;
                case 1:
                    push(5);
                    iOpcodeLength = 0;
                    if (iOpcodeLength == 0) {
                        iOpcodeLength = ClassFileWriter.opcodeLength(i2, this.wide);
                    }
                    if (this.wide) {
                        this.wide = false;
                    }
                    return iOpcodeLength;
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                case 8:
                case 16:
                case 17:
                case 21:
                case 26:
                case 27:
                case 28:
                case 29:
                    push(1);
                    iOpcodeLength = 0;
                    if (iOpcodeLength == 0) {
                        iOpcodeLength = ClassFileWriter.opcodeLength(i2, this.wide);
                    }
                    if (this.wide) {
                        this.wide = false;
                    }
                    return iOpcodeLength;
                case 9:
                case 10:
                case 22:
                case 30:
                case 31:
                case 32:
                case 33:
                    push(4);
                    iOpcodeLength = 0;
                    if (iOpcodeLength == 0) {
                        iOpcodeLength = ClassFileWriter.opcodeLength(i2, this.wide);
                    }
                    if (this.wide) {
                        this.wide = false;
                    }
                    return iOpcodeLength;
                case 11:
                case 12:
                case 13:
                case 23:
                case 34:
                case 35:
                case 36:
                case 37:
                    push(2);
                    iOpcodeLength = 0;
                    if (iOpcodeLength == 0) {
                        iOpcodeLength = ClassFileWriter.opcodeLength(i2, this.wide);
                    }
                    if (this.wide) {
                        this.wide = false;
                    }
                    return iOpcodeLength;
                case 14:
                case 15:
                case 24:
                case 38:
                case 39:
                case 40:
                case 41:
                    push(3);
                    iOpcodeLength = 0;
                    if (iOpcodeLength == 0) {
                        iOpcodeLength = ClassFileWriter.opcodeLength(i2, this.wide);
                    }
                    if (this.wide) {
                        this.wide = false;
                    }
                    return iOpcodeLength;
                case 18:
                case 19:
                case 20:
                    if (i2 == 18) {
                        operand = getOperand(i + 1);
                    } else {
                        operand = getOperand(i + 1, 2);
                    }
                    byte constantType = ClassFileWriter.this.itsConstantPool.getConstantType(operand);
                    if (constantType == 3) {
                        push(1);
                    } else if (constantType == 4) {
                        push(2);
                    } else if (constantType == 5) {
                        push(4);
                    } else if (constantType == 6) {
                        push(3);
                    } else if (constantType == 8) {
                        push(TypeInfo.OBJECT("java/lang/String", ClassFileWriter.this.itsConstantPool));
                    } else {
                        throw new IllegalArgumentException("bad const type " + ((int) constantType));
                    }
                    iOpcodeLength = 0;
                    if (iOpcodeLength == 0) {
                        iOpcodeLength = ClassFileWriter.opcodeLength(i2, this.wide);
                    }
                    if (this.wide) {
                        this.wide = false;
                    }
                    return iOpcodeLength;
                case 25:
                    executeALoad(getOperand(i + 1, this.wide ? 2 : 1));
                    iOpcodeLength = 0;
                    if (iOpcodeLength == 0) {
                        iOpcodeLength = ClassFileWriter.opcodeLength(i2, this.wide);
                    }
                    if (this.wide) {
                        this.wide = false;
                    }
                    return iOpcodeLength;
                case 42:
                case 43:
                case 44:
                case 45:
                    executeALoad(i2 - 42);
                    iOpcodeLength = 0;
                    if (iOpcodeLength == 0) {
                        iOpcodeLength = ClassFileWriter.opcodeLength(i2, this.wide);
                    }
                    if (this.wide) {
                        this.wide = false;
                    }
                    return iOpcodeLength;
                case 46:
                case 51:
                case 52:
                case 53:
                case 96:
                case 100:
                case 104:
                case 108:
                case 112:
                case 120:
                case 122:
                case 124:
                case 126:
                case 128:
                case 130:
                case 148:
                case 149:
                case 150:
                case 151:
                case 152:
                    pop();
                    pop();
                    push(1);
                    iOpcodeLength = 0;
                    if (iOpcodeLength == 0) {
                        iOpcodeLength = ClassFileWriter.opcodeLength(i2, this.wide);
                    }
                    if (this.wide) {
                        this.wide = false;
                    }
                    return iOpcodeLength;
                case 47:
                case 97:
                case 101:
                case 105:
                case 109:
                case 113:
                case 121:
                case 123:
                case 125:
                case 127:
                case 129:
                case 131:
                    pop();
                    pop();
                    push(4);
                    iOpcodeLength = 0;
                    if (iOpcodeLength == 0) {
                        iOpcodeLength = ClassFileWriter.opcodeLength(i2, this.wide);
                    }
                    if (this.wide) {
                        this.wide = false;
                    }
                    return iOpcodeLength;
                case 48:
                case 98:
                case 102:
                case 106:
                case 110:
                case 114:
                    pop();
                    pop();
                    push(2);
                    iOpcodeLength = 0;
                    if (iOpcodeLength == 0) {
                        iOpcodeLength = ClassFileWriter.opcodeLength(i2, this.wide);
                    }
                    if (this.wide) {
                        this.wide = false;
                    }
                    return iOpcodeLength;
                case 49:
                case 99:
                case 103:
                case 107:
                case 111:
                case 115:
                    pop();
                    pop();
                    push(3);
                    iOpcodeLength = 0;
                    if (iOpcodeLength == 0) {
                        iOpcodeLength = ClassFileWriter.opcodeLength(i2, this.wide);
                    }
                    if (this.wide) {
                        this.wide = false;
                    }
                    return iOpcodeLength;
                case 50:
                    pop();
                    String str = (String) ClassFileWriter.this.itsConstantPool.getConstantData(pop() >>> 8);
                    if (str.charAt(0) != '[') {
                        throw new IllegalStateException("bad array type");
                    }
                    push(TypeInfo.OBJECT(ClassFileWriter.this.itsConstantPool.addClass(ClassFileWriter.descriptorToInternalName(str.substring(1)))));
                    iOpcodeLength = 0;
                    if (iOpcodeLength == 0) {
                        iOpcodeLength = ClassFileWriter.opcodeLength(i2, this.wide);
                    }
                    if (this.wide) {
                        this.wide = false;
                    }
                    return iOpcodeLength;
                case 54:
                    executeStore(getOperand(i + 1, this.wide ? 2 : 1), 1);
                    iOpcodeLength = 0;
                    if (iOpcodeLength == 0) {
                        iOpcodeLength = ClassFileWriter.opcodeLength(i2, this.wide);
                    }
                    if (this.wide) {
                        this.wide = false;
                    }
                    return iOpcodeLength;
                case 55:
                    executeStore(getOperand(i + 1, this.wide ? 2 : 1), 4);
                    iOpcodeLength = 0;
                    if (iOpcodeLength == 0) {
                        iOpcodeLength = ClassFileWriter.opcodeLength(i2, this.wide);
                    }
                    if (this.wide) {
                        this.wide = false;
                    }
                    return iOpcodeLength;
                case 56:
                    executeStore(getOperand(i + 1, this.wide ? 2 : 1), 2);
                    iOpcodeLength = 0;
                    if (iOpcodeLength == 0) {
                        iOpcodeLength = ClassFileWriter.opcodeLength(i2, this.wide);
                    }
                    if (this.wide) {
                        this.wide = false;
                    }
                    return iOpcodeLength;
                case 57:
                    executeStore(getOperand(i + 1, this.wide ? 2 : 1), 3);
                    iOpcodeLength = 0;
                    if (iOpcodeLength == 0) {
                        iOpcodeLength = ClassFileWriter.opcodeLength(i2, this.wide);
                    }
                    if (this.wide) {
                        this.wide = false;
                    }
                    return iOpcodeLength;
                case 58:
                    executeAStore(getOperand(i + 1, this.wide ? 2 : 1));
                    iOpcodeLength = 0;
                    if (iOpcodeLength == 0) {
                        iOpcodeLength = ClassFileWriter.opcodeLength(i2, this.wide);
                    }
                    if (this.wide) {
                        this.wide = false;
                    }
                    return iOpcodeLength;
                case 59:
                case 60:
                case 61:
                case 62:
                    executeStore(i2 - 59, 1);
                    iOpcodeLength = 0;
                    if (iOpcodeLength == 0) {
                        iOpcodeLength = ClassFileWriter.opcodeLength(i2, this.wide);
                    }
                    if (this.wide) {
                        this.wide = false;
                    }
                    return iOpcodeLength;
                case 63:
                case 64:
                case 65:
                case 66:
                    executeStore(i2 - 63, 4);
                    iOpcodeLength = 0;
                    if (iOpcodeLength == 0) {
                        iOpcodeLength = ClassFileWriter.opcodeLength(i2, this.wide);
                    }
                    if (this.wide) {
                        this.wide = false;
                    }
                    return iOpcodeLength;
                case 67:
                case 68:
                case 69:
                case 70:
                    executeStore(i2 - 67, 2);
                    iOpcodeLength = 0;
                    if (iOpcodeLength == 0) {
                        iOpcodeLength = ClassFileWriter.opcodeLength(i2, this.wide);
                    }
                    if (this.wide) {
                        this.wide = false;
                    }
                    return iOpcodeLength;
                case 71:
                case 72:
                case 73:
                case 74:
                    executeStore(i2 - 71, 3);
                    iOpcodeLength = 0;
                    if (iOpcodeLength == 0) {
                        iOpcodeLength = ClassFileWriter.opcodeLength(i2, this.wide);
                    }
                    if (this.wide) {
                        this.wide = false;
                    }
                    return iOpcodeLength;
                case 75:
                case 76:
                case 77:
                case 78:
                    executeAStore(i2 - 75);
                    iOpcodeLength = 0;
                    if (iOpcodeLength == 0) {
                        iOpcodeLength = ClassFileWriter.opcodeLength(i2, this.wide);
                    }
                    if (this.wide) {
                        this.wide = false;
                    }
                    return iOpcodeLength;
                case 79:
                case 80:
                case 81:
                case 82:
                case 83:
                case 84:
                case 85:
                case 86:
                    pop();
                    pop();
                    pop();
                    iOpcodeLength = 0;
                    if (iOpcodeLength == 0) {
                        iOpcodeLength = ClassFileWriter.opcodeLength(i2, this.wide);
                    }
                    if (this.wide) {
                        this.wide = false;
                    }
                    return iOpcodeLength;
                case 87:
                case 153:
                case 154:
                case 155:
                case 156:
                case 157:
                case 158:
                case 179:
                case 194:
                case 195:
                case 198:
                case 199:
                    pop();
                    iOpcodeLength = 0;
                    if (iOpcodeLength == 0) {
                        iOpcodeLength = ClassFileWriter.opcodeLength(i2, this.wide);
                    }
                    if (this.wide) {
                        this.wide = false;
                    }
                    return iOpcodeLength;
                case 88:
                    pop2();
                    iOpcodeLength = 0;
                    if (iOpcodeLength == 0) {
                        iOpcodeLength = ClassFileWriter.opcodeLength(i2, this.wide);
                    }
                    if (this.wide) {
                        this.wide = false;
                    }
                    return iOpcodeLength;
                case 89:
                    int iPop2 = pop();
                    push(iPop2);
                    push(iPop2);
                    iOpcodeLength = 0;
                    if (iOpcodeLength == 0) {
                        iOpcodeLength = ClassFileWriter.opcodeLength(i2, this.wide);
                    }
                    if (this.wide) {
                        this.wide = false;
                    }
                    return iOpcodeLength;
                case 90:
                    int iPop3 = pop();
                    int iPop4 = pop();
                    push(iPop3);
                    push(iPop4);
                    push(iPop3);
                    iOpcodeLength = 0;
                    if (iOpcodeLength == 0) {
                        iOpcodeLength = ClassFileWriter.opcodeLength(i2, this.wide);
                    }
                    if (this.wide) {
                        this.wide = false;
                    }
                    return iOpcodeLength;
                case 91:
                    int iPop5 = pop();
                    long jPop2 = pop2();
                    push(iPop5);
                    push2(jPop2);
                    push(iPop5);
                    iOpcodeLength = 0;
                    if (iOpcodeLength == 0) {
                        iOpcodeLength = ClassFileWriter.opcodeLength(i2, this.wide);
                    }
                    if (this.wide) {
                        this.wide = false;
                    }
                    return iOpcodeLength;
                case 92:
                    long jPop3 = pop2();
                    push2(jPop3);
                    push2(jPop3);
                    iOpcodeLength = 0;
                    if (iOpcodeLength == 0) {
                        iOpcodeLength = ClassFileWriter.opcodeLength(i2, this.wide);
                    }
                    if (this.wide) {
                        this.wide = false;
                    }
                    return iOpcodeLength;
                case 93:
                    long jPop4 = pop2();
                    int iPop6 = pop();
                    push2(jPop4);
                    push(iPop6);
                    push2(jPop4);
                    iOpcodeLength = 0;
                    if (iOpcodeLength == 0) {
                        iOpcodeLength = ClassFileWriter.opcodeLength(i2, this.wide);
                    }
                    if (this.wide) {
                        this.wide = false;
                    }
                    return iOpcodeLength;
                case 94:
                    long jPop5 = pop2();
                    long jPop6 = pop2();
                    push2(jPop5);
                    push2(jPop6);
                    push2(jPop5);
                    iOpcodeLength = 0;
                    if (iOpcodeLength == 0) {
                        iOpcodeLength = ClassFileWriter.opcodeLength(i2, this.wide);
                    }
                    if (this.wide) {
                        this.wide = false;
                    }
                    return iOpcodeLength;
                case 95:
                    int iPop7 = pop();
                    int iPop8 = pop();
                    push(iPop7);
                    push(iPop8);
                    iOpcodeLength = 0;
                    if (iOpcodeLength == 0) {
                        iOpcodeLength = ClassFileWriter.opcodeLength(i2, this.wide);
                    }
                    if (this.wide) {
                        this.wide = false;
                    }
                    return iOpcodeLength;
                case 116:
                case 136:
                case 139:
                case 142:
                case 145:
                case 146:
                case 147:
                case 190:
                case 193:
                    pop();
                    push(1);
                    iOpcodeLength = 0;
                    if (iOpcodeLength == 0) {
                        iOpcodeLength = ClassFileWriter.opcodeLength(i2, this.wide);
                    }
                    if (this.wide) {
                        this.wide = false;
                    }
                    return iOpcodeLength;
                case 117:
                case 133:
                case 140:
                case 143:
                    pop();
                    push(4);
                    iOpcodeLength = 0;
                    if (iOpcodeLength == 0) {
                        iOpcodeLength = ClassFileWriter.opcodeLength(i2, this.wide);
                    }
                    if (this.wide) {
                        this.wide = false;
                    }
                    return iOpcodeLength;
                case 118:
                case 134:
                case 137:
                case 144:
                    pop();
                    push(2);
                    iOpcodeLength = 0;
                    if (iOpcodeLength == 0) {
                        iOpcodeLength = ClassFileWriter.opcodeLength(i2, this.wide);
                    }
                    if (this.wide) {
                        this.wide = false;
                    }
                    return iOpcodeLength;
                case 119:
                case 135:
                case 138:
                case 141:
                    pop();
                    push(3);
                    iOpcodeLength = 0;
                    if (iOpcodeLength == 0) {
                        iOpcodeLength = ClassFileWriter.opcodeLength(i2, this.wide);
                    }
                    if (this.wide) {
                        this.wide = false;
                    }
                    return iOpcodeLength;
                case 159:
                case 160:
                case 161:
                case 162:
                case 163:
                case 164:
                case 165:
                case 166:
                case 181:
                    pop();
                    pop();
                    iOpcodeLength = 0;
                    if (iOpcodeLength == 0) {
                        iOpcodeLength = ClassFileWriter.opcodeLength(i2, this.wide);
                    }
                    if (this.wide) {
                        this.wide = false;
                    }
                    return iOpcodeLength;
                case 168:
                case 169:
                case 171:
                case 197:
                default:
                    throw new IllegalArgumentException("bad opcode: " + i2);
                case 170:
                    int i3 = i + 1 + ((~i) & 3);
                    iOpcodeLength = ((((getOperand(i3 + 8, 4) - getOperand(i3 + 4, 4)) + 4) * 4) + i3) - i;
                    pop();
                    if (iOpcodeLength == 0) {
                        iOpcodeLength = ClassFileWriter.opcodeLength(i2, this.wide);
                    }
                    if (this.wide) {
                        this.wide = false;
                    }
                    return iOpcodeLength;
                case 172:
                case 173:
                case 174:
                case 175:
                case 176:
                case 177:
                    clearStack();
                    iOpcodeLength = 0;
                    if (iOpcodeLength == 0) {
                        iOpcodeLength = ClassFileWriter.opcodeLength(i2, this.wide);
                    }
                    if (this.wide) {
                        this.wide = false;
                    }
                    return iOpcodeLength;
                case 178:
                    push(TypeInfo.fromType(ClassFileWriter.descriptorToInternalName(((FieldOrMethodRef) ClassFileWriter.this.itsConstantPool.getConstantData(getOperand(i + 1, 2))).getType()), ClassFileWriter.this.itsConstantPool));
                    iOpcodeLength = 0;
                    if (iOpcodeLength == 0) {
                        iOpcodeLength = ClassFileWriter.opcodeLength(i2, this.wide);
                    }
                    if (this.wide) {
                        this.wide = false;
                    }
                    return iOpcodeLength;
                case 180:
                    pop();
                    push(TypeInfo.fromType(ClassFileWriter.descriptorToInternalName(((FieldOrMethodRef) ClassFileWriter.this.itsConstantPool.getConstantData(getOperand(i + 1, 2))).getType()), ClassFileWriter.this.itsConstantPool));
                    iOpcodeLength = 0;
                    if (iOpcodeLength == 0) {
                        iOpcodeLength = ClassFileWriter.opcodeLength(i2, this.wide);
                    }
                    if (this.wide) {
                        this.wide = false;
                    }
                    return iOpcodeLength;
                case 182:
                case 183:
                case 184:
                case 185:
                    FieldOrMethodRef fieldOrMethodRef = (FieldOrMethodRef) ClassFileWriter.this.itsConstantPool.getConstantData(getOperand(i + 1, 2));
                    String type = fieldOrMethodRef.getType();
                    String name2 = fieldOrMethodRef.getName();
                    int iSizeOfParameters = ClassFileWriter.sizeOfParameters(type) >>> 16;
                    for (int i4 = 0; i4 < iSizeOfParameters; i4++) {
                        pop();
                    }
                    if (i2 != 184 && ((tag = TypeInfo.getTag((iPop = pop()))) == TypeInfo.UNINITIALIZED_VARIABLE(0) || tag == 6)) {
                        if ("<init>".equals(name2)) {
                            initializeTypeInfo(iPop, TypeInfo.OBJECT(ClassFileWriter.this.itsThisClassIndex));
                        } else {
                            throw new IllegalStateException("bad instance");
                        }
                    }
                    String strDescriptorToInternalName = ClassFileWriter.descriptorToInternalName(type.substring(type.indexOf(41) + 1));
                    if (!strDescriptorToInternalName.equals(ExifInterface.GPS_MEASUREMENT_INTERRUPTED)) {
                        push(TypeInfo.fromType(strDescriptorToInternalName, ClassFileWriter.this.itsConstantPool));
                    }
                    iOpcodeLength = 0;
                    if (iOpcodeLength == 0) {
                        iOpcodeLength = ClassFileWriter.opcodeLength(i2, this.wide);
                    }
                    if (this.wide) {
                        this.wide = false;
                    }
                    return iOpcodeLength;
                case 186:
                    String str2 = (String) ClassFileWriter.this.itsConstantPool.getConstantData(getOperand(i + 1, 2));
                    int iSizeOfParameters2 = ClassFileWriter.sizeOfParameters(str2) >>> 16;
                    for (int i5 = 0; i5 < iSizeOfParameters2; i5++) {
                        pop();
                    }
                    String strDescriptorToInternalName2 = ClassFileWriter.descriptorToInternalName(str2.substring(str2.indexOf(41) + 1));
                    if (!strDescriptorToInternalName2.equals(ExifInterface.GPS_MEASUREMENT_INTERRUPTED)) {
                        push(TypeInfo.fromType(strDescriptorToInternalName2, ClassFileWriter.this.itsConstantPool));
                    }
                    iOpcodeLength = 0;
                    if (iOpcodeLength == 0) {
                        iOpcodeLength = ClassFileWriter.opcodeLength(i2, this.wide);
                    }
                    if (this.wide) {
                        this.wide = false;
                    }
                    return iOpcodeLength;
                case 187:
                    push(TypeInfo.UNINITIALIZED_VARIABLE(i));
                    iOpcodeLength = 0;
                    if (iOpcodeLength == 0) {
                        iOpcodeLength = ClassFileWriter.opcodeLength(i2, this.wide);
                    }
                    if (this.wide) {
                        this.wide = false;
                    }
                    return iOpcodeLength;
                case 188:
                    pop();
                    push(TypeInfo.OBJECT(ClassFileWriter.this.itsConstantPool.addClass("[" + ClassFileWriter.arrayTypeToName(ClassFileWriter.this.itsCodeBuffer[i + 1]))));
                    iOpcodeLength = 0;
                    if (iOpcodeLength == 0) {
                        iOpcodeLength = ClassFileWriter.opcodeLength(i2, this.wide);
                    }
                    if (this.wide) {
                        this.wide = false;
                    }
                    return iOpcodeLength;
                case 189:
                    String str3 = (String) ClassFileWriter.this.itsConstantPool.getConstantData(getOperand(i + 1, 2));
                    pop();
                    push(TypeInfo.OBJECT("[L" + str3 + ';', ClassFileWriter.this.itsConstantPool));
                    iOpcodeLength = 0;
                    if (iOpcodeLength == 0) {
                        iOpcodeLength = ClassFileWriter.opcodeLength(i2, this.wide);
                    }
                    if (this.wide) {
                        this.wide = false;
                    }
                    return iOpcodeLength;
                case 191:
                    int iPop9 = pop();
                    clearStack();
                    push(iPop9);
                    iOpcodeLength = 0;
                    if (iOpcodeLength == 0) {
                        iOpcodeLength = ClassFileWriter.opcodeLength(i2, this.wide);
                    }
                    if (this.wide) {
                        this.wide = false;
                    }
                    return iOpcodeLength;
                case 192:
                    pop();
                    push(TypeInfo.OBJECT(getOperand(i + 1, 2)));
                    iOpcodeLength = 0;
                    if (iOpcodeLength == 0) {
                        iOpcodeLength = ClassFileWriter.opcodeLength(i2, this.wide);
                    }
                    if (this.wide) {
                        this.wide = false;
                    }
                    return iOpcodeLength;
                case 196:
                    this.wide = true;
                    iOpcodeLength = 0;
                    if (iOpcodeLength == 0) {
                        iOpcodeLength = ClassFileWriter.opcodeLength(i2, this.wide);
                    }
                    if (this.wide) {
                        this.wide = false;
                    }
                    return iOpcodeLength;
            }
        }

        private void executeALoad(int i) {
            int local = getLocal(i);
            int tag = TypeInfo.getTag(local);
            if (tag == 7 || tag == 6 || tag == 8 || tag == 5) {
                push(local);
                return;
            }
            throw new IllegalStateException("bad local variable type: " + local + " at index: " + i);
        }

        private void executeAStore(int i) {
            setLocal(i, pop());
        }

        private void executeStore(int i, int i2) {
            pop();
            setLocal(i, i2);
        }

        private void initializeTypeInfo(int i, int i2) {
            initializeTypeInfo(i, i2, this.locals, this.localsTop);
            initializeTypeInfo(i, i2, this.stack, this.stackTop);
        }

        private void initializeTypeInfo(int i, int i2, int[] iArr, int i3) {
            for (int i4 = 0; i4 < i3; i4++) {
                if (iArr[i4] == i) {
                    iArr[i4] = i2;
                }
            }
        }

        private int getLocal(int i) {
            if (i < this.localsTop) {
                return this.locals[i];
            }
            return 0;
        }

        private void setLocal(int i, int i2) {
            int i3 = this.localsTop;
            if (i >= i3) {
                int i4 = i + 1;
                int[] iArr = new int[i4];
                System.arraycopy(this.locals, 0, iArr, 0, i3);
                this.locals = iArr;
                this.localsTop = i4;
            }
            this.locals[i] = i2;
        }

        private void push(int i) {
            int i2 = this.stackTop;
            if (i2 == this.stack.length) {
                int[] iArr = new int[Math.max(i2 * 2, 4)];
                System.arraycopy(this.stack, 0, iArr, 0, this.stackTop);
                this.stack = iArr;
            }
            int[] iArr2 = this.stack;
            int i3 = this.stackTop;
            this.stackTop = i3 + 1;
            iArr2[i3] = i;
        }

        private int pop() {
            int[] iArr = this.stack;
            int i = this.stackTop - 1;
            this.stackTop = i;
            return iArr[i];
        }

        private void push2(long j) {
            push((int) (j & 16777215));
            long j2 = j >>> 32;
            if (j2 != 0) {
                push((int) (j2 & 16777215));
            }
        }

        private long pop2() {
            long jPop = pop();
            return TypeInfo.isTwoWords((int) jPop) ? jPop : (jPop << 32) | ((long) (pop() & 16777215));
        }

        private void clearStack() {
            this.stackTop = 0;
        }

        int computeWriteSize() {
            this.rawStackMap = new byte[getWorstCaseWriteSize()];
            computeRawStackMap();
            return this.rawStackMapTop + 2;
        }

        int write(byte[] bArr, int i) {
            int iPutInt16 = ClassFileWriter.putInt16(this.superBlocks.length - 1, bArr, ClassFileWriter.putInt32(this.rawStackMapTop + 2, bArr, i));
            System.arraycopy(this.rawStackMap, 0, bArr, iPutInt16, this.rawStackMapTop);
            return iPutInt16 + this.rawStackMapTop;
        }

        private void computeRawStackMap() {
            int[] trimmedLocals = this.superBlocks[0].getTrimmedLocals();
            int start = -1;
            int i = 1;
            while (true) {
                SuperBlock[] superBlockArr = this.superBlocks;
                if (i >= superBlockArr.length) {
                    return;
                }
                SuperBlock superBlock = superBlockArr[i];
                int[] trimmedLocals2 = superBlock.getTrimmedLocals();
                int[] stack = superBlock.getStack();
                int start2 = (superBlock.getStart() - start) - 1;
                if (stack.length == 0) {
                    int length = trimmedLocals.length > trimmedLocals2.length ? trimmedLocals2.length : trimmedLocals.length;
                    int iAbs = Math.abs(trimmedLocals.length - trimmedLocals2.length);
                    int i2 = 0;
                    while (i2 < length && trimmedLocals[i2] == trimmedLocals2[i2]) {
                        i2++;
                    }
                    if (i2 == trimmedLocals2.length && iAbs == 0) {
                        writeSameFrame(start2);
                    } else if (i2 == trimmedLocals2.length && iAbs <= 3) {
                        writeChopFrame(iAbs, start2);
                    } else if (i2 == trimmedLocals.length && iAbs <= 3) {
                        writeAppendFrame(trimmedLocals2, iAbs, start2);
                    } else {
                        writeFullFrame(trimmedLocals2, stack, start2);
                    }
                } else if (stack.length == 1) {
                    if (Arrays.equals(trimmedLocals, trimmedLocals2)) {
                        writeSameLocalsOneStackItemFrame(stack, start2);
                    } else {
                        writeFullFrame(trimmedLocals2, stack, start2);
                    }
                } else {
                    writeFullFrame(trimmedLocals2, stack, start2);
                }
                start = superBlock.getStart();
                i++;
                trimmedLocals = trimmedLocals2;
            }
        }

        private int getWorstCaseWriteSize() {
            return (this.superBlocks.length - 1) * ((ClassFileWriter.this.itsMaxLocals * 3) + 7 + (ClassFileWriter.this.itsMaxStack * 3));
        }

        private void writeSameFrame(int i) {
            if (i <= 63) {
                byte[] bArr = this.rawStackMap;
                int i2 = this.rawStackMapTop;
                this.rawStackMapTop = i2 + 1;
                bArr[i2] = (byte) i;
                return;
            }
            byte[] bArr2 = this.rawStackMap;
            int i3 = this.rawStackMapTop;
            int i4 = i3 + 1;
            this.rawStackMapTop = i4;
            bArr2[i3] = -5;
            this.rawStackMapTop = ClassFileWriter.putInt16(i, bArr2, i4);
        }

        private void writeSameLocalsOneStackItemFrame(int[] iArr, int i) {
            if (i <= 63) {
                byte[] bArr = this.rawStackMap;
                int i2 = this.rawStackMapTop;
                this.rawStackMapTop = i2 + 1;
                bArr[i2] = (byte) (i + 64);
            } else {
                byte[] bArr2 = this.rawStackMap;
                int i3 = this.rawStackMapTop;
                int i4 = i3 + 1;
                this.rawStackMapTop = i4;
                bArr2[i3] = -9;
                this.rawStackMapTop = ClassFileWriter.putInt16(i, bArr2, i4);
            }
            writeType(iArr[0]);
        }

        private void writeFullFrame(int[] iArr, int[] iArr2, int i) {
            byte[] bArr = this.rawStackMap;
            int i2 = this.rawStackMapTop;
            int i3 = i2 + 1;
            this.rawStackMapTop = i3;
            bArr[i2] = -1;
            int iPutInt16 = ClassFileWriter.putInt16(i, bArr, i3);
            this.rawStackMapTop = iPutInt16;
            this.rawStackMapTop = ClassFileWriter.putInt16(iArr.length, this.rawStackMap, iPutInt16);
            int iWriteTypes = writeTypes(iArr);
            this.rawStackMapTop = iWriteTypes;
            this.rawStackMapTop = ClassFileWriter.putInt16(iArr2.length, this.rawStackMap, iWriteTypes);
            this.rawStackMapTop = writeTypes(iArr2);
        }

        private void writeAppendFrame(int[] iArr, int i, int i2) {
            int length = iArr.length - i;
            byte[] bArr = this.rawStackMap;
            int i3 = this.rawStackMapTop;
            int i4 = i3 + 1;
            this.rawStackMapTop = i4;
            bArr[i3] = (byte) (i + 251);
            this.rawStackMapTop = ClassFileWriter.putInt16(i2, bArr, i4);
            this.rawStackMapTop = writeTypes(iArr, length);
        }

        private void writeChopFrame(int i, int i2) {
            byte[] bArr = this.rawStackMap;
            int i3 = this.rawStackMapTop;
            int i4 = i3 + 1;
            this.rawStackMapTop = i4;
            bArr[i3] = (byte) (251 - i);
            this.rawStackMapTop = ClassFileWriter.putInt16(i2, bArr, i4);
        }

        private int writeTypes(int[] iArr) {
            return writeTypes(iArr, 0);
        }

        private int writeTypes(int[] iArr, int i) {
            while (i < iArr.length) {
                this.rawStackMapTop = writeType(iArr[i]);
                i++;
            }
            return this.rawStackMapTop;
        }

        private int writeType(int i) {
            int i2 = i & 255;
            byte[] bArr = this.rawStackMap;
            int i3 = this.rawStackMapTop;
            int i4 = i3 + 1;
            this.rawStackMapTop = i4;
            bArr[i3] = (byte) i2;
            if (i2 == 7 || i2 == 8) {
                this.rawStackMapTop = ClassFileWriter.putInt16(i >>> 8, bArr, i4);
            }
            return this.rawStackMapTop;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static char arrayTypeToName(int i) {
        switch (i) {
            case 4:
                return 'Z';
            case 5:
                return Util.C_CHAR;
            case 6:
                return Util.C_FLOAT;
            case 7:
                return Util.C_DOUBLE;
            case 8:
                return Util.C_BYTE;
            case 9:
                return Util.C_SHORT;
            case 10:
                return 'I';
            case 11:
                return Util.C_LONG;
            default:
                throw new IllegalArgumentException("bad operand");
        }
    }

    private static String classDescriptorToInternalName(String str) {
        return str.substring(1, str.length() - 1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String descriptorToInternalName(String str) {
        char cCharAt = str.charAt(0);
        if (cCharAt == 'F') {
            return str;
        }
        if (cCharAt == 'L') {
            return classDescriptorToInternalName(str);
        }
        if (cCharAt == 'S' || cCharAt == 'V' || cCharAt == 'I' || cCharAt == 'J' || cCharAt == 'Z' || cCharAt == '[') {
            return str;
        }
        switch (cCharAt) {
            case 'B':
            case 'C':
            case 'D':
                return str;
            default:
                throw new IllegalArgumentException("bad descriptor:" + str);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code duplicated, block: B:36:0x00a6  */
    /* JADX WARN: Code duplicated, block: B:37:0x00a9  */
    public int[] createInitialLocals() {
        int i;
        int iFromType;
        int i2;
        int[] iArr = new int[this.itsMaxLocals];
        if ((this.itsCurrentMethod.getFlags() & 8) == 0) {
            if ("<init>".equals(this.itsCurrentMethod.getName())) {
                iArr[0] = 6;
            } else {
                iArr[0] = TypeInfo.OBJECT(this.itsThisClassIndex);
            }
            i = 1;
        } else {
            i = 0;
        }
        String type = this.itsCurrentMethod.getType();
        int iIndexOf = type.indexOf(40);
        int iIndexOf2 = type.indexOf(41);
        if (iIndexOf != 0 || iIndexOf2 < 0) {
            throw new IllegalArgumentException("bad method type");
        }
        int i3 = iIndexOf + 1;
        StringBuilder sb = new StringBuilder();
        while (i3 < iIndexOf2) {
            char cCharAt = type.charAt(i3);
            if (cCharAt != 'F') {
                if (cCharAt == 'L') {
                    int iIndexOf3 = type.indexOf(59, i3) + 1;
                    sb.append(type.substring(i3, iIndexOf3));
                    i3 = iIndexOf3;
                } else if (cCharAt != 'S' && cCharAt != 'I' && cCharAt != 'J' && cCharAt != 'Z') {
                    if (cCharAt != '[') {
                        switch (cCharAt) {
                        }
                    } else {
                        sb.append('[');
                        i3++;
                    }
                }
                iFromType = TypeInfo.fromType(descriptorToInternalName(sb.toString()), this.itsConstantPool);
                i2 = i + 1;
                iArr[i] = iFromType;
                if (TypeInfo.isTwoWords(iFromType)) {
                    i += 2;
                } else {
                    i = i2;
                }
                sb.setLength(0);
            }
            sb.append(type.charAt(i3));
            i3++;
            iFromType = TypeInfo.fromType(descriptorToInternalName(sb.toString()), this.itsConstantPool);
            i2 = i + 1;
            iArr[i] = iFromType;
            if (TypeInfo.isTwoWords(iFromType)) {
                i += 2;
            } else {
                i = i2;
            }
            sb.setLength(0);
        }
        return iArr;
    }

    public void write(OutputStream outputStream) throws IOException {
        outputStream.write(toByteArray());
    }

    private int getWriteSize() {
        if (this.itsSourceFileNameIndex != 0) {
            this.itsConstantPool.addUtf8("SourceFile");
        }
        int writeSize = this.itsConstantPool.getWriteSize() + 16 + (this.itsInterfaces.size() * 2) + 2;
        for (int i = 0; i < this.itsFields.size(); i++) {
            writeSize += ((ClassFileField) this.itsFields.get(i)).getWriteSize();
        }
        int writeSize2 = writeSize + 2;
        for (int i2 = 0; i2 < this.itsMethods.size(); i2++) {
            writeSize2 += ((ClassFileMethod) this.itsMethods.get(i2)).getWriteSize();
        }
        int i3 = writeSize2 + 2;
        if (this.itsSourceFileNameIndex != 0) {
            i3 = writeSize2 + 10;
        }
        return this.itsBootstrapMethods != null ? i3 + 8 + this.itsBootstrapMethodsLength : i3;
    }

    public byte[] toByteArray() {
        short sAddUtf8;
        int i;
        short sAddUtf9;
        if (this.itsBootstrapMethods != null) {
            sAddUtf8 = this.itsConstantPool.addUtf8(AttBootstrapMethods.ATTRIBUTE_NAME);
            i = 1;
        } else {
            sAddUtf8 = 0;
            i = 0;
        }
        if (this.itsSourceFileNameIndex != 0) {
            i++;
            sAddUtf9 = this.itsConstantPool.addUtf8("SourceFile");
        } else {
            sAddUtf9 = 0;
        }
        int writeSize = getWriteSize();
        byte[] bArr = new byte[writeSize];
        int iPutInt16 = putInt16(this.itsInterfaces.size(), bArr, putInt16(this.itsSuperClassIndex, bArr, putInt16(this.itsThisClassIndex, bArr, putInt16(this.itsFlags, bArr, this.itsConstantPool.write(bArr, putInt16(MajorVersion, bArr, putInt16(MinorVersion, bArr, putInt32(FileHeaderConstant, bArr, 0))))))));
        for (int i2 = 0; i2 < this.itsInterfaces.size(); i2++) {
            iPutInt16 = putInt16(((Short) this.itsInterfaces.get(i2)).shortValue(), bArr, iPutInt16);
        }
        int iPutInt17 = putInt16(this.itsFields.size(), bArr, iPutInt16);
        for (int i3 = 0; i3 < this.itsFields.size(); i3++) {
            iPutInt17 = ((ClassFileField) this.itsFields.get(i3)).write(bArr, iPutInt17);
        }
        int iPutInt18 = putInt16(this.itsMethods.size(), bArr, iPutInt17);
        for (int i4 = 0; i4 < this.itsMethods.size(); i4++) {
            iPutInt18 = ((ClassFileMethod) this.itsMethods.get(i4)).write(bArr, iPutInt18);
        }
        int iPutInt19 = putInt16(i, bArr, iPutInt18);
        if (this.itsBootstrapMethods != null) {
            iPutInt19 = putInt16(this.itsBootstrapMethods.size(), bArr, putInt32(this.itsBootstrapMethodsLength + 2, bArr, putInt16(sAddUtf8, bArr, iPutInt19)));
            for (int i5 = 0; i5 < this.itsBootstrapMethods.size(); i5++) {
                BootstrapEntry bootstrapEntry = (BootstrapEntry) this.itsBootstrapMethods.get(i5);
                System.arraycopy(bootstrapEntry.code, 0, bArr, iPutInt19, bootstrapEntry.code.length);
                iPutInt19 += bootstrapEntry.code.length;
            }
        }
        if (this.itsSourceFileNameIndex != 0) {
            iPutInt19 = putInt16(this.itsSourceFileNameIndex, bArr, putInt32(2, bArr, putInt16(sAddUtf9, bArr, iPutInt19)));
        }
        if (iPutInt19 == writeSize) {
            return bArr;
        }
        throw new RuntimeException();
    }

    static int putInt64(long j, byte[] bArr, int i) {
        return putInt32((int) j, bArr, putInt32((int) (j >>> 32), bArr, i));
    }

    private static void badStack(int i) {
        String str;
        if (i < 0) {
            str = "Stack underflow: " + i;
        } else {
            str = "Too big stack: " + i;
        }
        throw new IllegalStateException(str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code duplicated, block: B:58:0x00af  */
    /* JADX WARN: Code duplicated, block: B:59:0x00b1 A[PHI: r6
      0x00b1: PHI (r6v3 int) = (r6v2 int), (r6v2 int), (r6v2 int), (r6v2 int), (r6v6 int), (r6v2 int), (r6v2 int), (r6v2 int) binds: [B:47:0x0098, B:48:0x009a, B:49:0x009c, B:52:0x00a2, B:58:0x00af, B:54:0x00a6, B:55:0x00a8, B:56:0x00aa] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Failed to find 'out' block for switch in B:21:0x0044. Please report as an issue. */
    public static int sizeOfParameters(String str) {
        int i;
        int length = str.length();
        int iLastIndexOf = str.lastIndexOf(41);
        if (3 <= length) {
            boolean z = false;
            if (str.charAt(0) == '(' && 1 <= iLastIndexOf && (i = iLastIndexOf + 1) < length) {
                int i2 = 0;
                int i3 = 0;
                int i4 = 1;
                while (true) {
                    if (i4 != iLastIndexOf) {
                        char cCharAt = str.charAt(i4);
                        if (cCharAt != 'F') {
                            if (cCharAt != 'L') {
                                if (cCharAt != 'S' && cCharAt != 'I') {
                                    if (cCharAt != 'J') {
                                        if (cCharAt != 'Z') {
                                            if (cCharAt != '[') {
                                                switch (cCharAt) {
                                                }
                                            } else {
                                                i4++;
                                                char cCharAt2 = str.charAt(i4);
                                                while (cCharAt2 == '[') {
                                                    i4++;
                                                    cCharAt2 = str.charAt(i4);
                                                }
                                                if (cCharAt2 != 'F') {
                                                    if (cCharAt2 != 'L') {
                                                        if (cCharAt2 != 'S' && cCharAt2 != 'Z' && cCharAt2 != 'I' && cCharAt2 != 'J') {
                                                            switch (cCharAt2) {
                                                                case 'B':
                                                                case 'C':
                                                                case 'D':
                                                                    break;
                                                                default:
                                                                    break;
                                                            }
                                                        }
                                                    }
                                                }
                                                i2--;
                                                i3++;
                                                i4++;
                                            }
                                        }
                                    }
                                    i2--;
                                }
                            }
                            i2--;
                            i3++;
                            int iIndexOf = str.indexOf(59, i4 + 1);
                            if (i4 + 2 > iIndexOf || iIndexOf >= iLastIndexOf) {
                                z = false;
                            } else {
                                i4 = iIndexOf + 1;
                                z = false;
                            }
                        }
                        i2--;
                        i3++;
                        i4++;
                        z = false;
                    } else {
                        z = true;
                    }
                }
                if (z) {
                    char cCharAt3 = str.charAt(i);
                    if (cCharAt3 != 'F' && cCharAt3 != 'L' && cCharAt3 != 'S') {
                        if (cCharAt3 != 'V') {
                            if (cCharAt3 != 'I') {
                                if (cCharAt3 != 'J') {
                                    if (cCharAt3 != 'Z' && cCharAt3 != '[') {
                                        switch (cCharAt3) {
                                            case 'D':
                                                i2++;
                                            case 'B':
                                            case 'C':
                                                i2++;
                                                break;
                                            default:
                                                z = false;
                                                break;
                                        }
                                    } else {
                                        i2++;
                                    }
                                } else {
                                    i2++;
                                    i2++;
                                }
                            } else {
                                i2++;
                            }
                        }
                    } else {
                        i2++;
                    }
                    if (z) {
                        return (i3 << 16) | (65535 & i2);
                    }
                }
            }
        }
        throw new IllegalArgumentException("Bad parameter signature: " + str);
    }

    static int putInt16(int i, byte[] bArr, int i2) {
        bArr[i2] = (byte) (i >>> 8);
        bArr[i2 + 1] = (byte) i;
        return i2 + 2;
    }

    static int putInt32(int i, byte[] bArr, int i2) {
        bArr[i2] = (byte) (i >>> 24);
        bArr[i2 + 1] = (byte) (i >>> 16);
        bArr[i2 + 2] = (byte) (i >>> 8);
        bArr[i2 + 3] = (byte) i;
        return i2 + 4;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code duplicated, block: B:21:0x0032 A[RETURN] */
    /* JADX WARN: Code duplicated, block: B:22:0x0033 A[RETURN] */
    public static int opcodeLength(int i, boolean z) {
        if (i == 254 || i == 255) {
            return 1;
        }
        switch (i) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 26:
            case 27:
            case 28:
            case 29:
            case 30:
            case 31:
            case 32:
            case 33:
            case 34:
            case 35:
            case 36:
            case 37:
            case 38:
            case 39:
            case 40:
            case 41:
            case 42:
            case 43:
            case 44:
            case 45:
            case 46:
            case 47:
            case 48:
            case 49:
            case 50:
            case 51:
            case 52:
            case 53:
            case 59:
            case 60:
            case 61:
            case 62:
            case 63:
            case 64:
            case 65:
            case 66:
            case 67:
            case 68:
            case 69:
            case 70:
            case 71:
            case 72:
            case 73:
            case 74:
            case 75:
            case 76:
            case 77:
            case 78:
            case 79:
            case 80:
            case 81:
            case 82:
            case 83:
            case 84:
            case 85:
            case 86:
            case 87:
            case 88:
            case 89:
            case 90:
            case 91:
            case 92:
            case 93:
            case 94:
            case 95:
            case 96:
            case 97:
            case 98:
            case 99:
            case 100:
            case 101:
            case 102:
            case 103:
            case 104:
            case 105:
            case 106:
            case 107:
            case 108:
            case 109:
            case 110:
            case 111:
            case 112:
            case 113:
            case 114:
            case 115:
            case 116:
            case 117:
            case 118:
            case 119:
            case 120:
            case 121:
            case 122:
            case 123:
            case 124:
            case 125:
            case 126:
            case 127:
            case 128:
            case 129:
            case 130:
            case 131:
            case 133:
            case 134:
            case 135:
            case 136:
            case 137:
            case 138:
            case 139:
            case 140:
            case 141:
            case 142:
            case 143:
            case 144:
            case 145:
            case 146:
            case 147:
            case 148:
            case 149:
            case 150:
            case 151:
            case 152:
                return 1;
            case 16:
            case 18:
                return 2;
            case 17:
            case 19:
            case 20:
            case 153:
            case 154:
            case 155:
            case 156:
            case 157:
            case 158:
            case 159:
            case 160:
            case 161:
            case 162:
            case 163:
            case 164:
            case 165:
            case 166:
            case 167:
            case 168:
                return 3;
            case 21:
            case 22:
            case 23:
            case 24:
            case 25:
            case 54:
            case 55:
            case 56:
            case 57:
            case 58:
            case 169:
                return z ? 3 : 2;
            case 132:
                return z ? 5 : 3;
            default:
                switch (i) {
                    case 172:
                    case 173:
                    case 174:
                    case 175:
                    case 176:
                    case 177:
                    case 190:
                    case 191:
                    case 194:
                    case 195:
                    case 196:
                    case 202:
                        return 1;
                    case 178:
                    case 179:
                    case 180:
                    case 181:
                    case 182:
                    case 183:
                    case 184:
                    case 187:
                    case 189:
                    case 192:
                    case 193:
                    case 198:
                    case 199:
                        return 3;
                    case 185:
                    case 186:
                    case 200:
                    case 201:
                        return 5;
                    case 188:
                        return 2;
                    case 197:
                        return 4;
                    default:
                        throw new IllegalArgumentException("Bad opcode: " + i);
                }
        }
    }

    private static int opcodeCount(int i) {
        if (i == 254 || i == 255) {
            return 0;
        }
        switch (i) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 26:
            case 27:
            case 28:
            case 29:
            case 30:
            case 31:
            case 32:
            case 33:
            case 34:
            case 35:
            case 36:
            case 37:
            case 38:
            case 39:
            case 40:
            case 41:
            case 42:
            case 43:
            case 44:
            case 45:
            case 46:
            case 47:
            case 48:
            case 49:
            case 50:
            case 51:
            case 52:
            case 53:
            case 59:
            case 60:
            case 61:
            case 62:
            case 63:
            case 64:
            case 65:
            case 66:
            case 67:
            case 68:
            case 69:
            case 70:
            case 71:
            case 72:
            case 73:
            case 74:
            case 75:
            case 76:
            case 77:
            case 78:
            case 79:
            case 80:
            case 81:
            case 82:
            case 83:
            case 84:
            case 85:
            case 86:
            case 87:
            case 88:
            case 89:
            case 90:
            case 91:
            case 92:
            case 93:
            case 94:
            case 95:
            case 96:
            case 97:
            case 98:
            case 99:
            case 100:
            case 101:
            case 102:
            case 103:
            case 104:
            case 105:
            case 106:
            case 107:
            case 108:
            case 109:
            case 110:
            case 111:
            case 112:
            case 113:
            case 114:
            case 115:
            case 116:
            case 117:
            case 118:
            case 119:
            case 120:
            case 121:
            case 122:
            case 123:
            case 124:
            case 125:
            case 126:
            case 127:
            case 128:
            case 129:
            case 130:
            case 131:
            case 133:
            case 134:
            case 135:
            case 136:
            case 137:
            case 138:
            case 139:
            case 140:
            case 141:
            case 142:
            case 143:
            case 144:
            case 145:
            case 146:
            case 147:
            case 148:
            case 149:
            case 150:
            case 151:
            case 152:
            case 172:
            case 173:
            case 174:
            case 175:
            case 176:
            case 177:
                return 0;
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
            case 24:
            case 25:
            case 54:
            case 55:
            case 56:
            case 57:
            case 58:
            case 153:
            case 154:
            case 155:
            case 156:
            case 157:
            case 158:
            case 159:
            case 160:
            case 161:
            case 162:
            case 163:
            case 164:
            case 165:
            case 166:
            case 167:
            case 168:
            case 169:
            case 178:
            case 179:
            case 180:
            case 181:
            case 182:
            case 183:
            case 184:
            case 185:
                return 1;
            case 132:
                return 2;
            case 170:
            case 171:
                return -1;
            default:
                switch (i) {
                    case 187:
                    case 188:
                    case 189:
                    case 192:
                    case 193:
                    case 198:
                    case 199:
                    case 200:
                    case 201:
                        return 1;
                    case 190:
                    case 191:
                    case 194:
                    case 195:
                    case 196:
                    case 202:
                        return 0;
                    case 197:
                        return 2;
                    default:
                        throw new IllegalArgumentException("Bad opcode: " + i);
                }
        }
    }

    private static int stackChange(int i) {
        if (i == 254 || i == 255) {
            return 0;
        }
        switch (i) {
            case 0:
            case 47:
            case 49:
            case 95:
            case 116:
            case 117:
            case 118:
            case 119:
            case 132:
            case 134:
            case 138:
            case 139:
            case 143:
            case 145:
            case 146:
            case 147:
            case 167:
            case 169:
            case 177:
            case 178:
            case 179:
            case 184:
            case 186:
            case 188:
            case 189:
            case 190:
            case 192:
            case 193:
            case 196:
            case 200:
            case 202:
                return 0;
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 11:
            case 12:
            case 13:
            case 16:
            case 17:
            case 18:
            case 19:
            case 21:
            case 23:
            case 25:
            case 26:
            case 27:
            case 28:
            case 29:
            case 34:
            case 35:
            case 36:
            case 37:
            case 42:
            case 43:
            case 44:
            case 45:
            case 89:
            case 90:
            case 91:
            case 133:
            case 135:
            case 140:
            case 141:
            case 168:
            case 187:
            case 197:
            case 201:
                return 1;
            case 9:
            case 10:
            case 14:
            case 15:
            case 20:
            case 22:
            case 24:
            case 30:
            case 31:
            case 32:
            case 33:
            case 38:
            case 39:
            case 40:
            case 41:
            case 92:
            case 93:
            case 94:
                return 2;
            case 46:
            case 48:
            case 50:
            case 51:
            case 52:
            case 53:
            case 54:
            case 56:
            case 58:
            case 59:
            case 60:
            case 61:
            case 62:
            case 67:
            case 68:
            case 69:
            case 70:
            case 75:
            case 76:
            case 77:
            case 78:
            case 87:
            case 96:
            case 98:
            case 100:
            case 102:
            case 104:
            case 106:
            case 108:
            case 110:
            case 112:
            case 114:
            case 120:
            case 121:
            case 122:
            case 123:
            case 124:
            case 125:
            case 126:
            case 128:
            case 130:
            case 136:
            case 137:
            case 142:
            case 144:
            case 149:
            case 150:
            case 153:
            case 154:
            case 155:
            case 156:
            case 157:
            case 158:
            case 170:
            case 171:
            case 172:
            case 174:
            case 176:
            case 180:
            case 181:
            case 182:
            case 183:
            case 185:
            case 191:
            case 194:
            case 195:
            case 198:
            case 199:
                return -1;
            case 55:
            case 57:
            case 63:
            case 64:
            case 65:
            case 66:
            case 71:
            case 72:
            case 73:
            case 74:
            case 88:
            case 97:
            case 99:
            case 101:
            case 103:
            case 105:
            case 107:
            case 109:
            case 111:
            case 113:
            case 115:
            case 127:
            case 129:
            case 131:
            case 159:
            case 160:
            case 161:
            case 162:
            case 163:
            case 164:
            case 165:
            case 166:
            case 173:
            case 175:
                return -2;
            case 79:
            case 81:
            case 83:
            case 84:
            case 85:
            case 86:
            case 148:
            case 151:
            case 152:
                return -3;
            case 80:
            case 82:
                return -4;
            default:
                throw new IllegalArgumentException("Bad opcode: " + i);
        }
    }

    private static String bytecodeStr(int i) {
        return "";
    }

    final char[] getCharBuffer(int i) {
        char[] cArr = this.tmpCharBuffer;
        if (i > cArr.length) {
            int length = cArr.length * 2;
            if (i <= length) {
                i = length;
            }
            this.tmpCharBuffer = new char[i];
        }
        return this.tmpCharBuffer;
    }

    private void addSuperBlockStart(int i) {
        if (GenerateStackMap) {
            int[] iArr = this.itsSuperBlockStarts;
            if (iArr == null) {
                this.itsSuperBlockStarts = new int[4];
            } else {
                int length = iArr.length;
                int i2 = this.itsSuperBlockStartsTop;
                if (length == i2) {
                    int[] iArr2 = new int[i2 * 2];
                    System.arraycopy(iArr, 0, iArr2, 0, i2);
                    this.itsSuperBlockStarts = iArr2;
                }
            }
            int[] iArr3 = this.itsSuperBlockStarts;
            int i3 = this.itsSuperBlockStartsTop;
            this.itsSuperBlockStartsTop = i3 + 1;
            iArr3[i3] = i;
        }
    }

    private void finalizeSuperBlockStarts() {
        if (GenerateStackMap) {
            for (int i = 0; i < this.itsExceptionTableTop; i++) {
                addSuperBlockStart(getLabelPC(this.itsExceptionTable[i].itsHandlerLabel));
            }
            Arrays.sort(this.itsSuperBlockStarts, 0, this.itsSuperBlockStartsTop);
            int i2 = this.itsSuperBlockStarts[0];
            int i3 = 1;
            for (int i4 = 1; i4 < this.itsSuperBlockStartsTop; i4++) {
                int[] iArr = this.itsSuperBlockStarts;
                int i5 = iArr[i4];
                if (i2 != i5) {
                    if (i3 != i4) {
                        iArr[i3] = i5;
                    }
                    i3++;
                    i2 = i5;
                }
            }
            this.itsSuperBlockStartsTop = i3;
            if (this.itsSuperBlockStarts[i3 - 1] == this.itsCodeBufferTop) {
                this.itsSuperBlockStartsTop = i3 - 1;
            }
        }
    }

    static {
        int i;
        InputStream resourceAsStream = null;
        try {
            resourceAsStream = ClassFileWriter.class.getResourceAsStream("ClassFileWriter.class");
            if (resourceAsStream == null) {
                resourceAsStream = ClassLoader.getSystemResourceAsStream("org/mozilla/classfile/ClassFileWriter.class");
            }
            byte[] bArr = new byte[8];
            int i2 = 0;
            while (i2 < 8) {
                int i3 = resourceAsStream.read(bArr, i2, 8 - i2);
                if (i3 < 0) {
                    throw new IOException();
                }
                i2 += i3;
            }
            i = (bArr[4] << 8) | (bArr[5] & 255);
            try {
                int i4 = (bArr[7] & 255) | (bArr[6] << 8);
                MinorVersion = i;
                MajorVersion = i4;
                GenerateStackMap = i4 >= 50;
                if (resourceAsStream == null) {
                    return;
                }
            } catch (Exception unused) {
                MinorVersion = i;
                MajorVersion = 48;
                GenerateStackMap = false;
                if (resourceAsStream == null) {
                    return;
                }
            } catch (Throwable th) {
                th = th;
                MinorVersion = i;
                MajorVersion = 48;
                GenerateStackMap = false;
                if (resourceAsStream != null) {
                    try {
                        resourceAsStream.close();
                    } catch (IOException unused2) {
                    }
                }
                throw th;
            }
        } catch (Exception unused3) {
            i = 0;
        } catch (Throwable th2) {
            th = th2;
            i = 0;
        }
        try {
            resourceAsStream.close();
        } catch (IOException unused4) {
        }
    }

    final class BootstrapEntry {
        final byte[] code;

        BootstrapEntry(MHandle mHandle, Object... objArr) {
            byte[] bArr = new byte[(objArr.length * 2) + 4];
            this.code = bArr;
            ClassFileWriter.putInt16(ClassFileWriter.this.itsConstantPool.addMethodHandle(mHandle), bArr, 0);
            ClassFileWriter.putInt16(objArr.length, bArr, 2);
            for (int i = 0; i < objArr.length; i++) {
                ClassFileWriter.putInt16(ClassFileWriter.this.itsConstantPool.addConstant(objArr[i]), this.code, (i * 2) + 4);
            }
        }

        public boolean equals(Object obj) {
            return (obj instanceof BootstrapEntry) && Arrays.equals(this.code, ((BootstrapEntry) obj).code);
        }

        public int hashCode() {
            return ~Arrays.hashCode(this.code);
        }
    }

    public static final class MHandle {
        final String desc;

        /* JADX INFO: renamed from: name, reason: collision with root package name */
        final String f1061name;
        final String owner;
        final byte tag;

        public MHandle(byte b, String str, String str2, String str3) {
            this.tag = b;
            this.owner = str;
            this.f1061name = str2;
            this.desc = str3;
        }

        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof MHandle)) {
                return false;
            }
            MHandle mHandle = (MHandle) obj;
            return this.tag == mHandle.tag && this.owner.equals(mHandle.owner) && this.f1061name.equals(mHandle.f1061name) && this.desc.equals(mHandle.desc);
        }

        public int hashCode() {
            return this.tag + (this.owner.hashCode() * this.f1061name.hashCode() * this.desc.hashCode());
        }

        public String toString() {
            return this.owner + '.' + this.f1061name + this.desc + " (" + ((int) this.tag) + ')';
        }
    }
}

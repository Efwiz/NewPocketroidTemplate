package org.jf.dexlib2.analysis;

import com.huawei.hms.ml.language.common.utils.Constant;
import java.io.IOException;
import java.io.Writer;
import org.jf.util.ExceptionWithContext;

/* JADX INFO: loaded from: classes6.dex */
public class RegisterType {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public static final byte BOOLEAN = 4;
    public static final byte BYTE = 5;
    public static final byte CHAR = 9;
    public static final byte CONFLICTED = 19;
    public static final byte DOUBLE_HI = 15;
    public static final byte DOUBLE_LO = 14;
    public static final byte FLOAT = 11;
    public static final byte INTEGER = 10;
    public static final byte LONG_HI = 13;
    public static final byte LONG_LO = 12;
    public static final byte NULL = 2;
    public static final byte ONE = 3;
    public static final byte POS_BYTE = 6;
    public static final byte POS_SHORT = 8;
    public static final byte REFERENCE = 18;
    public static final byte SHORT = 7;
    public static final byte UNINIT = 1;
    public static final byte UNINIT_REF = 16;
    public static final byte UNINIT_THIS = 17;
    public static final byte UNKNOWN = 0;
    public final byte category;
    public final TypeProto type;
    public static final String[] CATEGORY_NAMES = {"Unknown", "Uninit", "Null", "One", "Boolean", "Byte", "PosByte", "Short", "PosShort", "Char", "Integer", "Float", "LongLo", "LongHi", "DoubleLo", "DoubleHi", "UninitRef", "UninitThis", "Reference", "Conflicted"};
    protected static byte[][] mergeTable = {new byte[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19}, new byte[]{1, 1, 19, 19, 19, 19, 19, 19, 19, 19, 19, 19, 19, 19, 19, 19, 19, 19, 19, 19}, new byte[]{2, 19, 2, 4, 4, 5, 6, 7, 8, 9, 10, 11, 19, 19, 19, 19, 19, 19, 18, 19}, new byte[]{3, 19, 4, 3, 4, 5, 6, 7, 8, 9, 10, 11, 19, 19, 19, 19, 19, 19, 19, 19}, new byte[]{4, 19, 4, 4, 4, 5, 6, 7, 8, 9, 10, 11, 19, 19, 19, 19, 19, 19, 19, 19}, new byte[]{5, 19, 5, 5, 5, 5, 5, 7, 7, 10, 10, 11, 19, 19, 19, 19, 19, 19, 19, 19}, new byte[]{6, 19, 6, 6, 6, 5, 6, 7, 8, 9, 10, 11, 19, 19, 19, 19, 19, 19, 19, 19}, new byte[]{7, 19, 7, 7, 7, 7, 7, 7, 7, 10, 10, 11, 19, 19, 19, 19, 19, 19, 19, 19}, new byte[]{8, 19, 8, 8, 8, 7, 8, 7, 8, 9, 10, 11, 19, 19, 19, 19, 19, 19, 19, 19}, new byte[]{9, 19, 9, 9, 9, 10, 9, 10, 9, 9, 10, 11, 19, 19, 19, 19, 19, 19, 19, 19}, new byte[]{10, 19, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 19, 19, 19, 19, 19, 19, 19, 19}, new byte[]{11, 19, 11, 11, 11, 11, 11, 11, 11, 11, 10, 11, 19, 19, 19, 19, 19, 19, 19, 19}, new byte[]{12, 19, 19, 19, 19, 19, 19, 19, 19, 19, 19, 19, 12, 19, 12, 19, 19, 19, 19, 19}, new byte[]{13, 19, 19, 19, 19, 19, 19, 19, 19, 19, 19, 19, 19, 13, 19, 13, 19, 19, 19, 19}, new byte[]{14, 19, 19, 19, 19, 19, 19, 19, 19, 19, 19, 19, 12, 19, 14, 19, 19, 19, 19, 19}, new byte[]{15, 19, 19, 19, 19, 19, 19, 19, 19, 19, 19, 19, 19, 13, 19, 15, 19, 19, 19, 19}, new byte[]{16, 19, 19, 19, 19, 19, 19, 19, 19, 19, 19, 19, 19, 19, 19, 19, 19, 19, 19, 19}, new byte[]{17, 19, 19, 19, 19, 19, 19, 19, 19, 19, 19, 19, 19, 19, 19, 19, 19, 17, 19, 19}, new byte[]{18, 19, 18, 19, 19, 19, 19, 19, 19, 19, 19, 19, 19, 19, 19, 19, 19, 19, 18, 19}, new byte[]{19, 19, 19, 19, 19, 19, 19, 19, 19, 19, 19, 19, 19, 19, 19, 19, 19, 19, 19, 19}};
    public static final RegisterType UNKNOWN_TYPE = new RegisterType((byte) 0, null);
    public static final RegisterType UNINIT_TYPE = new RegisterType((byte) 1, null);
    public static final RegisterType NULL_TYPE = new RegisterType((byte) 2, null);
    public static final RegisterType ONE_TYPE = new RegisterType((byte) 3, null);
    public static final RegisterType BOOLEAN_TYPE = new RegisterType((byte) 4, null);
    public static final RegisterType BYTE_TYPE = new RegisterType((byte) 5, null);
    public static final RegisterType POS_BYTE_TYPE = new RegisterType((byte) 6, null);
    public static final RegisterType SHORT_TYPE = new RegisterType((byte) 7, null);
    public static final RegisterType POS_SHORT_TYPE = new RegisterType((byte) 8, null);
    public static final RegisterType CHAR_TYPE = new RegisterType((byte) 9, null);
    public static final RegisterType INTEGER_TYPE = new RegisterType((byte) 10, null);
    public static final RegisterType FLOAT_TYPE = new RegisterType((byte) 11, null);
    public static final RegisterType LONG_LO_TYPE = new RegisterType((byte) 12, null);
    public static final RegisterType LONG_HI_TYPE = new RegisterType((byte) 13, null);
    public static final RegisterType DOUBLE_LO_TYPE = new RegisterType((byte) 14, null);
    public static final RegisterType DOUBLE_HI_TYPE = new RegisterType((byte) 15, null);
    public static final RegisterType CONFLICTED_TYPE = new RegisterType((byte) 19, null);

    private RegisterType(byte b, TypeProto typeProto) {
        this.category = b;
        this.type = typeProto;
    }

    public String toString() {
        return "(" + CATEGORY_NAMES[this.category] + (this.type == null ? "" : "," + this.type) + Constant.AFTER_QUTO;
    }

    public void writeTo(Writer writer) throws IOException {
        writer.write(40);
        writer.write(CATEGORY_NAMES[this.category]);
        if (this.type != null) {
            writer.write(44);
            writer.write(this.type.getType());
        }
        writer.write(41);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            RegisterType registerType = (RegisterType) obj;
            byte b = this.category;
            if (b == registerType.category && b != 16 && b != 17) {
                TypeProto typeProto = this.type;
                TypeProto typeProto2 = registerType.type;
                if (typeProto != null) {
                    return typeProto.equals(typeProto2);
                }
                return typeProto2 == null;
            }
        }
        return false;
    }

    public int hashCode() {
        int i = this.category * 31;
        TypeProto typeProto = this.type;
        return i + (typeProto != null ? typeProto.hashCode() : 0);
    }

    public static RegisterType getWideRegisterType(CharSequence charSequence, boolean z) {
        char cCharAt = charSequence.charAt(0);
        if (cCharAt == 'D') {
            if (z) {
                return getRegisterType((byte) 14, (TypeProto) null);
            }
            return getRegisterType((byte) 15, (TypeProto) null);
        }
        if (cCharAt != 'J') {
            throw new ExceptionWithContext("Cannot use this method for narrow register type: %s", charSequence);
        }
        if (z) {
            return getRegisterType((byte) 12, (TypeProto) null);
        }
        return getRegisterType((byte) 13, (TypeProto) null);
    }

    public static RegisterType getRegisterType(ClassPath classPath, CharSequence charSequence) {
        char cCharAt = charSequence.charAt(0);
        if (cCharAt == 'F') {
            return FLOAT_TYPE;
        }
        if (cCharAt != 'L') {
            if (cCharAt == 'S') {
                return SHORT_TYPE;
            }
            if (cCharAt == 'I') {
                return INTEGER_TYPE;
            }
            if (cCharAt == 'J') {
                return LONG_LO_TYPE;
            }
            if (cCharAt == 'Z') {
                return BOOLEAN_TYPE;
            }
            if (cCharAt != '[') {
                switch (cCharAt) {
                    case 'B':
                        return BYTE_TYPE;
                    case 'C':
                        return CHAR_TYPE;
                    case 'D':
                        return DOUBLE_LO_TYPE;
                    default:
                        throw new AnalysisException("Invalid type: " + ((Object) charSequence), new Object[0]);
                }
            }
        }
        return getRegisterType((byte) 18, classPath.getClass(charSequence));
    }

    public static RegisterType getRegisterTypeForLiteral(int i) {
        if (i < -32768) {
            return INTEGER_TYPE;
        }
        if (i < -128) {
            return SHORT_TYPE;
        }
        if (i < 0) {
            return BYTE_TYPE;
        }
        if (i == 0) {
            return NULL_TYPE;
        }
        if (i == 1) {
            return ONE_TYPE;
        }
        if (i < 128) {
            return POS_BYTE_TYPE;
        }
        if (i < 32768) {
            return POS_SHORT_TYPE;
        }
        if (i < 65536) {
            return CHAR_TYPE;
        }
        return INTEGER_TYPE;
    }

    public RegisterType merge(RegisterType registerType) {
        TypeProto commonSuperclass;
        if (registerType.equals(this)) {
            return this;
        }
        byte[][] bArr = mergeTable;
        byte b = this.category;
        byte b2 = bArr[b][registerType.category];
        if (b2 == 18) {
            commonSuperclass = this.type;
            if (commonSuperclass != null) {
                TypeProto typeProto = registerType.type;
                if (typeProto != null) {
                    commonSuperclass = commonSuperclass.getCommonSuperclass(typeProto);
                }
            } else {
                commonSuperclass = registerType.type;
            }
        } else {
            if (b2 == 16 || b2 == 17) {
                return b == 0 ? registerType : this;
            }
            commonSuperclass = null;
        }
        if (commonSuperclass != null) {
            if (commonSuperclass.equals(this.type)) {
                return this;
            }
            if (commonSuperclass.equals(registerType.type)) {
                return registerType;
            }
        }
        return getRegisterType(b2, commonSuperclass);
    }

    public static RegisterType getRegisterType(byte b, TypeProto typeProto) {
        if (b != 19) {
            switch (b) {
                case 0:
                    return UNKNOWN_TYPE;
                case 1:
                    return UNINIT_TYPE;
                case 2:
                    return NULL_TYPE;
                case 3:
                    return ONE_TYPE;
                case 4:
                    return BOOLEAN_TYPE;
                case 5:
                    return BYTE_TYPE;
                case 6:
                    return POS_BYTE_TYPE;
                case 7:
                    return SHORT_TYPE;
                case 8:
                    return POS_SHORT_TYPE;
                case 9:
                    return CHAR_TYPE;
                case 10:
                    return INTEGER_TYPE;
                case 11:
                    return FLOAT_TYPE;
                case 12:
                    return LONG_LO_TYPE;
                case 13:
                    return LONG_HI_TYPE;
                case 14:
                    return DOUBLE_LO_TYPE;
                case 15:
                    return DOUBLE_HI_TYPE;
                default:
                    return new RegisterType(b, typeProto);
            }
        }
        return CONFLICTED_TYPE;
    }
}

package org.eclipse.jdt.internal.compiler.codegen;

import com.huawei.hms.ml.language.common.utils.Constant;
import org.eclipse.jdt.core.compiler.CharOperation;
import org.eclipse.jdt.internal.compiler.lookup.TypeBinding;
import org.eclipse.jdt.internal.compiler.util.Util;

/* JADX INFO: loaded from: classes14.dex */
public class VerificationTypeInfo {
    public static final int ITEM_DOUBLE = 3;
    public static final int ITEM_FLOAT = 2;
    public static final int ITEM_INTEGER = 1;
    public static final int ITEM_LONG = 4;
    public static final int ITEM_NULL = 5;
    public static final int ITEM_OBJECT = 7;
    public static final int ITEM_TOP = 0;
    public static final int ITEM_UNINITIALIZED = 8;
    public static final int ITEM_UNINITIALIZED_THIS = 6;
    private char[] constantPoolName;

    /* JADX INFO: renamed from: id, reason: collision with root package name */
    private int f623id;
    public int offset;
    public int tag;

    private VerificationTypeInfo() {
    }

    public VerificationTypeInfo(int i, char[] cArr) {
        this(i, 7, cArr);
    }

    public VerificationTypeInfo(int i, int i2, char[] cArr) {
        this.f623id = i;
        this.tag = i2;
        this.constantPoolName = cArr;
    }

    public VerificationTypeInfo(int i, TypeBinding typeBinding) {
        this(typeBinding);
        this.tag = i;
    }

    public VerificationTypeInfo(TypeBinding typeBinding) {
        this.f623id = typeBinding.f626id;
        switch (typeBinding.f626id) {
            case 2:
            case 3:
            case 4:
            case 5:
            case 10:
                this.tag = 1;
                break;
            case 6:
            case 11:
            default:
                this.tag = 7;
                this.constantPoolName = typeBinding.constantPoolName();
                break;
            case 7:
                this.tag = 4;
                break;
            case 8:
                this.tag = 3;
                break;
            case 9:
                this.tag = 2;
                break;
            case 12:
                this.tag = 5;
                break;
        }
    }

    public void setBinding(TypeBinding typeBinding) {
        this.constantPoolName = typeBinding.constantPoolName();
        int i = typeBinding.f626id;
        this.f623id = i;
        switch (i) {
            case 2:
            case 3:
            case 4:
            case 5:
            case 10:
                this.tag = 1;
                break;
            case 6:
            case 11:
            default:
                this.tag = 7;
                break;
            case 7:
                this.tag = 4;
                break;
            case 8:
                this.tag = 3;
                break;
            case 9:
                this.tag = 2;
                break;
            case 12:
                this.tag = 5;
                break;
        }
    }

    public int id() {
        return this.f623id;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        switch (this.tag) {
            case 0:
                stringBuffer.append("top");
                break;
            case 1:
                stringBuffer.append('I');
                break;
            case 2:
                stringBuffer.append(Util.C_FLOAT);
                break;
            case 3:
                stringBuffer.append(Util.C_DOUBLE);
                break;
            case 4:
                stringBuffer.append(Util.C_LONG);
                break;
            case 5:
                stringBuffer.append("null");
                break;
            case 6:
                stringBuffer.append("uninitialized_this(").append(readableName()).append(Constant.AFTER_QUTO);
                break;
            case 7:
                stringBuffer.append(readableName());
                break;
            case 8:
                stringBuffer.append("uninitialized(").append(readableName()).append(Constant.AFTER_QUTO);
                break;
        }
        return String.valueOf(stringBuffer);
    }

    public VerificationTypeInfo duplicate() {
        VerificationTypeInfo verificationTypeInfo = new VerificationTypeInfo();
        verificationTypeInfo.f623id = this.f623id;
        verificationTypeInfo.tag = this.tag;
        verificationTypeInfo.constantPoolName = this.constantPoolName;
        verificationTypeInfo.offset = this.offset;
        return verificationTypeInfo;
    }

    public boolean equals(Object obj) {
        if (obj instanceof VerificationTypeInfo) {
            VerificationTypeInfo verificationTypeInfo = (VerificationTypeInfo) obj;
            if (verificationTypeInfo.tag == this.tag && CharOperation.equals(verificationTypeInfo.constantPoolName(), constantPoolName())) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return this.tag + this.f623id + this.constantPoolName.length + this.offset;
    }

    public char[] constantPoolName() {
        return this.constantPoolName;
    }

    public char[] readableName() {
        return this.constantPoolName;
    }

    public void replaceWithElementType() {
        char[] cArr = this.constantPoolName;
        if (cArr[1] == 'L') {
            this.constantPoolName = CharOperation.subarray(cArr, 2, cArr.length - 1);
            return;
        }
        char[] cArrSubarray = CharOperation.subarray(cArr, 1, cArr.length);
        this.constantPoolName = cArrSubarray;
        if (cArrSubarray.length == 1) {
            char c = cArrSubarray[0];
            if (c == 'F') {
                this.f623id = 9;
                return;
            }
            if (c == 'N') {
                this.f623id = 12;
                return;
            }
            if (c == 'S') {
                this.f623id = 4;
                return;
            }
            if (c == 'V') {
                this.f623id = 6;
                return;
            }
            if (c == 'Z') {
                this.f623id = 5;
                return;
            }
            if (c == 'I') {
                this.f623id = 10;
                return;
            }
            if (c != 'J') {
                switch (c) {
                    case 'B':
                        this.f623id = 3;
                        break;
                    case 'C':
                        this.f623id = 2;
                        break;
                    case 'D':
                        this.f623id = 8;
                        break;
                }
                return;
            }
            this.f623id = 7;
        }
    }
}

package org.eclipse.jdt.internal.compiler.codegen;

import java.text.MessageFormat;
import org.eclipse.jdt.internal.compiler.lookup.TypeBinding;

/* JADX INFO: loaded from: classes14.dex */
public class StackMapFrame {
    public static final int APPEND_FRAME = 2;
    public static final int CHOP_FRAME = 1;
    public static final int FULL_FRAME = 4;
    public static final int SAME_FRAME = 0;
    public static final int SAME_FRAME_EXTENDED = 3;
    public static final int SAME_LOCALS_1_STACK_ITEMS = 5;
    public static final int SAME_LOCALS_1_STACK_ITEMS_EXTENDED = 6;
    public static final int USED = 1;
    public int localIndex;
    public VerificationTypeInfo[] locals;
    private int numberOfDifferentLocals;
    private int numberOfLocals = -1;
    public int numberOfStackItems;
    public int pc;
    public VerificationTypeInfo[] stackItems;
    public int tagBits;

    public StackMapFrame(int i) {
        this.numberOfDifferentLocals = -1;
        this.locals = new VerificationTypeInfo[i];
        this.numberOfDifferentLocals = -1;
    }

    public int getFrameType(StackMapFrame stackMapFrame) {
        int offsetDelta = getOffsetDelta(stackMapFrame);
        int i = this.numberOfStackItems;
        if (i != 0) {
            if (i == 1 && numberOfDifferentLocals(stackMapFrame) == 0) {
                return offsetDelta <= 63 ? 5 : 6;
            }
            return 4;
        }
        switch (numberOfDifferentLocals(stackMapFrame)) {
            case -3:
            case -2:
            case -1:
                return 1;
            case 0:
                return offsetDelta <= 63 ? 0 : 3;
            case 1:
            case 2:
            case 3:
                return 2;
            default:
                return 4;
        }
    }

    public void addLocal(int i, VerificationTypeInfo verificationTypeInfo) {
        VerificationTypeInfo[] verificationTypeInfoArr = this.locals;
        if (verificationTypeInfoArr == null) {
            VerificationTypeInfo[] verificationTypeInfoArr2 = new VerificationTypeInfo[i + 1];
            this.locals = verificationTypeInfoArr2;
            verificationTypeInfoArr2[i] = verificationTypeInfo;
        } else {
            int length = verificationTypeInfoArr.length;
            if (i >= length) {
                VerificationTypeInfo[] verificationTypeInfoArr3 = new VerificationTypeInfo[i + 1];
                this.locals = verificationTypeInfoArr3;
                System.arraycopy(verificationTypeInfoArr, 0, verificationTypeInfoArr3, 0, length);
            }
            this.locals[i] = verificationTypeInfo;
        }
    }

    public void addStackItem(VerificationTypeInfo verificationTypeInfo) {
        if (verificationTypeInfo == null) {
            throw new IllegalArgumentException("info cannot be null");
        }
        VerificationTypeInfo[] verificationTypeInfoArr = this.stackItems;
        if (verificationTypeInfoArr == null) {
            this.stackItems = new VerificationTypeInfo[]{verificationTypeInfo};
            this.numberOfStackItems = 1;
            return;
        }
        int length = verificationTypeInfoArr.length;
        if (this.numberOfStackItems == length) {
            VerificationTypeInfo[] verificationTypeInfoArr2 = new VerificationTypeInfo[length + 1];
            this.stackItems = verificationTypeInfoArr2;
            System.arraycopy(verificationTypeInfoArr, 0, verificationTypeInfoArr2, 0, length);
        }
        VerificationTypeInfo[] verificationTypeInfoArr3 = this.stackItems;
        int i = this.numberOfStackItems;
        this.numberOfStackItems = i + 1;
        verificationTypeInfoArr3[i] = verificationTypeInfo;
    }

    public void addStackItem(TypeBinding typeBinding) {
        VerificationTypeInfo[] verificationTypeInfoArr = this.stackItems;
        if (verificationTypeInfoArr == null) {
            this.stackItems = new VerificationTypeInfo[]{new VerificationTypeInfo(typeBinding)};
            this.numberOfStackItems = 1;
            return;
        }
        int length = verificationTypeInfoArr.length;
        if (this.numberOfStackItems == length) {
            VerificationTypeInfo[] verificationTypeInfoArr2 = new VerificationTypeInfo[length + 1];
            this.stackItems = verificationTypeInfoArr2;
            System.arraycopy(verificationTypeInfoArr, 0, verificationTypeInfoArr2, 0, length);
        }
        VerificationTypeInfo[] verificationTypeInfoArr3 = this.stackItems;
        int i = this.numberOfStackItems;
        this.numberOfStackItems = i + 1;
        verificationTypeInfoArr3[i] = new VerificationTypeInfo(typeBinding);
    }

    public StackMapFrame duplicate() {
        int length = this.locals.length;
        StackMapFrame stackMapFrame = new StackMapFrame(length);
        stackMapFrame.numberOfLocals = -1;
        stackMapFrame.numberOfDifferentLocals = -1;
        stackMapFrame.pc = this.pc;
        stackMapFrame.numberOfStackItems = this.numberOfStackItems;
        if (length != 0) {
            stackMapFrame.locals = new VerificationTypeInfo[length];
            for (int i = 0; i < length; i++) {
                VerificationTypeInfo verificationTypeInfo = this.locals[i];
                if (verificationTypeInfo != null) {
                    stackMapFrame.locals[i] = verificationTypeInfo.duplicate();
                }
            }
        }
        int i2 = this.numberOfStackItems;
        if (i2 != 0) {
            stackMapFrame.stackItems = new VerificationTypeInfo[i2];
            for (int i3 = 0; i3 < i2; i3++) {
                stackMapFrame.stackItems[i3] = this.stackItems[i3].duplicate();
            }
        }
        return stackMapFrame;
    }

    public int numberOfDifferentLocals(StackMapFrame stackMapFrame) {
        int i = this.numberOfDifferentLocals;
        if (i != -1) {
            return i;
        }
        int i2 = 0;
        if (stackMapFrame == null) {
            this.numberOfDifferentLocals = 0;
            return 0;
        }
        VerificationTypeInfo[] verificationTypeInfoArr = stackMapFrame.locals;
        VerificationTypeInfo[] verificationTypeInfoArr2 = this.locals;
        int length = verificationTypeInfoArr == null ? 0 : verificationTypeInfoArr.length;
        int length2 = verificationTypeInfoArr2 == null ? 0 : verificationTypeInfoArr2.length;
        int numberOfLocals = stackMapFrame.getNumberOfLocals();
        int numberOfLocals2 = getNumberOfLocals();
        int i3 = Integer.MAX_VALUE;
        if (numberOfLocals == 0) {
            if (numberOfLocals2 != 0) {
                int i4 = 0;
                while (i2 < length2 && i4 < numberOfLocals2) {
                    VerificationTypeInfo verificationTypeInfo = verificationTypeInfoArr2[i2];
                    if (verificationTypeInfo != null) {
                        int iId = verificationTypeInfo.id();
                        if (iId == 7 || iId == 8) {
                            i2++;
                        }
                        i4++;
                        i2++;
                    } else {
                        this.numberOfDifferentLocals = Integer.MAX_VALUE;
                        return Integer.MAX_VALUE;
                    }
                }
                i2 = numberOfLocals2;
            }
        } else if (numberOfLocals2 == 0) {
            int i5 = -numberOfLocals;
            int i6 = 0;
            while (i2 < length && i6 < numberOfLocals) {
                VerificationTypeInfo verificationTypeInfo2 = verificationTypeInfoArr[i2];
                if (verificationTypeInfo2 != null) {
                    int iId2 = verificationTypeInfo2.id();
                    if (iId2 == 7 || iId2 == 8) {
                        i2++;
                    }
                    i6++;
                    i2++;
                } else {
                    this.numberOfDifferentLocals = Integer.MAX_VALUE;
                    return Integer.MAX_VALUE;
                }
            }
            i2 = i5;
        } else {
            int i7 = 0;
            int i8 = 0;
            int i9 = 0;
            int i10 = 0;
            while (true) {
                if (i7 >= length2 || i8 >= numberOfLocals2) {
                    i2 = 0;
                } else {
                    VerificationTypeInfo verificationTypeInfo3 = verificationTypeInfoArr2[i7];
                    if (verificationTypeInfo3 != null) {
                        i8++;
                        int iId3 = verificationTypeInfo3.id();
                        if (iId3 == 7 || iId3 == 8) {
                            i7++;
                        }
                    }
                    if (i9 < length && i10 < numberOfLocals) {
                        VerificationTypeInfo verificationTypeInfo4 = verificationTypeInfoArr[i9];
                        if (verificationTypeInfo4 != null) {
                            i10++;
                            int iId4 = verificationTypeInfo4.id();
                            if (iId4 == 7 || iId4 == 8) {
                                i9++;
                            }
                        }
                        if (!equals(verificationTypeInfo4, verificationTypeInfo3) || i9 != i7) {
                            this.numberOfDifferentLocals = Integer.MAX_VALUE;
                            return Integer.MAX_VALUE;
                        }
                        i9++;
                        i7++;
                        i3 = Integer.MAX_VALUE;
                    } else {
                        if (verificationTypeInfo3 == null) {
                            this.numberOfDifferentLocals = i3;
                            return i3;
                        }
                        i7++;
                        i2 = 1;
                    }
                }
                if (i8 < numberOfLocals2) {
                    while (i7 < length2 && i8 < numberOfLocals2) {
                        VerificationTypeInfo verificationTypeInfo5 = verificationTypeInfoArr2[i7];
                        if (verificationTypeInfo5 == null) {
                            this.numberOfDifferentLocals = i3;
                            return i3;
                        }
                        i2++;
                        i8++;
                        int iId5 = verificationTypeInfo5.id();
                        if (iId5 == 7 || iId5 == 8) {
                            i7++;
                        }
                        i7++;
                        i3 = Integer.MAX_VALUE;
                    }
                } else if (i10 < numberOfLocals) {
                    i2 = -i2;
                    while (i9 < length && i10 < numberOfLocals) {
                        VerificationTypeInfo verificationTypeInfo6 = verificationTypeInfoArr[i9];
                        if (verificationTypeInfo6 == null) {
                            this.numberOfDifferentLocals = Integer.MAX_VALUE;
                            return Integer.MAX_VALUE;
                        }
                        i2--;
                        i10++;
                        int iId6 = verificationTypeInfo6.id();
                        if (iId6 == 7 || iId6 == 8) {
                            i9++;
                        }
                        i9++;
                    }
                }
            }
        }
        this.numberOfDifferentLocals = i2;
        return i2;
    }

    public int getNumberOfLocals() {
        int i = this.numberOfLocals;
        if (i != -1) {
            return i;
        }
        VerificationTypeInfo[] verificationTypeInfoArr = this.locals;
        int i2 = 0;
        int length = verificationTypeInfoArr == null ? 0 : verificationTypeInfoArr.length;
        int i3 = 0;
        while (i2 < length) {
            VerificationTypeInfo verificationTypeInfo = this.locals[i2];
            if (verificationTypeInfo != null) {
                int iId = verificationTypeInfo.id();
                if (iId == 7 || iId == 8) {
                    i2++;
                }
                i3++;
            }
            i2++;
        }
        this.numberOfLocals = i3;
        return i3;
    }

    public int getOffsetDelta(StackMapFrame stackMapFrame) {
        if (stackMapFrame == null) {
            return this.pc;
        }
        int i = stackMapFrame.pc;
        return i == -1 ? this.pc : (this.pc - i) - 1;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        printFrame(stringBuffer, this);
        return String.valueOf(stringBuffer);
    }

    private void printFrame(StringBuffer stringBuffer, StackMapFrame stackMapFrame) {
        VerificationTypeInfo[] verificationTypeInfoArr = stackMapFrame.locals;
        stringBuffer.append(MessageFormat.format("[pc : {0} locals: {1} stack items: {2}\nlocals: {3}\nstack: {4}\n]", Integer.toString(stackMapFrame.pc), Integer.toString(stackMapFrame.getNumberOfLocals()), Integer.toString(stackMapFrame.numberOfStackItems), print(stackMapFrame.locals, verificationTypeInfoArr == null ? 0 : verificationTypeInfoArr.length), print(stackMapFrame.stackItems, stackMapFrame.numberOfStackItems)));
    }

    private String print(VerificationTypeInfo[] verificationTypeInfoArr, int i) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append('[');
        if (verificationTypeInfoArr != null) {
            for (int i2 = 0; i2 < i; i2++) {
                if (i2 != 0) {
                    stringBuffer.append(',');
                }
                VerificationTypeInfo verificationTypeInfo = verificationTypeInfoArr[i2];
                if (verificationTypeInfo == null) {
                    stringBuffer.append("top");
                } else {
                    stringBuffer.append(verificationTypeInfo);
                }
            }
        }
        stringBuffer.append(']');
        return String.valueOf(stringBuffer);
    }

    public void putLocal(int i, VerificationTypeInfo verificationTypeInfo) {
        VerificationTypeInfo[] verificationTypeInfoArr = this.locals;
        if (verificationTypeInfoArr == null) {
            VerificationTypeInfo[] verificationTypeInfoArr2 = new VerificationTypeInfo[i + 1];
            this.locals = verificationTypeInfoArr2;
            verificationTypeInfoArr2[i] = verificationTypeInfo;
        } else {
            int length = verificationTypeInfoArr.length;
            if (i >= length) {
                VerificationTypeInfo[] verificationTypeInfoArr3 = new VerificationTypeInfo[i + 1];
                this.locals = verificationTypeInfoArr3;
                System.arraycopy(verificationTypeInfoArr, 0, verificationTypeInfoArr3, 0, length);
            }
            this.locals[i] = verificationTypeInfo;
        }
    }

    public void replaceWithElementType() {
        VerificationTypeInfo verificationTypeInfoDuplicate = this.stackItems[this.numberOfStackItems - 1].duplicate();
        verificationTypeInfoDuplicate.replaceWithElementType();
        this.stackItems[this.numberOfStackItems - 1] = verificationTypeInfoDuplicate;
    }

    public int getIndexOfDifferentLocals(int i) {
        for (int length = this.locals.length - 1; length >= 0; length--) {
            if (this.locals[length] != null && (i = i - 1) == 0) {
                return length;
            }
        }
        return 0;
    }

    private boolean equals(VerificationTypeInfo verificationTypeInfo, VerificationTypeInfo verificationTypeInfo2) {
        if (verificationTypeInfo == null) {
            return verificationTypeInfo2 == null;
        }
        if (verificationTypeInfo2 == null) {
            return false;
        }
        return verificationTypeInfo.equals(verificationTypeInfo2);
    }
}

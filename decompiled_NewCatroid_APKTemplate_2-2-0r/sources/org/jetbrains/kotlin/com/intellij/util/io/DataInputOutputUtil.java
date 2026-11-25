package org.jetbrains.kotlin.com.intellij.util.io;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import org.jetbrains.kotlin.com.intellij.openapi.util.io.DataInputOutputUtilRt;
import org.jetbrains.kotlin.com.intellij.psi.PsiKeyword;

/* JADX INFO: loaded from: classes3.dex */
public final class DataInputOutputUtil {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 16 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[i != 16 ? 3 : 2];
        if (i != 1 && i != 3) {
            switch (i) {
                case 10:
                case 17:
                    objArr[0] = "out";
                    break;
                case 11:
                    objArr[0] = "writeValue";
                    break;
                case 12:
                case 14:
                    objArr[0] = "in";
                    break;
                case 13:
                    objArr[0] = "readValue";
                    break;
                case 15:
                    objArr[0] = "readElement";
                    break;
                case 16:
                    objArr[0] = "org/jetbrains/kotlin/com/intellij/util/io/DataInputOutputUtil";
                    break;
                case 18:
                    objArr[0] = "collection";
                    break;
                case 19:
                    objArr[0] = "writeElement";
                    break;
                default:
                    objArr[0] = PsiKeyword.RECORD;
                    break;
            }
        } else {
            objArr[0] = "byteBuffer";
        }
        if (i != 16) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/util/io/DataInputOutputUtil";
        } else {
            objArr[1] = "readSeq";
        }
        switch (i) {
            case 2:
            case 3:
                objArr[2] = "writeINT";
                break;
            case 4:
                objArr[2] = "readLONG";
                break;
            case 5:
                objArr[2] = "writeLONG";
                break;
            case 6:
                objArr[2] = "readSINT";
                break;
            case 7:
                objArr[2] = "writeSINT";
                break;
            case 8:
                objArr[2] = "writeTIME";
                break;
            case 9:
                objArr[2] = "readTIME";
                break;
            case 10:
            case 11:
                objArr[2] = "writeNullable";
                break;
            case 12:
            case 13:
                objArr[2] = "readNullable";
                break;
            case 14:
            case 15:
                objArr[2] = "readSeq";
                break;
            case 16:
                break;
            case 17:
            case 18:
            case 19:
                objArr[2] = "writeSeq";
                break;
            default:
                objArr[2] = "readINT";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i == 16) {
            throw new IllegalStateException(str2);
        }
    }

    public static int readINT(DataInput dataInput) throws IOException {
        if (dataInput == null) {
            $$$reportNull$$$0(0);
        }
        return DataInputOutputUtilRt.readINT(dataInput);
    }

    public static void writeINT(DataOutput dataOutput, int i) throws IOException {
        if (dataOutput == null) {
            $$$reportNull$$$0(2);
        }
        DataInputOutputUtilRt.writeINT(dataOutput, i);
    }

    public static long readLONG(DataInput dataInput) throws IOException {
        if (dataInput == null) {
            $$$reportNull$$$0(4);
        }
        int unsignedByte = dataInput.readUnsignedByte();
        if (unsignedByte < 192) {
            return unsignedByte;
        }
        long j = unsignedByte - 192;
        int i = 6;
        while (true) {
            int unsignedByte2 = dataInput.readUnsignedByte();
            j |= ((long) (unsignedByte2 & 127)) << i;
            if ((unsignedByte2 & 128) == 0) {
                return j;
            }
            i += 7;
        }
    }

    public static void writeLONG(DataOutput dataOutput, long j) throws IOException {
        if (dataOutput == null) {
            $$$reportNull$$$0(5);
        }
        if (0 > j || j >= 192) {
            dataOutput.writeByte(((int) (63 & j)) + 192);
            char c = 6;
            while (true) {
                j >>>= c;
                if (j < 128) {
                    break;
                }
                dataOutput.writeByte(((int) (127 & j)) | 128);
                c = 7;
            }
        }
        dataOutput.writeByte((int) j);
    }
}

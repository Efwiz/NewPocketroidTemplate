package org.jetbrains.org.objectweb.asm;

/* JADX INFO: loaded from: classes6.dex */
public class TypeReference {
    private final int targetTypeAndInfo;

    public TypeReference(int i) {
        this.targetTypeAndInfo = i;
    }

    public static TypeReference newTypeReference(int i) {
        return new TypeReference(i << 24);
    }

    public static TypeReference newTypeParameterReference(int i, int i2) {
        return new TypeReference((i << 24) | (i2 << 16));
    }

    public static TypeReference newTypeParameterBoundReference(int i, int i2, int i3) {
        return new TypeReference((i << 24) | (i2 << 16) | (i3 << 8));
    }

    public static TypeReference newFormalParameterReference(int i) {
        return new TypeReference((i << 16) | 369098752);
    }

    public int getSort() {
        return this.targetTypeAndInfo >>> 24;
    }

    public int getTypeParameterIndex() {
        return (this.targetTypeAndInfo & 16711680) >> 16;
    }

    public int getTypeParameterBoundIndex() {
        return (this.targetTypeAndInfo & 65280) >> 8;
    }

    public int getSuperTypeIndex() {
        return (short) ((this.targetTypeAndInfo & 16776960) >> 8);
    }

    public int getFormalParameterIndex() {
        return (this.targetTypeAndInfo & 16711680) >> 16;
    }

    public int getExceptionIndex() {
        return (this.targetTypeAndInfo & 16776960) >> 8;
    }

    public int getTryCatchBlockIndex() {
        return (this.targetTypeAndInfo & 16776960) >> 8;
    }

    public int getTypeArgumentIndex() {
        return this.targetTypeAndInfo & 255;
    }

    public int getValue() {
        return this.targetTypeAndInfo;
    }

    static void putTarget(int i, ByteVector byteVector) {
        int i2 = i >>> 24;
        if (i2 != 0 && i2 != 1) {
            switch (i2) {
                case 16:
                case 17:
                case 18:
                case 23:
                    break;
                case 19:
                case 20:
                case 21:
                    byteVector.putByte(i2);
                    return;
                case 22:
                    break;
                default:
                    switch (i2) {
                        case 66:
                        case 67:
                        case 68:
                        case 69:
                        case 70:
                            break;
                        case 71:
                        case 72:
                        case 73:
                        case 74:
                        case 75:
                            byteVector.putInt(i);
                            return;
                        default:
                            throw new IllegalArgumentException();
                    }
                    break;
            }
            byteVector.put12(i2, (i & 16776960) >> 8);
            return;
        }
        byteVector.putShort(i >>> 16);
    }
}

package org.jetbrains.kotlin.com.intellij.openapi.util.io;

import org.jetbrains.kotlin.com.intellij.util.BitUtil;

/* JADX INFO: loaded from: classes3.dex */
public class FileAttributes {
    public static final FileAttributes BROKEN_SYMLINK = new FileAttributes((byte) 1, 0, 0);
    protected static final FileAttributes UNKNOWN = new FileAttributes((byte) -1, 0, 0);
    protected final byte flags;
    public final long lastModified;
    public final long length;

    public enum CaseSensitivity {
        SENSITIVE,
        INSENSITIVE,
        UNKNOWN
    }

    public enum Type {
        FILE,
        DIRECTORY,
        SPECIAL
    }

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 2 || i == 3 || i == 4 || i == 5) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 2 || i == 3 || i == 4 || i == 5) ? 2 : 3];
        switch (i) {
            case 1:
                objArr[0] = "fileAttributes";
                break;
            case 2:
            case 3:
            case 4:
            case 5:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/openapi/util/io/FileAttributes";
                break;
            case 6:
                objArr[0] = "sensitivity";
                break;
            default:
                objArr[0] = "caseSensitivity";
                break;
        }
        if (i == 2 || i == 3 || i == 4 || i == 5) {
            objArr[1] = "areChildrenCaseSensitive";
        } else {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/openapi/util/io/FileAttributes";
        }
        if (i != 2 && i != 3 && i != 4 && i != 5) {
            if (i != 6) {
                objArr[2] = "<init>";
            } else {
                objArr[2] = "withCaseSensitivity";
            }
        }
        String str2 = String.format(str, objArr);
        if (i != 2 && i != 3 && i != 4 && i != 5) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    public FileAttributes(boolean z, boolean z2, boolean z3, boolean z4, long j, long j2, boolean z5) {
        this(z, z2, z3, z4, j, j2, z5, CaseSensitivity.UNKNOWN);
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public FileAttributes(boolean z, boolean z2, boolean z3, boolean z4, long j, long j2, boolean z5, CaseSensitivity caseSensitivity) {
        this(flags(z, z2, z3, z4, z5, caseSensitivity), j, j2);
        if (caseSensitivity == null) {
            $$$reportNull$$$0(0);
        }
    }

    protected FileAttributes(byte b, long j, long j2) {
        if (b != -1 && (b & 128) != 0) {
            throw new IllegalArgumentException("Invalid flags: " + Integer.toBinaryString(b));
        }
        this.flags = b;
        this.length = j;
        this.lastModified = j2;
    }

    private static byte flags(boolean z, boolean z2, boolean z3, boolean z4, boolean z5, CaseSensitivity caseSensitivity) {
        int i = 1;
        byte b = z3 ? (byte) 1 : (byte) 0;
        if (z4) {
            b = (byte) (b | 2);
        }
        if (!z5) {
            b = (byte) (b | 4);
        }
        if (z2) {
            i = 3;
        } else if (z) {
            i = 2;
        }
        byte b2 = (byte) ((i << 3) | b);
        if (!z) {
            caseSensitivity = CaseSensitivity.UNKNOWN;
        }
        return packSensitivityIntoFlags(caseSensitivity, b2);
    }

    private static byte packSensitivityIntoFlags(CaseSensitivity caseSensitivity, byte b) {
        int i;
        if (caseSensitivity == CaseSensitivity.UNKNOWN) {
            i = 0;
        } else {
            i = caseSensitivity == CaseSensitivity.SENSITIVE ? 1 : 2;
        }
        return (byte) ((i << 5) | b);
    }

    public boolean isDirectory() {
        return ((this.flags >> 3) & 3) == 2;
    }

    public boolean isSymLink() {
        return BitUtil.isSet(this.flags, (byte) 1);
    }

    public boolean isWritable() {
        return !BitUtil.isSet(this.flags, (byte) 4);
    }

    public Type getType() {
        int i = (this.flags >> 3) & 3;
        if (i == 0) {
            return null;
        }
        if (i == 1) {
            return Type.FILE;
        }
        if (i == 2) {
            return Type.DIRECTORY;
        }
        if (i == 3) {
            return Type.SPECIAL;
        }
        throw new IllegalStateException("Invalid type flags: " + Integer.toBinaryString(this.flags));
    }

    public CaseSensitivity areChildrenCaseSensitive() {
        if (!isDirectory()) {
            CaseSensitivity caseSensitivity = CaseSensitivity.UNKNOWN;
            if (caseSensitivity == null) {
                $$$reportNull$$$0(2);
            }
            return caseSensitivity;
        }
        int i = (this.flags >> 5) & 3;
        if (i == 0) {
            CaseSensitivity caseSensitivity2 = CaseSensitivity.UNKNOWN;
            if (caseSensitivity2 == null) {
                $$$reportNull$$$0(3);
            }
            return caseSensitivity2;
        }
        if (i == 1) {
            CaseSensitivity caseSensitivity3 = CaseSensitivity.SENSITIVE;
            if (caseSensitivity3 == null) {
                $$$reportNull$$$0(4);
            }
            return caseSensitivity3;
        }
        if (i == 2) {
            CaseSensitivity caseSensitivity4 = CaseSensitivity.INSENSITIVE;
            if (caseSensitivity4 == null) {
                $$$reportNull$$$0(5);
            }
            return caseSensitivity4;
        }
        throw new IllegalStateException("Invalid sensitivity flags: " + Integer.toBinaryString(i));
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        FileAttributes fileAttributes = (FileAttributes) obj;
        return this.flags == fileAttributes.flags && this.lastModified == fileAttributes.lastModified && this.length == fileAttributes.length;
    }

    public int hashCode() {
        int i = this.flags * 31;
        long j = this.length;
        int i2 = (i + ((int) (j ^ (j >>> 32)))) * 31;
        long j2 = this.lastModified;
        return i2 + ((int) (j2 ^ (j2 >>> 32)));
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("[type:");
        sb.append(getType());
        if (BitUtil.isSet(this.flags, (byte) 1)) {
            sb.append(" l");
        }
        if (BitUtil.isSet(this.flags, (byte) 2)) {
            sb.append(" .");
        }
        if (BitUtil.isSet(this.flags, (byte) 4)) {
            sb.append(" ro");
        }
        sb.append(" length:").append(this.length);
        sb.append(" modified:").append(this.lastModified);
        sb.append(" case sensitive: ").append(areChildrenCaseSensitive());
        sb.append(']');
        return sb.toString();
    }
}

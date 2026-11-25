package org.eclipse.jgit.lib;

import java.io.IOException;
import java.io.OutputStream;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;

/* JADX INFO: loaded from: classes13.dex */
public abstract class FileMode {
    public static final FileMode EXECUTABLE_FILE;
    public static final FileMode REGULAR_FILE;
    public static final FileMode SYMLINK;
    public static final int TYPE_FILE = 32768;
    public static final int TYPE_MASK = 61440;
    public static final int TYPE_MISSING = 0;
    public static final int TYPE_SYMLINK = 40960;
    public static final int TYPE_TREE = 16384;
    private final int modeBits;
    private final int objectType;
    private final byte[] octalBytes;
    public static final FileMode TREE = new FileMode(16384, 2) { // from class: org.eclipse.jgit.lib.FileMode.1
        @Override // org.eclipse.jgit.lib.FileMode
        public boolean equals(int i) {
            return (i & 61440) == 16384;
        }

        {
            FileMode fileMode = null;
        }
    };
    public static final int TYPE_GITLINK = 57344;
    public static final FileMode GITLINK = new FileMode(TYPE_GITLINK, 1) { // from class: org.eclipse.jgit.lib.FileMode.5
        @Override // org.eclipse.jgit.lib.FileMode
        public boolean equals(int i) {
            return (i & 61440) == 57344;
        }

        {
            FileMode fileMode = null;
        }
    };
    public static final FileMode MISSING = new FileMode(0, -1) { // from class: org.eclipse.jgit.lib.FileMode.6
        @Override // org.eclipse.jgit.lib.FileMode
        public boolean equals(int i) {
            return i == 0;
        }

        {
            FileMode fileMode = null;
        }
    };

    public abstract boolean equals(int i);

    static {
        int i = 3;
        SYMLINK = new FileMode(40960, i) { // from class: org.eclipse.jgit.lib.FileMode.2
            @Override // org.eclipse.jgit.lib.FileMode
            public boolean equals(int i2) {
                return (i2 & 61440) == 40960;
            }

            {
                FileMode fileMode = null;
            }
        };
        REGULAR_FILE = new FileMode(TarArchiveEntry.DEFAULT_FILE_MODE, i) { // from class: org.eclipse.jgit.lib.FileMode.3
            @Override // org.eclipse.jgit.lib.FileMode
            public boolean equals(int i2) {
                return (61440 & i2) == 32768 && (i2 & 73) == 0;
            }

            {
                FileMode fileMode = null;
            }
        };
        EXECUTABLE_FILE = new FileMode(33261, i) { // from class: org.eclipse.jgit.lib.FileMode.4
            @Override // org.eclipse.jgit.lib.FileMode
            public boolean equals(int i2) {
                return (61440 & i2) == 32768 && (i2 & 73) != 0;
            }

            {
                FileMode fileMode = null;
            }
        };
    }

    public static final FileMode fromBits(final int i) {
        int i2 = 61440 & i;
        if (i2 != 0) {
            if (i2 == 16384) {
                return TREE;
            }
            if (i2 == 32768) {
                if ((i & 73) != 0) {
                    return EXECUTABLE_FILE;
                }
                return REGULAR_FILE;
            }
            if (i2 == 40960) {
                return SYMLINK;
            }
            if (i2 == 57344) {
                return GITLINK;
            }
        } else if (i == 0) {
            return MISSING;
        }
        return new FileMode(i, -1) { // from class: org.eclipse.jgit.lib.FileMode.7
            {
                FileMode fileMode = null;
            }

            @Override // org.eclipse.jgit.lib.FileMode
            public boolean equals(int i3) {
                return i == i3;
            }
        };
    }

    private FileMode(int i, int i2) {
        this.modeBits = i;
        this.objectType = i2;
        int i3 = 0;
        if (i != 0) {
            int i4 = 10;
            byte[] bArr = new byte[10];
            while (i != 0) {
                i4--;
                bArr[i4] = (byte) ((i & 7) + 48);
                i >>= 3;
            }
            this.octalBytes = new byte[10 - i4];
            while (true) {
                byte[] bArr2 = this.octalBytes;
                if (i3 >= bArr2.length) {
                    return;
                }
                bArr2[i3] = bArr[i4 + i3];
                i3++;
            }
        } else {
            this.octalBytes = new byte[]{48};
        }
    }

    /* synthetic */ FileMode(int i, int i2, FileMode fileMode) {
        this(i, i2);
    }

    public void copyTo(OutputStream outputStream) throws IOException {
        outputStream.write(this.octalBytes);
    }

    public void copyTo(byte[] bArr, int i) {
        byte[] bArr2 = this.octalBytes;
        System.arraycopy(bArr2, 0, bArr, i, bArr2.length);
    }

    public int copyToLength() {
        return this.octalBytes.length;
    }

    public int getObjectType() {
        return this.objectType;
    }

    public String toString() {
        return Integer.toOctalString(this.modeBits);
    }

    public int getBits() {
        return this.modeBits;
    }
}

package org.eclipse.jgit.internal.storage.file;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.ObjectId;

/* JADX INFO: loaded from: classes13.dex */
class PackFileSnapshot extends FileSnapshot {
    private static final ObjectId MISSING_CHECKSUM = ObjectId.zeroId();
    private AnyObjectId checksum;
    private boolean wasChecksumChanged;

    public static PackFileSnapshot save(File file) {
        return new PackFileSnapshot(file);
    }

    PackFileSnapshot(File file) {
        super(file);
        this.checksum = MISSING_CHECKSUM;
    }

    void setChecksum(AnyObjectId anyObjectId) {
        this.checksum = anyObjectId;
    }

    @Override // org.eclipse.jgit.internal.storage.file.FileSnapshot
    public boolean isModified(File file) {
        if (!super.isModified(file)) {
            return false;
        }
        if (wasSizeChanged() || wasFileKeyChanged() || !wasLastModifiedRacilyClean()) {
            return true;
        }
        return isChecksumChanged(file);
    }

    boolean isChecksumChanged(File file) {
        AnyObjectId anyObjectId = this.checksum;
        boolean z = (anyObjectId == MISSING_CHECKSUM || anyObjectId.equals(readChecksum(file))) ? false : true;
        this.wasChecksumChanged = z;
        return z;
    }

    private AnyObjectId readChecksum(File file) {
        Throwable th = null;
        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile(file, "r");
            try {
                randomAccessFile.seek(randomAccessFile.length() - 20);
                byte[] bArr = new byte[20];
                randomAccessFile.readFully(bArr, 0, 20);
                return ObjectId.fromRaw(bArr);
            } finally {
                randomAccessFile.close();
            }
        } catch (Throwable th2) {
            if (0 == 0) {
                throw th2;
            }
            if (null != th2) {
                try {
                    th.addSuppressed(th2);
                } catch (IOException unused) {
                    return MISSING_CHECKSUM;
                }
            }
            throw null;
        }
    }

    boolean wasChecksumChanged() {
        return this.wasChecksumChanged;
    }

    @Override // org.eclipse.jgit.internal.storage.file.FileSnapshot
    public String toString() {
        return "PackFileSnapshot [checksum=" + this.checksum + ", " + super.toString() + "]";
    }
}

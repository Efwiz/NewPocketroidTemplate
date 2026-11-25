package org.eclipse.jgit.lib;

import org.eclipse.jgit.errors.CorruptObjectException;

/* JADX INFO: loaded from: classes13.dex */
public interface BlobObjectChecker {
    public static final BlobObjectChecker NULL_CHECKER = new BlobObjectChecker() { // from class: org.eclipse.jgit.lib.BlobObjectChecker.1
        @Override // org.eclipse.jgit.lib.BlobObjectChecker
        public void endBlob(AnyObjectId anyObjectId) {
        }

        @Override // org.eclipse.jgit.lib.BlobObjectChecker
        public void update(byte[] bArr, int i, int i2) {
        }
    };

    void endBlob(AnyObjectId anyObjectId) throws CorruptObjectException;

    void update(byte[] bArr, int i, int i2);
}

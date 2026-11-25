package org.eclipse.jgit.internal.storage.file;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.Iterator;
import java.util.Set;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.errors.UnsupportedPackIndexVersionException;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.lib.AbbreviatedObjectId;
import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.MutableObjectId;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectIdSet;
import org.eclipse.jgit.util.IO;
import org.eclipse.jgit.util.NB;
import org.eclipse.jgit.util.io.SilentFileInputStream;

/* JADX INFO: loaded from: classes13.dex */
public abstract class PackIndex implements Iterable<MutableEntry>, ObjectIdSet {
    protected byte[] packChecksum;

    public abstract long findCRC32(AnyObjectId anyObjectId) throws UnsupportedOperationException, MissingObjectException;

    public abstract long findOffset(AnyObjectId anyObjectId);

    public abstract long getObjectCount();

    public abstract ObjectId getObjectId(long j);

    abstract long getOffset(long j);

    public abstract long getOffset64Count();

    public abstract boolean hasCRC32Support();

    @Override // java.lang.Iterable
    public abstract Iterator<MutableEntry> iterator();

    public abstract void resolve(Set<ObjectId> set, AbbreviatedObjectId abbreviatedObjectId, int i) throws IOException;

    public static PackIndex open(File file) throws IOException {
        Throwable th = null;
        try {
            SilentFileInputStream silentFileInputStream = new SilentFileInputStream(file);
            try {
                return read(silentFileInputStream);
            } finally {
                silentFileInputStream.close();
            }
        } catch (Throwable th2) {
            if (0 == 0) {
                throw th2;
            }
            if (null == th2) {
                throw null;
            }
            try {
                th.addSuppressed(th2);
                throw null;
            } catch (IOException e) {
                throw new IOException(MessageFormat.format(JGitText.get().unreadablePackIndex, file.getAbsolutePath()), e);
            }
            throw new IOException(MessageFormat.format(JGitText.get().unreadablePackIndex, file.getAbsolutePath()), e);
        }
    }

    public static PackIndex read(InputStream inputStream) throws IOException {
        byte[] bArr = new byte[8];
        IO.readFully(inputStream, bArr, 0, 8);
        if (isTOC(bArr)) {
            int iDecodeInt32 = NB.decodeInt32(bArr, 4);
            if (iDecodeInt32 == 2) {
                return new PackIndexV2(inputStream);
            }
            throw new UnsupportedPackIndexVersionException(iDecodeInt32);
        }
        return new PackIndexV1(inputStream, bArr);
    }

    private static boolean isTOC(byte[] bArr) {
        byte[] bArr2 = PackIndexWriter.TOC;
        for (int i = 0; i < bArr2.length; i++) {
            if (bArr[i] != bArr2[i]) {
                return false;
            }
        }
        return true;
    }

    public boolean hasObject(AnyObjectId anyObjectId) {
        return findOffset(anyObjectId) != -1;
    }

    @Override // org.eclipse.jgit.lib.ObjectIdSet
    public boolean contains(AnyObjectId anyObjectId) {
        return findOffset(anyObjectId) != -1;
    }

    public final ObjectId getObjectId(int i) {
        if (i >= 0) {
            return getObjectId(i);
        }
        return getObjectId((((long) (i >>> 1)) << 1) | ((long) (i & 1)));
    }

    public byte[] getChecksum() {
        return this.packChecksum;
    }

    public static class MutableEntry {
        final MutableObjectId idBuffer = new MutableObjectId();
        long offset;

        void ensureId() {
        }

        public long getOffset() {
            return this.offset;
        }

        public String name() {
            ensureId();
            return this.idBuffer.name();
        }

        public ObjectId toObjectId() {
            ensureId();
            return this.idBuffer.toObjectId();
        }

        public MutableEntry cloneEntry() {
            MutableEntry mutableEntry = new MutableEntry();
            ensureId();
            mutableEntry.idBuffer.fromObjectId(this.idBuffer);
            mutableEntry.offset = this.offset;
            return mutableEntry;
        }
    }

    abstract class EntriesIterator implements Iterator<MutableEntry> {
        protected final MutableEntry entry = initEntry();
        protected long returnedNumber = 0;

        protected abstract MutableEntry initEntry();

        @Override // java.util.Iterator
        public abstract MutableEntry next();

        EntriesIterator() {
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            return this.returnedNumber < PackIndex.this.getObjectCount();
        }

        @Override // java.util.Iterator
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}

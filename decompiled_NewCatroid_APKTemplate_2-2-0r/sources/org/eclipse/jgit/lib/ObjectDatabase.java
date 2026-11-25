package org.eclipse.jgit.lib;

import java.io.IOException;

/* JADX INFO: loaded from: classes13.dex */
public abstract class ObjectDatabase {
    public abstract void close();

    public void create() throws IOException {
    }

    public boolean exists() {
        return true;
    }

    public ObjectDatabase newCachedDatabase() {
        return this;
    }

    public abstract ObjectInserter newInserter();

    public abstract ObjectReader newReader();

    protected ObjectDatabase() {
    }

    public boolean has(AnyObjectId anyObjectId) throws IOException {
        Throwable th = null;
        try {
            ObjectReader objectReaderNewReader = newReader();
            try {
                return objectReaderNewReader.has(anyObjectId);
            } finally {
                if (objectReaderNewReader != null) {
                    objectReaderNewReader.close();
                }
            }
        } catch (Throwable th2) {
            if (0 == 0) {
                throw th2;
            }
            if (null != th2) {
                th.addSuppressed(th2);
            }
            throw null;
        }
    }

    public ObjectLoader open(AnyObjectId anyObjectId) throws IOException {
        return open(anyObjectId, -1);
    }

    public ObjectLoader open(AnyObjectId anyObjectId, int i) throws IOException {
        Throwable th = null;
        try {
            ObjectReader objectReaderNewReader = newReader();
            try {
                return objectReaderNewReader.open(anyObjectId, i);
            } finally {
                if (objectReaderNewReader != null) {
                    objectReaderNewReader.close();
                }
            }
        } catch (Throwable th2) {
            if (0 == 0) {
                throw th2;
            }
            if (null != th2) {
                th.addSuppressed(th2);
            }
            throw null;
        }
    }
}

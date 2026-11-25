package org.eclipse.jgit.internal.storage.file;

import java.io.IOException;
import org.eclipse.jgit.internal.storage.pack.StoredObjectRepresentation;
import org.eclipse.jgit.lib.ObjectId;

/* JADX INFO: loaded from: classes13.dex */
class LocalObjectRepresentation extends StoredObjectRepresentation {
    private ObjectId baseId;
    private long baseOffset;
    long length;
    long offset;
    Pack pack;

    LocalObjectRepresentation() {
    }

    static LocalObjectRepresentation newWhole(Pack pack, long j, long j2) {
        LocalObjectRepresentation localObjectRepresentation = new LocalObjectRepresentation() { // from class: org.eclipse.jgit.internal.storage.file.LocalObjectRepresentation.1
            @Override // org.eclipse.jgit.internal.storage.pack.StoredObjectRepresentation
            public int getFormat() {
                return 1;
            }
        };
        localObjectRepresentation.pack = pack;
        localObjectRepresentation.offset = j;
        localObjectRepresentation.length = j2;
        return localObjectRepresentation;
    }

    static LocalObjectRepresentation newDelta(Pack pack, long j, long j2, ObjectId objectId) {
        Delta delta = new Delta(null);
        delta.pack = pack;
        delta.offset = j;
        delta.length = j2;
        ((LocalObjectRepresentation) delta).baseId = objectId;
        return delta;
    }

    static LocalObjectRepresentation newDelta(Pack pack, long j, long j2, long j3) {
        Delta delta = new Delta(null);
        delta.pack = pack;
        delta.offset = j;
        delta.length = j2;
        ((LocalObjectRepresentation) delta).baseOffset = j3;
        return delta;
    }

    @Override // org.eclipse.jgit.internal.storage.pack.StoredObjectRepresentation
    public int getWeight() {
        return (int) Math.min(this.length, 2147483647L);
    }

    @Override // org.eclipse.jgit.internal.storage.pack.StoredObjectRepresentation
    public ObjectId getDeltaBase() {
        if (this.baseId == null && getFormat() == 0) {
            try {
                this.baseId = this.pack.findObjectForOffset(this.baseOffset);
            } catch (IOException unused) {
                return null;
            }
        }
        return this.baseId;
    }

    private static final class Delta extends LocalObjectRepresentation {
        @Override // org.eclipse.jgit.internal.storage.pack.StoredObjectRepresentation
        public int getFormat() {
            return 0;
        }

        private Delta() {
        }

        /* synthetic */ Delta(Delta delta) {
            this();
        }
    }
}

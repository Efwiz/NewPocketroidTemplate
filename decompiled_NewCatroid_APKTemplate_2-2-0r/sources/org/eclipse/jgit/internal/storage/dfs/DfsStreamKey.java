package org.eclipse.jgit.internal.storage.dfs;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import org.eclipse.jgit.annotations.Nullable;
import org.eclipse.jgit.internal.storage.pack.PackExt;

/* JADX INFO: loaded from: classes13.dex */
public abstract class DfsStreamKey {
    final int hash;
    final int packExtPos;

    public abstract boolean equals(Object obj);

    public static DfsStreamKey of(DfsRepositoryDescription dfsRepositoryDescription, String str, @Nullable PackExt packExt) {
        return new ByteArrayDfsStreamKey(dfsRepositoryDescription, str.getBytes(StandardCharsets.UTF_8), packExt);
    }

    protected DfsStreamKey(int i, @Nullable PackExt packExt) {
        this.hash = i * 31;
        this.packExtPos = packExt == null ? 0 : packExt.getPosition();
    }

    public int hashCode() {
        return this.hash;
    }

    public String toString() {
        return String.format("DfsStreamKey[hash=%08x]", Integer.valueOf(this.hash));
    }

    private static final class ByteArrayDfsStreamKey extends DfsStreamKey {

        /* JADX INFO: renamed from: name, reason: collision with root package name */
        private final byte[] f647name;
        private final DfsRepositoryDescription repo;

        ByteArrayDfsStreamKey(DfsRepositoryDescription dfsRepositoryDescription, byte[] bArr, @Nullable PackExt packExt) {
            super((dfsRepositoryDescription.hashCode() * 31) + Arrays.hashCode(bArr), packExt);
            this.repo = dfsRepositoryDescription;
            this.f647name = bArr;
        }

        @Override // org.eclipse.jgit.internal.storage.dfs.DfsStreamKey
        public boolean equals(Object obj) {
            if (obj instanceof ByteArrayDfsStreamKey) {
                ByteArrayDfsStreamKey byteArrayDfsStreamKey = (ByteArrayDfsStreamKey) obj;
                if (this.hash == byteArrayDfsStreamKey.hash && this.repo.equals(byteArrayDfsStreamKey.repo) && Arrays.equals(this.f647name, byteArrayDfsStreamKey.f647name)) {
                    return true;
                }
            }
            return false;
        }
    }

    static final class ForReverseIndex extends DfsStreamKey {
        private final DfsStreamKey idxKey;

        ForReverseIndex(DfsStreamKey dfsStreamKey) {
            super(dfsStreamKey.hash + 1, null);
            this.idxKey = dfsStreamKey;
        }

        @Override // org.eclipse.jgit.internal.storage.dfs.DfsStreamKey
        public boolean equals(Object obj) {
            return (obj instanceof ForReverseIndex) && this.idxKey.equals(((ForReverseIndex) obj).idxKey);
        }
    }
}

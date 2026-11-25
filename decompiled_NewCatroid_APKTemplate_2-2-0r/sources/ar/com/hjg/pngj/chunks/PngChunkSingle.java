package ar.com.hjg.pngj.chunks;

import ar.com.hjg.pngj.ImageInfo;

/* JADX INFO: loaded from: classes.dex */
public abstract class PngChunkSingle extends PngChunk {
    @Override // ar.com.hjg.pngj.chunks.PngChunk
    public final boolean allowsMultiple() {
        return false;
    }

    protected PngChunkSingle(String str, ImageInfo imageInfo) {
        super(str, imageInfo);
    }

    public int hashCode() {
        return 31 + (this.f62id == null ? 0 : this.f62id.hashCode());
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        PngChunkSingle pngChunkSingle = (PngChunkSingle) obj;
        if (this.f62id == null) {
            if (pngChunkSingle.f62id != null) {
                return false;
            }
        } else if (!this.f62id.equals(pngChunkSingle.f62id)) {
            return false;
        }
        return true;
    }
}

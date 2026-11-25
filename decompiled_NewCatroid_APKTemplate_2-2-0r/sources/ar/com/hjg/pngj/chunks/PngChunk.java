package ar.com.hjg.pngj.chunks;

import ar.com.hjg.pngj.ImageInfo;
import ar.com.hjg.pngj.PngjExceptionInternal;
import com.huawei.hms.ml.language.common.utils.Constant;
import java.io.OutputStream;

/* JADX INFO: loaded from: classes.dex */
public abstract class PngChunk {
    public final boolean crit;

    /* JADX INFO: renamed from: id, reason: collision with root package name */
    public final String f62id;
    protected final ImageInfo imgInfo;
    public final boolean pub;
    protected ChunkRaw raw;
    public final boolean safe;
    private boolean priority = false;
    protected int chunkGroup = -1;

    protected abstract boolean allowsMultiple();

    protected abstract ChunkRaw createRawChunk();

    public abstract ChunkOrderingConstraint getOrderingConstraint();

    protected abstract void parseFromRaw(ChunkRaw chunkRaw);

    public enum ChunkOrderingConstraint {
        NONE,
        BEFORE_PLTE_AND_IDAT,
        AFTER_PLTE_BEFORE_IDAT,
        AFTER_PLTE_BEFORE_IDAT_PLTE_REQUIRED,
        BEFORE_IDAT,
        NA;

        public boolean mustGoBeforePLTE() {
            return this == BEFORE_PLTE_AND_IDAT;
        }

        public boolean mustGoBeforeIDAT() {
            return this == BEFORE_IDAT || this == BEFORE_PLTE_AND_IDAT || this == AFTER_PLTE_BEFORE_IDAT;
        }

        public boolean mustGoAfterPLTE() {
            return this == AFTER_PLTE_BEFORE_IDAT || this == AFTER_PLTE_BEFORE_IDAT_PLTE_REQUIRED;
        }

        public boolean isOk(int i, boolean z) {
            if (this == NONE) {
                return true;
            }
            if (this == BEFORE_IDAT) {
                return i < 4;
            }
            if (this == BEFORE_PLTE_AND_IDAT) {
                return i < 2;
            }
            if (this != AFTER_PLTE_BEFORE_IDAT) {
                return false;
            }
            if (z) {
                if (i < 4) {
                    return true;
                }
            } else if (i < 4 && i > 2) {
                return true;
            }
            return false;
        }
    }

    public PngChunk(String str, ImageInfo imageInfo) {
        this.f62id = str;
        this.imgInfo = imageInfo;
        this.crit = ChunkHelper.isCritical(str);
        this.pub = ChunkHelper.isPublic(str);
        this.safe = ChunkHelper.isSafeToCopy(str);
    }

    protected final ChunkRaw createEmptyChunk(int i, boolean z) {
        return new ChunkRaw(i, ChunkHelper.toBytes(this.f62id), z);
    }

    public final int getChunkGroup() {
        return this.chunkGroup;
    }

    final void setChunkGroup(int i) {
        this.chunkGroup = i;
    }

    public boolean hasPriority() {
        return this.priority;
    }

    public void setPriority(boolean z) {
        this.priority = z;
    }

    final void write(OutputStream outputStream) {
        ChunkRaw chunkRaw = this.raw;
        if (chunkRaw == null || chunkRaw.data == null) {
            this.raw = createRawChunk();
        }
        ChunkRaw chunkRaw2 = this.raw;
        if (chunkRaw2 == null) {
            throw new PngjExceptionInternal("null chunk ! creation failed for " + this);
        }
        chunkRaw2.writeChunk(outputStream);
    }

    public ChunkRaw getRaw() {
        return this.raw;
    }

    void setRaw(ChunkRaw chunkRaw) {
        this.raw = chunkRaw;
    }

    public int getLen() {
        ChunkRaw chunkRaw = this.raw;
        if (chunkRaw != null) {
            return chunkRaw.len;
        }
        return -1;
    }

    public long getOffset() {
        ChunkRaw chunkRaw = this.raw;
        if (chunkRaw != null) {
            return chunkRaw.getOffset();
        }
        return -1L;
    }

    public void invalidateRawData() {
        this.raw = null;
    }

    public String toString() {
        return "chunk id= " + this.f62id + " (len=" + getLen() + " offset=" + getOffset() + Constant.AFTER_QUTO;
    }
}

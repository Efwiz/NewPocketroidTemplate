package ar.com.hjg.pngj.chunks;

import ar.com.hjg.pngj.ImageInfo;
import ar.com.hjg.pngj.PngjException;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class ChunksList {
    public static final int CHUNK_GROUP_0_IDHR = 0;
    public static final int CHUNK_GROUP_1_AFTERIDHR = 1;
    public static final int CHUNK_GROUP_2_PLTE = 2;
    public static final int CHUNK_GROUP_3_AFTERPLTE = 3;
    public static final int CHUNK_GROUP_4_IDAT = 4;
    public static final int CHUNK_GROUP_5_AFTERIDAT = 5;
    public static final int CHUNK_GROUP_6_END = 6;
    final ImageInfo imageInfo;
    List<PngChunk> chunks = new ArrayList();
    boolean withPlte = false;

    public ChunksList(ImageInfo imageInfo) {
        this.imageInfo = imageInfo;
    }

    public List<PngChunk> getChunks() {
        return this.chunks;
    }

    protected static List<PngChunk> getXById(List<PngChunk> list, final String str, final String str2) {
        if (str2 == null) {
            return ChunkHelper.filterList(list, new ChunkPredicate() { // from class: ar.com.hjg.pngj.chunks.ChunksList.1
                @Override // ar.com.hjg.pngj.chunks.ChunkPredicate
                public boolean match(PngChunk pngChunk) {
                    return pngChunk.f62id.equals(str);
                }
            });
        }
        return ChunkHelper.filterList(list, new ChunkPredicate() { // from class: ar.com.hjg.pngj.chunks.ChunksList.2
            @Override // ar.com.hjg.pngj.chunks.ChunkPredicate
            public boolean match(PngChunk pngChunk) {
                if (!pngChunk.f62id.equals(str)) {
                    return false;
                }
                if (!(pngChunk instanceof PngChunkTextVar) || ((PngChunkTextVar) pngChunk).getKey().equals(str2)) {
                    return !(pngChunk instanceof PngChunkSPLT) || ((PngChunkSPLT) pngChunk).getPalName().equals(str2);
                }
                return false;
            }
        });
    }

    public void appendReadChunk(PngChunk pngChunk, int i) {
        pngChunk.setChunkGroup(i);
        this.chunks.add(pngChunk);
        if (pngChunk.f62id.equals("PLTE")) {
            this.withPlte = true;
        }
    }

    public List<? extends PngChunk> getById(String str) {
        return getById(str, null);
    }

    public List<? extends PngChunk> getById(String str, String str2) {
        return getXById(this.chunks, str, str2);
    }

    public PngChunk getById1(String str) {
        return getById1(str, false);
    }

    public PngChunk getById1(String str, boolean z) {
        return getById1(str, null, z);
    }

    public PngChunk getById1(String str, String str2, boolean z) {
        List<? extends PngChunk> byId = getById(str, str2);
        if (byId.isEmpty()) {
            return null;
        }
        if (byId.size() > 1 && (z || !byId.get(0).allowsMultiple())) {
            throw new PngjException("unexpected multiple chunks id=" + str);
        }
        return byId.get(byId.size() - 1);
    }

    public List<PngChunk> getEquivalent(final PngChunk pngChunk) {
        return ChunkHelper.filterList(this.chunks, new ChunkPredicate() { // from class: ar.com.hjg.pngj.chunks.ChunksList.3
            @Override // ar.com.hjg.pngj.chunks.ChunkPredicate
            public boolean match(PngChunk pngChunk2) {
                return ChunkHelper.equivalent(pngChunk2, pngChunk);
            }
        });
    }

    public String toString() {
        return "ChunkList: read: " + this.chunks.size();
    }

    public String toStringFull() {
        StringBuilder sb = new StringBuilder(toString());
        sb.append("\n Read:\n");
        for (PngChunk pngChunk : this.chunks) {
            sb.append(pngChunk).append(" G=" + pngChunk.getChunkGroup() + "\n");
        }
        return sb.toString();
    }
}

package ar.com.hjg.pngj.chunks;

import ar.com.hjg.pngj.ImageInfo;
import ar.com.hjg.pngj.PngjException;

/* JADX INFO: loaded from: classes.dex */
public class PngChunkICCP extends PngChunkSingle {
    public static final String ID = "iCCP";
    private byte[] compressedProfile;
    private String profileName;

    public PngChunkICCP(ImageInfo imageInfo) {
        super("iCCP", imageInfo);
    }

    @Override // ar.com.hjg.pngj.chunks.PngChunk
    public PngChunk.ChunkOrderingConstraint getOrderingConstraint() {
        return PngChunk.ChunkOrderingConstraint.BEFORE_PLTE_AND_IDAT;
    }

    @Override // ar.com.hjg.pngj.chunks.PngChunk
    public ChunkRaw createRawChunk() {
        ChunkRaw chunkRawCreateEmptyChunk = createEmptyChunk(this.profileName.length() + this.compressedProfile.length + 2, true);
        System.arraycopy(ChunkHelper.toBytes(this.profileName), 0, chunkRawCreateEmptyChunk.data, 0, this.profileName.length());
        chunkRawCreateEmptyChunk.data[this.profileName.length()] = 0;
        chunkRawCreateEmptyChunk.data[this.profileName.length() + 1] = 0;
        System.arraycopy(this.compressedProfile, 0, chunkRawCreateEmptyChunk.data, this.profileName.length() + 2, this.compressedProfile.length);
        return chunkRawCreateEmptyChunk;
    }

    @Override // ar.com.hjg.pngj.chunks.PngChunk
    public void parseFromRaw(ChunkRaw chunkRaw) {
        int iPosNullByte = ChunkHelper.posNullByte(chunkRaw.data);
        this.profileName = ChunkHelper.toString(chunkRaw.data, 0, iPosNullByte);
        if ((chunkRaw.data[iPosNullByte + 1] & 255) != 0) {
            throw new PngjException("bad compression for ChunkTypeICCP");
        }
        int i = iPosNullByte + 2;
        int length = chunkRaw.data.length - i;
        this.compressedProfile = new byte[length];
        System.arraycopy(chunkRaw.data, i, this.compressedProfile, 0, length);
    }

    public void setProfileNameAndContent(String str, byte[] bArr) {
        this.profileName = str;
        this.compressedProfile = ChunkHelper.compressBytes(bArr, true);
    }

    public void setProfileNameAndContent(String str, String str2) {
        setProfileNameAndContent(str, ChunkHelper.toBytes(str2));
    }

    public String getProfileName() {
        return this.profileName;
    }

    public byte[] getProfile() {
        return ChunkHelper.compressBytes(this.compressedProfile, false);
    }

    public String getProfileAsString() {
        return ChunkHelper.toString(getProfile());
    }
}

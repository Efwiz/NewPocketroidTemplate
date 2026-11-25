package com.reandroid.arsc.header;

import com.reandroid.arsc.base.Block;
import com.reandroid.arsc.chunk.ChunkType;
import com.reandroid.arsc.io.BlockReader;
import com.reandroid.arsc.item.ByteItem;
import com.reandroid.arsc.item.IntegerItem;
import com.reandroid.arsc.item.ShortItem;
import com.reandroid.arsc.value.ResConfig;
import java.io.IOException;
import org.billthefarmer.mididriver.MidiConstants;

/* JADX INFO: loaded from: classes10.dex */
public class TypeHeader extends HeaderBlock {
    private static final byte FLAG_OFFSET16 = 2;
    private static final byte FLAG_SPARSE = 1;
    private static final int TYPE_MIN_SIZE = 36;
    private final ResConfig config;
    private final IntegerItem count;
    private final IntegerItem entriesStart;
    private final ByteItem flags;

    /* JADX INFO: renamed from: id, reason: collision with root package name */
    private final ByteItem f228id;

    @Override // com.reandroid.arsc.header.HeaderBlock
    public int getMinimumSize() {
        return 36;
    }

    public TypeHeader(boolean z, boolean z2) {
        super(ChunkType.TYPE.ID);
        ByteItem byteItem = new ByteItem();
        this.f228id = byteItem;
        ByteItem byteItem2 = new ByteItem();
        this.flags = byteItem2;
        Block shortItem = new ShortItem();
        IntegerItem integerItem = new IntegerItem();
        this.count = integerItem;
        IntegerItem integerItem2 = new IntegerItem();
        this.entriesStart = integerItem2;
        ResConfig resConfig = new ResConfig();
        this.config = resConfig;
        addChild(byteItem);
        addChild(byteItem2);
        addChild(shortItem);
        addChild(integerItem);
        addChild(integerItem2);
        addChild(resConfig);
        setSparse(z);
        setOffset16(z2);
    }

    @Deprecated
    public TypeHeader(boolean z) {
        this(z, false);
    }

    public boolean isSparse() {
        return (getFlags().get() & 1) == 1;
    }

    public void setSparse(boolean z) {
        byte b = getFlags().get();
        getFlags().set((byte) (z ? b | 1 : b & MidiConstants.ACTIVE_SENSING));
    }

    public boolean isOffset16() {
        return getFlags().get() == 2;
    }

    public void setOffset16(boolean z) {
        byte b = getFlags().get();
        getFlags().set((byte) (z ? b | 2 : b & 253));
    }

    public ByteItem getId() {
        return this.f228id;
    }

    public ByteItem getFlags() {
        return this.flags;
    }

    public IntegerItem getCountItem() {
        return this.count;
    }

    public IntegerItem getEntriesStart() {
        return this.entriesStart;
    }

    public ResConfig getConfig() {
        return this.config;
    }

    @Override // com.reandroid.arsc.header.HeaderBlock
    public String toString() {
        if (getChunkType() != ChunkType.TYPE) {
            return super.toString();
        }
        return getClass().getSimpleName() + " {id=" + getId().toHex() + ", flags=" + getFlags().toHex() + ", count=" + getCountItem() + ", entriesStart=" + getEntriesStart() + ", config=" + getConfig() + '}';
    }

    public static TypeHeader read(BlockReader blockReader) throws IOException {
        TypeHeader typeHeader = new TypeHeader(false, false);
        if (blockReader.available() < typeHeader.getMinimumSize()) {
            throw new IOException("Too few bytes to read type header, available = " + blockReader.available());
        }
        int position = blockReader.getPosition();
        typeHeader.readBytes(blockReader);
        blockReader.seek(position);
        return typeHeader;
    }
}

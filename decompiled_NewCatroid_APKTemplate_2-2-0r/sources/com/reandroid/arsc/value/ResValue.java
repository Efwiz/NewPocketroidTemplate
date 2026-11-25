package com.reandroid.arsc.value;

import com.reandroid.arsc.chunk.PackageBlock;
import com.reandroid.arsc.io.BlockReader;
import java.io.IOException;

/* JADX INFO: loaded from: classes10.dex */
public class ResValue extends ValueItem {
    private static final int OFFSET_SIZE = 0;

    public ResValue() {
        super(8, 0);
    }

    public boolean isCompact() {
        return getHeader().isCompact();
    }

    public void setCompact(boolean z) {
        EntryHeader header = getHeader();
        if (z == header.isCompact()) {
            return;
        }
        byte type = getType();
        int data = getData();
        updateBytesLength(z);
        header.setCompact(z);
        setType(type);
        setData(data);
        if (z) {
            setRes0((byte) 0);
        }
    }

    @Override // com.reandroid.arsc.value.ValueItem
    public int getSize() {
        if (isCompact()) {
            return 0;
        }
        return super.getSize();
    }

    @Override // com.reandroid.arsc.value.ValueItem
    public void setSize(int i) {
        if (isCompact()) {
            return;
        }
        super.setSize(i);
    }

    @Override // com.reandroid.arsc.value.ValueItem
    void updateSize() {
        if (isCompact()) {
            return;
        }
        super.updateSize();
    }

    private void updateBytesLength(boolean z) {
        setBytesLength(z ? 0 : 8, false);
    }

    private EntryHeader getHeader() {
        ResTableEntry resTableEntry = (ResTableEntry) getParent(ResTableEntry.class);
        if (resTableEntry != null) {
            return resTableEntry.getHeader();
        }
        throw new RuntimeException("Unreachable");
    }

    public Entry getEntry() {
        return (Entry) getParent(Entry.class);
    }

    @Override // com.reandroid.arsc.value.ValueItem, com.reandroid.arsc.item.BlockItem, com.reandroid.arsc.base.Block
    public void onReadBytes(BlockReader blockReader) throws IOException {
        boolean zIsCompact = isCompact();
        updateBytesLength(zIsCompact);
        if (zIsCompact) {
            return;
        }
        super.onReadBytes(blockReader);
    }

    @Override // com.reandroid.arsc.value.ValueItem, com.reandroid.arsc.value.Value
    public int getData() {
        if (isCompact()) {
            return getHeader().getData();
        }
        return super.getData();
    }

    @Override // com.reandroid.arsc.value.ValueItem
    void writeData(int i) {
        if (isCompact()) {
            getHeader().setData(i);
        } else {
            super.writeData(i);
        }
    }

    @Override // com.reandroid.arsc.value.ValueItem
    public byte getType() {
        if (isCompact()) {
            return getHeader().getType();
        }
        return super.getType();
    }

    @Override // com.reandroid.arsc.value.ValueItem
    public void setType(byte b) {
        if (isCompact()) {
            getHeader().setType(b);
        } else {
            super.setType(b);
        }
    }

    @Override // com.reandroid.arsc.value.Value
    public PackageBlock getParentChunk() {
        Entry entry = getEntry();
        if (entry != null) {
            return entry.getPackageBlock();
        }
        return null;
    }
}

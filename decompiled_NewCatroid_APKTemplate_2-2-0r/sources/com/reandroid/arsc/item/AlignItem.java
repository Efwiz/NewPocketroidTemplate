package com.reandroid.arsc.item;

import com.reandroid.arsc.base.Block;
import com.reandroid.arsc.io.BlockReader;
import com.reandroid.utils.HexUtil;
import java.io.IOException;
import java.util.Arrays;

/* JADX INFO: loaded from: classes10.dex */
public class AlignItem extends BlockItem {
    private static final int ALIGNMENT = 4;
    private int alignment;
    private byte fill;

    public AlignItem() {
        super(0);
        this.alignment = 4;
    }

    public void align(Block block) {
        clear();
        if (getAlignment() <= 0) {
            return;
        }
        align(block.countBytes());
    }

    public void align(int i) {
        int alignment = getAlignment();
        if (alignment <= 0) {
            return;
        }
        setSize((alignment - (i % alignment)) % alignment);
    }

    public void alignSafe(BlockReader blockReader) throws IOException {
        align(blockReader.getPosition());
        int size = size();
        int iAvailable = blockReader.available();
        if (size == 0 || iAvailable < size) {
            return;
        }
        super.onReadBytes(blockReader);
    }

    public void clear() {
        setBytesLength(0, false);
    }

    public int size() {
        return countBytes();
    }

    public void ensureSize(int i) {
        if (i > size()) {
            setSize(i);
        }
    }

    public void setSize(int i) {
        setBytesLength(i, false);
        setFill(this.fill);
    }

    public int getAlignment() {
        return this.alignment;
    }

    public void setAlignment(int i) {
        this.alignment = i;
        if (i <= 0) {
            setBytesLength(0, false);
        }
    }

    public void setFill(byte b) {
        this.fill = b;
        Arrays.fill(getBytesInternal(), b);
    }

    public String toString() {
        int alignment = getAlignment();
        if (alignment <= 0) {
            return "OFF";
        }
        int size = size();
        byte b = this.fill;
        StringBuilder sb = new StringBuilder();
        if (alignment != 4) {
            sb.append("alignment=");
            sb.append(alignment);
            sb.append(", ");
        }
        if (b != 0) {
            sb.append("fill=");
            sb.append(HexUtil.toHex2(b));
            sb.append(", ");
        }
        sb.append("align=");
        sb.append(size);
        return sb.toString();
    }
}

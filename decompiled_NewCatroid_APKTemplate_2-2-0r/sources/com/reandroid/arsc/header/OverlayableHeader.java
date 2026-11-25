package com.reandroid.arsc.header;

import com.reandroid.arsc.chunk.ChunkType;
import com.reandroid.arsc.item.FixedLengthString;

/* JADX INFO: loaded from: classes10.dex */
public class OverlayableHeader extends HeaderBlock {
    private final FixedLengthString actor;

    /* JADX INFO: renamed from: name, reason: collision with root package name */
    private final FixedLengthString f226name;

    public OverlayableHeader() {
        super(ChunkType.OVERLAYABLE.ID);
        FixedLengthString fixedLengthString = new FixedLengthString(512);
        this.f226name = fixedLengthString;
        FixedLengthString fixedLengthString2 = new FixedLengthString(512);
        this.actor = fixedLengthString2;
        addChild(fixedLengthString);
        addChild(fixedLengthString2);
    }

    public FixedLengthString getName() {
        return this.f226name;
    }

    public FixedLengthString getActor() {
        return this.actor;
    }

    @Override // com.reandroid.arsc.header.HeaderBlock
    public String toString() {
        if (getChunkType() != ChunkType.OVERLAYABLE) {
            return super.toString();
        }
        return getClass().getSimpleName() + " {count=" + getName() + ", actor=" + getActor() + '}';
    }
}

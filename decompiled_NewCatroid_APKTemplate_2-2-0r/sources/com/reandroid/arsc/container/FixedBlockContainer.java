package com.reandroid.arsc.container;

import com.reandroid.arsc.base.Block;
import com.reandroid.arsc.base.BlockContainer;

/* JADX INFO: loaded from: classes10.dex */
public class FixedBlockContainer extends BlockContainer<Block> {
    private static final Block[] EMPTY = new Block[0];
    private final Block[] mChildes;

    @Override // com.reandroid.arsc.base.BlockContainer
    protected void onRefreshed() {
    }

    public FixedBlockContainer(int i) {
        Block[] blockArr;
        if (i == 0) {
            blockArr = EMPTY;
        } else {
            blockArr = new Block[i];
        }
        this.mChildes = blockArr;
    }

    public void addChild(int i, Block block) {
        this.mChildes[i] = block;
        if (block != null) {
            block.setIndex(i);
            block.setParent(this);
        }
    }

    @Override // com.reandroid.arsc.base.BlockContainer
    public int getChildesCount() {
        return this.mChildes.length;
    }

    @Override // com.reandroid.arsc.base.BlockContainer
    public Block[] getChildes() {
        return this.mChildes;
    }
}

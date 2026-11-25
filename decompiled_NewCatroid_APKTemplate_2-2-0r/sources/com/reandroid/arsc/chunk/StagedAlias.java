package com.reandroid.arsc.chunk;

import com.reandroid.arsc.array.StagedAliasEntryArray;
import com.reandroid.arsc.header.StagedAliasHeader;
import com.reandroid.arsc.value.StagedAliasEntry;
import java.util.Collection;

/* JADX INFO: loaded from: classes10.dex */
public class StagedAlias extends Chunk<StagedAliasHeader> {
    private final StagedAliasEntryArray stagedAliasEntryArray;

    public StagedAlias() {
        super(new StagedAliasHeader(), 1);
        StagedAliasEntryArray stagedAliasEntryArray = new StagedAliasEntryArray(((StagedAliasHeader) getHeaderBlock()).getCountItem());
        this.stagedAliasEntryArray = stagedAliasEntryArray;
        addChild(stagedAliasEntryArray);
    }

    public StagedAliasEntry searchByStagedResId(int i) {
        return getStagedAliasEntryArray().searchByStagedResId(i);
    }

    public void merge(StagedAlias stagedAlias) {
        if (stagedAlias == null || stagedAlias == this) {
            return;
        }
        StagedAliasEntryArray stagedAliasEntryArray = getStagedAliasEntryArray();
        for (StagedAliasEntry stagedAliasEntry : stagedAlias.listStagedAliasEntry()) {
            if (!stagedAliasEntryArray.contains(stagedAliasEntry)) {
                stagedAliasEntryArray.add(stagedAliasEntry);
            }
        }
    }

    public StagedAliasEntryArray getStagedAliasEntryArray() {
        return this.stagedAliasEntryArray;
    }

    public Iterable<StagedAliasEntry> listStagedAliasEntry() {
        return getStagedAliasEntryArray().listItems();
    }

    public int getStagedAliasEntryCount() {
        return getStagedAliasEntryArray().size();
    }

    @Override // com.reandroid.arsc.base.Block
    public boolean isNull() {
        return getStagedAliasEntryCount() == 0;
    }

    @Override // com.reandroid.arsc.chunk.Chunk
    protected void onChunkRefreshed() {
        getHeaderBlock().getCountItem().set(getStagedAliasEntryCount());
    }

    @Override // com.reandroid.arsc.chunk.Chunk
    public String toString() {
        return getClass().getSimpleName() + ": count=" + getStagedAliasEntryCount();
    }

    public static StagedAlias mergeAll(Collection<StagedAlias> collection) {
        if (collection.size() == 0) {
            return null;
        }
        StagedAlias stagedAlias = new StagedAlias();
        for (StagedAlias stagedAlias2 : collection) {
            if (!stagedAlias2.isNull()) {
                stagedAlias.merge(stagedAlias2);
            }
        }
        if (stagedAlias.isNull()) {
            return null;
        }
        stagedAlias.refresh();
        return stagedAlias;
    }
}

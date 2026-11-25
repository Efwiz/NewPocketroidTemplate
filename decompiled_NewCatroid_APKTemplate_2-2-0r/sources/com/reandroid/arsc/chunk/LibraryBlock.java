package com.reandroid.arsc.chunk;

import com.reandroid.arsc.array.LibraryInfoArray;
import com.reandroid.arsc.header.LibraryHeader;
import com.reandroid.arsc.value.LibraryInfo;
import java.util.Iterator;

/* JADX INFO: loaded from: classes10.dex */
public class LibraryBlock extends Chunk<LibraryHeader> implements Iterable<LibraryInfo> {
    private final LibraryInfoArray mLibraryInfoArray;

    public LibraryBlock() {
        super(new LibraryHeader(), 1);
        LibraryInfoArray libraryInfoArray = new LibraryInfoArray(((LibraryHeader) getHeaderBlock()).getCountItem());
        this.mLibraryInfoArray = libraryInfoArray;
        addChild(libraryInfoArray);
    }

    public boolean containsLibraryInfo(String str) {
        if (isEmpty()) {
            return false;
        }
        return getLibraryInfoArray().containsLibraryInfo(str);
    }

    public LibraryInfoArray getLibraryInfoArray() {
        return this.mLibraryInfoArray;
    }

    public void addLibraryInfo(LibraryBlock libraryBlock) {
        if (libraryBlock == null) {
            return;
        }
        Iterator<LibraryInfo> it = libraryBlock.getLibraryInfoArray().listItems().iterator();
        while (it.hasNext()) {
            addLibraryInfo(it.next());
        }
    }

    public void addLibraryInfo(LibraryInfo libraryInfo) {
        if (libraryInfo == null) {
            return;
        }
        getLibraryInfoArray().add(libraryInfo);
        getHeaderBlock().getCountItem().set(this.mLibraryInfoArray.size());
    }

    /* JADX WARN: Multi-variable type inference failed */
    public LibraryInfo newLibraryInfo() {
        return (LibraryInfo) this.mLibraryInfoArray.createNext();
    }

    public int size() {
        return this.mLibraryInfoArray.size();
    }

    @Override // java.lang.Iterable
    public Iterator<LibraryInfo> iterator() {
        return getLibraryInfoArray().m2755lambda$listItems$0$comreandroidarscbaseBlockArray(true);
    }

    @Override // com.reandroid.arsc.base.Block
    public boolean isNull() {
        return isEmpty();
    }

    public boolean isEmpty() {
        return !iterator().hasNext();
    }

    public void setLibraryCount(int i) {
        getHeaderBlock().getCountItem().set(i);
        this.mLibraryInfoArray.setSize(i);
    }

    @Override // com.reandroid.arsc.chunk.Chunk
    protected void onChunkRefreshed() {
        getHeaderBlock().getCountItem().set(this.mLibraryInfoArray.size());
    }

    public void merge(LibraryBlock libraryBlock) {
        if (libraryBlock == null || libraryBlock == this) {
            return;
        }
        getLibraryInfoArray().merge(libraryBlock.getLibraryInfoArray());
    }
}

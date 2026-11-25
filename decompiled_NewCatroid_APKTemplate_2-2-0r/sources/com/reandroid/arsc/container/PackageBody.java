package com.reandroid.arsc.container;

import com.reandroid.arsc.array.SpecTypePairArray;
import com.reandroid.arsc.chunk.ChunkType;
import com.reandroid.arsc.chunk.LibraryBlock;
import com.reandroid.arsc.chunk.Overlayable;
import com.reandroid.arsc.chunk.OverlayablePolicy;
import com.reandroid.arsc.chunk.StagedAlias;
import com.reandroid.arsc.chunk.UnknownChunk;
import com.reandroid.arsc.header.InfoHeader;
import com.reandroid.arsc.header.SpecHeader;
import com.reandroid.arsc.header.TypeHeader;
import com.reandroid.arsc.io.BlockReader;
import com.reandroid.arsc.list.OverlayableList;
import com.reandroid.arsc.list.StagedAliasList;
import java.io.IOException;

/* JADX INFO: loaded from: classes10.dex */
public class PackageBody extends FixedBlockContainer {
    private final LibraryBlock mLibraryBlock;
    private final OverlayableList mOverlayableList;
    private final BlockList<OverlayablePolicy> mOverlayablePolicyList;
    private final SpecTypePairArray mSpecTypePairArray;
    private final StagedAliasList mStagedAliasList;
    private final BlockList<UnknownChunk> mUnknownChunkList;

    public PackageBody() {
        super(6);
        SpecTypePairArray specTypePairArray = new SpecTypePairArray();
        this.mSpecTypePairArray = specTypePairArray;
        LibraryBlock libraryBlock = new LibraryBlock();
        this.mLibraryBlock = libraryBlock;
        StagedAliasList stagedAliasList = new StagedAliasList();
        this.mStagedAliasList = stagedAliasList;
        OverlayableList overlayableList = new OverlayableList();
        this.mOverlayableList = overlayableList;
        BlockList<OverlayablePolicy> blockList = new BlockList<>();
        this.mOverlayablePolicyList = blockList;
        BlockList<UnknownChunk> blockList2 = new BlockList<>();
        this.mUnknownChunkList = blockList2;
        addChild(0, specTypePairArray);
        addChild(1, libraryBlock);
        addChild(2, stagedAliasList);
        addChild(3, overlayableList);
        addChild(4, blockList);
        addChild(5, blockList2);
    }

    public boolean isEmpty() {
        return getSpecTypePairArray().isEmpty() && getLibraryBlock().isEmpty() && getStagedAliasList().size() == 0 && getOverlayableList().size() == 0 && getOverlayablePolicyList().size() == 0 && getUnknownChunkList().size() == 0;
    }

    public void destroy() {
        getSpecTypePairArray().clear();
        getLibraryBlock().getLibraryInfoArray().clear();
        getStagedAliasList().clearChildes();
        getOverlayableList().clearChildes();
        getOverlayablePolicyList().clearChildes();
        getUnknownChunkList().clearChildes();
    }

    public OverlayableList getOverlayableList() {
        return this.mOverlayableList;
    }

    public BlockList<OverlayablePolicy> getOverlayablePolicyList() {
        return this.mOverlayablePolicyList;
    }

    public StagedAliasList getStagedAliasList() {
        return this.mStagedAliasList;
    }

    public LibraryBlock getLibraryBlock() {
        return this.mLibraryBlock;
    }

    public SpecTypePairArray getSpecTypePairArray() {
        return this.mSpecTypePairArray;
    }

    public BlockList<UnknownChunk> getUnknownChunkList() {
        return this.mUnknownChunkList;
    }

    @Override // com.reandroid.arsc.base.BlockContainer, com.reandroid.arsc.base.Block
    public void onReadBytes(BlockReader blockReader) throws IOException {
        boolean nextBlock = true;
        while (nextBlock) {
            nextBlock = readNextBlock(blockReader);
        }
    }

    private boolean readNextBlock(BlockReader blockReader) throws IOException {
        InfoHeader headerBlock = blockReader.readHeaderBlock();
        if (headerBlock == null) {
            return false;
        }
        int position = blockReader.getPosition();
        ChunkType chunkType = headerBlock.getChunkType();
        if (chunkType == ChunkType.SPEC) {
            readSpecBlock(blockReader);
        } else if (chunkType == ChunkType.TYPE) {
            readTypeBlock(blockReader);
        } else if (chunkType == ChunkType.LIBRARY) {
            readLibraryBlock(blockReader);
        } else if (chunkType == ChunkType.OVERLAYABLE) {
            readOverlayable(blockReader);
        } else if (chunkType == ChunkType.OVERLAYABLE_POLICY) {
            readOverlayablePolicy(blockReader);
        } else if (chunkType == ChunkType.STAGED_ALIAS) {
            readStagedAlias(blockReader);
        } else {
            readUnknownChunk(blockReader);
        }
        return position != blockReader.getPosition();
    }

    private void readSpecBlock(BlockReader blockReader) throws IOException {
        this.mSpecTypePairArray.getOrCreate(SpecHeader.read(blockReader).getId().get()).getSpecBlock().readBytes(blockReader);
    }

    private void readTypeBlock(BlockReader blockReader) throws IOException {
        TypeHeader typeHeader = TypeHeader.read(blockReader);
        this.mSpecTypePairArray.getOrCreate(typeHeader.getId().get()).getTypeBlockArray().createNext(typeHeader.isSparse(), typeHeader.isOffset16()).readBytes(blockReader);
    }

    private void readLibraryBlock(BlockReader blockReader) throws IOException {
        LibraryBlock libraryBlock = new LibraryBlock();
        libraryBlock.readBytes(blockReader);
        this.mLibraryBlock.addLibraryInfo(libraryBlock);
    }

    private void readStagedAlias(BlockReader blockReader) throws IOException {
        StagedAlias stagedAlias = new StagedAlias();
        stagedAlias.readBytes(blockReader);
        this.mStagedAliasList.add(stagedAlias);
    }

    private void readOverlayable(BlockReader blockReader) throws IOException {
        Overlayable overlayable = new Overlayable();
        overlayable.readBytes(blockReader);
        this.mOverlayableList.add(overlayable);
    }

    private void readOverlayablePolicy(BlockReader blockReader) throws IOException {
        OverlayablePolicy overlayablePolicy = new OverlayablePolicy();
        overlayablePolicy.readBytes(blockReader);
        this.mOverlayablePolicyList.add(overlayablePolicy);
    }

    private void readUnknownChunk(BlockReader blockReader) throws IOException {
        UnknownChunk unknownChunk = new UnknownChunk();
        unknownChunk.readBytes(blockReader);
        this.mUnknownChunkList.add(unknownChunk);
    }
}

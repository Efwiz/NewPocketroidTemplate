package com.reandroid.dex.data;

import com.reandroid.arsc.base.Creator;
import com.reandroid.arsc.container.BlockList;
import com.reandroid.arsc.io.BlockReader;
import com.reandroid.utils.CompareUtil;
import java.io.IOException;
import java.io.OutputStream;

/* JADX INFO: loaded from: classes11.dex */
public class HiddenApiFlagValueList extends BlockList<HiddenApiFlagValue> {
    private static final Creator<HiddenApiFlagValue> CREATOR = new Creator<HiddenApiFlagValue>() { // from class: com.reandroid.dex.data.HiddenApiFlagValueList.1
        @Override // com.reandroid.arsc.base.Creator, com.reandroid.arsc.base.BlockArrayCreator
        public HiddenApiFlagValue[] newArrayInstance(int i) {
            return new HiddenApiFlagValue[i];
        }

        @Override // com.reandroid.arsc.base.Creator, com.reandroid.arsc.base.BlockCreator
        public HiddenApiFlagValue newInstance() {
            return new HiddenApiFlagValue();
        }
    };
    private DefArray<?> defArray;

    HiddenApiFlagValueList(Creator<? extends HiddenApiFlagValue> creator) {
        super(creator);
    }

    public HiddenApiFlagValueList() {
        this(CREATOR);
    }

    HiddenApiFlagValueList newCopy() {
        return new Copy(this);
    }

    public boolean isEmptyValueList() {
        return size() != 0;
    }

    @Override // com.reandroid.arsc.container.BlockList
    protected void onPreRefresh() {
        super.onPreRefresh();
        ensureDefArraySize();
        sort();
    }

    private void ensureDefArraySize() {
        DefArray<?> defArray = this.defArray;
        setSize(defArray == null ? 0 : defArray.size());
    }

    public boolean sort() {
        if (this.defArray == null) {
            return false;
        }
        return super.sort(CompareUtil.getComparableComparator());
    }

    void linkDefArray(DefArray<?> defArray) {
        this.defArray = defArray;
        if (defArray == null) {
            setSize(0);
            return;
        }
        int size = defArray.size();
        setSize(size);
        for (int i = 0; i < size; i++) {
            get(i).linkDef((Def) defArray.get(i));
        }
    }

    @Override // com.reandroid.arsc.base.Block
    protected void onReadBytes(BlockReader blockReader) throws IOException {
        readChildes(blockReader);
    }

    static class Copy extends HiddenApiFlagValueList {
        private final HiddenApiFlagValueList source;

        @Override // com.reandroid.arsc.container.BlockList, com.reandroid.arsc.base.Block
        public int countBytes() {
            return 0;
        }

        @Override // com.reandroid.dex.data.HiddenApiFlagValueList, com.reandroid.arsc.container.BlockList
        protected void onPreRefresh() {
        }

        @Override // com.reandroid.dex.data.HiddenApiFlagValueList, com.reandroid.arsc.base.Block
        protected void onReadBytes(BlockReader blockReader) throws IOException {
        }

        @Override // com.reandroid.arsc.container.BlockList, com.reandroid.arsc.base.Block
        protected int onWriteBytes(OutputStream outputStream) throws IOException {
            return 0;
        }

        @Override // com.reandroid.arsc.container.BlockList
        public void readChildes(BlockReader blockReader) throws IOException {
        }

        Copy(HiddenApiFlagValueList hiddenApiFlagValueList) {
            super(new CopyCreator(hiddenApiFlagValueList));
            this.source = hiddenApiFlagValueList;
        }

        @Override // com.reandroid.dex.data.HiddenApiFlagValueList
        HiddenApiFlagValueList newCopy() {
            return new Copy(this.source);
        }

        @Override // com.reandroid.dex.data.HiddenApiFlagValueList
        public boolean isEmptyValueList() {
            return this.source.isEmptyValueList();
        }
    }

    static class CopyCreator implements Creator<HiddenApiFlagValue> {
        private final HiddenApiFlagValueList source;

        CopyCreator(HiddenApiFlagValueList hiddenApiFlagValueList) {
            this.source = hiddenApiFlagValueList;
        }

        @Override // com.reandroid.arsc.base.Creator, com.reandroid.arsc.base.BlockArrayCreator
        public HiddenApiFlagValue[] newArrayInstance(int i) {
            return new HiddenApiFlagValue[i];
        }

        @Override // com.reandroid.arsc.base.Creator, com.reandroid.arsc.base.BlockCreator
        public HiddenApiFlagValue newInstance() {
            throw new RuntimeException("Call newInstanceAt()");
        }

        @Override // com.reandroid.arsc.base.Creator
        public HiddenApiFlagValue newInstanceAt(int i) {
            return this.source.get(i).newCopy();
        }
    }
}

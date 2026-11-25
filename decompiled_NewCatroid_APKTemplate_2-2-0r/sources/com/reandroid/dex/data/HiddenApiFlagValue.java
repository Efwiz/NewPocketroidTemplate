package com.reandroid.dex.data;

import com.reandroid.arsc.io.BlockReader;
import com.reandroid.dex.base.Ule128Item;
import com.reandroid.dex.common.HiddenApiFlag;
import com.reandroid.dex.common.Modifier;
import com.reandroid.dex.common.SectionTool;
import com.reandroid.dex.smali.SmaliFormat;
import com.reandroid.dex.smali.SmaliWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;

/* JADX INFO: loaded from: classes11.dex */
public class HiddenApiFlagValue extends Ule128Item implements SmaliFormat, Comparable<HiddenApiFlagValue> {
    private Def<?> def;

    public HiddenApiFlagValue() {
        set(HiddenApiFlag.NO_RESTRICTION);
    }

    HiddenApiFlagValue newCopy() {
        return new Copy(this);
    }

    public void linkDef(Def<?> def) {
        this.def = def;
        def.setHiddenApiFlagValue(this);
    }

    public Iterator<HiddenApiFlag> getFlags() {
        return HiddenApiFlag.valuesOf(get());
    }

    @Override // com.reandroid.dex.smali.SmaliFormat
    public void append(SmaliWriter smaliWriter) throws IOException {
        smaliWriter.appendModifiers(getFlags());
    }

    @Override // java.lang.Comparable
    public int compareTo(HiddenApiFlagValue hiddenApiFlagValue) {
        if (hiddenApiFlagValue == null) {
            return -1;
        }
        if (hiddenApiFlagValue == this) {
            return 0;
        }
        return SectionTool.compareIndex(this.def, hiddenApiFlagValue.def);
    }

    public int hashCode() {
        return get();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof HiddenApiFlagValue) && get() == ((HiddenApiFlagValue) obj).get();
    }

    @Override // com.reandroid.dex.base.Le128
    public String toString() {
        return Modifier.toString(getFlags());
    }

    static class Copy extends HiddenApiFlagValue {
        private final HiddenApiFlagValue source;

        @Override // com.reandroid.arsc.item.BlockItem, com.reandroid.arsc.base.Block
        public int countBytes() {
            return 0;
        }

        @Override // com.reandroid.arsc.item.BlockItem, com.reandroid.arsc.base.Block
        public byte[] getBytes() {
            return null;
        }

        @Override // com.reandroid.arsc.base.Block
        public boolean isNull() {
            return true;
        }

        @Override // com.reandroid.dex.base.Le128, com.reandroid.arsc.item.BlockItem, com.reandroid.arsc.base.Block
        public void onReadBytes(BlockReader blockReader) throws IOException {
        }

        @Override // com.reandroid.arsc.item.BlockItem, com.reandroid.arsc.base.Block
        protected int onWriteBytes(OutputStream outputStream) throws IOException {
            return 0;
        }

        @Override // com.reandroid.dex.base.Ule128Item, com.reandroid.dex.base.Le128
        protected void writeValue(int i) {
        }

        @Override // com.reandroid.dex.data.HiddenApiFlagValue, java.lang.Comparable
        public /* bridge */ /* synthetic */ int compareTo(HiddenApiFlagValue hiddenApiFlagValue) {
            return super.compareTo(hiddenApiFlagValue);
        }

        Copy(HiddenApiFlagValue hiddenApiFlagValue) {
            this.source = hiddenApiFlagValue;
        }

        @Override // com.reandroid.dex.base.Le128, com.reandroid.arsc.item.IntegerReference
        public int get() {
            return this.source.get();
        }

        @Override // com.reandroid.dex.base.Le128, com.reandroid.arsc.item.IntegerReference
        public void set(int i) {
            this.source.set(i);
        }
    }
}

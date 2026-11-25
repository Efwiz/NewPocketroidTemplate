package com.reandroid.dex.header;

import com.reandroid.arsc.base.Block;
import com.reandroid.arsc.base.OffsetSupplier;
import com.reandroid.arsc.io.BlockLoad;
import com.reandroid.arsc.io.BlockReader;
import com.reandroid.arsc.item.ByteArray;
import com.reandroid.arsc.item.IntegerItem;
import com.reandroid.arsc.item.IntegerReference;
import com.reandroid.arsc.item.NumberIntegerReference;
import com.reandroid.dex.sections.SectionType;
import com.reandroid.dex.sections.SpecialItem;
import java.io.IOException;
import java.io.InputStream;

/* JADX INFO: loaded from: classes11.dex */
public class DexHeader extends SpecialItem implements OffsetSupplier, BlockLoad {
    private static final int COMMON_HEADER_SIZE = 112;
    public final Checksum checksum;
    public final CountAndOffset class_id;
    public final CountAndOffset data;
    public final Endian endian;
    public final CountAndOffset field_id;
    public final IntegerItem fileSize;
    public final IntegerItem headerSize;
    public final Magic magic;
    public final IntegerItem map;
    public final CountAndOffset method_id;
    private final IntegerReference offsetReference;
    public final CountAndOffset proto_id;
    public final Signature signature;
    public final CountAndOffset string_id;
    public final CountAndOffset type_id;
    public final ByteArray unknown;
    public final Version version;

    @Override // com.reandroid.dex.common.SectionItemContainer
    protected boolean isValidOffset(int i) {
        return i >= 0;
    }

    public DexHeader(IntegerReference integerReference) {
        super(16);
        this.offsetReference = integerReference;
        Magic magic = new Magic();
        this.magic = magic;
        Version version = new Version();
        this.version = version;
        Checksum checksum = new Checksum();
        this.checksum = checksum;
        Signature signature = new Signature();
        this.signature = signature;
        IntegerItem integerItem = new IntegerItem();
        this.fileSize = integerItem;
        IntegerItem integerItem2 = new IntegerItem();
        this.headerSize = integerItem2;
        Endian endian = new Endian();
        this.endian = endian;
        IntegerItem integerItem3 = new IntegerItem();
        this.map = integerItem3;
        CountAndOffset countAndOffset = new CountAndOffset();
        this.string_id = countAndOffset;
        CountAndOffset countAndOffset2 = new CountAndOffset();
        this.type_id = countAndOffset2;
        CountAndOffset countAndOffset3 = new CountAndOffset();
        this.proto_id = countAndOffset3;
        CountAndOffset countAndOffset4 = new CountAndOffset();
        this.field_id = countAndOffset4;
        CountAndOffset countAndOffset5 = new CountAndOffset();
        this.method_id = countAndOffset5;
        CountAndOffset countAndOffset6 = new CountAndOffset();
        this.class_id = countAndOffset6;
        CountAndOffset countAndOffset7 = new CountAndOffset();
        this.data = countAndOffset7;
        ByteArray byteArray = new ByteArray();
        this.unknown = byteArray;
        addChild(0, magic);
        addChild(1, version);
        addChild(2, checksum);
        addChild(3, signature);
        addChild(4, integerItem);
        addChild(5, integerItem2);
        addChild(6, endian);
        addChild(7, integerItem3);
        addChild(8, countAndOffset);
        addChild(9, countAndOffset2);
        addChild(10, countAndOffset3);
        addChild(11, countAndOffset4);
        addChild(12, countAndOffset5);
        addChild(13, countAndOffset6);
        addChild(14, countAndOffset7);
        addChild(15, byteArray);
        integerItem2.setBlockLoad(this);
        setOffsetReference(integerReference);
    }

    public DexHeader() {
        this(new NumberIntegerReference(0));
    }

    @Override // com.reandroid.dex.common.SectionItem
    public SectionType<DexHeader> getSectionType() {
        return SectionType.HEADER;
    }

    public int getVersion() {
        return this.version.getVersionAsInteger();
    }

    public void setVersion(int i) {
        this.version.setVersionAsInteger(i);
    }

    public CountAndOffset get(SectionType<?> sectionType) {
        if (sectionType == SectionType.STRING_ID) {
            return this.string_id;
        }
        if (sectionType == SectionType.TYPE_ID) {
            return this.type_id;
        }
        if (sectionType == SectionType.PROTO_ID) {
            return this.proto_id;
        }
        if (sectionType == SectionType.FIELD_ID) {
            return this.field_id;
        }
        if (sectionType == SectionType.METHOD_ID) {
            return this.method_id;
        }
        if (sectionType == SectionType.CLASS_ID) {
            return this.class_id;
        }
        return null;
    }

    public void updateHeaderInternal(Block block) {
        byte[] bytes = block.getBytes();
        this.headerSize.set(countBytes());
        this.fileSize.set(bytes.length);
        this.signature.update(block, bytes);
        this.checksum.update(block, bytes);
    }

    @Override // com.reandroid.dex.common.SectionItemContainer, com.reandroid.arsc.base.OffsetSupplier, com.reandroid.dex.base.DexArraySupplier
    public IntegerReference getOffsetReference() {
        return this.offsetReference;
    }

    @Override // com.reandroid.arsc.io.BlockLoad
    public void onBlockLoaded(BlockReader blockReader, Block block) throws IOException {
        IntegerItem integerItem = this.headerSize;
        if (block == integerItem) {
            this.unknown.setSize(integerItem.get() - countBytes());
        }
    }

    public boolean isClassDefinitionOrderEnforced() {
        return this.version.isClassDefinitionOrderEnforced();
    }

    public String toString() {
        return "Header {magic=" + this.magic + ", version=" + this.version + ", checksum=" + this.checksum + ", signature=" + this.signature + ", fileSize=" + this.fileSize + ", headerSize=" + this.headerSize + ", endian=" + this.endian + ", map=" + this.map + ", strings=" + this.string_id + ", type=" + this.type_id + ", proto=" + this.proto_id + ", field=" + this.field_id + ", method=" + this.method_id + ", clazz=" + this.class_id + ", data=" + this.data + ", unknown=" + this.unknown + '}';
    }

    public static DexHeader readHeader(InputStream inputStream) throws IOException {
        byte[] bArr = new byte[112];
        int i = inputStream.read(bArr, 0, 112);
        if (i < 0) {
            throw new IOException("Finished reading");
        }
        if (i < 112) {
            throw new IOException("Few bytes to read header: " + i);
        }
        BlockReader blockReader = new BlockReader(bArr);
        DexHeader dexHeader = new DexHeader();
        dexHeader.headerSize.setBlockLoad(null);
        dexHeader.readBytes(blockReader);
        blockReader.close();
        return dexHeader;
    }
}

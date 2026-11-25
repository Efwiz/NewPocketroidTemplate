package org.jf.dexlib2.dexbacked;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.AbstractList;
import java.util.List;
import java.util.Set;
import org.jf.dexlib2.Opcodes;
import org.jf.dexlib2.dexbacked.model.DexStringSection;
import org.jf.dexlib2.dexbacked.model.DexTypeStringSection;
import org.jf.dexlib2.dexbacked.raw.HeaderItem;
import org.jf.dexlib2.dexbacked.raw.MapItem;
import org.jf.dexlib2.dexbacked.reference.DexBackedCallSiteReference;
import org.jf.dexlib2.dexbacked.reference.DexBackedFieldReference;
import org.jf.dexlib2.dexbacked.reference.DexBackedMethodHandleReference;
import org.jf.dexlib2.dexbacked.reference.DexBackedMethodProtoReference;
import org.jf.dexlib2.dexbacked.reference.DexBackedMethodReference;
import org.jf.dexlib2.dexbacked.reference.DexBackedStringReference;
import org.jf.dexlib2.dexbacked.reference.DexBackedTypeReference;
import org.jf.dexlib2.dexbacked.util.FixedSizeList;
import org.jf.dexlib2.dexbacked.util.FixedSizeSet;
import org.jf.dexlib2.extra.DexMarker;
import org.jf.dexlib2.iface.DexFile;
import org.jf.dexlib2.iface.reference.Reference;
import org.jf.dexlib2.util.DexUtil;
import org.jf.util.io.ByteStreams;

/* JADX INFO: loaded from: classes6.dex */
public class DexBackedDexFile implements DexFile {
    private IndexedSection<DexBackedCallSiteReference> callSiteSection;
    private final int classCount;
    private IndexedSection<DexBackedClassDef> classSection;
    private final int classStartOffset;
    private final DexBuffer dataBuffer;
    private final DexBuffer dexBuffer;
    private final DexStringSection dexStringSection;
    private final int fieldCount;
    private IndexedSection<DexBackedFieldReference> fieldSection;
    private final int fieldStartOffset;
    private final int hiddenApiRestrictionsOffset;
    private final int mapOffset;
    private final int methodCount;
    private IndexedSection<DexBackedMethodHandleReference> methodHandleSection;
    private IndexedSection<DexBackedMethodReference> methodSection;
    private final int methodStartOffset;
    private final Opcodes opcodes;
    private final int protoCount;
    private IndexedSection<DexBackedMethodProtoReference> protoSection;
    private final int protoStartOffset;
    private final int typeCount;
    private final int typeStartOffset;
    private final DexTypeStringSection typeStringSection;

    public static abstract class IndexedSection<T> extends AbstractList<T> {
        public abstract int getOffset(int i);
    }

    public int getBaseDataOffset() {
        return 0;
    }

    public boolean supportsOptimizedOpcodes() {
        return false;
    }

    protected DexBackedDexFile(Opcodes opcodes, byte[] bArr, int i, boolean z) {
        this.fieldSection = new IndexedSection<DexBackedFieldReference>() { // from class: org.jf.dexlib2.dexbacked.DexBackedDexFile.5
            @Override // java.util.AbstractList, java.util.List
            public DexBackedFieldReference get(int i2) {
                return new DexBackedFieldReference(DexBackedDexFile.this, i2);
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
            public int size() {
                return DexBackedDexFile.this.fieldCount;
            }

            @Override // org.jf.dexlib2.dexbacked.DexBackedDexFile.IndexedSection
            public int getOffset(int i2) {
                if (i2 >= 0 && i2 < size()) {
                    return DexBackedDexFile.this.fieldStartOffset + (i2 * 8);
                }
                throw new IndexOutOfBoundsException(String.format("Invalid field index %d, not in [0, %d)", Integer.valueOf(i2), Integer.valueOf(size())));
            }
        };
        this.methodSection = new IndexedSection<DexBackedMethodReference>() { // from class: org.jf.dexlib2.dexbacked.DexBackedDexFile.6
            @Override // java.util.AbstractList, java.util.List
            public DexBackedMethodReference get(int i2) {
                return new DexBackedMethodReference(DexBackedDexFile.this, i2);
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
            public int size() {
                return DexBackedDexFile.this.methodCount;
            }

            @Override // org.jf.dexlib2.dexbacked.DexBackedDexFile.IndexedSection
            public int getOffset(int i2) {
                if (i2 >= 0 && i2 < size()) {
                    return DexBackedDexFile.this.methodStartOffset + (i2 * 8);
                }
                throw new IndexOutOfBoundsException(String.format("Invalid method index %d, not in [0, %d)", Integer.valueOf(i2), Integer.valueOf(size())));
            }
        };
        this.protoSection = new IndexedSection<DexBackedMethodProtoReference>() { // from class: org.jf.dexlib2.dexbacked.DexBackedDexFile.7
            @Override // java.util.AbstractList, java.util.List
            public DexBackedMethodProtoReference get(int i2) {
                return new DexBackedMethodProtoReference(DexBackedDexFile.this, i2);
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
            public int size() {
                return DexBackedDexFile.this.protoCount;
            }

            @Override // org.jf.dexlib2.dexbacked.DexBackedDexFile.IndexedSection
            public int getOffset(int i2) {
                if (i2 >= 0 && i2 < size()) {
                    return DexBackedDexFile.this.protoStartOffset + (i2 * 12);
                }
                throw new IndexOutOfBoundsException(String.format("Invalid proto index %d, not in [0, %d)", Integer.valueOf(i2), Integer.valueOf(size())));
            }
        };
        this.classSection = new IndexedSection<DexBackedClassDef>() { // from class: org.jf.dexlib2.dexbacked.DexBackedDexFile.8
            @Override // java.util.AbstractList, java.util.List
            public DexBackedClassDef get(int i2) {
                return new DexBackedClassDef(DexBackedDexFile.this, getOffset(i2), DexBackedDexFile.this.readHiddenApiRestrictionsOffset(i2));
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
            public int size() {
                return DexBackedDexFile.this.classCount;
            }

            @Override // org.jf.dexlib2.dexbacked.DexBackedDexFile.IndexedSection
            public int getOffset(int i2) {
                if (i2 >= 0 && i2 < size()) {
                    return DexBackedDexFile.this.classStartOffset + (i2 * 32);
                }
                throw new IndexOutOfBoundsException(String.format("Invalid class index %d, not in [0, %d)", Integer.valueOf(i2), Integer.valueOf(size())));
            }
        };
        this.callSiteSection = new IndexedSection<DexBackedCallSiteReference>() { // from class: org.jf.dexlib2.dexbacked.DexBackedDexFile.9
            @Override // java.util.AbstractList, java.util.List
            public DexBackedCallSiteReference get(int i2) {
                return new DexBackedCallSiteReference(DexBackedDexFile.this, i2);
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
            public int size() {
                MapItem mapItemForSection = DexBackedDexFile.this.getMapItemForSection(7);
                if (mapItemForSection == null) {
                    return 0;
                }
                return mapItemForSection.getItemCount();
            }

            @Override // org.jf.dexlib2.dexbacked.DexBackedDexFile.IndexedSection
            public int getOffset(int i2) {
                MapItem mapItemForSection = DexBackedDexFile.this.getMapItemForSection(7);
                if (i2 < 0 || i2 >= size()) {
                    throw new IndexOutOfBoundsException(String.format("Invalid callsite index %d, not in [0, %d)", Integer.valueOf(i2), Integer.valueOf(size())));
                }
                return mapItemForSection.getOffset() + (i2 * 4);
            }
        };
        this.methodHandleSection = new IndexedSection<DexBackedMethodHandleReference>() { // from class: org.jf.dexlib2.dexbacked.DexBackedDexFile.10
            @Override // java.util.AbstractList, java.util.List
            public DexBackedMethodHandleReference get(int i2) {
                return new DexBackedMethodHandleReference(DexBackedDexFile.this, i2);
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
            public int size() {
                MapItem mapItemForSection = DexBackedDexFile.this.getMapItemForSection(8);
                if (mapItemForSection == null) {
                    return 0;
                }
                return mapItemForSection.getItemCount();
            }

            @Override // org.jf.dexlib2.dexbacked.DexBackedDexFile.IndexedSection
            public int getOffset(int i2) {
                MapItem mapItemForSection = DexBackedDexFile.this.getMapItemForSection(8);
                if (i2 < 0 || i2 >= size()) {
                    throw new IndexOutOfBoundsException(String.format("Invalid method handle index %d, not in [0, %d)", Integer.valueOf(i2), Integer.valueOf(size())));
                }
                return mapItemForSection.getOffset() + (i2 * 8);
            }
        };
        DexBuffer dexBuffer = new DexBuffer(bArr, i);
        this.dexBuffer = dexBuffer;
        this.dataBuffer = new DexBuffer(bArr, getBaseDataOffset() + i);
        int version = getVersion(bArr, i, z);
        if (opcodes == null) {
            this.opcodes = getDefaultOpcodes(version);
        } else {
            this.opcodes = opcodes;
        }
        int smallUint = dexBuffer.readSmallUint(56);
        int smallUint2 = dexBuffer.readSmallUint(60);
        DexStringSection dexStringSection = new DexStringSection(smallUint);
        this.dexStringSection = dexStringSection;
        dexStringSection.load(dexBuffer, smallUint2, 4);
        int smallUint3 = dexBuffer.readSmallUint(64);
        this.typeCount = smallUint3;
        int smallUint4 = dexBuffer.readSmallUint(68);
        this.typeStartOffset = smallUint4;
        DexTypeStringSection dexTypeStringSection = new DexTypeStringSection(smallUint3);
        this.typeStringSection = dexTypeStringSection;
        dexTypeStringSection.load(dexBuffer, smallUint4, dexStringSection);
        this.protoCount = dexBuffer.readSmallUint(72);
        this.protoStartOffset = dexBuffer.readSmallUint(76);
        this.fieldCount = dexBuffer.readSmallUint(80);
        this.fieldStartOffset = dexBuffer.readSmallUint(84);
        this.methodCount = dexBuffer.readSmallUint(88);
        this.methodStartOffset = dexBuffer.readSmallUint(92);
        this.classCount = dexBuffer.readSmallUint(96);
        this.classStartOffset = dexBuffer.readSmallUint(100);
        this.mapOffset = dexBuffer.readSmallUint(52);
        MapItem mapItemForSection = getMapItemForSection(61440);
        if (mapItemForSection != null) {
            this.hiddenApiRestrictionsOffset = mapItemForSection.getOffset();
        } else {
            this.hiddenApiRestrictionsOffset = 0;
        }
    }

    protected DexBackedDexFile(Opcodes opcodes, DexBuffer dexBuffer, DexBuffer dexBuffer2, int i, boolean z) {
        this.fieldSection = new IndexedSection<DexBackedFieldReference>() { // from class: org.jf.dexlib2.dexbacked.DexBackedDexFile.5
            @Override // java.util.AbstractList, java.util.List
            public DexBackedFieldReference get(int i2) {
                return new DexBackedFieldReference(DexBackedDexFile.this, i2);
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
            public int size() {
                return DexBackedDexFile.this.fieldCount;
            }

            @Override // org.jf.dexlib2.dexbacked.DexBackedDexFile.IndexedSection
            public int getOffset(int i2) {
                if (i2 >= 0 && i2 < size()) {
                    return DexBackedDexFile.this.fieldStartOffset + (i2 * 8);
                }
                throw new IndexOutOfBoundsException(String.format("Invalid field index %d, not in [0, %d)", Integer.valueOf(i2), Integer.valueOf(size())));
            }
        };
        this.methodSection = new IndexedSection<DexBackedMethodReference>() { // from class: org.jf.dexlib2.dexbacked.DexBackedDexFile.6
            @Override // java.util.AbstractList, java.util.List
            public DexBackedMethodReference get(int i2) {
                return new DexBackedMethodReference(DexBackedDexFile.this, i2);
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
            public int size() {
                return DexBackedDexFile.this.methodCount;
            }

            @Override // org.jf.dexlib2.dexbacked.DexBackedDexFile.IndexedSection
            public int getOffset(int i2) {
                if (i2 >= 0 && i2 < size()) {
                    return DexBackedDexFile.this.methodStartOffset + (i2 * 8);
                }
                throw new IndexOutOfBoundsException(String.format("Invalid method index %d, not in [0, %d)", Integer.valueOf(i2), Integer.valueOf(size())));
            }
        };
        this.protoSection = new IndexedSection<DexBackedMethodProtoReference>() { // from class: org.jf.dexlib2.dexbacked.DexBackedDexFile.7
            @Override // java.util.AbstractList, java.util.List
            public DexBackedMethodProtoReference get(int i2) {
                return new DexBackedMethodProtoReference(DexBackedDexFile.this, i2);
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
            public int size() {
                return DexBackedDexFile.this.protoCount;
            }

            @Override // org.jf.dexlib2.dexbacked.DexBackedDexFile.IndexedSection
            public int getOffset(int i2) {
                if (i2 >= 0 && i2 < size()) {
                    return DexBackedDexFile.this.protoStartOffset + (i2 * 12);
                }
                throw new IndexOutOfBoundsException(String.format("Invalid proto index %d, not in [0, %d)", Integer.valueOf(i2), Integer.valueOf(size())));
            }
        };
        this.classSection = new IndexedSection<DexBackedClassDef>() { // from class: org.jf.dexlib2.dexbacked.DexBackedDexFile.8
            @Override // java.util.AbstractList, java.util.List
            public DexBackedClassDef get(int i2) {
                return new DexBackedClassDef(DexBackedDexFile.this, getOffset(i2), DexBackedDexFile.this.readHiddenApiRestrictionsOffset(i2));
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
            public int size() {
                return DexBackedDexFile.this.classCount;
            }

            @Override // org.jf.dexlib2.dexbacked.DexBackedDexFile.IndexedSection
            public int getOffset(int i2) {
                if (i2 >= 0 && i2 < size()) {
                    return DexBackedDexFile.this.classStartOffset + (i2 * 32);
                }
                throw new IndexOutOfBoundsException(String.format("Invalid class index %d, not in [0, %d)", Integer.valueOf(i2), Integer.valueOf(size())));
            }
        };
        this.callSiteSection = new IndexedSection<DexBackedCallSiteReference>() { // from class: org.jf.dexlib2.dexbacked.DexBackedDexFile.9
            @Override // java.util.AbstractList, java.util.List
            public DexBackedCallSiteReference get(int i2) {
                return new DexBackedCallSiteReference(DexBackedDexFile.this, i2);
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
            public int size() {
                MapItem mapItemForSection = DexBackedDexFile.this.getMapItemForSection(7);
                if (mapItemForSection == null) {
                    return 0;
                }
                return mapItemForSection.getItemCount();
            }

            @Override // org.jf.dexlib2.dexbacked.DexBackedDexFile.IndexedSection
            public int getOffset(int i2) {
                MapItem mapItemForSection = DexBackedDexFile.this.getMapItemForSection(7);
                if (i2 < 0 || i2 >= size()) {
                    throw new IndexOutOfBoundsException(String.format("Invalid callsite index %d, not in [0, %d)", Integer.valueOf(i2), Integer.valueOf(size())));
                }
                return mapItemForSection.getOffset() + (i2 * 4);
            }
        };
        this.methodHandleSection = new IndexedSection<DexBackedMethodHandleReference>() { // from class: org.jf.dexlib2.dexbacked.DexBackedDexFile.10
            @Override // java.util.AbstractList, java.util.List
            public DexBackedMethodHandleReference get(int i2) {
                return new DexBackedMethodHandleReference(DexBackedDexFile.this, i2);
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
            public int size() {
                MapItem mapItemForSection = DexBackedDexFile.this.getMapItemForSection(8);
                if (mapItemForSection == null) {
                    return 0;
                }
                return mapItemForSection.getItemCount();
            }

            @Override // org.jf.dexlib2.dexbacked.DexBackedDexFile.IndexedSection
            public int getOffset(int i2) {
                MapItem mapItemForSection = DexBackedDexFile.this.getMapItemForSection(8);
                if (i2 < 0 || i2 >= size()) {
                    throw new IndexOutOfBoundsException(String.format("Invalid method handle index %d, not in [0, %d)", Integer.valueOf(i2), Integer.valueOf(size())));
                }
                return mapItemForSection.getOffset() + (i2 * 8);
            }
        };
        this.dexBuffer = dexBuffer;
        this.dataBuffer = dexBuffer2;
        int version = getVersion(dexBuffer.readByteRange(i, 112), i, z);
        if (opcodes == null) {
            this.opcodes = getDefaultOpcodes(version);
        } else {
            this.opcodes = opcodes;
        }
        int smallUint = dexBuffer.readSmallUint(56);
        int smallUint2 = dexBuffer.readSmallUint(60);
        DexStringSection dexStringSection = new DexStringSection(smallUint);
        this.dexStringSection = dexStringSection;
        dexStringSection.load(dexBuffer, smallUint2, 4);
        int smallUint3 = dexBuffer.readSmallUint(64);
        this.typeCount = smallUint3;
        int smallUint4 = dexBuffer.readSmallUint(68);
        this.typeStartOffset = smallUint4;
        DexTypeStringSection dexTypeStringSection = new DexTypeStringSection(smallUint3);
        this.typeStringSection = dexTypeStringSection;
        dexTypeStringSection.load(dexBuffer, smallUint4, dexStringSection);
        this.protoCount = dexBuffer.readSmallUint(72);
        this.protoStartOffset = dexBuffer.readSmallUint(76);
        this.fieldCount = dexBuffer.readSmallUint(80);
        this.fieldStartOffset = dexBuffer.readSmallUint(84);
        this.methodCount = dexBuffer.readSmallUint(88);
        this.methodStartOffset = dexBuffer.readSmallUint(92);
        this.classCount = dexBuffer.readSmallUint(96);
        this.classStartOffset = dexBuffer.readSmallUint(100);
        this.mapOffset = dexBuffer.readSmallUint(52);
        MapItem mapItemForSection = getMapItemForSection(61440);
        if (mapItemForSection != null) {
            this.hiddenApiRestrictionsOffset = mapItemForSection.getOffset();
        } else {
            this.hiddenApiRestrictionsOffset = 0;
        }
    }

    protected int getVersion(byte[] bArr, int i, boolean z) {
        if (z) {
            return DexUtil.verifyDexHeader(bArr, i);
        }
        return HeaderItem.getVersion(bArr, i);
    }

    protected Opcodes getDefaultOpcodes(int i) {
        return Opcodes.forDexVersion(i);
    }

    public DexBuffer getBuffer() {
        return this.dexBuffer;
    }

    public DexBuffer getDataBuffer() {
        return this.dataBuffer;
    }

    public DexBackedDexFile(Opcodes opcodes, DexBuffer dexBuffer) {
        this(opcodes, dexBuffer.buf, dexBuffer.baseOffset);
    }

    public DexBackedDexFile(Opcodes opcodes, byte[] bArr, int i) {
        this(opcodes, bArr, i, false);
    }

    public DexBackedDexFile(Opcodes opcodes, byte[] bArr) {
        this(opcodes, bArr, 0, true);
    }

    public static DexBackedDexFile fromInputStream(Opcodes opcodes, InputStream inputStream) throws IOException {
        byte[] byteArray = ByteStreams.toByteArray(inputStream);
        DexUtil.verifyDexHeader(byteArray, 0);
        return new DexBackedDexFile(opcodes, byteArray, 0, false);
    }

    public static DexBackedDexFile fromFile(Opcodes opcodes, File file) throws IOException {
        byte[] byteArray = ByteStreams.toByteArray(file);
        DexUtil.verifyDexHeader(byteArray, 0);
        return new DexBackedDexFile(opcodes, byteArray, 0, false);
    }

    @Override // org.jf.dexlib2.iface.DexFile
    public Opcodes getOpcodes() {
        return this.opcodes;
    }

    @Override // org.jf.dexlib2.iface.DexFile
    public List<DexMarker> getMarkers() {
        return DexMarker.listMarkers(getStringSection().iterator());
    }

    @Override // org.jf.dexlib2.iface.DexFile
    public Set<? extends DexBackedClassDef> getClasses() {
        return new FixedSizeSet<DexBackedClassDef>() { // from class: org.jf.dexlib2.dexbacked.DexBackedDexFile.1
            @Override // org.jf.dexlib2.dexbacked.util.FixedSizeSet
            public DexBackedClassDef readItem(int i) {
                return DexBackedDexFile.this.getClassSection().get(i);
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
            public int size() {
                return DexBackedDexFile.this.classCount;
            }
        };
    }

    public List<DexBackedStringReference> getStringReferences() {
        return new AbstractList<DexBackedStringReference>() { // from class: org.jf.dexlib2.dexbacked.DexBackedDexFile.2
            @Override // java.util.AbstractList, java.util.List
            public DexBackedStringReference get(int i) {
                if (i < 0 || i >= DexBackedDexFile.this.getStringSection().size()) {
                    throw new IndexOutOfBoundsException();
                }
                return new DexBackedStringReference(DexBackedDexFile.this, i);
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
            public int size() {
                return DexBackedDexFile.this.getStringSection().size();
            }
        };
    }

    public List<DexBackedTypeReference> getTypeReferences() {
        return new AbstractList<DexBackedTypeReference>() { // from class: org.jf.dexlib2.dexbacked.DexBackedDexFile.3
            @Override // java.util.AbstractList, java.util.List
            public DexBackedTypeReference get(int i) {
                if (i < 0 || i >= DexBackedDexFile.this.getTypeSection().size()) {
                    throw new IndexOutOfBoundsException();
                }
                return new DexBackedTypeReference(DexBackedDexFile.this, i);
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
            public int size() {
                return DexBackedDexFile.this.getTypeSection().size();
            }
        };
    }

    public List<? extends Reference> getReferences(int i) {
        if (i == 0) {
            return getStringReferences();
        }
        if (i == 1) {
            return getTypeReferences();
        }
        if (i == 2) {
            return getFieldSection();
        }
        if (i == 3) {
            return getMethodSection();
        }
        throw new IllegalArgumentException(String.format("Invalid reference type: %d", Integer.valueOf(i)));
    }

    public List<MapItem> getMapItems() {
        final int smallUint = this.dataBuffer.readSmallUint(this.mapOffset);
        return new FixedSizeList<MapItem>() { // from class: org.jf.dexlib2.dexbacked.DexBackedDexFile.4
            @Override // org.jf.dexlib2.dexbacked.util.FixedSizeList
            public MapItem readItem(int i) {
                return new MapItem(DexBackedDexFile.this, DexBackedDexFile.this.mapOffset + 4 + (i * 12));
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
            public int size() {
                return smallUint;
            }
        };
    }

    public MapItem getMapItemForSection(int i) {
        for (MapItem mapItem : getMapItems()) {
            if (mapItem.getType() == i) {
                return mapItem;
            }
        }
        return null;
    }

    public static class NotADexFile extends RuntimeException {
        public NotADexFile() {
        }

        public NotADexFile(Throwable th) {
            super(th);
        }

        public NotADexFile(String str) {
            super(str);
        }

        public NotADexFile(String str, Throwable th) {
            super(str, th);
        }
    }

    public List<String> getStringSection() {
        return this.dexStringSection.getStringList();
    }

    public List<String> getTypeSection() {
        return this.typeStringSection.getStringList();
    }

    public DexStringSection getDexStringSection() {
        return this.dexStringSection;
    }

    public DexTypeStringSection getTypeStringSection() {
        return this.typeStringSection;
    }

    public IndexedSection<DexBackedFieldReference> getFieldSection() {
        return this.fieldSection;
    }

    public IndexedSection<DexBackedMethodReference> getMethodSection() {
        return this.methodSection;
    }

    public IndexedSection<DexBackedMethodProtoReference> getProtoSection() {
        return this.protoSection;
    }

    public IndexedSection<DexBackedClassDef> getClassSection() {
        return this.classSection;
    }

    public IndexedSection<DexBackedCallSiteReference> getCallSiteSection() {
        return this.callSiteSection;
    }

    public IndexedSection<DexBackedMethodHandleReference> getMethodHandleSection() {
        return this.methodHandleSection;
    }

    protected DexBackedMethodImplementation createMethodImplementation(DexBackedDexFile dexBackedDexFile, DexBackedMethod dexBackedMethod, int i) {
        return new DexBackedMethodImplementation(dexBackedDexFile, dexBackedMethod, i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int readHiddenApiRestrictionsOffset(int i) {
        int i2;
        int i3 = this.hiddenApiRestrictionsOffset;
        if (i3 == 0 || (i2 = this.dexBuffer.readInt(i3 + 4 + (i * 4))) == 0) {
            return 0;
        }
        return this.hiddenApiRestrictionsOffset + i2;
    }
}

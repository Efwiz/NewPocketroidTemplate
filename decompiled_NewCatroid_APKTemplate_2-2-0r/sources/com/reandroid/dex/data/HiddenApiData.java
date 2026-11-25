package com.reandroid.dex.data;

import com.reandroid.arsc.base.OffsetSupplier;
import com.reandroid.arsc.io.BlockReader;
import com.reandroid.arsc.item.IntegerReference;
import com.reandroid.arsc.item.NumberIntegerReference;
import com.reandroid.dex.base.FixedDexContainer;
import com.reandroid.dex.base.OffsetReceiver;
import com.reandroid.dex.id.ClassId;
import com.reandroid.dex.key.FieldKey;
import com.reandroid.dex.key.Key;
import com.reandroid.dex.key.MethodKey;
import com.reandroid.utils.ObjectsUtil;
import java.io.IOException;
import java.io.OutputStream;

/* JADX INFO: loaded from: classes11.dex */
public class HiddenApiData extends FixedDexContainer implements OffsetSupplier, OffsetReceiver {
    private ClassId classId;
    private HiddenApiFlagValueList directMethods;
    private HiddenApiFlagValueList instanceFields;
    private IntegerReference offsetReference;
    private HiddenApiFlagValueList staticFields;
    private HiddenApiFlagValueList virtualMethods;

    public HiddenApiData() {
        super(4);
    }

    public HiddenApiFlagValue get(Def<?> def) {
        HiddenApiFlagValueList flagValueList = getFlagValueList(def);
        if (flagValueList != null) {
            return flagValueList.get(def.getIndex());
        }
        return null;
    }

    private HiddenApiFlagValueList getFlagValueList(Def<?> def) {
        Key key = def.getKey();
        if (key instanceof FieldKey) {
            if (def.isStatic()) {
                return getStaticFields();
            }
            return getInstanceFields();
        }
        if (!(key instanceof MethodKey)) {
            return null;
        }
        if (def.isDirect()) {
            return getDirectMethods();
        }
        return getVirtualMethods();
    }

    HiddenApiData newCopy() {
        return new Copy(this);
    }

    public boolean isEmptyValueList() {
        HiddenApiFlagValueList hiddenApiFlagValueList = this.staticFields;
        if (hiddenApiFlagValueList != null && !hiddenApiFlagValueList.isEmptyValueList()) {
            return false;
        }
        HiddenApiFlagValueList hiddenApiFlagValueList2 = this.instanceFields;
        if (hiddenApiFlagValueList2 != null && !hiddenApiFlagValueList2.isEmptyValueList()) {
            return false;
        }
        HiddenApiFlagValueList hiddenApiFlagValueList3 = this.directMethods;
        if (hiddenApiFlagValueList3 != null && !hiddenApiFlagValueList3.isEmptyValueList()) {
            return false;
        }
        HiddenApiFlagValueList hiddenApiFlagValueList4 = this.virtualMethods;
        return hiddenApiFlagValueList4 == null || hiddenApiFlagValueList4.isEmptyValueList();
    }

    public ClassId getClassId() {
        return this.classId;
    }

    public void setClassId(ClassId classId) {
        if (this.classId == classId) {
            return;
        }
        this.classId = classId;
        initializeValueList();
        addChild(0, this.staticFields);
        addChild(1, this.instanceFields);
        addChild(2, this.directMethods);
        addChild(3, this.virtualMethods);
        linkDefArray(classId);
    }

    private void initializeValueList() {
        HiddenApiFlagValueList[] hiddenApiFlagValueListArrCreateHiddenApiFlagValueList = createHiddenApiFlagValueList();
        this.staticFields = hiddenApiFlagValueListArrCreateHiddenApiFlagValueList[0];
        this.instanceFields = hiddenApiFlagValueListArrCreateHiddenApiFlagValueList[1];
        this.directMethods = hiddenApiFlagValueListArrCreateHiddenApiFlagValueList[2];
        this.virtualMethods = hiddenApiFlagValueListArrCreateHiddenApiFlagValueList[3];
    }

    HiddenApiFlagValueList[] createHiddenApiFlagValueList() {
        return new HiddenApiFlagValueList[]{new HiddenApiFlagValueList(), new HiddenApiFlagValueList(), new HiddenApiFlagValueList(), new HiddenApiFlagValueList()};
    }

    void linkDefArray(ClassId classId) {
        ClassData classData = classId.getClassData();
        this.staticFields.linkDefArray(classData.getStaticFieldsArray());
        this.instanceFields.linkDefArray(classData.getInstanceFieldsArray());
        this.directMethods.linkDefArray(classData.getDirectMethodsArray());
        this.virtualMethods.linkDefArray(classData.getVirtualMethodArray());
    }

    public HiddenApiFlagValueList getStaticFields() {
        return this.staticFields;
    }

    public HiddenApiFlagValueList getInstanceFields() {
        return this.instanceFields;
    }

    public HiddenApiFlagValueList getDirectMethods() {
        return this.directMethods;
    }

    public HiddenApiFlagValueList getVirtualMethods() {
        return this.virtualMethods;
    }

    public int getOffset() {
        IntegerReference offsetReference = getOffsetReference();
        if (offsetReference != null) {
            return offsetReference.get();
        }
        return 0;
    }

    public void setOffset(int i) {
        IntegerReference offsetReference = getOffsetReference();
        if (offsetReference == null) {
            offsetReference = new NumberIntegerReference();
            setOffsetReference(offsetReference);
        }
        offsetReference.set(i);
    }

    @Override // com.reandroid.arsc.base.OffsetSupplier, com.reandroid.dex.base.DexArraySupplier
    public IntegerReference getOffsetReference() {
        return this.offsetReference;
    }

    @Override // com.reandroid.dex.base.OffsetReceiver
    public void setOffsetReference(IntegerReference integerReference) {
        this.offsetReference = integerReference;
    }

    @Override // com.reandroid.dex.base.FixedDexContainer, com.reandroid.arsc.base.BlockContainer, com.reandroid.arsc.base.Block
    public void onReadBytes(BlockReader blockReader) throws IOException {
        int offset = getOffset();
        if (offset == 0) {
            throw new IOException("Can not read at zero");
        }
        blockReader.seek(offset);
        super.onReadBytes(blockReader);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        HiddenApiData hiddenApiData = (HiddenApiData) obj;
        return ObjectsUtil.equals(this.staticFields, hiddenApiData.staticFields) && ObjectsUtil.equals(this.instanceFields, hiddenApiData.instanceFields) && ObjectsUtil.equals(this.directMethods, hiddenApiData.directMethods) && ObjectsUtil.equals(this.virtualMethods, hiddenApiData.virtualMethods);
    }

    public int hashCode() {
        return ObjectsUtil.hash(this.staticFields, this.instanceFields, this.directMethods, this.virtualMethods);
    }

    static class Copy extends HiddenApiData {
        private final HiddenApiData source;

        @Override // com.reandroid.arsc.base.BlockContainer, com.reandroid.arsc.base.Block
        public int countBytes() {
            return 0;
        }

        @Override // com.reandroid.arsc.base.BlockContainer, com.reandroid.arsc.base.Block
        public byte[] getBytes() {
            return null;
        }

        @Override // com.reandroid.dex.data.HiddenApiData, com.reandroid.dex.base.FixedDexContainer, com.reandroid.arsc.base.BlockContainer, com.reandroid.arsc.base.Block
        public void onReadBytes(BlockReader blockReader) throws IOException {
        }

        @Override // com.reandroid.arsc.base.BlockContainer, com.reandroid.arsc.base.Block
        public int onWriteBytes(OutputStream outputStream) throws IOException {
            return 0;
        }

        Copy(HiddenApiData hiddenApiData) {
            this.source = hiddenApiData;
        }

        @Override // com.reandroid.dex.data.HiddenApiData
        HiddenApiData newCopy() {
            return this.source.newCopy();
        }

        @Override // com.reandroid.dex.data.HiddenApiData
        HiddenApiFlagValueList[] createHiddenApiFlagValueList() {
            return new HiddenApiFlagValueList[]{this.source.getStaticFields().newCopy(), this.source.getInstanceFields().newCopy(), this.source.getDirectMethods().newCopy(), this.source.getVirtualMethods().newCopy()};
        }

        @Override // com.reandroid.dex.data.HiddenApiData, com.reandroid.arsc.base.OffsetSupplier, com.reandroid.dex.base.DexArraySupplier
        public IntegerReference getOffsetReference() {
            return this.source.getOffsetReference();
        }

        @Override // com.reandroid.dex.data.HiddenApiData
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (obj instanceof Copy) {
                return this.source.equals(obj);
            }
            return false;
        }

        @Override // com.reandroid.dex.data.HiddenApiData
        public int hashCode() {
            return this.source.hashCode() * 31;
        }
    }
}

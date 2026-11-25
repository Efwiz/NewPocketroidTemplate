package com.reandroid.arsc.value;

import com.reandroid.arsc.base.Block;
import com.reandroid.arsc.base.BlockCounter;
import com.reandroid.arsc.io.BlockReader;
import com.reandroid.arsc.item.FixedLengthString;
import com.reandroid.arsc.item.IntegerItem;
import com.reandroid.arsc.model.ResourceLibrary;
import com.reandroid.json.JSONConvert;
import com.reandroid.json.JSONObject;
import com.reandroid.utils.HexUtil;
import java.io.IOException;
import java.io.OutputStream;

/* JADX INFO: loaded from: classes10.dex */
public class LibraryInfo extends Block implements JSONConvert<JSONObject>, ResourceLibrary {
    private final IntegerItem mPackageId;
    private final FixedLengthString mPackageName;

    public LibraryInfo() {
        IntegerItem integerItem = new IntegerItem();
        this.mPackageId = integerItem;
        FixedLengthString fixedLengthString = new FixedLengthString(256);
        this.mPackageName = fixedLengthString;
        integerItem.setIndex(0);
        integerItem.setParent(this);
        fixedLengthString.setIndex(1);
        fixedLengthString.setParent(this);
    }

    @Override // com.reandroid.arsc.model.ResourceLibrary
    public int getId() {
        return this.mPackageId.get();
    }

    public void setId(int i) {
        this.mPackageId.set(i);
    }

    @Override // com.reandroid.arsc.model.ResourceLibrary
    public String getName() {
        return this.mPackageName.get();
    }

    public void setName(String str) {
        this.mPackageName.set(str);
    }

    @Override // com.reandroid.common.Namespace
    public String getPrefix() {
        return ResourceLibrary.toPrefix(getName());
    }

    @Override // com.reandroid.common.Namespace
    public String getUri() {
        if (getId() == 1 && ResourceLibrary.PREFIX_ANDROID.equals(getName())) {
            return ResourceLibrary.URI_ANDROID;
        }
        return ResourceLibrary.URI_RES_AUTO;
    }

    @Override // com.reandroid.arsc.model.ResourceLibrary
    public boolean packageNameMatches(String str) {
        return ResourceLibrary.packageNameMatches(this, str);
    }

    @Override // com.reandroid.arsc.base.Block
    public byte[] getBytes() {
        if (isNull()) {
            return null;
        }
        return addBytes(this.mPackageId.getBytes(), this.mPackageName.getBytes());
    }

    @Override // com.reandroid.arsc.base.Block
    public int countBytes() {
        if (isNull()) {
            return 0;
        }
        return this.mPackageId.countBytes() + this.mPackageName.countBytes();
    }

    @Override // com.reandroid.arsc.base.Block
    public void onCountUpTo(BlockCounter blockCounter) {
        if (blockCounter.FOUND) {
            return;
        }
        blockCounter.setCurrent(this);
        if (blockCounter.END == this) {
            blockCounter.FOUND = true;
        } else {
            this.mPackageId.onCountUpTo(blockCounter);
            this.mPackageName.onCountUpTo(blockCounter);
        }
    }

    @Override // com.reandroid.arsc.base.Block
    protected int onWriteBytes(OutputStream outputStream) throws IOException {
        return this.mPackageId.writeBytes(outputStream) + this.mPackageName.writeBytes(outputStream);
    }

    @Override // com.reandroid.arsc.base.Block
    public void onReadBytes(BlockReader blockReader) throws IOException {
        this.mPackageId.readBytes(blockReader);
        this.mPackageName.readBytes(blockReader);
    }

    @Override // com.reandroid.json.JSONConvert
    public JSONObject toJson() {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("id", getId());
        jSONObject.put("name", getName());
        return jSONObject;
    }

    @Override // com.reandroid.json.JSONConvert
    public void fromJson(JSONObject jSONObject) {
        setId(jSONObject.getInt("id"));
        setName(jSONObject.getString("name"));
    }

    public void merge(LibraryInfo libraryInfo) {
        if (libraryInfo == null || libraryInfo == this) {
            return;
        }
        if (getId() != libraryInfo.getId()) {
            throw new IllegalArgumentException("Can not add different id libraries: " + getId() + "!=" + libraryInfo.getId());
        }
        setName(libraryInfo.getName());
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("LIBRARY{");
        sb.append(HexUtil.toHex2((byte) getId()));
        sb.append(':');
        String name2 = getName();
        if (name2 == null) {
            name2 = "NULL";
        }
        sb.append(name2);
        sb.append('}');
        return sb.toString();
    }
}

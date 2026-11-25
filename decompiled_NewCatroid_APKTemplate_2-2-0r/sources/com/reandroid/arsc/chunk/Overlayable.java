package com.reandroid.arsc.chunk;

import com.reandroid.arsc.container.BlockList;
import com.reandroid.arsc.header.InfoHeader;
import com.reandroid.arsc.header.OverlayableHeader;
import com.reandroid.arsc.io.BlockReader;
import com.reandroid.arsc.item.ByteArray;
import com.reandroid.json.JSONArray;
import com.reandroid.json.JSONConvert;
import com.reandroid.json.JSONObject;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes10.dex */
public class Overlayable extends Chunk<OverlayableHeader> implements JSONConvert<JSONObject> {
    public static final String NAME_actor = "actor";
    public static final String NAME_name = "name";
    public static final String NAME_policies = "policies";
    private final ByteArray extraBytes;
    private final BlockList<OverlayablePolicy> policyList;

    @Override // com.reandroid.arsc.chunk.Chunk
    protected void onChunkRefreshed() {
    }

    public Overlayable() {
        super(new OverlayableHeader(), 2);
        BlockList<OverlayablePolicy> blockList = new BlockList<>();
        this.policyList = blockList;
        ByteArray byteArray = new ByteArray();
        this.extraBytes = byteArray;
        addChild(blockList);
        addChild(byteArray);
    }

    public OverlayablePolicy get(int i) {
        for (OverlayablePolicy overlayablePolicy : listOverlayablePolicies()) {
            if (i == overlayablePolicy.getFlags()) {
                return overlayablePolicy;
            }
        }
        return null;
    }

    public void addOverlayablePolicy(OverlayablePolicy overlayablePolicy) {
        this.policyList.add(overlayablePolicy);
    }

    public List<OverlayablePolicy> listOverlayablePolicies() {
        return this.policyList.getChildes();
    }

    public ByteArray getExtraBytes() {
        return this.extraBytes;
    }

    public String getName() {
        return getHeaderBlock().getName().get();
    }

    public void setName(String str) {
        getHeaderBlock().getName().set(str);
    }

    public String getActor() {
        return getHeaderBlock().getActor().get();
    }

    public void setActor(String str) {
        getHeaderBlock().getActor().set(str);
    }

    @Override // com.reandroid.arsc.chunk.Chunk, com.reandroid.arsc.base.BlockContainer, com.reandroid.arsc.base.Block
    public void onReadBytes(BlockReader blockReader) throws IOException {
        InfoHeader headerBlock = blockReader.readHeaderBlock();
        checkInvalidChunk(headerBlock);
        int chunkSize = headerBlock.getChunkSize();
        BlockReader blockReaderCreate = blockReader.create(chunkSize);
        getHeaderBlock().readBytes(blockReaderCreate);
        readOverlayablePolicies(blockReaderCreate);
        readExtraBytes(blockReaderCreate);
        blockReader.offset(chunkSize);
        blockReaderCreate.close();
        onChunkLoaded();
    }

    private void readOverlayablePolicies(BlockReader blockReader) throws IOException {
        InfoHeader headerBlock = blockReader.readHeaderBlock();
        BlockList<OverlayablePolicy> blockList = this.policyList;
        while (headerBlock != null && headerBlock.getChunkType() == ChunkType.OVERLAYABLE_POLICY) {
            OverlayablePolicy overlayablePolicy = new OverlayablePolicy();
            blockList.add(overlayablePolicy);
            overlayablePolicy.readBytes(blockReader);
            headerBlock = blockReader.readHeaderBlock();
        }
    }

    private void readExtraBytes(BlockReader blockReader) throws IOException {
        this.extraBytes.setSize(blockReader.available());
        this.extraBytes.readBytes(blockReader);
    }

    @Override // com.reandroid.json.JSONConvert
    public JSONObject toJson() {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("name", getName());
        jSONObject.put(NAME_actor, getActor());
        JSONArray jSONArray = new JSONArray();
        Iterator<OverlayablePolicy> it = listOverlayablePolicies().iterator();
        while (it.hasNext()) {
            jSONArray.put(it.next().toJson());
        }
        jSONObject.put(NAME_policies, jSONArray);
        return jSONObject;
    }

    @Override // com.reandroid.json.JSONConvert
    public void fromJson(JSONObject jSONObject) {
        setName(jSONObject.optString("name"));
        setActor(jSONObject.optString(NAME_actor));
        JSONArray jSONArray = jSONObject.getJSONArray(NAME_policies);
        int length = jSONArray.length();
        BlockList<OverlayablePolicy> blockList = this.policyList;
        for (int i = 0; i < length; i++) {
            OverlayablePolicy overlayablePolicy = new OverlayablePolicy();
            blockList.add(overlayablePolicy);
            overlayablePolicy.fromJson(jSONArray.getJSONObject(i));
        }
    }

    public void merge(Overlayable overlayable) {
        if (overlayable == null || overlayable == this) {
            return;
        }
        setName(overlayable.getName());
        setActor(overlayable.getActor());
        for (OverlayablePolicy overlayablePolicy : overlayable.listOverlayablePolicies()) {
            OverlayablePolicy overlayablePolicy2 = get(overlayablePolicy.getFlags());
            if (overlayablePolicy2 == null) {
                overlayablePolicy2 = new OverlayablePolicy();
                addOverlayablePolicy(overlayablePolicy2);
            }
            overlayablePolicy2.merge(overlayablePolicy);
        }
    }

    @Override // com.reandroid.arsc.chunk.Chunk
    public String toString() {
        return getClass().getSimpleName() + ": name='" + getName() + "', actor='" + getActor() + "', policies=" + this.policyList.size() + "', extra=" + getExtraBytes().size();
    }
}

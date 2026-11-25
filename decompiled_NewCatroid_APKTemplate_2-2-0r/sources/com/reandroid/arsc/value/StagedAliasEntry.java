package com.reandroid.arsc.value;

import com.reandroid.arsc.item.ByteArray;
import com.reandroid.json.JSONConvert;
import com.reandroid.json.JSONObject;
import com.reandroid.utils.HexUtil;

/* JADX INFO: loaded from: classes10.dex */
public class StagedAliasEntry extends ByteArray implements JSONConvert<JSONObject> {
    public static final String NAME_finalized_resource_id = "finalized_resource_id";
    public static final String NAME_staged_resource_id = "staged_resource_id";

    public StagedAliasEntry() {
        super(8);
    }

    public boolean isEqual(StagedAliasEntry stagedAliasEntry) {
        if (stagedAliasEntry == null) {
            return false;
        }
        if (stagedAliasEntry == this) {
            return true;
        }
        return getStagedResId() == stagedAliasEntry.getStagedResId() && getFinalizedResId() == stagedAliasEntry.getFinalizedResId();
    }

    public int getStagedResId() {
        return getInteger(0);
    }

    public void setStagedResId(int i) {
        putInteger(0, i);
    }

    public int getFinalizedResId() {
        return getInteger(4);
    }

    public void setFinalizedResId(int i) {
        putInteger(4, i);
    }

    @Override // com.reandroid.arsc.item.ByteArray
    public String toString() {
        return "stagedResId=" + HexUtil.toHex8(getStagedResId()) + ", finalizedResId=" + HexUtil.toHex8(getFinalizedResId());
    }

    @Override // com.reandroid.json.JSONConvert
    public JSONObject toJson() {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put(NAME_staged_resource_id, getStagedResId());
        jSONObject.put(NAME_finalized_resource_id, getFinalizedResId());
        return jSONObject;
    }

    @Override // com.reandroid.json.JSONConvert
    public void fromJson(JSONObject jSONObject) {
        setStagedResId(jSONObject.getInt(NAME_staged_resource_id));
        setFinalizedResId(jSONObject.getInt(NAME_finalized_resource_id));
    }
}

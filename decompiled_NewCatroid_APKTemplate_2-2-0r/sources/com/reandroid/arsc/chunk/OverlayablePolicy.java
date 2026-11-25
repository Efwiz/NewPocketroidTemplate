package com.reandroid.arsc.chunk;

import com.reandroid.arsc.base.Block;
import com.reandroid.arsc.header.OverlayablePolicyHeader;
import com.reandroid.arsc.io.BlockLoad;
import com.reandroid.arsc.io.BlockReader;
import com.reandroid.arsc.item.IntegerArrayBlock;
import com.reandroid.arsc.item.IntegerItem;
import com.reandroid.json.JSONArray;
import com.reandroid.json.JSONConvert;
import com.reandroid.json.JSONObject;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

/* JADX INFO: loaded from: classes10.dex */
public class OverlayablePolicy extends Chunk<OverlayablePolicyHeader> implements BlockLoad, JSONConvert<JSONObject> {
    public static final String NAME_flags = "flags";
    public static final String NAME_references = "references";
    private final IntegerArrayBlock tableRefArray;

    public OverlayablePolicy() {
        super(new OverlayablePolicyHeader(), 1);
        IntegerArrayBlock integerArrayBlock = new IntegerArrayBlock();
        this.tableRefArray = integerArrayBlock;
        addChild(integerArrayBlock);
        ((OverlayablePolicyHeader) getHeaderBlock()).getEntryCount().setBlockLoad(this);
    }

    @Override // com.reandroid.arsc.base.Block
    public boolean isNull() {
        return getTableReferenceCount() == 0;
    }

    public int getTableReferenceCount() {
        return getTableRefArray().size();
    }

    public Collection<Integer> listTableReferences() {
        return getTableRefArray().toList();
    }

    public IntegerArrayBlock getTableRefArray() {
        return this.tableRefArray;
    }

    public int getFlags() {
        return getHeaderBlock().getFlags().get();
    }

    public void setFlags(int i) {
        getHeaderBlock().getFlags().set(i);
    }

    public void setFlags(PolicyFlag[] policyFlagArr) {
        setFlags(PolicyFlag.sum(policyFlagArr));
    }

    public void addFlag(PolicyFlag policyFlag) {
        setFlags((policyFlag == null ? 0 : policyFlag.getFlagValue()) | getFlags());
    }

    public PolicyFlag[] getPolicyFlags() {
        return PolicyFlag.valuesOf(getFlags());
    }

    @Override // com.reandroid.arsc.chunk.Chunk
    protected void onChunkRefreshed() {
        getHeaderBlock().getEntryCount().set(getTableRefArray().size());
    }

    @Override // com.reandroid.arsc.io.BlockLoad
    public void onBlockLoaded(BlockReader blockReader, Block block) throws IOException {
        IntegerItem entryCount = getHeaderBlock().getEntryCount();
        if (block == entryCount) {
            this.tableRefArray.setSize(entryCount.get());
        }
    }

    @Override // com.reandroid.json.JSONConvert
    public JSONObject toJson() {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put(NAME_flags, getFlags());
        JSONArray jSONArray = new JSONArray();
        Iterator<Integer> it = listTableReferences().iterator();
        while (it.hasNext()) {
            jSONArray.put(it.next());
        }
        jSONObject.put(NAME_references, jSONArray);
        return jSONObject;
    }

    @Override // com.reandroid.json.JSONConvert
    public void fromJson(JSONObject jSONObject) {
        setFlags(jSONObject.getInt(NAME_flags));
        JSONArray jSONArray = jSONObject.getJSONArray(NAME_references);
        IntegerArrayBlock tableRefArray = getTableRefArray();
        int length = jSONArray.length();
        tableRefArray.setSize(length);
        for (int i = 0; i < length; i++) {
            tableRefArray.put(i, jSONArray.getInt(i));
        }
    }

    public void merge(OverlayablePolicy overlayablePolicy) {
        if (overlayablePolicy == null || overlayablePolicy == this) {
            return;
        }
        setFlags(overlayablePolicy.getFlags());
        IntegerArrayBlock tableRefArray = getTableRefArray();
        for (int i : overlayablePolicy.getTableRefArray().toArray()) {
            if (!tableRefArray.contains(i)) {
                tableRefArray.add(i);
            }
        }
    }

    @Override // com.reandroid.arsc.chunk.Chunk
    public String toString() {
        return getClass().getSimpleName() + ": flags=" + PolicyFlag.toString(getPolicyFlags()) + "', count=" + getTableReferenceCount();
    }

    public enum PolicyFlag {
        PUBLIC(1),
        SYSTEM_PARTITION(2),
        VENDOR_PARTITION(4),
        PRODUCT_PARTITION(8),
        SIGNATURE(16),
        ODM_PARTITION(32),
        OEM_PARTITION(64),
        ACTOR_SIGNATURE(128),
        CONFIG_SIGNATURE(256);

        private final int flag;

        PolicyFlag(int i) {
            this.flag = i;
        }

        public int getFlagValue() {
            return this.flag;
        }

        public boolean contains(int i) {
            int i2 = this.flag;
            return (i & i2) == i2;
        }

        public static PolicyFlag[] valuesOf(int i) {
            if (i == 0) {
                return null;
            }
            PolicyFlag[] policyFlagArrValues = values();
            int length = policyFlagArrValues.length;
            PolicyFlag[] policyFlagArr = new PolicyFlag[length];
            int i2 = 0;
            for (int i3 = 0; i3 < policyFlagArrValues.length; i3++) {
                PolicyFlag policyFlag = policyFlagArrValues[i3];
                if ((policyFlag.getFlagValue() & i) == policyFlag.getFlagValue()) {
                    policyFlagArr[i3] = policyFlag;
                    i2++;
                }
            }
            if (i2 == 0) {
                return null;
            }
            if (i2 == length) {
                return policyFlagArr;
            }
            PolicyFlag[] policyFlagArr2 = new PolicyFlag[i2];
            int i4 = 0;
            for (int i5 = 0; i5 < length; i5++) {
                PolicyFlag policyFlag2 = policyFlagArr[i5];
                if (policyFlag2 != null) {
                    policyFlagArr2[i4] = policyFlag2;
                    i4++;
                }
            }
            return policyFlagArr2;
        }

        public static int sum(PolicyFlag[] policyFlagArr) {
            if (policyFlagArr == null || policyFlagArr.length == 0) {
                return 0;
            }
            int flagValue = 0;
            for (PolicyFlag policyFlag : policyFlagArr) {
                if (policyFlag != null) {
                    flagValue |= policyFlag.getFlagValue();
                }
            }
            return flagValue;
        }

        public static boolean contains(PolicyFlag[] policyFlagArr, PolicyFlag policyFlag) {
            if (policyFlagArr == null || policyFlagArr.length == 0) {
                return policyFlag == null;
            }
            if (policyFlag == null) {
                return false;
            }
            for (PolicyFlag policyFlag2 : policyFlagArr) {
                if (policyFlag.equals(policyFlag2)) {
                    return true;
                }
            }
            return false;
        }

        public static String toString(PolicyFlag[] policyFlagArr) {
            if (policyFlagArr != null && policyFlagArr.length != 0) {
                StringBuilder sb = new StringBuilder();
                boolean z = false;
                for (PolicyFlag policyFlag : policyFlagArr) {
                    if (policyFlag != null) {
                        if (z) {
                            sb.append('|');
                        }
                        sb.append(policyFlag.name());
                        z = true;
                    }
                }
                if (z) {
                    return sb.toString();
                }
            }
            return "NONE";
        }

        public static PolicyFlag[] valuesOf(String str) {
            if (str == null) {
                return null;
            }
            String[] strArrSplit = str.trim().toUpperCase().split("\\s*\\|\\s*");
            int length = strArrSplit.length;
            PolicyFlag[] policyFlagArr = new PolicyFlag[length];
            int i = 0;
            for (int i2 = 0; i2 < strArrSplit.length; i2++) {
                PolicyFlag policyFlagNameOf = nameOf(strArrSplit[i2]);
                if (policyFlagNameOf != null) {
                    policyFlagArr[i2] = policyFlagNameOf;
                    i++;
                }
            }
            if (i == 0) {
                return null;
            }
            if (i == length) {
                return policyFlagArr;
            }
            PolicyFlag[] policyFlagArr2 = new PolicyFlag[i];
            int i3 = 0;
            for (int i4 = 0; i4 < length; i4++) {
                PolicyFlag policyFlag = policyFlagArr[i4];
                if (policyFlag != null) {
                    policyFlagArr2[i3] = policyFlag;
                    i3++;
                }
            }
            return policyFlagArr2;
        }

        public static PolicyFlag nameOf(String str) {
            if (str == null) {
                return null;
            }
            String upperCase = str.trim().toUpperCase();
            for (PolicyFlag policyFlag : values()) {
                if (upperCase.equals(policyFlag.name())) {
                    return policyFlag;
                }
            }
            return null;
        }
    }
}

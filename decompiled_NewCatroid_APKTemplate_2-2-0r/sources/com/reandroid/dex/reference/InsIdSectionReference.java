package com.reandroid.dex.reference;

import com.reandroid.dex.base.DexException;
import com.reandroid.dex.base.UsageMarker;
import com.reandroid.dex.data.CodeItem;
import com.reandroid.dex.data.InstructionList;
import com.reandroid.dex.data.MethodDef;
import com.reandroid.dex.id.IdItem;
import com.reandroid.dex.ins.Opcode;
import com.reandroid.dex.ins.SizeXIns;
import com.reandroid.dex.key.Key;
import com.reandroid.dex.key.TypeKey;
import com.reandroid.dex.sections.SectionType;
import com.reandroid.utils.HexUtil;
import java.util.Objects;

/* JADX INFO: loaded from: classes11.dex */
public class InsIdSectionReference implements IdReference<IdItem> {
    private IdItem item;
    private final SizeXIns sizeXIns;

    public InsIdSectionReference(SizeXIns sizeXIns) {
        this.sizeXIns = sizeXIns;
    }

    @Override // com.reandroid.dex.reference.DexReference
    public IdItem getItem() {
        return this.item;
    }

    @Override // com.reandroid.dex.reference.DexReference
    public void setItem(IdItem idItem) {
        IdItem idItemValidateReplace = validateReplace(idItem);
        this.item = idItemValidateReplace;
        set(idItemValidateReplace.getIdx());
        updateUsage();
    }

    @Override // com.reandroid.dex.reference.DexReference
    public Key getKey() {
        return getItem().getKey();
    }

    @Override // com.reandroid.dex.reference.DexReference
    public void setItem(Key key) {
        setItem((IdItem) this.sizeXIns.getOrCreateSection(getSectionType()).getOrCreate(key));
    }

    @Override // com.reandroid.dex.reference.DexReference
    public void pullItem() {
        setItem((IdItem) this.sizeXIns.getSectionItem(getSectionType(), get()));
    }

    @Override // com.reandroid.arsc.base.BlockRefresh
    public void refresh() {
        IdItem idItemValidateReplace = validateReplace(this.item);
        this.item = idItemValidateReplace;
        set(idItemValidateReplace.getIdx());
        updateUsage();
    }

    public void validate() {
        validateReplace(this.item);
    }

    private void updateUsage() {
        IdItem idItem = this.item;
        if (idItem != null) {
            idItem.addUsageType(UsageMarker.USAGE_INSTRUCTION);
        }
    }

    @Override // com.reandroid.arsc.item.IntegerReference
    public int get() {
        return this.sizeXIns.getData();
    }

    @Override // com.reandroid.arsc.item.IntegerReference
    public void set(int i) {
        this.sizeXIns.setData(i);
    }

    @Override // com.reandroid.dex.reference.DexReference
    public SectionType<IdItem> getSectionType() {
        return this.sizeXIns.getSectionType();
    }

    private IdItem validateReplace(IdItem idItem) {
        if (idItem == null) {
            throw new DexException("null id item: " + buildTrace(null));
        }
        IdItem idItem2 = (IdItem) idItem.getReplace();
        if (idItem2 == null) {
            throw new DexException("Invalid id item: " + buildTrace(null));
        }
        return validateType(idItem2);
    }

    private IdItem validateType(IdItem idItem) {
        Key key = idItem.getKey();
        if (key instanceof TypeKey) {
            TypeKey typeKey = (TypeKey) key;
            if (this.item != null && !typeKey.isTypeObject() && !this.sizeXIns.is(Opcode.CONST_CLASS)) {
                throw new DexException("Unexpected type '" + key + "', " + buildTrace(idItem));
            }
        }
        return idItem;
    }

    private String buildTrace(IdItem idItem) {
        SizeXIns sizeXIns = this.sizeXIns;
        InstructionList instructionList = sizeXIns.getInstructionList();
        if (instructionList == null) {
            return "removed instruction";
        }
        CodeItem codeItem = instructionList.getCodeItem();
        if (codeItem == null) {
            return "removed instruction list";
        }
        if (codeItem.getParent() == null) {
            return "removed code item";
        }
        MethodDef methodDef = codeItem.getMethodDef();
        StringBuilder sb = new StringBuilder();
        if (methodDef != null) {
            sb.append("method = ");
            sb.append(methodDef.getKey());
            sb.append(", ");
        }
        sb.append(sizeXIns.getOpcode());
        String debugString = toDebugString(idItem);
        if (debugString == null) {
            debugString = HexUtil.toHex(get(), 1);
        }
        sb.append(", key = '");
        sb.append(debugString);
        sb.append('\'');
        return sb.toString();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return Objects.equals(getItem(), ((InsIdSectionReference) obj).getItem());
    }

    public int hashCode() {
        IdItem item = getItem();
        if (item == null) {
            return 0;
        }
        return item.hashCode();
    }

    public String toString() {
        IdItem idItem = this.item;
        if (idItem == null) {
            return getSectionType().getName() + ": " + get();
        }
        return idItem.getKey().toString();
    }

    private static String toDebugString(IdItem idItem) {
        Key key;
        String string;
        if (idItem == null || (key = idItem.getKey()) == null || (string = key.toString()) == null) {
            return null;
        }
        if (string.length() > 100) {
            string = string.substring(0, 100) + "...";
        }
        if (string.startsWith("\"")) {
            string = string.substring(1);
        }
        return string.endsWith("\"") ? string.substring(0, string.length() - 1) : string;
    }
}

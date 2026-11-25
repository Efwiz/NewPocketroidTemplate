package com.reandroid.dex.ins;

import com.reandroid.dex.base.DexException;
import com.reandroid.dex.data.InstructionList;
import com.reandroid.dex.data.MethodDef;
import com.reandroid.dex.id.MethodId;
import com.reandroid.dex.sections.DexLayout;

/* JADX INFO: loaded from: classes11.dex */
public class InstructionException extends DexException {
    private final Ins ins;

    public InstructionException(String str, Ins ins) {
        super(str);
        this.ins = ins;
    }

    public Ins getIns() {
        return this.ins;
    }

    @Override // java.lang.Throwable
    public String getMessage() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.getMessage());
        sb.append(", ");
        appendDex(sb);
        if (!appendMethod(sb)) {
            sb.append(getIns());
        }
        return sb.toString();
    }

    private boolean appendMethod(StringBuilder sb) {
        MethodDef methodDef;
        MethodId id2;
        InstructionList instructionList = getIns().getInstructionList();
        if (instructionList == null || (methodDef = instructionList.getCodeItem().getMethodDef()) == null || (id2 = methodDef.getId()) == null) {
            return false;
        }
        sb.append("class = ");
        sb.append(id2.getDefining());
        sb.append(", method = ");
        sb.append(id2.getName());
        sb.append(id2.getProto());
        sb.append(" { ... ");
        sb.append(getIns());
        sb.append(" ... }");
        return true;
    }

    private void appendDex(StringBuilder sb) {
        String simpleName;
        DexLayout dexLayout = (DexLayout) getIns().getParentInstance(DexLayout.class);
        if (dexLayout == null || (simpleName = dexLayout.getSimpleName()) == null) {
            return;
        }
        sb.append("dex = ");
        sb.append(simpleName);
        sb.append(", ");
    }
}

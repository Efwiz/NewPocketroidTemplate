package org.luaj.vm2.luajc;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/* JADX INFO: loaded from: classes6.dex */
public class VarInfo {
    public static VarInfo INVALID = new VarInfo(-1, -1);
    public boolean allocupvalue;
    public boolean isreferenced;
    public final int pc;
    public final int slot;
    public UpvalInfo upvalue;

    private static final class NilVarInfo extends VarInfo {
        private NilVarInfo(int i, int i2) {
            super(i, i2);
        }

        @Override // org.luaj.vm2.luajc.VarInfo
        public String toString() {
            return "nil";
        }
    }

    private static final class ParamVarInfo extends VarInfo {
        private ParamVarInfo(int i, int i2) {
            super(i, i2);
        }

        @Override // org.luaj.vm2.luajc.VarInfo
        public String toString() {
            return new StringBuffer().append(this.slot).append(".p").toString();
        }
    }

    private static final class PhiVarInfo extends VarInfo {
        private final ProtoInfo pi;
        VarInfo[] values;

        private PhiVarInfo(ProtoInfo protoInfo, int i, int i2) {
            super(i, i2);
            this.pi = protoInfo;
        }

        @Override // org.luaj.vm2.luajc.VarInfo
        protected void collectUniqueValues(Set set, Set set2) {
            BasicBlock basicBlock = this.pi.blocks[this.pc];
            if (this.pc == 0) {
                set2.add(this.pi.params[this.slot]);
            }
            int length = basicBlock.prev != null ? basicBlock.prev.length : 0;
            for (int i = 0; i < length; i++) {
                BasicBlock basicBlock2 = basicBlock.prev[i];
                if (!set.contains(basicBlock2)) {
                    set.add(basicBlock2);
                    VarInfo varInfo = this.pi.vars[this.slot][basicBlock2.pc1];
                    if (varInfo != null) {
                        varInfo.collectUniqueValues(set, set2);
                    }
                }
            }
        }

        @Override // org.luaj.vm2.luajc.VarInfo
        public boolean isPhiVar() {
            return true;
        }

        @Override // org.luaj.vm2.luajc.VarInfo
        public VarInfo resolvePhiVariableValues() {
            HashSet hashSet = new HashSet();
            HashSet hashSet2 = new HashSet();
            collectUniqueValues(hashSet, hashSet2);
            if (hashSet2.contains(INVALID)) {
                return INVALID;
            }
            int size = hashSet2.size();
            Iterator it = hashSet2.iterator();
            if (size == 1) {
                VarInfo varInfo = (VarInfo) it.next();
                varInfo.isreferenced |= this.isreferenced;
                return varInfo;
            }
            this.values = new VarInfo[size];
            for (int i = 0; i < size; i++) {
                this.values[i] = (VarInfo) it.next();
                this.values[i].isreferenced |= this.isreferenced;
            }
            return null;
        }

        @Override // org.luaj.vm2.luajc.VarInfo
        public String toString() {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append(super.toString());
            stringBuffer.append("={");
            VarInfo[] varInfoArr = this.values;
            int length = varInfoArr != null ? varInfoArr.length : 0;
            for (int i = 0; i < length; i++) {
                if (i > 0) {
                    stringBuffer.append(",");
                }
                stringBuffer.append(String.valueOf(this.values[i]));
            }
            stringBuffer.append("}");
            return stringBuffer.toString();
        }
    }

    public VarInfo(int i, int i2) {
        this.slot = i;
        this.pc = i2;
    }

    public static VarInfo NIL(int i) {
        return new NilVarInfo(i, -1);
    }

    public static VarInfo PARAM(int i) {
        return new ParamVarInfo(i, -1);
    }

    public static VarInfo PHI(ProtoInfo protoInfo, int i, int i2) {
        return new PhiVarInfo(protoInfo, i, i2);
    }

    protected void collectUniqueValues(Set set, Set set2) {
        set2.add(this);
    }

    public boolean isPhiVar() {
        return false;
    }

    public VarInfo resolvePhiVariableValues() {
        return null;
    }

    public String toString() {
        return this.slot < 0 ? "x.x" : new StringBuffer().append(this.slot).append(".").append(this.pc).toString();
    }
}

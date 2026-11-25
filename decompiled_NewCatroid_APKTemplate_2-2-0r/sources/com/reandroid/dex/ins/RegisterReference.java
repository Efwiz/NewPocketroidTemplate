package com.reandroid.dex.ins;

import com.reandroid.dex.common.Register;
import com.reandroid.dex.common.RegisterType;
import com.reandroid.dex.common.RegistersTable;

/* JADX INFO: loaded from: classes11.dex */
public class RegisterReference extends Register {
    private final int index;
    private final RegistersSet registersSet;

    public RegisterReference(RegistersTable registersTable, RegistersSet registersSet, int i) {
        super(0, false, registersTable);
        this.registersSet = registersSet;
        this.index = i;
    }

    public Editor toEditor() {
        return new Editor(this);
    }

    @Override // com.reandroid.dex.common.Register
    public int getNumber() {
        int value = getValue();
        int localRegistersCount = getLocalRegistersCount();
        return value >= localRegistersCount ? value - localRegistersCount : value;
    }

    @Override // com.reandroid.dex.common.Register
    public boolean isParameter() {
        return getValue() >= getLocalRegistersCount();
    }

    @Override // com.reandroid.dex.common.Register
    public int getValue() {
        return getRegistersSet().getRegister(getIndex());
    }

    public void setRegisterValue(int i) {
        getRegistersSet().setRegister(getIndex(), i);
    }

    public int getIndex() {
        return this.index;
    }

    public int getLocalRegistersCount() {
        RegistersTable registersTable = getRegistersTable();
        return registersTable.getRegistersCount() - registersTable.getParameterRegistersCount();
    }

    public RegistersSet getRegistersSet() {
        return this.registersSet;
    }

    public RegisterType getRegisterType() {
        return getRegistersSet().getRegisterFormat().get(getIndex());
    }

    public int getLimit() {
        return getRegistersSet().getRegisterLimit(getIndex());
    }

    @Override // com.reandroid.dex.common.Register
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        RegisterReference registerReference = (RegisterReference) obj;
        return isParameter() == registerReference.isParameter() && getNumber() == registerReference.getNumber();
    }

    public static class Editor extends RegisterReference {
        private final RegisterReference mBaseRegisterReference;
        private final int number;
        private final boolean parameter;

        public Editor(RegisterReference registerReference) {
            super(null, null, registerReference.getIndex());
            this.mBaseRegisterReference = registerReference;
            this.number = registerReference.getNumber();
            this.parameter = registerReference.isParameter();
        }

        public void apply() {
            getBaseReg().setRegisterValue(getValue());
        }

        private boolean isChanged() {
            RegisterReference baseReg = getBaseReg();
            return isParameter() == baseReg.isParameter() && getNumber() == baseReg.getNumber() && getValue() == baseReg.getValue();
        }

        @Override // com.reandroid.dex.ins.RegisterReference, com.reandroid.dex.common.Register
        public int getValue() {
            int number = getNumber();
            return isParameter() ? number + getLocalRegistersCount() : number;
        }

        @Override // com.reandroid.dex.ins.RegisterReference, com.reandroid.dex.common.Register
        public int getNumber() {
            return this.number;
        }

        @Override // com.reandroid.dex.ins.RegisterReference, com.reandroid.dex.common.Register
        public boolean isParameter() {
            return this.parameter;
        }

        @Override // com.reandroid.dex.ins.RegisterReference
        public int getIndex() {
            return getBaseReg().getIndex();
        }

        @Override // com.reandroid.dex.common.Register
        public RegistersTable getRegistersTable() {
            return getBaseReg().getRegistersTable();
        }

        @Override // com.reandroid.dex.ins.RegisterReference
        public RegistersSet getRegistersSet() {
            return getBaseReg().getRegistersSet();
        }

        public RegisterReference getBaseReg() {
            return this.mBaseRegisterReference;
        }
    }
}

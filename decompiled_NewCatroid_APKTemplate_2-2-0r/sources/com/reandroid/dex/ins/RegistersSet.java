package com.reandroid.dex.ins;

import com.reandroid.dex.common.RegisterFormat;

/* JADX INFO: loaded from: classes11.dex */
public interface RegistersSet {
    int getRegister(int i);

    default RegisterFormat getRegisterFormat() {
        return null;
    }

    int getRegisterLimit(int i);

    int getRegistersCount();

    default boolean isWideRegisterAt(int i) {
        return false;
    }

    void setRegister(int i, int i2);

    void setRegistersCount(int i);

    default int getRegister() {
        return getRegister(0);
    }

    default void setRegister(int i) {
        setRegister(0, i);
    }

    default boolean removeRegisterAt(int i) {
        int registersCount;
        if (i < 0 || i >= (registersCount = getRegistersCount())) {
            return false;
        }
        int i2 = registersCount - 1;
        if (getRegisterFormat().isRange()) {
            if (i == 0) {
                setRegister(getRegister() + 1);
                return true;
            }
            if (i != i2) {
                return false;
            }
            setRegistersCount(i2);
            return true;
        }
        while (i < i2) {
            int i3 = i + 1;
            setRegister(i, getRegister(i3));
            i = i3;
        }
        setRegister(i2, 0);
        setRegistersCount(i2);
        return true;
    }
}

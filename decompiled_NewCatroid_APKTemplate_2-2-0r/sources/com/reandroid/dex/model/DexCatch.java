package com.reandroid.dex.model;

import com.reandroid.dex.ins.CatchAllHandler;
import com.reandroid.dex.ins.ExceptionHandler;
import com.reandroid.dex.key.Key;
import com.reandroid.dex.key.TypeKey;
import com.reandroid.dex.smali.SmaliWriter;
import java.io.IOException;

/* JADX INFO: loaded from: classes11.dex */
public class DexCatch extends DexCode {
    private final DexTry dexTry;
    private final ExceptionHandler exceptionHandler;

    public DexCatch(DexTry dexTry, ExceptionHandler exceptionHandler) {
        this.dexTry = dexTry;
        this.exceptionHandler = exceptionHandler;
    }

    public TypeKey getKey() {
        return getExceptionHandler().getKey();
    }

    public void setKey(TypeKey typeKey) {
        getExceptionHandler().setKey(typeKey);
    }

    public boolean isCatchAll() {
        return getExceptionHandler() instanceof CatchAllHandler;
    }

    public int getCatchAddress() {
        return getExceptionHandler().getCatchAddress();
    }

    public void setCatchAddress(int i) {
        getExceptionHandler().setCatchAddress(i);
    }

    public DexInstruction getCaughtInstruction() {
        return getDexMethod().getInstructionAt(getCatchAddress());
    }

    @Override // com.reandroid.dex.model.Dex
    public void removeSelf() {
        getExceptionHandler().removeSelf();
    }

    @Override // com.reandroid.dex.model.DexCode
    public DexMethod getDexMethod() {
        return getDexTry().getDexMethod();
    }

    public DexTry getDexTry() {
        return this.dexTry;
    }

    public ExceptionHandler getExceptionHandler() {
        return this.exceptionHandler;
    }

    @Override // com.reandroid.dex.model.Dex
    public boolean uses(Key key) {
        TypeKey key2 = getKey();
        if (key2 != null) {
            return key2.uses(key);
        }
        return false;
    }

    @Override // com.reandroid.dex.model.Dex, com.reandroid.dex.smali.SmaliFormat
    public void append(SmaliWriter smaliWriter) throws IOException {
        getExceptionHandler().getHandlerLabel().appendExtra(smaliWriter);
    }
}

package com.reandroid.dex.ins;

/* JADX INFO: loaded from: classes11.dex */
public interface ExceptionLabel extends Label {
    ExceptionHandler getHandler();

    @Override // com.reandroid.dex.ins.ExtraLine
    default boolean isRemoved() {
        return getHandler().isRemoved();
    }
}

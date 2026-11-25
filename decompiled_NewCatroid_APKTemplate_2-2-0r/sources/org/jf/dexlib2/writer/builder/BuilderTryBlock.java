package org.jf.dexlib2.writer.builder;

import java.util.List;
import org.jf.dexlib2.base.BaseTryBlock;

/* JADX INFO: loaded from: classes6.dex */
public class BuilderTryBlock extends BaseTryBlock<BuilderExceptionHandler> {
    private final int codeUnitCount;
    private final List<? extends BuilderExceptionHandler> exceptionHandlers;
    private final int startCodeAddress;

    public BuilderTryBlock(int i, int i2, List<? extends BuilderExceptionHandler> list) {
        this.startCodeAddress = i;
        this.codeUnitCount = i2;
        this.exceptionHandlers = list;
    }

    @Override // org.jf.dexlib2.iface.TryBlock
    public int getStartCodeAddress() {
        return this.startCodeAddress;
    }

    @Override // org.jf.dexlib2.iface.TryBlock
    public int getCodeUnitCount() {
        return this.codeUnitCount;
    }

    @Override // org.jf.dexlib2.iface.TryBlock
    public List<? extends BuilderExceptionHandler> getExceptionHandlers() {
        return this.exceptionHandlers;
    }
}

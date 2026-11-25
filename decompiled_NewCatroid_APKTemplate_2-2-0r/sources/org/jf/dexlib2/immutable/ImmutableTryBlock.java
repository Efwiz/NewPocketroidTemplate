package org.jf.dexlib2.immutable;

import java.util.List;
import org.jf.dexlib2.base.BaseTryBlock;
import org.jf.dexlib2.iface.ExceptionHandler;
import org.jf.dexlib2.iface.TryBlock;
import org.jf.util.ImmutableConverter;

/* JADX INFO: loaded from: classes6.dex */
public class ImmutableTryBlock extends BaseTryBlock<ImmutableExceptionHandler> {
    private static final ImmutableConverter<ImmutableTryBlock, TryBlock<? extends ExceptionHandler>> CONVERTER = new ImmutableConverter<ImmutableTryBlock, TryBlock<? extends ExceptionHandler>>() { // from class: org.jf.dexlib2.immutable.ImmutableTryBlock.1
        @Override // org.jf.util.ImmutableConverter
        protected /* bridge */ /* synthetic */ boolean isImmutable(TryBlock<? extends ExceptionHandler> tryBlock) {
            return isImmutable2((TryBlock) tryBlock);
        }

        /* JADX INFO: renamed from: isImmutable, reason: avoid collision after fix types in other method */
        protected boolean isImmutable2(TryBlock tryBlock) {
            return tryBlock instanceof ImmutableTryBlock;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // org.jf.util.ImmutableConverter
        public ImmutableTryBlock makeImmutable(TryBlock<? extends ExceptionHandler> tryBlock) {
            return ImmutableTryBlock.of(tryBlock);
        }
    };
    protected final int codeUnitCount;
    protected final List<? extends ImmutableExceptionHandler> exceptionHandlers;
    protected final int startCodeAddress;

    public ImmutableTryBlock(int i, int i2, List<? extends ExceptionHandler> list) {
        this.startCodeAddress = i;
        this.codeUnitCount = i2;
        this.exceptionHandlers = ImmutableExceptionHandler.immutableListOf(list);
    }

    public static ImmutableTryBlock of(TryBlock<? extends ExceptionHandler> tryBlock) {
        if (tryBlock instanceof ImmutableTryBlock) {
            return (ImmutableTryBlock) tryBlock;
        }
        return new ImmutableTryBlock(tryBlock.getStartCodeAddress(), tryBlock.getCodeUnitCount(), tryBlock.getExceptionHandlers());
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
    public List<? extends ImmutableExceptionHandler> getExceptionHandlers() {
        return this.exceptionHandlers;
    }

    public static List<ImmutableTryBlock> immutableListOf(List<? extends TryBlock<? extends ExceptionHandler>> list) {
        return CONVERTER.toList(list);
    }
}

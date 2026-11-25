package org.jf.dexlib2.immutable;

import java.util.List;
import org.jf.dexlib2.base.BaseExceptionHandler;
import org.jf.dexlib2.iface.ExceptionHandler;
import org.jf.util.ImmutableConverter;

/* JADX INFO: loaded from: classes6.dex */
public class ImmutableExceptionHandler extends BaseExceptionHandler implements ExceptionHandler {
    private static final ImmutableConverter<ImmutableExceptionHandler, ExceptionHandler> CONVERTER = new ImmutableConverter<ImmutableExceptionHandler, ExceptionHandler>() { // from class: org.jf.dexlib2.immutable.ImmutableExceptionHandler.1
        /* JADX INFO: Access modifiers changed from: protected */
        @Override // org.jf.util.ImmutableConverter
        public boolean isImmutable(ExceptionHandler exceptionHandler) {
            return exceptionHandler instanceof ImmutableExceptionHandler;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // org.jf.util.ImmutableConverter
        public ImmutableExceptionHandler makeImmutable(ExceptionHandler exceptionHandler) {
            return ImmutableExceptionHandler.of(exceptionHandler);
        }
    };
    protected final String exceptionType;
    protected final int handlerCodeAddress;

    public ImmutableExceptionHandler(String str, int i) {
        this.exceptionType = str;
        this.handlerCodeAddress = i;
    }

    public static ImmutableExceptionHandler of(ExceptionHandler exceptionHandler) {
        if (exceptionHandler instanceof ImmutableExceptionHandler) {
            return (ImmutableExceptionHandler) exceptionHandler;
        }
        return new ImmutableExceptionHandler(exceptionHandler.getExceptionType(), exceptionHandler.getHandlerCodeAddress());
    }

    @Override // org.jf.dexlib2.iface.ExceptionHandler
    public String getExceptionType() {
        return this.exceptionType;
    }

    @Override // org.jf.dexlib2.iface.ExceptionHandler
    public int getHandlerCodeAddress() {
        return this.handlerCodeAddress;
    }

    public static List<ImmutableExceptionHandler> immutableListOf(Iterable<? extends ExceptionHandler> iterable) {
        return CONVERTER.toList(iterable);
    }
}

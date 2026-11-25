package org.jf.dexlib2.dexbacked;

/* JADX INFO: loaded from: classes6.dex */
public class DexBackedCatchAllExceptionHandler extends DexBackedExceptionHandler {
    private final int handlerCodeAddress;

    @Override // org.jf.dexlib2.iface.ExceptionHandler
    public String getExceptionType() {
        return null;
    }

    public DexBackedCatchAllExceptionHandler(DexReader<?> dexReader) {
        this.handlerCodeAddress = dexReader.readSmallUleb128();
    }

    @Override // org.jf.dexlib2.iface.ExceptionHandler
    public int getHandlerCodeAddress() {
        return this.handlerCodeAddress;
    }
}

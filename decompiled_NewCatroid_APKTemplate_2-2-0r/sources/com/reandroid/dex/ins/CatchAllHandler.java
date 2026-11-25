package com.reandroid.dex.ins;

import com.reandroid.dex.base.Ule128Item;
import com.reandroid.dex.id.TypeId;
import com.reandroid.dex.smali.SmaliDirective;

/* JADX INFO: loaded from: classes11.dex */
public class CatchAllHandler extends ExceptionHandler {
    @Override // com.reandroid.dex.ins.ExceptionHandler
    TypeId getTypeId() {
        return null;
    }

    public CatchAllHandler() {
        super(0);
    }

    CatchAllHandler(boolean z) {
    }

    CatchAllHandler newCopy(TryItem tryItem) {
        Copy copy = new Copy(this);
        copy.setIndex(getIndex());
        copy.setParent(tryItem);
        return copy;
    }

    @Override // com.reandroid.dex.ins.ExceptionHandler, com.reandroid.dex.smali.SmaliRegion
    public SmaliDirective getSmaliDirective() {
        return SmaliDirective.CATCH_ALL;
    }

    static class Copy extends CatchAllHandler {
        private final CatchAllHandler catchAllHandler;

        Copy(CatchAllHandler catchAllHandler) {
            super(true);
            this.catchAllHandler = catchAllHandler;
        }

        @Override // com.reandroid.dex.ins.ExceptionHandler
        public boolean isRemoved() {
            return super.isRemoved() || this.catchAllHandler.isRemoved();
        }

        @Override // com.reandroid.dex.ins.ExceptionHandler
        Ule128Item getCatchAddressUle128() {
            return this.catchAllHandler.getCatchAddressUle128();
        }
    }
}

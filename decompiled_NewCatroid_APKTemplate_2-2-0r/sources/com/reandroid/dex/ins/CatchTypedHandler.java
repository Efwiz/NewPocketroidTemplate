package com.reandroid.dex.ins;

import com.reandroid.dex.base.Ule128Item;
import com.reandroid.dex.base.UsageMarker;
import com.reandroid.dex.id.IdItem;
import com.reandroid.dex.id.TypeId;
import com.reandroid.dex.key.TypeKey;
import com.reandroid.dex.reference.Ule128IdItemReference;
import com.reandroid.dex.sections.SectionType;
import com.reandroid.dex.smali.SmaliDirective;
import com.reandroid.dex.smali.model.SmaliCodeCatch;
import com.reandroid.dex.smali.model.SmaliCodeExceptionHandler;
import com.reandroid.utils.ObjectsUtil;

/* JADX INFO: loaded from: classes11.dex */
public class CatchTypedHandler extends ExceptionHandler {
    private final Ule128IdItemReference<TypeId> typeId;

    public CatchTypedHandler() {
        super(1);
        Ule128IdItemReference<TypeId> ule128IdItemReference = new Ule128IdItemReference<>(SectionType.TYPE_ID, UsageMarker.USAGE_INSTRUCTION);
        this.typeId = ule128IdItemReference;
        addChild(0, ule128IdItemReference);
    }

    CatchTypedHandler(boolean z) {
        this.typeId = null;
    }

    CatchTypedHandler newCopy(TryItem tryItem) {
        Copy copy = new Copy(this);
        copy.setIndex(getIndex());
        copy.setParent(tryItem);
        return copy;
    }

    @Override // com.reandroid.dex.ins.ExceptionHandler
    public TypeId getTypeId() {
        return (TypeId) getTypeUle128().getItem();
    }

    @Override // com.reandroid.dex.ins.ExceptionHandler
    public TypeKey getKey() {
        return (TypeKey) getTypeUle128().getKey();
    }

    @Override // com.reandroid.dex.ins.ExceptionHandler
    public void setKey(TypeKey typeKey) {
        getTypeUle128().setItem(typeKey);
    }

    Ule128IdItemReference<TypeId> getTypeUle128() {
        return this.typeId;
    }

    @Override // com.reandroid.dex.ins.ExceptionHandler, com.reandroid.dex.smali.SmaliRegion
    public SmaliDirective getSmaliDirective() {
        return SmaliDirective.CATCH;
    }

    @Override // com.reandroid.dex.ins.ExceptionHandler
    public void onRemove() {
        super.onRemove();
        this.typeId.setItem((IdItem) null);
    }

    @Override // com.reandroid.dex.ins.ExceptionHandler
    public void merge(ExceptionHandler exceptionHandler) {
        super.merge(exceptionHandler);
        this.typeId.setItem(((CatchTypedHandler) exceptionHandler).typeId.getKey());
    }

    @Override // com.reandroid.dex.ins.ExceptionHandler
    public void fromSmali(SmaliCodeExceptionHandler smaliCodeExceptionHandler) {
        this.typeId.setItem(((SmaliCodeCatch) smaliCodeExceptionHandler).getType());
        super.fromSmali(smaliCodeExceptionHandler);
    }

    @Override // com.reandroid.dex.ins.ExceptionHandler
    boolean isTypeEqual(ExceptionHandler exceptionHandler) {
        return ObjectsUtil.equals(getKey(), exceptionHandler.getKey());
    }

    @Override // com.reandroid.dex.ins.ExceptionHandler
    int getTypeHashCode() {
        TypeKey key = getKey();
        if (key != null) {
            return key.hashCode();
        }
        return 0;
    }

    static class Copy extends CatchTypedHandler {
        private final CatchTypedHandler catchTypedHandler;

        @Override // com.reandroid.dex.ins.CatchTypedHandler, com.reandroid.dex.ins.ExceptionHandler
        public void merge(ExceptionHandler exceptionHandler) {
        }

        Copy(CatchTypedHandler catchTypedHandler) {
            super(true);
            this.catchTypedHandler = catchTypedHandler;
        }

        @Override // com.reandroid.dex.ins.ExceptionHandler
        public boolean isRemoved() {
            return super.isRemoved() || this.catchTypedHandler.isRemoved();
        }

        @Override // com.reandroid.dex.ins.CatchTypedHandler
        Ule128IdItemReference<TypeId> getTypeUle128() {
            return this.catchTypedHandler.getTypeUle128();
        }

        @Override // com.reandroid.dex.ins.ExceptionHandler
        Ule128Item getCatchAddressUle128() {
            return this.catchTypedHandler.getCatchAddressUle128();
        }
    }
}

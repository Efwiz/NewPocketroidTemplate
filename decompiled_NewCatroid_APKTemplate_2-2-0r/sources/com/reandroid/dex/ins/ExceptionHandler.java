package com.reandroid.dex.ins;

import com.reandroid.dex.base.Ule128Item;
import com.reandroid.dex.data.FixedDexContainerWithTool;
import com.reandroid.dex.data.InstructionList;
import com.reandroid.dex.id.TypeId;
import com.reandroid.dex.key.TypeKey;
import com.reandroid.dex.smali.SmaliDirective;
import com.reandroid.dex.smali.SmaliRegion;
import com.reandroid.dex.smali.SmaliWriter;
import com.reandroid.dex.smali.model.SmaliCodeExceptionHandler;
import com.reandroid.utils.HexUtil;
import com.reandroid.utils.collection.ArrayIterator;
import com.reandroid.utils.collection.EmptyIterator;
import io.netty.util.internal.StringUtil;
import java.io.IOException;
import java.util.Iterator;

/* JADX INFO: loaded from: classes11.dex */
public abstract class ExceptionHandler extends FixedDexContainerWithTool implements SmaliRegion, Iterable<Label>, LabelsSet {
    private final Ule128Item catchAddress;
    private final ExceptionLabel catchLabel;
    private final ExceptionLabel endLabel;
    private final ExceptionLabel handlerLabel;
    private Label[] mLabels;
    private final ExceptionLabel startLabel;

    @Override // com.reandroid.dex.smali.SmaliFormat
    public void append(SmaliWriter smaliWriter) throws IOException {
    }

    public TypeKey getKey() {
        return null;
    }

    public abstract SmaliDirective getSmaliDirective();

    int getTypeHashCode() {
        return 0;
    }

    abstract TypeId getTypeId();

    boolean isTypeEqual(ExceptionHandler exceptionHandler) {
        return true;
    }

    public void setKey(TypeKey typeKey) {
    }

    private ExceptionHandler(int i, Ule128Item ule128Item, int i2) {
        super(i);
        this.catchAddress = ule128Item;
        if (ule128Item != null) {
            addChild(i2, ule128Item);
        }
        TryStartLabel tryStartLabel = new TryStartLabel(this);
        this.startLabel = tryStartLabel;
        TryEndLabel tryEndLabel = new TryEndLabel(this);
        this.endLabel = tryEndLabel;
        HandlerLabel handlerLabel = new HandlerLabel(this);
        this.handlerLabel = handlerLabel;
        CatchLabel catchLabel = new CatchLabel(this);
        this.catchLabel = catchLabel;
        this.mLabels = new Label[]{tryStartLabel, tryEndLabel, handlerLabel, catchLabel};
    }

    ExceptionHandler(int i) {
        this(i + 1, new Ule128Item(), i);
    }

    ExceptionHandler() {
        this(0, null, 0);
    }

    public Iterator<Ins> getTryInstructions() {
        InstructionList instructionList = getInstructionList();
        if (instructionList == null) {
            return EmptyIterator.of();
        }
        return instructionList.iteratorByAddress(getStartLabel().getTargetAddress(), getCodeUnit());
    }

    private InstructionList getInstructionList() {
        TryItem tryItem = getTryItem();
        if (tryItem != null) {
            return tryItem.getInstructionList();
        }
        return null;
    }

    Ule128Item getCatchAddressUle128() {
        return this.catchAddress;
    }

    @Override // com.reandroid.dex.ins.LabelsSet
    public Iterator<Label> getLabels() {
        return iterator();
    }

    @Override // java.lang.Iterable
    public Iterator<Label> iterator() {
        return ArrayIterator.of(this.mLabels);
    }

    public ExceptionLabel getHandlerLabel() {
        return this.handlerLabel;
    }

    public ExceptionLabel getStartLabel() {
        return this.startLabel;
    }

    public ExceptionLabel getEndLabel() {
        return this.endLabel;
    }

    public ExceptionLabel getCatchLabel() {
        return this.catchLabel;
    }

    public void refreshAddresses() {
        Ins targetIns = getHandlerLabel().getTargetIns();
        Ins targetIns2 = getStartLabel().getTargetIns();
        Ins targetIns3 = getEndLabel().getTargetIns();
        Ins targetIns4 = getCatchLabel().getTargetIns();
        if (targetIns == null || targetIns2 == null || targetIns3 == null || targetIns4 == null) {
            return;
        }
        int address = targetIns.getAddress();
        int address2 = targetIns2.getAddress();
        targetIns3.getAddress();
        int address3 = targetIns4.getAddress();
        setStartAddress(targetIns2.getAddress());
        setCatchAddress(address3);
        setCodeUnit(address - address2);
    }

    public int getCatchAddress() {
        return getCatchAddressUle128().get();
    }

    public void setCatchAddress(int i) {
        getCatchAddressUle128().set(i);
    }

    public int getAddress() {
        return getStartAddress() + getCodeUnit();
    }

    public void setAddress(int i) {
        setCodeUnit(i - getStartAddress());
    }

    public int getStartAddress() {
        TryItem tryItem = getTryItem();
        if (tryItem != null) {
            return tryItem.getStartAddress();
        }
        return 0;
    }

    public void setStartAddress(int i) {
        TryItem tryItem = getTryItem();
        if (tryItem != null) {
            tryItem.setStartAddress(i);
        }
    }

    public int getCodeUnit() {
        TryItem tryItem = getTryItem();
        if (tryItem != null) {
            return tryItem.getCatchCodeUnit();
        }
        return 0;
    }

    public void setCodeUnit(int i) {
        TryItem tryItem = getTryItem();
        if (tryItem != null) {
            tryItem.getHandlerOffset().setCatchCodeUnit(i);
        }
    }

    TryItem getTryItem() {
        return (TryItem) getParentInstance(TryItem.class);
    }

    public void onRemove() {
        this.mLabels = null;
        setParent(null);
    }

    public void removeSelf() {
        TryItem tryItem = getTryItem();
        if (tryItem != null) {
            tryItem.remove(this);
        }
    }

    public boolean isRemoved() {
        return getParent() == null;
    }

    public void merge(ExceptionHandler exceptionHandler) {
        this.catchAddress.set(exceptionHandler.catchAddress.get());
    }

    public void fromSmali(SmaliCodeExceptionHandler smaliCodeExceptionHandler) {
        getHandlerLabel().setTargetAddress(smaliCodeExceptionHandler.getAddress());
        getStartLabel().setTargetAddress(smaliCodeExceptionHandler.getStart().getAddress());
        getEndLabel().setTargetAddress(smaliCodeExceptionHandler.getEnd().getAddress());
        getCatchLabel().setTargetAddress(smaliCodeExceptionHandler.getCatchLabel().getAddress());
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        ExceptionHandler exceptionHandler = (ExceptionHandler) obj;
        return getStartAddress() == exceptionHandler.getStartAddress() && getAddress() == exceptionHandler.getAddress() && getCatchAddress() == exceptionHandler.getCatchAddress() && isTypeEqual(exceptionHandler);
    }

    public int hashCode() {
        return ((((((getStartAddress() + 31) * 31) + getAddress()) * 31) + getCatchAddress()) * 31) + getTypeHashCode();
    }

    public String toString() {
        return getHandlerLabel().toString();
    }

    static abstract class AbstractExceptionLabel implements ExceptionLabel {
        private final ExceptionHandler handler;
        private Ins targetIns;

        @Override // com.reandroid.dex.ins.ExtraLine
        public void updateTarget() {
        }

        AbstractExceptionLabel(ExceptionHandler exceptionHandler) {
            this.handler = exceptionHandler;
        }

        @Override // com.reandroid.dex.ins.ExceptionLabel
        public ExceptionHandler getHandler() {
            return this.handler;
        }

        @Override // com.reandroid.dex.ins.ExtraLine
        public Ins getTargetIns() {
            Ins ins = this.targetIns;
            if (ins == null || !ins.isRemoved()) {
                return ins;
            }
            this.targetIns = null;
            return null;
        }

        @Override // com.reandroid.dex.ins.ExtraLine
        public void setTargetIns(Ins ins) {
            if (ins != this.targetIns) {
                this.targetIns = ins;
                if (ins != null) {
                    ins.addExtraLine(this);
                }
            }
        }
    }

    public static class HandlerLabel extends AbstractExceptionLabel {
        @Override // com.reandroid.dex.ins.ExtraLine
        public int getSortOrder() {
            return 3;
        }

        @Override // com.reandroid.dex.ins.ExceptionHandler.AbstractExceptionLabel, com.reandroid.dex.ins.ExceptionLabel
        public /* bridge */ /* synthetic */ ExceptionHandler getHandler() {
            return super.getHandler();
        }

        @Override // com.reandroid.dex.ins.ExceptionHandler.AbstractExceptionLabel, com.reandroid.dex.ins.ExtraLine
        public /* bridge */ /* synthetic */ Ins getTargetIns() {
            return super.getTargetIns();
        }

        @Override // com.reandroid.dex.ins.ExceptionHandler.AbstractExceptionLabel, com.reandroid.dex.ins.ExtraLine
        public /* bridge */ /* synthetic */ void setTargetIns(Ins ins) {
            super.setTargetIns(ins);
        }

        HandlerLabel(ExceptionHandler exceptionHandler) {
            super(exceptionHandler);
        }

        @Override // com.reandroid.dex.ins.Label
        public int getAddress() {
            return getHandler().getAddress();
        }

        @Override // com.reandroid.dex.ins.ExtraLine
        public int getTargetAddress() {
            return getHandler().getAddress();
        }

        @Override // com.reandroid.dex.ins.ExtraLine
        public void setTargetAddress(int i) {
            getHandler().setAddress(i);
        }

        @Override // com.reandroid.dex.ins.ExceptionHandler.AbstractExceptionLabel, com.reandroid.dex.ins.ExtraLine
        public void updateTarget() {
            getHandler().refreshAddresses();
        }

        @Override // com.reandroid.dex.ins.Label
        public String getLabelName() {
            ExceptionHandler handler = getHandler();
            StringBuilder sb = new StringBuilder(".");
            sb.append(handler.getSmaliDirective().getName());
            sb.append(StringUtil.SPACE);
            TypeId typeId = handler.getTypeId();
            if (typeId != null) {
                sb.append(typeId.getName());
                sb.append(StringUtil.SPACE);
            }
            sb.append("{");
            sb.append(handler.getStartLabel().getLabelName());
            sb.append(" .. ");
            sb.append(handler.getEndLabel().getLabelName());
            sb.append("} ");
            sb.append(handler.getCatchLabel().getLabelName());
            return sb.toString();
        }

        @Override // com.reandroid.dex.ins.Label, com.reandroid.dex.ins.ExtraLine
        public boolean isEqualExtraLine(Object obj) {
            if (obj == this) {
                return true;
            }
            return obj != null && getClass() == obj.getClass() && getHandler() == ((HandlerLabel) obj).getHandler();
        }

        @Override // com.reandroid.dex.ins.Label, com.reandroid.dex.ins.ExtraLine
        public void appendExtra(SmaliWriter smaliWriter) throws IOException {
            ExceptionHandler handler = getHandler();
            handler.getSmaliDirective().append(smaliWriter);
            TypeId typeId = handler.getTypeId();
            if (typeId != null) {
                typeId.append(smaliWriter);
                smaliWriter.append(StringUtil.SPACE);
            }
            smaliWriter.append("{");
            smaliWriter.appendLabelName(handler.getStartLabel().getLabelName());
            smaliWriter.append(" .. ");
            smaliWriter.appendLabelName(handler.getEndLabel().getLabelName());
            smaliWriter.append("} ");
            smaliWriter.appendLabelName(handler.getCatchLabel().getLabelName());
        }

        public String toString() {
            return getLabelName();
        }
    }

    public static class TryStartLabel extends AbstractExceptionLabel {
        @Override // com.reandroid.dex.ins.ExtraLine
        public int getSortOrder() {
            return 6;
        }

        @Override // com.reandroid.dex.ins.ExceptionHandler.AbstractExceptionLabel, com.reandroid.dex.ins.ExceptionLabel
        public /* bridge */ /* synthetic */ ExceptionHandler getHandler() {
            return super.getHandler();
        }

        @Override // com.reandroid.dex.ins.ExceptionHandler.AbstractExceptionLabel, com.reandroid.dex.ins.ExtraLine
        public /* bridge */ /* synthetic */ Ins getTargetIns() {
            return super.getTargetIns();
        }

        @Override // com.reandroid.dex.ins.ExceptionHandler.AbstractExceptionLabel, com.reandroid.dex.ins.ExtraLine
        public /* bridge */ /* synthetic */ void setTargetIns(Ins ins) {
            super.setTargetIns(ins);
        }

        @Override // com.reandroid.dex.ins.ExceptionHandler.AbstractExceptionLabel, com.reandroid.dex.ins.ExtraLine
        public /* bridge */ /* synthetic */ void updateTarget() {
            super.updateTarget();
        }

        TryStartLabel(ExceptionHandler exceptionHandler) {
            super(exceptionHandler);
        }

        @Override // com.reandroid.dex.ins.Label
        public int getAddress() {
            return getHandler().getAddress();
        }

        @Override // com.reandroid.dex.ins.ExtraLine
        public int getTargetAddress() {
            return getHandler().getStartAddress();
        }

        @Override // com.reandroid.dex.ins.ExtraLine
        public void setTargetAddress(int i) {
            getHandler().setStartAddress(i);
        }

        @Override // com.reandroid.dex.ins.Label
        public String getLabelName() {
            return HexUtil.toHex(":try_start_", getTargetAddress(), 1);
        }

        @Override // com.reandroid.dex.ins.Label, com.reandroid.dex.ins.ExtraLine
        public boolean isEqualExtraLine(Object obj) {
            if (obj == this) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            TryStartLabel tryStartLabel = (TryStartLabel) obj;
            return getHandler() == tryStartLabel.getHandler() || getTargetAddress() == tryStartLabel.getTargetAddress();
        }

        public String toString() {
            return getLabelName();
        }
    }

    public static class TryEndLabel extends AbstractExceptionLabel {
        @Override // com.reandroid.dex.ins.ExtraLine
        public int getSortOrder() {
            return 2;
        }

        @Override // com.reandroid.dex.ins.ExceptionHandler.AbstractExceptionLabel, com.reandroid.dex.ins.ExceptionLabel
        public /* bridge */ /* synthetic */ ExceptionHandler getHandler() {
            return super.getHandler();
        }

        @Override // com.reandroid.dex.ins.ExceptionHandler.AbstractExceptionLabel, com.reandroid.dex.ins.ExtraLine
        public /* bridge */ /* synthetic */ Ins getTargetIns() {
            return super.getTargetIns();
        }

        @Override // com.reandroid.dex.ins.ExceptionHandler.AbstractExceptionLabel, com.reandroid.dex.ins.ExtraLine
        public /* bridge */ /* synthetic */ void setTargetIns(Ins ins) {
            super.setTargetIns(ins);
        }

        @Override // com.reandroid.dex.ins.ExceptionHandler.AbstractExceptionLabel, com.reandroid.dex.ins.ExtraLine
        public /* bridge */ /* synthetic */ void updateTarget() {
            super.updateTarget();
        }

        TryEndLabel(ExceptionHandler exceptionHandler) {
            super(exceptionHandler);
        }

        @Override // com.reandroid.dex.ins.Label
        public int getAddress() {
            return getHandler().getAddress();
        }

        @Override // com.reandroid.dex.ins.ExtraLine
        public int getTargetAddress() {
            return getHandler().getAddress();
        }

        @Override // com.reandroid.dex.ins.ExtraLine
        public void setTargetAddress(int i) {
            getHandler().setAddress(i);
        }

        @Override // com.reandroid.dex.ins.Label
        public String getLabelName() {
            return HexUtil.toHex(":try_end_", getHandler().getStartLabel().getTargetAddress(), 1);
        }

        @Override // com.reandroid.dex.ins.Label, com.reandroid.dex.ins.ExtraLine
        public boolean isEqualExtraLine(Object obj) {
            if (obj == this) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            TryEndLabel tryEndLabel = (TryEndLabel) obj;
            return getHandler() == tryEndLabel.getHandler() || getTargetAddress() == tryEndLabel.getTargetAddress();
        }

        public String toString() {
            return getLabelName();
        }
    }

    public static class CatchLabel extends AbstractExceptionLabel {
        @Override // com.reandroid.dex.ins.ExtraLine
        public int getSortOrder() {
            return 4;
        }

        @Override // com.reandroid.dex.ins.ExceptionHandler.AbstractExceptionLabel, com.reandroid.dex.ins.ExceptionLabel
        public /* bridge */ /* synthetic */ ExceptionHandler getHandler() {
            return super.getHandler();
        }

        @Override // com.reandroid.dex.ins.ExceptionHandler.AbstractExceptionLabel, com.reandroid.dex.ins.ExtraLine
        public /* bridge */ /* synthetic */ Ins getTargetIns() {
            return super.getTargetIns();
        }

        @Override // com.reandroid.dex.ins.ExceptionHandler.AbstractExceptionLabel, com.reandroid.dex.ins.ExtraLine
        public /* bridge */ /* synthetic */ void setTargetIns(Ins ins) {
            super.setTargetIns(ins);
        }

        @Override // com.reandroid.dex.ins.ExceptionHandler.AbstractExceptionLabel, com.reandroid.dex.ins.ExtraLine
        public /* bridge */ /* synthetic */ void updateTarget() {
            super.updateTarget();
        }

        CatchLabel(ExceptionHandler exceptionHandler) {
            super(exceptionHandler);
        }

        @Override // com.reandroid.dex.ins.Label
        public int getAddress() {
            return getHandler().getStartLabel().getAddress();
        }

        @Override // com.reandroid.dex.ins.ExtraLine
        public int getTargetAddress() {
            return getHandler().getCatchAddress();
        }

        @Override // com.reandroid.dex.ins.ExtraLine
        public void setTargetAddress(int i) {
            getHandler().setCatchAddress(i);
        }

        @Override // com.reandroid.dex.ins.Label
        public String getLabelName() {
            return HexUtil.toHex(":" + getHandler().getSmaliDirective().getName() + "_", getTargetAddress(), 1);
        }

        @Override // com.reandroid.dex.ins.Label, com.reandroid.dex.ins.ExtraLine
        public boolean isEqualExtraLine(Object obj) {
            if (obj == this) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            CatchLabel catchLabel = (CatchLabel) obj;
            if (getHandler() == catchLabel.getHandler()) {
                return true;
            }
            return getTargetAddress() == catchLabel.getTargetAddress() && getHandler().getSmaliDirective().equals(catchLabel.getHandler().getSmaliDirective());
        }

        public String toString() {
            return getLabelName();
        }
    }
}

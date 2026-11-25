package com.reandroid.dex.model;

import com.reandroid.dex.base.DexException;
import com.reandroid.dex.data.InstructionList;
import com.reandroid.dex.ins.ExceptionHandler;
import com.reandroid.dex.ins.ExceptionLabel;
import com.reandroid.dex.ins.Ins;
import com.reandroid.dex.ins.TryItem;
import com.reandroid.dex.key.Key;
import com.reandroid.dex.key.TypeKey;
import com.reandroid.dex.smali.SmaliWriter;
import com.reandroid.utils.collection.CollectionUtil;
import com.reandroid.utils.collection.ComputeIterator;
import com.reandroid.utils.collection.EmptyIterator;
import com.reandroid.utils.collection.FilterIterator;
import java.io.IOException;
import java.util.Iterator;
import java.util.function.Function;
import java.util.function.Predicate;

/* JADX INFO: loaded from: classes11.dex */
public class DexTry extends DexCode {
    private final DexMethod dexMethod;
    private final TryItem tryItem;

    public DexTry(DexMethod dexMethod, TryItem tryItem) {
        this.dexMethod = dexMethod;
        this.tryItem = tryItem;
    }

    public DexInstruction getFirst() {
        return (DexInstruction) CollectionUtil.getFirst(getInstructions());
    }

    public void setFirst(DexInstruction dexInstruction) {
        setStartAddress(dexInstruction.getAddress());
    }

    public DexInstruction getLast() {
        return (DexInstruction) CollectionUtil.getLast(getInstructions());
    }

    public void setLast(DexInstruction dexInstruction) {
        setEndAddress(dexInstruction.getAddress() + dexInstruction.getCodeUnits());
    }

    public Iterator<DexInstruction> getInstructions() {
        InstructionList instructionList = getDexMethod().getDefinition().getInstructionList();
        if (instructionList == null) {
            return EmptyIterator.of();
        }
        return DexInstruction.create(getDexMethod(), (Iterator<Ins>) FilterIterator.of(instructionList.iterator(), new Predicate() { // from class: com.reandroid.dex.model.DexTry$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return this.f$0.m2774lambda$getInstructions$0$comreandroiddexmodelDexTry((Ins) obj);
            }
        }));
    }

    /* JADX INFO: renamed from: lambda$getInstructions$0$com-reandroid-dex-model-DexTry, reason: not valid java name */
    /* synthetic */ boolean m2774lambda$getInstructions$0$comreandroiddexmodelDexTry(Ins ins) {
        int address = ins.getAddress();
        return address >= getStartAddress() && address < getEndAddress();
    }

    public int getStartAddress() {
        return getTryItem().getStartAddress();
    }

    public void setStartAddress(int i) {
        getTryItem().setStartAddress(i);
    }

    public int getEndAddress() {
        TryItem tryItem = getTryItem();
        return tryItem.getStartAddress() + tryItem.getCatchCodeUnit();
    }

    public void setEndAddress(int i) {
        int startAddress = getTryItem().getStartAddress();
        if (i < startAddress) {
            throw new DexException("Invalid try end address " + i + "<" + startAddress);
        }
        getTryItem().setCatchCodeUnit(i - startAddress);
    }

    public DexCatch getCatchAll() {
        return create(getTryItem().getCatchAllHandler());
    }

    public DexCatch getOrCreateCatchAll() {
        boolean zHasCatchAllHandler = getTryItem().hasCatchAllHandler();
        DexCatch dexCatchCreate = create(getTryItem().getOrCreateCatchAll());
        if (!zHasCatchAllHandler) {
            dexCatchCreate.setCatchAddress(getEndAddress());
        }
        return dexCatchCreate;
    }

    public DexCatch getCatch(TypeKey typeKey) {
        return create(getTryItem().getExceptionHandler(typeKey));
    }

    public int getCatchCount() {
        TryItem tryItem = getTryItem();
        boolean zHasCatchAllHandler = tryItem.hasCatchAllHandler();
        return (zHasCatchAllHandler ? 1 : 0) + tryItem.getCatchTypedHandlersCount();
    }

    public Iterator<DexCatch> getCatches() {
        return ComputeIterator.of(getTryItem().getExceptionHandlers(), new Function() { // from class: com.reandroid.dex.model.DexTry$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.create((ExceptionHandler) obj);
            }
        });
    }

    DexCatch create(ExceptionHandler exceptionHandler) {
        if (exceptionHandler != null) {
            return new DexCatch(this, exceptionHandler);
        }
        return null;
    }

    @Override // com.reandroid.dex.model.Dex
    public void removeSelf() {
        getTryItem().removeSelf();
    }

    public TryItem getTryItem() {
        return this.tryItem;
    }

    @Override // com.reandroid.dex.model.Dex
    public boolean uses(Key key) {
        Iterator<DexCatch> catches = getCatches();
        while (catches.hasNext()) {
            if (catches.next().uses(key)) {
                return true;
            }
        }
        return false;
    }

    @Override // com.reandroid.dex.model.DexCode
    public DexMethod getDexMethod() {
        return this.dexMethod;
    }

    @Override // com.reandroid.dex.model.Dex, com.reandroid.dex.smali.SmaliFormat
    public void append(SmaliWriter smaliWriter) throws IOException {
        TryItem tryItem = getTryItem();
        Iterator<ExceptionHandler> exceptionHandlers = tryItem.getExceptionHandlers();
        if (!exceptionHandlers.hasNext()) {
            smaliWriter.appendComment("Empty try-catch");
            smaliWriter.newLine();
            return;
        }
        Object obj = null;
        Object obj2 = null;
        while (exceptionHandlers.hasNext()) {
            ExceptionLabel startLabel = exceptionHandlers.next().getStartLabel();
            if (!startLabel.isEqualExtraLine(obj2)) {
                smaliWriter.newLine();
                startLabel.appendExtra(smaliWriter);
                obj2 = startLabel;
            }
        }
        smaliWriter.indentPlus();
        smaliWriter.appendAll(getInstructions(), true);
        smaliWriter.indentMinus();
        Iterator<ExceptionHandler> exceptionHandlers2 = tryItem.getExceptionHandlers();
        while (exceptionHandlers2.hasNext()) {
            ExceptionLabel endLabel = exceptionHandlers2.next().getEndLabel();
            if (!endLabel.isEqualExtraLine(obj)) {
                smaliWriter.newLine();
                endLabel.appendExtra(smaliWriter);
                obj = endLabel;
            }
        }
        Iterator<ExceptionHandler> exceptionHandlers3 = tryItem.getExceptionHandlers();
        while (exceptionHandlers3.hasNext()) {
            ExceptionLabel handlerLabel = exceptionHandlers3.next().getHandlerLabel();
            smaliWriter.newLine();
            handlerLabel.appendExtra(smaliWriter);
        }
    }

    public static Iterator<DexTry> create(final DexMethod dexMethod, Iterator<TryItem> it) {
        if (dexMethod == null) {
            return EmptyIterator.of();
        }
        return ComputeIterator.of(it, new Function() { // from class: com.reandroid.dex.model.DexTry$$ExternalSyntheticLambda2
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return DexTry.create(dexMethod, (TryItem) obj);
            }
        });
    }

    public static DexTry create(DexMethod dexMethod, TryItem tryItem) {
        if (dexMethod == null || tryItem == null) {
            return null;
        }
        return new DexTry(dexMethod, tryItem);
    }
}

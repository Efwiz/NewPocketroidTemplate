package com.reandroid.graph;

import com.reandroid.arsc.chunk.PackageBlock;
import com.reandroid.arsc.chunk.TableBlock;
import com.reandroid.dex.ins.Opcode;
import com.reandroid.dex.key.FieldKey;
import com.reandroid.dex.key.Key;
import com.reandroid.dex.key.TypeKey;
import com.reandroid.dex.model.DexClass;
import com.reandroid.dex.model.DexClassRepository;
import com.reandroid.dex.model.DexField;
import com.reandroid.dex.model.DexInstruction;
import com.reandroid.dex.model.DexMethod;
import com.reandroid.dex.model.DexValue;
import java.util.Iterator;
import java.util.function.Predicate;

/* JADX INFO: loaded from: classes11.dex */
public class InlineFieldIntResolver extends BaseDexClassProcessor {
    private int mResolvedCount;
    private final Predicate<Integer> resourceIdChecker;

    public InlineFieldIntResolver(DexClassRepository dexClassRepository, Predicate<Integer> predicate) {
        super(dexClassRepository);
        this.resourceIdChecker = predicate;
    }

    public InlineFieldIntResolver(DexClassRepository dexClassRepository, TableBlock tableBlock) {
        this(dexClassRepository, createChecker(tableBlock));
    }

    public InlineFieldIntResolver(DexClassRepository dexClassRepository) {
        this(dexClassRepository, createDefaultChecker());
    }

    @Override // com.reandroid.graph.GraphTask
    public void apply() {
        verbose("Resolving inline resource ids ..");
        reset();
        Iterator<DexClass> dexClasses = getClassRepository().getDexClasses();
        while (dexClasses.hasNext()) {
            scanClass(dexClasses.next());
        }
        verbose("Resolved ids count: " + getResolvedCount());
    }

    public int getResolvedCount() {
        return this.mResolvedCount;
    }

    private void scanClass(DexClass dexClass) {
        Iterator<DexMethod> declaredMethods = dexClass.getDeclaredMethods();
        while (declaredMethods.hasNext()) {
            scanMethod(declaredMethods.next());
        }
    }

    private void scanMethod(DexMethod dexMethod) {
        Iterator<DexInstruction> instructions = dexMethod.getInstructions();
        while (instructions.hasNext()) {
            DexInstruction next = instructions.next();
            DexValue valueFromStaticField = getValueFromStaticField(next);
            if (valueFromStaticField != null) {
                resolve(next, valueFromStaticField);
            }
        }
    }

    private DexValue getValueFromStaticField(DexInstruction dexInstruction) {
        DexField dexField;
        if (!dexInstruction.is(Opcode.SGET)) {
            return null;
        }
        if (TypeKey.TYPE_I.equals(((FieldKey) dexInstruction.getKey()).getType()) && (dexField = (DexField) dexInstruction.findDeclaration()) != null) {
            return dexField.getInitialValue();
        }
        return null;
    }

    private void resolve(DexInstruction dexInstruction, DexValue dexValue) {
        Integer integer = dexValue.getInteger();
        if (integer == null || !this.resourceIdChecker.test(integer)) {
            return;
        }
        Key key = dexInstruction.getKey();
        int register = dexInstruction.getRegister();
        DexInstruction dexInstructionReplace = dexInstruction.replace(Opcode.CONST);
        dexInstructionReplace.setRegister(register);
        dexInstructionReplace.setAsInteger(integer.intValue());
        this.mResolvedCount++;
        if (isDebugEnabled()) {
            debug(key + " WITH " + dexInstructionReplace.toString());
        }
    }

    private void reset() {
        this.mResolvedCount = 0;
    }

    private static Predicate<Integer> createChecker(final TableBlock tableBlock) {
        return new Predicate() { // from class: com.reandroid.graph.InlineFieldIntResolver$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return InlineFieldIntResolver.lambda$createChecker$0(tableBlock, (Integer) obj);
            }
        };
    }

    static /* synthetic */ boolean lambda$createChecker$0(TableBlock tableBlock, Integer num) {
        int iIntValue = num.intValue();
        return PackageBlock.isResourceId(iIntValue) && tableBlock.getResource(iIntValue) != null;
    }

    private static Predicate<Integer> createDefaultChecker() {
        return new Predicate() { // from class: com.reandroid.graph.InlineFieldIntResolver$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return PackageBlock.isResourceId(((Integer) obj).intValue());
            }
        };
    }
}

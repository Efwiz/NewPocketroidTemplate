package com.reandroid.dex.model;

import com.reandroid.arsc.base.BlockRefresh;
import com.reandroid.arsc.item.IntegerReference;
import com.reandroid.dex.common.Register;
import com.reandroid.dex.common.RegisterFormat;
import com.reandroid.dex.common.RegisterType;
import com.reandroid.dex.data.InstructionList;
import com.reandroid.dex.id.FieldId;
import com.reandroid.dex.id.IdItem;
import com.reandroid.dex.id.MethodId;
import com.reandroid.dex.id.StringId;
import com.reandroid.dex.ins.ConstNumber;
import com.reandroid.dex.ins.ConstNumberLong;
import com.reandroid.dex.ins.ConstString;
import com.reandroid.dex.ins.Ins;
import com.reandroid.dex.ins.InsConstStringJumbo;
import com.reandroid.dex.ins.Label;
import com.reandroid.dex.ins.Opcode;
import com.reandroid.dex.ins.RegistersSet;
import com.reandroid.dex.ins.SizeXIns;
import com.reandroid.dex.key.FieldKey;
import com.reandroid.dex.key.Key;
import com.reandroid.dex.key.MethodKey;
import com.reandroid.dex.key.StringKey;
import com.reandroid.dex.sections.SectionType;
import com.reandroid.dex.smali.SmaliReader;
import com.reandroid.dex.smali.SmaliWriter;
import com.reandroid.dex.smali.model.SmaliInstruction;
import com.reandroid.utils.collection.CollectionUtil;
import com.reandroid.utils.collection.ComputeIterator;
import com.reandroid.utils.collection.EmptyIterator;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

/* JADX INFO: loaded from: classes11.dex */
public class DexInstruction extends DexCode {
    private final DexMethod dexMethod;
    private boolean mEdit;
    private Ins mIns;

    public DexInstruction(DexMethod dexMethod, Ins ins) {
        this.dexMethod = dexMethod;
        this.mIns = ins;
    }

    public boolean usesRegister(int i) {
        int registersCount = getRegistersCount();
        for (int i2 = 0; i2 < registersCount; i2++) {
            if (i == getRegister(i2)) {
                return true;
            }
        }
        return false;
    }

    public boolean usesRegister(int i, RegisterType registerType) {
        RegisterFormat registerFormat = getOpcode().getRegisterFormat();
        int registersCount = getRegistersCount();
        for (int i2 = 0; i2 < registersCount; i2++) {
            if (i == getRegister(i2) && registerType.is(registerFormat.get(i2))) {
                return true;
            }
        }
        return false;
    }

    public int getAddress() {
        return getIns().getAddress();
    }

    public int getCodeUnits() {
        return getIns().getCodeUnits();
    }

    public List<Register> getLocalFreeRegisters() {
        return getDexMethod().getLocalFreeRegisters(getIndex());
    }

    public String getString() {
        IdItem idSectionEntry = getIdSectionEntry();
        if (idSectionEntry instanceof StringId) {
            return ((StringId) idSectionEntry).getString();
        }
        return null;
    }

    public void setString(String str) {
        setKey(StringKey.create(str));
    }

    /* JADX WARN: Multi-variable type inference failed */
    public DexInstruction setStringWithJumbo(String str) {
        SizeXIns sizeXIns = (SizeXIns) edit();
        StringId stringId = (StringId) sizeXIns.getOrCreateSectionItem(SectionType.STRING_ID, StringKey.create(str));
        if ((stringId.getIdx() & (-65536)) == 0 || !sizeXIns.is(Opcode.CONST_STRING)) {
            sizeXIns.setSectionId(stringId);
            return this;
        }
        int register = ((RegistersSet) sizeXIns).getRegister();
        InsConstStringJumbo insConstStringJumbo = (InsConstStringJumbo) sizeXIns.replace(Opcode.CONST_STRING_JUMBO);
        insConstStringJumbo.setRegister(register);
        insConstStringJumbo.setSectionId(stringId);
        return create(getDexMethod(), insConstStringJumbo);
    }

    public FieldKey getFieldKey() {
        IdItem idSectionEntry = getIdSectionEntry();
        if (idSectionEntry instanceof FieldId) {
            return ((FieldId) idSectionEntry).getKey();
        }
        return null;
    }

    public MethodKey getMethodKey() {
        IdItem idSectionEntry = getIdSectionEntry();
        if (idSectionEntry instanceof MethodId) {
            return ((MethodId) idSectionEntry).getKey();
        }
        return null;
    }

    public Key getKey() {
        IdItem idSectionEntry = getIdSectionEntry();
        if (idSectionEntry != null) {
            return idSectionEntry.getKey();
        }
        return null;
    }

    public void setKey(Key key) {
        if (getIns() instanceof SizeXIns) {
            ((SizeXIns) edit()).setSectionIdKey(key);
        }
    }

    public IdItem getIdSectionEntry() {
        Ins ins = getIns();
        if (ins instanceof SizeXIns) {
            return ((SizeXIns) ins).getSectionId();
        }
        return null;
    }

    public int getRegister(int i) {
        if (i < 0) {
            return -1;
        }
        BlockRefresh ins = getIns();
        if (!(ins instanceof RegistersSet)) {
            return -1;
        }
        RegistersSet registersSet = (RegistersSet) ins;
        if (i >= registersSet.getRegistersCount()) {
            return -1;
        }
        return registersSet.getRegister(i);
    }

    public int getRegister() {
        return getRegister(0);
    }

    public int getRegistersCount() {
        BlockRefresh ins = getIns();
        if (ins instanceof RegistersSet) {
            return ((RegistersSet) ins).getRegistersCount();
        }
        return 0;
    }

    public void setRegister(int i) {
        setRegister(0, i);
    }

    public void setRegister(int i, int i2) {
        BlockRefresh ins = getIns();
        if (ins instanceof RegistersSet) {
            ensureRegistersCount(i + 1);
            ((RegistersSet) ins).setRegister(i, i2);
        }
    }

    public boolean removeRegisterAt(int i) {
        BlockRefresh blockRefreshEdit = edit();
        if (blockRefreshEdit instanceof RegistersSet) {
            return ((RegistersSet) blockRefreshEdit).removeRegisterAt(i);
        }
        return false;
    }

    private void ensureRegistersCount(int i) {
        if (i <= getRegistersCount() || !getOpcode().getRegisterFormat().isOut()) {
            return;
        }
        setRegistersCount(i);
    }

    public void setRegistersCount(int i) {
        if (getIns() instanceof RegistersSet) {
            ((RegistersSet) edit()).setRegistersCount(i);
        }
    }

    public boolean is(Opcode<?> opcode) {
        return opcode == getOpcode();
    }

    public boolean isConstString() {
        return getIns() instanceof ConstString;
    }

    public boolean isNumber() {
        return getIns() instanceof ConstNumber;
    }

    public boolean isNumberLong() {
        return getIns() instanceof ConstNumberLong;
    }

    public int getTargetAddress() {
        BlockRefresh ins = getIns();
        if (ins instanceof Label) {
            return ((Label) ins).getTargetAddress();
        }
        return -1;
    }

    public void setTargetAddress(int i) {
        if (getIns() instanceof Label) {
            ((Label) edit()).setTargetAddress(i);
        }
    }

    public IntegerReference getAsIntegerReference() {
        BlockRefresh ins = getIns();
        if (ins instanceof ConstNumber) {
            return (ConstNumber) ins;
        }
        return null;
    }

    public Integer getAsInteger() {
        BlockRefresh ins = getIns();
        if (ins instanceof ConstNumber) {
            return Integer.valueOf(((ConstNumber) ins).get());
        }
        return null;
    }

    public Long getAsLong() {
        BlockRefresh ins = getIns();
        if (ins instanceof ConstNumberLong) {
            return Long.valueOf(((ConstNumberLong) ins).getLong());
        }
        return null;
    }

    public void setAsInteger(int i) {
        BlockRefresh blockRefreshEdit = edit();
        if (blockRefreshEdit instanceof ConstNumber) {
            ((ConstNumber) blockRefreshEdit).set(i);
        }
    }

    public void setAsLong(long j) {
        BlockRefresh blockRefreshEdit = edit();
        if (blockRefreshEdit instanceof ConstNumberLong) {
            ((ConstNumberLong) blockRefreshEdit).set(j);
        }
    }

    public DexInstruction replace(String str) throws IOException {
        return replace(SmaliReader.of(str));
    }

    public DexInstruction replace(SmaliReader smaliReader) throws IOException {
        SmaliInstruction smaliInstruction = new SmaliInstruction();
        smaliInstruction.parse(smaliReader);
        Ins insReplace = edit().replace((Opcode<Ins>) smaliInstruction.getOpcode());
        insReplace.fromSmali(smaliInstruction);
        return create(getDexMethod(), insReplace);
    }

    public DexInstruction createNext(String str) throws IOException {
        return createNext(SmaliReader.of(str));
    }

    public DexInstruction createNext(SmaliReader smaliReader) throws IOException {
        SmaliInstruction smaliInstruction = new SmaliInstruction();
        smaliInstruction.parse(smaliReader);
        Ins insCreateNext = edit().createNext(smaliInstruction.getOpcode());
        insCreateNext.fromSmali(smaliInstruction);
        return create(getDexMethod(), insCreateNext);
    }

    public DexInstruction replace(Opcode<?> opcode) {
        return create(getDexMethod(), edit().replace(opcode));
    }

    public DexInstruction createNext(Opcode<?> opcode) {
        return create(getDexMethod(), edit().createNext(opcode));
    }

    @Override // com.reandroid.dex.model.Dex
    public void removeSelf() {
        Ins insEdit = edit();
        InstructionList instructionList = insEdit.getInstructionList();
        if (instructionList != null) {
            instructionList.remove(insEdit);
        }
    }

    public Opcode<?> getOpcode() {
        return getIns().getOpcode();
    }

    public Ins getIns() {
        Ins ins = this.mIns;
        if (this.mEdit) {
            return ins;
        }
        DexMethod dexMethod = getDexMethod();
        int editIndex = dexMethod.getEditIndex();
        int index = ins.getIndex();
        if (editIndex >= index) {
            return ins;
        }
        Ins instruction = dexMethod.getDefinition().getInstruction(index);
        this.mIns = instruction;
        this.mEdit = true;
        return instruction;
    }

    public Ins edit() {
        Ins ins = getIns();
        if (this.mEdit) {
            return ins;
        }
        Ins insEdit = this.mIns.edit();
        if (insEdit != this.mIns) {
            getDexMethod().setEditIndex(insEdit.getIndex());
            this.mIns = insEdit;
            this.mEdit = true;
        }
        return insEdit;
    }

    @Override // com.reandroid.dex.model.Dex
    public boolean uses(Key key) {
        Key key2 = getKey();
        if (key2 != null) {
            return key2.uses(key);
        }
        return false;
    }

    @Override // com.reandroid.dex.model.DexCode
    public DexMethod getDexMethod() {
        return this.dexMethod;
    }

    public DexDeclaration findDeclaration() {
        DexClassRepository classRepository;
        Key key = getKey();
        if (key == null || (classRepository = getClassRepository()) == null) {
            return null;
        }
        return classRepository.getDexDeclaration(key);
    }

    public DexInstruction getNext() {
        return getDexMethod().getInstruction(getIndex() + 1);
    }

    public DexInstruction getPrevious() {
        return getDexMethod().getInstruction(getIndex() - 1);
    }

    public DexInstruction getPreviousReader(int i) {
        return getPreviousReader(i, CollectionUtil.getAcceptAll());
    }

    public DexInstruction getPreviousReader(int i, final Opcode<?> opcode) {
        return getPreviousReader(i, new Predicate() { // from class: com.reandroid.dex.model.DexInstruction$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((DexInstruction) obj).is(opcode);
            }
        });
    }

    public DexInstruction getPreviousReader(int i, Predicate<DexInstruction> predicate) {
        for (DexInstruction previous = getPrevious(); previous != null; previous = previous.getPrevious()) {
            Opcode<?> opcode = previous.getOpcode();
            if (opcode.isMover() && previous.getRegister(0) == i) {
                i = previous.getRegister(1);
            } else {
                RegisterFormat registerFormat = opcode.getRegisterFormat();
                int registersCount = previous.getRegistersCount();
                for (int i2 = 0; i2 < registersCount; i2++) {
                    if (i == previous.getRegister(i2) && RegisterType.READ.is(registerFormat.get(i2))) {
                        if (predicate.test(previous)) {
                            return previous;
                        }
                        return null;
                    }
                }
            }
        }
        return null;
    }

    public DexInstruction getPreviousSetter(int i) {
        return getPreviousSetter(i, CollectionUtil.getAcceptAll());
    }

    public DexInstruction getPreviousSetter(int i, final Opcode<?> opcode) {
        return getPreviousSetter(i, new Predicate() { // from class: com.reandroid.dex.model.DexInstruction$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((DexInstruction) obj).is(opcode);
            }
        });
    }

    public DexInstruction getPreviousSetter(int i, Predicate<DexInstruction> predicate) {
        for (DexInstruction previous = getPrevious(); previous != null; previous = previous.getPrevious()) {
            Opcode<?> opcode = previous.getOpcode();
            if (opcode.isMover() && previous.getRegister(1) == i) {
                i = previous.getRegister(0);
            } else {
                RegisterFormat registerFormat = opcode.getRegisterFormat();
                int registersCount = previous.getRegistersCount();
                for (int i2 = 0; i2 < registersCount; i2++) {
                    if (i == previous.getRegister(i2) && RegisterType.WRITE.is(registerFormat.get(i2))) {
                        if (predicate.test(previous)) {
                            return previous;
                        }
                        return null;
                    }
                }
            }
        }
        return null;
    }

    public int getIndex() {
        return getIns().getIndex();
    }

    public void moveBackward() {
        int index = getIndex();
        if (index != 0) {
            edit().moveTo(index - 1);
        }
    }

    public void moveForward() {
        int index = getIndex() + 1;
        if (index < getDexMethod().getInstructionsCount()) {
            edit().moveTo(index);
        }
    }

    public void moveTo(int i) {
        edit().moveTo(i);
    }

    public void merge(DexInstruction dexInstruction) {
        getIns().merge(dexInstruction.getIns());
    }

    @Override // com.reandroid.dex.model.DexCode, com.reandroid.dex.model.Dex
    public DexClassRepository getClassRepository() {
        return getDexMethod().getClassRepository();
    }

    @Override // com.reandroid.dex.model.Dex, com.reandroid.dex.smali.SmaliFormat
    public void append(SmaliWriter smaliWriter) throws IOException {
        getIns().append(smaliWriter);
    }

    @Override // com.reandroid.dex.model.Dex
    public String toString() {
        return getIns().toString();
    }

    public static Iterator<DexInstruction> create(final DexMethod dexMethod, Iterator<Ins> it) {
        if (dexMethod == null) {
            return EmptyIterator.of();
        }
        return ComputeIterator.of(it, new Function() { // from class: com.reandroid.dex.model.DexInstruction$$ExternalSyntheticLambda2
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return DexInstruction.create(dexMethod, (Ins) obj);
            }
        });
    }

    public static DexInstruction create(DexMethod dexMethod, Ins ins) {
        if (dexMethod == null || ins == null) {
            return null;
        }
        return new DexInstruction(dexMethod, ins);
    }
}

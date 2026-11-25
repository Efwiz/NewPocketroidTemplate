package org.jf.baksmali.Adaptors;

import com.huawei.hms.ml.language.common.utils.Constant;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.jf.baksmali.Adaptors.Debug.DebugMethodItem;
import org.jf.baksmali.Adaptors.Debug.LineNumberMethodItem;
import org.jf.baksmali.Adaptors.Format.InstructionMethodItem;
import org.jf.baksmali.Adaptors.Format.InstructionMethodItemFactory;
import org.jf.baksmali.formatter.BaksmaliWriter;
import org.jf.dexlib2.AccessFlags;
import org.jf.dexlib2.Format;
import org.jf.dexlib2.HiddenApiRestriction;
import org.jf.dexlib2.Opcode;
import org.jf.dexlib2.analysis.AnalysisException;
import org.jf.dexlib2.analysis.AnalyzedInstruction;
import org.jf.dexlib2.analysis.MethodAnalyzer;
import org.jf.dexlib2.iface.Annotation;
import org.jf.dexlib2.iface.ExceptionHandler;
import org.jf.dexlib2.iface.Method;
import org.jf.dexlib2.iface.MethodImplementation;
import org.jf.dexlib2.iface.MethodParameter;
import org.jf.dexlib2.iface.TryBlock;
import org.jf.dexlib2.iface.debug.DebugItem;
import org.jf.dexlib2.iface.instruction.Instruction;
import org.jf.dexlib2.iface.instruction.OffsetInstruction;
import org.jf.dexlib2.iface.instruction.ReferenceInstruction;
import org.jf.dexlib2.iface.instruction.formats.Instruction31t;
import org.jf.dexlib2.iface.reference.MethodReference;
import org.jf.dexlib2.iface.reference.Reference;
import org.jf.dexlib2.immutable.instruction.ImmutableInstruction31t;
import org.jf.dexlib2.util.InstructionOffsetMap;
import org.jf.dexlib2.util.SyntheticAccessorResolver;
import org.jf.dexlib2.util.TypeUtils;
import org.jf.util.ExceptionWithContext;
import org.jf.util.SparseIntArray;
import org.jf.util.collection.ListUtil;

/* JADX INFO: loaded from: classes6.dex */
public class MethodDefinition {
    public final ClassDefinition classDef;
    public final List<Instruction> effectiveInstructions;
    private final InstructionOffsetMap instructionOffsetMap;
    public final List<Instruction> instructions;
    private final LabelCache labelCache = new LabelCache();
    public final Method method;
    public final MethodImplementation methodImpl;
    public final List<MethodParameter> methodParameters;
    private final SparseIntArray packedSwitchMap;
    public RegisterFormatter registerFormatter;
    private final SparseIntArray sparseSwitchMap;

    public MethodDefinition(ClassDefinition classDefinition, Method method, MethodImplementation methodImplementation) {
        boolean z;
        int codeUnits;
        boolean z2;
        this.classDef = classDefinition;
        this.method = method;
        this.methodImpl = methodImplementation;
        try {
            List<Instruction> listCopyOf = ListUtil.copyOf(methodImplementation.getInstructions().iterator());
            this.instructions = listCopyOf;
            this.methodParameters = new ArrayList(method.getParameters());
            this.effectiveInstructions = new ArrayList(listCopyOf);
            this.packedSwitchMap = new SparseIntArray(0);
            this.sparseSwitchMap = new SparseIntArray(0);
            InstructionOffsetMap instructionOffsetMap = new InstructionOffsetMap(listCopyOf);
            this.instructionOffsetMap = instructionOffsetMap;
            int instructionCodeOffset = instructionOffsetMap.getInstructionCodeOffset(listCopyOf.size() - 1) + listCopyOf.get(listCopyOf.size() - 1).getCodeUnits();
            for (int i = 0; i < this.instructions.size(); i++) {
                Instruction instruction = this.instructions.get(i);
                Opcode opcode = instruction.getOpcode();
                if (opcode == Opcode.PACKED_SWITCH) {
                    int instructionCodeOffset2 = this.instructionOffsetMap.getInstructionCodeOffset(i);
                    int codeOffset = ((OffsetInstruction) instruction).getCodeOffset() + instructionCodeOffset2;
                    try {
                        codeOffset = findPayloadOffset(codeOffset, Opcode.PACKED_SWITCH_PAYLOAD);
                        z2 = true;
                    } catch (InvalidSwitchPayload unused) {
                        z2 = false;
                    }
                    if (z2) {
                        if (this.packedSwitchMap.get(codeOffset, -1) != -1) {
                            Instruction instructionFindSwitchPayload = findSwitchPayload(codeOffset, Opcode.PACKED_SWITCH_PAYLOAD);
                            this.effectiveInstructions.set(i, new ImmutableInstruction31t(opcode, ((Instruction31t) instruction).getRegisterA(), instructionCodeOffset - instructionCodeOffset2));
                            this.effectiveInstructions.add(instructionFindSwitchPayload);
                            codeUnits = instructionFindSwitchPayload.getCodeUnits() + instructionCodeOffset;
                        } else {
                            codeUnits = instructionCodeOffset;
                            instructionCodeOffset = codeOffset;
                        }
                        this.packedSwitchMap.append(instructionCodeOffset, instructionCodeOffset2);
                        instructionCodeOffset = codeUnits;
                    }
                } else if (opcode == Opcode.SPARSE_SWITCH) {
                    int instructionCodeOffset3 = this.instructionOffsetMap.getInstructionCodeOffset(i);
                    int codeOffset2 = ((OffsetInstruction) instruction).getCodeOffset() + instructionCodeOffset3;
                    try {
                        codeOffset2 = findPayloadOffset(codeOffset2, Opcode.SPARSE_SWITCH_PAYLOAD);
                        z = true;
                    } catch (InvalidSwitchPayload unused2) {
                        z = false;
                    }
                    if (z) {
                        if (this.sparseSwitchMap.get(codeOffset2, -1) != -1) {
                            Instruction instructionFindSwitchPayload2 = findSwitchPayload(codeOffset2, Opcode.SPARSE_SWITCH_PAYLOAD);
                            this.effectiveInstructions.set(i, new ImmutableInstruction31t(opcode, ((Instruction31t) instruction).getRegisterA(), instructionCodeOffset - instructionCodeOffset3));
                            this.effectiveInstructions.add(instructionFindSwitchPayload2);
                            codeUnits = instructionFindSwitchPayload2.getCodeUnits() + instructionCodeOffset;
                        } else {
                            codeUnits = instructionCodeOffset;
                            instructionCodeOffset = codeOffset2;
                        }
                        this.sparseSwitchMap.append(instructionCodeOffset, instructionCodeOffset3);
                        instructionCodeOffset = codeUnits;
                    }
                }
            }
        } catch (Exception e) {
            try {
                throw ExceptionWithContext.withContext(e, "Error while processing method %s", classDefinition.getFormatter().getMethodDescriptor(method) + "\n" + e.toString());
            } catch (Exception unused3) {
                throw ExceptionWithContext.withContext(e, "Error while processing method", new Object[0]);
            }
        }
    }

    public static void writeEmptyMethodTo(BaksmaliWriter baksmaliWriter, Method method, ClassDefinition classDefinition) throws IOException {
        baksmaliWriter.write(".method ");
        writeAccessFlagsAndRestrictions(baksmaliWriter, method.getAccessFlags(), method.getHiddenApiRestrictions());
        baksmaliWriter.write(method.getName());
        baksmaliWriter.write("(");
        ArrayList arrayList = new ArrayList(method.getParameters());
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            baksmaliWriter.writeType(((MethodParameter) it.next()).getType());
        }
        baksmaliWriter.write(Constant.AFTER_QUTO);
        baksmaliWriter.write(method.getReturnType());
        baksmaliWriter.write(10);
        baksmaliWriter.indent(4);
        writeParameters(classDefinition, baksmaliWriter, method, arrayList);
        AnnotationFormatter.writeTo(baksmaliWriter, method.getAnnotations());
        baksmaliWriter.deindent(4);
        baksmaliWriter.write(".end method\n");
    }

    public void writeTo(BaksmaliWriter baksmaliWriter) throws IOException {
        int i = !AccessFlags.STATIC.isSet(this.method.getAccessFlags()) ? 1 : 0;
        baksmaliWriter.write(".method ");
        writeAccessFlagsAndRestrictions(baksmaliWriter, this.method.getAccessFlags(), this.method.getHiddenApiRestrictions());
        baksmaliWriter.writeSimpleName(this.method.getName());
        baksmaliWriter.write("(");
        Iterator<MethodParameter> it = this.methodParameters.iterator();
        while (it.hasNext()) {
            String type = it.next().getType();
            baksmaliWriter.writeType(type);
            i = TypeUtils.isWideType(type) ? i + 2 : i + 1;
        }
        baksmaliWriter.write(Constant.AFTER_QUTO);
        baksmaliWriter.writeType(this.method.getReturnType());
        baksmaliWriter.write(10);
        baksmaliWriter.indent(4);
        if (this.classDef.options.localsDirective) {
            baksmaliWriter.write(".locals ");
            baksmaliWriter.writeSignedIntAsDec(this.methodImpl.getRegisterCount() - i);
        } else {
            baksmaliWriter.write(".registers ");
            baksmaliWriter.writeSignedIntAsDec(this.methodImpl.getRegisterCount());
        }
        baksmaliWriter.write(10);
        writeParameters(this.classDef, baksmaliWriter, this.method, this.methodParameters);
        if (this.registerFormatter == null) {
            this.registerFormatter = new RegisterFormatter(this.classDef.options, this.methodImpl.getRegisterCount(), i);
        }
        AnnotationFormatter.writeTo(baksmaliWriter, this.method.getAnnotations());
        baksmaliWriter.write(10);
        List<MethodItem> methodItems = getMethodItems();
        if (this.classDef.options.autoLineNumbers) {
            writeMethodItemsAutoLineNumber(baksmaliWriter, methodItems);
        } else {
            writeMethodItems(baksmaliWriter, methodItems);
        }
        baksmaliWriter.deindent(4);
        baksmaliWriter.write(".end method\n");
    }

    private void writeMethodItems(BaksmaliWriter baksmaliWriter, List<MethodItem> list) throws IOException {
        Iterator<MethodItem> it = list.iterator();
        while (it.hasNext()) {
            if (it.next().writeTo(baksmaliWriter)) {
                baksmaliWriter.write(10);
            }
        }
    }

    private void writeMethodItemsAutoLineNumber(BaksmaliWriter baksmaliWriter, List<MethodItem> list) throws IOException {
        MethodItem methodItem = null;
        boolean z = false;
        boolean z2 = false;
        for (MethodItem methodItem2 : list) {
            if (methodItem2 instanceof LineNumberMethodItem) {
                if (!z && (!z2 || isLineNumberRequired(methodItem))) {
                    ((LineNumberMethodItem) methodItem2).setLineNumber(baksmaliWriter.getLineNumber());
                    z = true;
                    z2 = true;
                }
            } else {
                if (!z && isLineNumberRequired(methodItem2)) {
                    if (new LineNumberMethodItem(baksmaliWriter.getLineNumber()).writeTo(baksmaliWriter)) {
                        baksmaliWriter.write(10);
                    }
                    z2 = true;
                }
                z = false;
                methodItem = methodItem2;
            }
            if (methodItem2.writeTo(baksmaliWriter)) {
                baksmaliWriter.write(10);
            }
        }
    }

    private boolean isLineNumberRequired(MethodItem methodItem) {
        return (methodItem instanceof InstructionMethodItem) && ((InstructionMethodItem) methodItem).getInstruction().getOpcode().referenceType != 7;
    }

    public Instruction findSwitchPayload(int i, Opcode opcode) {
        int i2;
        try {
            int instructionIndexAtCodeOffset = this.instructionOffsetMap.getInstructionIndexAtCodeOffset(i);
            Instruction instruction = this.instructions.get(instructionIndexAtCodeOffset);
            if (instruction.getOpcode() == opcode) {
                return instruction;
            }
            if (instruction.getOpcode() == Opcode.NOP && (i2 = instructionIndexAtCodeOffset + 1) < this.instructions.size()) {
                Instruction instruction2 = this.instructions.get(i2);
                if (instruction2.getOpcode() == opcode) {
                    return instruction2;
                }
            }
            throw new InvalidSwitchPayload(i);
        } catch (InstructionOffsetMap.InvalidInstructionOffset unused) {
            throw new InvalidSwitchPayload(i);
        }
    }

    public int findPayloadOffset(int i, Opcode opcode) {
        int i2;
        try {
            int instructionIndexAtCodeOffset = this.instructionOffsetMap.getInstructionIndexAtCodeOffset(i);
            Instruction instruction = this.instructions.get(instructionIndexAtCodeOffset);
            if (instruction.getOpcode() == opcode) {
                return i;
            }
            if (instruction.getOpcode() == Opcode.NOP && (i2 = instructionIndexAtCodeOffset + 1) < this.instructions.size() && this.instructions.get(i2).getOpcode() == opcode) {
                return this.instructionOffsetMap.getInstructionCodeOffset(i2);
            }
            throw new InvalidSwitchPayload(i);
        } catch (InstructionOffsetMap.InvalidInstructionOffset unused) {
            throw new InvalidSwitchPayload(i);
        }
    }

    private static void writeAccessFlagsAndRestrictions(BaksmaliWriter baksmaliWriter, int i, Set<HiddenApiRestriction> set) throws IOException {
        for (AccessFlags accessFlags : AccessFlags.getAccessFlagsForMethod(i)) {
            baksmaliWriter.write(accessFlags.toString());
            baksmaliWriter.write(32);
        }
        Iterator<HiddenApiRestriction> it = set.iterator();
        while (it.hasNext()) {
            baksmaliWriter.write(it.next().toString());
            baksmaliWriter.write(32);
        }
    }

    private static void writeParameters(ClassDefinition classDefinition, BaksmaliWriter baksmaliWriter, Method method, List<? extends MethodParameter> list) throws IOException {
        int i = !AccessFlags.STATIC.isSet(method.getAccessFlags()) ? 1 : 0;
        for (MethodParameter methodParameter : list) {
            String type = methodParameter.getType();
            CharSequence name2 = methodParameter.getName();
            Set<? extends Annotation> annotations = methodParameter.getAnnotations();
            if ((classDefinition.options.debugInfo && name2 != null) || annotations.size() != 0) {
                baksmaliWriter.write(".param p");
                baksmaliWriter.writeSignedIntAsDec(i);
                if (name2 != null && classDefinition.options.debugInfo) {
                    baksmaliWriter.write(", ");
                    baksmaliWriter.writeQuotedString(name2);
                }
                baksmaliWriter.write("    # ");
                baksmaliWriter.writeType(type);
                baksmaliWriter.write("\n");
                if (annotations.size() > 0) {
                    baksmaliWriter.indent(4);
                    AnnotationFormatter.writeTo(baksmaliWriter, annotations);
                    baksmaliWriter.deindent(4);
                    baksmaliWriter.write(".end param\n");
                }
            }
            i = TypeUtils.isWideType(type) ? i + 2 : i + 1;
        }
    }

    public LabelCache getLabelCache() {
        return this.labelCache;
    }

    public int getPackedSwitchBaseAddress(int i) {
        return this.packedSwitchMap.get(i, -1);
    }

    public int getSparseSwitchBaseAddress(int i) {
        return this.sparseSwitchMap.get(i, -1);
    }

    private List<MethodItem> getMethodItems() {
        ArrayList arrayList = new ArrayList();
        if (this.classDef.options.registerInfo != 0 || this.classDef.options.normalizeVirtualMethods || (this.classDef.options.deodex && needsAnalyzed())) {
            addAnalyzedInstructionMethodItems(arrayList);
        } else {
            addInstructionMethodItems(arrayList);
        }
        addTries(arrayList);
        if (this.classDef.options.debugInfo) {
            addDebugInfo(arrayList);
        }
        if (this.classDef.options.sequentialLabels) {
            setLabelSequentialNumbers();
        }
        Iterator<LabelMethodItem> it = this.labelCache.getLabels().iterator();
        while (it.hasNext()) {
            arrayList.add(it.next());
        }
        Collections.sort(arrayList);
        return this.classDef.options.skipDuplicateLineNumbers ? clearDuplicateLineNumbers(arrayList) : arrayList;
    }

    private List<MethodItem> clearDuplicateLineNumbers(List<MethodItem> list) {
        ArrayList arrayList = new ArrayList(list.size());
        boolean z = false;
        for (MethodItem methodItem : list) {
            if (!(methodItem instanceof LineNumberMethodItem)) {
                z = false;
            } else if (!z) {
                if ((((LineNumberMethodItem) methodItem).getLineNumber() & (-65536)) != 0) {
                    z = true;
                } else {
                    z = true;
                }
            }
            arrayList.add(methodItem);
        }
        return arrayList;
    }

    private boolean needsAnalyzed() {
        Iterator<? extends Instruction> it = this.methodImpl.getInstructions().iterator();
        while (it.hasNext()) {
            if (it.next().getOpcode().odexOnly()) {
                return true;
            }
        }
        return false;
    }

    private void addInstructionMethodItems(List<MethodItem> list) {
        SyntheticAccessorResolver.AccessedMember accessedMember;
        int codeUnits = 0;
        for (int i = 0; i < this.effectiveInstructions.size(); i++) {
            Instruction instruction = this.effectiveInstructions.get(i);
            list.add(InstructionMethodItemFactory.makeInstructionFormatMethodItem(this, codeUnits, instruction));
            if (i != this.effectiveInstructions.size() - 1) {
                list.add(new BlankMethodItem(codeUnits));
            }
            if (this.classDef.options.codeOffsets) {
                list.add(new MethodItem(codeUnits) { // from class: org.jf.baksmali.Adaptors.MethodDefinition.1
                    @Override // org.jf.baksmali.Adaptors.MethodItem
                    public double getSortOrder() {
                        return -1000.0d;
                    }

                    @Override // org.jf.baksmali.Adaptors.MethodItem
                    public boolean writeTo(BaksmaliWriter baksmaliWriter) throws IOException {
                        baksmaliWriter.write("#@");
                        baksmaliWriter.writeUnsignedLongAsHex(((long) this.codeAddress) & 4294967295L);
                        return true;
                    }
                });
            }
            if (this.classDef.options.accessorComments && this.classDef.options.syntheticAccessorResolver != null && (instruction instanceof ReferenceInstruction) && instruction.getOpcode().referenceType == 3) {
                MethodReference methodReference = (MethodReference) ((ReferenceInstruction) instruction).getReference();
                try {
                    methodReference.validateReference();
                    if (SyntheticAccessorResolver.looksLikeSyntheticAccessor(methodReference.getName()) && (accessedMember = this.classDef.options.syntheticAccessorResolver.getAccessedMember(methodReference)) != null) {
                        list.add(new SyntheticAccessCommentMethodItem(this.classDef, accessedMember, codeUnits));
                    }
                } catch (Reference.InvalidReferenceException unused) {
                }
            }
            codeUnits += instruction.getCodeUnits();
        }
    }

    private void addAnalyzedInstructionMethodItems(List<MethodItem> list) {
        MethodAnalyzer methodAnalyzer = new MethodAnalyzer(this.classDef.options.classPath, this.method, this.classDef.options.inlineResolver, this.classDef.options.normalizeVirtualMethods);
        AnalysisException analysisException = methodAnalyzer.getAnalysisException();
        if (analysisException != null) {
            list.add(new CommentMethodItem(String.format("AnalysisException: %s", analysisException.getMessage()), analysisException.codeAddress, -2.147483648E9d));
            analysisException.printStackTrace(System.err);
        }
        List<AnalyzedInstruction> analyzedInstructions = methodAnalyzer.getAnalyzedInstructions();
        int codeUnits = 0;
        for (int i = 0; i < analyzedInstructions.size(); i++) {
            AnalyzedInstruction analyzedInstruction = analyzedInstructions.get(i);
            list.add(InstructionMethodItemFactory.makeInstructionFormatMethodItem(this, codeUnits, analyzedInstruction.getInstruction()));
            if (analyzedInstruction.getInstruction().getOpcode().format == Format.UnresolvedOdexInstruction) {
                list.add(new CommentedOutMethodItem(InstructionMethodItemFactory.makeInstructionFormatMethodItem(this, codeUnits, analyzedInstruction.getOriginalInstruction())));
            }
            if (i != analyzedInstructions.size() - 1) {
                list.add(new BlankMethodItem(codeUnits));
            }
            if (this.classDef.options.codeOffsets) {
                list.add(new MethodItem(codeUnits) { // from class: org.jf.baksmali.Adaptors.MethodDefinition.2
                    @Override // org.jf.baksmali.Adaptors.MethodItem
                    public double getSortOrder() {
                        return -1000.0d;
                    }

                    @Override // org.jf.baksmali.Adaptors.MethodItem
                    public boolean writeTo(BaksmaliWriter baksmaliWriter) throws IOException {
                        baksmaliWriter.write("#@");
                        baksmaliWriter.writeUnsignedLongAsHex(((long) this.codeAddress) & 4294967295L);
                        return true;
                    }
                });
            }
            if (this.classDef.options.registerInfo != 0 && !analyzedInstruction.getInstruction().getOpcode().format.isPayloadFormat) {
                list.add(new PreInstructionRegisterInfoMethodItem(this.classDef.options.registerInfo, methodAnalyzer, this.registerFormatter, analyzedInstruction, codeUnits));
                list.add(new PostInstructionRegisterInfoMethodItem(this.registerFormatter, analyzedInstruction, codeUnits));
            }
            codeUnits += analyzedInstruction.getInstruction().getCodeUnits();
        }
    }

    private void addTries(List<MethodItem> list) {
        List<? extends TryBlock<? extends ExceptionHandler>> tryBlocks = this.methodImpl.getTryBlocks();
        if (tryBlocks.size() == 0) {
            return;
        }
        int instructionCodeOffset = this.instructionOffsetMap.getInstructionCodeOffset(this.instructions.size() - 1);
        List<Instruction> list2 = this.instructions;
        int codeUnits = instructionCodeOffset + list2.get(list2.size() - 1).getCodeUnits();
        for (TryBlock<? extends ExceptionHandler> tryBlock : tryBlocks) {
            int startCodeAddress = tryBlock.getStartCodeAddress();
            int codeUnitCount = startCodeAddress + tryBlock.getCodeUnitCount();
            if (startCodeAddress >= codeUnits) {
                throw new RuntimeException(String.format("Try start offset %d is past the end of the code block.", Integer.valueOf(startCodeAddress)));
            }
            if (codeUnitCount > codeUnits) {
                throw new RuntimeException(String.format("Try end offset %d is past the end of the code block.", Integer.valueOf(codeUnitCount)));
            }
            int instructionCodeOffset2 = this.instructionOffsetMap.getInstructionCodeOffset(this.instructionOffsetMap.getInstructionIndexAtCodeOffset(codeUnitCount - 1, false));
            Iterator it = tryBlock.getExceptionHandlers().iterator();
            while (it.hasNext()) {
                ExceptionHandler exceptionHandler = (ExceptionHandler) it.next();
                int handlerCodeAddress = exceptionHandler.getHandlerCodeAddress();
                if (handlerCodeAddress >= codeUnits) {
                    throw new ExceptionWithContext("Exception handler offset %d is past the end of the code block.", Integer.valueOf(handlerCodeAddress));
                }
                list.add(new CatchMethodItem(this.classDef.options, this.labelCache, instructionCodeOffset2, exceptionHandler.getExceptionType(), startCodeAddress, codeUnitCount, handlerCodeAddress));
            }
        }
    }

    private void addDebugInfo(List<MethodItem> list) {
        Iterator<? extends DebugItem> it = this.methodImpl.getDebugItems().iterator();
        while (it.hasNext()) {
            list.add(DebugMethodItem.build(this.classDef, this.registerFormatter, it.next()));
        }
    }

    private void setLabelSequentialNumbers() {
        HashMap map = new HashMap();
        ArrayList<LabelMethodItem> arrayList = new ArrayList(this.labelCache.getLabels());
        Collections.sort(arrayList);
        for (LabelMethodItem labelMethodItem : arrayList) {
            Integer num = (Integer) map.get(labelMethodItem.getLabelPrefix());
            if (num == null) {
                num = 0;
            }
            labelMethodItem.setLabelSequence(num.intValue());
            map.put(labelMethodItem.getLabelPrefix(), Integer.valueOf(num.intValue() + 1));
        }
    }

    public static class LabelCache {
        protected HashMap<LabelMethodItem, LabelMethodItem> labels = new HashMap<>();

        public LabelMethodItem internLabel(LabelMethodItem labelMethodItem) {
            LabelMethodItem labelMethodItem2 = this.labels.get(labelMethodItem);
            if (labelMethodItem2 != null) {
                return labelMethodItem2;
            }
            this.labels.put(labelMethodItem, labelMethodItem);
            return labelMethodItem;
        }

        public Collection<LabelMethodItem> getLabels() {
            return this.labels.values();
        }
    }

    public static class InvalidSwitchPayload extends ExceptionWithContext {
        private final int payloadOffset;

        public InvalidSwitchPayload(int i) {
            super("No switch payload at offset: %d", Integer.valueOf(i));
            this.payloadOffset = i;
        }

        public int getPayloadOffset() {
            return this.payloadOffset;
        }
    }
}

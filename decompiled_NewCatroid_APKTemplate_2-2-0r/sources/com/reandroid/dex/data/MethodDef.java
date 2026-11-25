package com.reandroid.dex.data;

import com.reandroid.arsc.base.Block;
import com.reandroid.arsc.io.BlockReader;
import com.reandroid.common.ArraySupplier;
import com.reandroid.dex.base.UsageMarker;
import com.reandroid.dex.common.AccessFlag;
import com.reandroid.dex.common.Modifier;
import com.reandroid.dex.debug.DebugParameter;
import com.reandroid.dex.id.IdItem;
import com.reandroid.dex.id.MethodId;
import com.reandroid.dex.id.ProtoId;
import com.reandroid.dex.id.TypeId;
import com.reandroid.dex.ins.Ins;
import com.reandroid.dex.ins.TryBlock;
import com.reandroid.dex.key.Key;
import com.reandroid.dex.key.MethodKey;
import com.reandroid.dex.key.TypeKey;
import com.reandroid.dex.reference.DataItemUle128Reference;
import com.reandroid.dex.sections.SectionType;
import com.reandroid.dex.smali.SmaliDirective;
import com.reandroid.dex.smali.SmaliRegion;
import com.reandroid.dex.smali.SmaliWriter;
import com.reandroid.dex.smali.model.SmaliMethod;
import com.reandroid.dex.smali.model.SmaliMethodParameter;
import com.reandroid.utils.StringsUtil;
import com.reandroid.utils.collection.ArraySupplierIterator;
import com.reandroid.utils.collection.CombiningIterator;
import com.reandroid.utils.collection.EmptyIterator;
import com.reandroid.utils.collection.ExpandIterator;
import com.reandroid.utils.collection.FilterIterator;
import java.io.IOException;
import java.util.Iterator;
import java.util.Objects;
import java.util.function.Predicate;
import net.bytebuddy.asm.Advice;

/* JADX INFO: loaded from: classes11.dex */
public class MethodDef extends Def<MethodId> {
    private final DataItemUle128Reference<CodeItem> codeOffset;

    public MethodDef() {
        super(1, SectionType.METHOD_ID);
        DataItemUle128Reference<CodeItem> dataItemUle128Reference = new DataItemUle128Reference<>(SectionType.CODE, UsageMarker.USAGE_DEFINITION);
        this.codeOffset = dataItemUle128Reference;
        addChild(2, dataItemUle128Reference);
    }

    public boolean isBridge() {
        return AccessFlag.BRIDGE.isSet(getAccessFlagsValue());
    }

    @Override // com.reandroid.dex.data.Def
    public boolean isDirect() {
        return isConstructor() || isPrivate() || isStatic();
    }

    public String getName() {
        MethodId id2 = getId();
        if (id2 != null) {
            return id2.getName();
        }
        return null;
    }

    public void setName(String str) {
        if (Objects.equals(getName(), str)) {
            return;
        }
        getId().setName(str);
    }

    public int getParametersCount() {
        MethodId id2 = getId();
        if (id2 != null) {
            return id2.getParametersCount();
        }
        return 0;
    }

    public int getParameterRegistersCount() {
        MethodId id2 = getId();
        if (id2 == null) {
            return 0;
        }
        int parameterRegistersCount = id2.getParameterRegistersCount();
        return !isStatic() ? parameterRegistersCount + 1 : parameterRegistersCount;
    }

    public Parameter getParameter(int i) {
        if (i < 0 || i >= getParametersCount()) {
            return null;
        }
        return new Parameter(this, i);
    }

    public Iterator<Parameter> getParameters() {
        return getParameters(false);
    }

    public Iterator<Parameter> getParameters(boolean z) {
        if (getParametersCount() == 0) {
            return EmptyIterator.of();
        }
        Iterator<Parameter> itOf = ArraySupplierIterator.of(new ArraySupplier<Parameter>() { // from class: com.reandroid.dex.data.MethodDef.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // com.reandroid.common.ArraySupplier
            public Parameter get(int i) {
                return MethodDef.this.getParameter(i);
            }

            @Override // com.reandroid.common.CountSupplier
            public int getCount() {
                return MethodDef.this.getParametersCount();
            }
        });
        return !z ? itOf : FilterIterator.of(itOf, new Predicate() { // from class: com.reandroid.dex.data.MethodDef$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return MethodDef.lambda$getParameters$0((MethodDef.Parameter) obj);
            }
        });
    }

    static /* synthetic */ boolean lambda$getParameters$0(Parameter parameter) {
        return !parameter.isEmpty();
    }

    @Override // com.reandroid.dex.data.Def, com.reandroid.dex.data.DefIndex
    public MethodKey getKey() {
        return (MethodKey) super.getKey();
    }

    @Override // com.reandroid.dex.data.Def
    void onRemove() {
        CodeItem codeItem = (CodeItem) this.codeOffset.getItem();
        if (codeItem != null) {
            codeItem.setMethodDef(null);
            this.codeOffset.setItem((DataItem) null);
        }
        super.onRemove();
    }

    public void removeParameter(int i) {
        Parameter parameter = getParameter(i);
        if (parameter == null) {
            return;
        }
        parameter.remove();
        MethodKey key = getKey();
        if (key == null) {
            return;
        }
        setItem(key.removeParameter(i));
    }

    public void setDebugInfo(DebugInfo debugInfo) {
        CodeItem codeItem = getCodeItem();
        if (codeItem != null) {
            codeItem.setDebugInfo(debugInfo);
        }
    }

    public DebugInfo getDebugInfo() {
        CodeItem codeItem = getCodeItem();
        if (codeItem != null) {
            return codeItem.getDebugInfo();
        }
        return null;
    }

    public DebugInfo getUniqueDebugInfo() {
        CodeItem codeItem = getCodeItem();
        if (codeItem != null) {
            return codeItem.getDebugInfo();
        }
        return null;
    }

    public DebugInfo getOrCreateDebugInfo() {
        return getOrCreateCodeItem().getOrCreateDebugInfo();
    }

    public ProtoId getProtoId() {
        MethodId id2 = getId();
        if (id2 != null) {
            return id2.getProto();
        }
        return null;
    }

    public Iterator<Ins> getInstructions() {
        return new ArraySupplierIterator(new ArraySupplier<Ins>() { // from class: com.reandroid.dex.data.MethodDef.2
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // com.reandroid.common.ArraySupplier
            public Ins get(int i) {
                return MethodDef.this.getInstruction(i);
            }

            @Override // com.reandroid.common.CountSupplier
            public int getCount() {
                return MethodDef.this.getInstructionsCount();
            }
        });
    }

    public Ins getInstruction(int i) {
        InstructionList instructionList = getInstructionList();
        if (instructionList != null) {
            return instructionList.get(i);
        }
        return null;
    }

    public Ins getInstructionAt(int i) {
        InstructionList instructionList = getInstructionList();
        if (instructionList != null) {
            return instructionList.getAtAddress(i);
        }
        return null;
    }

    public int getInstructionsCount() {
        InstructionList instructionList = getInstructionList();
        if (instructionList != null) {
            return instructionList.getCount();
        }
        return 0;
    }

    public InstructionList getOrCreateInstructionList() {
        return getOrCreateCodeItem().getInstructionList();
    }

    public InstructionList getInstructionList() {
        CodeItem codeItem = getCodeItem();
        if (codeItem != null) {
            return codeItem.getInstructionList();
        }
        return null;
    }

    public TryBlock getTryBlock() {
        CodeItem codeItem = getCodeItem();
        if (codeItem != null) {
            return codeItem.getTryBlock();
        }
        return null;
    }

    public TryBlock getOrCreateTryBlock() {
        return getOrCreateCodeItem().getOrCreateTryBlock();
    }

    public CodeItem getOrCreateCodeItem() {
        CodeItem codeItem = (CodeItem) this.codeOffset.getItem();
        CodeItem codeItem2 = (CodeItem) this.codeOffset.getOrCreateUniqueItem(this);
        if (codeItem == null) {
            codeItem2.setMethodDef(this);
            int parameterRegistersCount = getParameterRegistersCount();
            codeItem2.setRegistersCount(parameterRegistersCount);
            codeItem2.setParameterRegistersCount(parameterRegistersCount);
        }
        return codeItem2;
    }

    public CodeItem getCodeItem() {
        CodeItem codeItem = (CodeItem) this.codeOffset.getItem();
        if (codeItem != null) {
            codeItem.setMethodDef(this);
        }
        return codeItem;
    }

    public void clearCode() {
        this.codeOffset.setItem((DataItem) null);
    }

    public void clearDebug() {
        CodeItem codeItem = getCodeItem();
        if (codeItem != null) {
            codeItem.removeDebugInfo();
        }
    }

    private void linkCodeItem() {
        CodeItem codeItem = (CodeItem) this.codeOffset.getItem();
        if (codeItem != null) {
            codeItem.addUniqueUser(this);
            codeItem.setMethodDef(this);
        }
    }

    public Iterator<AnnotationGroup> getParameterAnnotations() {
        AnnotationsDirectory annotationsDirectory = getAnnotationsDirectory();
        if (annotationsDirectory != null) {
            return annotationsDirectory.getParameterAnnotation(this);
        }
        return EmptyIterator.of();
    }

    public Iterator<AnnotationSet> getParameterAnnotations(int i) {
        AnnotationsDirectory annotationsDirectory = getAnnotationsDirectory();
        if (annotationsDirectory == null) {
            return EmptyIterator.of();
        }
        return annotationsDirectory.getParameterAnnotation(getDefinitionIndex(), i);
    }

    @Override // com.reandroid.dex.common.IdDefinition
    public Iterator<? extends Modifier> getAccessFlags() {
        return AccessFlag.valuesOfMethod(getAccessFlagsValue());
    }

    @Override // com.reandroid.dex.data.Def, com.reandroid.dex.base.FixedDexContainer, com.reandroid.arsc.base.BlockContainer, com.reandroid.arsc.base.Block
    public void onReadBytes(BlockReader blockReader) throws IOException {
        super.onReadBytes(blockReader);
        linkCodeItem();
    }

    @Override // com.reandroid.dex.smali.SmaliFormat
    public void append(SmaliWriter smaliWriter) throws IOException {
        smaliWriter.onWriteMethod(getKey());
        smaliWriter.newLine();
        getSmaliDirective().append(smaliWriter);
        smaliWriter.appendModifiers(getModifiers());
        getId().append(smaliWriter, false);
        smaliWriter.indentPlus();
        if (!smaliWriter.appendOptional(getCodeItem())) {
            smaliWriter.appendAllWithDoubleNewLine(getParameters(true));
            smaliWriter.appendAllWithDoubleNewLine(getAnnotations(true));
        }
        smaliWriter.indentMinus();
        getSmaliDirective().appendEnd(smaliWriter);
    }

    @Override // com.reandroid.dex.data.Def
    public void replaceKeys(Key key, Key key2) {
        super.replaceKeys(key, key2);
        CodeItem codeItem = getCodeItem();
        if (codeItem != null) {
            codeItem.replaceKeys(key, key2);
        }
    }

    @Override // com.reandroid.dex.common.EditableItem
    public void edit() {
        CodeItem codeItem = (CodeItem) this.codeOffset.getItem();
        CodeItem codeItem2 = (CodeItem) this.codeOffset.getUniqueItem(this);
        if (codeItem2 != null) {
            codeItem2.setMethodDef(this);
            if (codeItem != codeItem2) {
                codeItem2.edit();
                codeItem.getInstructionList().onEditing(codeItem2.getInstructionList());
            }
        }
    }

    @Override // com.reandroid.dex.data.Def, com.reandroid.dex.common.EditableItem
    public void editInternal(Block block) {
        edit();
    }

    @Override // com.reandroid.dex.data.Def, com.reandroid.dex.common.IdUsageIterator
    public Iterator<IdItem> usedIds() {
        Iterator<IdItem> itUsedIds;
        CodeItem codeItem = getCodeItem();
        if (codeItem == null) {
            itUsedIds = EmptyIterator.of();
        } else {
            itUsedIds = codeItem.usedIds();
        }
        return CombiningIterator.singleOne(getId(), itUsedIds);
    }

    @Override // com.reandroid.dex.data.Def
    public void merge(Def<?> def) {
        super.merge(def);
        CodeItem codeItem = ((MethodDef) def).getCodeItem();
        if (codeItem != null) {
            this.codeOffset.setItem(codeItem.getKey());
        }
    }

    public void fromSmali(SmaliMethod smaliMethod) throws IOException {
        setKey(smaliMethod.getKey());
        setAccessFlagsValue(smaliMethod.getAccessFlagsValue());
        if (smaliMethod.hasInstructions()) {
            getOrCreateCodeItem().fromSmali(smaliMethod);
        }
        if (smaliMethod.hasAnnotation()) {
            AnnotationSet annotationSet = (AnnotationSet) getOrCreateSection(SectionType.ANNOTATION_SET).createItem();
            annotationSet.fromSmali(smaliMethod.getAnnotation());
            addAnnotationSet(annotationSet);
        }
        Iterator<SmaliMethodParameter> parameters = smaliMethod.getParameters();
        while (parameters.hasNext()) {
            SmaliMethodParameter next = parameters.next();
            int definitionIndex = next.getDefinitionIndex();
            if (definitionIndex < 0) {
                MethodKey key = smaliMethod.getKey();
                throw new RuntimeException("Parameter out of range, class = " + key.getDeclaring() + ", method = " + key.getName() + key.getProtoKey() + "\n" + next);
            }
            getParameter(definitionIndex).fromSmali(next);
        }
        linkCodeItem();
    }

    @Override // com.reandroid.dex.smali.SmaliRegion
    public SmaliDirective getSmaliDirective() {
        return SmaliDirective.METHOD;
    }

    public String toString() {
        if (isReading()) {
            return getSmaliDirective() + " " + getKey();
        }
        MethodId id2 = getId();
        if (id2 != null) {
            return getSmaliDirective() + " " + Modifier.toString(getAccessFlags()) + " " + id2.toString();
        }
        return getSmaliDirective() + " " + Modifier.toString(getAccessFlags()) + " " + getRelativeIdValue();
    }

    public static class Parameter implements DefIndex, SmaliRegion {
        private final int index;
        private final MethodDef methodDef;

        public Parameter(MethodDef methodDef, int i) {
            this.methodDef = methodDef;
            this.index = i;
        }

        public void remove() {
            clearAnnotations();
            clearDebugParameter();
        }

        public void clearAnnotations() {
            AnnotationsDirectory uniqueAnnotationsDirectory = this.methodDef.getUniqueAnnotationsDirectory();
            if (uniqueAnnotationsDirectory == null || !hasAnnotations()) {
                return;
            }
            Iterator<DirectoryEntry<MethodDef, AnnotationGroup>> parameterEntries = uniqueAnnotationsDirectory.getParameterEntries(this.methodDef);
            int definitionIndex = getDefinitionIndex();
            while (parameterEntries.hasNext()) {
                DirectoryEntry<MethodDef, AnnotationGroup> next = parameterEntries.next();
                AnnotationGroup annotationGroup = (AnnotationGroup) next.getValue();
                if (annotationGroup != null && annotationGroup.getItem(definitionIndex) != null) {
                    AnnotationGroup annotationGroup2 = (AnnotationGroup) annotationGroup.getSection(SectionType.ANNOTATION_GROUP).createItem();
                    next.setValue(annotationGroup2);
                    annotationGroup2.put(definitionIndex, 0);
                    annotationGroup2.refresh();
                }
            }
        }

        public boolean hasAnnotations() {
            return getAnnotations().hasNext();
        }

        public Iterator<AnnotationItem> getAnnotationItems() {
            return ExpandIterator.of(getAnnotations());
        }

        public Iterator<AnnotationSet> getAnnotations() {
            AnnotationsDirectory annotationsDirectory = this.methodDef.getAnnotationsDirectory();
            if (annotationsDirectory != null) {
                return annotationsDirectory.getParameterAnnotation(this.methodDef, getDefinitionIndex());
            }
            return EmptyIterator.of();
        }

        public AnnotationItem addAnnotationItem(TypeKey typeKey) {
            return getOrCreateAnnotationSet().addNewItem(typeKey);
        }

        public AnnotationItem getOrCreateAnnotationItem(TypeKey typeKey) {
            return getOrCreateAnnotationSet().getOrCreate(typeKey);
        }

        public AnnotationSet getOrCreateAnnotationSet() {
            return this.methodDef.getOrCreateUniqueAnnotationsDirectory().getOrCreateParameterAnnotation(this.methodDef, getDefinitionIndex());
        }

        public AnnotationSet addNewAnnotationSet() {
            return this.methodDef.getOrCreateUniqueAnnotationsDirectory().createNewParameterAnnotation(this.methodDef, getDefinitionIndex());
        }

        public TypeKey getType() {
            TypeId typeId = getTypeId();
            if (typeId != null) {
                return typeId.getKey();
            }
            return null;
        }

        public TypeId getTypeId() {
            ProtoId protoId = this.methodDef.getProtoId();
            if (protoId != null) {
                return protoId.getParameter(getDefinitionIndex());
            }
            return null;
        }

        @Override // com.reandroid.dex.data.DefIndex
        public int getDefinitionIndex() {
            return this.index;
        }

        public int getRegister() {
            MethodDef methodDef = this.methodDef;
            return (!methodDef.isStatic() ? 1 : 0) + methodDef.getKey().getRegister(getDefinitionIndex());
        }

        public void clearDebugParameter() {
            DebugInfo debugInfo = this.methodDef.getDebugInfo();
            if (debugInfo != null) {
                debugInfo.removeDebugParameter(getDefinitionIndex());
            }
        }

        public String getDebugName() {
            DebugParameter debugParameter = getDebugParameter();
            if (debugParameter != null) {
                return debugParameter.getName();
            }
            return null;
        }

        public void setDebugName(String str) {
            if (StringsUtil.isEmpty(str)) {
                str = null;
            }
            DebugInfo debugInfo = this.methodDef.getDebugInfo();
            if (debugInfo == null) {
                if (str == null) {
                    return;
                } else {
                    debugInfo = this.methodDef.getOrCreateDebugInfo();
                }
            }
            if (str == null) {
                debugInfo.removeDebugParameter(getDefinitionIndex());
            } else {
                debugInfo.getOrCreateDebugParameter(getDefinitionIndex()).setName(str);
            }
        }

        public DebugParameter getDebugParameter() {
            DebugInfo debugInfo = this.methodDef.getDebugInfo();
            if (debugInfo != null) {
                return debugInfo.getDebugParameter(getDefinitionIndex());
            }
            return null;
        }

        @Override // com.reandroid.dex.data.DefIndex
        public Key getKey() {
            TypeId typeId = getTypeId();
            if (typeId != null) {
                return typeId.getKey();
            }
            return null;
        }

        public void fromSmali(SmaliMethodParameter smaliMethodParameter) {
            if (smaliMethodParameter.hasAnnotations()) {
                getOrCreateAnnotationSet().fromSmali(smaliMethodParameter.getAnnotationSet());
            }
            setDebugName(smaliMethodParameter.getName());
        }

        public boolean isEmpty() {
            DebugParameter debugParameter = getDebugParameter();
            if (debugParameter == null || debugParameter.getNameId() == null) {
                return !getAnnotationItems().hasNext();
            }
            return false;
        }

        @Override // com.reandroid.dex.smali.SmaliFormat
        public void append(SmaliWriter smaliWriter) throws IOException {
            DebugParameter debugParameter = getDebugParameter();
            boolean z = (debugParameter == null || debugParameter.getNameId() == null) ? false : true;
            Iterator<AnnotationSet> annotations = getAnnotations();
            boolean zHasNext = annotations.hasNext();
            if (z || zHasNext) {
                getSmaliDirective().append(smaliWriter);
                smaliWriter.append(Advice.OffsetMapping.ForOrigin.Renderer.ForPropertyName.SYMBOL);
                smaliWriter.appendInteger(getRegister());
                if (z) {
                    debugParameter.append(smaliWriter);
                }
                smaliWriter.appendComment(getTypeId().getName());
                if (zHasNext) {
                    smaliWriter.indentPlus();
                    smaliWriter.appendAllWithDoubleNewLine(annotations);
                    smaliWriter.indentMinus();
                    getSmaliDirective().appendEnd(smaliWriter);
                }
            }
        }

        public int hashCode() {
            return (this.methodDef.hashCode() * 31) + this.index;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            Parameter parameter = (Parameter) obj;
            return this.index == parameter.index && this.methodDef == parameter.methodDef;
        }

        @Override // com.reandroid.dex.smali.SmaliRegion
        public SmaliDirective getSmaliDirective() {
            return SmaliDirective.PARAM;
        }

        public String toString() {
            return SmaliWriter.toStringSafe(this);
        }
    }
}

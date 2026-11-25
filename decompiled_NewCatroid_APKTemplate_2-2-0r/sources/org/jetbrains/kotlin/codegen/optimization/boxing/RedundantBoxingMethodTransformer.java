package org.jetbrains.kotlin.codegen.optimization.boxing;

import com.reandroid.arsc.value.CompoundEntry;
import com.reandroid.dex.sections.Marker;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.Triple;
import kotlin.TuplesKt;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IndexedValue;
import kotlin.collections.MapsKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlin.jvm.internal.Reflection;
import kotlin.ranges.RangesKt;
import kotlin.reflect.KDeclarationContainer;
import kotlin.sequences.SequencesKt;
import org.jetbrains.kotlin.codegen.inline.InlineCodegenUtilsKt;
import org.jetbrains.kotlin.codegen.optimization.common.StrictBasicValue;
import org.jetbrains.kotlin.codegen.optimization.common.UtilKt;
import org.jetbrains.kotlin.codegen.optimization.fixStack.StackTransformationUtilsKt;
import org.jetbrains.kotlin.codegen.optimization.transformer.MethodTransformer;
import org.jetbrains.kotlin.codegen.state.GenerationState;
import org.jetbrains.org.objectweb.asm.Label;
import org.jetbrains.org.objectweb.asm.Type;
import org.jetbrains.org.objectweb.asm.commons.InstructionAdapter;
import org.jetbrains.org.objectweb.asm.tree.AbstractInsnNode;
import org.jetbrains.org.objectweb.asm.tree.InsnList;
import org.jetbrains.org.objectweb.asm.tree.InsnNode;
import org.jetbrains.org.objectweb.asm.tree.JumpInsnNode;
import org.jetbrains.org.objectweb.asm.tree.LabelNode;
import org.jetbrains.org.objectweb.asm.tree.LocalVariableNode;
import org.jetbrains.org.objectweb.asm.tree.MethodInsnNode;
import org.jetbrains.org.objectweb.asm.tree.MethodNode;
import org.jetbrains.org.objectweb.asm.tree.TypeInsnNode;
import org.jetbrains.org.objectweb.asm.tree.VarInsnNode;
import org.jetbrains.org.objectweb.asm.tree.analysis.BasicValue;
import org.jetbrains.org.objectweb.asm.tree.analysis.Frame;
import org.jetbrains.org.objectweb.asm.tree.analysis.Value;
import org.mozilla.javascript.ES6Iterator;

/* JADX INFO: compiled from: RedundantBoxingMethodTransformer.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u008a\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0000\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0015\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\f\n\u0002\u0010\u0001\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J \u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0002J\u0018\u0010\r\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0002J\u0018\u0010\u000e\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0002J\u0018\u0010\u000f\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\u000b\u001a\u00020\fH\u0002J,\u0010\u0010\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\u000b\u001a\u00020\f2\u0012\u0010\u0011\u001a\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u00130\u0012H\u0002J \u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0002J\u0018\u0010\u0016\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\u000b\u001a\u00020\fH\u0002J\u0018\u0010\u0017\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\u0018\u001a\u00020\u0019H\u0002J \u0010\u001a\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0002J\u0018\u0010\u001b\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0002J\u0018\u0010\u001c\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0002J\u0018\u0010\u001d\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0002J\u0018\u0010\u001e\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0002J=\u0010\u001f\u001a\u0014\u0012\u0004\u0012\u00020!\u0012\n\u0012\b\u0012\u0004\u0012\u00020!0\"0 2\u0006\u0010\u0007\u001a\u00020\b2\u0014\u0010#\u001a\u0010\u0012\f\u0012\n\u0012\u0004\u0012\u00020&\u0018\u00010%0$H\u0002¢\u0006\u0002\u0010'J\u0018\u0010(\u001a\u00020)2\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u0007\u001a\u00020\bH\u0002J(\u0010*\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010+\u001a\u00020\u00152\u0006\u0010,\u001a\u00020\u0015H\u0002J;\u0010-\u001a\b\u0012\u0004\u0012\u00020&0\"2\u0006\u0010.\u001a\u00020!2\u0006\u0010\u0007\u001a\u00020\b2\u0016\u0010#\u001a\u0012\u0012\u000e\b\u0001\u0012\n\u0012\u0004\u0012\u00020&\u0018\u00010%0$H\u0002¢\u0006\u0002\u0010/J \u00100\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u00101\u001a\u00020\u0015H\u0002J5\u00102\u001a\u00020\u00062\u0006\u00103\u001a\u0002042\u0006\u0010\u0007\u001a\u00020\b2\u0016\u0010#\u001a\u0012\u0012\u000e\b\u0001\u0012\n\u0012\u0004\u0012\u00020&\u0018\u00010%0$H\u0002¢\u0006\u0002\u00105J$\u00106\u001a\u0002072\f\u00108\u001a\b\u0012\u0004\u0012\u00020&0\"2\f\u00109\u001a\b\u0012\u0004\u0012\u00020\u00130\"H\u0002J3\u0010:\u001a\u00020\u00062\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u0007\u001a\u00020\b2\u0014\u0010#\u001a\u0010\u0012\f\u0012\n\u0012\u0004\u0012\u00020&\u0018\u00010%0$H\u0002¢\u0006\u0002\u0010;J5\u0010<\u001a\u0002072\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u0007\u001a\u00020\b2\u0016\u0010#\u001a\u0012\u0012\u000e\b\u0001\u0012\n\u0012\u0004\u0012\u00020&\u0018\u00010%0$H\u0002¢\u0006\u0002\u0010=J\u0010\u0010>\u001a\u00020\u00062\u0006\u0010?\u001a\u00020\u0019H\u0002J*\u0010@\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0018\u0010A\u001a\u0014\u0012\u0004\u0012\u00020!\u0012\n\u0012\b\u0012\u0004\u0012\u00020!0\"0 H\u0002J\u0018\u0010B\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010?\u001a\u00020\u0019H\u0002J\u0010\u0010C\u001a\u00020D2\u0006\u0010\t\u001a\u00020\nH\u0002J\u0018\u0010E\u001a\u00020\u00062\u0006\u0010F\u001a\u00020G2\u0006\u0010\u0007\u001a\u00020\bH\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006H"}, d2 = {"Lorg/jetbrains/kotlin/codegen/optimization/boxing/RedundantBoxingMethodTransformer;", "Lorg/jetbrains/kotlin/codegen/optimization/transformer/MethodTransformer;", "generationState", "Lorg/jetbrains/kotlin/codegen/state/GenerationState;", "(Lorg/jetbrains/kotlin/codegen/state/GenerationState;)V", "adaptAreEqualIntrinsic", "", "node", "Lorg/jetbrains/org/objectweb/asm/tree/MethodNode;", "insn", "Lorg/jetbrains/org/objectweb/asm/tree/AbstractInsnNode;", "value", "Lorg/jetbrains/kotlin/codegen/optimization/boxing/BoxedValueDescriptor;", "adaptAreEqualIntrinsicForInt", "adaptAreEqualIntrinsicForLong", "adaptBoxingInstruction", "adaptCastInstruction", "castWithType", "Lorg/jetbrains/kotlin/com/intellij/openapi/util/Pair;", "Lorg/jetbrains/org/objectweb/asm/Type;", "adaptInstruction", "", "adaptInstructionsForBoxedValue", "adaptInstructionsForBoxedValues", CompoundEntry.NAME_values, "Lorg/jetbrains/kotlin/codegen/optimization/boxing/RedundantBoxedValuesCollection;", "adaptJavaLangComparableCompareTo", "adaptJavaLangComparableCompareToForDouble", "adaptJavaLangComparableCompareToForFloat", "adaptJavaLangComparableCompareToForInt", "adaptJavaLangComparableCompareToForLong", "adaptLocalSingleVariableTableForBoxedValuesAndPrepareMultiVariables", "", "Lorg/jetbrains/org/objectweb/asm/tree/LocalVariableNode;", "", "frames", "", "Lorg/jetbrains/org/objectweb/asm/tree/analysis/Frame;", "Lorg/jetbrains/org/objectweb/asm/tree/analysis/BasicValue;", "(Lorg/jetbrains/org/objectweb/asm/tree/MethodNode;[Lorg/jetbrains/org/objectweb/asm/tree/analysis/Frame;)Ljava/util/Map;", "buildVariablesRemapping", "", "fuseAreEqualWithBranch", "ifEqualOpcode", "ifNotEqualOpcode", "getValuesStoredOrLoadedToVariable", "localVariableNode", "(Lorg/jetbrains/org/objectweb/asm/tree/LocalVariableNode;Lorg/jetbrains/org/objectweb/asm/tree/MethodNode;[Lorg/jetbrains/org/objectweb/asm/tree/analysis/Frame;)Ljava/util/List;", "ifEqual1Else0", "ifneOpcode", "interpretPopInstructionsForBoxedValues", "interpreter", "Lorg/jetbrains/kotlin/codegen/optimization/boxing/RedundantBoxingInterpreter;", "(Lorg/jetbrains/kotlin/codegen/optimization/boxing/RedundantBoxingInterpreter;Lorg/jetbrains/org/objectweb/asm/tree/MethodNode;[Lorg/jetbrains/org/objectweb/asm/tree/analysis/Frame;)V", "isUnsafeToRemoveBoxingForConnectedValues", "", "usedValues", "unboxedTypes", "removeValuesClashingWithVariables", "(Lorg/jetbrains/kotlin/codegen/optimization/boxing/RedundantBoxedValuesCollection;Lorg/jetbrains/org/objectweb/asm/tree/MethodNode;[Lorg/jetbrains/org/objectweb/asm/tree/analysis/Frame;)V", "removeValuesClashingWithVariablesPass", "(Lorg/jetbrains/kotlin/codegen/optimization/boxing/RedundantBoxedValuesCollection;Lorg/jetbrains/org/objectweb/asm/tree/MethodNode;[Lorg/jetbrains/org/objectweb/asm/tree/analysis/Frame;)Z", "removeValuesFromTaintedProgressionIterators", "valuesToOptimize", "replaceVariables", "variablesForReplacement", "sortAdaptableInstructionsForBoxedValues", "throwCannotAdaptInstruction", "", "transform", "internalClassName", "", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class RedundantBoxingMethodTransformer extends MethodTransformer {
    private final GenerationState generationState;

    public RedundantBoxingMethodTransformer(GenerationState generationState) {
        Intrinsics.checkNotNullParameter(generationState, "generationState");
        this.generationState = generationState;
    }

    @Override // org.jetbrains.kotlin.codegen.optimization.transformer.MethodTransformer
    public void transform(String internalClassName, MethodNode node) {
        Intrinsics.checkNotNullParameter(internalClassName, "internalClassName");
        Intrinsics.checkNotNullParameter(node, "node");
        AbstractInsnNode[] array = node.instructions.toArray();
        Intrinsics.checkNotNull(array);
        for (AbstractInsnNode abstractInsnNode : array) {
            Intrinsics.checkNotNull(abstractInsnNode);
            if (BoxingInterpreterKt.isBoxing(abstractInsnNode, this.generationState) || (abstractInsnNode.getOpcode() == 185 && (abstractInsnNode instanceof MethodInsnNode) && Intrinsics.areEqual(((MethodInsnNode) abstractInsnNode).f1015name, ES6Iterator.NEXT_METHOD))) {
                RedundantBoxingInterpreter redundantBoxingInterpreter = new RedundantBoxingInterpreter(node, this.generationState);
                Frame<BasicValue>[] frameArrAnalyze = new BoxingAnalyzer(internalClassName, node, redundantBoxingInterpreter).analyze();
                interpretPopInstructionsForBoxedValues(redundantBoxingInterpreter, node, frameArrAnalyze);
                RedundantBoxedValuesCollection candidatesBoxedValues = redundantBoxingInterpreter.getCandidatesBoxedValues();
                if (candidatesBoxedValues.isEmpty()) {
                    return;
                }
                removeValuesFromTaintedProgressionIterators(candidatesBoxedValues);
                removeValuesClashingWithVariables(candidatesBoxedValues, node, frameArrAnalyze);
                Map<LocalVariableNode, List<LocalVariableNode>> mapAdaptLocalSingleVariableTableForBoxedValuesAndPrepareMultiVariables = adaptLocalSingleVariableTableForBoxedValuesAndPrepareMultiVariables(node, frameArrAnalyze);
                UtilKt.remapLocalVariables(node, buildVariablesRemapping(candidatesBoxedValues, node));
                replaceVariables(node, mapAdaptLocalSingleVariableTableForBoxedValuesAndPrepareMultiVariables);
                sortAdaptableInstructionsForBoxedValues(node, candidatesBoxedValues);
                adaptInstructionsForBoxedValues(node, candidatesBoxedValues);
                return;
            }
        }
    }

    private final void sortAdaptableInstructionsForBoxedValues(MethodNode node, RedundantBoxedValuesCollection valuesToOptimize) {
        InsnList instructions = node.instructions;
        Intrinsics.checkNotNullExpressionValue(instructions, "instructions");
        Iterable<IndexedValue> iterableWithIndex = CollectionsKt.withIndex(instructions);
        LinkedHashMap linkedHashMap = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(iterableWithIndex, 10)), 16));
        for (IndexedValue indexedValue : iterableWithIndex) {
            Pair pair = TuplesKt.to((AbstractInsnNode) indexedValue.component2(), Integer.valueOf(indexedValue.getIndex()));
            linkedHashMap.put(pair.getFirst(), pair.getSecond());
        }
        for (BoxedValueDescriptor boxedValueDescriptor : valuesToOptimize) {
            boxedValueDescriptor.sortAssociatedInsns(linkedHashMap);
            boxedValueDescriptor.sortUnboxingWithCastInsns(linkedHashMap);
        }
    }

    /* JADX WARN: Code duplicated, block: B:15:0x0049  */
    private final void replaceVariables(MethodNode node, Map<LocalVariableNode, ? extends List<? extends LocalVariableNode>> variablesForReplacement) {
        if (variablesForReplacement.isEmpty()) {
            return;
        }
        List<LocalVariableNode> localVariables = node.localVariables;
        Intrinsics.checkNotNullExpressionValue(localVariables, "localVariables");
        ArrayList arrayList = new ArrayList();
        for (LocalVariableNode localVariableNode : localVariables) {
            List<? extends LocalVariableNode> listListOf = variablesForReplacement.get(localVariableNode);
            if (listListOf != null) {
                Iterator<? extends LocalVariableNode> it = listListOf.iterator();
                while (it.hasNext()) {
                    it.next().index += localVariableNode.index;
                }
                if (listListOf == null) {
                    listListOf = CollectionsKt.listOf(localVariableNode);
                }
            } else {
                listListOf = CollectionsKt.listOf(localVariableNode);
            }
            CollectionsKt.addAll(arrayList, listListOf);
        }
        node.localVariables = CollectionsKt.toMutableList((Collection) arrayList);
    }

    private final void interpretPopInstructionsForBoxedValues(RedundantBoxingInterpreter interpreter, MethodNode node, Frame<BasicValue>[] frames) {
        Frame<BasicValue> frame;
        int length = frames.length;
        for (int i = 0; i < length; i++) {
            AbstractInsnNode abstractInsnNode = node.instructions.get(i);
            if ((abstractInsnNode.getOpcode() == 87 || abstractInsnNode.getOpcode() == 88) && (frame = frames[i]) != null) {
                Value pVar = StackTransformationUtilsKt.top(frame);
                Intrinsics.checkNotNull(pVar);
                BasicValue basicValue = (BasicValue) pVar;
                Intrinsics.checkNotNull(abstractInsnNode);
                interpreter.processPopInstruction(abstractInsnNode, basicValue);
                if (basicValue.get_size() == 1 && abstractInsnNode.getOpcode() == 88) {
                    Value valuePeek = StackTransformationUtilsKt.peek(frame, 1);
                    Intrinsics.checkNotNull(valuePeek);
                    interpreter.processPopInstruction(abstractInsnNode, (BasicValue) valuePeek);
                }
            }
        }
    }

    private final void removeValuesClashingWithVariables(RedundantBoxedValuesCollection values, MethodNode node, Frame<BasicValue>[] frames) {
        while (removeValuesClashingWithVariablesPass(values, node, frames)) {
        }
    }

    private final boolean removeValuesClashingWithVariablesPass(RedundantBoxedValuesCollection values, MethodNode node, Frame<BasicValue>[] frames) {
        boolean z = false;
        for (LocalVariableNode localVariableNode : node.localVariables) {
            if (Type.getType(localVariableNode.desc).getSort() == 10) {
                Intrinsics.checkNotNull(localVariableNode);
                List<BasicValue> valuesStoredOrLoadedToVariable = getValuesStoredOrLoadedToVariable(localVariableNode, node, frames);
                ArrayList arrayList = new ArrayList();
                for (Object obj : valuesStoredOrLoadedToVariable) {
                    if (obj instanceof BoxedBasicValue) {
                        arrayList.add(obj);
                    }
                }
                ArrayList arrayList2 = arrayList;
                if (!arrayList2.isEmpty() && isUnsafeToRemoveBoxingForConnectedValues(valuesStoredOrLoadedToVariable, ((BoxedBasicValue) CollectionsKt.first((List) arrayList2)).getDescriptor().getUnboxedTypes())) {
                    Iterator it = arrayList2.iterator();
                    while (it.hasNext()) {
                        BoxedValueDescriptor descriptor = ((BoxedBasicValue) it.next()).getDescriptor();
                        if (descriptor.getIsSafeToRemove()) {
                            values.remove(descriptor);
                            z = true;
                        }
                    }
                }
            }
        }
        return z;
    }

    private final void removeValuesFromTaintedProgressionIterators(RedundantBoxedValuesCollection valuesToOptimize) {
        ProgressionIteratorBasicValue progressionIterator;
        for (BoxedValueDescriptor boxedValueDescriptor : CollectionsKt.toList(valuesToOptimize)) {
            if (boxedValueDescriptor != null && (progressionIterator = boxedValueDescriptor.getProgressionIterator()) != null && progressionIterator.getTainted()) {
                valuesToOptimize.remove(boxedValueDescriptor);
            }
        }
    }

    private final boolean isUnsafeToRemoveBoxingForConnectedValues(List<? extends BasicValue> usedValues, List<Type> unboxedTypes) {
        List<? extends BasicValue> list = usedValues;
        if ((list instanceof Collection) && list.isEmpty()) {
            return false;
        }
        for (BasicValue basicValue : list) {
            if (basicValue != StrictBasicValue.UNINITIALIZED_VALUE) {
                if (basicValue instanceof CleanBoxedValue) {
                    BoxedValueDescriptor descriptor = ((CleanBoxedValue) basicValue).getDescriptor();
                    if (!descriptor.getIsSafeToRemove() || !Intrinsics.areEqual(descriptor.getUnboxedTypes(), unboxedTypes)) {
                    }
                }
                return true;
            }
        }
        return false;
    }

    private final Map<LocalVariableNode, List<LocalVariableNode>> adaptLocalSingleVariableTableForBoxedValuesAndPrepareMultiVariables(MethodNode node, Frame<BasicValue>[] frames) {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (LocalVariableNode localVariableNode : node.localVariables) {
            int i = 10;
            if (Type.getType(localVariableNode.desc).getSort() == 10) {
                Intrinsics.checkNotNull(localVariableNode);
                for (BasicValue basicValue : getValuesStoredOrLoadedToVariable(localVariableNode, node, frames)) {
                    if (basicValue instanceof BoxedBasicValue) {
                        BoxedValueDescriptor descriptor = ((BoxedBasicValue) basicValue).getDescriptor();
                        if (descriptor.getIsSafeToRemove()) {
                            Type type = (Type) CollectionsKt.singleOrNull((List) descriptor.getUnboxedTypes());
                            if (type == null) {
                                GenerationState.MultiFieldValueClassUnboxInfo multiFieldValueClassUnboxInfo = descriptor.getMultiFieldValueClassUnboxInfo();
                                Intrinsics.checkNotNull(multiFieldValueClassUnboxInfo);
                                List<Triple<Type, String, String>> unboxedTypesAndMethodNamesAndFieldNames = multiFieldValueClassUnboxInfo.getUnboxedTypesAndMethodNamesAndFieldNames();
                                ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(unboxedTypesAndMethodNamesAndFieldNames, i));
                                Iterator<T> it = unboxedTypesAndMethodNamesAndFieldNames.iterator();
                                int i2 = 0;
                                while (it.hasNext()) {
                                    Triple triple = (Triple) it.next();
                                    Type type2 = (Type) triple.component1();
                                    String str = localVariableNode.f1014name + '-' + ((String) triple.component3());
                                    LabelNode labelNode = localVariableNode.start;
                                    LabelNode labelNode2 = localVariableNode.end;
                                    int size = i2 + type2.getSize();
                                    arrayList.add(new LocalVariableNode(str, type2.getDescriptor(), null, labelNode, labelNode2, i2));
                                    i2 = size;
                                }
                                linkedHashMap.put(localVariableNode, arrayList);
                            } else {
                                localVariableNode.desc = type.getDescriptor();
                            }
                            i = 10;
                        }
                    }
                }
            }
        }
        return linkedHashMap;
    }

    private final List<BasicValue> getValuesStoredOrLoadedToVariable(LocalVariableNode localVariableNode, MethodNode node, Frame<BasicValue>[] frames) {
        Frame<BasicValue> frame;
        BasicValue basicValue;
        ArrayList arrayList = new ArrayList();
        InsnList insnList = node.instructions;
        int iIndexOf = insnList.indexOf(localVariableNode.start);
        int iIndexOf2 = insnList.indexOf(localVariableNode.end);
        Frame<BasicValue> frame2 = frames[iIndexOf];
        if (frame2 != null && (basicValue = (BasicValue) frame2.getLocal(localVariableNode.index)) != null) {
            Intrinsics.checkNotNull(basicValue);
            arrayList.add(basicValue);
        }
        while (iIndexOf < iIndexOf2) {
            if (iIndexOf >= 0 && iIndexOf < insnList.size() && (frame = frames[iIndexOf]) != null) {
                AbstractInsnNode abstractInsnNode = insnList.get(iIndexOf);
                if (abstractInsnNode.getOpcode() == 58 || abstractInsnNode.getOpcode() == 25) {
                    Intrinsics.checkNotNull(abstractInsnNode, "null cannot be cast to non-null type org.jetbrains.org.objectweb.asm.tree.VarInsnNode");
                    VarInsnNode varInsnNode = (VarInsnNode) abstractInsnNode;
                    if (varInsnNode.var == localVariableNode.index) {
                        if (abstractInsnNode.getOpcode() == 58) {
                            Value pVar = StackTransformationUtilsKt.top(frame);
                            Intrinsics.checkNotNull(pVar);
                            arrayList.add(pVar);
                        } else {
                            arrayList.add(frame.getLocal(varInsnNode.var));
                        }
                    }
                }
            }
            iIndexOf++;
        }
        return arrayList;
    }

    private final int[] buildVariablesRemapping(RedundantBoxedValuesCollection values, MethodNode node) {
        HashMap map = new HashMap();
        for (BoxedValueDescriptor boxedValueDescriptor : values) {
            int totalUnboxSize = boxedValueDescriptor.getTotalUnboxSize();
            if (totalUnboxSize >= 2) {
                Iterator<Integer> it = boxedValueDescriptor.getVariablesIndexes().iterator();
                while (it.hasNext()) {
                    Integer numValueOf = Integer.valueOf(it.next().intValue());
                    Integer numValueOf2 = Integer.valueOf(totalUnboxSize - 1);
                    final AnonymousClass1 anonymousClass1 = AnonymousClass1.INSTANCE;
                    map.merge(numValueOf, numValueOf2, new BiFunction() { // from class: org.jetbrains.kotlin.codegen.optimization.boxing.RedundantBoxingMethodTransformer$$ExternalSyntheticLambda0
                        @Override // java.util.function.BiFunction
                        public final Object apply(Object obj, Object obj2) {
                            return RedundantBoxingMethodTransformer.buildVariablesRemapping$lambda$9(anonymousClass1, obj, obj2);
                        }
                    });
                }
            }
        }
        int i = node.maxLocals;
        Collection collectionValues = map.values();
        Intrinsics.checkNotNullExpressionValue(collectionValues, "<get-values>(...)");
        node.maxLocals = i + CollectionsKt.sumOfInt(collectionValues);
        int i2 = node.maxLocals;
        int[] iArr = new int[i2];
        for (int i3 = 0; i3 < i2; i3++) {
            iArr[i3] = i3;
        }
        for (Map.Entry entry : map.entrySet()) {
            int iIntValue = ((Number) entry.getKey()).intValue();
            int iIntValue2 = ((Number) entry.getValue()).intValue();
            int i4 = iIntValue + 1;
            int lastIndex = ArraysKt.getLastIndex(iArr);
            if (i4 <= lastIndex) {
                while (true) {
                    iArr[i4] = iArr[i4] + iIntValue2;
                    if (i4 != lastIndex) {
                        i4++;
                    }
                }
            }
        }
        return iArr;
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.codegen.optimization.boxing.RedundantBoxingMethodTransformer$buildVariablesRemapping$1, reason: invalid class name */
    /* JADX INFO: compiled from: RedundantBoxingMethodTransformer.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    /* synthetic */ class AnonymousClass1 extends FunctionReference implements Function2<Integer, Integer, Integer> {
        public static final AnonymousClass1 INSTANCE = new AnonymousClass1();

        AnonymousClass1() {
            super(2);
        }

        @Override // kotlin.jvm.internal.CallableReference
        public final String getSignature() {
            return "maxOf(II)I";
        }

        @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
        public final String getName() {
            return "maxOf";
        }

        @Override // kotlin.jvm.internal.CallableReference
        public final KDeclarationContainer getOwner() {
            return Reflection.getOrCreateKotlinPackage(ComparisonsKt.class, Marker.BACKEND);
        }

        public final Integer invoke(int i, int i2) {
            return Integer.valueOf(Math.max(i, i2));
        }

        @Override // kotlin.jvm.functions.Function2
        public /* bridge */ /* synthetic */ Integer invoke(Integer num, Integer num2) {
            return invoke(num.intValue(), num2.intValue());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Integer buildVariablesRemapping$lambda$9(Function2 tmp0, Object obj, Object obj2) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        return (Integer) tmp0.invoke(obj, obj2);
    }

    private final void adaptInstructionsForBoxedValues(MethodNode node, RedundantBoxedValuesCollection values) {
        for (BoxedValueDescriptor boxedValueDescriptor : values) {
            Intrinsics.checkNotNull(boxedValueDescriptor);
            adaptInstructionsForBoxedValue(node, boxedValueDescriptor);
        }
    }

    private final void adaptInstructionsForBoxedValue(MethodNode node, BoxedValueDescriptor value) {
        adaptBoxingInstruction(node, value);
        Iterator<org.jetbrains.kotlin.com.intellij.openapi.util.Pair<AbstractInsnNode, Type>> it = value.getUnboxingWithCastInsns().iterator();
        while (it.hasNext()) {
            adaptCastInstruction(node, value, it.next());
        }
        Iterator<AbstractInsnNode> it2 = value.getAssociatedInsns().iterator();
        int iMax = 0;
        while (it2.hasNext()) {
            iMax = Math.max(iMax, adaptInstruction(node, it2.next(), value));
        }
        node.maxLocals += iMax;
    }

    private final void adaptBoxingInstruction(MethodNode node, BoxedValueDescriptor value) {
        if (!value.isFromProgressionIterator()) {
            node.instructions.remove(value.getBoxingInsn());
            return;
        }
        ProgressionIteratorBasicValue progressionIterator = value.getProgressionIterator();
        if (progressionIterator == null) {
            throw new IllegalStateException("iterator should not be null because isFromProgressionIterator returns true".toString());
        }
        node.instructions.insertBefore(value.getBoxingInsn(), new TypeInsnNode(192, progressionIterator.getType().getInternalName()));
        node.instructions.set(value.getBoxingInsn(), new MethodInsnNode(182, progressionIterator.getType().getInternalName(), progressionIterator.getNextMethodName(), progressionIterator.getNextMethodDesc(), false));
    }

    private final void adaptCastInstruction(MethodNode node, BoxedValueDescriptor value, org.jetbrains.kotlin.com.intellij.openapi.util.Pair<AbstractInsnNode, Type> castWithType) {
        AbstractInsnNode first = castWithType.getFirst();
        MethodNode methodNode = new MethodNode(589824);
        new InstructionAdapter(methodNode).cast(value.getUnboxTypeOrOtherwiseMethodReturnType(first instanceof MethodInsnNode ? (MethodInsnNode) first : null), castWithType.getSecond());
        AbstractInsnNode[] array = methodNode.instructions.toArray();
        Intrinsics.checkNotNullExpressionValue(array, "toArray(...)");
        for (AbstractInsnNode abstractInsnNode : array) {
            node.instructions.insertBefore(first, abstractInsnNode);
        }
        node.instructions.remove(first);
    }

    private final int adaptInstruction(MethodNode node, AbstractInsnNode insn, BoxedValueDescriptor value) {
        Type type;
        Ref.BooleanRef booleanRef;
        Ref.BooleanRef booleanRef2;
        Ref.IntRef intRef = new Ref.IntRef();
        int opcode = insn.getOpcode();
        int size = 0;
        if (opcode == 25 || opcode == 58) {
            boolean z = insn.getOpcode() == 58;
            Type type2 = (Type) CollectionsKt.singleOrNull((List) value.getUnboxedTypes());
            if (type2 == null) {
                ArrayList arrayList = new ArrayList();
                for (Type type3 : value.getUnboxedTypes()) {
                    int opcode2 = type3.getOpcode(z ? 54 : 21);
                    Intrinsics.checkNotNull(insn, "null cannot be cast to non-null type org.jetbrains.org.objectweb.asm.tree.VarInsnNode");
                    arrayList.add(new VarInsnNode(opcode2, ((VarInsnNode) insn).var + size));
                    size += type3.getSize();
                }
                if (z) {
                    List listAsReversed = CollectionsKt.asReversed(SequencesKt.toList(SequencesKt.take(SequencesKt.generateSequence(insn.getPrevious(), new Function1<AbstractInsnNode, AbstractInsnNode>() { // from class: org.jetbrains.kotlin.codegen.optimization.boxing.RedundantBoxingMethodTransformer$adaptInstruction$previousInstructions$1
                        @Override // kotlin.jvm.functions.Function1
                        public final AbstractInsnNode invoke(AbstractInsnNode it) {
                            Intrinsics.checkNotNullParameter(it, "it");
                            return it.getPrevious();
                        }
                    }), value.getUnboxedTypes().size())));
                    List<Type> unboxedTypes = value.getUnboxedTypes();
                    ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(unboxedTypes, 10));
                    Iterator<T> it = unboxedTypes.iterator();
                    while (it.hasNext()) {
                        arrayList2.add(Integer.valueOf(((Type) it.next()).getOpcode(21)));
                    }
                    ArrayList arrayList3 = arrayList2;
                    List list = listAsReversed;
                    ArrayList arrayList4 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
                    Iterator it2 = list.iterator();
                    while (it2.hasNext()) {
                        arrayList4.add(Integer.valueOf(((AbstractInsnNode) it2.next()).getOpcode()));
                    }
                    if (Intrinsics.areEqual(arrayList3, arrayList4)) {
                        for (Pair pair : CollectionsKt.zip(list, arrayList)) {
                            AbstractInsnNode abstractInsnNode = (AbstractInsnNode) pair.component1();
                            VarInsnNode varInsnNode = (VarInsnNode) pair.component2();
                            arrayList.remove(varInsnNode);
                            node.instructions.insert(abstractInsnNode, varInsnNode);
                        }
                    } else {
                        Iterator it3 = CollectionsKt.asReversedMutable(arrayList).iterator();
                        while (it3.hasNext()) {
                            node.instructions.insertBefore(insn, (VarInsnNode) it3.next());
                        }
                    }
                } else {
                    Iterator it4 = arrayList.iterator();
                    while (it4.hasNext()) {
                        node.instructions.insertBefore(insn, (VarInsnNode) it4.next());
                    }
                }
                node.instructions.remove(insn);
            } else {
                int opcode3 = type2.getOpcode(z ? 54 : 21);
                InsnList insnList = node.instructions;
                Intrinsics.checkNotNull(insn, "null cannot be cast to non-null type org.jetbrains.org.objectweb.asm.tree.VarInsnNode");
                insnList.set(insn, new VarInsnNode(opcode3, ((VarInsnNode) insn).var));
            }
        } else if (opcode == 87) {
            node.instructions.insert(insn, BoxedBasicValueKt.makePops(value.getUnboxedTypes()));
            node.instructions.remove(insn);
        } else if (opcode == 89) {
            int totalUnboxSize = value.getTotalUnboxSize();
            if (totalUnboxSize != 1) {
                if (totalUnboxSize != 2) {
                    intRef.element = value.getTotalUnboxSize();
                    int i = node.maxLocals;
                    List<Type> unboxedTypes2 = value.getUnboxedTypes();
                    ArrayList arrayList5 = new ArrayList(CollectionsKt.collectionSizeOrDefault(unboxedTypes2, 10));
                    Iterator<T> it5 = unboxedTypes2.iterator();
                    while (it5.hasNext()) {
                        int size2 = ((Type) it5.next()).getSize() + i;
                        arrayList5.add(Integer.valueOf(i));
                        i = size2;
                    }
                    ArrayList arrayList6 = arrayList5;
                    for (Pair pair2 : CollectionsKt.asReversed(CollectionsKt.zip(value.getUnboxedTypes(), arrayList6))) {
                        node.instructions.insertBefore(insn, new VarInsnNode(((Type) pair2.component1()).getOpcode(54), ((Number) pair2.component2()).intValue()));
                    }
                    while (size < 2) {
                        for (Pair pair3 : CollectionsKt.zip(value.getUnboxedTypes(), arrayList6)) {
                            node.instructions.insertBefore(insn, new VarInsnNode(((Type) pair3.component1()).getOpcode(21), ((Number) pair3.component2()).intValue()));
                        }
                        size++;
                    }
                    node.instructions.remove(insn);
                } else {
                    node.instructions.set(insn, new InsnNode(92));
                }
            }
        } else if (opcode == 182) {
            if (value.getUnboxedTypes().size() != 1) {
                Intrinsics.checkNotNull(insn, "null cannot be cast to non-null type org.jetbrains.org.objectweb.asm.tree.MethodInsnNode");
                MethodInsnNode methodInsnNode = (MethodInsnNode) insn;
                GenerationState.MultiFieldValueClassUnboxInfo multiFieldValueClassUnboxInfo = value.getMultiFieldValueClassUnboxInfo();
                Intrinsics.checkNotNull(multiFieldValueClassUnboxInfo);
                int iIndexOf = multiFieldValueClassUnboxInfo.getUnboxedMethodNames().indexOf(methodInsnNode.f1015name);
                Type type4 = value.getUnboxedTypes().get(iIndexOf);
                Ref.BooleanRef booleanRef3 = new Ref.BooleanRef();
                booleanRef3.element = true;
                Ref.BooleanRef booleanRef4 = new Ref.BooleanRef();
                for (IndexedValue indexedValue : CollectionsKt.asReversed(CollectionsKt.toList(CollectionsKt.withIndex(value.getUnboxedTypes())))) {
                    int index = indexedValue.getIndex();
                    Type type5 = (Type) indexedValue.component2();
                    if (index != iIndexOf) {
                        type = type4;
                        booleanRef = booleanRef4;
                        booleanRef2 = booleanRef3;
                        if (adaptInstruction$canRemoveInsn(booleanRef2, index, iIndexOf, methodInsnNode, type5, index > iIndexOf)) {
                            node.instructions.remove(index < iIndexOf ? methodInsnNode.getPrevious().getPrevious() : methodInsnNode.getPrevious());
                        } else {
                            adaptInstruction$saveToVariableIfNecessary(booleanRef, index, iIndexOf, intRef, type, node, insn);
                            adaptInstruction$insertPopInstruction(node, methodInsnNode, type5);
                        }
                    } else if (iIndexOf > 0) {
                        booleanRef = booleanRef4;
                        booleanRef2 = booleanRef3;
                        if (!adaptInstruction$canRemoveInsn(booleanRef3, index, iIndexOf, methodInsnNode, type5, false)) {
                            Type type6 = type4;
                            type = type6;
                            adaptInstruction$saveToVariableIfNecessary(booleanRef, index, iIndexOf, intRef, type6, node, insn);
                        }
                        booleanRef4 = booleanRef;
                        booleanRef3 = booleanRef2;
                    }
                    type4 = type;
                    booleanRef4 = booleanRef;
                    booleanRef3 = booleanRef2;
                }
                Type type7 = type4;
                if (booleanRef4.element) {
                    node.instructions.insertBefore(insn, new VarInsnNode(type7.getOpcode(21), node.maxLocals));
                }
            }
            node.instructions.remove(insn);
        } else if (opcode != 184) {
            if (opcode == 185) {
                if (!BoxingInterpreterKt.isJavaLangComparableCompareTo(insn)) {
                    throwCannotAdaptInstruction(insn);
                    throw null;
                }
                adaptJavaLangComparableCompareTo(node, insn, value);
            } else if (opcode == 192) {
                node.instructions.remove(insn);
            } else {
                if (opcode != 193) {
                    throwCannotAdaptInstruction(insn);
                    throw null;
                }
                node.instructions.insertBefore(insn, BoxedBasicValueKt.makePops(value.getUnboxedTypes()));
                node.instructions.set(insn, new InsnNode(4));
            }
        } else if (BoxingInterpreterKt.isAreEqualIntrinsic(insn)) {
            adaptAreEqualIntrinsic(node, insn, value);
        } else if (BoxingInterpreterKt.isJavaLangComparableCompareTo(insn)) {
            adaptJavaLangComparableCompareTo(node, insn, value);
        } else {
            if (!BoxingInterpreterKt.isJavaLangClassBoxing(insn) && !BoxingInterpreterKt.isJavaLangClassUnboxing(insn)) {
                throwCannotAdaptInstruction(insn);
                throw null;
            }
            node.instructions.remove(insn);
        }
        return intRef.element;
    }

    private static final boolean adaptInstruction$canRemoveInsn(Ref.BooleanRef booleanRef, int i, int i2, MethodInsnNode methodInsnNode, Type type, boolean z) {
        boolean z2 = false;
        if (!booleanRef.element) {
            return false;
        }
        int opcode = (i < i2 ? methodInsnNode.getPrevious().getPrevious() : methodInsnNode.getPrevious()).getOpcode();
        if (opcode == type.getOpcode(21) || (opcode != 92 ? !(opcode != 89 || !z || type.getSize() != 1) : !(!z || type.getSize() != 2))) {
            z2 = true;
        }
        booleanRef.element = z2;
        return z2;
    }

    private static final void adaptInstruction$insertPopInstruction(MethodNode methodNode, MethodInsnNode methodInsnNode, Type type) {
        methodNode.instructions.insertBefore(methodInsnNode, new InsnNode(type.getSize() == 2 ? 88 : 87));
    }

    private static final void adaptInstruction$saveToVariableIfNecessary(Ref.BooleanRef booleanRef, int i, int i2, Ref.IntRef intRef, Type type, MethodNode methodNode, AbstractInsnNode abstractInsnNode) {
        if (!booleanRef.element && i <= i2) {
            booleanRef.element = true;
            intRef.element = type.getSize();
            methodNode.instructions.insertBefore(abstractInsnNode, new VarInsnNode(type.getOpcode(54), methodNode.maxLocals));
        }
    }

    private final Void throwCannotAdaptInstruction(AbstractInsnNode insn) {
        throw new AssertionError("Cannot adapt instruction: " + InlineCodegenUtilsKt.getInsnText(insn));
    }

    private final void adaptAreEqualIntrinsic(MethodNode node, AbstractInsnNode insn, BoxedValueDescriptor value) {
        Type type = (Type) CollectionsKt.singleOrNull((List) value.getUnboxedTypes());
        Integer numValueOf = type != null ? Integer.valueOf(type.getSort()) : null;
        if ((numValueOf != null && numValueOf.intValue() == 1) || ((numValueOf != null && numValueOf.intValue() == 3) || ((numValueOf != null && numValueOf.intValue() == 4) || ((numValueOf != null && numValueOf.intValue() == 5) || (numValueOf != null && numValueOf.intValue() == 2))))) {
            adaptAreEqualIntrinsicForInt(node, insn);
            return;
        }
        if (numValueOf != null && numValueOf.intValue() == 7) {
            adaptAreEqualIntrinsicForLong(node, insn);
        } else if ((numValueOf == null || numValueOf.intValue() != 10) && numValueOf != null) {
            throw new AssertionError("Unexpected unboxed type kind: " + type);
        }
    }

    private final void adaptAreEqualIntrinsicForInt(MethodNode node, AbstractInsnNode insn) {
        InsnList insnList = node.instructions;
        AbstractInsnNode next = insn.getNext();
        if (next != null && (next.getOpcode() == 153 || next.getOpcode() == 154)) {
            fuseAreEqualWithBranch(node, insn, 160, 159);
            insnList.remove(insn);
            insnList.remove(next);
        } else {
            ifEqual1Else0(node, insn, 160);
            insnList.remove(insn);
        }
    }

    private final void adaptAreEqualIntrinsicForLong(MethodNode node, AbstractInsnNode insn) {
        InsnList insnList = node.instructions;
        insnList.insertBefore(insn, new InsnNode(148));
        AbstractInsnNode next = insn.getNext();
        if (next != null && (next.getOpcode() == 153 || next.getOpcode() == 154)) {
            fuseAreEqualWithBranch(node, insn, 154, 153);
            insnList.remove(insn);
            insnList.remove(next);
        } else {
            ifEqual1Else0(node, insn, 154);
            insnList.remove(insn);
        }
    }

    private final void fuseAreEqualWithBranch(MethodNode node, AbstractInsnNode insn, int ifEqualOpcode, int ifNotEqualOpcode) {
        InsnList insnList = node.instructions;
        AbstractInsnNode next = insn.getNext();
        boolean z = next instanceof JumpInsnNode;
        Intrinsics.checkNotNull(next, "null cannot be cast to non-null type org.jetbrains.org.objectweb.asm.tree.JumpInsnNode");
        LabelNode labelNode = ((JumpInsnNode) next).label;
        if (next.getOpcode() == 153) {
            insnList.insertBefore(insn, new JumpInsnNode(ifEqualOpcode, labelNode));
        } else {
            if (next.getOpcode() == 154) {
                insnList.insertBefore(insn, new JumpInsnNode(ifNotEqualOpcode, labelNode));
                return;
            }
            throw new AssertionError("IFEQ or IFNE expected: " + InlineCodegenUtilsKt.getInsnOpcodeText(next));
        }
    }

    private final void ifEqual1Else0(MethodNode node, AbstractInsnNode insn, int ifneOpcode) {
        InsnList insnList = node.instructions;
        LabelNode labelNode = new LabelNode(new Label());
        LabelNode labelNode2 = new LabelNode(new Label());
        insnList.insertBefore(insn, new JumpInsnNode(ifneOpcode, labelNode));
        insnList.insertBefore(insn, new InsnNode(4));
        insnList.insertBefore(insn, new JumpInsnNode(167, labelNode2));
        insnList.insertBefore(insn, labelNode);
        insnList.insertBefore(insn, new InsnNode(3));
        insnList.insertBefore(insn, labelNode2);
    }

    private final void adaptJavaLangComparableCompareTo(MethodNode node, AbstractInsnNode insn, BoxedValueDescriptor value) {
        Type type = (Type) CollectionsKt.single((List) value.getUnboxedTypes());
        switch (type.getSort()) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
                adaptJavaLangComparableCompareToForInt(node, insn);
                return;
            case 6:
                adaptJavaLangComparableCompareToForFloat(node, insn);
                return;
            case 7:
                adaptJavaLangComparableCompareToForLong(node, insn);
                return;
            case 8:
                adaptJavaLangComparableCompareToForDouble(node, insn);
                return;
            default:
                throw new AssertionError("Unexpected unboxed type kind: " + type);
        }
    }

    private final void adaptJavaLangComparableCompareToForInt(MethodNode node, AbstractInsnNode insn) {
        InsnList insnList = node.instructions;
        AbstractInsnNode next = insn.getNext();
        AbstractInsnNode next2 = next != null ? next.getNext() : null;
        if (next != null && next2 != null && next.getOpcode() == 3 && next2.getOpcode() >= 159 && next2.getOpcode() <= 164) {
            insnList.remove(insn);
            insnList.remove(next);
        } else {
            if (next != null && next.getOpcode() >= 153 && next.getOpcode() <= 158) {
                JumpInsnNode jumpInsnNode = (JumpInsnNode) next;
                insnList.insertBefore(insn, new JumpInsnNode(jumpInsnNode.getOpcode() + 6, jumpInsnNode.label));
                insnList.remove(insn);
                insnList.remove(next);
                return;
            }
            insnList.set(insn, new MethodInsnNode(184, "kotlin/jvm/internal/Intrinsics", "compare", "(II)I", false));
        }
    }

    private final void adaptJavaLangComparableCompareToForLong(MethodNode node, AbstractInsnNode insn) {
        node.instructions.set(insn, new InsnNode(148));
    }

    private final void adaptJavaLangComparableCompareToForFloat(MethodNode node, AbstractInsnNode insn) {
        node.instructions.set(insn, new MethodInsnNode(184, "java/lang/Float", "compare", "(FF)I", false));
    }

    private final void adaptJavaLangComparableCompareToForDouble(MethodNode node, AbstractInsnNode insn) {
        node.instructions.set(insn, new MethodInsnNode(184, "java/lang/Double", "compare", "(DD)I", false));
    }
}

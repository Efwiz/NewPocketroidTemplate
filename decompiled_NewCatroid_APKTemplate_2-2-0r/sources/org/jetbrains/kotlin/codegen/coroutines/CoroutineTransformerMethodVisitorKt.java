package org.jetbrains.kotlin.codegen.coroutines;

import com.reandroid.dex.sections.Marker;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.SequencesKt;
import kotlin.text.StringsKt;
import org.jetbrains.kotlin.codegen.ClassBuilder;
import org.jetbrains.kotlin.codegen.inline.InlineCodegenUtilsKt;
import org.jetbrains.kotlin.codegen.inline.ReifiedTypeInliner;
import org.jetbrains.kotlin.codegen.optimization.common.InsnSequence;
import org.jetbrains.kotlin.codegen.optimization.common.UtilKt;
import org.jetbrains.kotlin.codegen.optimization.common.VariableLivenessFrame;
import org.jetbrains.kotlin.codegen.optimization.common.VariableLivenessKt;
import org.jetbrains.kotlin.resolve.jvm.AsmTypes;
import org.jetbrains.org.objectweb.asm.Type;
import org.jetbrains.org.objectweb.asm.commons.InstructionAdapter;
import org.jetbrains.org.objectweb.asm.tree.AbstractInsnNode;
import org.jetbrains.org.objectweb.asm.tree.InsnList;
import org.jetbrains.org.objectweb.asm.tree.LabelNode;
import org.jetbrains.org.objectweb.asm.tree.LocalVariableNode;
import org.jetbrains.org.objectweb.asm.tree.MethodNode;
import org.jetbrains.org.objectweb.asm.tree.VarInsnNode;

/* JADX INFO: compiled from: CoroutineTransformerMethodVisitor.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u0088\u0001\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\b\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a+\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\f2\u0006\u0010\u000e\u001a\u00020\u00032\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0003H\u0002¢\u0006\u0002\u0010\u0012\u001a\u0018\u0010\u0013\u001a\u00020\u00012\u0006\u0010\u000e\u001a\u00020\u00032\u0006\u0010\u0014\u001a\u00020\u0001H\u0000\u001a+\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\r0\f2\u0006\u0010\u000e\u001a\u00020\u00032\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0003H\u0002¢\u0006\u0002\u0010\u0012\u001a:\u0010\u0016\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\u00010\u00180\u00172\u0006\u0010\u000e\u001a\u00020\u00032\u0006\u0010\u0019\u001a\u00020\u00012\u0006\u0010\u001a\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0003H\u0002\u001a\u0010\u0010\u001b\u001a\u00020\u00102\u0006\u0010\u0014\u001a\u00020\u0001H\u0002\u001a\u0018\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020\u0001H\u0000\u001a&\u0010!\u001a\u00020\u001d2\u0006\u0010\"\u001a\u00020\u001f2\u0006\u0010#\u001a\u00020\u00102\f\u0010$\u001a\b\u0012\u0004\u0012\u00020&0%H\u0002\u001a%\u0010'\u001a\u00020(2\u0017\u0010)\u001a\u0013\u0012\u0004\u0012\u00020+\u0012\u0004\u0012\u00020\u001d0*¢\u0006\u0002\b,H\u0086\bø\u0001\u0000\u001a\u001b\u0010-\u001a\u00020\u0010*\b\u0012\u0004\u0012\u00020.0%2\u0006\u0010/\u001a\u000200H\u0080\u0002\u001a8\u00101\u001a\u00020\u0010*\u0002022\u0006\u0010\"\u001a\u00020\u001f2\f\u0010$\u001a\b\u0012\u0004\u0012\u00020&0%2\u0006\u00103\u001a\u00020&2\f\u00104\u001a\b\u0012\u0004\u0012\u0002050%H\u0002\u001a\u0014\u00106\u001a\u00020\u0003*\u00020\r2\u0006\u00107\u001a\u00020\u0001H\u0002\u001a@\u00108\u001a\u00020\u001d*\u00020+2\b\u00109\u001a\u0004\u0018\u00010\r2\u0006\u0010\u001e\u001a\u00020\u001f2\u0006\u0010\u001a\u001a\u00020\u00102\b\u0010:\u001a\u0004\u0018\u00010\u00032\u0006\u0010;\u001a\u00020\u00032\u0006\u0010<\u001a\u00020=H\u0000\u001a\u0014\u0010>\u001a\u00020\u001d*\u00020+2\u0006\u0010?\u001a\u00020\u0001H\u0002\u001a\u001a\u0010@\u001a\u00020\u0003*\u00020\u001f2\f\u00104\u001a\b\u0012\u0004\u0012\u0002050%H\u0002\u001a\n\u0010A\u001a\u00020\r*\u00020\r\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0082T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0002\u001a\u00020\u0003X\u0082T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0004\u001a\u00020\u0003X\u0082T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0005\u001a\u00020\u0003X\u0082T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0006\u001a\u00020\u0003X\u0082T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0007\u001a\u00020\u0003X\u0082T¢\u0006\u0002\n\u0000\"\u000e\u0010\b\u001a\u00020\u0003X\u0082T¢\u0006\u0002\n\u0000\"\u000e\u0010\t\u001a\u00020\u0003X\u0082T¢\u0006\u0002\n\u0000\"\u000e\u0010\n\u001a\u00020\u0003X\u0082T¢\u0006\u0002\n\u0000*$\b\u0002\u0010B\"\u000e\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020C0\u00182\u000e\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020C0\u0018*(\b\u0002\u0010D\"\u0010\u0012\u0004\u0012\u00020\u0001\u0012\u0006\u0012\u0004\u0018\u00010C0\u00182\u0010\u0012\u0004\u0012\u00020\u0001\u0012\u0006\u0012\u0004\u0018\u00010C0\u0018\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006E"}, d2 = {"COROUTINES_DEBUG_METADATA_VERSION", "", "COROUTINES_METADATA_CLASS_NAME_JVM_NAME", "", "COROUTINES_METADATA_INDEX_TO_LABEL_JVM_NAME", "COROUTINES_METADATA_LINE_NUMBERS_JVM_NAME", "COROUTINES_METADATA_LOCAL_NAMES_JVM_NAME", "COROUTINES_METADATA_METHOD_NAME_JVM_NAME", "COROUTINES_METADATA_SOURCE_FILE_JVM_NAME", "COROUTINES_METADATA_SPILLED_JVM_NAME", "COROUTINES_METADATA_VERSION_JVM_NAME", "getAllParameterTypes", "", "Lorg/jetbrains/org/objectweb/asm/Type;", "desc", "hasDispatchReceiver", "", "thisName", "(Ljava/lang/String;ZLjava/lang/String;)[Lorg/jetbrains/org/objectweb/asm/Type;", "getLastParameterIndex", "access", "getParameterTypesForCoroutineConstructor", "getParameterTypesIndicesForCoroutineConstructor", "", "Lkotlin/Pair;", "containingFunctionAccess", "needDispatchReceiver", "isStatic", "replaceFakeContinuationsWithRealOnes", "", "methodNode", "Lorg/jetbrains/org/objectweb/asm/tree/MethodNode;", "continuationIndex", "updateLvtAccordingToLiveness", "method", "isForNamedFunction", "suspensionPoints", "", "Lorg/jetbrains/org/objectweb/asm/tree/LabelNode;", "withInstructionAdapter", "Lorg/jetbrains/org/objectweb/asm/tree/InsnList;", "block", "Lkotlin/Function1;", "Lorg/jetbrains/org/objectweb/asm/commons/InstructionAdapter;", "Lkotlin/ExtensionFunctionType;", "contains", "Lorg/jetbrains/kotlin/codegen/coroutines/SuspensionPoint;", "insn", "Lorg/jetbrains/org/objectweb/asm/tree/AbstractInsnNode;", "extendRecordIfPossible", "Lorg/jetbrains/org/objectweb/asm/tree/LocalVariableNode;", "endLabel", "liveness", "Lorg/jetbrains/kotlin/codegen/optimization/common/VariableLivenessFrame;", "fieldNameForVar", "index", "generateContinuationConstructorCall", "objectTypeForState", "internalNameForDispatchReceiver", "containingClassInternalName", "classBuilderForCoroutineState", "Lorg/jetbrains/kotlin/codegen/ClassBuilder;", "generateResumeWithExceptionCheck", "dataIndex", "nodeTextWithLiveness", "normalize", "PrimitiveToSpill", "Lorg/jetbrains/kotlin/codegen/coroutines/SpillableVariable;", "ReferenceToSpill", Marker.BACKEND}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class CoroutineTransformerMethodVisitorKt {
    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean isStatic(int i) {
        return (i & 8) != 0;
    }

    public static final void generateContinuationConstructorCall(InstructionAdapter instructionAdapter, Type type, MethodNode methodNode, boolean z, String str, String containingClassInternalName, ClassBuilder classBuilderForCoroutineState) {
        Intrinsics.checkNotNullParameter(instructionAdapter, "<this>");
        Intrinsics.checkNotNullParameter(methodNode, "methodNode");
        Intrinsics.checkNotNullParameter(containingClassInternalName, "containingClassInternalName");
        Intrinsics.checkNotNullParameter(classBuilderForCoroutineState, "classBuilderForCoroutineState");
        instructionAdapter.anew(type);
        instructionAdapter.dup();
        String desc = methodNode.desc;
        Intrinsics.checkNotNullExpressionValue(desc, "desc");
        for (Pair<Type, Integer> pair : getParameterTypesIndicesForCoroutineConstructor(desc, methodNode.access, z, str == null ? containingClassInternalName : str)) {
            instructionAdapter.load(pair.component2().intValue(), pair.component1());
        }
        String thisName = classBuilderForCoroutineState.getThisName();
        Type type2 = Type.VOID_TYPE;
        String desc2 = methodNode.desc;
        Intrinsics.checkNotNullExpressionValue(desc2, "desc");
        if (str == null) {
            str = containingClassInternalName;
        }
        Type[] parameterTypesForCoroutineConstructor = getParameterTypesForCoroutineConstructor(desc2, z, str);
        instructionAdapter.invokespecial(thisName, "<init>", Type.getMethodDescriptor(type2, (Type[]) Arrays.copyOf(parameterTypesForCoroutineConstructor, parameterTypesForCoroutineConstructor.length)), false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void generateResumeWithExceptionCheck(InstructionAdapter instructionAdapter, int i) {
        instructionAdapter.load(i, AsmTypes.OBJECT_TYPE);
        instructionAdapter.invokestatic("kotlin/ResultKt", "throwOnFailure", ReifiedTypeInliner.pluginIntrinsicsMarkerSignature, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String fieldNameForVar(Type type, int i) {
        StringBuilder sb = new StringBuilder();
        String descriptor = type.getDescriptor();
        Intrinsics.checkNotNullExpressionValue(descriptor, "getDescriptor(...)");
        return sb.append(StringsKt.first(descriptor) + "$").append(i).toString();
    }

    public static final InsnList withInstructionAdapter(Function1<? super InstructionAdapter, Unit> block) {
        Intrinsics.checkNotNullParameter(block, "block");
        MethodNode methodNode = new MethodNode();
        block.invoke(new InstructionAdapter(methodNode));
        InsnList instructions = methodNode.instructions;
        Intrinsics.checkNotNullExpressionValue(instructions, "instructions");
        return instructions;
    }

    public static final Type normalize(Type type) {
        Intrinsics.checkNotNullParameter(type, "<this>");
        int sort = type.getSort();
        if (sort != 9 && sort != 10) {
            return type;
        }
        Type OBJECT_TYPE = AsmTypes.OBJECT_TYPE;
        Intrinsics.checkNotNullExpressionValue(OBJECT_TYPE, "OBJECT_TYPE");
        return OBJECT_TYPE;
    }

    public static final boolean contains(List<SuspensionPoint> list, AbstractInsnNode insn) {
        Intrinsics.checkNotNullParameter(list, "<this>");
        Intrinsics.checkNotNullParameter(insn, "insn");
        List<SuspensionPoint> list2 = list;
        if ((list2 instanceof Collection) && list2.isEmpty()) {
            return false;
        }
        Iterator<T> it = list2.iterator();
        while (it.hasNext()) {
            if (((SuspensionPoint) it.next()).contains(insn)) {
                return true;
            }
        }
        return false;
    }

    public static final int getLastParameterIndex(String desc, int i) {
        Intrinsics.checkNotNullParameter(desc, "desc");
        Type[] argumentTypes = Type.getArgumentTypes(desc);
        Intrinsics.checkNotNullExpressionValue(argumentTypes, "getArgumentTypes(...)");
        Iterator it = ArraysKt.dropLast(argumentTypes, 1).iterator();
        int size = 0;
        while (it.hasNext()) {
            size += ((Type) it.next()).getSize();
        }
        return size + (!isStatic(i) ? 1 : 0);
    }

    private static final Type[] getParameterTypesForCoroutineConstructor(String str, boolean z, String str2) {
        Object[] array = CollectionsKt.listOfNotNull(!z ? null : Type.getObjectType(str2)).toArray(new Type[0]);
        Type[] argumentTypes = Type.getArgumentTypes(str);
        Intrinsics.checkNotNullExpressionValue(argumentTypes, "getArgumentTypes(...)");
        Object objLast = ArraysKt.last(argumentTypes);
        Intrinsics.checkNotNullExpressionValue(objLast, "last(...)");
        return (Type[]) ArraysKt.plus(array, objLast);
    }

    private static final Collection<Pair<Type, Integer>> getParameterTypesIndicesForCoroutineConstructor(String str, int i, boolean z, String str2) {
        ArrayList arrayList = new ArrayList();
        int size = 0;
        if (z) {
            arrayList.add(TuplesKt.to(Type.getObjectType(str2), 0));
        }
        Iterator it = ArraysKt.dropLast(getAllParameterTypes(str, !isStatic(i), str2), 1).iterator();
        while (it.hasNext()) {
            size += ((Type) it.next()).getSize();
        }
        arrayList.add(TuplesKt.to(CoroutineCodegenUtilKt.CONTINUATION_ASM_TYPE, Integer.valueOf(size)));
        return arrayList;
    }

    private static final Type[] getAllParameterTypes(String str, boolean z, String str2) {
        Object[] array = CollectionsKt.listOfNotNull(!z ? null : Type.getObjectType(str2)).toArray(new Type[0]);
        Type[] argumentTypes = Type.getArgumentTypes(str);
        Intrinsics.checkNotNullExpressionValue(argumentTypes, "getArgumentTypes(...)");
        return (Type[]) ArraysKt.plus(array, (Object[]) argumentTypes);
    }

    public static final void replaceFakeContinuationsWithRealOnes(MethodNode methodNode, int i) {
        Intrinsics.checkNotNullParameter(methodNode, "methodNode");
        InsnList instructions = methodNode.instructions;
        Intrinsics.checkNotNullExpressionValue(instructions, "instructions");
        for (AbstractInsnNode abstractInsnNode : SequencesKt.toList(SequencesKt.filter(UtilKt.asSequence(instructions), CoroutineTransformerMethodVisitorKt$replaceFakeContinuationsWithRealOnes$fakeContinuations$1.INSTANCE))) {
            InsnList instructions2 = methodNode.instructions;
            Intrinsics.checkNotNullExpressionValue(instructions2, "instructions");
            UtilKt.removeAll(instructions2, CollectionsKt.listOf((Object[]) new AbstractInsnNode[]{abstractInsnNode.getPrevious().getPrevious(), abstractInsnNode.getPrevious()}));
            methodNode.instructions.set(abstractInsnNode, new VarInsnNode(25, i));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code duplicated, block: B:55:0x00e9  */
    /* JADX WARN: Code duplicated, block: B:66:0x0139  */
    public static final void updateLvtAccordingToLiveness(MethodNode methodNode, boolean z, List<? extends LabelNode> list) {
        LocalVariableNode localVariableNodeUpdateLvtAccordingToLiveness$findRecord;
        LabelNode labelNodeUpdateLvtAccordingToLiveness$min;
        boolean zExtendRecordIfPossible;
        List<VariableLivenessFrame> listAnalyzeLiveness = VariableLivenessKt.analyzeLiveness(methodNode);
        ArrayList<LocalVariableNode> arrayList = new ArrayList();
        Iterator<LocalVariableNode> it = methodNode.localVariables.iterator();
        while (it.hasNext()) {
            arrayList.add(it.next());
        }
        methodNode.localVariables.clear();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        int i = !z ? 1 : 0;
        int i2 = methodNode.maxLocals;
        while (i < i2) {
            ArrayList arrayList2 = arrayList;
            if (!(arrayList2 instanceof Collection) || !arrayList2.isEmpty()) {
                Iterator it2 = arrayList2.iterator();
                while (it2.hasNext()) {
                    if (((LocalVariableNode) it2.next()).index == i) {
                        int size = methodNode.instructions.size() - 1;
                        int i3 = 0;
                        LabelNode labelNode = null;
                        while (i3 < size) {
                            AbstractInsnNode abstractInsnNode = methodNode.instructions.get(i3);
                            if (!updateLvtAccordingToLiveness$isAlive(listAnalyzeLiveness, i3, i) && updateLvtAccordingToLiveness$isAlive(listAnalyzeLiveness, i3 + 1, i)) {
                                labelNode = abstractInsnNode instanceof LabelNode ? (LabelNode) abstractInsnNode : null;
                                if (labelNode == null) {
                                    Intrinsics.checkNotNull(abstractInsnNode);
                                    AbstractInsnNode next = abstractInsnNode.getNext();
                                    while (next != null && !(next instanceof LabelNode)) {
                                        next = next.getNext();
                                    }
                                    labelNode = next instanceof LabelNode ? (LabelNode) next : null;
                                }
                            }
                            if (updateLvtAccordingToLiveness$isAlive(listAnalyzeLiveness, i3, i) && !updateLvtAccordingToLiveness$isAlive(listAnalyzeLiveness, i3 + 1, i) && (localVariableNodeUpdateLvtAccordingToLiveness$findRecord = updateLvtAccordingToLiveness$findRecord(arrayList, methodNode, i3, i)) != null && !Intrinsics.areEqual(localVariableNodeUpdateLvtAccordingToLiveness$findRecord.f1014name, CoroutineCodegenUtilKt.CONTINUATION_VARIABLE_NAME) && !Intrinsics.areEqual(localVariableNodeUpdateLvtAccordingToLiveness$findRecord.f1014name, CoroutineConstantsKt.SUSPEND_CALL_RESULT_NAME)) {
                                LabelNode labelNodeUpdateLvtAccordingToLiveness$nextLabel = updateLvtAccordingToLiveness$nextLabel(abstractInsnNode.getNext());
                                if (labelNodeUpdateLvtAccordingToLiveness$nextLabel != null) {
                                    LabelNode end = localVariableNodeUpdateLvtAccordingToLiveness$findRecord.end;
                                    Intrinsics.checkNotNullExpressionValue(end, "end");
                                    labelNodeUpdateLvtAccordingToLiveness$min = updateLvtAccordingToLiveness$min(methodNode, end, labelNodeUpdateLvtAccordingToLiveness$nextLabel);
                                    if (labelNodeUpdateLvtAccordingToLiveness$min == null) {
                                        labelNodeUpdateLvtAccordingToLiveness$min = localVariableNodeUpdateLvtAccordingToLiveness$findRecord.end;
                                    }
                                } else {
                                    labelNodeUpdateLvtAccordingToLiveness$min = localVariableNodeUpdateLvtAccordingToLiveness$findRecord.end;
                                }
                                LabelNode labelNode2 = labelNodeUpdateLvtAccordingToLiveness$min;
                                LabelNode labelNode3 = labelNode == null ? localVariableNodeUpdateLvtAccordingToLiveness$findRecord.start : labelNode;
                                LocalVariableNode localVariableNode = (LocalVariableNode) linkedHashMap.get(localVariableNodeUpdateLvtAccordingToLiveness$findRecord);
                                if (localVariableNode != null) {
                                    LabelNode end2 = localVariableNodeUpdateLvtAccordingToLiveness$findRecord.end;
                                    Intrinsics.checkNotNullExpressionValue(end2, "end");
                                    zExtendRecordIfPossible = extendRecordIfPossible(localVariableNode, methodNode, list, end2, listAnalyzeLiveness);
                                } else {
                                    zExtendRecordIfPossible = false;
                                }
                                if (!zExtendRecordIfPossible) {
                                    LocalVariableNode localVariableNode2 = new LocalVariableNode(localVariableNodeUpdateLvtAccordingToLiveness$findRecord.f1014name, localVariableNodeUpdateLvtAccordingToLiveness$findRecord.desc, localVariableNodeUpdateLvtAccordingToLiveness$findRecord.signature, labelNode3, labelNode2, localVariableNodeUpdateLvtAccordingToLiveness$findRecord.index);
                                    linkedHashMap.put(localVariableNodeUpdateLvtAccordingToLiveness$findRecord, localVariableNode2);
                                    methodNode.localVariables.add(localVariableNode2);
                                    LabelNode end3 = localVariableNodeUpdateLvtAccordingToLiveness$findRecord.end;
                                    Intrinsics.checkNotNullExpressionValue(end3, "end");
                                    extendRecordIfPossible(localVariableNode2, methodNode, list, end3, listAnalyzeLiveness);
                                }
                            }
                            i3++;
                            i2 = i2;
                            size = size;
                            labelNode = labelNode;
                        }
                        break;
                    }
                }
            }
            i++;
            i2 = i2;
        }
        for (LocalVariableNode localVariableNode3 : arrayList) {
            if (!Intrinsics.areEqual(localVariableNode3.f1014name, CoroutineCodegenUtilKt.CONTINUATION_VARIABLE_NAME) && !Intrinsics.areEqual(localVariableNode3.f1014name, CoroutineConstantsKt.SUSPEND_CALL_RESULT_NAME)) {
                String name2 = localVariableNode3.f1014name;
                Intrinsics.checkNotNullExpressionValue(name2, "name");
                if (!InlineCodegenUtilsKt.isFakeLocalVariableForInline(name2)) {
                    if (Intrinsics.areEqual(localVariableNode3.f1014name, "this") && !z) {
                        methodNode.localVariables.add(localVariableNode3);
                    }
                }
            }
            methodNode.localVariables.add(localVariableNode3);
        }
    }

    private static final LocalVariableNode updateLvtAccordingToLiveness$findRecord(List<? extends LocalVariableNode> list, MethodNode methodNode, int i, int i2) {
        for (LocalVariableNode localVariableNode : list) {
            if (localVariableNode.index == i2 && methodNode.instructions.indexOf(localVariableNode.start) <= i && i < methodNode.instructions.indexOf(localVariableNode.end)) {
                return localVariableNode;
            }
        }
        return null;
    }

    private static final boolean updateLvtAccordingToLiveness$isAlive(List<VariableLivenessFrame> list, int i, int i2) {
        return list.get(i).isAlive(i2);
    }

    private static final LabelNode updateLvtAccordingToLiveness$nextLabel(AbstractInsnNode abstractInsnNode) {
        while (abstractInsnNode != null) {
            if (abstractInsnNode instanceof LabelNode) {
                return (LabelNode) abstractInsnNode;
            }
            abstractInsnNode = abstractInsnNode.getNext();
        }
        return null;
    }

    private static final LabelNode updateLvtAccordingToLiveness$min(MethodNode methodNode, LabelNode labelNode, LabelNode labelNode2) {
        return methodNode.instructions.indexOf(labelNode) < methodNode.instructions.indexOf(labelNode2) ? labelNode : labelNode2;
    }

    private static final boolean extendRecordIfPossible(LocalVariableNode localVariableNode, MethodNode methodNode, List<? extends LabelNode> list, LabelNode labelNode, List<VariableLivenessFrame> list2) {
        Object next;
        LabelNode end;
        Iterator<T> it = list.iterator();
        do {
            if (!it.hasNext()) {
                next = null;
                break;
            }
            next = it.next();
            end = localVariableNode.end;
            Intrinsics.checkNotNullExpressionValue(end, "end");
        } while (!SequencesKt.contains(new InsnSequence(end, labelNode), (LabelNode) next));
        LabelNode labelNode2 = (LabelNode) next;
        if (labelNode2 == null) {
            labelNode2 = labelNode;
        }
        LabelNode next2 = localVariableNode.end;
        int iIndexOf = methodNode.instructions.indexOf(next2);
        while (next2 != null && !Intrinsics.areEqual(next2, labelNode2)) {
            if (list2.get(iIndexOf).getControlFlowMerge()) {
                return false;
            }
            if (next2.getOpcode() == 176 && !Intrinsics.areEqual(labelNode2, labelNode)) {
                return false;
            }
            if (UtilKt.isStoreOperation(next2) && ((VarInsnNode) next2).var == iIndexOf) {
                return false;
            }
            next2 = next2.getNext();
            iIndexOf++;
        }
        localVariableNode.end = labelNode2;
        return true;
    }
}

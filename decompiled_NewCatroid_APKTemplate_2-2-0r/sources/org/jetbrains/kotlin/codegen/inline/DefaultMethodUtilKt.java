package org.jetbrains.kotlin.codegen.inline;

import com.reandroid.dex.sections.Marker;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.function.Predicate;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.Triple;
import kotlin.TuplesKt;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlin.ranges.RangesKt;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import org.jetbrains.kotlin.codegen.optimization.common.InsnSequence;
import org.jetbrains.kotlin.codegen.optimization.common.UtilKt;
import org.jetbrains.kotlin.resolve.jvm.AsmTypes;
import org.jetbrains.kotlin.util.OperatorNameConventions;
import org.jetbrains.org.objectweb.asm.ClassReader;
import org.jetbrains.org.objectweb.asm.ClassVisitor;
import org.jetbrains.org.objectweb.asm.MethodVisitor;
import org.jetbrains.org.objectweb.asm.Type;
import org.jetbrains.org.objectweb.asm.commons.Method;
import org.jetbrains.org.objectweb.asm.tree.AbstractInsnNode;
import org.jetbrains.org.objectweb.asm.tree.FieldInsnNode;
import org.jetbrains.org.objectweb.asm.tree.InsnList;
import org.jetbrains.org.objectweb.asm.tree.InsnNode;
import org.jetbrains.org.objectweb.asm.tree.JumpInsnNode;
import org.jetbrains.org.objectweb.asm.tree.LabelNode;
import org.jetbrains.org.objectweb.asm.tree.LineNumberNode;
import org.jetbrains.org.objectweb.asm.tree.LocalVariableNode;
import org.jetbrains.org.objectweb.asm.tree.MethodInsnNode;
import org.jetbrains.org.objectweb.asm.tree.MethodNode;
import org.jetbrains.org.objectweb.asm.tree.TryCatchBlockNode;
import org.jetbrains.org.objectweb.asm.tree.TypeInsnNode;
import org.jetbrains.org.objectweb.asm.tree.VarInsnNode;

/* JADX INFO: compiled from: defaultMethodUtil.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000d\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u001e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001f\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0012\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\u001a#\u0010\u0000\u001a\u00020\u00012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\u0006\u0010\u0005\u001a\u00020\u0006H\u0002¢\u0006\u0002\u0010\u0007\u001a@\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u00062\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00060\t2\u0006\u0010\u000f\u001a\u00020\u00062\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00060\u0011\u001aD\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\n0\t2\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00140\t2\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00170\u00162\u0018\u0010\u0018\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0017\u0012\u0004\u0012\u00020\u00170\u001a0\u0019H\u0002\u001a\u001e\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020\u00042\u0006\u0010 \u001a\u00020!\u001a\u001c\u0010\"\u001a\u00020\u001c*\u00020\f2\u0006\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020\u0004H\u0002¨\u0006#"}, d2 = {"defaultLambdaFakeCallStub", "Lorg/jetbrains/org/objectweb/asm/tree/MethodInsnNode;", "args", "", "Lorg/jetbrains/org/objectweb/asm/Type;", "lambdaOffset", "", "([Lorg/jetbrains/org/objectweb/asm/Type;I)Lorg/jetbrains/org/objectweb/asm/tree/MethodInsnNode;", "expandMaskConditionsAndUpdateVariableNodes", "", "Lorg/jetbrains/kotlin/codegen/inline/ExtractedDefaultLambda;", "node", "Lorg/jetbrains/org/objectweb/asm/tree/MethodNode;", "maskStartIndex", "masks", "methodHandlerIndex", "validOffsets", "", "extractDefaultLambdasInfo", "conditions", "Lorg/jetbrains/kotlin/codegen/inline/Condition;", "toDelete", "", "Lorg/jetbrains/org/objectweb/asm/tree/AbstractInsnNode;", "toInsert", "", "Lkotlin/Pair;", "loadDefaultLambdaBody", "Lorg/jetbrains/kotlin/codegen/inline/SMAPAndMethodNode;", "classBytes", "", "classType", "isPropertyReference", "", "inlineBridge", Marker.BACKEND}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class DefaultMethodUtilKt {
    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean expandMaskConditionsAndUpdateVariableNodes$isMaskIndex(int i, List<Integer> list, int i2) {
        return i <= i2 && i2 < i + list.size();
    }

    public static final List<ExtractedDefaultLambda> expandMaskConditionsAndUpdateVariableNodes(MethodNode node, final int i, final List<Integer> masks, final int i2, final Collection<Integer> validOffsets) {
        LinkedHashMap linkedHashMapEmptyMap;
        LocalVariableNode localVariableNode;
        Intrinsics.checkNotNullParameter(node, "node");
        Intrinsics.checkNotNullParameter(masks, "masks");
        Intrinsics.checkNotNullParameter(validOffsets, "validOffsets");
        InsnList instructions = node.instructions;
        Intrinsics.checkNotNullExpressionValue(instructions, "instructions");
        Sequence sequenceFilter = SequencesKt.filter(SequencesKt.takeWhile(UtilKt.asSequence(instructions), new Function1<AbstractInsnNode, Boolean>() { // from class: org.jetbrains.kotlin.codegen.inline.DefaultMethodUtilKt$expandMaskConditionsAndUpdateVariableNodes$maskProcessingHeader$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(AbstractInsnNode it) {
                Intrinsics.checkNotNullParameter(it, "it");
                if (it instanceof VarInsnNode) {
                    VarInsnNode varInsnNode = (VarInsnNode) it;
                    if (DefaultMethodUtilKt.expandMaskConditionsAndUpdateVariableNodes$isMaskIndex(i, masks, varInsnNode.var)) {
                        return Boolean.valueOf(varInsnNode.getOpcode() == 21);
                    }
                    if (i2 == varInsnNode.var) {
                        return Boolean.valueOf(varInsnNode.getOpcode() == 25);
                    }
                }
                return true;
            }
        }), new Function1<Object, Boolean>() { // from class: org.jetbrains.kotlin.codegen.inline.DefaultMethodUtilKt$expandMaskConditionsAndUpdateVariableNodes$$inlined$filterIsInstance$1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(Object obj) {
                return Boolean.valueOf(obj instanceof VarInsnNode);
            }
        });
        Intrinsics.checkNotNull(sequenceFilter, "null cannot be cast to non-null type kotlin.sequences.Sequence<R of kotlin.sequences.SequencesKt___SequencesKt.filterIsInstance>");
        List list = SequencesKt.toList(SequencesKt.mapNotNull(sequenceFilter, new Function1<VarInsnNode, Condition>() { // from class: org.jetbrains.kotlin.codegen.inline.DefaultMethodUtilKt$expandMaskConditionsAndUpdateVariableNodes$conditions$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Condition invoke(VarInsnNode it) {
                AbstractInsnNode next;
                AbstractInsnNode next2;
                AbstractInsnNode next3;
                AbstractInsnNode next4;
                AbstractInsnNode next5;
                AbstractInsnNode next6;
                Intrinsics.checkNotNullParameter(it, "it");
                AbstractInsnNode next7 = null;
                if (DefaultMethodUtilKt.expandMaskConditionsAndUpdateVariableNodes$isMaskIndex(i, masks, it.var) && (next3 = it.getNext()) != null && (next4 = next3.getNext()) != null && next4.getOpcode() == 126 && (next5 = it.getNext().getNext().getNext()) != null && next5.getOpcode() == 153) {
                    AbstractInsnNode next8 = it.getNext();
                    if (next8 != null && (next6 = next8.getNext()) != null) {
                        next7 = next6.getNext();
                    }
                    Intrinsics.checkNotNull(next7, "null cannot be cast to non-null type org.jetbrains.org.objectweb.asm.tree.JumpInsnNode");
                    JumpInsnNode jumpInsnNode = (JumpInsnNode) next7;
                    int iIntValue = masks.get(it.var - i).intValue();
                    AbstractInsnNode next9 = it.getNext();
                    Intrinsics.checkNotNullExpressionValue(next9, "getNext(...)");
                    int constant = InlineCodegenUtilsKt.getConstant(next9);
                    AbstractInsnNode previous = jumpInsnNode.label.getPrevious();
                    Intrinsics.checkNotNull(previous, "null cannot be cast to non-null type org.jetbrains.org.objectweb.asm.tree.VarInsnNode");
                    return new Condition(iIntValue, constant, it, jumpInsnNode, (VarInsnNode) previous);
                }
                if (i2 != it.var || (next = it.getNext()) == null || next.getOpcode() != 198 || (next2 = it.getNext().getNext()) == null || next2.getOpcode() != 187) {
                    return null;
                }
                AbstractInsnNode next10 = it.getNext();
                Intrinsics.checkNotNull(next10, "null cannot be cast to non-null type org.jetbrains.org.objectweb.asm.tree.JumpInsnNode");
                return new Condition(0, 0, it, (JumpInsnNode) next10, null);
            }
        }));
        final LinkedHashSet linkedHashSet = new LinkedHashSet();
        ArrayList<Pair> arrayList = new ArrayList();
        List<Condition> list2 = list;
        ArrayList arrayList2 = new ArrayList();
        for (Object obj : list2) {
            Condition condition = (Condition) obj;
            if (condition.getExpandNotDelete() && validOffsets.contains(Integer.valueOf(condition.getVarIndex()))) {
                arrayList2.add(obj);
            }
        }
        LinkedHashSet linkedHashSet2 = linkedHashSet;
        List<ExtractedDefaultLambda> listExtractDefaultLambdasInfo = extractDefaultLambdasInfo(arrayList2, linkedHashSet2, arrayList);
        List<LocalVariableNode> list3 = node.localVariables;
        if (list3 != null) {
            ArrayList arrayList3 = new ArrayList();
            for (Object obj2 : list3) {
                if (((LocalVariableNode) obj2).index < i) {
                    arrayList3.add(obj2);
                }
            }
            ArrayList arrayList4 = arrayList3;
            linkedHashMapEmptyMap = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(arrayList4, 10)), 16));
            for (Object obj3 : arrayList4) {
                linkedHashMapEmptyMap.put(Integer.valueOf(((LocalVariableNode) obj3).index), obj3);
            }
        } else {
            linkedHashMapEmptyMap = MapsKt.emptyMap();
        }
        for (Condition condition2 : list2) {
            JumpInsnNode jumpInstruction = condition2.getJumpInstruction();
            Iterator<AbstractInsnNode> it = new InsnSequence(condition2.getMaskInstruction(), condition2.getExpandNotDelete() ? jumpInstruction.getNext() : jumpInstruction.label).iterator();
            while (it.hasNext()) {
                linkedHashSet.add(it.next());
            }
            if (condition2.getExpandNotDelete() && (localVariableNode = (LocalVariableNode) linkedHashMapEmptyMap.get(Integer.valueOf(condition2.getVarIndex()))) != null) {
                localVariableNode.start = condition2.getJumpInstruction().label;
            }
        }
        for (Pair pair : arrayList) {
            node.instructions.insert((AbstractInsnNode) pair.component1(), (AbstractInsnNode) pair.component2());
        }
        List<LocalVariableNode> list4 = node.localVariables;
        final Function1<LocalVariableNode, Boolean> function1 = new Function1<LocalVariableNode, Boolean>() { // from class: org.jetbrains.kotlin.codegen.inline.DefaultMethodUtilKt.expandMaskConditionsAndUpdateVariableNodes.3
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(LocalVariableNode localVariableNode2) {
                return Boolean.valueOf((linkedHashSet.contains(localVariableNode2.start) && linkedHashSet.contains(localVariableNode2.end)) || validOffsets.contains(Integer.valueOf(localVariableNode2.index)));
            }
        };
        list4.removeIf(new Predicate() { // from class: org.jetbrains.kotlin.codegen.inline.DefaultMethodUtilKt$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj4) {
                return DefaultMethodUtilKt.expandMaskConditionsAndUpdateVariableNodes$lambda$7(function1, obj4);
            }
        });
        List<TryCatchBlockNode> list5 = node.tryCatchBlocks;
        final Function1<TryCatchBlockNode, Boolean> function2 = new Function1<TryCatchBlockNode, Boolean>() { // from class: org.jetbrains.kotlin.codegen.inline.DefaultMethodUtilKt.expandMaskConditionsAndUpdateVariableNodes.4
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(TryCatchBlockNode tryCatchBlockNode) {
                return Boolean.valueOf(linkedHashSet.contains(tryCatchBlockNode.start) && linkedHashSet.contains(tryCatchBlockNode.end));
            }
        };
        list5.removeIf(new Predicate() { // from class: org.jetbrains.kotlin.codegen.inline.DefaultMethodUtilKt$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj4) {
                return DefaultMethodUtilKt.expandMaskConditionsAndUpdateVariableNodes$lambda$8(function2, obj4);
            }
        });
        MethodInlinerUtilKt.remove(node, linkedHashSet2);
        return listExtractDefaultLambdasInfo;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean expandMaskConditionsAndUpdateVariableNodes$lambda$7(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        return ((Boolean) tmp0.invoke(obj)).booleanValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean expandMaskConditionsAndUpdateVariableNodes$lambda$8(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        return ((Boolean) tmp0.invoke(obj)).booleanValue();
    }

    private static final List<ExtractedDefaultLambda> extractDefaultLambdasInfo(List<Condition> list, Collection<AbstractInsnNode> collection, List<Pair<AbstractInsnNode, AbstractInsnNode>> list2) {
        Triple triple;
        List<Condition> list3 = list;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list3, 10));
        for (Condition condition : list3) {
            VarInsnNode varInsNode = condition.getVarInsNode();
            Intrinsics.checkNotNull(varInsNode);
            AbstractInsnNode element = varInsNode.getPrevious();
            if (element instanceof TypeInsnNode) {
                TypeInsnNode typeInsnNode = (TypeInsnNode) element;
                if (typeInsnNode.getOpcode() == 192) {
                    element = typeInsnNode.getPrevious();
                }
            }
            if (element instanceof MethodInsnNode) {
                MethodInsnNode methodInsnNode = (MethodInsnNode) element;
                Intrinsics.areEqual(methodInsnNode.f1015name, "<init>");
                final String str = methodInsnNode.owner;
                AbstractInsnNode abstractInsnNode = (AbstractInsnNode) SequencesKt.single(SequencesKt.filter(new InsnSequence(condition.getJumpInstruction(), condition.getJumpInstruction().label), new Function1<AbstractInsnNode, Boolean>() { // from class: org.jetbrains.kotlin.codegen.inline.DefaultMethodUtilKt$extractDefaultLambdasInfo$1$instanceCreation$1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public final Boolean invoke(AbstractInsnNode it) {
                        Intrinsics.checkNotNullParameter(it, "it");
                        return Boolean.valueOf(it.getOpcode() == 187 && Intrinsics.areEqual(((TypeInsnNode) it).desc, str));
                    }
                }));
                AbstractInsnNode next = abstractInsnNode.getNext();
                if (next != null) {
                    next.getOpcode();
                }
                collection.addAll(CollectionsKt.listOf((Object[]) new AbstractInsnNode[]{abstractInsnNode, abstractInsnNode.getNext()}));
                Intrinsics.checkNotNullExpressionValue(element, "element");
                collection.addAll(SequencesKt.toList(new InsnSequence(element, varInsNode.getNext())));
                AbstractInsnNode previous = abstractInsnNode.getPrevious();
                ReifiedTypeInliner.Companion companion = ReifiedTypeInliner.INSTANCE;
                Intrinsics.checkNotNull(previous);
                if (!companion.isNeedClassReificationMarker(previous)) {
                    previous = null;
                }
                triple = new Triple(Type.getObjectType(methodInsnNode.owner), Type.getArgumentTypes(methodInsnNode.desc), Boolean.valueOf((previous != null ? Boolean.valueOf(collection.add(previous)) : null) != null));
            } else if (element instanceof FieldInsnNode) {
                Intrinsics.checkNotNullExpressionValue(element, "element");
                collection.addAll(SequencesKt.toList(new InsnSequence(element, varInsNode.getNext())));
                FieldInsnNode fieldInsnNode = (FieldInsnNode) element;
                AbstractInsnNode previous2 = fieldInsnNode.getPrevious();
                ReifiedTypeInliner.Companion companion2 = ReifiedTypeInliner.INSTANCE;
                Intrinsics.checkNotNull(previous2);
                if (!companion2.isNeedClassReificationMarker(previous2)) {
                    previous2 = null;
                }
                triple = new Triple(Type.getObjectType(fieldInsnNode.owner), new Type[0], Boolean.valueOf((previous2 != null ? Boolean.valueOf(collection.add(previous2)) : null) != null));
            } else {
                throw new RuntimeException("Can't extract default lambda info " + condition + ".\n Unknown instruction: " + InlineCodegenUtilsKt.getInsnText(element));
            }
            Type type = (Type) triple.component1();
            Type[] typeArr = (Type[]) triple.component2();
            boolean zBooleanValue = ((Boolean) triple.component3()).booleanValue();
            Intrinsics.checkNotNull(typeArr);
            list2.add(TuplesKt.to(varInsNode, defaultLambdaFakeCallStub(typeArr, condition.getVarIndex())));
            Intrinsics.checkNotNull(type);
            arrayList.add(new ExtractedDefaultLambda(type, typeArr, condition.getVarIndex(), zBooleanValue));
        }
        return arrayList;
    }

    private static final MethodInsnNode defaultLambdaFakeCallStub(Type[] typeArr, int i) {
        return new MethodInsnNode(184, InlineCodegenUtilsKt.DEFAULT_LAMBDA_FAKE_CALL, InlineCodegenUtilsKt.DEFAULT_LAMBDA_FAKE_CALL + i, Type.getMethodDescriptor(Type.VOID_TYPE, (Type[]) Arrays.copyOf(typeArr, typeArr.length)), false);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static final SMAPAndMethodNode loadDefaultLambdaBody(byte[] classBytes, Type classType, boolean z) {
        SMAPAndMethodNode sMAPAndMethodNode;
        Intrinsics.checkNotNullParameter(classBytes, "classBytes");
        Intrinsics.checkNotNullParameter(classType, "classType");
        final String strAsString = (z ? OperatorNameConventions.GET : OperatorNameConventions.INVOKE).asString();
        Intrinsics.checkNotNull(strAsString);
        final Ref.ObjectRef objectRef = new Ref.ObjectRef();
        final Ref.ObjectRef objectRef2 = new Ref.ObjectRef();
        final Ref.ObjectRef objectRef3 = new Ref.ObjectRef();
        new ClassReader(classBytes).accept(new ClassVisitor() { // from class: org.jetbrains.kotlin.codegen.inline.DefaultMethodUtilKt$loadDefaultLambdaBody$$inlined$getMethodNode$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(589824);
            }

            /* JADX WARN: Multi-variable type inference failed */
            @Override // org.jetbrains.org.objectweb.asm.ClassVisitor
            public void visitSource(String source, String debug) {
                objectRef2.element = source;
                objectRef3.element = debug;
            }

            /* JADX WARN: Multi-variable type inference failed */
            /* JADX WARN: Type inference failed for: r8v0, types: [T, org.jetbrains.org.objectweb.asm.tree.MethodNode] */
            @Override // org.jetbrains.org.objectweb.asm.ClassVisitor
            public MethodVisitor visitMethod(int access, String name2, String desc, String signature, String[] exceptions) {
                Intrinsics.checkNotNullParameter(name2, "name");
                Intrinsics.checkNotNullParameter(desc, "desc");
                Method method = new Method(name2, desc);
                if (!Intrinsics.areEqual(method.getName(), strAsString) || !Intrinsics.areEqual(method.getReturnType(), AsmTypes.OBJECT_TYPE)) {
                    return null;
                }
                Type[] argumentTypes = method.getArgumentTypes();
                Intrinsics.checkNotNullExpressionValue(argumentTypes, "getArgumentTypes(...)");
                for (Type type : argumentTypes) {
                    if (!Intrinsics.areEqual(type, AsmTypes.OBJECT_TYPE)) {
                        return null;
                    }
                }
                MethodNode methodNode = (MethodNode) objectRef.element;
                if (methodNode != null) {
                    throw new AssertionError("Can't find proper '" + name2 + "' method for inline: ambiguity between '" + methodNode.f1016name + methodNode.desc + "' and '" + name2 + desc + '\'');
                }
                objectRef.element = new MethodNode(589824, access, name2, desc, signature, exceptions);
                T t = objectRef.element;
                Intrinsics.checkNotNull(t);
                return (MethodVisitor) t;
            }
        }, 4);
        MethodNode methodNode = (MethodNode) objectRef.element;
        if (methodNode != null) {
            Pair<Integer, Integer> pairLineNumberRange = InlineCodegenUtilsKt.lineNumberRange(CollectionsKt.listOfNotNull(methodNode));
            int iIntValue = pairLineNumberRange.component1().intValue();
            int iIntValue2 = pairLineNumberRange.component2().intValue();
            String str = (String) objectRef3.element;
            String str2 = (String) objectRef2.element;
            String internalName = classType.getInternalName();
            Intrinsics.checkNotNullExpressionValue(internalName, "getInternalName(...)");
            sMAPAndMethodNode = new SMAPAndMethodNode(methodNode, SMAPParser.parseOrCreateDefault(str, str2, internalName, iIntValue, iIntValue2));
        } else {
            sMAPAndMethodNode = null;
        }
        if (sMAPAndMethodNode != null) {
            return (sMAPAndMethodNode.getNode().access & 64) == 0 ? sMAPAndMethodNode : inlineBridge(sMAPAndMethodNode.getNode(), classBytes, classType);
        }
        throw new IllegalStateException(("can't find erased invoke '" + strAsString + "(Object...): Object' in default lambda '" + classType.getInternalName() + '\'').toString());
    }

    /* JADX WARN: Type inference failed for: r3v6, types: [java.util.ListIterator] */
    private static final SMAPAndMethodNode inlineBridge(MethodNode methodNode, byte[] bArr, Type type) {
        InsnList instructions = methodNode.instructions;
        Intrinsics.checkNotNullExpressionValue(instructions, "instructions");
        Iterator<AbstractInsnNode> it = instructions.iterator();
        AbstractInsnNode abstractInsnNode = null;
        AbstractInsnNode abstractInsnNode2 = null;
        boolean z = false;
        while (true) {
            if (!it.hasNext()) {
                if (!z) {
                    break;
                }
                abstractInsnNode = abstractInsnNode2;
                break;
            }
            AbstractInsnNode next = it.next();
            AbstractInsnNode abstractInsnNode3 = next;
            if ((abstractInsnNode3 instanceof MethodInsnNode) && Intrinsics.areEqual(((MethodInsnNode) abstractInsnNode3).owner, type.getInternalName())) {
                if (z) {
                    break;
                }
                abstractInsnNode2 = next;
                z = true;
            }
        }
        MethodInsnNode methodInsnNode = (MethodInsnNode) abstractInsnNode;
        if (methodInsnNode == null) {
            throw new IllegalStateException(("no single invoke of method on this in '" + methodNode.f1016name + methodNode.desc + "' of default lambda '" + type.getInternalName() + '\'').toString());
        }
        Method method = new Method(methodInsnNode.f1015name, methodInsnNode.desc);
        SMAPAndMethodNode methodNode2 = InlineCodegenUtilsKt.getMethodNode(bArr, type, method);
        if (methodNode2 == null) {
            throw new IllegalStateException(("can't find non-bridge invoke '" + method + "' in default lambda '" + type.getInternalName()).toString());
        }
        Type[] argumentTypes = method.getArgumentTypes();
        Intrinsics.checkNotNull(argumentTypes);
        int size = 0;
        for (Type type2 : argumentTypes) {
            size += type2.getSize();
        }
        int i = size + ((methodNode2.getNode().access & 8) != 0 ? 0 : 1);
        List listReversed = ArraysKt.reversed(argumentTypes);
        int size2 = listReversed.size();
        int size3 = i;
        for (int i2 = 0; i2 < size2; i2++) {
            Type type3 = (Type) listReversed.get(i2);
            size3 -= type3.getSize();
            methodNode.instructions.insertBefore(methodInsnNode, new VarInsnNode(type3.getOpcode(54), size3));
        }
        if ((methodNode2.getNode().access & 8) == 0) {
            methodNode.instructions.insertBefore(methodInsnNode, new InsnNode(87));
        }
        LabelNode labelNode = new LabelNode();
        LabelNode labelNode2 = new LabelNode();
        MethodInsnNode methodInsnNode2 = methodInsnNode;
        methodNode.instructions.insertBefore(methodInsnNode2, labelNode);
        methodNode.instructions.insert(methodInsnNode2, labelNode2);
        ?? it2 = methodNode2.getNode().instructions.iterator();
        while (it2.hasNext()) {
            AbstractInsnNode abstractInsnNode4 = (AbstractInsnNode) it2.next();
            int opcode = abstractInsnNode4.getOpcode();
            if (172 <= opcode && opcode < 178) {
                methodNode2.getNode().instructions.set(abstractInsnNode4, new JumpInsnNode(167, labelNode2));
            }
        }
        for (LocalVariableNode localVariableNode : methodNode2.getNode().localVariables) {
            if (localVariableNode.index < i) {
                localVariableNode.start = labelNode;
                localVariableNode.end = labelNode2;
            }
        }
        InsnList instructions2 = methodNode.instructions;
        Intrinsics.checkNotNullExpressionValue(instructions2, "instructions");
        ArrayList arrayList = new ArrayList();
        for (AbstractInsnNode abstractInsnNode5 : instructions2) {
            if (abstractInsnNode5 instanceof LineNumberNode) {
                arrayList.add(abstractInsnNode5);
            }
        }
        Iterator it3 = arrayList.iterator();
        while (it3.hasNext()) {
            methodNode.instructions.remove((LineNumberNode) it3.next());
        }
        methodNode.instructions.insertBefore(methodInsnNode2, methodNode2.getNode().instructions);
        methodNode.instructions.remove(methodInsnNode2);
        methodNode.localVariables = methodNode2.getNode().localVariables;
        methodNode.tryCatchBlocks = methodNode2.getNode().tryCatchBlocks;
        methodNode.maxLocals = Math.max(methodNode.maxLocals, methodNode2.getNode().maxLocals);
        methodNode.maxStack = Math.max(methodNode.maxStack, methodNode2.getNode().maxStack);
        return new SMAPAndMethodNode(methodNode, methodNode2.getClassSMAP());
    }
}

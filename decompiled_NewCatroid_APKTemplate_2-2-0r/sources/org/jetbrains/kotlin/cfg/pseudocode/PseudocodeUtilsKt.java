package org.jetbrains.kotlin.cfg.pseudocode;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.cfg.CfgContainingDeclarationUtilsKt;
import org.jetbrains.kotlin.cfg.Label;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.Instruction;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.eval.AccessTarget;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.eval.AccessValueInstruction;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.eval.CallInstruction;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.eval.InstructionWithValue;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.eval.MagicInstruction;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.eval.MagicKind;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.eval.MergeInstruction;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.eval.ReadValueInstruction;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.jumps.ConditionalJumpInstruction;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.jumps.ReturnValueInstruction;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.jumps.ThrowExceptionInstruction;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.special.LocalFunctionDeclarationInstruction;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.kotlin.config.LanguageVersionSettingsImpl;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyAccessorDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.ReceiverParameterDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.descriptors.VariableDescriptor;
import org.jetbrains.kotlin.descriptors.impl.LocalVariableDescriptor;
import org.jetbrains.kotlin.psi.Call;
import org.jetbrains.kotlin.psi.KtConstantExpression;
import org.jetbrains.kotlin.psi.KtDeclaration;
import org.jetbrains.kotlin.psi.KtDelegatedSuperTypeEntry;
import org.jetbrains.kotlin.psi.KtElement;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtFunctionLiteral;
import org.jetbrains.kotlin.psi.KtLambdaExpression;
import org.jetbrains.kotlin.psi.KtNamedFunction;
import org.jetbrains.kotlin.psi.KtProperty;
import org.jetbrains.kotlin.psi.KtReturnExpression;
import org.jetbrains.kotlin.psi.KtStringTemplateExpression;
import org.jetbrains.kotlin.psi.KtWhileExpression;
import org.jetbrains.kotlin.psi.psiUtil.PsiUtilsKt;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.OverridingUtilsKt;
import org.jetbrains.kotlin.resolve.bindingContextUtil.BindingContextUtilsKt;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.resolve.calls.model.VarargValueArgument;
import org.jetbrains.kotlin.resolve.calls.util.CallUtilKt;
import org.jetbrains.kotlin.resolve.calls.util.ResolvedCallUtilKt;
import org.jetbrains.kotlin.resolve.scopes.receivers.ReceiverValue;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.TypeUtils;
import org.jetbrains.kotlin.util.slicedMap.WritableSlice;

/* JADX INFO: compiled from: pseudocodeUtils.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000^\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u001e\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f\u001a\u001c\u0010\u0010\u001a\u0004\u0018\u00010\t2\n\u0010\u0011\u001a\u0006\u0012\u0002\b\u00030\u00122\u0006\u0010\u0013\u001a\u00020\u0014\u001a\n\u0010\u0015\u001a\u00020\u0001*\u00020\u0005\u001a\u0014\u0010\u0016\u001a\u0004\u0018\u00010\u0017*\u00020\u00182\u0006\u0010\u0019\u001a\u00020\r\u001a\u0014\u0010\u0016\u001a\u0004\u0018\u00010\u0017*\u00020\u001a2\u0006\u0010\u0019\u001a\u00020\r\u001a\u0018\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u000b0\u001c*\u00020\u00172\u0006\u0010\u001d\u001a\u00020\u001a\u001a\u0014\u0010\u001e\u001a\u0004\u0018\u00010\u001f*\u00020\u00052\u0006\u0010\f\u001a\u00020\r\u001a\u0014\u0010 \u001a\u0004\u0018\u00010\u0017*\u00020\u00172\u0006\u0010\u001d\u001a\u00020\u001a\"\u0015\u0010\u0000\u001a\u00020\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0000\u0010\u0003\"\u0015\u0010\u0004\u001a\u00020\u0001*\u00020\u00058F¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007¨\u0006!"}, d2 = {"isJumpToError", "", "Lorg/jetbrains/kotlin/cfg/Label;", "(Lorg/jetbrains/kotlin/cfg/Label;)Z", "sideEffectFree", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/Instruction;", "getSideEffectFree", "(Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/Instruction;)Z", "getExpectedTypePredicate", "Lorg/jetbrains/kotlin/cfg/pseudocode/TypePredicate;", "value", "Lorg/jetbrains/kotlin/cfg/pseudocode/PseudoValue;", "bindingContext", "Lorg/jetbrains/kotlin/resolve/BindingContext;", "builtIns", "Lorg/jetbrains/kotlin/builtins/KotlinBuiltIns;", "getReceiverTypePredicate", "resolvedCall", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCall;", "receiverValue", "Lorg/jetbrains/kotlin/resolve/scopes/receivers/ReceiverValue;", "calcSideEffectFree", "getContainingPseudocode", "Lorg/jetbrains/kotlin/cfg/pseudocode/Pseudocode;", "Lorg/jetbrains/kotlin/psi/KtDeclaration;", "context", "Lorg/jetbrains/kotlin/psi/KtElement;", "getElementValuesRecursively", "", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "getPrimaryDeclarationDescriptorIfAny", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "getPseudocodeByElement", "cfg"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class PseudocodeUtilsKt {

    /* JADX INFO: compiled from: pseudocodeUtils.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[MagicKind.values().length];
            try {
                iArr[MagicKind.AND.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[MagicKind.OR.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[MagicKind.LOOP_RANGE_ITERATION.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[MagicKind.VALUE_CONSUMER.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    /* JADX WARN: Code duplicated, block: B:23:0x007b  */
    public static final TypePredicate getReceiverTypePredicate(ResolvedCall<?> resolvedCall, ReceiverValue receiverValue) {
        TypePredicate subtypesPredicate;
        KotlinType type;
        Intrinsics.checkNotNullParameter(resolvedCall, "resolvedCall");
        Intrinsics.checkNotNullParameter(receiverValue, "receiverValue");
        CallableDescriptor resultingDescriptor = resolvedCall.getResultingDescriptor();
        if (resultingDescriptor == null) {
            return null;
        }
        if (Intrinsics.areEqual(receiverValue, resolvedCall.getExtensionReceiver())) {
            ReceiverParameterDescriptor extensionReceiverParameter = resultingDescriptor.getExtensionReceiverParameter();
            if (extensionReceiverParameter != null) {
                KotlinType type2 = extensionReceiverParameter.getType();
                Intrinsics.checkNotNullExpressionValue(type2, "getType(...)");
                return TypePredicateKt.getSubtypesPredicate(type2);
            }
        } else if (Intrinsics.areEqual(receiverValue, resolvedCall.getDispatchReceiver())) {
            List listFindTopMostOverriddenDescriptors = OverridingUtilsKt.findTopMostOverriddenDescriptors(resultingDescriptor);
            ArrayList arrayList = new ArrayList();
            Iterator it = listFindTopMostOverriddenDescriptors.iterator();
            while (it.hasNext()) {
                ReceiverParameterDescriptor receiverParameterDescriptorMo6753getDispatchReceiverParameter = ((CallableDescriptor) it.next()).mo6753getDispatchReceiverParameter();
                if (receiverParameterDescriptorMo6753getDispatchReceiverParameter == null || (type = receiverParameterDescriptorMo6753getDispatchReceiverParameter.getType()) == null) {
                    subtypesPredicate = null;
                } else {
                    Call call = resolvedCall.getCall();
                    Intrinsics.checkNotNullExpressionValue(call, "getCall(...)");
                    KotlinType kotlinTypeMakeNullableIfNeeded = TypeUtils.makeNullableIfNeeded(type, CallUtilKt.isSafeCall(call));
                    if (kotlinTypeMakeNullableIfNeeded != null) {
                        subtypesPredicate = TypePredicateKt.getSubtypesPredicate(kotlinTypeMakeNullableIfNeeded);
                    } else {
                        subtypesPredicate = null;
                    }
                }
                if (subtypesPredicate != null) {
                    arrayList.add(subtypesPredicate);
                }
            }
            return TypePredicateKt.or(arrayList);
        }
        return null;
    }

    public static final TypePredicate getExpectedTypePredicate(PseudoValue value, BindingContext bindingContext, KotlinBuiltIns builtIns) {
        Pseudocode owner;
        Intrinsics.checkNotNullParameter(value, "value");
        Intrinsics.checkNotNullParameter(bindingContext, "bindingContext");
        Intrinsics.checkNotNullParameter(builtIns, "builtIns");
        InstructionWithValue createdAt = value.getCreatedAt();
        if (createdAt == null || (owner = createdAt.getOwner()) == null) {
            return AllTypes.INSTANCE;
        }
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        getExpectedTypePredicate$addTypePredicates(owner, bindingContext, builtIns, linkedHashSet, value);
        return TypePredicateKt.and(CollectionsKt.filterNotNull(linkedHashSet));
    }

    private static final boolean getExpectedTypePredicate$addSubtypesOf(LinkedHashSet<TypePredicate> linkedHashSet, KotlinType kotlinType) {
        return linkedHashSet.add(kotlinType != null ? TypePredicateKt.getSubtypesPredicate(kotlinType) : null);
    }

    private static final void getExpectedTypePredicate$addByExplicitReceiver(LinkedHashSet<TypePredicate> linkedHashSet, ResolvedCall<?> resolvedCall) {
        ReceiverValue explicitReceiverValue;
        if (resolvedCall == null || (explicitReceiverValue = ResolvedCallUtilKt.getExplicitReceiverValue(resolvedCall)) == null) {
            return;
        }
        linkedHashSet.add(getReceiverTypePredicate(resolvedCall, explicitReceiverValue));
    }

    private static final void getExpectedTypePredicate$addTypePredicates(Pseudocode pseudocode, BindingContext bindingContext, KotlinBuiltIns kotlinBuiltIns, LinkedHashSet<TypePredicate> linkedHashSet, PseudoValue pseudoValue) {
        KotlinType type;
        List<PropertyAccessorDescriptor> accessors;
        for (Instruction instruction : pseudocode.getUsages(pseudoValue)) {
            type = null;
            KotlinType type2 = null;
            if (instruction instanceof ReturnValueInstruction) {
                KtElement element = ((ReturnValueInstruction) instruction).getElement();
                FunctionDescriptor targetFunctionDescriptor = element instanceof KtReturnExpression ? BindingContextUtilsKt.getTargetFunctionDescriptor((KtReturnExpression) element, bindingContext) : (DeclarationDescriptor) bindingContext.get(BindingContext.DECLARATION_TO_DESCRIPTOR, pseudocode.getCorrespondingElement());
                CallableDescriptor callableDescriptor = targetFunctionDescriptor instanceof CallableDescriptor ? (CallableDescriptor) targetFunctionDescriptor : null;
                getExpectedTypePredicate$addSubtypesOf(linkedHashSet, callableDescriptor != null ? callableDescriptor.getReturnType() : null);
            } else if (instruction instanceof ConditionalJumpInstruction) {
                getExpectedTypePredicate$addSubtypesOf(linkedHashSet, kotlinBuiltIns.getBooleanType());
            } else if (instruction instanceof ThrowExceptionInstruction) {
                getExpectedTypePredicate$addSubtypesOf(linkedHashSet, kotlinBuiltIns.getThrowable().getDefaultType());
            } else if (instruction instanceof MergeInstruction) {
                getExpectedTypePredicate$addTypePredicates(pseudocode, bindingContext, kotlinBuiltIns, linkedHashSet, ((MergeInstruction) instruction).getResultValue());
            } else if (instruction instanceof AccessValueInstruction) {
                AccessValueInstruction accessValueInstruction = (AccessValueInstruction) instruction;
                AccessTarget target = accessValueInstruction.getTarget();
                ReceiverValue receiverValue = accessValueInstruction.getReceiverValues().get(pseudoValue);
                if (receiverValue != null) {
                    Intrinsics.checkNotNull(target, "null cannot be cast to non-null type org.jetbrains.kotlin.cfg.pseudocode.instructions.eval.AccessTarget.Call");
                    linkedHashSet.add(getReceiverTypePredicate(((AccessTarget.Call) target).getResolvedCall(), receiverValue));
                } else {
                    if (target instanceof AccessTarget.Call) {
                        CallableDescriptor resultingDescriptor = ((AccessTarget.Call) target).getResolvedCall().getResultingDescriptor();
                        VariableDescriptor variableDescriptor = resultingDescriptor instanceof VariableDescriptor ? (VariableDescriptor) resultingDescriptor : null;
                        if (variableDescriptor != null) {
                            type2 = variableDescriptor.getType();
                        }
                    } else if (target instanceof AccessTarget.Declaration) {
                        type2 = ((AccessTarget.Declaration) target).getDescriptor().getType();
                    }
                    getExpectedTypePredicate$addSubtypesOf(linkedHashSet, type2);
                }
            } else if (instruction instanceof CallInstruction) {
                CallInstruction callInstruction = (CallInstruction) instruction;
                ReceiverValue receiverValue2 = callInstruction.getReceiverValues().get(pseudoValue);
                if (receiverValue2 != null) {
                    linkedHashSet.add(getReceiverTypePredicate(callInstruction.getResolvedCall(), receiverValue2));
                } else {
                    ValueParameterDescriptor valueParameterDescriptor = callInstruction.getArguments().get(pseudoValue);
                    if (valueParameterDescriptor != null) {
                        if (callInstruction.getResolvedCall().getValueArguments().get(valueParameterDescriptor) instanceof VarargValueArgument) {
                            type = valueParameterDescriptor.getVarargElementType();
                        } else {
                            type = valueParameterDescriptor.getType();
                        }
                        getExpectedTypePredicate$addSubtypesOf(linkedHashSet, type);
                    }
                }
            } else if (instruction instanceof MagicInstruction) {
                MagicInstruction magicInstruction = (MagicInstruction) instruction;
                int i = WhenMappings.$EnumSwitchMapping$0[magicInstruction.getKind().ordinal()];
                if (i == 1 || i == 2) {
                    getExpectedTypePredicate$addSubtypesOf(linkedHashSet, kotlinBuiltIns.getBooleanType());
                } else if (i == 3) {
                    WritableSlice<KtExpression, ResolvedCall<FunctionDescriptor>> writableSlice = BindingContext.LOOP_RANGE_ITERATOR_RESOLVED_CALL;
                    KtElement element2 = pseudoValue.getElement();
                    getExpectedTypePredicate$addByExplicitReceiver(linkedHashSet, (ResolvedCall) bindingContext.get(writableSlice, element2 instanceof KtExpression ? (KtExpression) element2 : null));
                } else if (i == 4) {
                    KtElement element3 = magicInstruction.getElement();
                    KtWhileExpression ktWhileExpression = (KtWhileExpression) PsiTreeUtil.getParentOfType(element3, KtWhileExpression.class, true);
                    if (Intrinsics.areEqual(element3, ktWhileExpression != null ? ktWhileExpression.getCondition() : null)) {
                        getExpectedTypePredicate$addSubtypesOf(linkedHashSet, kotlinBuiltIns.getBooleanType());
                    } else if (element3 instanceof KtProperty) {
                        Object obj = bindingContext.get(BindingContext.DECLARATION_TO_DESCRIPTOR, element3);
                        PropertyDescriptor propertyDescriptor = obj instanceof PropertyDescriptor ? (PropertyDescriptor) obj : null;
                        if (propertyDescriptor != null && (accessors = propertyDescriptor.getAccessors()) != null) {
                            Intrinsics.checkNotNull(accessors);
                            List<PropertyAccessorDescriptor> list = accessors;
                            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
                            Iterator<T> it = list.iterator();
                            while (it.hasNext()) {
                                getExpectedTypePredicate$addByExplicitReceiver(linkedHashSet, (ResolvedCall) bindingContext.get(BindingContext.DELEGATED_PROPERTY_RESOLVED_CALL, (PropertyAccessorDescriptor) it.next()));
                                arrayList.add(Unit.INSTANCE);
                            }
                        }
                    } else if (element3 instanceof KtDelegatedSuperTypeEntry) {
                        getExpectedTypePredicate$addSubtypesOf(linkedHashSet, (KotlinType) bindingContext.get(BindingContext.TYPE, ((KtDelegatedSuperTypeEntry) element3).getTypeReference()));
                    }
                }
            }
        }
    }

    public static final DeclarationDescriptor getPrimaryDeclarationDescriptorIfAny(Instruction instruction, BindingContext bindingContext) {
        Intrinsics.checkNotNullParameter(instruction, "<this>");
        Intrinsics.checkNotNullParameter(bindingContext, "bindingContext");
        return instruction instanceof CallInstruction ? ((CallInstruction) instruction).getResolvedCall().getResultingDescriptor() : PseudocodeUtil.extractVariableDescriptorIfAny(instruction, bindingContext);
    }

    public static final boolean getSideEffectFree(Instruction instruction) {
        Intrinsics.checkNotNullParameter(instruction, "<this>");
        return instruction.getOwner().isSideEffectFree(instruction);
    }

    public static final boolean calcSideEffectFree(Instruction instruction) {
        Intrinsics.checkNotNullParameter(instruction, "<this>");
        if (!(instruction instanceof InstructionWithValue)) {
            return false;
        }
        List<PseudoValue> inputValues = instruction.getInputValues();
        if (!(inputValues instanceof Collection) || !inputValues.isEmpty()) {
            Iterator<T> it = inputValues.iterator();
            while (it.hasNext()) {
                InstructionWithValue createdAt = ((PseudoValue) it.next()).getCreatedAt();
                if (createdAt == null || !getSideEffectFree(createdAt)) {
                    return false;
                }
            }
        }
        if (instruction instanceof ReadValueInstruction) {
            AccessTarget target = ((ReadValueInstruction) instruction).getTarget();
            if (target instanceof AccessTarget.Call) {
                CallableDescriptor resultingDescriptor = ((AccessTarget.Call) target).getResolvedCall().getResultingDescriptor();
                if (!(resultingDescriptor instanceof LocalVariableDescriptor ? true : resultingDescriptor instanceof ValueParameterDescriptor ? true : resultingDescriptor instanceof ReceiverParameterDescriptor)) {
                    return false;
                }
            } else {
                InstructionWithValue instructionWithValue = (InstructionWithValue) instruction;
                KtElement element = instructionWithValue.getElement();
                if (!(element instanceof KtNamedFunction)) {
                    if (!(element instanceof KtConstantExpression ? true : element instanceof KtLambdaExpression ? true : element instanceof KtStringTemplateExpression)) {
                        return false;
                    }
                } else if (instructionWithValue.getElement().get$name() != null) {
                    return false;
                }
            }
            return true;
        }
        if (instruction instanceof MagicInstruction) {
            return ((MagicInstruction) instruction).getKind().getSideEffectFree();
        }
        return false;
    }

    public static final List<PseudoValue> getElementValuesRecursively(Pseudocode pseudocode, KtElement element) {
        Intrinsics.checkNotNullParameter(pseudocode, "<this>");
        Intrinsics.checkNotNullParameter(element, "element");
        ArrayList arrayList = new ArrayList();
        getElementValuesRecursively$collectValues(pseudocode, element, arrayList);
        return arrayList;
    }

    private static final void getElementValuesRecursively$collectValues(Pseudocode pseudocode, KtElement ktElement, ArrayList<PseudoValue> arrayList) {
        PseudoValue elementValue = pseudocode.getElementValue(ktElement);
        if (elementValue != null) {
            arrayList.add(elementValue);
        }
        Iterator<LocalFunctionDeclarationInstruction> it = pseudocode.getLocalDeclarations().iterator();
        while (it.hasNext()) {
            getElementValuesRecursively$collectValues(it.next().getBody(), ktElement, arrayList);
        }
    }

    /* JADX WARN: Code duplicated, block: B:20:0x0040  */
    public static final Pseudocode getContainingPseudocode(KtDeclaration ktDeclaration, BindingContext context) {
        KtDeclaration ktDeclaration2;
        PsiElement next;
        Intrinsics.checkNotNullParameter(ktDeclaration, "<this>");
        Intrinsics.checkNotNullParameter(context, "context");
        KtFunctionLiteral ktFunctionLiteral = ktDeclaration instanceof KtFunctionLiteral ? (KtFunctionLiteral) ktDeclaration : null;
        if (ktFunctionLiteral != null) {
            Iterator<PsiElement> it = PsiUtilsKt.getParents(ktFunctionLiteral).iterator();
            while (true) {
                if (!it.hasNext()) {
                    next = null;
                    break;
                }
                next = it.next();
                PsiElement psiElement = next;
                if ((psiElement instanceof KtDeclaration) && !(psiElement instanceof KtFunctionLiteral)) {
                    break;
                }
            }
            ktDeclaration2 = next instanceof KtDeclaration ? (KtDeclaration) next : null;
            if (ktDeclaration2 == null) {
                ktDeclaration2 = ktDeclaration;
            }
        } else {
            ktDeclaration2 = ktDeclaration;
        }
        Pseudocode pseudocodeGeneratePseudocode = PseudocodeUtil.generatePseudocode(ktDeclaration2, context, LanguageVersionSettingsImpl.DEFAULT);
        Intrinsics.checkNotNullExpressionValue(pseudocodeGeneratePseudocode, "generatePseudocode(...)");
        return getPseudocodeByElement(pseudocodeGeneratePseudocode, ktDeclaration);
    }

    public static final Pseudocode getContainingPseudocode(KtElement ktElement, BindingContext context) {
        Intrinsics.checkNotNullParameter(ktElement, "<this>");
        Intrinsics.checkNotNullParameter(context, "context");
        KtDeclaration containingDeclarationForPseudocode = CfgContainingDeclarationUtilsKt.getContainingDeclarationForPseudocode(ktElement);
        if (containingDeclarationForPseudocode != null) {
            return getContainingPseudocode(containingDeclarationForPseudocode, context);
        }
        return null;
    }

    public static final Pseudocode getPseudocodeByElement(Pseudocode pseudocode, KtElement element) {
        Intrinsics.checkNotNullParameter(pseudocode, "<this>");
        Intrinsics.checkNotNullParameter(element, "element");
        if (Intrinsics.areEqual(pseudocode.getCorrespondingElement(), element)) {
            return pseudocode;
        }
        Iterator<T> it = pseudocode.getLocalDeclarations().iterator();
        while (it.hasNext()) {
            Pseudocode pseudocodeByElement = getPseudocodeByElement(((LocalFunctionDeclarationInstruction) it.next()).getBody(), element);
            if (pseudocodeByElement != null) {
                return pseudocodeByElement;
            }
        }
        return null;
    }

    public static final boolean isJumpToError(Label label) {
        Intrinsics.checkNotNullParameter(label, "<this>");
        return Intrinsics.areEqual(label.resolveToInstruction(), label.getPseudocode().getErrorInstruction());
    }
}

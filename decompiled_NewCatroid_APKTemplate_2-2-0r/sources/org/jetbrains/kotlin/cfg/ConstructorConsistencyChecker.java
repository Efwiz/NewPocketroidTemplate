package org.jetbrains.kotlin.cfg;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.cfg.pseudocode.Pseudocode;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.Instruction;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.KtElementInstruction;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.eval.MagicInstruction;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.eval.MagicKind;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.eval.ReadValueInstruction;
import org.jetbrains.kotlin.cfg.pseudocodeTraverser.PseudocodeTraverserKt;
import org.jetbrains.kotlin.cfg.pseudocodeTraverser.TraversalOrder;
import org.jetbrains.kotlin.cfg.variable.PseudocodeVariablesData;
import org.jetbrains.kotlin.cfg.variable.VariableControlFlowState;
import org.jetbrains.kotlin.cfg.variable.VariableInitReadOnlyControlFlowInfo;
import org.jetbrains.kotlin.com.google.common.collect.ImmutableSet;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.ModalityUtilsKt;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyGetterDescriptor;
import org.jetbrains.kotlin.descriptors.PropertySetterDescriptor;
import org.jetbrains.kotlin.descriptors.VariableDescriptor;
import org.jetbrains.kotlin.lexer.KtSingleValueToken;
import org.jetbrains.kotlin.lexer.KtTokens;
import org.jetbrains.kotlin.load.java.JvmAbi;
import org.jetbrains.kotlin.psi.KtBinaryExpression;
import org.jetbrains.kotlin.psi.KtCallExpression;
import org.jetbrains.kotlin.psi.KtClassOrObject;
import org.jetbrains.kotlin.psi.KtElement;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtQualifiedExpression;
import org.jetbrains.kotlin.psi.KtReferenceExpression;
import org.jetbrains.kotlin.psi.KtSecondaryConstructor;
import org.jetbrains.kotlin.psi.KtSimpleNameExpression;
import org.jetbrains.kotlin.psi.KtThisExpression;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.BindingTrace;
import org.jetbrains.kotlin.types.expressions.OperatorConventions;

/* JADX INFO: compiled from: ConstructorConsistencyChecker.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 \u001a2\u00020\u0001:\u0001\u001aB/\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b¢\u0006\u0002\u0010\fJ\u0006\u0010\u000f\u001a\u00020\u0010J\u0010\u0010\u0011\u001a\u00020\u000e2\u0006\u0010\u0012\u001a\u00020\u0013H\u0002J\u0010\u0010\u0014\u001a\u00020\u000e2\u0006\u0010\u0015\u001a\u00020\u0016H\u0002J\u0010\u0010\u0017\u001a\u00020\u000e2\u0006\u0010\u0012\u001a\u00020\u0013H\u0002J\u0010\u0010\u0018\u001a\u00020\u000e2\u0006\u0010\u0015\u001a\u00020\u0019H\u0002R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001b"}, d2 = {"Lorg/jetbrains/kotlin/cfg/ConstructorConsistencyChecker;", "", "classOrObject", "Lorg/jetbrains/kotlin/psi/KtClassOrObject;", "classDescriptor", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "trace", "Lorg/jetbrains/kotlin/resolve/BindingTrace;", "pseudocode", "Lorg/jetbrains/kotlin/cfg/pseudocode/Pseudocode;", "variablesData", "Lorg/jetbrains/kotlin/cfg/variable/PseudocodeVariablesData;", "(Lorg/jetbrains/kotlin/psi/KtClassOrObject;Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;Lorg/jetbrains/kotlin/resolve/BindingTrace;Lorg/jetbrains/kotlin/cfg/pseudocode/Pseudocode;Lorg/jetbrains/kotlin/cfg/variable/PseudocodeVariablesData;)V", "finalClass", "", "check", "", "insideLValue", "reference", "Lorg/jetbrains/kotlin/psi/KtReferenceExpression;", "safeCallUsage", "expression", "Lorg/jetbrains/kotlin/psi/KtCallExpression;", "safeReferenceUsage", "safeThisUsage", "Lorg/jetbrains/kotlin/psi/KtThisExpression;", "Companion", "cfg"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ConstructorConsistencyChecker {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final ClassDescriptor classDescriptor;
    private final KtClassOrObject classOrObject;
    private final boolean finalClass;
    private final Pseudocode pseudocode;
    private final BindingTrace trace;
    private final PseudocodeVariablesData variablesData;

    public /* synthetic */ ConstructorConsistencyChecker(KtClassOrObject ktClassOrObject, ClassDescriptor classDescriptor, BindingTrace bindingTrace, Pseudocode pseudocode, PseudocodeVariablesData pseudocodeVariablesData, DefaultConstructorMarker defaultConstructorMarker) {
        this(ktClassOrObject, classDescriptor, bindingTrace, pseudocode, pseudocodeVariablesData);
    }

    @JvmStatic
    public static final void check(KtClassOrObject ktClassOrObject, BindingTrace bindingTrace, Pseudocode pseudocode, PseudocodeVariablesData pseudocodeVariablesData) {
        INSTANCE.check(ktClassOrObject, bindingTrace, pseudocode, pseudocodeVariablesData);
    }

    @JvmStatic
    public static final void check(KtSecondaryConstructor ktSecondaryConstructor, BindingTrace bindingTrace, Pseudocode pseudocode, PseudocodeVariablesData pseudocodeVariablesData) {
        INSTANCE.check(ktSecondaryConstructor, bindingTrace, pseudocode, pseudocodeVariablesData);
    }

    private ConstructorConsistencyChecker(KtClassOrObject ktClassOrObject, ClassDescriptor classDescriptor, BindingTrace bindingTrace, Pseudocode pseudocode, PseudocodeVariablesData pseudocodeVariablesData) {
        this.classOrObject = ktClassOrObject;
        this.classDescriptor = classDescriptor;
        this.trace = bindingTrace;
        this.pseudocode = pseudocode;
        this.variablesData = pseudocodeVariablesData;
        this.finalClass = ModalityUtilsKt.isFinalClass(classDescriptor);
    }

    private final boolean insideLValue(KtReferenceExpression reference) {
        KtReferenceExpression myParent = reference;
        KtBinaryExpression ktBinaryExpression = (KtBinaryExpression) PsiTreeUtil.getParentOfType(myParent, KtBinaryExpression.class, true);
        if (ktBinaryExpression == null || !KtTokens.ALL_ASSIGNMENTS.contains(ktBinaryExpression.getOperationToken())) {
            return false;
        }
        KtExpression left = ktBinaryExpression.getLeft();
        while (myParent != left && myParent != ktBinaryExpression) {
            myParent = myParent.getLightParent();
            if (myParent == null) {
                return false;
            }
        }
        return myParent == left;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean safeReferenceUsage(KtReferenceExpression reference) {
        DeclarationDescriptor declarationDescriptor = (DeclarationDescriptor) this.trace.get(BindingContext.REFERENCE_TARGET, reference);
        if (!(declarationDescriptor instanceof PropertyDescriptor)) {
            return true;
        }
        if (!this.finalClass && ModalityUtilsKt.isOverridable((CallableMemberDescriptor) declarationDescriptor)) {
            this.trace.record(BindingContext.LEAKING_THIS, reference, new LeakingThisDescriptor.NonFinalProperty((PropertyDescriptor) declarationDescriptor, this.classOrObject));
            return true;
        }
        PropertyDescriptor propertyDescriptor = (PropertyDescriptor) declarationDescriptor;
        if (!Intrinsics.areEqual(propertyDescriptor.getContainingModule(), this.classDescriptor)) {
            return true;
        }
        if (insideLValue(reference)) {
            PropertySetterDescriptor setter = propertyDescriptor.getSetter();
            if (setter == null || setter.isDefault()) {
                return true;
            }
        } else {
            PropertyGetterDescriptor getter = propertyDescriptor.getGetter();
            if (getter == null || getter.isDefault()) {
                return true;
            }
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean safeThisUsage(KtThisExpression expression) {
        if (!Intrinsics.areEqual((DeclarationDescriptor) this.trace.get(BindingContext.REFERENCE_TARGET, expression.getInstanceReference()), this.classDescriptor)) {
            return true;
        }
        PsiElement parent = expression.getLightParent();
        if (parent instanceof KtQualifiedExpression) {
            KtExpression selectorExpression = ((KtQualifiedExpression) parent).getSelectorExpression();
            KtSimpleNameExpression ktSimpleNameExpression = selectorExpression instanceof KtSimpleNameExpression ? (KtSimpleNameExpression) selectorExpression : null;
            if (ktSimpleNameExpression != null) {
                return safeReferenceUsage(ktSimpleNameExpression);
            }
            return false;
        }
        if (!(parent instanceof KtBinaryExpression)) {
            return false;
        }
        ImmutableSet<KtSingleValueToken> IDENTITY_EQUALS_OPERATIONS = OperatorConventions.IDENTITY_EQUALS_OPERATIONS;
        Intrinsics.checkNotNullExpressionValue(IDENTITY_EQUALS_OPERATIONS, "IDENTITY_EQUALS_OPERATIONS");
        return CollectionsKt.contains(IDENTITY_EQUALS_OPERATIONS, ((KtBinaryExpression) parent).getOperationToken());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean safeCallUsage(KtCallExpression expression) {
        KtExpression calleeExpression = expression.getCalleeExpression();
        if (!(calleeExpression instanceof KtReferenceExpression)) {
            return false;
        }
        DeclarationDescriptor declarationDescriptor = (DeclarationDescriptor) this.trace.get(BindingContext.REFERENCE_TARGET, calleeExpression);
        if (!(declarationDescriptor instanceof FunctionDescriptor)) {
            return false;
        }
        FunctionDescriptor functionDescriptor = (FunctionDescriptor) declarationDescriptor;
        DeclarationDescriptor containingDeclaration = functionDescriptor.getContainingModule();
        Intrinsics.checkNotNullExpressionValue(containingDeclaration, "getContainingDeclaration(...)");
        if (!Intrinsics.areEqual(containingDeclaration, this.classDescriptor)) {
            return true;
        }
        if (this.finalClass || !ModalityUtilsKt.isOverridable((CallableMemberDescriptor) declarationDescriptor)) {
            return false;
        }
        this.trace.record(BindingContext.LEAKING_THIS, calleeExpression, new LeakingThisDescriptor.NonFinalFunction(functionDescriptor, this.classOrObject));
        return true;
    }

    public final void check() {
        Set<VariableDescriptor> declaredVariables = this.variablesData.getDeclaredVariables(this.pseudocode, false);
        ArrayList arrayList = new ArrayList();
        for (Object obj : declaredVariables) {
            if (obj instanceof PropertyDescriptor) {
                arrayList.add(obj);
            }
        }
        ArrayList arrayList2 = new ArrayList();
        for (Object obj2 : arrayList) {
            if (Intrinsics.areEqual(this.trace.get(BindingContext.BACKING_FIELD_REQUIRED, (PropertyDescriptor) obj2), (Object) true)) {
                arrayList2.add(obj2);
            }
        }
        final ArrayList arrayList3 = arrayList2;
        PseudocodeTraverserKt.traverse(this.pseudocode, TraversalOrder.FORWARD, this.variablesData.getVariableInitializers(), new Function3<Instruction, VariableInitReadOnlyControlFlowInfo, VariableInitReadOnlyControlFlowInfo, Unit>() { // from class: org.jetbrains.kotlin.cfg.ConstructorConsistencyChecker.check.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            {
                super(3);
            }

            @Override // kotlin.jvm.functions.Function3
            public /* bridge */ /* synthetic */ Unit invoke(Instruction instruction, VariableInitReadOnlyControlFlowInfo variableInitReadOnlyControlFlowInfo, VariableInitReadOnlyControlFlowInfo variableInitReadOnlyControlFlowInfo2) {
                invoke2(instruction, variableInitReadOnlyControlFlowInfo, variableInitReadOnlyControlFlowInfo2);
                return Unit.INSTANCE;
            }

            private static final PropertyDescriptor invoke$firstUninitializedNotNullProperty(List<? extends PropertyDescriptor> list, VariableInitReadOnlyControlFlowInfo variableInitReadOnlyControlFlowInfo) {
                Object next;
                Iterator<T> it = list.iterator();
                while (it.hasNext()) {
                    next = it.next();
                    PropertyDescriptor propertyDescriptor = (PropertyDescriptor) next;
                    if (!propertyDescriptor.getType().getIsMarkedNullable() && !KotlinBuiltIns.isPrimitiveType(propertyDescriptor.getType()) && !propertyDescriptor.isLateInit()) {
                        VariableControlFlowState orNull = variableInitReadOnlyControlFlowInfo.getOrNull(propertyDescriptor);
                        if (!(orNull != null ? orNull.definitelyInitialized() : false)) {
                            return (PropertyDescriptor) next;
                        }
                    }
                }
                next = null;
                return (PropertyDescriptor) next;
            }

            private static final void invoke$handleLeakingThis(ConstructorConsistencyChecker constructorConsistencyChecker, List<? extends PropertyDescriptor> list, VariableInitReadOnlyControlFlowInfo variableInitReadOnlyControlFlowInfo, KtExpression ktExpression) {
                if (!constructorConsistencyChecker.finalClass) {
                    constructorConsistencyChecker.trace.record(BindingContext.LEAKING_THIS, ConstructorConsistencyChecker.INSTANCE.target(ktExpression), new LeakingThisDescriptor.NonFinalClass(constructorConsistencyChecker.classDescriptor, constructorConsistencyChecker.classOrObject));
                    return;
                }
                PropertyDescriptor propertyDescriptorInvoke$firstUninitializedNotNullProperty = invoke$firstUninitializedNotNullProperty(list, variableInitReadOnlyControlFlowInfo);
                if (propertyDescriptorInvoke$firstUninitializedNotNullProperty != null) {
                    constructorConsistencyChecker.trace.record(BindingContext.LEAKING_THIS, ConstructorConsistencyChecker.INSTANCE.target(ktExpression), new LeakingThisDescriptor.PropertyIsNull(propertyDescriptorInvoke$firstUninitializedNotNullProperty, constructorConsistencyChecker.classOrObject));
                }
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(Instruction instruction, VariableInitReadOnlyControlFlowInfo enterData, VariableInitReadOnlyControlFlowInfo variableInitReadOnlyControlFlowInfo) {
                Intrinsics.checkNotNullParameter(instruction, "instruction");
                Intrinsics.checkNotNullParameter(enterData, "enterData");
                Intrinsics.checkNotNullParameter(variableInitReadOnlyControlFlowInfo, "<anonymous parameter 2>");
                if (Intrinsics.areEqual(instruction.getOwner(), ConstructorConsistencyChecker.this.pseudocode) && (instruction instanceof KtElementInstruction)) {
                    KtElement element = ((KtElementInstruction) instruction).getElement();
                    if (instruction instanceof ReadValueInstruction) {
                        if (!(element instanceof KtThisExpression) || ConstructorConsistencyChecker.this.safeThisUsage((KtThisExpression) element)) {
                            return;
                        }
                        invoke$handleLeakingThis(ConstructorConsistencyChecker.this, arrayList3, enterData, (KtExpression) element);
                        return;
                    }
                    if ((instruction instanceof MagicInstruction) && ((MagicInstruction) instruction).getKind() == MagicKind.IMPLICIT_RECEIVER) {
                        if (element instanceof KtCallExpression) {
                            if (ConstructorConsistencyChecker.this.safeCallUsage((KtCallExpression) element)) {
                                return;
                            }
                            invoke$handleLeakingThis(ConstructorConsistencyChecker.this, arrayList3, enterData, (KtExpression) element);
                        } else {
                            if (!(element instanceof KtReferenceExpression) || ConstructorConsistencyChecker.this.safeReferenceUsage((KtReferenceExpression) element)) {
                                return;
                            }
                            invoke$handleLeakingThis(ConstructorConsistencyChecker.this, arrayList3, enterData, (KtExpression) element);
                        }
                    }
                }
            }
        });
    }

    /* JADX INFO: compiled from: ConstructorConsistencyChecker.kt */
    @Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J(\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0007J(\u0010\u0003\u001a\u00020\u00042\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0007J\u0010\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0010H\u0002¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/cfg/ConstructorConsistencyChecker$Companion;", "", "()V", "check", "", "classOrObject", "Lorg/jetbrains/kotlin/psi/KtClassOrObject;", "trace", "Lorg/jetbrains/kotlin/resolve/BindingTrace;", "pseudocode", "Lorg/jetbrains/kotlin/cfg/pseudocode/Pseudocode;", "pseudocodeVariablesData", "Lorg/jetbrains/kotlin/cfg/variable/PseudocodeVariablesData;", JvmAbi.ERASED_INLINE_CONSTRUCTOR_NAME, "Lorg/jetbrains/kotlin/psi/KtSecondaryConstructor;", "target", "Lorg/jetbrains/kotlin/psi/KtExpression;", "expression", "cfg"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        @JvmStatic
        public final void check(KtSecondaryConstructor constructor, BindingTrace trace, Pseudocode pseudocode, PseudocodeVariablesData pseudocodeVariablesData) {
            Intrinsics.checkNotNullParameter(constructor, "constructor");
            Intrinsics.checkNotNullParameter(trace, "trace");
            Intrinsics.checkNotNullParameter(pseudocode, "pseudocode");
            Intrinsics.checkNotNullParameter(pseudocodeVariablesData, "pseudocodeVariablesData");
            check(constructor.getContainingClassOrObject(), trace, pseudocode, pseudocodeVariablesData);
        }

        @JvmStatic
        public final void check(KtClassOrObject classOrObject, BindingTrace trace, Pseudocode pseudocode, PseudocodeVariablesData pseudocodeVariablesData) {
            Intrinsics.checkNotNullParameter(classOrObject, "classOrObject");
            Intrinsics.checkNotNullParameter(trace, "trace");
            Intrinsics.checkNotNullParameter(pseudocode, "pseudocode");
            Intrinsics.checkNotNullParameter(pseudocodeVariablesData, "pseudocodeVariablesData");
            ClassDescriptor classDescriptor = (ClassDescriptor) trace.get(BindingContext.CLASS, classOrObject);
            if (classDescriptor == null) {
                return;
            }
            new ConstructorConsistencyChecker(classOrObject, classDescriptor, trace, pseudocode, pseudocodeVariablesData, null).check();
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* JADX WARN: Code duplicated, block: B:13:0x0022  */
        public final KtExpression target(KtExpression expression) {
            KtExpression selectorExpression;
            if (expression instanceof KtThisExpression) {
                PsiElement parent = ((KtThisExpression) expression).getLightParent();
                KtQualifiedExpression ktQualifiedExpression = parent instanceof KtQualifiedExpression ? (KtQualifiedExpression) parent : null;
                if (ktQualifiedExpression == null) {
                    selectorExpression = expression;
                } else {
                    selectorExpression = ktQualifiedExpression.getReceiverExpression() == expression ? ktQualifiedExpression.getSelectorExpression() : null;
                    if (selectorExpression == null) {
                        selectorExpression = expression;
                    }
                }
                return selectorExpression == expression ? selectorExpression : target(selectorExpression);
            }
            if (!(expression instanceof KtCallExpression)) {
                return expression;
            }
            KtCallExpression ktCallExpression = (KtCallExpression) expression;
            KtExpression calleeExpression = ktCallExpression.getCalleeExpression();
            KtCallExpression ktCallExpression2 = calleeExpression == null ? ktCallExpression : calleeExpression;
            Intrinsics.checkNotNull(ktCallExpression2);
            return ktCallExpression2;
        }
    }
}

package org.jetbrains.kotlin.resolve;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import kotlin.jvm.functions.Function3;
import org.antlr.stringtemplate.language.ASTExpr;
import org.jetbrains.kotlin.com.google.common.collect.Lists;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ConstructorDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.SimpleFunctionDescriptor;
import org.jetbrains.kotlin.descriptors.VariableDescriptor;
import org.jetbrains.kotlin.diagnostics.Diagnostic;
import org.jetbrains.kotlin.diagnostics.Errors;
import org.jetbrains.kotlin.psi.KtCallExpression;
import org.jetbrains.kotlin.psi.KtElement;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtQualifiedExpression;
import org.jetbrains.kotlin.psi.KtReferenceExpression;
import org.jetbrains.kotlin.psi.KtSimpleNameExpression;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.resolve.calls.model.VariableAsFunctionResolvedCall;
import org.jetbrains.kotlin.resolve.calls.smartcasts.DataFlowInfoFactory;
import org.jetbrains.kotlin.resolve.calls.tower.TowerLevelsKt;
import org.jetbrains.kotlin.resolve.calls.util.CallUtilKt;
import org.jetbrains.kotlin.resolve.diagnostics.MutableDiagnosticsWithSuppression;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.TypeUtils;
import org.jetbrains.kotlin.types.expressions.CaptureKind;
import org.jetbrains.kotlin.types.expressions.KotlinTypeInfo;
import org.jetbrains.kotlin.types.expressions.typeInfoFactory.TypeInfoFactoryKt;
import org.jetbrains.kotlin.util.slicedMap.MutableSlicedMap;
import org.jetbrains.kotlin.util.slicedMap.ReadOnlySlice;
import org.jetbrains.kotlin.util.slicedMap.WritableSlice;

/* JADX INFO: loaded from: classes6.dex */
public class BindingContextUtils {
    static final /* synthetic */ boolean $assertionsDisabled = false;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 10 || i == 15) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 10 || i == 15) ? 2 : 3];
        switch (i) {
            case 2:
            case 16:
            case 20:
            case 27:
                objArr[0] = "trace";
                break;
            case 3:
                objArr[0] = "psiElement";
                break;
            case 4:
                objArr[0] = "function";
                break;
            case 5:
            case 8:
            case 11:
            case 25:
            default:
                objArr[0] = "bindingContext";
                break;
            case 6:
            case 12:
                objArr[0] = "slice";
                break;
            case 7:
            case 13:
                objArr[0] = ASTExpr.DEFAULT_MAP_KEY_NAME;
                break;
            case 9:
            case 19:
            case 21:
            case 23:
                objArr[0] = "expression";
                break;
            case 10:
            case 15:
                objArr[0] = "org/jetbrains/kotlin/resolve/BindingContextUtils";
                break;
            case 14:
                objArr[0] = "messageIfNull";
                break;
            case 17:
                objArr[0] = "targetLabel";
                break;
            case 18:
                objArr[0] = "declarationsByLabel";
                break;
            case 22:
            case 24:
                objArr[0] = "context";
                break;
            case 26:
                objArr[0] = "constructorDescriptor";
                break;
            case 28:
                objArr[0] = "map";
                break;
        }
        if (i == 10) {
            objArr[1] = "getTypeNotNull";
        } else if (i != 15) {
            objArr[1] = "org/jetbrains/kotlin/resolve/BindingContextUtils";
        } else {
            objArr[1] = "getNotNull";
        }
        switch (i) {
            case 1:
                objArr[2] = "extractVariableDescriptorFromReference";
                break;
            case 2:
            case 3:
            case 4:
                objArr[2] = "recordFunctionDeclarationToDescriptor";
                break;
            case 5:
            case 6:
            case 7:
            case 11:
            case 12:
            case 13:
            case 14:
                objArr[2] = "getNotNull";
                break;
            case 8:
            case 9:
                objArr[2] = "getTypeNotNull";
                break;
            case 10:
            case 15:
                break;
            case 16:
            case 17:
            case 18:
                objArr[2] = "reportAmbiguousLabel";
                break;
            case 19:
            case 20:
                objArr[2] = "updateRecordedType";
                break;
            case 21:
            case 22:
                objArr[2] = "getRecordedTypeInfo";
                break;
            case 23:
            case 24:
                objArr[2] = "isExpressionWithValidReference";
                break;
            case 25:
            case 26:
                objArr[2] = "getDelegationConstructorCall";
                break;
            case 27:
            case 28:
                objArr[2] = "addOwnDataTo";
                break;
            default:
                objArr[2] = "extractVariableFromResolvedCall";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 10 && i != 15) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    private BindingContextUtils() {
    }

    public static VariableDescriptor extractVariableFromResolvedCall(BindingContext bindingContext, KtElement ktElement) {
        if (bindingContext == null) {
            $$$reportNull$$$0(0);
        }
        ResolvedCall<? extends CallableDescriptor> resolvedCall = CallUtilKt.getResolvedCall(ktElement, bindingContext);
        if (resolvedCall == null || !(resolvedCall.getResultingDescriptor() instanceof VariableDescriptor)) {
            return null;
        }
        return (VariableDescriptor) resolvedCall.getResultingDescriptor();
    }

    public static VariableDescriptor variableDescriptorForDeclaration(DeclarationDescriptor declarationDescriptor) {
        if (declarationDescriptor instanceof VariableDescriptor) {
            return (VariableDescriptor) declarationDescriptor;
        }
        if (declarationDescriptor instanceof ClassDescriptor) {
            return TowerLevelsKt.getFakeDescriptorForObject((ClassDescriptor) declarationDescriptor);
        }
        return null;
    }

    public static VariableDescriptor extractVariableDescriptorFromReference(BindingContext bindingContext, KtElement ktElement) {
        if (bindingContext == null) {
            $$$reportNull$$$0(1);
        }
        if (ktElement instanceof KtSimpleNameExpression) {
            return variableDescriptorForDeclaration((DeclarationDescriptor) bindingContext.get(BindingContext.REFERENCE_TARGET, (KtSimpleNameExpression) ktElement));
        }
        if (ktElement instanceof KtQualifiedExpression) {
            return extractVariableDescriptorFromReference(bindingContext, ((KtQualifiedExpression) ktElement).getSelectorExpression());
        }
        return null;
    }

    public static void recordFunctionDeclarationToDescriptor(BindingTrace bindingTrace, PsiElement psiElement, SimpleFunctionDescriptor simpleFunctionDescriptor) {
        if (bindingTrace == null) {
            $$$reportNull$$$0(2);
        }
        if (psiElement == null) {
            $$$reportNull$$$0(3);
        }
        if (simpleFunctionDescriptor == null) {
            $$$reportNull$$$0(4);
        }
        bindingTrace.record(BindingContext.FUNCTION, psiElement, simpleFunctionDescriptor);
    }

    public static <K, V> V getNotNull(BindingContext bindingContext, ReadOnlySlice<K, V> readOnlySlice, K k) {
        if (bindingContext == null) {
            $$$reportNull$$$0(5);
        }
        if (readOnlySlice == null) {
            $$$reportNull$$$0(6);
        }
        if (k == null) {
            $$$reportNull$$$0(7);
        }
        return (V) getNotNull(bindingContext, readOnlySlice, k, "Value at " + readOnlySlice + " must not be null for " + k);
    }

    public static KotlinType getTypeNotNull(BindingContext bindingContext, KtExpression ktExpression) {
        if (bindingContext == null) {
            $$$reportNull$$$0(8);
        }
        if (ktExpression == null) {
            $$$reportNull$$$0(9);
        }
        KotlinType type = bindingContext.getType(ktExpression);
        if (type == null) {
            throw new IllegalStateException("Type must be not null for " + ktExpression);
        }
        if (type == null) {
            $$$reportNull$$$0(10);
        }
        return type;
    }

    public static <K, V> V getNotNull(BindingContext bindingContext, ReadOnlySlice<K, V> readOnlySlice, K k, String str) {
        if (bindingContext == null) {
            $$$reportNull$$$0(11);
        }
        if (readOnlySlice == null) {
            $$$reportNull$$$0(12);
        }
        if (k == null) {
            $$$reportNull$$$0(13);
        }
        if (str == null) {
            $$$reportNull$$$0(14);
        }
        V v = (V) bindingContext.get(readOnlySlice, k);
        if (v == null) {
            throw new IllegalStateException(str);
        }
        if (v == null) {
            $$$reportNull$$$0(15);
        }
        return v;
    }

    public static void reportAmbiguousLabel(BindingTrace bindingTrace, KtSimpleNameExpression ktSimpleNameExpression, Collection<DeclarationDescriptor> collection) {
        if (bindingTrace == null) {
            $$$reportNull$$$0(16);
        }
        if (ktSimpleNameExpression == null) {
            $$$reportNull$$$0(17);
        }
        if (collection == null) {
            $$$reportNull$$$0(18);
        }
        ArrayList arrayListNewArrayList = Lists.newArrayList();
        Iterator<DeclarationDescriptor> it = collection.iterator();
        while (it.hasNext()) {
            arrayListNewArrayList.add(DescriptorToSourceUtils.descriptorToDeclaration(it.next()));
        }
        if (!arrayListNewArrayList.isEmpty()) {
            bindingTrace.record(BindingContext.AMBIGUOUS_LABEL_TARGET, ktSimpleNameExpression, arrayListNewArrayList);
        }
        bindingTrace.report(Errors.AMBIGUOUS_LABEL.on(ktSimpleNameExpression));
    }

    public static KotlinType updateRecordedType(KotlinType kotlinType, KtExpression ktExpression, BindingTrace bindingTrace, boolean z) {
        if (ktExpression == null) {
            $$$reportNull$$$0(19);
        }
        if (bindingTrace == null) {
            $$$reportNull$$$0(20);
        }
        if (kotlinType == null) {
            return null;
        }
        if (z) {
            kotlinType = TypeUtils.makeNullable(kotlinType);
        }
        bindingTrace.recordType(ktExpression, kotlinType);
        return kotlinType;
    }

    public static KotlinTypeInfo getRecordedTypeInfo(KtExpression ktExpression, BindingContext bindingContext) {
        if (ktExpression == null) {
            $$$reportNull$$$0(21);
        }
        if (bindingContext == null) {
            $$$reportNull$$$0(22);
        }
        if (bindingContext.get(BindingContext.PROCESSED, ktExpression) != Boolean.TRUE) {
            return null;
        }
        KotlinTypeInfo kotlinTypeInfo = (KotlinTypeInfo) bindingContext.get(BindingContext.EXPRESSION_TYPE_INFO, ktExpression);
        return kotlinTypeInfo != null ? kotlinTypeInfo : TypeInfoFactoryKt.noTypeInfo(DataFlowInfoFactory.EMPTY);
    }

    public static boolean isExpressionWithValidReference(KtExpression ktExpression, BindingContext bindingContext) {
        if (ktExpression == null) {
            $$$reportNull$$$0(23);
        }
        if (bindingContext == null) {
            $$$reportNull$$$0(24);
        }
        if (ktExpression instanceof KtCallExpression) {
            return CallUtilKt.getResolvedCall(ktExpression, bindingContext) instanceof VariableAsFunctionResolvedCall;
        }
        return ktExpression instanceof KtReferenceExpression;
    }

    public static boolean isCapturedInClosure(BindingContext bindingContext, DeclarationDescriptor declarationDescriptor) {
        if (!(declarationDescriptor instanceof VariableDescriptor) || (declarationDescriptor instanceof PropertyDescriptor)) {
            return false;
        }
        return bindingContext.get(BindingContext.CAPTURED_IN_CLOSURE, (VariableDescriptor) declarationDescriptor) != null;
    }

    public static boolean isCapturedInClosureWithExactlyOnceEffect(BindingContext bindingContext, DeclarationDescriptor declarationDescriptor) {
        if (!(declarationDescriptor instanceof VariableDescriptor) || (declarationDescriptor instanceof PropertyDescriptor)) {
            return false;
        }
        return bindingContext.get(BindingContext.CAPTURED_IN_CLOSURE, (VariableDescriptor) declarationDescriptor) == CaptureKind.EXACTLY_ONCE_EFFECT;
    }

    public static boolean isBoxedLocalCapturedInClosure(BindingContext bindingContext, DeclarationDescriptor declarationDescriptor) {
        return (isCapturedInClosure(bindingContext, declarationDescriptor) && ((VariableDescriptor) declarationDescriptor).isVar()) || isCapturedInClosureWithExactlyOnceEffect(bindingContext, declarationDescriptor);
    }

    /* JADX WARN: Code restructure failed: missing block: B:13:0x0011, code lost:
    
        r0 = null;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static org.jetbrains.kotlin.com.intellij.openapi.util.Pair<org.jetbrains.kotlin.descriptors.FunctionDescriptor, org.jetbrains.kotlin.com.intellij.psi.PsiElement> getContainingFunctionSkipFunctionLiterals(org.jetbrains.kotlin.descriptors.DeclarationDescriptor r2, boolean r3) {
        /*
            java.lang.Class<org.jetbrains.kotlin.descriptors.FunctionDescriptor> r0 = org.jetbrains.kotlin.descriptors.FunctionDescriptor.class
            org.jetbrains.kotlin.descriptors.DeclarationDescriptor r2 = org.jetbrains.kotlin.resolve.DescriptorUtils.getParentOfType(r2, r0, r3)
            org.jetbrains.kotlin.descriptors.FunctionDescriptor r2 = (org.jetbrains.kotlin.descriptors.FunctionDescriptor) r2
            r3 = 0
            if (r2 == 0) goto L10
            org.jetbrains.kotlin.com.intellij.psi.PsiElement r0 = org.jetbrains.kotlin.resolve.DescriptorToSourceUtils.getSourceFromDescriptor(r2)
            goto L11
        L10:
            r0 = r3
        L11:
            boolean r1 = r0 instanceof org.jetbrains.kotlin.psi.KtFunctionLiteral
            if (r1 == 0) goto L24
            java.lang.Class<org.jetbrains.kotlin.descriptors.FunctionDescriptor> r0 = org.jetbrains.kotlin.descriptors.FunctionDescriptor.class
            org.jetbrains.kotlin.descriptors.DeclarationDescriptor r2 = org.jetbrains.kotlin.resolve.DescriptorUtils.getParentOfType(r2, r0)
            org.jetbrains.kotlin.descriptors.FunctionDescriptor r2 = (org.jetbrains.kotlin.descriptors.FunctionDescriptor) r2
            if (r2 == 0) goto L10
            org.jetbrains.kotlin.com.intellij.psi.PsiElement r0 = org.jetbrains.kotlin.resolve.DescriptorToSourceUtils.getSourceFromDescriptor(r2)
            goto L11
        L24:
            org.jetbrains.kotlin.com.intellij.openapi.util.Pair r3 = new org.jetbrains.kotlin.com.intellij.openapi.util.Pair
            r3.<init>(r2, r0)
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: org.jetbrains.kotlin.resolve.BindingContextUtils.getContainingFunctionSkipFunctionLiterals(org.jetbrains.kotlin.descriptors.DeclarationDescriptor, boolean):org.jetbrains.kotlin.com.intellij.openapi.util.Pair");
    }

    public static ResolvedCall<ConstructorDescriptor> getDelegationConstructorCall(BindingContext bindingContext, ConstructorDescriptor constructorDescriptor) {
        if (bindingContext == null) {
            $$$reportNull$$$0(25);
        }
        if (constructorDescriptor == null) {
            $$$reportNull$$$0(26);
        }
        return (ResolvedCall) bindingContext.get(BindingContext.CONSTRUCTOR_RESOLVED_DELEGATION_CALL, constructorDescriptor);
    }

    static void addOwnDataTo(final BindingTrace bindingTrace, final TraceEntryFilter traceEntryFilter, boolean z, MutableSlicedMap mutableSlicedMap, MutableDiagnosticsWithSuppression mutableDiagnosticsWithSuppression) {
        if (bindingTrace == null) {
            $$$reportNull$$$0(27);
        }
        if (mutableSlicedMap == null) {
            $$$reportNull$$$0(28);
        }
        mutableSlicedMap.forEach(new Function3() { // from class: org.jetbrains.kotlin.resolve.BindingContextUtils$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function3
            public final Object invoke(Object obj, Object obj2, Object obj3) {
                return BindingContextUtils.lambda$addOwnDataTo$0(traceEntryFilter, bindingTrace, (WritableSlice) obj, obj2, obj3);
            }
        });
        if (z) {
            for (Diagnostic diagnostic : mutableDiagnosticsWithSuppression.getOwnDiagnostics()) {
                if (traceEntryFilter == null || traceEntryFilter.accept(null, diagnostic.getPsiElement())) {
                    bindingTrace.report(diagnostic);
                }
            }
        }
    }

    static /* synthetic */ Void lambda$addOwnDataTo$0(TraceEntryFilter traceEntryFilter, BindingTrace bindingTrace, WritableSlice writableSlice, Object obj, Object obj2) {
        if (traceEntryFilter != null && !traceEntryFilter.accept(writableSlice, obj)) {
            return null;
        }
        bindingTrace.record(writableSlice, obj, obj2);
        return null;
    }
}

package org.jetbrains.kotlin.checkers.utils;

import java.util.Collection;
import java.util.HashMap;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.KtNodeTypes;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.google.common.collect.ImmutableSet;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyGetterDescriptor;
import org.jetbrains.kotlin.descriptors.PropertySetterDescriptor;
import org.jetbrains.kotlin.descriptors.VariableAccessorDescriptor;
import org.jetbrains.kotlin.descriptors.VariableDescriptor;
import org.jetbrains.kotlin.descriptors.VariableDescriptorWithAccessors;
import org.jetbrains.kotlin.diagnostics.Diagnostic;
import org.jetbrains.kotlin.diagnostics.DiagnosticFactory;
import org.jetbrains.kotlin.diagnostics.Errors;
import org.jetbrains.kotlin.lexer.KtTokens;
import org.jetbrains.kotlin.psi.KtArrayAccessExpression;
import org.jetbrains.kotlin.psi.KtDestructuringDeclaration;
import org.jetbrains.kotlin.psi.KtDestructuringDeclarationEntry;
import org.jetbrains.kotlin.psi.KtElement;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtForExpression;
import org.jetbrains.kotlin.psi.KtNameReferenceExpression;
import org.jetbrains.kotlin.psi.KtProperty;
import org.jetbrains.kotlin.psi.KtPropertyDelegate;
import org.jetbrains.kotlin.psi.KtReferenceExpression;
import org.jetbrains.kotlin.psi.KtSimpleNameExpression;
import org.jetbrains.kotlin.psi.KtSuperExpression;
import org.jetbrains.kotlin.psi.KtThisExpression;
import org.jetbrains.kotlin.psi.KtTreeVisitorVoid;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.BindingContextUtils;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.resolve.calls.tasks.DynamicCallsKt;
import org.jetbrains.kotlin.resolve.calls.util.CallUtilKt;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.error.ErrorUtils;
import org.jetbrains.kotlin.util.slicedMap.WritableSlice;

/* JADX INFO: compiled from: DebugInfoUtil.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001:\u0001\u0015B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u001e\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eJ\"\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\b\u0010\u0013\u001a\u0004\u0018\u00010\u00142\u0006\u0010\r\u001a\u00020\u000eH\u0002R\u0013\u0010\u0003\u001a\u00070\u0004¢\u0006\u0002\b\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0013\u0010\u0006\u001a\u00070\u0004¢\u0006\u0002\b\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0016"}, d2 = {"Lorg/jetbrains/kotlin/checkers/utils/DebugInfoUtil;", "", "()V", "EXCLUDED", "Lorg/jetbrains/kotlin/com/intellij/psi/tree/TokenSet;", "Lorg/jetbrains/annotations/NotNull;", "MAY_BE_UNRESOLVED", "markDebugAnnotations", "", "root", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "bindingContext", "Lorg/jetbrains/kotlin/resolve/BindingContext;", "debugInfoReporter", "Lorg/jetbrains/kotlin/checkers/utils/DebugInfoUtil$DebugInfoReporter;", "reportIfDynamic", "", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/jetbrains/kotlin/psi/KtElement;", "declarationDescriptor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "DebugInfoReporter", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class DebugInfoUtil {
    private static final TokenSet EXCLUDED;
    public static final DebugInfoUtil INSTANCE = new DebugInfoUtil();
    private static final TokenSet MAY_BE_UNRESOLVED;

    /* JADX INFO: compiled from: DebugInfoUtil.kt */
    @Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0000\b&\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006J\u0018\u0010\u0007\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016J\u0010\u0010\f\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H&J\u0010\u0010\r\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H&J\u0018\u0010\u000e\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u000f\u001a\u00020\u0010H&¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/checkers/utils/DebugInfoUtil$DebugInfoReporter;", "", "()V", "preProcessReference", "", "expression", "Lorg/jetbrains/kotlin/psi/KtReferenceExpression;", "reportDynamicCall", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/jetbrains/kotlin/psi/KtElement;", "declarationDescriptor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "reportElementWithErrorType", "reportMissingUnresolved", "reportUnresolvedWithTarget", "target", "", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static abstract class DebugInfoReporter {
        public final void preProcessReference(KtReferenceExpression expression) {
            Intrinsics.checkNotNullParameter(expression, "expression");
        }

        public void reportDynamicCall(KtElement element, DeclarationDescriptor declarationDescriptor) {
            Intrinsics.checkNotNullParameter(element, "element");
            Intrinsics.checkNotNullParameter(declarationDescriptor, "declarationDescriptor");
        }

        public abstract void reportElementWithErrorType(KtReferenceExpression expression);

        public abstract void reportMissingUnresolved(KtReferenceExpression expression);

        public abstract void reportUnresolvedWithTarget(KtReferenceExpression expression, String target);
    }

    private DebugInfoUtil() {
    }

    static {
        TokenSet tokenSetCreate = TokenSet.create(KtTokens.IN_KEYWORD, KtTokens.NOT_IN);
        Intrinsics.checkNotNullExpressionValue(tokenSetCreate, "create(...)");
        MAY_BE_UNRESOLVED = tokenSetCreate;
        TokenSet tokenSetCreate2 = TokenSet.create(KtTokens.COLON, KtTokens.AS_KEYWORD, KtTokens.AS_SAFE, KtTokens.IS_KEYWORD, KtTokens.NOT_IS, KtTokens.OROR, KtTokens.ANDAND, KtTokens.EQ, KtTokens.EQEQEQ, KtTokens.EXCLEQEQEQ, KtTokens.ELVIS, KtTokens.EXCLEXCL);
        Intrinsics.checkNotNullExpressionValue(tokenSetCreate2, "create(...)");
        EXCLUDED = tokenSetCreate2;
    }

    public final void markDebugAnnotations(PsiElement root, final BindingContext bindingContext, final DebugInfoReporter debugInfoReporter) {
        Intrinsics.checkNotNullParameter(root, "root");
        Intrinsics.checkNotNullParameter(bindingContext, "bindingContext");
        Intrinsics.checkNotNullParameter(debugInfoReporter, "debugInfoReporter");
        final HashMap map = new HashMap();
        for (Diagnostic diagnostic : bindingContext.getDiagnostics()) {
            DiagnosticFactory<?> factory = diagnostic.getFactory();
            ImmutableSet<? extends DiagnosticFactory<?>> UNRESOLVED_REFERENCE_DIAGNOSTICS = Errors.UNRESOLVED_REFERENCE_DIAGNOSTICS;
            Intrinsics.checkNotNullExpressionValue(UNRESOLVED_REFERENCE_DIAGNOSTICS, "UNRESOLVED_REFERENCE_DIAGNOSTICS");
            if (CollectionsKt.contains(UNRESOLVED_REFERENCE_DIAGNOSTICS, diagnostic.getFactory())) {
                PsiElement psiElement = diagnostic.getPsiElement();
                Intrinsics.checkNotNull(psiElement, "null cannot be cast to non-null type org.jetbrains.kotlin.psi.KtReferenceExpression");
                map.put((KtReferenceExpression) psiElement, factory);
            } else if (factory == Errors.SUPER_IS_NOT_AN_EXPRESSION || factory == Errors.SUPER_NOT_AVAILABLE) {
                PsiElement psiElement2 = diagnostic.getPsiElement();
                Intrinsics.checkNotNull(psiElement2, "null cannot be cast to non-null type org.jetbrains.kotlin.psi.KtSuperExpression");
                KtReferenceExpression instanceReference = ((KtSuperExpression) psiElement2).getInstanceReference();
                Intrinsics.checkNotNullExpressionValue(instanceReference, "getInstanceReference(...)");
                map.put(instanceReference, factory);
            } else if (factory == Errors.EXPRESSION_EXPECTED_PACKAGE_FOUND) {
                PsiElement psiElement3 = diagnostic.getPsiElement();
                Intrinsics.checkNotNull(psiElement3, "null cannot be cast to non-null type org.jetbrains.kotlin.psi.KtSimpleNameExpression");
                map.put((KtSimpleNameExpression) psiElement3, factory);
            } else if (factory == Errors.UNSUPPORTED || factory == Errors.UNSUPPORTED_FEATURE) {
                for (KtReferenceExpression ktReferenceExpression : PsiTreeUtil.findChildrenOfType(diagnostic.getPsiElement(), KtReferenceExpression.class)) {
                    Intrinsics.checkNotNull(ktReferenceExpression);
                    map.put(ktReferenceExpression, factory);
                }
            }
        }
        root.acceptChildren(new KtTreeVisitorVoid() { // from class: org.jetbrains.kotlin.checkers.utils.DebugInfoUtil.markDebugAnnotations.1
            @Override // org.jetbrains.kotlin.psi.KtVisitorVoid
            public void visitForExpression(KtForExpression expression) {
                Intrinsics.checkNotNullParameter(expression, "expression");
                KtExpression loopRange = expression.getLoopRange();
                if (loopRange != null) {
                    KtExpression ktExpression = loopRange;
                    WritableSlice<KtExpression, ResolvedCall<FunctionDescriptor>> LOOP_RANGE_ITERATOR_RESOLVED_CALL = BindingContext.LOOP_RANGE_ITERATOR_RESOLVED_CALL;
                    Intrinsics.checkNotNullExpressionValue(LOOP_RANGE_ITERATOR_RESOLVED_CALL, "LOOP_RANGE_ITERATOR_RESOLVED_CALL");
                    reportIfDynamicCall(ktExpression, loopRange, LOOP_RANGE_ITERATOR_RESOLVED_CALL);
                    WritableSlice<KtExpression, ResolvedCall<FunctionDescriptor>> LOOP_RANGE_HAS_NEXT_RESOLVED_CALL = BindingContext.LOOP_RANGE_HAS_NEXT_RESOLVED_CALL;
                    Intrinsics.checkNotNullExpressionValue(LOOP_RANGE_HAS_NEXT_RESOLVED_CALL, "LOOP_RANGE_HAS_NEXT_RESOLVED_CALL");
                    reportIfDynamicCall(ktExpression, loopRange, LOOP_RANGE_HAS_NEXT_RESOLVED_CALL);
                    WritableSlice<KtExpression, ResolvedCall<FunctionDescriptor>> LOOP_RANGE_NEXT_RESOLVED_CALL = BindingContext.LOOP_RANGE_NEXT_RESOLVED_CALL;
                    Intrinsics.checkNotNullExpressionValue(LOOP_RANGE_NEXT_RESOLVED_CALL, "LOOP_RANGE_NEXT_RESOLVED_CALL");
                    reportIfDynamicCall(ktExpression, loopRange, LOOP_RANGE_NEXT_RESOLVED_CALL);
                }
                super.visitForExpression(expression);
            }

            @Override // org.jetbrains.kotlin.psi.KtVisitorVoid
            public void visitDestructuringDeclaration(KtDestructuringDeclaration destructuringDeclaration) {
                Intrinsics.checkNotNullParameter(destructuringDeclaration, "destructuringDeclaration");
                for (KtDestructuringDeclarationEntry ktDestructuringDeclarationEntry : destructuringDeclaration.getEntries()) {
                    WritableSlice<KtDestructuringDeclarationEntry, ResolvedCall<FunctionDescriptor>> COMPONENT_RESOLVED_CALL = BindingContext.COMPONENT_RESOLVED_CALL;
                    Intrinsics.checkNotNullExpressionValue(COMPONENT_RESOLVED_CALL, "COMPONENT_RESOLVED_CALL");
                    reportIfDynamicCall(ktDestructuringDeclarationEntry, ktDestructuringDeclarationEntry, COMPONENT_RESOLVED_CALL);
                }
                super.visitDestructuringDeclaration(destructuringDeclaration);
            }

            @Override // org.jetbrains.kotlin.psi.KtVisitorVoid
            public void visitProperty(KtProperty property) {
                Intrinsics.checkNotNullParameter(property, "property");
                VariableDescriptor variableDescriptor = (VariableDescriptor) bindingContext.get(BindingContext.VARIABLE, property);
                KtPropertyDelegate delegate = property.getDelegate();
                if ((variableDescriptor instanceof PropertyDescriptor) && delegate != null) {
                    KtPropertyDelegate ktPropertyDelegate = delegate;
                    WritableSlice<VariableDescriptorWithAccessors, ResolvedCall<FunctionDescriptor>> PROVIDE_DELEGATE_RESOLVED_CALL = BindingContext.PROVIDE_DELEGATE_RESOLVED_CALL;
                    Intrinsics.checkNotNullExpressionValue(PROVIDE_DELEGATE_RESOLVED_CALL, "PROVIDE_DELEGATE_RESOLVED_CALL");
                    reportIfDynamicCall(ktPropertyDelegate, variableDescriptor, PROVIDE_DELEGATE_RESOLVED_CALL);
                    PropertyDescriptor propertyDescriptor = (PropertyDescriptor) variableDescriptor;
                    PropertyGetterDescriptor getter = propertyDescriptor.getGetter();
                    WritableSlice<VariableAccessorDescriptor, ResolvedCall<FunctionDescriptor>> DELEGATED_PROPERTY_RESOLVED_CALL = BindingContext.DELEGATED_PROPERTY_RESOLVED_CALL;
                    Intrinsics.checkNotNullExpressionValue(DELEGATED_PROPERTY_RESOLVED_CALL, "DELEGATED_PROPERTY_RESOLVED_CALL");
                    reportIfDynamicCall(ktPropertyDelegate, getter, DELEGATED_PROPERTY_RESOLVED_CALL);
                    PropertySetterDescriptor setter = propertyDescriptor.getSetter();
                    WritableSlice<VariableAccessorDescriptor, ResolvedCall<FunctionDescriptor>> DELEGATED_PROPERTY_RESOLVED_CALL2 = BindingContext.DELEGATED_PROPERTY_RESOLVED_CALL;
                    Intrinsics.checkNotNullExpressionValue(DELEGATED_PROPERTY_RESOLVED_CALL2, "DELEGATED_PROPERTY_RESOLVED_CALL");
                    reportIfDynamicCall(ktPropertyDelegate, setter, DELEGATED_PROPERTY_RESOLVED_CALL2);
                }
                super.visitProperty(property);
            }

            @Override // org.jetbrains.kotlin.psi.KtVisitorVoid
            public void visitThisExpression(KtThisExpression expression) {
                Intrinsics.checkNotNullParameter(expression, "expression");
                KtThisExpression ktThisExpression = expression;
                ResolvedCall<? extends CallableDescriptor> resolvedCall = CallUtilKt.getResolvedCall(ktThisExpression, bindingContext);
                if (resolvedCall != null) {
                    DebugInfoUtil.INSTANCE.reportIfDynamic(ktThisExpression, resolvedCall.getResultingDescriptor(), debugInfoReporter);
                }
                super.visitThisExpression(expression);
            }

            @Override // org.jetbrains.kotlin.psi.KtVisitorVoid
            public void visitReferenceExpression(KtReferenceExpression expression) {
                IElementType referencedNameElementType;
                Collection collection;
                Collection collection2;
                PsiElement psiElement4;
                Intrinsics.checkNotNullParameter(expression, "expression");
                super.visitReferenceExpression(expression);
                KtReferenceExpression ktReferenceExpression2 = expression;
                if (BindingContextUtils.isExpressionWithValidReference(ktReferenceExpression2, bindingContext)) {
                    String text = null;
                    if (expression instanceof KtSimpleNameExpression) {
                        IElementType elementType = expression.getNode().getElementType();
                        Intrinsics.checkNotNullExpressionValue(elementType, "getElementType(...)");
                        if (elementType == KtNodeTypes.OPERATION_REFERENCE) {
                            referencedNameElementType = ((KtSimpleNameExpression) expression).getReferencedNameElementType();
                            if (DebugInfoUtil.EXCLUDED.contains(referencedNameElementType)) {
                                return;
                            }
                        } else {
                            referencedNameElementType = null;
                        }
                        if (elementType == KtNodeTypes.LABEL || ((KtSimpleNameExpression) expression).getReferencedNameElementType() == KtTokens.THIS_KEYWORD) {
                            return;
                        }
                    } else {
                        referencedNameElementType = null;
                    }
                    debugInfoReporter.preProcessReference(expression);
                    DeclarationDescriptor declarationDescriptor = (DeclarationDescriptor) bindingContext.get(BindingContext.REFERENCE_TARGET, expression);
                    if (declarationDescriptor != null) {
                        text = declarationDescriptor.toString();
                        DebugInfoUtil.INSTANCE.reportIfDynamic(expression, declarationDescriptor, debugInfoReporter);
                    }
                    if (text == null && (psiElement4 = (PsiElement) bindingContext.get(BindingContext.LABEL_TARGET, expression)) != null) {
                        text = psiElement4.getText();
                    }
                    if (text == null && (collection2 = (Collection) bindingContext.get(BindingContext.AMBIGUOUS_REFERENCE_TARGET, expression)) != null) {
                        text = "[" + collection2.size() + " descriptors]";
                    }
                    if (text == null && (collection = (Collection) bindingContext.get(BindingContext.AMBIGUOUS_LABEL_TARGET, expression)) != null) {
                        text = "[" + collection.size() + " elements]";
                    }
                    if (DebugInfoUtil.MAY_BE_UNRESOLVED.contains(referencedNameElementType)) {
                        return;
                    }
                    if ((expression instanceof KtNameReferenceExpression) && ((KtNameReferenceExpression) expression).isPlaceholder()) {
                        return;
                    }
                    boolean z = text != null;
                    boolean zContainsKey = ((expression instanceof KtArrayAccessExpression) && map.containsKey(((KtArrayAccessExpression) expression).getArrayExpression())) ? true : map.containsKey(expression);
                    KotlinType type = bindingContext.getType(ktReferenceExpression2);
                    DiagnosticFactory<?> diagnosticFactory = map.get(expression);
                    if (declarationDescriptor != null && ((ErrorUtils.isError(declarationDescriptor) || ErrorUtils.INSTANCE.containsErrorType(type)) && diagnosticFactory != Errors.EXPRESSION_EXPECTED_PACKAGE_FOUND)) {
                        debugInfoReporter.reportElementWithErrorType(expression);
                    }
                    if (!z || !zContainsKey) {
                        if (z || zContainsKey) {
                            return;
                        }
                        debugInfoReporter.reportMissingUnresolved(expression);
                        return;
                    }
                    ImmutableSet<? extends DiagnosticFactory<?>> UNRESOLVED_REFERENCE_DIAGNOSTICS2 = Errors.UNRESOLVED_REFERENCE_DIAGNOSTICS;
                    Intrinsics.checkNotNullExpressionValue(UNRESOLVED_REFERENCE_DIAGNOSTICS2, "UNRESOLVED_REFERENCE_DIAGNOSTICS");
                    if (CollectionsKt.contains(UNRESOLVED_REFERENCE_DIAGNOSTICS2, diagnosticFactory)) {
                        DebugInfoReporter debugInfoReporter2 = debugInfoReporter;
                        Intrinsics.checkNotNull(text);
                        debugInfoReporter2.reportUnresolvedWithTarget(expression, text);
                    }
                }
            }

            private final <E extends KtElement, K, D extends CallableDescriptor> boolean reportIfDynamicCall(E element, K key, WritableSlice<K, ResolvedCall<D>> slice) {
                ResolvedCall resolvedCall = (ResolvedCall) bindingContext.get(slice, key);
                if (resolvedCall != null) {
                    return DebugInfoUtil.INSTANCE.reportIfDynamic(element, resolvedCall.getResultingDescriptor(), debugInfoReporter);
                }
                return false;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean reportIfDynamic(KtElement element, DeclarationDescriptor declarationDescriptor, DebugInfoReporter debugInfoReporter) {
        if (declarationDescriptor == null || !DynamicCallsKt.isDynamic(declarationDescriptor)) {
            return false;
        }
        debugInfoReporter.reportDynamicCall(element, declarationDescriptor);
        return true;
    }
}

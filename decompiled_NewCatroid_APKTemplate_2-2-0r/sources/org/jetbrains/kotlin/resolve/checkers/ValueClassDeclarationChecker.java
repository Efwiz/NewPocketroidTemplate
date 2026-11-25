package org.jetbrains.kotlin.resolve.checkers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.StandardNames;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.DescriptorUtilKt;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.descriptors.SimpleFunctionDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.diagnostics.DiagnosticFactory0;
import org.jetbrains.kotlin.diagnostics.Errors;
import org.jetbrains.kotlin.lexer.KtTokens;
import org.jetbrains.kotlin.psi.KtClass;
import org.jetbrains.kotlin.psi.KtContextReceiverList;
import org.jetbrains.kotlin.psi.KtDeclaration;
import org.jetbrains.kotlin.psi.KtDelegatedSuperTypeEntry;
import org.jetbrains.kotlin.psi.KtElementImplStub;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtModifierList;
import org.jetbrains.kotlin.psi.KtNamedFunction;
import org.jetbrains.kotlin.psi.KtParameter;
import org.jetbrains.kotlin.psi.KtParameterList;
import org.jetbrains.kotlin.psi.KtPrimaryConstructor;
import org.jetbrains.kotlin.psi.KtSuperTypeListEntry;
import org.jetbrains.kotlin.psi.KtTypeReference;
import org.jetbrains.kotlin.psi.psiUtil.KtPsiUtilKt;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.BindingTrace;
import org.jetbrains.kotlin.resolve.DescriptorUtils;
import org.jetbrains.kotlin.resolve.InlineClassesUtilsKt;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.resolve.calls.util.CallUtilKt;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.SimpleType;
import org.jetbrains.kotlin.types.typeUtil.TypeUtilsKt;

/* JADX INFO: compiled from: ValueClassDeclarationChecker.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J \u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016J\u0010\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0002J\f\u0010\u000f\u001a\u00020\f*\u00020\u0010H\u0002¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/resolve/checkers/ValueClassDeclarationChecker;", "Lorg/jetbrains/kotlin/resolve/checkers/DeclarationChecker;", "()V", "check", "", "declaration", "Lorg/jetbrains/kotlin/psi/KtDeclaration;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "context", "Lorg/jetbrains/kotlin/resolve/checkers/DeclarationCheckerContext;", "isParameterAcceptableForInlineClass", "", "parameter", "Lorg/jetbrains/kotlin/psi/KtParameter;", "isInapplicableParameterType", "Lorg/jetbrains/kotlin/types/KotlinType;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ValueClassDeclarationChecker implements DeclarationChecker {
    public static final ValueClassDeclarationChecker INSTANCE = new ValueClassDeclarationChecker();

    private ValueClassDeclarationChecker() {
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.resolve.checkers.DeclarationChecker
    public void check(KtDeclaration declaration, DeclarationDescriptor descriptor, DeclarationCheckerContext context) {
        boolean z;
        List<KotlinType> listEmptyList;
        boolean z2;
        Object next;
        Object obj;
        KotlinType kotlinType;
        SimpleType defaultType;
        PsiElement modifier;
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(context, "context");
        if ((declaration instanceof KtClass) && ((z = descriptor instanceof ClassDescriptor))) {
            ClassDescriptor classDescriptor = (ClassDescriptor) descriptor;
            if ((classDescriptor.isInline() || classDescriptor.isValue()) && classDescriptor.getClassKind() == ClassKind.CLASS) {
                BindingTrace trace = context.getTrace();
                KtClass ktClass = (KtClass) declaration;
                KtModifierList modifierList = ktClass.getModifierList();
                PsiElement modifier2 = modifierList != null ? modifierList.getModifier(KtTokens.VALUE_KEYWORD) : null;
                if (modifier2 != null && !context.getLanguageVersionSettings().supportsFeature(LanguageFeature.JvmInlineValueClasses) && !context.getLanguageVersionSettings().supportsFeature(LanguageFeature.InlineClasses)) {
                    trace.report(Errors.UNSUPPORTED_FEATURE.on(modifier2, TuplesKt.to(LanguageFeature.JvmInlineValueClasses, context.getLanguageVersionSettings())));
                    return;
                }
                KtModifierList modifierList2 = ktClass.getModifierList();
                if (modifierList2 != null && (modifier = modifierList2.getModifier(KtTokens.INLINE_KEYWORD)) != null) {
                    modifier2 = modifier;
                }
                if (modifier2 == null) {
                    throw new IllegalArgumentException("Declaration of inline class must have 'inline' keyword".toString());
                }
                if (classDescriptor.getIsInner() || DescriptorUtils.isLocal(descriptor)) {
                    trace.report(Errors.VALUE_CLASS_NOT_TOP_LEVEL.on(modifier2));
                    return;
                }
                if (!ktClass.getContextReceivers().isEmpty()) {
                    KtContextReceiverList contextReceiverList = ktClass.getContextReceiverList();
                    if (contextReceiverList == null) {
                        throw new IllegalArgumentException("Declaration cannot have context receivers with no context receiver list".toString());
                    }
                    trace.report(Errors.VALUE_CLASS_CANNOT_HAVE_CONTEXT_RECEIVERS.on(contextReceiverList));
                }
                PsiElement psiElementModalityModifier = KtPsiUtilKt.modalityModifier(declaration);
                if (psiElementModalityModifier != null && classDescriptor.getModality() != Modality.FINAL) {
                    trace.report(Errors.VALUE_CLASS_NOT_FINAL.on(psiElementModalityModifier));
                    return;
                }
                KtPrimaryConstructor primaryConstructor = ktClass.getPrimaryConstructor();
                if (primaryConstructor == null) {
                    trace.report(Errors.ABSENCE_OF_PRIMARY_CONSTRUCTOR_FOR_VALUE_CLASS.on(modifier2));
                    return;
                }
                if (context.getLanguageVersionSettings().supportsFeature(LanguageFeature.ValueClasses)) {
                    if (primaryConstructor.getValueParameters().isEmpty()) {
                        KtParameterList valueParameterList = primaryConstructor.getValueParameterList();
                        trace.report(Errors.VALUE_CLASS_EMPTY_CONSTRUCTOR.on(valueParameterList != null ? valueParameterList : (KtElementImplStub) declaration));
                        return;
                    }
                } else if (primaryConstructor.getValueParameters().size() != 1) {
                    KtParameterList valueParameterList2 = primaryConstructor.getValueParameterList();
                    trace.report(Errors.INLINE_CLASS_CONSTRUCTOR_WRONG_PARAMETERS_SIZE.on(valueParameterList2 != null ? valueParameterList2 : (KtElementImplStub) declaration));
                    return;
                }
                ClassDescriptor classDescriptor2 = z ? classDescriptor : null;
                if (classDescriptor2 == null || (defaultType = classDescriptor2.getDefaultType()) == null || (listEmptyList = InlineClassesUtilsKt.substitutedUnderlyingTypes(defaultType)) == null) {
                    listEmptyList = CollectionsKt.emptyList();
                }
                Iterator it = CollectionsKt.zip(primaryConstructor.getValueParameters(), listEmptyList).iterator();
                boolean z3 = true;
                while (true) {
                    z2 = false;
                    if (!it.hasNext()) {
                        break;
                    }
                    Pair pair = (Pair) it.next();
                    KtParameter ktParameter = (KtParameter) pair.component1();
                    KotlinType kotlinType2 = (KotlinType) pair.component2();
                    Intrinsics.checkNotNull(ktParameter);
                    if (!isParameterAcceptableForInlineClass(ktParameter)) {
                        trace.report(Errors.VALUE_CLASS_CONSTRUCTOR_NOT_FINAL_READ_ONLY_PARAMETER.on(ktParameter));
                        z3 = false;
                    } else {
                        KtTypeReference ktTypeReferenceMo7396getTypeReference = ktParameter.mo7396getTypeReference();
                        if (kotlinType2 != null && ktTypeReferenceMo7396getTypeReference != null) {
                            if (!context.getLanguageVersionSettings().supportsFeature(LanguageFeature.GenericInlineClassParameter) && (TypeUtilsKt.isTypeParameter(kotlinType2) || TypeUtilsKt.isGenericArrayOfTypeParameter(kotlinType2))) {
                                trace.report(Errors.UNSUPPORTED_FEATURE.on(ktTypeReferenceMo7396getTypeReference, TuplesKt.to(LanguageFeature.GenericInlineClassParameter, context.getLanguageVersionSettings())));
                            } else if (isInapplicableParameterType(kotlinType2)) {
                                trace.report(Errors.VALUE_CLASS_HAS_INAPPLICABLE_PARAMETER_TYPE.on(ktTypeReferenceMo7396getTypeReference, kotlinType2));
                            } else if (InlineClassesUtilsKt.isRecursiveInlineOrValueClassType(kotlinType2)) {
                                trace.report(Errors.VALUE_CLASS_CANNOT_BE_RECURSIVE.on(ktTypeReferenceMo7396getTypeReference));
                            } else if (InlineClassesUtilsKt.isMultiFieldValueClass(descriptor) && ktParameter.getDefaultValue() != null) {
                                DiagnosticFactory0<KtExpression> diagnosticFactory0 = Errors.MULTI_FIELD_VALUE_CLASS_PRIMARY_CONSTRUCTOR_DEFAULT_PARAMETER;
                                KtExpression defaultValue = ktParameter.getDefaultValue();
                                Intrinsics.checkNotNull(defaultValue);
                                trace.report(diagnosticFactory0.on(defaultValue));
                            }
                            z3 = false;
                        }
                    }
                }
                if (z3) {
                    for (KtSuperTypeListEntry ktSuperTypeListEntry : ktClass.getSuperTypeListEntries()) {
                        KtTypeReference typeReference = ktSuperTypeListEntry.getTypeReference();
                        if (typeReference != null && (kotlinType = (KotlinType) trace.get(BindingContext.TYPE, typeReference)) != null) {
                            if (ktSuperTypeListEntry instanceof KtDelegatedSuperTypeEntry) {
                                KtExpression delegateExpression = ((KtDelegatedSuperTypeEntry) ktSuperTypeListEntry).getDelegateExpression();
                                BindingContext context2 = trace.getContext();
                                Intrinsics.checkNotNullExpressionValue(context2, "getBindingContext(...)");
                                ResolvedCall<? extends CallableDescriptor> resolvedCall = CallUtilKt.getResolvedCall(delegateExpression, context2);
                                if (resolvedCall != null && (!context.getLanguageVersionSettings().supportsFeature(LanguageFeature.InlineClassImplementationByDelegation) || !(resolvedCall.getResultingDescriptor() instanceof ValueParameterDescriptor) || !Intrinsics.areEqual(resolvedCall.getResultingDescriptor().getContainingModule(), trace.getContext().get(BindingContext.DECLARATION_TO_DESCRIPTOR, primaryConstructor)))) {
                                    trace.report(Errors.VALUE_CLASS_CANNOT_IMPLEMENT_INTERFACE_BY_DELEGATION.on(ktSuperTypeListEntry));
                                    return;
                                }
                            } else {
                                ClassifierDescriptor this$0 = kotlinType.getConstructor().getThis$0();
                                if (this$0 != null && !DescriptorUtils.isInterface(this$0)) {
                                    trace.report(Errors.VALUE_CLASS_CANNOT_EXTEND_CLASSES.on(typeReference));
                                    return;
                                }
                            }
                        }
                    }
                    for (ClassifierDescriptor classifierDescriptor : DescriptorUtilsKt.getAllSuperClassifiers((ClassifierDescriptor) descriptor)) {
                        if (Intrinsics.areEqual(DescriptorUtilsKt.getFqNameUnsafe(classifierDescriptor), StandardNames.FqNames.cloneable) || Intrinsics.areEqual(DescriptorUtilsKt.getFqNameUnsafe(classifierDescriptor), ValueClassDeclarationCheckerKt.javaLangCloneable)) {
                            trace.report(Errors.VALUE_CLASS_CANNOT_BE_CLONEABLE.on(modifier2));
                            return;
                        }
                    }
                    if (context.getLanguageVersionSettings().supportsFeature(LanguageFeature.CustomEqualsInValueClasses)) {
                        Iterator<T> it2 = check$namedFunctions(ktClass).iterator();
                        do {
                            if (!it2.hasNext()) {
                                next = null;
                                break;
                            }
                            next = it2.next();
                        } while (!check$isTypedEquals(context, (KtNamedFunction) next));
                        KtNamedFunction ktNamedFunction = (KtNamedFunction) next;
                        Iterator<T> it3 = check$namedFunctions(ktClass).iterator();
                        Object obj2 = null;
                        while (true) {
                            if (!it3.hasNext()) {
                                if (!z2) {
                                    break;
                                }
                                obj = obj2;
                                break;
                            } else {
                                Object next2 = it3.next();
                                if (check$isUntypedEquals(context, (KtNamedFunction) next2)) {
                                    if (!z2) {
                                        obj2 = next2;
                                        z2 = true;
                                    }
                                }
                            }
                            obj = null;
                            break;
                        }
                        KtNamedFunction ktNamedFunction2 = (KtNamedFunction) obj;
                        if (ktNamedFunction2 == null || ktNamedFunction != null) {
                            return;
                        }
                        SimpleType defaultType2 = classDescriptor.getDefaultType();
                        Intrinsics.checkNotNullExpressionValue(defaultType2, "getDefaultType(...)");
                        trace.report(Errors.INEFFICIENT_EQUALS_OVERRIDING_IN_VALUE_CLASS.on(ktNamedFunction2, TypeUtilsKt.replaceArgumentsWithStarProjections(defaultType2)));
                    }
                }
            }
        }
    }

    private static final SimpleFunctionDescriptor check$getFunctionDescriptor(DeclarationCheckerContext declarationCheckerContext, KtNamedFunction ktNamedFunction) {
        return (SimpleFunctionDescriptor) declarationCheckerContext.getTrace().getContext().get(BindingContext.FUNCTION, ktNamedFunction);
    }

    private static final boolean check$isUntypedEquals(DeclarationCheckerContext declarationCheckerContext, KtNamedFunction ktNamedFunction) {
        SimpleFunctionDescriptor simpleFunctionDescriptorCheck$getFunctionDescriptor = check$getFunctionDescriptor(declarationCheckerContext, ktNamedFunction);
        if (simpleFunctionDescriptorCheck$getFunctionDescriptor != null) {
            return DescriptorUtilKt.overridesEqualsFromAny(simpleFunctionDescriptorCheck$getFunctionDescriptor);
        }
        return false;
    }

    private static final boolean check$isTypedEquals(DeclarationCheckerContext declarationCheckerContext, KtNamedFunction ktNamedFunction) {
        SimpleFunctionDescriptor simpleFunctionDescriptorCheck$getFunctionDescriptor = check$getFunctionDescriptor(declarationCheckerContext, ktNamedFunction);
        if (simpleFunctionDescriptorCheck$getFunctionDescriptor != null) {
            return DescriptorUtilKt.isTypedEqualsInValueClass(simpleFunctionDescriptorCheck$getFunctionDescriptor);
        }
        return false;
    }

    private static final List<KtNamedFunction> check$namedFunctions(KtClass ktClass) {
        List<KtDeclaration> declarations = ktClass.getDeclarations();
        ArrayList arrayList = new ArrayList();
        for (Object obj : declarations) {
            if (obj instanceof KtNamedFunction) {
                arrayList.add(obj);
            }
        }
        return arrayList;
    }

    private final boolean isInapplicableParameterType(KotlinType kotlinType) {
        return TypeUtilsKt.isUnit(kotlinType) || TypeUtilsKt.isNothing(kotlinType);
    }

    private final boolean isParameterAcceptableForInlineClass(KtParameter parameter) {
        ASTNode node;
        PsiElement psiElementModalityModifier = KtPsiUtilKt.modalityModifier(parameter);
        return (!parameter.hasValOrVar() || parameter.isMutable() || parameter.isVarArg() || Intrinsics.areEqual((psiElementModalityModifier == null || (node = psiElementModalityModifier.getNode()) == null) ? null : node.getElementType(), KtTokens.OPEN_KEYWORD)) ? false : true;
    }
}

package org.jetbrains.kotlin.resolve.bindingContextUtil;

import androidx.exifinterface.media.ExifInterface;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.SequencesKt;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.SimpleFunctionDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.descriptors.impl.AnonymousFunctionDescriptor;
import org.jetbrains.kotlin.psi.KtCallExpression;
import org.jetbrains.kotlin.psi.KtCallableDeclaration;
import org.jetbrains.kotlin.psi.KtClassOrObject;
import org.jetbrains.kotlin.psi.KtCodeFragment;
import org.jetbrains.kotlin.psi.KtDeclarationWithBody;
import org.jetbrains.kotlin.psi.KtElement;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtFunction;
import org.jetbrains.kotlin.psi.KtFunctionLiteral;
import org.jetbrains.kotlin.psi.KtLambdaExpression;
import org.jetbrains.kotlin.psi.KtNamedDeclaration;
import org.jetbrains.kotlin.psi.KtNamedFunction;
import org.jetbrains.kotlin.psi.KtNullableType;
import org.jetbrains.kotlin.psi.KtPsiUtil;
import org.jetbrains.kotlin.psi.KtQualifiedExpression;
import org.jetbrains.kotlin.psi.KtReferenceExpression;
import org.jetbrains.kotlin.psi.KtReturnExpression;
import org.jetbrains.kotlin.psi.KtSimpleNameExpression;
import org.jetbrains.kotlin.psi.KtTypeElement;
import org.jetbrains.kotlin.psi.KtTypeParameterListOwner;
import org.jetbrains.kotlin.psi.KtTypeReference;
import org.jetbrains.kotlin.psi.KtValueArgument;
import org.jetbrains.kotlin.psi.KtValueArgumentList;
import org.jetbrains.kotlin.psi.ValueArgument;
import org.jetbrains.kotlin.psi.psiUtil.PsiUtilsKt;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.BindingTrace;
import org.jetbrains.kotlin.resolve.DescriptorToSourceUtils;
import org.jetbrains.kotlin.resolve.DescriptorUtils;
import org.jetbrains.kotlin.resolve.calls.context.ResolutionContext;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedValueArgument;
import org.jetbrains.kotlin.resolve.calls.smartcasts.DataFlowInfo;
import org.jetbrains.kotlin.resolve.calls.util.CallUtilKt;
import org.jetbrains.kotlin.resolve.scopes.HierarchicalScope;
import org.jetbrains.kotlin.resolve.scopes.LexicalScope;
import org.jetbrains.kotlin.resolve.scopes.utils.ScopeUtilsKt;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.expressions.KotlinTypeInfo;
import org.jetbrains.kotlin.types.expressions.typeInfoFactory.TypeInfoFactoryKt;
import org.jetbrains.kotlin.types.typeUtil.TypeUtilsKt;
import org.jetbrains.kotlin.util.slicedMap.WritableSlice;
import org.jetbrains.kotlin.utils.KotlinExceptionWithAttachments;

/* JADX INFO: compiled from: BindingContextUtils.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000|\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u001e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u001a\u0016\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005\u001a \u0010\u0006\u001a\u0004\u0018\u00010\u00072\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\b\u001a\u00020\t\u001a\u0016\u0010\n\u001a\u00020\t2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\u0002\u001a\u00020\u0003\u001a\u0014\u0010\r\u001a\u0004\u0018\u00010\u000e*\u00020\u000f2\u0006\u0010\u0002\u001a\u00020\u0003\u001a\u0014\u0010\r\u001a\u0004\u0018\u00010\u000e*\u00020\u00102\u0006\u0010\u0002\u001a\u00020\u0003\u001a\u0012\u0010\u0011\u001a\u00020\u0012*\u00020\u00032\u0006\u0010\u0013\u001a\u00020\u0014\u001a\u0012\u0010\u0015\u001a\u00020\u0012*\u00020\u00032\u0006\u0010\u0013\u001a\u00020\u0014\u001a?\u0010\u0016\u001a\u0004\u0018\u0001H\u0017\"\b\b\u0000\u0010\u0017*\u00020\u0014*\u00020\u00052\"\u0010\u0018\u001a\u0012\u0012\u000e\b\u0001\u0012\n\u0012\u0006\b\u0001\u0012\u0002H\u00170\u001a0\u0019\"\n\u0012\u0006\b\u0001\u0012\u0002H\u00170\u001a¢\u0006\u0002\u0010\u001b\u001a\u0018\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u00010\u001d*\u00020\f2\u0006\u0010\u0002\u001a\u00020\u0003\u001a\u0014\u0010\u001e\u001a\u0004\u0018\u00010\u001f*\u00020 2\u0006\u0010\u0002\u001a\u00020\u0003\u001a\u0014\u0010!\u001a\u0004\u0018\u00010\u0007*\u00020 2\u0006\u0010\u0002\u001a\u00020\u0003\u001a\u0012\u0010\"\u001a\u00020\t*\u00020\u00052\u0006\u0010\u0002\u001a\u00020\u0003\u001a\u0012\u0010#\u001a\u00020\t*\u00020\f2\u0006\u0010\u0002\u001a\u00020\u0003\u001a\u0012\u0010$\u001a\u00020\t*\u00020\f2\u0006\u0010\u0002\u001a\u00020\u0003\u001a*\u0010%\u001a\u00020&\"\u000e\b\u0000\u0010'*\b\u0012\u0004\u0012\u0002H'0(*\b\u0012\u0004\u0012\u0002H'0(2\b\u0010\u000b\u001a\u0004\u0018\u00010\f\u001a\u001c\u0010)\u001a\u00020&*\u00020*2\u0006\u0010+\u001a\u00020,2\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u001a\u001a\u0010-\u001a\u00020&*\u00020\u00052\u0006\u0010.\u001a\u00020*2\u0006\u0010/\u001a\u00020\t¨\u00060"}, d2 = {"getEnclosingDescriptor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "context", "Lorg/jetbrains/kotlin/resolve/BindingContext;", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/jetbrains/kotlin/psi/KtElement;", "getEnclosingFunctionDescriptor", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "skipInlineFunctionLiterals", "", "isInlineableFunctionLiteral", "expression", "Lorg/jetbrains/kotlin/psi/KtExpression;", "getAbbreviatedTypeOrType", "Lorg/jetbrains/kotlin/types/KotlinType;", "Lorg/jetbrains/kotlin/psi/KtTypeElement;", "Lorg/jetbrains/kotlin/psi/KtTypeReference;", "getDataFlowInfoAfter", "Lorg/jetbrains/kotlin/resolve/calls/smartcasts/DataFlowInfo;", "position", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "getDataFlowInfoBefore", "getParentOfTypeCodeFragmentAware", ExifInterface.GPS_DIRECTION_TRUE, "parentClasses", "", "Ljava/lang/Class;", "(Lorg/jetbrains/kotlin/psi/KtElement;[Ljava/lang/Class;)Lcom/intellij/psi/PsiElement;", "getReferenceTargets", "", "getTargetFunction", "Lorg/jetbrains/kotlin/psi/KtCallableDeclaration;", "Lorg/jetbrains/kotlin/psi/KtReturnExpression;", "getTargetFunctionDescriptor", "isUsedAsExpression", "isUsedAsResultOfLambda", "isUsedAsStatement", "recordDataFlowInfo", "", "C", "Lorg/jetbrains/kotlin/resolve/calls/context/ResolutionContext;", "recordScope", "Lorg/jetbrains/kotlin/resolve/BindingTrace;", "scope", "Lorg/jetbrains/kotlin/resolve/scopes/LexicalScope;", "recordUsedAsExpression", "trace", "value", "frontend"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class BindingContextUtilsKt {
    public static final FunctionDescriptor getTargetFunctionDescriptor(KtReturnExpression ktReturnExpression, BindingContext context) {
        Intrinsics.checkNotNullParameter(ktReturnExpression, "<this>");
        Intrinsics.checkNotNullParameter(context, "context");
        KtSimpleNameExpression targetLabel = ktReturnExpression.getTargetLabel();
        if (targetLabel != null) {
            PsiElement psiElement = (PsiElement) context.get(BindingContext.LABEL_TARGET, targetLabel);
            return psiElement != null ? (SimpleFunctionDescriptor) context.get(BindingContext.FUNCTION, psiElement) : null;
        }
        FunctionDescriptor functionDescriptor = (FunctionDescriptor) DescriptorUtils.getParentOfType((DeclarationDescriptor) context.get(BindingContext.DECLARATION_TO_DESCRIPTOR, PsiTreeUtil.getParentOfType(ktReturnExpression, KtDeclarationWithBody.class, false)), FunctionDescriptor.class, false);
        if (functionDescriptor == null) {
            return null;
        }
        return (FunctionDescriptor) SequencesKt.firstOrNull(SequencesKt.dropWhile(SequencesKt.generateSequence(functionDescriptor, new Function1<FunctionDescriptor, FunctionDescriptor>() { // from class: org.jetbrains.kotlin.resolve.bindingContextUtil.BindingContextUtilsKt.getTargetFunctionDescriptor.2
            @Override // kotlin.jvm.functions.Function1
            public final FunctionDescriptor invoke(FunctionDescriptor it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return (FunctionDescriptor) DescriptorUtils.getParentOfType(it, FunctionDescriptor.class);
            }
        }), new Function1<FunctionDescriptor, Boolean>() { // from class: org.jetbrains.kotlin.resolve.bindingContextUtil.BindingContextUtilsKt.getTargetFunctionDescriptor.3
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(FunctionDescriptor it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return Boolean.valueOf(it instanceof AnonymousFunctionDescriptor);
            }
        }));
    }

    public static final KtCallableDeclaration getTargetFunction(KtReturnExpression ktReturnExpression, BindingContext context) {
        Intrinsics.checkNotNullParameter(ktReturnExpression, "<this>");
        Intrinsics.checkNotNullParameter(context, "context");
        FunctionDescriptor targetFunctionDescriptor = getTargetFunctionDescriptor(ktReturnExpression, context);
        if (targetFunctionDescriptor == null) {
            return null;
        }
        PsiElement psiElementDescriptorToDeclaration = DescriptorToSourceUtils.descriptorToDeclaration(targetFunctionDescriptor);
        if (psiElementDescriptorToDeclaration instanceof KtCallableDeclaration) {
            return (KtCallableDeclaration) psiElementDescriptorToDeclaration;
        }
        return null;
    }

    public static final boolean isUsedAsExpression(KtElement ktElement, BindingContext context) {
        Intrinsics.checkNotNullParameter(ktElement, "<this>");
        Intrinsics.checkNotNullParameter(context, "context");
        Boolean bool = (Boolean) context.get(BindingContext.USED_AS_EXPRESSION, ktElement);
        if (bool == null) {
            return false;
        }
        return bool.booleanValue();
    }

    public static final void recordUsedAsExpression(KtElement ktElement, BindingTrace trace, boolean z) {
        Intrinsics.checkNotNullParameter(ktElement, "<this>");
        Intrinsics.checkNotNullParameter(trace, "trace");
        BindingContext context = trace.getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getBindingContext(...)");
        if (isUsedAsExpression(ktElement, context)) {
            return;
        }
        trace.record(BindingContext.USED_AS_EXPRESSION, ktElement, Boolean.valueOf(z));
    }

    public static final boolean isUsedAsResultOfLambda(KtExpression ktExpression, BindingContext context) {
        Intrinsics.checkNotNullParameter(ktExpression, "<this>");
        Intrinsics.checkNotNullParameter(context, "context");
        Object obj = context.get(BindingContext.USED_AS_RESULT_OF_LAMBDA, ktExpression);
        Intrinsics.checkNotNull(obj);
        return ((Boolean) obj).booleanValue();
    }

    public static final boolean isUsedAsStatement(KtExpression ktExpression, BindingContext context) {
        Intrinsics.checkNotNullParameter(ktExpression, "<this>");
        Intrinsics.checkNotNullParameter(context, "context");
        return !isUsedAsExpression(ktExpression, context);
    }

    public static final <C extends ResolutionContext<C>> void recordDataFlowInfo(ResolutionContext<C> resolutionContext, KtExpression ktExpression) {
        Intrinsics.checkNotNullParameter(resolutionContext, "<this>");
        if (ktExpression == null) {
            return;
        }
        KotlinTypeInfo kotlinTypeInfo = (KotlinTypeInfo) resolutionContext.trace.get(BindingContext.EXPRESSION_TYPE_INFO, ktExpression);
        if (kotlinTypeInfo != null) {
            BindingTrace bindingTrace = resolutionContext.trace;
            WritableSlice<KtExpression, KotlinTypeInfo> writableSlice = BindingContext.EXPRESSION_TYPE_INFO;
            DataFlowInfo dataFlowInfo = resolutionContext.dataFlowInfo;
            Intrinsics.checkNotNullExpressionValue(dataFlowInfo, "dataFlowInfo");
            bindingTrace.record(writableSlice, ktExpression, kotlinTypeInfo.replaceDataFlowInfo(dataFlowInfo));
            return;
        }
        if (Intrinsics.areEqual(resolutionContext.dataFlowInfo, DataFlowInfo.INSTANCE.getEMPTY())) {
            return;
        }
        BindingTrace bindingTrace2 = resolutionContext.trace;
        WritableSlice<KtExpression, KotlinTypeInfo> writableSlice2 = BindingContext.EXPRESSION_TYPE_INFO;
        DataFlowInfo dataFlowInfo2 = resolutionContext.dataFlowInfo;
        Intrinsics.checkNotNullExpressionValue(dataFlowInfo2, "dataFlowInfo");
        bindingTrace2.record(writableSlice2, ktExpression, TypeInfoFactoryKt.noTypeInfo(dataFlowInfo2));
    }

    public static final void recordScope(BindingTrace bindingTrace, LexicalScope scope, KtElement ktElement) {
        Intrinsics.checkNotNullParameter(bindingTrace, "<this>");
        Intrinsics.checkNotNullParameter(scope, "scope");
        if (ktElement != null) {
            WritableSlice<KtElement, LexicalScope> writableSlice = BindingContext.LEXICAL_SCOPE;
            HierarchicalScope hierarchicalScopeTakeSnapshot = ScopeUtilsKt.takeSnapshot(scope);
            Intrinsics.checkNotNull(hierarchicalScopeTakeSnapshot, "null cannot be cast to non-null type org.jetbrains.kotlin.resolve.scopes.LexicalScope");
            bindingTrace.record(writableSlice, ktElement, (LexicalScope) hierarchicalScopeTakeSnapshot);
        }
    }

    public static final DataFlowInfo getDataFlowInfoAfter(BindingContext bindingContext, PsiElement position) {
        Intrinsics.checkNotNullParameter(bindingContext, "<this>");
        Intrinsics.checkNotNullParameter(position, "position");
        for (PsiElement psiElement : PsiUtilsKt.getParentsWithSelf(position)) {
            KtExpression ktExpression = psiElement instanceof KtExpression ? (KtExpression) psiElement : null;
            if (ktExpression != null) {
                PsiElement parent = ktExpression.getMyParent();
                KotlinTypeInfo kotlinTypeInfo = ((parent instanceof KtQualifiedExpression) && Intrinsics.areEqual(ktExpression, ((KtQualifiedExpression) parent).getSelectorExpression())) ? null : (KotlinTypeInfo) bindingContext.get(BindingContext.EXPRESSION_TYPE_INFO, ktExpression);
                if (kotlinTypeInfo != null) {
                    return kotlinTypeInfo.getDataFlowInfo();
                }
            }
        }
        return DataFlowInfo.INSTANCE.getEMPTY();
    }

    public static final DataFlowInfo getDataFlowInfoBefore(BindingContext bindingContext, PsiElement position) {
        DataFlowInfo dataFlowInfo;
        Intrinsics.checkNotNullParameter(bindingContext, "<this>");
        Intrinsics.checkNotNullParameter(position, "position");
        for (PsiElement psiElement : PsiUtilsKt.getParentsWithSelf(position)) {
            KtExpression ktExpression = psiElement instanceof KtExpression ? (KtExpression) psiElement : null;
            if (ktExpression != null && (dataFlowInfo = (DataFlowInfo) bindingContext.get(BindingContext.DATA_FLOW_INFO_BEFORE, ktExpression)) != null) {
                return dataFlowInfo;
            }
        }
        return DataFlowInfo.INSTANCE.getEMPTY();
    }

    public static final Collection<DeclarationDescriptor> getReferenceTargets(KtExpression ktExpression, BindingContext context) {
        List listListOf;
        Intrinsics.checkNotNullParameter(ktExpression, "<this>");
        Intrinsics.checkNotNullParameter(context, "context");
        DeclarationDescriptor declarationDescriptor = ktExpression instanceof KtReferenceExpression ? (DeclarationDescriptor) context.get(BindingContext.REFERENCE_TARGET, ktExpression) : null;
        if (declarationDescriptor != null && (listListOf = CollectionsKt.listOf(declarationDescriptor)) != null) {
            return listListOf;
        }
        Collection<DeclarationDescriptor> collection = (Collection) context.get(BindingContext.AMBIGUOUS_REFERENCE_TARGET, ktExpression);
        return collection == null ? CollectionsKt.emptyList() : collection;
    }

    public static final KotlinType getAbbreviatedTypeOrType(KtTypeReference ktTypeReference, BindingContext context) {
        Intrinsics.checkNotNullParameter(ktTypeReference, "<this>");
        Intrinsics.checkNotNullParameter(context, "context");
        KotlinType kotlinType = (KotlinType) context.get(BindingContext.ABBREVIATED_TYPE, ktTypeReference);
        return kotlinType == null ? (KotlinType) context.get(BindingContext.TYPE, ktTypeReference) : kotlinType;
    }

    public static final KotlinType getAbbreviatedTypeOrType(KtTypeElement ktTypeElement, BindingContext context) {
        Intrinsics.checkNotNullParameter(ktTypeElement, "<this>");
        Intrinsics.checkNotNullParameter(context, "context");
        PsiElement parent = ktTypeElement.getMyParent();
        if (parent instanceof KtTypeReference) {
            return getAbbreviatedTypeOrType((KtTypeReference) parent, context);
        }
        if (parent instanceof KtNullableType) {
            KotlinType abbreviatedTypeOrType = getAbbreviatedTypeOrType((KtTypeElement) parent, context);
            if (ktTypeElement instanceof KtNullableType) {
                return abbreviatedTypeOrType;
            }
            if (abbreviatedTypeOrType != null) {
                return TypeUtilsKt.makeNotNullable(abbreviatedTypeOrType);
            }
        }
        return null;
    }

    public static final <T extends PsiElement> T getParentOfTypeCodeFragmentAware(KtElement ktElement, Class<? extends T>... parentClasses) {
        PsiElement context;
        Intrinsics.checkNotNullParameter(ktElement, "<this>");
        Intrinsics.checkNotNullParameter(parentClasses, "parentClasses");
        T t = (T) PsiTreeUtil.getParentOfType(ktElement, (Class[]) Arrays.copyOf(parentClasses, parentClasses.length));
        if (t != null) {
            return t;
        }
        PsiFile containingFile = ktElement.getContainingFile();
        if (!(containingFile instanceof KtCodeFragment) || (context = ((KtCodeFragment) containingFile).getContext()) == null) {
            return null;
        }
        return (T) PsiTreeUtil.getParentOfType(context, (Class[]) Arrays.copyOf(parentClasses, parentClasses.length));
    }

    public static final DeclarationDescriptor getEnclosingDescriptor(BindingContext context, KtElement element) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(element, "element");
        KtNamedDeclaration ktNamedDeclaration = (KtNamedDeclaration) getParentOfTypeCodeFragmentAware(element, KtNamedDeclaration.class);
        if (ktNamedDeclaration == null) {
            throw new KotlinExceptionWithAttachments("No parent KtNamedDeclaration for of type " + element.getClass()).withPsiAttachment("element.kt", element);
        }
        if (ktNamedDeclaration instanceof KtFunctionLiteral) {
            return getEnclosingDescriptor(context, ktNamedDeclaration);
        }
        DeclarationDescriptor declarationDescriptor = (DeclarationDescriptor) context.get(BindingContext.DECLARATION_TO_DESCRIPTOR, ktNamedDeclaration);
        if (declarationDescriptor != null) {
            return declarationDescriptor;
        }
        throw new KotlinExceptionWithAttachments("No descriptor for named declaration of type " + ktNamedDeclaration.getClass()).withPsiAttachment("declaration.kt", ktNamedDeclaration);
    }

    public static final FunctionDescriptor getEnclosingFunctionDescriptor(BindingContext context, KtElement element, boolean z) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(element, "element");
        while (true) {
            KtTypeParameterListOwner ktTypeParameterListOwner = (KtTypeParameterListOwner) getParentOfTypeCodeFragmentAware(element, KtFunction.class, KtClassOrObject.class);
            DeclarationDescriptor declarationDescriptor = (DeclarationDescriptor) context.get(BindingContext.DECLARATION_TO_DESCRIPTOR, ktTypeParameterListOwner);
            if (ktTypeParameterListOwner instanceof KtFunction) {
                if (!(declarationDescriptor instanceof FunctionDescriptor)) {
                    return null;
                }
                if (z) {
                    KtFunctionLiteral ktFunctionLiteral = ktTypeParameterListOwner instanceof KtFunctionLiteral ? (KtFunctionLiteral) ktTypeParameterListOwner : null;
                    PsiElement parent = ktFunctionLiteral != null ? ktFunctionLiteral.getMyParent() : null;
                    KtTypeParameterListOwner ktTypeParameterListOwner2 = parent instanceof KtExpression ? (KtExpression) parent : null;
                    if (ktTypeParameterListOwner2 == null) {
                        ktTypeParameterListOwner2 = ktTypeParameterListOwner;
                    }
                    if (isInlineableFunctionLiteral(ktTypeParameterListOwner2, context)) {
                        element = ktTypeParameterListOwner;
                    }
                }
                return (FunctionDescriptor) declarationDescriptor;
            }
            if (declarationDescriptor instanceof ClassDescriptor) {
                return ((ClassDescriptor) declarationDescriptor).mo6347getUnsubstitutedPrimaryConstructor();
            }
            return null;
        }
    }

    public static final boolean isInlineableFunctionLiteral(KtExpression expression, BindingContext context) {
        ResolvedCall<? extends CallableDescriptor> resolvedCall;
        Object next;
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(context, "context");
        if (!(expression instanceof KtLambdaExpression) && (!(expression instanceof KtNamedFunction) || ((KtNamedFunction) expression).getMyName() != null)) {
            return false;
        }
        KtExpression lightOwner = expression;
        while (true) {
            PsiElement lightOwner2 = lightOwner.getMyParent();
            if (!Intrinsics.areEqual(KtPsiUtil.deparenthesizeOnce(lightOwner2 instanceof KtExpression ? (KtExpression) lightOwner2 : null), lightOwner)) {
                break;
            }
            lightOwner = lightOwner.getMyParent();
            Intrinsics.checkNotNullExpressionValue(lightOwner, "getParent(...)");
        }
        PsiElement lightOwner3 = lightOwner.getMyParent();
        KtValueArgument ktValueArgument = lightOwner3 instanceof KtValueArgument ? (KtValueArgument) lightOwner3 : null;
        if (ktValueArgument == null) {
            return false;
        }
        PsiElement parent = ktValueArgument.getMyParent();
        KtValueArgumentList ktValueArgumentList = parent instanceof KtValueArgumentList ? (KtValueArgumentList) parent : null;
        PsiElement lightOwner4 = (ktValueArgumentList != null ? ktValueArgumentList : ktValueArgument).getMyParent();
        KtCallExpression ktCallExpression = lightOwner4 instanceof KtCallExpression ? (KtCallExpression) lightOwner4 : null;
        if (ktCallExpression == null || (resolvedCall = CallUtilKt.getResolvedCall(ktCallExpression, context)) == null) {
            return false;
        }
        CallableDescriptor resultingDescriptor = resolvedCall.getResultingDescriptor();
        FunctionDescriptor functionDescriptor = resultingDescriptor instanceof FunctionDescriptor ? (FunctionDescriptor) resultingDescriptor : null;
        if (functionDescriptor == null || !functionDescriptor.isInline()) {
            return false;
        }
        Iterator<T> it = resolvedCall.getValueArguments().entrySet().iterator();
        loop1: while (true) {
            if (!it.hasNext()) {
                next = null;
                break;
            }
            next = it.next();
            List<ValueArgument> arguments = ((ResolvedValueArgument) ((Map.Entry) next).getValue()).getArguments();
            Intrinsics.checkNotNullExpressionValue(arguments, "getArguments(...)");
            List<ValueArgument> list = arguments;
            if (!(list instanceof Collection) || !list.isEmpty()) {
                Iterator<T> it2 = list.iterator();
                while (it2.hasNext()) {
                    if (Intrinsics.areEqual(((ValueArgument) it2.next()).getCallElement(), ktValueArgument)) {
                        break loop1;
                    }
                }
            }
        }
        Map.Entry entry = (Map.Entry) next;
        ValueParameterDescriptor valueParameterDescriptor = entry != null ? (ValueParameterDescriptor) entry.getKey() : null;
        return (valueParameterDescriptor == null || valueParameterDescriptor.getIsNoinline() || valueParameterDescriptor.getIsCrossinline()) ? false : true;
    }
}

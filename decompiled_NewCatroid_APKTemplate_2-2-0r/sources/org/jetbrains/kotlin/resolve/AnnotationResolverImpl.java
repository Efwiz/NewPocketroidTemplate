package org.jetbrains.kotlin.resolve;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Pair;
import kotlin.collections.CollectionsKt;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.ConstructorDescriptor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.AnnotationDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.AnnotationWithTarget;
import org.jetbrains.kotlin.descriptors.annotations.Annotations;
import org.jetbrains.kotlin.descriptors.annotations.TargetedAnnotations;
import org.jetbrains.kotlin.diagnostics.Errors;
import org.jetbrains.kotlin.javax.inject.Inject;
import org.jetbrains.kotlin.psi.KtAnnotationEntry;
import org.jetbrains.kotlin.psi.KtAnnotationUseSiteTarget;
import org.jetbrains.kotlin.psi.KtModifierList;
import org.jetbrains.kotlin.psi.KtTypeParameter;
import org.jetbrains.kotlin.psi.KtTypeReference;
import org.jetbrains.kotlin.resolve.calls.CallResolver;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedValueArgument;
import org.jetbrains.kotlin.resolve.calls.results.OverloadResolutionResults;
import org.jetbrains.kotlin.resolve.calls.smartcasts.DataFlowInfoFactory;
import org.jetbrains.kotlin.resolve.calls.util.CallMaker;
import org.jetbrains.kotlin.resolve.constants.ConstantValue;
import org.jetbrains.kotlin.resolve.constants.evaluate.ConstantExpressionEvaluator;
import org.jetbrains.kotlin.resolve.lazy.ForceResolveUtil;
import org.jetbrains.kotlin.resolve.lazy.descriptors.LazyAnnotationDescriptor;
import org.jetbrains.kotlin.resolve.lazy.descriptors.LazyAnnotationsContextImpl;
import org.jetbrains.kotlin.resolve.scopes.LexicalScope;
import org.jetbrains.kotlin.storage.StorageManager;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.TypeUtils;
import org.jetbrains.kotlin.types.error.ErrorType;
import org.jetbrains.kotlin.types.error.ErrorTypeKind;
import org.jetbrains.kotlin.types.error.ErrorUtils;

/* JADX INFO: loaded from: classes6.dex */
public class AnnotationResolverImpl extends AnnotationResolver {
    private final CallResolver callResolver;
    private final ConstantExpressionEvaluator constantExpressionEvaluator;
    private final StorageManager storageManager;
    private TypeResolver typeResolver;

    /* JADX WARN: Code duplicated, block: B:7:0x000d  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        if (i != 7 && i != 20) {
            switch (i) {
                case 11:
                case 12:
                case 13:
                    str = "@NotNull method %s.%s must not return null";
                    break;
                default:
                    str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                    break;
            }
        } else {
            str = "@NotNull method %s.%s must not return null";
        }
        if (i != 7 && i != 20) {
            switch (i) {
                case 11:
                case 12:
                case 13:
                    i2 = 2;
                    break;
                default:
                    i2 = 3;
                    break;
            }
        } else {
            i2 = 2;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 1:
                objArr[0] = "constantExpressionEvaluator";
                break;
            case 2:
                objArr[0] = "storageManager";
                break;
            case 3:
                objArr[0] = "typeResolver";
                break;
            case 4:
            case 8:
            case 18:
                objArr[0] = "scope";
                break;
            case 5:
                objArr[0] = "annotationEntryElements";
                break;
            case 6:
            case 10:
            case 15:
            case 19:
            case 22:
            case 24:
                objArr[0] = "trace";
                break;
            case 7:
            case 11:
            case 12:
            case 13:
            case 20:
                objArr[0] = "org/jetbrains/kotlin/resolve/AnnotationResolverImpl";
                break;
            case 9:
            case 14:
                objArr[0] = "entryElement";
                break;
            case 16:
                objArr[0] = "results";
                break;
            case 17:
                objArr[0] = "annotationEntry";
                break;
            case 21:
                objArr[0] = "ktTypeParameter";
                break;
            case 23:
                objArr[0] = "languageVersionSettings";
                break;
            case 25:
                objArr[0] = "valueParameter";
                break;
            case 26:
                objArr[0] = "resolvedArgument";
                break;
            default:
                objArr[0] = "callResolver";
                break;
        }
        if (i == 7) {
            objArr[1] = "resolveAnnotationEntries";
        } else if (i != 20) {
            switch (i) {
                case 11:
                case 12:
                case 13:
                    objArr[1] = "resolveAnnotationType";
                    break;
                default:
                    objArr[1] = "org/jetbrains/kotlin/resolve/AnnotationResolverImpl";
                    break;
            }
        } else {
            objArr[1] = "resolveAnnotationCall";
        }
        switch (i) {
            case 3:
                objArr[2] = "setTypeResolver";
                break;
            case 4:
            case 5:
            case 6:
                objArr[2] = "resolveAnnotationEntries";
                break;
            case 7:
            case 11:
            case 12:
            case 13:
            case 20:
                break;
            case 8:
            case 9:
            case 10:
                objArr[2] = "resolveAnnotationType";
                break;
            case 14:
            case 15:
            case 16:
                objArr[2] = "checkAnnotationType";
                break;
            case 17:
            case 18:
            case 19:
                objArr[2] = "resolveAnnotationCall";
                break;
            case 21:
            case 22:
            case 23:
                objArr[2] = "reportUnsupportedAnnotationForTypeParameter";
                break;
            case 24:
            case 25:
            case 26:
                objArr[2] = "getAnnotationArgumentValue";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 7 && i != 20) {
            switch (i) {
                case 11:
                case 12:
                case 13:
                    break;
                default:
                    throw new IllegalArgumentException(str2);
            }
        }
        throw new IllegalStateException(str2);
    }

    public AnnotationResolverImpl(CallResolver callResolver, ConstantExpressionEvaluator constantExpressionEvaluator, StorageManager storageManager) {
        if (callResolver == null) {
            $$$reportNull$$$0(0);
        }
        if (constantExpressionEvaluator == null) {
            $$$reportNull$$$0(1);
        }
        if (storageManager == null) {
            $$$reportNull$$$0(2);
        }
        this.callResolver = callResolver;
        this.constantExpressionEvaluator = constantExpressionEvaluator;
        this.storageManager = storageManager;
    }

    @Inject
    public void setTypeResolver(TypeResolver typeResolver) {
        if (typeResolver == null) {
            $$$reportNull$$$0(3);
        }
        this.typeResolver = typeResolver;
    }

    @Override // org.jetbrains.kotlin.resolve.AnnotationResolver
    public Annotations resolveAnnotationEntries(LexicalScope lexicalScope, List<KtAnnotationEntry> list, BindingTrace bindingTrace, boolean z) {
        if (lexicalScope == null) {
            $$$reportNull$$$0(4);
        }
        if (list == null) {
            $$$reportNull$$$0(5);
        }
        if (bindingTrace == null) {
            $$$reportNull$$$0(6);
        }
        if (list.isEmpty()) {
            Annotations empty = Annotations.INSTANCE.getEMPTY();
            if (empty == null) {
                $$$reportNull$$$0(7);
            }
            return empty;
        }
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        for (KtAnnotationEntry ktAnnotationEntry : list) {
            AnnotationDescriptor lazyAnnotationDescriptor = (AnnotationDescriptor) bindingTrace.get(BindingContext.ANNOTATION, ktAnnotationEntry);
            if (lazyAnnotationDescriptor == null) {
                lazyAnnotationDescriptor = new LazyAnnotationDescriptor(new LazyAnnotationsContextImpl(this, this.storageManager, bindingTrace, lexicalScope), ktAnnotationEntry);
            }
            if (z) {
                ForceResolveUtil.forceResolveAllContents(lazyAnnotationDescriptor);
            }
            KtAnnotationUseSiteTarget useSiteTarget = ktAnnotationEntry.getUseSiteTarget();
            if (useSiteTarget != null) {
                arrayList2.add(new AnnotationWithTarget(lazyAnnotationDescriptor, useSiteTarget.getAnnotationUseSiteTarget()));
            } else {
                arrayList.add(lazyAnnotationDescriptor);
            }
        }
        return new TargetedAnnotations(CollectionsKt.toList(arrayList), CollectionsKt.toList(arrayList2));
    }

    @Override // org.jetbrains.kotlin.resolve.AnnotationResolver
    public KotlinType resolveAnnotationType(LexicalScope lexicalScope, KtAnnotationEntry ktAnnotationEntry, BindingTrace bindingTrace) {
        if (lexicalScope == null) {
            $$$reportNull$$$0(8);
        }
        if (ktAnnotationEntry == null) {
            $$$reportNull$$$0(9);
        }
        if (bindingTrace == null) {
            $$$reportNull$$$0(10);
        }
        KtTypeReference typeReference = ktAnnotationEntry.getTypeReference();
        if (typeReference == null) {
            ErrorType errorTypeCreateErrorType = ErrorUtils.createErrorType(ErrorTypeKind.UNRESOLVED_TYPE, ktAnnotationEntry.getText());
            if (errorTypeCreateErrorType == null) {
                $$$reportNull$$$0(11);
            }
            return errorTypeCreateErrorType;
        }
        KotlinType kotlinTypeResolveType = this.typeResolver.resolveType(lexicalScope, typeReference, bindingTrace, true);
        if (kotlinTypeResolveType.getConstructor().getThis$0() instanceof ClassDescriptor) {
            if (kotlinTypeResolveType == null) {
                $$$reportNull$$$0(13);
            }
            return kotlinTypeResolveType;
        }
        ErrorType errorTypeCreateErrorType2 = ErrorUtils.createErrorType(ErrorTypeKind.NOT_ANNOTATION_TYPE_IN_ANNOTATION_CONTEXT, kotlinTypeResolveType.toString());
        if (errorTypeCreateErrorType2 == null) {
            $$$reportNull$$$0(12);
        }
        return errorTypeCreateErrorType2;
    }

    public static void checkAnnotationType(KtAnnotationEntry ktAnnotationEntry, BindingTrace bindingTrace, OverloadResolutionResults<FunctionDescriptor> overloadResolutionResults) {
        if (ktAnnotationEntry == null) {
            $$$reportNull$$$0(14);
        }
        if (bindingTrace == null) {
            $$$reportNull$$$0(15);
        }
        if (overloadResolutionResults == null) {
            $$$reportNull$$$0(16);
        }
        if (overloadResolutionResults.isSingleResult()) {
            FunctionDescriptor functionDescriptor = (FunctionDescriptor) overloadResolutionResults.mo7437getResultingDescriptor();
            if (ErrorUtils.isError(functionDescriptor)) {
                return;
            }
            if (functionDescriptor instanceof ConstructorDescriptor) {
                ClassDescriptor constructedClass = ((ConstructorDescriptor) functionDescriptor).getConstructedClass();
                if (constructedClass.getClassKind() != ClassKind.ANNOTATION_CLASS) {
                    bindingTrace.report(Errors.NOT_AN_ANNOTATION_CLASS.on(ktAnnotationEntry, constructedClass));
                    return;
                }
                return;
            }
            bindingTrace.report(Errors.NOT_AN_ANNOTATION_CLASS.on(ktAnnotationEntry, functionDescriptor));
        }
    }

    @Override // org.jetbrains.kotlin.resolve.AnnotationResolver
    public OverloadResolutionResults<FunctionDescriptor> resolveAnnotationCall(KtAnnotationEntry ktAnnotationEntry, LexicalScope lexicalScope, BindingTrace bindingTrace) {
        if (ktAnnotationEntry == null) {
            $$$reportNull$$$0(17);
        }
        if (lexicalScope == null) {
            $$$reportNull$$$0(18);
        }
        if (bindingTrace == null) {
            $$$reportNull$$$0(19);
        }
        if (PsiTreeUtil.getParentOfType(ktAnnotationEntry, KtAnnotationEntry.class) != null) {
            bindingTrace.report(Errors.ANNOTATION_USED_AS_ANNOTATION_ARGUMENT.on(ktAnnotationEntry));
        }
        OverloadResolutionResults<FunctionDescriptor> overloadResolutionResultsResolveFunctionCall = this.callResolver.resolveFunctionCall(bindingTrace, lexicalScope, CallMaker.makeCall(null, null, ktAnnotationEntry), TypeUtils.NO_EXPECTED_TYPE, DataFlowInfoFactory.EMPTY, true, null);
        if (overloadResolutionResultsResolveFunctionCall == null) {
            $$$reportNull$$$0(20);
        }
        return overloadResolutionResultsResolveFunctionCall;
    }

    public static void reportUnsupportedAnnotationForTypeParameter(KtTypeParameter ktTypeParameter, BindingTrace bindingTrace, LanguageVersionSettings languageVersionSettings) {
        if (ktTypeParameter == null) {
            $$$reportNull$$$0(21);
        }
        if (bindingTrace == null) {
            $$$reportNull$$$0(22);
        }
        if (languageVersionSettings == null) {
            $$$reportNull$$$0(23);
        }
        KtModifierList modifierList = ktTypeParameter.getModifierList();
        if (modifierList == null) {
            return;
        }
        Iterator<KtAnnotationEntry> it = modifierList.getAnnotationEntries().iterator();
        while (it.hasNext()) {
            bindingTrace.report(Errors.UNSUPPORTED_FEATURE.on(it.next(), new Pair<>(LanguageFeature.ClassTypeParameterAnnotations, languageVersionSettings)));
        }
    }

    @Override // org.jetbrains.kotlin.resolve.AnnotationResolver
    public ConstantValue<?> getAnnotationArgumentValue(BindingTrace bindingTrace, ValueParameterDescriptor valueParameterDescriptor, ResolvedValueArgument resolvedValueArgument) {
        if (bindingTrace == null) {
            $$$reportNull$$$0(24);
        }
        if (valueParameterDescriptor == null) {
            $$$reportNull$$$0(25);
        }
        if (resolvedValueArgument == null) {
            $$$reportNull$$$0(26);
        }
        return this.constantExpressionEvaluator.getAnnotationArgumentValue(bindingTrace, valueParameterDescriptor, resolvedValueArgument);
    }
}

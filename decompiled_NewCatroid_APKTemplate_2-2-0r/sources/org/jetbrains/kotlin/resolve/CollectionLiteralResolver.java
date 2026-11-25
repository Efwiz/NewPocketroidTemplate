package org.jetbrains.kotlin.resolve;

import java.util.Collection;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.builtins.StandardNames;
import org.jetbrains.kotlin.builtins.UnsignedTypes;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.descriptors.SimpleFunctionDescriptor;
import org.jetbrains.kotlin.diagnostics.DiagnosticFactory1;
import org.jetbrains.kotlin.diagnostics.Errors;
import org.jetbrains.kotlin.incremental.KotlinLookupLocation;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.psi.Call;
import org.jetbrains.kotlin.psi.KtAnnotationEntry;
import org.jetbrains.kotlin.psi.KtClass;
import org.jetbrains.kotlin.psi.KtCollectionLiteralExpression;
import org.jetbrains.kotlin.psi.KtElementImplStub;
import org.jetbrains.kotlin.psi.KtObjectDeclaration;
import org.jetbrains.kotlin.resolve.calls.CallResolver;
import org.jetbrains.kotlin.resolve.calls.results.OverloadResolutionResults;
import org.jetbrains.kotlin.resolve.calls.util.CallMaker;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.TypeUtils;
import org.jetbrains.kotlin.types.expressions.ExpressionTypingContext;
import org.jetbrains.kotlin.types.expressions.KotlinTypeInfo;
import org.jetbrains.kotlin.types.expressions.typeInfoFactory.TypeInfoFactoryKt;

/* JADX INFO: compiled from: CollectionLiteralResolver.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001:\u0001'B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\u0010\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012H\u0002J\u0010\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0016H\u0002J\u001e\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00190\u00182\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u001a\u001a\u00020\u0014H\u0002J,\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u001e2\u0012\u0010\u001f\u001a\u000e\u0012\u0004\u0012\u00020!\u0012\u0004\u0012\u00020\"0 2\u0006\u0010#\u001a\u00020\u0012H\u0002J\u0016\u0010$\u001a\u00020%2\u0006\u0010#\u001a\u00020\u00122\u0006\u0010\u001d\u001a\u00020\u001eJ\u0018\u0010&\u001a\u00020%2\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u001d\u001a\u00020\u001eH\u0002R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e¨\u0006("}, d2 = {"Lorg/jetbrains/kotlin/resolve/CollectionLiteralResolver;", "", "module", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "callResolver", "Lorg/jetbrains/kotlin/resolve/calls/CallResolver;", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "(Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;Lorg/jetbrains/kotlin/resolve/calls/CallResolver;Lorg/jetbrains/kotlin/config/LanguageVersionSettings;)V", "getCallResolver", "()Lorg/jetbrains/kotlin/resolve/calls/CallResolver;", "getLanguageVersionSettings", "()Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "getModule", "()Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "computeKindOfContainer", "Lorg/jetbrains/kotlin/resolve/CollectionLiteralResolver$ContainerKind;", "expression", "Lorg/jetbrains/kotlin/psi/KtCollectionLiteralExpression;", "getArrayFunctionCallName", "Lorg/jetbrains/kotlin/name/Name;", "expectedType", "Lorg/jetbrains/kotlin/types/KotlinType;", "getFunctionDescriptorForCollectionLiteral", "", "Lorg/jetbrains/kotlin/descriptors/SimpleFunctionDescriptor;", "callName", "reportUnsupportedLiteral", "", "context", "Lorg/jetbrains/kotlin/types/expressions/ExpressionTypingContext;", "diagnosticFactory", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticFactory1;", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "", "collectionLiteralExpression", "resolveCollectionLiteral", "Lorg/jetbrains/kotlin/types/expressions/KotlinTypeInfo;", "resolveCollectionLiteralSpecialMethod", "ContainerKind", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CollectionLiteralResolver {
    private final CallResolver callResolver;
    private final LanguageVersionSettings languageVersionSettings;
    private final ModuleDescriptor module;

    /* JADX INFO: compiled from: CollectionLiteralResolver.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0005\b\u0082\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005¨\u0006\u0006"}, d2 = {"Lorg/jetbrains/kotlin/resolve/CollectionLiteralResolver$ContainerKind;", "", "(Ljava/lang/String;I)V", "AnnotationOrAnnotationClass", "CompanionOfAnnotation", "Other", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private enum ContainerKind {
        AnnotationOrAnnotationClass,
        CompanionOfAnnotation,
        Other;

        private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());

        public static EnumEntries<ContainerKind> getEntries() {
            return $ENTRIES;
        }
    }

    /* JADX INFO: compiled from: CollectionLiteralResolver.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[ContainerKind.values().length];
            try {
                iArr[ContainerKind.AnnotationOrAnnotationClass.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[ContainerKind.CompanionOfAnnotation.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[ContainerKind.Other.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public CollectionLiteralResolver(ModuleDescriptor module, CallResolver callResolver, LanguageVersionSettings languageVersionSettings) {
        Intrinsics.checkNotNullParameter(module, "module");
        Intrinsics.checkNotNullParameter(callResolver, "callResolver");
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        this.module = module;
        this.callResolver = callResolver;
        this.languageVersionSettings = languageVersionSettings;
    }

    public final ModuleDescriptor getModule() {
        return this.module;
    }

    public final CallResolver getCallResolver() {
        return this.callResolver;
    }

    public final LanguageVersionSettings getLanguageVersionSettings() {
        return this.languageVersionSettings;
    }

    public final KotlinTypeInfo resolveCollectionLiteral(KtCollectionLiteralExpression collectionLiteralExpression, ExpressionTypingContext context) {
        DiagnosticFactory1<PsiElement, String> diagnosticFactory1;
        Intrinsics.checkNotNullParameter(collectionLiteralExpression, "collectionLiteralExpression");
        Intrinsics.checkNotNullParameter(context, "context");
        int i = WhenMappings.$EnumSwitchMapping$0[computeKindOfContainer(collectionLiteralExpression).ordinal()];
        if (i == 2) {
            boolean zSupportsFeature = context.languageVersionSettings.supportsFeature(LanguageFeature.ProhibitArrayLiteralsInCompanionOfAnnotation);
            if (zSupportsFeature) {
                diagnosticFactory1 = Errors.UNSUPPORTED;
            } else if (!zSupportsFeature) {
                diagnosticFactory1 = Errors.UNSUPPORTED_WARNING;
            } else {
                throw new NoWhenBranchMatchedException();
            }
            Intrinsics.checkNotNull(diagnosticFactory1);
            reportUnsupportedLiteral(context, diagnosticFactory1, collectionLiteralExpression);
        } else if (i == 3) {
            DiagnosticFactory1<PsiElement, String> UNSUPPORTED = Errors.UNSUPPORTED;
            Intrinsics.checkNotNullExpressionValue(UNSUPPORTED, "UNSUPPORTED");
            reportUnsupportedLiteral(context, UNSUPPORTED, collectionLiteralExpression);
        }
        return resolveCollectionLiteralSpecialMethod(collectionLiteralExpression, context);
    }

    private final void reportUnsupportedLiteral(ExpressionTypingContext context, DiagnosticFactory1<PsiElement, String> diagnosticFactory, KtCollectionLiteralExpression collectionLiteralExpression) {
        context.trace.report(diagnosticFactory.on(collectionLiteralExpression, "Collection literals outside of annotations"));
    }

    private final KotlinTypeInfo resolveCollectionLiteralSpecialMethod(KtCollectionLiteralExpression expression, ExpressionTypingContext context) {
        Call callMakeCallForCollectionLiteral = CallMaker.makeCallForCollectionLiteral(expression);
        KotlinType expectedType = context.expectedType;
        Intrinsics.checkNotNullExpressionValue(expectedType, "expectedType");
        Name arrayFunctionCallName = getArrayFunctionCallName(expectedType);
        Collection<SimpleFunctionDescriptor> functionDescriptorForCollectionLiteral = getFunctionDescriptorForCollectionLiteral(expression, arrayFunctionCallName);
        if (functionDescriptorForCollectionLiteral.isEmpty()) {
            context.trace.report(Errors.MISSING_STDLIB.on(expression, "Collection literal call '" + arrayFunctionCallName + "()' is unresolved"));
            return TypeInfoFactoryKt.noTypeInfo(context);
        }
        OverloadResolutionResults<FunctionDescriptor> overloadResolutionResultsResolveCollectionLiteralCallWithGivenDescriptor = this.callResolver.resolveCollectionLiteralCallWithGivenDescriptor(context, expression, callMakeCallForCollectionLiteral, functionDescriptorForCollectionLiteral);
        Intrinsics.checkNotNullExpressionValue(overloadResolutionResultsResolveCollectionLiteralCallWithGivenDescriptor, "resolveCollectionLiteral…lWithGivenDescriptor(...)");
        if (!overloadResolutionResultsResolveCollectionLiteralCallWithGivenDescriptor.isSingleResult()) {
            return TypeInfoFactoryKt.noTypeInfo(context);
        }
        context.trace.record(BindingContext.COLLECTION_LITERAL_CALL, expression, overloadResolutionResultsResolveCollectionLiteralCallWithGivenDescriptor.mo7436getResultingCall());
        return TypeInfoFactoryKt.createTypeInfo(((FunctionDescriptor) overloadResolutionResultsResolveCollectionLiteralCallWithGivenDescriptor.mo7437getResultingDescriptor()).getReturnType(), context);
    }

    private final Collection<SimpleFunctionDescriptor> getFunctionDescriptorForCollectionLiteral(KtCollectionLiteralExpression expression, Name callName) {
        return this.module.getPackage(StandardNames.BUILT_INS_PACKAGE_FQ_NAME).getMemberScope().getContributedFunctions(callName, new KotlinLookupLocation(expression));
    }

    private final ContainerKind computeKindOfContainer(KtCollectionLiteralExpression expression) {
        KtClass ktClass;
        KtElementImplStub ktElementImplStub = (KtElementImplStub) PsiTreeUtil.getParentOfType(expression, KtAnnotationEntry.class, KtClass.class, KtObjectDeclaration.class);
        if ((ktElementImplStub instanceof KtObjectDeclaration) && (ktClass = (KtClass) PsiTreeUtil.getParentOfType(ktElementImplStub, KtClass.class)) != null && ktClass.isAnnotation()) {
            return ContainerKind.CompanionOfAnnotation;
        }
        if ((ktElementImplStub instanceof KtAnnotationEntry) || ((ktElementImplStub instanceof KtClass) && ((KtClass) ktElementImplStub).isAnnotation())) {
            return ContainerKind.AnnotationOrAnnotationClass;
        }
        return ContainerKind.Other;
    }

    private final Name getArrayFunctionCallName(KotlinType expectedType) {
        if (TypeUtils.noExpectedType(expectedType) || (!KotlinBuiltIns.isPrimitiveArray(expectedType) && !KotlinBuiltIns.isUnsignedArrayType(expectedType))) {
            return ArrayFqNames.INSTANCE.getARRAY_OF_FUNCTION();
        }
        ClassifierDescriptor this$0 = expectedType.getConstructor().getThis$0();
        if (this$0 == null) {
            return ArrayFqNames.INSTANCE.getARRAY_OF_FUNCTION();
        }
        ClassifierDescriptor classifierDescriptor = this$0;
        Name name2 = ArrayFqNames.INSTANCE.getPRIMITIVE_TYPE_TO_ARRAY().get(KotlinBuiltIns.getPrimitiveArrayType(classifierDescriptor));
        if (name2 != null) {
            return name2;
        }
        Name name3 = UnsignedTypes.INSTANCE.getUnsignedArrayTypeToArrayCall().get(UnsignedTypes.toUnsignedArrayType(classifierDescriptor));
        return name3 == null ? ArrayFqNames.INSTANCE.getARRAY_OF_FUNCTION() : name3;
    }
}

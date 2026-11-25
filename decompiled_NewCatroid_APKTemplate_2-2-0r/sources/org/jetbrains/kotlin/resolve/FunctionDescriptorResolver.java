package org.jetbrains.kotlin.resolve;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function5;
import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlin.jvm.internal.Reflection;
import kotlin.ranges.RangesKt;
import kotlin.reflect.KDeclarationContainer;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.builtins.FunctionTypesKt;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.builtins.StandardNames;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.com.google.common.collect.HashMultimap;
import org.jetbrains.kotlin.com.intellij.openapi.util.ThrowableComputable;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.util.AstLoadingFilter;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.contracts.description.ContractProviderKey;
import org.jetbrains.kotlin.contracts.description.LazyContractProvider;
import org.jetbrains.kotlin.contracts.parsing.ContractParsingServices;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.ConstructorDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.DescriptorVisibility;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.descriptors.PackageFragmentDescriptor;
import org.jetbrains.kotlin.descriptors.ReceiverParameterDescriptor;
import org.jetbrains.kotlin.descriptors.SimpleFunctionDescriptor;
import org.jetbrains.kotlin.descriptors.SourceElement;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.AnnotationDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.AnnotationSplitter;
import org.jetbrains.kotlin.descriptors.annotations.AnnotationUseSiteTarget;
import org.jetbrains.kotlin.descriptors.annotations.Annotations;
import org.jetbrains.kotlin.descriptors.impl.ClassConstructorDescriptorImpl;
import org.jetbrains.kotlin.descriptors.impl.FunctionExpressionDescriptor;
import org.jetbrains.kotlin.descriptors.impl.SimpleFunctionDescriptorImpl;
import org.jetbrains.kotlin.descriptors.impl.TypeParameterDescriptorImpl;
import org.jetbrains.kotlin.descriptors.impl.ValueParameterDescriptorImpl;
import org.jetbrains.kotlin.diagnostics.Errors;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.lexer.KtTokens;
import org.jetbrains.kotlin.load.java.JvmAbi;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.name.SpecialNames;
import org.jetbrains.kotlin.psi.KtContextReceiver;
import org.jetbrains.kotlin.psi.KtFunction;
import org.jetbrains.kotlin.psi.KtFunctionLiteral;
import org.jetbrains.kotlin.psi.KtModifierList;
import org.jetbrains.kotlin.psi.KtNamedFunction;
import org.jetbrains.kotlin.psi.KtParameter;
import org.jetbrains.kotlin.psi.KtPrimaryConstructor;
import org.jetbrains.kotlin.psi.KtPureClassOrObject;
import org.jetbrains.kotlin.psi.KtPureElement;
import org.jetbrains.kotlin.psi.KtSecondaryConstructor;
import org.jetbrains.kotlin.psi.KtTypeReference;
import org.jetbrains.kotlin.psi.psiUtil.PsiUtilsKt;
import org.jetbrains.kotlin.resolve.bindingContextUtil.BindingContextUtilsKt;
import org.jetbrains.kotlin.resolve.calls.DslMarkerUtils;
import org.jetbrains.kotlin.resolve.calls.components.InferenceSession;
import org.jetbrains.kotlin.resolve.calls.smartcasts.DataFlowInfo;
import org.jetbrains.kotlin.resolve.calls.util.FunctionTypeResolveUtilsKt;
import org.jetbrains.kotlin.resolve.lazy.ForceResolveUtil;
import org.jetbrains.kotlin.resolve.scopes.LexicalScope;
import org.jetbrains.kotlin.resolve.scopes.LexicalScopeKind;
import org.jetbrains.kotlin.resolve.scopes.LexicalWritableScope;
import org.jetbrains.kotlin.resolve.scopes.TraceBasedLocalRedeclarationChecker;
import org.jetbrains.kotlin.resolve.source.KotlinSourceElementKt;
import org.jetbrains.kotlin.storage.StorageManager;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.KotlinTypeKt;
import org.jetbrains.kotlin.types.SimpleType;
import org.jetbrains.kotlin.types.TypeUtils;
import org.jetbrains.kotlin.types.UnwrappedType;
import org.jetbrains.kotlin.types.checker.KotlinTypeChecker;
import org.jetbrains.kotlin.types.error.ErrorType;
import org.jetbrains.kotlin.types.error.ErrorTypeKind;
import org.jetbrains.kotlin.types.error.ErrorUtils;
import org.jetbrains.kotlin.types.expressions.ExpressionTypingServices;
import org.jetbrains.kotlin.types.expressions.ExpressionTypingUtils;
import org.jetbrains.kotlin.types.typeUtil.TypeUtilsKt;

/* JADX INFO: compiled from: FunctionDescriptorResolver.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000ú\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u00002\u00020\u0001:\u0001YBU\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\u0006\u0010\f\u001a\u00020\r\u0012\u0006\u0010\u000e\u001a\u00020\u000f\u0012\u0006\u0010\u0010\u001a\u00020\u0011\u0012\u0006\u0010\u0012\u001a\u00020\u0013\u0012\u0006\u0010\u0014\u001a\u00020\u0015¢\u0006\u0002\u0010\u0016JZ\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u001e2\b\u0010\u001f\u001a\u0004\u0018\u00010 2\u0006\u0010!\u001a\u00020\"2\f\u0010#\u001a\b\u0012\u0004\u0012\u00020%0$2\u0006\u0010&\u001a\u00020'2\u0006\u0010\u0012\u001a\u00020\u00132\b\u0010(\u001a\u0004\u0018\u00010)H\u0002J@\u0010*\u001a\b\u0012\u0004\u0012\u00020+0$2\u0006\u0010,\u001a\u00020-2\u0006\u0010.\u001a\u00020/2\u0006\u00100\u001a\u0002012\u0006\u0010&\u001a\u00020'2\u0006\u00102\u001a\u0002032\b\u0010(\u001a\u0004\u0018\u00010)H\u0002J<\u00104\u001a\u0004\u0018\u0001052\u0006\u0010.\u001a\u00020/2\u0006\u0010&\u001a\u00020'2\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u00106\u001a\u0002072\u0006\u0010,\u001a\u00020-2\b\u0010(\u001a\u0004\u0018\u00010)H\u0002JH\u00108\u001a\u0002092\u0006\u0010:\u001a\u00020;2\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010,\u001a\u00020-2\u0006\u0010.\u001a\u00020/2\u0006\u0010&\u001a\u00020'2\u0006\u00102\u001a\u0002032\u0006\u00106\u001a\u0002072\b\u0010(\u001a\u0004\u0018\u00010)J:\u0010<\u001a\u0002092\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010,\u001a\u00020=2\u0006\u0010.\u001a\u00020/2\u0006\u0010&\u001a\u00020'2\u0006\u00106\u001a\u0002072\b\u0010(\u001a\u0004\u0018\u00010)H\u0002Jn\u0010>\u001a\u00020?2*\u0010@\u001a&\u0012\u0004\u0012\u00020;\u0012\u0004\u0012\u00020B\u0012\u0004\u0012\u00020C\u0012\u0004\u0012\u00020D\u0012\u0004\u0012\u00020E\u0012\u0004\u0012\u00020/0A2\u0006\u0010F\u001a\u00020;2\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010,\u001a\u00020=2\u0006\u0010&\u001a\u00020'2\u0006\u00106\u001a\u0002072\u0006\u00102\u001a\u0002032\b\u0010(\u001a\u0004\u0018\u00010)H\u0002J8\u0010>\u001a\u00020?2\u0006\u0010F\u001a\u00020;2\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010,\u001a\u00020=2\u0006\u0010&\u001a\u00020'2\u0006\u00106\u001a\u0002072\b\u0010(\u001a\u0004\u0018\u00010)J@\u0010G\u001a\u00020?2\u0006\u0010F\u001a\u00020;2\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010,\u001a\u00020=2\u0006\u0010&\u001a\u00020'2\u0006\u00106\u001a\u0002072\u0006\u00102\u001a\u0002032\b\u0010(\u001a\u0004\u0018\u00010)J:\u0010H\u001a\u0004\u0018\u00010\u00182\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010I\u001a\u00020J2\u0006\u0010&\u001a\u00020'2\u0006\u0010\u0012\u001a\u00020\u00132\b\u0010(\u001a\u0004\u0018\u00010)J8\u0010K\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010L\u001a\u00020M2\u0006\u0010&\u001a\u00020'2\u0006\u0010\u0012\u001a\u00020\u00132\b\u0010(\u001a\u0004\u0018\u00010)JN\u0010N\u001a\b\u0012\u0004\u0012\u00020+0$2\u0006\u0010.\u001a\u00020O2\u0006\u0010P\u001a\u0002012\f\u0010#\u001a\b\u0012\u0004\u0012\u00020%0$2\u0006\u0010&\u001a\u00020'2\u000e\u0010Q\u001a\n\u0012\u0004\u0012\u000203\u0018\u00010$2\b\u0010(\u001a\u0004\u0018\u00010)H\u0002J\f\u0010R\u001a\u00020\u001e*\u000203H\u0002J\u0012\u0010S\u001a\b\u0012\u0004\u0012\u00020T0$*\u000203H\u0002J\u000e\u0010U\u001a\u0004\u0018\u000103*\u000203H\u0002J\u001c\u0010V\u001a\n\u0012\u0004\u0012\u00020+\u0018\u00010$*\u0002032\u0006\u0010W\u001a\u00020OH\u0002J\f\u0010X\u001a\u000203*\u000203H\u0002R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0013X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0015X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006Z"}, d2 = {"Lorg/jetbrains/kotlin/resolve/FunctionDescriptorResolver;", "", "typeResolver", "Lorg/jetbrains/kotlin/resolve/TypeResolver;", "descriptorResolver", "Lorg/jetbrains/kotlin/resolve/DescriptorResolver;", "annotationResolver", "Lorg/jetbrains/kotlin/resolve/AnnotationResolver;", "builtIns", "Lorg/jetbrains/kotlin/builtins/KotlinBuiltIns;", "modifiersChecker", "Lorg/jetbrains/kotlin/resolve/ModifiersChecker;", "overloadChecker", "Lorg/jetbrains/kotlin/resolve/OverloadChecker;", "contractParsingServices", "Lorg/jetbrains/kotlin/contracts/parsing/ContractParsingServices;", "expressionTypingServices", "Lorg/jetbrains/kotlin/types/expressions/ExpressionTypingServices;", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "storageManager", "Lorg/jetbrains/kotlin/storage/StorageManager;", "(Lorg/jetbrains/kotlin/resolve/TypeResolver;Lorg/jetbrains/kotlin/resolve/DescriptorResolver;Lorg/jetbrains/kotlin/resolve/AnnotationResolver;Lorg/jetbrains/kotlin/builtins/KotlinBuiltIns;Lorg/jetbrains/kotlin/resolve/ModifiersChecker;Lorg/jetbrains/kotlin/resolve/OverloadChecker;Lorg/jetbrains/kotlin/contracts/parsing/ContractParsingServices;Lorg/jetbrains/kotlin/types/expressions/ExpressionTypingServices;Lorg/jetbrains/kotlin/config/LanguageVersionSettings;Lorg/jetbrains/kotlin/storage/StorageManager;)V", "createConstructorDescriptor", "Lorg/jetbrains/kotlin/descriptors/impl/ClassConstructorDescriptorImpl;", "scope", "Lorg/jetbrains/kotlin/resolve/scopes/LexicalScope;", "classDescriptor", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "isPrimary", "", "modifierList", "Lorg/jetbrains/kotlin/psi/KtModifierList;", "declarationToTrace", "Lorg/jetbrains/kotlin/psi/KtPureElement;", "valueParameters", "", "Lorg/jetbrains/kotlin/psi/KtParameter;", "trace", "Lorg/jetbrains/kotlin/resolve/BindingTrace;", "inferenceSession", "Lorg/jetbrains/kotlin/resolve/calls/components/InferenceSession;", "createValueParameterDescriptors", "Lorg/jetbrains/kotlin/descriptors/ValueParameterDescriptor;", "function", "Lorg/jetbrains/kotlin/psi/KtFunction;", "functionDescriptor", "Lorg/jetbrains/kotlin/descriptors/impl/SimpleFunctionDescriptorImpl;", "innerScope", "Lorg/jetbrains/kotlin/resolve/scopes/LexicalWritableScope;", "expectedFunctionType", "Lorg/jetbrains/kotlin/types/KotlinType;", "getContractProvider", "Lorg/jetbrains/kotlin/contracts/description/LazyContractProvider;", "dataFlowInfo", "Lorg/jetbrains/kotlin/resolve/calls/smartcasts/DataFlowInfo;", "initializeFunctionDescriptorAndExplicitReturnType", "", "container", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "initializeFunctionReturnTypeBasedOnFunctionBody", "Lorg/jetbrains/kotlin/psi/KtNamedFunction;", "resolveFunctionDescriptor", "Lorg/jetbrains/kotlin/descriptors/SimpleFunctionDescriptor;", "functionConstructor", "Lkotlin/Function5;", "Lorg/jetbrains/kotlin/descriptors/annotations/Annotations;", "Lorg/jetbrains/kotlin/name/Name;", "Lorg/jetbrains/kotlin/descriptors/CallableMemberDescriptor$Kind;", "Lorg/jetbrains/kotlin/descriptors/SourceElement;", "containingDescriptor", "resolveFunctionExpressionDescriptor", "resolvePrimaryConstructorDescriptor", "classElement", "Lorg/jetbrains/kotlin/psi/KtPureClassOrObject;", "resolveSecondaryConstructorDescriptor", JvmAbi.ERASED_INLINE_CONSTRUCTOR_NAME, "Lorg/jetbrains/kotlin/psi/KtSecondaryConstructor;", "resolveValueParameters", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "parameterScope", "expectedParameterTypes", "functionTypeExpected", "getContextReceiversTypes", "Lorg/jetbrains/kotlin/resolve/FunctionDescriptorResolver$ContextReceiverTypeWithLabel;", "getReceiverType", "getValueParameters", "owner", "removeParameterNameAnnotation", "ContextReceiverTypeWithLabel", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FunctionDescriptorResolver {
    private final AnnotationResolver annotationResolver;
    private final KotlinBuiltIns builtIns;
    private final ContractParsingServices contractParsingServices;
    private final DescriptorResolver descriptorResolver;
    private final ExpressionTypingServices expressionTypingServices;
    private final LanguageVersionSettings languageVersionSettings;
    private final ModifiersChecker modifiersChecker;
    private final OverloadChecker overloadChecker;
    private final StorageManager storageManager;
    private final TypeResolver typeResolver;

    public FunctionDescriptorResolver(TypeResolver typeResolver, DescriptorResolver descriptorResolver, AnnotationResolver annotationResolver, KotlinBuiltIns builtIns, ModifiersChecker modifiersChecker, OverloadChecker overloadChecker, ContractParsingServices contractParsingServices, ExpressionTypingServices expressionTypingServices, LanguageVersionSettings languageVersionSettings, StorageManager storageManager) {
        Intrinsics.checkNotNullParameter(typeResolver, "typeResolver");
        Intrinsics.checkNotNullParameter(descriptorResolver, "descriptorResolver");
        Intrinsics.checkNotNullParameter(annotationResolver, "annotationResolver");
        Intrinsics.checkNotNullParameter(builtIns, "builtIns");
        Intrinsics.checkNotNullParameter(modifiersChecker, "modifiersChecker");
        Intrinsics.checkNotNullParameter(overloadChecker, "overloadChecker");
        Intrinsics.checkNotNullParameter(contractParsingServices, "contractParsingServices");
        Intrinsics.checkNotNullParameter(expressionTypingServices, "expressionTypingServices");
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        Intrinsics.checkNotNullParameter(storageManager, "storageManager");
        this.typeResolver = typeResolver;
        this.descriptorResolver = descriptorResolver;
        this.annotationResolver = annotationResolver;
        this.builtIns = builtIns;
        this.modifiersChecker = modifiersChecker;
        this.overloadChecker = overloadChecker;
        this.contractParsingServices = contractParsingServices;
        this.expressionTypingServices = expressionTypingServices;
        this.languageVersionSettings = languageVersionSettings;
        this.storageManager = storageManager;
    }

    public final SimpleFunctionDescriptor resolveFunctionDescriptor(DeclarationDescriptor containingDescriptor, LexicalScope scope, KtNamedFunction function, BindingTrace trace, DataFlowInfo dataFlowInfo, InferenceSession inferenceSession) {
        Intrinsics.checkNotNullParameter(containingDescriptor, "containingDescriptor");
        Intrinsics.checkNotNullParameter(scope, "scope");
        Intrinsics.checkNotNullParameter(function, "function");
        Intrinsics.checkNotNullParameter(trace, "trace");
        Intrinsics.checkNotNullParameter(dataFlowInfo, "dataFlowInfo");
        if (function.get$name() == null) {
            trace.report(Errors.FUNCTION_DECLARATION_WITH_NO_NAME.on(function));
        }
        C49511 c49511 = C49511.INSTANCE;
        SimpleType NO_EXPECTED_TYPE = TypeUtils.NO_EXPECTED_TYPE;
        Intrinsics.checkNotNullExpressionValue(NO_EXPECTED_TYPE, "NO_EXPECTED_TYPE");
        return resolveFunctionDescriptor(c49511, containingDescriptor, scope, function, trace, dataFlowInfo, NO_EXPECTED_TYPE, inferenceSession);
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.resolve.FunctionDescriptorResolver$resolveFunctionDescriptor$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: FunctionDescriptorResolver.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    /* synthetic */ class C49511 extends FunctionReference implements Function5<DeclarationDescriptor, Annotations, Name, CallableMemberDescriptor.Kind, SourceElement, SimpleFunctionDescriptorImpl> {
        public static final C49511 INSTANCE = new C49511();

        C49511() {
            super(5);
        }

        @Override // kotlin.jvm.internal.CallableReference
        public final String getSignature() {
            return "create(Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;Lorg/jetbrains/kotlin/descriptors/annotations/Annotations;Lorg/jetbrains/kotlin/name/Name;Lorg/jetbrains/kotlin/descriptors/CallableMemberDescriptor$Kind;Lorg/jetbrains/kotlin/descriptors/SourceElement;)Lorg/jetbrains/kotlin/descriptors/impl/SimpleFunctionDescriptorImpl;";
        }

        @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
        public final String getName() {
            return CoroutineCodegenUtilKt.SUSPEND_FUNCTION_CREATE_METHOD_NAME;
        }

        @Override // kotlin.jvm.internal.CallableReference
        public final KDeclarationContainer getOwner() {
            return Reflection.getOrCreateKotlinClass(SimpleFunctionDescriptorImpl.class);
        }

        @Override // kotlin.jvm.functions.Function5
        public final SimpleFunctionDescriptorImpl invoke(DeclarationDescriptor p0, Annotations p1, Name p2, CallableMemberDescriptor.Kind p3, SourceElement p4) {
            Intrinsics.checkNotNullParameter(p0, "p0");
            Intrinsics.checkNotNullParameter(p1, "p1");
            Intrinsics.checkNotNullParameter(p2, "p2");
            Intrinsics.checkNotNullParameter(p3, "p3");
            Intrinsics.checkNotNullParameter(p4, "p4");
            return SimpleFunctionDescriptorImpl.create(p0, p1, p2, p3, p4);
        }
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.resolve.FunctionDescriptorResolver$resolveFunctionExpressionDescriptor$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: FunctionDescriptorResolver.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    /* synthetic */ class C49521 extends FunctionReference implements Function5<DeclarationDescriptor, Annotations, Name, CallableMemberDescriptor.Kind, SourceElement, FunctionExpressionDescriptor> {
        public static final C49521 INSTANCE = new C49521();

        C49521() {
            super(5);
        }

        @Override // kotlin.jvm.internal.CallableReference
        public final String getSignature() {
            return "<init>(Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;Lorg/jetbrains/kotlin/descriptors/annotations/Annotations;Lorg/jetbrains/kotlin/name/Name;Lorg/jetbrains/kotlin/descriptors/CallableMemberDescriptor$Kind;Lorg/jetbrains/kotlin/descriptors/SourceElement;)V";
        }

        @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
        public final String getName() {
            return "<init>";
        }

        @Override // kotlin.jvm.internal.CallableReference
        public final KDeclarationContainer getOwner() {
            return Reflection.getOrCreateKotlinClass(FunctionExpressionDescriptor.class);
        }

        @Override // kotlin.jvm.functions.Function5
        public final FunctionExpressionDescriptor invoke(DeclarationDescriptor p0, Annotations p1, Name p2, CallableMemberDescriptor.Kind p3, SourceElement p4) {
            Intrinsics.checkNotNullParameter(p0, "p0");
            Intrinsics.checkNotNullParameter(p1, "p1");
            Intrinsics.checkNotNullParameter(p2, "p2");
            Intrinsics.checkNotNullParameter(p3, "p3");
            Intrinsics.checkNotNullParameter(p4, "p4");
            return new FunctionExpressionDescriptor(p0, p1, p2, p3, p4);
        }
    }

    public final SimpleFunctionDescriptor resolveFunctionExpressionDescriptor(DeclarationDescriptor containingDescriptor, LexicalScope scope, KtNamedFunction function, BindingTrace trace, DataFlowInfo dataFlowInfo, KotlinType expectedFunctionType, InferenceSession inferenceSession) {
        Intrinsics.checkNotNullParameter(containingDescriptor, "containingDescriptor");
        Intrinsics.checkNotNullParameter(scope, "scope");
        Intrinsics.checkNotNullParameter(function, "function");
        Intrinsics.checkNotNullParameter(trace, "trace");
        Intrinsics.checkNotNullParameter(dataFlowInfo, "dataFlowInfo");
        Intrinsics.checkNotNullParameter(expectedFunctionType, "expectedFunctionType");
        return resolveFunctionDescriptor(C49521.INSTANCE, containingDescriptor, scope, function, trace, dataFlowInfo, expectedFunctionType, inferenceSession);
    }

    private final SimpleFunctionDescriptor resolveFunctionDescriptor(Function5<? super DeclarationDescriptor, ? super Annotations, ? super Name, ? super CallableMemberDescriptor.Kind, ? super SourceElement, ? extends SimpleFunctionDescriptorImpl> functionConstructor, DeclarationDescriptor containingDescriptor, LexicalScope scope, KtNamedFunction function, BindingTrace trace, DataFlowInfo dataFlowInfo, KotlinType expectedFunctionType, InferenceSession inferenceSession) {
        Annotations annotationsResolveAnnotationsWithoutArguments = this.annotationResolver.resolveAnnotationsWithoutArguments(scope, function.getModifierList(), trace);
        Name nameAsSafeName = function.getNameAsSafeName();
        Intrinsics.checkNotNullExpressionValue(nameAsSafeName, "getNameAsSafeName(...)");
        SimpleFunctionDescriptorImpl simpleFunctionDescriptorImplInvoke = functionConstructor.invoke(containingDescriptor, annotationsResolveAnnotationsWithoutArguments, nameAsSafeName, CallableMemberDescriptor.Kind.DECLARATION, KotlinSourceElementKt.toSourceElement(function));
        initializeFunctionDescriptorAndExplicitReturnType(containingDescriptor, scope, function, simpleFunctionDescriptorImplInvoke, trace, expectedFunctionType, dataFlowInfo, inferenceSession);
        initializeFunctionReturnTypeBasedOnFunctionBody(scope, function, simpleFunctionDescriptorImplInvoke, trace, dataFlowInfo, inferenceSession);
        SimpleFunctionDescriptorImpl simpleFunctionDescriptorImpl = simpleFunctionDescriptorImplInvoke;
        BindingContextUtils.recordFunctionDeclarationToDescriptor(trace, function, simpleFunctionDescriptorImpl);
        return simpleFunctionDescriptorImpl;
    }

    private final void initializeFunctionReturnTypeBasedOnFunctionBody(LexicalScope scope, KtNamedFunction function, SimpleFunctionDescriptorImpl functionDescriptor, BindingTrace trace, DataFlowInfo dataFlowInfo, InferenceSession inferenceSession) {
        ErrorType errorTypeCreateErrorType;
        if (functionDescriptor.getReturnType() != null) {
            return;
        }
        function.mo7396getTypeReference();
        if (function.hasBlockBody()) {
            errorTypeCreateErrorType = this.builtIns.getUnitType();
        } else if (function.hasBody()) {
            errorTypeCreateErrorType = this.descriptorResolver.inferReturnTypeFromExpressionBody(trace, scope, dataFlowInfo, function, functionDescriptor, inferenceSession);
        } else {
            ErrorTypeKind errorTypeKind = ErrorTypeKind.RETURN_TYPE;
            String strAsString = functionDescriptor.getShortName().asString();
            Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
            errorTypeCreateErrorType = ErrorUtils.createErrorType(errorTypeKind, strAsString);
        }
        Intrinsics.checkNotNull(errorTypeCreateErrorType);
        functionDescriptor.setReturnType(errorTypeCreateErrorType);
    }

    /* JADX WARN: Code duplicated, block: B:100:0x00e1 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:12:0x0098  */
    /* JADX WARN: Code duplicated, block: B:13:0x00a1  */
    /* JADX WARN: Code duplicated, block: B:16:0x00b5  */
    /* JADX WARN: Code duplicated, block: B:18:0x00c1  */
    /* JADX WARN: Code duplicated, block: B:19:0x00c8  */
    /* JADX WARN: Code duplicated, block: B:21:0x00de  */
    /* JADX WARN: Code duplicated, block: B:26:0x0112  */
    /* JADX WARN: Code duplicated, block: B:27:0x011b  */
    /* JADX WARN: Code duplicated, block: B:30:0x015f  */
    /* JADX WARN: Code duplicated, block: B:40:0x0185  */
    /* JADX WARN: Code duplicated, block: B:41:0x01ac  */
    /* JADX WARN: Code duplicated, block: B:45:0x01c2  */
    /* JADX WARN: Code duplicated, block: B:47:0x01ca  */
    /* JADX WARN: Code duplicated, block: B:50:0x0208  */
    /* JADX WARN: Code duplicated, block: B:54:0x0224  */
    /* JADX WARN: Code duplicated, block: B:63:0x0259  */
    /* JADX WARN: Code duplicated, block: B:65:0x027d  */
    /* JADX WARN: Code duplicated, block: B:68:0x0289  */
    /* JADX WARN: Code duplicated, block: B:71:0x0294  */
    /* JADX WARN: Code duplicated, block: B:72:0x0297  */
    /* JADX WARN: Code duplicated, block: B:85:0x0308  */
    /* JADX WARN: Code duplicated, block: B:89:0x0319 A[LOOP:2: B:87:0x0313->B:89:0x0319, LOOP_END] */
    /* JADX WARN: Code duplicated, block: B:93:0x020b A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:96:0x0280 A[SYNTHETIC] */
    public final void initializeFunctionDescriptorAndExplicitReturnType(DeclarationDescriptor container, LexicalScope scope, KtFunction function, SimpleFunctionDescriptorImpl functionDescriptor, BindingTrace trace, KotlinType expectedFunctionType, DataFlowInfo dataFlowInfo, InferenceSession inferenceSession) {
        KotlinType kotlinType;
        KotlinType receiverType;
        List<KtContextReceiver> contextReceivers;
        ArrayList arrayList;
        Iterator it;
        KtTypeReference ktTypeReference;
        List<ContextReceiverTypeWithLabel> contextReceiversTypes;
        KtContextReceiver ktContextReceiver;
        KtTypeReference ktTypeReferenceTypeReference;
        ContextReceiverTypeWithLabel contextReceiverTypeWithLabel;
        KotlinType kotlinType2;
        List<KtContextReceiver> list;
        KtTypeReference ktTypeReference2;
        KtTypeReference typeReference;
        KotlinType kotlinTypeResolveType;
        DescriptorVisibility descriptorVisibilityResolveVisibilityFromModifiers;
        LazyContractProvider contractProvider;
        LinkedHashMap linkedHashMap;
        String str;
        ReceiverParameterDescriptor receiverParameterDescriptorCreateExtensionReceiverParameterForCallable;
        ArrayList arrayList2;
        Iterator it2;
        int i;
        ArrayList arrayList3;
        boolean z;
        LinkedHashMap linkedHashMap2;
        Iterator<ValueParameterDescriptor> it3;
        HashMultimap hashMultimapCreate;
        ReceiverParameterDescriptor receiverParameterDescriptor;
        String strName;
        String strNameForReceiverLabel;
        ReceiverParameterDescriptor receiverParameterDescriptorCreateContextReceiverParameterForCallable;
        Intrinsics.checkNotNullParameter(container, "container");
        Intrinsics.checkNotNullParameter(scope, "scope");
        Intrinsics.checkNotNullParameter(function, "function");
        Intrinsics.checkNotNullParameter(functionDescriptor, "functionDescriptor");
        Intrinsics.checkNotNullParameter(trace, "trace");
        Intrinsics.checkNotNullParameter(expectedFunctionType, "expectedFunctionType");
        Intrinsics.checkNotNullParameter(dataFlowInfo, "dataFlowInfo");
        SimpleFunctionDescriptorImpl simpleFunctionDescriptorImpl = functionDescriptor;
        LexicalWritableScope lexicalWritableScope = new LexicalWritableScope(scope, simpleFunctionDescriptorImpl, true, new TraceBasedLocalRedeclarationChecker(trace, this.overloadChecker), LexicalScopeKind.FUNCTION_HEADER);
        List<TypeParameterDescriptorImpl> listResolveTypeParametersForDescriptor = this.descriptorResolver.resolveTypeParametersForDescriptor(simpleFunctionDescriptorImpl, lexicalWritableScope, scope, function.getTypeParameters(), trace);
        LexicalWritableScope lexicalWritableScope2 = lexicalWritableScope;
        this.descriptorResolver.resolveGenericBounds(function, simpleFunctionDescriptorImpl, lexicalWritableScope2, listResolveTypeParametersForDescriptor, trace);
        KtTypeReference receiverTypeReference = function.mo7393getReceiverTypeReference();
        if (receiverTypeReference != null) {
            receiverType = this.typeResolver.resolveType(lexicalWritableScope2, receiverTypeReference, trace, true);
        } else {
            if (function instanceof KtFunctionLiteral) {
                receiverType = getReceiverType(expectedFunctionType);
            } else {
                kotlinType = null;
            }
            contextReceivers = function.getContextReceivers();
            Intrinsics.checkNotNullExpressionValue(contextReceivers, "getContextReceivers(...)");
            if (function instanceof KtFunctionLiteral) {
                contextReceiversTypes = getContextReceiversTypes(expectedFunctionType);
                ktTypeReference = receiverTypeReference;
            } else {
                arrayList = new ArrayList();
                it = contextReceivers.iterator();
                while (it.hasNext()) {
                    ktContextReceiver = (KtContextReceiver) it.next();
                    ktTypeReferenceTypeReference = ktContextReceiver.typeReference();
                    if (ktTypeReferenceTypeReference == null) {
                        contextReceiverTypeWithLabel = null;
                    } else {
                        contextReceiverTypeWithLabel = new ContextReceiverTypeWithLabel(this.typeResolver.resolveType(lexicalWritableScope2, ktTypeReferenceTypeReference, trace, true), ktContextReceiver.labelNameAsName());
                    }
                    if (contextReceiverTypeWithLabel != null) {
                        arrayList.add(contextReceiverTypeWithLabel);
                    }
                    it = it;
                    receiverTypeReference = receiverTypeReference;
                }
                ktTypeReference = receiverTypeReference;
                contextReceiversTypes = arrayList;
            }
            kotlinType2 = kotlinType;
            list = contextReceivers;
            ktTypeReference2 = ktTypeReference;
            List<ValueParameterDescriptor> listCreateValueParameterDescriptors = createValueParameterDescriptors(function, functionDescriptor, lexicalWritableScope, trace, expectedFunctionType, inferenceSession);
            lexicalWritableScope.freeze();
            typeReference = function.mo7396getTypeReference();
            if (typeReference != null) {
                kotlinTypeResolveType = this.typeResolver.resolveType(lexicalWritableScope2, typeReference, trace, true);
            } else {
                kotlinTypeResolveType = null;
            }
            KtFunction ktFunction = function;
            descriptorVisibilityResolveVisibilityFromModifiers = ModifiersChecker.resolveVisibilityFromModifiers(ktFunction, DescriptorResolver.getDefaultVisibility(ktFunction, container));
            Intrinsics.checkNotNullExpressionValue(descriptorVisibilityResolveVisibilityFromModifiers, "resolveVisibilityFromModifiers(...)");
            Modality modalityResolveMemberModalityFromModifiers = ModifiersChecker.resolveMemberModalityFromModifiers(ktFunction, DescriptorResolver.getDefaultModality(container, descriptorVisibilityResolveVisibilityFromModifiers, function.hasBody()), trace.getContext(), container);
            Intrinsics.checkNotNullExpressionValue(modalityResolveMemberModalityFromModifiers, "resolveMemberModalityFromModifiers(...)");
            contractProvider = getContractProvider(functionDescriptor, trace, scope, dataFlowInfo, function, inferenceSession);
            linkedHashMap = new LinkedHashMap();
            if (contractProvider != null) {
                linkedHashMap.put(ContractProviderKey.INSTANCE, contractProvider);
            }
            if (kotlinType2 != null && functionTypeExpected(expectedFunctionType) && !expectedFunctionType.getAnnotations().isEmpty()) {
                linkedHashMap.put(DslMarkerUtils.FunctionTypeAnnotationsKey.INSTANCE, expectedFunctionType.getAnnotations());
            }
            str = "of(...)";
            if (kotlinType2 != null) {
                StorageManager storageManager = this.storageManager;
                Annotations annotations = kotlinType2.getAnnotations();
                EnumSet enumSetOf = EnumSet.of(AnnotationUseSiteTarget.RECEIVER);
                Intrinsics.checkNotNullExpressionValue(enumSetOf, "of(...)");
                receiverParameterDescriptorCreateExtensionReceiverParameterForCallable = DescriptorFactory.createExtensionReceiverParameterForCallable(functionDescriptor, kotlinType2, new AnnotationSplitter(storageManager, annotations, enumSetOf).getAnnotationsForTarget(AnnotationUseSiteTarget.RECEIVER));
            } else {
                receiverParameterDescriptorCreateExtensionReceiverParameterForCallable = null;
            }
            arrayList2 = new ArrayList();
            it2 = contextReceiversTypes.iterator();
            i = 0;
            while (it2.hasNext()) {
                Object next = it2.next();
                int i2 = i + 1;
                if (i < 0) {
                    CollectionsKt.throwIndexOverflow();
                }
                ContextReceiverTypeWithLabel contextReceiverTypeWithLabel2 = (ContextReceiverTypeWithLabel) next;
                StorageManager storageManager2 = this.storageManager;
                Iterator it4 = it2;
                Annotations annotations2 = contextReceiverTypeWithLabel2.getType().getAnnotations();
                DescriptorVisibility descriptorVisibility = descriptorVisibilityResolveVisibilityFromModifiers;
                EnumSet enumSetOf2 = EnumSet.of(AnnotationUseSiteTarget.RECEIVER);
                Intrinsics.checkNotNullExpressionValue(enumSetOf2, str);
                String str2 = str;
                receiverParameterDescriptorCreateContextReceiverParameterForCallable = DescriptorFactory.createContextReceiverParameterForCallable(functionDescriptor, contextReceiverTypeWithLabel2.getType(), contextReceiverTypeWithLabel2.getLabel(), new AnnotationSplitter(storageManager2, annotations2, enumSetOf2).getAnnotationsForTarget(AnnotationUseSiteTarget.RECEIVER), i);
                if (receiverParameterDescriptorCreateContextReceiverParameterForCallable != null) {
                    arrayList2.add(receiverParameterDescriptorCreateContextReceiverParameterForCallable);
                }
                it2 = it4;
                str = str2;
                i = i2;
                descriptorVisibilityResolveVisibilityFromModifiers = descriptorVisibility;
            }
            DescriptorVisibility descriptorVisibility2 = descriptorVisibilityResolveVisibilityFromModifiers;
            arrayList3 = arrayList2;
            if (this.languageVersionSettings.supportsFeature(LanguageFeature.ContextReceivers)) {
                hashMultimapCreate = HashMultimap.create();
                if (ktTypeReference2 != null && receiverParameterDescriptorCreateExtensionReceiverParameterForCallable != null && (strNameForReceiverLabel = ktTypeReference2.nameForReceiverLabel()) != null) {
                    hashMultimapCreate.put(strNameForReceiverLabel, receiverParameterDescriptorCreateExtensionReceiverParameterForCallable);
                }
                z = false;
                for (Pair pair : CollectionsKt.reversed(CollectionsKt.zip(arrayList3, RangesKt.until(0, list.size())))) {
                    receiverParameterDescriptor = (ReceiverParameterDescriptor) pair.component1();
                    List<KtContextReceiver> list2 = list;
                    strName = list2.get(((Number) pair.component2()).intValue()).name();
                    if (strName != null) {
                        hashMultimapCreate.put(strName, receiverParameterDescriptor);
                    }
                    list = list2;
                }
                trace.record(BindingContext.DESCRIPTOR_TO_CONTEXT_RECEIVER_MAP, functionDescriptor, hashMultimapCreate);
            } else {
                z = false;
            }
            ReceiverParameterDescriptor dispatchReceiverParameterIfNeeded = DescriptorUtils.getDispatchReceiverParameterIfNeeded(container);
            if (linkedHashMap.isEmpty()) {
                linkedHashMap2 = null;
            } else {
                linkedHashMap2 = linkedHashMap;
            }
            boolean z2 = true;
            functionDescriptor.initialize(receiverParameterDescriptorCreateExtensionReceiverParameterForCallable, dispatchReceiverParameterIfNeeded, arrayList3, listResolveTypeParametersForDescriptor, listCreateValueParameterDescriptors, kotlinTypeResolveType, modalityResolveMemberModalityFromModifiers, descriptorVisibility2, linkedHashMap2);
            functionDescriptor.setOperator(function.hasModifier(KtTokens.OPERATOR_KEYWORD));
            functionDescriptor.setInfix(function.hasModifier(KtTokens.INFIX_KEYWORD));
            functionDescriptor.setExternal(function.hasModifier(KtTokens.EXTERNAL_KEYWORD));
            functionDescriptor.setInline(function.hasModifier(KtTokens.INLINE_KEYWORD));
            functionDescriptor.setTailrec(function.hasModifier(KtTokens.TAILREC_KEYWORD));
            functionDescriptor.setSuspend(function.hasModifier(KtTokens.SUSPEND_KEYWORD));
            if (((container instanceof PackageFragmentDescriptor) || !PsiUtilsKt.hasExpectModifier(ktFunction)) && (!(container instanceof ClassDescriptor) || !((ClassDescriptor) container).get$isExpect())) {
            }
            functionDescriptor.setExpect(z2);
            functionDescriptor.setActual(PsiUtilsKt.hasActualModifier(ktFunction));
            if (kotlinType2 != null) {
                ForceResolveUtil.forceResolveAllContents(kotlinType2.getAnnotations());
            }
            it3 = listCreateValueParameterDescriptors.iterator();
            while (it3.hasNext()) {
                ForceResolveUtil.forceResolveAllContents(it3.next().getType().getAnnotations());
            }
        }
        kotlinType = receiverType;
        contextReceivers = function.getContextReceivers();
        Intrinsics.checkNotNullExpressionValue(contextReceivers, "getContextReceivers(...)");
        if (function instanceof KtFunctionLiteral) {
            contextReceiversTypes = getContextReceiversTypes(expectedFunctionType);
            ktTypeReference = receiverTypeReference;
        } else {
            arrayList = new ArrayList();
            it = contextReceivers.iterator();
            while (it.hasNext()) {
                ktContextReceiver = (KtContextReceiver) it.next();
                ktTypeReferenceTypeReference = ktContextReceiver.typeReference();
                if (ktTypeReferenceTypeReference == null) {
                    contextReceiverTypeWithLabel = null;
                } else {
                    contextReceiverTypeWithLabel = new ContextReceiverTypeWithLabel(this.typeResolver.resolveType(lexicalWritableScope2, ktTypeReferenceTypeReference, trace, true), ktContextReceiver.labelNameAsName());
                }
                if (contextReceiverTypeWithLabel != null) {
                    arrayList.add(contextReceiverTypeWithLabel);
                }
                it = it;
                receiverTypeReference = receiverTypeReference;
            }
            ktTypeReference = receiverTypeReference;
            contextReceiversTypes = arrayList;
        }
        kotlinType2 = kotlinType;
        list = contextReceivers;
        ktTypeReference2 = ktTypeReference;
        List<ValueParameterDescriptor> listCreateValueParameterDescriptors2 = createValueParameterDescriptors(function, functionDescriptor, lexicalWritableScope, trace, expectedFunctionType, inferenceSession);
        lexicalWritableScope.freeze();
        typeReference = function.mo7396getTypeReference();
        if (typeReference != null) {
            kotlinTypeResolveType = this.typeResolver.resolveType(lexicalWritableScope2, typeReference, trace, true);
        } else {
            kotlinTypeResolveType = null;
        }
        KtFunction ktFunction2 = function;
        descriptorVisibilityResolveVisibilityFromModifiers = ModifiersChecker.resolveVisibilityFromModifiers(ktFunction2, DescriptorResolver.getDefaultVisibility(ktFunction2, container));
        Intrinsics.checkNotNullExpressionValue(descriptorVisibilityResolveVisibilityFromModifiers, "resolveVisibilityFromModifiers(...)");
        Modality modalityResolveMemberModalityFromModifiers2 = ModifiersChecker.resolveMemberModalityFromModifiers(ktFunction2, DescriptorResolver.getDefaultModality(container, descriptorVisibilityResolveVisibilityFromModifiers, function.hasBody()), trace.getContext(), container);
        Intrinsics.checkNotNullExpressionValue(modalityResolveMemberModalityFromModifiers2, "resolveMemberModalityFromModifiers(...)");
        contractProvider = getContractProvider(functionDescriptor, trace, scope, dataFlowInfo, function, inferenceSession);
        linkedHashMap = new LinkedHashMap();
        if (contractProvider != null) {
            linkedHashMap.put(ContractProviderKey.INSTANCE, contractProvider);
        }
        if (kotlinType2 != null) {
            linkedHashMap.put(DslMarkerUtils.FunctionTypeAnnotationsKey.INSTANCE, expectedFunctionType.getAnnotations());
        }
        str = "of(...)";
        if (kotlinType2 != null) {
            StorageManager storageManager3 = this.storageManager;
            Annotations annotations3 = kotlinType2.getAnnotations();
            EnumSet enumSetOf3 = EnumSet.of(AnnotationUseSiteTarget.RECEIVER);
            Intrinsics.checkNotNullExpressionValue(enumSetOf3, "of(...)");
            receiverParameterDescriptorCreateExtensionReceiverParameterForCallable = DescriptorFactory.createExtensionReceiverParameterForCallable(functionDescriptor, kotlinType2, new AnnotationSplitter(storageManager3, annotations3, enumSetOf3).getAnnotationsForTarget(AnnotationUseSiteTarget.RECEIVER));
        } else {
            receiverParameterDescriptorCreateExtensionReceiverParameterForCallable = null;
        }
        arrayList2 = new ArrayList();
        it2 = contextReceiversTypes.iterator();
        i = 0;
        while (it2.hasNext()) {
            Object next2 = it2.next();
            int i3 = i + 1;
            if (i < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            ContextReceiverTypeWithLabel contextReceiverTypeWithLabel3 = (ContextReceiverTypeWithLabel) next2;
            StorageManager storageManager4 = this.storageManager;
            Iterator it5 = it2;
            Annotations annotations4 = contextReceiverTypeWithLabel3.getType().getAnnotations();
            DescriptorVisibility descriptorVisibility3 = descriptorVisibilityResolveVisibilityFromModifiers;
            EnumSet enumSetOf4 = EnumSet.of(AnnotationUseSiteTarget.RECEIVER);
            Intrinsics.checkNotNullExpressionValue(enumSetOf4, str);
            String str3 = str;
            receiverParameterDescriptorCreateContextReceiverParameterForCallable = DescriptorFactory.createContextReceiverParameterForCallable(functionDescriptor, contextReceiverTypeWithLabel3.getType(), contextReceiverTypeWithLabel3.getLabel(), new AnnotationSplitter(storageManager4, annotations4, enumSetOf4).getAnnotationsForTarget(AnnotationUseSiteTarget.RECEIVER), i);
            if (receiverParameterDescriptorCreateContextReceiverParameterForCallable != null) {
                arrayList2.add(receiverParameterDescriptorCreateContextReceiverParameterForCallable);
            }
            it2 = it5;
            str = str3;
            i = i3;
            descriptorVisibilityResolveVisibilityFromModifiers = descriptorVisibility3;
        }
        DescriptorVisibility descriptorVisibility4 = descriptorVisibilityResolveVisibilityFromModifiers;
        arrayList3 = arrayList2;
        if (this.languageVersionSettings.supportsFeature(LanguageFeature.ContextReceivers)) {
            hashMultimapCreate = HashMultimap.create();
            if (ktTypeReference2 != null) {
                hashMultimapCreate.put(strNameForReceiverLabel, receiverParameterDescriptorCreateExtensionReceiverParameterForCallable);
            }
            z = false;
            while (r3.hasNext()) {
                receiverParameterDescriptor = (ReceiverParameterDescriptor) pair.component1();
                List<KtContextReceiver> list3 = list;
                strName = list3.get(((Number) pair.component2()).intValue()).name();
                if (strName != null) {
                    hashMultimapCreate.put(strName, receiverParameterDescriptor);
                }
                list = list3;
            }
            trace.record(BindingContext.DESCRIPTOR_TO_CONTEXT_RECEIVER_MAP, functionDescriptor, hashMultimapCreate);
        } else {
            z = false;
        }
        ReceiverParameterDescriptor dispatchReceiverParameterIfNeeded2 = DescriptorUtils.getDispatchReceiverParameterIfNeeded(container);
        if (linkedHashMap.isEmpty()) {
            linkedHashMap2 = linkedHashMap;
        } else {
            linkedHashMap2 = null;
        }
        boolean z3 = true;
        functionDescriptor.initialize(receiverParameterDescriptorCreateExtensionReceiverParameterForCallable, dispatchReceiverParameterIfNeeded2, arrayList3, listResolveTypeParametersForDescriptor, listCreateValueParameterDescriptors2, kotlinTypeResolveType, modalityResolveMemberModalityFromModifiers2, descriptorVisibility4, linkedHashMap2);
        functionDescriptor.setOperator(function.hasModifier(KtTokens.OPERATOR_KEYWORD));
        functionDescriptor.setInfix(function.hasModifier(KtTokens.INFIX_KEYWORD));
        functionDescriptor.setExternal(function.hasModifier(KtTokens.EXTERNAL_KEYWORD));
        functionDescriptor.setInline(function.hasModifier(KtTokens.INLINE_KEYWORD));
        functionDescriptor.setTailrec(function.hasModifier(KtTokens.TAILREC_KEYWORD));
        functionDescriptor.setSuspend(function.hasModifier(KtTokens.SUSPEND_KEYWORD));
        z3 = container instanceof PackageFragmentDescriptor ? z : z;
        functionDescriptor.setExpect(z3);
        functionDescriptor.setActual(PsiUtilsKt.hasActualModifier(ktFunction2));
        if (kotlinType2 != null) {
            ForceResolveUtil.forceResolveAllContents(kotlinType2.getAnnotations());
        }
        it3 = listCreateValueParameterDescriptors2.iterator();
        while (it3.hasNext()) {
            ForceResolveUtil.forceResolveAllContents(it3.next().getType().getAnnotations());
        }
    }

    private final LazyContractProvider getContractProvider(SimpleFunctionDescriptorImpl functionDescriptor, BindingTrace trace, LexicalScope scope, DataFlowInfo dataFlowInfo, KtFunction function, InferenceSession inferenceSession) {
        if (!(function instanceof KtNamedFunction)) {
            return null;
        }
        boolean zSupportsFeature = this.languageVersionSettings.supportsFeature(LanguageFeature.AllowContractsForCustomFunctions);
        boolean zSupportsFeature2 = this.languageVersionSettings.supportsFeature(LanguageFeature.AllowContractsForNonOverridableMembers);
        if (zSupportsFeature && ((KtNamedFunction) function).mayHaveContract(zSupportsFeature2)) {
            return new LazyContractProvider(this.storageManager, new AnonymousClass1(function, this, trace, scope, dataFlowInfo, functionDescriptor, inferenceSession));
        }
        return null;
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.resolve.FunctionDescriptorResolver$getContractProvider$1, reason: invalid class name */
    /* JADX INFO: compiled from: FunctionDescriptorResolver.kt */
    @Metadata(d1 = {"\u0000\b\n\u0000\n\u0002\u0010\u0000\n\u0000\u0010\u0000\u001a\u0004\u0018\u00010\u0001H\n¢\u0006\u0002\b\u0002"}, d2 = {SpecialNames.ANONYMOUS_STRING, "", "invoke"}, k = 3, mv = {1, 9, 0}, xi = 48)
    static final class AnonymousClass1 extends Lambda implements Function0<Object> {
        final /* synthetic */ DataFlowInfo $dataFlowInfo;
        final /* synthetic */ KtFunction $function;
        final /* synthetic */ SimpleFunctionDescriptorImpl $functionDescriptor;
        final /* synthetic */ InferenceSession $inferenceSession;
        final /* synthetic */ LexicalScope $scope;
        final /* synthetic */ BindingTrace $trace;
        final /* synthetic */ FunctionDescriptorResolver this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass1(KtFunction ktFunction, FunctionDescriptorResolver functionDescriptorResolver, BindingTrace bindingTrace, LexicalScope lexicalScope, DataFlowInfo dataFlowInfo, SimpleFunctionDescriptorImpl simpleFunctionDescriptorImpl, InferenceSession inferenceSession) {
            super(0);
            this.$function = ktFunction;
            this.this$0 = functionDescriptorResolver;
            this.$trace = bindingTrace;
            this.$scope = lexicalScope;
            this.$dataFlowInfo = dataFlowInfo;
            this.$functionDescriptor = simpleFunctionDescriptorImpl;
            this.$inferenceSession = inferenceSession;
        }

        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            PsiFile containingFile = ((KtNamedFunction) this.$function).getContainingFile();
            final FunctionDescriptorResolver functionDescriptorResolver = this.this$0;
            final BindingTrace bindingTrace = this.$trace;
            final LexicalScope lexicalScope = this.$scope;
            final DataFlowInfo dataFlowInfo = this.$dataFlowInfo;
            final KtFunction ktFunction = this.$function;
            final SimpleFunctionDescriptorImpl simpleFunctionDescriptorImpl = this.$functionDescriptor;
            final InferenceSession inferenceSession = this.$inferenceSession;
            return AstLoadingFilter.forceAllowTreeLoading(containingFile, new ThrowableComputable() { // from class: org.jetbrains.kotlin.resolve.FunctionDescriptorResolver$getContractProvider$1$$ExternalSyntheticLambda0
                @Override // org.jetbrains.kotlin.com.intellij.openapi.util.ThrowableComputable
                public final Object compute() {
                    return FunctionDescriptorResolver.AnonymousClass1.invoke$lambda$0(functionDescriptorResolver, bindingTrace, lexicalScope, dataFlowInfo, ktFunction, simpleFunctionDescriptorImpl, inferenceSession);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final KotlinType invoke$lambda$0(FunctionDescriptorResolver this$0, BindingTrace trace, LexicalScope scope, DataFlowInfo dataFlowInfo, KtFunction function, SimpleFunctionDescriptorImpl functionDescriptor, InferenceSession inferenceSession) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            Intrinsics.checkNotNullParameter(trace, "$trace");
            Intrinsics.checkNotNullParameter(scope, "$scope");
            Intrinsics.checkNotNullParameter(dataFlowInfo, "$dataFlowInfo");
            Intrinsics.checkNotNullParameter(function, "$function");
            Intrinsics.checkNotNullParameter(functionDescriptor, "$functionDescriptor");
            return this$0.expressionTypingServices.getBodyExpressionType(trace, scope, dataFlowInfo, function, functionDescriptor, inferenceSession);
        }
    }

    private final List<ValueParameterDescriptor> createValueParameterDescriptors(KtFunction function, SimpleFunctionDescriptorImpl functionDescriptor, LexicalWritableScope innerScope, BindingTrace trace, KotlinType expectedFunctionType, InferenceSession inferenceSession) {
        ArrayList arrayList;
        SimpleFunctionDescriptorImpl simpleFunctionDescriptorImpl = functionDescriptor;
        List<ValueParameterDescriptor> valueParameters = getValueParameters(expectedFunctionType, simpleFunctionDescriptorImpl);
        if (valueParameters != null) {
            List<ValueParameterDescriptor> list = valueParameters;
            ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
            Iterator<T> it = list.iterator();
            while (it.hasNext()) {
                KotlinType type = ((ValueParameterDescriptor) it.next()).getType();
                Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
                arrayList2.add(removeParameterNameAnnotation(type));
            }
            arrayList = arrayList2;
        } else {
            arrayList = null;
        }
        if (valueParameters != null) {
            if (valueParameters.size() == 1 && (function instanceof KtFunctionLiteral) && function.getValueParameterList() == null) {
                ValueParameterDescriptor valueParameterDescriptor = (ValueParameterDescriptor) CollectionsKt.single((List) valueParameters);
                Annotations empty = Annotations.INSTANCE.getEMPTY();
                Name name2 = StandardNames.IMPLICIT_LAMBDA_PARAMETER_NAME;
                Intrinsics.checkNotNull(arrayList);
                KotlinType kotlinType = (KotlinType) CollectionsKt.single((List) arrayList);
                boolean zDeclaresDefaultValue = valueParameterDescriptor.declaresDefaultValue();
                boolean isCrossinline = valueParameterDescriptor.getIsCrossinline();
                boolean isNoinline = valueParameterDescriptor.getIsNoinline();
                KotlinType varargElementType = valueParameterDescriptor.getVarargElementType();
                SourceElement NO_SOURCE = SourceElement.NO_SOURCE;
                Intrinsics.checkNotNullExpressionValue(NO_SOURCE, "NO_SOURCE");
                ValueParameterDescriptorImpl valueParameterDescriptorImpl = new ValueParameterDescriptorImpl(functionDescriptor, null, 0, empty, name2, kotlinType, zDeclaresDefaultValue, isCrossinline, isNoinline, varargElementType, NO_SOURCE);
                trace.record(BindingContext.AUTO_CREATED_IT, valueParameterDescriptorImpl);
                return CollectionsKt.listOf(valueParameterDescriptorImpl);
            }
            if (function.getValueParameters().size() != valueParameters.size()) {
                Intrinsics.checkNotNull(arrayList);
                trace.report(Errors.EXPECTED_PARAMETERS_NUMBER_MISMATCH.on(function, Integer.valueOf(arrayList.size()), arrayList));
            }
        }
        BindingContextUtilsKt.recordScope(trace, innerScope, function.getValueParameterList());
        List<KtParameter> valueParameters2 = function.getValueParameters();
        Intrinsics.checkNotNullExpressionValue(valueParameters2, "getValueParameters(...)");
        return resolveValueParameters(simpleFunctionDescriptorImpl, innerScope, valueParameters2, trace, arrayList, inferenceSession);
    }

    private final KotlinType removeParameterNameAnnotation(KotlinType kotlinType) {
        AnnotationDescriptor annotationDescriptorMo6752findAnnotation;
        if ((kotlinType instanceof TypeUtils.SpecialType) || (annotationDescriptorMo6752findAnnotation = kotlinType.getAnnotations().mo6752findAnnotation(StandardNames.FqNames.parameterName)) == null) {
            return kotlinType;
        }
        Annotations.Companion companion = Annotations.INSTANCE;
        Annotations annotations = kotlinType.getAnnotations();
        ArrayList arrayList = new ArrayList();
        for (AnnotationDescriptor annotationDescriptor : annotations) {
            if (!Intrinsics.areEqual(annotationDescriptor, annotationDescriptorMo6752findAnnotation)) {
                arrayList.add(annotationDescriptor);
            }
        }
        return TypeUtilsKt.replaceAnnotations(kotlinType, companion.create(arrayList));
    }

    private final boolean functionTypeExpected(KotlinType kotlinType) {
        return !TypeUtils.noExpectedType(kotlinType) && FunctionTypesKt.isBuiltinFunctionalType(kotlinType);
    }

    private final KotlinType getReceiverType(KotlinType kotlinType) {
        if (functionTypeExpected(kotlinType)) {
            return FunctionTypesKt.getReceiverTypeFromFunctionType(kotlinType);
        }
        return null;
    }

    private final List<ContextReceiverTypeWithLabel> getContextReceiversTypes(KotlinType kotlinType) {
        if (functionTypeExpected(kotlinType)) {
            List<KotlinType> contextReceiverTypesFromFunctionType = FunctionTypesKt.getContextReceiverTypesFromFunctionType(kotlinType);
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(contextReceiverTypesFromFunctionType, 10));
            Iterator<T> it = contextReceiverTypesFromFunctionType.iterator();
            while (it.hasNext()) {
                arrayList.add(new ContextReceiverTypeWithLabel((KotlinType) it.next(), null));
            }
            return arrayList;
        }
        return CollectionsKt.emptyList();
    }

    private final List<ValueParameterDescriptor> getValueParameters(KotlinType kotlinType, FunctionDescriptor functionDescriptor) {
        if (functionTypeExpected(kotlinType)) {
            return FunctionTypeResolveUtilsKt.createValueParametersForInvokeInFunctionType(functionDescriptor, FunctionTypesKt.getValueParameterTypesFromFunctionType(kotlinType));
        }
        return null;
    }

    public final ClassConstructorDescriptorImpl resolvePrimaryConstructorDescriptor(LexicalScope scope, ClassDescriptor classDescriptor, KtPureClassOrObject classElement, BindingTrace trace, LanguageVersionSettings languageVersionSettings, InferenceSession inferenceSession) {
        Intrinsics.checkNotNullParameter(scope, "scope");
        Intrinsics.checkNotNullParameter(classDescriptor, "classDescriptor");
        Intrinsics.checkNotNullParameter(classElement, "classElement");
        Intrinsics.checkNotNullParameter(trace, "trace");
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        if (classDescriptor.getClassKind() == ClassKind.ENUM_ENTRY || !classElement.hasPrimaryConstructor()) {
            return null;
        }
        KtModifierList primaryConstructorModifierList = classElement.getPrimaryConstructorModifierList();
        KtPrimaryConstructor primaryConstructor = classElement.getPrimaryConstructor();
        KtPrimaryConstructor ktPrimaryConstructor = primaryConstructor == null ? classElement : primaryConstructor;
        List<KtParameter> primaryConstructorParameters = classElement.getPrimaryConstructorParameters();
        Intrinsics.checkNotNullExpressionValue(primaryConstructorParameters, "getPrimaryConstructorParameters(...)");
        return createConstructorDescriptor(scope, classDescriptor, true, primaryConstructorModifierList, ktPrimaryConstructor, primaryConstructorParameters, trace, languageVersionSettings, inferenceSession);
    }

    public final ClassConstructorDescriptorImpl resolveSecondaryConstructorDescriptor(LexicalScope scope, ClassDescriptor classDescriptor, KtSecondaryConstructor constructor, BindingTrace trace, LanguageVersionSettings languageVersionSettings, InferenceSession inferenceSession) {
        Intrinsics.checkNotNullParameter(scope, "scope");
        Intrinsics.checkNotNullParameter(classDescriptor, "classDescriptor");
        Intrinsics.checkNotNullParameter(constructor, "constructor");
        Intrinsics.checkNotNullParameter(trace, "trace");
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        return createConstructorDescriptor(scope, classDescriptor, false, constructor.getModifierList(), constructor, constructor.getValueParameters(), trace, languageVersionSettings, inferenceSession);
    }

    private final ClassConstructorDescriptorImpl createConstructorDescriptor(LexicalScope scope, ClassDescriptor classDescriptor, boolean isPrimary, KtModifierList modifierList, KtPureElement declarationToTrace, List<? extends KtParameter> valueParameters, BindingTrace trace, LanguageVersionSettings languageVersionSettings, InferenceSession inferenceSession) {
        ClassConstructorDescriptorImpl classConstructorDescriptorImplCreate = ClassConstructorDescriptorImpl.create(classDescriptor, this.annotationResolver.resolveAnnotationsWithoutArguments(scope, modifierList, trace), isPrimary, KotlinSourceElementKt.toSourceElement(declarationToTrace));
        Intrinsics.checkNotNullExpressionValue(classConstructorDescriptorImplCreate, "create(...)");
        classConstructorDescriptorImplCreate.setExpect(classDescriptor.get$isExpect());
        boolean z = true;
        if ((modifierList == null || !PsiUtilsKt.hasActualModifier(modifierList)) && (classDescriptor.getClassKind() != ClassKind.ANNOTATION_CLASS || !classDescriptor.getIsActual())) {
            z = false;
        }
        classConstructorDescriptorImplCreate.setActual(z);
        ClassConstructorDescriptorImpl classConstructorDescriptorImplInitialize = classConstructorDescriptorImplCreate.initialize(resolveValueParameters(classConstructorDescriptorImplCreate, new LexicalWritableScope(scope, classConstructorDescriptorImplCreate, false, new TraceBasedLocalRedeclarationChecker(trace, this.overloadChecker), LexicalScopeKind.CONSTRUCTOR_HEADER), valueParameters, trace, null, inferenceSession), ModifiersChecker.resolveVisibilityFromModifiers(modifierList, DescriptorUtils.getDefaultConstructorVisibility(classDescriptor, languageVersionSettings.supportsFeature(LanguageFeature.AllowSealedInheritorsInDifferentFilesOfSamePackage))));
        classConstructorDescriptorImplInitialize.setReturnType(classDescriptor.getDefaultType());
        if (DescriptorUtils.isAnnotationClass(classDescriptor)) {
            CompileTimeConstantUtils.checkConstructorParametersType(valueParameters, trace);
        }
        if (declarationToTrace instanceof PsiElement) {
            trace.record(BindingContext.CONSTRUCTOR, declarationToTrace, classConstructorDescriptorImplCreate);
        }
        Intrinsics.checkNotNull(classConstructorDescriptorImplInitialize);
        return classConstructorDescriptorImplInitialize;
    }

    private final List<ValueParameterDescriptor> resolveValueParameters(FunctionDescriptor functionDescriptor, LexicalWritableScope parameterScope, List<? extends KtParameter> valueParameters, BindingTrace trace, List<? extends KotlinType> expectedParameterTypes, InferenceSession inferenceSession) {
        ErrorType errorTypeCreateErrorType;
        ArrayList arrayList = new ArrayList();
        int size = valueParameters.size();
        int i = 0;
        while (i < size) {
            KtParameter ktParameter = valueParameters.get(i);
            KtTypeReference ktTypeReferenceMo7396getTypeReference = ktParameter.mo7396getTypeReference();
            SimpleType simpleType = null;
            if (expectedParameterTypes != null) {
                KotlinType kotlinType = i < expectedParameterTypes.size() ? expectedParameterTypes.get(i) : null;
                if (kotlinType != null && !TypeUtils.noExpectedType(kotlinType)) {
                    simpleType = kotlinType;
                }
            }
            if (ktTypeReferenceMo7396getTypeReference != null) {
                errorTypeCreateErrorType = this.typeResolver.resolveType(parameterScope, ktTypeReferenceMo7396getTypeReference, trace, true);
                if (simpleType != null && !KotlinTypeChecker.DEFAULT.isSubtypeOf(simpleType, errorTypeCreateErrorType)) {
                    trace.report(Errors.EXPECTED_PARAMETER_TYPE_MISMATCH.on(ktParameter, simpleType));
                }
            } else {
                FunctionDescriptor functionDescriptor2 = functionDescriptor;
                if (ExpressionTypingUtils.isFunctionLiteral(functionDescriptor2) || ExpressionTypingUtils.isFunctionExpression(functionDescriptor2)) {
                    boolean zContains = TypeUtils.contains(simpleType, new Function1<UnwrappedType, Boolean>() { // from class: org.jetbrains.kotlin.resolve.FunctionDescriptorResolver$resolveValueParameters$containsErrorType$1
                        @Override // kotlin.jvm.functions.Function1
                        public final Boolean invoke(UnwrappedType unwrappedType) {
                            Intrinsics.checkNotNull(unwrappedType);
                            return Boolean.valueOf(KotlinTypeKt.isError(unwrappedType));
                        }
                    });
                    if (simpleType == null || zContains) {
                        trace.report(Errors.CANNOT_INFER_PARAMETER_TYPE.on(ktParameter));
                    }
                    if (simpleType == null) {
                        simpleType = TypeUtils.CANNOT_INFER_FUNCTION_PARAM_TYPE;
                    }
                    Intrinsics.checkNotNull(simpleType);
                    errorTypeCreateErrorType = simpleType;
                } else {
                    trace.report(Errors.VALUE_PARAMETER_WITH_NO_TYPE_ANNOTATION.on(ktParameter));
                    ErrorTypeKind errorTypeKind = ErrorTypeKind.MISSED_TYPE_FOR_PARAMETER;
                    String string = ktParameter.getNameAsSafeName().toString();
                    Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
                    errorTypeCreateErrorType = ErrorUtils.createErrorType(errorTypeKind, string);
                }
            }
            KotlinType kotlinType2 = errorTypeCreateErrorType;
            boolean z = functionDescriptor instanceof ConstructorDescriptor;
            if (!z || !((ConstructorDescriptor) functionDescriptor).isPrimary()) {
                this.modifiersChecker.withTrace(trace).checkParameterHasNoValOrVar(ktParameter, z ? Errors.VAL_OR_VAR_ON_SECONDARY_CONSTRUCTOR_PARAMETER : Errors.VAL_OR_VAR_ON_FUN_PARAMETER);
            }
            LexicalWritableScope lexicalWritableScope = parameterScope;
            ValueParameterDescriptorImpl valueParameterDescriptorImplResolveValueParameterDescriptor = this.descriptorResolver.resolveValueParameterDescriptor(lexicalWritableScope, functionDescriptor, ktParameter, i, kotlinType2, trace, Annotations.INSTANCE.getEMPTY(), inferenceSession);
            Intrinsics.checkNotNullExpressionValue(valueParameterDescriptorImplResolveValueParameterDescriptor, "resolveValueParameterDescriptor(...)");
            ValueParameterDescriptorImpl valueParameterDescriptorImpl = valueParameterDescriptorImplResolveValueParameterDescriptor;
            ExpressionTypingUtils.checkVariableShadowing(lexicalWritableScope, trace, valueParameterDescriptorImpl);
            parameterScope.addVariableDescriptor(valueParameterDescriptorImpl);
            arrayList.add(valueParameterDescriptorImplResolveValueParameterDescriptor);
            i++;
        }
        return arrayList;
    }

    /* JADX INFO: compiled from: FunctionDescriptorResolver.kt */
    @Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0082\b\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\u0002\u0010\u0006J\t\u0010\u000b\u001a\u00020\u0003HÆ\u0003J\u000b\u0010\f\u001a\u0004\u0018\u00010\u0005HÆ\u0003J\u001f\u0010\r\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005HÆ\u0001J\u0013\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0011\u001a\u00020\u0012HÖ\u0001J\t\u0010\u0013\u001a\u00020\u0014HÖ\u0001R\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/resolve/FunctionDescriptorResolver$ContextReceiverTypeWithLabel;", "", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/types/KotlinType;", CoroutineCodegenUtilKt.COROUTINE_LABEL_FIELD_NAME, "Lorg/jetbrains/kotlin/name/Name;", "(Lorg/jetbrains/kotlin/types/KotlinType;Lorg/jetbrains/kotlin/name/Name;)V", "getLabel", "()Lorg/jetbrains/kotlin/name/Name;", "getType", "()Lorg/jetbrains/kotlin/types/KotlinType;", "component1", "component2", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final /* data */ class ContextReceiverTypeWithLabel {
        private final Name label;
        private final KotlinType type;

        public static /* synthetic */ ContextReceiverTypeWithLabel copy$default(ContextReceiverTypeWithLabel contextReceiverTypeWithLabel, KotlinType kotlinType, Name name2, int i, Object obj) {
            if ((i & 1) != 0) {
                kotlinType = contextReceiverTypeWithLabel.type;
            }
            if ((i & 2) != 0) {
                name2 = contextReceiverTypeWithLabel.label;
            }
            return contextReceiverTypeWithLabel.copy(kotlinType, name2);
        }

        /* JADX INFO: renamed from: component1, reason: from getter */
        public final KotlinType getType() {
            return this.type;
        }

        /* JADX INFO: renamed from: component2, reason: from getter */
        public final Name getLabel() {
            return this.label;
        }

        public final ContextReceiverTypeWithLabel copy(KotlinType type, Name label) {
            Intrinsics.checkNotNullParameter(type, "type");
            return new ContextReceiverTypeWithLabel(type, label);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof ContextReceiverTypeWithLabel)) {
                return false;
            }
            ContextReceiverTypeWithLabel contextReceiverTypeWithLabel = (ContextReceiverTypeWithLabel) other;
            return Intrinsics.areEqual(this.type, contextReceiverTypeWithLabel.type) && Intrinsics.areEqual(this.label, contextReceiverTypeWithLabel.label);
        }

        public int hashCode() {
            int iHashCode = this.type.hashCode() * 31;
            Name name2 = this.label;
            return iHashCode + (name2 == null ? 0 : name2.hashCode());
        }

        public String toString() {
            return "ContextReceiverTypeWithLabel(type=" + this.type + ", label=" + this.label + ')';
        }

        public ContextReceiverTypeWithLabel(KotlinType type, Name name2) {
            Intrinsics.checkNotNullParameter(type, "type");
            this.type = type;
            this.label = name2;
        }

        public final Name getLabel() {
            return this.label;
        }

        public final KotlinType getType() {
            return this.type;
        }
    }
}

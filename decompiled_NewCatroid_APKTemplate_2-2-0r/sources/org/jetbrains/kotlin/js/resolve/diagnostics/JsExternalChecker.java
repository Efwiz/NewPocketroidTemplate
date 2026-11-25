package org.jetbrains.kotlin.js.resolve.diagnostics;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PropertyReference1;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;
import org.jetbrains.kotlin.builtins.FunctionTypesKt;
import org.jetbrains.kotlin.builtins.StandardNames;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.MemberDescriptor;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.descriptors.PackageFragmentDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyAccessorDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.SourceElement;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.diagnostics.DiagnosticFactory0;
import org.jetbrains.kotlin.diagnostics.DiagnosticSink;
import org.jetbrains.kotlin.js.PredefinedAnnotation;
import org.jetbrains.kotlin.js.translate.utils.AnnotationsUtils;
import org.jetbrains.kotlin.lexer.KtTokens;
import org.jetbrains.kotlin.name.CallableId;
import org.jetbrains.kotlin.name.FqNameUnsafe;
import org.jetbrains.kotlin.name.JsStandardClassIds;
import org.jetbrains.kotlin.platform.TargetPlatform;
import org.jetbrains.kotlin.platform.WasmPlatformKt;
import org.jetbrains.kotlin.psi.KtAnonymousInitializer;
import org.jetbrains.kotlin.psi.KtBlockExpression;
import org.jetbrains.kotlin.psi.KtCallableDeclaration;
import org.jetbrains.kotlin.psi.KtClassBody;
import org.jetbrains.kotlin.psi.KtClassOrObject;
import org.jetbrains.kotlin.psi.KtConstructorDelegationCall;
import org.jetbrains.kotlin.psi.KtDeclaration;
import org.jetbrains.kotlin.psi.KtDeclarationWithBody;
import org.jetbrains.kotlin.psi.KtDeclarationWithInitializer;
import org.jetbrains.kotlin.psi.KtDelegatedSuperTypeEntry;
import org.jetbrains.kotlin.psi.KtElement;
import org.jetbrains.kotlin.psi.KtEnumEntry;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtParameter;
import org.jetbrains.kotlin.psi.KtProperty;
import org.jetbrains.kotlin.psi.KtPropertyAccessor;
import org.jetbrains.kotlin.psi.KtPropertyDelegate;
import org.jetbrains.kotlin.psi.KtSecondaryConstructor;
import org.jetbrains.kotlin.psi.KtSuperTypeCallEntry;
import org.jetbrains.kotlin.psi.KtSuperTypeListEntry;
import org.jetbrains.kotlin.psi.KtValueArgumentList;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.BindingTrace;
import org.jetbrains.kotlin.resolve.DescriptorUtils;
import org.jetbrains.kotlin.resolve.InlineClassesUtilsKt;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.resolve.calls.util.CallUtilKt;
import org.jetbrains.kotlin.resolve.checkers.DeclarationChecker;
import org.jetbrains.kotlin.resolve.checkers.DeclarationCheckerContext;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.resolve.source.PsiSourceElementKt;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.TypeUtils;

/* JADX INFO: compiled from: JsExternalChecker.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000^\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J \u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0016J\u0018\u0010\u0010\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\u0011\u001a\u00020\u0012H\u0002J(\u0010\u0013\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0014\u001a\u00020\u0015H\u0002J \u0010\u0016\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u0011\u001a\u00020\u0012H\u0002J \u0010\u0017\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u0011\u001a\u00020\u0012H\u0002J\u0018\u0010\u0018\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\u0011\u001a\u00020\u0012H\u0002J\u0018\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH\u0002J\u0010\u0010\u001b\u001a\u00020\u001a2\u0006\u0010\f\u001a\u00020\rH\u0002J\u0014\u0010\u001c\u001a\u00020\u001a*\u00020\u001d2\u0006\u0010\u0014\u001a\u00020\u0015H\u0002J\u0014\u0010\u001e\u001a\u00020\u001a*\u00020\u001f2\u0006\u0010\u0014\u001a\u00020\u0015H\u0002J\f\u0010 \u001a\u00020\u001a*\u00020!H\u0002J\f\u0010\"\u001a\u00020\u001a*\u00020!H\u0002R\u0017\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007¨\u0006#"}, d2 = {"Lorg/jetbrains/kotlin/js/resolve/diagnostics/JsExternalChecker;", "Lorg/jetbrains/kotlin/resolve/checkers/DeclarationChecker;", "()V", "DEFINED_EXTERNALLY_PROPERTY_NAMES", "", "Lorg/jetbrains/kotlin/name/FqNameUnsafe;", "getDEFINED_EXTERNALLY_PROPERTY_NAMES", "()Ljava/util/List;", "check", "", "declaration", "Lorg/jetbrains/kotlin/psi/KtDeclaration;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "context", "Lorg/jetbrains/kotlin/resolve/checkers/DeclarationCheckerContext;", "checkAnonymousInitializer", "diagnosticHolder", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticSink;", "checkBody", "bindingContext", "Lorg/jetbrains/kotlin/resolve/BindingContext;", "checkConstructorPropertyParam", "checkDelegation", "checkEnumEntry", "isDirectlyExternal", "", "isPrivateMemberOfExternalClass", "hasValidExternalBody", "Lorg/jetbrains/kotlin/psi/KtDeclarationWithBody;", "isDefinedExternallyExpression", "Lorg/jetbrains/kotlin/psi/KtExpression;", "isNonAbstractMemberOfInterface", "Lorg/jetbrains/kotlin/descriptors/CallableMemberDescriptor;", "isNullableProperty", "js.frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JsExternalChecker implements DeclarationChecker {
    private static final List<FqNameUnsafe> DEFINED_EXTERNALLY_PROPERTY_NAMES;
    public static final JsExternalChecker INSTANCE = new JsExternalChecker();

    private JsExternalChecker() {
    }

    static {
        Set<CallableId> set = JsStandardClassIds.Callables.definedExternallyPropertyNames;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(set, 10));
        Iterator<T> it = set.iterator();
        while (it.hasNext()) {
            arrayList.add(((CallableId) it.next()).asSingleFqName().toUnsafe());
        }
        DEFINED_EXTERNALLY_PROPERTY_NAMES = arrayList;
    }

    public final List<FqNameUnsafe> getDEFINED_EXTERNALLY_PROPERTY_NAMES() {
        return DEFINED_EXTERNALLY_PROPERTY_NAMES;
    }

    @Override // org.jetbrains.kotlin.resolve.checkers.DeclarationChecker
    public void check(KtDeclaration declaration, DeclarationDescriptor descriptor, DeclarationCheckerContext context) {
        DiagnosticFactory0<KtElement> diagnosticFactory0;
        String str;
        String str2;
        TargetPlatform platform;
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(context, "context");
        if (AnnotationsUtils.isNativeObject(descriptor)) {
            BindingTrace trace = context.getTrace();
            if (!DescriptorUtils.isTopLevelDeclaration(descriptor) && isDirectlyExternal(declaration, descriptor) && !(descriptor instanceof PropertyAccessorDescriptor)) {
                trace.report(ErrorsJs.NESTED_EXTERNAL_DECLARATION.on(declaration));
            }
            boolean z = descriptor instanceof ClassDescriptor;
            if (z) {
                ClassDescriptor classDescriptor = (ClassDescriptor) descriptor;
                if (classDescriptor.isData()) {
                    str2 = "data class";
                } else if (classDescriptor.getIsInner()) {
                    str2 = "inner class";
                } else if (classDescriptor.isInline()) {
                    str2 = "inline class";
                } else if (classDescriptor.isValue()) {
                    str2 = "value class";
                } else if (classDescriptor.isFun()) {
                    str2 = "fun interface";
                } else {
                    str2 = DescriptorUtils.isAnnotationClass(descriptor) ? "annotation class" : null;
                }
                if (str2 != null) {
                    trace.report(ErrorsJs.WRONG_EXTERNAL_DECLARATION.on(declaration, str2));
                }
                if (DescriptorUtils.isEnumClass(descriptor) && ((platform = context.getModuleDescriptor().getPlatform()) == null || !WasmPlatformKt.isWasm(platform))) {
                    trace.report(ErrorsJs.ENUM_CLASS_IN_EXTERNAL_DECLARATION_WARNING.on(declaration));
                }
            }
            boolean z2 = descriptor instanceof PropertyAccessorDescriptor;
            if (z2 && isDirectlyExternal(declaration, descriptor)) {
                trace.report(ErrorsJs.WRONG_EXTERNAL_DECLARATION.on(declaration, "property accessor"));
            } else if (isPrivateMemberOfExternalClass(descriptor)) {
                trace.report(ErrorsJs.WRONG_EXTERNAL_DECLARATION.on(declaration, "private member of class"));
            }
            if (z) {
                ClassDescriptor classDescriptor2 = (ClassDescriptor) descriptor;
                if (classDescriptor2.getKind() != ClassKind.INTERFACE) {
                    DeclarationDescriptor containingDeclaration = classDescriptor2.getContainingDeclaration();
                    if ((containingDeclaration instanceof ClassDescriptor) && ((ClassDescriptor) containingDeclaration).getKind() == ClassKind.INTERFACE) {
                        trace.report(ErrorsJs.NESTED_CLASS_IN_EXTERNAL_INTERFACE.on(declaration));
                    }
                }
            }
            if (!z2 && DescriptorUtilsKt.isExtension(descriptor)) {
                if (descriptor instanceof FunctionDescriptor) {
                    str = "extension function";
                } else {
                    str = descriptor instanceof PropertyDescriptor ? "extension property" : "extension member";
                }
                trace.report(ErrorsJs.WRONG_EXTERNAL_DECLARATION.on(declaration, str));
            }
            if (z) {
                ClassDescriptor classDescriptor3 = (ClassDescriptor) descriptor;
                if (classDescriptor3.getKind() != ClassKind.ANNOTATION_CLASS) {
                    Set mutableSet = CollectionsKt.toMutableSet(CollectionsKt.plus((Collection) CollectionsKt.listOfNotNull(DescriptorUtilsKt.getSuperClassNotAny(classDescriptor3)), (Iterable) DescriptorUtilsKt.getSuperInterfaces(classDescriptor3)));
                    if (classDescriptor3.getKind() == ClassKind.ENUM_CLASS || classDescriptor3.getKind() == ClassKind.ENUM_ENTRY) {
                        CollectionsKt.removeAll(mutableSet, new Function1<ClassDescriptor, Boolean>() { // from class: org.jetbrains.kotlin.js.resolve.diagnostics.JsExternalChecker.check.2
                            @Override // kotlin.jvm.functions.Function1
                            public final Boolean invoke(ClassDescriptor it) {
                                Intrinsics.checkNotNullParameter(it, "it");
                                return Boolean.valueOf(Intrinsics.areEqual(DescriptorUtilsKt.getFqNameUnsafe(it), StandardNames.FqNames._enum));
                            }
                        });
                    }
                    Set<ClassDescriptor> set = mutableSet;
                    if (!(set instanceof Collection) || !set.isEmpty()) {
                        for (ClassDescriptor classDescriptor4 : set) {
                            if (!AnnotationsUtils.isNativeObject(classDescriptor4) && !Intrinsics.areEqual(DescriptorUtilsKt.getFqNameSafe(classDescriptor4), StandardNames.FqNames.throwable)) {
                                trace.report(ErrorsJs.EXTERNAL_TYPE_EXTENDS_NON_EXTERNAL_TYPE.on(declaration));
                                break;
                            }
                        }
                    }
                }
            }
            if ((descriptor instanceof FunctionDescriptor) && ((FunctionDescriptor) descriptor).isInline()) {
                trace.report(ErrorsJs.INLINE_EXTERNAL_DECLARATION.on(declaration));
            }
            if (context.getLanguageVersionSettings().supportsFeature(LanguageFeature.JsAllowValueClassesInExternals)) {
                diagnosticFactory0 = ErrorsJs.INLINE_CLASS_IN_EXTERNAL_DECLARATION_WARNING;
            } else {
                diagnosticFactory0 = ErrorsJs.INLINE_CLASS_IN_EXTERNAL_DECLARATION;
            }
            Intrinsics.checkNotNull(diagnosticFactory0);
            check$reportOnParametersAndReturnTypesIf(descriptor, declaration, trace, diagnosticFactory0, AnonymousClass4.INSTANCE);
            if (!context.getLanguageVersionSettings().supportsFeature(LanguageFeature.JsEnableExtensionFunctionInExternals)) {
                DiagnosticFactory0<KtElement> EXTENSION_FUNCTION_IN_EXTERNAL_DECLARATION = ErrorsJs.EXTENSION_FUNCTION_IN_EXTERNAL_DECLARATION;
                Intrinsics.checkNotNullExpressionValue(EXTENSION_FUNCTION_IN_EXTERNAL_DECLARATION, "EXTENSION_FUNCTION_IN_EXTERNAL_DECLARATION");
                check$reportOnParametersAndReturnTypesIf(descriptor, declaration, trace, EXTENSION_FUNCTION_IN_EXTERNAL_DECLARATION, new PropertyReference1() { // from class: org.jetbrains.kotlin.js.resolve.diagnostics.JsExternalChecker.check.5
                    @Override // kotlin.jvm.internal.CallableReference
                    public String getSignature() {
                        return "isExtensionFunctionType(Lorg/jetbrains/kotlin/types/KotlinType;)Z";
                    }

                    @Override // kotlin.reflect.KProperty1
                    public Object get(Object obj) {
                        return Boolean.valueOf(FunctionTypesKt.isExtensionFunctionType((KotlinType) obj));
                    }

                    @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
                    public String getName() {
                        return "isExtensionFunctionType";
                    }

                    @Override // kotlin.jvm.internal.CallableReference
                    public KDeclarationContainer getOwner() {
                        return Reflection.getOrCreateKotlinPackage(FunctionTypesKt.class, "js.frontend");
                    }
                });
            }
            if (descriptor instanceof CallableMemberDescriptor) {
                CallableMemberDescriptor callableMemberDescriptor = (CallableMemberDescriptor) descriptor;
                if (isNonAbstractMemberOfInterface(callableMemberDescriptor) && !isNullableProperty(callableMemberDescriptor)) {
                    trace.report(ErrorsJs.NON_ABSTRACT_MEMBER_OF_EXTERNAL_INTERFACE.on(declaration));
                }
            }
            BindingTrace bindingTrace = trace;
            BindingContext context2 = trace.getContext();
            Intrinsics.checkNotNullExpressionValue(context2, "getBindingContext(...)");
            checkBody(declaration, descriptor, bindingTrace, context2);
            checkDelegation(declaration, descriptor, bindingTrace);
            checkAnonymousInitializer(declaration, bindingTrace);
            checkEnumEntry(declaration, bindingTrace);
            checkConstructorPropertyParam(declaration, descriptor, bindingTrace);
        }
    }

    private static final void check$reportOnParametersAndReturnTypesIf(DeclarationDescriptor declarationDescriptor, KtDeclaration ktDeclaration, BindingTrace bindingTrace, DiagnosticFactory0<KtElement> diagnosticFactory0, Function1<? super KotlinType, Boolean> function1) {
        KtDeclaration returnTypeReference;
        if (declarationDescriptor instanceof CallableMemberDescriptor) {
            if ((declarationDescriptor instanceof PropertyAccessorDescriptor) && ((PropertyAccessorDescriptor) declarationDescriptor).isDefault()) {
                return;
            }
            CallableMemberDescriptor callableMemberDescriptor = (CallableMemberDescriptor) declarationDescriptor;
            for (ValueParameterDescriptor valueParameterDescriptor : callableMemberDescriptor.getValueParameters()) {
                SourceElement source = valueParameterDescriptor.getSourceElement();
                Intrinsics.checkNotNullExpressionValue(source, "getSource(...)");
                PsiElement psi = PsiSourceElementKt.getPsi(source);
                KtParameter ktParameter = psi instanceof KtParameter ? (KtParameter) psi : null;
                KtParameter ktParameter2 = ktParameter != null ? ktParameter : ktDeclaration;
                KotlinType varargElementType = valueParameterDescriptor.getVarargElementType();
                if (varargElementType == null) {
                    varargElementType = valueParameterDescriptor.getType();
                    Intrinsics.checkNotNullExpressionValue(varargElementType, "getType(...)");
                }
                check$reportOnParametersAndReturnTypesIf$checkTypeIsNotInlineClass(function1, bindingTrace, diagnosticFactory0, varargElementType, ktParameter2);
            }
            if (ktDeclaration instanceof KtCallableDeclaration) {
                returnTypeReference = ((KtCallableDeclaration) ktDeclaration).mo7396getTypeReference();
            } else {
                returnTypeReference = ktDeclaration instanceof KtPropertyAccessor ? ((KtPropertyAccessor) ktDeclaration).getReturnTypeReference() : ktDeclaration;
            }
            if (returnTypeReference != null) {
                KotlinType returnType = callableMemberDescriptor.getReturnType();
                Intrinsics.checkNotNull(returnType);
                check$reportOnParametersAndReturnTypesIf$checkTypeIsNotInlineClass(function1, bindingTrace, diagnosticFactory0, returnType, returnTypeReference);
            }
        }
    }

    private static final void check$reportOnParametersAndReturnTypesIf$checkTypeIsNotInlineClass(Function1<? super KotlinType, Boolean> function1, BindingTrace bindingTrace, DiagnosticFactory0<KtElement> diagnosticFactory0, KotlinType kotlinType, KtElement ktElement) {
        if (function1.invoke(kotlinType).booleanValue()) {
            bindingTrace.report(diagnosticFactory0.on(ktElement));
        }
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.js.resolve.diagnostics.JsExternalChecker$check$4, reason: invalid class name */
    /* JADX INFO: compiled from: JsExternalChecker.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    /* synthetic */ class AnonymousClass4 extends FunctionReference implements Function1<KotlinType, Boolean> {
        public static final AnonymousClass4 INSTANCE = new AnonymousClass4();

        AnonymousClass4() {
            super(1);
        }

        @Override // kotlin.jvm.internal.CallableReference
        public final String getSignature() {
            return "isInlineClassType(Lorg/jetbrains/kotlin/types/KotlinType;)Z";
        }

        @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
        public final String getName() {
            return "isInlineClassType";
        }

        @Override // kotlin.jvm.internal.CallableReference
        public final KDeclarationContainer getOwner() {
            return Reflection.getOrCreateKotlinPackage(InlineClassesUtilsKt.class, "js.frontend");
        }

        @Override // kotlin.jvm.functions.Function1
        public final Boolean invoke(KotlinType p0) {
            Intrinsics.checkNotNullParameter(p0, "p0");
            return Boolean.valueOf(InlineClassesUtilsKt.isInlineClassType(p0));
        }
    }

    /* JADX WARN: Code duplicated, block: B:12:0x002b  */
    private final void checkBody(KtDeclaration declaration, DeclarationDescriptor descriptor, DiagnosticSink diagnosticHolder, BindingContext bindingContext) {
        KtDeclarationWithInitializer ktDeclarationWithInitializer;
        KtExpression initializer;
        if ((declaration instanceof KtProperty) && (descriptor instanceof PropertyAccessorDescriptor)) {
            return;
        }
        if (declaration instanceof KtDeclarationWithBody) {
            KtDeclarationWithBody ktDeclarationWithBody = (KtDeclarationWithBody) declaration;
            if (!hasValidExternalBody(ktDeclarationWithBody, bindingContext)) {
                DiagnosticFactory0<KtExpression> diagnosticFactory0 = ErrorsJs.WRONG_BODY_OF_EXTERNAL_DECLARATION;
                KtExpression bodyExpression = ktDeclarationWithBody.getBodyExpression();
                Intrinsics.checkNotNull(bodyExpression);
                diagnosticHolder.report(diagnosticFactory0.on(bodyExpression));
            } else if ((declaration instanceof KtDeclarationWithInitializer) && (initializer = (ktDeclarationWithInitializer = (KtDeclarationWithInitializer) declaration).getInitializer()) != null && !isDefinedExternallyExpression(initializer, bindingContext)) {
                DiagnosticFactory0<KtExpression> diagnosticFactory1 = ErrorsJs.WRONG_INITIALIZER_OF_EXTERNAL_DECLARATION;
                KtExpression initializer2 = ktDeclarationWithInitializer.getInitializer();
                Intrinsics.checkNotNull(initializer2);
                diagnosticHolder.report(diagnosticFactory1.on(initializer2));
            }
        } else if (declaration instanceof KtDeclarationWithInitializer) {
            DiagnosticFactory0<KtExpression> diagnosticFactory2 = ErrorsJs.WRONG_INITIALIZER_OF_EXTERNAL_DECLARATION;
            KtExpression initializer3 = ktDeclarationWithInitializer.getInitializer();
            Intrinsics.checkNotNull(initializer3);
            diagnosticHolder.report(diagnosticFactory2.on(initializer3));
        }
        if (declaration instanceof KtCallableDeclaration) {
            List<KtParameter> valueParameters = ((KtCallableDeclaration) declaration).getValueParameters();
            Intrinsics.checkNotNullExpressionValue(valueParameters, "getValueParameters(...)");
            ArrayList<KtExpression> arrayList = new ArrayList();
            Iterator<T> it = valueParameters.iterator();
            while (it.hasNext()) {
                KtExpression defaultValue = ((KtParameter) it.next()).getDefaultValue();
                if (defaultValue != null) {
                    arrayList.add(defaultValue);
                }
            }
            for (KtExpression ktExpression : arrayList) {
                Intrinsics.checkNotNull(ktExpression);
                if (!isDefinedExternallyExpression(ktExpression, bindingContext)) {
                    diagnosticHolder.report(ErrorsJs.WRONG_DEFAULT_VALUE_FOR_EXTERNAL_FUN_PARAMETER.on(ktExpression));
                }
            }
        }
    }

    private final void checkDelegation(KtDeclaration declaration, DeclarationDescriptor descriptor, DiagnosticSink diagnosticHolder) {
        KtPropertyDelegate delegate;
        if ((descriptor instanceof MemberDescriptor) && DescriptorUtilsKt.isEffectivelyExternal((MemberDescriptor) descriptor)) {
            if (declaration instanceof KtClassOrObject) {
                for (KtSuperTypeListEntry ktSuperTypeListEntry : ((KtClassOrObject) declaration).getSuperTypeListEntries()) {
                    if (ktSuperTypeListEntry instanceof KtSuperTypeCallEntry) {
                        DiagnosticFactory0<KtElement> diagnosticFactory0 = ErrorsJs.EXTERNAL_DELEGATED_CONSTRUCTOR_CALL;
                        KtValueArgumentList valueArgumentList = ((KtSuperTypeCallEntry) ktSuperTypeListEntry).getValueArgumentList();
                        Intrinsics.checkNotNull(valueArgumentList);
                        diagnosticHolder.report(diagnosticFactory0.on(valueArgumentList));
                    } else if (ktSuperTypeListEntry instanceof KtDelegatedSuperTypeEntry) {
                        diagnosticHolder.report(ErrorsJs.EXTERNAL_DELEGATION.on(ktSuperTypeListEntry));
                    }
                }
                return;
            }
            if (declaration instanceof KtSecondaryConstructor) {
                KtConstructorDelegationCall delegationCall = ((KtSecondaryConstructor) declaration).getDelegationCall();
                if (delegationCall.isImplicit()) {
                    return;
                }
                diagnosticHolder.report(ErrorsJs.EXTERNAL_DELEGATED_CONSTRUCTOR_CALL.on(delegationCall));
                return;
            }
            if (!(declaration instanceof KtProperty) || (descriptor instanceof PropertyAccessorDescriptor) || (delegate = ((KtProperty) declaration).getDelegate()) == null) {
                return;
            }
            diagnosticHolder.report(ErrorsJs.EXTERNAL_DELEGATION.on(delegate));
        }
    }

    private final void checkAnonymousInitializer(KtDeclaration declaration, DiagnosticSink diagnosticHolder) {
        if (declaration instanceof KtClassOrObject) {
            Iterator<KtAnonymousInitializer> it = ((KtClassOrObject) declaration).getAnonymousInitializers().iterator();
            while (it.hasNext()) {
                diagnosticHolder.report(ErrorsJs.EXTERNAL_ANONYMOUS_INITIALIZER.on(it.next()));
            }
        }
    }

    private final void checkEnumEntry(KtDeclaration declaration, DiagnosticSink diagnosticHolder) {
        KtClassBody body;
        if ((declaration instanceof KtEnumEntry) && (body = ((KtEnumEntry) declaration).getBody()) != null) {
            diagnosticHolder.report(ErrorsJs.EXTERNAL_ENUM_ENTRY_WITH_BODY.on(body));
        }
    }

    private final void checkConstructorPropertyParam(KtDeclaration declaration, DeclarationDescriptor descriptor, DiagnosticSink diagnosticHolder) {
        if ((descriptor instanceof PropertyDescriptor) && (declaration instanceof KtParameter)) {
            DeclarationDescriptor containingDeclaration = ((PropertyDescriptor) descriptor).getContainingDeclaration();
            Intrinsics.checkNotNull(containingDeclaration, "null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.ClassDescriptor");
            ClassDescriptor classDescriptor = (ClassDescriptor) containingDeclaration;
            if (classDescriptor.isData() || DescriptorUtils.isAnnotationClass(classDescriptor)) {
                return;
            }
            diagnosticHolder.report(ErrorsJs.EXTERNAL_CLASS_CONSTRUCTOR_PROPERTY_PARAMETER.on(declaration));
        }
    }

    private final boolean isDirectlyExternal(KtDeclaration declaration, DeclarationDescriptor descriptor) {
        if ((declaration instanceof KtProperty) && (descriptor instanceof PropertyAccessorDescriptor)) {
            return false;
        }
        return declaration.hasModifier(KtTokens.EXTERNAL_KEYWORD) || AnnotationsUtils.hasAnnotation(descriptor, PredefinedAnnotation.NATIVE);
    }

    private final boolean isPrivateMemberOfExternalClass(DeclarationDescriptor descriptor) {
        if (descriptor instanceof PropertyAccessorDescriptor) {
            PropertyAccessorDescriptor propertyAccessorDescriptor = (PropertyAccessorDescriptor) descriptor;
            if (Intrinsics.areEqual(propertyAccessorDescriptor.getVisibilityImpl(), propertyAccessorDescriptor.getCorrespondingProperty().getVisibilityImpl())) {
                return false;
            }
        }
        if (descriptor instanceof MemberDescriptor) {
            MemberDescriptor memberDescriptor = (MemberDescriptor) descriptor;
            if (Intrinsics.areEqual(memberDescriptor.getVisibilityImpl(), DescriptorVisibilities.PRIVATE)) {
                DeclarationDescriptor containingDeclaration = memberDescriptor.getContainingDeclaration();
                ClassDescriptor classDescriptor = containingDeclaration instanceof ClassDescriptor ? (ClassDescriptor) containingDeclaration : null;
                if (classDescriptor == null) {
                    return false;
                }
                return AnnotationsUtils.isNativeObject(classDescriptor);
            }
        }
        return false;
    }

    private final boolean isNonAbstractMemberOfInterface(CallableMemberDescriptor callableMemberDescriptor) {
        return (callableMemberDescriptor.getModality() == Modality.ABSTRACT || !DescriptorUtils.isInterface(callableMemberDescriptor.getContainingDeclaration()) || (callableMemberDescriptor instanceof PropertyAccessorDescriptor)) ? false : true;
    }

    private final boolean isNullableProperty(CallableMemberDescriptor callableMemberDescriptor) {
        return (callableMemberDescriptor instanceof PropertyDescriptor) && TypeUtils.isNullableType(((PropertyDescriptor) callableMemberDescriptor).getType());
    }

    private final boolean hasValidExternalBody(KtDeclarationWithBody ktDeclarationWithBody, BindingContext bindingContext) {
        if (!ktDeclarationWithBody.hasBody()) {
            return true;
        }
        KtExpression bodyExpression = ktDeclarationWithBody.getBodyExpression();
        Intrinsics.checkNotNull(bodyExpression);
        if (!ktDeclarationWithBody.hasBlockBody()) {
            return isDefinedExternallyExpression(bodyExpression, bindingContext);
        }
        if (!(bodyExpression instanceof KtBlockExpression)) {
            return false;
        }
        List<KtExpression> statements = ((KtBlockExpression) bodyExpression).getStatements();
        Intrinsics.checkNotNullExpressionValue(statements, "getStatements(...)");
        KtExpression ktExpression = (KtExpression) CollectionsKt.singleOrNull((List) statements);
        if (ktExpression == null) {
            return false;
        }
        return isDefinedExternallyExpression(ktExpression, bindingContext);
    }

    private final boolean isDefinedExternallyExpression(KtExpression ktExpression, BindingContext bindingContext) {
        ResolvedCall<? extends CallableDescriptor> resolvedCall = CallUtilKt.getResolvedCall(ktExpression, bindingContext);
        CallableDescriptor resultingDescriptor = resolvedCall != null ? resolvedCall.getResultingDescriptor() : null;
        PropertyDescriptor propertyDescriptor = resultingDescriptor instanceof PropertyDescriptor ? (PropertyDescriptor) resultingDescriptor : null;
        if (propertyDescriptor == null) {
            return false;
        }
        DeclarationDescriptor containingDeclaration = propertyDescriptor.getContainingDeclaration();
        PackageFragmentDescriptor packageFragmentDescriptor = containingDeclaration instanceof PackageFragmentDescriptor ? (PackageFragmentDescriptor) containingDeclaration : null;
        if (packageFragmentDescriptor == null) {
            return false;
        }
        List<FqNameUnsafe> list = DEFINED_EXTERNALLY_PROPERTY_NAMES;
        if ((list instanceof Collection) && list.isEmpty()) {
            return false;
        }
        for (FqNameUnsafe fqNameUnsafe : list) {
            if (Intrinsics.areEqual(DescriptorUtilsKt.getFqNameUnsafe(packageFragmentDescriptor), fqNameUnsafe.parent()) && Intrinsics.areEqual(propertyDescriptor.getShortName(), fqNameUnsafe.shortName())) {
                return true;
            }
        }
        return false;
    }
}

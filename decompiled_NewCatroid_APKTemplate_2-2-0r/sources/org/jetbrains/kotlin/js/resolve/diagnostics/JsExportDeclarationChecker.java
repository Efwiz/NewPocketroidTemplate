package org.jetbrains.kotlin.js.resolve.diagnostics;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.FunctionTypesKt;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.ConstructorDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.DescriptorUtilKt;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.MemberDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyAccessorDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.AnnotationDescriptor;
import org.jetbrains.kotlin.js.common.IdentifierPolicyKt;
import org.jetbrains.kotlin.js.naming.NameSuggestion;
import org.jetbrains.kotlin.js.translate.utils.AnnotationsUtils;
import org.jetbrains.kotlin.psi.KtAnnotationEntry;
import org.jetbrains.kotlin.psi.KtDeclaration;
import org.jetbrains.kotlin.psi.KtNamedDeclaration;
import org.jetbrains.kotlin.psi.KtParameter;
import org.jetbrains.kotlin.psi.KtValueArgument;
import org.jetbrains.kotlin.psi.KtValueArgumentList;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.BindingTrace;
import org.jetbrains.kotlin.resolve.DescriptorToSourceUtils;
import org.jetbrains.kotlin.resolve.InlineClassesUtilsKt;
import org.jetbrains.kotlin.resolve.checkers.DeclarationChecker;
import org.jetbrains.kotlin.resolve.checkers.DeclarationCheckerContext;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.resolve.inline.InlineOnlyKt;
import org.jetbrains.kotlin.resolve.source.PsiSourceElementKt;
import org.jetbrains.kotlin.types.DynamicTypesKt;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.TypeProjection;
import org.jetbrains.kotlin.types.typeUtil.TypeUtilsKt;

/* JADX INFO: compiled from: JsExportDeclarationChecker.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000R\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010#\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J \u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016J \u0010\u000b\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\f\u001a\u00020\b2\u0006\u0010\r\u001a\u00020\u000eH\u0002J\f\u0010\u000f\u001a\u00020\u0010*\u00020\u0006H\u0002J\u000e\u0010\u0011\u001a\u0004\u0018\u00010\u0010*\u00020\bH\u0002J\f\u0010\u0012\u001a\u00020\u0013*\u00020\bH\u0002J$\u0010\u0014\u001a\u00020\u0015*\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u00182\u000e\b\u0002\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00160\u001aH\u0002J$\u0010\u001b\u001a\u00020\u0015*\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u00182\u000e\b\u0002\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00160\u001aH\u0002¨\u0006\u001c"}, d2 = {"Lorg/jetbrains/kotlin/js/resolve/diagnostics/JsExportDeclarationChecker;", "Lorg/jetbrains/kotlin/resolve/checkers/DeclarationChecker;", "()V", "check", "", "declaration", "Lorg/jetbrains/kotlin/psi/KtDeclaration;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "context", "Lorg/jetbrains/kotlin/resolve/checkers/DeclarationCheckerContext;", "validateDeclarationOnConsumableName", "declarationDescriptor", "trace", "Lorg/jetbrains/kotlin/resolve/BindingTrace;", "getIdentifier", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "getJsNameArgument", "getKotlinOrJsName", "", "isExportable", "", "Lorg/jetbrains/kotlin/types/KotlinType;", "bindingContext", "Lorg/jetbrains/kotlin/resolve/BindingContext;", "currentlyProcessed", "", "isExportableReturn", "js.frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JsExportDeclarationChecker implements DeclarationChecker {
    public static final JsExportDeclarationChecker INSTANCE = new JsExportDeclarationChecker();

    /* JADX INFO: compiled from: JsExportDeclarationChecker.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[ClassKind.values().length];
            try {
                iArr[ClassKind.ANNOTATION_CLASS.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[ClassKind.CLASS.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    private JsExportDeclarationChecker() {
    }

    @Override // org.jetbrains.kotlin.resolve.checkers.DeclarationChecker
    public void check(KtDeclaration declaration, DeclarationDescriptor descriptor, DeclarationCheckerContext context) {
        String strConcat;
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(context, "context");
        BindingTrace trace = context.getTrace();
        BindingContext context2 = trace.getContext();
        Intrinsics.checkNotNullExpressionValue(context2, "getBindingContext(...)");
        if (AnnotationsUtils.isExportedObject(descriptor, context2) && (descriptor instanceof MemberDescriptor)) {
            boolean z = AnnotationsUtils.getJsNameAnnotation(descriptor) != null;
            MemberDescriptor memberDescriptor = (MemberDescriptor) descriptor;
            if (memberDescriptor.get$isExpect()) {
                check$reportWrongExportedDeclaration(trace, declaration, "expect");
            }
            validateDeclarationOnConsumableName(declaration, descriptor, trace);
            if (descriptor instanceof FunctionDescriptor) {
                FunctionDescriptor functionDescriptor = (FunctionDescriptor) descriptor;
                for (TypeParameterDescriptor typeParameterDescriptor : functionDescriptor.getTypeParameters()) {
                    Intrinsics.checkNotNull(typeParameterDescriptor);
                    check$checkTypeParameter(context2, trace, typeParameterDescriptor);
                }
                if (InlineOnlyKt.isInlineWithReified(memberDescriptor)) {
                    check$reportWrongExportedDeclaration(trace, declaration, "inline function with reified type parameters");
                    return;
                }
                if (functionDescriptor.isSuspend()) {
                    check$reportWrongExportedDeclaration(trace, declaration, "suspend function");
                    return;
                }
                if ((descriptor instanceof ConstructorDescriptor) && !((ConstructorDescriptor) descriptor).isPrimary() && !z) {
                    check$reportWrongExportedDeclaration(trace, declaration, "secondary constructor without @JsName");
                }
                if (descriptor instanceof PropertyAccessorDescriptor) {
                    return;
                }
                for (ValueParameterDescriptor valueParameterDescriptor : functionDescriptor.getValueParameters()) {
                    Intrinsics.checkNotNull(valueParameterDescriptor);
                    check$checkValueParameter(context2, trace, valueParameterDescriptor);
                }
                KotlinType returnType = functionDescriptor.getReturnType();
                if (returnType == null || isExportableReturn$default(INSTANCE, returnType, context2, null, 2, null)) {
                    return;
                }
                trace.report(ErrorsJs.NON_EXPORTABLE_TYPE.on(declaration, "return", returnType));
                return;
            }
            if (descriptor instanceof PropertyDescriptor) {
                if (declaration instanceof KtParameter) {
                    return;
                }
                if (DescriptorUtilsKt.isExtensionProperty(descriptor)) {
                    check$reportWrongExportedDeclaration(trace, declaration, "extension property");
                    return;
                }
                PropertyDescriptor propertyDescriptor = (PropertyDescriptor) descriptor;
                KotlinType type = propertyDescriptor.getType();
                Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
                if (isExportable$default(this, type, context2, null, 2, null)) {
                    return;
                }
                trace.report(ErrorsJs.NON_EXPORTABLE_TYPE.on(declaration, "property", propertyDescriptor.getType()));
                return;
            }
            if (descriptor instanceof ClassDescriptor) {
                ClassDescriptor classDescriptor = (ClassDescriptor) descriptor;
                for (TypeParameterDescriptor typeParameterDescriptor2 : classDescriptor.getDeclaredTypeParameters()) {
                    Intrinsics.checkNotNull(typeParameterDescriptor2);
                    check$checkTypeParameter(context2, trace, typeParameterDescriptor2);
                }
                int i = WhenMappings.$EnumSwitchMapping$0[classDescriptor.getClassKind().ordinal()];
                if (i != 1) {
                    strConcat = null;
                    if (i == 2) {
                        if (DescriptorUtilsKt.isInsideInterface(descriptor)) {
                            strConcat = "nested class inside exported interface";
                        } else if (InlineClassesUtilsKt.isInlineClass(descriptor)) {
                            strConcat = (classDescriptor.isInline() ? "inline " : "") + (classDescriptor.isValue() ? "value " : "") + "class";
                        }
                    } else if (DescriptorUtilsKt.isInsideInterface(descriptor)) {
                        strConcat = (classDescriptor.getIsCompanionObject() ? "companion object" : "nested/inner declaration").concat(" inside exported interface");
                    }
                } else {
                    strConcat = "annotation class";
                }
                if (strConcat != null) {
                    check$reportWrongExportedDeclaration(trace, declaration, strConcat);
                } else {
                    classDescriptor.getClassKind();
                    ClassKind classKind = ClassKind.ENUM_ENTRY;
                }
            }
        }
    }

    private static final void check$checkTypeParameter(BindingContext bindingContext, BindingTrace bindingTrace, TypeParameterDescriptor typeParameterDescriptor) {
        for (KotlinType kotlinType : typeParameterDescriptor.getUpperBounds()) {
            JsExportDeclarationChecker jsExportDeclarationChecker = INSTANCE;
            Intrinsics.checkNotNull(kotlinType);
            if (!isExportable$default(jsExportDeclarationChecker, kotlinType, bindingContext, null, 2, null)) {
                PsiElement psiElementDescriptorToDeclaration = DescriptorToSourceUtils.descriptorToDeclaration(typeParameterDescriptor);
                Intrinsics.checkNotNull(psiElementDescriptorToDeclaration);
                bindingTrace.report(ErrorsJs.NON_EXPORTABLE_TYPE.on(psiElementDescriptorToDeclaration, "upper bound", kotlinType));
            }
        }
    }

    private static final void check$checkValueParameter(BindingContext bindingContext, BindingTrace bindingTrace, ValueParameterDescriptor valueParameterDescriptor) {
        JsExportDeclarationChecker jsExportDeclarationChecker = INSTANCE;
        KotlinType type = valueParameterDescriptor.getType();
        Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
        if (isExportable$default(jsExportDeclarationChecker, type, bindingContext, null, 2, null)) {
            return;
        }
        PsiElement psiElementDescriptorToDeclaration = DescriptorToSourceUtils.descriptorToDeclaration(valueParameterDescriptor);
        Intrinsics.checkNotNull(psiElementDescriptorToDeclaration);
        bindingTrace.report(ErrorsJs.NON_EXPORTABLE_TYPE.on(psiElementDescriptorToDeclaration, "parameter", valueParameterDescriptor.getType()));
    }

    private static final void check$reportWrongExportedDeclaration(BindingTrace bindingTrace, KtDeclaration ktDeclaration, String str) {
        bindingTrace.report(ErrorsJs.WRONG_EXPORTED_DECLARATION.on(ktDeclaration, str));
    }

    /* JADX WARN: Multi-variable type inference failed */
    static /* synthetic */ boolean isExportableReturn$default(JsExportDeclarationChecker jsExportDeclarationChecker, KotlinType kotlinType, BindingContext bindingContext, Set set, int i, Object obj) {
        if ((i & 2) != 0) {
            set = new LinkedHashSet();
        }
        return jsExportDeclarationChecker.isExportableReturn(kotlinType, bindingContext, set);
    }

    private final boolean isExportableReturn(KotlinType kotlinType, BindingContext bindingContext, Set<KotlinType> set) {
        return TypeUtilsKt.isUnit(kotlinType) || isExportable(kotlinType, bindingContext, set);
    }

    /* JADX WARN: Multi-variable type inference failed */
    static /* synthetic */ boolean isExportable$default(JsExportDeclarationChecker jsExportDeclarationChecker, KotlinType kotlinType, BindingContext bindingContext, Set set, int i, Object obj) {
        if ((i & 2) != 0) {
            set = new LinkedHashSet();
        }
        return jsExportDeclarationChecker.isExportable(kotlinType, bindingContext, set);
    }

    private final boolean isExportable(KotlinType kotlinType, BindingContext bindingContext, Set<KotlinType> set) {
        if (!set.add(kotlinType)) {
            return true;
        }
        set.add(kotlinType);
        if (FunctionTypesKt.isFunctionType(kotlinType)) {
            int lastIndex = CollectionsKt.getLastIndex(kotlinType.getArguments());
            for (int i = 0; i < lastIndex; i++) {
                KotlinType nullableAnyType = kotlinType.getArguments().get(i).getNullableAnyType();
                Intrinsics.checkNotNullExpressionValue(nullableAnyType, "getType(...)");
                if (!isExportable(nullableAnyType, bindingContext, set)) {
                    set.remove(kotlinType);
                    return false;
                }
            }
            set.remove(kotlinType);
            KotlinType nullableAnyType2 = ((TypeProjection) CollectionsKt.last((List) kotlinType.getArguments())).getNullableAnyType();
            Intrinsics.checkNotNullExpressionValue(nullableAnyType2, "getType(...)");
            return isExportableReturn(nullableAnyType2, bindingContext, set);
        }
        Iterator<TypeProjection> it = kotlinType.getArguments().iterator();
        while (it.hasNext()) {
            KotlinType nullableAnyType3 = it.next().getNullableAnyType();
            Intrinsics.checkNotNullExpressionValue(nullableAnyType3, "getType(...)");
            if (!isExportable(nullableAnyType3, bindingContext, set)) {
                set.remove(kotlinType);
                return false;
            }
        }
        set.remove(kotlinType);
        KotlinType kotlinTypeMakeNotNullable = TypeUtilsKt.makeNotNullable(kotlinType);
        if (TypeUtilsKt.isAnyOrNullableAny(kotlinTypeMakeNotNullable) || TypeUtilsKt.isTypeParameter(kotlinTypeMakeNotNullable) || DynamicTypesKt.isDynamic(kotlinTypeMakeNotNullable) || TypeUtilsKt.isBoolean(kotlinTypeMakeNotNullable) || KotlinBuiltIns.isThrowableOrNullableThrowable(kotlinTypeMakeNotNullable) || KotlinBuiltIns.isString(kotlinTypeMakeNotNullable)) {
            return true;
        }
        if ((TypeUtilsKt.isPrimitiveNumberOrNullableType(kotlinTypeMakeNotNullable) && !TypeUtilsKt.isLong(kotlinTypeMakeNotNullable)) || TypeUtilsKt.isNothingOrNullableNothing(kotlinTypeMakeNotNullable) || KotlinBuiltIns.isArray(kotlinType) || KotlinBuiltIns.isPrimitiveArray(kotlinType)) {
            return true;
        }
        ClassifierDescriptor this$0 = kotlinType.getConstructor().getThis$0();
        if (this$0 instanceof MemberDescriptor) {
            return KotlinBuiltIns.isEnum(kotlinType) || DescriptorUtilsKt.isEffectivelyExternal((MemberDescriptor) this$0) || AnnotationsUtils.isExportedObject(this$0, bindingContext);
        }
        return false;
    }

    private final void validateDeclarationOnConsumableName(KtDeclaration declaration, DeclarationDescriptor declarationDescriptor, BindingTrace trace) {
        if (!DescriptorUtilKt.isTopLevelInPackage(declarationDescriptor) || declarationDescriptor.getShortName().isSpecial()) {
            return;
        }
        String kotlinOrJsName = getKotlinOrJsName(declarationDescriptor);
        if (IdentifierPolicyKt.getSPECIAL_KEYWORDS().contains(kotlinOrJsName)) {
            return;
        }
        if (IdentifierPolicyKt.getRESERVED_KEYWORDS().contains(kotlinOrJsName) || !Intrinsics.areEqual(NameSuggestion.INSTANCE.sanitizeName(kotlinOrJsName), kotlinOrJsName)) {
            PsiElement jsNameArgument = getJsNameArgument(declarationDescriptor);
            if (jsNameArgument == null) {
                jsNameArgument = getIdentifier(declaration);
            }
            trace.report(ErrorsJs.NON_CONSUMABLE_EXPORTED_IDENTIFIER.on(jsNameArgument, kotlinOrJsName));
        }
    }

    private final String getKotlinOrJsName(DeclarationDescriptor declarationDescriptor) {
        String jsName = AnnotationsUtils.getJsName(declarationDescriptor);
        if (jsName != null) {
            return jsName;
        }
        String identifier = declarationDescriptor.getShortName().getIdentifier();
        Intrinsics.checkNotNullExpressionValue(identifier, "getIdentifier(...)");
        return identifier;
    }

    private final PsiElement getIdentifier(KtDeclaration ktDeclaration) {
        Intrinsics.checkNotNull(ktDeclaration, "null cannot be cast to non-null type org.jetbrains.kotlin.psi.KtNamedDeclaration");
        PsiElement nameIdentifier = ((KtNamedDeclaration) ktDeclaration).mo7392getNameIdentifier();
        Intrinsics.checkNotNull(nameIdentifier);
        return nameIdentifier;
    }

    private final PsiElement getJsNameArgument(DeclarationDescriptor declarationDescriptor) {
        List<KtValueArgument> arguments;
        AnnotationDescriptor jsNameAnnotation = AnnotationsUtils.getJsNameAnnotation(declarationDescriptor);
        KtValueArgument ktValueArgument = null;
        if (jsNameAnnotation == null) {
            return null;
        }
        PsiElement psi = PsiSourceElementKt.getPsi(jsNameAnnotation.getSource());
        Intrinsics.checkNotNull(psi, "null cannot be cast to non-null type org.jetbrains.kotlin.psi.KtAnnotationEntry");
        KtValueArgumentList valueArgumentList = ((KtAnnotationEntry) psi).getValueArgumentList();
        if (valueArgumentList != null && (arguments = valueArgumentList.getArguments()) != null) {
            ktValueArgument = (KtValueArgument) CollectionsKt.first((List) arguments);
        }
        return ktValueArgument;
    }
}

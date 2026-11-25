package org.jetbrains.kotlin.js.resolve.diagnostics;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.FunctionTypesKt;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.MemberDescriptor;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.psi.KtClassOrObject;
import org.jetbrains.kotlin.psi.KtDeclaration;
import org.jetbrains.kotlin.resolve.calls.components.ArgumentsUtilsKt;
import org.jetbrains.kotlin.resolve.checkers.DeclarationChecker;
import org.jetbrains.kotlin.resolve.checkers.DeclarationCheckerContext;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.resolve.scopes.DescriptorKindFilter;
import org.jetbrains.kotlin.resolve.scopes.MemberScope;
import org.jetbrains.kotlin.resolve.scopes.ResolutionScope;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.SimpleType;
import org.jetbrains.kotlin.types.typeUtil.TypeUtilsKt;

/* JADX INFO: compiled from: JsInheritanceChecker.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J \u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016J\u0012\u0010\u000b\u001a\u0004\u0018\u00010\f2\u0006\u0010\r\u001a\u00020\u000eH\u0002J\u0010\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\fH\u0002¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/js/resolve/diagnostics/JsInheritanceChecker;", "Lorg/jetbrains/kotlin/resolve/checkers/DeclarationChecker;", "()V", "check", "", "declaration", "Lorg/jetbrains/kotlin/psi/KtDeclaration;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "context", "Lorg/jetbrains/kotlin/resolve/checkers/DeclarationCheckerContext;", "findFakeMethodOverridingExternalWithOptionalParams", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "cls", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "isOverridingExternalWithOptionalParams", "", "function", "js.frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JsInheritanceChecker implements DeclarationChecker {
    public static final JsInheritanceChecker INSTANCE = new JsInheritanceChecker();

    private JsInheritanceChecker() {
    }

    @Override // org.jetbrains.kotlin.resolve.checkers.DeclarationChecker
    public void check(KtDeclaration declaration, DeclarationDescriptor descriptor, DeclarationCheckerContext context) {
        FunctionDescriptor functionDescriptorFindFakeMethodOverridingExternalWithOptionalParams;
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(context, "context");
        if ((descriptor instanceof FunctionDescriptor) && !DescriptorUtilsKt.isEffectivelyExternal((MemberDescriptor) descriptor) && isOverridingExternalWithOptionalParams((FunctionDescriptor) descriptor)) {
            context.getTrace().report(ErrorsJs.OVERRIDING_EXTERNAL_FUN_WITH_OPTIONAL_PARAMS.on(declaration));
        } else if ((descriptor instanceof ClassDescriptor) && !DescriptorUtilsKt.isEffectivelyExternal((MemberDescriptor) descriptor) && (functionDescriptorFindFakeMethodOverridingExternalWithOptionalParams = findFakeMethodOverridingExternalWithOptionalParams((ClassDescriptor) descriptor)) != null) {
            context.getTrace().report(ErrorsJs.OVERRIDING_EXTERNAL_FUN_WITH_OPTIONAL_PARAMS_WITH_FAKE.on(declaration, functionDescriptorFindFakeMethodOverridingExternalWithOptionalParams));
        }
        if (context.getLanguageVersionSettings().supportsFeature(LanguageFeature.JsAllowImplementingFunctionInterface) || !(descriptor instanceof ClassDescriptor)) {
            return;
        }
        SimpleType defaultType = ((ClassDescriptor) descriptor).getDefaultType();
        Intrinsics.checkNotNullExpressionValue(defaultType, "getDefaultType(...)");
        Collection<KotlinType> collectionImmediateSupertypes = TypeUtilsKt.immediateSupertypes(defaultType);
        if ((collectionImmediateSupertypes instanceof Collection) && collectionImmediateSupertypes.isEmpty()) {
            return;
        }
        for (KotlinType kotlinType : collectionImmediateSupertypes) {
            if (FunctionTypesKt.isBuiltinFunctionalTypeOrSubtype(kotlinType) && !FunctionTypesKt.isSuspendFunctionTypeOrSubtype(kotlinType)) {
                context.getTrace().report(ErrorsJs.IMPLEMENTING_FUNCTION_INTERFACE.on((KtClassOrObject) declaration));
                return;
            }
        }
    }

    private final boolean isOverridingExternalWithOptionalParams(FunctionDescriptor function) {
        if (!function.getKind().isReal() && function.getModality() == Modality.ABSTRACT) {
            return false;
        }
        Collection<? extends FunctionDescriptor> overriddenDescriptors = function.getOverriddenDescriptors();
        Intrinsics.checkNotNullExpressionValue(overriddenDescriptors, "getOverriddenDescriptors(...)");
        ArrayList arrayList = new ArrayList();
        for (Object obj : overriddenDescriptors) {
            FunctionDescriptor functionDescriptor = (FunctionDescriptor) obj;
            Intrinsics.checkNotNull(functionDescriptor);
            if (DescriptorUtilsKt.isEffectivelyExternal(functionDescriptor)) {
                arrayList.add(obj);
            }
        }
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            List<ValueParameterDescriptor> valueParameters = ((FunctionDescriptor) it.next()).getValueParameters();
            Intrinsics.checkNotNullExpressionValue(valueParameters, "getValueParameters(...)");
            List<ValueParameterDescriptor> list = valueParameters;
            if (!(list instanceof Collection) || !list.isEmpty()) {
                for (ValueParameterDescriptor valueParameterDescriptor : list) {
                    Intrinsics.checkNotNull(valueParameterDescriptor);
                    if (ArgumentsUtilsKt.hasDefaultValue(valueParameterDescriptor)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private final FunctionDescriptor findFakeMethodOverridingExternalWithOptionalParams(ClassDescriptor cls) {
        MemberScope unsubstitutedMemberScope = cls.getUnsubstitutedMemberScope();
        Intrinsics.checkNotNullExpressionValue(unsubstitutedMemberScope, "getUnsubstitutedMemberScope(...)");
        Object obj = null;
        Collection<DeclarationDescriptor> contributedDescriptors$default = ResolutionScope.DefaultImpls.getContributedDescriptors$default(unsubstitutedMemberScope, DescriptorKindFilter.CALLABLES, null, 2, null);
        ArrayList arrayList = new ArrayList();
        for (DeclarationDescriptor declarationDescriptor : contributedDescriptors$default) {
            FunctionDescriptor functionDescriptor = declarationDescriptor instanceof FunctionDescriptor ? (FunctionDescriptor) declarationDescriptor : null;
            if (functionDescriptor != null) {
                arrayList.add(functionDescriptor);
            }
        }
        ArrayList arrayList2 = new ArrayList();
        for (Object obj2 : arrayList) {
            FunctionDescriptor functionDescriptor2 = (FunctionDescriptor) obj2;
            if (Intrinsics.areEqual(functionDescriptor2.getContainingModule(), cls) && !functionDescriptor2.getKind().isReal() && functionDescriptor2.getOverriddenDescriptors().size() > 1) {
                arrayList2.add(obj2);
            }
        }
        for (Object obj3 : arrayList2) {
            if (INSTANCE.isOverridingExternalWithOptionalParams((FunctionDescriptor) obj3)) {
                obj = obj3;
                break;
            }
        }
        return (FunctionDescriptor) obj;
    }
}

package org.jetbrains.kotlin.resolve.jvm;

import java.util.Collection;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyAccessorDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.VariableDescriptor;
import org.jetbrains.kotlin.load.java.typeEnhancement.TypeEnhancementKt;
import org.jetbrains.kotlin.psi.KtDeclaration;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtFunction;
import org.jetbrains.kotlin.psi.KtProperty;
import org.jetbrains.kotlin.psi.KtPropertyAccessor;
import org.jetbrains.kotlin.resolve.BindingTrace;
import org.jetbrains.kotlin.types.FlexibleTypesKt;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.KotlinTypeKt;
import org.jetbrains.kotlin.types.SimpleType;
import org.jetbrains.kotlin.types.UnwrappedType;
import org.jetbrains.kotlin.types.checker.NewKotlinTypeCheckerKt;
import org.jetbrains.kotlin.types.typeUtil.TypeUtilsKt;

/* JADX INFO: compiled from: RuntimeAssertions.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J(\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0007J \u0010\r\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u000e2\u0006\u0010\u0007\u001a\u00020\u000f2\u0006\u0010\t\u001a\u00020\nH\u0002J \u0010\u0010\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00112\u0006\u0010\u0007\u001a\u00020\u00122\u0006\u0010\t\u001a\u00020\nH\u0002J \u0010\u0013\u001a\u00020\u00042\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\t\u001a\u00020\nH\u0002J \u0010\u0018\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00112\u0006\u0010\u0007\u001a\u00020\u00192\u0006\u0010\t\u001a\u00020\nH\u0002J \u0010\u001a\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u001b2\u0006\u0010\u0007\u001a\u00020\u001c2\u0006\u0010\t\u001a\u00020\nH\u0002J\f\u0010\u001d\u001a\u00020\u001e*\u00020\u001fH\u0002¨\u0006 "}, d2 = {"Lorg/jetbrains/kotlin/resolve/jvm/RuntimeAssertionsOnDeclarationBodyChecker;", "", "()V", "check", "", "declaration", "Lorg/jetbrains/kotlin/psi/KtDeclaration;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "bindingTrace", "Lorg/jetbrains/kotlin/resolve/BindingTrace;", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "checkFunction", "Lorg/jetbrains/kotlin/psi/KtFunction;", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "checkLocalVariable", "Lorg/jetbrains/kotlin/psi/KtProperty;", "Lorg/jetbrains/kotlin/descriptors/VariableDescriptor;", "checkNullabilityAssertion", "expression", "Lorg/jetbrains/kotlin/psi/KtExpression;", "declarationType", "Lorg/jetbrains/kotlin/types/KotlinType;", "checkProperty", "Lorg/jetbrains/kotlin/descriptors/PropertyDescriptor;", "checkPropertyAccessor", "Lorg/jetbrains/kotlin/psi/KtPropertyAccessor;", "Lorg/jetbrains/kotlin/descriptors/PropertyAccessorDescriptor;", "canContainNull", "", "Lorg/jetbrains/kotlin/types/UnwrappedType;", "frontend.java"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class RuntimeAssertionsOnDeclarationBodyChecker {
    public static final RuntimeAssertionsOnDeclarationBodyChecker INSTANCE = new RuntimeAssertionsOnDeclarationBodyChecker();

    private RuntimeAssertionsOnDeclarationBodyChecker() {
    }

    @JvmStatic
    public static final void check(KtDeclaration declaration, DeclarationDescriptor descriptor, BindingTrace bindingTrace, LanguageVersionSettings languageVersionSettings) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(bindingTrace, "bindingTrace");
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        if (languageVersionSettings.supportsFeature(LanguageFeature.StrictJavaNullabilityAssertions)) {
            boolean z = declaration instanceof KtProperty;
            if (z && (descriptor instanceof VariableDescriptor)) {
                INSTANCE.checkLocalVariable((KtProperty) declaration, (VariableDescriptor) descriptor, bindingTrace);
                return;
            }
            if ((declaration instanceof KtFunction) && (descriptor instanceof FunctionDescriptor)) {
                INSTANCE.checkFunction((KtFunction) declaration, (FunctionDescriptor) descriptor, bindingTrace);
                return;
            }
            if (z && (descriptor instanceof PropertyDescriptor)) {
                INSTANCE.checkProperty((KtProperty) declaration, (PropertyDescriptor) descriptor, bindingTrace);
            } else if ((declaration instanceof KtPropertyAccessor) && (descriptor instanceof PropertyAccessorDescriptor)) {
                INSTANCE.checkPropertyAccessor((KtPropertyAccessor) declaration, (PropertyAccessorDescriptor) descriptor, bindingTrace);
            }
        }
    }

    private final void checkLocalVariable(KtProperty declaration, VariableDescriptor descriptor, BindingTrace bindingTrace) {
        KtExpression initializer;
        if (declaration.mo7396getTypeReference() == null && (initializer = declaration.getInitializer()) != null) {
            KotlinType type = descriptor.getType();
            Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
            checkNullabilityAssertion(initializer, type, bindingTrace);
        }
    }

    private final void checkFunction(KtFunction declaration, FunctionDescriptor descriptor, BindingTrace bindingTrace) {
        KtExpression bodyExpression;
        KotlinType returnType;
        if (declaration.mo7396getTypeReference() != null || declaration.hasBlockBody() || (bodyExpression = declaration.getBodyExpression()) == null || (returnType = descriptor.getReturnType()) == null) {
            return;
        }
        checkNullabilityAssertion(bodyExpression, returnType, bindingTrace);
    }

    private final void checkProperty(KtProperty declaration, PropertyDescriptor descriptor, BindingTrace bindingTrace) {
        KtExpression initializer;
        if (declaration.mo7396getTypeReference() != null || declaration.hasDelegateExpression() || (initializer = declaration.getInitializer()) == null) {
            return;
        }
        KotlinType type = descriptor.getType();
        Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
        checkNullabilityAssertion(initializer, type, bindingTrace);
    }

    private final void checkPropertyAccessor(KtPropertyAccessor declaration, PropertyAccessorDescriptor descriptor, BindingTrace bindingTrace) {
        KtExpression bodyExpression;
        if (declaration.getProperty().mo7396getTypeReference() != null || declaration.hasBlockBody() || (bodyExpression = declaration.getBodyExpression()) == null) {
            return;
        }
        KotlinType type = descriptor.getCorrespondingProperty().getType();
        Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
        checkNullabilityAssertion(bodyExpression, type, bindingTrace);
    }

    private final void checkNullabilityAssertion(KtExpression expression, KotlinType declarationType, BindingTrace bindingTrace) {
        KotlinType type;
        if (canContainNull(declarationType.unwrap()) || (type = bindingTrace.getType(expression)) == null || KotlinTypeKt.isError(type) || !TypeEnhancementKt.hasEnhancedNullability(type)) {
            return;
        }
        bindingTrace.record(JvmBindingContextSlices.BODY_RUNTIME_ASSERTION_INFO, expression, new RuntimeAssertionInfo(true, RuntimeAssertionsKt.getTextForRuntimeAssertionInfo(expression)));
    }

    private final boolean canContainNull(UnwrappedType unwrappedType) {
        SimpleType simpleTypeUpperIfFlexible = FlexibleTypesKt.upperIfFlexible(unwrappedType);
        if (simpleTypeUpperIfFlexible.getIsMarkedNullable()) {
            return true;
        }
        if (!NewKotlinTypeCheckerKt.isClassType(simpleTypeUpperIfFlexible)) {
            Collection<KotlinType> collectionImmediateSupertypes = TypeUtilsKt.immediateSupertypes(simpleTypeUpperIfFlexible);
            if ((collectionImmediateSupertypes instanceof Collection) && collectionImmediateSupertypes.isEmpty()) {
                return true;
            }
            Iterator<T> it = collectionImmediateSupertypes.iterator();
            while (it.hasNext()) {
                if (!INSTANCE.canContainNull(((KotlinType) it.next()).unwrap())) {
                }
            }
            return true;
        }
        return false;
    }
}

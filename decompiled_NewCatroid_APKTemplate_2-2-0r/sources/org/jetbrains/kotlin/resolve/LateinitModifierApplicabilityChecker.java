package org.jetbrains.kotlin.resolve;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.InlineClassRepresentation;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.VariableDescriptor;
import org.jetbrains.kotlin.descriptors.impl.LocalVariableDescriptor;
import org.jetbrains.kotlin.diagnostics.Errors;
import org.jetbrains.kotlin.lexer.KtTokens;
import org.jetbrains.kotlin.psi.KtCallableDeclaration;
import org.jetbrains.kotlin.psi.KtParameter;
import org.jetbrains.kotlin.psi.KtProperty;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.SimpleType;
import org.jetbrains.kotlin.types.TypeUtils;
import org.jetbrains.kotlin.types.typeUtil.TypeUtilsKt;

/* JADX INFO: compiled from: LateinitModifierApplicabilityChecker.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J&\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fJ\u0010\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0002¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/resolve/LateinitModifierApplicabilityChecker;", "", "()V", "checkLateinitModifierApplicability", "", "trace", "Lorg/jetbrains/kotlin/resolve/BindingTrace;", "ktDeclaration", "Lorg/jetbrains/kotlin/psi/KtCallableDeclaration;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/VariableDescriptor;", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "hasUnderlyingTypeForbiddenForLateinit", "", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/types/KotlinType;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class LateinitModifierApplicabilityChecker {
    public static final LateinitModifierApplicabilityChecker INSTANCE = new LateinitModifierApplicabilityChecker();

    private LateinitModifierApplicabilityChecker() {
    }

    public final void checkLateinitModifierApplicability(BindingTrace trace, KtCallableDeclaration ktDeclaration, VariableDescriptor descriptor, LanguageVersionSettings languageVersionSettings) {
        String str;
        Intrinsics.checkNotNullParameter(trace, "trace");
        Intrinsics.checkNotNullParameter(ktDeclaration, "ktDeclaration");
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        if (ktDeclaration.hasModifier(KtTokens.LATEINIT_KEYWORD)) {
            boolean z = descriptor instanceof PropertyDescriptor;
            if (z) {
                str = "properties";
            } else {
                if (!(descriptor instanceof LocalVariableDescriptor)) {
                    throw new AssertionError("Should be a property or a local variable: " + descriptor);
                }
                str = "local variables";
            }
            KotlinType type = descriptor.getType();
            Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
            if (!descriptor.isVar()) {
                trace.report(Errors.INAPPLICABLE_LATEINIT_MODIFIER.on(ktDeclaration, "is allowed only on mutable ".concat(str)));
            }
            if (InlineClassesUtilsKt.isInlineClassType(type)) {
                if (TypeUtilsKt.isUnsignedNumberType(type)) {
                    trace.report(Errors.INAPPLICABLE_LATEINIT_MODIFIER.on(ktDeclaration, "is not allowed on " + str + " of unsigned types"));
                } else if (!languageVersionSettings.supportsFeature(LanguageFeature.InlineLateinit)) {
                    trace.report(Errors.INAPPLICABLE_LATEINIT_MODIFIER.on(ktDeclaration, "is not allowed on " + str + " of inline class types"));
                } else if (hasUnderlyingTypeForbiddenForLateinit(type)) {
                    trace.report(Errors.INAPPLICABLE_LATEINIT_MODIFIER.on(ktDeclaration, "is not allowed on " + str + " of inline type with underlying type not suitable for lateinit declaration"));
                }
            }
            if (type.getIsMarkedNullable()) {
                trace.report(Errors.INAPPLICABLE_LATEINIT_MODIFIER.on(ktDeclaration, "is not allowed on " + str + " of nullable types"));
            } else if (TypeUtils.isNullableType(type)) {
                trace.report(Errors.INAPPLICABLE_LATEINIT_MODIFIER.on(ktDeclaration, "is not allowed on " + str + " of a type with nullable upper bound"));
            }
            if (KotlinBuiltIns.isPrimitiveType(type)) {
                trace.report(Errors.INAPPLICABLE_LATEINIT_MODIFIER.on(ktDeclaration, "is not allowed on " + str + " of primitive types"));
            }
            boolean z2 = ktDeclaration instanceof KtProperty;
            if (z2) {
                KtProperty ktProperty = (KtProperty) ktDeclaration;
                if (ktProperty.hasDelegateExpression()) {
                    trace.report(Errors.INAPPLICABLE_LATEINIT_MODIFIER.on(ktDeclaration, "is not allowed on delegated properties"));
                } else if (ktProperty.hasInitializer()) {
                    trace.report(Errors.INAPPLICABLE_LATEINIT_MODIFIER.on(ktDeclaration, "is not allowed on " + str + " with initializer"));
                }
            }
            if (z) {
                PropertyDescriptor propertyDescriptor = (PropertyDescriptor) descriptor;
                boolean z3 = propertyDescriptor.getModality() == Modality.ABSTRACT;
                boolean z4 = z2 && ((KtProperty) ktDeclaration).hasDelegateExpressionOrInitializer();
                boolean zHasAnyAccessorImplementation = DeclarationsChecker.INSTANCE.hasAnyAccessorImplementation(propertyDescriptor);
                Boolean bool = (Boolean) trace.getContext().get(BindingContext.BACKING_FIELD_REQUIRED, descriptor);
                if (bool == null) {
                    bool = false;
                }
                boolean zBooleanValue = bool.booleanValue();
                if (ktDeclaration instanceof KtParameter) {
                    trace.report(Errors.INAPPLICABLE_LATEINIT_MODIFIER.on(ktDeclaration, "is not allowed on primary constructor parameters"));
                }
                if (z3) {
                    trace.report(Errors.INAPPLICABLE_LATEINIT_MODIFIER.on(ktDeclaration, "is not allowed on abstract properties"));
                }
                if (!z4) {
                    if (zHasAnyAccessorImplementation) {
                        trace.report(Errors.INAPPLICABLE_LATEINIT_MODIFIER.on(ktDeclaration, "is not allowed on properties with a custom getter or setter"));
                    } else if (!z3 && !zBooleanValue) {
                        trace.report(Errors.INAPPLICABLE_LATEINIT_MODIFIER.on(ktDeclaration, "is not allowed on properties without backing field"));
                    }
                }
                if (propertyDescriptor.getExtensionReceiverParameter() != null) {
                    trace.report(Errors.INAPPLICABLE_LATEINIT_MODIFIER.on(ktDeclaration, "is not allowed on extension properties"));
                }
            }
        }
    }

    private static final KotlinType hasUnderlyingTypeForbiddenForLateinit$getUnderlyingType(KotlinType kotlinType) {
        ClassifierDescriptor this$0 = kotlinType.getConstructor().getThis$0();
        Intrinsics.checkNotNull(this$0, "null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.ClassDescriptor");
        InlineClassRepresentation<SimpleType> inlineClassRepresentation = DescriptorUtilsKt.getInlineClassRepresentation((ClassDescriptor) this$0);
        Intrinsics.checkNotNull(inlineClassRepresentation);
        return (KotlinType) inlineClassRepresentation.getUnderlyingType();
    }

    private static final boolean hasUnderlyingTypeForbiddenForLateinit$isForbiddenForLateinit(KotlinType kotlinType) {
        if (kotlinType.getIsMarkedNullable() || TypeUtils.isNullableType(kotlinType) || KotlinBuiltIns.isPrimitiveType(kotlinType)) {
            return true;
        }
        if (InlineClassesUtilsKt.isInlineClassType(kotlinType)) {
            return hasUnderlyingTypeForbiddenForLateinit$isForbiddenForLateinit(hasUnderlyingTypeForbiddenForLateinit$getUnderlyingType(kotlinType));
        }
        return false;
    }

    private final boolean hasUnderlyingTypeForbiddenForLateinit(KotlinType type) {
        if (InlineClassesUtilsKt.isRecursiveInlineOrValueClassType(type)) {
            return false;
        }
        return hasUnderlyingTypeForbiddenForLateinit$isForbiddenForLateinit(hasUnderlyingTypeForbiddenForLateinit$getUnderlyingType(type));
    }
}

package org.jetbrains.kotlin.resolve;

import androidx.exifinterface.media.ExifInterface;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.ClassConstructorDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.ConstructorDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptorWithVisibility;
import org.jetbrains.kotlin.descriptors.DescriptorUtilKt;
import org.jetbrains.kotlin.descriptors.DescriptorVisibility;
import org.jetbrains.kotlin.descriptors.DescriptorWithRelation;
import org.jetbrains.kotlin.descriptors.EffectiveVisibility;
import org.jetbrains.kotlin.descriptors.EffectiveVisibilityUtilsKt;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.ReceiverParameterDescriptor;
import org.jetbrains.kotlin.descriptors.TypeAliasDescriptor;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.diagnostics.DiagnosticFactory3;
import org.jetbrains.kotlin.diagnostics.DiagnosticFactoryForDeprecation3;
import org.jetbrains.kotlin.diagnostics.Errors;
import org.jetbrains.kotlin.psi.KtClassOrObject;
import org.jetbrains.kotlin.psi.KtConstructor;
import org.jetbrains.kotlin.psi.KtFunction;
import org.jetbrains.kotlin.psi.KtModifierListOwner;
import org.jetbrains.kotlin.psi.KtParameter;
import org.jetbrains.kotlin.psi.KtPrimaryConstructor;
import org.jetbrains.kotlin.psi.KtProperty;
import org.jetbrains.kotlin.psi.KtSuperTypeListEntry;
import org.jetbrains.kotlin.psi.KtTypeAlias;
import org.jetbrains.kotlin.psi.KtTypeParameter;
import org.jetbrains.kotlin.psi.KtTypeReference;
import org.jetbrains.kotlin.psi.psiUtil.KtPsiUtilKt;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.KotlinTypeKt;
import org.jetbrains.kotlin.types.SimpleType;
import org.jetbrains.kotlin.types.TypeUtils;

/* JADX INFO: compiled from: ExposedVisibilityChecker.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u009c\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0019\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\u0002\u0010\u0006J\u0016\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fJ\u001e\u0010\r\u001a\u00020\b2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0013J \u0010\u0014\u001a\u00020\b2\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u00182\b\b\u0002\u0010\u0012\u001a\u00020\u0013J\"\u0010\u0019\u001a\u00020\b2\b\u0010\u001a\u001a\u0004\u0018\u00010\u001b2\u0006\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u0012\u001a\u00020\u0013H\u0002J\u0018\u0010\u001e\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0002J \u0010\u001f\u001a\u00020\b2\u0006\u0010 \u001a\u00020!2\u0006\u0010\"\u001a\u00020#2\b\b\u0002\u0010\u0012\u001a\u00020\u0013J\u0018\u0010$\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0002J\u0016\u0010%\u001a\u00020&2\u0006\u0010'\u001a\u00020(2\u0006\u0010)\u001a\u00020*JO\u0010+\u001a\u00020&\"\b\b\u0000\u0010,*\u00020-2\u001e\u0010.\u001a\u001a\u0012\u0004\u0012\u0002H,\u0012\u0004\u0012\u000200\u0012\u0004\u0012\u000201\u0012\u0004\u0012\u0002000/2\u0006\u00102\u001a\u0002H,2\u0006\u00103\u001a\u0002002\u0006\u00104\u001a\u000201H\u0002¢\u0006\u0002\u00105JO\u00106\u001a\u00020&\"\b\b\u0000\u0010,*\u00020-2\u001e\u0010.\u001a\u001a\u0012\u0004\u0012\u0002H,\u0012\u0004\u0012\u000200\u0012\u0004\u0012\u000201\u0012\u0004\u0012\u000200072\u0006\u00102\u001a\u0002H,2\u0006\u00103\u001a\u0002002\u0006\u00104\u001a\u000201H\u0002¢\u0006\u0002\u00108R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u00069"}, d2 = {"Lorg/jetbrains/kotlin/resolve/ExposedVisibilityChecker;", "", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "trace", "Lorg/jetbrains/kotlin/resolve/BindingTrace;", "(Lorg/jetbrains/kotlin/config/LanguageVersionSettings;Lorg/jetbrains/kotlin/resolve/BindingTrace;)V", "checkClassHeader", "", "klass", "Lorg/jetbrains/kotlin/psi/KtClassOrObject;", "classDescriptor", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "checkDeclarationWithVisibility", "modifierListOwner", "Lorg/jetbrains/kotlin/psi/KtModifierListOwner;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptorWithVisibility;", "visibility", "Lorg/jetbrains/kotlin/descriptors/DescriptorVisibility;", "checkFunction", "function", "Lorg/jetbrains/kotlin/psi/KtFunction;", "functionDescriptor", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "checkMemberReceiver", "typeReference", "Lorg/jetbrains/kotlin/psi/KtTypeReference;", "memberDescriptor", "Lorg/jetbrains/kotlin/descriptors/CallableMemberDescriptor;", "checkParameterBounds", "checkProperty", "property", "Lorg/jetbrains/kotlin/psi/KtProperty;", "propertyDescriptor", "Lorg/jetbrains/kotlin/descriptors/PropertyDescriptor;", "checkSupertypes", "checkTypeAlias", "", "typeAlias", "Lorg/jetbrains/kotlin/psi/KtTypeAlias;", "typeAliasDescriptor", "Lorg/jetbrains/kotlin/descriptors/TypeAliasDescriptor;", "reportExposure", ExifInterface.LONGITUDE_EAST, "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "diagnostic", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticFactory3;", "Lorg/jetbrains/kotlin/descriptors/EffectiveVisibility;", "Lorg/jetbrains/kotlin/descriptors/DescriptorWithRelation;", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "elementVisibility", "restrictingDescriptor", "(Lorg/jetbrains/kotlin/diagnostics/DiagnosticFactory3;Lcom/intellij/psi/PsiElement;Lorg/jetbrains/kotlin/descriptors/EffectiveVisibility;Lorg/jetbrains/kotlin/descriptors/DescriptorWithRelation;)V", "reportExposureForDeprecation", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticFactoryForDeprecation3;", "(Lorg/jetbrains/kotlin/diagnostics/DiagnosticFactoryForDeprecation3;Lcom/intellij/psi/PsiElement;Lorg/jetbrains/kotlin/descriptors/EffectiveVisibility;Lorg/jetbrains/kotlin/descriptors/DescriptorWithRelation;)V", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ExposedVisibilityChecker {
    private final LanguageVersionSettings languageVersionSettings;
    private final BindingTrace trace;

    public ExposedVisibilityChecker(LanguageVersionSettings languageVersionSettings, BindingTrace bindingTrace) {
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        this.languageVersionSettings = languageVersionSettings;
        this.trace = bindingTrace;
    }

    public /* synthetic */ ExposedVisibilityChecker(LanguageVersionSettings languageVersionSettings, BindingTrace bindingTrace, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(languageVersionSettings, (i & 2) != 0 ? null : bindingTrace);
    }

    private final <E extends PsiElement> void reportExposure(DiagnosticFactory3<E, EffectiveVisibility, DescriptorWithRelation, EffectiveVisibility> diagnosticFactory3, E e, EffectiveVisibility effectiveVisibility, DescriptorWithRelation descriptorWithRelation) {
        BindingTrace bindingTrace = this.trace;
        if (bindingTrace == null) {
            return;
        }
        EffectiveVisibility effectiveVisibility2 = descriptorWithRelation.effectiveVisibility();
        if (!this.languageVersionSettings.supportsFeature(LanguageFeature.PrivateInFileEffectiveVisibility) && Intrinsics.areEqual(effectiveVisibility, EffectiveVisibility.PrivateInFile.INSTANCE)) {
            bindingTrace.report(Errors.EXPOSED_FROM_PRIVATE_IN_FILE.on(e, effectiveVisibility, descriptorWithRelation, effectiveVisibility2));
        } else {
            bindingTrace.report(diagnosticFactory3.on(e, effectiveVisibility, descriptorWithRelation, effectiveVisibility2));
        }
    }

    private final <E extends PsiElement> void reportExposureForDeprecation(DiagnosticFactoryForDeprecation3<E, EffectiveVisibility, DescriptorWithRelation, EffectiveVisibility> diagnosticFactoryForDeprecation3, E e, EffectiveVisibility effectiveVisibility, DescriptorWithRelation descriptorWithRelation) {
        BindingTrace bindingTrace = this.trace;
        if (bindingTrace == null) {
            return;
        }
        bindingTrace.report(diagnosticFactoryForDeprecation3.on(this.languageVersionSettings, e, effectiveVisibility, descriptorWithRelation, descriptorWithRelation.effectiveVisibility()));
    }

    public final boolean checkClassHeader(KtClassOrObject klass, ClassDescriptor classDescriptor) {
        ClassConstructorDescriptor classConstructorDescriptorMo6347getUnsubstitutedPrimaryConstructor;
        Intrinsics.checkNotNullParameter(klass, "klass");
        Intrinsics.checkNotNullParameter(classDescriptor, "classDescriptor");
        boolean zCheckSupertypes = checkSupertypes(klass, classDescriptor) & checkParameterBounds(klass, classDescriptor);
        KtPrimaryConstructor primaryConstructor = klass.getPrimaryConstructor();
        return (primaryConstructor == null || (classConstructorDescriptorMo6347getUnsubstitutedPrimaryConstructor = classDescriptor.mo6347getUnsubstitutedPrimaryConstructor()) == null) ? zCheckSupertypes : checkFunction$default(this, primaryConstructor, classConstructorDescriptorMo6347getUnsubstitutedPrimaryConstructor, null, 4, null) & zCheckSupertypes;
    }

    public final boolean checkDeclarationWithVisibility(KtModifierListOwner modifierListOwner, DeclarationDescriptorWithVisibility descriptor, DescriptorVisibility visibility) {
        Intrinsics.checkNotNullParameter(modifierListOwner, "modifierListOwner");
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(visibility, "visibility");
        if ((modifierListOwner instanceof KtFunction) && (descriptor instanceof FunctionDescriptor)) {
            return checkFunction((KtFunction) modifierListOwner, (FunctionDescriptor) descriptor, visibility);
        }
        if ((modifierListOwner instanceof KtProperty) && (descriptor instanceof PropertyDescriptor)) {
            return checkProperty((KtProperty) modifierListOwner, (PropertyDescriptor) descriptor, visibility);
        }
        return true;
    }

    public final void checkTypeAlias(KtTypeAlias typeAlias, TypeAliasDescriptor typeAliasDescriptor) {
        EffectiveVisibility effectiveVisibilityEffectiveVisibility$default;
        DescriptorWithRelation descriptorWithRelationLeastPermissiveDescriptor;
        Intrinsics.checkNotNullParameter(typeAlias, "typeAlias");
        Intrinsics.checkNotNullParameter(typeAliasDescriptor, "typeAliasDescriptor");
        SimpleType expandedType = typeAliasDescriptor.getExpandedType();
        if (KotlinTypeKt.isError(expandedType) || (descriptorWithRelationLeastPermissiveDescriptor = EffectiveVisibilityUtilsKt.leastPermissiveDescriptor(expandedType, (effectiveVisibilityEffectiveVisibility$default = EffectiveVisibilityUtilsKt.effectiveVisibility$default((DeclarationDescriptorWithVisibility) typeAliasDescriptor, (DescriptorVisibility) null, false, 3, (Object) null)))) == null) {
            return;
        }
        DiagnosticFactory3<PsiElement, EffectiveVisibility, DescriptorWithRelation, EffectiveVisibility> EXPOSED_TYPEALIAS_EXPANDED_TYPE = Errors.EXPOSED_TYPEALIAS_EXPANDED_TYPE;
        Intrinsics.checkNotNullExpressionValue(EXPOSED_TYPEALIAS_EXPANDED_TYPE, "EXPOSED_TYPEALIAS_EXPANDED_TYPE");
        KtTypeAlias nameIdentifier = typeAlias.mo7392getNameIdentifier();
        if (nameIdentifier == null) {
            nameIdentifier = typeAlias;
        }
        reportExposure(EXPOSED_TYPEALIAS_EXPANDED_TYPE, nameIdentifier, effectiveVisibilityEffectiveVisibility$default, descriptorWithRelationLeastPermissiveDescriptor);
    }

    public static /* synthetic */ boolean checkFunction$default(ExposedVisibilityChecker exposedVisibilityChecker, KtFunction ktFunction, FunctionDescriptor functionDescriptor, DescriptorVisibility descriptorVisibility, int i, Object obj) {
        if ((i & 4) != 0) {
            descriptorVisibility = functionDescriptor.getVisibility();
            Intrinsics.checkNotNullExpressionValue(descriptorVisibility, "getVisibility(...)");
        }
        return exposedVisibilityChecker.checkFunction(ktFunction, functionDescriptor, descriptorVisibility);
    }

    /* JADX WARN: Code duplicated, block: B:20:0x0069  */
    public final boolean checkFunction(KtFunction function, FunctionDescriptor functionDescriptor, DescriptorVisibility visibility) {
        boolean z;
        Intrinsics.checkNotNullParameter(function, "function");
        Intrinsics.checkNotNullParameter(functionDescriptor, "functionDescriptor");
        Intrinsics.checkNotNullParameter(visibility, "visibility");
        EffectiveVisibility effectiveVisibilityEffectiveVisibility$default = EffectiveVisibilityUtilsKt.effectiveVisibility$default((DeclarationDescriptorWithVisibility) functionDescriptor, visibility, false, 2, (Object) null);
        if (functionDescriptor instanceof ConstructorDescriptor) {
            ClassDescriptor constructedClass = ((ConstructorDescriptor) functionDescriptor).getConstructedClass();
            Intrinsics.checkNotNullExpressionValue(constructedClass, "getConstructedClass(...)");
            if (DescriptorUtilKt.isSealed(constructedClass) && KtPsiUtilKt.visibilityModifier(function) == null) {
                effectiveVisibilityEffectiveVisibility$default = EffectiveVisibility.PrivateInClass.INSTANCE;
            }
        }
        if (function instanceof KtConstructor) {
            z = true;
        } else {
            KotlinType returnType = functionDescriptor.getReturnType();
            DescriptorWithRelation descriptorWithRelationLeastPermissiveDescriptor = returnType != null ? EffectiveVisibilityUtilsKt.leastPermissiveDescriptor(returnType, effectiveVisibilityEffectiveVisibility$default) : null;
            if (descriptorWithRelationLeastPermissiveDescriptor != null) {
                DiagnosticFactory3<PsiElement, EffectiveVisibility, DescriptorWithRelation, EffectiveVisibility> EXPOSED_FUNCTION_RETURN_TYPE = Errors.EXPOSED_FUNCTION_RETURN_TYPE;
                Intrinsics.checkNotNullExpressionValue(EXPOSED_FUNCTION_RETURN_TYPE, "EXPOSED_FUNCTION_RETURN_TYPE");
                KtFunction nameIdentifier = function.mo7392getNameIdentifier();
                if (nameIdentifier == null) {
                    nameIdentifier = function;
                }
                reportExposure(EXPOSED_FUNCTION_RETURN_TYPE, nameIdentifier, effectiveVisibilityEffectiveVisibility$default, descriptorWithRelationLeastPermissiveDescriptor);
                z = false;
            } else {
                z = true;
            }
        }
        List<ValueParameterDescriptor> valueParameters = functionDescriptor.getValueParameters();
        Intrinsics.checkNotNullExpressionValue(valueParameters, "getValueParameters(...)");
        int i = 0;
        for (Object obj : valueParameters) {
            int i2 = i + 1;
            if (i < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            ValueParameterDescriptor valueParameterDescriptor = (ValueParameterDescriptor) obj;
            if (i < function.getValueParameters().size()) {
                KtParameter ktParameter = function.getValueParameters().get(i);
                KotlinType type = valueParameterDescriptor.getType();
                Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
                DescriptorWithRelation descriptorWithRelationLeastPermissiveDescriptor2 = EffectiveVisibilityUtilsKt.leastPermissiveDescriptor(type, effectiveVisibilityEffectiveVisibility$default);
                if (descriptorWithRelationLeastPermissiveDescriptor2 != null) {
                    DiagnosticFactory3<KtParameter, EffectiveVisibility, DescriptorWithRelation, EffectiveVisibility> EXPOSED_PARAMETER_TYPE = Errors.EXPOSED_PARAMETER_TYPE;
                    Intrinsics.checkNotNullExpressionValue(EXPOSED_PARAMETER_TYPE, "EXPOSED_PARAMETER_TYPE");
                    reportExposure(EXPOSED_PARAMETER_TYPE, ktParameter, effectiveVisibilityEffectiveVisibility$default, descriptorWithRelationLeastPermissiveDescriptor2);
                } else if ((functionDescriptor instanceof ClassConstructorDescriptor) && ktParameter.hasValOrVar()) {
                    BindingTrace bindingTrace = this.trace;
                    DeclarationDescriptorWithVisibility constructedClass2 = bindingTrace != null ? (PropertyDescriptor) bindingTrace.get(BindingContext.VALUE_PARAMETER_AS_PROPERTY, valueParameterDescriptor) : null;
                    if (constructedClass2 == null) {
                        constructedClass2 = ((ClassConstructorDescriptor) functionDescriptor).getConstructedClass();
                        Intrinsics.checkNotNullExpressionValue(constructedClass2, "getConstructedClass(...)");
                    }
                    EffectiveVisibility effectiveVisibilityEffectiveVisibility$default2 = EffectiveVisibilityUtilsKt.effectiveVisibility$default(constructedClass2, (DescriptorVisibility) null, false, 3, (Object) null);
                    KotlinType type2 = valueParameterDescriptor.getType();
                    Intrinsics.checkNotNullExpressionValue(type2, "getType(...)");
                    DescriptorWithRelation descriptorWithRelationLeastPermissiveDescriptor3 = EffectiveVisibilityUtilsKt.leastPermissiveDescriptor(type2, effectiveVisibilityEffectiveVisibility$default2);
                    if (descriptorWithRelationLeastPermissiveDescriptor3 != null) {
                        DiagnosticFactoryForDeprecation3<PsiElement, EffectiveVisibility, DescriptorWithRelation, EffectiveVisibility> EXPOSED_PROPERTY_TYPE_IN_CONSTRUCTOR = Errors.EXPOSED_PROPERTY_TYPE_IN_CONSTRUCTOR;
                        Intrinsics.checkNotNullExpressionValue(EXPOSED_PROPERTY_TYPE_IN_CONSTRUCTOR, "EXPOSED_PROPERTY_TYPE_IN_CONSTRUCTOR");
                        KtParameter ktParameterMo7392getNameIdentifier = ktParameter.mo7392getNameIdentifier();
                        if (ktParameterMo7392getNameIdentifier == null) {
                            ktParameterMo7392getNameIdentifier = ktParameter;
                        }
                        reportExposureForDeprecation(EXPOSED_PROPERTY_TYPE_IN_CONSTRUCTOR, ktParameterMo7392getNameIdentifier, effectiveVisibilityEffectiveVisibility$default2, descriptorWithRelationLeastPermissiveDescriptor3);
                    }
                }
                z = false;
            }
            i = i2;
        }
        return checkMemberReceiver(function.mo7393getReceiverTypeReference(), functionDescriptor, visibility) & z;
    }

    public static /* synthetic */ boolean checkProperty$default(ExposedVisibilityChecker exposedVisibilityChecker, KtProperty ktProperty, PropertyDescriptor propertyDescriptor, DescriptorVisibility descriptorVisibility, int i, Object obj) {
        if ((i & 4) != 0) {
            descriptorVisibility = propertyDescriptor.getVisibility();
            Intrinsics.checkNotNullExpressionValue(descriptorVisibility, "getVisibility(...)");
        }
        return exposedVisibilityChecker.checkProperty(ktProperty, propertyDescriptor, descriptorVisibility);
    }

    public final boolean checkProperty(KtProperty property, PropertyDescriptor propertyDescriptor, DescriptorVisibility visibility) {
        Intrinsics.checkNotNullParameter(property, "property");
        Intrinsics.checkNotNullParameter(propertyDescriptor, "propertyDescriptor");
        Intrinsics.checkNotNullParameter(visibility, "visibility");
        boolean z = false;
        EffectiveVisibility effectiveVisibilityEffectiveVisibility$default = EffectiveVisibilityUtilsKt.effectiveVisibility$default((DeclarationDescriptorWithVisibility) propertyDescriptor, visibility, false, 2, (Object) null);
        KotlinType type = propertyDescriptor.getType();
        Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
        DescriptorWithRelation descriptorWithRelationLeastPermissiveDescriptor = EffectiveVisibilityUtilsKt.leastPermissiveDescriptor(type, effectiveVisibilityEffectiveVisibility$default);
        if (descriptorWithRelationLeastPermissiveDescriptor != null) {
            DiagnosticFactory3<PsiElement, EffectiveVisibility, DescriptorWithRelation, EffectiveVisibility> EXPOSED_PROPERTY_TYPE = Errors.EXPOSED_PROPERTY_TYPE;
            Intrinsics.checkNotNullExpressionValue(EXPOSED_PROPERTY_TYPE, "EXPOSED_PROPERTY_TYPE");
            KtProperty nameIdentifier = property.mo7392getNameIdentifier();
            if (nameIdentifier == null) {
                nameIdentifier = property;
            }
            reportExposure(EXPOSED_PROPERTY_TYPE, nameIdentifier, effectiveVisibilityEffectiveVisibility$default, descriptorWithRelationLeastPermissiveDescriptor);
        } else {
            z = true;
        }
        return checkMemberReceiver(property.mo7393getReceiverTypeReference(), propertyDescriptor, visibility) & z;
    }

    private final boolean checkMemberReceiver(KtTypeReference typeReference, CallableMemberDescriptor memberDescriptor, DescriptorVisibility visibility) {
        ReceiverParameterDescriptor extensionReceiverParameter;
        if (typeReference == null || (extensionReceiverParameter = memberDescriptor.getExtensionReceiverParameter()) == null) {
            return true;
        }
        EffectiveVisibility effectiveVisibilityEffectiveVisibility$default = EffectiveVisibilityUtilsKt.effectiveVisibility$default((DeclarationDescriptorWithVisibility) memberDescriptor, visibility, false, 2, (Object) null);
        KotlinType type = extensionReceiverParameter.getType();
        Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
        DescriptorWithRelation descriptorWithRelationLeastPermissiveDescriptor = EffectiveVisibilityUtilsKt.leastPermissiveDescriptor(type, effectiveVisibilityEffectiveVisibility$default);
        if (descriptorWithRelationLeastPermissiveDescriptor == null) {
            return true;
        }
        DiagnosticFactory3<KtTypeReference, EffectiveVisibility, DescriptorWithRelation, EffectiveVisibility> EXPOSED_RECEIVER_TYPE = Errors.EXPOSED_RECEIVER_TYPE;
        Intrinsics.checkNotNullExpressionValue(EXPOSED_RECEIVER_TYPE, "EXPOSED_RECEIVER_TYPE");
        reportExposure(EXPOSED_RECEIVER_TYPE, typeReference, effectiveVisibilityEffectiveVisibility$default, descriptorWithRelationLeastPermissiveDescriptor);
        return false;
    }

    private final boolean checkSupertypes(KtClassOrObject klass, ClassDescriptor classDescriptor) {
        EffectiveVisibility effectiveVisibilityEffectiveVisibility$default = EffectiveVisibilityUtilsKt.effectiveVisibility$default(classDescriptor, false, 1, null);
        boolean z = classDescriptor.getClassKind() == ClassKind.INTERFACE;
        List<KtSuperTypeListEntry> superTypeListEntries = klass.getSuperTypeListEntries();
        Collection<KotlinType> collectionMo7438getSupertypes = classDescriptor.getTypeConstructor().mo7438getSupertypes();
        Intrinsics.checkNotNullExpressionValue(collectionMo7438getSupertypes, "getSupertypes(...)");
        int i = 0;
        boolean z2 = true;
        for (Object obj : collectionMo7438getSupertypes) {
            int i2 = i + 1;
            if (i < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            KotlinType kotlinType = (KotlinType) obj;
            if (i >= superTypeListEntries.size()) {
                return z2;
            }
            ClassDescriptor classDescriptor2 = TypeUtils.getClassDescriptor(kotlinType);
            if (classDescriptor2 != null) {
                Intrinsics.checkNotNull(classDescriptor2);
                if ((classDescriptor2.getClassKind() == ClassKind.INTERFACE) == z) {
                    Intrinsics.checkNotNull(kotlinType);
                    DescriptorWithRelation descriptorWithRelationLeastPermissiveDescriptor = EffectiveVisibilityUtilsKt.leastPermissiveDescriptor(kotlinType, effectiveVisibilityEffectiveVisibility$default);
                    if (descriptorWithRelationLeastPermissiveDescriptor != null) {
                        DiagnosticFactory3<KtSuperTypeListEntry, EffectiveVisibility, DescriptorWithRelation, EffectiveVisibility> diagnosticFactory3 = z ? Errors.EXPOSED_SUPER_INTERFACE : Errors.EXPOSED_SUPER_CLASS;
                        Intrinsics.checkNotNull(diagnosticFactory3);
                        reportExposure(diagnosticFactory3, superTypeListEntries.get(i), effectiveVisibilityEffectiveVisibility$default, descriptorWithRelationLeastPermissiveDescriptor);
                        z2 = false;
                    }
                }
            }
            i = i2;
        }
        return z2;
    }

    private final boolean checkParameterBounds(KtClassOrObject klass, ClassDescriptor classDescriptor) {
        boolean z = true;
        EffectiveVisibility effectiveVisibilityEffectiveVisibility$default = EffectiveVisibilityUtilsKt.effectiveVisibility$default(classDescriptor, false, 1, null);
        List<KtTypeParameter> typeParameters = klass.getTypeParameters();
        Intrinsics.checkNotNullExpressionValue(typeParameters, "getTypeParameters(...)");
        List<TypeParameterDescriptor> declaredTypeParameters = classDescriptor.getDeclaredTypeParameters();
        Intrinsics.checkNotNullExpressionValue(declaredTypeParameters, "getDeclaredTypeParameters(...)");
        int i = 0;
        for (Object obj : declaredTypeParameters) {
            int i2 = i + 1;
            if (i < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            TypeParameterDescriptor typeParameterDescriptor = (TypeParameterDescriptor) obj;
            if (i >= typeParameters.size()) {
                return z;
            }
            for (KotlinType kotlinType : typeParameterDescriptor.getUpperBounds()) {
                Intrinsics.checkNotNull(kotlinType);
                DescriptorWithRelation descriptorWithRelationLeastPermissiveDescriptor = EffectiveVisibilityUtilsKt.leastPermissiveDescriptor(kotlinType, effectiveVisibilityEffectiveVisibility$default);
                if (descriptorWithRelationLeastPermissiveDescriptor != null) {
                    DiagnosticFactory3<KtTypeParameter, EffectiveVisibility, DescriptorWithRelation, EffectiveVisibility> EXPOSED_TYPE_PARAMETER_BOUND = Errors.EXPOSED_TYPE_PARAMETER_BOUND;
                    Intrinsics.checkNotNullExpressionValue(EXPOSED_TYPE_PARAMETER_BOUND, "EXPOSED_TYPE_PARAMETER_BOUND");
                    reportExposure(EXPOSED_TYPE_PARAMETER_BOUND, typeParameters.get(i), effectiveVisibilityEffectiveVisibility$default, descriptorWithRelationLeastPermissiveDescriptor);
                    z = false;
                    break;
                }
            }
            i = i2;
        }
        return z;
    }
}

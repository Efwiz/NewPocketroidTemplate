package org.jetbrains.kotlin.resolve.calls.smartcasts;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cfg.CfgContainingDeclarationUtilsKt;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptorWithVisibility;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.descriptors.ModalityUtilsKt;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.descriptors.ParameterDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyGetterDescriptor;
import org.jetbrains.kotlin.descriptors.VariableDescriptor;
import org.jetbrains.kotlin.descriptors.impl.LocalVariableDescriptor;
import org.jetbrains.kotlin.descriptors.impl.SyntheticFieldDescriptor;
import org.jetbrains.kotlin.psi.KtDeclaration;
import org.jetbrains.kotlin.psi.KtElement;
import org.jetbrains.kotlin.psi.psiUtil.PsiUtilsKt;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.DescriptorUtils;
import org.jetbrains.kotlin.types.expressions.AssignedVariablesSearcher;
import org.jetbrains.kotlin.types.expressions.PreliminaryDeclarationVisitor;

/* JADX INFO: compiled from: DataFlowValueKindUtils.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000P\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u001a\u0010\u0000\u001a\u00020\u00012\b\u0010\u0002\u001a\u0004\u0018\u00010\u00032\u0006\u0010\u0004\u001a\u00020\u0003H\u0002\u001a$\u0010\u0005\u001a\u00020\u00012\u0006\u0010\u0006\u001a\u00020\u00072\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t2\u0006\u0010\u000b\u001a\u00020\f\u001a.\u0010\r\u001a\u00020\u00012\u0006\u0010\u0006\u001a\u00020\u00072\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\u000e\u001a\u00020\u000fH\u0002\u001a \u0010\u0010\u001a\u00020\u00012\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\u000e\u001a\u00020\u000fH\u0002\u001a\u001e\u0010\u0011\u001a\u00020\u00012\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t2\u0006\u0010\u000e\u001a\u00020\u000fH\u0002\u001a\f\u0010\u0012\u001a\u00020\u0001*\u00020\u0013H\u0002\u001a\u0016\u0010\u0014\u001a\u00020\u0001*\u00020\u00132\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003H\u0002\u001a\f\u0010\u0015\u001a\u00020\u0001*\u00020\u0016H\u0002\u001a\u001e\u0010\u0017\u001a\u00020\u0018*\u00020\u00132\b\u0010\u0002\u001a\u0004\u0018\u00010\u00032\u0006\u0010\u0019\u001a\u00020\u001aH\u0000\u001a.\u0010\u001b\u001a\u00020\u0018*\u00020\u001c2\b\u0010\u0002\u001a\u0004\u0018\u00010\u00032\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0019\u001a\u00020\u001aH\u0000¨\u0006\u001d"}, d2 = {"areCompiledTogether", "", "usageModule", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "declarationModule", "hasNoWritersInClosures", "variableContainingDeclaration", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "writers", "", "Lorg/jetbrains/kotlin/types/expressions/AssignedVariablesSearcher$Writer;", "bindingContext", "Lorg/jetbrains/kotlin/resolve/BindingContext;", "isAccessedBeforeAllClosureWriters", "accessElement", "Lorg/jetbrains/kotlin/psi/KtElement;", "isAccessedInsideClosure", "isAccessedInsideClosureAfterAllWriters", "hasDefaultGetter", "Lorg/jetbrains/kotlin/descriptors/PropertyDescriptor;", "isDeclaredInAnotherModule", "isInvisibleFromOtherModules", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptorWithVisibility;", "propertyKind", "Lorg/jetbrains/kotlin/resolve/calls/smartcasts/DataFlowValue$Kind;", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "variableKind", "Lorg/jetbrains/kotlin/descriptors/VariableDescriptor;", "frontend"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class DataFlowValueKindUtilsKt {
    public static final DataFlowValue.Kind propertyKind(PropertyDescriptor propertyDescriptor, ModuleDescriptor moduleDescriptor, LanguageVersionSettings languageVersionSettings) {
        Intrinsics.checkNotNullParameter(propertyDescriptor, "<this>");
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        if (propertyDescriptor.isVar()) {
            return DataFlowValue.Kind.MUTABLE_PROPERTY;
        }
        PropertyDescriptor propertyDescriptor2 = propertyDescriptor;
        if (!ModalityUtilsKt.isOverridable(propertyDescriptor2) && hasDefaultGetter(propertyDescriptor)) {
            CallableMemberDescriptor callableMemberDescriptorUnwrapFakeOverride = DescriptorUtils.unwrapFakeOverride(propertyDescriptor2);
            Intrinsics.checkNotNullExpressionValue(callableMemberDescriptorUnwrapFakeOverride, "unwrapFakeOverride(...)");
            PropertyDescriptor propertyDescriptor3 = (PropertyDescriptor) callableMemberDescriptorUnwrapFakeOverride;
            boolean zIsInvisibleFromOtherModules = isInvisibleFromOtherModules(propertyDescriptor3);
            if (zIsInvisibleFromOtherModules) {
                return DataFlowValue.Kind.STABLE_VALUE;
            }
            if (propertyDescriptor.getKind() == CallableMemberDescriptor.Kind.FAKE_OVERRIDE) {
                Collection<? extends PropertyDescriptor> overriddenDescriptors = propertyDescriptor.getOverriddenDescriptors();
                Intrinsics.checkNotNullExpressionValue(overriddenDescriptors, "getOverriddenDescriptors(...)");
                Collection<? extends PropertyDescriptor> collection = overriddenDescriptors;
                if (!collection.isEmpty()) {
                    for (PropertyDescriptor propertyDescriptor4 : collection) {
                        if (isDeclaredInAnotherModule(propertyDescriptor, moduleDescriptor)) {
                            if (isInvisibleFromOtherModules(propertyDescriptor) != zIsInvisibleFromOtherModules && !languageVersionSettings.supportsFeature(LanguageFeature.ProhibitSmartcastsOnPropertyFromAlienBaseClassInheritedInInvisibleClass)) {
                                return DataFlowValue.Kind.LEGACY_ALIEN_BASE_PROPERTY_INHERITED_IN_INVISIBLE_CLASS;
                            }
                            if (!languageVersionSettings.supportsFeature(LanguageFeature.ProhibitSmartcastsOnPropertyFromAlienBaseClass)) {
                                return DataFlowValue.Kind.LEGACY_ALIEN_BASE_PROPERTY;
                            }
                            return DataFlowValue.Kind.ALIEN_PUBLIC_PROPERTY;
                        }
                    }
                }
            }
            ModuleDescriptor containingModule = DescriptorUtils.getContainingModule(propertyDescriptor3);
            Intrinsics.checkNotNullExpressionValue(containingModule, "getContainingModule(...)");
            return !areCompiledTogether(moduleDescriptor, containingModule) ? DataFlowValue.Kind.ALIEN_PUBLIC_PROPERTY : DataFlowValue.Kind.STABLE_VALUE;
        }
        return DataFlowValue.Kind.PROPERTY_WITH_GETTER;
    }

    private static final boolean isDeclaredInAnotherModule(PropertyDescriptor propertyDescriptor, ModuleDescriptor moduleDescriptor) {
        ModuleDescriptor containingModule = DescriptorUtils.getContainingModule(propertyDescriptor);
        Intrinsics.checkNotNullExpressionValue(containingModule, "getContainingModule(...)");
        if (!areCompiledTogether(moduleDescriptor, containingModule)) {
            return true;
        }
        if (propertyDescriptor.getKind() != CallableMemberDescriptor.Kind.FAKE_OVERRIDE) {
            return false;
        }
        Collection<? extends PropertyDescriptor> overriddenDescriptors = propertyDescriptor.getOverriddenDescriptors();
        Intrinsics.checkNotNullExpressionValue(overriddenDescriptors, "getOverriddenDescriptors(...)");
        Collection<? extends PropertyDescriptor> collection = overriddenDescriptors;
        if (!collection.isEmpty()) {
            for (PropertyDescriptor propertyDescriptor2 : collection) {
                Intrinsics.checkNotNull(propertyDescriptor2);
                if (isDeclaredInAnotherModule(propertyDescriptor2, moduleDescriptor)) {
                    return true;
                }
            }
        }
        return false;
    }

    private static final boolean areCompiledTogether(ModuleDescriptor moduleDescriptor, ModuleDescriptor moduleDescriptor2) {
        if (moduleDescriptor == null) {
            return false;
        }
        if (Intrinsics.areEqual(moduleDescriptor, moduleDescriptor2)) {
            return true;
        }
        return moduleDescriptor.getAllExpectedByModules().contains(moduleDescriptor2);
    }

    public static final DataFlowValue.Kind variableKind(VariableDescriptor variableDescriptor, ModuleDescriptor moduleDescriptor, BindingContext bindingContext, KtElement accessElement, LanguageVersionSettings languageVersionSettings) {
        Intrinsics.checkNotNullParameter(variableDescriptor, "<this>");
        Intrinsics.checkNotNullParameter(bindingContext, "bindingContext");
        Intrinsics.checkNotNullParameter(accessElement, "accessElement");
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        if (variableDescriptor instanceof PropertyDescriptor) {
            return propertyKind((PropertyDescriptor) variableDescriptor, moduleDescriptor, languageVersionSettings);
        }
        boolean z = variableDescriptor instanceof LocalVariableDescriptor;
        if (z && ((LocalVariableDescriptor) variableDescriptor).getIsDelegated()) {
            if (languageVersionSettings.supportsFeature(LanguageFeature.ProhibitSmartcastsOnLocalDelegatedProperty)) {
                return DataFlowValue.Kind.PROPERTY_WITH_GETTER;
            }
            return DataFlowValue.Kind.LEGACY_STABLE_LOCAL_DELEGATED_PROPERTY;
        }
        if (!z && !(variableDescriptor instanceof ParameterDescriptor)) {
            return DataFlowValue.Kind.OTHER;
        }
        if (!variableDescriptor.isVar()) {
            return DataFlowValue.Kind.STABLE_VALUE;
        }
        if (variableDescriptor instanceof SyntheticFieldDescriptor) {
            return DataFlowValue.Kind.MUTABLE_PROPERTY;
        }
        PreliminaryDeclarationVisitor visitorByVariable = PreliminaryDeclarationVisitor.INSTANCE.getVisitorByVariable(variableDescriptor, bindingContext);
        if (visitorByVariable == null) {
            return DataFlowValue.Kind.CAPTURED_VARIABLE;
        }
        Set<AssignedVariablesSearcher.Writer> setWriters = visitorByVariable.writers(variableDescriptor);
        if (setWriters.isEmpty()) {
            return DataFlowValue.Kind.STABLE_VARIABLE;
        }
        DeclarationDescriptor containingDeclaration = variableDescriptor.getContainingModule();
        Intrinsics.checkNotNullExpressionValue(containingDeclaration, "getContainingDeclaration(...)");
        if (isAccessedInsideClosure(containingDeclaration, bindingContext, accessElement)) {
            if (visitorByVariable.getLanguageVersionSettings().supportsFeature(LanguageFeature.CapturedInClosureSmartCasts) && hasNoWritersInClosures(containingDeclaration, setWriters, bindingContext) && isAccessedInsideClosureAfterAllWriters(setWriters, accessElement)) {
                return DataFlowValue.Kind.STABLE_VARIABLE;
            }
            return DataFlowValue.Kind.CAPTURED_VARIABLE;
        }
        if (isAccessedBeforeAllClosureWriters(containingDeclaration, setWriters, bindingContext, accessElement)) {
            return DataFlowValue.Kind.STABLE_VARIABLE;
        }
        return DataFlowValue.Kind.CAPTURED_VARIABLE;
    }

    public static final boolean hasNoWritersInClosures(DeclarationDescriptor variableContainingDeclaration, Set<AssignedVariablesSearcher.Writer> writers, BindingContext bindingContext) {
        Intrinsics.checkNotNullParameter(variableContainingDeclaration, "variableContainingDeclaration");
        Intrinsics.checkNotNullParameter(writers, "writers");
        Intrinsics.checkNotNullParameter(bindingContext, "bindingContext");
        Set<AssignedVariablesSearcher.Writer> set = writers;
        if ((set instanceof Collection) && set.isEmpty()) {
            return true;
        }
        Iterator<T> it = set.iterator();
        while (it.hasNext()) {
            KtDeclaration ktDeclarationComponent2 = ((AssignedVariablesSearcher.Writer) it.next()).getDeclaration();
            if (ktDeclarationComponent2 != null && !Intrinsics.areEqual(variableContainingDeclaration, CfgContainingDeclarationUtilsKt.getDeclarationDescriptorIncludingConstructors(ktDeclarationComponent2, bindingContext))) {
                return false;
            }
        }
        return true;
    }

    private static final boolean isAccessedInsideClosureAfterAllWriters(Set<AssignedVariablesSearcher.Writer> set, KtElement ktElement) {
        KtDeclaration elementParentDeclaration = CfgContainingDeclarationUtilsKt.getElementParentDeclaration(ktElement);
        if (elementParentDeclaration == null) {
            return false;
        }
        Set<AssignedVariablesSearcher.Writer> set2 = set;
        if (!(set2 instanceof Collection) || !set2.isEmpty()) {
            Iterator<T> it = set2.iterator();
            while (it.hasNext()) {
                if (!PsiUtilsKt.before(((AssignedVariablesSearcher.Writer) it.next()).getAssignment(), elementParentDeclaration)) {
                    return false;
                }
            }
        }
        return true;
    }

    private static final boolean isAccessedBeforeAllClosureWriters(DeclarationDescriptor declarationDescriptor, Set<AssignedVariablesSearcher.Writer> set, BindingContext bindingContext, KtElement ktElement) {
        ArrayList<KtDeclaration> arrayList = new ArrayList();
        Iterator<T> it = set.iterator();
        while (it.hasNext()) {
            KtDeclaration declaration = ((AssignedVariablesSearcher.Writer) it.next()).getDeclaration();
            if (declaration != null) {
                arrayList.add(declaration);
            }
        }
        for (KtDeclaration ktDeclaration : arrayList) {
            if (!Intrinsics.areEqual(declarationDescriptor, CfgContainingDeclarationUtilsKt.getDeclarationDescriptorIncludingConstructors(ktDeclaration, bindingContext)) && !PsiUtilsKt.before(ktElement, ktDeclaration)) {
                return false;
            }
        }
        return true;
    }

    private static final boolean isInvisibleFromOtherModules(DeclarationDescriptorWithVisibility declarationDescriptorWithVisibility) {
        if (DescriptorVisibilities.INVISIBLE_FROM_OTHER_MODULES.contains(declarationDescriptorWithVisibility.getVisibilityImpl())) {
            return true;
        }
        DeclarationDescriptor containingDeclaration = declarationDescriptorWithVisibility.getContainingModule();
        return (containingDeclaration instanceof DeclarationDescriptorWithVisibility) && isInvisibleFromOtherModules((DeclarationDescriptorWithVisibility) containingDeclaration);
    }

    private static final boolean hasDefaultGetter(PropertyDescriptor propertyDescriptor) {
        PropertyGetterDescriptor getter = propertyDescriptor.getGetter();
        return getter == null || getter.isDefault();
    }

    private static final boolean isAccessedInsideClosure(DeclarationDescriptor declarationDescriptor, BindingContext bindingContext, KtElement ktElement) {
        KtDeclaration elementParentDeclaration = CfgContainingDeclarationUtilsKt.getElementParentDeclaration(ktElement);
        return (elementParentDeclaration == null || Intrinsics.areEqual(CfgContainingDeclarationUtilsKt.getDeclarationDescriptorIncludingConstructors(elementParentDeclaration, bindingContext), declarationDescriptor)) ? false : true;
    }
}

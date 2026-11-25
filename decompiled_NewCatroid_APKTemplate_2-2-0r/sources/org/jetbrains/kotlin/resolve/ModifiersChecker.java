package org.jetbrains.kotlin.resolve;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.jetbrains.kotlin.com.intellij.openapi.progress.ProgressManager;
import org.jetbrains.kotlin.com.intellij.psi.CommonClassNames;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.descriptors.DescriptorVisibility;
import org.jetbrains.kotlin.descriptors.MemberDescriptor;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.diagnostics.DiagnosticFactory1;
import org.jetbrains.kotlin.diagnostics.Errors;
import org.jetbrains.kotlin.extensions.DeclarationAttributeAltererExtension;
import org.jetbrains.kotlin.incremental.components.ExpectActualTracker;
import org.jetbrains.kotlin.lexer.KtKeywordToken;
import org.jetbrains.kotlin.lexer.KtModifierKeywordToken;
import org.jetbrains.kotlin.lexer.KtTokens;
import org.jetbrains.kotlin.psi.KtClassOrObject;
import org.jetbrains.kotlin.psi.KtDeclaration;
import org.jetbrains.kotlin.psi.KtDestructuringDeclaration;
import org.jetbrains.kotlin.psi.KtDestructuringDeclarationEntry;
import org.jetbrains.kotlin.psi.KtModifierList;
import org.jetbrains.kotlin.psi.KtModifierListOwner;
import org.jetbrains.kotlin.psi.KtTypeParameter;
import org.jetbrains.kotlin.psi.KtTypeParameterListOwner;
import org.jetbrains.kotlin.psi.KtValVarKeywordOwner;
import org.jetbrains.kotlin.resolve.checkers.DeclarationChecker;
import org.jetbrains.kotlin.resolve.checkers.DeclarationCheckerContext;
import org.jetbrains.kotlin.resolve.checkers.OptionalExpectationChecker;
import org.jetbrains.kotlin.resolve.checkers.PublishedApiUsageChecker;
import org.jetbrains.kotlin.resolve.checkers.UnderscoreChecker;
import org.jetbrains.kotlin.resolve.deprecation.DeprecationResolver;

/* JADX INFO: loaded from: classes6.dex */
public class ModifiersChecker {
    private final AnnotationChecker annotationChecker;
    private final Iterable<DeclarationChecker> declarationCheckers;
    private final DeprecationResolver deprecationResolver;
    private final ExpectActualTracker expectActualTracker;
    private final LanguageVersionSettings languageVersionSettings;
    private final MissingSupertypesResolver missingSupertypesResolver;
    private final ModuleDescriptor moduleDescriptor;

    /* JADX WARN: Code duplicated, block: B:35:0x0072  */
    /* JADX WARN: Code duplicated, block: B:7:0x000d  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        if (i != 4 && i != 17) {
            switch (i) {
                case 6:
                case 7:
                case 8:
                case 9:
                case 10:
                case 11:
                case 12:
                case 13:
                case 14:
                    str = "@NotNull method %s.%s must not return null";
                    break;
                default:
                    str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                    break;
            }
        } else {
            str = "@NotNull method %s.%s must not return null";
        }
        if (i != 4 && i != 17) {
            switch (i) {
                case 6:
                case 7:
                case 8:
                case 9:
                case 10:
                case 11:
                case 12:
                case 13:
                case 14:
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
            case 3:
                objArr[0] = "bindingContext";
                break;
            case 2:
            case 5:
            default:
                objArr[0] = "defaultModality";
                break;
            case 4:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 17:
                objArr[0] = "org/jetbrains/kotlin/resolve/ModifiersChecker";
                break;
            case 15:
                objArr[0] = "modifierListOwner";
                break;
            case 16:
            case 18:
                objArr[0] = "defaultVisibility";
                break;
            case 19:
                objArr[0] = "annotationChecker";
                break;
            case 20:
                objArr[0] = "declarationCheckers";
                break;
            case 21:
                objArr[0] = "languageVersionSettings";
                break;
            case 22:
                objArr[0] = "expectActualTracker";
                break;
            case 23:
                objArr[0] = "deprecationResolver";
                break;
            case 24:
                objArr[0] = "moduleDescriptor";
                break;
            case 25:
                objArr[0] = "missingSupertypesResolver";
                break;
            case 26:
                objArr[0] = "trace";
                break;
        }
        if (i == 4) {
            objArr[1] = "resolveModalityFromModifiers";
        } else if (i != 17) {
            switch (i) {
                case 6:
                case 7:
                case 8:
                case 9:
                case 10:
                case 11:
                case 12:
                case 13:
                case 14:
                    objArr[1] = "resolveModalityFromModifiers";
                    break;
                default:
                    objArr[1] = "org/jetbrains/kotlin/resolve/ModifiersChecker";
                    break;
            }
        } else {
            objArr[1] = "resolveVisibilityFromModifiers";
        }
        switch (i) {
            case 2:
            case 3:
            case 5:
                objArr[2] = "resolveModalityFromModifiers";
                break;
            case 4:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 17:
                break;
            case 15:
            case 16:
            case 18:
                objArr[2] = "resolveVisibilityFromModifiers";
                break;
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
            case 24:
            case 25:
                objArr[2] = "<init>";
                break;
            case 26:
                objArr[2] = "withTrace";
                break;
            default:
                objArr[2] = "resolveMemberModalityFromModifiers";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 4 && i != 17) {
            switch (i) {
                case 6:
                case 7:
                case 8:
                case 9:
                case 10:
                case 11:
                case 12:
                case 13:
                case 14:
                    break;
                default:
                    throw new IllegalArgumentException(str2);
            }
        }
        throw new IllegalStateException(str2);
    }

    private enum DetailedClassKind {
        ENUM_CLASS("Enum class"),
        ENUM_ENTRY("Enum entry"),
        ANNOTATION_CLASS("Annotation class"),
        INTERFACE("Interface"),
        COMPANION_OBJECT("Companion object"),
        ANONYMOUS_OBJECT("Anonymous object"),
        OBJECT(CommonClassNames.JAVA_LANG_OBJECT_SHORT),
        CLASS("Class");

        public final String withCapitalFirstLetter;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            String str;
            int i2;
            switch (i) {
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                case 8:
                    str = "@NotNull method %s.%s must not return null";
                    break;
                default:
                    str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                    break;
            }
            switch (i) {
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                case 8:
                    i2 = 2;
                    break;
                default:
                    i2 = 3;
                    break;
            }
            Object[] objArr = new Object[i2];
            switch (i) {
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                case 8:
                    objArr[0] = "org/jetbrains/kotlin/resolve/ModifiersChecker$DetailedClassKind";
                    break;
                default:
                    objArr[0] = "descriptor";
                    break;
            }
            switch (i) {
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                case 8:
                    objArr[1] = "getClassKind";
                    break;
                default:
                    objArr[1] = "org/jetbrains/kotlin/resolve/ModifiersChecker$DetailedClassKind";
                    break;
            }
            switch (i) {
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                case 8:
                    break;
                default:
                    objArr[2] = "getClassKind";
                    break;
            }
            String str2 = String.format(str, objArr);
            switch (i) {
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                case 8:
                    throw new IllegalStateException(str2);
                default:
                    throw new IllegalArgumentException(str2);
            }
        }

        DetailedClassKind(String str) {
            this.withCapitalFirstLetter = str;
        }

        public static DetailedClassKind getClassKind(ClassDescriptor classDescriptor) {
            if (classDescriptor == null) {
                $$$reportNull$$$0(0);
            }
            if (DescriptorUtils.isEnumEntry(classDescriptor)) {
                DetailedClassKind detailedClassKind = ENUM_ENTRY;
                if (detailedClassKind == null) {
                    $$$reportNull$$$0(1);
                }
                return detailedClassKind;
            }
            if (DescriptorUtils.isEnumClass(classDescriptor)) {
                DetailedClassKind detailedClassKind2 = ENUM_CLASS;
                if (detailedClassKind2 == null) {
                    $$$reportNull$$$0(2);
                }
                return detailedClassKind2;
            }
            if (DescriptorUtils.isAnnotationClass(classDescriptor)) {
                DetailedClassKind detailedClassKind3 = ANNOTATION_CLASS;
                if (detailedClassKind3 == null) {
                    $$$reportNull$$$0(3);
                }
                return detailedClassKind3;
            }
            if (DescriptorUtils.isInterface(classDescriptor)) {
                DetailedClassKind detailedClassKind4 = INTERFACE;
                if (detailedClassKind4 == null) {
                    $$$reportNull$$$0(4);
                }
                return detailedClassKind4;
            }
            if (DescriptorUtils.isCompanionObject(classDescriptor)) {
                DetailedClassKind detailedClassKind5 = COMPANION_OBJECT;
                if (detailedClassKind5 == null) {
                    $$$reportNull$$$0(5);
                }
                return detailedClassKind5;
            }
            if (DescriptorUtils.isAnonymousObject(classDescriptor)) {
                DetailedClassKind detailedClassKind6 = ANONYMOUS_OBJECT;
                if (detailedClassKind6 == null) {
                    $$$reportNull$$$0(6);
                }
                return detailedClassKind6;
            }
            if (DescriptorUtils.isObject(classDescriptor)) {
                DetailedClassKind detailedClassKind7 = OBJECT;
                if (detailedClassKind7 == null) {
                    $$$reportNull$$$0(7);
                }
                return detailedClassKind7;
            }
            DetailedClassKind detailedClassKind8 = CLASS;
            if (detailedClassKind8 == null) {
                $$$reportNull$$$0(8);
            }
            return detailedClassKind8;
        }
    }

    public static Modality resolveMemberModalityFromModifiers(KtModifierListOwner ktModifierListOwner, Modality modality, BindingContext bindingContext, DeclarationDescriptor declarationDescriptor) {
        if (modality == null) {
            $$$reportNull$$$0(0);
        }
        if (bindingContext == null) {
            $$$reportNull$$$0(1);
        }
        return resolveModalityFromModifiers(ktModifierListOwner, modality, bindingContext, declarationDescriptor, false);
    }

    public static Modality resolveModalityFromModifiers(KtModifierListOwner ktModifierListOwner, Modality modality, BindingContext bindingContext, DeclarationDescriptor declarationDescriptor, boolean z) {
        if (modality == null) {
            $$$reportNull$$$0(2);
        }
        if (bindingContext == null) {
            $$$reportNull$$$0(3);
        }
        Modality modalityResolveModalityFromModifiers = resolveModalityFromModifiers(declarationDescriptor, ktModifierListOwner != null ? ktModifierListOwner.getModifierList() : null, modality, z);
        if (ktModifierListOwner != null) {
            List<DeclarationAttributeAltererExtension> instances = DeclarationAttributeAltererExtension.INSTANCE.getInstances(ktModifierListOwner.getProject());
            DeclarationDescriptor declarationDescriptor2 = (DeclarationDescriptor) bindingContext.get(BindingContext.DECLARATION_TO_DESCRIPTOR, ktModifierListOwner);
            Iterator<DeclarationAttributeAltererExtension> it = instances.iterator();
            while (it.hasNext()) {
                Modality modalityRefineDeclarationModality = it.next().refineDeclarationModality(ktModifierListOwner, declarationDescriptor2, declarationDescriptor, modalityResolveModalityFromModifiers, false);
                if (modalityRefineDeclarationModality != null) {
                    modalityResolveModalityFromModifiers = modalityRefineDeclarationModality;
                    break;
                }
            }
        }
        if (modalityResolveModalityFromModifiers == null) {
            $$$reportNull$$$0(4);
        }
        return modalityResolveModalityFromModifiers;
    }

    private static Modality resolveModalityFromModifiers(DeclarationDescriptor declarationDescriptor, KtModifierList ktModifierList, Modality modality, boolean z) {
        if (modality == null) {
            $$$reportNull$$$0(5);
        }
        if (ktModifierList == null) {
            if (modality == null) {
                $$$reportNull$$$0(6);
            }
            return modality;
        }
        boolean zHasModifier = ktModifierList.hasModifier(KtTokens.ABSTRACT_KEYWORD);
        boolean zHasModifier2 = ktModifierList.hasModifier(KtTokens.OVERRIDE_KEYWORD);
        if (z && ktModifierList.hasModifier(KtTokens.SEALED_KEYWORD)) {
            Modality modality2 = Modality.SEALED;
            if (modality2 == null) {
                $$$reportNull$$$0(7);
            }
            return modality2;
        }
        if (ktModifierList.hasModifier(KtTokens.OPEN_KEYWORD)) {
            if (declarationDescriptor instanceof ClassDescriptor) {
                ClassDescriptor classDescriptor = (ClassDescriptor) declarationDescriptor;
                if (classDescriptor.getKind() == ClassKind.INTERFACE && classDescriptor.get$isExpect()) {
                    Modality modality3 = Modality.OPEN;
                    if (modality3 == null) {
                        $$$reportNull$$$0(8);
                    }
                    return modality3;
                }
            }
            if (zHasModifier || modality == Modality.ABSTRACT) {
                Modality modality4 = Modality.ABSTRACT;
                if (modality4 == null) {
                    $$$reportNull$$$0(9);
                }
                return modality4;
            }
            Modality modality5 = Modality.OPEN;
            if (modality5 == null) {
                $$$reportNull$$$0(10);
            }
            return modality5;
        }
        if (zHasModifier) {
            Modality modality6 = Modality.ABSTRACT;
            if (modality6 == null) {
                $$$reportNull$$$0(11);
            }
            return modality6;
        }
        boolean zHasModifier3 = ktModifierList.hasModifier(KtTokens.FINAL_KEYWORD);
        if (zHasModifier2 && !zHasModifier3 && modality != Modality.ABSTRACT) {
            Modality modality7 = Modality.OPEN;
            if (modality7 == null) {
                $$$reportNull$$$0(12);
            }
            return modality7;
        }
        if (!zHasModifier3) {
            if (modality == null) {
                $$$reportNull$$$0(14);
            }
            return modality;
        }
        Modality modality8 = Modality.FINAL;
        if (modality8 == null) {
            $$$reportNull$$$0(13);
        }
        return modality8;
    }

    public static DescriptorVisibility resolveVisibilityFromModifiers(KtModifierListOwner ktModifierListOwner, DescriptorVisibility descriptorVisibility) {
        if (ktModifierListOwner == null) {
            $$$reportNull$$$0(15);
        }
        if (descriptorVisibility == null) {
            $$$reportNull$$$0(16);
        }
        DescriptorVisibility descriptorVisibilityResolveVisibilityFromModifiers = resolveVisibilityFromModifiers(ktModifierListOwner.getModifierList(), descriptorVisibility);
        if (descriptorVisibilityResolveVisibilityFromModifiers == null) {
            $$$reportNull$$$0(17);
        }
        return descriptorVisibilityResolveVisibilityFromModifiers;
    }

    public static DescriptorVisibility resolveVisibilityFromModifiers(KtModifierList ktModifierList, DescriptorVisibility descriptorVisibility) {
        if (descriptorVisibility == null) {
            $$$reportNull$$$0(18);
        }
        if (ktModifierList == null) {
            return descriptorVisibility;
        }
        if (ktModifierList.hasModifier(KtTokens.PRIVATE_KEYWORD)) {
            return DescriptorVisibilities.PRIVATE;
        }
        if (ktModifierList.hasModifier(KtTokens.PUBLIC_KEYWORD)) {
            return DescriptorVisibilities.PUBLIC;
        }
        if (ktModifierList.hasModifier(KtTokens.PROTECTED_KEYWORD)) {
            return DescriptorVisibilities.PROTECTED;
        }
        return ktModifierList.hasModifier(KtTokens.INTERNAL_KEYWORD) ? DescriptorVisibilities.INTERNAL : descriptorVisibility;
    }

    public class ModifiersCheckingProcedure {
        final /* synthetic */ ModifiersChecker this$0;
        private final BindingTrace trace;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            String str = i != 16 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
            Object[] objArr = new Object[i != 16 ? 3 : 2];
            switch (i) {
                case 1:
                    objArr[0] = "parameter";
                    break;
                case 2:
                    objArr[0] = "diagnosticFactory";
                    break;
                case 3:
                case 7:
                case 9:
                case 12:
                case 19:
                    objArr[0] = "modifierListOwner";
                    break;
                case 4:
                case 6:
                case 8:
                case 10:
                case 13:
                case 18:
                    objArr[0] = "descriptor";
                    break;
                case 5:
                case 17:
                    objArr[0] = "declaration";
                    break;
                case 11:
                    objArr[0] = "multiDeclaration";
                    break;
                case 14:
                    objArr[0] = "modifierList";
                    break;
                case 15:
                    objArr[0] = "possibleModifiers";
                    break;
                case 16:
                    objArr[0] = "org/jetbrains/kotlin/resolve/ModifiersChecker$ModifiersCheckingProcedure";
                    break;
                default:
                    objArr[0] = "trace";
                    break;
            }
            if (i != 16) {
                objArr[1] = "org/jetbrains/kotlin/resolve/ModifiersChecker$ModifiersCheckingProcedure";
            } else {
                objArr[1] = "getTokensCorrespondingToModifiers";
            }
            switch (i) {
                case 1:
                case 2:
                    objArr[2] = "checkParameterHasNoValOrVar";
                    break;
                case 3:
                case 4:
                    objArr[2] = "checkModifiersForDeclaration";
                    break;
                case 5:
                case 6:
                    objArr[2] = "checkNestedClassAllowed";
                    break;
                case 7:
                case 8:
                    objArr[2] = "checkModifierListCommon";
                    break;
                case 9:
                case 10:
                    objArr[2] = "checkModifiersForLocalDeclaration";
                    break;
                case 11:
                    objArr[2] = "checkModifiersForDestructuringDeclaration";
                    break;
                case 12:
                case 13:
                    objArr[2] = "checkIllegalHeader";
                    break;
                case 14:
                case 15:
                    objArr[2] = "getTokensCorrespondingToModifiers";
                    break;
                case 16:
                    break;
                case 17:
                case 18:
                    objArr[2] = "runDeclarationCheckers";
                    break;
                case 19:
                    objArr[2] = "checkTypeParametersModifiers";
                    break;
                default:
                    objArr[2] = "<init>";
                    break;
            }
            String str2 = String.format(str, objArr);
            if (i == 16) {
                throw new IllegalStateException(str2);
            }
        }

        private ModifiersCheckingProcedure(ModifiersChecker modifiersChecker, BindingTrace bindingTrace) {
            if (bindingTrace == null) {
                $$$reportNull$$$0(0);
            }
            this.this$0 = modifiersChecker;
            this.trace = bindingTrace;
        }

        public void checkParameterHasNoValOrVar(KtValVarKeywordOwner ktValVarKeywordOwner, DiagnosticFactory1<PsiElement, KtKeywordToken> diagnosticFactory1) {
            if (ktValVarKeywordOwner == null) {
                $$$reportNull$$$0(1);
            }
            if (diagnosticFactory1 == null) {
                $$$reportNull$$$0(2);
            }
            PsiElement valOrVarKeyword = ktValVarKeywordOwner.getValOrVarKeyword();
            if (valOrVarKeyword != null) {
                this.trace.report(diagnosticFactory1.on(valOrVarKeyword, (KtKeywordToken) valOrVarKeyword.getNode().getElementType()));
            }
        }

        public void checkModifiersForDeclaration(KtDeclaration ktDeclaration, MemberDescriptor memberDescriptor) {
            if (ktDeclaration == null) {
                $$$reportNull$$$0(3);
            }
            if (memberDescriptor == null) {
                $$$reportNull$$$0(4);
            }
            checkNestedClassAllowed(ktDeclaration, memberDescriptor);
            checkTypeParametersModifiers(ktDeclaration);
            checkModifierListCommon(ktDeclaration, memberDescriptor);
            checkIllegalHeader(ktDeclaration, memberDescriptor);
        }

        private void checkNestedClassAllowed(KtDeclaration ktDeclaration, DeclarationDescriptor declarationDescriptor) {
            if (ktDeclaration == null) {
                $$$reportNull$$$0(5);
            }
            if (declarationDescriptor == null) {
                $$$reportNull$$$0(6);
            }
            if (ktDeclaration instanceof KtClassOrObject) {
                KtClassOrObject ktClassOrObject = (KtClassOrObject) ktDeclaration;
                if (declarationDescriptor instanceof ClassDescriptor) {
                    ClassDescriptor classDescriptor = (ClassDescriptor) declarationDescriptor;
                    DeclarationDescriptor containingModule = declarationDescriptor.getContainingModule();
                    if (containingModule instanceof ClassDescriptor) {
                        ClassDescriptor classDescriptor2 = (ClassDescriptor) containingModule;
                        DetailedClassKind classKind = DetailedClassKind.getClassKind(classDescriptor);
                        if (classKind == DetailedClassKind.ANONYMOUS_OBJECT || classKind == DetailedClassKind.ENUM_ENTRY) {
                            return;
                        }
                        if ((classKind == DetailedClassKind.ENUM_CLASS || classKind == DetailedClassKind.OBJECT || classKind == DetailedClassKind.COMPANION_OBJECT) && DescriptorUtils.isLocal(classDescriptor)) {
                            return;
                        }
                        if (DescriptorUtils.isEnumEntry(classDescriptor2) && !classDescriptor.getIsInner() && classKind != DetailedClassKind.COMPANION_OBJECT) {
                            this.trace.report((this.this$0.languageVersionSettings.supportsFeature(LanguageFeature.NestedClassesInEnumEntryShouldBeInner) ? Errors.NESTED_CLASS_NOT_ALLOWED : Errors.NESTED_CLASS_DEPRECATED).on(ktClassOrObject, classKind.withCapitalFirstLetter));
                        } else {
                            if (classDescriptor.getIsInner()) {
                                return;
                            }
                            if (classDescriptor2.getIsInner() || DescriptorUtils.isLocal(classDescriptor2)) {
                                this.trace.report(Errors.NESTED_CLASS_NOT_ALLOWED.on(ktClassOrObject, classKind.withCapitalFirstLetter));
                            }
                        }
                    }
                }
            }
        }

        private void checkModifierListCommon(KtDeclaration ktDeclaration, DeclarationDescriptor declarationDescriptor) {
            if (ktDeclaration == null) {
                $$$reportNull$$$0(7);
            }
            if (declarationDescriptor == null) {
                $$$reportNull$$$0(8);
            }
            AnnotationUseSiteTargetChecker.INSTANCE.check(ktDeclaration, declarationDescriptor, this.trace, this.this$0.languageVersionSettings);
            runDeclarationCheckers(ktDeclaration, declarationDescriptor);
            this.this$0.annotationChecker.check(ktDeclaration, this.trace, declarationDescriptor);
            ModifierCheckerCore.INSTANCE.check(ktDeclaration, this.trace, declarationDescriptor, this.this$0.languageVersionSettings);
        }

        public void checkModifiersForLocalDeclaration(KtDeclaration ktDeclaration, DeclarationDescriptor declarationDescriptor) {
            if (ktDeclaration == null) {
                $$$reportNull$$$0(9);
            }
            if (declarationDescriptor == null) {
                $$$reportNull$$$0(10);
            }
            checkModifierListCommon(ktDeclaration, declarationDescriptor);
        }

        public void checkModifiersForDestructuringDeclaration(KtDestructuringDeclaration ktDestructuringDeclaration) {
            if (ktDestructuringDeclaration == null) {
                $$$reportNull$$$0(11);
            }
            this.this$0.annotationChecker.check(ktDestructuringDeclaration, this.trace, null);
            ModifierCheckerCore.INSTANCE.check(ktDestructuringDeclaration, this.trace, null, this.this$0.languageVersionSettings);
            for (KtDestructuringDeclarationEntry ktDestructuringDeclarationEntry : ktDestructuringDeclaration.getEntries()) {
                this.this$0.annotationChecker.check(ktDestructuringDeclarationEntry, this.trace, null);
                ModifierCheckerCore.INSTANCE.check(ktDestructuringDeclarationEntry, this.trace, null, this.this$0.languageVersionSettings);
                UnderscoreChecker.INSTANCE.checkNamed(ktDestructuringDeclarationEntry, this.trace, this.this$0.languageVersionSettings, true);
            }
        }

        private void checkIllegalHeader(KtModifierListOwner ktModifierListOwner, DeclarationDescriptor declarationDescriptor) {
            PsiElement modifier;
            if (ktModifierListOwner == null) {
                $$$reportNull$$$0(12);
            }
            if (declarationDescriptor == null) {
                $$$reportNull$$$0(13);
            }
            KtModifierList modifierList = ktModifierListOwner.getModifierList();
            PsiElement modifier2 = modifierList != null ? modifierList.getModifier(KtTokens.HEADER_KEYWORD) : null;
            if (modifier2 != null && (declarationDescriptor instanceof ClassDescriptor) && (declarationDescriptor.getContainingModule() instanceof ClassDescriptor)) {
                this.trace.report(Errors.WRONG_MODIFIER_TARGET.on(modifier2, KtTokens.HEADER_KEYWORD, "nested class"));
                return;
            }
            if (modifier2 == null && modifierList != null && (modifier = modifierList.getModifier(KtTokens.EXPECT_KEYWORD)) != null && (declarationDescriptor instanceof ClassDescriptor) && (declarationDescriptor.getContainingModule() instanceof ClassDescriptor)) {
                this.trace.report(Errors.WRONG_MODIFIER_TARGET.on(modifier, KtTokens.EXPECT_KEYWORD, "nested class"));
            }
        }

        public Map<KtModifierKeywordToken, PsiElement> getTokensCorrespondingToModifiers(KtModifierList ktModifierList, Collection<KtModifierKeywordToken> collection) {
            if (ktModifierList == null) {
                $$$reportNull$$$0(14);
            }
            if (collection == null) {
                $$$reportNull$$$0(15);
            }
            HashMap map = new HashMap();
            for (KtModifierKeywordToken ktModifierKeywordToken : collection) {
                if (ktModifierList.hasModifier(ktModifierKeywordToken)) {
                    map.put(ktModifierKeywordToken, ktModifierList.getModifier(ktModifierKeywordToken));
                }
            }
            return map;
        }

        public void runDeclarationCheckers(KtDeclaration ktDeclaration, DeclarationDescriptor declarationDescriptor) {
            if (ktDeclaration == null) {
                $$$reportNull$$$0(17);
            }
            if (declarationDescriptor == null) {
                $$$reportNull$$$0(18);
            }
            DeclarationCheckerContext declarationCheckerContext = new DeclarationCheckerContext(this.trace, this.this$0.languageVersionSettings, this.this$0.deprecationResolver, this.this$0.moduleDescriptor, this.this$0.expectActualTracker, this.this$0.missingSupertypesResolver);
            for (DeclarationChecker declarationChecker : this.this$0.declarationCheckers) {
                ProgressManager.checkCanceled();
                declarationChecker.check(ktDeclaration, declarationDescriptor, declarationCheckerContext);
            }
            OperatorModifierChecker.INSTANCE.check(ktDeclaration, declarationDescriptor, this.trace, this.this$0.languageVersionSettings);
            PublishedApiUsageChecker.INSTANCE.check(ktDeclaration, declarationDescriptor, this.trace);
            OptionalExpectationChecker.INSTANCE.check(ktDeclaration, declarationDescriptor, this.trace);
        }

        public void checkTypeParametersModifiers(KtModifierListOwner ktModifierListOwner) {
            if (ktModifierListOwner == null) {
                $$$reportNull$$$0(19);
            }
            if (ktModifierListOwner instanceof KtTypeParameterListOwner) {
                Iterator<KtTypeParameter> it = ((KtTypeParameterListOwner) ktModifierListOwner).getTypeParameters().iterator();
                while (it.hasNext()) {
                    ModifierCheckerCore.INSTANCE.check(it.next(), this.trace, null, this.this$0.languageVersionSettings);
                }
            }
        }
    }

    public ModifiersChecker(AnnotationChecker annotationChecker, Iterable<DeclarationChecker> iterable, LanguageVersionSettings languageVersionSettings, ExpectActualTracker expectActualTracker, DeprecationResolver deprecationResolver, ModuleDescriptor moduleDescriptor, MissingSupertypesResolver missingSupertypesResolver) {
        if (annotationChecker == null) {
            $$$reportNull$$$0(19);
        }
        if (iterable == null) {
            $$$reportNull$$$0(20);
        }
        if (languageVersionSettings == null) {
            $$$reportNull$$$0(21);
        }
        if (expectActualTracker == null) {
            $$$reportNull$$$0(22);
        }
        if (deprecationResolver == null) {
            $$$reportNull$$$0(23);
        }
        if (moduleDescriptor == null) {
            $$$reportNull$$$0(24);
        }
        if (missingSupertypesResolver == null) {
            $$$reportNull$$$0(25);
        }
        this.annotationChecker = annotationChecker;
        this.declarationCheckers = iterable;
        this.languageVersionSettings = languageVersionSettings;
        this.expectActualTracker = expectActualTracker;
        this.deprecationResolver = deprecationResolver;
        this.moduleDescriptor = moduleDescriptor;
        this.missingSupertypesResolver = missingSupertypesResolver;
    }

    public ModifiersCheckingProcedure withTrace(BindingTrace bindingTrace) {
        if (bindingTrace == null) {
            $$$reportNull$$$0(26);
        }
        return new ModifiersCheckingProcedure(bindingTrace);
    }
}

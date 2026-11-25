package org.jetbrains.kotlin.resolve.jvm.checkers;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptorWithVisibility;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.descriptors.MemberDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyGetterDescriptor;
import org.jetbrains.kotlin.descriptors.PropertySetterDescriptor;
import org.jetbrains.kotlin.diagnostics.DiagnosticSink;
import org.jetbrains.kotlin.lexer.KtTokens;
import org.jetbrains.kotlin.psi.KtDeclaration;
import org.jetbrains.kotlin.psi.KtModifierList;
import org.jetbrains.kotlin.psi.KtNamedFunction;
import org.jetbrains.kotlin.psi.KtParameter;
import org.jetbrains.kotlin.psi.KtProperty;
import org.jetbrains.kotlin.psi.KtPropertyAccessor;
import org.jetbrains.kotlin.resolve.DescriptorUtils;
import org.jetbrains.kotlin.resolve.annotations.AnnotationUtilKt;
import org.jetbrains.kotlin.resolve.checkers.DeclarationChecker;
import org.jetbrains.kotlin.resolve.checkers.DeclarationCheckerContext;
import org.jetbrains.kotlin.resolve.jvm.annotations.JvmAnnotationUtilKt;
import org.jetbrains.kotlin.resolve.jvm.diagnostics.ErrorsJvm;

/* JADX INFO: compiled from: declarationCheckers.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J \u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0016J \u0010\u000f\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0002J \u0010\u0012\u001a\u00020\b2\u0006\u0010\u000b\u001a\u00020\u00132\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\nH\u0002R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0014"}, d2 = {"Lorg/jetbrains/kotlin/resolve/jvm/checkers/JvmStaticChecker;", "Lorg/jetbrains/kotlin/resolve/checkers/DeclarationChecker;", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "(Lorg/jetbrains/kotlin/config/LanguageVersionSettings;)V", "supportJvmStaticInInterface", "", "check", "", "declaration", "Lorg/jetbrains/kotlin/psi/KtDeclaration;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "context", "Lorg/jetbrains/kotlin/resolve/checkers/DeclarationCheckerContext;", "checkDeclaration", "diagnosticHolder", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticSink;", "checkForInterface", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptorWithVisibility;", "frontend.java"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JvmStaticChecker implements DeclarationChecker {
    private final boolean supportJvmStaticInInterface;

    public JvmStaticChecker(LanguageVersionSettings languageVersionSettings) {
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        this.supportJvmStaticInInterface = languageVersionSettings.supportsFeature(LanguageFeature.JvmStaticInInterface);
    }

    @Override // org.jetbrains.kotlin.resolve.checkers.DeclarationChecker
    public void check(KtDeclaration declaration, DeclarationDescriptor descriptor, DeclarationCheckerContext context) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(context, "context");
        if (AnnotationUtilKt.hasJvmStaticAnnotation(descriptor)) {
            if ((declaration instanceof KtNamedFunction) || (declaration instanceof KtProperty) || (declaration instanceof KtPropertyAccessor) || (declaration instanceof KtParameter)) {
                checkDeclaration(declaration, descriptor, context.getTrace());
            }
        }
    }

    /* JADX WARN: Code duplicated, block: B:7:0x001e  */
    private final void checkDeclaration(KtDeclaration declaration, DeclarationDescriptor descriptor, DiagnosticSink diagnosticHolder) {
        boolean z;
        KtProperty ktProperty;
        KtModifierList modifierList;
        DeclarationDescriptor containingDeclaration = descriptor.getContainingModule();
        boolean zIsObject = DescriptorUtils.isObject(containingDeclaration);
        if (DescriptorUtils.isCompanionObject(containingDeclaration)) {
            Intrinsics.checkNotNull(containingDeclaration);
            if (DescriptorUtils.isInterface(containingDeclaration.getContainingModule())) {
                z = true;
            } else {
                z = false;
            }
        } else {
            z = false;
        }
        if (!zIsObject || z) {
            if (z && this.supportJvmStaticInInterface && (descriptor instanceof DeclarationDescriptorWithVisibility)) {
                checkForInterface((DeclarationDescriptorWithVisibility) descriptor, diagnosticHolder, declaration);
            } else {
                diagnosticHolder.report((this.supportJvmStaticInInterface ? ErrorsJvm.JVM_STATIC_NOT_IN_OBJECT_OR_COMPANION : ErrorsJvm.JVM_STATIC_NOT_IN_OBJECT_OR_CLASS_COMPANION).on(declaration));
            }
        }
        if (declaration instanceof KtPropertyAccessor) {
            PsiElement parent = declaration.getMyParent();
            Intrinsics.checkNotNull(parent, "null cannot be cast to non-null type org.jetbrains.kotlin.psi.KtProperty");
            ktProperty = (KtProperty) parent;
        } else {
            ktProperty = declaration;
        }
        if (DescriptorUtils.isNonCompanionObject(containingDeclaration) && (modifierList = ktProperty.getModifierList()) != null && modifierList.hasModifier(KtTokens.OVERRIDE_KEYWORD)) {
            diagnosticHolder.report(ErrorsJvm.OVERRIDE_CANNOT_BE_STATIC.on(declaration));
        }
        if (descriptor instanceof PropertyDescriptor) {
            if (((PropertyDescriptor) descriptor).isConst() || JvmAnnotationUtilKt.hasJvmFieldAnnotation(descriptor)) {
                diagnosticHolder.report(ErrorsJvm.JVM_STATIC_ON_CONST_OR_JVM_FIELD.on(declaration));
            }
        }
    }

    private final void checkForInterface(DeclarationDescriptorWithVisibility descriptor, DiagnosticSink diagnosticHolder, KtDeclaration declaration) {
        if (!Intrinsics.areEqual(descriptor.getVisibilityImpl(), DescriptorVisibilities.PUBLIC)) {
            diagnosticHolder.report(ErrorsJvm.JVM_STATIC_ON_NON_PUBLIC_MEMBER.on(declaration));
            return;
        }
        if ((descriptor instanceof MemberDescriptor) && ((MemberDescriptor) descriptor).isExternal()) {
            diagnosticHolder.report(ErrorsJvm.JVM_STATIC_ON_EXTERNAL_IN_INTERFACE.on(declaration));
            return;
        }
        if (descriptor instanceof PropertyDescriptor) {
            PropertyDescriptor propertyDescriptor = (PropertyDescriptor) descriptor;
            PropertyGetterDescriptor getter = propertyDescriptor.getGetter();
            if (getter != null) {
                checkForInterface(getter, diagnosticHolder, declaration);
            }
            PropertySetterDescriptor setter = propertyDescriptor.getSetter();
            if (setter != null) {
                checkForInterface(setter, diagnosticHolder, declaration);
            }
        }
    }
}

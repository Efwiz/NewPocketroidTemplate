package org.jetbrains.kotlin.resolve.jvm.checkers;

import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.descriptors.MemberDescriptor;
import org.jetbrains.kotlin.descriptors.VariableDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.AnnotationDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.AnnotationUseSiteTarget;
import org.jetbrains.kotlin.descriptors.annotations.KotlinRetention;
import org.jetbrains.kotlin.diagnostics.Diagnostic;
import org.jetbrains.kotlin.diagnostics.DiagnosticUtilsKt;
import org.jetbrains.kotlin.psi.KtAnnotationEntry;
import org.jetbrains.kotlin.psi.KtAnnotationUseSiteTarget;
import org.jetbrains.kotlin.psi.KtDeclaration;
import org.jetbrains.kotlin.psi.KtParameter;
import org.jetbrains.kotlin.psi.KtProperty;
import org.jetbrains.kotlin.psi.KtPropertyAccessor;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.BindingTrace;
import org.jetbrains.kotlin.resolve.checkers.DeclarationChecker;
import org.jetbrains.kotlin.resolve.checkers.DeclarationCheckerContext;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.resolve.jvm.annotations.JvmAnnotationUtilKt;
import org.jetbrains.kotlin.resolve.jvm.diagnostics.ErrorsJvm;

/* JADX INFO: compiled from: JvmAnnotationsTargetNonExistentAccessorChecker.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\u0018\u0000 \u00132\u00020\u0001:\u0001\u0013B\u0005¢\u0006\u0002\u0010\u0002J \u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016J\u0010\u0010\u000b\u001a\u00020\f2\u0006\u0010\u0007\u001a\u00020\rH\u0002J \u0010\u000e\u001a\u00020\u00042\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\t\u001a\u00020\nH\u0002¨\u0006\u0014"}, d2 = {"Lorg/jetbrains/kotlin/resolve/jvm/checkers/JvmAnnotationsTargetNonExistentAccessorChecker;", "Lorg/jetbrains/kotlin/resolve/checkers/DeclarationChecker;", "()V", "check", "", "declaration", "Lorg/jetbrains/kotlin/psi/KtDeclaration;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "context", "Lorg/jetbrains/kotlin/resolve/checkers/DeclarationCheckerContext;", "isSpecialStaticProperty", "", "Lorg/jetbrains/kotlin/descriptors/MemberDescriptor;", "reportOnAnnotationWithNonSourceRetention", "entry", "Lorg/jetbrains/kotlin/psi/KtAnnotationEntry;", "declarationName", "", "Companion", "frontend.java"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JvmAnnotationsTargetNonExistentAccessorChecker implements DeclarationChecker {
    private static final Set<AnnotationUseSiteTarget> getterUselessTargets = SetsKt.setOf(AnnotationUseSiteTarget.PROPERTY_GETTER);
    private static final Set<AnnotationUseSiteTarget> setterUselessTargets = SetsKt.setOf((Object[]) new AnnotationUseSiteTarget[]{AnnotationUseSiteTarget.PROPERTY_SETTER, AnnotationUseSiteTarget.SETTER_PARAMETER});

    @Override // org.jetbrains.kotlin.resolve.checkers.DeclarationChecker
    public void check(KtDeclaration declaration, DeclarationDescriptor descriptor, DeclarationCheckerContext context) {
        KtPropertyAccessor setter;
        List<KtAnnotationEntry> annotationEntries;
        KtPropertyAccessor getter;
        List<KtAnnotationEntry> annotationEntries2;
        KtPropertyAccessor setter2;
        KtPropertyAccessor getter2;
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(context, "context");
        if (descriptor instanceof MemberDescriptor) {
            if ((declaration instanceof KtParameter) || (declaration instanceof KtProperty)) {
                MemberDescriptor memberDescriptor = (MemberDescriptor) descriptor;
                if (DescriptorVisibilities.isPrivate(memberDescriptor.getVisibilityImpl()) || isSpecialStaticProperty(memberDescriptor)) {
                    boolean z = declaration instanceof KtProperty;
                    boolean z2 = false;
                    boolean z3 = z && (getter2 = ((KtProperty) declaration).getGetter()) != null && getter2.hasBody();
                    if (z && (setter2 = ((KtProperty) declaration).getSetter()) != null && setter2.hasBody()) {
                        z2 = true;
                    }
                    if (z3 && z2) {
                        return;
                    }
                    if (z && ((KtProperty) declaration).hasDelegate()) {
                        return;
                    }
                    String name2 = declaration.getName();
                    if (name2 == null) {
                        name2 = memberDescriptor.getShortName().asString();
                    }
                    Intrinsics.checkNotNull(name2);
                    for (KtAnnotationEntry ktAnnotationEntry : declaration.getAnnotationEntries()) {
                        KtAnnotationUseSiteTarget useSiteTarget = ktAnnotationEntry.getUseSiteTarget();
                        if (useSiteTarget != null) {
                            AnnotationUseSiteTarget annotationUseSiteTarget = useSiteTarget.getAnnotationUseSiteTarget();
                            if ((!z3 && getterUselessTargets.contains(annotationUseSiteTarget)) || (!z2 && setterUselessTargets.contains(annotationUseSiteTarget))) {
                                Intrinsics.checkNotNull(ktAnnotationEntry);
                                reportOnAnnotationWithNonSourceRetention(ktAnnotationEntry, name2, context);
                            }
                        }
                    }
                    if (z) {
                        if (!z3 && (getter = ((KtProperty) declaration).getGetter()) != null && (annotationEntries2 = getter.getAnnotationEntries()) != null) {
                            for (KtAnnotationEntry ktAnnotationEntry2 : annotationEntries2) {
                                Intrinsics.checkNotNull(ktAnnotationEntry2);
                                reportOnAnnotationWithNonSourceRetention(ktAnnotationEntry2, name2, context);
                            }
                        }
                        if (z2 || (setter = ((KtProperty) declaration).getSetter()) == null || (annotationEntries = setter.getAnnotationEntries()) == null) {
                            return;
                        }
                        for (KtAnnotationEntry ktAnnotationEntry3 : annotationEntries) {
                            Intrinsics.checkNotNull(ktAnnotationEntry3);
                            reportOnAnnotationWithNonSourceRetention(ktAnnotationEntry3, name2, context);
                        }
                    }
                }
            }
        }
    }

    private final void reportOnAnnotationWithNonSourceRetention(KtAnnotationEntry entry, String declarationName, DeclarationCheckerContext context) {
        AnnotationDescriptor annotationDescriptor = (AnnotationDescriptor) context.getTrace().get(BindingContext.ANNOTATION, entry);
        if (annotationDescriptor == null) {
            return;
        }
        ClassDescriptor annotationClass = DescriptorUtilsKt.getAnnotationClass(annotationDescriptor);
        if ((annotationClass != null ? DescriptorUtilsKt.getAnnotationRetention(annotationClass) : null) == KotlinRetention.SOURCE) {
            return;
        }
        BindingTrace trace = context.getTrace();
        Diagnostic diagnosticOn = ErrorsJvm.ANNOTATION_TARGETS_NON_EXISTENT_ACCESSOR.on(entry, declarationName);
        Intrinsics.checkNotNullExpressionValue(diagnosticOn, "on(...)");
        DiagnosticUtilsKt.reportDiagnosticOnce(trace, diagnosticOn);
    }

    private final boolean isSpecialStaticProperty(MemberDescriptor descriptor) {
        return JvmAnnotationUtilKt.hasJvmFieldAnnotation(descriptor) || ((descriptor instanceof VariableDescriptor) && ((VariableDescriptor) descriptor).isConst());
    }
}

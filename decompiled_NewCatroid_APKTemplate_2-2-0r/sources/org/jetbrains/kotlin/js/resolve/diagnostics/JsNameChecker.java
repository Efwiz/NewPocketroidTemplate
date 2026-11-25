package org.jetbrains.kotlin.js.resolve.diagnostics;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.ConstructorDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyAccessorDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.AnnotationDescriptor;
import org.jetbrains.kotlin.js.PredefinedAnnotation;
import org.jetbrains.kotlin.js.translate.utils.AnnotationsUtils;
import org.jetbrains.kotlin.psi.KtDeclaration;
import org.jetbrains.kotlin.resolve.BindingTrace;
import org.jetbrains.kotlin.resolve.checkers.DeclarationChecker;
import org.jetbrains.kotlin.resolve.checkers.DeclarationCheckerContext;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.resolve.source.PsiSourceElementKt;

/* JADX INFO: compiled from: JsNameChecker.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J \u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/js/resolve/diagnostics/JsNameChecker;", "Lorg/jetbrains/kotlin/resolve/checkers/DeclarationChecker;", "()V", "check", "", "declaration", "Lorg/jetbrains/kotlin/psi/KtDeclaration;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "context", "Lorg/jetbrains/kotlin/resolve/checkers/DeclarationCheckerContext;", "js.frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JsNameChecker implements DeclarationChecker {
    public static final JsNameChecker INSTANCE = new JsNameChecker();

    private JsNameChecker() {
    }

    @Override // org.jetbrains.kotlin.resolve.checkers.DeclarationChecker
    public void check(KtDeclaration declaration, DeclarationDescriptor descriptor, DeclarationCheckerContext context) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(context, "context");
        BindingTrace trace = context.getTrace();
        boolean z = descriptor instanceof PropertyDescriptor;
        if (z) {
            PropertyDescriptor propertyDescriptor = (PropertyDescriptor) descriptor;
            List<PropertyAccessorDescriptor> accessors = propertyDescriptor.getAccessors();
            Intrinsics.checkNotNullExpressionValue(accessors, "getAccessors(...)");
            List<PropertyAccessorDescriptor> list = accessors;
            int i = 0;
            if (!(list instanceof Collection) || !list.isEmpty()) {
                Iterator<T> it = list.iterator();
                while (it.hasNext()) {
                    if (AnnotationsUtils.getJsName((PropertyAccessorDescriptor) it.next()) != null && (i = i + 1) < 0) {
                        CollectionsKt.throwCountOverflow();
                    }
                }
            }
            if (i > 0 && i < propertyDescriptor.getAccessors().size()) {
                trace.report(ErrorsJs.JS_NAME_IS_NOT_ON_ALL_ACCESSORS.on(declaration));
            }
        }
        AnnotationDescriptor jsNameAnnotation = AnnotationsUtils.getJsNameAnnotation(descriptor);
        if (jsNameAnnotation == null) {
            return;
        }
        KtDeclaration psi = PsiSourceElementKt.getPsi(jsNameAnnotation.getSource());
        if (psi == null) {
            psi = declaration;
        }
        if (AnnotationsUtils.getNameForAnnotatedObject(descriptor, PredefinedAnnotation.NATIVE) != null) {
            trace.report(ErrorsJs.JS_NAME_PROHIBITED_FOR_NAMED_NATIVE.on(psi));
        }
        if (descriptor instanceof CallableMemberDescriptor) {
            Collection<? extends CallableMemberDescriptor> overriddenDescriptors = ((CallableMemberDescriptor) descriptor).getOverriddenDescriptors();
            Intrinsics.checkNotNullExpressionValue(overriddenDescriptors, "getOverriddenDescriptors(...)");
            if (!overriddenDescriptors.isEmpty()) {
                trace.report(ErrorsJs.JS_NAME_PROHIBITED_FOR_OVERRIDE.on(psi));
            }
        }
        if (descriptor instanceof ConstructorDescriptor) {
            if (((ConstructorDescriptor) descriptor).isPrimary()) {
                trace.report(ErrorsJs.JS_NAME_ON_PRIMARY_CONSTRUCTOR_PROHIBITED.on(psi));
            }
        } else if (descriptor instanceof PropertyAccessorDescriptor) {
            if (AnnotationsUtils.getJsName(((PropertyAccessorDescriptor) descriptor).getCorrespondingProperty()) != null) {
                trace.report(ErrorsJs.JS_NAME_ON_ACCESSOR_AND_PROPERTY.on(psi));
            }
        } else if (z && DescriptorUtilsKt.isExtension(descriptor)) {
            trace.report(ErrorsJs.JS_NAME_PROHIBITED_FOR_EXTENSION_PROPERTY.on(psi));
        }
    }
}

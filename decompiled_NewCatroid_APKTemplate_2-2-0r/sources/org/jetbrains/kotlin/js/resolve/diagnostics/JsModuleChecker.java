package org.jetbrains.kotlin.js.resolve.diagnostics;

import java.util.Iterator;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.js.translate.utils.AnnotationsUtils;
import org.jetbrains.kotlin.psi.KtClassOrObject;
import org.jetbrains.kotlin.psi.KtDeclaration;
import org.jetbrains.kotlin.psi.KtElementImplStub;
import org.jetbrains.kotlin.psi.KtSuperTypeListEntry;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.BindingTrace;
import org.jetbrains.kotlin.resolve.DescriptorUtils;
import org.jetbrains.kotlin.resolve.checkers.DeclarationChecker;
import org.jetbrains.kotlin.resolve.checkers.DeclarationCheckerContext;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.TypeConstructor;

/* JADX INFO: compiled from: JsModuleChecker.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J \u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016J \u0010\u000b\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\f\u001a\u00020\rH\u0002¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/js/resolve/diagnostics/JsModuleChecker;", "Lorg/jetbrains/kotlin/resolve/checkers/DeclarationChecker;", "()V", "check", "", "declaration", "Lorg/jetbrains/kotlin/psi/KtDeclaration;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "context", "Lorg/jetbrains/kotlin/resolve/checkers/DeclarationCheckerContext;", "checkSuperClass", "trace", "Lorg/jetbrains/kotlin/resolve/BindingTrace;", "js.frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JsModuleChecker implements DeclarationChecker {
    public static final JsModuleChecker INSTANCE = new JsModuleChecker();

    private JsModuleChecker() {
    }

    @Override // org.jetbrains.kotlin.resolve.checkers.DeclarationChecker
    public void check(KtDeclaration declaration, DeclarationDescriptor descriptor, DeclarationCheckerContext context) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(context, "context");
        BindingTrace trace = context.getTrace();
        checkSuperClass(declaration, descriptor, trace);
        if (AnnotationsUtils.getModuleName(descriptor) != null || AnnotationsUtils.isNonModule(descriptor)) {
            if ((descriptor instanceof PropertyDescriptor) && ((PropertyDescriptor) descriptor).isVar()) {
                trace.report(ErrorsJs.JS_MODULE_PROHIBITED_ON_VAR.on(declaration));
            }
            if (!AnnotationsUtils.isNativeObject(descriptor)) {
                trace.report(ErrorsJs.JS_MODULE_PROHIBITED_ON_NON_NATIVE.on(declaration));
            }
            if (DescriptorUtils.isTopLevelDeclaration(descriptor)) {
                if (AnnotationsUtils.getFileModuleName(trace.getContext(), descriptor) != null || AnnotationsUtils.isFromNonModuleFile(trace.getContext(), descriptor)) {
                    trace.report(ErrorsJs.NESTED_JS_MODULE_PROHIBITED.on(declaration));
                }
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final void checkSuperClass(KtDeclaration declaration, DeclarationDescriptor descriptor, BindingTrace trace) {
        ClassDescriptor superClassNotAny;
        Object this$0;
        TypeConstructor constructor;
        if ((descriptor instanceof ClassDescriptor) && (superClassNotAny = DescriptorUtilsKt.getSuperClassNotAny((ClassDescriptor) descriptor)) != null) {
            Intrinsics.checkNotNull(declaration, "null cannot be cast to non-null type org.jetbrains.kotlin.psi.KtClassOrObject");
            Iterator<T> it = ((KtClassOrObject) declaration).getSuperTypeListEntries().iterator();
            while (true) {
                this$0 = null;
                if (!it.hasNext()) {
                    break;
                }
                Object next = it.next();
                KotlinType kotlinType = (KotlinType) trace.get(BindingContext.TYPE, ((KtSuperTypeListEntry) next).getTypeReference());
                if (kotlinType != null && (constructor = kotlinType.getConstructor()) != null) {
                    this$0 = constructor.getThis$0();
                }
                if (Intrinsics.areEqual(this$0, superClassNotAny)) {
                    this$0 = next;
                    break;
                }
            }
            KtSuperTypeListEntry ktSuperTypeListEntry = (KtSuperTypeListEntry) this$0;
            BindingContext context = trace.getContext();
            Intrinsics.checkNotNullExpressionValue(context, "getBindingContext(...)");
            JsModuleCheckUtilKt.checkJsModuleUsage(context, trace, descriptor, superClassNotAny, ktSuperTypeListEntry != null ? ktSuperTypeListEntry : (KtElementImplStub) declaration);
        }
    }
}

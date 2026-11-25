package org.jetbrains.kotlin.resolve.jvm.checkers;

import java.util.Iterator;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.diagnostics.Errors;
import org.jetbrains.kotlin.load.java.lazy.descriptors.LazyJavaClassDescriptor;
import org.jetbrains.kotlin.psi.KtClassOrObject;
import org.jetbrains.kotlin.psi.KtDeclaration;
import org.jetbrains.kotlin.psi.KtSuperTypeListEntry;
import org.jetbrains.kotlin.psi.KtTypeReference;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.bindingContextUtil.BindingContextUtilsKt;
import org.jetbrains.kotlin.resolve.checkers.DeclarationChecker;
import org.jetbrains.kotlin.resolve.checkers.DeclarationCheckerContext;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.UnwrappedType;

/* JADX INFO: compiled from: ClassInheritsJavaSealedClassChecker.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J \u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/resolve/jvm/checkers/ClassInheritsJavaSealedClassChecker;", "Lorg/jetbrains/kotlin/resolve/checkers/DeclarationChecker;", "()V", "check", "", "declaration", "Lorg/jetbrains/kotlin/psi/KtDeclaration;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "context", "Lorg/jetbrains/kotlin/resolve/checkers/DeclarationCheckerContext;", "frontend.java"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ClassInheritsJavaSealedClassChecker implements DeclarationChecker {
    public static final ClassInheritsJavaSealedClassChecker INSTANCE = new ClassInheritsJavaSealedClassChecker();

    private ClassInheritsJavaSealedClassChecker() {
    }

    @Override // org.jetbrains.kotlin.resolve.checkers.DeclarationChecker
    public void check(KtDeclaration declaration, DeclarationDescriptor descriptor, DeclarationCheckerContext context) {
        UnwrappedType unwrappedTypeUnwrap;
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(context, "context");
        if ((descriptor instanceof ClassDescriptor) && (declaration instanceof KtClassOrObject)) {
            Iterator<KtSuperTypeListEntry> it = ((KtClassOrObject) declaration).getSuperTypeListEntries().iterator();
            while (it.hasNext()) {
                KtTypeReference typeReference = it.next().getTypeReference();
                if (typeReference != null) {
                    BindingContext context2 = context.getTrace().getContext();
                    Intrinsics.checkNotNullExpressionValue(context2, "getBindingContext(...)");
                    KotlinType abbreviatedTypeOrType = BindingContextUtilsKt.getAbbreviatedTypeOrType(typeReference, context2);
                    if (abbreviatedTypeOrType != null && (unwrappedTypeUnwrap = abbreviatedTypeOrType.unwrap()) != null) {
                        ClassifierDescriptor this$0 = unwrappedTypeUnwrap.getConstructor().getThis$0();
                        LazyJavaClassDescriptor lazyJavaClassDescriptor = this$0 instanceof LazyJavaClassDescriptor ? (LazyJavaClassDescriptor) this$0 : null;
                        if (lazyJavaClassDescriptor != null && lazyJavaClassDescriptor.getJClass().isSealed()) {
                            context.getTrace().report(Errors.CLASS_INHERITS_JAVA_SEALED_CLASS.on(typeReference));
                        }
                    }
                }
            }
        }
    }
}

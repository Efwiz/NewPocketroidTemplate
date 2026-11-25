package org.jetbrains.kotlin.resolve.konan.diagnostics;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.psi.KtDeclaration;
import org.jetbrains.kotlin.resolve.checkers.DeclarationChecker;
import org.jetbrains.kotlin.resolve.checkers.DeclarationCheckerContext;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;

/* JADX INFO: compiled from: NativeHiddenFromObjCInheritanceChecker.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J \u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/resolve/konan/diagnostics/NativeHiddenFromObjCInheritanceChecker;", "Lorg/jetbrains/kotlin/resolve/checkers/DeclarationChecker;", "()V", "check", "", "declaration", "Lorg/jetbrains/kotlin/psi/KtDeclaration;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "context", "Lorg/jetbrains/kotlin/resolve/checkers/DeclarationCheckerContext;", "frontend.native"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class NativeHiddenFromObjCInheritanceChecker implements DeclarationChecker {
    public static final NativeHiddenFromObjCInheritanceChecker INSTANCE = new NativeHiddenFromObjCInheritanceChecker();

    private NativeHiddenFromObjCInheritanceChecker() {
    }

    @Override // org.jetbrains.kotlin.resolve.checkers.DeclarationChecker
    public void check(KtDeclaration declaration, DeclarationDescriptor descriptor, DeclarationCheckerContext context) {
        ClassDescriptor superClassNotAny;
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(context, "context");
        if (descriptor instanceof ClassDescriptor) {
            ClassDescriptor classDescriptor = (ClassDescriptor) descriptor;
            if (classDescriptor.getKind() == ClassKind.ENUM_ENTRY || !classDescriptor.getVisibility().isPublicAPI() || NativeHiddenFromObjCInheritanceCheckerKt.checkClassIsHiddenFromObjC(classDescriptor)) {
                return;
            }
            List<ClassDescriptor> superInterfaces = DescriptorUtilsKt.getSuperInterfaces(classDescriptor);
            if ((superInterfaces instanceof Collection) && superInterfaces.isEmpty()) {
                superClassNotAny = DescriptorUtilsKt.getSuperClassNotAny(classDescriptor);
                if (superClassNotAny != null) {
                    return;
                } else {
                    return;
                }
            } else {
                Iterator<T> it = superInterfaces.iterator();
                while (it.hasNext()) {
                    if (NativeHiddenFromObjCInheritanceCheckerKt.checkClassIsHiddenFromObjC((ClassDescriptor) it.next())) {
                    }
                }
                superClassNotAny = DescriptorUtilsKt.getSuperClassNotAny(classDescriptor);
                if (superClassNotAny != null || !NativeHiddenFromObjCInheritanceCheckerKt.checkClassIsHiddenFromObjC(superClassNotAny)) {
                    return;
                }
            }
            context.getTrace().report(ErrorsNative.SUBTYPE_OF_HIDDEN_FROM_OBJC.on(declaration));
        }
    }
}

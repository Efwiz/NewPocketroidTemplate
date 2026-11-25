package org.jetbrains.kotlin.resolve.konan.diagnostics;

import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.VariableDescriptor;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.psi.KtDeclaration;
import org.jetbrains.kotlin.psi.KtProperty;
import org.jetbrains.kotlin.resolve.DescriptorUtils;
import org.jetbrains.kotlin.resolve.ResolveUtilKt;
import org.jetbrains.kotlin.resolve.checkers.DeclarationChecker;
import org.jetbrains.kotlin.resolve.checkers.DeclarationCheckerContext;

/* JADX INFO: compiled from: NativeThreadLocalChecker.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J \u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/resolve/konan/diagnostics/NativeThreadLocalChecker;", "Lorg/jetbrains/kotlin/resolve/checkers/DeclarationChecker;", "()V", "threadLocalFqName", "Lorg/jetbrains/kotlin/name/FqName;", "check", "", "declaration", "Lorg/jetbrains/kotlin/psi/KtDeclaration;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "context", "Lorg/jetbrains/kotlin/resolve/checkers/DeclarationCheckerContext;", "frontend.native"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class NativeThreadLocalChecker implements DeclarationChecker {
    public static final NativeThreadLocalChecker INSTANCE = new NativeThreadLocalChecker();
    private static final FqName threadLocalFqName = new FqName("kotlin.native.concurrent.ThreadLocal");

    private NativeThreadLocalChecker() {
    }

    @Override // org.jetbrains.kotlin.resolve.checkers.DeclarationChecker
    public void check(final KtDeclaration declaration, final DeclarationDescriptor descriptor, final DeclarationCheckerContext context) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(context, "context");
        NativeThreadLocalChecker nativeThreadLocalChecker = this;
        FqName fqName = threadLocalFqName;
        NativeThreadLocalCheckerKt.check(nativeThreadLocalChecker, fqName, declaration, descriptor, context, ErrorsNative.INAPPLICABLE_THREAD_LOCAL, new Function1<DeclarationDescriptor, Boolean>() { // from class: org.jetbrains.kotlin.resolve.konan.diagnostics.NativeThreadLocalChecker.check.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(DeclarationDescriptor it) {
                Intrinsics.checkNotNullParameter(it, "it");
                DeclarationDescriptor declarationDescriptor = descriptor;
                boolean z = declarationDescriptor instanceof VariableDescriptor;
                boolean z2 = true;
                boolean z3 = (declarationDescriptor instanceof PropertyDescriptor) && ResolveUtilKt.hasBackingField((PropertyDescriptor) declarationDescriptor, context.getTrace().getContext());
                KtDeclaration ktDeclaration = declaration;
                boolean z4 = (ktDeclaration instanceof KtProperty) && ((KtProperty) ktDeclaration).getDelegate() != null;
                if (!z || (!z3 && !z4)) {
                    DeclarationDescriptor declarationDescriptor2 = descriptor;
                    if (!(declarationDescriptor2 instanceof ClassDescriptor) || ((ClassDescriptor) declarationDescriptor2).getKind() != ClassKind.OBJECT) {
                        z2 = false;
                    }
                }
                return Boolean.valueOf(z2);
            }
        });
        NativeThreadLocalCheckerKt.check(nativeThreadLocalChecker, fqName, declaration, descriptor, context, ErrorsNative.INAPPLICABLE_THREAD_LOCAL_TOP_LEVEL, new Function1<DeclarationDescriptor, Boolean>() { // from class: org.jetbrains.kotlin.resolve.konan.diagnostics.NativeThreadLocalChecker.check.2
            {
                super(1);
            }

            /* JADX WARN: Code duplicated, block: B:10:0x0020  */
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(DeclarationDescriptor it) {
                boolean z;
                Intrinsics.checkNotNullParameter(it, "it");
                if (DescriptorUtils.isTopLevelDeclaration(descriptor)) {
                    z = true;
                } else {
                    DeclarationDescriptor declarationDescriptor = descriptor;
                    if ((declarationDescriptor instanceof ClassDescriptor) && ((ClassDescriptor) declarationDescriptor).getKind() == ClassKind.OBJECT) {
                        z = true;
                    } else {
                        z = false;
                    }
                }
                return Boolean.valueOf(z);
            }
        });
    }
}

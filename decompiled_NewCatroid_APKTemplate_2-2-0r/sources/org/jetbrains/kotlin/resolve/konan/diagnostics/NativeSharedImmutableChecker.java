package org.jetbrains.kotlin.resolve.konan.diagnostics;

import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
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

/* JADX INFO: compiled from: NativeSharedImmutableChecker.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J \u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/resolve/konan/diagnostics/NativeSharedImmutableChecker;", "Lorg/jetbrains/kotlin/resolve/checkers/DeclarationChecker;", "()V", "sharedImmutableFqName", "Lorg/jetbrains/kotlin/name/FqName;", "check", "", "declaration", "Lorg/jetbrains/kotlin/psi/KtDeclaration;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "context", "Lorg/jetbrains/kotlin/resolve/checkers/DeclarationCheckerContext;", "frontend.native"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class NativeSharedImmutableChecker implements DeclarationChecker {
    public static final NativeSharedImmutableChecker INSTANCE = new NativeSharedImmutableChecker();
    private static final FqName sharedImmutableFqName = new FqName("kotlin.native.concurrent.SharedImmutable");

    private NativeSharedImmutableChecker() {
    }

    @Override // org.jetbrains.kotlin.resolve.checkers.DeclarationChecker
    public void check(final KtDeclaration declaration, final DeclarationDescriptor descriptor, final DeclarationCheckerContext context) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(context, "context");
        NativeSharedImmutableChecker nativeSharedImmutableChecker = this;
        FqName fqName = sharedImmutableFqName;
        NativeThreadLocalCheckerKt.check(nativeSharedImmutableChecker, fqName, declaration, descriptor, context, ErrorsNative.INAPPLICABLE_SHARED_IMMUTABLE_PROPERTY, new Function1<DeclarationDescriptor, Boolean>() { // from class: org.jetbrains.kotlin.resolve.konan.diagnostics.NativeSharedImmutableChecker.check.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(DeclarationDescriptor it) {
                Intrinsics.checkNotNullParameter(it, "it");
                DeclarationDescriptor declarationDescriptor = descriptor;
                boolean z = true;
                boolean z2 = (declarationDescriptor instanceof VariableDescriptor) && ((VariableDescriptor) declarationDescriptor).isVar();
                DeclarationDescriptor declarationDescriptor2 = descriptor;
                boolean z3 = (declarationDescriptor2 instanceof PropertyDescriptor) && ResolveUtilKt.hasBackingField((PropertyDescriptor) declarationDescriptor2, context.getTrace().getContext());
                KtDeclaration ktDeclaration = declaration;
                boolean z4 = (ktDeclaration instanceof KtProperty) && ((KtProperty) ktDeclaration).getDelegate() != null;
                if ((z2 || !z3) && !z4) {
                    z = false;
                }
                return Boolean.valueOf(z);
            }
        });
        NativeThreadLocalCheckerKt.check(nativeSharedImmutableChecker, fqName, declaration, descriptor, context, ErrorsNative.INAPPLICABLE_SHARED_IMMUTABLE_TOP_LEVEL, new Function1<DeclarationDescriptor, Boolean>() { // from class: org.jetbrains.kotlin.resolve.konan.diagnostics.NativeSharedImmutableChecker.check.2
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(DeclarationDescriptor it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return Boolean.valueOf(DescriptorUtils.isTopLevelDeclaration(descriptor));
            }
        });
    }
}

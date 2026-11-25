package org.jetbrains.kotlin.resolve.checkers;

import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyAccessorDescriptor;
import org.jetbrains.kotlin.diagnostics.Errors;
import org.jetbrains.kotlin.psi.KtDeclaration;
import org.jetbrains.kotlin.psi.KtNamedDeclaration;
import org.jetbrains.kotlin.psi.KtProperty;
import org.jetbrains.kotlin.resolve.deprecation.DeprecationResolver;
import org.jetbrains.kotlin.resolve.deprecation.DescriptorBasedDeprecationInfo;

/* JADX INFO: compiled from: DeprecationInheritanceChecker.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J \u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/resolve/checkers/DeprecationInheritanceChecker;", "Lorg/jetbrains/kotlin/resolve/checkers/DeclarationChecker;", "()V", "check", "", "declaration", "Lorg/jetbrains/kotlin/psi/KtDeclaration;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "context", "Lorg/jetbrains/kotlin/resolve/checkers/DeclarationCheckerContext;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class DeprecationInheritanceChecker implements DeclarationChecker {
    public static final DeprecationInheritanceChecker INSTANCE = new DeprecationInheritanceChecker();

    private DeprecationInheritanceChecker() {
    }

    @Override // org.jetbrains.kotlin.resolve.checkers.DeclarationChecker
    public void check(KtDeclaration declaration, DeclarationDescriptor descriptor, DeclarationCheckerContext context) {
        Pair pair;
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(context, "context");
        if (declaration instanceof KtNamedDeclaration) {
            if ((declaration instanceof KtProperty) && (descriptor instanceof PropertyAccessorDescriptor) && ((PropertyAccessorDescriptor) descriptor).isDefault()) {
                return;
            }
            DeprecationResolver deprecationResolver = context.getDeprecationResolver();
            if (deprecationResolver.areDeprecationsInheritedFromOverriden(descriptor)) {
                if (context.getLanguageVersionSettings().supportsFeature(LanguageFeature.StopPropagatingDeprecationThroughOverrides)) {
                    pair = TuplesKt.to(deprecationResolver.getHiddenDeprecationsFromOverriden(descriptor), "");
                } else {
                    pair = TuplesKt.to(deprecationResolver.getDeprecations(descriptor), "This deprecation won't be inherited in future releases. ");
                }
                List<DescriptorBasedDeprecationInfo> list = (List) pair.component1();
                context.getTrace().report(Errors.OVERRIDE_DEPRECATION.on(declaration, (String) pair.component2(), descriptor, list));
            }
        }
    }
}

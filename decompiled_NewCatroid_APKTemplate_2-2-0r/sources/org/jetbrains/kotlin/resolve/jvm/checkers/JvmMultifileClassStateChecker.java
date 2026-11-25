package org.jetbrains.kotlin.resolve.jvm.checkers;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.config.JvmAnalysisFlags;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.fileClasses.JvmFileClassUtil;
import org.jetbrains.kotlin.psi.KtDeclaration;
import org.jetbrains.kotlin.psi.KtFile;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.DescriptorUtils;
import org.jetbrains.kotlin.resolve.checkers.DeclarationChecker;
import org.jetbrains.kotlin.resolve.checkers.DeclarationCheckerContext;
import org.jetbrains.kotlin.resolve.jvm.diagnostics.ErrorsJvm;

/* JADX INFO: compiled from: JvmMultifileClassStateChecker.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J \u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/resolve/jvm/checkers/JvmMultifileClassStateChecker;", "Lorg/jetbrains/kotlin/resolve/checkers/DeclarationChecker;", "()V", "check", "", "declaration", "Lorg/jetbrains/kotlin/psi/KtDeclaration;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "context", "Lorg/jetbrains/kotlin/resolve/checkers/DeclarationCheckerContext;", "frontend.java"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JvmMultifileClassStateChecker implements DeclarationChecker {
    public static final JvmMultifileClassStateChecker INSTANCE = new JvmMultifileClassStateChecker();

    private JvmMultifileClassStateChecker() {
    }

    @Override // org.jetbrains.kotlin.resolve.checkers.DeclarationChecker
    public void check(KtDeclaration declaration, DeclarationDescriptor descriptor, DeclarationCheckerContext context) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(context, "context");
        if ((descriptor instanceof PropertyDescriptor) && DescriptorUtils.isTopLevelDeclaration(descriptor)) {
            PropertyDescriptor propertyDescriptor = (PropertyDescriptor) descriptor;
            if (propertyDescriptor.get$isExpect() || propertyDescriptor.isConst() || !((Boolean) context.getLanguageVersionSettings().getFlag(JvmAnalysisFlags.getInheritMultifileParts())).booleanValue()) {
                return;
            }
            KtFile containingKtFile = declaration.getContainingKtFile();
            Intrinsics.checkNotNullExpressionValue(containingKtFile, "getContainingKtFile(...)");
            if (JvmFileClassUtil.getFileClassInfoNoResolve(containingKtFile).getWithJvmMultifileClass()) {
                if (propertyDescriptor.getIsDelegated() || Intrinsics.areEqual(context.getTrace().getContext().get(BindingContext.BACKING_FIELD_REQUIRED, descriptor), (Object) true)) {
                    context.getTrace().report(ErrorsJvm.STATE_IN_MULTIFILE_CLASS.on(declaration));
                }
            }
        }
    }
}

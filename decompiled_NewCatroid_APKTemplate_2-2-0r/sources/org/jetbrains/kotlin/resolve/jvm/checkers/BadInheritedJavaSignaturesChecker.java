package org.jetbrains.kotlin.resolve.jvm.checkers;

import java.util.Iterator;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.diagnostics.Errors;
import org.jetbrains.kotlin.psi.KtClass;
import org.jetbrains.kotlin.psi.KtDeclaration;
import org.jetbrains.kotlin.psi.KtObjectDeclaration;
import org.jetbrains.kotlin.renderer.DescriptorRenderer;
import org.jetbrains.kotlin.resolve.checkers.DeclarationChecker;
import org.jetbrains.kotlin.resolve.checkers.DeclarationCheckerContext;
import org.jetbrains.kotlin.resolve.scopes.MemberScope;
import org.jetbrains.kotlin.resolve.scopes.ResolutionScope;

/* JADX INFO: compiled from: BadInheritedJavaSignaturesChecker.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J \u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/resolve/jvm/checkers/BadInheritedJavaSignaturesChecker;", "Lorg/jetbrains/kotlin/resolve/checkers/DeclarationChecker;", "()V", "check", "", "declaration", "Lorg/jetbrains/kotlin/psi/KtDeclaration;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "context", "Lorg/jetbrains/kotlin/resolve/checkers/DeclarationCheckerContext;", "frontend.java"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class BadInheritedJavaSignaturesChecker implements DeclarationChecker {
    public static final BadInheritedJavaSignaturesChecker INSTANCE = new BadInheritedJavaSignaturesChecker();

    private BadInheritedJavaSignaturesChecker() {
    }

    @Override // org.jetbrains.kotlin.resolve.checkers.DeclarationChecker
    public void check(KtDeclaration declaration, DeclarationDescriptor descriptor, DeclarationCheckerContext context) {
        DeclarationDescriptor declarationDescriptorFindFirstBadJavaSignatureOverridden;
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(context, "context");
        if (descriptor instanceof ClassDescriptor) {
            MemberScope unsubstitutedMemberScope = ((ClassDescriptor) descriptor).getUnsubstitutedMemberScope();
            Intrinsics.checkNotNullExpressionValue(unsubstitutedMemberScope, "getUnsubstitutedMemberScope(...)");
            KtDeclaration objectKeyword = null;
            Iterator it = ResolutionScope.DefaultImpls.getContributedDescriptors$default(unsubstitutedMemberScope, null, null, 3, null).iterator();
            do {
                if (!it.hasNext()) {
                    declarationDescriptorFindFirstBadJavaSignatureOverridden = null;
                    break;
                }
                declarationDescriptorFindFirstBadJavaSignatureOverridden = BadInheritedJavaSignaturesCheckerKt.findFirstBadJavaSignatureOverridden((DeclarationDescriptor) it.next());
            } while (declarationDescriptorFindFirstBadJavaSignatureOverridden == null);
            if (declarationDescriptorFindFirstBadJavaSignatureOverridden != null) {
                if (declaration instanceof KtClass) {
                    KtClass ktClass = (KtClass) declaration;
                    objectKeyword = ktClass.mo7392getNameIdentifier();
                    if (objectKeyword == null) {
                        objectKeyword = ktClass.getClassOrInterfaceKeyword();
                    }
                } else if (declaration instanceof KtObjectDeclaration) {
                    objectKeyword = ((KtObjectDeclaration) declaration).getObjectKeyword();
                }
                if (objectKeyword == null) {
                    objectKeyword = declaration;
                }
                context.getTrace().report(Errors.UNSUPPORTED_INHERITANCE_FROM_JAVA_MEMBER_REFERENCING_KOTLIN_FUNCTION.on(objectKeyword, DescriptorRenderer.COMPACT_WITH_SHORT_TYPES.render(declarationDescriptorFindFirstBadJavaSignatureOverridden)));
            }
        }
    }
}

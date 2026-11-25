package org.jetbrains.kotlin.resolve.checkers;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.psi.KtCallableDeclaration;
import org.jetbrains.kotlin.psi.KtClass;
import org.jetbrains.kotlin.psi.KtDeclaration;
import org.jetbrains.kotlin.psi.KtDestructuringDeclaration;
import org.jetbrains.kotlin.psi.KtParameterList;
import org.jetbrains.kotlin.psi.KtProperty;
import org.jetbrains.kotlin.psi.KtPropertyAccessor;
import org.jetbrains.kotlin.psi.KtTypeAlias;
import org.jetbrains.kotlin.psi.KtTypeParameterList;

/* JADX INFO: compiled from: TrailingCommaChecker.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J \u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/resolve/checkers/TrailingCommaDeclarationChecker;", "Lorg/jetbrains/kotlin/resolve/checkers/DeclarationChecker;", "()V", "check", "", "declaration", "Lorg/jetbrains/kotlin/psi/KtDeclaration;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "context", "Lorg/jetbrains/kotlin/resolve/checkers/DeclarationCheckerContext;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class TrailingCommaDeclarationChecker implements DeclarationChecker {
    public static final TrailingCommaDeclarationChecker INSTANCE = new TrailingCommaDeclarationChecker();

    private TrailingCommaDeclarationChecker() {
    }

    @Override // org.jetbrains.kotlin.resolve.checkers.DeclarationChecker
    public void check(KtDeclaration declaration, DeclarationDescriptor descriptor, DeclarationCheckerContext context) {
        KtParameterList parameterList;
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(context, "context");
        PsiElement trailingComma = null;
        if (declaration instanceof KtClass) {
            TrailingCommaChecker trailingCommaChecker = TrailingCommaChecker.INSTANCE;
            KtTypeParameterList typeParameterList = ((KtClass) declaration).mo7395getTypeParameterList();
            trailingCommaChecker.check(typeParameterList != null ? typeParameterList.getTrailingComma() : null, context.getTrace(), context.getLanguageVersionSettings());
            return;
        }
        if (declaration instanceof KtCallableDeclaration) {
            TrailingCommaChecker trailingCommaChecker2 = TrailingCommaChecker.INSTANCE;
            KtCallableDeclaration ktCallableDeclaration = (KtCallableDeclaration) declaration;
            KtParameterList valueParameterList = ktCallableDeclaration.getValueParameterList();
            trailingCommaChecker2.check(valueParameterList != null ? valueParameterList.getTrailingComma() : null, context.getTrace(), context.getLanguageVersionSettings());
            TrailingCommaChecker trailingCommaChecker3 = TrailingCommaChecker.INSTANCE;
            KtTypeParameterList typeParameterList2 = ktCallableDeclaration.mo7395getTypeParameterList();
            trailingCommaChecker3.check(typeParameterList2 != null ? typeParameterList2.getTrailingComma() : null, context.getTrace(), context.getLanguageVersionSettings());
            if (declaration instanceof KtProperty) {
                KtProperty ktProperty = (KtProperty) declaration;
                if (ktProperty.getSetter() != null) {
                    TrailingCommaChecker trailingCommaChecker4 = TrailingCommaChecker.INSTANCE;
                    KtPropertyAccessor setter = ktProperty.getSetter();
                    if (setter != null && (parameterList = setter.getParameterList()) != null) {
                        trailingComma = parameterList.getTrailingComma();
                    }
                    trailingCommaChecker4.check(trailingComma, context.getTrace(), context.getLanguageVersionSettings());
                    return;
                }
                return;
            }
            return;
        }
        if (declaration instanceof KtDestructuringDeclaration) {
            TrailingCommaChecker.INSTANCE.check(((KtDestructuringDeclaration) declaration).getTrailingComma(), context.getTrace(), context.getLanguageVersionSettings());
        } else if (declaration instanceof KtTypeAlias) {
            TrailingCommaChecker trailingCommaChecker5 = TrailingCommaChecker.INSTANCE;
            KtTypeParameterList typeParameterList3 = ((KtTypeAlias) declaration).mo7395getTypeParameterList();
            trailingCommaChecker5.check(typeParameterList3 != null ? typeParameterList3.getTrailingComma() : null, context.getTrace(), context.getLanguageVersionSettings());
        }
    }
}

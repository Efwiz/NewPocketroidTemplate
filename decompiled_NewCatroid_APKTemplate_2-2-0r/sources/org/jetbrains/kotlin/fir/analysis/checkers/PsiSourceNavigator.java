package org.jetbrains.kotlin.fir.analysis.checkers;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.KtPsiSourceElement;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.KtSourceElementKt;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiNameIdentifierOwner;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.LeafPsiElement;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.symbols.impl.FirValueParameterSymbol;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.lexer.KtTokens;
import org.jetbrains.kotlin.psi.KtCatchClause;
import org.jetbrains.kotlin.psi.KtConstructorCalleeExpression;
import org.jetbrains.kotlin.psi.KtNameReferenceExpression;
import org.jetbrains.kotlin.psi.KtNullableType;
import org.jetbrains.kotlin.psi.KtTypeElement;
import org.jetbrains.kotlin.psi.KtTypeProjection;
import org.jetbrains.kotlin.psi.KtTypeReference;

/* JADX INFO: compiled from: SourceNavigator.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\r\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u0004\u0018\u00010\u0004*\u00020\u0005H\u0016J\u000e\u0010\u0006\u001a\u0004\u0018\u00010\u0007*\u00020\bH\u0016J\f\u0010\t\u001a\u00020\n*\u00020\u000bH\u0016J\f\u0010\f\u001a\u00020\n*\u00020\rH\u0016J\f\u0010\u000e\u001a\u00020\n*\u00020\rH\u0016J \u0010\u000f\u001a\u0004\u0018\u0001H\u0010\"\n\b\u0000\u0010\u0010\u0018\u0001*\u00020\u0011*\u00020\u0005H\u0082\b¢\u0006\u0002\u0010\u0012J \u0010\u000f\u001a\u0004\u0018\u0001H\u0010\"\n\b\u0000\u0010\u0010\u0018\u0001*\u00020\u0011*\u00020\u0013H\u0082\b¢\u0006\u0002\u0010\u0014¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/PsiSourceNavigator;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/LightTreeSourceNavigator;", "()V", "getRawIdentifier", "", "Lorg/jetbrains/kotlin/KtSourceElement;", "getRawName", "", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;", "isCatchElementParameter", "", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirValueParameterSymbol;", "isInConstructorCallee", "Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "isRedundantNullable", "psi", "P", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "(Lorg/jetbrains/kotlin/KtSourceElement;)Lcom/intellij/psi/PsiElement;", "Lorg/jetbrains/kotlin/fir/FirElement;", "(Lorg/jetbrains/kotlin/fir/FirElement;)Lcom/intellij/psi/PsiElement;", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class PsiSourceNavigator extends LightTreeSourceNavigator {
    public static final PsiSourceNavigator INSTANCE = new PsiSourceNavigator();

    private PsiSourceNavigator() {
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.LightTreeSourceNavigator, org.jetbrains.kotlin.fir.analysis.checkers.SourceNavigator
    public boolean isInConstructorCallee(FirTypeRef firTypeRef) {
        KtTypeReference ktTypeReference;
        Intrinsics.checkNotNullParameter(firTypeRef, "<this>");
        KtSourceElement source = firTypeRef.getSource();
        if (source != null) {
            KtPsiSourceElement ktPsiSourceElement = source instanceof KtPsiSourceElement ? (KtPsiSourceElement) source : null;
            PsiElement psi = ktPsiSourceElement != null ? ktPsiSourceElement.getPsi() : null;
            if (!(psi instanceof KtTypeReference)) {
                psi = null;
            }
            ktTypeReference = (KtTypeReference) psi;
        } else {
            ktTypeReference = null;
        }
        KtTypeReference ktTypeReference2 = ktTypeReference;
        return (ktTypeReference2 != null ? ktTypeReference2.getLightOwner() : null) instanceof KtConstructorCalleeExpression;
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.LightTreeSourceNavigator, org.jetbrains.kotlin.fir.analysis.checkers.SourceNavigator
    public String getRawName(FirDeclaration firDeclaration) {
        PsiNameIdentifierOwner psiNameIdentifierOwner;
        PsiElement psiElementMo7392getNameIdentifier;
        Intrinsics.checkNotNullParameter(firDeclaration, "<this>");
        KtSourceElement source = firDeclaration.getSource();
        if (source != null) {
            KtPsiSourceElement ktPsiSourceElement = source instanceof KtPsiSourceElement ? (KtPsiSourceElement) source : null;
            PsiElement psi = ktPsiSourceElement != null ? ktPsiSourceElement.getPsi() : null;
            if (!(psi instanceof PsiNameIdentifierOwner)) {
                psi = null;
            }
            psiNameIdentifierOwner = (PsiNameIdentifierOwner) psi;
        } else {
            psiNameIdentifierOwner = null;
        }
        PsiNameIdentifierOwner psiNameIdentifierOwner2 = psiNameIdentifierOwner instanceof PsiNameIdentifierOwner ? psiNameIdentifierOwner : null;
        if (psiNameIdentifierOwner2 == null || (psiElementMo7392getNameIdentifier = psiNameIdentifierOwner2.mo7392getNameIdentifier()) == null) {
            return null;
        }
        return psiElementMo7392getNameIdentifier.getText();
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.LightTreeSourceNavigator, org.jetbrains.kotlin.fir.analysis.checkers.SourceNavigator
    public boolean isCatchElementParameter(FirValueParameterSymbol firValueParameterSymbol) {
        PsiElement lightOwner;
        Intrinsics.checkNotNullParameter(firValueParameterSymbol, "<this>");
        KtSourceElement source = firValueParameterSymbol.getSource();
        PsiElement lightOwner2 = null;
        if (source != null) {
            KtPsiSourceElement ktPsiSourceElement = source instanceof KtPsiSourceElement ? (KtPsiSourceElement) source : null;
            PsiElement psi = ktPsiSourceElement != null ? ktPsiSourceElement.getPsi() : null;
            if (!(psi instanceof PsiElement)) {
                psi = null;
            }
            if (psi != null && (lightOwner = psi.getLightOwner()) != null) {
                lightOwner2 = lightOwner.getLightOwner();
            }
        }
        return lightOwner2 instanceof KtCatchClause;
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.LightTreeSourceNavigator, org.jetbrains.kotlin.fir.analysis.checkers.SourceNavigator
    public boolean isRedundantNullable(FirTypeRef firTypeRef) {
        Intrinsics.checkNotNullParameter(firTypeRef, "<this>");
        KtSourceElement source = firTypeRef.getSource();
        if (source == null) {
            return false;
        }
        PsiElement psi = KtSourceElementKt.getPsi(source);
        KtTypeReference ktTypeReference = psi instanceof KtTypeReference ? (KtTypeReference) psi : null;
        if (ktTypeReference == null) {
            return false;
        }
        KtTypeElement typeElement = ktTypeReference.getTypeElement();
        KtNullableType ktNullableType = typeElement instanceof KtNullableType ? (KtNullableType) typeElement : null;
        if (ktNullableType == null) {
            return false;
        }
        return ktNullableType.getInnerType() instanceof KtNullableType;
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.LightTreeSourceNavigator, org.jetbrains.kotlin.fir.analysis.checkers.SourceNavigator
    public CharSequence getRawIdentifier(KtSourceElement ktSourceElement) {
        KtTypeElement typeElement;
        Intrinsics.checkNotNullParameter(ktSourceElement, "<this>");
        String text = null;
        KtPsiSourceElement ktPsiSourceElement = ktSourceElement instanceof KtPsiSourceElement ? (KtPsiSourceElement) ktSourceElement : null;
        PsiElement psi = ktPsiSourceElement != null ? ktPsiSourceElement.getPsi() : null;
        if (!(psi instanceof PsiElement)) {
            psi = null;
        }
        if (psi instanceof KtNameReferenceExpression) {
            return ((KtNameReferenceExpression) psi).getReferencedNameElement().getNode().getChars();
        }
        if (psi instanceof KtTypeProjection) {
            KtTypeReference typeReference = ((KtTypeProjection) psi).getTypeReference();
            if (typeReference != null && (typeElement = typeReference.getTypeElement()) != null) {
                text = typeElement.getText();
            }
            return text;
        }
        if (!(psi instanceof LeafPsiElement)) {
            return null;
        }
        LeafPsiElement leafPsiElement = (LeafPsiElement) psi;
        if (Intrinsics.areEqual(leafPsiElement.getElementType(), KtTokens.IDENTIFIER)) {
            return leafPsiElement.getChars();
        }
        return null;
    }
}

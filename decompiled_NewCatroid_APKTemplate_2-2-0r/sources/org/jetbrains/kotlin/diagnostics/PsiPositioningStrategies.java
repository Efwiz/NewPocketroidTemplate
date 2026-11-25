package org.jetbrains.kotlin.diagnostics;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.AbstractKtSourceElement;
import org.jetbrains.kotlin.KtPsiSourceElement;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.KtSourceElementKt;
import org.jetbrains.kotlin.cfg.UnreachableCode;
import org.jetbrains.kotlin.com.intellij.openapi.util.TextRange;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.psi.KtElement;
import org.jetbrains.kotlin.psi.KtNamedDeclaration;

/* JADX INFO: compiled from: PsiPositioningStrategies.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0017\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0017\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\u0007¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/diagnostics/PsiPositioningStrategies;", "", "()V", "ACTUAL_DECLARATION_NAME", "Lorg/jetbrains/kotlin/diagnostics/PositioningStrategy;", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "getACTUAL_DECLARATION_NAME", "()Lorg/jetbrains/kotlin/diagnostics/PositioningStrategy;", "UNREACHABLE_CODE", "getUNREACHABLE_CODE", "frontend.common-psi"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class PsiPositioningStrategies {
    public static final PsiPositioningStrategies INSTANCE = new PsiPositioningStrategies();
    private static final PositioningStrategy<PsiElement> UNREACHABLE_CODE = new PositioningStrategy<PsiElement>() { // from class: org.jetbrains.kotlin.diagnostics.PsiPositioningStrategies$UNREACHABLE_CODE$1
        @Override // org.jetbrains.kotlin.diagnostics.PositioningStrategy
        public List<TextRange> markDiagnostic(DiagnosticMarker diagnostic) {
            Intrinsics.checkNotNullParameter(diagnostic, "diagnostic");
            KtDiagnosticWithParameters2 ktDiagnosticWithParameters2 = (KtDiagnosticWithParameters2) diagnostic;
            AbstractKtSourceElement element = ktDiagnosticWithParameters2.getElement();
            Intrinsics.checkNotNull(element, "null cannot be cast to non-null type org.jetbrains.kotlin.KtPsiSourceElement");
            UnreachableCode.Companion companion = UnreachableCode.Companion;
            PsiElement psi = ((KtPsiSourceElement) element).getPsi();
            Intrinsics.checkNotNull(psi, "null cannot be cast to non-null type org.jetbrains.kotlin.psi.KtElement");
            KtElement ktElement = (KtElement) psi;
            Iterable iterable = (Iterable) ktDiagnosticWithParameters2.getA();
            ArrayList arrayList = new ArrayList();
            Iterator it = iterable.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                PsiElement psi2 = KtSourceElementKt.getPsi((KtSourceElement) it.next());
                KtElement ktElement2 = psi2 instanceof KtElement ? (KtElement) psi2 : null;
                if (ktElement2 != null) {
                    arrayList.add(ktElement2);
                }
            }
            Set<? extends KtElement> set = CollectionsKt.toSet(arrayList);
            Iterable iterable2 = (Iterable) ktDiagnosticWithParameters2.getB();
            ArrayList arrayList2 = new ArrayList();
            Iterator it2 = iterable2.iterator();
            while (it2.hasNext()) {
                PsiElement psi3 = KtSourceElementKt.getPsi((KtSourceElement) it2.next());
                KtElement ktElement3 = psi3 instanceof KtElement ? (KtElement) psi3 : null;
                if (ktElement3 != null) {
                    arrayList2.add(ktElement3);
                }
            }
            return companion.getUnreachableTextRanges(ktElement, set, CollectionsKt.toSet(arrayList2));
        }
    };
    private static final PositioningStrategy<PsiElement> ACTUAL_DECLARATION_NAME = new PositioningStrategy<PsiElement>() { // from class: org.jetbrains.kotlin.diagnostics.PsiPositioningStrategies$ACTUAL_DECLARATION_NAME$1
        @Override // org.jetbrains.kotlin.diagnostics.PositioningStrategy
        public List<TextRange> markDiagnostic(DiagnosticMarker diagnostic) {
            PsiElement nameIdentifier;
            Intrinsics.checkNotNullParameter(diagnostic, "diagnostic");
            if (!(diagnostic instanceof KtDiagnostic)) {
                throw new IllegalArgumentException("Failed requirement.".toString());
            }
            PsiElement psi = KtSourceElementKt.getPsi(((KtDiagnostic) diagnostic).getElement());
            if (psi == null) {
                return CollectionsKt.emptyList();
            }
            KtNamedDeclaration ktNamedDeclaration = psi instanceof KtNamedDeclaration ? (KtNamedDeclaration) psi : null;
            if (ktNamedDeclaration != null && (nameIdentifier = ktNamedDeclaration.mo7392getNameIdentifier()) != null) {
                return mark(nameIdentifier);
            }
            return mark(psi);
        }
    };

    private PsiPositioningStrategies() {
    }

    public final PositioningStrategy<PsiElement> getUNREACHABLE_CODE() {
        return UNREACHABLE_CODE;
    }

    public final PositioningStrategy<PsiElement> getACTUAL_DECLARATION_NAME() {
        return ACTUAL_DECLARATION_NAME;
    }
}

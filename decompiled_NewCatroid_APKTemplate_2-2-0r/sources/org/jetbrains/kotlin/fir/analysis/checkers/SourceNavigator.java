package org.jetbrains.kotlin.fir.analysis.checkers;

import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.KtLightSourceElement;
import org.jetbrains.kotlin.KtPsiSourceElement;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.fir.FirElement;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.symbols.impl.FirValueParameterSymbol;
import org.jetbrains.kotlin.fir.types.FirTypeRef;

/* JADX INFO: compiled from: SourceNavigator.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\r\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\bf\u0018\u0000 \u000f2\u00020\u0001:\u0001\u000fJ\u000e\u0010\u0002\u001a\u0004\u0018\u00010\u0003*\u00020\u0004H&J\u000e\u0010\u0005\u001a\u0004\u0018\u00010\u0006*\u00020\u0007H&J\f\u0010\b\u001a\u00020\t*\u00020\nH&J\f\u0010\u000b\u001a\u00020\t*\u00020\fH&J\f\u0010\r\u001a\u00020\t*\u00020\fH&J\f\u0010\u000e\u001a\u00020\t*\u00020\fH&ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0010À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/SourceNavigator;", "", "getRawIdentifier", "", "Lorg/jetbrains/kotlin/KtSourceElement;", "getRawName", "", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;", "isCatchElementParameter", "", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirValueParameterSymbol;", "isInConstructorCallee", "Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "isInTypeConstraint", "isRedundantNullable", "Companion", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface SourceNavigator {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = Companion.$$INSTANCE;

    CharSequence getRawIdentifier(KtSourceElement ktSourceElement);

    String getRawName(FirDeclaration firDeclaration);

    boolean isCatchElementParameter(FirValueParameterSymbol firValueParameterSymbol);

    boolean isInConstructorCallee(FirTypeRef firTypeRef);

    boolean isInTypeConstraint(FirTypeRef firTypeRef);

    boolean isRedundantNullable(FirTypeRef firTypeRef);

    /* JADX INFO: compiled from: SourceNavigator.kt */
    @Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bJ\u0010\u0010\t\u001a\u00020\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\nJ4\u0010\u000b\u001a\u0002H\f\"\u0004\b\u0000\u0010\f*\u00020\b2\u0017\u0010\r\u001a\u0013\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u0002H\f0\u000e¢\u0006\u0002\b\u000fH\u0086\bø\u0001\u0000¢\u0006\u0002\u0010\u0010R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/SourceNavigator$Companion;", "", "()V", "lightTreeInstance", "Lorg/jetbrains/kotlin/fir/analysis/checkers/LightTreeSourceNavigator;", "forElement", "Lorg/jetbrains/kotlin/fir/analysis/checkers/SourceNavigator;", "e", "Lorg/jetbrains/kotlin/fir/FirElement;", "forSource", "Lorg/jetbrains/kotlin/KtSourceElement;", "withNavigator", "R", "block", "Lkotlin/Function1;", "Lkotlin/ExtensionFunctionType;", "(Lorg/jetbrains/kotlin/fir/FirElement;Lkotlin/jvm/functions/Function1;)Ljava/lang/Object;", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        static final /* synthetic */ Companion $$INSTANCE = new Companion();
        private static final LightTreeSourceNavigator lightTreeInstance = new LightTreeSourceNavigator();

        private Companion() {
        }

        public final SourceNavigator forElement(FirElement e) {
            Intrinsics.checkNotNullParameter(e, "e");
            return forSource(e.getSource());
        }

        public final SourceNavigator forSource(KtSourceElement e) {
            if (e instanceof KtLightSourceElement) {
                return lightTreeInstance;
            }
            if (e instanceof KtPsiSourceElement) {
                return PsiSourceNavigator.INSTANCE;
            }
            if (e == null) {
                return lightTreeInstance;
            }
            throw new NoWhenBranchMatchedException();
        }

        public final <R> R withNavigator(FirElement firElement, Function1<? super SourceNavigator, ? extends R> block) {
            Intrinsics.checkNotNullParameter(firElement, "<this>");
            Intrinsics.checkNotNullParameter(block, "block");
            return block.invoke(forSource(firElement.getSource()));
        }
    }
}

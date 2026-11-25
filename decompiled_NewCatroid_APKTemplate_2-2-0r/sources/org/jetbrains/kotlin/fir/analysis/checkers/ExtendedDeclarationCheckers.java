package org.jetbrains.kotlin.fir.analysis.checkers;

import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.SetsKt;
import org.jetbrains.kotlin.fir.analysis.cfa.AbstractFirPropertyInitializationChecker;
import org.jetbrains.kotlin.fir.analysis.cfa.FirReturnsImpliesAnalyzer;
import org.jetbrains.kotlin.fir.analysis.checkers.cfa.FirControlFlowChecker;
import org.jetbrains.kotlin.fir.analysis.checkers.declaration.DeclarationCheckers;
import org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirDeclarationChecker;
import org.jetbrains.kotlin.fir.analysis.checkers.extended.CanBeValChecker;
import org.jetbrains.kotlin.fir.analysis.checkers.extended.PlatformClassMappedToKotlinImportsChecker;
import org.jetbrains.kotlin.fir.analysis.checkers.extended.RedundantExplicitTypeChecker;
import org.jetbrains.kotlin.fir.analysis.checkers.extended.RedundantModalityModifierSyntaxChecker;
import org.jetbrains.kotlin.fir.analysis.checkers.extended.RedundantReturnUnitType;
import org.jetbrains.kotlin.fir.analysis.checkers.extended.RedundantSetterParameterTypeChecker;
import org.jetbrains.kotlin.fir.analysis.checkers.extended.RedundantVisibilityModifierSyntaxChecker;
import org.jetbrains.kotlin.fir.analysis.checkers.extended.UnreachableCodeChecker;
import org.jetbrains.kotlin.fir.analysis.checkers.extended.UnusedChecker;
import org.jetbrains.kotlin.fir.analysis.checkers.syntax.FirDeclarationSyntaxChecker;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirFile;
import org.jetbrains.kotlin.fir.declarations.FirProperty;
import org.jetbrains.kotlin.fir.declarations.FirSimpleFunction;

/* JADX INFO: compiled from: ExtendedDeclarationCheckers.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000T\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R$\u0010\u0003\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020\u00060\u0005j\u0002`\u00070\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\tR\u001a\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u000b0\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\tR$\u0010\r\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020\u000e0\u0005j\u0002`\u000f0\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0010\u0010\tR$\u0010\u0011\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020\u00120\u0005j\u0002`\u00130\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0014\u0010\tR$\u0010\u0015\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020\u00160\u0005j\u0002`\u00170\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0018\u0010\tR\u001a\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u001a0\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001b\u0010\t¨\u0006\u001c"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/ExtendedDeclarationCheckers;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/DeclarationCheckers;", "()V", "basicDeclarationCheckers", "", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirDeclarationChecker;", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirBasicDeclarationChecker;", "getBasicDeclarationCheckers", "()Ljava/util/Set;", "controlFlowAnalyserCheckers", "Lorg/jetbrains/kotlin/fir/analysis/checkers/cfa/FirControlFlowChecker;", "getControlFlowAnalyserCheckers", "fileCheckers", "Lorg/jetbrains/kotlin/fir/declarations/FirFile;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirFileChecker;", "getFileCheckers", "propertyCheckers", "Lorg/jetbrains/kotlin/fir/declarations/FirProperty;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirPropertyChecker;", "getPropertyCheckers", "simpleFunctionCheckers", "Lorg/jetbrains/kotlin/fir/declarations/FirSimpleFunction;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirSimpleFunctionChecker;", "getSimpleFunctionCheckers", "variableAssignmentCfaBasedCheckers", "Lorg/jetbrains/kotlin/fir/analysis/cfa/AbstractFirPropertyInitializationChecker;", "getVariableAssignmentCfaBasedCheckers", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ExtendedDeclarationCheckers extends DeclarationCheckers {
    public static final ExtendedDeclarationCheckers INSTANCE = new ExtendedDeclarationCheckers();

    private ExtendedDeclarationCheckers() {
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.declaration.DeclarationCheckers
    public Set<FirDeclarationChecker<FirFile>> getFileCheckers() {
        return SetsKt.setOf(PlatformClassMappedToKotlinImportsChecker.INSTANCE);
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.declaration.DeclarationCheckers
    public Set<FirDeclarationChecker<FirDeclaration>> getBasicDeclarationCheckers() {
        return SetsKt.setOf((Object[]) new FirDeclarationSyntaxChecker[]{RedundantVisibilityModifierSyntaxChecker.INSTANCE, RedundantModalityModifierSyntaxChecker.INSTANCE});
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.declaration.DeclarationCheckers
    public Set<FirDeclarationChecker<FirProperty>> getPropertyCheckers() {
        return SetsKt.setOf((Object[]) new FirDeclarationChecker[]{RedundantSetterParameterTypeChecker.INSTANCE, RedundantExplicitTypeChecker.INSTANCE});
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.declaration.DeclarationCheckers
    public Set<AbstractFirPropertyInitializationChecker> getVariableAssignmentCfaBasedCheckers() {
        return SetsKt.setOf((Object[]) new AbstractFirPropertyInitializationChecker[]{CanBeValChecker.INSTANCE, UnusedChecker.INSTANCE});
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.declaration.DeclarationCheckers
    public Set<FirControlFlowChecker> getControlFlowAnalyserCheckers() {
        return SetsKt.setOf((Object[]) new FirControlFlowChecker[]{UnreachableCodeChecker.INSTANCE, FirReturnsImpliesAnalyzer.INSTANCE});
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.declaration.DeclarationCheckers
    public Set<FirDeclarationChecker<FirSimpleFunction>> getSimpleFunctionCheckers() {
        return SetsKt.setOf(RedundantReturnUnitType.INSTANCE);
    }
}

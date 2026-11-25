package org.jetbrains.kotlin.fir.analysis.jvm.checkers;

import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.SetsKt;
import org.jetbrains.kotlin.fir.analysis.checkers.declaration.DeclarationCheckers;
import org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirDeclarationChecker;
import org.jetbrains.kotlin.fir.analysis.jvm.checkers.declaration.FirInlineBodyRegularClassChecker;
import org.jetbrains.kotlin.fir.analysis.jvm.checkers.declaration.FirInlineBodySimpleFunctionChecker;
import org.jetbrains.kotlin.fir.analysis.jvm.checkers.declaration.FirJvmConflictsChecker;
import org.jetbrains.kotlin.fir.analysis.jvm.checkers.declaration.FirJvmDefaultChecker;
import org.jetbrains.kotlin.fir.analysis.jvm.checkers.declaration.FirJvmExternalDeclarationChecker;
import org.jetbrains.kotlin.fir.analysis.jvm.checkers.declaration.FirJvmFieldApplicabilityChecker;
import org.jetbrains.kotlin.fir.analysis.jvm.checkers.declaration.FirJvmFunctionDelegateMemberNameClashChecker;
import org.jetbrains.kotlin.fir.analysis.jvm.checkers.declaration.FirJvmInlineApplicabilityChecker;
import org.jetbrains.kotlin.fir.analysis.jvm.checkers.declaration.FirJvmInvalidAndDangerousCharactersChecker;
import org.jetbrains.kotlin.fir.analysis.jvm.checkers.declaration.FirJvmNameChecker;
import org.jetbrains.kotlin.fir.analysis.jvm.checkers.declaration.FirJvmRecordChecker;
import org.jetbrains.kotlin.fir.analysis.jvm.checkers.declaration.FirJvmRedundantRepeatableChecker;
import org.jetbrains.kotlin.fir.analysis.jvm.checkers.declaration.FirJvmStaticChecker;
import org.jetbrains.kotlin.fir.analysis.jvm.checkers.declaration.FirJvmSyntheticApplicabilityChecker;
import org.jetbrains.kotlin.fir.analysis.jvm.checkers.declaration.FirOverloadsChecker;
import org.jetbrains.kotlin.fir.analysis.jvm.checkers.declaration.FirRepeatableAnnotationChecker;
import org.jetbrains.kotlin.fir.analysis.jvm.checkers.declaration.FirStrictfpApplicabilityChecker;
import org.jetbrains.kotlin.fir.analysis.jvm.checkers.declaration.FirSynchronizedAnnotationChecker;
import org.jetbrains.kotlin.fir.analysis.jvm.checkers.declaration.FirUpperBoundsChecker;
import org.jetbrains.kotlin.fir.declarations.FirClass;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirFunction;
import org.jetbrains.kotlin.fir.declarations.FirProperty;
import org.jetbrains.kotlin.fir.declarations.FirRegularClass;
import org.jetbrains.kotlin.fir.declarations.FirSimpleFunction;
import org.jetbrains.kotlin.fir.declarations.FirTypeParameter;

/* JADX INFO: compiled from: JvmDeclarationCheckers.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000h\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R$\u0010\u0003\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020\u00060\u0005j\u0002`\u00070\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\tR$\u0010\n\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020\u000b0\u0005j\u0002`\f0\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\tR$\u0010\u000e\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020\u000f0\u0005j\u0002`\u00100\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0011\u0010\tR$\u0010\u0012\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020\u00130\u0005j\u0002`\u00140\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0015\u0010\tR$\u0010\u0016\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020\u00170\u0005j\u0002`\u00180\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0019\u0010\tR$\u0010\u001a\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020\u001b0\u0005j\u0002`\u001c0\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001d\u0010\tR$\u0010\u001e\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020\u001f0\u0005j\u0002` 0\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b!\u0010\t¨\u0006\""}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/jvm/checkers/JvmDeclarationCheckers;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/DeclarationCheckers;", "()V", "basicDeclarationCheckers", "", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirDeclarationChecker;", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirBasicDeclarationChecker;", "getBasicDeclarationCheckers", "()Ljava/util/Set;", "classCheckers", "Lorg/jetbrains/kotlin/fir/declarations/FirClass;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirClassChecker;", "getClassCheckers", "functionCheckers", "Lorg/jetbrains/kotlin/fir/declarations/FirFunction;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirFunctionChecker;", "getFunctionCheckers", "propertyCheckers", "Lorg/jetbrains/kotlin/fir/declarations/FirProperty;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirPropertyChecker;", "getPropertyCheckers", "regularClassCheckers", "Lorg/jetbrains/kotlin/fir/declarations/FirRegularClass;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirRegularClassChecker;", "getRegularClassCheckers", "simpleFunctionCheckers", "Lorg/jetbrains/kotlin/fir/declarations/FirSimpleFunction;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirSimpleFunctionChecker;", "getSimpleFunctionCheckers", "typeParameterCheckers", "Lorg/jetbrains/kotlin/fir/declarations/FirTypeParameter;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirTypeParameterChecker;", "getTypeParameterCheckers", "checkers.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JvmDeclarationCheckers extends DeclarationCheckers {
    public static final JvmDeclarationCheckers INSTANCE = new JvmDeclarationCheckers();

    private JvmDeclarationCheckers() {
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.declaration.DeclarationCheckers
    public Set<FirDeclarationChecker<FirDeclaration>> getBasicDeclarationCheckers() {
        return SetsKt.setOf((Object[]) new FirDeclarationChecker[]{FirJvmExternalDeclarationChecker.INSTANCE, FirJvmNameChecker.INSTANCE, FirJvmDefaultChecker.INSTANCE, FirJvmFunctionDelegateMemberNameClashChecker.INSTANCE, FirJvmStaticChecker.INSTANCE, FirRepeatableAnnotationChecker.INSTANCE, FirJvmInvalidAndDangerousCharactersChecker.INSTANCE, FirJvmRedundantRepeatableChecker.INSTANCE});
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.declaration.DeclarationCheckers
    public Set<FirDeclarationChecker<FirClass>> getClassCheckers() {
        return SetsKt.setOf(FirStrictfpApplicabilityChecker.INSTANCE);
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.declaration.DeclarationCheckers
    public Set<FirDeclarationChecker<FirRegularClass>> getRegularClassCheckers() {
        return SetsKt.setOf((Object[]) new FirDeclarationChecker[]{FirJvmRecordChecker.INSTANCE, FirJvmInlineApplicabilityChecker.INSTANCE, FirJvmConflictsChecker.INSTANCE, FirInlineBodyRegularClassChecker.INSTANCE});
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.declaration.DeclarationCheckers
    public Set<FirDeclarationChecker<FirProperty>> getPropertyCheckers() {
        return SetsKt.setOf((Object[]) new FirDeclarationChecker[]{FirJvmFieldApplicabilityChecker.INSTANCE, FirJvmSyntheticApplicabilityChecker.INSTANCE});
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.declaration.DeclarationCheckers
    public Set<FirDeclarationChecker<FirFunction>> getFunctionCheckers() {
        return SetsKt.setOf((Object[]) new FirDeclarationChecker[]{FirSynchronizedAnnotationChecker.INSTANCE, FirOverloadsChecker.INSTANCE});
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.declaration.DeclarationCheckers
    public Set<FirDeclarationChecker<FirTypeParameter>> getTypeParameterCheckers() {
        return SetsKt.setOf(FirUpperBoundsChecker.INSTANCE);
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.declaration.DeclarationCheckers
    public Set<FirDeclarationChecker<FirSimpleFunction>> getSimpleFunctionCheckers() {
        return SetsKt.setOf(FirInlineBodySimpleFunctionChecker.INSTANCE);
    }
}

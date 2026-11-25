package org.jetbrains.kotlin.fir.analysis.p003native.checkers;

import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.SetsKt;
import org.jetbrains.kotlin.fir.analysis.checkers.declaration.DeclarationCheckers;
import org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirDeclarationChecker;
import org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirClass;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirRegularClass;

/* JADX INFO: compiled from: NativeDeclarationCheckers.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R$\u0010\u0003\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020\u00060\u0005j\u0002`\u00070\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\tR$\u0010\n\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020\u000b0\u0005j\u0002`\f0\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\tR$\u0010\u000e\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020\u000f0\u0005j\u0002`\u00100\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0011\u0010\tR$\u0010\u0012\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020\u00130\u0005j\u0002`\u00140\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0015\u0010\t¨\u0006\u0016"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/native/checkers/NativeDeclarationCheckers;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/DeclarationCheckers;", "()V", "basicDeclarationCheckers", "", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirDeclarationChecker;", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirBasicDeclarationChecker;", "getBasicDeclarationCheckers", "()Ljava/util/Set;", "callableDeclarationCheckers", "Lorg/jetbrains/kotlin/fir/declarations/FirCallableDeclaration;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirCallableDeclarationChecker;", "getCallableDeclarationCheckers", "classCheckers", "Lorg/jetbrains/kotlin/fir/declarations/FirClass;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirClassChecker;", "getClassCheckers", "regularClassCheckers", "Lorg/jetbrains/kotlin/fir/declarations/FirRegularClass;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirRegularClassChecker;", "getRegularClassCheckers", "checkers.native"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class NativeDeclarationCheckers extends DeclarationCheckers {
    public static final NativeDeclarationCheckers INSTANCE = new NativeDeclarationCheckers();

    private NativeDeclarationCheckers() {
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.declaration.DeclarationCheckers
    public Set<FirDeclarationChecker<FirDeclaration>> getBasicDeclarationCheckers() {
        return SetsKt.setOf((Object[]) new FirDeclarationChecker[]{FirNativeThrowsChecker.INSTANCE, FirNativeSharedImmutableChecker.INSTANCE, FirNativeThreadLocalChecker.INSTANCE, FirNativeIdentifierChecker.INSTANCE, FirNativeObjCNameChecker.INSTANCE});
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.declaration.DeclarationCheckers
    public Set<FirDeclarationChecker<FirCallableDeclaration>> getCallableDeclarationCheckers() {
        return SetsKt.setOf(FirNativeObjCRefinementChecker.INSTANCE);
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.declaration.DeclarationCheckers
    public Set<FirDeclarationChecker<FirClass>> getClassCheckers() {
        return SetsKt.setOf((Object[]) new FirDeclarationChecker[]{FirNativeObjCRefinementOverridesChecker.INSTANCE, FirNativeObjCNameOverridesChecker.INSTANCE});
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.declaration.DeclarationCheckers
    public Set<FirDeclarationChecker<FirRegularClass>> getRegularClassCheckers() {
        return SetsKt.setOf((Object[]) new FirDeclarationChecker[]{FirNativeObjCRefinementAnnotationChecker.INSTANCE, FirNativeHiddenFromObjCInheritanceChecker.INSTANCE});
    }
}

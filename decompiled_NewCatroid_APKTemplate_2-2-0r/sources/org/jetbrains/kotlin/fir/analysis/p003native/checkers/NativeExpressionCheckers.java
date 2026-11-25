package org.jetbrains.kotlin.fir.analysis.p003native.checkers;

import defpackage.FirNativeForwardDeclarationGetClassCallChecker;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.SetsKt;
import org.jetbrains.kotlin.fir.analysis.checkers.expression.ExpressionCheckers;

/* JADX INFO: compiled from: NativeExpressionCheckers.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u001a\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u001a\u0010\b\u001a\b\u0012\u0004\u0012\u00020\t0\u0004X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u0007R\u001a\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\f0\u0004X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u0007¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/native/checkers/NativeExpressionCheckers;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/ExpressionCheckers;", "()V", "getClassCallCheckers", "", "LFirNativeForwardDeclarationGetClassCallChecker;", "getGetClassCallCheckers", "()Ljava/util/Set;", "qualifiedAccessExpressionCheckers", "Lorg/jetbrains/kotlin/fir/analysis/native/checkers/FirNativeForwardDeclarationReifiedChecker;", "getQualifiedAccessExpressionCheckers", "typeOperatorCallCheckers", "Lorg/jetbrains/kotlin/fir/analysis/native/checkers/FirNativeForwardDeclarationTypeOperatorChecker;", "getTypeOperatorCallCheckers", "checkers.native"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class NativeExpressionCheckers extends ExpressionCheckers {
    public static final NativeExpressionCheckers INSTANCE = new NativeExpressionCheckers();
    private static final Set<FirNativeForwardDeclarationTypeOperatorChecker> typeOperatorCallCheckers = SetsKt.setOf(FirNativeForwardDeclarationTypeOperatorChecker.INSTANCE);
    private static final Set<FirNativeForwardDeclarationGetClassCallChecker> getClassCallCheckers = SetsKt.setOf(FirNativeForwardDeclarationGetClassCallChecker.INSTANCE);
    private static final Set<FirNativeForwardDeclarationReifiedChecker> qualifiedAccessExpressionCheckers = SetsKt.setOf(FirNativeForwardDeclarationReifiedChecker.INSTANCE);

    private NativeExpressionCheckers() {
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.expression.ExpressionCheckers
    public Set<FirNativeForwardDeclarationTypeOperatorChecker> getTypeOperatorCallCheckers() {
        return typeOperatorCallCheckers;
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.expression.ExpressionCheckers
    public Set<FirNativeForwardDeclarationGetClassCallChecker> getGetClassCallCheckers() {
        return getClassCallCheckers;
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.expression.ExpressionCheckers
    public Set<FirNativeForwardDeclarationReifiedChecker> getQualifiedAccessExpressionCheckers() {
        return qualifiedAccessExpressionCheckers;
    }
}

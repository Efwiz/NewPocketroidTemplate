package org.jetbrains.kotlin.fir.resolve.transformers.body.resolve;

import kotlin.Metadata;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirFile;
import org.jetbrains.kotlin.fir.declarations.FirRegularClass;
import org.jetbrains.kotlin.fir.declarations.FirTowerDataContext;
import org.jetbrains.kotlin.fir.expressions.FirStatement;

/* JADX INFO: compiled from: FirResolveContextCollector.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\u0018\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H&J\u0018\u0010\b\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\t2\u0006\u0010\u0006\u001a\u00020\nH&J\u0018\u0010\u000b\u001a\u00020\u00032\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u0006\u001a\u00020\u0007H&J\u0018\u0010\u000e\u001a\u00020\u00032\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0006\u001a\u00020\nH&ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0011À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/transformers/body/resolve/FirResolveContextCollector;", "", "addClassHeaderContext", "", "declaration", "Lorg/jetbrains/kotlin/fir/declarations/FirRegularClass;", "context", "Lorg/jetbrains/kotlin/fir/declarations/FirTowerDataContext;", "addDeclarationContext", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;", "Lorg/jetbrains/kotlin/fir/resolve/transformers/body/resolve/BodyResolveContext;", "addFileContext", "file", "Lorg/jetbrains/kotlin/fir/declarations/FirFile;", "addStatementContext", "statement", "Lorg/jetbrains/kotlin/fir/expressions/FirStatement;", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface FirResolveContextCollector {
    void addClassHeaderContext(FirRegularClass declaration, FirTowerDataContext context);

    void addDeclarationContext(FirDeclaration declaration, BodyResolveContext context);

    void addFileContext(FirFile file, FirTowerDataContext context);

    void addStatementContext(FirStatement statement, BodyResolveContext context);
}

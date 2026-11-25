package org.jetbrains.kotlin.fir.analysis;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.FirSessionComponent;
import org.jetbrains.kotlin.fir.SessionConfiguration;
import org.jetbrains.kotlin.fir.analysis.checkers.LanguageVersionSettingsCheckers;
import org.jetbrains.kotlin.fir.analysis.checkers.config.ComposedLanguageVersionSettingsCheckers;
import org.jetbrains.kotlin.fir.analysis.checkers.declaration.ComposedDeclarationCheckers;
import org.jetbrains.kotlin.fir.analysis.checkers.declaration.DeclarationCheckers;
import org.jetbrains.kotlin.fir.analysis.checkers.expression.ComposedExpressionCheckers;
import org.jetbrains.kotlin.fir.analysis.checkers.expression.ExpressionCheckers;
import org.jetbrains.kotlin.fir.analysis.checkers.type.ComposedTypeCheckers;
import org.jetbrains.kotlin.fir.analysis.checkers.type.TypeCheckers;
import org.jetbrains.kotlin.fir.analysis.extensions.FirAdditionalCheckersExtension;

/* JADX INFO: compiled from: CheckersComponent.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u0014H\u0007J\u0010\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\fH\u0007J\u0010\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u0010H\u0007J\u0010\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u0018H\u0007J\u0010\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u001eH\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u000b\u001a\u00020\f8F¢\u0006\u0006\u001a\u0004\b\r\u0010\u000eR\u0011\u0010\u000f\u001a\u00020\u00108F¢\u0006\u0006\u001a\u0004\b\u0011\u0010\u0012R\u0011\u0010\u0013\u001a\u00020\u00148F¢\u0006\u0006\u001a\u0004\b\u0015\u0010\u0016R\u0011\u0010\u0017\u001a\u00020\u00188F¢\u0006\u0006\u001a\u0004\b\u0019\u0010\u001a¨\u0006\u001d"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/CheckersComponent;", "Lorg/jetbrains/kotlin/fir/FirSessionComponent;", "()V", "_declarationCheckers", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/ComposedDeclarationCheckers;", "_expressionCheckers", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/ComposedExpressionCheckers;", "_languageVersionSettingsCheckers", "Lorg/jetbrains/kotlin/fir/analysis/checkers/config/ComposedLanguageVersionSettingsCheckers;", "_typeCheckers", "Lorg/jetbrains/kotlin/fir/analysis/checkers/type/ComposedTypeCheckers;", "declarationCheckers", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/DeclarationCheckers;", "getDeclarationCheckers", "()Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/DeclarationCheckers;", "expressionCheckers", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/ExpressionCheckers;", "getExpressionCheckers", "()Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/ExpressionCheckers;", "languageVersionSettingsCheckers", "Lorg/jetbrains/kotlin/fir/analysis/checkers/LanguageVersionSettingsCheckers;", "getLanguageVersionSettingsCheckers", "()Lorg/jetbrains/kotlin/fir/analysis/checkers/LanguageVersionSettingsCheckers;", "typeCheckers", "Lorg/jetbrains/kotlin/fir/analysis/checkers/type/TypeCheckers;", "getTypeCheckers", "()Lorg/jetbrains/kotlin/fir/analysis/checkers/type/TypeCheckers;", "register", "", "checkers", "Lorg/jetbrains/kotlin/fir/analysis/extensions/FirAdditionalCheckersExtension;"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CheckersComponent implements FirSessionComponent {
    private final ComposedDeclarationCheckers _declarationCheckers = new ComposedDeclarationCheckers();
    private final ComposedExpressionCheckers _expressionCheckers = new ComposedExpressionCheckers();
    private final ComposedTypeCheckers _typeCheckers = new ComposedTypeCheckers();
    private final ComposedLanguageVersionSettingsCheckers _languageVersionSettingsCheckers = new ComposedLanguageVersionSettingsCheckers();

    public final DeclarationCheckers getDeclarationCheckers() {
        return this._declarationCheckers;
    }

    public final ExpressionCheckers getExpressionCheckers() {
        return this._expressionCheckers;
    }

    public final TypeCheckers getTypeCheckers() {
        return this._typeCheckers;
    }

    public final LanguageVersionSettingsCheckers getLanguageVersionSettingsCheckers() {
        return this._languageVersionSettingsCheckers;
    }

    @SessionConfiguration
    public final void register(DeclarationCheckers checkers) {
        Intrinsics.checkNotNullParameter(checkers, "checkers");
        this._declarationCheckers.register(checkers);
    }

    @SessionConfiguration
    public final void register(ExpressionCheckers checkers) {
        Intrinsics.checkNotNullParameter(checkers, "checkers");
        this._expressionCheckers.register(checkers);
    }

    @SessionConfiguration
    public final void register(TypeCheckers checkers) {
        Intrinsics.checkNotNullParameter(checkers, "checkers");
        this._typeCheckers.register(checkers);
    }

    @SessionConfiguration
    public final void register(LanguageVersionSettingsCheckers checkers) {
        Intrinsics.checkNotNullParameter(checkers, "checkers");
        this._languageVersionSettingsCheckers.register(checkers);
    }

    @SessionConfiguration
    public final void register(FirAdditionalCheckersExtension checkers) {
        Intrinsics.checkNotNullParameter(checkers, "checkers");
        register(checkers.getDeclarationCheckers());
        register(checkers.getExpressionCheckers());
        register(checkers.getTypeCheckers());
        register(checkers.getLanguageVersionSettingsCheckers());
    }
}

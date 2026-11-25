package org.jetbrains.kotlin.fir.analysis.checkers.config;

import java.util.LinkedHashSet;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.analysis.CheckersComponentInternal;
import org.jetbrains.kotlin.fir.analysis.checkers.LanguageVersionSettingsCheckers;

/* JADX INFO: compiled from: ComposedLanguageVersionSettingsCheckers.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010#\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u0001H\u0007R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00050\u00078VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\t¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/config/ComposedLanguageVersionSettingsCheckers;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/LanguageVersionSettingsCheckers;", "()V", "_languageVersionSettingsCheckers", "", "Lorg/jetbrains/kotlin/fir/analysis/checkers/config/FirLanguageVersionSettingsChecker;", "languageVersionSettingsCheckers", "", "getLanguageVersionSettingsCheckers", "()Ljava/util/Set;", "register", "", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ComposedLanguageVersionSettingsCheckers extends LanguageVersionSettingsCheckers {
    private final Set<FirLanguageVersionSettingsChecker> _languageVersionSettingsCheckers = new LinkedHashSet();

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.LanguageVersionSettingsCheckers
    public Set<FirLanguageVersionSettingsChecker> getLanguageVersionSettingsCheckers() {
        return this._languageVersionSettingsCheckers;
    }

    @CheckersComponentInternal
    public final void register(LanguageVersionSettingsCheckers checkers) {
        Intrinsics.checkNotNullParameter(checkers, "checkers");
        CollectionsKt.addAll(this._languageVersionSettingsCheckers, checkers.getLanguageVersionSettingsCheckers());
    }
}

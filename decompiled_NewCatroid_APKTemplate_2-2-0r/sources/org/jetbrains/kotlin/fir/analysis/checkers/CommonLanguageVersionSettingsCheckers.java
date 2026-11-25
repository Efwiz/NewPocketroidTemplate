package org.jetbrains.kotlin.fir.analysis.checkers;

import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.SetsKt;
import org.jetbrains.kotlin.fir.analysis.checkers.config.FirLanguageVersionSettingsChecker;

/* JADX INFO: compiled from: CommonLanguageVersionSettingsCheckers.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u001a\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007¨\u0006\b"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/CommonLanguageVersionSettingsCheckers;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/LanguageVersionSettingsCheckers;", "()V", "languageVersionSettingsCheckers", "", "Lorg/jetbrains/kotlin/fir/analysis/checkers/config/FirLanguageVersionSettingsChecker;", "getLanguageVersionSettingsCheckers", "()Ljava/util/Set;", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CommonLanguageVersionSettingsCheckers extends LanguageVersionSettingsCheckers {
    public static final CommonLanguageVersionSettingsCheckers INSTANCE = new CommonLanguageVersionSettingsCheckers();

    private CommonLanguageVersionSettingsCheckers() {
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.LanguageVersionSettingsCheckers
    public Set<FirLanguageVersionSettingsChecker> getLanguageVersionSettingsCheckers() {
        return SetsKt.emptySet();
    }
}

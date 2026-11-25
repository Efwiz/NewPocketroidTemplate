package org.jetbrains.kotlin.fir.analysis.checkers;

import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import org.jetbrains.kotlin.fir.analysis.checkers.config.FirLanguageVersionSettingsChecker;

/* JADX INFO: compiled from: LanguageVersionSettingsCheckers.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\u0004\b&\u0018\u0000 \b2\u00020\u0001:\u0001\bB\u0005¢\u0006\u0002\u0010\u0002R\u001a\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/LanguageVersionSettingsCheckers;", "", "()V", "languageVersionSettingsCheckers", "", "Lorg/jetbrains/kotlin/fir/analysis/checkers/config/FirLanguageVersionSettingsChecker;", "getLanguageVersionSettingsCheckers", "()Ljava/util/Set;", "Companion", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class LanguageVersionSettingsCheckers {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final LanguageVersionSettingsCheckers EMPTY = new LanguageVersionSettingsCheckers() { // from class: org.jetbrains.kotlin.fir.analysis.checkers.LanguageVersionSettingsCheckers$Companion$EMPTY$1
    };
    private final Set<FirLanguageVersionSettingsChecker> languageVersionSettingsCheckers = SetsKt.emptySet();

    /* JADX INFO: compiled from: LanguageVersionSettingsCheckers.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/LanguageVersionSettingsCheckers$Companion;", "", "()V", "EMPTY", "Lorg/jetbrains/kotlin/fir/analysis/checkers/LanguageVersionSettingsCheckers;", "getEMPTY", "()Lorg/jetbrains/kotlin/fir/analysis/checkers/LanguageVersionSettingsCheckers;", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final LanguageVersionSettingsCheckers getEMPTY() {
            return LanguageVersionSettingsCheckers.EMPTY;
        }
    }

    public Set<FirLanguageVersionSettingsChecker> getLanguageVersionSettingsCheckers() {
        return this.languageVersionSettingsCheckers;
    }
}

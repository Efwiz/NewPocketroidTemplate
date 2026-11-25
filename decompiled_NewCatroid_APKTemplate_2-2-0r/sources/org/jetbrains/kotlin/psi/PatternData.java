package org.jetbrains.kotlin.psi;

import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: createByPattern.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010$\n\u0002\u0010\b\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0082\b\u0018\u00002\u00020\u0001B'\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0018\u0010\u0004\u001a\u0014\u0012\u0004\u0012\u00020\u0006\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u0005¢\u0006\u0002\u0010\tJ\t\u0010\u000e\u001a\u00020\u0003HÆ\u0003J\u001b\u0010\u000f\u001a\u0014\u0012\u0004\u0012\u00020\u0006\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u0005HÆ\u0003J/\u0010\u0010\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\u001a\b\u0002\u0010\u0004\u001a\u0014\u0012\u0004\u0012\u00020\u0006\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u0005HÆ\u0001J\u0013\u0010\u0011\u001a\u00020\u00122\b\u0010\u0013\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0014\u001a\u00020\u0006HÖ\u0001J\t\u0010\u0015\u001a\u00020\u0003HÖ\u0001R#\u0010\u0004\u001a\u0014\u0012\u0004\u0012\u00020\u0006\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u0005¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\r¨\u0006\u0016"}, d2 = {"Lorg/jetbrains/kotlin/psi/PatternData;", "", "processedText", "", "placeholders", "", "", "", "Lorg/jetbrains/kotlin/psi/Placeholder;", "(Ljava/lang/String;Ljava/util/Map;)V", "getPlaceholders", "()Ljava/util/Map;", "getProcessedText", "()Ljava/lang/String;", "component1", "component2", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "toString", "psi"}, k = 1, mv = {1, 9, 0}, xi = 48)
final /* data */ class PatternData {
    private final Map<Integer, List<Placeholder>> placeholders;
    private final String processedText;

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ PatternData copy$default(PatternData patternData, String str, Map map, int i, Object obj) {
        if ((i & 1) != 0) {
            str = patternData.processedText;
        }
        if ((i & 2) != 0) {
            map = patternData.placeholders;
        }
        return patternData.copy(str, map);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final String getProcessedText() {
        return this.processedText;
    }

    public final Map<Integer, List<Placeholder>> component2() {
        return this.placeholders;
    }

    public final PatternData copy(String processedText, Map<Integer, ? extends List<Placeholder>> placeholders) {
        Intrinsics.checkNotNullParameter(processedText, "processedText");
        Intrinsics.checkNotNullParameter(placeholders, "placeholders");
        return new PatternData(processedText, placeholders);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof PatternData)) {
            return false;
        }
        PatternData patternData = (PatternData) other;
        return Intrinsics.areEqual(this.processedText, patternData.processedText) && Intrinsics.areEqual(this.placeholders, patternData.placeholders);
    }

    public int hashCode() {
        return (this.processedText.hashCode() * 31) + this.placeholders.hashCode();
    }

    public String toString() {
        return "PatternData(processedText=" + this.processedText + ", placeholders=" + this.placeholders + ')';
    }

    /* JADX WARN: Multi-variable type inference failed */
    public PatternData(String processedText, Map<Integer, ? extends List<Placeholder>> placeholders) {
        Intrinsics.checkNotNullParameter(processedText, "processedText");
        Intrinsics.checkNotNullParameter(placeholders, "placeholders");
        this.processedText = processedText;
        this.placeholders = placeholders;
    }

    public final Map<Integer, List<Placeholder>> getPlaceholders() {
        return this.placeholders;
    }

    public final String getProcessedText() {
        return this.processedText;
    }
}

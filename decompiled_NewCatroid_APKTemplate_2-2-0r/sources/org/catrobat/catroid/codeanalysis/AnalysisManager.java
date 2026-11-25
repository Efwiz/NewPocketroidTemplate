package org.catrobat.catroid.codeanalysis;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.catroid.content.bricks.Brick;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/codeanalysis/AnalysisManager.class
  classes12.dex
 */
/* JADX INFO: compiled from: AnalysisManager.kt */
/* JADX INFO: loaded from: classes12.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0007\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0006\u0010\u0007\u001a\u00020\bJ\u0006\u0010\t\u001a\u00020\nJ\u0010\u0010\u000b\u001a\u0004\u0018\u00010\u00062\u0006\u0010\f\u001a\u00020\u0005J\u0012\u0010\r\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u0004J\u0006\u0010\u000e\u001a\u00020\nJ\u001a\u0010\u000f\u001a\u00020\b2\u0012\u0010\u0010\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u0004R\u001a\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u0004X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0011"}, d2 = {"Lorg/catrobat/catroid/codeanalysis/AnalysisManager;", "", "()V", "analysisResults", "", "Lorg/catrobat/catroid/content/bricks/Brick;", "Lorg/catrobat/catroid/codeanalysis/AnalysisResult;", "clearResults", "", "getErrorCount", "", "getResultFor", "brick", "getResults", "getWarningCount", "updateResults", "results", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class AnalysisManager {
    public static final AnalysisManager INSTANCE = new AnalysisManager();
    private static Map<Brick, AnalysisResult> analysisResults = MapsKt.emptyMap();

    private AnalysisManager() {
    }

    public final void updateResults(Map<Brick, AnalysisResult> results) {
        Intrinsics.checkNotNullParameter(results, "results");
        analysisResults = results;
    }

    public final AnalysisResult getResultFor(Brick brick) {
        Intrinsics.checkNotNullParameter(brick, "brick");
        return analysisResults.get(brick);
    }

    public final Map<Brick, AnalysisResult> getResults() {
        return analysisResults;
    }

    public final int getErrorCount() {
        Collection<AnalysisResult> collectionValues = analysisResults.values();
        int i = 0;
        if (!(collectionValues instanceof Collection) || !collectionValues.isEmpty()) {
            Iterator<T> it = collectionValues.iterator();
            while (it.hasNext()) {
                if (((AnalysisResult) it.next()).getSeverity() == Severity.ERROR && (i = i + 1) < 0) {
                    CollectionsKt.throwCountOverflow();
                }
            }
        }
        return i;
    }

    public final int getWarningCount() {
        Collection<AnalysisResult> collectionValues = analysisResults.values();
        int i = 0;
        if (!(collectionValues instanceof Collection) || !collectionValues.isEmpty()) {
            Iterator<T> it = collectionValues.iterator();
            while (it.hasNext()) {
                if (((AnalysisResult) it.next()).getSeverity() == Severity.WARNING && (i = i + 1) < 0) {
                    CollectionsKt.throwCountOverflow();
                }
            }
        }
        return i;
    }

    public final void clearResults() {
        analysisResults = MapsKt.emptyMap();
    }
}

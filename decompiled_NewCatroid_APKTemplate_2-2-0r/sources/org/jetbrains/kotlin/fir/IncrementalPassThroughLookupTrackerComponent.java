package org.jetbrains.kotlin.fir;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.KtPsiSourceElement;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.diagnostics.DiagnosticUtils;
import org.jetbrains.kotlin.diagnostics.PsiDiagnosticUtils;
import org.jetbrains.kotlin.incremental.components.LookupTracker;
import org.jetbrains.kotlin.incremental.components.Position;
import org.jetbrains.kotlin.incremental.components.ScopeKind;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.utils.SmartList;

/* JADX INFO: compiled from: IncrementalPassThroughLookupTrackerComponent.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010 \n\u0000\u0018\u00002\u00020\u0001B#\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0014\u0010\u0004\u001a\u0010\u0012\u0004\u0012\u00020\u0006\u0012\u0006\u0012\u0004\u0018\u00010\u00070\u0005¢\u0006\u0002\u0010\bJ,\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00072\b\u0010\u0012\u001a\u0004\u0018\u00010\u00062\b\u0010\u0013\u001a\u0004\u0018\u00010\u0006H\u0016J2\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00070\u00152\b\u0010\u0012\u001a\u0004\u0018\u00010\u00062\b\u0010\u0013\u001a\u0004\u0018\u00010\u0006H\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u001c\u0010\u0004\u001a\u0010\u0012\u0004\u0012\u00020\u0006\u0012\u0006\u0012\u0004\u0018\u00010\u00070\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00070\fX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0016"}, d2 = {"Lorg/jetbrains/kotlin/fir/IncrementalPassThroughLookupTrackerComponent;", "Lorg/jetbrains/kotlin/fir/FirLookupTrackerComponent;", "lookupTracker", "Lorg/jetbrains/kotlin/incremental/components/LookupTracker;", "sourceToFilePath", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/KtSourceElement;", "", "(Lorg/jetbrains/kotlin/incremental/components/LookupTracker;Lkotlin/jvm/functions/Function1;)V", "requiresPosition", "", "sourceToFilePathsCache", "Ljava/util/concurrent/ConcurrentHashMap;", "recordLookup", "", "name", "Lorg/jetbrains/kotlin/name/Name;", "inScope", FirebaseAnalytics.Param.SOURCE, "fileSource", "inScopes", "", "entrypoint"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class IncrementalPassThroughLookupTrackerComponent extends FirLookupTrackerComponent {
    private final LookupTracker lookupTracker;
    private final boolean requiresPosition;
    private final Function1<KtSourceElement, String> sourceToFilePath;
    private final ConcurrentHashMap<KtSourceElement, String> sourceToFilePathsCache;

    /* JADX WARN: Multi-variable type inference failed */
    public IncrementalPassThroughLookupTrackerComponent(LookupTracker lookupTracker, Function1<? super KtSourceElement, String> sourceToFilePath) {
        Intrinsics.checkNotNullParameter(lookupTracker, "lookupTracker");
        Intrinsics.checkNotNullParameter(sourceToFilePath, "sourceToFilePath");
        this.lookupTracker = lookupTracker;
        this.sourceToFilePath = sourceToFilePath;
        this.requiresPosition = lookupTracker.getRequiresPosition();
        this.sourceToFilePathsCache = new ConcurrentHashMap<>();
    }

    @Override // org.jetbrains.kotlin.fir.FirLookupTrackerComponent
    public void recordLookup(Name name2, List<String> inScopes, KtSourceElement source, KtSourceElement fileSource) {
        Position no_position;
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(inScopes, "inScopes");
        if (fileSource == null) {
            KtPsiSourceElement ktPsiSourceElement = source instanceof KtPsiSourceElement ? (KtPsiSourceElement) source : null;
            if (ktPsiSourceElement == null) {
                return;
            } else {
                fileSource = ktPsiSourceElement;
            }
        }
        ConcurrentHashMap<KtSourceElement, String> concurrentHashMap = this.sourceToFilePathsCache;
        String strInvoke = concurrentHashMap.get(fileSource);
        if (strInvoke == null) {
            strInvoke = this.sourceToFilePath.invoke(fileSource);
            if (strInvoke == null) {
                return;
            }
            String strPutIfAbsent = concurrentHashMap.putIfAbsent(fileSource, strInvoke);
            if (strPutIfAbsent != null) {
                strInvoke = strPutIfAbsent;
            }
        }
        String str = strInvoke;
        if (this.requiresPosition && source != null && (source instanceof KtPsiSourceElement)) {
            KtPsiSourceElement ktPsiSourceElement2 = (KtPsiSourceElement) source;
            PsiDiagnosticUtils.LineAndColumn lineAndColumnInPsiFile = DiagnosticUtils.getLineAndColumnInPsiFile(ktPsiSourceElement2.getPsi().getContainingFile(), ktPsiSourceElement2.getPsi().getTextRange());
            no_position = new Position(lineAndColumnInPsiFile.getLine(), lineAndColumnInPsiFile.getColumn());
        } else {
            no_position = Position.INSTANCE.getNO_POSITION();
        }
        for (String str2 : inScopes) {
            LookupTracker lookupTracker = this.lookupTracker;
            Intrinsics.checkNotNull(str);
            ScopeKind scopeKind = ScopeKind.PACKAGE;
            String strAsString = name2.asString();
            Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
            lookupTracker.record(str, no_position, str2, scopeKind, strAsString);
        }
    }

    @Override // org.jetbrains.kotlin.fir.FirLookupTrackerComponent
    public void recordLookup(Name name2, String inScope, KtSourceElement source, KtSourceElement fileSource) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(inScope, "inScope");
        recordLookup(name2, new SmartList(inScope), source, fileSource);
    }
}

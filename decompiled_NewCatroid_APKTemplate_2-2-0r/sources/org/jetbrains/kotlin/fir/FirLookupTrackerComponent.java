package org.jetbrains.kotlin.fir;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.List;
import kotlin.Metadata;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: FirLookupTrackerComponent.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0000\b&\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J,\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\b\u0010\t\u001a\u0004\u0018\u00010\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\nH&J2\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\b0\r2\b\u0010\t\u001a\u0004\u0018\u00010\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\nH&¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/fir/FirLookupTrackerComponent;", "Lorg/jetbrains/kotlin/fir/FirSessionComponent;", "()V", "recordLookup", "", "name", "Lorg/jetbrains/kotlin/name/Name;", "inScope", "", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/KtSourceElement;", "fileSource", "inScopes", "", "semantics"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class FirLookupTrackerComponent implements FirSessionComponent {
    public abstract void recordLookup(Name name2, String inScope, KtSourceElement source, KtSourceElement fileSource);

    public abstract void recordLookup(Name name2, List<String> inScopes, KtSourceElement source, KtSourceElement fileSource);
}

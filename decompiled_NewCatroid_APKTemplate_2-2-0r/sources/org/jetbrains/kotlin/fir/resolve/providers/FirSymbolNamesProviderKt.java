package org.jetbrains.kotlin.fir.resolve.providers;

import java.util.Set;
import kotlin.Metadata;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: FirSymbolNamesProvider.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0016\n\u0000\n\u0002\u0010\u000b\n\u0002\u0010\"\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u001a\u0010\u0000\u001a\u00020\u0001*\b\u0012\u0004\u0012\u00020\u00030\u00022\u0006\u0010\u0004\u001a\u00020\u0005H\u0002¨\u0006\u0006"}, d2 = {"mayContainTopLevelClassifier", "", "", "", "shortClassName", "Lorg/jetbrains/kotlin/name/Name;", "providers"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class FirSymbolNamesProviderKt {
    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean mayContainTopLevelClassifier(Set<String> set, Name name2) {
        return name2.isSpecial() || set.contains(name2.asString());
    }
}

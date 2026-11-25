package org.jetbrains.kotlin.resolve;

import kotlin.Metadata;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;

/* JADX INFO: compiled from: TopDownAnalysisMode.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0005\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u000f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0002\u0010\u0005j\u0002\b\u0006j\u0002\b\u0007¨\u0006\b"}, d2 = {"Lorg/jetbrains/kotlin/resolve/TopDownAnalysisMode;", "", "isLocalDeclarations", "", "(Ljava/lang/String;IZ)V", "()Z", "LocalDeclarations", "TopLevelDeclarations", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public enum TopDownAnalysisMode {
    LocalDeclarations(true),
    TopLevelDeclarations(false);

    private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());
    private final boolean isLocalDeclarations;

    public static EnumEntries<TopDownAnalysisMode> getEntries() {
        return $ENTRIES;
    }

    TopDownAnalysisMode(boolean z) {
        this.isLocalDeclarations = z;
    }

    /* JADX INFO: renamed from: isLocalDeclarations, reason: from getter */
    public final boolean getIsLocalDeclarations() {
        return this.isLocalDeclarations;
    }
}

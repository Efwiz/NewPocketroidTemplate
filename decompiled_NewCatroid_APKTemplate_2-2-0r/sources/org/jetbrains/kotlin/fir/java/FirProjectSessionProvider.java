package org.jetbrains.kotlin.fir.java;

import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jdt.internal.compiler.util.SuffixConstants;
import org.jetbrains.kotlin.fir.FirModuleData;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.FirSessionProvider;

/* JADX INFO: compiled from: FirCliSession.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0012\u0010\u0007\u001a\u0004\u0018\u00010\u00062\u0006\u0010\b\u001a\u00020\u0005H\u0016J\u0016\u0010\t\u001a\u00020\n2\u0006\u0010\b\u001a\u00020\u00052\u0006\u0010\u000b\u001a\u00020\u0006R\u001a\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/fir/java/FirProjectSessionProvider;", "Lorg/jetbrains/kotlin/fir/FirSessionProvider;", "()V", "sessionCache", "", "Lorg/jetbrains/kotlin/fir/FirModuleData;", "Lorg/jetbrains/kotlin/fir/FirSession;", "getSession", "moduleData", "registerSession", "", "session", SuffixConstants.EXTENSION_java}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirProjectSessionProvider extends FirSessionProvider {
    private final Map<FirModuleData, FirSession> sessionCache = new LinkedHashMap();

    @Override // org.jetbrains.kotlin.fir.FirSessionProvider
    public FirSession getSession(FirModuleData moduleData) {
        Intrinsics.checkNotNullParameter(moduleData, "moduleData");
        return this.sessionCache.get(moduleData);
    }

    public final void registerSession(FirModuleData moduleData, FirSession session) {
        Intrinsics.checkNotNullParameter(moduleData, "moduleData");
        Intrinsics.checkNotNullParameter(session, "session");
        this.sessionCache.put(moduleData, session);
    }
}

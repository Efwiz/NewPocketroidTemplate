package org.jetbrains.kotlin.fir;

import kotlin.Metadata;
import org.eclipse.jgit.lib.Constants;

/* JADX INFO: compiled from: FirSession.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b&\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0012\u0010\u0003\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\u0006H&¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/fir/FirSessionProvider;", "", "()V", "getSession", "Lorg/jetbrains/kotlin/fir/FirSession;", "moduleData", "Lorg/jetbrains/kotlin/fir/FirModuleData;", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class FirSessionProvider {
    public abstract FirSession getSession(FirModuleData moduleData);
}

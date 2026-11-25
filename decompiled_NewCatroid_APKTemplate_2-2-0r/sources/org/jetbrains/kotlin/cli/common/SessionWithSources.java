package org.jetbrains.kotlin.cli.common;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: FirSessionConstructionUtils.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u0000*\u0004\b\u0000\u0010\u00012\u00020\u0002B\u001b\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00028\u00000\u0006¢\u0006\u0002\u0010\u0007J\t\u0010\f\u001a\u00020\u0004HÆ\u0003J\u000f\u0010\r\u001a\b\u0012\u0004\u0012\u00028\u00000\u0006HÆ\u0003J)\u0010\u000e\u001a\b\u0012\u0004\u0012\u00028\u00000\u00002\b\b\u0002\u0010\u0003\u001a\u00020\u00042\u000e\b\u0002\u0010\u0005\u001a\b\u0012\u0004\u0012\u00028\u00000\u0006HÆ\u0001J\u0013\u0010\u000f\u001a\u00020\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u0002HÖ\u0003J\t\u0010\u0012\u001a\u00020\u0013HÖ\u0001J\t\u0010\u0014\u001a\u00020\u0015HÖ\u0001R\u0017\u0010\u0005\u001a\b\u0012\u0004\u0012\u00028\u00000\u0006¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b¨\u0006\u0016"}, d2 = {"Lorg/jetbrains/kotlin/cli/common/SessionWithSources;", "F", "", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "files", "", "(Lorg/jetbrains/kotlin/fir/FirSession;Ljava/util/List;)V", "getFiles", "()Ljava/util/List;", "getSession", "()Lorg/jetbrains/kotlin/fir/FirSession;", "component1", "component2", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "", "cli"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class SessionWithSources<F> {
    private final List<F> files;
    private final FirSession session;

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ SessionWithSources copy$default(SessionWithSources sessionWithSources, FirSession firSession, List list, int i, Object obj) {
        if ((i & 1) != 0) {
            firSession = sessionWithSources.session;
        }
        if ((i & 2) != 0) {
            list = sessionWithSources.files;
        }
        return sessionWithSources.copy(firSession, list);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final FirSession getSession() {
        return this.session;
    }

    public final List<F> component2() {
        return this.files;
    }

    public final SessionWithSources<F> copy(FirSession session, List<? extends F> files) {
        Intrinsics.checkNotNullParameter(session, "session");
        Intrinsics.checkNotNullParameter(files, "files");
        return new SessionWithSources<>(session, files);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof SessionWithSources)) {
            return false;
        }
        SessionWithSources sessionWithSources = (SessionWithSources) other;
        return Intrinsics.areEqual(this.session, sessionWithSources.session) && Intrinsics.areEqual(this.files, sessionWithSources.files);
    }

    public int hashCode() {
        return (this.session.hashCode() * 31) + this.files.hashCode();
    }

    public String toString() {
        return "SessionWithSources(session=" + this.session + ", files=" + this.files + ')';
    }

    /* JADX WARN: Multi-variable type inference failed */
    public SessionWithSources(FirSession session, List<? extends F> files) {
        Intrinsics.checkNotNullParameter(session, "session");
        Intrinsics.checkNotNullParameter(files, "files");
        this.session = session;
        this.files = files;
    }

    public final List<F> getFiles() {
        return this.files;
    }

    public final FirSession getSession() {
        return this.session;
    }
}

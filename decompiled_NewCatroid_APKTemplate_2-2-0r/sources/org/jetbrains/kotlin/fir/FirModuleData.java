package org.jetbrains.kotlin.fir;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.konan.library.NativeLibraryConstantsKt;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.platform.TargetPlatform;
import org.jetbrains.kotlin.resolve.PlatformDependentAnalyzerServices;

/* JADX INFO: compiled from: FirModuleData.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\b&\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u000e\u0010 \u001a\u00020!2\u0006\u0010\u001d\u001a\u00020\u0004J\b\u0010\"\u001a\u00020#H\u0016R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u0012\u0010\u0005\u001a\u00020\u0006X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u0014\u0010\t\u001a\u00020\n8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\fR\u0018\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00000\u000eX¦\u0004¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010R\u0018\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00000\u000eX¦\u0004¢\u0006\u0006\u001a\u0004\b\u0012\u0010\u0010R\u0018\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00000\u000eX¦\u0004¢\u0006\u0006\u001a\u0004\b\u0014\u0010\u0010R\u0012\u0010\u0015\u001a\u00020\u0016X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0017\u0010\u0018R\u0012\u0010\u0019\u001a\u00020\u001aX¦\u0004¢\u0006\u0006\u001a\u0004\b\u001b\u0010\u001cR\u0011\u0010\u001d\u001a\u00020\u00048F¢\u0006\u0006\u001a\u0004\b\u001e\u0010\u001f¨\u0006$"}, d2 = {"Lorg/jetbrains/kotlin/fir/FirModuleData;", "Lorg/jetbrains/kotlin/fir/FirSessionComponent;", "()V", "_session", "Lorg/jetbrains/kotlin/fir/FirSession;", "analyzerServices", "Lorg/jetbrains/kotlin/resolve/PlatformDependentAnalyzerServices;", "getAnalyzerServices", "()Lorg/jetbrains/kotlin/resolve/PlatformDependentAnalyzerServices;", "capabilities", "Lorg/jetbrains/kotlin/fir/FirModuleCapabilities;", "getCapabilities", "()Lorg/jetbrains/kotlin/fir/FirModuleCapabilities;", "dependencies", "", "getDependencies", "()Ljava/util/List;", "dependsOnDependencies", "getDependsOnDependencies", "friendDependencies", "getFriendDependencies", "name", "Lorg/jetbrains/kotlin/name/Name;", "getName", "()Lorg/jetbrains/kotlin/name/Name;", NativeLibraryConstantsKt.KONAN_DISTRIBUTION_PLATFORM_LIBS_DIR, "Lorg/jetbrains/kotlin/platform/TargetPlatform;", "getPlatform", "()Lorg/jetbrains/kotlin/platform/TargetPlatform;", "session", "getSession", "()Lorg/jetbrains/kotlin/fir/FirSession;", "bindSession", "", "toString", "", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class FirModuleData implements FirSessionComponent {
    private FirSession _session;

    public abstract PlatformDependentAnalyzerServices getAnalyzerServices();

    public abstract List<FirModuleData> getDependencies();

    public abstract List<FirModuleData> getDependsOnDependencies();

    public abstract List<FirModuleData> getFriendDependencies();

    public abstract Name getName();

    public abstract TargetPlatform getPlatform();

    public FirModuleCapabilities getCapabilities() {
        return FirModuleCapabilities.INSTANCE.getEmpty();
    }

    public final FirSession getSession() {
        FirSession firSession = this._session;
        if (firSession != null) {
            return firSession;
        }
        throw new IllegalStateException(("module data " + Reflection.getOrCreateKotlinClass(getClass()).getSimpleName() + ':' + getName() + " not bound to session").toString());
    }

    public final void bindSession(FirSession session) {
        Intrinsics.checkNotNullParameter(session, "session");
        if (this._session != null) {
            throw new IllegalStateException(("module data already bound to " + this).toString());
        }
        this._session = session;
    }

    public String toString() {
        return "Module " + getName();
    }
}

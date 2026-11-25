package org.jetbrains.kotlin.fir;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration;

/* JADX INFO: compiled from: FirVisibilityChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b&\u0018\u00002\u00020\u0001:\u0001\u0007B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H&¨\u0006\b"}, d2 = {"Lorg/jetbrains/kotlin/fir/FirModuleVisibilityChecker;", "Lorg/jetbrains/kotlin/fir/FirSessionComponent;", "()V", "isInFriendModule", "", "declaration", "Lorg/jetbrains/kotlin/fir/declarations/FirMemberDeclaration;", "Standard", "providers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class FirModuleVisibilityChecker implements FirSessionComponent {
    public abstract boolean isInFriendModule(FirMemberDeclaration declaration);

    /* JADX INFO: compiled from: FirVisibilityChecker.kt */
    @Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0016R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u000e\u0010\n\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/fir/FirModuleVisibilityChecker$Standard;", "Lorg/jetbrains/kotlin/fir/FirModuleVisibilityChecker;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "(Lorg/jetbrains/kotlin/fir/FirSession;)V", "allDependsOnDependencies", "", "Lorg/jetbrains/kotlin/fir/FirModuleData;", "getSession", "()Lorg/jetbrains/kotlin/fir/FirSession;", "useSiteModuleData", "isInFriendModule", "", "declaration", "Lorg/jetbrains/kotlin/fir/declarations/FirMemberDeclaration;", "providers"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Standard extends FirModuleVisibilityChecker {
        private final List<FirModuleData> allDependsOnDependencies;
        private final FirSession session;
        private final FirModuleData useSiteModuleData;

        public Standard(FirSession session) {
            Intrinsics.checkNotNullParameter(session, "session");
            this.session = session;
            FirModuleData moduleData = FirModuleDataKt.getModuleData(session);
            this.useSiteModuleData = moduleData;
            this.allDependsOnDependencies = FirVisibilityCheckerKt.getAllDependsOnDependencies(moduleData);
        }

        public final FirSession getSession() {
            return this.session;
        }

        @Override // org.jetbrains.kotlin.fir.FirModuleVisibilityChecker
        public boolean isInFriendModule(FirMemberDeclaration declaration) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            FirModuleData moduleData = declaration.getModuleData();
            if (Intrinsics.areEqual(moduleData, this.useSiteModuleData) ? true : this.useSiteModuleData.getFriendDependencies().contains(moduleData)) {
                return true;
            }
            return this.allDependsOnDependencies.contains(moduleData);
        }
    }
}

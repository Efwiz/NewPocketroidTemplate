package org.jetbrains.kotlin.analyzer;

import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.openapi.util.ModificationTracker;
import org.jetbrains.kotlin.psi.KtFile;

/* JADX INFO: compiled from: KotlinModificationTrackerService.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0016\u0018\u0000 \f2\u00020\u0001:\u0001\fB\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\t\u001a\u00020\u00042\u0006\u0010\n\u001a\u00020\u000bH\u0016R\u0014\u0010\u0003\u001a\u00020\u0004X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0014\u0010\u0007\u001a\u00020\u0004X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0006¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/analyzer/KotlinModificationTrackerService;", "", "()V", "modificationTracker", "Lorg/jetbrains/kotlin/com/intellij/openapi/util/ModificationTracker;", "getModificationTracker", "()Lcom/intellij/openapi/util/ModificationTracker;", "outOfBlockModificationTracker", "getOutOfBlockModificationTracker", "fileModificationTracker", "file", "Lorg/jetbrains/kotlin/psi/KtFile;", "Companion", "frontend.common-psi"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class KotlinModificationTrackerService {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final KotlinModificationTrackerService NEVER_CHANGE_TRACKER_SERVICE = new KotlinModificationTrackerService();
    private final ModificationTracker modificationTracker;
    private final ModificationTracker outOfBlockModificationTracker;

    @JvmStatic
    public static final KotlinModificationTrackerService getInstance(Project project) {
        return INSTANCE.getInstance(project);
    }

    public KotlinModificationTrackerService() {
        ModificationTracker NEVER_CHANGED = ModificationTracker.NEVER_CHANGED;
        Intrinsics.checkNotNullExpressionValue(NEVER_CHANGED, "NEVER_CHANGED");
        this.modificationTracker = NEVER_CHANGED;
        ModificationTracker NEVER_CHANGED2 = ModificationTracker.NEVER_CHANGED;
        Intrinsics.checkNotNullExpressionValue(NEVER_CHANGED2, "NEVER_CHANGED");
        this.outOfBlockModificationTracker = NEVER_CHANGED2;
    }

    public ModificationTracker getModificationTracker() {
        return this.modificationTracker;
    }

    public ModificationTracker getOutOfBlockModificationTracker() {
        return this.outOfBlockModificationTracker;
    }

    public ModificationTracker fileModificationTracker(KtFile file) {
        Intrinsics.checkNotNullParameter(file, "file");
        ModificationTracker NEVER_CHANGED = ModificationTracker.NEVER_CHANGED;
        Intrinsics.checkNotNullExpressionValue(NEVER_CHANGED, "NEVER_CHANGED");
        return NEVER_CHANGED;
    }

    /* JADX INFO: compiled from: KotlinModificationTrackerService.kt */
    @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0006\u001a\u00020\u0007H\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\b"}, d2 = {"Lorg/jetbrains/kotlin/analyzer/KotlinModificationTrackerService$Companion;", "", "()V", "NEVER_CHANGE_TRACKER_SERVICE", "Lorg/jetbrains/kotlin/analyzer/KotlinModificationTrackerService;", "getInstance", "project", "Lorg/jetbrains/kotlin/com/intellij/openapi/project/Project;", "frontend.common-psi"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        @JvmStatic
        public final KotlinModificationTrackerService getInstance(Project project) {
            Intrinsics.checkNotNullParameter(project, "project");
            KotlinModificationTrackerService kotlinModificationTrackerService = (KotlinModificationTrackerService) project.getService(KotlinModificationTrackerService.class);
            return kotlinModificationTrackerService == null ? KotlinModificationTrackerService.NEVER_CHANGE_TRACKER_SERVICE : kotlinModificationTrackerService;
        }
    }
}

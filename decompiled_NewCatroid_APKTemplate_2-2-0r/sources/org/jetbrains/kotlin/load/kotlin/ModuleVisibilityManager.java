package org.jetbrains.kotlin.load.kotlin;

import java.util.Collection;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.modules.Module;

/* JADX INFO: compiled from: moduleVisibilityUtils.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0005\bf\u0018\u00002\u00020\u0001:\u0001\u0013J\u0010\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\fH&J\u0010\u0010\u0011\u001a\u00020\u000f2\u0006\u0010\u0012\u001a\u00020\u0004H&R\u0018\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006R\u0014\u0010\u0007\u001a\u00020\b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\nR\u0018\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\f0\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\u0006ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0014À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/load/kotlin/ModuleVisibilityManager;", "", "chunk", "", "Lorg/jetbrains/kotlin/modules/Module;", "getChunk", "()Ljava/util/Collection;", CompilerOptions.ENABLED, "", "getEnabled", "()Z", "friendPaths", "", "getFriendPaths", "addFriendPath", "", "path", "addModule", "module", "SERVICE", "frontend.java"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface ModuleVisibilityManager {
    void addFriendPath(String path);

    void addModule(Module module);

    Collection<Module> getChunk();

    default boolean getEnabled() {
        return true;
    }

    Collection<String> getFriendPaths();

    /* JADX INFO: compiled from: moduleVisibilityUtils.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0007¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/load/kotlin/ModuleVisibilityManager$SERVICE;", "", "()V", "getInstance", "Lorg/jetbrains/kotlin/load/kotlin/ModuleVisibilityManager;", "project", "Lorg/jetbrains/kotlin/com/intellij/openapi/project/Project;", "frontend.java"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class SERVICE {
        public static final SERVICE INSTANCE = new SERVICE();

        private SERVICE() {
        }

        @JvmStatic
        public static final ModuleVisibilityManager getInstance(Project project) {
            Intrinsics.checkNotNullParameter(project, "project");
            Object service = project.getService(ModuleVisibilityManager.class);
            Intrinsics.checkNotNullExpressionValue(service, "getService(...)");
            return (ModuleVisibilityManager) service;
        }
    }
}

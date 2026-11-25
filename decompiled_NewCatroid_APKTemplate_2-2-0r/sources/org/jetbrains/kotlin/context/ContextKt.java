package org.jetbrains.kotlin.context;

import java.util.Map;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.com.intellij.openapi.progress.ProcessCanceledException;
import org.jetbrains.kotlin.com.intellij.openapi.progress.ProgressManager;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.descriptors.ModuleCapability;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.descriptors.impl.ModuleDescriptorImpl;
import org.jetbrains.kotlin.konan.library.NativeLibraryConstantsKt;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.platform.TargetPlatform;
import org.jetbrains.kotlin.storage.ExceptionTracker;
import org.jetbrains.kotlin.storage.LockBasedStorageManager;

/* JADX INFO: compiled from: context.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000T\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\u001aD\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\b\u0010\b\u001a\u0004\u0018\u00010\t2\u001a\b\u0002\u0010\n\u001a\u0014\u0012\b\u0012\u0006\u0012\u0002\b\u00030\f\u0012\u0006\u0012\u0004\u0018\u00010\r0\u000b\u001a\u000e\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011\u001a\u001e\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0010\u001a\u00020\u0011\u001a\u0016\u0010\u0018\u001a\u00020\u00032\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0010\u001a\u00020\u0011\u001a\u0012\u0010\u0019\u001a\u00020\u0013*\u00020\u00032\u0006\u0010\u0014\u001a\u00020\u0015\u001a\u0012\u0010\u001a\u001a\u00020\u0003*\u00020\u001b2\u0006\u0010\u0016\u001a\u00020\u0017¨\u0006\u001c"}, d2 = {"ContextForNewModule", "Lorg/jetbrains/kotlin/context/MutableModuleContext;", "projectContext", "Lorg/jetbrains/kotlin/context/ProjectContext;", "moduleName", "Lorg/jetbrains/kotlin/name/Name;", "builtIns", "Lorg/jetbrains/kotlin/builtins/KotlinBuiltIns;", NativeLibraryConstantsKt.KONAN_DISTRIBUTION_PLATFORM_LIBS_DIR, "Lorg/jetbrains/kotlin/platform/TargetPlatform;", "capabilities", "", "Lorg/jetbrains/kotlin/descriptors/ModuleCapability;", "", "GlobalContext", "Lorg/jetbrains/kotlin/context/GlobalContextImpl;", "debugName", "", "ModuleContext", "Lorg/jetbrains/kotlin/context/ModuleContext;", "module", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "project", "Lorg/jetbrains/kotlin/com/intellij/openapi/project/Project;", "ProjectContext", "withModule", "withProject", "Lorg/jetbrains/kotlin/context/GlobalContext;", "frontend"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class ContextKt {
    public static final GlobalContextImpl GlobalContext(String debugName) {
        Intrinsics.checkNotNullParameter(debugName, "debugName");
        ExceptionTracker exceptionTracker = new ExceptionTracker();
        LockBasedStorageManager lockBasedStorageManagerCreateWithExceptionHandling = LockBasedStorageManager.createWithExceptionHandling(debugName, exceptionTracker, new Runnable() { // from class: org.jetbrains.kotlin.context.ContextKt$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                ProgressManager.checkCanceled();
            }
        }, new Function1<InterruptedException, Unit>() { // from class: org.jetbrains.kotlin.context.ContextKt.GlobalContext.2
            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(InterruptedException interruptedException) {
                invoke2(interruptedException);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(InterruptedException interruptedException) {
                throw new ProcessCanceledException(interruptedException);
            }
        });
        Intrinsics.checkNotNullExpressionValue(lockBasedStorageManagerCreateWithExceptionHandling, "createWithExceptionHandling(...)");
        return new GlobalContextImpl(lockBasedStorageManagerCreateWithExceptionHandling, exceptionTracker);
    }

    public static final ProjectContext ProjectContext(Project project, String debugName) {
        Intrinsics.checkNotNullParameter(project, "project");
        Intrinsics.checkNotNullParameter(debugName, "debugName");
        return new ProjectContextImpl(project, GlobalContext(debugName));
    }

    public static final ModuleContext ModuleContext(ModuleDescriptor module, Project project, String debugName) {
        Intrinsics.checkNotNullParameter(module, "module");
        Intrinsics.checkNotNullParameter(project, "project");
        Intrinsics.checkNotNullParameter(debugName, "debugName");
        return new ModuleContextImpl(module, ProjectContext(project, debugName));
    }

    public static final ProjectContext withProject(GlobalContext globalContext, Project project) {
        Intrinsics.checkNotNullParameter(globalContext, "<this>");
        Intrinsics.checkNotNullParameter(project, "project");
        return new ProjectContextImpl(project, globalContext);
    }

    public static final ModuleContext withModule(ProjectContext projectContext, ModuleDescriptor module) {
        Intrinsics.checkNotNullParameter(projectContext, "<this>");
        Intrinsics.checkNotNullParameter(module, "module");
        return new ModuleContextImpl(module, projectContext);
    }

    public static /* synthetic */ MutableModuleContext ContextForNewModule$default(ProjectContext projectContext, Name name2, KotlinBuiltIns kotlinBuiltIns, TargetPlatform targetPlatform, Map map, int i, Object obj) {
        if ((i & 16) != 0) {
            map = MapsKt.emptyMap();
        }
        return ContextForNewModule(projectContext, name2, kotlinBuiltIns, targetPlatform, map);
    }

    public static final MutableModuleContext ContextForNewModule(ProjectContext projectContext, Name moduleName, KotlinBuiltIns builtIns, TargetPlatform targetPlatform, Map<ModuleCapability<?>, ? extends Object> capabilities) {
        Intrinsics.checkNotNullParameter(projectContext, "projectContext");
        Intrinsics.checkNotNullParameter(moduleName, "moduleName");
        Intrinsics.checkNotNullParameter(builtIns, "builtIns");
        Intrinsics.checkNotNullParameter(capabilities, "capabilities");
        return new MutableModuleContextImpl(new ModuleDescriptorImpl(moduleName, projectContext.getStorageManager(), builtIns, targetPlatform, capabilities, null, 32, null), projectContext);
    }
}

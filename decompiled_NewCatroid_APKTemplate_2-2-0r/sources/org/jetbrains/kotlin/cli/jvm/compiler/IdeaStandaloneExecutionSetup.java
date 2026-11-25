package org.jetbrains.kotlin.cli.jvm.compiler;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.openapi.application.Application;
import org.jetbrains.kotlin.com.intellij.openapi.application.ApplicationManager;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;

/* JADX INFO: compiled from: compat.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\b\u0010\u0007\u001a\u00020\bH\u0002J\u0006\u0010\t\u001a\u00020\bR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/cli/jvm/compiler/IdeaStandaloneExecutionSetup;", "", "()V", "FALLBACK_IDEA_BUILD_NUMBER", "", "LOG", "Lorg/jetbrains/kotlin/com/intellij/openapi/diagnostic/Logger;", "checkInHeadlessMode", "", "doSetup", "cli-base"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class IdeaStandaloneExecutionSetup {
    public static final IdeaStandaloneExecutionSetup INSTANCE = new IdeaStandaloneExecutionSetup();
    private static final Logger LOG;

    private IdeaStandaloneExecutionSetup() {
    }

    static {
        Logger logger = Logger.getInstance((Class<?>) IdeaStandaloneExecutionSetup.class);
        Intrinsics.checkNotNullExpressionValue(logger, "getInstance(...)");
        LOG = logger;
    }

    public final void doSetup() {
        checkInHeadlessMode();
        System.getProperties().setProperty("project.structure.add.tools.jar.to.new.jdk", "false");
        System.getProperties().setProperty("psi.track.invalidation", "true");
        System.getProperties().setProperty("psi.incremental.reparse.depth.limit", "1000");
        System.getProperties().setProperty("ide.hide.excluded.files", "false");
        System.getProperties().setProperty("ast.loading.filter", "false");
        System.getProperties().setProperty("idea.ignore.disabled.plugins", "true");
        System.getProperties().setProperty("idea.plugins.compatible.build", "999.SNAPSHOT");
    }

    private final void checkInHeadlessMode() {
        Application application = ApplicationManager.getApplication();
        if (application == null || application.isHeadlessEnvironment()) {
            return;
        }
        LOG.error(new Throwable("IdeaStandaloneExecutionSetup should be called only in headless environment"));
    }
}

package org.jetbrains.kotlin.com.intellij.ide.plugins;

import java.util.Arrays;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.psi.PsiKeyword;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ReferenceOpenHashSet;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;

/* JADX INFO: compiled from: DescriptorListLoadingContext.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000(\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\"\u001c\u0010\u0000\u001a\u0010\u0012\f\u0012\n \u0003*\u0004\u0018\u00010\u00020\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000\"8\u0010\u0004\u001a&\u0012\f\u0012\n \u0003*\u0004\u0018\u00010\u00020\u0002 \u0003*\u0012\u0012\f\u0012\n \u0003*\u0004\u0018\u00010\u00020\u0002\u0018\u00010\u00060\u0005X\u0082\u0004¢\u0006\b\n\u0000\u0012\u0004\b\u0007\u0010\b\"\u0014\u0010\t\u001a\u00020\n8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\f\"\u000e\u0010\r\u001a\u00020\u000eX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000f"}, d2 = {"CLASS_NAMES", "Lorg/jetbrains/kotlin/it/unimi/dsi/fastutil/objects/ReferenceOpenHashSet;", "", JvmProtoBufUtil.PLATFORM_TYPE_ID, "EXTRA_STRINGS", "", "", "getEXTRA_STRINGS$annotations", "()V", "LOG", "Lorg/jetbrains/kotlin/com/intellij/openapi/diagnostic/Logger;", "getLOG", "()Lcom/intellij/openapi/diagnostic/Logger;", "unitTestWithBundledPlugins", "", "intellij.platform.core.impl"}, k = 2, mv = {1, 5, 1})
public final class DescriptorListLoadingContextKt {
    private static final boolean unitTestWithBundledPlugins = Boolean.getBoolean("idea.run.tests.with.bundled.plugins");
    private static final ReferenceOpenHashSet<String> CLASS_NAMES = new ReferenceOpenHashSet<>(new String[]{"implementation", "implementationClass", "builderClass", "serviceImplementation", "class", "className", "beanClass", "serviceInterface", PsiKeyword.INTERFACE, "interfaceClass", "instance", "implementation-class", "qualifiedName"});
    private static final List<String> EXTRA_STRINGS = Arrays.asList("id", "order", "os", "JetBrains", "JetBrains s.r.o.", "org.jetbrains.kotlin.com.intellij.applicationService", "org.jetbrains.kotlin.com.intellij.projectService", "org.jetbrains.kotlin.com.intellij.moduleService", "org.jetbrains.kotlin.com.intellij.postStartupActivity", "org.jetbrains.kotlin.com.intellij", "org.jetbrains.kotlin.com.intellij.java", "org.jetbrains.kotlin.com.intellij.modules.java", "Docker", "intellij.clouds.docker.file", "intellij.clouds.docker.remoteRun");

    /* JADX INFO: Access modifiers changed from: private */
    public static final Logger getLOG() {
        Logger logger = PluginManagerCore.getLogger();
        Intrinsics.checkNotNullExpressionValue(logger, "PluginManagerCore.getLogger()");
        return logger;
    }
}

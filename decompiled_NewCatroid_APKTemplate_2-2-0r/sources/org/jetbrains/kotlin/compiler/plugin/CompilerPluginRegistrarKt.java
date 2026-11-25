package org.jetbrains.kotlin.compiler.plugin;

import com.huawei.hms.mlplugin.asr.MLAsrCaptureConstants;
import io.netty.util.internal.StringUtil;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.config.CompilerConfiguration;
import org.jetbrains.kotlin.extensions.ProjectExtensionDescriptor;

/* JADX INFO: compiled from: CompilerPluginRegistrar.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u00008\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\u001a5\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u001d\u0010\u0006\u001a\u0019\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0007¢\u0006\u0002\b\t\u001a\"\u0010\n\u001a\u00020\u0001*\b\u0012\u0004\u0012\u00020\f0\u000b2\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\r\u001a\u00020\fH\u0002\u001a(\u0010\u000e\u001a\u00020\u0001*\u00020\b2\u0006\u0010\u0002\u001a\u00020\u00032\u0014\b\u0002\u0010\u000f\u001a\u000e\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\u00110\u0010¨\u0006\u0012"}, d2 = {"registerExtensionsForTest", "", "project", "Lorg/jetbrains/kotlin/com/intellij/openapi/project/Project;", "configuration", "Lorg/jetbrains/kotlin/config/CompilerConfiguration;", "register", "Lkotlin/Function2;", "Lorg/jetbrains/kotlin/compiler/plugin/CompilerPluginRegistrar$ExtensionStorage;", "Lkotlin/ExtensionFunctionType;", "registerExtensionUnsafe", "Lorg/jetbrains/kotlin/extensions/ProjectExtensionDescriptor;", "", SchemaSymbols.ATTVAL_EXTENSION, "registerInProject", MLAsrCaptureConstants.ASR_ERROR_MESSAGE, "Lkotlin/Function1;", "", "plugin-api"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class CompilerPluginRegistrarKt {
    public static /* synthetic */ void registerInProject$default(CompilerPluginRegistrar.ExtensionStorage extensionStorage, Project project, Function1 function1, int i, Object obj) {
        if ((i & 2) != 0) {
            function1 = new Function1<Object, String>() { // from class: org.jetbrains.kotlin.compiler.plugin.CompilerPluginRegistrarKt.registerInProject.1
                @Override // kotlin.jvm.functions.Function1
                public final String invoke(Object it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    return "Error while registering " + it.getClass().getName() + StringUtil.SPACE;
                }
            };
        }
        registerInProject(extensionStorage, project, function1);
    }

    public static final void registerInProject(CompilerPluginRegistrar.ExtensionStorage extensionStorage, Project project, Function1<Object, String> errorMessage) {
        Intrinsics.checkNotNullParameter(extensionStorage, "<this>");
        Intrinsics.checkNotNullParameter(project, "project");
        Intrinsics.checkNotNullParameter(errorMessage, "errorMessage");
        for (Map.Entry<ProjectExtensionDescriptor<?>, List<Object>> entry : extensionStorage.getRegisteredExtensions().entrySet()) {
            ProjectExtensionDescriptor<?> key = entry.getKey();
            for (Object obj : entry.getValue()) {
                try {
                    Intrinsics.checkNotNull(key, "null cannot be cast to non-null type org.jetbrains.kotlin.extensions.ProjectExtensionDescriptor<kotlin.Any>");
                    registerExtensionUnsafe(key, project, obj);
                } catch (AbstractMethodError e) {
                    throw new IllegalStateException(errorMessage.invoke(obj), e);
                }
            }
        }
    }

    private static final void registerExtensionUnsafe(ProjectExtensionDescriptor<Object> projectExtensionDescriptor, Project project, Object obj) {
        projectExtensionDescriptor.registerExtension(project, obj);
    }

    public static final void registerExtensionsForTest(Project project, CompilerConfiguration configuration, Function2<? super CompilerPluginRegistrar.ExtensionStorage, ? super CompilerConfiguration, Unit> register) {
        Intrinsics.checkNotNullParameter(project, "project");
        Intrinsics.checkNotNullParameter(configuration, "configuration");
        Intrinsics.checkNotNullParameter(register, "register");
        CompilerPluginRegistrar.ExtensionStorage extensionStorage = new CompilerPluginRegistrar.ExtensionStorage();
        register.invoke(extensionStorage, configuration);
        registerInProject$default(extensionStorage, project, null, 2, null);
    }
}

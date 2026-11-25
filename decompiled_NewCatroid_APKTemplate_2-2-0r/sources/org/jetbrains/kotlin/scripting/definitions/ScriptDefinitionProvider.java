package org.jetbrains.kotlin.scripting.definitions;

import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.script.experimental.api.SourceCode;
import kotlin.sequences.Sequence;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.psi.PsiTreeChangeEvent;
import org.jetbrains.kotlin.utils.PathUtil;

/* JADX INFO: compiled from: KotlinScriptDefinitionProvider.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\bf\u0018\u0000 \u00102\u00020\u0001:\u0001\u0010J\u0012\u0010\u0002\u001a\u0004\u0018\u00010\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&J\u0012\u0010\u0006\u001a\u0004\u0018\u00010\u00072\u0006\u0010\b\u001a\u00020\tH'J\b\u0010\n\u001a\u00020\u0003H&J\b\u0010\u000b\u001a\u00020\u0007H'J\u000e\u0010\f\u001a\b\u0012\u0004\u0012\u00020\t0\rH&J\u0010\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0004\u001a\u00020\u0005H&ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0011À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/scripting/definitions/ScriptDefinitionProvider;", "", "findDefinition", "Lorg/jetbrains/kotlin/scripting/definitions/ScriptDefinition;", "script", "Lkotlin/script/experimental/api/SourceCode;", "findScriptDefinition", "Lorg/jetbrains/kotlin/scripting/definitions/KotlinScriptDefinition;", PsiTreeChangeEvent.PROP_FILE_NAME, "", "getDefaultDefinition", "getDefaultScriptDefinition", "getKnownFilenameExtensions", "Lkotlin/sequences/Sequence;", "isScript", "", "Companion", PathUtil.KOTLIN_SCRIPTING_COMPILER_IMPL_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface ScriptDefinitionProvider {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = Companion.$$INSTANCE;

    ScriptDefinition findDefinition(SourceCode script);

    @Deprecated(level = DeprecationLevel.ERROR, message = "Migrating to configuration refinement")
    KotlinScriptDefinition findScriptDefinition(String fileName);

    ScriptDefinition getDefaultDefinition();

    @Deprecated(level = DeprecationLevel.ERROR, message = "Migrating to configuration refinement")
    KotlinScriptDefinition getDefaultScriptDefinition();

    Sequence<String> getKnownFilenameExtensions();

    boolean isScript(SourceCode script);

    /* JADX INFO: compiled from: KotlinScriptDefinitionProvider.kt */
    @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\u0006J\u0010\u0010\u0007\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\u0006¨\u0006\b"}, d2 = {"Lorg/jetbrains/kotlin/scripting/definitions/ScriptDefinitionProvider$Companion;", "", "()V", "getInstance", "Lorg/jetbrains/kotlin/scripting/definitions/ScriptDefinitionProvider;", "project", "Lorg/jetbrains/kotlin/com/intellij/openapi/project/Project;", "getServiceIfCreated", PathUtil.KOTLIN_SCRIPTING_COMPILER_IMPL_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        static final /* synthetic */ Companion $$INSTANCE = new Companion();

        private Companion() {
        }

        public final ScriptDefinitionProvider getInstance(Project project) {
            Intrinsics.checkNotNullParameter(project, "project");
            return (ScriptDefinitionProvider) project.getService(ScriptDefinitionProvider.class);
        }

        public final ScriptDefinitionProvider getServiceIfCreated(Project project) {
            Intrinsics.checkNotNullParameter(project, "project");
            return (ScriptDefinitionProvider) project.getServiceIfCreated(ScriptDefinitionProvider.class);
        }
    }
}

package org.jetbrains.kotlin.scripting.definitions;

import java.io.File;
import java.lang.annotation.Annotation;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.script.dependencies.ScriptContents;
import kotlin.script.experimental.api.ScriptCollectedData;
import kotlin.script.experimental.api.ScriptConfigurationRefinementContext;
import kotlin.script.experimental.api.ScriptDataKt;
import kotlin.script.experimental.api.SourceCode;
import kotlin.script.experimental.host.FileBasedScriptSource;
import org.jetbrains.kotlin.utils.PathUtil;

/* JADX INFO: compiled from: ScriptCompilationConfigurationFromDefinition.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u001c\n\u0002\u0010\u001b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\r\n\u0002\b\u0003\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u001a\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u00068VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0016\u0010\f\u001a\u0004\u0018\u00010\r8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u000fR\u0016\u0010\u0010\u001a\u0004\u0018\u00010\u00118VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0012\u0010\u0013¨\u0006\u0014"}, d2 = {"Lorg/jetbrains/kotlin/scripting/definitions/ScriptContentsFromRefinementContext;", "Lkotlin/script/dependencies/ScriptContents;", "context", "Lkotlin/script/experimental/api/ScriptConfigurationRefinementContext;", "(Lkotlin/script/experimental/api/ScriptConfigurationRefinementContext;)V", "annotations", "", "", "getAnnotations", "()Ljava/lang/Iterable;", "getContext", "()Lkotlin/script/experimental/api/ScriptConfigurationRefinementContext;", "file", "Ljava/io/File;", "getFile", "()Ljava/io/File;", "text", "", "getText", "()Ljava/lang/CharSequence;", PathUtil.KOTLIN_SCRIPTING_COMPILER_IMPL_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
final class ScriptContentsFromRefinementContext implements ScriptContents {
    private final ScriptConfigurationRefinementContext context;

    public ScriptContentsFromRefinementContext(ScriptConfigurationRefinementContext context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
    }

    public final ScriptConfigurationRefinementContext getContext() {
        return this.context;
    }

    @Override // kotlin.script.dependencies.ScriptContents
    public File getFile() {
        SourceCode script = this.context.getScript();
        FileBasedScriptSource fileBasedScriptSource = script instanceof FileBasedScriptSource ? (FileBasedScriptSource) script : null;
        if (fileBasedScriptSource != null) {
            return fileBasedScriptSource.getFile();
        }
        return null;
    }

    @Override // kotlin.script.dependencies.ScriptContents
    public Iterable<Annotation> getAnnotations() {
        List listEmptyList;
        ScriptCollectedData collectedData = this.context.getCollectedData();
        if (collectedData == null || (listEmptyList = (List) collectedData.get(ScriptDataKt.getFoundAnnotations(ScriptCollectedData.INSTANCE))) == null) {
            listEmptyList = CollectionsKt.emptyList();
        }
        return listEmptyList;
    }

    @Override // kotlin.script.dependencies.ScriptContents
    public CharSequence getText() {
        return this.context.getScript().getText();
    }
}

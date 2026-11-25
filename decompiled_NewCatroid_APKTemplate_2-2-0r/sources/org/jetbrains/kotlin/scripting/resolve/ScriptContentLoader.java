package org.jetbrains.kotlin.scripting.resolve;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.annotation.Annotation;
import java.nio.charset.Charset;
import java.util.Map;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.Metadata;
import kotlin.collections.MapsKt;
import kotlin.io.TextStreamsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.script.dependencies.ScriptContents;
import kotlin.script.experimental.dependencies.DependenciesResolver;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.scripting.definitions.KotlinScriptDefinition;
import org.jetbrains.kotlin.utils.PathUtil;

/* JADX INFO: compiled from: ScriptContentLoader.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001:\u0001\u0011B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u001c\u0010\u0005\u001a\u0010\u0012\u0004\u0012\u00020\u0007\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u00062\u0006\u0010\b\u001a\u00020\tJ\u0016\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\t2\u0006\u0010\r\u001a\u00020\u000eJ\u0016\u0010\u000f\u001a\u00020\u00102\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\r\u001a\u00020\u000eR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/scripting/resolve/ScriptContentLoader;", "", "project", "Lorg/jetbrains/kotlin/com/intellij/openapi/project/Project;", "(Lcom/intellij/openapi/project/Project;)V", "getEnvironment", "", "", "scriptDef", "Lorg/jetbrains/kotlin/scripting/definitions/KotlinScriptDefinition;", "getScriptContents", "Lorg/jetbrains/kotlin/scripting/resolve/ScriptContentLoader$BasicScriptContents;", "scriptDefinition", "file", "Lorg/jetbrains/kotlin/com/intellij/openapi/vfs/VirtualFile;", "loadContentsAndResolveDependencies", "Lkotlin/script/experimental/dependencies/DependenciesResolver$ResolveResult;", "BasicScriptContents", PathUtil.KOTLIN_SCRIPTING_COMPILER_IMPL_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ScriptContentLoader {
    private final Project project;

    public ScriptContentLoader(Project project) {
        Intrinsics.checkNotNullParameter(project, "project");
        this.project = project;
    }

    public final BasicScriptContents getScriptContents(KotlinScriptDefinition scriptDefinition, VirtualFile file) {
        Intrinsics.checkNotNullParameter(scriptDefinition, "scriptDefinition");
        Intrinsics.checkNotNullParameter(file, "file");
        return RefineCompilationConfigurationKt.makeScriptContents(file, scriptDefinition, this.project, scriptDefinition.getTemplate().getClass().getClassLoader());
    }

    /* JADX INFO: compiled from: ScriptContentLoader.kt */
    @Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u001c\n\u0002\u0010\u001b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\r\n\u0002\b\u0004\u0018\u00002\u00020\u0001B!\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0012\u0010\u0004\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00070\u00060\u0005¢\u0006\u0002\u0010\bR!\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00070\u00068VX\u0096\u0084\u0002¢\u0006\f\n\u0004\b\u000b\u0010\f\u001a\u0004\b\u0004\u0010\nR\u0014\u0010\r\u001a\u00020\u000eX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u001d\u0010\u0011\u001a\u0004\u0018\u00010\u00128VX\u0096\u0084\u0002¢\u0006\f\n\u0004\b\u0015\u0010\f\u001a\u0004\b\u0013\u0010\u0014¨\u0006\u0016"}, d2 = {"Lorg/jetbrains/kotlin/scripting/resolve/ScriptContentLoader$BasicScriptContents;", "Lkotlin/script/dependencies/ScriptContents;", "virtualFile", "Lorg/jetbrains/kotlin/com/intellij/openapi/vfs/VirtualFile;", "getAnnotations", "Lkotlin/Function0;", "", "", "(Lcom/intellij/openapi/vfs/VirtualFile;Lkotlin/jvm/functions/Function0;)V", "annotations", "()Ljava/lang/Iterable;", "annotations$delegate", "Lkotlin/Lazy;", "file", "Ljava/io/File;", "getFile", "()Ljava/io/File;", "text", "", "getText", "()Ljava/lang/CharSequence;", "text$delegate", PathUtil.KOTLIN_SCRIPTING_COMPILER_IMPL_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class BasicScriptContents implements ScriptContents {

        /* JADX INFO: renamed from: annotations$delegate, reason: from kotlin metadata */
        private final Lazy annotations;
        private final File file;

        /* JADX INFO: renamed from: text$delegate, reason: from kotlin metadata */
        private final Lazy text;

        public BasicScriptContents(final VirtualFile virtualFile, final Function0<? extends Iterable<? extends Annotation>> getAnnotations) {
            Intrinsics.checkNotNullParameter(virtualFile, "virtualFile");
            Intrinsics.checkNotNullParameter(getAnnotations, "getAnnotations");
            this.file = new File(virtualFile.getPath());
            this.annotations = LazyKt.lazy(LazyThreadSafetyMode.PUBLICATION, (Function0) new Function0<Iterable<? extends Annotation>>() { // from class: org.jetbrains.kotlin.scripting.resolve.ScriptContentLoader$BasicScriptContents$annotations$2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                /* JADX WARN: Multi-variable type inference failed */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Iterable<? extends Annotation> invoke() {
                    return getAnnotations.invoke();
                }
            });
            this.text = LazyKt.lazy(LazyThreadSafetyMode.PUBLICATION, (Function0) new Function0<String>() { // from class: org.jetbrains.kotlin.scripting.resolve.ScriptContentLoader$BasicScriptContents$text$2
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final String invoke() throws IOException {
                    InputStream inputStream = virtualFile.getInputStream();
                    Intrinsics.checkNotNullExpressionValue(inputStream, "getInputStream(...)");
                    Charset charset = virtualFile.getCharset();
                    Intrinsics.checkNotNullExpressionValue(charset, "getCharset(...)");
                    return TextStreamsKt.readText(new InputStreamReader(inputStream, charset));
                }
            });
        }

        @Override // kotlin.script.dependencies.ScriptContents
        public File getFile() {
            return this.file;
        }

        @Override // kotlin.script.dependencies.ScriptContents
        public Iterable<Annotation> getAnnotations() {
            return (Iterable) this.annotations.getValue();
        }

        @Override // kotlin.script.dependencies.ScriptContents
        public CharSequence getText() {
            return (CharSequence) this.text.getValue();
        }
    }

    public final DependenciesResolver.ResolveResult loadContentsAndResolveDependencies(KotlinScriptDefinition scriptDef, VirtualFile file) {
        Intrinsics.checkNotNullParameter(scriptDef, "scriptDef");
        Intrinsics.checkNotNullParameter(file, "file");
        try {
            return scriptDef.getDependencyResolver().resolve(getScriptContents(scriptDef, file), getEnvironment(scriptDef));
        } catch (Throwable th) {
            return ScriptContentLoaderKt.asResolveFailure(th, scriptDef);
        }
    }

    public final Map<String, Object> getEnvironment(KotlinScriptDefinition scriptDef) {
        Intrinsics.checkNotNullParameter(scriptDef, "scriptDef");
        KotlinScriptDefinitionFromAnnotatedTemplate kotlinScriptDefinitionFromAnnotatedTemplate = scriptDef instanceof KotlinScriptDefinitionFromAnnotatedTemplate ? (KotlinScriptDefinitionFromAnnotatedTemplate) scriptDef : null;
        Map<String, Object> environment = kotlinScriptDefinitionFromAnnotatedTemplate != null ? kotlinScriptDefinitionFromAnnotatedTemplate.getEnvironment() : null;
        return environment == null ? MapsKt.emptyMap() : environment;
    }
}

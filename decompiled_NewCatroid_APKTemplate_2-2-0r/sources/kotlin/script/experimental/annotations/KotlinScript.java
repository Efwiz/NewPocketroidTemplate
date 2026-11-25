package kotlin.script.experimental.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import kotlin.Metadata;
import kotlin.annotation.AnnotationRetention;
import kotlin.annotation.AnnotationTarget;
import kotlin.script.experimental.api.ScriptCompilationConfiguration;
import kotlin.script.experimental.api.ScriptEvaluationConfiguration;
import kotlin.script.experimental.host.ScriptingHostConfiguration;
import org.jetbrains.kotlin.utils.PathUtil;

/* JADX INFO: compiled from: scriptAnnotations.kt */
/* JADX INFO: loaded from: classes12.dex */
@Target({ElementType.TYPE})
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u001b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0087\u0002\u0018\u00002\u00020\u0001BT\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0003\u0012\u0010\b\u0002\u0010\u0006\u001a\n\u0012\u0006\b\u0001\u0012\u00020\b0\u0007\u0012\u0010\b\u0002\u0010\t\u001a\n\u0012\u0006\b\u0001\u0012\u00020\n0\u0007\u0012\u0010\b\u0002\u0010\u000b\u001a\n\u0012\u0006\b\u0001\u0012\u00020\f0\u0007R\u0017\u0010\u0006\u001a\n\u0012\u0006\b\u0001\u0012\u00020\b0\u0007¢\u0006\u0006\u001a\u0004\b\u0006\u0010\rR\u000f\u0010\u0002\u001a\u00020\u0003¢\u0006\u0006\u001a\u0004\b\u0002\u0010\u000eR\u0017\u0010\t\u001a\n\u0012\u0006\b\u0001\u0012\u00020\n0\u0007¢\u0006\u0006\u001a\u0004\b\t\u0010\rR\u000f\u0010\u0004\u001a\u00020\u0003¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u000eR\u000f\u0010\u0005\u001a\u00020\u0003¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u000eR\u0017\u0010\u000b\u001a\n\u0012\u0006\b\u0001\u0012\u00020\f0\u0007¢\u0006\u0006\u001a\u0004\b\u000b\u0010\r¨\u0006\u000f"}, d2 = {"Lkotlin/script/experimental/annotations/KotlinScript;", "", "displayName", "", "fileExtension", "filePathPattern", "compilationConfiguration", "Lkotlin/reflect/KClass;", "Lkotlin/script/experimental/api/ScriptCompilationConfiguration;", "evaluationConfiguration", "Lkotlin/script/experimental/api/ScriptEvaluationConfiguration;", "hostConfiguration", "Lkotlin/script/experimental/host/ScriptingHostConfiguration;", "()Ljava/lang/Class;", "()Ljava/lang/String;", PathUtil.KOTLIN_SCRIPTING_COMMON_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
@kotlin.annotation.Target(allowedTargets = {AnnotationTarget.CLASS})
@Retention(RetentionPolicy.RUNTIME)
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
public @interface KotlinScript {
    Class<? extends ScriptCompilationConfiguration> compilationConfiguration() default ScriptCompilationConfiguration.Default.class;

    String displayName() default "";

    Class<? extends ScriptEvaluationConfiguration> evaluationConfiguration() default ScriptEvaluationConfiguration.Default.class;

    String fileExtension() default "kts";

    String filePathPattern() default "";

    Class<? extends ScriptingHostConfiguration> hostConfiguration() default ScriptingHostConfiguration.class;
}

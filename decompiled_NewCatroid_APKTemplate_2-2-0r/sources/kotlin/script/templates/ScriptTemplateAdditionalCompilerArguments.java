package kotlin.script.templates;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import kotlin.Metadata;
import kotlin.annotation.AnnotationRetention;
import kotlin.annotation.AnnotationTarget;
import org.catrobat.catroid.common.Constants;
import org.jetbrains.kotlin.utils.PathUtil;

/* JADX INFO: compiled from: annotations_deprecated.kt */
/* JADX INFO: loaded from: classes12.dex */
@Target({ElementType.TYPE})
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u001b\n\u0000\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0087\u0002\u0018\u00002\u00020\u0001B\"\u0012\u000e\b\u0002\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\u0010\b\u0002\u0010\u0005\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00070\u0006R\u0015\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\u0006\u001a\u0004\b\u0002\u0010\bR\u0017\u0010\u0005\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00070\u0006¢\u0006\u0006\u001a\u0004\b\u0005\u0010\t¨\u0006\n"}, d2 = {"Lkotlin/script/templates/ScriptTemplateAdditionalCompilerArguments;", "", "arguments", "", "", Constants.EXTRA_PROVIDER, "Lkotlin/reflect/KClass;", "Lkotlin/script/templates/ScriptTemplateAdditionalCompilerArgumentsProvider;", "()[Ljava/lang/String;", "()Ljava/lang/Class;", PathUtil.KOTLIN_JAVA_SCRIPT_RUNTIME_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
@kotlin.annotation.Target(allowedTargets = {AnnotationTarget.CLASS})
@Retention(RetentionPolicy.RUNTIME)
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
public @interface ScriptTemplateAdditionalCompilerArguments {
    String[] arguments() default {};

    Class<? extends ScriptTemplateAdditionalCompilerArgumentsProvider> provider() default ScriptTemplateAdditionalCompilerArgumentsProvider.class;
}

package org.jetbrains.kotlin.cli.common.arguments;

import com.google.android.gms.cast.MediaTrack;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import kotlin.Metadata;
import kotlin.annotation.AnnotationTarget;

/* JADX INFO: compiled from: parseCommandLineArguments.kt */
/* JADX INFO: loaded from: classes3.dex */
@Target({ElementType.FIELD})
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u001b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u000b\b\u0087\u0002\u0018\u00002\u00020\u0001:\u0002\f\rB8\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0007\u001a\u00020\u0003\u0012\u0006\u0010\b\u001a\u00020\u0003R\u001a\u0010\u0006\u001a\u00020\u00038\u0006X\u0087\u0004¢\u0006\f\u0012\u0004\b\t\u0010\n\u001a\u0004\b\u0006\u0010\u000bR\u000f\u0010\u0005\u001a\u00020\u0003¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u000bR\u000f\u0010\b\u001a\u00020\u0003¢\u0006\u0006\u001a\u0004\b\b\u0010\u000bR\u000f\u0010\u0004\u001a\u00020\u0003¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u000bR\u000f\u0010\u0002\u001a\u00020\u0003¢\u0006\u0006\u001a\u0004\b\u0002\u0010\u000bR\u000f\u0010\u0007\u001a\u00020\u0003¢\u0006\u0006\u001a\u0004\b\u0007\u0010\u000b¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/cli/common/arguments/Argument;", "", "value", "", "shortName", "deprecatedName", "delimiter", "valueDescription", MediaTrack.ROLE_DESCRIPTION, "delimiter$annotations", "()V", "()Ljava/lang/String;", "Delimiters", "RawDelimiter", "cli-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
@kotlin.annotation.Target(allowedTargets = {AnnotationTarget.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Argument {

    /* JADX INFO: compiled from: parseCommandLineArguments.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public static final class DefaultImpls {
        @RawDelimiter
        public static /* synthetic */ void delimiter$annotations() {
        }
    }

    /* JADX INFO: compiled from: parseCommandLineArguments.kt */
    @Retention(RetentionPolicy.RUNTIME)
    @Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0010\u001b\n\u0000\b\u0087\u0002\u0018\u00002\u00020\u0001B\u0000¨\u0006\u0002"}, d2 = {"Lorg/jetbrains/kotlin/cli/common/arguments/Argument$RawDelimiter;", "", "cli-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public @interface RawDelimiter {
    }

    String delimiter() default ",";

    String deprecatedName() default "";

    String description();

    String shortName() default "";

    String value();

    String valueDescription() default "";

    /* JADX INFO: compiled from: parseCommandLineArguments.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/cli/common/arguments/Argument$Delimiters;", "", "()V", "default", "", "none", "pathSeparator", "cli-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Delimiters {
        public static final Delimiters INSTANCE = new Delimiters();
        public static final String default = ",";
        public static final String none = "";
        public static final String pathSeparator = "<path_separator>";

        private Delimiters() {
        }
    }
}

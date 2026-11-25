package org.jetbrains.kotlin.cli.common.arguments;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import kotlin.Metadata;
import kotlin.annotation.AnnotationRetention;

/* JADX INFO: compiled from: GradleOption.kt */
/* JADX INFO: loaded from: classes3.dex */
@Retention(RetentionPolicy.RUNTIME)
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u001b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0087\u0002\u0018\u00002\u00020\u0001B\u001a\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007R\u000f\u0010\u0004\u001a\u00020\u0005¢\u0006\u0006\u001a\u0004\b\u0004\u0010\bR\u000f\u0010\u0006\u001a\u00020\u0007¢\u0006\u0006\u001a\u0004\b\u0006\u0010\tR\u000f\u0010\u0002\u001a\u00020\u0003¢\u0006\u0006\u001a\u0004\b\u0002\u0010\n¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/cli/common/arguments/GradleOption;", "", "value", "Lorg/jetbrains/kotlin/cli/common/arguments/DefaultValue;", "gradleInputType", "Lorg/jetbrains/kotlin/cli/common/arguments/GradleInputTypes;", "shouldGenerateDeprecatedKotlinOptions", "", "()Lorg/jetbrains/kotlin/cli/common/arguments/GradleInputTypes;", "()Z", "()Lorg/jetbrains/kotlin/cli/common/arguments/DefaultValue;", "cli-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
public @interface GradleOption {
    GradleInputTypes gradleInputType();

    boolean shouldGenerateDeprecatedKotlinOptions() default false;

    DefaultValue value();
}

package org.jf.util.jcommander;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* JADX INFO: loaded from: classes6.dex */
@Retention(RetentionPolicy.RUNTIME)
public @interface ExtendedParameters {
    String[] commandAliases() default {};

    String commandName();

    boolean includeParametersInUsage() default false;

    String postfixDescription() default "";
}

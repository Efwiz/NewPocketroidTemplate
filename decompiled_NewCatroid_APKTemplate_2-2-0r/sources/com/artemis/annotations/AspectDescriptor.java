package com.artemis.annotations;

import com.artemis.Component;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/* JADX INFO: loaded from: classes8.dex */
@Target({ElementType.FIELD})
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface AspectDescriptor {
    Class<? extends Component>[] all() default {};

    Class<? extends Component>[] exclude() default {};

    Class<? extends Component>[] one() default {};
}

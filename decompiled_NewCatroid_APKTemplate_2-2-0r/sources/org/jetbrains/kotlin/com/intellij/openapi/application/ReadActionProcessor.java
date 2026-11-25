package org.jetbrains.kotlin.com.intellij.openapi.application;

import org.jetbrains.kotlin.com.intellij.openapi.util.ThrowableComputable;
import org.jetbrains.kotlin.com.intellij.util.Processor;

/* JADX INFO: loaded from: classes3.dex */
public abstract class ReadActionProcessor<T> implements Processor<T> {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 1 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[i != 1 ? 3 : 2];
        if (i != 1) {
            objArr[0] = "processor";
        } else {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/openapi/application/ReadActionProcessor";
        }
        if (i != 1) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/openapi/application/ReadActionProcessor";
        } else {
            objArr[1] = "wrapInReadAction";
        }
        if (i != 1) {
            objArr[2] = "wrapInReadAction";
        }
        String str2 = String.format(str, objArr);
        if (i == 1) {
            throw new IllegalStateException(str2);
        }
    }

    public abstract boolean processInReadAction(T t);

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX INFO: renamed from: lambda$process$0$org-jetbrains-kotlin-com-intellij-openapi-application-ReadActionProcessor, reason: not valid java name */
    /* synthetic */ Boolean m6471x62cdba88(Object obj) throws RuntimeException {
        return Boolean.valueOf(processInReadAction(obj));
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.Processor
    public boolean process(final T t) {
        return ((Boolean) ReadAction.compute(new ThrowableComputable() { // from class: org.jetbrains.kotlin.com.intellij.openapi.application.ReadActionProcessor$$ExternalSyntheticLambda0
            @Override // org.jetbrains.kotlin.com.intellij.openapi.util.ThrowableComputable
            public final Object compute() {
                return this.f$0.m6471x62cdba88(t);
            }
        })).booleanValue();
    }

    public static <T> Processor<T> wrapInReadAction(final Processor<? super T> processor) {
        if (processor == null) {
            $$$reportNull$$$0(0);
        }
        return new Processor() { // from class: org.jetbrains.kotlin.com.intellij.openapi.application.ReadActionProcessor$$ExternalSyntheticLambda1
            @Override // org.jetbrains.kotlin.com.intellij.util.Processor
            public final boolean process(Object obj) {
                return ((Boolean) ReadAction.compute(new ThrowableComputable() { // from class: org.jetbrains.kotlin.com.intellij.openapi.application.ReadActionProcessor$$ExternalSyntheticLambda2
                    @Override // org.jetbrains.kotlin.com.intellij.openapi.util.ThrowableComputable
                    public final Object compute() {
                        return Boolean.valueOf(processor.process(obj));
                    }
                })).booleanValue();
            }
        };
    }
}

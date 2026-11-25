package org.jetbrains.kotlin.com.intellij.util;

import java.util.Collection;
import org.jetbrains.kotlin.com.intellij.openapi.progress.ProgressManager;

/* JADX INFO: loaded from: classes3.dex */
public final class Processors {
    /* JADX WARN: Code duplicated, block: B:21:0x0036  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 2 || i == 5) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 2 || i == 5) ? 2 : 3];
        if (i == 1) {
            objArr[0] = "filter";
        } else if (i == 2) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/util/Processors";
        } else if (i == 4) {
            objArr[0] = "map";
        } else if (i == 5) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/util/Processors";
        } else if (i != 6) {
            objArr[0] = "processor";
        } else {
            objArr[0] = "collection";
        }
        if (i == 2) {
            objArr[1] = "filter";
        } else if (i != 5) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/util/Processors";
        } else {
            objArr[1] = "map";
        }
        if (i != 2) {
            if (i == 3 || i == 4) {
                objArr[2] = "map";
            } else if (i != 5) {
                if (i != 6) {
                    objArr[2] = "filter";
                } else {
                    objArr[2] = "cancelableCollectProcessor";
                }
            }
        }
        String str2 = String.format(str, objArr);
        if (i != 2 && i != 5) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    public static <T> Processor<T> cancelableCollectProcessor(Collection<T> collection) {
        if (collection == null) {
            $$$reportNull$$$0(6);
        }
        return new CommonProcessors.CollectProcessor<T>(collection) { // from class: org.jetbrains.kotlin.com.intellij.util.Processors.1
            @Override // org.jetbrains.kotlin.com.intellij.util.CommonProcessors.CollectProcessor, org.jetbrains.kotlin.com.intellij.util.Processor
            public boolean process(T t) {
                ProgressManager.checkCanceled();
                return super.process(t);
            }
        };
    }
}

package org.jetbrains.kotlin.com.intellij.util;

import java.util.List;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.openapi.progress.ProcessCanceledException;
import org.jetbrains.kotlin.com.intellij.openapi.progress.ProgressManager;
import org.jetbrains.kotlin.com.intellij.openapi.project.IndexNotReadyException;

/* JADX INFO: loaded from: classes3.dex */
public final class ExecutorsQuery<Result, Parameter> extends AbstractQuery<Result> {
    private static final Logger LOG = Logger.getInstance((Class<?>) ExecutorsQuery.class);
    private final List<? extends QueryExecutor<Result, Parameter>> myExecutors;
    private final Parameter myParameters;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i == 1) {
            objArr[0] = "executors";
        } else if (i != 2) {
            objArr[0] = "params";
        } else {
            objArr[0] = "consumer";
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/util/ExecutorsQuery";
        if (i != 2) {
            objArr[2] = "<init>";
        } else {
            objArr[2] = "processResults";
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    public ExecutorsQuery(Parameter parameter, List<? extends QueryExecutor<Result, Parameter>> list) {
        if (parameter == null) {
            $$$reportNull$$$0(0);
        }
        if (list == null) {
            $$$reportNull$$$0(1);
        }
        this.myParameters = parameter;
        this.myExecutors = list;
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.AbstractQuery
    protected boolean processResults(Processor<? super Result> processor) {
        if (processor == null) {
            $$$reportNull$$$0(2);
        }
        for (QueryExecutor<Result, Parameter> queryExecutor : this.myExecutors) {
            try {
                ProgressManager.checkCanceled();
                if (!queryExecutor.execute(this.myParameters, processor)) {
                    return false;
                }
            } catch (ProcessCanceledException e) {
                throw e;
            } catch (IndexNotReadyException e2) {
                throw e2;
            } catch (Exception e3) {
                LOG.error((Throwable) e3);
            }
        }
        return true;
    }
}

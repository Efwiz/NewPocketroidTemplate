package org.jetbrains.kotlin.com.intellij.openapi.application;

import java.util.concurrent.atomic.AtomicBoolean;
import org.jetbrains.kotlin.com.intellij.openapi.project.DumbService;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.util.Processor;
import org.jetbrains.kotlin.com.intellij.util.QueryExecutor;
import org.jetbrains.kotlin.com.intellij.util.QueryParameters;

/* JADX INFO: loaded from: classes3.dex */
public abstract class QueryExecutorBase<Result, Params> implements QueryExecutor<Result, Params> {
    private final boolean myRequireReadAction;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i != 1) {
            objArr[0] = "queryParameters";
        } else {
            objArr[0] = "consumer";
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/openapi/application/QueryExecutorBase";
        objArr[2] = "execute";
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    public abstract void processQuery(Params params, Processor<? super Result> processor);

    protected QueryExecutorBase(boolean z) {
        this.myRequireReadAction = z;
    }

    protected QueryExecutorBase() {
        this(false);
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.QueryExecutor
    public final boolean execute(final Params params, final Processor<? super Result> processor) {
        if (params == null) {
            $$$reportNull$$$0(0);
        }
        if (processor == null) {
            $$$reportNull$$$0(1);
        }
        final AtomicBoolean atomicBoolean = new AtomicBoolean(true);
        final Processor<? super Result> processor2 = new Processor() { // from class: org.jetbrains.kotlin.com.intellij.openapi.application.QueryExecutorBase$$ExternalSyntheticLambda0
            @Override // org.jetbrains.kotlin.com.intellij.util.Processor
            public final boolean process(Object obj) {
                return QueryExecutorBase.lambda$execute$0(atomicBoolean, processor, obj);
            }
        };
        if (this.myRequireReadAction && !ApplicationManager.getApplication().isReadAccessAllowed()) {
            Runnable runnable = new Runnable() { // from class: org.jetbrains.kotlin.com.intellij.openapi.application.QueryExecutorBase$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m6470xae2bdc35(params, processor2);
                }
            };
            if (!DumbService.isDumbAware(this)) {
                Project project = params instanceof QueryParameters ? ((QueryParameters) params).getProject() : null;
                if (project != null) {
                    DumbService.getInstance(project).runReadActionInSmartMode(runnable);
                    return atomicBoolean.get();
                }
            }
            ApplicationManager.getApplication().runReadAction(runnable);
        } else {
            processQuery(params, processor2);
        }
        return atomicBoolean.get();
    }

    static /* synthetic */ boolean lambda$execute$0(AtomicBoolean atomicBoolean, Processor processor, Object obj) {
        if (!atomicBoolean.get()) {
            return false;
        }
        if (processor.process(obj)) {
            return true;
        }
        atomicBoolean.set(false);
        return false;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX INFO: renamed from: lambda$execute$1$org-jetbrains-kotlin-com-intellij-openapi-application-QueryExecutorBase, reason: not valid java name */
    /* synthetic */ void m6470xae2bdc35(Object obj, Processor processor) {
        if (!(obj instanceof QueryParameters) || ((QueryParameters) obj).isQueryValid()) {
            processQuery(obj, processor);
        }
    }
}

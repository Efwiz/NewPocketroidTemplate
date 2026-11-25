package org.jetbrains.kotlin.com.intellij.openapi.project;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import org.jetbrains.kotlin.com.intellij.openapi.application.ApplicationManager;
import org.jetbrains.kotlin.com.intellij.openapi.application.ReadAction;
import org.jetbrains.kotlin.com.intellij.openapi.progress.ProcessCanceledException;
import org.jetbrains.kotlin.com.intellij.openapi.util.ThrowableComputable;
import org.jetbrains.kotlin.com.intellij.util.messages.Topic;

/* JADX INFO: loaded from: classes3.dex */
public abstract class DumbService {
    public static final Topic<DumbModeListener> DUMB_MODE = new Topic<>("dumb mode", DumbModeListener.class, Topic.BroadcastDirection.NONE);

    public interface DumbModeListener {
    }

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 3 || i == 4 || i == 5 || i == 8 || i == 15 || i == 17) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 3 || i == 4 || i == 5 || i == 8 || i == 15 || i == 17) ? 2 : 3];
        switch (i) {
            case 2:
            case 7:
                objArr[0] = "extensionPoint";
                break;
            case 3:
            case 4:
            case 5:
            case 8:
            case 15:
            case 17:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/openapi/project/DumbService";
                break;
            case 6:
            case 13:
            default:
                objArr[0] = "project";
                break;
            case 9:
            case 11:
            case 12:
                objArr[0] = "r";
                break;
            case 10:
                objArr[0] = "task";
                break;
            case 14:
                objArr[0] = "array";
                break;
            case 16:
                objArr[0] = "collection";
                break;
            case 18:
                objArr[0] = "componentToDisable";
                break;
            case 19:
                objArr[0] = "parentDisposable";
                break;
            case 20:
            case 21:
            case 22:
            case 24:
                objArr[0] = "runnable";
                break;
            case 23:
                objArr[0] = "permission";
                break;
        }
        if (i == 3 || i == 4 || i == 5 || i == 8) {
            objArr[1] = "getDumbAwareExtensions";
        } else if (i == 15 || i == 17) {
            objArr[1] = "filterByDumbAwareness";
        } else {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/openapi/project/DumbService";
        }
        switch (i) {
            case 1:
            case 2:
            case 6:
            case 7:
                objArr[2] = "getDumbAwareExtensions";
                break;
            case 3:
            case 4:
            case 5:
            case 8:
            case 15:
            case 17:
                break;
            case 9:
            case 11:
                objArr[2] = "runReadActionInSmartMode";
                break;
            case 10:
                objArr[2] = "tryRunReadActionInSmartMode";
                break;
            case 12:
                objArr[2] = "repeatUntilPassesInSmartMode";
                break;
            case 13:
                objArr[2] = "getInstance";
                break;
            case 14:
            case 16:
                objArr[2] = "filterByDumbAwareness";
                break;
            case 18:
            case 19:
                objArr[2] = "makeDumbAware";
                break;
            case 20:
                objArr[2] = "withAlternativeResolveEnabled";
                break;
            case 21:
                objArr[2] = "computeWithAlternativeResolveEnabled";
                break;
            case 22:
                objArr[2] = "runWithAlternativeResolveEnabled";
                break;
            case 23:
            case 24:
                objArr[2] = "allowStartingDumbModeInside";
                break;
            default:
                objArr[2] = "isDumb";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 3 && i != 4 && i != 5 && i != 8 && i != 15 && i != 17) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    public abstract void completeJustSubmittedTasks();

    public abstract Project getProject();

    public abstract boolean isAlternativeResolveEnabled();

    public abstract boolean isDumb();

    public abstract void waitForSmartMode();

    public static boolean isDumb(Project project) {
        if (project == null) {
            $$$reportNull$$$0(0);
        }
        return getInstance(project).isDumb();
    }

    public void runReadActionInSmartMode(final Runnable runnable) {
        if (runnable == null) {
            $$$reportNull$$$0(11);
        }
        if (ApplicationManager.getApplication().isReadAccessAllowed()) {
            runnable.run();
        } else {
            do {
                waitForSmartMode();
            } while (!((Boolean) ReadAction.compute(new ThrowableComputable() { // from class: org.jetbrains.kotlin.com.intellij.openapi.project.DumbService$$ExternalSyntheticLambda0
                @Override // org.jetbrains.kotlin.com.intellij.openapi.util.ThrowableComputable
                public final Object compute() {
                    return this.f$0.m6499xb1478e36(runnable);
                }
            })).booleanValue());
        }
    }

    /* JADX INFO: renamed from: lambda$runReadActionInSmartMode$1$org-jetbrains-kotlin-com-intellij-openapi-project-DumbService, reason: not valid java name */
    /* synthetic */ Boolean m6499xb1478e36(Runnable runnable) throws RuntimeException {
        if (getProject().isDisposed()) {
            throw new ProcessCanceledException();
        }
        if (isDumb()) {
            return false;
        }
        runnable.run();
        return true;
    }

    public static DumbService getInstance(Project project) {
        if (project == null) {
            $$$reportNull$$$0(13);
        }
        return (DumbService) project.getService(DumbService.class);
    }

    public <T> List<T> filterByDumbAwareness(T[] tArr) {
        if (tArr == null) {
            $$$reportNull$$$0(14);
        }
        List<T> listFilterByDumbAwareness = filterByDumbAwareness(Arrays.asList(tArr));
        if (listFilterByDumbAwareness == null) {
            $$$reportNull$$$0(15);
        }
        return listFilterByDumbAwareness;
    }

    public <T> List<T> filterByDumbAwareness(Collection<? extends T> collection) {
        if (collection == null) {
            $$$reportNull$$$0(16);
        }
        if (!isDumb()) {
            return collection instanceof List ? (List) collection : new ArrayList(collection);
        }
        ArrayList arrayList = new ArrayList(collection.size());
        for (T t : collection) {
            if (isDumbAware(t)) {
                arrayList.add(t);
            }
        }
        return arrayList;
    }

    public static boolean isDumbAware(Object obj) {
        if (obj instanceof PossiblyDumbAware) {
            return ((PossiblyDumbAware) obj).isDumbAware();
        }
        return obj instanceof DumbAware;
    }
}

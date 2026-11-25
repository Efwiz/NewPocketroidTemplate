package org.catrobat.catroid.web;

import java.util.Collections;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.Intrinsics;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/web/GlobalProjectDownloadQueue.class
  classes14.dex
 */
/* JADX INFO: compiled from: ProjectDownloader.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\bÆ\u0002\u0018\u00002\u00020\u0001:\u0001\u0007B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\b"}, d2 = {"Lorg/catrobat/catroid/web/GlobalProjectDownloadQueue;", "", "()V", "queue", "Lorg/catrobat/catroid/web/GlobalProjectDownloadQueue$ProjectDownloadQueue;", "getQueue", "()Lorg/catrobat/catroid/web/GlobalProjectDownloadQueue$ProjectDownloadQueue;", "ProjectDownloadQueue", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class GlobalProjectDownloadQueue {
    public static final GlobalProjectDownloadQueue INSTANCE = new GlobalProjectDownloadQueue();
    private static final ProjectDownloadQueue queue = new ProjectDownloadQueue();

    private GlobalProjectDownloadQueue() {
    }

    public final ProjectDownloadQueue getQueue() {
        return queue;
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/web/GlobalProjectDownloadQueue$ProjectDownloadQueue.class
      classes14.dex
     */
    /* JADX INFO: compiled from: ProjectDownloader.kt */
    @Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u0005H\u0016J\u0010\u0010\t\u001a\u00020\n2\u0006\u0010\b\u001a\u00020\u0005H\u0016J\u0010\u0010\u000b\u001a\u00020\n2\u0006\u0010\b\u001a\u00020\u0005H\u0016R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\f"}, d2 = {"Lorg/catrobat/catroid/web/GlobalProjectDownloadQueue$ProjectDownloadQueue;", "Lorg/catrobat/catroid/web/ProjectDownloader$ProjectDownloadQueue;", "()V", "projectNameSet", "", "", "alreadyInQueue", "", "projectName", "enqueue", "", "finished", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class ProjectDownloadQueue implements ProjectDownloader.ProjectDownloadQueue {
        private Set<String> projectNameSet;

        public ProjectDownloadQueue() {
            Set<String> setSynchronizedSet = Collections.synchronizedSet(new HashSet());
            Intrinsics.checkNotNullExpressionValue(setSynchronizedSet, "synchronizedSet(...)");
            this.projectNameSet = setSynchronizedSet;
        }

        @Override // org.catrobat.catroid.web.ProjectDownloader.ProjectDownloadQueue
        public synchronized boolean alreadyInQueue(String projectName) {
            Set<String> set;
            String lowerCase;
            Intrinsics.checkNotNullParameter(projectName, "projectName");
            set = this.projectNameSet;
            Locale locale = Locale.getDefault();
            Intrinsics.checkNotNullExpressionValue(locale, "getDefault(...)");
            lowerCase = projectName.toLowerCase(locale);
            Intrinsics.checkNotNullExpressionValue(lowerCase, "toLowerCase(...)");
            return set.contains(lowerCase);
        }

        @Override // org.catrobat.catroid.web.ProjectDownloader.ProjectDownloadQueue
        public synchronized void enqueue(String projectName) {
            Intrinsics.checkNotNullParameter(projectName, "projectName");
            Set<String> set = this.projectNameSet;
            Locale locale = Locale.getDefault();
            Intrinsics.checkNotNullExpressionValue(locale, "getDefault(...)");
            String lowerCase = projectName.toLowerCase(locale);
            Intrinsics.checkNotNullExpressionValue(lowerCase, "toLowerCase(...)");
            this.projectNameSet = SetsKt.plus(set, lowerCase);
        }

        @Override // org.catrobat.catroid.web.ProjectDownloader.ProjectDownloadQueue
        public synchronized void finished(String projectName) {
            Intrinsics.checkNotNullParameter(projectName, "projectName");
            Set<String> set = this.projectNameSet;
            Locale locale = Locale.getDefault();
            Intrinsics.checkNotNullExpressionValue(locale, "getDefault(...)");
            String lowerCase = projectName.toLowerCase(locale);
            Intrinsics.checkNotNullExpressionValue(lowerCase, "toLowerCase(...)");
            this.projectNameSet = SetsKt.minus(set, lowerCase);
        }
    }
}

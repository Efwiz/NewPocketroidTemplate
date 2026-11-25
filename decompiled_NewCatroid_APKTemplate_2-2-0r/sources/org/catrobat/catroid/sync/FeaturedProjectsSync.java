package org.catrobat.catroid.sync;

import kotlin.Metadata;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/sync/FeaturedProjectsSync.class
  classes14.dex
 */
/* JADX INFO: compiled from: FeaturedProjectsSync.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\bf\u0018\u00002\u00020\u0001J\u0012\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005H&¨\u0006\u0006"}, d2 = {"Lorg/catrobat/catroid/sync/FeaturedProjectsSync;", "", "sync", "", "force", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface FeaturedProjectsSync {
    void sync(boolean force);

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/sync/FeaturedProjectsSync$DefaultImpls.class
      classes14.dex
     */
    /* JADX INFO: compiled from: FeaturedProjectsSync.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public static final class DefaultImpls {
        public static /* synthetic */ void sync$default(FeaturedProjectsSync featuredProjectsSync, boolean z, int i, Object obj) {
            if (obj != null) {
                throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: sync");
            }
            if ((i & 1) != 0) {
                z = false;
            }
            featuredProjectsSync.sync(z);
        }
    }
}

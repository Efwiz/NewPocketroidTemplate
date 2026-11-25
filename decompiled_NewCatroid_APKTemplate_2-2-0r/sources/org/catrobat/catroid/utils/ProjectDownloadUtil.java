package org.catrobat.catroid.utils;

import java.lang.ref.WeakReference;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.catroid.scratchconverter.Client;
import org.catrobat.catroid.ui.recyclerview.fragment.MainMenuFragment;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/utils/ProjectDownloadUtil.class
  classes14.dex
 */
/* JADX INFO: compiled from: ProjectDownloadUtil.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0005\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0018\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\tH\u0016J\u0018\u0010\u000b\u001a\u00020\u00072\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\n\u001a\u00020\tH\u0016J\u0010\u0010\u000e\u001a\u00020\u00072\u0006\u0010\u000f\u001a\u00020\tH\u0016J\u0010\u0010\u0010\u001a\u00020\u00072\u0006\u0010\n\u001a\u00020\tH\u0016J\u000e\u0010\u0011\u001a\u00020\u00072\u0006\u0010\u0003\u001a\u00020\u0005R\u0016\u0010\u0003\u001a\n\u0012\u0004\u0012\u00020\u0005\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0012"}, d2 = {"Lorg/catrobat/catroid/utils/ProjectDownloadUtil;", "Lorg/catrobat/catroid/scratchconverter/Client$ProjectDownloadCallback;", "()V", "fragment", "Ljava/lang/ref/WeakReference;", "Lorg/catrobat/catroid/ui/recyclerview/fragment/MainMenuFragment;", "onDownloadFinished", "", "catrobatProgramName", "", "url", "onDownloadProgress", "progress", "", "onDownloadStarted", "string", "onUserCanceledDownload", "setFragment", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ProjectDownloadUtil implements Client.ProjectDownloadCallback {
    public static final ProjectDownloadUtil INSTANCE = new ProjectDownloadUtil();
    private static WeakReference<MainMenuFragment> fragment;

    @Override // org.catrobat.catroid.scratchconverter.Client.ProjectDownloadCallback
    public void onDownloadProgress(int progress, String url) {
        Intrinsics.checkNotNullParameter(url, "url");
    }

    @Override // org.catrobat.catroid.scratchconverter.Client.ProjectDownloadCallback
    public void onDownloadStarted(String string) {
        Intrinsics.checkNotNullParameter(string, "string");
    }

    @Override // org.catrobat.catroid.scratchconverter.Client.ProjectDownloadCallback
    public void onUserCanceledDownload(String url) {
        Intrinsics.checkNotNullParameter(url, "url");
    }

    private ProjectDownloadUtil() {
    }

    public final void setFragment(MainMenuFragment fragment2) {
        Intrinsics.checkNotNullParameter(fragment2, "fragment");
        fragment = new WeakReference<>(fragment2);
    }

    @Override // org.catrobat.catroid.scratchconverter.Client.ProjectDownloadCallback
    public void onDownloadFinished(String catrobatProgramName, String url) {
        MainMenuFragment mainMenuFragment;
        Intrinsics.checkNotNullParameter(catrobatProgramName, "catrobatProgramName");
        Intrinsics.checkNotNullParameter(url, "url");
        WeakReference<MainMenuFragment> weakReference = fragment;
        if (weakReference == null || (mainMenuFragment = weakReference.get()) == null) {
            return;
        }
        mainMenuFragment.refreshData();
    }
}

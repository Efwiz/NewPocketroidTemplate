package org.catrobat.catroid.pocketmusic.fastscroller.viewprovider;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/pocketmusic/fastscroller/viewprovider/DefaultHandleBehavior.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class DefaultHandleBehavior implements ViewBehavior {
    private final VisibilityAnimationManager animationManager;

    public DefaultHandleBehavior(VisibilityAnimationManager animationManager) {
        this.animationManager = animationManager;
    }

    @Override // org.catrobat.catroid.pocketmusic.fastscroller.viewprovider.ViewBehavior
    public void onHandleGrabbed() {
        this.animationManager.show();
    }

    @Override // org.catrobat.catroid.pocketmusic.fastscroller.viewprovider.ViewBehavior
    public void onHandleReleased() {
        this.animationManager.hide();
    }

    @Override // org.catrobat.catroid.pocketmusic.fastscroller.viewprovider.ViewBehavior
    public void onScrollStarted() {
        this.animationManager.show();
    }

    @Override // org.catrobat.catroid.pocketmusic.fastscroller.viewprovider.ViewBehavior
    public void onScrollFinished() {
        this.animationManager.hide();
    }
}

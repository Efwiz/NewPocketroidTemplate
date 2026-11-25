package org.catrobat.catroid.pocketmusic.fastscroller.viewprovider;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/pocketmusic/fastscroller/viewprovider/DefaultBubbleBehavior.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class DefaultBubbleBehavior implements ViewBehavior {
    private final VisibilityAnimationManager animationManager;

    @Override // org.catrobat.catroid.pocketmusic.fastscroller.viewprovider.ViewBehavior
    public void onScrollFinished() {
    }

    @Override // org.catrobat.catroid.pocketmusic.fastscroller.viewprovider.ViewBehavior
    public void onScrollStarted() {
    }

    public DefaultBubbleBehavior(VisibilityAnimationManager animationManager) {
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
}

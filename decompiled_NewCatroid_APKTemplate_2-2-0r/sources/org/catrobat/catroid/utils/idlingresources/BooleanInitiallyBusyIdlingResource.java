package org.catrobat.catroid.utils.idlingresources;

import androidx.test.espresso.IdlingResource;
import java.util.concurrent.atomic.AtomicBoolean;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/utils/idlingresources/BooleanInitiallyBusyIdlingResource.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class BooleanInitiallyBusyIdlingResource implements IdlingResource {
    private volatile IdlingResource.ResourceCallback callback;
    private AtomicBoolean isIdleNow = new AtomicBoolean(false);

    @Override // androidx.test.espresso.IdlingResource
    public String getName() {
        return BooleanInitiallyBusyIdlingResource.class.getName();
    }

    @Override // androidx.test.espresso.IdlingResource
    public boolean isIdleNow() {
        return this.isIdleNow.get();
    }

    @Override // androidx.test.espresso.IdlingResource
    public void registerIdleTransitionCallback(IdlingResource.ResourceCallback callback) {
        this.callback = callback;
    }

    public void setIdleState(boolean isIdleNow) {
        this.isIdleNow.set(isIdleNow);
        if (!isIdleNow || this.callback == null) {
            return;
        }
        this.callback.onTransitionToIdle();
    }
}

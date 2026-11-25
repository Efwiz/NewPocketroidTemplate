package org.catrobat.catroid.ui;

import android.view.Menu;
import android.view.MenuItem;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.cast.CastManager;
import org.catrobat.catroid.ui.settingsfragments.SettingsFragment;
import org.jetbrains.kotlin.org.jline.reader.LineReader;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/BaseCastActivity.class
  classes14.dex
 */
/* JADX INFO: compiled from: BaseCastActivity.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b&\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0016J\u0010\u0010\u0007\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\tH\u0016¨\u0006\n"}, d2 = {"Lorg/catrobat/catroid/ui/BaseCastActivity;", "Lorg/catrobat/catroid/ui/BaseActivity;", "()V", "onCreateOptionsMenu", "", LineReader.MENU, "Landroid/view/Menu;", "onOptionsItemSelected", "item", "Landroid/view/MenuItem;", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class BaseCastActivity extends BaseActivity {
    @Override // org.catrobat.catroid.ui.BaseActivity, android.app.Activity
    public boolean onCreateOptionsMenu(Menu menu) {
        Intrinsics.checkNotNullParameter(menu, "menu");
        if (SettingsFragment.isCastSharedPreferenceEnabled(this)) {
            CastManager.getInstance().setCastButton(menu.findItem(R.id.cast_button));
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override // org.catrobat.catroid.ui.BaseActivity, android.app.Activity
    public boolean onOptionsItemSelected(MenuItem item) {
        Intrinsics.checkNotNullParameter(item, "item");
        if (item.getItemId() == R.id.cast_button) {
            CastManager.getInstance().openDeviceSelectorOrDisconnectDialog();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

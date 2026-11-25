package org.catrobat.catroid.ui.recyclerview.backpack;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.MyActivityManager;
import org.catrobat.catroid.ui.BaseActivity;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/recyclerview/backpack/ActorAndObjectActivity.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class ActorAndObjectActivity extends BaseActivity {
    public static final String EXTRA_FRAGMENT_POSITION = "fragmentPosition";
    public static final int FRAGMENT_SPRITES = 0;
    public static final String TAG = "ActorAndObjectActivity";

    @Override // org.catrobat.catroid.ui.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_backpack);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setTitle(R.string.actor_object_title);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#83B3C7")));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        switchToFragment();
        MyActivityManager.INSTANCE.setActor_activity(this);
    }

    private void switchToFragment() {
        FragmentTransaction fragmentTransactionBeginTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransactionBeginTransaction.replace(R.id.fragment_container, new ActorAndObjectSpriteFragment(), ActorAndObjectSpriteFragment.TAG);
        fragmentTransactionBeginTransaction.commit();
    }
}

package org.catrobat.catroid.ui.recyclerview.backpack;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.ui.BaseActivity;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/recyclerview/backpack/BackpackActivity.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class BackpackActivity extends BaseActivity {
    public static final String EXTRA_FRAGMENT_POSITION = "fragmentPosition";
    public static final int FRAGMENT_LOOKS = 2;
    public static final int FRAGMENT_SCENES = 0;
    public static final int FRAGMENT_SCRIPTS = 4;
    public static final int FRAGMENT_SOUNDS = 3;
    public static final int FRAGMENT_SPRITES = 1;
    public static final String TAG = "BackpackActivity";
    private int fragmentPosition = 0;

    @Override // org.catrobat.catroid.ui.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        Bundle extras;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_backpack);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setTitle(R.string.backpack_title);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#83B3C7")));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (savedInstanceState == null && (extras = getIntent().getExtras()) != null) {
            this.fragmentPosition = extras.getInt("fragmentPosition", 0);
        }
        switchToFragment(this.fragmentPosition);
    }

    private void switchToFragment(int fragmentPosition) {
        FragmentTransaction fragmentTransactionBeginTransaction = getSupportFragmentManager().beginTransaction();
        if (fragmentPosition == 0) {
            fragmentTransactionBeginTransaction.replace(R.id.fragment_container, new BackpackSceneFragment(), BackpackSceneFragment.TAG);
        } else if (fragmentPosition == 1) {
            fragmentTransactionBeginTransaction.replace(R.id.fragment_container, new BackpackSpriteFragment(), BackpackSpriteFragment.TAG);
        } else if (fragmentPosition == 2) {
            fragmentTransactionBeginTransaction.replace(R.id.fragment_container, new BackpackLookFragment(), BackpackLookFragment.TAG);
        } else if (fragmentPosition == 3) {
            fragmentTransactionBeginTransaction.replace(R.id.fragment_container, new BackpackSoundFragment(), BackpackSoundFragment.TAG);
        } else if (fragmentPosition != 4) {
            return;
        } else {
            fragmentTransactionBeginTransaction.replace(R.id.fragment_container, new BackpackScriptFragment(), BackpackScriptFragment.TAG);
        }
        fragmentTransactionBeginTransaction.commit();
    }

    @Override // org.catrobat.catroid.ui.BaseActivity, android.app.Activity
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_backpack_activity, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override // android.app.Activity
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.show_details).setVisible(((BackpackRecyclerViewFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container)).hasDetails);
        return true;
    }
}

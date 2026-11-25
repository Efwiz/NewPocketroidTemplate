package org.catrobat.catroid.ui.dialogs;

import android.R;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.reandroid.arsc.value.ResConfigBase;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.content.Script;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.StartScript;
import org.catrobat.catroid.stage.StageActivity;
import org.catrobat.catroid.ui.recyclerview.fragment.ScriptFragment;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/dialogs/RuntimeConsoleActivity.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class RuntimeConsoleActivity extends AppCompatActivity {
    private List<Sprite> availableSprites;
    private Sprite originalSprite;
    private Sprite sandboxSprite;
    private ScriptFragment scriptFragment;
    private Spinner spriteSpinner;

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(getIntent().getIntExtra(ResConfigBase.NAME_orientation, 0));
        overridePendingTransition(0, 0);
        super.onCreate(savedInstanceState);
        float f = getResources().getDisplayMetrics().density;
        this.originalSprite = ProjectManager.getInstance().getCurrentSprite();
        Sprite sprite = new Sprite("CONSOLE_SANDBOX");
        this.sandboxSprite = sprite;
        sprite.addScript(new StartScript());
        ProjectManager.getInstance().setCurrentSprite(this.sandboxSprite);
        FrameLayout frameLayout = new FrameLayout(this);
        frameLayout.setBackgroundColor(Color.parseColor("#04223F"));
        Toolbar toolbar = new Toolbar(this);
        toolbar.setBackgroundColor(Color.parseColor("#002B4D"));
        toolbar.setTitleTextColor(-1);
        toolbar.setTitle("Sandbox Console");
        setSupportActionBar(toolbar);
        if (StageActivity.getActiveStageListener() != null) {
            this.availableSprites = StageActivity.getActiveStageListener().getSpritesFromStage();
        } else {
            this.availableSprites = new ArrayList();
        }
        ArrayList arrayList = new ArrayList();
        Iterator<Sprite> it = this.availableSprites.iterator();
        while (it.hasNext()) {
            arrayList.add(it.next().getName());
        }
        this.spriteSpinner = new Spinner(this);
        this.spriteSpinner.setAdapter((SpinnerAdapter) new ArrayAdapter(this, R.layout.simple_spinner_dropdown_item, arrayList));
        Toolbar.LayoutParams layoutParams = new Toolbar.LayoutParams(-2, -2);
        layoutParams.gravity = GravityCompat.END;
        layoutParams.setMarginEnd((int) (16.0f * f));
        toolbar.addView(this.spriteSpinner, layoutParams);
        FrameLayout frameLayout2 = new FrameLayout(this);
        frameLayout2.setId(org.DanVexTeam.NewCatroidApkTemplate.R.id.fragment_container);
        LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(-1, -1);
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(1);
        linearLayout.addView(toolbar);
        linearLayout.addView(frameLayout2, layoutParams2);
        frameLayout.addView(linearLayout);
        RelativeLayout relativeLayout = new RelativeLayout(this);
        relativeLayout.setLayoutParams(new FrameLayout.LayoutParams(-1, -1));
        int i = (int) (65.0f * f);
        int i2 = (int) (12.0f * f);
        FloatingActionButton floatingActionButton = new FloatingActionButton(this);
        floatingActionButton.setId(org.DanVexTeam.NewCatroidApkTemplate.R.id.button_add);
        floatingActionButton.setCustomSize(i);
        float f2 = f * 6.0f;
        floatingActionButton.setCompatElevation(f2);
        floatingActionButton.setBackgroundTintList(ContextCompat.getColorStateList(this, org.DanVexTeam.NewCatroidApkTemplate.R.color.button_background));
        floatingActionButton.setImageDrawable(ContextCompat.getDrawable(this, org.DanVexTeam.NewCatroidApkTemplate.R.drawable.ic_plus));
        floatingActionButton.setSupportImageTintList(ContextCompat.getColorStateList(this, org.DanVexTeam.NewCatroidApkTemplate.R.color.solid_white));
        RelativeLayout.LayoutParams layoutParams3 = new RelativeLayout.LayoutParams(-2, -2);
        layoutParams3.addRule(12);
        layoutParams3.addRule(21);
        layoutParams3.setMargins(i2, i2, i2, i2);
        floatingActionButton.setLayoutParams(layoutParams3);
        floatingActionButton.setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.catroid.ui.dialogs.RuntimeConsoleActivity$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.m5673x89276e08(view);
            }
        });
        FloatingActionButton floatingActionButton2 = new FloatingActionButton(this);
        floatingActionButton2.setId(org.DanVexTeam.NewCatroidApkTemplate.R.id.button_play);
        floatingActionButton2.setCustomSize(i);
        floatingActionButton2.setCompatElevation(f2);
        floatingActionButton2.setBackgroundTintList(ContextCompat.getColorStateList(this, org.DanVexTeam.NewCatroidApkTemplate.R.color.button_background));
        floatingActionButton2.setImageDrawable(ContextCompat.getDrawable(this, org.DanVexTeam.NewCatroidApkTemplate.R.drawable.ic_play));
        floatingActionButton2.setSupportImageTintList(ContextCompat.getColorStateList(this, org.DanVexTeam.NewCatroidApkTemplate.R.color.solid_white));
        RelativeLayout.LayoutParams layoutParams4 = new RelativeLayout.LayoutParams(-2, -2);
        layoutParams4.addRule(21);
        layoutParams4.addRule(2, floatingActionButton.getId());
        layoutParams4.setMargins(i2, i2, i2, 0);
        floatingActionButton2.setLayoutParams(layoutParams4);
        floatingActionButton2.setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.catroid.ui.dialogs.RuntimeConsoleActivity$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.m5674x8a5dc0e7(view);
            }
        });
        relativeLayout.addView(floatingActionButton);
        relativeLayout.addView(floatingActionButton2);
        frameLayout.addView(relativeLayout);
        setContentView(frameLayout);
        this.scriptFragment = new ScriptFragment();
        getSupportFragmentManager().beginTransaction().replace(org.DanVexTeam.NewCatroidApkTemplate.R.id.fragment_container, this.scriptFragment, ScriptFragment.TAG).commit();
    }

    /* JADX INFO: renamed from: lambda$onCreate$0$org-catrobat-catroid-ui-dialogs-RuntimeConsoleActivity, reason: not valid java name */
    /* synthetic */ void m5673x89276e08(View view) {
        ScriptFragment scriptFragment = this.scriptFragment;
        if (scriptFragment != null) {
            scriptFragment.handleAddButton();
        }
    }

    /* JADX INFO: renamed from: lambda$onCreate$1$org-catrobat-catroid-ui-dialogs-RuntimeConsoleActivity, reason: not valid java name */
    /* synthetic */ void m5674x8a5dc0e7(View view) {
        executeScript();
    }

    private void executeScript() {
        if (this.availableSprites.isEmpty()) {
            Toast.makeText(this, "No sprites on stage!", 0).show();
            return;
        }
        int selectedItemPosition = this.spriteSpinner.getSelectedItemPosition();
        if (selectedItemPosition < 0 || selectedItemPosition >= this.availableSprites.size()) {
            return;
        }
        Sprite sprite = this.availableSprites.get(selectedItemPosition);
        boolean z = false;
        for (Script script : this.sandboxSprite.getScriptList()) {
            if (script.getBrickList() != null && !script.getBrickList().isEmpty() && StageActivity.getActiveStageListener() != null) {
                StageActivity.getActiveStageListener().executeConsoleScript(sprite, script);
                z = true;
            }
        }
        if (!z) {
            Toast.makeText(this, "Add some blocks first!", 0).show();
        } else {
            finish();
        }
    }

    @Override // android.app.Activity
    public void finish() {
        super.finish();
        overridePendingTransition(0, 0);
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
        if (this.originalSprite != null) {
            ProjectManager.getInstance().setCurrentSprite(this.originalSprite);
        }
    }
}

package org.catrobat.catroid.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import com.google.android.gms.analytics.ecommerce.Promotion;
import com.huawei.hms.support.api.entity.core.CommonCode;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.databinding.ActivityRecyclerBinding;
import org.catrobat.catroid.ui.dialogs.NewProjectDialogFragment;
import org.catrobat.catroid.ui.recyclerview.fragment.ProjectListFragment;
import org.catrobat.catroid.utils.ProjectTrashManager;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/ProjectListActivity.class
  classes14.dex
 */
/* JADX INFO: compiled from: ProjectListActivity.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 \u00102\u00020\u0001:\u0001\u0010B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0005\u001a\u00020\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\bJ\u0010\u0010\t\u001a\u00020\u00062\u0006\u0010\n\u001a\u00020\u000bH\u0002J\b\u0010\f\u001a\u00020\u0006H\u0016J\u0012\u0010\r\u001a\u00020\u00062\b\u0010\u000e\u001a\u0004\u0018\u00010\u000fH\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.¢\u0006\u0002\n\u0000¨\u0006\u0011"}, d2 = {"Lorg/catrobat/catroid/ui/ProjectListActivity;", "Lorg/catrobat/catroid/ui/BaseCastActivity;", "()V", "binding", "Lorg/catrobat/catroid/databinding/ActivityRecyclerBinding;", "handleAddButton", "", Promotion.ACTION_VIEW, "Landroid/view/View;", "loadFragment", "fragment", "Landroidx/fragment/app/Fragment;", "onBackPressed", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "Companion", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ProjectListActivity extends BaseCastActivity {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    public static final String IMPORT_LOCAL_INTENT = "merge";
    private static final String TAG;
    private ActivityRecyclerBinding binding;

    @Override // org.catrobat.catroid.ui.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityRecyclerBinding activityRecyclerBindingInflate = ActivityRecyclerBinding.inflate(getLayoutInflater());
        Intrinsics.checkNotNullExpressionValue(activityRecyclerBindingInflate, "inflate(...)");
        this.binding = activityRecyclerBindingInflate;
        ActivityRecyclerBinding activityRecyclerBinding = null;
        if (activityRecyclerBindingInflate == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityRecyclerBindingInflate = null;
        }
        setContentView(activityRecyclerBindingInflate.getRoot());
        ActivityRecyclerBinding activityRecyclerBinding2 = this.binding;
        if (activityRecyclerBinding2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
        } else {
            activityRecyclerBinding = activityRecyclerBinding2;
        }
        setSupportActionBar(activityRecyclerBinding.toolbar.toolbar);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setTitle(R.string.project_list_title);
            supportActionBar.setDisplayHomeAsUpEnabled(true);
        }
        ProjectListActivity projectListActivity = this;
        BottomBar.hidePlayButton(projectListActivity);
        BottomBar.hideAiAssistButton(projectListActivity);
        ProjectListFragment projectListFragment = new ProjectListFragment();
        if (getIntent().hasExtra("merge")) {
            BottomBar.hideAddButton(projectListActivity);
            ActionBar supportActionBar2 = getSupportActionBar();
            if (supportActionBar2 != null) {
                supportActionBar2.setTitle(R.string.import_from_project);
            }
        }
        Intent intent = getIntent();
        if (intent != null && intent.getAction() != null) {
            Bundle bundle = new Bundle();
            bundle.putParcelable(CommonCode.Resolution.HAS_RESOLUTION_FROM_APK, getIntent());
            projectListFragment.setArguments(bundle);
        }
        loadFragment(projectListFragment);
        Context applicationContext = getApplicationContext();
        Intrinsics.checkNotNullExpressionValue(applicationContext, "getApplicationContext(...)");
        ProjectTrashManager.cleanExpiredTrash(applicationContext);
    }

    private final void loadFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment, ProjectListFragment.INSTANCE.getTAG()).commit();
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }

    public final void handleAddButton(View view) {
        new NewProjectDialogFragment().show(getSupportFragmentManager(), NewProjectDialogFragment.INSTANCE.getTAG());
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/ProjectListActivity$Companion.class
      classes14.dex
     */
    /* JADX INFO: compiled from: ProjectListActivity.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0004\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u0011\u0010\u0005\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007¨\u0006\b"}, d2 = {"Lorg/catrobat/catroid/ui/ProjectListActivity$Companion;", "", "()V", "IMPORT_LOCAL_INTENT", "", "TAG", "getTAG", "()Ljava/lang/String;", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final String getTAG() {
            return ProjectListActivity.TAG;
        }
    }

    static {
        Intrinsics.checkNotNullExpressionValue("ProjectListActivity", "getSimpleName(...)");
        TAG = "ProjectListActivity";
    }
}

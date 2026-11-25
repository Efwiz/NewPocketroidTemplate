package org.catrobat.catroid.cast;

import android.content.Context;
import android.os.Bundle;
import android.view.Display;
import android.widget.RelativeLayout;
import com.google.android.gms.cast.CastPresentation;
import com.google.android.gms.cast.CastRemoteDisplayLocalService;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.utils.ToastUtil;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/cast/CastService.class
  classes12.dex
 */
/* JADX INFO: loaded from: classes12.dex */
public class CastService extends CastRemoteDisplayLocalService {
    private Display display;
    private CastPresentation presentation;

    @Override // com.google.android.gms.cast.CastRemoteDisplayLocalService
    public void onCreatePresentation(Display display) {
        createPresentation(display);
    }

    @Override // com.google.android.gms.cast.CastRemoteDisplayLocalService
    public void onDismissPresentation() {
        dismissPresentation();
    }

    private void dismissPresentation() {
        CastPresentation castPresentation = this.presentation;
        if (castPresentation != null) {
            castPresentation.dismiss();
            this.presentation = null;
        }
    }

    public void createPresentation(Display display) {
        if (display != null) {
            this.display = display;
        }
        dismissPresentation();
        FirstScreenPresentation firstScreenPresentation = new FirstScreenPresentation(this, this.display);
        this.presentation = firstScreenPresentation;
        try {
            firstScreenPresentation.show();
        } catch (Exception unused) {
            ToastUtil.showError(getApplicationContext(), getString(R.string.cast_error_not_connected_msg));
            dismissPresentation();
        }
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/cast/CastService$FirstScreenPresentation.class
      classes12.dex
     */
    public class FirstScreenPresentation extends CastPresentation {
        public FirstScreenPresentation(Context serviceContext, Display display) {
            super(serviceContext, display);
        }

        @Override // android.app.Dialog
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            RelativeLayout relativeLayout = new RelativeLayout(CastService.this.getApplication());
            setContentView(relativeLayout);
            CastManager.getInstance().setIsConnected(true);
            CastManager.getInstance().setRemoteLayout(relativeLayout);
            CastManager.getInstance().setRemoteLayoutToIdleScreen(CastService.this.getApplicationContext());
        }
    }
}

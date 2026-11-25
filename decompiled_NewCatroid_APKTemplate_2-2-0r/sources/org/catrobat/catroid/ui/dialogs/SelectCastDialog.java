package org.catrobat.catroid.ui.dialogs;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.mediarouter.media.MediaRouter;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.cast.CastManager;
import org.jetbrains.kotlin.konan.util.DependencyDownloader;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/dialogs/SelectCastDialog.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class SelectCastDialog extends DialogFragment {
    public static final String TAG = "SelectCastDialog";

    @Override // androidx.fragment.app.DialogFragment
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final ArrayAdapter<MediaRouter.RouteInfo> deviceAdapter = CastManager.getInstance().getDeviceAdapter();
        if (CastManager.getInstance().currentlyConnecting() || CastManager.getInstance().isConnected()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setMessage(getString(R.string.cast_ready_to_cast) + " " + CastManager.getInstance().getSelectedDevice().getFriendlyName());
            builder.setPositiveButton(R.string.disconnect, new DialogInterface.OnClickListener() { // from class: org.catrobat.catroid.ui.dialogs.SelectCastDialog.1
                @Override // android.content.DialogInterface.OnClickListener
                public void onClick(DialogInterface dialogInterface, int which) {
                    synchronized (this) {
                        CastManager.getInstance().getMediaRouter().unselect(2);
                    }
                }
            });
            return builder.create();
        }
        View viewInflate = View.inflate(getActivity(), R.layout.dialog_select_cast, null);
        ListView listView = (ListView) viewInflate.findViewById(R.id.cast_device_list_view);
        listView.setEmptyView(viewInflate.findViewById(R.id.empty_view_item));
        listView.setAdapter((ListAdapter) deviceAdapter);
        listView.setDivider(null);
        final AlertDialog alertDialogCreate = new AlertDialog.Builder(getContext()).setTitle(getString(R.string.cast_device_selector_dialog_title)).setView(viewInflate).create();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: org.catrobat.catroid.ui.dialogs.SelectCastDialog.2
            @Override // android.widget.AdapterView.OnItemClickListener
            public void onItemClick(AdapterView<?> parent, View view, int position, long id2) {
                synchronized (this) {
                    MediaRouter.RouteInfo routeInfo = CastManager.getInstance().getRouteInfos().get(position);
                    CastManager.getInstance().startCastButtonAnimation();
                    CastManager.getInstance().selectRoute(routeInfo);
                    alertDialogCreate.dismiss();
                }
            }
        });
        new Handler().postDelayed(new Runnable() { // from class: org.catrobat.catroid.ui.dialogs.SelectCastDialog.3
            @Override // java.lang.Runnable
            public void run() {
                synchronized (this) {
                    AlertDialog alertDialog = alertDialogCreate;
                    if (alertDialog != null && alertDialog.isShowing() && deviceAdapter.isEmpty()) {
                        ((TextView) alertDialogCreate.findViewById(R.id.cast_searching_for_cast_text_view)).setText(SelectCastDialog.this.getString(R.string.cast_searching_for_cast_devices) + SelectCastDialog.this.getString(R.string.cast_trouble_finding_devices_tip));
                    }
                }
            }
        }, DependencyDownloader.DEFAULT_ATTEMPT_INTERVAL_MS);
        return alertDialogCreate;
    }
}

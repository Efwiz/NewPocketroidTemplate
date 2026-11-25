package org.catrobat.catroid.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.mediarouter.media.MediaRouter;
import java.util.List;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.cast.CastManager;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/adapter/CastDevicesAdapter.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class CastDevicesAdapter extends ArrayAdapter<MediaRouter.RouteInfo> {
    public CastDevicesAdapter(Context context, int resource, List<MediaRouter.RouteInfo> items) {
        super(context, resource, items);
    }

    @Override // android.widget.ArrayAdapter, android.widget.Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_cast_device_list_item, parent, false);
        }
        ((TextView) convertView.findViewById(R.id.cast_device_name)).setText(CastManager.getInstance().getRouteInfos().get(position).getName());
        return convertView;
    }
}

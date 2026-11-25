package com.google.android.gms.cast.framework.media;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.TextView;
import com.google.android.gms.cast.MediaTrack;
import com.google.android.gms.cast.framework.R;
import com.google.android.gms.common.internal.Preconditions;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/* JADX INFO: compiled from: com.google.android.gms:play-services-cast-framework@@21.4.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzbu extends ArrayAdapter implements View.OnClickListener {
    private final Context zza;
    private int zzb;

    public zzbu(Context context, List list, int i) {
        super(context, R.layout.cast_tracks_chooser_dialog_row_layout, list == null ? new ArrayList() : list);
        this.zza = context;
        this.zzb = i;
    }

    /* JADX WARN: Code duplicated, block: B:18:0x008d  */
    @Override // android.widget.ArrayAdapter, android.widget.Adapter
    public final View getView(int i, View view, ViewGroup viewGroup) {
        zzbt zzbtVar;
        if (view == null) {
            view = ((LayoutInflater) Preconditions.checkNotNull((LayoutInflater) this.zza.getSystemService("layout_inflater"))).inflate(R.layout.cast_tracks_chooser_dialog_row_layout, viewGroup, false);
            zzbtVar = new zzbt(this, (TextView) view.findViewById(R.id.text), (RadioButton) view.findViewById(R.id.radio), null);
            view.setTag(zzbtVar);
        } else {
            zzbtVar = (zzbt) Preconditions.checkNotNull((zzbt) view.getTag());
        }
        zzbtVar.zzb.setTag(Integer.valueOf(i));
        zzbtVar.zzb.setChecked(this.zzb == i);
        view.setOnClickListener(this);
        MediaTrack mediaTrack = (MediaTrack) Preconditions.checkNotNull((MediaTrack) getItem(i));
        String name2 = mediaTrack.getName();
        Locale languageLocale = mediaTrack.getLanguageLocale();
        if (TextUtils.isEmpty(name2)) {
            if (mediaTrack.getSubtype() == 2) {
                name2 = this.zza.getString(R.string.cast_tracks_chooser_dialog_closed_captions);
            } else if (languageLocale != null) {
                name2 = languageLocale.getDisplayLanguage();
                if (TextUtils.isEmpty(name2)) {
                    name2 = this.zza.getString(R.string.cast_tracks_chooser_dialog_default_track_name, Integer.valueOf(i + 1));
                }
            } else {
                name2 = this.zza.getString(R.string.cast_tracks_chooser_dialog_default_track_name, Integer.valueOf(i + 1));
            }
        }
        zzbtVar.zza.setText(name2);
        return view;
    }

    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
        this.zzb = ((Integer) Preconditions.checkNotNull(((zzbt) Preconditions.checkNotNull((zzbt) view.getTag())).zzb.getTag())).intValue();
        notifyDataSetChanged();
    }

    public final MediaTrack zza() {
        int i = this.zzb;
        if (i < 0 || i >= getCount()) {
            return null;
        }
        return (MediaTrack) getItem(this.zzb);
    }
}

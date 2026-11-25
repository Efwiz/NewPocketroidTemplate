package org.catrobat.catroid.ui.recyclerview.adapter;

import android.content.Context;
import android.widget.TextView;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.StringCompanionObject;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.common.LookData;
import org.catrobat.catroid.ui.recyclerview.viewholder.ExtendedViewHolder;
import org.catrobat.catroid.utils.FileMetaDataExtractor;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/recyclerview/adapter/LookAdapter.class
  classes14.dex
 */
/* JADX INFO: compiled from: LookAdapter.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\u0018\u00002\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u0001B\u0017\u0012\u0010\u0010\u0003\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u0002\u0018\u00010\u0004¢\u0006\u0002\u0010\u0005J\u0018\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016¨\u0006\f"}, d2 = {"Lorg/catrobat/catroid/ui/recyclerview/adapter/LookAdapter;", "Lorg/catrobat/catroid/ui/recyclerview/adapter/ExtendedRVAdapter;", "Lorg/catrobat/catroid/common/LookData;", FirebaseAnalytics.Param.ITEMS, "", "(Ljava/util/List;)V", "onBindViewHolder", "", "holder", "Lorg/catrobat/catroid/ui/recyclerview/viewholder/ExtendedViewHolder;", "position", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class LookAdapter extends ExtendedRVAdapter<LookData> {
    public LookAdapter(List<? extends LookData> list) {
        super(list);
    }

    @Override // org.catrobat.catroid.ui.recyclerview.adapter.ExtendedRVAdapter
    public void onBindViewHolder(ExtendedViewHolder holder, int position) {
        Intrinsics.checkNotNullParameter(holder, "holder");
        LookData lookData = (LookData) this.items.get(position);
        holder.title.setText(lookData != null ? lookData.f512name : null);
        holder.image.setImageBitmap(lookData != null ? lookData.getThumbnailBitmap() : null);
        if (this.showDetails) {
            int[] measure = lookData != null ? lookData.getMeasure() : null;
            String str = (measure != null ? Integer.valueOf(measure[0]) : null) + " x " + (measure != null ? Integer.valueOf(measure[1]) : null);
            Context context = holder.itemView.getContext();
            TextView textView = holder.details;
            StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
            Locale locale = Locale.getDefault();
            String string = context.getString(R.string.look_details);
            Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
            String str2 = String.format(locale, string, Arrays.copyOf(new Object[]{str, FileMetaDataExtractor.getSizeAsString(lookData != null ? lookData.getFile() : null, context)}, 2));
            Intrinsics.checkNotNullExpressionValue(str2, "format(...)");
            textView.setText(str2);
            holder.details.setVisibility(0);
            return;
        }
        holder.details.setVisibility(8);
    }
}

package org.catrobat.catroid.ui.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/adapter/BrickCategoryAdapter.class
  classes14.dex
 */
/* JADX INFO: compiled from: BrickCategoryAdapter.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0013\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\u0002\u0010\u0005J\b\u0010\u0006\u001a\u00020\u0007H\u0016J\u0010\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u0007H\u0016J\u0010\u0010\u000b\u001a\u00020\f2\u0006\u0010\n\u001a\u00020\u0007H\u0016J\"\u0010\r\u001a\u00020\u00042\u0006\u0010\n\u001a\u00020\u00072\b\u0010\u000e\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u000f\u001a\u00020\u0010H\u0016R\u0014\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0011"}, d2 = {"Lorg/catrobat/catroid/ui/adapter/BrickCategoryAdapter;", "Landroid/widget/BaseAdapter;", "categories", "", "Landroid/view/View;", "(Ljava/util/List;)V", "getCount", "", "getItem", "", "position", "getItemId", "", "getView", "convertView", "parent", "Landroid/view/ViewGroup;", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class BrickCategoryAdapter extends BaseAdapter {
    private final List<View> categories;

    @Override // android.widget.Adapter
    public long getItemId(int position) {
        return position;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public BrickCategoryAdapter(List<? extends View> categories) {
        Intrinsics.checkNotNullParameter(categories, "categories");
        this.categories = categories;
    }

    @Override // android.widget.Adapter
    public int getCount() {
        return this.categories.size();
    }

    @Override // android.widget.Adapter
    public String getItem(int position) {
        View view = this.categories.get(position);
        Intrinsics.checkNotNull(view, "null cannot be cast to non-null type android.widget.LinearLayout");
        View childAt = ((LinearLayout) view).getChildAt(1);
        Intrinsics.checkNotNull(childAt, "null cannot be cast to non-null type android.widget.TextView");
        return ((TextView) childAt).getText().toString();
    }

    @Override // android.widget.Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        Intrinsics.checkNotNullParameter(parent, "parent");
        return this.categories.get(position);
    }
}

package org.catrobat.catroid.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.animation.DecelerateInterpolator;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.catroid.content.bricks.Brick;
import org.catrobat.catroid.content.bricks.SubCategoryHeaderBrick;
import org.catrobat.catroid.ui.recyclerview.util.IndentedBrickLayout;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/adapter/PrototypeBrickAdapter.class
  classes14.dex
 */
/* JADX INFO: compiled from: PrototypeBrickAdapter.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0010\t\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\"\n\u0000\u0018\u00002\u00020\u0001B\u0013\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\u0002\u0010\u0005J\u0006\u0010\u0013\u001a\u00020\u0014J\u0010\u0010\u0015\u001a\u00020\u00112\u0006\u0010\u0016\u001a\u00020\u0011H\u0002J\b\u0010\u0017\u001a\u00020\u0011H\u0016J\u0010\u0010\u0018\u001a\u00020\u00042\u0006\u0010\u0016\u001a\u00020\u0011H\u0016J\u0010\u0010\u0019\u001a\u00020\u000e2\u0006\u0010\u0016\u001a\u00020\u0011H\u0016J&\u0010\u001a\u001a\u0004\u0018\u00010\u00122\u0006\u0010\u0016\u001a\u00020\u00112\b\u0010\u001b\u001a\u0004\u0018\u00010\u00122\b\u0010\u001c\u001a\u0004\u0018\u00010\u001dH\u0016J\b\u0010\u001e\u001a\u00020\u0007H\u0016J$\u0010\u001f\u001a\u00020\u00142\f\u0010 \u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\u000e\b\u0002\u0010!\u001a\b\u0012\u0004\u0012\u00020\u00040\"R\u0014\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003X\u0082\u000e¢\u0006\u0002\n\u0000R$\u0010\b\u001a\u00020\u00072\u0006\u0010\u0006\u001a\u00020\u0007@FX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\b\u0010\t\"\u0004\b\n\u0010\u000bR*\u0010\f\u001a\u001e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u000e0\rj\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u000e`\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R*\u0010\u0010\u001a\u001e\u0012\u0004\u0012\u00020\u0011\u0012\u0004\u0012\u00020\u00120\rj\u000e\u0012\u0004\u0012\u00020\u0011\u0012\u0004\u0012\u00020\u0012`\u000fX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006#"}, d2 = {"Lorg/catrobat/catroid/ui/adapter/PrototypeBrickAdapter;", "Landroid/widget/BaseAdapter;", "brickList", "", "Lorg/catrobat/catroid/content/bricks/Brick;", "(Ljava/util/List;)V", "value", "", "isScrolling", "()Z", "setScrolling", "(Z)V", "itemsToAnimate", "Ljava/util/HashMap;", "", "Lkotlin/collections/HashMap;", "viewCache", "", "Landroid/view/View;", "clearCache", "", "getBrickDepth", "position", "getCount", "getItem", "getItemId", "getView", "convertView", "parent", "Landroid/view/ViewGroup;", "hasStableIds", "replaceList", "newList", "visibleBricksBefore", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class PrototypeBrickAdapter extends BaseAdapter {
    private List<? extends Brick> brickList;
    private boolean isScrolling;
    private final HashMap<Brick, Long> itemsToAnimate;
    private final HashMap<Integer, View> viewCache;

    @Override // android.widget.BaseAdapter, android.widget.Adapter
    public boolean hasStableIds() {
        return true;
    }

    public PrototypeBrickAdapter(List<? extends Brick> brickList) {
        Intrinsics.checkNotNullParameter(brickList, "brickList");
        this.brickList = brickList;
        this.viewCache = new HashMap<>();
        this.itemsToAnimate = new HashMap<>();
        Iterator<? extends Brick> it = this.brickList.iterator();
        int i = 0;
        while (it.hasNext()) {
            this.itemsToAnimate.put(it.next(), Long.valueOf(Math.min(((long) i) * 30, 250L)));
            i++;
        }
    }

    /* JADX INFO: renamed from: isScrolling, reason: from getter */
    public final boolean getIsScrolling() {
        return this.isScrolling;
    }

    public final void setScrolling(boolean z) {
        if (this.isScrolling != z) {
            this.isScrolling = z;
            if (z) {
                this.itemsToAnimate.clear();
            }
        }
    }

    @Override // android.widget.Adapter
    public int getCount() {
        return this.brickList.size();
    }

    @Override // android.widget.Adapter
    public Brick getItem(int position) {
        return this.brickList.get(position);
    }

    @Override // android.widget.Adapter
    public long getItemId(int position) {
        return this.brickList.get(position).hashCode();
    }

    @Override // android.widget.Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        int brickDepth;
        Brick brick = this.brickList.get(position);
        int iHashCode = brick.hashCode();
        View prototypeView = this.viewCache.get(Integer.valueOf(iHashCode));
        if (prototypeView == null) {
            prototypeView = brick.getPrototypeView(parent != null ? parent.getContext() : null);
            if (prototypeView != null) {
                this.viewCache.put(Integer.valueOf(iHashCode), prototypeView);
            }
        } else {
            ViewParent parent2 = prototypeView.getParent();
            ViewGroup viewGroup = parent2 instanceof ViewGroup ? (ViewGroup) parent2 : null;
            if (viewGroup != null && !(viewGroup instanceof AdapterView)) {
                viewGroup.removeView(prototypeView);
            }
        }
        if (prototypeView != null) {
            if (!this.isScrolling && this.itemsToAnimate.containsKey(brick)) {
                Long lRemove = this.itemsToAnimate.remove(brick);
                if (lRemove == null) {
                    lRemove = 0L;
                }
                long jLongValue = lRemove.longValue();
                prototypeView.setAlpha(0.0f);
                prototypeView.setScaleX(0.93f);
                prototypeView.setScaleY(0.93f);
                prototypeView.animate().alpha(1.0f).scaleX(1.0f).scaleY(1.0f).setStartDelay(jLongValue).setDuration(200L).setInterpolator(new DecelerateInterpolator()).start();
            } else {
                prototypeView.animate().cancel();
                prototypeView.setAlpha(1.0f);
                prototypeView.setScaleX(1.0f);
                prototypeView.setScaleY(1.0f);
                prototypeView.setTranslationX(0.0f);
                prototypeView.setTranslationY(0.0f);
                this.itemsToAnimate.remove(brick);
            }
        }
        Context context = parent != null ? parent.getContext() : null;
        if (context == null) {
            return prototypeView;
        }
        if (prototypeView == null || (brickDepth = getBrickDepth(position)) <= 0) {
            if (prototypeView != null) {
                ViewParent parent3 = prototypeView.getParent();
                if (parent3 instanceof IndentedBrickLayout) {
                    ((IndentedBrickLayout) parent3).removeView(prototypeView);
                }
            }
            return prototypeView;
        }
        Object parent4 = prototypeView.getParent();
        if (parent4 instanceof IndentedBrickLayout) {
            ((IndentedBrickLayout) parent4).setDepth(brickDepth);
            return (View) parent4;
        }
        ViewGroup viewGroup2 = parent4 instanceof ViewGroup ? (ViewGroup) parent4 : null;
        if (viewGroup2 != null && !(viewGroup2 instanceof AdapterView)) {
            viewGroup2.removeView(prototypeView);
        }
        IndentedBrickLayout indentedBrickLayout = new IndentedBrickLayout(context, brickDepth);
        indentedBrickLayout.setLayoutParams(new ViewGroup.LayoutParams(-1, -2));
        indentedBrickLayout.addView(prototypeView, new LinearLayout.LayoutParams(-1, -2));
        return indentedBrickLayout;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ void replaceList$default(PrototypeBrickAdapter prototypeBrickAdapter, List list, Set set, int i, Object obj) {
        if ((i & 2) != 0) {
            set = SetsKt.emptySet();
        }
        prototypeBrickAdapter.replaceList(list, set);
    }

    public final void replaceList(List<? extends Brick> newList, Set<? extends Brick> visibleBricksBefore) {
        Intrinsics.checkNotNullParameter(newList, "newList");
        Intrinsics.checkNotNullParameter(visibleBricksBefore, "visibleBricksBefore");
        this.itemsToAnimate.clear();
        if (!this.isScrolling) {
            int i = 0;
            for (Brick brick : newList) {
                if (!visibleBricksBefore.contains(brick)) {
                    this.itemsToAnimate.put(brick, Long.valueOf(Math.min(((long) i) * 30, 250L)));
                    i++;
                }
            }
        }
        this.brickList = newList;
        notifyDataSetChanged();
    }

    public final void clearCache() {
        this.viewCache.clear();
        this.itemsToAnimate.clear();
    }

    private final int getBrickDepth(int position) {
        if (this.brickList.get(position) instanceof SubCategoryHeaderBrick) {
            return 0;
        }
        for (int i = position - 1; -1 < i; i--) {
            if (this.brickList.get(i) instanceof SubCategoryHeaderBrick) {
                return 1;
            }
        }
        return 0;
    }
}

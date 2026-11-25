package org.catrobat.catroid.ui.dragndrop;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.List;
import kotlin.Metadata;
import org.catrobat.catroid.content.bricks.Brick;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/dragndrop/BrickAdapterInterface.class
  classes14.dex
 */
/* JADX INFO: compiled from: BrickAdapterInterface.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\b\u0004\bf\u0018\u00002\u00020\u0001J\u0012\u0010\u0002\u001a\u0004\u0018\u00010\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&J\u0012\u0010\u0006\u001a\u00020\u00052\b\u0010\u0007\u001a\u0004\u0018\u00010\u0003H&J\u001a\u0010\b\u001a\u00020\t2\u0006\u0010\u0004\u001a\u00020\u00052\b\u0010\n\u001a\u0004\u0018\u00010\u0003H&J\u0018\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u00052\u0006\u0010\u000e\u001a\u00020\u0005H&J\u0016\u0010\u000f\u001a\u00020\f2\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00030\u0011H&J\b\u0010\u0012\u001a\u00020\tH&J\u0018\u0010\u0013\u001a\u00020\t2\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0014\u001a\u00020\fH&¨\u0006\u0015"}, d2 = {"Lorg/catrobat/catroid/ui/dragndrop/BrickAdapterInterface;", "", "getItem", "Lorg/catrobat/catroid/content/bricks/Brick;", "position", "", "getPosition", "brick", "moveItemTo", "", "brickToMove", "onItemMove", "", "fromPosition", "toPosition", "removeItems", FirebaseAnalytics.Param.ITEMS, "", "setAllPositionsVisible", "setItemVisible", "visible", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface BrickAdapterInterface {
    Brick getItem(int position);

    int getPosition(Brick brick);

    void moveItemTo(int position, Brick brickToMove);

    boolean onItemMove(int fromPosition, int toPosition);

    boolean removeItems(List<? extends Brick> items);

    void setAllPositionsVisible();

    void setItemVisible(int position, boolean visible);
}

package org.catrobat.catroid.utils.git.merger;

import java.util.List;
import kotlin.Metadata;
import org.catrobat.catroid.content.bricks.Brick;
import org.catrobat.catroid.utils.git.Conflict;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/utils/git/merger/BrickComparator.class
  classes14.dex
 */
/* JADX INFO: compiled from: BrickComparator.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J.\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010\u0007\u001a\u00020\u00052\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\tH&¨\u0006\u000b"}, d2 = {"Lorg/catrobat/catroid/utils/git/merger/BrickComparator;", "", "compare", "", "mergedBrick", "Lorg/catrobat/catroid/content/bricks/Brick;", "baseBrick", "remoteBrick", "conflicts", "", "Lorg/catrobat/catroid/utils/git/Conflict;", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface BrickComparator {
    void compare(Brick mergedBrick, Brick baseBrick, Brick remoteBrick, List<Conflict> conflicts);
}

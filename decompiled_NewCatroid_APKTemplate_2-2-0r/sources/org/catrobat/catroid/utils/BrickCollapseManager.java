package org.catrobat.catroid.utils;

import java.util.HashSet;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.catroid.content.bricks.Brick;
import org.jetbrains.kotlin.org.jline.reader.LineReader;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/utils/BrickCollapseManager.class
  classes14.dex
 */
/* JADX INFO: compiled from: BrickCollapseManager.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0006\u0010\u0007\u001a\u00020\bJ\u000e\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fJ\u0016\u0010\r\u001a\u00020\b2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\u000e\u001a\u00020\nJ\u000e\u0010\u000f\u001a\u00020\b2\u0006\u0010\u000b\u001a\u00020\fR\u001e\u0010\u0003\u001a\u0012\u0012\u0004\u0012\u00020\u00050\u0004j\b\u0012\u0004\u0012\u00020\u0005`\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0010"}, d2 = {"Lorg/catrobat/catroid/utils/BrickCollapseManager;", "", "()V", "collapsedBricks", "Ljava/util/HashSet;", "", "Lkotlin/collections/HashSet;", LineReader.CLEAR, "", "isCollapsed", "", "brick", "Lorg/catrobat/catroid/content/bricks/Brick;", "setCollapsed", "collapsed", "toggleCollapsed", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class BrickCollapseManager {
    public static final BrickCollapseManager INSTANCE = new BrickCollapseManager();
    private static final HashSet<Integer> collapsedBricks = new HashSet<>();

    private BrickCollapseManager() {
    }

    public final boolean isCollapsed(Brick brick) {
        Intrinsics.checkNotNullParameter(brick, "brick");
        return collapsedBricks.contains(Integer.valueOf(brick.hashCode()));
    }

    public final void setCollapsed(Brick brick, boolean collapsed) {
        Intrinsics.checkNotNullParameter(brick, "brick");
        if (collapsed) {
            collapsedBricks.add(Integer.valueOf(brick.hashCode()));
        } else {
            collapsedBricks.remove(Integer.valueOf(brick.hashCode()));
        }
    }

    public final void toggleCollapsed(Brick brick) {
        Intrinsics.checkNotNullParameter(brick, "brick");
        setCollapsed(brick, !isCollapsed(brick));
    }

    public final void clear() {
        collapsedBricks.clear();
    }
}

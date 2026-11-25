package org.catrobat.catroid.ui.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.catrobat.catroid.common.Constants;
import org.catrobat.catroid.common.FlavoredConstants;
import org.catrobat.catroid.common.RecentBricksHolder;
import org.catrobat.catroid.content.bricks.Brick;
import org.catrobat.catroid.content.bricks.ComeToFrontBrick;
import org.catrobat.catroid.content.bricks.GoNStepsBackBrick;
import org.catrobat.catroid.content.bricks.IfOnEdgeBounceBrick;
import org.catrobat.catroid.content.bricks.LookRequestBrick;
import org.catrobat.catroid.content.bricks.PenDownBrick;
import org.catrobat.catroid.content.bricks.PenUpBrick;
import org.catrobat.catroid.content.bricks.SayBubbleBrick;
import org.catrobat.catroid.content.bricks.SayForBubbleBrick;
import org.catrobat.catroid.content.bricks.SetLookBrick;
import org.catrobat.catroid.content.bricks.SetLookByIndexBrick;
import org.catrobat.catroid.content.bricks.SetPenColorBrick;
import org.catrobat.catroid.content.bricks.SetPenSizeBrick;
import org.catrobat.catroid.content.bricks.StampBrick;
import org.catrobat.catroid.content.bricks.ThinkBubbleBrick;
import org.catrobat.catroid.content.bricks.ThinkForBubbleBrick;
import org.catrobat.catroid.content.bricks.WhenBounceOffBrick;
import org.catrobat.catroid.io.RecentBrickListSerializer;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/controller/RecentBrickListManager.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class RecentBrickListManager {
    private static final RecentBrickListManager INSTANCE = new RecentBrickListManager();
    private final Class[] nonBackgroundSpriteClasses = {WhenBounceOffBrick.class, IfOnEdgeBounceBrick.class, GoNStepsBackBrick.class, ComeToFrontBrick.class, SetLookBrick.class, SetLookByIndexBrick.class, SayBubbleBrick.class, SayForBubbleBrick.class, ThinkBubbleBrick.class, ThinkForBubbleBrick.class, LookRequestBrick.class, PenDownBrick.class, PenUpBrick.class, SetPenSizeBrick.class, SetPenColorBrick.class, StampBrick.class};
    public final File recentBrickListDirectory;
    public final File recentBricksFile;
    private RecentBricksHolder recentBricksHolder;
    private final RecentBrickListSerializer recentBricksSerializer;

    public static RecentBrickListManager getInstance() {
        return INSTANCE;
    }

    private RecentBrickListManager() {
        File file = new File(FlavoredConstants.DEFAULT_ROOT_DIRECTORY, Constants.RECENT_BRICKS_DIRECTORY);
        this.recentBrickListDirectory = file;
        File file2 = new File(file, Constants.RECENT_BRICKS_FILE);
        this.recentBricksFile = file2;
        this.recentBricksHolder = new RecentBricksHolder();
        this.recentBricksSerializer = new RecentBrickListSerializer(file2);
        createRecentbrickDirectories();
    }

    private void createRecentbrickDirectories() {
        FlavoredConstants.DEFAULT_ROOT_DIRECTORY.mkdir();
        this.recentBrickListDirectory.mkdir();
    }

    boolean isNotBackgroundSpriteBrick(Brick brick) {
        if (brick == null) {
            return false;
        }
        for (Class cls : this.nonBackgroundSpriteClasses) {
            if (brick.getClass().equals(cls)) {
                return true;
            }
        }
        return false;
    }

    public List<Brick> getRecentBricks(boolean isBackgroundSprite) {
        List<Brick> recentBricks = this.recentBricksHolder.getRecentBricks();
        ArrayList arrayList = new ArrayList();
        if (isBackgroundSprite) {
            for (Brick brick : recentBricks) {
                if (brick != null && !isNotBackgroundSpriteBrick(brick)) {
                    arrayList.add(brick);
                }
            }
        } else {
            for (Brick brick2 : recentBricks) {
                if (brick2 != null) {
                    arrayList.add(brick2);
                }
            }
        }
        return arrayList;
    }

    public void addBrick(Brick brick) {
        if (brick == null) {
            return;
        }
        int iFind = this.recentBricksHolder.find(brick);
        if (iFind >= 0) {
            this.recentBricksHolder.remove(iFind);
        } else if (this.recentBricksHolder.size() == 10) {
            this.recentBricksHolder.remove();
        }
        this.recentBricksHolder.insert(brick);
    }

    public void saveRecentBrickList() {
        this.recentBricksSerializer.saveRecentBricks(this.recentBricksHolder);
    }

    public void loadRecentBricks() {
        this.recentBricksHolder = this.recentBricksSerializer.loadRecentBricks();
    }
}

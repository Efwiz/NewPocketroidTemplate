package org.catrobat.catroid.ui.fragment;

import android.content.Context;
import java.util.ArrayList;
import java.util.List;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.content.BroadcastScript;
import org.catrobat.catroid.content.bricks.AskBrick;
import org.catrobat.catroid.content.bricks.Brick;
import org.catrobat.catroid.content.bricks.BroadcastBrick;
import org.catrobat.catroid.content.bricks.BroadcastReceiverBrick;
import org.catrobat.catroid.content.bricks.ChangeColorByNBrick;
import org.catrobat.catroid.content.bricks.ChangeHeightBrick;
import org.catrobat.catroid.content.bricks.ChangeSizeByNBrick;
import org.catrobat.catroid.content.bricks.ChangeVariableBrick;
import org.catrobat.catroid.content.bricks.CloneBrick;
import org.catrobat.catroid.content.bricks.DeleteThisCloneBrick;
import org.catrobat.catroid.content.bricks.ForeverBrick;
import org.catrobat.catroid.content.bricks.GlideToBrick;
import org.catrobat.catroid.content.bricks.GoToBrick;
import org.catrobat.catroid.content.bricks.HideBrick;
import org.catrobat.catroid.content.bricks.HideTextBrick;
import org.catrobat.catroid.content.bricks.IfOnEdgeBounceBrick;
import org.catrobat.catroid.content.bricks.MoveNStepsBrick;
import org.catrobat.catroid.content.bricks.NextLookBrick;
import org.catrobat.catroid.content.bricks.PlaceAtBrick;
import org.catrobat.catroid.content.bricks.PlaySoundBrick;
import org.catrobat.catroid.content.bricks.PreviousLookBrick;
import org.catrobat.catroid.content.bricks.SayBubbleBrick;
import org.catrobat.catroid.content.bricks.SayForBubbleBrick;
import org.catrobat.catroid.content.bricks.SetBackgroundBrick;
import org.catrobat.catroid.content.bricks.SetColorBrick;
import org.catrobat.catroid.content.bricks.SetHeightBrick;
import org.catrobat.catroid.content.bricks.SetLookBrick;
import org.catrobat.catroid.content.bricks.SetSizeToBrick;
import org.catrobat.catroid.content.bricks.SetVariableBrick;
import org.catrobat.catroid.content.bricks.SetWidthBrick;
import org.catrobat.catroid.content.bricks.ShowBrick;
import org.catrobat.catroid.content.bricks.ShowTextBrick;
import org.catrobat.catroid.content.bricks.SpeakBrick;
import org.catrobat.catroid.content.bricks.StopAllSoundsBrick;
import org.catrobat.catroid.content.bricks.ThinkBubbleBrick;
import org.catrobat.catroid.content.bricks.ThinkForBubbleBrick;
import org.catrobat.catroid.content.bricks.TurnLeftBrick;
import org.catrobat.catroid.content.bricks.TurnRightBrick;
import org.catrobat.catroid.content.bricks.WaitBrick;
import org.catrobat.catroid.content.bricks.WhenBackPressedBrick;
import org.catrobat.catroid.content.bricks.WhenClonedBrick;
import org.catrobat.catroid.content.bricks.WhenMouseButtonClickedBrick;
import org.catrobat.catroid.content.bricks.WhenMouseWheelScrolledBrick;
import org.catrobat.catroid.content.bricks.WhenProjectExitsBrick;
import org.catrobat.catroid.content.bricks.WhenStartedBrick;
import org.catrobat.catroid.content.bricks.WhenTouchDownBrick;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/fragment/CategoryBeginnerBricksFactory.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class CategoryBeginnerBricksFactory extends CategoryBricksFactory {
    @Override // org.catrobat.catroid.ui.fragment.CategoryBricksFactory
    protected List<Brick> setupEventCategoryList(Context context, boolean isBackgroundSprite) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new WhenStartedBrick());
        arrayList.add(new WhenTouchDownBrick());
        List<String> broadcastMessages = ProjectManager.getInstance().getCurrentProject().getBroadcastMessageContainer().getBroadcastMessages();
        String string = context.getString(R.string.brick_broadcast_default_value);
        if (broadcastMessages.size() > 0) {
            string = broadcastMessages.get(0);
        }
        arrayList.add(new BroadcastReceiverBrick(new BroadcastScript(string)));
        arrayList.add(new BroadcastBrick(string));
        arrayList.add(new WhenProjectExitsBrick());
        arrayList.add(new WhenBackPressedBrick());
        arrayList.add(new WhenMouseButtonClickedBrick());
        arrayList.add(new WhenMouseWheelScrolledBrick());
        return arrayList;
    }

    @Override // org.catrobat.catroid.ui.fragment.CategoryBricksFactory
    protected List<Brick> setupControlCategoryList(Context context) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new WaitBrick(1000));
        arrayList.add(new ForeverBrick());
        arrayList.add(new CloneBrick());
        arrayList.add(new DeleteThisCloneBrick());
        arrayList.add(new WhenClonedBrick());
        return arrayList;
    }

    @Override // org.catrobat.catroid.ui.fragment.CategoryBricksFactory
    protected List<Brick> setupMotionCategoryList(Context context, boolean isBackgroundSprite) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new PlaceAtBrick(100, 200));
        arrayList.add(new GoToBrick(null));
        if (!isBackgroundSprite) {
            arrayList.add(new IfOnEdgeBounceBrick());
        }
        arrayList.add(new MoveNStepsBrick(10.0d));
        arrayList.add(new TurnLeftBrick(15.0d));
        arrayList.add(new TurnRightBrick(15.0d));
        arrayList.add(new GlideToBrick(100, 200, 1000));
        return arrayList;
    }

    @Override // org.catrobat.catroid.ui.fragment.CategoryBricksFactory
    protected List<Brick> setupSoundCategoryList(Context context) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new PlaySoundBrick());
        arrayList.add(new StopAllSoundsBrick());
        arrayList.add(new SpeakBrick(context.getString(R.string.brick_speak_default_value)));
        return arrayList;
    }

    @Override // org.catrobat.catroid.ui.fragment.CategoryBricksFactory
    protected List<Brick> setupLooksCategoryList(Context context, boolean isBackgroundSprite) {
        ArrayList arrayList = new ArrayList();
        if (!isBackgroundSprite) {
            arrayList.add(new SetLookBrick());
        }
        arrayList.add(new NextLookBrick());
        arrayList.add(new PreviousLookBrick());
        arrayList.add(new SetSizeToBrick(60.0d));
        arrayList.add(new SetWidthBrick(75.0d));
        arrayList.add(new SetHeightBrick(75.0d));
        arrayList.add(new ChangeHeightBrick(75.0d));
        arrayList.add(new ChangeHeightBrick(75.0d));
        arrayList.add(new ChangeSizeByNBrick(10.0d));
        arrayList.add(new HideBrick());
        arrayList.add(new ShowBrick());
        arrayList.add(new AskBrick(context.getString(R.string.brick_ask_default_question)));
        if (!isBackgroundSprite) {
            arrayList.add(new SayBubbleBrick(context.getString(R.string.brick_say_bubble_default_value)));
            arrayList.add(new SayForBubbleBrick(context.getString(R.string.brick_say_bubble_default_value), 1.0f));
            arrayList.add(new ThinkBubbleBrick(context.getString(R.string.brick_think_bubble_default_value)));
            arrayList.add(new ThinkForBubbleBrick(context.getString(R.string.brick_think_bubble_default_value), 1.0f));
        }
        arrayList.add(new SetColorBrick(0.0d));
        arrayList.add(new ChangeColorByNBrick(25.0d));
        arrayList.add(new SetBackgroundBrick());
        return arrayList;
    }

    @Override // org.catrobat.catroid.ui.fragment.CategoryBricksFactory
    protected List<Brick> setupDataCategoryList(Context context, boolean isBackgroundSprite) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new SetVariableBrick(1.0d));
        arrayList.add(new ChangeVariableBrick(1.0d));
        arrayList.add(new ShowTextBrick(100, 200));
        arrayList.add(new HideTextBrick());
        return arrayList;
    }
}

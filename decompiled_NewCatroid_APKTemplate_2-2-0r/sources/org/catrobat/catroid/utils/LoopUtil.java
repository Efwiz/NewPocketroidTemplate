package org.catrobat.catroid.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KClass;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.content.Script;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.UserDefinedScript;
import org.catrobat.catroid.content.bricks.Brick;
import org.catrobat.catroid.content.bricks.BrickBaseType;
import org.catrobat.catroid.content.bricks.ChangeBrightnessByNBrick;
import org.catrobat.catroid.content.bricks.ChangeColorByNBrick;
import org.catrobat.catroid.content.bricks.ChangeSizeByNBrick;
import org.catrobat.catroid.content.bricks.ChangeTransparencyByNBrick;
import org.catrobat.catroid.content.bricks.ChangeVolumeByNBrick;
import org.catrobat.catroid.content.bricks.ChangeXByNBrick;
import org.catrobat.catroid.content.bricks.ChangeYByNBrick;
import org.catrobat.catroid.content.bricks.CompositeBrick;
import org.catrobat.catroid.content.bricks.GoToBrick;
import org.catrobat.catroid.content.bricks.IfOnEdgeBounceBrick;
import org.catrobat.catroid.content.bricks.MoveNStepsBrick;
import org.catrobat.catroid.content.bricks.NextLookBrick;
import org.catrobat.catroid.content.bricks.PlaceAtBrick;
import org.catrobat.catroid.content.bricks.PointInDirectionBrick;
import org.catrobat.catroid.content.bricks.PointToBrick;
import org.catrobat.catroid.content.bricks.PreviousLookBrick;
import org.catrobat.catroid.content.bricks.SetBackgroundBrick;
import org.catrobat.catroid.content.bricks.SetBackgroundByIndexBrick;
import org.catrobat.catroid.content.bricks.SetBrightnessBrick;
import org.catrobat.catroid.content.bricks.SetColorBrick;
import org.catrobat.catroid.content.bricks.SetLookBrick;
import org.catrobat.catroid.content.bricks.SetLookByIndexBrick;
import org.catrobat.catroid.content.bricks.SetSizeToBrick;
import org.catrobat.catroid.content.bricks.SetTempoBrick;
import org.catrobat.catroid.content.bricks.SetTransparencyBrick;
import org.catrobat.catroid.content.bricks.SetVolumeToBrick;
import org.catrobat.catroid.content.bricks.SetXBrick;
import org.catrobat.catroid.content.bricks.SetYBrick;
import org.catrobat.catroid.content.bricks.TurnLeftBrick;
import org.catrobat.catroid.content.bricks.TurnRightBrick;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/utils/LoopUtil.class
  classes14.dex
 */
/* JADX INFO: compiled from: LoopUtil.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0018\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0007J\b\u0010\r\u001a\u00020\bH\u0007R\u001c\u0010\u0003\u001a\u0010\u0012\f\u0012\n\u0012\u0006\b\u0001\u0012\u00020\u00060\u00050\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000e"}, d2 = {"Lorg/catrobat/catroid/utils/LoopUtil;", "", "()V", "loopDelayBricks", "", "Lkotlin/reflect/KClass;", "Lorg/catrobat/catroid/content/bricks/BrickBaseType;", "checkLoopBrickForLoopDelay", "", "loopBrick", "Lorg/catrobat/catroid/content/bricks/CompositeBrick;", "script", "Lorg/catrobat/catroid/content/Script;", "isAnyStitchRunning", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class LoopUtil {
    public static final LoopUtil INSTANCE = new LoopUtil();
    private static final List<KClass<? extends BrickBaseType>> loopDelayBricks = CollectionsKt.listOf((Object[]) new KClass[]{Reflection.getOrCreateKotlinClass(PlaceAtBrick.class), Reflection.getOrCreateKotlinClass(SetXBrick.class), Reflection.getOrCreateKotlinClass(SetYBrick.class), Reflection.getOrCreateKotlinClass(ChangeXByNBrick.class), Reflection.getOrCreateKotlinClass(ChangeYByNBrick.class), Reflection.getOrCreateKotlinClass(GoToBrick.class), Reflection.getOrCreateKotlinClass(IfOnEdgeBounceBrick.class), Reflection.getOrCreateKotlinClass(MoveNStepsBrick.class), Reflection.getOrCreateKotlinClass(TurnLeftBrick.class), Reflection.getOrCreateKotlinClass(TurnRightBrick.class), Reflection.getOrCreateKotlinClass(PointInDirectionBrick.class), Reflection.getOrCreateKotlinClass(PointToBrick.class), Reflection.getOrCreateKotlinClass(SetLookBrick.class), Reflection.getOrCreateKotlinClass(SetLookByIndexBrick.class), Reflection.getOrCreateKotlinClass(NextLookBrick.class), Reflection.getOrCreateKotlinClass(PreviousLookBrick.class), Reflection.getOrCreateKotlinClass(SetSizeToBrick.class), Reflection.getOrCreateKotlinClass(ChangeSizeByNBrick.class), Reflection.getOrCreateKotlinClass(SetTransparencyBrick.class), Reflection.getOrCreateKotlinClass(ChangeTransparencyByNBrick.class), Reflection.getOrCreateKotlinClass(SetBrightnessBrick.class), Reflection.getOrCreateKotlinClass(ChangeBrightnessByNBrick.class), Reflection.getOrCreateKotlinClass(SetColorBrick.class), Reflection.getOrCreateKotlinClass(ChangeColorByNBrick.class), Reflection.getOrCreateKotlinClass(SetBackgroundBrick.class), Reflection.getOrCreateKotlinClass(SetBackgroundByIndexBrick.class), Reflection.getOrCreateKotlinClass(SetVolumeToBrick.class), Reflection.getOrCreateKotlinClass(ChangeVolumeByNBrick.class), Reflection.getOrCreateKotlinClass(SetTempoBrick.class)});

    private LoopUtil() {
    }

    @JvmStatic
    public static final boolean checkLoopBrickForLoopDelay(CompositeBrick loopBrick, Script script) {
        Intrinsics.checkNotNullParameter(loopBrick, "loopBrick");
        Intrinsics.checkNotNullParameter(script, "script");
        ArrayList arrayList = new ArrayList();
        loopBrick.addToFlatList(arrayList);
        if ((script instanceof UserDefinedScript) && !((UserDefinedScript) script).getScreenRefresh().booleanValue()) {
            return false;
        }
        ArrayList arrayList2 = new ArrayList();
        for (Object obj : arrayList) {
            if (!((Brick) obj).isCommentedOut()) {
                arrayList2.add(obj);
            }
        }
        Iterator it = arrayList2.iterator();
        while (it.hasNext()) {
            if (CollectionsKt.contains(loopDelayBricks, Reflection.getOrCreateKotlinClass(((Brick) it.next()).getClass()))) {
                return true;
            }
        }
        return false;
    }

    @JvmStatic
    public static final boolean isAnyStitchRunning() {
        List<Sprite> spriteListWithClones;
        if (ProjectManager.getInstance() != null && ProjectManager.getInstance().getCurrentProject() != null && (spriteListWithClones = ProjectManager.getInstance().getCurrentProject().getSpriteListWithClones()) != null) {
            Iterator<T> it = spriteListWithClones.iterator();
            while (it.hasNext()) {
                if (((Sprite) it.next()).runningStitch.isRunning()) {
                    return true;
                }
            }
        }
        return false;
    }
}

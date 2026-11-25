package org.catrobat.catroid.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.view.ContextThemeWrapper;
import androidx.appcompat.app.AlertDialog;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.content.Project;
import org.catrobat.catroid.content.Scene;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.XmlHeader;
import org.catrobat.catroid.content.bricks.AsyncRepeatBrick;
import org.catrobat.catroid.content.bricks.BackgroundRequestBrick;
import org.catrobat.catroid.content.bricks.Brick;
import org.catrobat.catroid.content.bricks.ForItemInUserListBrick;
import org.catrobat.catroid.content.bricks.ForVariableFromToBrick;
import org.catrobat.catroid.content.bricks.ForeverBrick;
import org.catrobat.catroid.content.bricks.IfLogicBeginBrick;
import org.catrobat.catroid.content.bricks.IfThenLogicBeginBrick;
import org.catrobat.catroid.content.bricks.InstantBrick;
import org.catrobat.catroid.content.bricks.IntervalRepeatBrick;
import org.catrobat.catroid.content.bricks.LookRequestBrick;
import org.catrobat.catroid.content.bricks.ParameterizedBrick;
import org.catrobat.catroid.content.bricks.PhiroIfLogicBeginBrick;
import org.catrobat.catroid.content.bricks.RepeatBrick;
import org.catrobat.catroid.content.bricks.RepeatUntilBrick;
import org.catrobat.catroid.content.bricks.RunAsSpriteBrick;
import org.catrobat.catroid.content.bricks.SpawnThreadBrick;
import org.catrobat.catroid.content.bricks.StartListeningBrick;
import org.catrobat.catroid.content.bricks.WebRequestBrick;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/ProjectUtils.class
  classes14.dex
 */
/* JADX INFO: compiled from: ProjectUtils.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000(\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u000e\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003\u001a\u0012\u0010\u0004\u001a\u00020\u0005*\b\u0012\u0004\u0012\u00020\u00070\u0006H\u0002\u001a\u0010\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006*\u00020\t\u001a\f\u0010\n\u001a\u00020\u0005*\u00020\u000bH\u0002¨\u0006\f"}, d2 = {"showWarningForSuspiciousBricksOnce", "", "context", "Landroid/content/Context;", "containsSuspiciousBricks", "", "", "Lorg/catrobat/catroid/content/bricks/Brick;", "getListAllBricks", "Lorg/catrobat/catroid/content/Sprite;", "shouldDisplaySuspiciousBricksWarning", "Lorg/catrobat/catroid/content/Project;", "catroid_apktemplateTemplateRelease"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class ProjectUtils {
    private static final boolean containsSuspiciousBricks(List<? extends Brick> list) {
        boolean z;
        List<? extends Brick> list2 = list;
        boolean z2 = list2 instanceof Collection;
        boolean z3 = true;
        if (!z2 || !list2.isEmpty()) {
            Iterator<T> it = list2.iterator();
            while (true) {
                if (!it.hasNext()) {
                    z = false;
                    break;
                }
                if (((Brick) it.next()) instanceof StartListeningBrick) {
                    z = true;
                    break;
                }
            }
        } else {
            z = false;
            break;
        }
        if (z2 && list2.isEmpty()) {
            z3 = false;
        } else {
            for (Brick brick : list2) {
                if ((brick instanceof WebRequestBrick) || (brick instanceof BackgroundRequestBrick) || (brick instanceof LookRequestBrick)) {
                }
            }
            z3 = false;
        }
        return z & z3;
    }

    public static final List<Brick> getListAllBricks(Sprite sprite) {
        Intrinsics.checkNotNullParameter(sprite, "<this>");
        ArrayList arrayList = new ArrayList();
        List<Brick> allBricks = sprite.getAllBricks();
        Intrinsics.checkNotNullExpressionValue(allBricks, "getAllBricks(...)");
        for (Brick brick : allBricks) {
            arrayList.add(brick);
            if (brick instanceof ForeverBrick) {
                arrayList.addAll(((ForeverBrick) brick).getNestedBricks());
            } else if (brick instanceof IfLogicBeginBrick) {
                IfLogicBeginBrick ifLogicBeginBrick = (IfLogicBeginBrick) brick;
                arrayList.addAll(ifLogicBeginBrick.getNestedBricks());
                arrayList.addAll(ifLogicBeginBrick.getSecondaryNestedBricks());
            } else if (brick instanceof IfThenLogicBeginBrick) {
                arrayList.addAll(((IfThenLogicBeginBrick) brick).getNestedBricks());
            } else if (brick instanceof RepeatBrick) {
                arrayList.addAll(((RepeatBrick) brick).getNestedBricks());
            } else if (brick instanceof RunAsSpriteBrick) {
                arrayList.addAll(((RunAsSpriteBrick) brick).getNestedBricks());
            } else if (brick instanceof AsyncRepeatBrick) {
                arrayList.addAll(((AsyncRepeatBrick) brick).getNestedBricks());
            } else if (brick instanceof InstantBrick) {
                arrayList.addAll(((InstantBrick) brick).getNestedBricks());
            } else if (brick instanceof SpawnThreadBrick) {
                arrayList.addAll(((SpawnThreadBrick) brick).getNestedBricks());
            } else if (brick instanceof IntervalRepeatBrick) {
                arrayList.addAll(((IntervalRepeatBrick) brick).getNestedBricks());
            } else if (brick instanceof RepeatUntilBrick) {
                arrayList.addAll(((RepeatUntilBrick) brick).getNestedBricks());
            } else if (brick instanceof ForVariableFromToBrick) {
                arrayList.addAll(((ForVariableFromToBrick) brick).getNestedBricks());
            } else if (brick instanceof ForItemInUserListBrick) {
                arrayList.addAll(((ForItemInUserListBrick) brick).getNestedBricks());
            } else if (brick instanceof ParameterizedBrick) {
                arrayList.addAll(((ParameterizedBrick) brick).getNestedBricks());
            } else if (brick instanceof PhiroIfLogicBeginBrick) {
                PhiroIfLogicBeginBrick phiroIfLogicBeginBrick = (PhiroIfLogicBeginBrick) brick;
                arrayList.addAll(phiroIfLogicBeginBrick.getNestedBricks());
                arrayList.addAll(phiroIfLogicBeginBrick.getSecondaryNestedBricks());
            }
        }
        return arrayList;
    }

    private static final boolean shouldDisplaySuspiciousBricksWarning(Project project) {
        ArrayList arrayList = new ArrayList();
        List<Scene> sceneList = project.getSceneList();
        Intrinsics.checkNotNullExpressionValue(sceneList, "getSceneList(...)");
        Iterator<T> it = sceneList.iterator();
        while (it.hasNext()) {
            List<Sprite> spriteList = ((Scene) it.next()).getSpriteList();
            Intrinsics.checkNotNullExpressionValue(spriteList, "getSpriteList(...)");
            for (Sprite sprite : spriteList) {
                Intrinsics.checkNotNull(sprite);
                arrayList.addAll(getListAllBricks(sprite));
            }
        }
        return containsSuspiciousBricks(arrayList);
    }

    public static final void showWarningForSuspiciousBricksOnce(Context context) {
        XmlHeader xmlHeader;
        Intrinsics.checkNotNullParameter(context, "context");
        final SharedPreferences sharedPreferences = context.getSharedPreferences(context.getString(R.string.preference_approved_list_file_key), 0);
        Project currentProject = ProjectManager.getInstance().getCurrentProject();
        final String remixParentsUrlString = (currentProject == null || (xmlHeader = currentProject.getXmlHeader()) == null) ? null : xmlHeader.getRemixParentsUrlString();
        if (remixParentsUrlString == null) {
            return;
        }
        boolean zIsBlank = StringsKt.isBlank(remixParentsUrlString);
        String string = sharedPreferences.getString(remixParentsUrlString, null);
        boolean z = string == null || StringsKt.isBlank(string);
        if (!zIsBlank) {
            Intrinsics.checkNotNull(currentProject);
            if (shouldDisplaySuspiciousBricksWarning(currentProject) && z) {
                new AlertDialog.Builder(new ContextThemeWrapper(context, 2131952355)).setTitle(context.getResources().getString(R.string.warning)).setMessage(context.getResources().getString(R.string.security_warning_dialog_msg)).setCancelable(false).setPositiveButton(context.getResources().getString(R.string.ok), new DialogInterface.OnClickListener() { // from class: org.catrobat.catroid.ui.ProjectUtils$$ExternalSyntheticLambda0
                    @Override // android.content.DialogInterface.OnClickListener
                    public final void onClick(DialogInterface dialogInterface, int i) {
                        ProjectUtils.showWarningForSuspiciousBricksOnce$lambda$6(sharedPreferences, remixParentsUrlString, dialogInterface, i);
                    }
                }).show();
                return;
            }
        }
        if (zIsBlank) {
            return;
        }
        sharedPreferences.edit().putString(remixParentsUrlString, remixParentsUrlString).apply();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void showWarningForSuspiciousBricksOnce$lambda$6(SharedPreferences sharedPreferences, String projectUrl, DialogInterface dialogInterface, int i) {
        Intrinsics.checkNotNullParameter(projectUrl, "$projectUrl");
        sharedPreferences.edit().putString(projectUrl, projectUrl).apply();
        dialogInterface.dismiss();
    }
}

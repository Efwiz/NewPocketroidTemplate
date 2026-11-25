package org.catrobat.catroid.ui.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.common.Constants;
import org.catrobat.catroid.common.FlavoredConstants;
import org.catrobat.catroid.common.LookData;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.StartScript;
import org.catrobat.catroid.content.bricks.MoveNStepsBrick;
import org.catrobat.catroid.content.bricks.PenDownBrick;
import org.catrobat.catroid.content.bricks.PlaceAtBrick;
import org.catrobat.catroid.content.bricks.SetTransparencyBrick;
import org.catrobat.catroid.content.bricks.TurnRightBrick;
import org.catrobat.catroid.io.ResourceImporter;
import org.catrobat.catroid.ui.settingsfragments.SettingsFragment;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/controller/ActorsAndObjectsManager.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class ActorsAndObjectsManager {
    private static ActorsAndObjectsManager instance;

    public static ActorsAndObjectsManager getInstance() {
        if (instance == null) {
            instance = new ActorsAndObjectsManager();
        }
        return instance;
    }

    private ActorsAndObjectsManager() {
    }

    private Sprite generateSprite(String name2, int imgId, double scale) throws IOException {
        Sprite sprite = new Sprite(name2);
        File file = new File(FlavoredConstants.DEFAULT_ROOT_DIRECTORY, Constants.IMAGE_DIRECTORY_NAME);
        if (!file.exists()) {
            file.mkdirs();
        }
        sprite.getLookList().add(new LookData(name2, ResourceImporter.createImageFileFromResourcesInDirectory(ProjectManager.getInstance().getApplicationContext().getResources(), imgId, file, "img.png", scale)));
        return sprite;
    }

    public List<Sprite> getSprites() {
        ArrayList arrayList = new ArrayList();
        try {
            if (SettingsFragment.isEmroiderySharedPreferenceEnabled(ProjectManager.getInstance().getApplicationContext())) {
                Sprite spriteGenerateSprite = generateSprite("10x10", R.drawable.frame, 1.0d);
                StartScript startScript = new StartScript();
                startScript.addBrick(new SetTransparencyBrick(100.0d));
                startScript.addBrick(new PlaceAtBrick(-250, 250));
                startScript.addBrick(new PenDownBrick());
                for (int i = 0; i < 4; i++) {
                    startScript.addBrick(new MoveNStepsBrick(500.0d));
                    startScript.addBrick(new TurnRightBrick(90.0d));
                }
                spriteGenerateSprite.addScript(startScript);
                arrayList.add(spriteGenerateSprite);
                arrayList.add(generateSprite("Needle", R.drawable.plotter, 0.2d));
            } else {
                arrayList.add(generateSprite("Plotter", R.drawable.plotter, 0.2d));
                arrayList.add(generateSprite("PandaA", R.drawable.panda_a, 1.0d));
                arrayList.add(generateSprite("PandaB", R.drawable.panda_b, 1.0d));
                arrayList.add(generateSprite("Apple", R.drawable.apple, 1.0d));
                arrayList.add(generateSprite("LynxA", R.drawable.lynx_a, 1.0d));
            }
            return arrayList;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

package org.catrobat.catroid.common.defaultprojectcreators;

import android.content.Context;
import android.graphics.BitmapFactory;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.common.Constants;
import org.catrobat.catroid.common.LookData;
import org.catrobat.catroid.common.ScreenValues;
import org.catrobat.catroid.common.SoundInfo;
import org.catrobat.catroid.content.Project;
import org.catrobat.catroid.content.Scene;
import org.catrobat.catroid.content.Script;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.StartScript;
import org.catrobat.catroid.content.WhenGamepadButtonScript;
import org.catrobat.catroid.content.bricks.Brick;
import org.catrobat.catroid.content.bricks.ChangeYByNBrick;
import org.catrobat.catroid.content.bricks.ForeverBrick;
import org.catrobat.catroid.content.bricks.GlideToBrick;
import org.catrobat.catroid.content.bricks.IfLogicBeginBrick;
import org.catrobat.catroid.content.bricks.PlaceAtBrick;
import org.catrobat.catroid.content.bricks.PlaySoundBrick;
import org.catrobat.catroid.content.bricks.SetLookBrick;
import org.catrobat.catroid.content.bricks.SetVariableBrick;
import org.catrobat.catroid.content.bricks.WaitBrick;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.FormulaElement;
import org.catrobat.catroid.formulaeditor.Functions;
import org.catrobat.catroid.formulaeditor.Operators;
import org.catrobat.catroid.formulaeditor.Sensors;
import org.catrobat.catroid.formulaeditor.UserVariable;
import org.catrobat.catroid.io.ResourceImporter;
import org.catrobat.catroid.io.XstreamSerializer;
import org.catrobat.catroid.utils.ImageEditing;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/common/defaultprojectcreators/ChromeCastProjectCreator.class
  classes12.dex
 */
/* JADX INFO: loaded from: classes12.dex */
public class ChromeCastProjectCreator extends ProjectCreator {
    public ChromeCastProjectCreator() {
        this.defaultProjectNameResourceId = R.string.default_cast_project_name;
    }

    @Override // org.catrobat.catroid.common.defaultprojectcreators.ProjectCreator
    public Project createDefaultProject(String name2, Context context, boolean landscapeMode) throws IOException {
        Project project = new Project(context, name2, true, true);
        if (project.getDirectory().exists()) {
            throw new IOException("Cannot create new project at " + project.getDirectory().getAbsolutePath() + ", directory already exists.");
        }
        XstreamSerializer.getInstance().saveProject(project);
        if (!project.getDirectory().isDirectory()) {
            throw new FileNotFoundException("Cannot create project at " + project.getDirectory().getAbsolutePath());
        }
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(context.getResources(), R.drawable.default_project_background_landscape, options);
        backgroundImageScaleFactor = ImageEditing.calculateScaleFactor(options.outWidth, options.outHeight, ScreenValues.CAST_SCREEN_RESOLUTION.getWidth(), ScreenValues.CAST_SCREEN_RESOLUTION.getHeight());
        Scene defaultScene = project.getDefaultScene();
        File file = new File(defaultScene.getDirectory(), Constants.IMAGE_DIRECTORY_NAME);
        File file2 = new File(defaultScene.getDirectory(), Constants.SOUND_DIRECTORY_NAME);
        File fileCreateImageFileFromResourcesInDirectory = ResourceImporter.createImageFileFromResourcesInDirectory(context.getResources(), R.drawable.default_project_background_landscape, file, "img.png", backgroundImageScaleFactor);
        File fileCreateImageFileFromResourcesInDirectory2 = ResourceImporter.createImageFileFromResourcesInDirectory(context.getResources(), R.drawable.default_project_clouds_landscape, file, "img.png", backgroundImageScaleFactor);
        File fileCreateImageFileFromResourcesInDirectory3 = ResourceImporter.createImageFileFromResourcesInDirectory(context.getResources(), R.drawable.default_project_clouds_landscape, file, "img.png", backgroundImageScaleFactor);
        File fileCreateImageFileFromResourcesInDirectory4 = ResourceImporter.createImageFileFromResourcesInDirectory(context.getResources(), R.drawable.default_project_bird_wing_up, file, "img.png", backgroundImageScaleFactor);
        File fileCreateImageFileFromResourcesInDirectory5 = ResourceImporter.createImageFileFromResourcesInDirectory(context.getResources(), R.drawable.default_project_bird_wing_down, file, "img.png", backgroundImageScaleFactor);
        File fileCreateImageFileFromResourcesInDirectory6 = ResourceImporter.createImageFileFromResourcesInDirectory(context.getResources(), R.drawable.default_project_bird_wing_up_left, file, "img.png", backgroundImageScaleFactor);
        File fileCreateImageFileFromResourcesInDirectory7 = ResourceImporter.createImageFileFromResourcesInDirectory(context.getResources(), R.drawable.default_project_bird_wing_down_left, file, "img.png", backgroundImageScaleFactor);
        File fileCreateSoundFileFromResourcesInDirectory = ResourceImporter.createSoundFileFromResourcesInDirectory(context.getResources(), R.raw.default_project_tweet_1, file2, "snd.wav");
        File fileCreateSoundFileFromResourcesInDirectory2 = ResourceImporter.createSoundFileFromResourcesInDirectory(context.getResources(), R.raw.default_project_tweet_2, file2, "snd.wav");
        ResourceImporter.createImageFileFromResourcesInDirectory(context.getResources(), R.drawable.default_project_screenshot_landscape, defaultScene.getDirectory(), Constants.SCREENSHOT_AUTOMATIC_FILE_NAME, 1.0d);
        defaultScene.getSpriteList().get(0).getLookList().add(new LookData(context.getString(R.string.default_cast_project_background_name), fileCreateImageFileFromResourcesInDirectory));
        Sprite sprite = new Sprite(context.getString(R.string.default_cast_project_cloud_sprite_name1));
        Sprite sprite2 = new Sprite(context.getString(R.string.default_cast_project_cloud_sprite_name2));
        Sprite sprite3 = new Sprite(context.getString(R.string.default_cast_project_sprites_bird_name));
        defaultScene.addSprite(sprite);
        defaultScene.addSprite(sprite2);
        defaultScene.addSprite(sprite3);
        sprite.getLookList().add(new LookData(context.getString(R.string.default_cast_project_cloud_name), fileCreateImageFileFromResourcesInDirectory2));
        sprite2.getLookList().add(new LookData(context.getString(R.string.default_cast_project_cloud_name), fileCreateImageFileFromResourcesInDirectory3));
        sprite3.getLookList().add(new LookData(context.getString(R.string.default_cast_project_sprites_bird_name_wing_up), fileCreateImageFileFromResourcesInDirectory4));
        sprite3.getLookList().add(new LookData(context.getString(R.string.default_cast_project_sprites_bird_name_wing_down), fileCreateImageFileFromResourcesInDirectory5));
        sprite3.getLookList().add(new LookData(context.getString(R.string.default_cast_project_sprites_bird_name_wing_up_left), fileCreateImageFileFromResourcesInDirectory6));
        sprite3.getLookList().add(new LookData(context.getString(R.string.default_cast_project_sprites_bird_name_wing_down_left), fileCreateImageFileFromResourcesInDirectory7));
        sprite3.getSoundList().add(new SoundInfo(context.getString(R.string.default_cast_project_sprites_tweet_1), fileCreateSoundFileFromResourcesInDirectory));
        sprite3.getSoundList().add(new SoundInfo(context.getString(R.string.default_cast_project_sprites_tweet_2), fileCreateSoundFileFromResourcesInDirectory2));
        Script startScript = new StartScript();
        startScript.addBrick(new PlaceAtBrick(new Formula((Integer) 0), new Formula((Integer) 0)));
        startScript.addBrick(new GlideToBrick(new Formula(Integer.valueOf(-ScreenValues.CAST_SCREEN_RESOLUTION.getWidth())), new Formula((Integer) 0), new Formula((Integer) 5)));
        startScript.addBrick(new PlaceAtBrick(ScreenValues.CAST_SCREEN_RESOLUTION.getWidth(), 0));
        ForeverBrick foreverBrick = new ForeverBrick();
        foreverBrick.addBrick(new GlideToBrick(new Formula(Integer.valueOf(-ScreenValues.CAST_SCREEN_RESOLUTION.getWidth())), new Formula((Integer) 0), new Formula((Integer) 10)));
        foreverBrick.addBrick(new PlaceAtBrick(ScreenValues.CAST_SCREEN_RESOLUTION.getWidth(), 0));
        startScript.addBrick(foreverBrick);
        sprite.addScript(startScript);
        Script startScript2 = new StartScript();
        startScript2.addBrick(new PlaceAtBrick(new Formula(Integer.valueOf(ScreenValues.CAST_SCREEN_RESOLUTION.getWidth())), new Formula((Integer) 0)));
        ForeverBrick foreverBrick2 = new ForeverBrick();
        foreverBrick2.addBrick(new GlideToBrick(new Formula(Integer.valueOf(-ScreenValues.CAST_SCREEN_RESOLUTION.getWidth())), new Formula((Integer) 0), new Formula((Integer) 10)));
        foreverBrick2.addBrick(new PlaceAtBrick(new Formula(Integer.valueOf(ScreenValues.CAST_SCREEN_RESOLUTION.getWidth())), new Formula((Integer) 0)));
        startScript2.addBrick(foreverBrick2);
        sprite2.addScript(startScript2);
        StartScript startScript3 = new StartScript();
        Brick foreverBrick3 = new ForeverBrick();
        FormulaElement formulaElement = new FormulaElement(FormulaElement.ElementType.NUMBER, "-640", null);
        FormulaElement formulaElement2 = new FormulaElement(FormulaElement.ElementType.NUMBER, "640", null);
        FormulaElement formulaElement3 = new FormulaElement(FormulaElement.ElementType.NUMBER, "-360", null);
        FormulaElement formulaElement4 = new FormulaElement(FormulaElement.ElementType.NUMBER, "360", null);
        FormulaElement formulaElement5 = new FormulaElement(FormulaElement.ElementType.SENSOR, Sensors.OBJECT_X.name(), null);
        FormulaElement formulaElement6 = new FormulaElement(FormulaElement.ElementType.SENSOR, Sensors.OBJECT_Y.name(), null);
        IfLogicBeginBrick ifLogicBeginBrick = new IfLogicBeginBrick(new Formula(new FormulaElement(FormulaElement.ElementType.SENSOR, Sensors.GAMEPAD_UP_PRESSED.name(), null)));
        IfLogicBeginBrick ifLogicBeginBrick2 = new IfLogicBeginBrick(new Formula(new FormulaElement(FormulaElement.ElementType.OPERATOR, Operators.EQUAL.name(), null, formulaElement4, formulaElement6)));
        ifLogicBeginBrick2.addBrickToIfBranch(new PlaceAtBrick(new Formula(new FormulaElement(FormulaElement.ElementType.SENSOR, Sensors.OBJECT_X.name(), null)), new Formula(new FormulaElement(FormulaElement.ElementType.NUMBER, "-360", null))));
        ifLogicBeginBrick.addBrickToIfBranch(ifLogicBeginBrick2);
        ifLogicBeginBrick.addBrickToIfBranch(new ChangeYByNBrick(new Formula((Integer) 5)));
        startScript3.addBrick(ifLogicBeginBrick);
        IfLogicBeginBrick ifLogicBeginBrick3 = new IfLogicBeginBrick(new Formula(new FormulaElement(FormulaElement.ElementType.SENSOR, Sensors.GAMEPAD_DOWN_PRESSED.name(), null)));
        IfLogicBeginBrick ifLogicBeginBrick4 = new IfLogicBeginBrick(new Formula(new FormulaElement(FormulaElement.ElementType.OPERATOR, Operators.EQUAL.name(), null, formulaElement3, formulaElement6)));
        ifLogicBeginBrick4.addBrickToIfBranch(new PlaceAtBrick(new Formula(new FormulaElement(FormulaElement.ElementType.SENSOR, Sensors.OBJECT_X.name(), null)), new Formula(new FormulaElement(FormulaElement.ElementType.NUMBER, "360", null))));
        ifLogicBeginBrick3.addBrickToIfBranch(ifLogicBeginBrick4);
        ifLogicBeginBrick3.addBrickToIfBranch(new ChangeYByNBrick(new Formula((Integer) (-5))));
        startScript3.addBrick(ifLogicBeginBrick3);
        UserVariable userVariable = new UserVariable(context.getString(R.string.default_cast_project_var_direction));
        project.addUserVariable(userVariable);
        IfLogicBeginBrick ifLogicBeginBrick5 = new IfLogicBeginBrick(new Formula(new FormulaElement(FormulaElement.ElementType.SENSOR, Sensors.GAMEPAD_LEFT_PRESSED.name(), null)));
        ifLogicBeginBrick5.addBrickToIfBranch(new SetVariableBrick(new Formula(new FormulaElement(FormulaElement.ElementType.FUNCTION, Functions.TRUE.name(), null)), userVariable));
        SetLookBrick setLookBrick = new SetLookBrick();
        setLookBrick.setLook(sprite3.getLookList().get(2));
        ifLogicBeginBrick5.addBrickToIfBranch(setLookBrick);
        IfLogicBeginBrick ifLogicBeginBrick6 = new IfLogicBeginBrick(new Formula(new FormulaElement(FormulaElement.ElementType.OPERATOR, Operators.EQUAL.name(), null, formulaElement, formulaElement5)));
        ifLogicBeginBrick6.addBrickToIfBranch(new PlaceAtBrick(new Formula(new FormulaElement(FormulaElement.ElementType.SENSOR, Sensors.OBJECT_X.name(), null)), new Formula(new FormulaElement(FormulaElement.ElementType.NUMBER, "640", null))));
        ifLogicBeginBrick5.addBrickToIfBranch(ifLogicBeginBrick6);
        ifLogicBeginBrick5.addBrickToIfBranch(new ChangeYByNBrick(new Formula((Integer) (-5))));
        startScript3.addBrick(ifLogicBeginBrick5);
        startScript3.addBrick(foreverBrick3);
        sprite3.addScript(startScript3);
        IfLogicBeginBrick ifLogicBeginBrick7 = new IfLogicBeginBrick(new Formula(new FormulaElement(FormulaElement.ElementType.SENSOR, Sensors.GAMEPAD_RIGHT_PRESSED.name(), null)));
        ifLogicBeginBrick7.addBrickToIfBranch(new SetVariableBrick(new Formula(new FormulaElement(FormulaElement.ElementType.FUNCTION, Functions.TRUE.name(), null)), userVariable));
        SetLookBrick setLookBrick2 = new SetLookBrick();
        setLookBrick2.setLook(sprite3.getLookList().get(0));
        ifLogicBeginBrick7.addBrickToIfBranch(setLookBrick2);
        IfLogicBeginBrick ifLogicBeginBrick8 = new IfLogicBeginBrick(new Formula(new FormulaElement(FormulaElement.ElementType.OPERATOR, Operators.EQUAL.name(), null, formulaElement2, formulaElement5)));
        ifLogicBeginBrick8.addBrickToIfBranch(new PlaceAtBrick(new Formula(new FormulaElement(FormulaElement.ElementType.SENSOR, Sensors.OBJECT_X.name(), null)), new Formula(new FormulaElement(FormulaElement.ElementType.NUMBER, "-640", null))));
        ifLogicBeginBrick7.addBrickToIfBranch(ifLogicBeginBrick8);
        ifLogicBeginBrick7.addBrickToIfBranch(new ChangeYByNBrick(new Formula((Integer) 5)));
        startScript3.addBrick(ifLogicBeginBrick7);
        startScript3.addBrick(foreverBrick3);
        sprite3.addScript(startScript3);
        WhenGamepadButtonScript whenGamepadButtonScript = new WhenGamepadButtonScript(context.getString(R.string.cast_gamepad_A));
        IfLogicBeginBrick ifLogicBeginBrick9 = new IfLogicBeginBrick(new Formula(new FormulaElement(FormulaElement.ElementType.USER_VARIABLE, userVariable.getName(), null)));
        SetLookBrick setLookBrick3 = new SetLookBrick();
        setLookBrick3.setLook(sprite3.getLookList().get(3));
        ifLogicBeginBrick9.addBrickToIfBranch(setLookBrick3);
        ifLogicBeginBrick9.addBrickToIfBranch(new WaitBrick(new Formula(Double.valueOf(0.1d))));
        SetLookBrick setLookBrick4 = new SetLookBrick();
        setLookBrick4.setLook(sprite3.getLookList().get(2));
        ifLogicBeginBrick9.addBrickToIfBranch(setLookBrick4);
        SetLookBrick setLookBrick5 = new SetLookBrick();
        setLookBrick5.setLook(sprite3.getLookList().get(0));
        ifLogicBeginBrick9.addBrickToIfBranch(setLookBrick5);
        ifLogicBeginBrick9.addBrickToIfBranch(new WaitBrick(new Formula(Double.valueOf(0.1d))));
        SetLookBrick setLookBrick6 = new SetLookBrick();
        setLookBrick6.setLook(sprite3.getLookList().get(1));
        ifLogicBeginBrick9.addBrickToIfBranch(setLookBrick6);
        whenGamepadButtonScript.addBrick(ifLogicBeginBrick9);
        sprite3.addScript(whenGamepadButtonScript);
        WhenGamepadButtonScript whenGamepadButtonScript2 = new WhenGamepadButtonScript(context.getString(R.string.cast_gamepad_B));
        IfLogicBeginBrick ifLogicBeginBrick10 = new IfLogicBeginBrick(new Formula(new FormulaElement(FormulaElement.ElementType.USER_VARIABLE, userVariable.getName(), null)));
        PlaySoundBrick playSoundBrick = new PlaySoundBrick();
        playSoundBrick.setSound(sprite3.getSoundList().get(0));
        ifLogicBeginBrick10.addBrickToIfBranch(playSoundBrick);
        PlaySoundBrick playSoundBrick2 = new PlaySoundBrick();
        playSoundBrick2.setSound(sprite3.getSoundList().get(1));
        ifLogicBeginBrick10.addBrickToElseBranch(playSoundBrick2);
        whenGamepadButtonScript2.addBrick(ifLogicBeginBrick10);
        sprite3.addScript(whenGamepadButtonScript2);
        XstreamSerializer.getInstance().saveProject(project);
        return project;
    }
}

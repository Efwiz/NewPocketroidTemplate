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
import org.catrobat.catroid.content.WhenScript;
import org.catrobat.catroid.content.bricks.ForeverBrick;
import org.catrobat.catroid.content.bricks.GlideToBrick;
import org.catrobat.catroid.content.bricks.NextLookBrick;
import org.catrobat.catroid.content.bricks.PlaceAtBrick;
import org.catrobat.catroid.content.bricks.PlaySoundBrick;
import org.catrobat.catroid.content.bricks.WaitBrick;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.FormulaElement;
import org.catrobat.catroid.formulaeditor.Functions;
import org.catrobat.catroid.formulaeditor.Operators;
import org.catrobat.catroid.io.ResourceImporter;
import org.catrobat.catroid.io.XstreamSerializer;
import org.catrobat.catroid.utils.ImageEditing;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/common/defaultprojectcreators/DefaultProjectCreator.class
  classes12.dex
 */
/* JADX INFO: loaded from: classes12.dex */
public class DefaultProjectCreator extends ProjectCreator {
    public DefaultProjectCreator() {
        this.defaultProjectNameResourceId = R.string.default_project_name;
    }

    @Override // org.catrobat.catroid.common.defaultprojectcreators.ProjectCreator
    public Project createDefaultProject(String name2, Context context, boolean landscapeMode) throws IOException {
        int i;
        int i2;
        int i3;
        Project project = new Project(context, name2, landscapeMode);
        if (project.getDirectory().exists()) {
            throw new IOException("Cannot create new project at " + project.getDirectory().getAbsolutePath() + ", directory already exists.");
        }
        XstreamSerializer.getInstance().saveProject(project);
        if (!project.getDirectory().isDirectory()) {
            throw new FileNotFoundException("Cannot create project at " + project.getDirectory().getAbsolutePath());
        }
        if (landscapeMode) {
            i = R.drawable.default_project_background_landscape;
            i2 = R.drawable.default_project_clouds_landscape;
            i3 = R.drawable.default_project_screenshot_landscape;
        } else {
            i = R.drawable.default_project_background_portrait;
            i2 = R.drawable.default_project_clouds_portrait;
            i3 = R.drawable.default_project_screenshot;
        }
        int i4 = i;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(context.getResources(), i4, options);
        backgroundImageScaleFactor = ImageEditing.calculateScaleFactor(options.outWidth, options.outHeight, ScreenValues.currentScreenResolution.getWidth(), ScreenValues.currentScreenResolution.getHeight());
        Scene defaultScene = project.getDefaultScene();
        File file = new File(defaultScene.getDirectory(), Constants.IMAGE_DIRECTORY_NAME);
        File file2 = new File(defaultScene.getDirectory(), Constants.SOUND_DIRECTORY_NAME);
        File fileCreateImageFileFromResourcesInDirectory = ResourceImporter.createImageFileFromResourcesInDirectory(context.getResources(), i4, file, "img.png", backgroundImageScaleFactor);
        int i5 = i2;
        File fileCreateImageFileFromResourcesInDirectory2 = ResourceImporter.createImageFileFromResourcesInDirectory(context.getResources(), i5, file, "img.png", backgroundImageScaleFactor);
        File fileCreateImageFileFromResourcesInDirectory3 = ResourceImporter.createImageFileFromResourcesInDirectory(context.getResources(), i5, file, "img.png", backgroundImageScaleFactor);
        File fileCreateImageFileFromResourcesInDirectory4 = ResourceImporter.createImageFileFromResourcesInDirectory(context.getResources(), R.drawable.default_project_bird_wing_up, file, "img.png", backgroundImageScaleFactor);
        File fileCreateImageFileFromResourcesInDirectory5 = ResourceImporter.createImageFileFromResourcesInDirectory(context.getResources(), R.drawable.default_project_bird_wing_down, file, "img.png", backgroundImageScaleFactor);
        File fileCreateSoundFileFromResourcesInDirectory = ResourceImporter.createSoundFileFromResourcesInDirectory(context.getResources(), R.raw.default_project_tweet_1, file2, "snd.wav");
        File fileCreateSoundFileFromResourcesInDirectory2 = ResourceImporter.createSoundFileFromResourcesInDirectory(context.getResources(), R.raw.default_project_tweet_2, file2, "snd.wav");
        ResourceImporter.createImageFileFromResourcesInDirectory(context.getResources(), i3, defaultScene.getDirectory(), Constants.SCREENSHOT_AUTOMATIC_FILE_NAME, 1.0d);
        defaultScene.getSpriteList().get(0).getLookList().add(new LookData(context.getString(R.string.default_project_background_name), fileCreateImageFileFromResourcesInDirectory));
        Sprite sprite = new Sprite(context.getString(R.string.default_project_cloud_sprite_name_1));
        Sprite sprite2 = new Sprite(context.getString(R.string.default_project_cloud_sprite_name_2));
        Sprite sprite3 = new Sprite(context.getString(R.string.default_project_sprites_animal_name));
        defaultScene.addSprite(sprite);
        defaultScene.addSprite(sprite2);
        defaultScene.addSprite(sprite3);
        sprite.getLookList().add(new LookData(context.getString(R.string.default_project_cloud_name), fileCreateImageFileFromResourcesInDirectory2));
        sprite2.getLookList().add(new LookData(context.getString(R.string.default_project_cloud_name), fileCreateImageFileFromResourcesInDirectory3));
        sprite3.getLookList().add(new LookData(context.getString(R.string.default_project_sprites_animal_wings_up), fileCreateImageFileFromResourcesInDirectory4));
        sprite3.getLookList().add(new LookData(context.getString(R.string.default_project_sprites_animal_wings_down), fileCreateImageFileFromResourcesInDirectory5));
        sprite3.getSoundList().add(new SoundInfo(context.getString(R.string.default_project_sprites_tweet_1), fileCreateSoundFileFromResourcesInDirectory));
        sprite3.getSoundList().add(new SoundInfo(context.getString(R.string.default_project_sprites_tweet_2), fileCreateSoundFileFromResourcesInDirectory2));
        Script startScript = new StartScript();
        startScript.addBrick(new PlaceAtBrick(new Formula((Integer) 0), new Formula((Integer) 0)));
        startScript.addBrick(new GlideToBrick(new Formula(Integer.valueOf(-ScreenValues.currentScreenResolution.getWidth())), new Formula((Integer) 0), new Formula((Integer) 5)));
        startScript.addBrick(new PlaceAtBrick(new Formula(Integer.valueOf(ScreenValues.currentScreenResolution.getWidth())), new Formula((Integer) 0)));
        ForeverBrick foreverBrick = new ForeverBrick();
        foreverBrick.addBrick(new GlideToBrick(new Formula(Integer.valueOf(-ScreenValues.currentScreenResolution.getWidth())), new Formula((Integer) 0), new Formula((Integer) 10)));
        foreverBrick.addBrick(new PlaceAtBrick(new Formula(Integer.valueOf(ScreenValues.currentScreenResolution.getWidth())), new Formula((Integer) 0)));
        startScript.addBrick(foreverBrick);
        sprite.addScript(startScript);
        Script startScript2 = new StartScript();
        startScript2.addBrick(new PlaceAtBrick(new Formula(Integer.valueOf(ScreenValues.currentScreenResolution.getWidth())), new Formula((Integer) 0)));
        ForeverBrick foreverBrick2 = new ForeverBrick();
        foreverBrick2.addBrick(new GlideToBrick(new Formula(Integer.valueOf(-ScreenValues.currentScreenResolution.getWidth())), new Formula((Integer) 0), new Formula((Integer) 10)));
        foreverBrick2.addBrick(new PlaceAtBrick(new Formula(Integer.valueOf(ScreenValues.currentScreenResolution.getWidth())), new Formula((Integer) 0)));
        startScript2.addBrick(foreverBrick2);
        sprite2.addScript(startScript2);
        Script startScript3 = new StartScript();
        ForeverBrick foreverBrick3 = new ForeverBrick();
        FormulaElement formulaElement = new FormulaElement(FormulaElement.ElementType.FUNCTION, Functions.RAND.toString(), null);
        FormulaElement formulaElement2 = new FormulaElement(FormulaElement.ElementType.OPERATOR, Operators.MINUS.toString(), formulaElement);
        formulaElement2.setRightChild(new FormulaElement(FormulaElement.ElementType.NUMBER, "300", formulaElement2));
        formulaElement.setLeftChild(formulaElement2);
        formulaElement.setRightChild(new FormulaElement(FormulaElement.ElementType.NUMBER, "300", formulaElement));
        Formula formula = new Formula(formulaElement);
        FormulaElement formulaElement3 = new FormulaElement(FormulaElement.ElementType.FUNCTION, Functions.RAND.toString(), null);
        FormulaElement formulaElement4 = new FormulaElement(FormulaElement.ElementType.OPERATOR, Operators.MINUS.toString(), formulaElement3);
        formulaElement4.setRightChild(new FormulaElement(FormulaElement.ElementType.NUMBER, "200", formulaElement4));
        formulaElement3.setLeftChild(formulaElement4);
        formulaElement3.setRightChild(new FormulaElement(FormulaElement.ElementType.NUMBER, "200", formulaElement3));
        foreverBrick3.addBrick(new GlideToBrick(formula, new Formula(formulaElement3), new Formula((Integer) 1)));
        startScript3.addBrick(foreverBrick3);
        sprite3.addScript(startScript3);
        Script startScript4 = new StartScript();
        ForeverBrick foreverBrick4 = new ForeverBrick();
        foreverBrick4.addBrick(new NextLookBrick());
        foreverBrick4.addBrick(new WaitBrick(new Formula(Double.valueOf(0.2d))));
        startScript4.addBrick(foreverBrick4);
        sprite3.addScript(startScript4);
        Script whenScript = new WhenScript();
        PlaySoundBrick playSoundBrick = new PlaySoundBrick();
        playSoundBrick.setSound(sprite3.getSoundList().get(0));
        whenScript.addBrick(playSoundBrick);
        sprite3.addScript(whenScript);
        XstreamSerializer.getInstance().saveProject(project);
        return project;
    }
}

package org.catrobat.catroid.stage;

import android.content.res.Resources;
import android.util.DisplayMetrics;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import java.util.Iterator;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.content.PenConfiguration;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.XmlHeader;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/stage/PenActor.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class PenActor extends Actor {
    private FrameBuffer buffer;
    private Batch bufferBatch;
    private OrthographicCamera camera;
    private Float screenRatio;

    public PenActor() {
        XmlHeader xmlHeader = ProjectManager.getInstance().getCurrentProject().getXmlHeader();
        this.buffer = new FrameBuffer(Pixmap.Format.RGBA8888, xmlHeader.getVirtualScreenWidth(), xmlHeader.getVirtualScreenHeight(), false);
        this.bufferBatch = new SpriteBatch();
        OrthographicCamera orthographicCamera = new OrthographicCamera(xmlHeader.getVirtualScreenWidth(), xmlHeader.getVirtualScreenHeight());
        this.camera = orthographicCamera;
        orthographicCamera.update();
        this.bufferBatch.setProjectionMatrix(this.camera.combined);
        this.screenRatio = Float.valueOf(calculateScreenRatio());
        reset();
    }

    @Override // com.badlogic.gdx.scenes.scene2d.Actor
    public void draw(Batch batch, float parentAlpha) {
        this.buffer.begin();
        Iterator<Sprite> it = StageActivity.getActiveStageListener().getSpritesFromStage().iterator();
        while (it.hasNext()) {
            it.next().penConfiguration.drawLinesForSprite(this.screenRatio, getStage().getViewport().getCamera());
        }
        this.buffer.end();
        batch.end();
        TextureRegion textureRegion = new TextureRegion(this.buffer.getColorBufferTexture());
        textureRegion.flip(false, true);
        Image image = new Image(textureRegion);
        image.setPosition((-this.buffer.getWidth()) / 2, (-this.buffer.getHeight()) / 2);
        batch.begin();
        image.draw(batch, parentAlpha);
    }

    public void reset() {
        this.buffer.begin();
        Gdx.gl20.glClear(16384);
        this.buffer.end();
    }

    public void stampToFrameBuffer() {
        this.buffer.begin();
        this.bufferBatch.begin();
        for (Sprite sprite : StageActivity.getActiveStageListener().getSpritesFromStage()) {
            PenConfiguration penConfiguration = sprite.penConfiguration;
            if (penConfiguration.hasStamp()) {
                sprite.look.draw(this.bufferBatch, 1.0f);
                penConfiguration.setStamp(false);
            }
        }
        this.bufferBatch.end();
        this.buffer.end();
    }

    public void dispose() {
        FrameBuffer frameBuffer = this.buffer;
        if (frameBuffer != null) {
            frameBuffer.dispose();
            this.buffer = null;
        }
        Batch batch = this.bufferBatch;
        if (batch != null) {
            batch.dispose();
            this.bufferBatch = null;
        }
    }

    private float calculateScreenRatio() {
        DisplayMetrics displayMetrics = Resources.getSystem().getDisplayMetrics();
        float fSqrt = (float) Math.sqrt(Math.pow(displayMetrics.widthPixels, 2.0d) + Math.pow(displayMetrics.heightPixels, 2.0d));
        XmlHeader xmlHeader = ProjectManager.getInstance().getCurrentProject().getXmlHeader();
        return ((float) Math.sqrt(Math.pow(xmlHeader.getVirtualScreenWidth(), 2.0d) + Math.pow(xmlHeader.getVirtualScreenHeight(), 2.0d))) / fSqrt;
    }
}

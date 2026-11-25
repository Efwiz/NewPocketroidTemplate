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
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.XmlHeader;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/stage/PlotActor.class
  classes14.dex
 */
/* JADX INFO: compiled from: PlotActor.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u000b\u001a\u00020\nH\u0002J\u0006\u0010\f\u001a\u00020\rJ\u0018\u0010\u000e\u001a\u00020\r2\u0006\u0010\u000f\u001a\u00020\u00062\u0006\u0010\u0010\u001a\u00020\nH\u0016J\u0006\u0010\u0011\u001a\u00020\rR\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0012"}, d2 = {"Lorg/catrobat/catroid/stage/PlotActor;", "Lcom/badlogic/gdx/scenes/scene2d/Actor;", "()V", "buffer", "Lcom/badlogic/gdx/graphics/glutils/FrameBuffer;", "bufferBatch", "Lcom/badlogic/gdx/graphics/g2d/Batch;", "camera", "Lcom/badlogic/gdx/graphics/OrthographicCamera;", "screenRatio", "", "calculateScreenRatio", "dispose", "", "draw", "batch", "parentAlpha", "reset", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class PlotActor extends Actor {
    private FrameBuffer buffer;
    private Batch bufferBatch;
    private final OrthographicCamera camera;
    private final float screenRatio;

    public PlotActor() {
        XmlHeader xmlHeader = ProjectManager.getInstance().getCurrentProject().getXmlHeader();
        this.buffer = new FrameBuffer(Pixmap.Format.RGBA8888, xmlHeader.virtualScreenWidth, xmlHeader.virtualScreenHeight, false);
        this.bufferBatch = new SpriteBatch();
        OrthographicCamera orthographicCamera = new OrthographicCamera(xmlHeader.getVirtualScreenWidth(), xmlHeader.getVirtualScreenHeight());
        this.camera = orthographicCamera;
        Batch batch = this.bufferBatch;
        Intrinsics.checkNotNull(batch, "null cannot be cast to non-null type com.badlogic.gdx.graphics.g2d.SpriteBatch");
        ((SpriteBatch) batch).setProjectionMatrix(orthographicCamera.combined);
        this.screenRatio = calculateScreenRatio();
        reset();
    }

    @Override // com.badlogic.gdx.scenes.scene2d.Actor
    public void draw(Batch batch, float parentAlpha) {
        StageListener stageListener;
        Intrinsics.checkNotNullParameter(batch, "batch");
        FrameBuffer frameBuffer = this.buffer;
        Intrinsics.checkNotNull(frameBuffer);
        frameBuffer.begin();
        StageActivity stageActivity = StageActivity.activeStageActivity.get();
        List<Sprite> spritesFromStage = (stageActivity == null || (stageListener = stageActivity.stageListener) == null) ? null : stageListener.getSpritesFromStage();
        Intrinsics.checkNotNull(spritesFromStage);
        Iterator<Sprite> it = spritesFromStage.iterator();
        while (it.hasNext()) {
            it.next().plot.drawLinesForSprite(this.screenRatio, getStage().getViewport().getCamera());
        }
        FrameBuffer frameBuffer2 = this.buffer;
        Intrinsics.checkNotNull(frameBuffer2);
        frameBuffer2.end();
        batch.end();
        FrameBuffer frameBuffer3 = this.buffer;
        Intrinsics.checkNotNull(frameBuffer3);
        TextureRegion textureRegion = new TextureRegion(frameBuffer3.getColorBufferTexture());
        textureRegion.flip(false, true);
        Image image = new Image(textureRegion);
        FrameBuffer frameBuffer4 = this.buffer;
        Intrinsics.checkNotNull(frameBuffer4);
        float f = (-frameBuffer4.getWidth()) / 2;
        FrameBuffer frameBuffer5 = this.buffer;
        Intrinsics.checkNotNull(frameBuffer5);
        image.setPosition(f, (-frameBuffer5.getHeight()) / 2);
        batch.begin();
        image.draw(batch, parentAlpha);
    }

    public final void reset() {
        FrameBuffer frameBuffer = this.buffer;
        Intrinsics.checkNotNull(frameBuffer);
        frameBuffer.begin();
        Gdx.gl20.glClear(16384);
        FrameBuffer frameBuffer2 = this.buffer;
        Intrinsics.checkNotNull(frameBuffer2);
        frameBuffer2.end();
    }

    public final void dispose() {
        FrameBuffer frameBuffer = this.buffer;
        if (frameBuffer != null) {
            Intrinsics.checkNotNull(frameBuffer);
            frameBuffer.dispose();
            this.buffer = null;
        }
        Batch batch = this.bufferBatch;
        if (batch != null) {
            Intrinsics.checkNotNull(batch);
            batch.dispose();
            this.bufferBatch = null;
        }
    }

    private final float calculateScreenRatio() {
        DisplayMetrics displayMetrics = Resources.getSystem().getDisplayMetrics();
        double d = 2;
        float fSqrt = (float) Math.sqrt(((float) Math.pow(displayMetrics.widthPixels, d)) + ((float) Math.pow(displayMetrics.heightPixels, d)));
        XmlHeader xmlHeader = ProjectManager.getInstance().getCurrentProject().getXmlHeader();
        return ((float) Math.sqrt(((float) Math.pow(xmlHeader.getVirtualScreenWidth(), d)) + ((float) Math.pow(xmlHeader.getVirtualScreenHeight(), d)))) / fSqrt;
    }
}

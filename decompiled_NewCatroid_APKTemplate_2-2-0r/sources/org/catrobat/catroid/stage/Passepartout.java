package org.catrobat.catroid.stage;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/stage/Passepartout.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class Passepartout extends Actor {
    private final Color passepartoutColor;
    private float passepartoutHeight;
    private float passepartoutWidth;
    private Texture texture;
    private float virtualScreenHeight;
    private float virtualScreenWidth;

    Passepartout(int screenWidth, int screenHeight, int screenViewPortWidth, int screenViewPortHeight, float virtualScreenWidth, float virtualScreenHeight) {
        Color color = Color.BLACK;
        this.passepartoutColor = color;
        this.virtualScreenWidth = virtualScreenWidth;
        this.virtualScreenHeight = virtualScreenHeight;
        this.passepartoutHeight = ((screenHeight / (screenViewPortHeight / virtualScreenHeight)) - virtualScreenHeight) / 2.0f;
        this.passepartoutWidth = ((screenWidth / (screenViewPortWidth / virtualScreenWidth)) - virtualScreenWidth) / 2.0f;
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(color);
        pixmap.fill();
        this.texture = new Texture(pixmap);
    }

    @Override // com.badlogic.gdx.scenes.scene2d.Actor
    public void draw(Batch batch, float parentAlpha) {
        batch.setColor(this.passepartoutColor);
        if (Float.compare(this.passepartoutWidth, 0.0f) != 0) {
            Texture texture = this.texture;
            float f = (-this.virtualScreenWidth) / 2.0f;
            float f2 = this.virtualScreenHeight;
            batch.draw(texture, f, (-f2) / 2.0f, -this.passepartoutWidth, f2);
            Texture texture2 = this.texture;
            float f3 = this.virtualScreenWidth / 2.0f;
            float f4 = this.virtualScreenHeight;
            batch.draw(texture2, f3, f4 / 2.0f, this.passepartoutWidth, -f4);
        }
        if (Float.compare(this.passepartoutHeight, 0.0f) != 0) {
            Texture texture3 = this.texture;
            float f5 = this.virtualScreenWidth;
            batch.draw(texture3, (-f5) / 2.0f, (-this.virtualScreenHeight) / 2.0f, f5, -this.passepartoutHeight);
            Texture texture4 = this.texture;
            float f6 = this.virtualScreenWidth;
            batch.draw(texture4, f6 / 2.0f, this.virtualScreenHeight / 2.0f, -f6, this.passepartoutHeight);
        }
        batch.flush();
    }

    @Override // com.badlogic.gdx.scenes.scene2d.Actor
    public Actor hit(float x, float y, boolean touchable) {
        float f = x - getStage().getViewport().getCamera().position.x;
        float f2 = y - getStage().getViewport().getCamera().position.y;
        float f3 = this.virtualScreenWidth;
        if (f >= (-f3) / 2.0f && f <= f3 / 2.0f) {
            float f4 = this.virtualScreenHeight;
            if (f2 >= (-f4) / 2.0f && f2 <= f4 / 2.0f) {
                return null;
            }
        }
        return this;
    }
}

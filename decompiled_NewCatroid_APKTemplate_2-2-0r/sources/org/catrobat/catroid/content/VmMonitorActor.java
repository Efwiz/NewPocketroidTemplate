package org.catrobat.catroid.content;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.scenes.scene2d.Actor;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/VmMonitorActor.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class VmMonitorActor extends Actor {
    private ShaderProgram shader;
    private Texture vmTexture;

    public VmMonitorActor(ShaderProgram shader) {
        this.shader = shader;
    }

    public void setTexture(Texture texture) {
        this.vmTexture = texture;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.Actor
    public void draw(Batch batch, float parentAlpha) {
        if (this.vmTexture == null) {
            return;
        }
        ShaderProgram shader = batch.getShader();
        float packedColor = batch.getPackedColor();
        boolean zIsBlendingEnabled = batch.isBlendingEnabled();
        batch.setShader(this.shader);
        batch.setColor(1.0f, 1.0f, 1.0f, parentAlpha);
        if (zIsBlendingEnabled) {
            batch.disableBlending();
        }
        batch.draw(this.vmTexture, getX(), getY(), getWidth(), getHeight());
        if (zIsBlendingEnabled) {
            batch.enableBlending();
        }
        batch.setPackedColor(packedColor);
        batch.setShader(shader);
    }
}

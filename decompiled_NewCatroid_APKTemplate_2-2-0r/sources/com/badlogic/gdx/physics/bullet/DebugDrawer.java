package com.badlogic.gdx.physics.bullet;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.linearmath.btIDebugDraw;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.Viewport;

/* JADX INFO: loaded from: classes8.dex */
public class DebugDrawer extends btIDebugDraw implements Disposable {
    private Camera camera;
    private BitmapFont font;
    private SpriteBatch spriteBatch;
    private Viewport viewport;
    private ShapeRenderer shapeRenderer = new ShapeRenderer();
    private boolean ownsShapeRenderer = true;
    private boolean ownsSpriteBatch = true;
    private boolean ownsFont = true;
    private int debugMode = 0;

    @Override // com.badlogic.gdx.physics.bullet.linearmath.btIDebugDraw
    public void drawLine(Vector3 vector3, Vector3 vector4, Vector3 vector5) {
        this.shapeRenderer.setColor(vector5.x, vector5.y, vector5.z, 1.0f);
        this.shapeRenderer.line(vector3, vector4);
    }

    @Override // com.badlogic.gdx.physics.bullet.linearmath.btIDebugDraw
    public void drawContactPoint(Vector3 vector3, Vector3 vector4, float f, int i, Vector3 vector5) {
        this.shapeRenderer.setColor(vector5.x, vector5.y, vector5.z, 1.0f);
        this.shapeRenderer.point(vector3.x, vector3.y, vector3.z);
        this.shapeRenderer.line(vector3, vector4.scl(f).add(vector3));
    }

    @Override // com.badlogic.gdx.physics.bullet.linearmath.btIDebugDraw
    public void drawTriangle(Vector3 vector3, Vector3 vector4, Vector3 vector5, Vector3 vector6, float f) {
        this.shapeRenderer.setColor(vector6.x, vector6.y, vector6.z, f);
        this.shapeRenderer.line(vector3, vector4);
        this.shapeRenderer.line(vector4, vector5);
        this.shapeRenderer.line(vector5, vector3);
    }

    @Override // com.badlogic.gdx.physics.bullet.linearmath.btIDebugDraw
    public void reportErrorWarning(String str) {
        Gdx.f82app.error("Bullet", str);
    }

    @Override // com.badlogic.gdx.physics.bullet.linearmath.btIDebugDraw
    public void draw3dText(Vector3 vector3, String str) {
        if (this.spriteBatch == null) {
            this.spriteBatch = new SpriteBatch();
        }
        if (this.font == null) {
            this.font = new BitmapFont();
        }
        if (this.camera.frustum.pointInFrustum(vector3)) {
            Viewport viewport = this.viewport;
            if (viewport != null) {
                this.camera.project(vector3, viewport.getScreenX(), this.viewport.getScreenY(), this.viewport.getScreenWidth(), this.viewport.getScreenHeight());
            } else {
                this.camera.project(vector3);
            }
            this.shapeRenderer.end();
            this.spriteBatch.begin();
            this.font.draw(this.spriteBatch, str, vector3.x, vector3.y, 0, str.length(), 0.0f, 1, false);
            this.spriteBatch.end();
            this.shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        }
    }

    @Override // com.badlogic.gdx.physics.bullet.linearmath.btIDebugDraw
    public void setDebugMode(int i) {
        this.debugMode = i;
    }

    @Override // com.badlogic.gdx.physics.bullet.linearmath.btIDebugDraw
    public int getDebugMode() {
        return this.debugMode;
    }

    public void begin(Camera camera) {
        this.camera = camera;
        this.shapeRenderer.setProjectionMatrix(camera.combined);
        this.shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
    }

    public void begin(Viewport viewport) {
        this.viewport = viewport;
        begin(viewport.getCamera());
    }

    public void end() {
        this.shapeRenderer.end();
    }

    public ShapeRenderer getShapeRenderer() {
        return this.shapeRenderer;
    }

    public void setShapeRenderer(ShapeRenderer shapeRenderer) {
        if (this.ownsShapeRenderer) {
            this.shapeRenderer.dispose();
        }
        this.shapeRenderer = shapeRenderer;
        this.ownsShapeRenderer = false;
    }

    public SpriteBatch getSpriteBatch() {
        return this.spriteBatch;
    }

    public void setSpriteBatch(SpriteBatch spriteBatch) {
        SpriteBatch spriteBatch2;
        if (this.ownsSpriteBatch && (spriteBatch2 = this.spriteBatch) != null) {
            spriteBatch2.dispose();
        }
        this.spriteBatch = spriteBatch;
        this.ownsSpriteBatch = false;
    }

    public BitmapFont getFont() {
        return this.font;
    }

    public void setFont(BitmapFont bitmapFont) {
        BitmapFont bitmapFont2;
        if (this.ownsFont && (bitmapFont2 = this.font) != null) {
            bitmapFont2.dispose();
        }
        this.font = bitmapFont;
        this.ownsFont = false;
    }

    @Override // com.badlogic.gdx.physics.bullet.BulletBase, com.badlogic.gdx.utils.Disposable
    public void dispose() {
        BitmapFont bitmapFont;
        SpriteBatch spriteBatch;
        super.dispose();
        if (this.ownsShapeRenderer) {
            this.shapeRenderer.dispose();
        }
        if (this.ownsSpriteBatch && (spriteBatch = this.spriteBatch) != null) {
            spriteBatch.dispose();
        }
        if (!this.ownsFont || (bitmapFont = this.font) == null) {
            return;
        }
        bitmapFont.dispose();
    }
}

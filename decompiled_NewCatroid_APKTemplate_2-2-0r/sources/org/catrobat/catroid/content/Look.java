package org.catrobat.catroid.content;

import android.graphics.PointF;
import android.util.Log;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEmitter;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.Viewport;
import java.io.File;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.concurrent.atomic.AtomicBoolean;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.common.LookData;
import org.catrobat.catroid.common.ThreadScheduler;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.content.actions.ScriptSequenceActionWithWaiter;
import org.catrobat.catroid.content.eventids.EventId;
import org.catrobat.catroid.sensing.CollisionInformation;
import org.catrobat.catroid.utils.GifDecoder;
import org.catrobat.catroid.utils.TouchUtil;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/Look.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class Look extends Image {
    private static final float COLOR_SCALE = 200.0f;
    public static final float DEGREE_UI_OFFSET = 90.0f;
    public static final int ROTATION_STYLE_ALL_AROUND = 1;
    public static final int ROTATION_STYLE_LEFT_RIGHT_ONLY = 0;
    public static final int ROTATION_STYLE_NONE = 2;
    private static final int UPDATE_BUCKETS = 4;
    private static int globalFrameTicker;
    private static ShaderProgram maskShader;
    private transient OrthographicCamera gameCamera;
    private transient Viewport gameViewport;
    private final transient Polygon hitboxPolygon;
    private final transient float[] hitboxVertices;
    protected LookData lookData;
    private int myUpdateBucket;
    private ParticleEffect particleEffect;
    protected Pixmap pixmap;
    private ThreadScheduler scheduler;
    private BrightnessContrastHueShader shader;
    public Sprite sprite;
    private transient Stage uiStage;
    private final AtomicBoolean collisionDirty = new AtomicBoolean(true);
    private boolean assumesConvexPolygons = true;
    private boolean lookVisible = true;
    private boolean simultaneousMovementXY = false;
    private int lookListIndexBeforeLookRequest = -1;
    private boolean useCustomShader = false;
    public LookData lookData2 = null;
    protected float alpha = 1.0f;
    protected float brightness = 1.0f;
    protected float hue = 0.0f;
    protected float height = 1.0f;
    protected float width = 1.0f;
    private int rotationMode = 1;
    private float rotation = 90.0f;
    private float realRotation = 90.0f;
    public boolean hasParticleEffect = false;
    public boolean isAdditive = true;
    private boolean isParticleEffectPaused = false;
    public boolean drawOnlyInBuffer = false;
    public String maskBufferName = null;
    public int maskMode = 0;
    private float topLeftOffsetX = 0.0f;
    private float topLeftOffsetY = 0.0f;
    private float topRightOffsetX = 0.0f;
    private float topRightOffsetY = 0.0f;
    private float bottomRightOffsetX = 0.0f;
    private float bottomRightOffsetY = 0.0f;
    private float bottomLeftOffsetX = 0.0f;
    private float bottomLeftOffsetY = 0.0f;
    private boolean hasCornerOffsets = false;
    private final transient float[] cornerOffsetsVerts = new float[20];
    private transient Animation<TextureRegion> activeGifAnimation = null;
    private float gifStateTime = 0.0f;
    private transient Animation<TextureRegion> activeSheetAnimation = null;
    private float sheetStateTime = 0.0f;
    private transient String cachedSheetKey = "";
    private transient TextureRegion[][] cachedSplitRegions = null;
    private final transient Vector3 tempVec3 = new Vector3();
    private Polygon[] cachedCollisionPolygons = null;

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/Look$RotationStyle.class
      classes14.dex
     */
    @Retention(RetentionPolicy.SOURCE)
    public @interface RotationStyle {
    }

    private boolean isAngleInCatroidInterval(float catroidAngle) {
        return catroidAngle > -180.0f && catroidAngle <= 180.0f;
    }

    public float getAngularVelocityInUserInterfaceDimensionUnit() {
        return 0.0f;
    }

    public Vector2 getPosition() {
        return new Vector2(getX(), getY());
    }

    public Look(final Sprite sprite) {
        this.myUpdateBucket = -1;
        float[] fArr = new float[8];
        this.hitboxVertices = fArr;
        this.hitboxPolygon = new Polygon(fArr);
        this.sprite = sprite;
        int i = globalFrameTicker + 1;
        globalFrameTicker = i;
        this.myUpdateBucket = i % 4;
        this.scheduler = new ThreadScheduler(this);
        setBounds(0.0f, 0.0f, 0.0f, 0.0f);
        setOrigin(0.0f, 0.0f);
        setScale(1.0f, 1.0f);
        setRotation(0.0f);
        setTouchable(Touchable.enabled);
        setAssumesConvexPolygons(false);
        addListeners();
    }

    public void setCornerOffsets(float tlX, float tlY, float trX, float trY, float brX, float brY, float blX, float blY) {
        this.topLeftOffsetX = tlX;
        this.topLeftOffsetY = tlY;
        this.topRightOffsetX = trX;
        this.topRightOffsetY = trY;
        this.bottomRightOffsetX = brX;
        this.bottomRightOffsetY = brY;
        this.bottomLeftOffsetX = blX;
        this.bottomLeftOffsetY = blY;
        this.hasCornerOffsets = (tlX == 0.0f && tlY == 0.0f && trX == 0.0f && trY == 0.0f && brX == 0.0f && brY == 0.0f && blX == 0.0f && blY == 0.0f) ? false : true;
    }

    protected void addListeners() {
        addListener(new InputListener() { // from class: org.catrobat.catroid.content.Look.1
            @Override // com.badlogic.gdx.scenes.scene2d.InputListener
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (Look.this.getTouchable() == Touchable.disabled || !Look.this.isLookVisible()) {
                    return false;
                }
                float stageX = event.getStageX();
                float stageY = event.getStageY();
                for (Polygon polygon : Look.this.getCurrentCollisionPolygon()) {
                    if (polygon.contains(stageX, stageY)) {
                        Look.this.sprite.look.fire(new EventWrapper(new EventId(1), false));
                        return true;
                    }
                }
                Look.this.setTouchable(Touchable.disabled);
                Actor actorHit = Look.this.getParent().hit(stageX, stageY, true);
                if (actorHit != null) {
                    actorHit.fire(event);
                    actorHit.fire(event);
                }
                Look.this.setTouchable(Touchable.enabled);
                return false;
            }

            @Override // com.badlogic.gdx.scenes.scene2d.InputListener
            public void touchDragged(InputEvent event, float x, float y, int pointer) {
                if (Look.this.getTouchable() == Touchable.disabled || !Look.this.isLookVisible()) {
                    return;
                }
                float stageX = event.getStageX();
                float stageY = event.getStageY();
                for (Polygon polygon : Look.this.getCurrentCollisionPolygon()) {
                    if (polygon.contains(stageX, stageY)) {
                        Look.this.sprite.look.fire(new EventWrapper(new EventId(13), false));
                        return;
                    }
                }
            }

            @Override // com.badlogic.gdx.scenes.scene2d.InputListener
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (Look.this.getTouchable() == Touchable.disabled || !Look.this.isLookVisible()) {
                    return;
                }
                float stageX = event.getStageX();
                float stageY = event.getStageY();
                for (Polygon polygon : Look.this.getCurrentCollisionPolygon()) {
                    if (polygon.contains(stageX, stageY)) {
                        Look.this.sprite.look.fire(new EventWrapper(new EventId(12), false));
                        return;
                    }
                }
            }
        });
        addListener(new EventWrapperListener(this));
    }

    private void initMaskShader() {
        if (maskShader == null) {
            ShaderProgram shaderProgram = new ShaderProgram("attribute vec4 a_position;\nattribute vec4 a_color;\nattribute vec2 a_texCoord0;\nuniform mat4 u_projTrans;\nvarying vec4 v_color;\nvarying vec2 v_texCoords;\nvarying vec4 v_screenPos;\nvoid main() {\n    v_color = a_color;\n    v_texCoords = a_texCoord0;\n    vec4 pos = u_projTrans * a_position;\n    v_screenPos = pos;\n    gl_Position = pos;\n}", "#ifdef GL_ES\n    precision mediump float;\n#endif\nvarying vec4 v_color;\nvarying vec2 v_texCoords;\nvarying vec4 v_screenPos;\nuniform sampler2D u_texture;\nuniform sampler2D u_mask;\nuniform int u_mode;\nvoid main() {\n    vec4 spriteColor = texture2D(u_texture, v_texCoords);\n    vec2 maskCoords;\n    if (u_mode == 0) {\n        maskCoords = v_texCoords;\n    } else {\n        vec2 clipCoords = (v_screenPos.xy / v_screenPos.w) * 0.5 + 0.5;\n        clipCoords.y = 1.0 - clipCoords.y;\n        maskCoords = clipCoords;\n    }\n    vec4 maskColor = texture2D(u_mask, maskCoords);\n    gl_FragColor = v_color * vec4(spriteColor.rgb, spriteColor.a * maskColor.a);\n}");
            maskShader = shaderProgram;
            if (shaderProgram.isCompiled()) {
                return;
            }
            Log.e("Look", "Error compiling Mask Shader: " + maskShader.getLog());
        }
    }

    public static void disposeMaskShader() {
        ShaderProgram shaderProgram = maskShader;
        if (shaderProgram != null) {
            shaderProgram.dispose();
            maskShader = null;
        }
    }

    public void playGif(final String filename) {
        stopGif();
        new Thread(new Runnable() { // from class: org.catrobat.catroid.content.Look.2
            @Override // java.lang.Runnable
            public void run() {
                try {
                    File file = ProjectManager.getInstance().getCurrentProject().getFile(filename);
                    if (file == null || !file.exists()) {
                        return;
                    }
                    FileHandle fileHandleAbsolute = Gdx.files.absolute(file.getAbsolutePath());
                    final GifDecoder gifDecoder = new GifDecoder();
                    if (gifDecoder.read(fileHandleAbsolute.read()) != 0 || gifDecoder.getFrameCount() <= 0) {
                        return;
                    }
                    Gdx.f82app.postRunnable(new Runnable() { // from class: org.catrobat.catroid.content.Look.2.1
                        @Override // java.lang.Runnable
                        public void run() {
                            try {
                                Array array = new Array();
                                int frameCount = gifDecoder.getFrameCount();
                                float delay = 0.0f;
                                for (int i = 0; i < frameCount; i++) {
                                    Pixmap frame = gifDecoder.getFrame(i);
                                    if (frame != null) {
                                        array.add(new TextureRegion(new Texture(frame)));
                                        delay += gifDecoder.getDelay(i) / 1000.0f;
                                        frame.dispose();
                                    }
                                }
                                float f = delay / frameCount;
                                if (f <= 0.0f) {
                                    f = 0.1f;
                                }
                                Look.this.activeGifAnimation = new Animation(f, array, Animation.PlayMode.LOOP);
                                Look.this.gifStateTime = 0.0f;
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void stopGif() {
        this.activeSheetAnimation = null;
        Animation<TextureRegion> animation = this.activeGifAnimation;
        if (animation != null) {
            for (TextureRegion textureRegion : animation.getKeyFrames()) {
                if (textureRegion instanceof TextureRegion) {
                    TextureRegion textureRegion2 = textureRegion;
                    if (textureRegion2.getTexture() != null) {
                        textureRegion2.getTexture().dispose();
                    }
                }
            }
            this.activeGifAnimation = null;
        }
        this.gifStateTime = 0.0f;
        refreshTextures(true);
    }

    public void playSpritesheet(int totalRows, int totalCols, int selectedRow, int frameCount, float frameDuration) {
        TextureRegion textureRegion;
        Texture texture;
        stopGif();
        this.activeSheetAnimation = null;
        this.sheetStateTime = 0.0f;
        LookData lookData = this.lookData;
        if (lookData == null || (textureRegion = lookData.getTextureRegion()) == null || (texture = textureRegion.getTexture()) == null) {
            return;
        }
        try {
            String str = texture.getTextureObjectHandle() + "_" + totalRows + "x" + totalCols;
            if (!str.equals(this.cachedSheetKey) || this.cachedSplitRegions == null) {
                this.cachedSheetKey = str;
                this.cachedSplitRegions = TextureRegion.split(texture, texture.getWidth() / totalCols, texture.getHeight() / totalRows);
            }
            int iMin = Math.min(selectedRow, totalRows - 1);
            int iMax = Math.max(0, iMin);
            int iMin2 = Math.min(frameCount, totalCols);
            Array array = new Array();
            for (int i = 0; i < iMin2; i++) {
                array.add(this.cachedSplitRegions[iMax][i]);
            }
            this.activeSheetAnimation = new Animation<>(frameDuration, array, Animation.PlayMode.LOOP);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stopSpritesheet() {
        this.activeSheetAnimation = null;
        this.sheetStateTime = 0.0f;
        refreshTextures(true);
    }

    public void setRenderingContext(OrthographicCamera gameCamera, Viewport gameViewport, Stage uiStage) {
        this.gameCamera = gameCamera;
        this.gameViewport = gameViewport;
        this.uiStage = uiStage;
    }

    public boolean isPinnedToCamera() {
        return getStage() != null && getStage() == this.uiStage;
    }

    public void setAssumesConvexPolygons(boolean convex) {
        this.assumesConvexPolygons = convex;
    }

    public boolean getAssumesConvexPolygons() {
        return this.assumesConvexPolygons;
    }

    public synchronized boolean isLookVisible() {
        return this.lookVisible;
    }

    public synchronized void setLookVisible(boolean lookVisible) {
        this.lookVisible = lookVisible;
        if (lookVisible) {
            setTouchable(Touchable.enabled);
        } else {
            setTouchable(Touchable.disabled);
        }
    }

    public synchronized int getLookListIndexBeforeLookRequest() {
        return this.lookListIndexBeforeLookRequest;
    }

    public synchronized void setLookListIndexBeforeLookRequest(int lookListIndexBeforeLookRequest) {
        this.lookListIndexBeforeLookRequest = lookListIndexBeforeLookRequest;
    }

    public void destroy() {
        notifyAllWaiters();
        setLookVisible(false);
        stopGif();
        stopSpritesheet();
        Array.ArrayIterator<EventListener> it = getListeners().iterator();
        while (it.hasNext()) {
            removeListener(it.next());
        }
        getActions().clear();
        this.scheduler = null;
        this.sprite = null;
        this.lookData = null;
        BrightnessContrastHueShader brightnessContrastHueShader = this.shader;
        if (brightnessContrastHueShader != null) {
            brightnessContrastHueShader.dispose();
            this.shader = null;
        }
        clearParticleEffect();
        super.remove();
    }

    @Override // com.badlogic.gdx.scenes.scene2d.Actor
    public boolean remove() {
        return super.remove();
    }

    public void copyTo(final Look destination) {
        destination.setLookVisible(isLookVisible());
        destination.setPositionInUserInterfaceDimensionUnit(getXInUserInterfaceDimensionUnit(), getYInUserInterfaceDimensionUnit());
        destination.setSizeInUserInterfaceDimensionUnit(getSizeInUserInterfaceDimensionUnit());
        destination.setTransparencyInUserInterfaceDimensionUnit(getTransparencyInUserInterfaceDimensionUnit());
        destination.setColorInUserInterfaceDimensionUnit(getColorInUserInterfaceDimensionUnit());
        destination.setRotationMode(getRotationMode());
        destination.setMotionDirectionInUserInterfaceDimensionUnit(getMotionDirectionInUserInterfaceDimensionUnit());
        destination.setBrightnessInUserInterfaceDimensionUnit(getBrightnessInUserInterfaceDimensionUnit());
        destination.hasParticleEffect = this.hasParticleEffect;
        destination.isAdditive = this.isAdditive;
    }

    public boolean doTouchDown(float x, float y, int pointer) {
        if (!isLookVisible()) {
            return false;
        }
        getCurrentCollisionPolygon();
        addListener(new InputListener() { // from class: org.catrobat.catroid.content.Look.3
            @Override // com.badlogic.gdx.scenes.scene2d.InputListener
            public boolean touchDown(InputEvent event, float x2, float y2, int pointer2, int button) {
                if (!Look.this.isLookVisible()) {
                    return false;
                }
                for (Polygon polygon : Look.this.getCurrentCollisionPolygon()) {
                    if (polygon.contains(event.getStageX(), event.getStageY())) {
                        Look.this.sprite.look.fire(new EventWrapper(new EventId(1), false));
                        return true;
                    }
                }
                Look.this.setTouchable(Touchable.disabled);
                Actor actorHit = Look.this.getParent().hit(event.getStageX(), event.getStageY(), true);
                if (actorHit != null) {
                    actorHit.fire(event);
                }
                Look.this.setTouchable(Touchable.enabled);
                return false;
            }
        });
        return false;
    }

    public synchronized void createBrightnessContrastHueShader() {
        BrightnessContrastHueShader brightnessContrastHueShader = this.shader;
        if (brightnessContrastHueShader != null) {
            brightnessContrastHueShader.dispose();
        }
        BrightnessContrastHueShader brightnessContrastHueShader2 = new BrightnessContrastHueShader();
        this.shader = brightnessContrastHueShader2;
        brightnessContrastHueShader2.setBrightness(this.brightness);
        this.shader.setHue(this.hue);
    }

    public ParticleEffect getParticleEffect() {
        if (this.particleEffect == null) {
            initialiseParticleEffect();
        }
        return this.particleEffect;
    }

    private void initialiseParticleEffect() {
        ParticleEffect particleEffect = new ParticleEffect();
        this.particleEffect = particleEffect;
        particleEffect.load(Gdx.files.internal("particles"), Gdx.files.internal(""));
        this.particleEffect.start();
    }

    public void pauseParticleEffect() {
        this.isParticleEffectPaused = true;
    }

    public void resumeParticleEffect() {
        this.isParticleEffectPaused = false;
    }

    public boolean isParticleEffectPaused() {
        return this.isParticleEffectPaused;
    }

    public void clearParticleEffect() {
        ParticleEffect particleEffect = this.particleEffect;
        if (particleEffect != null) {
            particleEffect.dispose();
            this.particleEffect = null;
        }
    }

    public void setHeightV(Float value) {
        this.height = value.floatValue();
        setScaleY(value.floatValue());
    }

    public void setWidthV(Float value) {
        this.height = value.floatValue();
        setScaleX(value.floatValue());
    }

    public ParticleEmitter getParticleEmitter() {
        return getParticleEffect().getEmitters().first();
    }

    private void setupParticleEffects(ParticleEmitter particleEmitter) {
        particleEmitter.setPosition(this.sprite.look.getX() + (this.sprite.look.getWidth() / 2.0f), this.sprite.look.getY() + (this.sprite.look.getHeight() / 2.0f));
        float sizeInUserInterfaceDimensionUnit = this.sprite.look.getSizeInUserInterfaceDimensionUnit() / 2.0f;
        float f = this.sprite.getLookList().size() == 0 ? sizeInUserInterfaceDimensionUnit / 32.0f : 1.0f;
        particleEmitter.getXScale().setHigh(sizeInUserInterfaceDimensionUnit);
        particleEmitter.getVelocity().setHighMin(120.0f * f);
        particleEmitter.getVelocity().setHighMax(f * 300.0f);
        particleEmitter.getGravity().setHigh(ProjectManager.getInstance().getCurrentlyPlayingScene().getPhysicsWorld().getGravity().y);
        particleEmitter.setAdditive(this.isAdditive);
    }

    private void fadeInParticles() {
        ParticleEmitter particleEmitter = getParticleEmitter();
        setupParticleEffects(particleEmitter);
        particleEmitter.setContinuous(true);
        particleEmitter.getLife().setHighMax(1000.0f);
        this.particleEffect.update(Gdx.graphics.getDeltaTime());
    }

    private void fadeOutParticles() {
        ParticleEmitter particleEmitter = getParticleEmitter();
        setupParticleEffects(particleEmitter);
        particleEmitter.setContinuous(false);
        particleEmitter.getLife().setHighMax(5000.0f);
        this.particleEffect.update(Gdx.graphics.getDeltaTime());
    }

    @Override // com.badlogic.gdx.scenes.scene2d.ui.Image, com.badlogic.gdx.scenes.scene2d.ui.Widget, com.badlogic.gdx.scenes.scene2d.Actor
    public synchronized void draw(Batch batch, float parentAlpha) {
        ShaderProgram shaderProgram;
        Batch batch2 = batch;
        synchronized (this) {
            if (!this.drawOnlyInBuffer || RenderTextureManager.INSTANCE.isRenderingToBuffer()) {
                ParticleEffect particleEffect = this.particleEffect;
                if (particleEffect != null) {
                    particleEffect.draw(batch2);
                    batch2.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
                }
                if (isLookVisible() && getDrawable() != null) {
                    super.setVisible(this.alpha != 0.0f);
                    if (this.maskBufferName != null) {
                        initMaskShader();
                        TextureRegion textureRegion = RenderTextureManager.INSTANCE.getTextureRegion(this.maskBufferName);
                        if (textureRegion != null && (shaderProgram = maskShader) != null && shaderProgram.isCompiled()) {
                            textureRegion.getTexture().bind(1);
                            Gdx.gl.glActiveTexture(GL20.GL_TEXTURE0);
                            batch2.setShader(maskShader);
                            maskShader.setUniformi("u_mask", 1);
                            maskShader.setUniformi("u_mode", this.maskMode);
                        }
                    } else {
                        BrightnessContrastHueShader brightnessContrastHueShader = this.shader;
                        if (brightnessContrastHueShader != null) {
                            batch2.setShader(brightnessContrastHueShader);
                        }
                    }
                    if (isLookVisible() && getDrawable() != null) {
                        if (this.hasCornerOffsets && (getDrawable() instanceof TextureRegionDrawable)) {
                            TextureRegion region = ((TextureRegionDrawable) getDrawable()).getRegion();
                            Texture texture = region.getTexture();
                            float x = getX();
                            float y = getY();
                            float width = getWidth() * getScaleX();
                            float height = getHeight() * getScaleY();
                            float originX = getOriginX();
                            float originY = getOriginY();
                            float f = this.bottomLeftOffsetX + x;
                            float f2 = this.bottomLeftOffsetY + y;
                            float f3 = this.topLeftOffsetX + x;
                            float f4 = height + y;
                            float f5 = this.topLeftOffsetY + f4;
                            float f6 = width + x;
                            float f7 = this.topRightOffsetX + f6;
                            float f8 = f4 + this.topRightOffsetY;
                            float f9 = f6 + this.bottomRightOffsetX;
                            float f10 = this.bottomRightOffsetY + y;
                            if (getRotation() != 0.0f) {
                                float fCosDeg = MathUtils.cosDeg(getRotation());
                                float fSinDeg = MathUtils.sinDeg(getRotation());
                                float f11 = x + originX;
                                float f12 = y + originY;
                                float f13 = f - f11;
                                float f14 = f2 - f12;
                                float f15 = ((f13 * fCosDeg) - (f14 * fSinDeg)) + f11;
                                f2 = (f13 * fSinDeg) + (f14 * fCosDeg) + f12;
                                float f16 = f3 - f11;
                                float f17 = f5 - f12;
                                float f18 = ((f16 * fCosDeg) - (f17 * fSinDeg)) + f11;
                                f5 = (f16 * fSinDeg) + (f17 * fCosDeg) + f12;
                                float f19 = f7 - f11;
                                float f20 = f8 - f12;
                                float f21 = ((f19 * fCosDeg) - (f20 * fSinDeg)) + f11;
                                f8 = (f19 * fSinDeg) + (f20 * fCosDeg) + f12;
                                float f22 = f9 - f11;
                                float f23 = f10 - f12;
                                float f24 = ((f22 * fCosDeg) - (f23 * fSinDeg)) + f11;
                                f10 = (f22 * fSinDeg) + (f23 * fCosDeg) + f12;
                                f9 = f24;
                                f3 = f18;
                                f7 = f21;
                                f = f15;
                            }
                            float u = region.getU();
                            float v2 = region.getV2();
                            float u2 = region.getU2();
                            float v = region.getV();
                            float packedColor = batch.getPackedColor();
                            float[] fArr = this.cornerOffsetsVerts;
                            fArr[0] = f;
                            fArr[1] = f2;
                            fArr[2] = packedColor;
                            fArr[3] = u;
                            fArr[4] = v2;
                            fArr[5] = f3;
                            fArr[6] = f5;
                            fArr[7] = packedColor;
                            fArr[8] = u;
                            fArr[9] = v;
                            fArr[10] = f7;
                            fArr[11] = f8;
                            fArr[12] = packedColor;
                            fArr[13] = u2;
                            fArr[14] = v;
                            fArr[15] = f9;
                            fArr[16] = f10;
                            fArr[17] = packedColor;
                            fArr[18] = u2;
                            fArr[19] = v2;
                            batch2 = batch;
                            batch2.draw(texture, fArr, 0, 20);
                        } else {
                            super.draw(batch2, this.alpha);
                        }
                    }
                    batch2.setShader(null);
                }
            }
        }
    }

    public static void tickGlobalFrame() {
        globalFrameTicker++;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.Actor
    public void act(float delta) {
        this.scheduler.tick(delta);
        Animation<TextureRegion> animation = this.activeGifAnimation;
        if (animation != null) {
            float f = this.gifStateTime + delta;
            this.gifStateTime = f;
            setDrawable(new TextureRegionDrawable(animation.getKeyFrame(f, true)));
        } else {
            Animation<TextureRegion> animation2 = this.activeSheetAnimation;
            if (animation2 != null) {
                float f2 = this.sheetStateTime + delta;
                this.sheetStateTime = f2;
                setDrawable(new TextureRegionDrawable(animation2.getKeyFrame(f2, true)));
            }
        }
        Sprite sprite = this.sprite;
        if (sprite == null || this.myUpdateBucket != globalFrameTicker % 4) {
            return;
        }
        sprite.runningStitch.update();
        this.sprite.evaluateConditionScriptTriggers();
    }

    @Override // com.badlogic.gdx.scenes.scene2d.Actor
    protected void positionChanged() {
        this.collisionDirty.set(true);
        super.positionChanged();
        Sprite sprite = this.sprite;
        if (sprite != null && sprite.penConfiguration != null && this.sprite.penConfiguration.isPenDown() && !this.simultaneousMovementXY) {
            this.sprite.penConfiguration.addPosition(new PointF(getXInUserInterfaceDimensionUnit(), getYInUserInterfaceDimensionUnit()));
        }
        Sprite sprite2 = this.sprite;
        if (sprite2 == null || sprite2.plot == null || !this.sprite.plot.getIsPlotting() || this.simultaneousMovementXY) {
            return;
        }
        this.sprite.plot.addPoint(new PointF(getXInUserInterfaceDimensionUnit(), getYInUserInterfaceDimensionUnit()));
    }

    public void startThread(ScriptSequenceAction sequenceAction) {
        ThreadScheduler threadScheduler = this.scheduler;
        if (threadScheduler != null) {
            threadScheduler.startThread(sequenceAction);
        }
    }

    public void stopThreads(Array<Action> threads) {
        ThreadScheduler threadScheduler = this.scheduler;
        if (threadScheduler != null) {
            threadScheduler.stopThreads(threads);
        }
    }

    public void stopThreadWithScript(Script script) {
        ThreadScheduler threadScheduler = this.scheduler;
        if (threadScheduler != null) {
            threadScheduler.stopThreadsWithScript(script);
        }
    }

    public void setSchedulerState(int state) {
        this.scheduler.setState(state);
    }

    @Override // com.badlogic.gdx.scenes.scene2d.Actor
    protected void rotationChanged() {
        this.collisionDirty.set(true);
        super.rotationChanged();
    }

    @Override // com.badlogic.gdx.scenes.scene2d.ui.Widget, com.badlogic.gdx.scenes.scene2d.Actor
    protected void sizeChanged() {
        this.collisionDirty.set(true);
        super.sizeChanged();
    }

    public synchronized void refreshTextures(boolean refreshShader) {
        LookData lookData = this.lookData;
        if (lookData == null) {
            setBounds(getX() + (getWidth() / 2.0f), getY() + (getHeight() / 2.0f), 0.0f, 0.0f);
            setDrawable(null);
            return;
        }
        Pixmap pixmap = lookData.getPixmap();
        this.pixmap = pixmap;
        if (pixmap != null) {
            float x = getX() - ((this.pixmap.getWidth() - getWidth()) / 2.0f);
            float y = getY() - ((this.pixmap.getHeight() - getHeight()) / 2.0f);
            setSize(this.pixmap.getWidth(), this.pixmap.getHeight());
            setPosition(x, y);
            setOrigin(getWidth() / 2.0f, getHeight() / 2.0f);
            setDrawable(new TextureRegionDrawable(this.lookData.getTextureRegion()));
            flipLookDataIfNeeded(getRotationMode());
            if (refreshShader) {
                refreshShader();
            }
        }
    }

    private void refreshShader() {
        createShaderIfNotExisting();
        this.shader.setBrightness(this.brightness);
        this.shader.setHue(this.hue);
    }

    public synchronized LookData getLookData() {
        return this.lookData;
    }

    public synchronized LookData getLookData2() {
        return this.lookData2;
    }

    public synchronized void setLookData(LookData lookData) {
        if (this.lookData != lookData) {
            this.lookData = lookData;
            this.lookData2 = lookData;
            this.collisionDirty.set(true);
            refreshTextures(false);
            Sprite sprite = this.sprite;
            if (sprite != null && sprite.getLookList() != null && this.sprite.getLookList().size() > 100) {
                int size = this.sprite.getLookList().size();
                int iIndexOf = this.sprite.getLookList().indexOf(lookData);
                if (iIndexOf != -1) {
                    for (int i = 0; i < size; i++) {
                        int iAbs = Math.abs(i - iIndexOf);
                        if (Math.min(iAbs, size - iAbs) > 30) {
                            this.sprite.getLookList().get(i).dispose();
                        }
                    }
                }
            }
        }
    }

    public synchronized void setLookData2(LookData lookData) {
        if (this.lookData2 != lookData) {
            this.lookData2 = lookData;
            this.collisionDirty.set(true);
            refreshTextures(false);
            Sprite sprite = this.sprite;
            if (sprite != null && sprite.getLookList() != null && this.sprite.getLookList().size() > 100) {
                int size = this.sprite.getLookList().size();
                int iIndexOf = this.sprite.getLookList().indexOf(lookData);
                if (iIndexOf != -1) {
                    for (int i = 0; i < size; i++) {
                        int iAbs = Math.abs(i - iIndexOf);
                        if (Math.min(iAbs, size - iAbs) > 30) {
                            this.sprite.getLookList().get(i).dispose();
                        }
                    }
                }
            }
        }
    }

    public boolean haveAllThreadsFinished() {
        return this.scheduler.haveAllThreadsFinished();
    }

    public synchronized String getImagePath() {
        String absolutePath;
        LookData lookData = this.lookData;
        if (lookData == null) {
            absolutePath = "";
        } else {
            absolutePath = lookData.getFile().getAbsolutePath();
        }
        return absolutePath;
    }

    public float getXInUserInterfaceDimensionUnit() {
        if (isPinnedToCamera()) {
            this.tempVec3.set(getX() + (getWidth() / 2.0f), getY() + (getHeight() / 2.0f), 0.0f);
            OrthographicCamera orthographicCamera = this.gameCamera;
            if (orthographicCamera != null) {
                orthographicCamera.unproject(this.tempVec3);
            }
            return this.tempVec3.x;
        }
        return getX() + (getWidth() / 2.0f);
    }

    public float getYInUserInterfaceDimensionUnit() {
        if (isPinnedToCamera()) {
            this.tempVec3.set(getX() + (getWidth() / 2.0f), getY() + (getHeight() / 2.0f), 0.0f);
            OrthographicCamera orthographicCamera = this.gameCamera;
            if (orthographicCamera != null) {
                orthographicCamera.unproject(this.tempVec3);
            }
            return this.tempVec3.y;
        }
        return getY() + (getHeight() / 2.0f);
    }

    public void setXInUserInterfaceDimensionUnit(float worldX) {
        if (isPinnedToCamera()) {
            this.tempVec3.set(worldX, getYInUserInterfaceDimensionUnit(), 0.0f);
            OrthographicCamera orthographicCamera = this.gameCamera;
            if (orthographicCamera != null) {
                orthographicCamera.project(this.tempVec3);
            }
            setX(this.tempVec3.x - (getWidth() / 2.0f));
            return;
        }
        setX(worldX - (getWidth() / 2.0f));
    }

    public void setYInUserInterfaceDimensionUnit(float worldY) {
        if (isPinnedToCamera()) {
            this.tempVec3.set(getXInUserInterfaceDimensionUnit(), worldY, 0.0f);
            OrthographicCamera orthographicCamera = this.gameCamera;
            if (orthographicCamera != null) {
                orthographicCamera.project(this.tempVec3);
            }
            setY(this.tempVec3.y - (getHeight() / 2.0f));
            return;
        }
        setY(worldY - (getHeight() / 2.0f));
    }

    public void setPositionInUserInterfaceDimensionUnit(float worldX, float worldY) {
        if (isPinnedToCamera()) {
            this.tempVec3.set(worldX, worldY, 0.0f);
            OrthographicCamera orthographicCamera = this.gameCamera;
            if (orthographicCamera != null) {
                orthographicCamera.project(this.tempVec3);
            }
            setPosition(this.tempVec3.x - (getWidth() / 2.0f), this.tempVec3.y - (getHeight() / 2.0f));
            return;
        }
        setPosition(worldX - (getWidth() / 2.0f), worldY - (getHeight() / 2.0f));
    }

    public float getDistanceToTouchPositionInUserInterfaceDimensions() {
        int lastTouchIndex = TouchUtil.getLastTouchIndex();
        return (float) Math.hypot(TouchUtil.getX(lastTouchIndex) - getXInUserInterfaceDimensionUnit(), TouchUtil.getY(lastTouchIndex) - getYInUserInterfaceDimensionUnit());
    }

    public float getXVelocityInUserInterfaceDimensionUnit() {
        if (this.sprite.isGliding()) {
            return this.sprite.getGlidingVelocityX();
        }
        return 0.0f;
    }

    public float getYVelocityInUserInterfaceDimensionUnit() {
        if (this.sprite.isGliding()) {
            return this.sprite.getGlidingVelocityY();
        }
        return 0.0f;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.Actor
    public void setPosition(float x, float y) {
        if (getX() == x && getY() == y) {
            return;
        }
        super.setPosition(x, y);
    }

    @Override // com.badlogic.gdx.scenes.scene2d.Actor
    public void setX(float x) {
        super.setX(x);
    }

    @Override // com.badlogic.gdx.scenes.scene2d.Actor
    public void setY(float y) {
        super.setY(y);
    }

    @Override // com.badlogic.gdx.scenes.scene2d.Actor
    public void setRotation(float degrees) {
        super.setRotation(degrees);
    }

    @Override // com.badlogic.gdx.scenes.scene2d.Actor
    public void setScale(float scaleXY) {
        if (getScaleX() == scaleXY && getScaleY() == scaleXY) {
            return;
        }
        super.setScale(scaleXY);
    }

    @Override // com.badlogic.gdx.scenes.scene2d.Actor
    public void setScale(float scaleX, float scaleY) {
        if (getScaleX() == scaleX && getScaleY() == scaleY) {
            return;
        }
        super.setScale(scaleX, scaleY);
    }

    @Override // com.badlogic.gdx.scenes.scene2d.Actor
    public void setScaleX(float scaleX) {
        if (getScaleX() != scaleX) {
            super.setScaleX(scaleX);
        }
    }

    @Override // com.badlogic.gdx.scenes.scene2d.Actor
    public void setScaleY(float scaleY) {
        if (getScaleY() != scaleY) {
            super.setScaleY(scaleY);
        }
    }

    private void adjustSimultaneousMovementXY(float x, float y) {
        this.simultaneousMovementXY = (x == getXInUserInterfaceDimensionUnit() || y == getYInUserInterfaceDimensionUnit()) ? false : true;
    }

    public void changeXInUserInterfaceDimensionUnit(float changeX) {
        setX(getX() + changeX);
    }

    public void changeYInUserInterfaceDimensionUnit(float changeY) {
        setY(getY() + changeY);
    }

    public void changePositionInInterfaceDimensionUnit(float changeX, float changeY) {
        setPosition(getX() + changeX, getY() + changeY);
    }

    public float getWidthInUserInterfaceDimensionUnit() {
        return getWidth() * this.width;
    }

    public float getHeightInUserInterfaceDimensionUnit() {
        return getHeight() * this.height;
    }

    public float getMotionDirectionInUserInterfaceDimensionUnit() {
        return this.realRotation;
    }

    public float getLookDirectionInUserInterfaceDimensionUnit() {
        int i = this.rotationMode;
        if (i == 0) {
            return isFlipped() ? -90.0f : 90.0f;
        }
        if (i != 1) {
            return i != 2 ? 0.0f : 90.0f;
        }
        return this.realRotation;
    }

    public void setRotationMode(int mode) {
        this.rotationMode = mode;
        flipLookDataIfNeeded(mode);
    }

    private void flipLookDataIfNeeded(int mode) {
        boolean z = getMotionDirectionInUserInterfaceDimensionUnit() < 0.0f;
        boolean z2 = mode != 0 && isFlipped();
        boolean z3 = mode == 0 && (isFlipped() ^ z);
        if (z2 || z3) {
            getLookData().getTextureRegion().flip(true, false);
            LookData lookData = this.lookData2;
            if (lookData == null || lookData == this.lookData) {
                return;
            }
            lookData.getTextureRegion().flip(true, false);
        }
    }

    public int getRotationMode() {
        return this.rotationMode;
    }

    private PointF rotatePointAroundPoint(PointF center, PointF point, float rotation) {
        double d = rotation;
        float fSin = (float) Math.sin(d);
        float fCos = (float) Math.cos(d);
        point.x -= center.x;
        point.y -= center.y;
        float f = (point.x * fCos) - (point.y * fSin);
        float f2 = (point.x * fSin) + (point.y * fCos);
        point.x = f + center.x;
        point.y = f2 + center.y;
        return point;
    }

    public Rectangle getHitbox() {
        float xInUserInterfaceDimensionUnit = getXInUserInterfaceDimensionUnit() - (getWidthInUserInterfaceDimensionUnit() / 2.0f);
        float yInUserInterfaceDimensionUnit = getYInUserInterfaceDimensionUnit() - (getHeightInUserInterfaceDimensionUnit() / 2.0f);
        float widthInUserInterfaceDimensionUnit = getWidthInUserInterfaceDimensionUnit();
        float heightInUserInterfaceDimensionUnit = getHeightInUserInterfaceDimensionUnit();
        float[] fArr = this.hitboxVertices;
        fArr[0] = xInUserInterfaceDimensionUnit;
        fArr[1] = yInUserInterfaceDimensionUnit;
        fArr[2] = xInUserInterfaceDimensionUnit;
        float f = yInUserInterfaceDimensionUnit + heightInUserInterfaceDimensionUnit;
        fArr[3] = f;
        float f2 = xInUserInterfaceDimensionUnit + widthInUserInterfaceDimensionUnit;
        fArr[4] = f2;
        fArr[5] = f;
        fArr[6] = f2;
        fArr[7] = yInUserInterfaceDimensionUnit;
        this.hitboxPolygon.setVertices(fArr);
        this.hitboxPolygon.setPosition(0.0f, 0.0f);
        this.hitboxPolygon.setOrigin(xInUserInterfaceDimensionUnit + (widthInUserInterfaceDimensionUnit / 2.0f), yInUserInterfaceDimensionUnit + (heightInUserInterfaceDimensionUnit / 2.0f));
        this.hitboxPolygon.setRotation(getRotation());
        return this.hitboxPolygon.getBoundingRectangle();
    }

    public void setMotionDirectionInUserInterfaceDimensionUnit(float degrees) {
        LookData lookData;
        float f = ((-degrees) + 90.0f) % 360.0f;
        this.rotation = f;
        this.realRotation = convertStageAngleToCatroidAngle(f);
        int i = this.rotationMode;
        if (i != 0) {
            if (i == 1) {
                setRotation(this.rotation);
                return;
            } else {
                if (i != 2) {
                    return;
                }
                setRotation(0.0f);
                return;
            }
        }
        setRotation(0.0f);
        float f2 = this.realRotation;
        boolean z = f2 >= 0.0f;
        boolean z2 = f2 < 0.0f;
        if ((!(isFlipped() && z) && (isFlipped() || !z2)) || (lookData = this.lookData) == null) {
            return;
        }
        lookData.getTextureRegion().flip(true, false);
        LookData lookData2 = this.lookData2;
        if (lookData2 == null || lookData2 == this.lookData) {
            return;
        }
        lookData2.getTextureRegion().flip(true, false);
    }

    public boolean isFlipped() {
        LookData lookData = this.lookData;
        return lookData != null && lookData.getTextureRegion().isFlipX();
    }

    public void changeDirectionInUserInterfaceDimensionUnit(float changeDegrees) {
        setMotionDirectionInUserInterfaceDimensionUnit((getMotionDirectionInUserInterfaceDimensionUnit() + changeDegrees) % 360.0f);
    }

    public float getSizeInUserInterfaceDimensionUnit() {
        return getScaleX() * 100.0f;
    }

    public void setSizeInUserInterfaceDimensionUnit(float percent) {
        float f = percent / 100.0f;
        this.height = f;
        this.width = f;
        setScale(f, f);
    }

    public void SetSizeX(float percent) {
        setScale(percent / 100.0f, getScaleY());
    }

    public void SetSizeY(float percent) {
        setScale(getScaleX(), percent / 100.0f);
    }

    public void changeSizeInUserInterfaceDimensionUnit(float changePercent) {
        setSizeInUserInterfaceDimensionUnit(getSizeInUserInterfaceDimensionUnit() + changePercent);
    }

    public float getTransparencyInUserInterfaceDimensionUnit() {
        return (1.0f - this.alpha) * 100.0f;
    }

    public void setTransparencyInUserInterfaceDimensionUnit(float percent) {
        if (percent < 100.0f) {
            if (percent < 0.0f) {
                percent = 0.0f;
            }
            setVisible(true);
        } else {
            setVisible(false);
            percent = 100.0f;
        }
        this.alpha = (100.0f - percent) / 100.0f;
    }

    public void changeTransparencyInUserInterfaceDimensionUnit(float changePercent) {
        setTransparencyInUserInterfaceDimensionUnit(getTransparencyInUserInterfaceDimensionUnit() + changePercent);
    }

    public float getBrightnessInUserInterfaceDimensionUnit() {
        return this.brightness * 100.0f;
    }

    public synchronized void setBrightnessInUserInterfaceDimensionUnit(float percent) {
        if (percent < 0.0f) {
            percent = 0.0f;
        } else if (percent > 200.0f) {
            percent = 200.0f;
        }
        float f = percent / 100.0f;
        this.brightness = f;
        this.useCustomShader = (f == 1.0f && this.hue == 0.0f) ? false : true;
        refreshTextures(true);
    }

    public void changeBrightnessInUserInterfaceDimensionUnit(float changePercent) {
        setBrightnessInUserInterfaceDimensionUnit(getBrightnessInUserInterfaceDimensionUnit() + changePercent);
    }

    public float getColorInUserInterfaceDimensionUnit() {
        return this.hue * 200.0f;
    }

    public synchronized void setColorInUserInterfaceDimensionUnit(float val) {
        float f = val % 200.0f;
        if (f < 0.0f) {
            f += 200.0f;
        }
        float f2 = f / 200.0f;
        this.hue = f2;
        this.useCustomShader = (this.brightness == 1.0f && f2 == 0.0f) ? false : true;
        refreshTextures(true);
    }

    private void createShaderIfNotExisting() {
        if (this.shader == null) {
            createBrightnessContrastHueShader();
        }
    }

    public void changeColorInUserInterfaceDimensionUnit(float val) {
        setColorInUserInterfaceDimensionUnit(getColorInUserInterfaceDimensionUnit() + val);
    }

    public boolean needsCustomShader() {
        return this.useCustomShader;
    }

    public float getBrightnessValue() {
        return this.brightness;
    }

    public float getHueValue() {
        return this.hue;
    }

    public void applyShaderParameters(ShaderProgram customShader) {
        if (customShader instanceof BrightnessContrastHueShader) {
            BrightnessContrastHueShader brightnessContrastHueShader = (BrightnessContrastHueShader) customShader;
            brightnessContrastHueShader.setBrightness(this.brightness);
            brightnessContrastHueShader.setHue(this.hue);
        }
    }

    public float breakDownCatroidAngle(float catroidAngle) {
        float f = catroidAngle % 360.0f;
        if (f < 0.0f || isAngleInCatroidInterval(f)) {
            return (f >= 0.0f || isAngleInCatroidInterval(f)) ? f : f + 360.0f;
        }
        return f - 360.0f;
    }

    public float convertCatroidAngleToStageAngle(float catroidAngle) {
        return (-breakDownCatroidAngle(catroidAngle)) + 90.0f;
    }

    public float convertStageAngleToCatroidAngle(float stageAngle) {
        return breakDownCatroidAngle((-stageAngle) + 90.0f);
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/Look$BrightnessContrastHueShader.class
      classes14.dex
     */
    public static class BrightnessContrastHueShader extends ShaderProgram {
        private static final String BRIGHTNESS_STRING_IN_SHADER = "brightness";
        private static final String CONTRAST_STRING_IN_SHADER = "contrast";
        private static final String FRAGMENT_SHADER = "#ifdef GL_ES\n    #define LOWP lowp\n    precision mediump float;\n#else\n    #define LOWP\n#endif\nvarying LOWP vec4 v_color;\nvarying vec2 v_texCoords;\nuniform sampler2D u_texture;\nuniform float brightness;\nuniform float contrast;\nuniform float hue;\nvec3 rgb2hsv(vec3 c)\n{\n    vec4 K = vec4(0.0, -1.0 / 3.0, 2.0 / 3.0, -1.0);\n    vec4 p = mix(vec4(c.bg, K.wz), vec4(c.gb, K.xy), step(c.b, c.g));\n    vec4 q = mix(vec4(p.xyw, c.r), vec4(c.r, p.yzx), step(p.x, c.r));\n    float d = q.x - min(q.w, q.y);\n    float e = 1.0e-10;\n    return vec3(abs(q.z + (q.w - q.y) / (6.0 * d + e)), d / (q.x + e), q.x);\n}\nvec3 hsv2rgb(vec3 c)\n{\n    vec4 K = vec4(1.0, 2.0 / 3.0, 1.0 / 3.0, 3.0);\n    vec3 p = abs(fract(c.xxx + K.xyz) * 6.0 - K.www);\n    return c.z * mix(K.xxx, clamp(p - K.xxx, 0.0, 1.0), c.y);\n}\nvoid main()\n{\n    vec4 color = v_color * texture2D(u_texture, v_texCoords);\n    color.rgb /= color.a;\n    color.rgb = ((color.rgb - 0.5) * max(contrast, 0.0)) + 0.5;\n    color.rgb += brightness;\n    color.rgb *= color.a;\n    vec3 hsv = rgb2hsv(color.rgb);\n    hsv.x += hue;\n    vec3 rgb = hsv2rgb(hsv);\n    gl_FragColor = vec4(rgb.r, rgb.g, rgb.b, color.a);\n }";
        private static final String HUE_STRING_IN_SHADER = "hue";
        private static final String VERTEX_SHADER = "attribute vec4 a_position;\nattribute vec4 a_color;\nattribute vec2 a_texCoord0;\nuniform mat4 u_projTrans;\nvarying vec4 v_color;\nvarying vec2 v_texCoords;\n\nvoid main()\n{\n v_color = a_color;\n v_texCoords = a_texCoord0;\n gl_Position = u_projTrans * a_position;\n}\n";

        public BrightnessContrastHueShader() {
            super(VERTEX_SHADER, FRAGMENT_SHADER);
            ShaderProgram.pedantic = false;
            if (isCompiled()) {
                begin();
                setUniformf(BRIGHTNESS_STRING_IN_SHADER, 0.0f);
                setUniformf(CONTRAST_STRING_IN_SHADER, 1.0f);
                setUniformf(HUE_STRING_IN_SHADER, 0.0f);
                end();
            }
        }

        public void setBrightness(float brightness) {
            begin();
            setUniformf(BRIGHTNESS_STRING_IN_SHADER, brightness - 1.0f);
            end();
        }

        public void setHue(float hue) {
            begin();
            setUniformf(HUE_STRING_IN_SHADER, hue);
            end();
        }
    }

    public Polygon[] getCurrentCollisionPolygon() {
        Polygon[] polygonArr;
        if (this.cachedCollisionPolygons != null && !this.collisionDirty.get()) {
            return this.cachedCollisionPolygons;
        }
        if (getLookData2() == null) {
            polygonArr = new Polygon[0];
        } else {
            CollisionInformation collisionInformation = getLookData2().getCollisionInformation();
            if (collisionInformation.collisionPolygons == null) {
                collisionInformation.loadCollisionPolygon();
            }
            polygonArr = collisionInformation.collisionPolygons;
        }
        Polygon[] polygonArr2 = this.cachedCollisionPolygons;
        if (polygonArr2 == null || polygonArr2.length != polygonArr.length) {
            this.cachedCollisionPolygons = new Polygon[polygonArr.length];
            for (int i = 0; i < polygonArr.length; i++) {
                this.cachedCollisionPolygons[i] = new Polygon(polygonArr[i].getVertices());
            }
        }
        int i2 = 0;
        while (true) {
            Polygon[] polygonArr3 = this.cachedCollisionPolygons;
            if (i2 < polygonArr3.length) {
                Polygon polygon = polygonArr3[i2];
                polygon.setVertices(polygonArr[i2].getVertices());
                polygon.setPosition(getX(), getY());
                polygon.setRotation(getRotation());
                polygon.setScale(getScaleX(), getScaleY());
                polygon.setOrigin(getOriginX(), getOriginY());
                i2++;
            } else {
                this.collisionDirty.set(false);
                return this.cachedCollisionPolygons;
            }
        }
    }

    void notifyAllWaiters() {
        Array.ArrayIterator<Action> it = getActions().iterator();
        while (it.hasNext()) {
            Action next = it.next();
            if (next instanceof ScriptSequenceActionWithWaiter) {
                ((ScriptSequenceActionWithWaiter) next).notifyWaiter();
            }
        }
    }

    public float getAlpha() {
        return this.alpha;
    }

    public float getBrightness() {
        return this.brightness;
    }
}

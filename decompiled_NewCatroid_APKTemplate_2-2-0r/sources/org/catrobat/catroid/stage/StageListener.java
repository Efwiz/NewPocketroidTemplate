package org.catrobat.catroid.stage;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.SystemClock;
import android.util.DisplayMetrics;
import android.util.Log;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.StringBuilder;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.beust.jcommander.Parameters;
import com.gaurav.avnc.vnc.VncClient;
import com.google.common.collect.Multimap;
import com.huawei.hms.support.api.entity.core.JosStatusCodes;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentLinkedQueue;
import kotlinx.coroutines.GlobalScope;
import org.apache.commons.compress.harmony.pack200.PackingOptions;
import org.catrobat.catroid.CatroidApplication;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.camera.CameraManager;
import org.catrobat.catroid.common.Constants;
import org.catrobat.catroid.common.LookData;
import org.catrobat.catroid.common.NativeViewBindingManager;
import org.catrobat.catroid.common.NewCatroidBackgroundService;
import org.catrobat.catroid.common.ScreenModes;
import org.catrobat.catroid.common.ScreenValues;
import org.catrobat.catroid.content.AfterUpdateScript;
import org.catrobat.catroid.content.BeforeUpdateScript;
import org.catrobat.catroid.content.EventWrapper;
import org.catrobat.catroid.content.ExitProjectScript;
import org.catrobat.catroid.content.GlobalManager;
import org.catrobat.catroid.content.Look;
import org.catrobat.catroid.content.Project;
import org.catrobat.catroid.content.RenderTextureManager;
import org.catrobat.catroid.content.Scene;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.content.Script;
import org.catrobat.catroid.content.SoundBackup;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.SquareActor;
import org.catrobat.catroid.content.SquareController;
import org.catrobat.catroid.content.VmMonitorActor;
import org.catrobat.catroid.content.WhenNotificationActionTriggeredScript;
import org.catrobat.catroid.content.WhenNotificationClickedScript;
import org.catrobat.catroid.content.XmlHeader;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.content.bricks.WhenNotificationActionTriggeredBrick;
import org.catrobat.catroid.content.bricks.WhenNotificationClickedBrick;
import org.catrobat.catroid.content.eventids.EventId;
import org.catrobat.catroid.content.eventids.GamepadEventId;
import org.catrobat.catroid.embroidery.DSTPatternManager;
import org.catrobat.catroid.embroidery.EmbroideryPatternManager;
import org.catrobat.catroid.fast2d.FastTwoDManager;
import org.catrobat.catroid.formulaeditor.SensorHandler;
import org.catrobat.catroid.formulaeditor.UserDataWrapper;
import org.catrobat.catroid.io.SoundCacheManager;
import org.catrobat.catroid.io.SoundManager;
import org.catrobat.catroid.physics.PhysicsLook;
import org.catrobat.catroid.physics.PhysicsObject;
import org.catrobat.catroid.physics.PhysicsWorld;
import org.catrobat.catroid.physics.shapebuilder.PhysicsShapeBuilder;
import org.catrobat.catroid.pocketmusic.mididriver.MidiSoundManager;
import org.catrobat.catroid.raptor.SceneManager;
import org.catrobat.catroid.raptor.ThreeDManager;
import org.catrobat.catroid.sensing.ColorAtXYDetection;
import org.catrobat.catroid.ui.MainMenuActivity;
import org.catrobat.catroid.ui.dialogs.DebugMenuManager;
import org.catrobat.catroid.ui.dialogs.StageDialog;
import org.catrobat.catroid.ui.recyclerview.controller.SpriteController;
import org.catrobat.catroid.utils.ActionThreadRegistry;
import org.catrobat.catroid.utils.GlobalShaderManager;
import org.catrobat.catroid.utils.NewCatroidMqttManager;
import org.catrobat.catroid.utils.NewCatroidNotificationManager;
import org.catrobat.catroid.utils.PerformanceTracker;
import org.catrobat.catroid.utils.Resolution;
import org.catrobat.catroid.utils.TouchUtil;
import org.catrobat.catroid.utils.VibrationManager;
import org.catrobat.catroid.utils.lunoscript.RenderManager;
import org.catrobat.catroid.web.WebConnectionHolder;
import org.koin.java.KoinJavaComponent;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/stage/StageListener.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class StageListener implements ApplicationListener {
    private static final int ACTIONS_COMPUTATION_TIME_MAXIMUM = 8;
    private static final Color AXIS_COLOR = new Color(-16773889);
    private static final float AXIS_FONT_SIZE_SCALE_FACTOR = 0.025f;
    private static final int AXIS_WIDTH = 4;
    private static final float DELTA_ACTIONS_DIVIDER_MAXIMUM = 10.0f;
    private static final int Z_LAYER_EMBROIDERY_ACTOR = 2;
    private static final int Z_LAYER_PEN_ACTOR = 1;
    private Texture axes;
    private Look.BrightnessContrastHueShader brightnessContrastHueShader;
    private OrthographicCamera camera;
    public CameraPositioner cameraPositioner;
    public EmbroideryPatternManager embroideryPatternManager;
    private Environment environment;
    public FastTwoDManager fastTwoDManager;
    private TextureRegion fboRegion;
    private BitmapFont font;
    private Mesh fullscreenQuad;
    private InputMultiplexer inputMultiplexer;
    private InputProcessor mouseInputAdapter;
    private Passepartout passepartout;
    private PenActor penActor;
    private PhysicsWorld physicsWorld;
    private PlotActor plotActor;
    private SpriteBatch postProcessBatch;
    private FrameBuffer postProcessFbo;
    private ShaderProgram postProcessShader;
    private Project project;
    private Scene scene;
    private FrameBuffer sceneFbo;
    public SceneManager sceneManager;
    private int screenshotHeight;
    private String screenshotName;
    private ScreenshotSaver screenshotSaver;
    private int screenshotWidth;
    private int screenshotX;
    private int screenshotY;
    public ShapeRenderer shapeRenderer;
    private List<Sprite> sprites;
    private StageDialog stageDialog;
    private byte[] testPixels;
    private ThreeDManager threeDManager;
    private OrthographicCamera uiCamera;
    private Viewport uiViewPort;
    private Viewport viewPort;
    private float virtualHeight;
    private float virtualHeightHalf;
    private float virtualWidth;
    private float virtualWidthHalf;
    private float vmHeight;
    private VmMonitorActor vmMonitorActor;
    private Mesh vmScreenMesh;
    private Texture vmTexture;
    private float vmWidth;
    private float vmX;
    private float vmY;
    private ShaderProgram vncSwizzleShader;
    private double accumulator = 0.0d;
    private final double TIME_STEP = 0.016666666666666666d;
    private final double MAX_ACCUMULATOR = 0.25d;
    private float deltaActionTimeDivisor = 10.0f;
    private Stage stage = null;
    private Stage uiStage = null;
    private boolean paused = true;
    private boolean finished = false;
    private boolean reloadProject = false;
    public boolean firstFrameDrawn = false;
    private boolean makeScreenshot = false;
    private Batch batch = null;
    private float time = 0.0f;
    private boolean makeTestPixels = false;
    private int testX = 0;
    private int testY = 0;
    private int testWidth = 0;
    private int testHeight = 0;
    private Resolution maxViewPort = null;
    private float cameraRotation = 0.0f;
    public boolean axesOn = false;
    private String lastFragmentShaderCode = null;
    private final String POST_PROCESS_VERTEX_SHADER = "attribute vec4 a_position;\nattribute vec2 a_texCoord0;\nvarying vec2 v_texCoords;\n\nvoid main()\n{\n    v_texCoords = a_texCoord0;\n    gl_Position = a_position;\n}\n";
    private Map<String, StageBackup> stageBackupMap = new HashMap();
    private InputListener inputListener = null;
    private Map<Sprite, ShowBubbleActor> bubbleActorMap = new HashMap();
    private ScreenshotSaverCallback screenshotSaverCallback = null;
    private final StringBuilder debugTextBuilder = new StringBuilder();
    private final List<ScriptSequenceAction> beforeUpdateActions = new ArrayList();
    private boolean hasBeforeUpdateScripts = false;
    private final List<ScriptSequenceAction> afterUpdateActions = new ArrayList();
    private boolean hasAfterUpdateScripts = false;
    private boolean isFirstResize = true;
    private final Map<Sprite, PinnedState> pinnedSprites = new HashMap();
    public boolean isBackgroundModeEnabled = false;
    private boolean vmGeometryDirty = false;
    private boolean isVmDisplayVisible = false;
    private final Vector3 tempVec3ForTouch = new Vector3();
    private final Vector3 tempVec3 = new Vector3();
    private volatile boolean captureNextFrame = false;
    public WebConnectionHolder webConnectionHolder = new WebConnectionHolder();

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/stage/StageListener$PinnedState.class
      classes14.dex
     */
    public static class PinnedState {
        public float originalRotation;
        public float originalScaleX;
        public float originalScaleY;
        public float rx;
        public float ry;
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/stage/StageListener$StageBackup.class
      classes14.dex
     */
    public static class StageBackup {
        Array<Actor> actors;
        boolean axesOn;
        Batch batch;
        Map<Sprite, ShowBubbleActor> bubbleActorMap;
        OrthographicCamera camera;
        boolean cameraRunning;
        float deltaActionTimeDivisor;
        EmbroideryPatternManager embroideryPatternManager;
        boolean finished;
        boolean flashState;
        BitmapFont font;
        Passepartout passepartout;
        boolean paused;
        PenActor penActor;
        PhysicsWorld physicsWorld;
        PlotActor plotActor;
        boolean reloadProject;
        List<SoundBackup> soundBackupList;
        Sprite spriteToFocusOn;
        List<Sprite> sprites;
        long timeToVibrate;
        Viewport viewPort;
    }

    public void setMaxViewPort(Resolution maxViewPort) {
        this.maxViewPort = maxViewPort;
    }

    public ThreeDManager getThreeDManager() {
        return this.threeDManager;
    }

    public SceneManager getSceneManager() {
        return this.sceneManager;
    }

    @Override // com.badlogic.gdx.ApplicationListener
    public void create() {
        this.isFirstResize = true;
        ActionThreadRegistry.clear();
        this.deltaActionTimeDivisor = 10.0f;
        Stage stage = this.stage;
        if (stage != null) {
            stage.dispose();
            this.stage = null;
        }
        Stage stage2 = this.uiStage;
        if (stage2 != null) {
            stage2.dispose();
            this.uiStage = null;
        }
        ShapeRenderer shapeRenderer = this.shapeRenderer;
        if (shapeRenderer != null) {
            shapeRenderer.dispose();
            this.shapeRenderer = null;
        }
        Look.BrightnessContrastHueShader brightnessContrastHueShader = this.brightnessContrastHueShader;
        if (brightnessContrastHueShader != null) {
            brightnessContrastHueShader.dispose();
            this.brightnessContrastHueShader = null;
        }
        ShaderProgram shaderProgram = this.vncSwizzleShader;
        if (shaderProgram != null) {
            shaderProgram.dispose();
            this.vncSwizzleShader = null;
        }
        ThreeDManager threeDManager = this.threeDManager;
        if (threeDManager != null) {
            threeDManager.dispose();
            this.threeDManager = null;
        }
        SceneManager sceneManager = this.sceneManager;
        if (sceneManager != null) {
            sceneManager.clearScene();
        }
        Texture texture = this.axes;
        if (texture != null) {
            texture.dispose();
            this.axes = null;
        }
        SquareController.getInstance().clearSquares();
        SquareActor.disposeShared();
        this.brightnessContrastHueShader = new Look.BrightnessContrastHueShader();
        this.shapeRenderer = new ShapeRenderer();
        this.project = ProjectManager.getInstance().getCurrentProject();
        this.scene = ProjectManager.getInstance().getCurrentlyPlayingScene();
        ThreeDManager threeDManager2 = new ThreeDManager();
        this.threeDManager = threeDManager2;
        threeDManager2.init();
        this.sceneManager = new SceneManager(this.threeDManager);
        Stage stage3 = this.stage;
        if (stage3 == null) {
            createNewStage();
            Gdx.input.setInputProcessor(this.inputMultiplexer);
        } else {
            stage3.getRoot().clear();
            this.uiStage.getRoot().clear();
        }
        initScreenMode();
        initStageInputListener();
        this.screenshotSaver = new ScreenshotSaver(Gdx.files, getScreenshotPath(), this.screenshotWidth, this.screenshotHeight);
        this.font = getLabelFont(this.project);
        this.physicsWorld = this.scene.resetPhysicsWorld();
        this.sprites = new ArrayList(this.scene.getSpriteList());
        resetConditionScriptTriggers();
        this.embroideryPatternManager = new DSTPatternManager();
        initActors(this.sprites);
        SoundCacheManager.getInstance().initialize();
        RenderManager.INSTANCE.initialize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.f82app.log("CacheWarming", "Starting asset pre-loading...");
        for (Sprite sprite : this.sprites) {
            if (sprite.getLookList() != null) {
                for (LookData lookData : sprite.getLookList()) {
                    if (lookData != null) {
                        lookData.getCollisionInformation().loadCollisionPolygon();
                    }
                }
            }
        }
        Gdx.f82app.log("CacheWarming", "Pre-loading finished.");
        this.passepartout = new Passepartout(ScreenValues.currentScreenResolution.getWidth(), ScreenValues.currentScreenResolution.getHeight(), this.maxViewPort.getWidth(), this.maxViewPort.getHeight(), this.virtualWidth, this.virtualHeight);
        this.axes = new Texture(Gdx.files.internal("stage/red_pixel.bmp"));
        if (this.fullscreenQuad == null) {
            Mesh mesh = new Mesh(true, 4, 6, new VertexAttribute(1, 2, ShaderProgram.POSITION_ATTRIBUTE), new VertexAttribute(16, 2, "a_texCoord0"));
            this.fullscreenQuad = mesh;
            mesh.setVertices(new float[]{-1.0f, -1.0f, 0.0f, 0.0f, 1.0f, -1.0f, 1.0f, 0.0f, 1.0f, 1.0f, 1.0f, 1.0f, -1.0f, 1.0f, 0.0f, 1.0f});
            this.fullscreenQuad.setIndices(new short[]{0, 1, 2, 2, 3, 0});
            this.vmWidth = this.virtualWidth;
            this.vmHeight = this.virtualHeight;
            this.vmX = -this.virtualWidthHalf;
            this.vmY = -this.virtualHeightHalf;
        }
        try {
            ShaderProgram shaderProgram2 = new ShaderProgram("attribute vec4 a_position;\nattribute vec4 a_color;\nattribute vec2 a_texCoord0;\nuniform mat4 u_projTrans;\nvarying vec4 v_color;\nvarying vec2 v_texCoords;\n\nvoid main()\n{\n   v_color = a_color;\n   v_texCoords = a_texCoord0;\n   gl_Position =  u_projTrans * a_position;\n}\n", "#ifdef GL_ES\nprecision mediump float;\n#endif\nvarying vec4 v_color;\nvarying vec2 v_texCoords;\nuniform sampler2D u_texture;\nvoid main()\n{\n  vec4 tex = texture2D(u_texture, v_texCoords);\n  gl_FragColor = v_color * vec4(tex.b, tex.g, tex.r, tex.a);\n}");
            this.vncSwizzleShader = shaderProgram2;
            if (!shaderProgram2.isCompiled()) {
                Log.e("SHADER_ERROR", "Error compiling shader: " + this.vncSwizzleShader.getLog());
            }
            if (!this.vncSwizzleShader.isCompiled()) {
                Log.e("SHADER_ERROR", "VNC Swizzle Shader failed to compile: " + this.vncSwizzleShader.getLog());
            } else {
                Log.i("SHADER_INFO", "VNC Swizzle Shader compiled successfully.");
            }
        } catch (Exception e) {
            Log.e("SHADER_ERROR", "Could not load VNC Swizzle Shader files", e);
        }
        cacheBeforeUpdateScripts();
        cacheAfterUpdateScripts();
        if (StageActivity.pendingNotificationId != null) {
            String str = StageActivity.pendingNotificationId;
            StageActivity.pendingNotificationId = null;
            onNotificationActionTriggered(str);
            onNotificationClicked(str);
        }
    }

    public boolean isFinished() {
        return this.finished;
    }

    public void backgroundTick(float delta) {
        processPendingNotificationActions();
        if (this.paused && this.isBackgroundModeEnabled && !this.finished) {
            try {
                PhysicsWorld physicsWorld = this.physicsWorld;
                if (physicsWorld != null) {
                    physicsWorld.step(delta);
                }
                Stage stage = this.stage;
                if (stage != null) {
                    stage.act(delta);
                }
                Stage stage2 = this.uiStage;
                if (stage2 != null) {
                    stage2.act(delta);
                }
                ThreeDManager threeDManager = this.threeDManager;
                if (threeDManager != null) {
                    threeDManager.update(delta);
                }
                if (this.hasBeforeUpdateScripts) {
                    executeBeforeUpdateScripts(delta);
                }
                if (this.hasAfterUpdateScripts) {
                    executeAfterUpdateScripts(delta);
                }
            } catch (Exception e) {
                Log.e("BACKGROUND_TICK", "Error during background logic tick", e);
            }
        }
    }

    private void processPendingNotificationActions() {
        ConcurrentLinkedQueue<String> pendingActions = NewCatroidNotificationManager.INSTANCE.getPendingActions();
        while (!pendingActions.isEmpty()) {
            String strPoll = pendingActions.poll();
            if (strPoll != null) {
                onNotificationActionTriggered(strPoll);
                onNotificationClicked(strPoll);
            }
        }
    }

    public void setCameraPosition(float x, float y) {
        OrthographicCamera orthographicCamera = this.camera;
        if (orthographicCamera != null) {
            orthographicCamera.position.set(x, y, 0.0f);
        }
    }

    public void setCameraZoom(float zoom) {
        OrthographicCamera orthographicCamera = this.camera;
        if (orthographicCamera == null || zoom <= 0.0f) {
            return;
        }
        orthographicCamera.zoom = zoom;
    }

    public void setCameraRotation(float degrees) {
        OrthographicCamera orthographicCamera = this.camera;
        if (orthographicCamera != null) {
            orthographicCamera.rotate(this.cameraRotation - degrees);
            this.cameraRotation = degrees;
        }
    }

    public void pinSpriteToCamera(Sprite sprite) {
        if (sprite == null || this.camera == null) {
            return;
        }
        float x = sprite.look.getX();
        float y = sprite.look.getY();
        float f = x - this.camera.position.x;
        float f2 = y - this.camera.position.y;
        float fCosDeg = MathUtils.cosDeg(-this.cameraRotation);
        float fSinDeg = MathUtils.sinDeg(-this.cameraRotation);
        float f3 = (f * fCosDeg) - (f2 * fSinDeg);
        float f4 = f3 / this.camera.zoom;
        float f5 = ((f * fSinDeg) + (f2 * fCosDeg)) / this.camera.zoom;
        PinnedState pinnedState = new PinnedState();
        pinnedState.rx = f4;
        pinnedState.ry = f5;
        pinnedState.originalRotation = sprite.look.getRotation() - this.cameraRotation;
        pinnedState.originalScaleX = sprite.look.getScaleX() / this.camera.zoom;
        pinnedState.originalScaleY = sprite.look.getScaleY() / this.camera.zoom;
        this.pinnedSprites.put(sprite, pinnedState);
    }

    public void unpinSpriteFromCamera(Sprite sprite) {
        if (sprite == null) {
            return;
        }
        this.pinnedSprites.remove(sprite);
    }

    private void updatePinnedSprites() {
        if (this.pinnedSprites.isEmpty() || this.camera == null) {
            return;
        }
        float fCosDeg = MathUtils.cosDeg(this.cameraRotation);
        float fSinDeg = MathUtils.sinDeg(this.cameraRotation);
        for (Map.Entry<Sprite, PinnedState> entry : this.pinnedSprites.entrySet()) {
            Sprite key = entry.getKey();
            PinnedState value = entry.getValue();
            float f = value.rx * this.camera.zoom;
            float f2 = value.ry * this.camera.zoom;
            key.look.setPosition(this.camera.position.x + ((f * fCosDeg) - (f2 * fSinDeg)), this.camera.position.y + (f * fSinDeg) + (f2 * fCosDeg));
            key.look.setScale(value.originalScaleX * this.camera.zoom, value.originalScaleY * this.camera.zoom);
            key.look.setRotation(value.originalRotation + this.cameraRotation);
        }
    }

    public void setVmScreenGeometry(float x, float y, float width, float height) {
        this.vmX = x;
        this.vmY = y;
        this.vmWidth = width;
        this.vmHeight = height;
        this.vmGeometryDirty = true;
    }

    public void setVmDisplayVisible(boolean visible) {
        this.isVmDisplayVisible = visible;
        Log.d("Display", "Display is: " + this.isVmDisplayVisible);
    }

    private void updateVmScreenMesh() {
        float f = this.vmX;
        float f2 = this.vmWidth + f;
        float f3 = this.vmY;
        float f4 = this.vmHeight + f3;
        float[] fArr = {f, f3, 0.0f, 0.0f, 1.0f, f2, f3, 0.0f, 1.0f, 1.0f, f2, f4, 0.0f, 1.0f, 0.0f, f, f4, 0.0f, 0.0f, 0.0f};
        short[] sArr = {0, 1, 2, 2, 3, 0};
        if (this.vmScreenMesh == null) {
            this.vmScreenMesh = new Mesh(true, 4, 6, new VertexAttribute(1, 3, ShaderProgram.POSITION_ATTRIBUTE), new VertexAttribute(16, 2, "a_texCoord0"));
        }
        this.vmScreenMesh.setVertices(fArr);
        this.vmScreenMesh.setIndices(sArr);
        this.vmGeometryDirty = false;
    }

    private void resetConditionScriptTriggers() {
        Iterator<Sprite> it = this.sprites.iterator();
        while (it.hasNext()) {
            it.next().resetConditionScriptTriggers();
        }
    }

    public void setPaused(boolean paused) {
        this.paused = paused;
    }

    private BitmapFont getLabelFont(Project project) {
        BitmapFont bitmapFont = new BitmapFont();
        bitmapFont.setColor(AXIS_COLOR);
        bitmapFont.getData().setScale(getFontScaleFactor(project, bitmapFont, new GlyphLayout()));
        return bitmapFont;
    }

    public float getFontScaleFactor(Project project, BitmapFont font, GlyphLayout tempAxisLabelLayout) {
        int virtualScreenWidth;
        tempAxisLabelLayout.setText(font, String.valueOf(project.getXmlHeader().virtualScreenWidth / 2));
        if (project.getXmlHeader().islandscapeMode()) {
            virtualScreenWidth = project.getXmlHeader().getVirtualScreenHeight();
        } else {
            virtualScreenWidth = project.getXmlHeader().getVirtualScreenWidth();
        }
        return (virtualScreenWidth * 0.025f) / tempAxisLabelLayout.height;
    }

    private void createNewStage() {
        this.time = 0.0f;
        GlobalShaderManager.INSTANCE.clear();
        if (!this.project.getXmlHeader().customResolution.booleanValue()) {
            this.virtualWidth = this.project.getXmlHeader().getVirtualScreenWidth();
            this.virtualHeight = this.project.getXmlHeader().getVirtualScreenHeight();
        } else {
            this.virtualWidth = Gdx.graphics.getWidth();
            this.virtualHeight = Gdx.graphics.getHeight();
        }
        this.virtualWidthHalf = this.virtualWidth / 2.0f;
        this.virtualHeightHalf = this.virtualHeight / 2.0f;
        FrameBuffer frameBuffer = this.sceneFbo;
        if (frameBuffer != null) {
            frameBuffer.dispose();
        }
        this.sceneFbo = new FrameBuffer(Pixmap.Format.RGBA8888, Math.round(this.virtualWidth), Math.round(this.virtualHeight), false);
        FrameBuffer frameBuffer2 = this.postProcessFbo;
        if (frameBuffer2 != null) {
            frameBuffer2.dispose();
        }
        this.postProcessFbo = new FrameBuffer(Pixmap.Format.RGBA8888, Math.round(this.virtualWidth), Math.round(this.virtualHeight), false);
        TextureRegion textureRegion = new TextureRegion(this.sceneFbo.getColorBufferTexture());
        this.fboRegion = textureRegion;
        textureRegion.flip(false, true);
        this.camera = new OrthographicCamera();
        this.cameraPositioner = new CameraPositioner(this.camera, this.virtualHeightHalf, this.virtualWidthHalf);
        this.viewPort = new ExtendViewport(this.virtualWidth, this.virtualHeight, this.camera);
        if (this.batch == null) {
            this.batch = new SpriteBatch(JosStatusCodes.RTN_CODE_COMMON_ERROR);
        }
        if (this.fastTwoDManager == null) {
            this.fastTwoDManager = new FastTwoDManager();
        }
        this.fastTwoDManager.init((SpriteBatch) this.batch);
        if (this.postProcessBatch == null) {
            this.postProcessBatch = new SpriteBatch();
        }
        this.stage = new Stage(this.viewPort, this.batch);
        this.uiCamera = new OrthographicCamera();
        this.uiViewPort = new ScreenViewport(this.uiCamera);
        this.uiStage = new Stage(this.uiViewPort, this.batch);
        InputAdapter inputAdapter = new InputAdapter() { // from class: org.catrobat.catroid.stage.StageListener.1
            @Override // com.badlogic.gdx.InputAdapter, com.badlogic.gdx.InputProcessor
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                return StageListener.this.threeDManager.handleTouchDown(screenX, screenY, pointer, StageListener.this.uiStage, StageListener.this.stage);
            }

            @Override // com.badlogic.gdx.InputAdapter, com.badlogic.gdx.InputProcessor
            public boolean touchDragged(int screenX, int screenY, int pointer) {
                StageListener.this.threeDManager.handleTouchDragged(screenX, screenY, pointer);
                return false;
            }

            @Override // com.badlogic.gdx.InputAdapter, com.badlogic.gdx.InputProcessor
            public boolean touchUp(int screenX, int screenY, int pointer, int button) {
                StageListener.this.threeDManager.handleTouchUp(pointer);
                return false;
            }
        };
        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        this.inputMultiplexer = inputMultiplexer;
        inputMultiplexer.addProcessor(inputAdapter);
        this.inputMultiplexer.addProcessor(this.uiStage);
        this.inputMultiplexer.addProcessor(this.stage);
        initMouseInputAdapter();
        SensorHandler.timerReferenceValue = SystemClock.uptimeMillis();
    }

    private void initStageInputListener() {
        Stage stage = this.stage;
        if (stage != null) {
            stage.getRoot().clearListeners();
        }
        Stage stage2 = this.uiStage;
        if (stage2 != null) {
            stage2.getRoot().clearListeners();
        }
        InputListener inputListener = new InputListener() { // from class: org.catrobat.catroid.stage.StageListener.2
            @Override // com.badlogic.gdx.scenes.scene2d.InputListener
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                TouchUtil.touchDown(event.getStageX(), event.getStageY(), pointer);
                return true;
            }

            @Override // com.badlogic.gdx.scenes.scene2d.InputListener
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                TouchUtil.touchUp(pointer);
            }

            @Override // com.badlogic.gdx.scenes.scene2d.InputListener
            public void touchDragged(InputEvent event, float x, float y, int pointer) {
                TouchUtil.updatePosition(event.getStageX(), event.getStageY(), pointer);
                if (StageListener.this.sprites != null) {
                    EventWrapper eventWrapper = new EventWrapper(new EventId(14), false);
                    for (Sprite sprite : StageListener.this.sprites) {
                        if (sprite != null && sprite.look != null) {
                            sprite.look.fire(eventWrapper);
                        }
                    }
                }
            }
        };
        InputListener inputListener2 = new InputListener() { // from class: org.catrobat.catroid.stage.StageListener.3
            private void updateTouchInWorldCoords(float screenX, float screenY, int pointer, boolean isDown) {
                StageListener.this.tempVec3ForTouch.set(Gdx.input.getX(pointer), Gdx.input.getY(pointer), 0.0f);
                StageListener.this.viewPort.unproject(StageListener.this.tempVec3ForTouch);
                if (isDown) {
                    TouchUtil.touchDown(StageListener.this.tempVec3ForTouch.x, StageListener.this.tempVec3ForTouch.y, pointer);
                } else {
                    TouchUtil.updatePosition(StageListener.this.tempVec3ForTouch.x, StageListener.this.tempVec3ForTouch.y, pointer);
                }
            }

            @Override // com.badlogic.gdx.scenes.scene2d.InputListener
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (event.getTarget() == StageListener.this.uiStage.getRoot()) {
                    return false;
                }
                updateTouchInWorldCoords(event.getStageX(), event.getStageY(), pointer, true);
                return true;
            }

            @Override // com.badlogic.gdx.scenes.scene2d.InputListener
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                TouchUtil.touchUp(pointer);
            }

            @Override // com.badlogic.gdx.scenes.scene2d.InputListener
            public void touchDragged(InputEvent event, float x, float y, int pointer) {
                if (event.getTarget() != StageListener.this.uiStage.getRoot()) {
                    updateTouchInWorldCoords(event.getStageX(), event.getStageY(), pointer, false);
                }
            }
        };
        this.stage.addListener(inputListener);
        this.uiStage.addListener(inputListener2);
    }

    private void initActors(List<Sprite> sprites) {
        VmMonitorActor vmMonitorActor = new VmMonitorActor(this.vncSwizzleShader);
        this.vmMonitorActor = vmMonitorActor;
        vmMonitorActor.setSize(this.virtualWidth, this.virtualHeight);
        this.vmMonitorActor.setPosition(-this.virtualWidthHalf, -this.virtualHeightHalf);
        this.stage.addActor(this.vmMonitorActor);
        this.vmMonitorActor.setZIndex(0);
        if (sprites.isEmpty()) {
            return;
        }
        for (Sprite sprite : sprites) {
            sprite.resetSprite();
            sprite.look.setRenderingContext(this.camera, this.viewPort, this.uiStage);
            this.stage.addActor(sprite.look);
        }
        PenActor penActor = new PenActor();
        this.penActor = penActor;
        this.stage.addActor(penActor);
        this.penActor.setZIndex(1);
        PlotActor plotActor = new PlotActor();
        this.plotActor = plotActor;
        this.stage.addActor(plotActor);
        this.plotActor.setZIndex(1);
        EmbroideryActor embroideryActor = new EmbroideryActor(calculateScreenRatio(), this.embroideryPatternManager, this.shapeRenderer);
        this.stage.addActor(embroideryActor);
        embroideryActor.setZIndex(2);
    }

    public void onNotificationClicked(String clickedId) {
        List<Sprite> list = this.sprites;
        if (list == null || clickedId == null) {
            return;
        }
        for (Sprite sprite : list) {
            for (Script script : sprite.getScriptList()) {
                if ((script instanceof WhenNotificationClickedScript) && !script.isCommentedOut()) {
                    WhenNotificationClickedBrick whenNotificationClickedBrick = (WhenNotificationClickedBrick) script.getScriptBrick();
                    try {
                        if (clickedId.equals(whenNotificationClickedBrick.getNotificationIdFormula().interpretString(new Scope(this.project, sprite, null)))) {
                            ScriptSequenceAction scriptSequenceActionCreateSequenceAction = sprite.createSequenceAction(script);
                            scriptSequenceActionCreateSequenceAction.restart();
                            sprite.look.addAction(scriptSequenceActionCreateSequenceAction);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public void cloneSpriteAndAddToStage(Sprite cloneMe) {
        Sprite spriteCopyForCloneBrick = new SpriteController().copyForCloneBrick(cloneMe);
        if (cloneMe.isClone) {
            spriteCopyForCloneBrick.myOriginal = cloneMe.myOriginal;
        } else {
            spriteCopyForCloneBrick.myOriginal = cloneMe;
        }
        spriteCopyForCloneBrick.look.setRenderingContext(this.camera, this.viewPort, this.uiStage);
        Stage stage = this.stage;
        addCloneActorToStage(stage, stage.getRoot(), cloneMe.look, spriteCopyForCloneBrick.look);
        this.sprites.add(spriteCopyForCloneBrick);
        if (!spriteCopyForCloneBrick.getLookList().isEmpty()) {
            spriteCopyForCloneBrick.look.setLookData(spriteCopyForCloneBrick.getLookList().get(cloneMe.getLookList().indexOf(cloneMe.look.getLookData())));
        }
        spriteCopyForCloneBrick.initializeEventThreads(4);
        spriteCopyForCloneBrick.initConditionScriptTriggers();
        cacheBeforeUpdateScripts();
        cacheAfterUpdateScripts();
    }

    public void cloneSpriteAndAddToStage(Sprite cloneMe, String newName) {
        Sprite spriteCopyForCloneBrick = new SpriteController().copyForCloneBrick(cloneMe, newName);
        if (cloneMe.isClone) {
            spriteCopyForCloneBrick.myOriginal = cloneMe.myOriginal;
        } else {
            spriteCopyForCloneBrick.myOriginal = cloneMe;
        }
        spriteCopyForCloneBrick.look.setRenderingContext(this.camera, this.viewPort, this.uiStage);
        Stage stage = this.stage;
        addCloneActorToStage(stage, stage.getRoot(), cloneMe.look, spriteCopyForCloneBrick.look);
        this.sprites.add(spriteCopyForCloneBrick);
        if (!spriteCopyForCloneBrick.getLookList().isEmpty()) {
            spriteCopyForCloneBrick.look.setLookData(spriteCopyForCloneBrick.getLookList().get(cloneMe.getLookList().indexOf(cloneMe.look.getLookData())));
        }
        spriteCopyForCloneBrick.initializeEventThreads(4);
        spriteCopyForCloneBrick.initConditionScriptTriggers();
        cacheBeforeUpdateScripts();
        cacheAfterUpdateScripts();
    }

    public void addCloneActorToStage(Stage stage, Group rootGroup, Look cloneMeLook, Look copyLook) {
        if (!stage.getActors().contains(cloneMeLook, true)) {
            rootGroup.addActor(cloneMeLook);
        }
        rootGroup.addActorBefore(cloneMeLook, copyLook);
    }

    public boolean removeClonedSpriteFromStage(Sprite sprite) {
        if (!sprite.isClone) {
            return false;
        }
        boolean zRemove = this.sprites.remove(sprite);
        if (zRemove) {
            sprite.look.destroy();
            sprite.invalidate();
            cacheBeforeUpdateScripts();
            cacheAfterUpdateScripts();
        }
        return zRemove;
    }

    private void removeAllClonedSpritesFromStage() {
        for (Sprite sprite : new ArrayList(this.sprites)) {
            if (sprite.isClone) {
                removeClonedSpriteFromStage(sprite);
            }
        }
        StageActivity.resetNumberOfClonedSprites();
    }

    public List<Sprite> getAllClonesOfSprite(Sprite sprite) {
        ArrayList arrayList = new ArrayList();
        for (Sprite sprite2 : this.sprites) {
            if (spriteIsCloneOfSprite(sprite, sprite2).booleanValue()) {
                arrayList.add(sprite2);
            }
        }
        return arrayList;
    }

    private Boolean spriteIsCloneOfSprite(Sprite sprite, Sprite cloneSprite) {
        if (!cloneSprite.isClone) {
            return false;
        }
        return Boolean.valueOf(cloneSprite.getName().split("\\-c\\d+$")[0].contentEquals(sprite.getName()));
    }

    private void disposeClonedSprites() {
        Iterator<Scene> it = ProjectManager.getInstance().getCurrentProject().getSceneList().iterator();
        while (it.hasNext()) {
            it.next().removeClonedSprites();
        }
    }

    private void initMouseInputAdapter() {
        if (this.mouseInputAdapter == null) {
            this.mouseInputAdapter = new InputAdapter() { // from class: org.catrobat.catroid.stage.StageListener.4
                @Override // com.badlogic.gdx.InputAdapter, com.badlogic.gdx.InputProcessor
                public boolean mouseMoved(int screenX, int screenY) {
                    StageListener.this.tempVec3.set(screenX, screenY, 0.0f);
                    StageListener.this.viewPort.unproject(StageListener.this.tempVec3);
                    SensorHandler.getInstance(null).updateMousePosition(StageListener.this.tempVec3.x, StageListener.this.tempVec3.y);
                    return false;
                }

                @Override // com.badlogic.gdx.InputAdapter, com.badlogic.gdx.InputProcessor
                public boolean touchDragged(int screenX, int screenY, int pointer) {
                    if (pointer == 0) {
                        return mouseMoved(screenX, screenY);
                    }
                    return false;
                }

                @Override // com.badlogic.gdx.InputAdapter, com.badlogic.gdx.InputProcessor
                public boolean scrolled(float amountX, float amountY) {
                    SensorHandler.getInstance(null).setLastScrollAmount(-amountY);
                    EventWrapper eventWrapper = new EventWrapper(new EventId(9), false);
                    if (StageListener.this.project == null) {
                        return true;
                    }
                    StageListener.this.project.fireToAllSprites(eventWrapper);
                    return true;
                }
            };
        }
    }

    void menuResume() {
        if (this.reloadProject) {
            return;
        }
        this.paused = false;
    }

    void menuPause() {
        if (this.finished || this.reloadProject) {
            return;
        }
        this.paused = true;
        this.webConnectionHolder.onPause();
    }

    public void transitionToScene(String sceneName) {
        Scene sceneByName = ProjectManager.getInstance().getCurrentProject().getSceneByName(sceneName);
        if (sceneByName == null) {
            return;
        }
        this.stageBackupMap.put(this.scene.getName(), saveToBackup());
        pause();
        this.scene = sceneByName;
        ProjectManager.getInstance().setCurrentlyPlayingScene(this.scene);
        if (this.stageBackupMap.containsKey(this.scene.getName())) {
            restoreFromBackup(this.stageBackupMap.get(this.scene.getName()));
        }
        if (this.scene.firstStart) {
            create();
        } else {
            resume();
        }
        Gdx.input.setInputProcessor(this.stage);
    }

    public void transitionToScene(String sceneName, Boolean stopSounds) {
        Scene sceneByName = ProjectManager.getInstance().getCurrentProject().getSceneByName(sceneName);
        if (sceneByName == null) {
            return;
        }
        this.stageBackupMap.put(this.scene.getName(), saveToBackup());
        if (stopSounds.booleanValue()) {
            pause();
        }
        this.scene = sceneByName;
        ProjectManager.getInstance().setCurrentlyPlayingScene(this.scene);
        if (this.stageBackupMap.containsKey(this.scene.getName())) {
            restoreFromBackup(this.stageBackupMap.get(this.scene.getName()));
        }
        if (this.scene.firstStart) {
            create();
        } else {
            resume();
        }
        Gdx.input.setInputProcessor(this.stage);
    }

    public void transitionToScene(String sceneName, Boolean stopSounds, Boolean save) {
        Scene sceneByName = ProjectManager.getInstance().getCurrentProject().getSceneByName(sceneName);
        if (sceneByName == null) {
            return;
        }
        if (save.booleanValue()) {
            this.stageBackupMap.put(this.scene.getName(), saveToBackup());
        }
        if (stopSounds.booleanValue()) {
            pause();
        }
        this.scene = sceneByName;
        ProjectManager.getInstance().setCurrentlyPlayingScene(this.scene);
        if (this.stageBackupMap.containsKey(this.scene.getName())) {
            restoreFromBackup(this.stageBackupMap.get(this.scene.getName()));
        }
        if (this.scene.firstStart) {
            create();
        } else {
            resume();
        }
        Gdx.input.setInputProcessor(this.stage);
    }

    public void clearScene(String name2) {
        this.stageBackupMap.remove(name2);
    }

    public void startScene(String sceneName, Boolean stopSound) {
        Scene sceneByName = ProjectManager.getInstance().getCurrentProject().getSceneByName(sceneName);
        if (sceneByName == null) {
            return;
        }
        this.stageBackupMap.put(this.scene.getName(), saveToBackup());
        if (stopSound.booleanValue()) {
            pause();
        }
        this.scene = sceneByName;
        ProjectManager.getInstance().setCurrentlyPlayingScene(this.scene);
        final CameraManager activeCameraManager = StageActivity.getActiveCameraManager();
        if (activeCameraManager != null) {
            StageActivity.runOnMainThread(new Runnable() { // from class: org.catrobat.catroid.stage.StageListener.5
                @Override // java.lang.Runnable
                public void run() {
                    activeCameraManager.resume();
                }
            });
        }
        if (stopSound.booleanValue()) {
            SoundManager.getInstance().clear();
        }
        ((SpeechRecognitionHolderFactory) KoinJavaComponent.get(SpeechRecognitionHolderFactory.class)).getInstance().destroy();
        this.stageBackupMap.remove(sceneName);
        Gdx.input.setInputProcessor(this.stage);
        this.scene.firstStart = true;
        create();
    }

    public void startScene(String sceneName, Boolean stopSound, Boolean save) {
        Scene sceneByName = ProjectManager.getInstance().getCurrentProject().getSceneByName(sceneName);
        if (sceneByName == null) {
            return;
        }
        if (save.booleanValue()) {
            this.stageBackupMap.put(this.scene.getName(), saveToBackup());
        }
        if (stopSound.booleanValue()) {
            pause();
        }
        this.scene = sceneByName;
        ProjectManager.getInstance().setCurrentlyPlayingScene(this.scene);
        final CameraManager activeCameraManager = StageActivity.getActiveCameraManager();
        if (activeCameraManager != null) {
            StageActivity.runOnMainThread(new Runnable() { // from class: org.catrobat.catroid.stage.StageListener.6
                @Override // java.lang.Runnable
                public void run() {
                    activeCameraManager.resume();
                }
            });
        }
        if (stopSound.booleanValue()) {
            SoundManager.getInstance().clear();
        }
        ((SpeechRecognitionHolderFactory) KoinJavaComponent.get(SpeechRecognitionHolderFactory.class)).getInstance().destroy();
        this.stageBackupMap.remove(sceneName);
        Gdx.input.setInputProcessor(this.stage);
        this.scene.firstStart = true;
        create();
    }

    public void startScene(String sceneName) {
        Scene sceneByName = ProjectManager.getInstance().getCurrentProject().getSceneByName(sceneName);
        if (sceneByName == null) {
            return;
        }
        this.stageBackupMap.put(this.scene.getName(), saveToBackup());
        pause();
        this.scene = sceneByName;
        ProjectManager.getInstance().setCurrentlyPlayingScene(this.scene);
        final CameraManager activeCameraManager = StageActivity.getActiveCameraManager();
        if (activeCameraManager != null) {
            StageActivity.runOnMainThread(new Runnable() { // from class: org.catrobat.catroid.stage.StageListener.7
                @Override // java.lang.Runnable
                public void run() {
                    activeCameraManager.resume();
                }
            });
        }
        SoundManager.getInstance().clear();
        ((SpeechRecognitionHolderFactory) KoinJavaComponent.get(SpeechRecognitionHolderFactory.class)).getInstance().destroy();
        this.stageBackupMap.remove(sceneName);
        Gdx.input.setInputProcessor(this.stage);
        this.scene.firstStart = true;
        create();
    }

    public void startSceneById(Integer sceneId) {
        Scene sceneById = ProjectManager.getInstance().getCurrentProject().getSceneById(sceneId);
        String sceneNameById = ProjectManager.getInstance().getCurrentProject().getSceneNameById(sceneId);
        if (sceneById == null) {
            return;
        }
        this.stageBackupMap.put(this.scene.getName(), saveToBackup());
        pause();
        this.scene = sceneById;
        ProjectManager.getInstance().setCurrentlyPlayingScene(this.scene);
        final CameraManager activeCameraManager = StageActivity.getActiveCameraManager();
        if (activeCameraManager != null) {
            StageActivity.runOnMainThread(new Runnable() { // from class: org.catrobat.catroid.stage.StageListener.8
                @Override // java.lang.Runnable
                public void run() {
                    activeCameraManager.resume();
                }
            });
        }
        SoundManager.getInstance().clear();
        ((SpeechRecognitionHolderFactory) KoinJavaComponent.get(SpeechRecognitionHolderFactory.class)).getInstance().destroy();
        this.stageBackupMap.remove(sceneNameById);
        Gdx.input.setInputProcessor(this.stage);
        this.scene.firstStart = true;
        create();
    }

    public void reloadProject(StageDialog stageDialog) {
        executeExitScriptsSynchronously();
        if (this.reloadProject) {
            return;
        }
        StageActivity stageActivity = StageActivity.activeStageActivity.get();
        if (stageActivity != null) {
            stageActivity.removeAllNativeViews();
        }
        ThreeDManager threeDManager = this.threeDManager;
        if (threeDManager != null) {
            threeDManager.dispose();
        }
        this.threeDManager = null;
        SquareController.getInstance().clearSquares();
        SquareActor.disposeShared();
        SceneManager sceneManager = this.sceneManager;
        if (sceneManager != null) {
            sceneManager.clearScene();
        }
        FastTwoDManager fastTwoDManager = this.fastTwoDManager;
        if (fastTwoDManager != null) {
            fastTwoDManager.clearScene();
        }
        this.isVmDisplayVisible = false;
        this.stageDialog = stageDialog;
        if (!ProjectManager.getInstance().getStartScene().getName().equals(this.scene.getName())) {
            transitionToScene(ProjectManager.getInstance().getStartScene().getName());
        }
        this.stageBackupMap.clear();
        this.embroideryPatternManager.clear();
        this.pinnedSprites.clear();
        CameraManager activeCameraManager = StageActivity.getActiveCameraManager();
        if (activeCameraManager != null) {
            activeCameraManager.reset();
        }
        VibrationManager activeVibrationManager = StageActivity.getActiveVibrationManager();
        if (activeVibrationManager != null) {
            activeVibrationManager.reset();
        }
        TouchUtil.reset();
        MidiSoundManager.getInstance().reset();
        removeAllClonedSpritesFromStage();
        UserDataWrapper.resetAllUserData(ProjectManager.getInstance().getCurrentProject());
        NewCatroidMqttManager.INSTANCE.disconnectAll();
        Iterator<Scene> it = ProjectManager.getInstance().getCurrentProject().getSceneList().iterator();
        while (it.hasNext()) {
            it.next().firstStart = true;
        }
        GlobalManager.INSTANCE.setStopSounds(true);
        GlobalManager.INSTANCE.setSaveScenes(true);
        try {
            RenderManager.INSTANCE.initialize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            ThreeDManager threeDManager2 = new ThreeDManager();
            this.threeDManager = threeDManager2;
            threeDManager2.init();
            this.sceneManager = new SceneManager(this.threeDManager);
        } catch (Exception e) {
            Log.e("StageListener", "INITIALIZE ERROR: " + e);
        }
        ActionThreadRegistry.clear();
        this.reloadProject = true;
    }

    private void cacheBeforeUpdateScripts() {
        this.beforeUpdateActions.clear();
        this.hasBeforeUpdateScripts = false;
        List<Sprite> list = this.sprites;
        if (list == null) {
            return;
        }
        for (Sprite sprite : list) {
            for (Script script : sprite.getScriptList()) {
                if ((script instanceof BeforeUpdateScript) && !script.isCommentedOut()) {
                    ScriptSequenceAction scriptSequenceActionCreateSequenceAction = sprite.createSequenceAction(script);
                    scriptSequenceActionCreateSequenceAction.setActor(sprite.look);
                    this.beforeUpdateActions.add(scriptSequenceActionCreateSequenceAction);
                }
            }
        }
        this.hasBeforeUpdateScripts = !this.beforeUpdateActions.isEmpty();
    }

    private void cacheAfterUpdateScripts() {
        this.afterUpdateActions.clear();
        this.hasAfterUpdateScripts = false;
        List<Sprite> list = this.sprites;
        if (list == null) {
            return;
        }
        for (Sprite sprite : list) {
            for (Script script : sprite.getScriptList()) {
                if ((script instanceof AfterUpdateScript) && !script.isCommentedOut()) {
                    ScriptSequenceAction scriptSequenceActionCreateSequenceAction = sprite.createSequenceAction(script);
                    scriptSequenceActionCreateSequenceAction.setActor(sprite.look);
                    this.afterUpdateActions.add(scriptSequenceActionCreateSequenceAction);
                }
            }
        }
        this.hasAfterUpdateScripts = !this.afterUpdateActions.isEmpty();
    }

    private void executeAfterUpdateScripts(float delta) {
        for (int i = 0; i < this.afterUpdateActions.size(); i++) {
            ScriptSequenceAction scriptSequenceAction = this.afterUpdateActions.get(i);
            scriptSequenceAction.restart();
            for (int i2 = 0; i2 < 10000 && !scriptSequenceAction.act(delta); i2++) {
            }
        }
    }

    private void executeBeforeUpdateScripts(float delta) {
        for (int i = 0; i < this.beforeUpdateActions.size(); i++) {
            ScriptSequenceAction scriptSequenceAction = this.beforeUpdateActions.get(i);
            scriptSequenceAction.restart();
            for (int i2 = 0; i2 < 10000 && !scriptSequenceAction.act(delta); i2++) {
            }
        }
    }

    @Override // com.badlogic.gdx.ApplicationListener
    public void resume() {
        if (this.firstFrameDrawn) {
            broadcastEventToAllSprites(new EventId(17));
        }
        if (!this.paused) {
            setSchedulerStateForAllLooks(0);
            SoundManager.getInstance().resume();
        }
        Iterator<Sprite> it = this.sprites.iterator();
        while (it.hasNext()) {
            it.next().look.refreshTextures(true);
        }
    }

    @Override // com.badlogic.gdx.ApplicationListener
    public void pause() {
        if (this.finished) {
            return;
        }
        StageActivity stageActivity = StageActivity.activeStageActivity.get();
        boolean z = stageActivity != null && stageActivity.isFinishing();
        if (!this.finished && !z) {
            broadcastEventToAllSprites(new EventId(16));
        }
        if (this.paused) {
            return;
        }
        if (!this.isBackgroundModeEnabled) {
            setSchedulerStateForAllLooks(1);
        }
        SoundManager.getInstance().pause();
    }

    public void setVmScreenSize(final int width, final int height) {
        Gdx.f82app.postRunnable(new Runnable() { // from class: org.catrobat.catroid.stage.StageListener$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m5555x3140d891(width, height);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$setVmScreenSize$0$org-catrobat-catroid-stage-StageListener, reason: not valid java name */
    /* synthetic */ void m5555x3140d891(int i, int i2) {
        Texture texture = this.vmTexture;
        if (texture != null) {
            texture.dispose();
        }
        Texture texture2 = new Texture(i, i2, Pixmap.Format.RGBA8888);
        this.vmTexture = texture2;
        texture2.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        VmMonitorActor vmMonitorActor = this.vmMonitorActor;
        if (vmMonitorActor != null) {
            vmMonitorActor.setTexture(this.vmTexture);
        }
    }

    public void setVmMonitorBounds(float x, float y, float width, float height) {
        VmMonitorActor vmMonitorActor = this.vmMonitorActor;
        if (vmMonitorActor != null) {
            vmMonitorActor.setPosition(x, y);
            this.vmMonitorActor.setSize(width, height);
        }
    }

    public void resizeVmMonitor(float width, float height) {
        VmMonitorActor vmMonitorActor = this.vmMonitorActor;
        if (vmMonitorActor != null) {
            float x = vmMonitorActor.getX() + (this.vmMonitorActor.getWidth() / 2.0f);
            float y = this.vmMonitorActor.getY() + (this.vmMonitorActor.getHeight() / 2.0f);
            this.vmMonitorActor.setSize(width, height);
            this.vmMonitorActor.setPosition(x - (width / 2.0f), y - (height / 2.0f));
        }
    }

    public void captureAndSaveVmTexture() {
        this.captureNextFrame = true;
    }

    public float getVirtualWidth() {
        return this.virtualWidth;
    }

    public float getVirtualHeight() {
        return this.virtualHeight;
    }

    public int getVmWidth() {
        return (int) this.vmWidth;
    }

    public int getVmHeight() {
        return (int) this.vmHeight;
    }

    /* JADX WARN: Code duplicated, block: B:116:0x027f A[Catch: Exception -> 0x028a, TRY_LEAVE, TryCatch #1 {Exception -> 0x028a, blocks: (B:89:0x0212, B:91:0x0216, B:93:0x021a, B:94:0x0223, B:96:0x022a, B:98:0x022e, B:107:0x0260, B:109:0x0264, B:111:0x0268, B:113:0x026e, B:114:0x0279, B:116:0x027f, B:106:0x0248, B:99:0x0237, B:101:0x023b, B:103:0x0241), top: B:167:0x0212, outer: #8, inners: #3 }] */
    /* JADX WARN: Code duplicated, block: B:121:0x02a5 A[Catch: Exception -> 0x03c4, TryCatch #8 {Exception -> 0x03c4, blocks: (B:78:0x01c3, B:80:0x01c7, B:82:0x01cb, B:84:0x01cf, B:86:0x01d5, B:87:0x020e, B:120:0x02a3, B:121:0x02a5, B:123:0x02a9, B:125:0x02d9, B:138:0x035b, B:140:0x0383, B:142:0x0389, B:143:0x038c, B:127:0x02e3, B:129:0x02f5, B:131:0x0305, B:137:0x033f, B:132:0x0315, B:144:0x038e, B:146:0x0392, B:148:0x0396, B:149:0x0399, B:151:0x039d, B:152:0x03ad, B:154:0x03b6, B:156:0x03ba, B:119:0x028b, B:89:0x0212, B:91:0x0216, B:93:0x021a, B:94:0x0223, B:96:0x022a, B:98:0x022e, B:107:0x0260, B:109:0x0264, B:111:0x0268, B:113:0x026e, B:114:0x0279, B:116:0x027f, B:106:0x0248, B:99:0x0237, B:101:0x023b, B:103:0x0241), top: B:179:0x01c3, inners: #1 }] */
    /* JADX WARN: Code duplicated, block: B:123:0x02a9 A[Catch: Exception -> 0x03c4, TryCatch #8 {Exception -> 0x03c4, blocks: (B:78:0x01c3, B:80:0x01c7, B:82:0x01cb, B:84:0x01cf, B:86:0x01d5, B:87:0x020e, B:120:0x02a3, B:121:0x02a5, B:123:0x02a9, B:125:0x02d9, B:138:0x035b, B:140:0x0383, B:142:0x0389, B:143:0x038c, B:127:0x02e3, B:129:0x02f5, B:131:0x0305, B:137:0x033f, B:132:0x0315, B:144:0x038e, B:146:0x0392, B:148:0x0396, B:149:0x0399, B:151:0x039d, B:152:0x03ad, B:154:0x03b6, B:156:0x03ba, B:119:0x028b, B:89:0x0212, B:91:0x0216, B:93:0x021a, B:94:0x0223, B:96:0x022a, B:98:0x022e, B:107:0x0260, B:109:0x0264, B:111:0x0268, B:113:0x026e, B:114:0x0279, B:116:0x027f, B:106:0x0248, B:99:0x0237, B:101:0x023b, B:103:0x0241), top: B:179:0x01c3, inners: #1 }] */
    /* JADX WARN: Code duplicated, block: B:125:0x02d9 A[Catch: Exception -> 0x03c4, TryCatch #8 {Exception -> 0x03c4, blocks: (B:78:0x01c3, B:80:0x01c7, B:82:0x01cb, B:84:0x01cf, B:86:0x01d5, B:87:0x020e, B:120:0x02a3, B:121:0x02a5, B:123:0x02a9, B:125:0x02d9, B:138:0x035b, B:140:0x0383, B:142:0x0389, B:143:0x038c, B:127:0x02e3, B:129:0x02f5, B:131:0x0305, B:137:0x033f, B:132:0x0315, B:144:0x038e, B:146:0x0392, B:148:0x0396, B:149:0x0399, B:151:0x039d, B:152:0x03ad, B:154:0x03b6, B:156:0x03ba, B:119:0x028b, B:89:0x0212, B:91:0x0216, B:93:0x021a, B:94:0x0223, B:96:0x022a, B:98:0x022e, B:107:0x0260, B:109:0x0264, B:111:0x0268, B:113:0x026e, B:114:0x0279, B:116:0x027f, B:106:0x0248, B:99:0x0237, B:101:0x023b, B:103:0x0241), top: B:179:0x01c3, inners: #1 }] */
    /* JADX WARN: Code duplicated, block: B:127:0x02e3 A[Catch: Exception -> 0x03c4, TryCatch #8 {Exception -> 0x03c4, blocks: (B:78:0x01c3, B:80:0x01c7, B:82:0x01cb, B:84:0x01cf, B:86:0x01d5, B:87:0x020e, B:120:0x02a3, B:121:0x02a5, B:123:0x02a9, B:125:0x02d9, B:138:0x035b, B:140:0x0383, B:142:0x0389, B:143:0x038c, B:127:0x02e3, B:129:0x02f5, B:131:0x0305, B:137:0x033f, B:132:0x0315, B:144:0x038e, B:146:0x0392, B:148:0x0396, B:149:0x0399, B:151:0x039d, B:152:0x03ad, B:154:0x03b6, B:156:0x03ba, B:119:0x028b, B:89:0x0212, B:91:0x0216, B:93:0x021a, B:94:0x0223, B:96:0x022a, B:98:0x022e, B:107:0x0260, B:109:0x0264, B:111:0x0268, B:113:0x026e, B:114:0x0279, B:116:0x027f, B:106:0x0248, B:99:0x0237, B:101:0x023b, B:103:0x0241), top: B:179:0x01c3, inners: #1 }] */
    /* JADX WARN: Code duplicated, block: B:129:0x02f5 A[Catch: Exception -> 0x03c4, TryCatch #8 {Exception -> 0x03c4, blocks: (B:78:0x01c3, B:80:0x01c7, B:82:0x01cb, B:84:0x01cf, B:86:0x01d5, B:87:0x020e, B:120:0x02a3, B:121:0x02a5, B:123:0x02a9, B:125:0x02d9, B:138:0x035b, B:140:0x0383, B:142:0x0389, B:143:0x038c, B:127:0x02e3, B:129:0x02f5, B:131:0x0305, B:137:0x033f, B:132:0x0315, B:144:0x038e, B:146:0x0392, B:148:0x0396, B:149:0x0399, B:151:0x039d, B:152:0x03ad, B:154:0x03b6, B:156:0x03ba, B:119:0x028b, B:89:0x0212, B:91:0x0216, B:93:0x021a, B:94:0x0223, B:96:0x022a, B:98:0x022e, B:107:0x0260, B:109:0x0264, B:111:0x0268, B:113:0x026e, B:114:0x0279, B:116:0x027f, B:106:0x0248, B:99:0x0237, B:101:0x023b, B:103:0x0241), top: B:179:0x01c3, inners: #1 }] */
    /* JADX WARN: Code duplicated, block: B:132:0x0315 A[Catch: Exception -> 0x03c4, TryCatch #8 {Exception -> 0x03c4, blocks: (B:78:0x01c3, B:80:0x01c7, B:82:0x01cb, B:84:0x01cf, B:86:0x01d5, B:87:0x020e, B:120:0x02a3, B:121:0x02a5, B:123:0x02a9, B:125:0x02d9, B:138:0x035b, B:140:0x0383, B:142:0x0389, B:143:0x038c, B:127:0x02e3, B:129:0x02f5, B:131:0x0305, B:137:0x033f, B:132:0x0315, B:144:0x038e, B:146:0x0392, B:148:0x0396, B:149:0x0399, B:151:0x039d, B:152:0x03ad, B:154:0x03b6, B:156:0x03ba, B:119:0x028b, B:89:0x0212, B:91:0x0216, B:93:0x021a, B:94:0x0223, B:96:0x022a, B:98:0x022e, B:107:0x0260, B:109:0x0264, B:111:0x0268, B:113:0x026e, B:114:0x0279, B:116:0x027f, B:106:0x0248, B:99:0x0237, B:101:0x023b, B:103:0x0241), top: B:179:0x01c3, inners: #1 }] */
    /* JADX WARN: Code duplicated, block: B:134:0x033b  */
    /* JADX WARN: Code duplicated, block: B:136:0x033e  */
    /* JADX WARN: Code duplicated, block: B:140:0x0383 A[Catch: Exception -> 0x03c4, TryCatch #8 {Exception -> 0x03c4, blocks: (B:78:0x01c3, B:80:0x01c7, B:82:0x01cb, B:84:0x01cf, B:86:0x01d5, B:87:0x020e, B:120:0x02a3, B:121:0x02a5, B:123:0x02a9, B:125:0x02d9, B:138:0x035b, B:140:0x0383, B:142:0x0389, B:143:0x038c, B:127:0x02e3, B:129:0x02f5, B:131:0x0305, B:137:0x033f, B:132:0x0315, B:144:0x038e, B:146:0x0392, B:148:0x0396, B:149:0x0399, B:151:0x039d, B:152:0x03ad, B:154:0x03b6, B:156:0x03ba, B:119:0x028b, B:89:0x0212, B:91:0x0216, B:93:0x021a, B:94:0x0223, B:96:0x022a, B:98:0x022e, B:107:0x0260, B:109:0x0264, B:111:0x0268, B:113:0x026e, B:114:0x0279, B:116:0x027f, B:106:0x0248, B:99:0x0237, B:101:0x023b, B:103:0x0241), top: B:179:0x01c3, inners: #1 }] */
    /* JADX WARN: Code duplicated, block: B:146:0x0392 A[Catch: Exception -> 0x03c4, TryCatch #8 {Exception -> 0x03c4, blocks: (B:78:0x01c3, B:80:0x01c7, B:82:0x01cb, B:84:0x01cf, B:86:0x01d5, B:87:0x020e, B:120:0x02a3, B:121:0x02a5, B:123:0x02a9, B:125:0x02d9, B:138:0x035b, B:140:0x0383, B:142:0x0389, B:143:0x038c, B:127:0x02e3, B:129:0x02f5, B:131:0x0305, B:137:0x033f, B:132:0x0315, B:144:0x038e, B:146:0x0392, B:148:0x0396, B:149:0x0399, B:151:0x039d, B:152:0x03ad, B:154:0x03b6, B:156:0x03ba, B:119:0x028b, B:89:0x0212, B:91:0x0216, B:93:0x021a, B:94:0x0223, B:96:0x022a, B:98:0x022e, B:107:0x0260, B:109:0x0264, B:111:0x0268, B:113:0x026e, B:114:0x0279, B:116:0x027f, B:106:0x0248, B:99:0x0237, B:101:0x023b, B:103:0x0241), top: B:179:0x01c3, inners: #1 }] */
    /* JADX WARN: Code duplicated, block: B:151:0x039d A[Catch: Exception -> 0x03c4, TryCatch #8 {Exception -> 0x03c4, blocks: (B:78:0x01c3, B:80:0x01c7, B:82:0x01cb, B:84:0x01cf, B:86:0x01d5, B:87:0x020e, B:120:0x02a3, B:121:0x02a5, B:123:0x02a9, B:125:0x02d9, B:138:0x035b, B:140:0x0383, B:142:0x0389, B:143:0x038c, B:127:0x02e3, B:129:0x02f5, B:131:0x0305, B:137:0x033f, B:132:0x0315, B:144:0x038e, B:146:0x0392, B:148:0x0396, B:149:0x0399, B:151:0x039d, B:152:0x03ad, B:154:0x03b6, B:156:0x03ba, B:119:0x028b, B:89:0x0212, B:91:0x0216, B:93:0x021a, B:94:0x0223, B:96:0x022a, B:98:0x022e, B:107:0x0260, B:109:0x0264, B:111:0x0268, B:113:0x026e, B:114:0x0279, B:116:0x027f, B:106:0x0248, B:99:0x0237, B:101:0x023b, B:103:0x0241), top: B:179:0x01c3, inners: #1 }] */
    /* JADX WARN: Code duplicated, block: B:154:0x03b6 A[Catch: Exception -> 0x03c4, TryCatch #8 {Exception -> 0x03c4, blocks: (B:78:0x01c3, B:80:0x01c7, B:82:0x01cb, B:84:0x01cf, B:86:0x01d5, B:87:0x020e, B:120:0x02a3, B:121:0x02a5, B:123:0x02a9, B:125:0x02d9, B:138:0x035b, B:140:0x0383, B:142:0x0389, B:143:0x038c, B:127:0x02e3, B:129:0x02f5, B:131:0x0305, B:137:0x033f, B:132:0x0315, B:144:0x038e, B:146:0x0392, B:148:0x0396, B:149:0x0399, B:151:0x039d, B:152:0x03ad, B:154:0x03b6, B:156:0x03ba, B:119:0x028b, B:89:0x0212, B:91:0x0216, B:93:0x021a, B:94:0x0223, B:96:0x022a, B:98:0x022e, B:107:0x0260, B:109:0x0264, B:111:0x0268, B:113:0x026e, B:114:0x0279, B:116:0x027f, B:106:0x0248, B:99:0x0237, B:101:0x023b, B:103:0x0241), top: B:179:0x01c3, inners: #1 }] */
    /* JADX WARN: Code duplicated, block: B:167:0x0212 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:169:0x0160 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:173:0x00d2 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:182:0x0121 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:184:0x0101 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:24:0x006d A[Catch: Exception -> 0x03c6, TryCatch #5 {Exception -> 0x03c6, blocks: (B:3:0x0007, B:5:0x002d, B:8:0x0033, B:10:0x003f, B:13:0x0058, B:20:0x0066, B:21:0x0068, B:22:0x0069, B:24:0x006d, B:26:0x0071, B:27:0x0074, B:29:0x0078, B:30:0x007b, B:32:0x0097, B:33:0x009a, B:35:0x009e, B:36:0x00a1, B:38:0x00d1, B:44:0x00db, B:45:0x00dc, B:47:0x00fb, B:48:0x0101, B:50:0x0107, B:52:0x0121, B:53:0x0131, B:54:0x0135, B:56:0x0139, B:58:0x014e, B:59:0x0151, B:76:0x01be, B:39:0x00d2, B:40:0x00d7, B:12:0x0043, B:18:0x005e), top: B:175:0x0007, inners: #4, #7 }] */
    /* JADX WARN: Code duplicated, block: B:26:0x0071 A[Catch: Exception -> 0x03c6, TryCatch #5 {Exception -> 0x03c6, blocks: (B:3:0x0007, B:5:0x002d, B:8:0x0033, B:10:0x003f, B:13:0x0058, B:20:0x0066, B:21:0x0068, B:22:0x0069, B:24:0x006d, B:26:0x0071, B:27:0x0074, B:29:0x0078, B:30:0x007b, B:32:0x0097, B:33:0x009a, B:35:0x009e, B:36:0x00a1, B:38:0x00d1, B:44:0x00db, B:45:0x00dc, B:47:0x00fb, B:48:0x0101, B:50:0x0107, B:52:0x0121, B:53:0x0131, B:54:0x0135, B:56:0x0139, B:58:0x014e, B:59:0x0151, B:76:0x01be, B:39:0x00d2, B:40:0x00d7, B:12:0x0043, B:18:0x005e), top: B:175:0x0007, inners: #4, #7 }] */
    /* JADX WARN: Code duplicated, block: B:29:0x0078 A[Catch: Exception -> 0x03c6, TryCatch #5 {Exception -> 0x03c6, blocks: (B:3:0x0007, B:5:0x002d, B:8:0x0033, B:10:0x003f, B:13:0x0058, B:20:0x0066, B:21:0x0068, B:22:0x0069, B:24:0x006d, B:26:0x0071, B:27:0x0074, B:29:0x0078, B:30:0x007b, B:32:0x0097, B:33:0x009a, B:35:0x009e, B:36:0x00a1, B:38:0x00d1, B:44:0x00db, B:45:0x00dc, B:47:0x00fb, B:48:0x0101, B:50:0x0107, B:52:0x0121, B:53:0x0131, B:54:0x0135, B:56:0x0139, B:58:0x014e, B:59:0x0151, B:76:0x01be, B:39:0x00d2, B:40:0x00d7, B:12:0x0043, B:18:0x005e), top: B:175:0x0007, inners: #4, #7 }] */
    /* JADX WARN: Code duplicated, block: B:32:0x0097 A[Catch: Exception -> 0x03c6, TryCatch #5 {Exception -> 0x03c6, blocks: (B:3:0x0007, B:5:0x002d, B:8:0x0033, B:10:0x003f, B:13:0x0058, B:20:0x0066, B:21:0x0068, B:22:0x0069, B:24:0x006d, B:26:0x0071, B:27:0x0074, B:29:0x0078, B:30:0x007b, B:32:0x0097, B:33:0x009a, B:35:0x009e, B:36:0x00a1, B:38:0x00d1, B:44:0x00db, B:45:0x00dc, B:47:0x00fb, B:48:0x0101, B:50:0x0107, B:52:0x0121, B:53:0x0131, B:54:0x0135, B:56:0x0139, B:58:0x014e, B:59:0x0151, B:76:0x01be, B:39:0x00d2, B:40:0x00d7, B:12:0x0043, B:18:0x005e), top: B:175:0x0007, inners: #4, #7 }] */
    /* JADX WARN: Code duplicated, block: B:35:0x009e A[Catch: Exception -> 0x03c6, TryCatch #5 {Exception -> 0x03c6, blocks: (B:3:0x0007, B:5:0x002d, B:8:0x0033, B:10:0x003f, B:13:0x0058, B:20:0x0066, B:21:0x0068, B:22:0x0069, B:24:0x006d, B:26:0x0071, B:27:0x0074, B:29:0x0078, B:30:0x007b, B:32:0x0097, B:33:0x009a, B:35:0x009e, B:36:0x00a1, B:38:0x00d1, B:44:0x00db, B:45:0x00dc, B:47:0x00fb, B:48:0x0101, B:50:0x0107, B:52:0x0121, B:53:0x0131, B:54:0x0135, B:56:0x0139, B:58:0x014e, B:59:0x0151, B:76:0x01be, B:39:0x00d2, B:40:0x00d7, B:12:0x0043, B:18:0x005e), top: B:175:0x0007, inners: #4, #7 }] */
    /* JADX WARN: Code duplicated, block: B:38:0x00d1 A[Catch: Exception -> 0x03c6, TRY_LEAVE, TryCatch #5 {Exception -> 0x03c6, blocks: (B:3:0x0007, B:5:0x002d, B:8:0x0033, B:10:0x003f, B:13:0x0058, B:20:0x0066, B:21:0x0068, B:22:0x0069, B:24:0x006d, B:26:0x0071, B:27:0x0074, B:29:0x0078, B:30:0x007b, B:32:0x0097, B:33:0x009a, B:35:0x009e, B:36:0x00a1, B:38:0x00d1, B:44:0x00db, B:45:0x00dc, B:47:0x00fb, B:48:0x0101, B:50:0x0107, B:52:0x0121, B:53:0x0131, B:54:0x0135, B:56:0x0139, B:58:0x014e, B:59:0x0151, B:76:0x01be, B:39:0x00d2, B:40:0x00d7, B:12:0x0043, B:18:0x005e), top: B:175:0x0007, inners: #4, #7 }] */
    /* JADX WARN: Code duplicated, block: B:47:0x00fb A[Catch: Exception -> 0x03c6, TryCatch #5 {Exception -> 0x03c6, blocks: (B:3:0x0007, B:5:0x002d, B:8:0x0033, B:10:0x003f, B:13:0x0058, B:20:0x0066, B:21:0x0068, B:22:0x0069, B:24:0x006d, B:26:0x0071, B:27:0x0074, B:29:0x0078, B:30:0x007b, B:32:0x0097, B:33:0x009a, B:35:0x009e, B:36:0x00a1, B:38:0x00d1, B:44:0x00db, B:45:0x00dc, B:47:0x00fb, B:48:0x0101, B:50:0x0107, B:52:0x0121, B:53:0x0131, B:54:0x0135, B:56:0x0139, B:58:0x014e, B:59:0x0151, B:76:0x01be, B:39:0x00d2, B:40:0x00d7, B:12:0x0043, B:18:0x005e), top: B:175:0x0007, inners: #4, #7 }] */
    /* JADX WARN: Code duplicated, block: B:50:0x0107 A[Catch: Exception -> 0x03c6, TryCatch #5 {Exception -> 0x03c6, blocks: (B:3:0x0007, B:5:0x002d, B:8:0x0033, B:10:0x003f, B:13:0x0058, B:20:0x0066, B:21:0x0068, B:22:0x0069, B:24:0x006d, B:26:0x0071, B:27:0x0074, B:29:0x0078, B:30:0x007b, B:32:0x0097, B:33:0x009a, B:35:0x009e, B:36:0x00a1, B:38:0x00d1, B:44:0x00db, B:45:0x00dc, B:47:0x00fb, B:48:0x0101, B:50:0x0107, B:52:0x0121, B:53:0x0131, B:54:0x0135, B:56:0x0139, B:58:0x014e, B:59:0x0151, B:76:0x01be, B:39:0x00d2, B:40:0x00d7, B:12:0x0043, B:18:0x005e), top: B:175:0x0007, inners: #4, #7 }] */
    /* JADX WARN: Code duplicated, block: B:56:0x0139 A[Catch: Exception -> 0x03c6, TryCatch #5 {Exception -> 0x03c6, blocks: (B:3:0x0007, B:5:0x002d, B:8:0x0033, B:10:0x003f, B:13:0x0058, B:20:0x0066, B:21:0x0068, B:22:0x0069, B:24:0x006d, B:26:0x0071, B:27:0x0074, B:29:0x0078, B:30:0x007b, B:32:0x0097, B:33:0x009a, B:35:0x009e, B:36:0x00a1, B:38:0x00d1, B:44:0x00db, B:45:0x00dc, B:47:0x00fb, B:48:0x0101, B:50:0x0107, B:52:0x0121, B:53:0x0131, B:54:0x0135, B:56:0x0139, B:58:0x014e, B:59:0x0151, B:76:0x01be, B:39:0x00d2, B:40:0x00d7, B:12:0x0043, B:18:0x005e), top: B:175:0x0007, inners: #4, #7 }] */
    /* JADX WARN: Code duplicated, block: B:58:0x014e A[Catch: Exception -> 0x03c6, TryCatch #5 {Exception -> 0x03c6, blocks: (B:3:0x0007, B:5:0x002d, B:8:0x0033, B:10:0x003f, B:13:0x0058, B:20:0x0066, B:21:0x0068, B:22:0x0069, B:24:0x006d, B:26:0x0071, B:27:0x0074, B:29:0x0078, B:30:0x007b, B:32:0x0097, B:33:0x009a, B:35:0x009e, B:36:0x00a1, B:38:0x00d1, B:44:0x00db, B:45:0x00dc, B:47:0x00fb, B:48:0x0101, B:50:0x0107, B:52:0x0121, B:53:0x0131, B:54:0x0135, B:56:0x0139, B:58:0x014e, B:59:0x0151, B:76:0x01be, B:39:0x00d2, B:40:0x00d7, B:12:0x0043, B:18:0x005e), top: B:175:0x0007, inners: #4, #7 }] */
    /* JADX WARN: Code duplicated, block: B:67:0x0192 A[Catch: Exception -> 0x01b5, TryCatch #6 {Exception -> 0x01b5, blocks: (B:65:0x0183, B:67:0x0192, B:69:0x01ab, B:68:0x01a0), top: B:176:0x0183 }] */
    /* JADX WARN: Code duplicated, block: B:68:0x01a0 A[Catch: Exception -> 0x01b5, TryCatch #6 {Exception -> 0x01b5, blocks: (B:65:0x0183, B:67:0x0192, B:69:0x01ab, B:68:0x01a0), top: B:176:0x0183 }] */
    /* JADX WARN: Code duplicated, block: B:76:0x01be A[Catch: Exception -> 0x03c6, TRY_ENTER, TRY_LEAVE, TryCatch #5 {Exception -> 0x03c6, blocks: (B:3:0x0007, B:5:0x002d, B:8:0x0033, B:10:0x003f, B:13:0x0058, B:20:0x0066, B:21:0x0068, B:22:0x0069, B:24:0x006d, B:26:0x0071, B:27:0x0074, B:29:0x0078, B:30:0x007b, B:32:0x0097, B:33:0x009a, B:35:0x009e, B:36:0x00a1, B:38:0x00d1, B:44:0x00db, B:45:0x00dc, B:47:0x00fb, B:48:0x0101, B:50:0x0107, B:52:0x0121, B:53:0x0131, B:54:0x0135, B:56:0x0139, B:58:0x014e, B:59:0x0151, B:76:0x01be, B:39:0x00d2, B:40:0x00d7, B:12:0x0043, B:18:0x005e), top: B:175:0x0007, inners: #4, #7 }] */
    @Override // com.badlogic.gdx.ApplicationListener
    public void render() {
        long jNanoTime;
        long j;
        ThreeDManager threeDManager;
        File file;
        int i;
        int i2;
        int i3;
        int i4;
        StageActivity stageActivity;
        int iMin;
        int i5;
        int width;
        int i6;
        File file2;
        ShaderProgram shaderProgram;
        long jNanoTime2;
        float fMin;
        SceneManager sceneManager;
        int iMax;
        float f;
        long jNanoTime3;
        int i7;
        long jNanoTime4;
        ThreeDManager threeDManager2;
        SceneManager sceneManager2;
        PenActor penActor;
        PlotActor plotActor;
        StageDialog stageDialog;
        VncClient vncClient;
        processPendingNotificationActions();
        long j2 = 0;
        try {
            Look.tickGlobalFrame();
            NativeViewBindingManager.updateBindings(this.camera, this.viewPort);
            Gdx.gl20.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
            Gdx.gl20.glClear(16640);
            StageActivity stageActivity2 = StageActivity.activeStageActivity.get();
            if (this.isVmDisplayVisible && this.vmTexture != null && stageActivity2 != null && (vncClient = stageActivity2.vncClients.get(StageActivity.DEFAULT_VM_NAME)) != null && stageActivity2.frameReadyToRender) {
                try {
                    try {
                        this.vmTexture.bind();
                        Gdx.gl.glPixelStorei(3317, 1);
                        vncClient.uploadFrameTexture();
                        Gdx.gl.glPixelStorei(3317, 4);
                    } catch (Exception e) {
                        Log.e("VNC_RENDER", "Error uploading VM frame", e);
                    }
                    stageActivity2.frameReadyToRender = false;
                    if (this.reloadProject) {
                        threeDManager2 = this.threeDManager;
                        if (threeDManager2 != null) {
                            threeDManager2.dispose();
                        }
                        sceneManager2 = this.sceneManager;
                        if (sceneManager2 != null) {
                            sceneManager2.clearScene();
                        }
                        ThreeDManager threeDManager3 = new ThreeDManager();
                        this.threeDManager = threeDManager3;
                        threeDManager3.init();
                        this.sceneManager = new SceneManager(this.threeDManager);
                        this.stage.clear();
                        penActor = this.penActor;
                        if (penActor != null) {
                            penActor.dispose();
                        }
                        plotActor = this.plotActor;
                        if (plotActor != null) {
                            plotActor.dispose();
                        }
                        this.embroideryPatternManager.clear();
                        SoundManager.getInstance().clear();
                        this.physicsWorld = this.scene.resetPhysicsWorld();
                        initActors(this.sprites);
                        initStageInputListener();
                        this.paused = true;
                        this.scene.firstStart = true;
                        this.reloadProject = false;
                        this.cameraPositioner.reset();
                        resetCamera();
                        stageDialog = this.stageDialog;
                        if (stageDialog != null) {
                            synchronized (stageDialog) {
                                this.stageDialog.notify();
                            }
                        }
                    }
                    RenderTextureManager.INSTANCE.renderAllTargets(this.batch);
                    this.batch.setProjectionMatrix(this.camera.combined);
                    this.shapeRenderer.setProjectionMatrix(this.camera.combined);
                    if (this.scene.firstStart) {
                        for (Sprite sprite : this.sprites) {
                            sprite.initializeEventThreads(3);
                            sprite.initConditionScriptTriggers();
                            sprite.initIfConditionBrickTriggers();
                            if (!sprite.getLookList().isEmpty()) {
                                sprite.look.setLookData(sprite.getLookList().get(0));
                            }
                        }
                        this.scene.firstStart = false;
                    }
                    if (!this.paused) {
                        jNanoTime2 = System.nanoTime();
                        fMin = Math.min(Gdx.graphics.getDeltaTime(), 0.05f);
                        sceneManager = this.sceneManager;
                        if (sceneManager != null) {
                            sceneManager.update(fMin);
                        }
                        iMax = (int) Math.max(1.0f, this.deltaActionTimeDivisor);
                        f = fMin / iMax;
                        jNanoTime3 = 0;
                        for (i7 = 0; i7 < iMax; i7++) {
                            long jNanoTime5 = System.nanoTime();
                            this.physicsWorld.step(f);
                            jNanoTime3 += System.nanoTime() - jNanoTime5;
                            this.stage.act(f);
                            this.uiStage.act(f);
                        }
                        jNanoTime4 = System.nanoTime();
                        j = (jNanoTime4 - jNanoTime2) - jNanoTime3;
                        if ((System.nanoTime() - jNanoTime2) / PackingOptions.SEGMENT_LIMIT <= 8) {
                            float f2 = this.deltaActionTimeDivisor + 1.0f;
                            this.deltaActionTimeDivisor = f2;
                            this.deltaActionTimeDivisor = Math.min(10.0f, f2);
                        } else {
                            float f3 = this.deltaActionTimeDivisor - 1.0f;
                            this.deltaActionTimeDivisor = f3;
                            this.deltaActionTimeDivisor = Math.max(1.0f, f3);
                        }
                        DebugMenuManager.getInstance().updateIfVisible();
                        jNanoTime = jNanoTime4;
                        j2 = jNanoTime3;
                    } else {
                        jNanoTime = System.nanoTime();
                        j = 0;
                    }
                    if (this.isVmDisplayVisible) {
                        this.batch.setProjectionMatrix(this.camera.combined);
                        this.batch.setShader(this.vncSwizzleShader);
                        this.batch.begin();
                        this.batch.draw(this.vmTexture, this.vmX, this.vmY, this.vmWidth, this.vmHeight);
                        this.batch.end();
                        this.batch.setShader(null);
                    }
                    if (!this.finished) {
                        if (!this.paused) {
                            threeDManager.update(Gdx.graphics.getDeltaTime());
                        }
                        updatePinnedSprites();
                        if (this.hasBeforeUpdateScripts) {
                            executeBeforeUpdateScripts(Gdx.graphics.getDeltaTime());
                        }
                        if (this.threeDManager != null) {
                            this.threeDManager.render();
                        }
                        if (this.fastTwoDManager != null) {
                            this.fastTwoDManager.updateAndRender(Gdx.graphics.getDeltaTime());
                        }
                        if (RenderTextureManager.isMain2DRenderEnabled()) {
                            this.stage.draw();
                            this.uiStage.draw();
                        }
                        this.firstFrameDrawn = true;
                        if (this.makeScreenshot) {
                            Scene currentlyEditedScene = ProjectManager.getInstance().getCurrentlyEditedScene();
                            file = new File(currentlyEditedScene.getDirectory() + "/" + Constants.SCREENSHOT_MANUAL_FILE_NAME);
                            if (file.exists()) {
                                i = this.screenshotX;
                                i2 = this.screenshotY;
                                i3 = this.screenshotWidth;
                                i4 = this.screenshotHeight;
                                stageActivity = StageActivity.activeStageActivity.get();
                                if (stageActivity == null) {
                                    int iMax2 = Math.max(0, i);
                                    int iMax3 = Math.max(0, i2);
                                    int iMin2 = Math.min(Gdx.graphics.getWidth() - iMax2, i3);
                                    iMin = Math.min(Gdx.graphics.getHeight() - iMax3, i4);
                                    i5 = iMax2;
                                    width = iMin2;
                                    i6 = iMax3;
                                } else {
                                    int iMax4 = Math.max(0, i);
                                    int iMax5 = Math.max(0, i2);
                                    int iMin3 = Math.min(Gdx.graphics.getWidth() - iMax4, i3);
                                    iMin = Math.min(Gdx.graphics.getHeight() - iMax5, i4);
                                    i5 = iMax4;
                                    width = iMin3;
                                    i6 = iMax5;
                                }
                                if (width <= 0) {
                                    width = 1;
                                }
                                if (iMin <= 0) {
                                    iMin = 1;
                                }
                                new ScreenshotSaver(Gdx.files, getScreenshotPath(), width, iMin).saveScreenshotAndNotify(ScreenUtils.getFrameBufferPixels(i5, i6, width, iMin, true), this.screenshotName, new ScreenshotSaverCallback() { // from class: org.catrobat.catroid.stage.StageListener$$ExternalSyntheticLambda1
                                    @Override // org.catrobat.catroid.stage.ScreenshotSaverCallback
                                    public final void screenshotSaved(boolean z) {
                                        this.f$0.notifyScreenshotCallbackAndCleanup(Boolean.valueOf(z));
                                    }
                                }, GlobalScope.INSTANCE);
                            } else {
                                i = this.screenshotX;
                                i2 = this.screenshotY;
                                i3 = this.screenshotWidth;
                                i4 = this.screenshotHeight;
                                stageActivity = StageActivity.activeStageActivity.get();
                                if (stageActivity == null) {
                                    int iMax6 = Math.max(0, i);
                                    int iMax7 = Math.max(0, i2);
                                    int iMin4 = Math.min(Gdx.graphics.getWidth() - iMax6, i3);
                                    iMin = Math.min(Gdx.graphics.getHeight() - iMax7, i4);
                                    i5 = iMax6;
                                    width = iMin4;
                                    i6 = iMax7;
                                } else {
                                    int iMax8 = Math.max(0, i);
                                    int iMax9 = Math.max(0, i2);
                                    int iMin5 = Math.min(Gdx.graphics.getWidth() - iMax8, i3);
                                    iMin = Math.min(Gdx.graphics.getHeight() - iMax9, i4);
                                    i5 = iMax8;
                                    width = iMin5;
                                    i6 = iMax9;
                                }
                                if (width <= 0) {
                                    width = 1;
                                }
                                if (iMin <= 0) {
                                    iMin = 1;
                                }
                                new ScreenshotSaver(Gdx.files, getScreenshotPath(), width, iMin).saveScreenshotAndNotify(ScreenUtils.getFrameBufferPixels(i5, i6, width, iMin, true), this.screenshotName, new ScreenshotSaverCallback() { // from class: org.catrobat.catroid.stage.StageListener$$ExternalSyntheticLambda1
                                    @Override // org.catrobat.catroid.stage.ScreenshotSaverCallback
                                    public final void screenshotSaved(boolean z) {
                                        this.f$0.notifyScreenshotCallbackAndCleanup(Boolean.valueOf(z));
                                    }
                                }, GlobalScope.INSTANCE);
                            }
                            file2 = new File(currentlyEditedScene.getDirectory() + "/" + Constants.SCREENSHOT_AUTOMATIC_FILE_NAME);
                            if (file.exists()) {
                                file2.delete();
                            }
                            this.makeScreenshot = false;
                        }
                        if (this.axesOn) {
                            drawAxes();
                        }
                        if (this.makeTestPixels) {
                            this.testPixels = ScreenUtils.getFrameBufferPixels(this.testX, this.testY, this.testWidth, this.testHeight, false);
                            this.makeTestPixels = false;
                        }
                        this.cameraPositioner.updateCameraPositionForFocusedSprite();
                        if (this.hasAfterUpdateScripts) {
                            executeAfterUpdateScripts(Gdx.graphics.getDeltaTime());
                        }
                    } else {
                        if (this.makeScreenshot) {
                            Scene currentlyEditedScene2 = ProjectManager.getInstance().getCurrentlyEditedScene();
                            file = new File(currentlyEditedScene2.getDirectory() + "/" + Constants.SCREENSHOT_MANUAL_FILE_NAME);
                            if (file.exists()) {
                                i = this.screenshotX;
                                i2 = this.screenshotY;
                                i3 = this.screenshotWidth;
                                i4 = this.screenshotHeight;
                                stageActivity = StageActivity.activeStageActivity.get();
                                if (stageActivity == null) {
                                    int iMax10 = Math.max(0, i);
                                    int iMax11 = Math.max(0, i2);
                                    int iMin6 = Math.min(Gdx.graphics.getWidth() - iMax10, i3);
                                    iMin = Math.min(Gdx.graphics.getHeight() - iMax11, i4);
                                    i5 = iMax10;
                                    width = iMin6;
                                    i6 = iMax11;
                                } else {
                                    int iMax12 = Math.max(0, i);
                                    int iMax13 = Math.max(0, i2);
                                    int iMin7 = Math.min(Gdx.graphics.getWidth() - iMax12, i3);
                                    iMin = Math.min(Gdx.graphics.getHeight() - iMax13, i4);
                                    i5 = iMax12;
                                    width = iMin7;
                                    i6 = iMax13;
                                }
                                if (width <= 0) {
                                    width = 1;
                                }
                                if (iMin <= 0) {
                                    iMin = 1;
                                }
                                new ScreenshotSaver(Gdx.files, getScreenshotPath(), width, iMin).saveScreenshotAndNotify(ScreenUtils.getFrameBufferPixels(i5, i6, width, iMin, true), this.screenshotName, new ScreenshotSaverCallback() { // from class: org.catrobat.catroid.stage.StageListener$$ExternalSyntheticLambda1
                                    @Override // org.catrobat.catroid.stage.ScreenshotSaverCallback
                                    public final void screenshotSaved(boolean z) {
                                        this.f$0.notifyScreenshotCallbackAndCleanup(Boolean.valueOf(z));
                                    }
                                }, GlobalScope.INSTANCE);
                            } else {
                                i = this.screenshotX;
                                i2 = this.screenshotY;
                                i3 = this.screenshotWidth;
                                i4 = this.screenshotHeight;
                                stageActivity = StageActivity.activeStageActivity.get();
                                if (stageActivity == null) {
                                    int iMax14 = Math.max(0, i);
                                    int iMax15 = Math.max(0, i2);
                                    int iMin8 = Math.min(Gdx.graphics.getWidth() - iMax14, i3);
                                    iMin = Math.min(Gdx.graphics.getHeight() - iMax15, i4);
                                    i5 = iMax14;
                                    width = iMin8;
                                    i6 = iMax15;
                                } else {
                                    int iMax16 = Math.max(0, i);
                                    int iMax17 = Math.max(0, i2);
                                    int iMin9 = Math.min(Gdx.graphics.getWidth() - iMax16, i3);
                                    iMin = Math.min(Gdx.graphics.getHeight() - iMax17, i4);
                                    i5 = iMax16;
                                    width = iMin9;
                                    i6 = iMax17;
                                }
                                if (width <= 0) {
                                    width = 1;
                                }
                                if (iMin <= 0) {
                                    iMin = 1;
                                }
                                new ScreenshotSaver(Gdx.files, getScreenshotPath(), width, iMin).saveScreenshotAndNotify(ScreenUtils.getFrameBufferPixels(i5, i6, width, iMin, true), this.screenshotName, new ScreenshotSaverCallback() { // from class: org.catrobat.catroid.stage.StageListener$$ExternalSyntheticLambda1
                                    @Override // org.catrobat.catroid.stage.ScreenshotSaverCallback
                                    public final void screenshotSaved(boolean z) {
                                        this.f$0.notifyScreenshotCallbackAndCleanup(Boolean.valueOf(z));
                                    }
                                }, GlobalScope.INSTANCE);
                            }
                            file2 = new File(currentlyEditedScene2.getDirectory() + "/" + Constants.SCREENSHOT_AUTOMATIC_FILE_NAME);
                            if (file.exists()) {
                                file2.delete();
                            }
                            this.makeScreenshot = false;
                        }
                        if (this.axesOn) {
                            drawAxes();
                        }
                        if (this.makeTestPixels) {
                            this.testPixels = ScreenUtils.getFrameBufferPixels(this.testX, this.testY, this.testWidth, this.testHeight, false);
                            this.makeTestPixels = false;
                        }
                        this.cameraPositioner.updateCameraPositionForFocusedSprite();
                        if (this.hasAfterUpdateScripts) {
                            executeAfterUpdateScripts(Gdx.graphics.getDeltaTime());
                        }
                    }
                } catch (Throwable th) {
                    stageActivity2.frameReadyToRender = false;
                    throw th;
                }
            } else {
                if (this.reloadProject) {
                    threeDManager2 = this.threeDManager;
                    if (threeDManager2 != null) {
                        threeDManager2.dispose();
                    }
                    sceneManager2 = this.sceneManager;
                    if (sceneManager2 != null) {
                        sceneManager2.clearScene();
                    }
                    ThreeDManager threeDManager4 = new ThreeDManager();
                    this.threeDManager = threeDManager4;
                    threeDManager4.init();
                    this.sceneManager = new SceneManager(this.threeDManager);
                    this.stage.clear();
                    penActor = this.penActor;
                    if (penActor != null) {
                        penActor.dispose();
                    }
                    plotActor = this.plotActor;
                    if (plotActor != null) {
                        plotActor.dispose();
                    }
                    this.embroideryPatternManager.clear();
                    SoundManager.getInstance().clear();
                    this.physicsWorld = this.scene.resetPhysicsWorld();
                    initActors(this.sprites);
                    initStageInputListener();
                    this.paused = true;
                    this.scene.firstStart = true;
                    this.reloadProject = false;
                    this.cameraPositioner.reset();
                    resetCamera();
                    stageDialog = this.stageDialog;
                    if (stageDialog != null) {
                        synchronized (stageDialog) {
                            this.stageDialog.notify();
                        }
                    }
                }
                RenderTextureManager.INSTANCE.renderAllTargets(this.batch);
                this.batch.setProjectionMatrix(this.camera.combined);
                this.shapeRenderer.setProjectionMatrix(this.camera.combined);
                if (this.scene.firstStart) {
                    while (r0.hasNext()) {
                        sprite.initializeEventThreads(3);
                        sprite.initConditionScriptTriggers();
                        sprite.initIfConditionBrickTriggers();
                        if (!sprite.getLookList().isEmpty()) {
                            sprite.look.setLookData(sprite.getLookList().get(0));
                        }
                    }
                    this.scene.firstStart = false;
                }
                if (!this.paused) {
                    jNanoTime2 = System.nanoTime();
                    fMin = Math.min(Gdx.graphics.getDeltaTime(), 0.05f);
                    sceneManager = this.sceneManager;
                    if (sceneManager != null) {
                        sceneManager.update(fMin);
                    }
                    iMax = (int) Math.max(1.0f, this.deltaActionTimeDivisor);
                    f = fMin / iMax;
                    jNanoTime3 = 0;
                    while (i7 < iMax) {
                        try {
                            long jNanoTime6 = System.nanoTime();
                            this.physicsWorld.step(f);
                            jNanoTime3 += System.nanoTime() - jNanoTime6;
                            this.stage.act(f);
                            this.uiStage.act(f);
                        } catch (Exception e2) {
                            e = e2;
                            jNanoTime = 0;
                            j = 0;
                            j2 = jNanoTime3;
                            Log.e("RENDER_CRASH", "Fatal error during render loop", e);
                            PerformanceTracker.recordFrame(j2, j, System.nanoTime() - jNanoTime);
                        }
                    }
                    jNanoTime4 = System.nanoTime();
                    j = (jNanoTime4 - jNanoTime2) - jNanoTime3;
                    try {
                        if ((System.nanoTime() - jNanoTime2) / PackingOptions.SEGMENT_LIMIT <= 8) {
                            float f4 = this.deltaActionTimeDivisor + 1.0f;
                            this.deltaActionTimeDivisor = f4;
                            this.deltaActionTimeDivisor = Math.min(10.0f, f4);
                        } else {
                            float f5 = this.deltaActionTimeDivisor - 1.0f;
                            this.deltaActionTimeDivisor = f5;
                            this.deltaActionTimeDivisor = Math.max(1.0f, f5);
                        }
                        DebugMenuManager.getInstance().updateIfVisible();
                        jNanoTime = jNanoTime4;
                        j2 = jNanoTime3;
                    } catch (Exception e3) {
                        e = e3;
                        jNanoTime = jNanoTime4;
                        j2 = jNanoTime3;
                        Log.e("RENDER_CRASH", "Fatal error during render loop", e);
                        PerformanceTracker.recordFrame(j2, j, System.nanoTime() - jNanoTime);
                    }
                } else {
                    jNanoTime = System.nanoTime();
                    j = 0;
                }
                try {
                    if (this.isVmDisplayVisible && this.vmTexture != null && (shaderProgram = this.vncSwizzleShader) != null && shaderProgram.isCompiled()) {
                        this.batch.setProjectionMatrix(this.camera.combined);
                        this.batch.setShader(this.vncSwizzleShader);
                        this.batch.begin();
                        this.batch.draw(this.vmTexture, this.vmX, this.vmY, this.vmWidth, this.vmHeight);
                        this.batch.end();
                        this.batch.setShader(null);
                    }
                    if (!this.finished) {
                        try {
                            if (!this.paused && (threeDManager = this.threeDManager) != null) {
                                threeDManager.update(Gdx.graphics.getDeltaTime());
                            }
                            updatePinnedSprites();
                            if (this.hasBeforeUpdateScripts && !this.paused) {
                                executeBeforeUpdateScripts(Gdx.graphics.getDeltaTime());
                            }
                            try {
                                if (this.threeDManager != null && RenderTextureManager.isMain3DRenderEnabled()) {
                                    this.threeDManager.render();
                                }
                            } catch (Exception e4) {
                                Log.e("3DRENDER", "ERROR: " + e4);
                            }
                            if (this.fastTwoDManager != null && !this.paused && RenderTextureManager.isMainFast2DRenderEnabled()) {
                                this.fastTwoDManager.updateAndRender(Gdx.graphics.getDeltaTime());
                            }
                            if (RenderTextureManager.isMain2DRenderEnabled()) {
                                this.stage.draw();
                                this.uiStage.draw();
                            }
                        } catch (Exception e5) {
                            Log.e("RENDER", "FATAL ERROR: " + e5);
                        }
                        this.firstFrameDrawn = true;
                        if (this.makeScreenshot) {
                            Scene currentlyEditedScene3 = ProjectManager.getInstance().getCurrentlyEditedScene();
                            file = new File(currentlyEditedScene3.getDirectory() + "/" + Constants.SCREENSHOT_MANUAL_FILE_NAME);
                            if (file.exists() || Objects.equals(this.screenshotName, Constants.SCREENSHOT_MANUAL_FILE_NAME)) {
                                i = this.screenshotX;
                                i2 = this.screenshotY;
                                i3 = this.screenshotWidth;
                                i4 = this.screenshotHeight;
                                stageActivity = StageActivity.activeStageActivity.get();
                                if (stageActivity == null && "StageWorkspaceActivity".equals(stageActivity.getClass().getSimpleName())) {
                                    width = Gdx.graphics.getWidth();
                                    iMin = Gdx.graphics.getHeight();
                                    i5 = 0;
                                    i6 = 0;
                                } else {
                                    int iMax18 = Math.max(0, i);
                                    int iMax19 = Math.max(0, i2);
                                    int iMin10 = Math.min(Gdx.graphics.getWidth() - iMax18, i3);
                                    iMin = Math.min(Gdx.graphics.getHeight() - iMax19, i4);
                                    i5 = iMax18;
                                    width = iMin10;
                                    i6 = iMax19;
                                }
                                if (width <= 0) {
                                    width = 1;
                                }
                                if (iMin <= 0) {
                                    iMin = 1;
                                }
                                new ScreenshotSaver(Gdx.files, getScreenshotPath(), width, iMin).saveScreenshotAndNotify(ScreenUtils.getFrameBufferPixels(i5, i6, width, iMin, true), this.screenshotName, new ScreenshotSaverCallback() { // from class: org.catrobat.catroid.stage.StageListener$$ExternalSyntheticLambda1
                                    @Override // org.catrobat.catroid.stage.ScreenshotSaverCallback
                                    public final void screenshotSaved(boolean z) {
                                        this.f$0.notifyScreenshotCallbackAndCleanup(Boolean.valueOf(z));
                                    }
                                }, GlobalScope.INSTANCE);
                            }
                            file2 = new File(currentlyEditedScene3.getDirectory() + "/" + Constants.SCREENSHOT_AUTOMATIC_FILE_NAME);
                            if (file.exists() && file2.exists()) {
                                file2.delete();
                            }
                            this.makeScreenshot = false;
                        }
                        if (this.axesOn && !this.finished) {
                            drawAxes();
                        }
                        if (this.makeTestPixels) {
                            this.testPixels = ScreenUtils.getFrameBufferPixels(this.testX, this.testY, this.testWidth, this.testHeight, false);
                            this.makeTestPixels = false;
                        }
                        this.cameraPositioner.updateCameraPositionForFocusedSprite();
                        if (this.hasAfterUpdateScripts && !this.paused) {
                            executeAfterUpdateScripts(Gdx.graphics.getDeltaTime());
                        }
                    } else {
                        if (this.makeScreenshot) {
                            Scene currentlyEditedScene4 = ProjectManager.getInstance().getCurrentlyEditedScene();
                            file = new File(currentlyEditedScene4.getDirectory() + "/" + Constants.SCREENSHOT_MANUAL_FILE_NAME);
                            if (file.exists()) {
                                i = this.screenshotX;
                                i2 = this.screenshotY;
                                i3 = this.screenshotWidth;
                                i4 = this.screenshotHeight;
                                stageActivity = StageActivity.activeStageActivity.get();
                                if (stageActivity == null) {
                                    int iMax110 = Math.max(0, i);
                                    int iMax111 = Math.max(0, i2);
                                    int iMin11 = Math.min(Gdx.graphics.getWidth() - iMax110, i3);
                                    iMin = Math.min(Gdx.graphics.getHeight() - iMax111, i4);
                                    i5 = iMax110;
                                    width = iMin11;
                                    i6 = iMax111;
                                } else {
                                    int iMax112 = Math.max(0, i);
                                    int iMax113 = Math.max(0, i2);
                                    int iMin12 = Math.min(Gdx.graphics.getWidth() - iMax112, i3);
                                    iMin = Math.min(Gdx.graphics.getHeight() - iMax113, i4);
                                    i5 = iMax112;
                                    width = iMin12;
                                    i6 = iMax113;
                                }
                                if (width <= 0) {
                                    width = 1;
                                }
                                if (iMin <= 0) {
                                    iMin = 1;
                                }
                                new ScreenshotSaver(Gdx.files, getScreenshotPath(), width, iMin).saveScreenshotAndNotify(ScreenUtils.getFrameBufferPixels(i5, i6, width, iMin, true), this.screenshotName, new ScreenshotSaverCallback() { // from class: org.catrobat.catroid.stage.StageListener$$ExternalSyntheticLambda1
                                    @Override // org.catrobat.catroid.stage.ScreenshotSaverCallback
                                    public final void screenshotSaved(boolean z) {
                                        this.f$0.notifyScreenshotCallbackAndCleanup(Boolean.valueOf(z));
                                    }
                                }, GlobalScope.INSTANCE);
                            } else {
                                i = this.screenshotX;
                                i2 = this.screenshotY;
                                i3 = this.screenshotWidth;
                                i4 = this.screenshotHeight;
                                stageActivity = StageActivity.activeStageActivity.get();
                                if (stageActivity == null) {
                                    int iMax114 = Math.max(0, i);
                                    int iMax115 = Math.max(0, i2);
                                    int iMin13 = Math.min(Gdx.graphics.getWidth() - iMax114, i3);
                                    iMin = Math.min(Gdx.graphics.getHeight() - iMax115, i4);
                                    i5 = iMax114;
                                    width = iMin13;
                                    i6 = iMax115;
                                } else {
                                    int iMax116 = Math.max(0, i);
                                    int iMax117 = Math.max(0, i2);
                                    int iMin14 = Math.min(Gdx.graphics.getWidth() - iMax116, i3);
                                    iMin = Math.min(Gdx.graphics.getHeight() - iMax117, i4);
                                    i5 = iMax116;
                                    width = iMin14;
                                    i6 = iMax117;
                                }
                                if (width <= 0) {
                                    width = 1;
                                }
                                if (iMin <= 0) {
                                    iMin = 1;
                                }
                                new ScreenshotSaver(Gdx.files, getScreenshotPath(), width, iMin).saveScreenshotAndNotify(ScreenUtils.getFrameBufferPixels(i5, i6, width, iMin, true), this.screenshotName, new ScreenshotSaverCallback() { // from class: org.catrobat.catroid.stage.StageListener$$ExternalSyntheticLambda1
                                    @Override // org.catrobat.catroid.stage.ScreenshotSaverCallback
                                    public final void screenshotSaved(boolean z) {
                                        this.f$0.notifyScreenshotCallbackAndCleanup(Boolean.valueOf(z));
                                    }
                                }, GlobalScope.INSTANCE);
                            }
                            file2 = new File(currentlyEditedScene4.getDirectory() + "/" + Constants.SCREENSHOT_AUTOMATIC_FILE_NAME);
                            if (file.exists()) {
                                file2.delete();
                            }
                            this.makeScreenshot = false;
                        }
                        if (this.axesOn) {
                            drawAxes();
                        }
                        if (this.makeTestPixels) {
                            this.testPixels = ScreenUtils.getFrameBufferPixels(this.testX, this.testY, this.testWidth, this.testHeight, false);
                            this.makeTestPixels = false;
                        }
                        this.cameraPositioner.updateCameraPositionForFocusedSprite();
                        if (this.hasAfterUpdateScripts) {
                            executeAfterUpdateScripts(Gdx.graphics.getDeltaTime());
                        }
                    }
                } catch (Exception e6) {
                    e = e6;
                    Log.e("RENDER_CRASH", "Fatal error during render loop", e);
                }
            }
        } catch (Exception e7) {
            e = e7;
            jNanoTime = 0;
            j = 0;
            Log.e("RENDER_CRASH", "Fatal error during render loop", e);
            PerformanceTracker.recordFrame(j2, j, System.nanoTime() - jNanoTime);
        }
        PerformanceTracker.recordFrame(j2, j, System.nanoTime() - jNanoTime);
    }

    private void printPhysicsLabelOnScreen() {
        this.batch.setProjectionMatrix(this.camera.combined);
        this.batch.begin();
        for (Sprite sprite : this.sprites) {
            if (sprite.look instanceof PhysicsLook) {
                PhysicsObject physicsObject = this.physicsWorld.getPhysicsObject(sprite);
                float x = physicsObject.getX();
                float y = physicsObject.getY();
                float xHeight = this.font.getXHeight();
                this.debugTextBuilder.setLength(0);
                this.debugTextBuilder.append("velocity_x: ").append(physicsObject.getVelocity().x);
                this.font.draw(this.batch, this.debugTextBuilder, x, y);
                this.debugTextBuilder.setLength(0);
                this.debugTextBuilder.append("velocity_y: ").append(physicsObject.getVelocity().y);
                this.font.draw(this.batch, this.debugTextBuilder, x, y + xHeight + 5.0f);
                this.debugTextBuilder.setLength(0);
                this.debugTextBuilder.append("angular velocity: ").append(physicsObject.getRotationSpeed());
                this.font.draw(this.batch, this.debugTextBuilder, x, (2.0f * xHeight) + y + 10.0f);
                this.debugTextBuilder.setLength(0);
                this.debugTextBuilder.append("direction: ").append(physicsObject.getDirection());
                this.font.draw(this.batch, this.debugTextBuilder, x, y + (xHeight * 3.0f) + 15.0f);
            }
        }
        this.batch.end();
    }

    private void drawAxes() {
        GlyphLayout glyphLayout = new GlyphLayout();
        glyphLayout.setText(this.font, String.valueOf((int) this.virtualWidthHalf));
        this.batch.setProjectionMatrix(this.camera.combined);
        this.batch.begin();
        this.batch.draw(this.axes, -this.virtualWidthHalf, -2.0f, this.virtualWidth, 4.0f);
        this.batch.draw(this.axes, -2.0f, -this.virtualHeightHalf, 4.0f, this.virtualHeight);
        float f = glyphLayout.height / 2.0f;
        float f2 = -f;
        this.font.draw(this.batch, Parameters.DEFAULT_OPTION_PREFIXES + ((int) this.virtualWidthHalf), (-this.virtualWidthHalf) + f, f2);
        this.font.draw(this.batch, String.valueOf((int) this.virtualWidthHalf), (this.virtualWidthHalf - glyphLayout.width) - f, f2);
        this.font.draw(this.batch, Parameters.DEFAULT_OPTION_PREFIXES + ((int) this.virtualHeightHalf), f, (-this.virtualHeightHalf) + glyphLayout.height + f);
        this.font.draw(this.batch, String.valueOf((int) this.virtualHeightHalf), f, this.virtualHeightHalf - f);
        this.font.draw(this.batch, "0", f, f2);
        this.batch.end();
    }

    public PenActor getPenActor() {
        return this.penActor;
    }

    public PlotActor getPlotActor() {
        return this.plotActor;
    }

    @Override // com.badlogic.gdx.ApplicationListener
    public void resize(int width, int height) {
        Viewport viewport;
        Viewport viewport2;
        boolean z = StageActivity.activeStageActivity.get() instanceof StageWorkspaceActivity;
        boolean z2 = width > height;
        float f = this.virtualWidth;
        float f2 = this.virtualHeight;
        boolean z3 = f > f2;
        if (!z && z2 != z3) {
            this.virtualWidth = f2;
            this.virtualHeight = f;
            this.virtualWidthHalf = f2 / 2.0f;
            this.virtualHeightHalf = f / 2.0f;
            FrameBuffer frameBuffer = this.sceneFbo;
            if (frameBuffer != null) {
                frameBuffer.dispose();
            }
            this.sceneFbo = new FrameBuffer(Pixmap.Format.RGBA8888, Math.round(this.virtualWidth), Math.round(this.virtualHeight), false);
            FrameBuffer frameBuffer2 = this.postProcessFbo;
            if (frameBuffer2 != null) {
                frameBuffer2.dispose();
            }
            this.postProcessFbo = new FrameBuffer(Pixmap.Format.RGBA8888, Math.round(this.virtualWidth), Math.round(this.virtualHeight), false);
            TextureRegion textureRegion = this.fboRegion;
            if (textureRegion != null) {
                textureRegion.setTexture(this.sceneFbo.getColorBufferTexture());
            } else {
                this.fboRegion = new TextureRegion(this.sceneFbo.getColorBufferTexture());
            }
            this.fboRegion.flip(false, true);
            initScreenMode();
            Stage stage = this.stage;
            if (stage != null) {
                stage.setViewport(this.viewPort);
            }
            if (this.cameraPositioner != null) {
                this.cameraPositioner = new CameraPositioner(this.camera, this.virtualHeightHalf, this.virtualWidthHalf);
            }
            if (this.maxViewPort != null) {
                this.passepartout = new Passepartout(width, height, this.maxViewPort.getWidth(), this.maxViewPort.getHeight(), this.virtualWidth, this.virtualHeight);
            }
        }
        Stage stage2 = this.stage;
        if (stage2 != null && (viewport2 = this.viewPort) != null) {
            stage2.setViewport(viewport2);
        }
        Stage stage3 = this.uiStage;
        if (stage3 != null && (viewport = this.uiViewPort) != null) {
            stage3.setViewport(viewport);
        }
        this.camera.viewportWidth = width;
        this.camera.viewportHeight = height;
        Viewport viewport3 = this.viewPort;
        if (viewport3 != null) {
            viewport3.update(width, height, false);
        }
        Viewport viewport4 = this.uiViewPort;
        if (viewport4 != null) {
            viewport4.update(width, height, true);
        }
        OrthographicCamera orthographicCamera = this.camera;
        if (orthographicCamera != null) {
            orthographicCamera.update();
        }
        ThreeDManager threeDManager = this.threeDManager;
        if (threeDManager != null) {
            threeDManager.resize(width, height);
        }
        FastTwoDManager fastTwoDManager = this.fastTwoDManager;
        if (fastTwoDManager != null) {
            fastTwoDManager.resize(width, height);
        }
        if (this.isFirstResize) {
            this.isFirstResize = false;
        } else {
            broadcastEventToAllSprites(new EventId(18));
        }
    }

    public float getCameraPositionX() {
        OrthographicCamera orthographicCamera = this.camera;
        if (orthographicCamera != null) {
            return orthographicCamera.position.x;
        }
        return 0.0f;
    }

    public float getCameraPositionY() {
        OrthographicCamera orthographicCamera = this.camera;
        if (orthographicCamera != null) {
            return orthographicCamera.position.y;
        }
        return 0.0f;
    }

    public float getCameraZoom() {
        OrthographicCamera orthographicCamera = this.camera;
        if (orthographicCamera != null) {
            return orthographicCamera.zoom;
        }
        return 1.0f;
    }

    public float getCameraRotation() {
        return this.cameraRotation;
    }

    public void executeExitScriptsSynchronously() {
        List<Sprite> list;
        Log.d("StageListener", "Force-executing exit scripts...");
        if (ProjectManager.getInstance().getCurrentProject() == null || (list = this.sprites) == null) {
            Log.e("StageListener", "Cannot execute exit scripts, project or sprites are null.");
            return;
        }
        for (Sprite sprite : list) {
            for (Script script : sprite.getScriptList()) {
                if ((script instanceof ExitProjectScript) && !script.isCommentedOut()) {
                    Log.d("StageListener", "Found exit script in sprite: " + sprite.getName());
                    sprite.createSequenceAction(script).act(Float.MAX_VALUE);
                }
            }
        }
        Log.d("StageListener", "Finished executing exit scripts.");
    }

    private void broadcastEventToAllSprites(EventId eventId) {
        List<Sprite> list = this.sprites;
        if (list == null) {
            return;
        }
        for (Sprite sprite : list) {
            Multimap<EventId, ScriptSequenceAction> idToEventThreadMap = sprite.getIdToEventThreadMap();
            if (idToEventThreadMap != null && idToEventThreadMap.containsKey(eventId)) {
                for (ScriptSequenceAction scriptSequenceAction : idToEventThreadMap.get(eventId)) {
                    scriptSequenceAction.restart();
                    sprite.look.addAction(scriptSequenceAction);
                }
            }
        }
    }

    public void onNotificationActionTriggered(String clickedActionId) {
        List<Sprite> list = this.sprites;
        if (list == null || clickedActionId == null) {
            return;
        }
        for (Sprite sprite : list) {
            for (Script script : sprite.getScriptList()) {
                if ((script instanceof WhenNotificationActionTriggeredScript) && !script.isCommentedOut()) {
                    WhenNotificationActionTriggeredBrick whenNotificationActionTriggeredBrick = (WhenNotificationActionTriggeredBrick) script.getScriptBrick();
                    try {
                        if (clickedActionId.equals(whenNotificationActionTriggeredBrick.getActionIdFormula().interpretString(new Scope(this.project, sprite, null)))) {
                            ScriptSequenceAction scriptSequenceActionCreateSequenceAction = sprite.createSequenceAction(script);
                            scriptSequenceActionCreateSequenceAction.restart();
                            sprite.look.addAction(scriptSequenceActionCreateSequenceAction);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public void resetCamera() {
        OrthographicCamera orthographicCamera = this.camera;
        if (orthographicCamera != null) {
            orthographicCamera.position.set(0.0f, 0.0f, 0.0f);
            this.camera.zoom = 1.0f;
            this.camera.rotate(-this.cameraRotation);
            this.cameraRotation = 0.0f;
            this.camera.update();
        }
    }

    @Override // com.badlogic.gdx.ApplicationListener
    public void dispose() {
        if (this.isBackgroundModeEnabled) {
            this.isBackgroundModeEnabled = false;
            Context appContext = CatroidApplication.getAppContext();
            if (appContext != null) {
                appContext.stopService(new Intent(appContext, (Class<?>) NewCatroidBackgroundService.class));
            }
        }
        executeExitScriptsSynchronously();
        Stage stage = this.stage;
        if (stage != null) {
            Array.ArrayIterator<Actor> it = stage.getActors().iterator();
            while (it.hasNext()) {
                Actor next = it.next();
                if (next instanceof Look) {
                    ((Look) next).destroy();
                }
            }
        }
        Stage stage2 = this.uiStage;
        if (stage2 != null) {
            Array.ArrayIterator<Actor> it2 = stage2.getActors().iterator();
            while (it2.hasNext()) {
                Actor next2 = it2.next();
                if (next2 instanceof Look) {
                    ((Look) next2).destroy();
                }
            }
        }
        Stage stage3 = this.stage;
        if (stage3 != null) {
            stage3.dispose();
            this.stage = null;
        }
        Stage stage4 = this.uiStage;
        if (stage4 != null) {
            stage4.dispose();
            this.uiStage = null;
        }
        Mesh mesh = this.vmScreenMesh;
        if (mesh != null) {
            mesh.dispose();
        }
        Look.BrightnessContrastHueShader brightnessContrastHueShader = this.brightnessContrastHueShader;
        if (brightnessContrastHueShader != null) {
            brightnessContrastHueShader.dispose();
        }
        ShaderProgram shaderProgram = this.vncSwizzleShader;
        if (shaderProgram != null) {
            shaderProgram.dispose();
        }
        if (!this.finished) {
            finish();
        }
        Mesh mesh2 = this.fullscreenQuad;
        if (mesh2 != null) {
            mesh2.dispose();
        }
        FrameBuffer frameBuffer = this.postProcessFbo;
        if (frameBuffer != null) {
            frameBuffer.dispose();
        }
        ThreeDManager threeDManager = this.threeDManager;
        if (threeDManager != null) {
            threeDManager.dispose();
        }
        SceneManager sceneManager = this.sceneManager;
        if (sceneManager != null) {
            sceneManager.clearScene();
        }
        ShaderProgram shaderProgram2 = this.postProcessShader;
        if (shaderProgram2 != null) {
            shaderProgram2.dispose();
        }
        SquareController.getInstance().clearSquares();
        SquareActor.disposeShared();
        StageActivity stageActivity = StageActivity.activeStageActivity.get();
        if (stageActivity != null) {
            stageActivity.removeAllNativeViews();
        }
        FastTwoDManager fastTwoDManager = this.fastTwoDManager;
        if (fastTwoDManager != null) {
            fastTwoDManager.dispose();
            this.fastTwoDManager = null;
        }
        RenderManager.INSTANCE.dispose();
        NewCatroidMqttManager.INSTANCE.disconnectAll();
        try {
            if (MainMenuActivity.pythonEngine != null) {
                MainMenuActivity.pythonEngine.clearEnvironment();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Look.disposeMaskShader();
        NativeViewBindingManager.clearAll();
        disposeStageButKeepActors();
        this.font.dispose();
        this.axes.dispose();
        ColorAtXYDetection.INSTANCE.disposeShared();
        this.sceneFbo.dispose();
        GlobalShaderManager.INSTANCE.dispose();
        GlobalShaderManager.INSTANCE.clear();
        RenderTextureManager.INSTANCE.clearAll();
        SoundCacheManager.getInstance().release();
        disposeTextures();
        disposeClonedSprites();
        SoundManager.getInstance().clear();
        PhysicsShapeBuilder.getInstance().reset();
        this.embroideryPatternManager = null;
        PenActor penActor = this.penActor;
        if (penActor != null) {
            penActor.dispose();
        }
        PlotActor plotActor = this.plotActor;
        if (plotActor != null) {
            plotActor.dispose();
        }
        SpriteBatch spriteBatch = this.postProcessBatch;
        if (spriteBatch != null) {
            spriteBatch.dispose();
        }
        Texture texture = this.vmTexture;
        if (texture != null) {
            texture.dispose();
            this.vmTexture = null;
        }
    }

    public void finish() {
        this.finished = true;
    }

    public void requestTakingScreenshot(String screenshotName, ScreenshotSaverCallback screenshotCallback) {
        this.screenshotName = screenshotName;
        this.screenshotSaverCallback = screenshotCallback;
        StageActivity stageActivity = StageActivity.activeStageActivity.get();
        if (stageActivity != null) {
            stageActivity.captureScreenWithNativeViews(getScreenshotPath(), screenshotName, screenshotCallback);
        } else {
            this.makeScreenshot = true;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyScreenshotCallbackAndCleanup(Boolean success) {
        ScreenshotSaverCallback screenshotSaverCallback = this.screenshotSaverCallback;
        if (screenshotSaverCallback != null) {
            screenshotSaverCallback.screenshotSaved(success.booleanValue());
            this.screenshotSaverCallback = null;
        } else {
            Log.e("StageListener", "Lost reference to screenshot callback");
        }
    }

    public byte[] getPixels(int x, int y, int width, int height) {
        this.testX = x;
        this.testY = y;
        this.testWidth = width;
        this.testHeight = height;
        this.makeTestPixels = true;
        while (this.makeTestPixels) {
            Thread.yield();
        }
        byte[] bArr = this.testPixels;
        byte[] bArr2 = new byte[bArr.length];
        System.arraycopy(bArr, 0, bArr2, 0, bArr.length);
        return bArr2;
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/stage/StageListener$10.class
      classes14.dex
     */
    /* JADX INFO: renamed from: org.catrobat.catroid.stage.StageListener$10, reason: invalid class name */
    static /* synthetic */ class AnonymousClass10 {
        static final /* synthetic */ int[] $SwitchMap$org$catrobat$catroid$common$ScreenModes;

        static {
            int[] iArr = new int[ScreenModes.values().length];
            $SwitchMap$org$catrobat$catroid$common$ScreenModes = iArr;
            try {
                iArr[ScreenModes.MAXIMIZE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$common$ScreenModes[ScreenModes.STRETCH.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    public void toggleScreenMode() {
        int i = AnonymousClass10.$SwitchMap$org$catrobat$catroid$common$ScreenModes[this.project.getScreenMode().ordinal()];
        if (i == 1) {
            this.project.setScreenMode(ScreenModes.STRETCH);
        } else if (i == 2) {
            this.project.setScreenMode(ScreenModes.MAXIMIZE);
        }
        initScreenMode();
    }

    public void clearBackground() {
        this.penActor.reset();
        this.plotActor.reset();
    }

    private void initScreenMode() {
        int width = Gdx.graphics.getWidth();
        int height = Gdx.graphics.getHeight();
        if (width <= 0) {
            width = ScreenValues.currentScreenResolution.getWidth();
        }
        if (height <= 0) {
            height = ScreenValues.currentScreenResolution.getHeight();
        }
        this.screenshotWidth = ScreenValues.getResolutionForProject(this.project).getWidth();
        this.screenshotHeight = ScreenValues.getResolutionForProject(this.project).getHeight();
        int i = AnonymousClass10.$SwitchMap$org$catrobat$catroid$common$ScreenModes[this.project.getScreenMode().ordinal()];
        if (i == 1) {
            float width2 = (this.screenshotWidth == this.maxViewPort.getWidth() || this.maxViewPort.getWidth() <= 0) ? 1.0f : this.screenshotWidth / this.maxViewPort.getWidth();
            float height2 = (this.screenshotHeight == this.maxViewPort.getHeight() || this.maxViewPort.getHeight() <= 0) ? 1.0f : this.screenshotHeight / this.maxViewPort.getHeight();
            this.screenshotWidth = this.maxViewPort.getWidth();
            this.screenshotHeight = this.maxViewPort.getHeight();
            this.screenshotX = this.maxViewPort.getOffsetX();
            this.screenshotY = this.maxViewPort.getOffsetY();
            this.viewPort = new ExtendViewport(this.virtualWidth, this.virtualHeight, this.camera);
            this.shapeRenderer.scale(width2, height2, 1.0f);
        } else if (i == 2) {
            this.screenshotX = 0;
            this.screenshotY = 0;
            this.viewPort = new ScalingViewport(Scaling.stretch, this.virtualWidth, this.virtualHeight, this.camera);
            this.shapeRenderer.identity();
        }
        Stage stage = this.stage;
        if (stage != null) {
            stage.setViewport(this.viewPort);
        }
        this.viewPort.update(width, height, false);
        this.camera.position.set(0.0f, 0.0f, 0.0f);
        this.camera.update();
        this.shapeRenderer.updateMatrices();
    }

    private void disposeTextures() {
        Iterator<Scene> it = this.project.getSceneList().iterator();
        while (it.hasNext()) {
            Iterator<Sprite> it2 = it.next().getSpriteList().iterator();
            while (it2.hasNext()) {
                Iterator<LookData> it3 = it2.next().getLookList().iterator();
                while (it3.hasNext()) {
                    it3.next().dispose();
                }
            }
        }
    }

    private void disposeStageButKeepActors() {
        Stage stage = this.stage;
        if (stage != null) {
            stage.unfocusAll();
        }
        Batch batch = this.batch;
        if (batch != null) {
            batch.dispose();
        }
    }

    public void gamepadPressed(String buttonType) {
        if (this.project == null) {
            Log.e("StageListener", "Gamepad event received, but project is null. Ignoring.");
        } else {
            this.project.fireToAllSprites(new EventWrapper(new GamepadEventId(buttonType), false));
        }
    }

    public void addActor(Actor actor) {
        this.stage.addActor(actor);
    }

    public Stage getStage() {
        return this.stage;
    }

    private void setSchedulerStateForAllLooks(int state) {
        Array.ArrayIterator<Actor> it = this.stage.getActors().iterator();
        while (it.hasNext()) {
            Actor next = it.next();
            if (next instanceof Look) {
                ((Look) next).setSchedulerState(state);
            }
        }
    }

    public void setBubbleActorForSprite(Sprite sprite, ShowBubbleActor showBubbleActor) {
        addActor(showBubbleActor);
        this.bubbleActorMap.put(sprite, showBubbleActor);
    }

    public void removeBubbleActorForSprite(Sprite sprite) {
        getBubbleActorForSprite(sprite).close();
        getStage().getActors().removeValue(getBubbleActorForSprite(sprite), true);
        this.bubbleActorMap.remove(sprite);
    }

    public ShowBubbleActor getBubbleActorForSprite(Sprite sprite) {
        return this.bubbleActorMap.get(sprite);
    }

    public List<Sprite> getSpritesFromStage() {
        return this.sprites;
    }

    private StageBackup saveToBackup() {
        StageBackup stageBackup = new StageBackup();
        CameraManager activeCameraManager = StageActivity.getActiveCameraManager();
        VibrationManager activeVibrationManager = StageActivity.getActiveVibrationManager();
        stageBackup.sprites = new ArrayList(this.sprites);
        stageBackup.actors = new Array<>(this.stage.getActors());
        stageBackup.penActor = this.penActor;
        stageBackup.plotActor = this.plotActor;
        stageBackup.bubbleActorMap = new HashMap(this.bubbleActorMap);
        stageBackup.embroideryPatternManager = this.embroideryPatternManager;
        stageBackup.paused = this.paused;
        stageBackup.finished = this.finished;
        stageBackup.reloadProject = this.reloadProject;
        stageBackup.flashState = activeCameraManager != null && activeCameraManager.getFlashOn();
        if (stageBackup.flashState) {
            activeCameraManager.disableFlash();
        }
        if (activeVibrationManager != null && activeVibrationManager.hasActiveVibration()) {
            activeVibrationManager.pause();
            stageBackup.timeToVibrate = activeVibrationManager.getTimeToVibrate();
            activeVibrationManager.reset();
        }
        stageBackup.physicsWorld = this.physicsWorld;
        stageBackup.camera = this.camera;
        stageBackup.spriteToFocusOn = this.cameraPositioner.getSpriteToFocusOn();
        this.cameraPositioner.reset();
        stageBackup.batch = this.batch;
        stageBackup.font = this.font;
        stageBackup.passepartout = this.passepartout;
        stageBackup.viewPort = this.viewPort;
        stageBackup.axesOn = this.axesOn;
        stageBackup.deltaActionTimeDivisor = this.deltaActionTimeDivisor;
        stageBackup.cameraRunning = activeCameraManager != null && activeCameraManager.isCameraActive();
        if (stageBackup.cameraRunning) {
            activeCameraManager.pause();
        }
        stageBackup.soundBackupList = new ArrayList();
        stageBackup.soundBackupList.addAll(SoundManager.getInstance().getPlayingSoundBackups());
        return stageBackup;
    }

    private void restoreFromBackup(StageBackup backup) {
        this.sprites.clear();
        this.sprites.addAll(backup.sprites);
        final CameraManager activeCameraManager = StageActivity.getActiveCameraManager();
        VibrationManager activeVibrationManager = StageActivity.getActiveVibrationManager();
        this.stage.clear();
        Array.ArrayIterator<Actor> it = backup.actors.iterator();
        while (it.hasNext()) {
            this.stage.addActor(it.next());
        }
        this.penActor = backup.penActor;
        this.plotActor = backup.plotActor;
        this.bubbleActorMap.clear();
        this.bubbleActorMap.putAll(backup.bubbleActorMap);
        this.embroideryPatternManager = backup.embroideryPatternManager;
        this.paused = backup.paused;
        this.finished = backup.finished;
        this.reloadProject = backup.reloadProject;
        if (backup.flashState && activeCameraManager != null) {
            activeCameraManager.enableFlash();
        }
        if (backup.timeToVibrate > 0 && activeVibrationManager != null) {
            activeVibrationManager.setTimeToVibrate(backup.timeToVibrate);
            activeVibrationManager.resume();
        } else if (activeVibrationManager != null) {
            activeVibrationManager.pause();
        }
        this.physicsWorld = backup.physicsWorld;
        this.camera = backup.camera;
        this.cameraPositioner.setSpriteToFocusOn(backup.spriteToFocusOn);
        this.cameraPositioner.updateCameraPositionForFocusedSprite();
        this.batch = backup.batch;
        this.font = backup.font;
        this.passepartout = backup.passepartout;
        this.viewPort = backup.viewPort;
        this.axesOn = backup.axesOn;
        this.deltaActionTimeDivisor = backup.deltaActionTimeDivisor;
        if (backup.cameraRunning && activeCameraManager != null) {
            StageActivity.runOnMainThread(new Runnable() { // from class: org.catrobat.catroid.stage.StageListener.9
                @Override // java.lang.Runnable
                public void run() {
                    activeCameraManager.resume();
                }
            });
        }
        for (SoundBackup soundBackup : backup.soundBackupList) {
            SoundManager.getInstance().playSoundFileWithStartTime(soundBackup.getPathToSoundFile(), soundBackup.getStartedBySprite(), soundBackup.getCurrentPosition());
        }
        initStageInputListener();
        cacheBeforeUpdateScripts();
        cacheAfterUpdateScripts();
    }

    private float calculateScreenRatio() {
        DisplayMetrics displayMetrics = Resources.getSystem().getDisplayMetrics();
        XmlHeader xmlHeader = ProjectManager.getInstance().getCurrentProject().getXmlHeader();
        return ((float) Math.sqrt(Math.pow(xmlHeader.getVirtualScreenWidth(), 2.0d) + Math.pow(xmlHeader.getVirtualScreenHeight(), 2.0d))) / ((float) Math.sqrt(Math.pow(displayMetrics.widthPixels, 2.0d) + Math.pow(displayMetrics.heightPixels, 2.0d)));
    }

    public String getScreenshotPath() {
        return this.scene.getDirectory().getAbsolutePath() + "/";
    }

    public void executeConsoleScript(final Sprite targetSprite, final Script script) {
        if (targetSprite == null || script == null) {
            return;
        }
        Gdx.f82app.postRunnable(new Runnable() { // from class: org.catrobat.catroid.stage.StageListener$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                StageListener.lambda$executeConsoleScript$1(targetSprite, script);
            }
        });
    }

    static /* synthetic */ void lambda$executeConsoleScript$1(Sprite sprite, Script script) {
        try {
            ScriptSequenceAction scriptSequenceActionCreateSequenceAction = sprite.createSequenceAction(script);
            scriptSequenceActionCreateSequenceAction.restart();
            sprite.look.addAction(scriptSequenceActionCreateSequenceAction);
        } catch (Exception e) {
            Log.e("RuntimeConsole", "Failed to execute sandbox script", e);
        }
    }
}

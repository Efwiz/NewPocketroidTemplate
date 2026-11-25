package net.mgsx.gltf.demo.ui;

import androidx.exifinterface.media.ExifInterface;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.FloatAttribute;
import com.badlogic.gdx.graphics.g3d.environment.BaseLight;
import com.badlogic.gdx.graphics.g3d.model.Animation;
import com.badlogic.gdx.graphics.g3d.model.Node;
import com.badlogic.gdx.graphics.g3d.model.NodePart;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.Scaling;
import com.huawei.hms.ml.language.common.utils.Constant;
import net.mgsx.gltf.demo.GLTFDemo;
import net.mgsx.gltf.demo.data.ModelEntry;
import net.mgsx.gltf.demo.events.FileOpenEvent;
import net.mgsx.gltf.demo.events.FileSaveEvent;
import net.mgsx.gltf.demo.events.IBLFolderChangeEvent;
import net.mgsx.gltf.demo.events.ModelSelectedEvent;
import net.mgsx.gltf.demo.model.IBLStudio;
import net.mgsx.gltf.scene3d.attributes.MirrorAttribute;
import net.mgsx.gltf.scene3d.attributes.PBRColorAttribute;
import net.mgsx.gltf.scene3d.attributes.PBRFloatAttribute;
import net.mgsx.gltf.scene3d.attributes.PBRHDRColorAttribute;
import net.mgsx.gltf.scene3d.attributes.PBRIridescenceAttribute;
import net.mgsx.gltf.scene3d.attributes.PBRTextureAttribute;
import net.mgsx.gltf.scene3d.attributes.PBRVolumeAttribute;
import net.mgsx.gltf.scene3d.model.NodePartPlus;
import net.mgsx.gltf.scene3d.model.NodePlus;
import net.mgsx.gltf.scene3d.scene.SceneManager;
import net.mgsx.gltf.scene3d.scene.SceneModel;
import net.mgsx.gltf.scene3d.shaders.PBRShaderConfig;

/* JADX INFO: loaded from: classes12.dex */
public class GLTFDemoUI extends Table {
    public static boolean LIVE_STATS = true;
    public static FileSelector fileSelector;
    private Table IBLChooser;
    public BooleanUI IBLEnabled;
    public BooleanUI IBLLookup;
    private CollapsableUI IBLOptions;
    public final SelectBox<IBLStudio.IBLPreset> IBLSelector;
    public BooleanUI IBLSpecular;
    public Slider ambiantSlider;
    public SelectBox<String> animationSelector;
    public TextButton btAllAnimations;
    private TextButton btNodeExclusive;
    public SelectBox<String> cameraSelector;
    public Slider debugAmbiantSlider;
    public Slider debugSpecularSlider;
    public final Slider emissiveSlider;
    private SelectBox<ModelEntry> entrySelector;
    public FloatUI envRotation;
    public Vector4UI fogColor;
    public BooleanUI fogEnabled;
    public Vector3UI fogEquation;
    public final Vector3UI lightDirectionControl;
    private Label lightLabel;
    private CollapsableUI lightOptions;
    public SelectBox<String> lightSelector;
    public BooleanUI lightShadow;
    public Slider lightSlider;
    private final ObjectMap<String, Material> materialMap;
    private SelectBox<String> materialSelector;
    private Table materialTable;
    public BooleanUI mirror;
    public BooleanUI mirrorClip;
    public Vector3UI mirrorNormal;
    public FloatUI mirrorOrigin;
    public SelectBox<PBRShaderConfig.SRGB> mirrorSRGB;
    private final ObjectMap<String, Node> nodeMap;
    private SelectBox<String> nodeSelector;
    private Table nodeTable;
    public FloatUI outlineDepthMax;
    public FloatUI outlineDepthMin;
    public FloatUI outlineDistFalloff;
    public BooleanUI outlineDistFalloffOption;
    public Vector4UI outlineInnerColor;
    private CollapsableUI outlineOptions;
    public Vector4UI outlineOuterColor;
    public BooleanUI outlinesEnabled;
    public FloatUI outlinesWidth;
    private FileHandle rootFolder;
    private SceneManager sceneManager;
    public SelectBox<SceneModel> sceneSelector;
    public Table screenshotsTable;
    private Node selectedNode;
    public Label shaderCount;
    public BooleanUI shaderGammaCorrection;
    protected CollapsableUI shaderOptions;
    public SelectBox<PBRShaderConfig.SRGB> shaderSRGB;
    public SelectBox<GLTFDemo.ShaderMode> shaderSelector;
    public FloatUI shadowBias;
    public BooleanUI shadowCascade;
    public TextButton skeletonButton;
    public Vector4UI skyBoxColor;
    public BooleanUI skyBoxEnabled;
    public BooleanUI skyboxGammaCorrection;
    public SelectBox<PBRShaderConfig.SRGB> skyboxSRGB;
    public BooleanUI transmissionPassEnabled;
    public final SelectBox<PBRShaderConfig.SRGB> transmissionSRGB;
    public final Slider uiScaleSlider;
    private SelectBox<String> variantSelector;

    public void toggleModelSelector(boolean z) {
    }

    public GLTFDemoUI(SceneManager sceneManager, Skin skin, final FileHandle fileHandle) {
        super(skin);
        this.materialMap = new ObjectMap<>();
        this.nodeMap = new ObjectMap<>();
        this.sceneManager = sceneManager;
        this.rootFolder = fileHandle;
        Table table = new Table(skin);
        table.defaults().pad(5.0f);
        Table table2 = new Table(skin);
        table2.defaults().pad(5.0f);
        add(table).expandY().top();
        add().expand();
        add(table2).expandY().top();
        if (fileSelector != null) {
            TextButton textButton = new TextButton("Open file", skin);
            TextButton textButton2 = new TextButton("Save to file", skin);
            table.add("File");
            Table table3 = new Table();
            table3.add(textButton);
            table3.add(textButton2);
            table.add(table3).row();
            textButton.addListener(new ChangeListener() { // from class: net.mgsx.gltf.demo.ui.GLTFDemoUI.1
                @Override // com.badlogic.gdx.scenes.scene2d.utils.ChangeListener
                public void changed(ChangeListener.ChangeEvent changeEvent, Actor actor) {
                    GLTFDemoUI.fileSelector.open(new Runnable() { // from class: net.mgsx.gltf.demo.ui.GLTFDemoUI.1.1
                        @Override // java.lang.Runnable
                        public void run() {
                            GLTFDemoUI.this.fire(new FileOpenEvent(GLTFDemoUI.fileSelector.lastFile));
                        }
                    });
                }
            });
            textButton2.addListener(new ChangeListener() { // from class: net.mgsx.gltf.demo.ui.GLTFDemoUI.2
                @Override // com.badlogic.gdx.scenes.scene2d.utils.ChangeListener
                public void changed(ChangeListener.ChangeEvent changeEvent, Actor actor) {
                    GLTFDemoUI.fileSelector.open(new Runnable() { // from class: net.mgsx.gltf.demo.ui.GLTFDemoUI.2.1
                        @Override // java.lang.Runnable
                        public void run() {
                            GLTFDemoUI.this.fire(new FileSaveEvent(GLTFDemoUI.fileSelector.lastFile));
                        }
                    });
                }
            });
        }
        if (fileHandle != null) {
            this.entrySelector = new SelectBox<>(skin);
            table.add(ExifInterface.TAG_MODEL);
            table.add(this.entrySelector).row();
            table.add("Screenshot");
            Table table4 = new Table(skin);
            this.screenshotsTable = table4;
            table.add(table4).row();
            this.variantSelector = new SelectBox<>(skin);
            table.add("File");
            table.add(this.variantSelector).row();
            this.entrySelector.addListener(new ChangeListener() { // from class: net.mgsx.gltf.demo.ui.GLTFDemoUI.3
                @Override // com.badlogic.gdx.scenes.scene2d.utils.ChangeListener
                public void changed(ChangeListener.ChangeEvent changeEvent, Actor actor) {
                    GLTFDemoUI gLTFDemoUI = GLTFDemoUI.this;
                    gLTFDemoUI.setEntry((ModelEntry) gLTFDemoUI.entrySelector.getSelected(), fileHandle);
                    GLTFDemoUI gLTFDemoUI2 = GLTFDemoUI.this;
                    gLTFDemoUI2.setImage((ModelEntry) gLTFDemoUI2.entrySelector.getSelected());
                }
            });
            this.variantSelector.addListener(new ChangeListener() { // from class: net.mgsx.gltf.demo.ui.GLTFDemoUI.4
                @Override // com.badlogic.gdx.scenes.scene2d.utils.ChangeListener
                public void changed(ChangeListener.ChangeEvent changeEvent, Actor actor) {
                    GLTFDemoUI gLTFDemoUI = GLTFDemoUI.this;
                    gLTFDemoUI.load((ModelEntry) gLTFDemoUI.entrySelector.getSelected(), (String) GLTFDemoUI.this.variantSelector.getSelected());
                }
            });
        }
        this.sceneSelector = new SelectBox<SceneModel>(skin) { // from class: net.mgsx.gltf.demo.ui.GLTFDemoUI.5
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.badlogic.gdx.scenes.scene2d.ui.SelectBox
            public String toString(SceneModel sceneModel) {
                return sceneModel.f412name == null ? "<null>" : sceneModel.f412name;
            }
        };
        table.add("Scene");
        table.add(this.sceneSelector).row();
        this.shaderSelector = new SelectBox<>(skin);
        table.add("Shader");
        table.add(this.shaderSelector).row();
        this.shaderSelector.setItems(GLTFDemo.ShaderMode.values());
        table.add("Shader count");
        Label label = new Label("", skin);
        this.shaderCount = label;
        table.add(label).row();
        table.add();
        CollapsableUI collapsableUI = new CollapsableUI(skin, "Shader Options", false);
        this.shaderOptions = collapsableUI;
        table.add(collapsableUI).row();
        this.shaderOptions.optTable.add("SRGB");
        Table table5 = this.shaderOptions.optTable;
        SelectBox<PBRShaderConfig.SRGB> selectBox = new SelectBox<>(skin);
        this.shaderSRGB = selectBox;
        table5.add(selectBox).row();
        this.shaderSRGB.setItems(PBRShaderConfig.SRGB.values());
        this.shaderSRGB.setSelected(PBRShaderConfig.SRGB.ACCURATE);
        this.shaderOptions.optTable.add("Gamma correction");
        Table table6 = this.shaderOptions.optTable;
        BooleanUI booleanUI = new BooleanUI(skin, true);
        this.shaderGammaCorrection = booleanUI;
        table6.add(booleanUI).row();
        this.shaderOptions.optTable.add("Fog");
        Table table7 = this.shaderOptions.optTable;
        BooleanUI booleanUI2 = new BooleanUI(skin, false);
        this.fogEnabled = booleanUI2;
        table7.add(booleanUI2).row();
        this.shaderOptions.optTable.add("Fog Color");
        Table table8 = this.shaderOptions.optTable;
        Vector4UI vector4UI = new Vector4UI(skin, new Color());
        this.fogColor = vector4UI;
        table8.add(vector4UI).row();
        this.shaderOptions.optTable.add("Fog Equation");
        Table table9 = this.shaderOptions.optTable;
        Vector3UI vector3UI = new Vector3UI(skin, new Vector3(-1.0f, 1.0f, -0.8f));
        this.fogEquation = vector3UI;
        table9.add(vector3UI).row();
        this.shaderOptions.optTable.add("SkyBox");
        Table table10 = this.shaderOptions.optTable;
        BooleanUI booleanUI3 = new BooleanUI(skin, true);
        this.skyBoxEnabled = booleanUI3;
        table10.add(booleanUI3).row();
        this.shaderOptions.optTable.add("SkyBox Color");
        Table table11 = this.shaderOptions.optTable;
        Vector4UI vector4UI2 = new Vector4UI(skin, new Color(Color.WHITE));
        this.skyBoxColor = vector4UI2;
        table11.add(vector4UI2).row();
        this.shaderOptions.optTable.add("Skybox SRGB");
        Table table12 = this.shaderOptions.optTable;
        SelectBox<PBRShaderConfig.SRGB> selectBox2 = new SelectBox<>(skin);
        this.skyboxSRGB = selectBox2;
        table12.add(selectBox2).row();
        this.skyboxSRGB.setItems(PBRShaderConfig.SRGB.values());
        this.skyboxSRGB.setSelected(PBRShaderConfig.SRGB.NONE);
        this.shaderOptions.optTable.add("Skybox Gamma");
        Table table13 = this.shaderOptions.optTable;
        BooleanUI booleanUI4 = new BooleanUI(skin, false);
        this.skyboxGammaCorrection = booleanUI4;
        table13.add(booleanUI4).row();
        this.shaderOptions.optTable.add("Rotation");
        Table table14 = this.shaderOptions.optTable;
        FloatUI floatUI = new FloatUI(skin, 0.5f);
        this.envRotation = floatUI;
        table14.add(floatUI).row();
        this.shaderOptions.optTable.add("Transmission Pass");
        Table table15 = this.shaderOptions.optTable;
        BooleanUI booleanUI5 = new BooleanUI(skin, true);
        this.transmissionPassEnabled = booleanUI5;
        table15.add(booleanUI5).row();
        this.shaderOptions.optTable.add("Transmission SRGB");
        Table table16 = this.shaderOptions.optTable;
        SelectBox<PBRShaderConfig.SRGB> selectBox3 = new SelectBox<>(skin);
        this.transmissionSRGB = selectBox3;
        table16.add(selectBox3).row();
        selectBox3.setItems(PBRShaderConfig.SRGB.values());
        selectBox3.setSelected(PBRShaderConfig.SRGB.ACCURATE);
        this.shaderOptions.optTable.add("Mirror");
        Table table17 = this.shaderOptions.optTable;
        BooleanUI booleanUI6 = new BooleanUI(skin, false);
        this.mirror = booleanUI6;
        table17.add(booleanUI6).row();
        this.shaderOptions.optTable.add("Mirror normal");
        Table table18 = this.shaderOptions.optTable;
        Vector3UI vector3UI2 = new Vector3UI(skin, new Vector3(0.0f, 1.0f, 0.0f));
        this.mirrorNormal = vector3UI2;
        table18.add(vector3UI2).row();
        this.shaderOptions.optTable.add("Mirror origin");
        Table table19 = this.shaderOptions.optTable;
        FloatUI floatUI2 = new FloatUI(skin, 0.0f, null, -10.0f, 10.0f);
        this.mirrorOrigin = floatUI2;
        table19.add(floatUI2).row();
        this.shaderOptions.optTable.add("Mirror clip");
        Table table20 = this.shaderOptions.optTable;
        BooleanUI booleanUI7 = new BooleanUI(skin, true);
        this.mirrorClip = booleanUI7;
        table20.add(booleanUI7).row();
        this.shaderOptions.optTable.add("Mirror SRGB");
        Table table21 = this.shaderOptions.optTable;
        SelectBox<PBRShaderConfig.SRGB> selectBox4 = new SelectBox<>(skin);
        this.mirrorSRGB = selectBox4;
        table21.add(selectBox4).row();
        this.mirrorSRGB.setItems(PBRShaderConfig.SRGB.values());
        this.mirrorSRGB.setSelected(PBRShaderConfig.SRGB.ACCURATE);
        table.add();
        CollapsableUI collapsableUI2 = new CollapsableUI(skin, "Outline Options", false);
        this.outlineOptions = collapsableUI2;
        table.add(collapsableUI2).row();
        this.outlineOptions.optTable.add("Outlines");
        Table table22 = this.outlineOptions.optTable;
        BooleanUI booleanUI8 = new BooleanUI(skin, false);
        this.outlinesEnabled = booleanUI8;
        table22.add(booleanUI8).row();
        this.outlineOptions.optTable.add("Thickness");
        Table table23 = this.outlineOptions.optTable;
        FloatUI floatUI3 = new FloatUI(skin, 0.0f);
        this.outlinesWidth = floatUI3;
        table23.add(floatUI3).row();
        this.outlineOptions.optTable.add("Depth min");
        Table table24 = this.outlineOptions.optTable;
        FloatUI floatUI4 = new FloatUI(skin, 0.35f);
        this.outlineDepthMin = floatUI4;
        table24.add(floatUI4).row();
        this.outlineOptions.optTable.add("Depth max");
        Table table25 = this.outlineOptions.optTable;
        FloatUI floatUI5 = new FloatUI(skin, 0.9f);
        this.outlineDepthMax = floatUI5;
        table25.add(floatUI5).row();
        this.outlineOptions.optTable.add("Inner color");
        Table table26 = this.outlineOptions.optTable;
        Vector4UI vector4UI3 = new Vector4UI(skin, new Color(0.0f, 0.0f, 0.0f, 0.3f));
        this.outlineInnerColor = vector4UI3;
        table26.add(vector4UI3).row();
        this.outlineOptions.optTable.add("Outer color");
        Table table27 = this.outlineOptions.optTable;
        Vector4UI vector4UI4 = new Vector4UI(skin, new Color(0.0f, 0.0f, 0.0f, 0.7f));
        this.outlineOuterColor = vector4UI4;
        table27.add(vector4UI4).row();
        this.outlineOptions.optTable.add("Distance Falloff");
        Table table28 = this.outlineOptions.optTable;
        BooleanUI booleanUI9 = new BooleanUI(skin, false);
        this.outlineDistFalloffOption = booleanUI9;
        table28.add(booleanUI9).row();
        this.outlineOptions.optTable.add("Distance Exponent");
        Table table29 = this.outlineOptions.optTable;
        FloatUI floatUI6 = new FloatUI(skin, 1.0f);
        this.outlineDistFalloff = floatUI6;
        table29.add(floatUI6).row();
        table.add();
        CollapsableUI collapsableUI3 = new CollapsableUI(skin, "Light Options", false);
        this.lightOptions = collapsableUI3;
        table.add(collapsableUI3).row();
        this.lightSlider = new Slider(0.0f, 100.0f, 0.01f, false, skin);
        this.lightOptions.optTable.add("Light intensity");
        this.lightOptions.optTable.add(this.lightSlider).row();
        this.lightSlider.setValue(1.0f);
        this.lightOptions.optTable.add("Dir Light");
        Table table30 = this.lightOptions.optTable;
        Vector3UI vector3UI3 = new Vector3UI(skin, new Vector3());
        this.lightDirectionControl = vector3UI3;
        table30.add(vector3UI3).row();
        this.lightOptions.optTable.add("Shadows");
        Table table31 = this.lightOptions.optTable;
        BooleanUI booleanUI10 = new BooleanUI(skin, false);
        this.lightShadow = booleanUI10;
        table31.add(booleanUI10).row();
        this.lightOptions.optTable.add("Shadow Bias");
        Table table32 = this.lightOptions.optTable;
        FloatUI floatUI7 = new FloatUI(skin, 0.0f);
        this.shadowBias = floatUI7;
        table32.add(floatUI7).row();
        this.lightOptions.optTable.add("Cascade Shadow Map");
        Table table33 = this.lightOptions.optTable;
        BooleanUI booleanUI11 = new BooleanUI(skin, false);
        this.shadowCascade = booleanUI11;
        table33.add(booleanUI11).row();
        Slider slider = new Slider(0.0f, 1.0f, 0.01f, false, skin);
        this.ambiantSlider = slider;
        slider.setValue(1.0f);
        this.lightOptions.optTable.add("Ambient Light");
        this.lightOptions.optTable.add(this.ambiantSlider).row();
        Slider slider2 = new Slider(0.0f, 10.0f, 0.1f, false, skin);
        this.emissiveSlider = slider2;
        slider2.setValue(1.0f);
        this.lightOptions.optTable.add("Emissive intensity");
        this.lightOptions.optTable.add(slider2).row();
        table.add();
        CollapsableUI collapsableUI4 = new CollapsableUI(skin, "IBL Options", false);
        this.IBLOptions = collapsableUI4;
        table.add(collapsableUI4).row();
        this.IBLOptions.optTable.add("Overall");
        Table table34 = this.IBLOptions.optTable;
        BooleanUI booleanUI12 = new BooleanUI(skin, true);
        this.IBLEnabled = booleanUI12;
        table34.add(booleanUI12).row();
        this.IBLOptions.optTable.add("Radiance");
        Table table35 = this.IBLOptions.optTable;
        BooleanUI booleanUI13 = new BooleanUI(skin, true);
        this.IBLSpecular = booleanUI13;
        table35.add(booleanUI13).row();
        this.IBLOptions.optTable.add("BSDF");
        Table table36 = this.IBLOptions.optTable;
        BooleanUI booleanUI14 = new BooleanUI(skin, true);
        this.IBLLookup = booleanUI14;
        table36.add(booleanUI14).row();
        this.IBLOptions.optTable.add("Presets");
        Table table37 = this.IBLOptions.optTable;
        Table table38 = new Table(skin);
        this.IBLChooser = table38;
        table37.add(table38).row();
        Table table39 = this.IBLChooser;
        SelectBox<IBLStudio.IBLPreset> selectBox5 = new SelectBox<>(getSkin());
        this.IBLSelector = selectBox5;
        table39.add(selectBox5);
        if (fileSelector != null) {
            TextButton textButton3 = new TextButton("Load", skin);
            this.IBLChooser.add(textButton3);
            textButton3.addListener(new ChangeListener() { // from class: net.mgsx.gltf.demo.ui.GLTFDemoUI.6
                @Override // com.badlogic.gdx.scenes.scene2d.utils.ChangeListener
                public void changed(ChangeListener.ChangeEvent changeEvent, Actor actor) {
                    GLTFDemoUI.fileSelector.selectFolder(new Runnable() { // from class: net.mgsx.gltf.demo.ui.GLTFDemoUI.6.1
                        @Override // java.lang.Runnable
                        public void run() {
                            GLTFDemoUI.this.fire(new IBLFolderChangeEvent(GLTFDemoUI.fileSelector.lastFile));
                        }
                    });
                }
            });
        }
        this.cameraSelector = new SelectBox<>(skin);
        table.add("Camera");
        table.add(this.cameraSelector).row();
        this.lightSelector = new SelectBox<>(skin);
        Label label2 = new Label("Lights", skin);
        this.lightLabel = label2;
        table.add(label2);
        table.add(this.lightSelector).row();
        this.animationSelector = new SelectBox<>(skin);
        this.btAllAnimations = new TextButton("All", skin, "toggle");
        table.add("Animation");
        table.add(this.animationSelector);
        table.add(this.btAllAnimations).row();
        this.skeletonButton = new TextButton("Skeletons", getSkin(), "toggle");
        table.add("Skeletons");
        table.add(this.skeletonButton).row();
        table2.add("UI Scale");
        Slider slider3 = new Slider(1.0f, 3.0f, 0.5f, false, getSkin());
        this.uiScaleSlider = slider3;
        slider3.setValue(GLTFDemo.defaultUIScale);
        table2.add(slider3).row();
        this.materialSelector = new SelectBox<>(skin);
        table2.add("Materials");
        table2.add(this.materialSelector).row();
        this.materialTable = new Table(skin);
        table2.add();
        table2.add(this.materialTable).row();
        this.nodeSelector = new SelectBox<>(skin);
        table2.add("Nodes");
        table2.add(this.nodeSelector).row();
        table2.add();
        TextButton textButton4 = new TextButton("Hide other nodes", getSkin(), "toggle");
        this.btNodeExclusive = textButton4;
        table2.add(textButton4).row();
        this.nodeTable = new Table(skin);
        table2.add();
        table2.add(this.nodeTable).row();
        this.materialSelector.addListener(new ChangeListener() { // from class: net.mgsx.gltf.demo.ui.GLTFDemoUI.7
            @Override // com.badlogic.gdx.scenes.scene2d.utils.ChangeListener
            public void changed(ChangeListener.ChangeEvent changeEvent, Actor actor) {
                GLTFDemoUI gLTFDemoUI = GLTFDemoUI.this;
                gLTFDemoUI.setMaterial((String) gLTFDemoUI.materialSelector.getSelected());
            }
        });
        this.nodeSelector.addListener(new ChangeListener() { // from class: net.mgsx.gltf.demo.ui.GLTFDemoUI.8
            @Override // com.badlogic.gdx.scenes.scene2d.utils.ChangeListener
            public void changed(ChangeListener.ChangeEvent changeEvent, Actor actor) {
                GLTFDemoUI gLTFDemoUI = GLTFDemoUI.this;
                gLTFDemoUI.setNode((String) gLTFDemoUI.nodeSelector.getSelected());
            }
        });
        this.btNodeExclusive.addListener(new ChangeListener() { // from class: net.mgsx.gltf.demo.ui.GLTFDemoUI.9
            @Override // com.badlogic.gdx.scenes.scene2d.utils.ChangeListener
            public void changed(ChangeListener.ChangeEvent changeEvent, Actor actor) {
                GLTFDemoUI gLTFDemoUI = GLTFDemoUI.this;
                gLTFDemoUI.showHideOtherNodes(gLTFDemoUI.btNodeExclusive.isChecked());
            }
        });
        this.btAllAnimations.addListener(new ChangeListener() { // from class: net.mgsx.gltf.demo.ui.GLTFDemoUI.10
            @Override // com.badlogic.gdx.scenes.scene2d.utils.ChangeListener
            public void changed(ChangeListener.ChangeEvent changeEvent, Actor actor) {
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void load(ModelEntry modelEntry, String str) {
        String str2;
        if (str.isEmpty() || (str2 = modelEntry.variants.get(str)) == null) {
            return;
        }
        fire(new ModelSelectedEvent(this.rootFolder.child(modelEntry.f410name).child(str).child(str2)));
    }

    protected void setImage(ModelEntry modelEntry) {
        if (modelEntry.screenshot != null) {
            FileHandle fileHandleChild = this.rootFolder.child(modelEntry.f410name).child(modelEntry.screenshot);
            if (fileHandleChild.exists()) {
                setImage(new Texture(fileHandleChild));
            } else {
                Gdx.f82app.error("DEMO UI", "file not found " + fileHandleChild.path());
            }
        }
    }

    protected TextButton toggle(String str, boolean z) {
        TextButton textButton = new TextButton(str, getSkin(), "toggle");
        textButton.setChecked(z);
        return textButton;
    }

    /* JADX WARN: Multi-variable type inference failed */
    protected void showHideOtherNodes(boolean z) {
        ObjectMap.Entries<String, Node> it = this.nodeMap.iterator();
        while (it.hasNext()) {
            ObjectMap.Entry next = it.next();
            Array.ArrayIterator<NodePart> it2 = ((Node) next.value).parts.iterator();
            while (it2.hasNext()) {
                it2.next().enabled = this.selectedNode == null || !z || next.value == this.selectedNode;
            }
        }
    }

    protected void setNode(String str) {
        this.nodeTable.clearChildren();
        this.selectedNode = null;
        if (str == null || str.isEmpty()) {
            showHideOtherNodes(false);
            return;
        }
        Node node = this.nodeMap.get(str);
        this.selectedNode = node;
        showHideOtherNodes(this.btNodeExclusive.isChecked());
        if (node instanceof NodePlus) {
            final NodePlus nodePlus = (NodePlus) node;
            if (nodePlus.weights != null) {
                WeightsUI weightsUI = new WeightsUI(getSkin(), nodePlus.weights, nodePlus.morphTargetNames);
                this.nodeTable.add("Morph Targets").row();
                this.nodeTable.add(weightsUI);
                weightsUI.addListener(new ChangeListener() { // from class: net.mgsx.gltf.demo.ui.GLTFDemoUI.11
                    @Override // com.badlogic.gdx.scenes.scene2d.utils.ChangeListener
                    public void changed(ChangeListener.ChangeEvent changeEvent, Actor actor) {
                        Array.ArrayIterator<NodePart> it = nodePlus.parts.iterator();
                        while (it.hasNext()) {
                            NodePartPlus nodePartPlus = (NodePartPlus) it.next();
                            if (nodePartPlus.morphTargets != null) {
                                nodePartPlus.morphTargets.set(nodePlus.weights);
                            }
                        }
                    }
                });
            }
        }
    }

    protected void setMaterial(String str) {
        this.materialTable.clearChildren();
        if (str == null || str.isEmpty()) {
            return;
        }
        final Material material = this.materialMap.get(str);
        this.materialTable.add(new ColorAttributeUI(getSkin(), (ColorAttribute) material.get(ColorAttribute.class, PBRColorAttribute.BaseColorFactor))).row();
        addMaterialTextureSwitch("Color Texture", material, PBRTextureAttribute.BaseColorTexture);
        this.materialTable.add(new ColorAttributeUI(getSkin(), (ColorAttribute) material.get(ColorAttribute.class, PBRColorAttribute.Emissive))).row();
        addMaterialTextureSwitch("Emissive Texture", material, PBRTextureAttribute.EmissiveTexture);
        this.materialTable.add(new FloatAttributeUI(getSkin(), (FloatAttribute) material.get(PBRFloatAttribute.class, PBRFloatAttribute.Metallic))).row();
        this.materialTable.add(new FloatAttributeUI(getSkin(), (FloatAttribute) material.get(PBRFloatAttribute.class, PBRFloatAttribute.Roughness))).row();
        addMaterialTextureSwitch("MR Texture", material, PBRTextureAttribute.MetallicRoughnessTexture);
        this.materialTable.add(new FloatAttributeUI(getSkin(), (FloatAttribute) material.get(PBRFloatAttribute.class, PBRFloatAttribute.NormalScale))).row();
        addMaterialTextureSwitch("Normal Texture", material, PBRTextureAttribute.NormalTexture);
        this.materialTable.add(new FloatAttributeUI(getSkin(), (FloatAttribute) material.get(PBRFloatAttribute.class, PBRFloatAttribute.OcclusionStrength))).row();
        addMaterialTextureSwitch("Occlusion Texture", material, PBRTextureAttribute.OcclusionTexture);
        this.materialTable.add(new FloatAttributeUI(getSkin(), (FloatAttribute) material.get(PBRFloatAttribute.class, PBRFloatAttribute.TransmissionFactor))).row();
        addMaterialTextureSwitch("Transmission Texture", material, PBRTextureAttribute.TransmissionTexture);
        final PBRVolumeAttribute pBRVolumeAttribute = (PBRVolumeAttribute) material.get(PBRVolumeAttribute.class, PBRVolumeAttribute.Type);
        if (pBRVolumeAttribute != null) {
            this.materialTable.add(new FloatUI(getSkin(), pBRVolumeAttribute.thicknessFactor, "Thickness", 0.0f, 10.0f) { // from class: net.mgsx.gltf.demo.ui.GLTFDemoUI.12
                @Override // net.mgsx.gltf.demo.ui.FloatUI
                protected void onChange(float f) {
                    pBRVolumeAttribute.thicknessFactor = f;
                }
            }).row();
        }
        addMaterialTextureSwitch("Thickness Texture", material, PBRTextureAttribute.ThicknessTexture);
        this.materialTable.add(new FloatAttributeUI(getSkin(), (FloatAttribute) material.get(PBRFloatAttribute.class, PBRFloatAttribute.IOR), 1.0f, 3.0f)).row();
        this.materialTable.add(new FloatAttributeUI(getSkin(), (FloatAttribute) material.get(PBRFloatAttribute.class, PBRFloatAttribute.SpecularFactor), 0.0f, 1.0f)).row();
        addMaterialTextureSwitch("Specular Factor Texture", material, PBRTextureAttribute.SpecularFactorTexture);
        this.materialTable.add(new HDRColorAttributeUI(getSkin(), (PBRHDRColorAttribute) material.get(PBRHDRColorAttribute.class, PBRHDRColorAttribute.Specular), 100.0f)).row();
        addMaterialTextureSwitch("Specular Color Texture", material, PBRTextureAttribute.Specular);
        final PBRIridescenceAttribute pBRIridescenceAttribute = (PBRIridescenceAttribute) material.get(PBRIridescenceAttribute.class, PBRIridescenceAttribute.Type);
        if (pBRIridescenceAttribute != null) {
            this.materialTable.add(new FloatUI(getSkin(), pBRIridescenceAttribute.factor, "Iridescence") { // from class: net.mgsx.gltf.demo.ui.GLTFDemoUI.13
                @Override // net.mgsx.gltf.demo.ui.FloatUI
                protected void onChange(float f) {
                    pBRIridescenceAttribute.factor = f;
                }
            }).row();
        }
        final BooleanUI booleanUI = new BooleanUI(getSkin(), material.has(MirrorAttribute.Specular));
        Table table = new Table(getSkin());
        table.add("Mirror");
        table.add(booleanUI);
        this.materialTable.add(table).row();
        booleanUI.addListener(new ChangeListener() { // from class: net.mgsx.gltf.demo.ui.GLTFDemoUI.14
            @Override // com.badlogic.gdx.scenes.scene2d.utils.ChangeListener
            public void changed(ChangeListener.ChangeEvent changeEvent, Actor actor) {
                if (booleanUI.isOn()) {
                    material.set(MirrorAttribute.createSpecular());
                } else {
                    material.remove(MirrorAttribute.Specular);
                }
            }
        });
    }

    private void addMaterialTextureSwitch(String str, final Material material, long j) {
        final PBRTextureAttribute pBRTextureAttribute = (PBRTextureAttribute) material.get(PBRTextureAttribute.class, j);
        if (pBRTextureAttribute != null) {
            final TextButton textButton = new TextButton(str, getSkin(), "toggle");
            textButton.setChecked(true);
            this.materialTable.add(textButton);
            Image image = new Image((Texture) pBRTextureAttribute.textureDescription.texture);
            image.setScaling(Scaling.fit);
            this.materialTable.add(image).size(64.0f);
            this.materialTable.row();
            textButton.addListener(new ChangeListener() { // from class: net.mgsx.gltf.demo.ui.GLTFDemoUI.15
                @Override // com.badlogic.gdx.scenes.scene2d.utils.ChangeListener
                public void changed(ChangeListener.ChangeEvent changeEvent, Actor actor) {
                    if (textButton.isChecked()) {
                        material.set(pBRTextureAttribute);
                    } else {
                        material.remove(pBRTextureAttribute.type);
                    }
                }
            });
        }
    }

    public void setEntry(ModelEntry modelEntry, FileHandle fileHandle) {
        this.variantSelector.setSelected(null);
        Array<String> array = modelEntry.variants.keys().toArray(new Array<>());
        array.insert(0, "");
        this.variantSelector.setItems(array);
        if (modelEntry.variants.size == 1) {
            this.variantSelector.setSelectedIndex(1);
        } else {
            this.variantSelector.setSelectedIndex(0);
        }
        if (this.screenshotsTable.getChildren().size > 0) {
            ((TextureRegionDrawable) ((Image) this.screenshotsTable.getChildren().first()).getDrawable()).getRegion().getTexture().dispose();
        }
        this.screenshotsTable.clear();
    }

    public void setImage(Texture texture) {
        if (texture != null) {
            Image image = new Image(texture);
            image.setScaling(Scaling.fit);
            this.screenshotsTable.add(image).height(100.0f);
        }
    }

    public void setAnimations(Array<Animation> array) {
        if (array.size > 0) {
            Array<String> array2 = new Array<>();
            array2.add("");
            Array.ArrayIterator<Animation> it = array.iterator();
            while (it.hasNext()) {
                array2.add(it.next().f102id);
            }
            this.animationSelector.setItems(array2);
            return;
        }
        this.animationSelector.setItems(new String[0]);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void setCameras(ObjectMap<Node, Camera> objectMap) {
        Array<String> array = new Array<>();
        array.add("");
        ObjectMap.Entries<Node, Camera> it = objectMap.iterator();
        while (it.hasNext()) {
            array.add(((Node) it.next().key).f104id);
        }
        this.cameraSelector.setItems(new String[0]);
        this.cameraSelector.setItems(array);
    }

    public void setMaterials(Array<Material> array) {
        this.materialMap.clear();
        Array<String> array2 = new Array<>();
        array2.add("");
        Array.ArrayIterator<Material> it = array.iterator();
        while (it.hasNext()) {
            Material next = it.next();
            array2.add(next.f100id);
            this.materialMap.put(next.f100id, next);
        }
        this.materialSelector.setItems(new String[0]);
        this.materialSelector.setItems(array2);
    }

    public void setNodes(Array<Node> array) {
        this.nodeMap.clear();
        Array<String> array2 = new Array<>();
        Array.ArrayIterator<Node> it = array.iterator();
        while (it.hasNext()) {
            Node next = it.next();
            array2.add(next.f104id);
            this.nodeMap.put(next.f104id, next);
        }
        array2.sort();
        array2.insert(0, "");
        this.nodeSelector.setItems(new String[0]);
        this.nodeSelector.setItems(array2);
    }

    public void setScenes(Array<SceneModel> array) {
        if (array == null) {
            this.sceneSelector.setDisabled(true);
            this.sceneSelector.setItems(new Array<>());
            return;
        }
        this.sceneSelector.setDisabled(false);
        Array<SceneModel> array2 = new Array<>();
        for (int i = 0; i < array.size; i++) {
            array2.add(array.get(i));
        }
        this.sceneSelector.setItems(array2);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void setLights(ObjectMap<Node, BaseLight> objectMap) {
        Array<String> array = new Array<>();
        array.add("");
        ObjectMap.Entries<Node, BaseLight> it = objectMap.iterator();
        while (it.hasNext()) {
            array.add(((Node) it.next().key).f104id);
        }
        this.lightSelector.setItems(array);
    }

    @Override // com.badlogic.gdx.scenes.scene2d.Group, com.badlogic.gdx.scenes.scene2d.Actor
    public void act(float f) {
        if (LIVE_STATS) {
            this.lightLabel.setText("Lights (" + this.sceneManager.getActiveLightsCount() + "/" + this.sceneManager.getTotalLightsCount() + Constant.AFTER_QUTO);
        }
        super.act(f);
    }

    public void setEntries(Array<ModelEntry> array, String str, String str2) {
        this.entrySelector.setItems(array);
        if (str == null || str2 == null) {
            return;
        }
        for (int i = 0; i < array.size; i++) {
            ModelEntry modelEntry = array.get(i);
            if (modelEntry.f410name.equals(str)) {
                this.entrySelector.setSelected(modelEntry);
                if (modelEntry.variants.size != 1) {
                    this.variantSelector.setSelected(str2);
                    return;
                }
                return;
            }
        }
    }
}

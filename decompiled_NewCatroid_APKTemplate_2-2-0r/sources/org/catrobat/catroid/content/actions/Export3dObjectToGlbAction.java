package org.catrobat.catroid.content.actions;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g3d.Attribute;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.scenes.scene2d.Action;
import java.io.File;
import java.util.ArrayList;
import java.util.Locale;
import kotlin.Metadata;
import kotlin.io.FilesKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.mgsx.gltf.exporters.GLTFExporter;
import net.mgsx.gltf.scene3d.attributes.PBRColorAttribute;
import net.mgsx.gltf.scene3d.attributes.PBRFloatAttribute;
import net.mgsx.gltf.scene3d.attributes.PBRTextureAttribute;
import net.mgsx.gltf.scene3d.scene.Scene;
import org.catrobat.catroid.CatroidApplication;
import org.catrobat.catroid.content.Project;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.raptor.ThreeDManager;
import org.catrobat.catroid.stage.StageActivity;
import org.catrobat.catroid.stage.StageListener;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/Export3dObjectToGlbAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: Export3dObjectToGlbAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0015\u001a\u00020\n2\u0006\u0010\u0016\u001a\u00020\u0017H\u0016J\u0010\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001bH\u0002J\b\u0010\u001c\u001a\u00020\u0019H\u0016J\b\u0010\u001d\u001a\u00020\u0019H\u0016R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u000e\u0010\t\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u001c\u0010\u000b\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\u0006\"\u0004\b\r\u0010\bR\u001c\u0010\u000e\u001a\u0004\u0018\u00010\u000fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\u0011\"\u0004\b\u0012\u0010\u0013R\u000e\u0010\u0014\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u001e"}, d2 = {"Lorg/catrobat/catroid/content/actions/Export3dObjectToGlbAction;", "Lcom/badlogic/gdx/scenes/scene2d/Action;", "()V", "destFileName", "Lorg/catrobat/catroid/formulaeditor/Formula;", "getDestFileName", "()Lorg/catrobat/catroid/formulaeditor/Formula;", "setDestFileName", "(Lorg/catrobat/catroid/formulaeditor/Formula;)V", "finished", "", "objectId", "getObjectId", "setObjectId", "scope", "Lorg/catrobat/catroid/content/Scope;", "getScope", "()Lorg/catrobat/catroid/content/Scope;", "setScope", "(Lorg/catrobat/catroid/content/Scope;)V", "started", "act", "delta", "", "ensurePbrMaterials", "", "instance", "Lcom/badlogic/gdx/graphics/g3d/ModelInstance;", "reset", "restart", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class Export3dObjectToGlbAction extends Action {
    private Formula destFileName;
    private volatile boolean finished;
    private Formula objectId;
    private Scope scope;
    private boolean started;

    public final Scope getScope() {
        return this.scope;
    }

    public final void setScope(Scope scope) {
        this.scope = scope;
    }

    public final Formula getObjectId() {
        return this.objectId;
    }

    public final void setObjectId(Formula formula) {
        this.objectId = formula;
    }

    public final Formula getDestFileName() {
        return this.destFileName;
    }

    public final void setDestFileName(Formula formula) {
        this.destFileName = formula;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.Action
    public boolean act(float delta) {
        Project project;
        if (!this.started) {
            this.started = true;
            Formula formula = this.objectId;
            String strInterpretString = formula != null ? formula.interpretString(this.scope) : null;
            if (strInterpretString == null) {
                strInterpretString = "";
            }
            final String str = strInterpretString;
            Formula formula2 = this.destFileName;
            String strInterpretString2 = formula2 != null ? formula2.interpretString(this.scope) : null;
            if (strInterpretString2 == null) {
                strInterpretString2 = "model.glb";
            }
            Scope scope = this.scope;
            final File filesDir = (scope == null || (project = scope.getProject()) == null) ? null : project.getFilesDir();
            if (filesDir == null || str.length() == 0) {
                this.finished = true;
                return true;
            }
            String lowerCase = strInterpretString2.toLowerCase(Locale.ROOT);
            Intrinsics.checkNotNullExpressionValue(lowerCase, "toLowerCase(...)");
            if (!StringsKt.endsWith$default(lowerCase, ".glb", false, 2, (Object) null)) {
                strInterpretString2 = strInterpretString2 + ".glb";
            }
            final File file = new File(filesDir, strInterpretString2);
            File parentFile = file.getParentFile();
            if (parentFile != null) {
                parentFile.mkdirs();
            }
            final File file2 = new File(CatroidApplication.getAppContext().getCacheDir(), "glb_temp_export_" + System.currentTimeMillis());
            file2.mkdirs();
            final File file3 = new File(file2, "temp_model.gltf");
            Gdx.f82app.postRunnable(new Runnable() { // from class: org.catrobat.catroid.content.actions.Export3dObjectToGlbAction$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    Export3dObjectToGlbAction.act$lambda$0(str, this, file3, file, filesDir, file2);
                }
            });
        }
        return this.finished;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void act$lambda$0(String idStr, Export3dObjectToGlbAction this$0, File tempGltfFile, File finalDestFile, File file, File tempDir) {
        Intrinsics.checkNotNullParameter(idStr, "$idStr");
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(tempGltfFile, "$tempGltfFile");
        Intrinsics.checkNotNullParameter(finalDestFile, "$finalDestFile");
        Intrinsics.checkNotNullParameter(tempDir, "$tempDir");
        try {
            try {
                StageListener activeStageListener = StageActivity.getActiveStageListener();
                ThreeDManager threeDManager = activeStageListener != null ? activeStageListener.getThreeDManager() : null;
                ModelInstance modelInstance = threeDManager != null ? threeDManager.getModelInstance(idStr) : null;
                if (modelInstance != null) {
                    this$0.ensurePbrMaterials(modelInstance);
                    ArrayList arrayList = new ArrayList();
                    int i = modelInstance.nodes.size;
                    for (int i2 = 0; i2 < i; i2++) {
                        arrayList.add(new Matrix4(modelInstance.nodes.get(i2).localTransform));
                    }
                    for (int i3 = 0; i3 < i; i3++) {
                        modelInstance.nodes.get(i3).localTransform.mulLeft(modelInstance.transform);
                    }
                    new GLTFExporter().export(new Scene(modelInstance), Gdx.files.absolute(tempGltfFile.getAbsolutePath()));
                    for (int i4 = 0; i4 < i; i4++) {
                        modelInstance.nodes.get(i4).localTransform.set((Matrix4) arrayList.get(i4));
                    }
                    if (GltfToGlbConverter.INSTANCE.convert(tempGltfFile, finalDestFile, file)) {
                        Gdx.f82app.log("GLB_Export", "Successfully packed and exported " + idStr + " to " + finalDestFile.getName());
                    } else {
                        Gdx.f82app.error("GLB_Export", "Failed to pack files into GLB");
                    }
                } else {
                    Gdx.f82app.error("GLB_Export", "Object " + idStr + " not found in ThreeDManager");
                }
            } catch (Exception e) {
                Gdx.f82app.error("GLB_Export", "Error exporting object " + idStr, e);
                e.printStackTrace();
            }
        } finally {
            FilesKt.deleteRecursively(tempDir);
            this$0.finished = true;
        }
    }

    private final void ensurePbrMaterials(ModelInstance instance) {
        int i = instance.materials.size;
        for (int i2 = 0; i2 < i; i2++) {
            Material material = instance.materials.get(i2);
            if (material.has(ColorAttribute.Diffuse) && !material.has(PBRColorAttribute.BaseColorFactor)) {
                Attribute attribute = material.get(ColorAttribute.Diffuse);
                Intrinsics.checkNotNull(attribute, "null cannot be cast to non-null type com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute");
                material.set(PBRColorAttribute.createBaseColorFactor(((ColorAttribute) attribute).color));
            }
            if (material.has(TextureAttribute.Diffuse) && !material.has(PBRTextureAttribute.BaseColorTexture)) {
                Attribute attribute2 = material.get(TextureAttribute.Diffuse);
                Intrinsics.checkNotNull(attribute2, "null cannot be cast to non-null type com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute");
                material.set(PBRTextureAttribute.createBaseColorTexture((Texture) ((TextureAttribute) attribute2).textureDescription.texture));
            }
            if (!material.has(PBRFloatAttribute.Metallic)) {
                material.set(PBRFloatAttribute.createMetallic(0.0f));
            }
            if (!material.has(PBRFloatAttribute.Roughness)) {
                material.set(PBRFloatAttribute.createRoughness(1.0f));
            }
        }
    }

    @Override // com.badlogic.gdx.scenes.scene2d.Action
    public void restart() {
        super.restart();
        this.started = false;
        this.finished = false;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.Action, com.badlogic.gdx.utils.Pool.Poolable
    public void reset() {
        super.reset();
        this.started = false;
        this.finished = false;
        this.scope = null;
        this.objectId = null;
        this.destFileName = null;
    }
}

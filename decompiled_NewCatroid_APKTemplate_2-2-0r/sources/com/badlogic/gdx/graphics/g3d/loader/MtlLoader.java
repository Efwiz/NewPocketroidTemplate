package com.badlogic.gdx.graphics.g3d.loader;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g3d.model.data.ModelMaterial;
import com.badlogic.gdx.graphics.g3d.model.data.ModelTexture;
import com.badlogic.gdx.utils.Array;
import com.huawei.hms.ml.language.common.utils.LanguageCodeUtil;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import org.eclipse.jgit.transport.WalkEncryption;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/graphics/g3d/loader/MtlLoader.class
  classes8.dex
 */
/* JADX INFO: compiled from: ObjLoader.java */
/* JADX INFO: loaded from: classes8.dex */
class MtlLoader {
    public Array<ModelMaterial> materials = new Array<>();

    MtlLoader() {
    }

    public void load(FileHandle fileHandle) {
        ObjMaterial objMaterial = new ObjMaterial();
        if (fileHandle == null || !fileHandle.exists()) {
            return;
        }
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileHandle.read()), 4096);
        while (true) {
            try {
                String line = bufferedReader.readLine();
                if (line != null) {
                    if (line.length() > 0 && line.charAt(0) == '\t') {
                        line = line.substring(1).trim();
                    }
                    String[] strArrSplit = line.split(WalkEncryption.Vals.REGEX_WS);
                    if (strArrSplit[0].length() != 0 && strArrSplit[0].charAt(0) != '#') {
                        String lowerCase = strArrSplit[0].toLowerCase();
                        if (lowerCase.equals("newmtl")) {
                            this.materials.add(objMaterial.build());
                            if (strArrSplit.length > 1) {
                                objMaterial.materialName = strArrSplit[1];
                                objMaterial.materialName = objMaterial.materialName.replace('.', '_');
                            } else {
                                objMaterial.materialName = "default";
                            }
                            objMaterial.reset();
                        } else if (lowerCase.equals(LanguageCodeUtil.KA)) {
                            objMaterial.ambientColor = parseColor(strArrSplit);
                        } else if (lowerCase.equals("kd")) {
                            objMaterial.diffuseColor = parseColor(strArrSplit);
                        } else if (lowerCase.equals("ks")) {
                            objMaterial.specularColor = parseColor(strArrSplit);
                        } else if (lowerCase.equals(LanguageCodeUtil.TR) || lowerCase.equals("d")) {
                            objMaterial.opacity = Float.parseFloat(strArrSplit[1]);
                        } else if (lowerCase.equals("ns")) {
                            objMaterial.shininess = Float.parseFloat(strArrSplit[1]);
                        } else if (lowerCase.equals("map_d")) {
                            objMaterial.alphaTexFilename = fileHandle.parent().child(strArrSplit[1]).path();
                        } else if (lowerCase.equals("map_ka")) {
                            objMaterial.ambientTexFilename = fileHandle.parent().child(strArrSplit[1]).path();
                        } else if (lowerCase.equals("map_kd")) {
                            objMaterial.diffuseTexFilename = fileHandle.parent().child(strArrSplit[1]).path();
                        } else if (lowerCase.equals("map_ks")) {
                            objMaterial.specularTexFilename = fileHandle.parent().child(strArrSplit[1]).path();
                        } else if (lowerCase.equals("map_ns")) {
                            objMaterial.shininessTexFilename = fileHandle.parent().child(strArrSplit[1]).path();
                        }
                    }
                } else {
                    bufferedReader.close();
                    this.materials.add(objMaterial.build());
                    return;
                }
            } catch (IOException unused) {
                return;
            }
        }
    }

    private Color parseColor(String[] strArr) {
        return new Color(Float.parseFloat(strArr[1]), Float.parseFloat(strArr[2]), Float.parseFloat(strArr[3]), strArr.length > 4 ? Float.parseFloat(strArr[4]) : 1.0f);
    }

    public ModelMaterial getMaterial(String str) {
        Array.ArrayIterator<ModelMaterial> it = this.materials.iterator();
        while (it.hasNext()) {
            ModelMaterial next = it.next();
            if (next.f107id.equals(str)) {
                return next;
            }
        }
        ModelMaterial modelMaterial = new ModelMaterial();
        modelMaterial.f107id = str;
        modelMaterial.diffuse = new Color(Color.WHITE);
        this.materials.add(modelMaterial);
        return modelMaterial;
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/graphics/g3d/loader/MtlLoader$ObjMaterial.class
      classes8.dex
     */
    /* JADX INFO: compiled from: ObjLoader.java */
    private static class ObjMaterial {
        String alphaTexFilename;
        Color ambientColor;
        String ambientTexFilename;
        Color diffuseColor;
        String diffuseTexFilename;
        String materialName = "default";
        float opacity;
        float shininess;
        String shininessTexFilename;
        Color specularColor;
        String specularTexFilename;

        public ObjMaterial() {
            reset();
        }

        public ModelMaterial build() {
            ModelMaterial modelMaterial = new ModelMaterial();
            modelMaterial.f107id = this.materialName;
            modelMaterial.ambient = this.ambientColor == null ? null : new Color(this.ambientColor);
            modelMaterial.diffuse = new Color(this.diffuseColor);
            modelMaterial.specular = new Color(this.specularColor);
            modelMaterial.opacity = this.opacity;
            modelMaterial.shininess = this.shininess;
            addTexture(modelMaterial, this.alphaTexFilename, 9);
            addTexture(modelMaterial, this.ambientTexFilename, 4);
            addTexture(modelMaterial, this.diffuseTexFilename, 2);
            addTexture(modelMaterial, this.specularTexFilename, 5);
            addTexture(modelMaterial, this.shininessTexFilename, 6);
            return modelMaterial;
        }

        private void addTexture(ModelMaterial modelMaterial, String str, int i) {
            if (str != null) {
                ModelTexture modelTexture = new ModelTexture();
                modelTexture.usage = i;
                modelTexture.fileName = str;
                if (modelMaterial.textures == null) {
                    modelMaterial.textures = new Array<>(1);
                }
                modelMaterial.textures.add(modelTexture);
            }
        }

        public void reset() {
            this.ambientColor = null;
            this.diffuseColor = Color.WHITE;
            this.specularColor = Color.WHITE;
            this.opacity = 1.0f;
            this.shininess = 0.0f;
            this.alphaTexFilename = null;
            this.ambientTexFilename = null;
            this.diffuseTexFilename = null;
            this.shininessTexFilename = null;
            this.specularTexFilename = null;
        }
    }
}

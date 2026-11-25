package com.badlogic.gdx.assets.loaders;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.utils.Array;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/assets/loaders/ShaderProgramLoader.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public class ShaderProgramLoader extends AsynchronousAssetLoader<ShaderProgram, ShaderProgramParameter> {
    private String fragmentFileSuffix;
    private String vertexFileSuffix;

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/assets/loaders/ShaderProgramLoader$ShaderProgramParameter.class
      classes8.dex
     */
    public static class ShaderProgramParameter extends AssetLoaderParameters<ShaderProgram> {
        public String fragmentFile;
        public boolean logOnCompileFailure = true;
        public String prependFragmentCode;
        public String prependVertexCode;
        public String vertexFile;
    }

    @Override // com.badlogic.gdx.assets.loaders.AssetLoader
    public Array<AssetDescriptor> getDependencies(String str, FileHandle fileHandle, ShaderProgramParameter shaderProgramParameter) {
        return null;
    }

    @Override // com.badlogic.gdx.assets.loaders.AsynchronousAssetLoader
    public void loadAsync(AssetManager assetManager, String str, FileHandle fileHandle, ShaderProgramParameter shaderProgramParameter) {
    }

    public ShaderProgramLoader(FileHandleResolver fileHandleResolver) {
        super(fileHandleResolver);
        this.vertexFileSuffix = ".vert";
        this.fragmentFileSuffix = ".frag";
    }

    public ShaderProgramLoader(FileHandleResolver fileHandleResolver, String str, String str2) {
        super(fileHandleResolver);
        this.vertexFileSuffix = str;
        this.fragmentFileSuffix = str2;
    }

    @Override // com.badlogic.gdx.assets.loaders.AsynchronousAssetLoader
    public ShaderProgram loadSync(AssetManager assetManager, String str, FileHandle fileHandle, ShaderProgramParameter shaderProgramParameter) {
        String str2;
        String str3 = null;
        if (shaderProgramParameter != null) {
            String str4 = shaderProgramParameter.vertexFile != null ? shaderProgramParameter.vertexFile : null;
            str2 = shaderProgramParameter.fragmentFile != null ? shaderProgramParameter.fragmentFile : null;
            str3 = str4;
        } else {
            str2 = null;
        }
        if (str3 == null && str.endsWith(this.fragmentFileSuffix)) {
            str3 = str.substring(0, str.length() - this.fragmentFileSuffix.length()) + this.vertexFileSuffix;
        }
        if (str2 == null && str.endsWith(this.vertexFileSuffix)) {
            str2 = str.substring(0, str.length() - this.vertexFileSuffix.length()) + this.fragmentFileSuffix;
        }
        FileHandle fileHandleResolve = str3 == null ? fileHandle : resolve(str3);
        if (str2 != null) {
            fileHandle = resolve(str2);
        }
        String string = fileHandleResolve.readString();
        String string2 = fileHandleResolve.equals(fileHandle) ? string : fileHandle.readString();
        if (shaderProgramParameter != null) {
            if (shaderProgramParameter.prependVertexCode != null) {
                string = shaderProgramParameter.prependVertexCode + string;
            }
            if (shaderProgramParameter.prependFragmentCode != null) {
                string2 = shaderProgramParameter.prependFragmentCode + string2;
            }
        }
        ShaderProgram shaderProgram = new ShaderProgram(string, string2);
        if ((shaderProgramParameter == null || shaderProgramParameter.logOnCompileFailure) && !shaderProgram.isCompiled()) {
            assetManager.getLogger().error("ShaderProgram " + str + " failed to compile:\n" + shaderProgram.getLog());
        }
        return shaderProgram;
    }
}

package com.badlogic.gdx.graphics.g3d.utils;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.g3d.Shader;
import com.badlogic.gdx.graphics.g3d.shaders.DefaultShader;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/graphics/g3d/utils/DefaultShaderProvider.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public class DefaultShaderProvider extends BaseShaderProvider {
    public final DefaultShader.Config config;

    public DefaultShaderProvider(DefaultShader.Config config) {
        this.config = config == null ? new DefaultShader.Config() : config;
    }

    public DefaultShaderProvider(String str, String str2) {
        this(new DefaultShader.Config(str, str2));
    }

    public DefaultShaderProvider(FileHandle fileHandle, FileHandle fileHandle2) {
        this(fileHandle.readString(), fileHandle2.readString());
    }

    public DefaultShaderProvider() {
        this(null);
    }

    @Override // com.badlogic.gdx.graphics.g3d.utils.BaseShaderProvider
    protected Shader createShader(Renderable renderable) {
        return new DefaultShader(renderable, this.config);
    }
}

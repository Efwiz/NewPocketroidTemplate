package com.crashinvaders.vfx.gl;

import com.badlogic.gdx.Gdx;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;

/* JADX INFO: loaded from: classes8.dex */
public class DefaultVfxGlExtension implements VfxGlExtension {
    private static final IntBuffer tmpIntBuf = ByteBuffer.allocateDirect(64).order(ByteOrder.nativeOrder()).asIntBuffer();

    @Override // com.crashinvaders.vfx.gl.VfxGlExtension
    public int getBoundFboHandle() {
        IntBuffer intBuffer = tmpIntBuf;
        Gdx.gl.glGetIntegerv(36006, intBuffer);
        return intBuffer.get(0);
    }
}

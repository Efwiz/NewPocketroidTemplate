package com.badlogic.gdx.graphics.g3d.particles.renderers;

import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.particles.ParallelArray;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/graphics/g3d/particles/renderers/ModelInstanceControllerRenderData.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public class ModelInstanceControllerRenderData extends ParticleControllerRenderData {
    public ParallelArray.FloatChannel colorChannel;
    public ParallelArray.ObjectChannel<ModelInstance> modelInstanceChannel;
    public ParallelArray.FloatChannel rotationChannel;
    public ParallelArray.FloatChannel scaleChannel;
}

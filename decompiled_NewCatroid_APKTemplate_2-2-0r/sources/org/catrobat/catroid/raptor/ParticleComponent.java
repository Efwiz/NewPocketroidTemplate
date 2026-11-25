package org.catrobat.catroid.raptor;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/raptor/ParticleComponent.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class ParticleComponent implements Component {
    public boolean looping = true;
    public float duration = 5.0f;
    public float startLifetime = 5.0f;
    public int maxParticles = 100;
    public float emissionRate = 10.0f;
    public boolean isAdditive = false;
    public String texturePath = null;
    public float coneAngle = 45.0f;
    public float coneRadius = 1.0f;
    public List<ParticleCurvePoint<Color>> colorGraph = new ArrayList();
    public float baseSize = 1.0f;
    public List<ParticleCurvePoint<Float>> sizeGraph = new ArrayList();
    public List<ParticleCurvePoint<Float>> rotationGraph = new ArrayList();
    public List<ParticleCurvePoint<Float>> speedGraph = new ArrayList();
    public List<ParticleCurvePoint<Float>> gravityGraph = new ArrayList();
    public List<ParticleCurvePoint<Float>> vortexGraph = new ArrayList();
    public List<ParticleCurvePoint<Float>> turbulenceGraph = new ArrayList();
    public Color startColor = new Color(1.0f, 1.0f, 1.0f, 1.0f);
    public Color endColor = new Color(1.0f, 1.0f, 1.0f, 1.0f);
    public float startSize = 1.0f;
    public float endSize = 1.0f;
    public float startSpeed = 5.0f;
    public float gravityModifier = 0.0f;
    public float startRotation = 0.0f;
    public float rotationOverLifetime = 0.0f;
    public SpawnShape spawnShape = SpawnShape.CYLINDER;
    public Vector3 spawnSize = new Vector3(1.0f, 1.0f, 1.0f);
    public boolean spawnOnSurface = false;

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/raptor/ParticleComponent$SpawnShape.class
      classes14.dex
     */
    public enum SpawnShape {
        POINT,
        BOX,
        SPHERE,
        CYLINDER
    }

    public void migrateOldDataIfNeeded() {
        if (this.colorGraph.isEmpty()) {
            this.colorGraph.add(new ParticleCurvePoint<>(0.0f, new Color(this.startColor)));
            this.colorGraph.add(new ParticleCurvePoint<>(1.0f, new Color(this.endColor)));
        }
        if (this.sizeGraph.isEmpty()) {
            this.baseSize = this.startSize;
            this.sizeGraph.add(new ParticleCurvePoint<>(0.0f, Float.valueOf(1.0f)));
            float f = this.startSize;
            this.sizeGraph.add(new ParticleCurvePoint<>(1.0f, Float.valueOf(f != 0.0f ? this.endSize / f : 1.0f)));
        }
        if (this.speedGraph.isEmpty()) {
            this.speedGraph.add(new ParticleCurvePoint<>(0.0f, Float.valueOf(this.startSpeed)));
            this.speedGraph.add(new ParticleCurvePoint<>(1.0f, Float.valueOf(this.startSpeed)));
        }
        if (this.gravityGraph.isEmpty()) {
            float f2 = this.gravityModifier;
            if (f2 != 0.0f) {
                float f3 = f2 * 9.81f;
                this.gravityGraph.add(new ParticleCurvePoint<>(0.0f, Float.valueOf(f3)));
                this.gravityGraph.add(new ParticleCurvePoint<>(1.0f, Float.valueOf(f3)));
            }
        }
        if (this.rotationGraph.isEmpty()) {
            if (this.startRotation == 0.0f && this.rotationOverLifetime == 0.0f) {
                return;
            }
            this.rotationGraph.add(new ParticleCurvePoint<>(0.0f, Float.valueOf(this.rotationOverLifetime)));
            this.rotationGraph.add(new ParticleCurvePoint<>(1.0f, Float.valueOf(this.rotationOverLifetime)));
        }
    }

    public void sortGraphs() {
        Comparator comparator = new Comparator() { // from class: org.catrobat.catroid.raptor.ParticleComponent$$ExternalSyntheticLambda0
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                return Float.compare(((ParticleCurvePoint) obj).time, ((ParticleCurvePoint) obj2).time);
            }
        };
        Collections.sort(this.colorGraph, comparator);
        Collections.sort(this.sizeGraph, comparator);
        Collections.sort(this.rotationGraph, comparator);
        Collections.sort(this.speedGraph, comparator);
        Collections.sort(this.gravityGraph, comparator);
        Collections.sort(this.vortexGraph, comparator);
        Collections.sort(this.turbulenceGraph, comparator);
    }
}

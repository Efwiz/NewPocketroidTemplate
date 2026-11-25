package org.catrobat.catroid.raptor;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/raptor/ParticleSystem3DComponent.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class ParticleSystem3DComponent implements Component {
    public boolean isPlaying = true;
    public float duration = 5.0f;
    public boolean looping = true;
    public boolean prewarm = false;
    public TextureSheetModule textureSheetAnimation = new TextureSheetModule();
    public MinMaxCurve startDelay = new MinMaxCurve(0.0f);
    public MinMaxCurve startLifetime = new MinMaxCurve(5.0f);
    public MinMaxCurve startSpeed = new MinMaxCurve(5.0f);
    public boolean threeDStartSize = false;
    public MinMaxCurve startSize = new MinMaxCurve(1.0f);
    public MinMaxCurve startSizeX = new MinMaxCurve(1.0f);
    public MinMaxCurve startSizeY = new MinMaxCurve(1.0f);
    public MinMaxCurve startSizeZ = new MinMaxCurve(1.0f);
    public boolean threeDStartRotation = false;
    public MinMaxCurve startRotation = new MinMaxCurve(0.0f);
    public MinMaxCurve startRotationX = new MinMaxCurve(0.0f);
    public MinMaxCurve startRotationY = new MinMaxCurve(0.0f);
    public MinMaxCurve startRotationZ = new MinMaxCurve(0.0f);
    public float flipRotation = 0.0f;
    public MinMaxGradient startColor = new MinMaxGradient(new Color(1.0f, 1.0f, 1.0f, 1.0f));
    public MinMaxCurve gravityModifier = new MinMaxCurve(0.0f);
    public SimulationSpace simulationSpace = SimulationSpace.LOCAL;
    public ScalingMode scalingMode = ScalingMode.LOCAL;
    public int maxParticles = 1000;
    public long randomSeed = 0;
    public EmissionModule emission = new EmissionModule();
    public ShapeModule shape = new ShapeModule();
    public VelocityOverLifetimeModule velocityOverLifetime = new VelocityOverLifetimeModule();
    public ForceOverLifetimeModule forceOverLifetime = new ForceOverLifetimeModule();
    public ColorOverLifetimeModule colorOverLifetime = new ColorOverLifetimeModule();
    public SizeOverLifetimeModule sizeOverLifetime = new SizeOverLifetimeModule();
    public RotationOverLifetimeModule rotationOverLifetime = new RotationOverLifetimeModule();
    public NoiseModule noise = new NoiseModule();
    public CollisionModule collision = new CollisionModule();
    public SubEmittersModule subEmitters = new SubEmittersModule();
    public TrailsModule trails = new TrailsModule();
    public RendererModule renderer = new RendererModule();

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/raptor/ParticleSystem3DComponent$AnimationType.class
      classes14.dex
     */
    public enum AnimationType {
        WHOLE_SHEET,
        SINGLE_ROW
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/raptor/ParticleSystem3DComponent$CollisionMode.class
      classes14.dex
     */
    public enum CollisionMode {
        NONE,
        WORLD_PHYSICS,
        PLANES
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/raptor/ParticleSystem3DComponent$CollisionModule.class
      classes14.dex
     */
    public static class CollisionModule {
        public boolean enabled = false;
        public CollisionMode mode = CollisionMode.WORLD_PHYSICS;
        public float bounce = 0.5f;
        public float lifetimeLoss = 0.0f;
        public float minKillSpeed = 0.0f;
        public int maxCollisionShapes = 256;
        public float radiusScale = 1.0f;
        public float dampen = 0.0f;
        public CollisionQuality quality = CollisionQuality.MEDIUM;
        public List<CollisionPlane> planes = new ArrayList();
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/raptor/ParticleSystem3DComponent$CollisionPlane.class
      classes14.dex
     */
    public static class CollisionPlane {
        public Vector3 point = new Vector3(0.0f, 0.0f, 0.0f);
        public Vector3 normal = new Vector3(0.0f, 1.0f, 0.0f);
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/raptor/ParticleSystem3DComponent$CollisionQuality.class
      classes14.dex
     */
    public enum CollisionQuality {
        LOW,
        MEDIUM,
        HIGH
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/raptor/ParticleSystem3DComponent$ColorOverLifetimeModule.class
      classes14.dex
     */
    public static class ColorOverLifetimeModule {
        public boolean enabled = true;
        public MinMaxGradient color = new MinMaxGradient(new Color(1.0f, 1.0f, 1.0f, 1.0f));
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/raptor/ParticleSystem3DComponent$CurveMode.class
      classes14.dex
     */
    public enum CurveMode {
        CONSTANT,
        RANDOM_BETWEEN_TWO_CONSTANTS,
        CURVE,
        RANDOM_BETWEEN_TWO_CURVES
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/raptor/ParticleSystem3DComponent$EmissionModule.class
      classes14.dex
     */
    public static class EmissionModule {
        public boolean enabled = true;
        public MinMaxCurve rateOverTime = new MinMaxCurve(10.0f);
        public MinMaxCurve rateOverDistance = new MinMaxCurve(0.0f);
        public List<Burst> bursts = new ArrayList();
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/raptor/ParticleSystem3DComponent$EmitFrom.class
      classes14.dex
     */
    public enum EmitFrom {
        VOLUME,
        SURFACE,
        EDGE
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/raptor/ParticleSystem3DComponent$ForceOverLifetimeModule.class
      classes14.dex
     */
    public static class ForceOverLifetimeModule {
        public boolean enabled = false;
        public MinMaxCurve x = new MinMaxCurve(0.0f);
        public MinMaxCurve y = new MinMaxCurve(0.0f);
        public MinMaxCurve z = new MinMaxCurve(0.0f);
        public SimulationSpace space = SimulationSpace.WORLD;
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/raptor/ParticleSystem3DComponent$GradientMode.class
      classes14.dex
     */
    public enum GradientMode {
        COLOR,
        RANDOM_BETWEEN_TWO_COLORS,
        GRADIENT,
        RANDOM_BETWEEN_TWO_GRADIENTS
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/raptor/ParticleSystem3DComponent$MeshType.class
      classes14.dex
     */
    public enum MeshType {
        CUBE,
        SPHERE_LOW,
        CYLINDER_LOW,
        CUSTOM
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/raptor/ParticleSystem3DComponent$NoiseModule.class
      classes14.dex
     */
    public static class NoiseModule {
        public boolean enabled = false;
        public float strength = 1.0f;
        public float frequency = 0.5f;
        public int octaves = 1;
        public float scrollSpeed = 0.0f;
        public boolean damping = true;
        public boolean separateAxes = false;
        public float strengthX = 1.0f;
        public float strengthY = 1.0f;
        public float strengthZ = 1.0f;
        public boolean remapEnabled = false;
        public MinMaxCurve remapCurve = new MinMaxCurve(1.0f);
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/raptor/ParticleSystem3DComponent$RenderMode.class
      classes14.dex
     */
    public enum RenderMode {
        BILLBOARD,
        STRETCHED_BILLBOARD,
        HORIZONTAL_BILLBOARD,
        VERTICAL_BILLBOARD,
        MESH
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/raptor/ParticleSystem3DComponent$RendererModule.class
      classes14.dex
     */
    public static class RendererModule {
        public RenderMode renderMode = RenderMode.BILLBOARD;
        public float lengthScale = 2.0f;
        public float speedScale = 0.0f;
        public MeshType meshType = MeshType.CUBE;
        public String meshPath = null;
        public boolean alignToVelocity = false;
        public boolean isAdditive = false;
        public String texturePath = null;
        public int sortMode = 1;
        public float minParticleSize = 0.0f;
        public float maxParticleSize = 0.5f;
        public boolean castShadows = false;
        public boolean receiveShadows = false;
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/raptor/ParticleSystem3DComponent$RotationOverLifetimeModule.class
      classes14.dex
     */
    public static class RotationOverLifetimeModule {
        public boolean enabled = false;
        public boolean separateAxes = false;
        public MinMaxCurve angularVelocity = new MinMaxCurve(45.0f);
        public MinMaxCurve angularVelocityX = new MinMaxCurve(0.0f);
        public MinMaxCurve angularVelocityY = new MinMaxCurve(0.0f);
        public MinMaxCurve angularVelocityZ = new MinMaxCurve(45.0f);
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/raptor/ParticleSystem3DComponent$ScalingMode.class
      classes14.dex
     */
    public enum ScalingMode {
        HIERARCHY,
        LOCAL,
        SHAPE
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/raptor/ParticleSystem3DComponent$ShapeModule.class
      classes14.dex
     */
    public static class ShapeModule {
        public boolean enabled = true;
        public ShapeType type = ShapeType.CONE;
        public float coneAngle = 25.0f;
        public float coneRadius = 1.0f;
        public float coneLength = 5.0f;
        public EmitFrom emitFrom = EmitFrom.VOLUME;
        public Vector3 boxSize = new Vector3(1.0f, 1.0f, 1.0f);
        public float sphereRadius = 1.0f;
        public float circleRadius = 1.0f;
        public float circleArc = 360.0f;
        public float edgeLength = 1.0f;
        public boolean randomizeDirection = false;
        public float spherizeDirection = 0.0f;
        public float randomizePosition = 0.0f;
        public String meshModelPath = null;
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/raptor/ParticleSystem3DComponent$ShapeType.class
      classes14.dex
     */
    public enum ShapeType {
        SPHERE,
        HEMISPHERE,
        CONE,
        BOX,
        CIRCLE,
        EDGE,
        MESH_SHAPE
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/raptor/ParticleSystem3DComponent$SimulationSpace.class
      classes14.dex
     */
    public enum SimulationSpace {
        LOCAL,
        WORLD
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/raptor/ParticleSystem3DComponent$SizeOverLifetimeModule.class
      classes14.dex
     */
    public static class SizeOverLifetimeModule {
        public boolean enabled = false;
        public boolean separateAxes = false;
        public MinMaxCurve size = new MinMaxCurve(1.0f);
        public MinMaxCurve sizeX = new MinMaxCurve(1.0f);
        public MinMaxCurve sizeY = new MinMaxCurve(1.0f);
        public MinMaxCurve sizeZ = new MinMaxCurve(1.0f);
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/raptor/ParticleSystem3DComponent$SubEmitterEntry.class
      classes14.dex
     */
    public static class SubEmitterEntry {
        public SubEmitterTrigger trigger = SubEmitterTrigger.DEATH;
        public String subEmitterObjectId = "";
        public float probability = 1.0f;
        public int emitCount = 0;
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/raptor/ParticleSystem3DComponent$SubEmitterTrigger.class
      classes14.dex
     */
    public enum SubEmitterTrigger {
        BIRTH,
        DEATH,
        COLLISION
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/raptor/ParticleSystem3DComponent$SubEmittersModule.class
      classes14.dex
     */
    public static class SubEmittersModule {
        public boolean enabled = false;
        public List<SubEmitterEntry> entries = new ArrayList();
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/raptor/ParticleSystem3DComponent$TextureSheetModule.class
      classes14.dex
     */
    public static class TextureSheetModule {
        public boolean enabled = false;
        public int tilesX = 1;
        public int tilesY = 1;
        public AnimationType animationType = AnimationType.WHOLE_SHEET;
        public MinMaxCurve frameOverTime = new MinMaxCurve(0.0f, 1.0f);
        public MinMaxCurve startFrame = new MinMaxCurve(0.0f);
        public int cycles = 1;
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/raptor/ParticleSystem3DComponent$TrailsModule.class
      classes14.dex
     */
    public static class TrailsModule {
        public boolean enabled = false;
        public float ratio = 1.0f;
        public float lifetime = 1.0f;
        public float minimumVertexDistance = 0.2f;
        public boolean worldSpace = false;
        public boolean dieWithParticles = true;
        public MinMaxGradient colorOverLifetime = new MinMaxGradient(new Color(1.0f, 1.0f, 1.0f, 1.0f));
        public MinMaxCurve widthOverTrail = new MinMaxCurve(1.0f);
        public boolean inheritParticleColor = true;
        public int textureMode = 0;
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/raptor/ParticleSystem3DComponent$VelocityOverLifetimeModule.class
      classes14.dex
     */
    public static class VelocityOverLifetimeModule {
        public boolean enabled = false;
        public MinMaxCurve x = new MinMaxCurve(0.0f);
        public MinMaxCurve y = new MinMaxCurve(0.0f);
        public MinMaxCurve z = new MinMaxCurve(0.0f);
        public SimulationSpace space = SimulationSpace.LOCAL;
        public MinMaxCurve orbitalX = new MinMaxCurve(0.0f);
        public MinMaxCurve orbitalY = new MinMaxCurve(0.0f);
        public MinMaxCurve orbitalZ = new MinMaxCurve(0.0f);
        public MinMaxCurve radial = new MinMaxCurve(0.0f);
        public MinMaxCurve speedModifier = new MinMaxCurve(1.0f);
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/raptor/ParticleSystem3DComponent$Burst.class
      classes14.dex
     */
    public static class Burst {
        public MinMaxCurve count;
        public int cycles;
        public float interval;
        public float probability;
        public float time;

        public Burst() {
            this.time = 0.0f;
            this.count = new MinMaxCurve(30.0f);
            this.cycles = 1;
            this.interval = 0.01f;
            this.probability = 1.0f;
        }

        public Burst(float time, float count) {
            this.time = 0.0f;
            this.count = new MinMaxCurve(30.0f);
            this.cycles = 1;
            this.interval = 0.01f;
            this.probability = 1.0f;
            this.time = time;
            this.count = new MinMaxCurve(count);
        }
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/raptor/ParticleSystem3DComponent$MinMaxCurve.class
      classes14.dex
     */
    public static class MinMaxCurve {
        public float constantMax;
        public float constantMin;
        public List<ParticleCurvePoint<Float>> curve;
        public List<ParticleCurvePoint<Float>> curveMin;
        public CurveMode mode;
        public float multiplier;

        public MinMaxCurve() {
            this.mode = CurveMode.CONSTANT;
            this.constantMin = 0.0f;
            this.constantMax = 1.0f;
            this.curve = new ArrayList();
            this.curveMin = new ArrayList();
            this.multiplier = 1.0f;
        }

        public MinMaxCurve(float constant) {
            this.mode = CurveMode.CONSTANT;
            this.constantMin = 0.0f;
            this.constantMax = 1.0f;
            this.curve = new ArrayList();
            this.curveMin = new ArrayList();
            this.multiplier = 1.0f;
            this.mode = CurveMode.CONSTANT;
            this.constantMax = constant;
            this.constantMin = constant;
        }

        public MinMaxCurve(float min, float max) {
            this.mode = CurveMode.CONSTANT;
            this.constantMin = 0.0f;
            this.constantMax = 1.0f;
            this.curve = new ArrayList();
            this.curveMin = new ArrayList();
            this.multiplier = 1.0f;
            this.mode = CurveMode.RANDOM_BETWEEN_TWO_CONSTANTS;
            this.constantMin = min;
            this.constantMax = max;
        }

        public float evaluate(float t) {
            return evaluate(t, (float) Math.random());
        }

        public float evaluate(float t, float randomFactor) {
            float fEvaluateCurve;
            float f;
            int i = AnonymousClass1.$SwitchMap$org$catrobat$catroid$raptor$ParticleSystem3DComponent$CurveMode[this.mode.ordinal()];
            if (i == 1) {
                fEvaluateCurve = this.constantMax;
                f = this.multiplier;
            } else if (i == 2) {
                float f2 = this.constantMin;
                fEvaluateCurve = f2 + ((this.constantMax - f2) * randomFactor);
                f = this.multiplier;
            } else if (i == 3) {
                fEvaluateCurve = evaluateCurve(this.curve, t);
                f = this.multiplier;
            } else {
                if (i == 4) {
                    float fEvaluateCurve2 = evaluateCurve(this.curveMin, t);
                    return (fEvaluateCurve2 + ((evaluateCurve(this.curve, t) - fEvaluateCurve2) * randomFactor)) * this.multiplier;
                }
                fEvaluateCurve = this.constantMax;
                f = this.multiplier;
            }
            return fEvaluateCurve * f;
        }

        private float evaluateCurve(List<ParticleCurvePoint<Float>> pts, float t) {
            if (pts == null || pts.isEmpty()) {
                return this.constantMax;
            }
            if (pts.size() == 1) {
                return pts.get(0).value.floatValue();
            }
            float fMax = Math.max(0.0f, Math.min(1.0f, t));
            ParticleCurvePoint<Float> particleCurvePoint = pts.get(0);
            ParticleCurvePoint<Float> particleCurvePoint2 = pts.get(pts.size() - 1);
            for (int i = 0; i < pts.size() - 1; i++) {
                if (pts.get(i).time <= fMax) {
                    int i2 = i + 1;
                    if (pts.get(i2).time >= fMax) {
                        particleCurvePoint = pts.get(i);
                        particleCurvePoint2 = pts.get(i2);
                        break;
                    }
                }
            }
            if (particleCurvePoint == particleCurvePoint2 || particleCurvePoint.time == particleCurvePoint2.time) {
                return particleCurvePoint.value.floatValue();
            }
            return particleCurvePoint.value.floatValue() + ((particleCurvePoint2.value.floatValue() - particleCurvePoint.value.floatValue()) * ((fMax - particleCurvePoint.time) / (particleCurvePoint2.time - particleCurvePoint.time)));
        }
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/raptor/ParticleSystem3DComponent$MinMaxGradient.class
      classes14.dex
     */
    public static class MinMaxGradient {
        public Color colorMax;
        public Color colorMin;
        public List<ParticleCurvePoint<Color>> gradient;
        public List<ParticleCurvePoint<Color>> gradientMin;
        public GradientMode mode;

        public MinMaxGradient() {
            this.mode = GradientMode.COLOR;
            this.colorMin = new Color(1.0f, 1.0f, 1.0f, 1.0f);
            this.colorMax = new Color(1.0f, 1.0f, 1.0f, 1.0f);
            this.gradient = new ArrayList();
            this.gradientMin = new ArrayList();
        }

        public MinMaxGradient(Color color) {
            this.mode = GradientMode.COLOR;
            this.colorMin = new Color(1.0f, 1.0f, 1.0f, 1.0f);
            this.colorMax = new Color(1.0f, 1.0f, 1.0f, 1.0f);
            this.gradient = new ArrayList();
            this.gradientMin = new ArrayList();
            this.mode = GradientMode.COLOR;
            this.colorMax.set(color);
            this.colorMin.set(color);
        }

        public Color evaluate(float t) {
            return evaluate(t, (float) Math.random());
        }

        public Color evaluate(float t, float randomFactor) {
            int i = AnonymousClass1.$SwitchMap$org$catrobat$catroid$raptor$ParticleSystem3DComponent$GradientMode[this.mode.ordinal()];
            if (i == 1) {
                return new Color(this.colorMax);
            }
            if (i == 2) {
                return new Color(this.colorMin).lerp(this.colorMax, randomFactor);
            }
            if (i == 3) {
                return evaluateGradient(this.gradient, t);
            }
            if (i == 4) {
                return evaluateGradient(this.gradientMin, t).lerp(evaluateGradient(this.gradient, t), randomFactor);
            }
            return new Color(this.colorMax);
        }

        private Color evaluateGradient(List<ParticleCurvePoint<Color>> pts, float t) {
            if (pts == null || pts.isEmpty()) {
                return new Color(this.colorMax);
            }
            if (pts.size() == 1) {
                return new Color(pts.get(0).value);
            }
            float fMax = Math.max(0.0f, Math.min(1.0f, t));
            ParticleCurvePoint<Color> particleCurvePoint = pts.get(0);
            ParticleCurvePoint<Color> particleCurvePoint2 = pts.get(pts.size() - 1);
            for (int i = 0; i < pts.size() - 1; i++) {
                if (pts.get(i).time <= fMax) {
                    int i2 = i + 1;
                    if (pts.get(i2).time >= fMax) {
                        particleCurvePoint = pts.get(i);
                        particleCurvePoint2 = pts.get(i2);
                        break;
                    }
                }
            }
            if (particleCurvePoint == particleCurvePoint2 || particleCurvePoint.time == particleCurvePoint2.time) {
                return new Color(particleCurvePoint.value);
            }
            return new Color(particleCurvePoint.value).lerp(particleCurvePoint2.value, (fMax - particleCurvePoint.time) / (particleCurvePoint2.time - particleCurvePoint.time));
        }
    }

    public static ParticleSystem3DComponent migrateFromLegacy(ParticleComponent old) {
        if (old == null) {
            return new ParticleSystem3DComponent();
        }
        old.migrateOldDataIfNeeded();
        ParticleSystem3DComponent particleSystem3DComponent = new ParticleSystem3DComponent();
        particleSystem3DComponent.looping = old.looping;
        particleSystem3DComponent.duration = old.duration;
        particleSystem3DComponent.startLifetime = new MinMaxCurve(old.startLifetime);
        particleSystem3DComponent.maxParticles = old.maxParticles;
        particleSystem3DComponent.emission.rateOverTime = new MinMaxCurve(old.emissionRate);
        if (!old.speedGraph.isEmpty()) {
            particleSystem3DComponent.startSpeed = new MinMaxCurve(old.speedGraph.get(0).value.floatValue());
            if (old.speedGraph.size() > 1) {
                particleSystem3DComponent.velocityOverLifetime.enabled = true;
                particleSystem3DComponent.velocityOverLifetime.speedModifier = new MinMaxCurve();
                particleSystem3DComponent.velocityOverLifetime.speedModifier.mode = CurveMode.CURVE;
                particleSystem3DComponent.velocityOverLifetime.speedModifier.curve.addAll(old.speedGraph);
                particleSystem3DComponent.velocityOverLifetime.speedModifier.multiplier = 1.0f;
            }
        } else {
            particleSystem3DComponent.startSpeed = new MinMaxCurve(old.startSpeed);
        }
        particleSystem3DComponent.shape.enabled = true;
        int i = AnonymousClass1.$SwitchMap$org$catrobat$catroid$raptor$ParticleComponent$SpawnShape[old.spawnShape.ordinal()];
        if (i == 1) {
            particleSystem3DComponent.shape.type = ShapeType.SPHERE;
            particleSystem3DComponent.shape.sphereRadius = 0.001f;
        } else if (i == 2) {
            particleSystem3DComponent.shape.type = ShapeType.BOX;
            particleSystem3DComponent.shape.boxSize.set(old.spawnSize.x * 2.0f, old.spawnSize.y * 2.0f, old.spawnSize.z * 2.0f);
        } else if (i == 3) {
            particleSystem3DComponent.shape.type = ShapeType.SPHERE;
            particleSystem3DComponent.shape.sphereRadius = old.spawnSize.x;
        } else if (i == 4) {
            particleSystem3DComponent.shape.type = ShapeType.CONE;
            particleSystem3DComponent.shape.coneRadius = old.spawnSize.x;
            particleSystem3DComponent.shape.coneLength = old.spawnSize.y;
            particleSystem3DComponent.shape.coneAngle = old.coneAngle;
        }
        if (old.spawnOnSurface) {
            particleSystem3DComponent.shape.emitFrom = EmitFrom.SURFACE;
        }
        if (!old.sizeGraph.isEmpty()) {
            particleSystem3DComponent.startSize = new MinMaxCurve(old.baseSize);
            particleSystem3DComponent.sizeOverLifetime.enabled = true;
            particleSystem3DComponent.sizeOverLifetime.size = new MinMaxCurve();
            particleSystem3DComponent.sizeOverLifetime.size.mode = CurveMode.CURVE;
            particleSystem3DComponent.sizeOverLifetime.size.curve.addAll(old.sizeGraph);
        } else {
            particleSystem3DComponent.startSize = new MinMaxCurve(old.startSize);
        }
        if (!old.colorGraph.isEmpty()) {
            particleSystem3DComponent.colorOverLifetime.enabled = true;
            particleSystem3DComponent.colorOverLifetime.color = new MinMaxGradient();
            particleSystem3DComponent.colorOverLifetime.color.mode = GradientMode.GRADIENT;
            particleSystem3DComponent.colorOverLifetime.color.gradient.addAll(old.colorGraph);
        } else {
            particleSystem3DComponent.startColor = new MinMaxGradient(old.startColor);
        }
        float fFloatValue = 0.0f;
        if (!old.gravityGraph.isEmpty()) {
            Iterator<ParticleCurvePoint<Float>> it = old.gravityGraph.iterator();
            float fFloatValue2 = 0.0f;
            while (it.hasNext()) {
                fFloatValue2 += it.next().value.floatValue();
            }
            particleSystem3DComponent.gravityModifier = new MinMaxCurve((fFloatValue2 / old.gravityGraph.size()) / 9.81f);
        } else if (old.gravityModifier != 0.0f) {
            particleSystem3DComponent.gravityModifier = new MinMaxCurve(old.gravityModifier);
        }
        if (!old.turbulenceGraph.isEmpty()) {
            particleSystem3DComponent.noise.enabled = true;
            Iterator<ParticleCurvePoint<Float>> it2 = old.turbulenceGraph.iterator();
            float fFloatValue3 = 0.0f;
            while (it2.hasNext()) {
                fFloatValue3 += it2.next().value.floatValue();
            }
            particleSystem3DComponent.noise.strength = fFloatValue3 / old.turbulenceGraph.size();
            particleSystem3DComponent.noise.frequency = 0.5f;
        }
        if (!old.vortexGraph.isEmpty()) {
            particleSystem3DComponent.velocityOverLifetime.enabled = true;
            Iterator<ParticleCurvePoint<Float>> it3 = old.vortexGraph.iterator();
            while (it3.hasNext()) {
                fFloatValue += it3.next().value.floatValue();
            }
            particleSystem3DComponent.velocityOverLifetime.orbitalY = new MinMaxCurve(fFloatValue / old.vortexGraph.size());
        }
        if (!old.rotationGraph.isEmpty()) {
            particleSystem3DComponent.rotationOverLifetime.enabled = true;
            particleSystem3DComponent.rotationOverLifetime.angularVelocity = new MinMaxCurve();
            particleSystem3DComponent.rotationOverLifetime.angularVelocity.mode = CurveMode.CURVE;
            particleSystem3DComponent.rotationOverLifetime.angularVelocity.curve.addAll(old.rotationGraph);
        }
        particleSystem3DComponent.renderer.isAdditive = old.isAdditive;
        particleSystem3DComponent.renderer.texturePath = old.texturePath;
        return particleSystem3DComponent;
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/raptor/ParticleSystem3DComponent$1.class
      classes14.dex
     */
    /* JADX INFO: renamed from: org.catrobat.catroid.raptor.ParticleSystem3DComponent$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$org$catrobat$catroid$raptor$ParticleComponent$SpawnShape;
        static final /* synthetic */ int[] $SwitchMap$org$catrobat$catroid$raptor$ParticleSystem3DComponent$CurveMode;
        static final /* synthetic */ int[] $SwitchMap$org$catrobat$catroid$raptor$ParticleSystem3DComponent$GradientMode;

        static {
            int[] iArr = new int[ParticleComponent.SpawnShape.values().length];
            $SwitchMap$org$catrobat$catroid$raptor$ParticleComponent$SpawnShape = iArr;
            try {
                iArr[ParticleComponent.SpawnShape.POINT.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$raptor$ParticleComponent$SpawnShape[ParticleComponent.SpawnShape.BOX.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$raptor$ParticleComponent$SpawnShape[ParticleComponent.SpawnShape.SPHERE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$raptor$ParticleComponent$SpawnShape[ParticleComponent.SpawnShape.CYLINDER.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            int[] iArr2 = new int[GradientMode.values().length];
            $SwitchMap$org$catrobat$catroid$raptor$ParticleSystem3DComponent$GradientMode = iArr2;
            try {
                iArr2[GradientMode.COLOR.ordinal()] = 1;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$raptor$ParticleSystem3DComponent$GradientMode[GradientMode.RANDOM_BETWEEN_TWO_COLORS.ordinal()] = 2;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$raptor$ParticleSystem3DComponent$GradientMode[GradientMode.GRADIENT.ordinal()] = 3;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$raptor$ParticleSystem3DComponent$GradientMode[GradientMode.RANDOM_BETWEEN_TWO_GRADIENTS.ordinal()] = 4;
            } catch (NoSuchFieldError unused8) {
            }
            int[] iArr3 = new int[CurveMode.values().length];
            $SwitchMap$org$catrobat$catroid$raptor$ParticleSystem3DComponent$CurveMode = iArr3;
            try {
                iArr3[CurveMode.CONSTANT.ordinal()] = 1;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$raptor$ParticleSystem3DComponent$CurveMode[CurveMode.RANDOM_BETWEEN_TWO_CONSTANTS.ordinal()] = 2;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$raptor$ParticleSystem3DComponent$CurveMode[CurveMode.CURVE.ordinal()] = 3;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$raptor$ParticleSystem3DComponent$CurveMode[CurveMode.RANDOM_BETWEEN_TWO_CURVES.ordinal()] = 4;
            } catch (NoSuchFieldError unused12) {
            }
        }
    }
}

package com.badlogic.gdx.physics.box2d.graphics;

import com.badlogic.gdx.graphics.g2d.ParticleEmitter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.RayCastCallback;
import com.badlogic.gdx.physics.box2d.World;
import java.io.BufferedReader;
import java.io.IOException;

/* JADX INFO: loaded from: classes8.dex */
public class ParticleEmitterBox2D extends ParticleEmitter {
    private static final float EPSILON = 0.001f;
    final Vector2 endPoint;
    float normalAngle;
    boolean particleCollided;
    final RayCastCallback rayCallBack;
    final Vector2 startPoint;
    final World world;

    public ParticleEmitterBox2D(World world) {
        this.startPoint = new Vector2();
        this.endPoint = new Vector2();
        this.rayCallBack = new RayCastCallback() { // from class: com.badlogic.gdx.physics.box2d.graphics.ParticleEmitterBox2D.1
            @Override // com.badlogic.gdx.physics.box2d.RayCastCallback
            public float reportRayFixture(Fixture fixture, Vector2 vector2, Vector2 vector3, float f) {
                ParticleEmitterBox2D.this.particleCollided = true;
                ParticleEmitterBox2D.this.normalAngle = MathUtils.atan2(vector3.y, vector3.x) * 57.295776f;
                return f;
            }
        };
        this.world = world;
    }

    public ParticleEmitterBox2D(World world, BufferedReader bufferedReader) throws IOException {
        super(bufferedReader);
        this.startPoint = new Vector2();
        this.endPoint = new Vector2();
        this.rayCallBack = new RayCastCallback() { // from class: com.badlogic.gdx.physics.box2d.graphics.ParticleEmitterBox2D.1
            @Override // com.badlogic.gdx.physics.box2d.RayCastCallback
            public float reportRayFixture(Fixture fixture, Vector2 vector2, Vector2 vector3, float f) {
                ParticleEmitterBox2D.this.particleCollided = true;
                ParticleEmitterBox2D.this.normalAngle = MathUtils.atan2(vector3.y, vector3.x) * 57.295776f;
                return f;
            }
        };
        this.world = world;
    }

    public ParticleEmitterBox2D(World world, ParticleEmitter particleEmitter) {
        super(particleEmitter);
        this.startPoint = new Vector2();
        this.endPoint = new Vector2();
        this.rayCallBack = new RayCastCallback() { // from class: com.badlogic.gdx.physics.box2d.graphics.ParticleEmitterBox2D.1
            @Override // com.badlogic.gdx.physics.box2d.RayCastCallback
            public float reportRayFixture(Fixture fixture, Vector2 vector2, Vector2 vector3, float f) {
                ParticleEmitterBox2D.this.particleCollided = true;
                ParticleEmitterBox2D.this.normalAngle = MathUtils.atan2(vector3.y, vector3.x) * 57.295776f;
                return f;
            }
        };
        this.world = world;
    }

    @Override // com.badlogic.gdx.graphics.g2d.ParticleEmitter
    protected ParticleEmitter.Particle newParticle(Sprite sprite) {
        return new ParticleBox2D(sprite);
    }

    private class ParticleBox2D extends ParticleEmitter.Particle {
        public ParticleBox2D(Sprite sprite) {
            super(sprite);
        }

        @Override // com.badlogic.gdx.graphics.g2d.Sprite
        public void translate(float f, float f2) {
            if ((f * f) + (f2 * f2) < ParticleEmitterBox2D.EPSILON) {
                return;
            }
            float x = getX() + (getWidth() / 2.0f);
            float y = getY() + (getHeight() / 2.0f);
            ParticleEmitterBox2D.this.particleCollided = false;
            ParticleEmitterBox2D.this.startPoint.set(x, y);
            ParticleEmitterBox2D.this.endPoint.set(x + f, y + f2);
            if (ParticleEmitterBox2D.this.world != null) {
                ParticleEmitterBox2D.this.world.rayCast(ParticleEmitterBox2D.this.rayCallBack, ParticleEmitterBox2D.this.startPoint, ParticleEmitterBox2D.this.endPoint);
            }
            if (ParticleEmitterBox2D.this.particleCollided) {
                this.angle = ((ParticleEmitterBox2D.this.normalAngle * 2.0f) - this.angle) - 180.0f;
                this.angleCos = MathUtils.cosDeg(this.angle);
                this.angleSin = MathUtils.sinDeg(this.angle);
                f *= this.angleCos;
                f2 *= this.angleSin;
            }
            super.translate(f, f2);
        }
    }
}

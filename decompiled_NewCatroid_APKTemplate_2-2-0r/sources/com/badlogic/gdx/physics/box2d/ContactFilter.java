package com.badlogic.gdx.physics.box2d;

/* JADX INFO: loaded from: classes8.dex */
public interface ContactFilter {
    boolean shouldCollide(Fixture fixture, Fixture fixture2);
}

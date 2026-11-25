package com.badlogic.gdx.physics.bullet.extras;

import com.badlogic.gdx.physics.bullet.BulletBase;
import java.nio.IntBuffer;

/* JADX INFO: loaded from: classes8.dex */
public class User2InternalIndex extends BulletBase {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private long swigCPtr;

    protected User2InternalIndex(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public User2InternalIndex(long j, boolean z) {
        this("User2InternalIndex", j, z);
        construct();
    }

    @Override // com.badlogic.gdx.physics.bullet.BulletBase
    protected void reset(long j, boolean z) {
        if (!this.destroyed) {
            destroy();
        }
        this.swigCPtr = j;
        super.reset(j, z);
    }

    public static long getCPtr(User2InternalIndex user2InternalIndex) {
        if (user2InternalIndex == null) {
            return 0L;
        }
        return user2InternalIndex.swigCPtr;
    }

    @Override // com.badlogic.gdx.physics.bullet.BulletBase
    protected void finalize() throws Throwable {
        if (!this.destroyed) {
            destroy();
        }
        super.finalize();
    }

    @Override // com.badlogic.gdx.physics.bullet.BulletBase
    protected synchronized void delete() {
        if (this.swigCPtr != 0) {
            if (this.swigCMemOwn) {
                this.swigCMemOwn = false;
                ExtrasJNI.delete_User2InternalIndex(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public User2InternalIndex() {
        this(ExtrasJNI.new_User2InternalIndex(), true);
    }

    public void addBody(int i, int i2) {
        ExtrasJNI.User2InternalIndex_addBody(this.swigCPtr, this, i, i2);
    }

    public int buildMapping() {
        return ExtrasJNI.User2InternalIndex_buildMapping(this.swigCPtr, this);
    }

    public int user2internal(int i, IntBuffer intBuffer) {
        return ExtrasJNI.User2InternalIndex_user2internal(this.swigCPtr, this, i, intBuffer);
    }

    public int internal2user(int i, IntBuffer intBuffer) {
        return ExtrasJNI.User2InternalIndex_internal2user(this.swigCPtr, this, i, intBuffer);
    }
}

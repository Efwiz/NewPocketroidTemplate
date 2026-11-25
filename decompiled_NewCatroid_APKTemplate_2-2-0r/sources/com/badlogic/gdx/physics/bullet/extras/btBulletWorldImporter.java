package com.badlogic.gdx.physics.bullet.extras;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.physics.bullet.dynamics.btDynamicsWorld;
import com.badlogic.gdx.utils.BufferUtils;
import com.badlogic.gdx.utils.GdxRuntimeException;
import java.nio.ByteBuffer;

/* JADX INFO: loaded from: classes8.dex */
public class btBulletWorldImporter extends btWorldImporter {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private long swigCPtr;

    protected btBulletWorldImporter(String str, long j, boolean z) {
        super(str, ExtrasJNI.btBulletWorldImporter_SWIGUpcast(j), z);
        this.swigCPtr = j;
    }

    public btBulletWorldImporter(long j, boolean z) {
        this("btBulletWorldImporter", j, z);
        construct();
    }

    @Override // com.badlogic.gdx.physics.bullet.extras.btWorldImporter, com.badlogic.gdx.physics.bullet.BulletBase
    protected void reset(long j, boolean z) {
        if (!this.destroyed) {
            destroy();
        }
        this.swigCPtr = j;
        super.reset(ExtrasJNI.btBulletWorldImporter_SWIGUpcast(j), z);
    }

    public static long getCPtr(btBulletWorldImporter btbulletworldimporter) {
        if (btbulletworldimporter == null) {
            return 0L;
        }
        return btbulletworldimporter.swigCPtr;
    }

    @Override // com.badlogic.gdx.physics.bullet.extras.btWorldImporter, com.badlogic.gdx.physics.bullet.BulletBase
    protected void finalize() throws Throwable {
        if (!this.destroyed) {
            destroy();
        }
        super.finalize();
    }

    @Override // com.badlogic.gdx.physics.bullet.extras.btWorldImporter, com.badlogic.gdx.physics.bullet.BulletBase
    protected synchronized void delete() {
        if (this.swigCPtr != 0) {
            if (this.swigCMemOwn) {
                this.swigCMemOwn = false;
                ExtrasJNI.delete_btBulletWorldImporter(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    protected void swigDirectorDisconnect() {
        this.swigCMemOwn = false;
        delete();
    }

    public void swigReleaseOwnership() {
        this.swigCMemOwn = false;
        ExtrasJNI.btBulletWorldImporter_change_ownership(this, this.swigCPtr, false);
    }

    public void swigTakeOwnership() {
        this.swigCMemOwn = true;
        ExtrasJNI.btBulletWorldImporter_change_ownership(this, this.swigCPtr, true);
    }

    public boolean loadFile(FileHandle fileHandle) {
        int length = (int) fileHandle.length();
        if (length <= 0) {
            throw new GdxRuntimeException("Incorrect file specified");
        }
        ByteBuffer byteBufferNewUnsafeByteBuffer = BufferUtils.newUnsafeByteBuffer(length);
        byteBufferNewUnsafeByteBuffer.put(fileHandle.readBytes());
        byteBufferNewUnsafeByteBuffer.position(0);
        boolean zLoadFileFromMemory = loadFileFromMemory(byteBufferNewUnsafeByteBuffer, length);
        BufferUtils.disposeUnsafeByteBuffer(byteBufferNewUnsafeByteBuffer);
        return zLoadFileFromMemory;
    }

    public btBulletWorldImporter(btDynamicsWorld btdynamicsworld) {
        this(ExtrasJNI.new_btBulletWorldImporter__SWIG_0(btDynamicsWorld.getCPtr(btdynamicsworld), btdynamicsworld), true);
        ExtrasJNI.btBulletWorldImporter_director_connect(this, this.swigCPtr, this.swigCMemOwn, true);
    }

    public btBulletWorldImporter() {
        this(ExtrasJNI.new_btBulletWorldImporter__SWIG_1(), true);
        ExtrasJNI.btBulletWorldImporter_director_connect(this, this.swigCPtr, this.swigCMemOwn, true);
    }

    public boolean loadFile(String str, String str2) {
        return ExtrasJNI.btBulletWorldImporter_loadFile__SWIG_0(this.swigCPtr, this, str, str2);
    }

    public boolean loadFile(String str) {
        return ExtrasJNI.btBulletWorldImporter_loadFile__SWIG_1(this.swigCPtr, this, str);
    }

    public boolean loadFileFromMemory(SWIGTYPE_p_bParse__btBulletFile sWIGTYPE_p_bParse__btBulletFile) {
        return ExtrasJNI.btBulletWorldImporter_loadFileFromMemory__SWIG_0(this.swigCPtr, this, SWIGTYPE_p_bParse__btBulletFile.getCPtr(sWIGTYPE_p_bParse__btBulletFile));
    }

    public boolean convertAllObjects(SWIGTYPE_p_bParse__btBulletFile sWIGTYPE_p_bParse__btBulletFile) {
        if (getClass() == btBulletWorldImporter.class) {
            return ExtrasJNI.btBulletWorldImporter_convertAllObjects(this.swigCPtr, this, SWIGTYPE_p_bParse__btBulletFile.getCPtr(sWIGTYPE_p_bParse__btBulletFile));
        }
        return ExtrasJNI.btBulletWorldImporter_convertAllObjectsSwigExplicitbtBulletWorldImporter(this.swigCPtr, this, SWIGTYPE_p_bParse__btBulletFile.getCPtr(sWIGTYPE_p_bParse__btBulletFile));
    }

    public boolean loadFileFromMemory(ByteBuffer byteBuffer, int i) {
        return ExtrasJNI.btBulletWorldImporter_loadFileFromMemory__SWIG_1(this.swigCPtr, this, byteBuffer, i);
    }
}

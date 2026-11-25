package com.badlogic.gdx.files;

import com.badlogic.gdx.Files;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/files/FileHandleStream.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public abstract class FileHandleStream extends FileHandle {
    @Override // com.badlogic.gdx.files.FileHandle
    public boolean exists() {
        return true;
    }

    @Override // com.badlogic.gdx.files.FileHandle
    public boolean isDirectory() {
        return false;
    }

    @Override // com.badlogic.gdx.files.FileHandle
    public long length() {
        return 0L;
    }

    public FileHandleStream(String str) {
        super(new File(str), Files.FileType.Absolute);
    }

    @Override // com.badlogic.gdx.files.FileHandle
    public FileHandle child(String str) {
        throw new UnsupportedOperationException();
    }

    @Override // com.badlogic.gdx.files.FileHandle
    public FileHandle sibling(String str) {
        throw new UnsupportedOperationException();
    }

    @Override // com.badlogic.gdx.files.FileHandle
    public FileHandle parent() {
        throw new UnsupportedOperationException();
    }

    @Override // com.badlogic.gdx.files.FileHandle
    public InputStream read() {
        throw new UnsupportedOperationException();
    }

    @Override // com.badlogic.gdx.files.FileHandle
    public OutputStream write(boolean z) {
        throw new UnsupportedOperationException();
    }

    @Override // com.badlogic.gdx.files.FileHandle
    public FileHandle[] list() {
        throw new UnsupportedOperationException();
    }

    @Override // com.badlogic.gdx.files.FileHandle
    public void mkdirs() {
        throw new UnsupportedOperationException();
    }

    @Override // com.badlogic.gdx.files.FileHandle
    public boolean delete() {
        throw new UnsupportedOperationException();
    }

    @Override // com.badlogic.gdx.files.FileHandle
    public boolean deleteDirectory() {
        throw new UnsupportedOperationException();
    }

    @Override // com.badlogic.gdx.files.FileHandle
    public void copyTo(FileHandle fileHandle) {
        throw new UnsupportedOperationException();
    }

    @Override // com.badlogic.gdx.files.FileHandle
    public void moveTo(FileHandle fileHandle) {
        throw new UnsupportedOperationException();
    }

    @Override // com.badlogic.gdx.files.FileHandle
    public void emptyDirectory() {
        throw new UnsupportedOperationException();
    }

    @Override // com.badlogic.gdx.files.FileHandle
    public void emptyDirectory(boolean z) {
        throw new UnsupportedOperationException();
    }
}

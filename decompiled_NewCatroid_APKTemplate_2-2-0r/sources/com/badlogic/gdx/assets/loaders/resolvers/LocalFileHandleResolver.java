package com.badlogic.gdx.assets.loaders.resolvers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.files.FileHandle;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/assets/loaders/resolvers/LocalFileHandleResolver.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public class LocalFileHandleResolver implements FileHandleResolver {
    @Override // com.badlogic.gdx.assets.loaders.FileHandleResolver
    public FileHandle resolve(String str) {
        return Gdx.files.local(str);
    }
}

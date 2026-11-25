package com.badlogic.gdx.assets.loaders.resolvers;

import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.files.FileHandle;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/assets/loaders/resolvers/PrefixFileHandleResolver.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public class PrefixFileHandleResolver implements FileHandleResolver {
    private FileHandleResolver baseResolver;
    private String prefix;

    public PrefixFileHandleResolver(FileHandleResolver fileHandleResolver, String str) {
        this.baseResolver = fileHandleResolver;
        this.prefix = str;
    }

    public void setBaseResolver(FileHandleResolver fileHandleResolver) {
        this.baseResolver = fileHandleResolver;
    }

    public FileHandleResolver getBaseResolver() {
        return this.baseResolver;
    }

    public void setPrefix(String str) {
        this.prefix = str;
    }

    public String getPrefix() {
        return this.prefix;
    }

    @Override // com.badlogic.gdx.assets.loaders.FileHandleResolver
    public FileHandle resolve(String str) {
        return this.baseResolver.resolve(this.prefix + str);
    }
}

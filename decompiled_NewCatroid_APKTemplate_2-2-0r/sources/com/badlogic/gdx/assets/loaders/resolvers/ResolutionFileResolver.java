package com.badlogic.gdx.assets.loaders.resolvers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.files.FileHandle;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/assets/loaders/resolvers/ResolutionFileResolver.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public class ResolutionFileResolver implements FileHandleResolver {
    protected final FileHandleResolver baseResolver;
    protected final Resolution[] descriptors;

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/assets/loaders/resolvers/ResolutionFileResolver$Resolution.class
      classes8.dex
     */
    public static class Resolution {
        public final String folder;
        public final int portraitHeight;
        public final int portraitWidth;

        public Resolution(int i, int i2, String str) {
            this.portraitWidth = i;
            this.portraitHeight = i2;
            this.folder = str;
        }
    }

    public ResolutionFileResolver(FileHandleResolver fileHandleResolver, Resolution... resolutionArr) {
        if (resolutionArr.length == 0) {
            throw new IllegalArgumentException("At least one Resolution needs to be supplied.");
        }
        this.baseResolver = fileHandleResolver;
        this.descriptors = resolutionArr;
    }

    @Override // com.badlogic.gdx.assets.loaders.FileHandleResolver
    public FileHandle resolve(String str) {
        FileHandle fileHandleResolve = this.baseResolver.resolve(resolve(new FileHandle(str), choose(this.descriptors).folder));
        return !fileHandleResolve.exists() ? this.baseResolver.resolve(str) : fileHandleResolve;
    }

    protected String resolve(FileHandle fileHandle, String str) {
        FileHandle fileHandleParent = fileHandle.parent();
        String str2 = "";
        if (fileHandleParent != null && !fileHandleParent.name().equals("")) {
            str2 = fileHandleParent + "/";
        }
        return str2 + str + "/" + fileHandle.name();
    }

    public static Resolution choose(Resolution... resolutionArr) {
        int backBufferWidth = Gdx.graphics.getBackBufferWidth();
        int backBufferHeight = Gdx.graphics.getBackBufferHeight();
        int i = 0;
        Resolution resolution = resolutionArr[0];
        if (backBufferWidth < backBufferHeight) {
            int length = resolutionArr.length;
            while (i < length) {
                Resolution resolution2 = resolutionArr[i];
                if (backBufferWidth >= resolution2.portraitWidth && resolution2.portraitWidth >= resolution.portraitWidth && backBufferHeight >= resolution2.portraitHeight && resolution2.portraitHeight >= resolution.portraitHeight) {
                    resolution = resolutionArr[i];
                }
                i++;
            }
        } else {
            int length2 = resolutionArr.length;
            while (i < length2) {
                Resolution resolution3 = resolutionArr[i];
                if (backBufferWidth >= resolution3.portraitHeight && resolution3.portraitHeight >= resolution.portraitHeight && backBufferHeight >= resolution3.portraitWidth && resolution3.portraitWidth >= resolution.portraitWidth) {
                    resolution = resolutionArr[i];
                }
                i++;
            }
        }
        return resolution;
    }
}

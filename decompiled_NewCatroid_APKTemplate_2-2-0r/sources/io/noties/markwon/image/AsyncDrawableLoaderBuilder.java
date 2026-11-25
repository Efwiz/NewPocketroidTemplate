package io.noties.markwon.image;

import io.noties.markwon.image.data.DataUriSchemeHandler;
import io.noties.markwon.image.gif.GifMediaDecoder;
import io.noties.markwon.image.gif.GifSupport;
import io.noties.markwon.image.network.NetworkSchemeHandler;
import io.noties.markwon.image.svg.SvgMediaDecoder;
import io.noties.markwon.image.svg.SvgSupport;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/* JADX INFO: loaded from: classes11.dex */
class AsyncDrawableLoaderBuilder {
    MediaDecoder defaultMediaDecoder;
    ImagesPlugin.ErrorHandler errorHandler;
    ExecutorService executorService;
    boolean isBuilt;
    ImagesPlugin.PlaceholderProvider placeholderProvider;
    final Map<String, SchemeHandler> schemeHandlers = new HashMap(3);
    final Map<String, MediaDecoder> mediaDecoders = new HashMap(3);

    AsyncDrawableLoaderBuilder() {
        addSchemeHandler(DataUriSchemeHandler.create());
        addSchemeHandler(NetworkSchemeHandler.create());
        if (SvgSupport.hasSvgSupport()) {
            addMediaDecoder(SvgMediaDecoder.create());
        }
        if (GifSupport.hasGifSupport()) {
            addMediaDecoder(GifMediaDecoder.create());
        }
        this.defaultMediaDecoder = DefaultMediaDecoder.create();
    }

    void executorService(ExecutorService executorService) {
        checkState();
        this.executorService = executorService;
    }

    void addSchemeHandler(SchemeHandler schemeHandler) {
        checkState();
        Iterator<String> it = schemeHandler.supportedSchemes().iterator();
        while (it.hasNext()) {
            this.schemeHandlers.put(it.next(), schemeHandler);
        }
    }

    void addMediaDecoder(MediaDecoder mediaDecoder) {
        checkState();
        Iterator<String> it = mediaDecoder.supportedTypes().iterator();
        while (it.hasNext()) {
            this.mediaDecoders.put(it.next(), mediaDecoder);
        }
    }

    void defaultMediaDecoder(MediaDecoder mediaDecoder) {
        checkState();
        this.defaultMediaDecoder = mediaDecoder;
    }

    void removeSchemeHandler(String str) {
        checkState();
        this.schemeHandlers.remove(str);
    }

    void removeMediaDecoder(String str) {
        checkState();
        this.mediaDecoders.remove(str);
    }

    void placeholderProvider(ImagesPlugin.PlaceholderProvider placeholderProvider) {
        checkState();
        this.placeholderProvider = placeholderProvider;
    }

    void errorHandler(ImagesPlugin.ErrorHandler errorHandler) {
        checkState();
        this.errorHandler = errorHandler;
    }

    AsyncDrawableLoader build() {
        checkState();
        this.isBuilt = true;
        if (this.executorService == null) {
            this.executorService = Executors.newCachedThreadPool();
        }
        return new AsyncDrawableLoaderImpl(this);
    }

    private void checkState() {
        if (this.isBuilt) {
            throw new IllegalStateException("ImagesPlugin has already been configured and cannot be modified any further");
        }
    }
}

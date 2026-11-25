package com.badlogic.gdx.assets;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.loaders.AssetLoader;
import com.badlogic.gdx.assets.loaders.BitmapFontLoader;
import com.badlogic.gdx.assets.loaders.CubemapLoader;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.I18NBundleLoader;
import com.badlogic.gdx.assets.loaders.MusicLoader;
import com.badlogic.gdx.assets.loaders.ParticleEffectLoader;
import com.badlogic.gdx.assets.loaders.PixmapLoader;
import com.badlogic.gdx.assets.loaders.ShaderProgramLoader;
import com.badlogic.gdx.assets.loaders.SkinLoader;
import com.badlogic.gdx.assets.loaders.SoundLoader;
import com.badlogic.gdx.assets.loaders.TextureAtlasLoader;
import com.badlogic.gdx.assets.loaders.TextureLoader;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Cubemap;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.PolygonRegion;
import com.badlogic.gdx.graphics.g2d.PolygonRegionLoader;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.loader.G3dModelLoader;
import com.badlogic.gdx.graphics.g3d.loader.ObjLoader;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.I18NBundle;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.ObjectIntMap;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.ObjectSet;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.UBJsonReader;
import com.badlogic.gdx.utils.async.AsyncExecutor;
import com.badlogic.gdx.utils.async.ThreadUtils;
import com.badlogic.gdx.utils.reflect.ClassReflection;
import com.huawei.hms.ml.language.common.utils.Constant;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/assets/AssetManager.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public class AssetManager implements Disposable {
    final ObjectMap<String, Array<String>> assetDependencies;
    final ObjectMap<String, Class> assetTypes;
    final ObjectMap<Class, ObjectMap<String, RefCountedContainer>> assets;
    final AsyncExecutor executor;
    final ObjectSet<String> injected;
    AssetErrorListener listener;
    final Array<AssetDescriptor> loadQueue;
    int loaded;
    final ObjectMap<Class, ObjectMap<String, AssetLoader>> loaders;
    Logger log;
    int peakTasks;
    final FileHandleResolver resolver;
    final Array<AssetLoadingTask> tasks;
    int toLoad;

    public AssetManager() {
        this(new InternalFileHandleResolver());
    }

    public AssetManager(FileHandleResolver fileHandleResolver) {
        this(fileHandleResolver, true);
    }

    public AssetManager(FileHandleResolver fileHandleResolver, boolean z) {
        this.assets = new ObjectMap<>();
        this.assetTypes = new ObjectMap<>();
        this.assetDependencies = new ObjectMap<>();
        this.injected = new ObjectSet<>();
        this.loaders = new ObjectMap<>();
        this.loadQueue = new Array<>();
        this.tasks = new Array<>();
        this.log = new Logger("AssetManager", 0);
        this.resolver = fileHandleResolver;
        if (z) {
            setLoader(BitmapFont.class, new BitmapFontLoader(fileHandleResolver));
            setLoader(Music.class, new MusicLoader(fileHandleResolver));
            setLoader(Pixmap.class, new PixmapLoader(fileHandleResolver));
            setLoader(Sound.class, new SoundLoader(fileHandleResolver));
            setLoader(TextureAtlas.class, new TextureAtlasLoader(fileHandleResolver));
            setLoader(Texture.class, new TextureLoader(fileHandleResolver));
            setLoader(Skin.class, new SkinLoader(fileHandleResolver));
            setLoader(ParticleEffect.class, new ParticleEffectLoader(fileHandleResolver));
            setLoader(com.badlogic.gdx.graphics.g3d.particles.ParticleEffect.class, new com.badlogic.gdx.graphics.g3d.particles.ParticleEffectLoader(fileHandleResolver));
            setLoader(PolygonRegion.class, new PolygonRegionLoader(fileHandleResolver));
            setLoader(I18NBundle.class, new I18NBundleLoader(fileHandleResolver));
            setLoader(Model.class, ".g3dj", new G3dModelLoader(new JsonReader(), fileHandleResolver));
            setLoader(Model.class, ".g3db", new G3dModelLoader(new UBJsonReader(), fileHandleResolver));
            setLoader(Model.class, ".obj", new ObjLoader(fileHandleResolver));
            setLoader(ShaderProgram.class, new ShaderProgramLoader(fileHandleResolver));
            setLoader(Cubemap.class, new CubemapLoader(fileHandleResolver));
        }
        this.executor = new AsyncExecutor(1, "AssetManager");
    }

    public FileHandleResolver getFileHandleResolver() {
        return this.resolver;
    }

    public synchronized <T> T get(String str) {
        return (T) get(str, true);
    }

    public synchronized <T> T get(String str, Class<T> cls) {
        return (T) get(str, cls, true);
    }

    public synchronized <T> T get(String str, boolean z) {
        ObjectMap<String, RefCountedContainer> objectMap;
        RefCountedContainer refCountedContainer;
        Class cls = this.assetTypes.get(str);
        if (cls != null && (objectMap = this.assets.get(cls)) != null && (refCountedContainer = objectMap.get(str)) != null) {
            return (T) refCountedContainer.object;
        }
        if (z) {
            throw new GdxRuntimeException("Asset not loaded: " + str);
        }
        return null;
    }

    public synchronized <T> T get(String str, Class<T> cls, boolean z) {
        RefCountedContainer refCountedContainer;
        ObjectMap<String, RefCountedContainer> objectMap = this.assets.get(cls);
        if (objectMap != null && (refCountedContainer = objectMap.get(str)) != null) {
            return (T) refCountedContainer.object;
        }
        if (z) {
            throw new GdxRuntimeException("Asset not loaded: " + str);
        }
        return null;
    }

    public synchronized <T> T get(AssetDescriptor<T> assetDescriptor) {
        return (T) get(assetDescriptor.fileName, assetDescriptor.type, true);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public synchronized <T> Array<T> getAll(Class<T> cls, Array<T> array) {
        ObjectMap<String, RefCountedContainer> objectMap = this.assets.get(cls);
        if (objectMap != null) {
            ObjectMap.Values<RefCountedContainer> it = objectMap.values().iterator();
            while (it.hasNext()) {
                array.add(it.next().object);
            }
        }
        return array;
    }

    public synchronized boolean contains(String str) {
        if (this.tasks.size > 0 && this.tasks.first().assetDesc.fileName.equals(str)) {
            return true;
        }
        for (int i = 0; i < this.loadQueue.size; i++) {
            if (this.loadQueue.get(i).fileName.equals(str)) {
                return true;
            }
        }
        return isLoaded(str);
    }

    public synchronized boolean contains(String str, Class cls) {
        if (this.tasks.size > 0) {
            AssetDescriptor assetDescriptor = this.tasks.first().assetDesc;
            if (assetDescriptor.type == cls && assetDescriptor.fileName.equals(str)) {
                return true;
            }
        }
        for (int i = 0; i < this.loadQueue.size; i++) {
            AssetDescriptor assetDescriptor2 = this.loadQueue.get(i);
            if (assetDescriptor2.type == cls && assetDescriptor2.fileName.equals(str)) {
                return true;
            }
        }
        return isLoaded(str, cls);
    }

    public synchronized void unload(String str) {
        if (this.tasks.size > 0) {
            AssetLoadingTask assetLoadingTaskFirst = this.tasks.first();
            if (assetLoadingTaskFirst.assetDesc.fileName.equals(str)) {
                this.log.info("Unload (from tasks): " + str);
                assetLoadingTaskFirst.cancel = true;
                assetLoadingTaskFirst.unload();
                return;
            }
        }
        Class cls = this.assetTypes.get(str);
        int i = 0;
        while (true) {
            if (i >= this.loadQueue.size) {
                i = -1;
                break;
            } else if (this.loadQueue.get(i).fileName.equals(str)) {
                break;
            } else {
                i++;
            }
        }
        if (i != -1) {
            this.toLoad--;
            AssetDescriptor assetDescriptorRemoveIndex = this.loadQueue.removeIndex(i);
            this.log.info("Unload (from queue): " + str);
            if (cls != null && assetDescriptorRemoveIndex.params != null && assetDescriptorRemoveIndex.params.loadedCallback != null) {
                assetDescriptorRemoveIndex.params.loadedCallback.finishedLoading(this, assetDescriptorRemoveIndex.fileName, assetDescriptorRemoveIndex.type);
            }
            return;
        }
        if (cls == null) {
            throw new GdxRuntimeException("Asset not loaded: " + str);
        }
        RefCountedContainer refCountedContainer = this.assets.get(cls).get(str);
        refCountedContainer.refCount--;
        if (refCountedContainer.refCount <= 0) {
            this.log.info("Unload (dispose): " + str);
            if (refCountedContainer.object instanceof Disposable) {
                ((Disposable) refCountedContainer.object).dispose();
            }
            this.assetTypes.remove(str);
            this.assets.get(cls).remove(str);
        } else {
            this.log.info("Unload (decrement): " + str);
        }
        Array<String> array = this.assetDependencies.get(str);
        if (array != null) {
            Array.ArrayIterator<String> it = array.iterator();
            while (it.hasNext()) {
                String next = it.next();
                if (isLoaded(next)) {
                    unload(next);
                }
            }
        }
        if (refCountedContainer.refCount <= 0) {
            this.assetDependencies.remove(str);
        }
    }

    public synchronized <T> boolean containsAsset(T t) {
        ObjectMap<String, RefCountedContainer> objectMap = this.assets.get(t.getClass());
        if (objectMap == null) {
            return false;
        }
        ObjectMap.Values<RefCountedContainer> it = objectMap.values().iterator();
        while (it.hasNext()) {
            RefCountedContainer next = it.next();
            if (next.object == t || t.equals(next.object)) {
                return true;
            }
        }
        return false;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public synchronized <T> String getAssetFileName(T t) {
        ObjectMap.Keys<Class> it = this.assets.keys().iterator();
        while (it.hasNext()) {
            ObjectMap.Entries<String, RefCountedContainer> it2 = this.assets.get(it.next()).iterator();
            while (it2.hasNext()) {
                ObjectMap.Entry next = it2.next();
                Object obj = ((RefCountedContainer) next.value).object;
                if (obj == t || t.equals(obj)) {
                    return (String) next.key;
                }
            }
        }
        return null;
    }

    public synchronized boolean isLoaded(AssetDescriptor assetDescriptor) {
        return isLoaded(assetDescriptor.fileName);
    }

    public synchronized boolean isLoaded(String str) {
        if (str == null) {
            return false;
        }
        return this.assetTypes.containsKey(str);
    }

    public synchronized boolean isLoaded(String str, Class cls) {
        ObjectMap<String, RefCountedContainer> objectMap = this.assets.get(cls);
        if (objectMap == null) {
            return false;
        }
        return objectMap.get(str) != null;
    }

    public <T> AssetLoader getLoader(Class<T> cls) {
        return getLoader(cls, null);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public <T> AssetLoader getLoader(Class<T> cls, String str) {
        ObjectMap<String, AssetLoader> objectMap = this.loaders.get(cls);
        AssetLoader assetLoader = null;
        if (objectMap != null && objectMap.size >= 1) {
            if (str == null) {
                return objectMap.get("");
            }
            ObjectMap.Entries<String, AssetLoader> it = objectMap.entries().iterator();
            int length = -1;
            while (it.hasNext()) {
                ObjectMap.Entry next = it.next();
                if (((String) next.key).length() > length && str.endsWith((String) next.key)) {
                    assetLoader = (AssetLoader) next.value;
                    length = ((String) next.key).length();
                }
            }
        }
        return assetLoader;
    }

    public synchronized <T> void load(String str, Class<T> cls) {
        load(str, cls, null);
    }

    public synchronized <T> void load(String str, Class<T> cls, AssetLoaderParameters<T> assetLoaderParameters) {
        if (getLoader(cls, str) == null) {
            throw new GdxRuntimeException("No loader for type: " + ClassReflection.getSimpleName(cls));
        }
        if (this.loadQueue.size == 0) {
            this.loaded = 0;
            this.toLoad = 0;
            this.peakTasks = 0;
        }
        for (int i = 0; i < this.loadQueue.size; i++) {
            AssetDescriptor assetDescriptor = this.loadQueue.get(i);
            if (assetDescriptor.fileName.equals(str) && !assetDescriptor.type.equals(cls)) {
                throw new GdxRuntimeException("Asset with name '" + str + "' already in preload queue, but has different type (expected: " + ClassReflection.getSimpleName(cls) + ", found: " + ClassReflection.getSimpleName(assetDescriptor.type) + Constant.AFTER_QUTO);
            }
        }
        for (int i2 = 0; i2 < this.tasks.size; i2++) {
            AssetDescriptor assetDescriptor2 = this.tasks.get(i2).assetDesc;
            if (assetDescriptor2.fileName.equals(str) && !assetDescriptor2.type.equals(cls)) {
                throw new GdxRuntimeException("Asset with name '" + str + "' already in task list, but has different type (expected: " + ClassReflection.getSimpleName(cls) + ", found: " + ClassReflection.getSimpleName(assetDescriptor2.type) + Constant.AFTER_QUTO);
            }
        }
        Class cls2 = this.assetTypes.get(str);
        if (cls2 != null && !cls2.equals(cls)) {
            throw new GdxRuntimeException("Asset with name '" + str + "' already loaded, but has different type (expected: " + ClassReflection.getSimpleName(cls) + ", found: " + ClassReflection.getSimpleName(cls2) + Constant.AFTER_QUTO);
        }
        this.toLoad++;
        AssetDescriptor assetDescriptor3 = new AssetDescriptor(str, cls, assetLoaderParameters);
        this.loadQueue.add(assetDescriptor3);
        this.log.debug("Queued: " + assetDescriptor3);
    }

    public synchronized void load(AssetDescriptor assetDescriptor) {
        load(assetDescriptor.fileName, assetDescriptor.type, assetDescriptor.params);
    }

    public synchronized boolean update() {
        boolean z = false;
        try {
            if (this.tasks.size == 0) {
                while (this.loadQueue.size != 0 && this.tasks.size == 0) {
                    nextTask();
                }
                if (this.tasks.size == 0) {
                    return true;
                }
            }
            if (updateTask() && this.loadQueue.size == 0 && this.tasks.size == 0) {
                z = true;
            }
            return z;
        } catch (Throwable th) {
            handleTaskError(th);
            return this.loadQueue.size == 0;
        }
    }

    public boolean update(int i) {
        boolean zUpdate;
        if (Gdx.f82app.getType() == Application.ApplicationType.WebGL) {
            return update();
        }
        long jMillis = TimeUtils.millis() + ((long) i);
        while (true) {
            zUpdate = update();
            if (zUpdate || TimeUtils.millis() > jMillis) {
                break;
            }
            ThreadUtils.yield();
        }
        return zUpdate;
    }

    public synchronized boolean isFinished() {
        return this.loadQueue.size == 0 && this.tasks.size == 0;
    }

    public void finishLoading() {
        this.log.debug("Waiting for loading to complete...");
        while (!update()) {
            ThreadUtils.yield();
        }
        this.log.debug("Loading complete.");
    }

    public <T> T finishLoadingAsset(AssetDescriptor assetDescriptor) {
        return (T) finishLoadingAsset(assetDescriptor.fileName);
    }

    public <T> T finishLoadingAsset(String str) {
        ObjectMap<String, RefCountedContainer> objectMap;
        RefCountedContainer refCountedContainer;
        this.log.debug("Waiting for asset to be loaded: " + str);
        while (true) {
            synchronized (this) {
                Class cls = this.assetTypes.get(str);
                if (cls != null && (objectMap = this.assets.get(cls)) != null && (refCountedContainer = objectMap.get(str)) != null) {
                    this.log.debug("Asset loaded: " + str);
                    return (T) refCountedContainer.object;
                }
                update();
            }
            ThreadUtils.yield();
        }
    }

    synchronized void injectDependencies(String str, Array<AssetDescriptor> array) {
        ObjectSet<String> objectSet = this.injected;
        Array.ArrayIterator<AssetDescriptor> it = array.iterator();
        while (it.hasNext()) {
            AssetDescriptor next = it.next();
            if (!objectSet.contains(next.fileName)) {
                objectSet.add(next.fileName);
                injectDependency(str, next);
            }
        }
        objectSet.clear(32);
    }

    private synchronized void injectDependency(String str, AssetDescriptor assetDescriptor) {
        Array<String> array = this.assetDependencies.get(str);
        if (array == null) {
            array = new Array<>();
            this.assetDependencies.put(str, array);
        }
        array.add(assetDescriptor.fileName);
        if (isLoaded(assetDescriptor.fileName)) {
            this.log.debug("Dependency already loaded: " + assetDescriptor);
            this.assets.get(this.assetTypes.get(assetDescriptor.fileName)).get(assetDescriptor.fileName).refCount++;
            incrementRefCountedDependencies(assetDescriptor.fileName);
        } else {
            this.log.info("Loading dependency: " + assetDescriptor);
            addTask(assetDescriptor);
        }
    }

    private void nextTask() {
        AssetDescriptor assetDescriptorRemoveIndex = this.loadQueue.removeIndex(0);
        if (isLoaded(assetDescriptorRemoveIndex.fileName)) {
            this.log.debug("Already loaded: " + assetDescriptorRemoveIndex);
            this.assets.get(this.assetTypes.get(assetDescriptorRemoveIndex.fileName)).get(assetDescriptorRemoveIndex.fileName).refCount++;
            incrementRefCountedDependencies(assetDescriptorRemoveIndex.fileName);
            if (assetDescriptorRemoveIndex.params != null && assetDescriptorRemoveIndex.params.loadedCallback != null) {
                assetDescriptorRemoveIndex.params.loadedCallback.finishedLoading(this, assetDescriptorRemoveIndex.fileName, assetDescriptorRemoveIndex.type);
            }
            this.loaded++;
            return;
        }
        this.log.info("Loading: " + assetDescriptorRemoveIndex);
        addTask(assetDescriptorRemoveIndex);
    }

    private void addTask(AssetDescriptor assetDescriptor) {
        AssetLoader loader = getLoader(assetDescriptor.type, assetDescriptor.fileName);
        if (loader == null) {
            throw new GdxRuntimeException("No loader for type: " + ClassReflection.getSimpleName(assetDescriptor.type));
        }
        this.tasks.add(new AssetLoadingTask(this, assetDescriptor, loader, this.executor));
        this.peakTasks++;
    }

    protected <T> void addAsset(String str, Class<T> cls, T t) {
        this.assetTypes.put(str, cls);
        ObjectMap<String, RefCountedContainer> objectMap = this.assets.get(cls);
        if (objectMap == null) {
            objectMap = new ObjectMap<>();
            this.assets.put(cls, objectMap);
        }
        RefCountedContainer refCountedContainer = new RefCountedContainer();
        refCountedContainer.object = t;
        objectMap.put(str, refCountedContainer);
    }

    private boolean updateTask() {
        AssetLoadingTask assetLoadingTaskPeek = this.tasks.peek();
        try {
            if (!assetLoadingTaskPeek.cancel && !assetLoadingTaskPeek.update()) {
                return false;
            }
        } catch (RuntimeException e) {
            assetLoadingTaskPeek.cancel = true;
            taskFailed(assetLoadingTaskPeek.assetDesc, e);
        }
        if (this.tasks.size == 1) {
            this.loaded++;
            this.peakTasks = 0;
        }
        this.tasks.pop();
        if (assetLoadingTaskPeek.cancel) {
            return true;
        }
        addAsset(assetLoadingTaskPeek.assetDesc.fileName, assetLoadingTaskPeek.assetDesc.type, assetLoadingTaskPeek.asset);
        if (assetLoadingTaskPeek.assetDesc.params != null && assetLoadingTaskPeek.assetDesc.params.loadedCallback != null) {
            assetLoadingTaskPeek.assetDesc.params.loadedCallback.finishedLoading(this, assetLoadingTaskPeek.assetDesc.fileName, assetLoadingTaskPeek.assetDesc.type);
        }
        this.log.debug("Loaded: " + ((TimeUtils.nanoTime() - assetLoadingTaskPeek.startTime) / 1000000.0f) + "ms " + assetLoadingTaskPeek.assetDesc);
        return true;
    }

    protected void taskFailed(AssetDescriptor assetDescriptor, RuntimeException runtimeException) {
        throw runtimeException;
    }

    private void incrementRefCountedDependencies(String str) {
        Array<String> array = this.assetDependencies.get(str);
        if (array == null) {
            return;
        }
        Array.ArrayIterator<String> it = array.iterator();
        while (it.hasNext()) {
            String next = it.next();
            this.assets.get(this.assetTypes.get(next)).get(next).refCount++;
            incrementRefCountedDependencies(next);
        }
    }

    private void handleTaskError(Throwable th) {
        this.log.error("Error loading asset.", th);
        if (this.tasks.isEmpty()) {
            throw new GdxRuntimeException(th);
        }
        AssetLoadingTask assetLoadingTaskPop = this.tasks.pop();
        AssetDescriptor assetDescriptor = assetLoadingTaskPop.assetDesc;
        if (assetLoadingTaskPop.dependenciesLoaded && assetLoadingTaskPop.dependencies != null) {
            Array.ArrayIterator<AssetDescriptor> it = assetLoadingTaskPop.dependencies.iterator();
            while (it.hasNext()) {
                unload(it.next().fileName);
            }
        }
        this.tasks.clear();
        AssetErrorListener assetErrorListener = this.listener;
        if (assetErrorListener != null) {
            assetErrorListener.error(assetDescriptor, th);
            return;
        }
        throw new GdxRuntimeException(th);
    }

    public synchronized <T, P extends AssetLoaderParameters<T>> void setLoader(Class<T> cls, AssetLoader<T, P> assetLoader) {
        setLoader(cls, null, assetLoader);
    }

    public synchronized <T, P extends AssetLoaderParameters<T>> void setLoader(Class<T> cls, String str, AssetLoader<T, P> assetLoader) {
        try {
            if (cls == null) {
                throw new IllegalArgumentException("type cannot be null.");
            }
            if (assetLoader == null) {
                throw new IllegalArgumentException("loader cannot be null.");
            }
            this.log.debug("Loader set: " + ClassReflection.getSimpleName(cls) + " -> " + ClassReflection.getSimpleName(assetLoader.getClass()));
            ObjectMap<String, AssetLoader> objectMap = this.loaders.get(cls);
            if (objectMap == null) {
                ObjectMap<Class, ObjectMap<String, AssetLoader>> objectMap2 = this.loaders;
                ObjectMap<String, AssetLoader> objectMap3 = new ObjectMap<>();
                objectMap2.put(cls, objectMap3);
                objectMap = objectMap3;
            }
            if (str == null) {
                str = "";
            }
            objectMap.put(str, assetLoader);
        } catch (Throwable th) {
            throw th;
        }
    }

    public synchronized int getLoadedAssets() {
        return this.assetTypes.size;
    }

    public synchronized int getQueuedAssets() {
        return this.loadQueue.size + this.tasks.size;
    }

    public synchronized float getProgress() {
        if (this.toLoad == 0) {
            return 1.0f;
        }
        float f = this.loaded;
        int i = this.peakTasks;
        if (i > 0) {
            f += (i - this.tasks.size) / this.peakTasks;
        }
        return Math.min(1.0f, f / this.toLoad);
    }

    public synchronized void setErrorListener(AssetErrorListener assetErrorListener) {
        this.listener = assetErrorListener;
    }

    @Override // com.badlogic.gdx.utils.Disposable
    public void dispose() {
        this.log.debug("Disposing.");
        clear();
        this.executor.dispose();
    }

    public void clear() {
        synchronized (this) {
            this.loadQueue.clear();
        }
        finishLoading();
        synchronized (this) {
            ObjectIntMap objectIntMap = new ObjectIntMap();
            while (this.assetTypes.size > 0) {
                objectIntMap.clear(51);
                Array<String> array = this.assetTypes.keys().toArray();
                Array.ArrayIterator<String> it = array.iterator();
                while (it.hasNext()) {
                    Array<String> array2 = this.assetDependencies.get(it.next());
                    if (array2 != null) {
                        Array.ArrayIterator<String> it2 = array2.iterator();
                        while (it2.hasNext()) {
                            objectIntMap.getAndIncrement(it2.next(), 0, 1);
                        }
                    }
                }
                Array.ArrayIterator<String> it3 = array.iterator();
                while (it3.hasNext()) {
                    String next = it3.next();
                    if (objectIntMap.get(next, 0) == 0) {
                        unload(next);
                    }
                }
            }
            this.assets.clear(51);
            this.assetTypes.clear(51);
            this.assetDependencies.clear(51);
            this.loaded = 0;
            this.toLoad = 0;
            this.peakTasks = 0;
            this.loadQueue.clear();
            this.tasks.clear();
        }
    }

    public Logger getLogger() {
        return this.log;
    }

    public void setLogger(Logger logger) {
        this.log = logger;
    }

    public synchronized int getReferenceCount(String str) {
        Class cls;
        cls = this.assetTypes.get(str);
        if (cls == null) {
            throw new GdxRuntimeException("Asset not loaded: " + str);
        }
        return this.assets.get(cls).get(str).refCount;
    }

    public synchronized void setReferenceCount(String str, int i) {
        Class cls = this.assetTypes.get(str);
        if (cls == null) {
            throw new GdxRuntimeException("Asset not loaded: " + str);
        }
        this.assets.get(cls).get(str).refCount = i;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public synchronized String getDiagnostics() {
        StringBuilder sb;
        sb = new StringBuilder(256);
        ObjectMap.Entries<String, Class> it = this.assetTypes.iterator();
        while (it.hasNext()) {
            ObjectMap.Entry next = it.next();
            String str = (String) next.key;
            Class cls = (Class) next.value;
            if (sb.length() > 0) {
                sb.append('\n');
            }
            sb.append(str);
            sb.append(", ");
            sb.append(ClassReflection.getSimpleName(cls));
            sb.append(", refs: ");
            sb.append(this.assets.get(cls).get(str).refCount);
            Array<String> array = this.assetDependencies.get(str);
            if (array != null) {
                sb.append(", deps: [");
                Array.ArrayIterator<String> it2 = array.iterator();
                while (it2.hasNext()) {
                    sb.append(it2.next());
                    sb.append(',');
                }
                sb.append(']');
            }
        }
        return sb.toString();
    }

    public synchronized Array<String> getAssetNames() {
        return this.assetTypes.keys().toArray();
    }

    public synchronized Array<String> getDependencies(String str) {
        return this.assetDependencies.get(str);
    }

    public synchronized Class getAssetType(String str) {
        return this.assetTypes.get(str);
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/assets/AssetManager$RefCountedContainer.class
      classes8.dex
     */
    static class RefCountedContainer {
        Object object;
        int refCount = 1;

        RefCountedContainer() {
        }
    }
}

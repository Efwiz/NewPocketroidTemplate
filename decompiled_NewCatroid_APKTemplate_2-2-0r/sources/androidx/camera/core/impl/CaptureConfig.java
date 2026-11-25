package androidx.camera.core.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/* JADX INFO: loaded from: classes.dex */
public final class CaptureConfig {
    final List<CameraCaptureCallback> mCameraCaptureCallbacks;
    final Config mImplementationOptions;
    final List<DeferrableSurface> mSurfaces;
    private final Object mTag;
    final int mTemplateType;
    private final boolean mUseRepeatingSurface;
    public static final Config.Option<Integer> OPTION_ROTATION = Config.Option.create("camerax.core.captureConfig.rotation", Integer.TYPE);
    public static final Config.Option<Integer> OPTION_JPEG_QUALITY = Config.Option.create("camerax.core.captureConfig.jpegQuality", Integer.class);

    public interface OptionUnpacker {
        void unpack(UseCaseConfig<?> useCaseConfig, Builder builder);
    }

    CaptureConfig(List<DeferrableSurface> list, Config config, int i, List<CameraCaptureCallback> list2, boolean z, Object obj) {
        this.mSurfaces = list;
        this.mImplementationOptions = config;
        this.mTemplateType = i;
        this.mCameraCaptureCallbacks = Collections.unmodifiableList(list2);
        this.mUseRepeatingSurface = z;
        this.mTag = obj;
    }

    public static CaptureConfig defaultEmptyCaptureConfig() {
        return new Builder().build();
    }

    public List<DeferrableSurface> getSurfaces() {
        return Collections.unmodifiableList(this.mSurfaces);
    }

    public Config getImplementationOptions() {
        return this.mImplementationOptions;
    }

    public int getTemplateType() {
        return this.mTemplateType;
    }

    public boolean isUseRepeatingSurface() {
        return this.mUseRepeatingSurface;
    }

    public List<CameraCaptureCallback> getCameraCaptureCallbacks() {
        return this.mCameraCaptureCallbacks;
    }

    public Object getTag() {
        return this.mTag;
    }

    public static final class Builder {
        private List<CameraCaptureCallback> mCameraCaptureCallbacks;
        private MutableConfig mImplementationOptions;
        private final Set<DeferrableSurface> mSurfaces;
        private Object mTag;
        private int mTemplateType;
        private boolean mUseRepeatingSurface;

        public Builder() {
            this.mSurfaces = new HashSet();
            this.mImplementationOptions = MutableOptionsBundle.create();
            this.mTemplateType = -1;
            this.mCameraCaptureCallbacks = new ArrayList();
            this.mUseRepeatingSurface = false;
            this.mTag = null;
        }

        private Builder(CaptureConfig captureConfig) {
            HashSet hashSet = new HashSet();
            this.mSurfaces = hashSet;
            this.mImplementationOptions = MutableOptionsBundle.create();
            this.mTemplateType = -1;
            this.mCameraCaptureCallbacks = new ArrayList();
            this.mUseRepeatingSurface = false;
            this.mTag = null;
            hashSet.addAll(captureConfig.mSurfaces);
            this.mImplementationOptions = MutableOptionsBundle.from(captureConfig.mImplementationOptions);
            this.mTemplateType = captureConfig.mTemplateType;
            this.mCameraCaptureCallbacks.addAll(captureConfig.getCameraCaptureCallbacks());
            this.mUseRepeatingSurface = captureConfig.isUseRepeatingSurface();
            this.mTag = captureConfig.getTag();
        }

        public static Builder createFrom(UseCaseConfig<?> useCaseConfig) {
            OptionUnpacker captureOptionUnpacker = useCaseConfig.getCaptureOptionUnpacker(null);
            if (captureOptionUnpacker == null) {
                throw new IllegalStateException("Implementation is missing option unpacker for " + useCaseConfig.getTargetName(useCaseConfig.toString()));
            }
            Builder builder = new Builder();
            captureOptionUnpacker.unpack(useCaseConfig, builder);
            return builder;
        }

        public static Builder from(CaptureConfig captureConfig) {
            return new Builder(captureConfig);
        }

        public int getTemplateType() {
            return this.mTemplateType;
        }

        public void setTemplateType(int i) {
            this.mTemplateType = i;
        }

        public void addCameraCaptureCallback(CameraCaptureCallback cameraCaptureCallback) {
            if (this.mCameraCaptureCallbacks.contains(cameraCaptureCallback)) {
                throw new IllegalArgumentException("duplicate camera capture callback");
            }
            this.mCameraCaptureCallbacks.add(cameraCaptureCallback);
        }

        public void addAllCameraCaptureCallbacks(Collection<CameraCaptureCallback> collection) {
            Iterator<CameraCaptureCallback> it = collection.iterator();
            while (it.hasNext()) {
                addCameraCaptureCallback(it.next());
            }
        }

        public void addSurface(DeferrableSurface deferrableSurface) {
            this.mSurfaces.add(deferrableSurface);
        }

        public void removeSurface(DeferrableSurface deferrableSurface) {
            this.mSurfaces.remove(deferrableSurface);
        }

        public void clearSurfaces() {
            this.mSurfaces.clear();
        }

        public Set<DeferrableSurface> getSurfaces() {
            return this.mSurfaces;
        }

        public void setImplementationOptions(Config config) {
            this.mImplementationOptions = MutableOptionsBundle.from(config);
        }

        public void addImplementationOptions(Config config) {
            for (Config.Option<?> option : config.listOptions()) {
                Object objRetrieveOption = this.mImplementationOptions.retrieveOption(option, null);
                Object objRetrieveOption2 = config.retrieveOption(option);
                if (objRetrieveOption instanceof MultiValueSet) {
                    ((MultiValueSet) objRetrieveOption).addAll(((MultiValueSet) objRetrieveOption2).getAllItems());
                } else {
                    if (objRetrieveOption2 instanceof MultiValueSet) {
                        objRetrieveOption2 = ((MultiValueSet) objRetrieveOption2).mo14clone();
                    }
                    this.mImplementationOptions.insertOption(option, config.getOptionPriority(option), objRetrieveOption2);
                }
            }
        }

        public <T> void addImplementationOption(Config.Option<T> option, T t) {
            this.mImplementationOptions.insertOption(option, t);
        }

        public Config getImplementationOptions() {
            return this.mImplementationOptions;
        }

        boolean isUseRepeatingSurface() {
            return this.mUseRepeatingSurface;
        }

        public void setUseRepeatingSurface(boolean z) {
            this.mUseRepeatingSurface = z;
        }

        public void setTag(Object obj) {
            this.mTag = obj;
        }

        public CaptureConfig build() {
            return new CaptureConfig(new ArrayList(this.mSurfaces), OptionsBundle.from(this.mImplementationOptions), this.mTemplateType, this.mCameraCaptureCallbacks, this.mUseRepeatingSurface, this.mTag);
        }
    }
}

package androidx.camera.camera2.internal;

import android.content.Context;
import android.graphics.Point;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.util.Pair;
import android.util.Rational;
import android.util.Size;
import android.view.WindowManager;
import androidx.camera.camera2.internal.compat.CameraAccessExceptionCompat;
import androidx.camera.camera2.internal.compat.CameraManagerCompat;
import androidx.camera.core.CameraUnavailableException;
import androidx.camera.core.CameraX;
import androidx.camera.core.impl.ImageOutputConfig;
import androidx.camera.core.impl.SurfaceCombination;
import androidx.camera.core.impl.SurfaceConfig;
import androidx.camera.core.impl.SurfaceSizeDefinition;
import androidx.camera.core.impl.UseCaseConfig;
import androidx.core.util.Preconditions;
import com.badlogic.gdx.graphics.GL20;
import com.hivemq.client.mqtt.MqttProxyConfig;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.eclipse.jgit.lib.ConfigConstants;

/* JADX INFO: loaded from: classes.dex */
final class SupportedSurfaceCombination {
    private static final int ALIGN16 = 16;
    private final CamcorderProfileHelper mCamcorderProfileHelper;
    private final String mCameraId;
    private final CameraCharacteristics mCharacteristics;
    private final int mHardwareLevel;
    private SurfaceSizeDefinition mSurfaceSizeDefinition;
    private static final Size MAX_PREVIEW_SIZE = new Size(1920, MqttProxyConfig.DEFAULT_SOCKS_PROXY_PORT);
    private static final Size DEFAULT_SIZE = new Size(640, 480);
    private static final Size ZERO_SIZE = new Size(0, 0);
    private static final Size QUALITY_2160P_SIZE = new Size(3840, 2160);
    private static final Size QUALITY_1080P_SIZE = new Size(1920, MqttProxyConfig.DEFAULT_SOCKS_PROXY_PORT);
    private static final Size QUALITY_720P_SIZE = new Size(GL20.GL_INVALID_ENUM, 720);
    private static final Size QUALITY_480P_SIZE = new Size(720, 480);
    private static final Rational ASPECT_RATIO_4_3 = new Rational(4, 3);
    private static final Rational ASPECT_RATIO_3_4 = new Rational(3, 4);
    private static final Rational ASPECT_RATIO_16_9 = new Rational(16, 9);
    private static final Rational ASPECT_RATIO_9_16 = new Rational(9, 16);
    private final List<SurfaceCombination> mSurfaceCombinations = new ArrayList();
    private final Map<Integer, Size> mMaxSizeCache = new HashMap();
    private final Map<Integer, List<Size>> mExcludedSizeListCache = new HashMap();
    private boolean mIsRawSupported = false;
    private boolean mIsBurstCaptureSupported = false;

    private void checkCustomization() {
    }

    private Rational getCorrectedAspectRatio() {
        return null;
    }

    SupportedSurfaceCombination(Context context, String str, CamcorderProfileHelper camcorderProfileHelper) throws CameraUnavailableException {
        String str2 = (String) Preconditions.checkNotNull(str);
        this.mCameraId = str2;
        this.mCamcorderProfileHelper = (CamcorderProfileHelper) Preconditions.checkNotNull(camcorderProfileHelper);
        CameraManagerCompat cameraManagerCompatFrom = CameraManagerCompat.from(context);
        WindowManager windowManager = (WindowManager) context.getSystemService(ConfigConstants.CONFIG_KEY_WINDOW);
        try {
            CameraCharacteristics cameraCharacteristics = cameraManagerCompatFrom.getCameraCharacteristics(str2);
            this.mCharacteristics = cameraCharacteristics;
            Integer num = (Integer) cameraCharacteristics.get(CameraCharacteristics.INFO_SUPPORTED_HARDWARE_LEVEL);
            this.mHardwareLevel = num != null ? num.intValue() : 2;
            generateSupportedCombinationList();
            generateSurfaceSizeDefinition(windowManager);
            checkCustomization();
        } catch (CameraAccessExceptionCompat e) {
            throw CameraUnavailableExceptionHelper.createFrom(e);
        }
    }

    String getCameraId() {
        return this.mCameraId;
    }

    boolean isRawSupported() {
        return this.mIsRawSupported;
    }

    boolean isBurstCaptureSupported() {
        return this.mIsBurstCaptureSupported;
    }

    boolean checkSupported(List<SurfaceConfig> list) {
        Iterator<SurfaceCombination> it = this.mSurfaceCombinations.iterator();
        boolean zIsSupported = false;
        while (it.hasNext() && !(zIsSupported = it.next().isSupported(list))) {
        }
        return zIsSupported;
    }

    SurfaceConfig transformSurfaceConfig(int i, Size size) {
        SurfaceConfig.ConfigType configType;
        SurfaceConfig.ConfigSize configSize = SurfaceConfig.ConfigSize.NOT_SUPPORT;
        if (getAllOutputSizesByFormat(i) == null) {
            throw new IllegalArgumentException("Can not get supported output size for the format: " + i);
        }
        if (i == 35) {
            configType = SurfaceConfig.ConfigType.YUV;
        } else if (i == 256) {
            configType = SurfaceConfig.ConfigType.JPEG;
        } else if (i == 32) {
            configType = SurfaceConfig.ConfigType.RAW;
        } else {
            configType = SurfaceConfig.ConfigType.PRIV;
        }
        Size sizeFetchMaxSize = fetchMaxSize(i);
        if (size.getWidth() * size.getHeight() <= this.mSurfaceSizeDefinition.getAnalysisSize().getWidth() * this.mSurfaceSizeDefinition.getAnalysisSize().getHeight()) {
            configSize = SurfaceConfig.ConfigSize.ANALYSIS;
        } else if (size.getWidth() * size.getHeight() <= this.mSurfaceSizeDefinition.getPreviewSize().getWidth() * this.mSurfaceSizeDefinition.getPreviewSize().getHeight()) {
            configSize = SurfaceConfig.ConfigSize.PREVIEW;
        } else if (size.getWidth() * size.getHeight() <= this.mSurfaceSizeDefinition.getRecordSize().getWidth() * this.mSurfaceSizeDefinition.getRecordSize().getHeight()) {
            configSize = SurfaceConfig.ConfigSize.RECORD;
        } else if (size.getWidth() * size.getHeight() <= sizeFetchMaxSize.getWidth() * sizeFetchMaxSize.getHeight()) {
            configSize = SurfaceConfig.ConfigSize.MAXIMUM;
        }
        return SurfaceConfig.create(configType, configSize);
    }

    Map<UseCaseConfig<?>, Size> getSuggestedResolutions(List<SurfaceConfig> list, List<UseCaseConfig<?>> list2) {
        HashMap map = new HashMap();
        List<Integer> useCasesPriorityOrder = getUseCasesPriorityOrder(list2);
        ArrayList arrayList = new ArrayList();
        Iterator<Integer> it = useCasesPriorityOrder.iterator();
        while (it.hasNext()) {
            arrayList.add(getSupportedOutputSizes(list2.get(it.next().intValue())));
        }
        for (List<Size> list3 : getAllPossibleSizeArrangements(arrayList)) {
            ArrayList arrayList2 = new ArrayList(list);
            for (int i = 0; i < list3.size(); i++) {
                arrayList2.add(transformSurfaceConfig(list2.get(useCasesPriorityOrder.get(i).intValue()).getInputFormat(), list3.get(i)));
            }
            if (checkSupported(arrayList2)) {
                for (UseCaseConfig<?> useCaseConfig : list2) {
                    map.put(useCaseConfig, list3.get(useCasesPriorityOrder.indexOf(Integer.valueOf(list2.indexOf(useCaseConfig)))));
                }
                break;
            }
        }
        return map;
    }

    SurfaceSizeDefinition getSurfaceSizeDefinition() {
        return this.mSurfaceSizeDefinition;
    }

    private Size fetchMaxSize(int i) {
        Size size = this.mMaxSizeCache.get(Integer.valueOf(i));
        if (size != null) {
            return size;
        }
        Size maxOutputSizeByFormat = getMaxOutputSizeByFormat(i);
        this.mMaxSizeCache.put(Integer.valueOf(i), maxOutputSizeByFormat);
        return maxOutputSizeByFormat;
    }

    private List<Integer> getUseCasesPriorityOrder(List<UseCaseConfig<?>> list) {
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        Iterator<UseCaseConfig<?>> it = list.iterator();
        while (it.hasNext()) {
            int surfaceOccupancyPriority = it.next().getSurfaceOccupancyPriority(0);
            if (!arrayList2.contains(Integer.valueOf(surfaceOccupancyPriority))) {
                arrayList2.add(Integer.valueOf(surfaceOccupancyPriority));
            }
        }
        Collections.sort(arrayList2);
        Collections.reverse(arrayList2);
        Iterator it2 = arrayList2.iterator();
        while (it2.hasNext()) {
            int iIntValue = ((Integer) it2.next()).intValue();
            for (UseCaseConfig<?> useCaseConfig : list) {
                if (iIntValue == useCaseConfig.getSurfaceOccupancyPriority(0)) {
                    arrayList.add(Integer.valueOf(list.indexOf(useCaseConfig)));
                }
            }
        }
        return arrayList;
    }

    List<Size> getSupportedOutputSizes(UseCaseConfig<?> useCaseConfig) {
        int inputFormat = useCaseConfig.getInputFormat();
        ImageOutputConfig imageOutputConfig = (ImageOutputConfig) useCaseConfig;
        Size[] allOutputSizesByFormat = getAllOutputSizesByFormat(inputFormat, imageOutputConfig);
        ArrayList<Size> arrayList = new ArrayList();
        Size maxResolution = imageOutputConfig.getMaxResolution(getMaxOutputSizeByFormat(inputFormat));
        int targetRotation = imageOutputConfig.getTargetRotation(0);
        Arrays.sort(allOutputSizesByFormat, new CompareSizesByArea(true));
        Size size = ZERO_SIZE;
        Size targetResolution = imageOutputConfig.getTargetResolution(size);
        if (isRotationNeeded(targetRotation)) {
            targetResolution = new Size(targetResolution.getHeight(), targetResolution.getWidth());
        }
        Size size2 = DEFAULT_SIZE;
        int area = getArea(size2);
        if (getArea(maxResolution) < area) {
            size2 = new Size(0, 0);
        } else if (!targetResolution.equals(size) && getArea(targetResolution) < area) {
            size2 = targetResolution;
        }
        for (Size size3 : allOutputSizesByFormat) {
            if (getArea(size3) <= getArea(maxResolution) && getArea(size3) >= getArea(size2)) {
                arrayList.add(size3);
            }
        }
        if (arrayList.isEmpty()) {
            throw new IllegalArgumentException("Can not get supported output size under supported maximum for the format: " + inputFormat);
        }
        ArrayList arrayList2 = new ArrayList();
        ArrayList arrayList3 = new ArrayList();
        Rational correctedAspectRatio = getCorrectedAspectRatio();
        if (correctedAspectRatio == null) {
            if (imageOutputConfig.hasTargetAspectRatio()) {
                boolean zIsRotationNeeded = isRotationNeeded(0);
                int targetAspectRatio = imageOutputConfig.getTargetAspectRatio();
                if (targetAspectRatio == 0) {
                    correctedAspectRatio = zIsRotationNeeded ? ASPECT_RATIO_4_3 : ASPECT_RATIO_3_4;
                } else if (targetAspectRatio == 1) {
                    correctedAspectRatio = zIsRotationNeeded ? ASPECT_RATIO_16_9 : ASPECT_RATIO_9_16;
                }
            } else {
                correctedAspectRatio = rotateAspectRatioByRotation(imageOutputConfig.getTargetAspectRatioCustom(null), targetRotation);
            }
        }
        for (Size size4 : arrayList) {
            if (correctedAspectRatio == null || hasMatchingAspectRatio(size4, correctedAspectRatio)) {
                if (!arrayList2.contains(size4)) {
                    arrayList2.add(size4);
                }
            } else if (!arrayList3.contains(size4)) {
                arrayList3.add(size4);
            }
        }
        if (correctedAspectRatio != null) {
            Collections.sort(arrayList3, new CompareSizesByDistanceToTargetRatio(Float.valueOf(correctedAspectRatio.floatValue())));
        }
        Size size5 = ZERO_SIZE;
        if (targetResolution.equals(size5)) {
            targetResolution = imageOutputConfig.getDefaultResolution(size5);
        }
        if (!targetResolution.equals(size5)) {
            removeSupportedSizesByTargetSize(arrayList2, targetResolution);
            removeSupportedSizesByTargetSizeAndAspectRatio(arrayList3, targetResolution);
        }
        ArrayList arrayList4 = new ArrayList();
        arrayList4.addAll(arrayList2);
        arrayList4.addAll(arrayList3);
        return arrayList4;
    }

    private Rational rotateAspectRatioByRotation(Rational rational, int i) {
        return (rational == null || !isRotationNeeded(i)) ? rational : new Rational(rational.getDenominator(), rational.getNumerator());
    }

    private boolean isRotationNeeded(int i) {
        int sensorRotationDegrees = CameraX.getCameraInfo(this.mCameraId).getSensorRotationDegrees(i);
        return sensorRotationDegrees == 90 || sensorRotationDegrees == 270;
    }

    static boolean hasMatchingAspectRatio(Size size, Rational rational) {
        if (rational == null) {
            return false;
        }
        if (rational.equals(new Rational(size.getWidth(), size.getHeight()))) {
            return true;
        }
        if (getArea(size) >= getArea(DEFAULT_SIZE)) {
            return isPossibleMod16FromAspectRatio(size, rational);
        }
        return false;
    }

    private static boolean isPossibleMod16FromAspectRatio(Size size, Rational rational) {
        int width = size.getWidth();
        int height = size.getHeight();
        Rational rational2 = new Rational(rational.getDenominator(), rational.getNumerator());
        int i = width % 16;
        if (i == 0 && height % 16 == 0) {
            return ratioIntersectsMod16Segment(Math.max(0, height + (-16)), width, rational) || ratioIntersectsMod16Segment(Math.max(0, width + (-16)), height, rational2);
        }
        if (i == 0) {
            return ratioIntersectsMod16Segment(height, width, rational);
        }
        if (height % 16 == 0) {
            return ratioIntersectsMod16Segment(width, height, rational2);
        }
        return false;
    }

    private static int getArea(Size size) {
        return size.getWidth() * size.getHeight();
    }

    private static boolean ratioIntersectsMod16Segment(int i, int i2, Rational rational) {
        Preconditions.checkArgument(i2 % 16 == 0);
        double numerator = ((double) (i * rational.getNumerator())) / ((double) rational.getDenominator());
        return numerator > ((double) Math.max(0, i2 + (-16))) && numerator < ((double) (i2 + 16));
    }

    private void removeSupportedSizesByTargetSizeAndAspectRatio(List<Size> list, Size size) {
        if (list == null || list.isEmpty()) {
            return;
        }
        HashMap map = new HashMap();
        ArrayList arrayList = new ArrayList();
        Rational rational = null;
        for (int i = 0; i < list.size(); i++) {
            Size size2 = list.get(i);
            if (size2.getWidth() >= size.getWidth() && size2.getHeight() >= size.getHeight()) {
                Rational rational2 = new Rational(size2.getWidth(), size2.getHeight());
                if (rational == null || !hasMatchingAspectRatio(size2, rational)) {
                    rational = rational2;
                }
                Size size3 = (Size) map.get(rational);
                if (size3 != null) {
                    arrayList.add(size3);
                }
                map.put(rational, size2);
            }
        }
        list.removeAll(arrayList);
    }

    private void removeSupportedSizesByTargetSize(List<Size> list, Size size) {
        if (list == null || list.isEmpty()) {
            return;
        }
        ArrayList arrayList = new ArrayList();
        int i = -1;
        int i2 = 0;
        while (true) {
            int i3 = i2;
            int i4 = i;
            i = i3;
            if (i >= list.size()) {
                break;
            }
            Size size2 = list.get(i);
            if (size2.getWidth() < size.getWidth() || size2.getHeight() < size.getHeight()) {
                break;
            }
            if (i4 >= 0) {
                arrayList.add(list.get(i4));
            }
            i2 = i + 1;
        }
        list.removeAll(arrayList);
    }

    private List<List<Size>> getAllPossibleSizeArrangements(List<List<Size>> list) {
        Iterator<List<Size>> it = list.iterator();
        int size = 1;
        while (it.hasNext()) {
            size *= it.next().size();
        }
        if (size == 0) {
            throw new IllegalArgumentException("Failed to find supported resolutions.");
        }
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < size; i++) {
            arrayList.add(new ArrayList());
        }
        int size2 = size / list.get(0).size();
        int i2 = size;
        for (int i3 = 0; i3 < list.size(); i3++) {
            List<Size> list2 = list.get(i3);
            for (int i4 = 0; i4 < size; i4++) {
                ((List) arrayList.get(i4)).add(list2.get((i4 % i2) / size2));
            }
            if (i3 < list.size() - 1) {
                i2 = size2;
                size2 /= list.get(i3 + 1).size();
            }
        }
        return arrayList;
    }

    private Size[] getAllOutputSizesByFormat(int i) {
        return getAllOutputSizesByFormat(i, null);
    }

    private Size[] getAllOutputSizesByFormat(int i, ImageOutputConfig imageOutputConfig) {
        Size[] outputSizes = null;
        List<Pair<Integer, Size[]>> supportedResolutions = imageOutputConfig != null ? imageOutputConfig.getSupportedResolutions(null) : null;
        if (supportedResolutions != null) {
            for (Pair<Integer, Size[]> pair : supportedResolutions) {
                if (((Integer) pair.first).intValue() == i) {
                    outputSizes = (Size[]) pair.second;
                    break;
                }
            }
        }
        if (outputSizes == null) {
            StreamConfigurationMap streamConfigurationMap = (StreamConfigurationMap) this.mCharacteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
            if (streamConfigurationMap == null) {
                throw new IllegalArgumentException("Can not get supported output size for the format: " + i);
            }
            outputSizes = streamConfigurationMap.getOutputSizes(i);
        }
        if (outputSizes == null) {
            throw new IllegalArgumentException("Can not get supported output size for the format: " + i);
        }
        List<Size> listFetchExcludedSizes = fetchExcludedSizes(i);
        ArrayList arrayList = new ArrayList(Arrays.asList(outputSizes));
        arrayList.removeAll(listFetchExcludedSizes);
        Size[] sizeArr = (Size[]) arrayList.toArray(new Size[0]);
        Arrays.sort(sizeArr, new CompareSizesByArea(true));
        return sizeArr;
    }

    Size getMaxOutputSizeByFormat(int i) {
        return (Size) Collections.max(Arrays.asList(getAllOutputSizesByFormat(i)), new CompareSizesByArea());
    }

    List<SurfaceCombination> getLegacySupportedCombinationList() {
        ArrayList arrayList = new ArrayList();
        SurfaceCombination surfaceCombination = new SurfaceCombination();
        surfaceCombination.addSurfaceConfig(SurfaceConfig.create(SurfaceConfig.ConfigType.PRIV, SurfaceConfig.ConfigSize.MAXIMUM));
        arrayList.add(surfaceCombination);
        SurfaceCombination surfaceCombination2 = new SurfaceCombination();
        surfaceCombination2.addSurfaceConfig(SurfaceConfig.create(SurfaceConfig.ConfigType.JPEG, SurfaceConfig.ConfigSize.MAXIMUM));
        arrayList.add(surfaceCombination2);
        SurfaceCombination surfaceCombination3 = new SurfaceCombination();
        surfaceCombination3.addSurfaceConfig(SurfaceConfig.create(SurfaceConfig.ConfigType.YUV, SurfaceConfig.ConfigSize.MAXIMUM));
        arrayList.add(surfaceCombination3);
        SurfaceCombination surfaceCombination4 = new SurfaceCombination();
        surfaceCombination4.addSurfaceConfig(SurfaceConfig.create(SurfaceConfig.ConfigType.PRIV, SurfaceConfig.ConfigSize.PREVIEW));
        surfaceCombination4.addSurfaceConfig(SurfaceConfig.create(SurfaceConfig.ConfigType.JPEG, SurfaceConfig.ConfigSize.MAXIMUM));
        arrayList.add(surfaceCombination4);
        SurfaceCombination surfaceCombination5 = new SurfaceCombination();
        surfaceCombination5.addSurfaceConfig(SurfaceConfig.create(SurfaceConfig.ConfigType.YUV, SurfaceConfig.ConfigSize.PREVIEW));
        surfaceCombination5.addSurfaceConfig(SurfaceConfig.create(SurfaceConfig.ConfigType.JPEG, SurfaceConfig.ConfigSize.MAXIMUM));
        arrayList.add(surfaceCombination5);
        SurfaceCombination surfaceCombination6 = new SurfaceCombination();
        surfaceCombination6.addSurfaceConfig(SurfaceConfig.create(SurfaceConfig.ConfigType.PRIV, SurfaceConfig.ConfigSize.PREVIEW));
        surfaceCombination6.addSurfaceConfig(SurfaceConfig.create(SurfaceConfig.ConfigType.PRIV, SurfaceConfig.ConfigSize.PREVIEW));
        arrayList.add(surfaceCombination6);
        SurfaceCombination surfaceCombination7 = new SurfaceCombination();
        surfaceCombination7.addSurfaceConfig(SurfaceConfig.create(SurfaceConfig.ConfigType.PRIV, SurfaceConfig.ConfigSize.PREVIEW));
        surfaceCombination7.addSurfaceConfig(SurfaceConfig.create(SurfaceConfig.ConfigType.YUV, SurfaceConfig.ConfigSize.PREVIEW));
        arrayList.add(surfaceCombination7);
        SurfaceCombination surfaceCombination8 = new SurfaceCombination();
        surfaceCombination8.addSurfaceConfig(SurfaceConfig.create(SurfaceConfig.ConfigType.PRIV, SurfaceConfig.ConfigSize.PREVIEW));
        surfaceCombination8.addSurfaceConfig(SurfaceConfig.create(SurfaceConfig.ConfigType.YUV, SurfaceConfig.ConfigSize.PREVIEW));
        surfaceCombination8.addSurfaceConfig(SurfaceConfig.create(SurfaceConfig.ConfigType.JPEG, SurfaceConfig.ConfigSize.MAXIMUM));
        arrayList.add(surfaceCombination8);
        return arrayList;
    }

    List<SurfaceCombination> getLimitedSupportedCombinationList() {
        ArrayList arrayList = new ArrayList();
        SurfaceCombination surfaceCombination = new SurfaceCombination();
        surfaceCombination.addSurfaceConfig(SurfaceConfig.create(SurfaceConfig.ConfigType.PRIV, SurfaceConfig.ConfigSize.PREVIEW));
        surfaceCombination.addSurfaceConfig(SurfaceConfig.create(SurfaceConfig.ConfigType.PRIV, SurfaceConfig.ConfigSize.RECORD));
        arrayList.add(surfaceCombination);
        SurfaceCombination surfaceCombination2 = new SurfaceCombination();
        surfaceCombination2.addSurfaceConfig(SurfaceConfig.create(SurfaceConfig.ConfigType.PRIV, SurfaceConfig.ConfigSize.PREVIEW));
        surfaceCombination2.addSurfaceConfig(SurfaceConfig.create(SurfaceConfig.ConfigType.YUV, SurfaceConfig.ConfigSize.RECORD));
        arrayList.add(surfaceCombination2);
        SurfaceCombination surfaceCombination3 = new SurfaceCombination();
        surfaceCombination3.addSurfaceConfig(SurfaceConfig.create(SurfaceConfig.ConfigType.YUV, SurfaceConfig.ConfigSize.PREVIEW));
        surfaceCombination3.addSurfaceConfig(SurfaceConfig.create(SurfaceConfig.ConfigType.YUV, SurfaceConfig.ConfigSize.RECORD));
        arrayList.add(surfaceCombination3);
        SurfaceCombination surfaceCombination4 = new SurfaceCombination();
        surfaceCombination4.addSurfaceConfig(SurfaceConfig.create(SurfaceConfig.ConfigType.PRIV, SurfaceConfig.ConfigSize.PREVIEW));
        surfaceCombination4.addSurfaceConfig(SurfaceConfig.create(SurfaceConfig.ConfigType.PRIV, SurfaceConfig.ConfigSize.RECORD));
        surfaceCombination4.addSurfaceConfig(SurfaceConfig.create(SurfaceConfig.ConfigType.JPEG, SurfaceConfig.ConfigSize.RECORD));
        arrayList.add(surfaceCombination4);
        SurfaceCombination surfaceCombination5 = new SurfaceCombination();
        surfaceCombination5.addSurfaceConfig(SurfaceConfig.create(SurfaceConfig.ConfigType.PRIV, SurfaceConfig.ConfigSize.PREVIEW));
        surfaceCombination5.addSurfaceConfig(SurfaceConfig.create(SurfaceConfig.ConfigType.YUV, SurfaceConfig.ConfigSize.RECORD));
        surfaceCombination5.addSurfaceConfig(SurfaceConfig.create(SurfaceConfig.ConfigType.JPEG, SurfaceConfig.ConfigSize.RECORD));
        arrayList.add(surfaceCombination5);
        SurfaceCombination surfaceCombination6 = new SurfaceCombination();
        surfaceCombination6.addSurfaceConfig(SurfaceConfig.create(SurfaceConfig.ConfigType.YUV, SurfaceConfig.ConfigSize.PREVIEW));
        surfaceCombination6.addSurfaceConfig(SurfaceConfig.create(SurfaceConfig.ConfigType.YUV, SurfaceConfig.ConfigSize.PREVIEW));
        surfaceCombination6.addSurfaceConfig(SurfaceConfig.create(SurfaceConfig.ConfigType.JPEG, SurfaceConfig.ConfigSize.MAXIMUM));
        arrayList.add(surfaceCombination6);
        return arrayList;
    }

    List<SurfaceCombination> getFullSupportedCombinationList() {
        ArrayList arrayList = new ArrayList();
        SurfaceCombination surfaceCombination = new SurfaceCombination();
        surfaceCombination.addSurfaceConfig(SurfaceConfig.create(SurfaceConfig.ConfigType.PRIV, SurfaceConfig.ConfigSize.PREVIEW));
        surfaceCombination.addSurfaceConfig(SurfaceConfig.create(SurfaceConfig.ConfigType.PRIV, SurfaceConfig.ConfigSize.MAXIMUM));
        arrayList.add(surfaceCombination);
        SurfaceCombination surfaceCombination2 = new SurfaceCombination();
        surfaceCombination2.addSurfaceConfig(SurfaceConfig.create(SurfaceConfig.ConfigType.PRIV, SurfaceConfig.ConfigSize.PREVIEW));
        surfaceCombination2.addSurfaceConfig(SurfaceConfig.create(SurfaceConfig.ConfigType.YUV, SurfaceConfig.ConfigSize.MAXIMUM));
        arrayList.add(surfaceCombination2);
        SurfaceCombination surfaceCombination3 = new SurfaceCombination();
        surfaceCombination3.addSurfaceConfig(SurfaceConfig.create(SurfaceConfig.ConfigType.YUV, SurfaceConfig.ConfigSize.PREVIEW));
        surfaceCombination3.addSurfaceConfig(SurfaceConfig.create(SurfaceConfig.ConfigType.YUV, SurfaceConfig.ConfigSize.MAXIMUM));
        arrayList.add(surfaceCombination3);
        SurfaceCombination surfaceCombination4 = new SurfaceCombination();
        surfaceCombination4.addSurfaceConfig(SurfaceConfig.create(SurfaceConfig.ConfigType.PRIV, SurfaceConfig.ConfigSize.PREVIEW));
        surfaceCombination4.addSurfaceConfig(SurfaceConfig.create(SurfaceConfig.ConfigType.PRIV, SurfaceConfig.ConfigSize.PREVIEW));
        surfaceCombination4.addSurfaceConfig(SurfaceConfig.create(SurfaceConfig.ConfigType.JPEG, SurfaceConfig.ConfigSize.MAXIMUM));
        arrayList.add(surfaceCombination4);
        SurfaceCombination surfaceCombination5 = new SurfaceCombination();
        surfaceCombination5.addSurfaceConfig(SurfaceConfig.create(SurfaceConfig.ConfigType.YUV, SurfaceConfig.ConfigSize.ANALYSIS));
        surfaceCombination5.addSurfaceConfig(SurfaceConfig.create(SurfaceConfig.ConfigType.PRIV, SurfaceConfig.ConfigSize.PREVIEW));
        surfaceCombination5.addSurfaceConfig(SurfaceConfig.create(SurfaceConfig.ConfigType.YUV, SurfaceConfig.ConfigSize.MAXIMUM));
        arrayList.add(surfaceCombination5);
        SurfaceCombination surfaceCombination6 = new SurfaceCombination();
        surfaceCombination6.addSurfaceConfig(SurfaceConfig.create(SurfaceConfig.ConfigType.YUV, SurfaceConfig.ConfigSize.ANALYSIS));
        surfaceCombination6.addSurfaceConfig(SurfaceConfig.create(SurfaceConfig.ConfigType.YUV, SurfaceConfig.ConfigSize.PREVIEW));
        surfaceCombination6.addSurfaceConfig(SurfaceConfig.create(SurfaceConfig.ConfigType.YUV, SurfaceConfig.ConfigSize.MAXIMUM));
        arrayList.add(surfaceCombination6);
        return arrayList;
    }

    List<SurfaceCombination> getRAWSupportedCombinationList() {
        ArrayList arrayList = new ArrayList();
        SurfaceCombination surfaceCombination = new SurfaceCombination();
        surfaceCombination.addSurfaceConfig(SurfaceConfig.create(SurfaceConfig.ConfigType.RAW, SurfaceConfig.ConfigSize.MAXIMUM));
        arrayList.add(surfaceCombination);
        SurfaceCombination surfaceCombination2 = new SurfaceCombination();
        surfaceCombination2.addSurfaceConfig(SurfaceConfig.create(SurfaceConfig.ConfigType.PRIV, SurfaceConfig.ConfigSize.PREVIEW));
        surfaceCombination2.addSurfaceConfig(SurfaceConfig.create(SurfaceConfig.ConfigType.RAW, SurfaceConfig.ConfigSize.MAXIMUM));
        arrayList.add(surfaceCombination2);
        SurfaceCombination surfaceCombination3 = new SurfaceCombination();
        surfaceCombination3.addSurfaceConfig(SurfaceConfig.create(SurfaceConfig.ConfigType.YUV, SurfaceConfig.ConfigSize.PREVIEW));
        surfaceCombination3.addSurfaceConfig(SurfaceConfig.create(SurfaceConfig.ConfigType.RAW, SurfaceConfig.ConfigSize.MAXIMUM));
        arrayList.add(surfaceCombination3);
        SurfaceCombination surfaceCombination4 = new SurfaceCombination();
        surfaceCombination4.addSurfaceConfig(SurfaceConfig.create(SurfaceConfig.ConfigType.PRIV, SurfaceConfig.ConfigSize.PREVIEW));
        surfaceCombination4.addSurfaceConfig(SurfaceConfig.create(SurfaceConfig.ConfigType.PRIV, SurfaceConfig.ConfigSize.PREVIEW));
        surfaceCombination4.addSurfaceConfig(SurfaceConfig.create(SurfaceConfig.ConfigType.RAW, SurfaceConfig.ConfigSize.MAXIMUM));
        arrayList.add(surfaceCombination4);
        SurfaceCombination surfaceCombination5 = new SurfaceCombination();
        surfaceCombination5.addSurfaceConfig(SurfaceConfig.create(SurfaceConfig.ConfigType.PRIV, SurfaceConfig.ConfigSize.PREVIEW));
        surfaceCombination5.addSurfaceConfig(SurfaceConfig.create(SurfaceConfig.ConfigType.YUV, SurfaceConfig.ConfigSize.PREVIEW));
        surfaceCombination5.addSurfaceConfig(SurfaceConfig.create(SurfaceConfig.ConfigType.RAW, SurfaceConfig.ConfigSize.MAXIMUM));
        arrayList.add(surfaceCombination5);
        SurfaceCombination surfaceCombination6 = new SurfaceCombination();
        surfaceCombination6.addSurfaceConfig(SurfaceConfig.create(SurfaceConfig.ConfigType.YUV, SurfaceConfig.ConfigSize.PREVIEW));
        surfaceCombination6.addSurfaceConfig(SurfaceConfig.create(SurfaceConfig.ConfigType.YUV, SurfaceConfig.ConfigSize.PREVIEW));
        surfaceCombination6.addSurfaceConfig(SurfaceConfig.create(SurfaceConfig.ConfigType.RAW, SurfaceConfig.ConfigSize.MAXIMUM));
        arrayList.add(surfaceCombination6);
        SurfaceCombination surfaceCombination7 = new SurfaceCombination();
        surfaceCombination7.addSurfaceConfig(SurfaceConfig.create(SurfaceConfig.ConfigType.PRIV, SurfaceConfig.ConfigSize.PREVIEW));
        surfaceCombination7.addSurfaceConfig(SurfaceConfig.create(SurfaceConfig.ConfigType.JPEG, SurfaceConfig.ConfigSize.MAXIMUM));
        surfaceCombination7.addSurfaceConfig(SurfaceConfig.create(SurfaceConfig.ConfigType.RAW, SurfaceConfig.ConfigSize.MAXIMUM));
        arrayList.add(surfaceCombination7);
        SurfaceCombination surfaceCombination8 = new SurfaceCombination();
        surfaceCombination8.addSurfaceConfig(SurfaceConfig.create(SurfaceConfig.ConfigType.YUV, SurfaceConfig.ConfigSize.PREVIEW));
        surfaceCombination8.addSurfaceConfig(SurfaceConfig.create(SurfaceConfig.ConfigType.JPEG, SurfaceConfig.ConfigSize.MAXIMUM));
        surfaceCombination8.addSurfaceConfig(SurfaceConfig.create(SurfaceConfig.ConfigType.RAW, SurfaceConfig.ConfigSize.MAXIMUM));
        arrayList.add(surfaceCombination8);
        return arrayList;
    }

    List<SurfaceCombination> getBurstSupportedCombinationList() {
        ArrayList arrayList = new ArrayList();
        SurfaceCombination surfaceCombination = new SurfaceCombination();
        surfaceCombination.addSurfaceConfig(SurfaceConfig.create(SurfaceConfig.ConfigType.PRIV, SurfaceConfig.ConfigSize.PREVIEW));
        surfaceCombination.addSurfaceConfig(SurfaceConfig.create(SurfaceConfig.ConfigType.PRIV, SurfaceConfig.ConfigSize.MAXIMUM));
        arrayList.add(surfaceCombination);
        SurfaceCombination surfaceCombination2 = new SurfaceCombination();
        surfaceCombination2.addSurfaceConfig(SurfaceConfig.create(SurfaceConfig.ConfigType.PRIV, SurfaceConfig.ConfigSize.PREVIEW));
        surfaceCombination2.addSurfaceConfig(SurfaceConfig.create(SurfaceConfig.ConfigType.YUV, SurfaceConfig.ConfigSize.MAXIMUM));
        arrayList.add(surfaceCombination2);
        SurfaceCombination surfaceCombination3 = new SurfaceCombination();
        surfaceCombination3.addSurfaceConfig(SurfaceConfig.create(SurfaceConfig.ConfigType.YUV, SurfaceConfig.ConfigSize.PREVIEW));
        surfaceCombination3.addSurfaceConfig(SurfaceConfig.create(SurfaceConfig.ConfigType.YUV, SurfaceConfig.ConfigSize.MAXIMUM));
        arrayList.add(surfaceCombination3);
        return arrayList;
    }

    List<SurfaceCombination> getLevel3SupportedCombinationList() {
        ArrayList arrayList = new ArrayList();
        SurfaceCombination surfaceCombination = new SurfaceCombination();
        surfaceCombination.addSurfaceConfig(SurfaceConfig.create(SurfaceConfig.ConfigType.PRIV, SurfaceConfig.ConfigSize.PREVIEW));
        surfaceCombination.addSurfaceConfig(SurfaceConfig.create(SurfaceConfig.ConfigType.PRIV, SurfaceConfig.ConfigSize.ANALYSIS));
        surfaceCombination.addSurfaceConfig(SurfaceConfig.create(SurfaceConfig.ConfigType.YUV, SurfaceConfig.ConfigSize.MAXIMUM));
        surfaceCombination.addSurfaceConfig(SurfaceConfig.create(SurfaceConfig.ConfigType.RAW, SurfaceConfig.ConfigSize.MAXIMUM));
        arrayList.add(surfaceCombination);
        SurfaceCombination surfaceCombination2 = new SurfaceCombination();
        surfaceCombination2.addSurfaceConfig(SurfaceConfig.create(SurfaceConfig.ConfigType.PRIV, SurfaceConfig.ConfigSize.PREVIEW));
        surfaceCombination2.addSurfaceConfig(SurfaceConfig.create(SurfaceConfig.ConfigType.PRIV, SurfaceConfig.ConfigSize.ANALYSIS));
        surfaceCombination2.addSurfaceConfig(SurfaceConfig.create(SurfaceConfig.ConfigType.JPEG, SurfaceConfig.ConfigSize.MAXIMUM));
        surfaceCombination2.addSurfaceConfig(SurfaceConfig.create(SurfaceConfig.ConfigType.RAW, SurfaceConfig.ConfigSize.MAXIMUM));
        arrayList.add(surfaceCombination2);
        return arrayList;
    }

    private void generateSupportedCombinationList() {
        this.mSurfaceCombinations.addAll(getLegacySupportedCombinationList());
        int i = this.mHardwareLevel;
        if (i == 0 || i == 1 || i == 3) {
            this.mSurfaceCombinations.addAll(getLimitedSupportedCombinationList());
        }
        int i2 = this.mHardwareLevel;
        if (i2 == 1 || i2 == 3) {
            this.mSurfaceCombinations.addAll(getFullSupportedCombinationList());
        }
        int[] iArr = (int[]) this.mCharacteristics.get(CameraCharacteristics.REQUEST_AVAILABLE_CAPABILITIES);
        if (iArr != null) {
            for (int i3 : iArr) {
                if (i3 == 3) {
                    this.mIsRawSupported = true;
                } else if (i3 == 6) {
                    this.mIsBurstCaptureSupported = true;
                }
            }
        }
        if (this.mIsRawSupported) {
            this.mSurfaceCombinations.addAll(getRAWSupportedCombinationList());
        }
        if (this.mIsBurstCaptureSupported && this.mHardwareLevel == 0) {
            this.mSurfaceCombinations.addAll(getBurstSupportedCombinationList());
        }
        if (this.mHardwareLevel == 3) {
            this.mSurfaceCombinations.addAll(getLevel3SupportedCombinationList());
        }
    }

    private void generateSurfaceSizeDefinition(WindowManager windowManager) {
        this.mSurfaceSizeDefinition = SurfaceSizeDefinition.create(new Size(640, 480), getPreviewSize(windowManager), getRecordSize());
    }

    private Size getPreviewSize(WindowManager windowManager) {
        Size size;
        Point point = new Point();
        windowManager.getDefaultDisplay().getRealSize(point);
        if (point.x > point.y) {
            size = new Size(point.x, point.y);
        } else {
            size = new Size(point.y, point.x);
        }
        return (Size) Collections.min(Arrays.asList(new Size(size.getWidth(), size.getHeight()), MAX_PREVIEW_SIZE), new CompareSizesByArea());
    }

    private Size getRecordSize() {
        Size size = QUALITY_480P_SIZE;
        if (this.mCamcorderProfileHelper.hasProfile(Integer.parseInt(this.mCameraId), 8)) {
            return QUALITY_2160P_SIZE;
        }
        if (this.mCamcorderProfileHelper.hasProfile(Integer.parseInt(this.mCameraId), 6)) {
            return QUALITY_1080P_SIZE;
        }
        if (this.mCamcorderProfileHelper.hasProfile(Integer.parseInt(this.mCameraId), 5)) {
            return QUALITY_720P_SIZE;
        }
        this.mCamcorderProfileHelper.hasProfile(Integer.parseInt(this.mCameraId), 4);
        return size;
    }

    private List<Size> fetchExcludedSizes(int i) {
        List<Size> list = this.mExcludedSizeListCache.get(Integer.valueOf(i));
        if (list != null) {
            return list;
        }
        List<Size> excludedSizes = SupportedSizeConstraints.getExcludedSizes(this.mCameraId, i);
        this.mExcludedSizeListCache.put(Integer.valueOf(i), excludedSizes);
        return excludedSizes;
    }

    static final class CompareSizesByArea implements Comparator<Size> {
        private boolean mReverse;

        CompareSizesByArea() {
            this.mReverse = false;
        }

        CompareSizesByArea(boolean z) {
            this.mReverse = z;
        }

        @Override // java.util.Comparator
        public int compare(Size size, Size size2) {
            int iSignum = Long.signum((((long) size.getWidth()) * ((long) size.getHeight())) - (((long) size2.getWidth()) * ((long) size2.getHeight())));
            return this.mReverse ? iSignum * (-1) : iSignum;
        }
    }

    static final class CompareSizesByDistanceToTargetRatio implements Comparator<Size> {
        private Float mTargetRatio;

        CompareSizesByDistanceToTargetRatio(Float f) {
            this.mTargetRatio = f;
        }

        @Override // java.util.Comparator
        public int compare(Size size, Size size2) {
            if (SupportedSurfaceCombination.hasMatchingAspectRatio(size, new Rational(size2.getWidth(), size2.getHeight()))) {
                return 0;
            }
            return (int) Math.signum(Float.valueOf(Math.abs(Float.valueOf((size.getWidth() * 1.0f) / size.getHeight()).floatValue() - this.mTargetRatio.floatValue())).floatValue() - Float.valueOf(Math.abs(Float.valueOf((size2.getWidth() * 1.0f) / size2.getHeight()).floatValue() - this.mTargetRatio.floatValue())).floatValue());
        }
    }
}

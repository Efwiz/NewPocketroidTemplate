package org.catrobat.catroid.camera;

import android.graphics.Point;
import android.graphics.Rect;
import com.google.android.gms.common.internal.ServiceSpecificExtraArgs;
import com.google.mlkit.vision.face.Face;
import com.google.mlkit.vision.pose.Pose;
import com.google.mlkit.vision.pose.PoseLandmark;
import com.huawei.hms.mlsdk.face.MLFace;
import com.huawei.hms.mlsdk.skeleton.MLJoint;
import com.huawei.hms.mlsdk.skeleton.MLSkeleton;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.Triple;
import kotlin.collections.ArraysKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import kotlin.math.MathKt;
import kotlin.ranges.RangesKt;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.common.ScreenValues;
import org.catrobat.catroid.formulaeditor.SensorCustomEvent;
import org.catrobat.catroid.formulaeditor.SensorCustomEventListener;
import org.catrobat.catroid.formulaeditor.SensorHandler;
import org.catrobat.catroid.formulaeditor.Sensors;
import org.catrobat.catroid.stage.StageActivity;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/camera/VisualDetectionHandler.class
  classes12.dex
 */
/* JADX INFO: compiled from: VisualDetectionHandler.kt */
/* JADX INFO: loaded from: classes12.dex */
@Metadata(d1 = {"\u0000\u008c\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0015\n\u0000\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010#\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0006\n\u0002\b\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0012H\u0007J\u001e\u0010\u0016\u001a\u00020\u00142\f\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\n0\u00182\u0006\u0010\u0019\u001a\u00020\u0004H\u0002J&\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u001d2\u0006\u0010\u001f\u001a\u00020\u001d2\u0006\u0010 \u001a\u00020\u001dJ\u0014\u0010!\u001a\u00020\u00142\f\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\n0\u0018J\u0014\u0010\"\u001a\u00020\u00142\f\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\n0\u0018J\u0010\u0010#\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0012H\u0007J\u001a\u0010$\u001a\b\u0012\u0004\u0012\u00020\n0\u00182\f\u0010%\u001a\b\u0012\u0004\u0012\u00020&0\u0018J\u001a\u0010'\u001a\b\u0012\u0004\u0012\u00020\n0\u00182\f\u0010%\u001a\b\u0012\u0004\u0012\u00020(0\u0018J(\u0010)\u001a\u00020\u001b2\u0006\u0010*\u001a\u00020\u001d2\u0006\u0010+\u001a\u00020\u001d2\u0006\u0010,\u001a\u00020\u00042\u0006\u0010-\u001a\u00020\u0004H\u0002J\u0016\u0010.\u001a\u00020\u00142\u0006\u0010,\u001a\u00020\u00042\u0006\u0010-\u001a\u00020\u0004J \u0010/\u001a\u00020\u00142\b\u00100\u001a\u0004\u0018\u0001012\u0006\u0010,\u001a\u00020\u00042\u0006\u0010-\u001a\u00020\u0004J$\u00102\u001a\u00020\u00142\f\u00103\u001a\b\u0012\u0004\u0012\u0002040\u00182\u0006\u0010,\u001a\u00020\u00042\u0006\u0010-\u001a\u00020\u0004J\u0006\u00105\u001a\u00020\u0014J\u001e\u00106\u001a\u00020\u00142\u0006\u00107\u001a\u00020\u001b2\u0006\u00108\u001a\u00020\u00042\u0006\u00109\u001a\u00020\u0004J \u0010:\u001a\u00020\u00142\u0006\u0010;\u001a\u00020\u00042\u0006\u0010<\u001a\u00020\u00122\u0006\u0010=\u001a\u00020\u001bH\u0002J \u0010>\u001a\u00020\u00142\u0006\u0010;\u001a\u00020\u00042\u0006\u0010<\u001a\u00020\u00122\u0006\u0010=\u001a\u00020\u001bH\u0002J\u0018\u0010?\u001a\u00020\u00142\u0006\u0010@\u001a\u00020A2\u0006\u0010=\u001a\u00020\u001bH\u0002J\u0018\u0010B\u001a\u00020\u00142\u0006\u0010C\u001a\u00020\u00042\u0006\u0010=\u001a\u00020\u001bH\u0002J\u0016\u0010D\u001a\u00020\u00142\u0006\u0010E\u001a\u00020F2\u0006\u0010G\u001a\u00020\u0004J \u0010H\u001a\u00020\u00142\u0006\u0010;\u001a\u00020\u00042\u0006\u0010<\u001a\u00020\u00122\u0006\u0010=\u001a\u00020\u001bH\u0002J\f\u0010I\u001a\u00020J*\u00020\u001bH\u0002J$\u0010K\u001a\u00020\u0014*\u00020\u00122\u0006\u0010L\u001a\u00020M2\u0006\u0010N\u001a\u00020M2\u0006\u0010=\u001a\u00020JH\u0002J\u001c\u0010O\u001a\u00020\u0014*\u00020\u00122\u0006\u0010P\u001a\u00020M2\u0006\u0010Q\u001a\u00020\u0001H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R$\u0010\b\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\n0\tX\u0086\u000e¢\u0006\u0010\n\u0002\u0010\u000f\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u0014\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00120\u0011X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006R"}, d2 = {"Lorg/catrobat/catroid/camera/VisualDetectionHandler;", "", "()V", "FACE_SENSORS", "", "MAX_FACE_SIZE", "faceIds", "", "facesForSensors", "", "Lorg/catrobat/catroid/camera/VisualDetectionHandlerFace;", "getFacesForSensors", "()[Lorg/catrobat/catroid/camera/VisualDetectionHandlerFace;", "setFacesForSensors", "([Lorg/catrobat/catroid/camera/VisualDetectionHandlerFace;)V", "[Lorg/catrobat/catroid/camera/VisualDetectionHandlerFace;", "sensorListeners", "", "Lorg/catrobat/catroid/formulaeditor/SensorCustomEventListener;", "addListener", "", ServiceSpecificExtraArgs.CastExtraArgs.LISTENER, "attachNewFaceIfExisting", "faces", "", "index", "coordinatesFromRelativePosition", "Landroid/graphics/Point;", "relativeX", "", "width", "relativeY", "height", "handleAlreadyExistingFaces", "handleNewFaces", "removeListener", "translateGoogleFaceToVisualDetectionFace", "faceList", "Lcom/google/mlkit/vision/face/Face;", "translateHuaweiFaceToVisualDetectionFace", "Lcom/huawei/hms/mlsdk/face/MLFace;", "translateToStageCoordinates", "x", "y", "imageWidth", "imageHeight", "updateAllFaceSensorValues", "updateAllPoseSensorValues", "pose", "Lcom/google/mlkit/vision/pose/Pose;", "updateAllPoseSensorValuesHuawei", "skeletonList", "Lcom/huawei/hms/mlsdk/skeleton/MLSkeleton;", "updateFaceDetectionStatusSensorValues", "updateFaceSensorValues", "facePosition", "faceSize", "faceNumber", "updateHeadPoseSensorValues", "poseLandmarkType", "sensorListener", "position", "updateLowerBodyPoseSensorValues", "updatePoseSensorValues", "poseLandmark", "Lcom/google/mlkit/vision/pose/PoseLandmark;", "updatePoseSensorValuesHuawei", "jointType", "updateTextSensorValues", "text", "", "numberOfBlocks", "updateUpperBodyPoseSensorValues", "toPosition", "Lorg/catrobat/catroid/camera/Position;", "writePositionAccordingToRotationToSensor", "sensorX", "Lorg/catrobat/catroid/formulaeditor/Sensors;", "sensorY", "writeToSensor", "sourceSensor", "value", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class VisualDetectionHandler {
    private static final int FACE_SENSORS = 2;
    private static final int MAX_FACE_SIZE = 100;
    private static int[] faceIds;
    private static VisualDetectionHandlerFace[] facesForSensors;
    public static final VisualDetectionHandler INSTANCE = new VisualDetectionHandler();
    private static final Set<SensorCustomEventListener> sensorListeners = new LinkedHashSet();

    private VisualDetectionHandler() {
    }

    static {
        VisualDetectionHandlerFace[] visualDetectionHandlerFaceArr = new VisualDetectionHandlerFace[2];
        for (int i = 0; i < 2; i++) {
            visualDetectionHandlerFaceArr[i] = null;
        }
        facesForSensors = visualDetectionHandlerFaceArr;
        int[] iArr = new int[2];
        for (int i2 = 0; i2 < 2; i2++) {
            iArr[i2] = -1;
        }
        faceIds = iArr;
    }

    public final VisualDetectionHandlerFace[] getFacesForSensors() {
        return facesForSensors;
    }

    public final void setFacesForSensors(VisualDetectionHandlerFace[] visualDetectionHandlerFaceArr) {
        Intrinsics.checkNotNullParameter(visualDetectionHandlerFaceArr, "<set-?>");
        facesForSensors = visualDetectionHandlerFaceArr;
    }

    @JvmStatic
    public static final void addListener(SensorCustomEventListener listener) {
        Intrinsics.checkNotNullParameter(listener, "listener");
        sensorListeners.add(listener);
    }

    @JvmStatic
    public static final void removeListener(SensorCustomEventListener listener) {
        Intrinsics.checkNotNullParameter(listener, "listener");
        sensorListeners.remove(listener);
    }

    public final List<VisualDetectionHandlerFace> translateGoogleFaceToVisualDetectionFace(List<? extends Face> faceList) {
        Intrinsics.checkNotNullParameter(faceList, "faceList");
        ArrayList arrayList = new ArrayList();
        for (Face face : faceList) {
            Integer trackingId = face.getTrackingId();
            Intrinsics.checkNotNullExpressionValue(trackingId, "getTrackingId(...)");
            int iIntValue = trackingId.intValue();
            Rect boundingBox = face.getBoundingBox();
            Intrinsics.checkNotNullExpressionValue(boundingBox, "getBoundingBox(...)");
            arrayList.add(new VisualDetectionHandlerFace(iIntValue, boundingBox));
        }
        return arrayList;
    }

    public final List<VisualDetectionHandlerFace> translateHuaweiFaceToVisualDetectionFace(List<? extends MLFace> faceList) {
        Intrinsics.checkNotNullParameter(faceList, "faceList");
        ArrayList arrayList = new ArrayList();
        for (MLFace mLFace : faceList) {
            int tracingIdentity = mLFace.getTracingIdentity();
            Rect border = mLFace.getBorder();
            Intrinsics.checkNotNullExpressionValue(border, "getBorder(...)");
            arrayList.add(new VisualDetectionHandlerFace(tracingIdentity, border));
        }
        return arrayList;
    }

    public final void updateTextSensorValues(String text, int numberOfBlocks) {
        Intrinsics.checkNotNullParameter(text, "text");
        for (SensorCustomEventListener sensorCustomEventListener : sensorListeners) {
            VisualDetectionHandler visualDetectionHandler = INSTANCE;
            visualDetectionHandler.writeToSensor(sensorCustomEventListener, Sensors.TEXT_FROM_CAMERA, text);
            visualDetectionHandler.writeToSensor(sensorCustomEventListener, Sensors.TEXT_BLOCKS_NUMBER, Double.valueOf(numberOfBlocks));
        }
    }

    public final void updateAllFaceSensorValues(int imageWidth, int imageHeight) {
        updateFaceDetectionStatusSensorValues();
        VisualDetectionHandlerFace[] visualDetectionHandlerFaceArr = facesForSensors;
        int length = visualDetectionHandlerFaceArr.length;
        int i = 0;
        int i2 = 0;
        while (i < length) {
            VisualDetectionHandlerFace visualDetectionHandlerFace = visualDetectionHandlerFaceArr[i];
            int i3 = i2 + 1;
            if (visualDetectionHandlerFace != null) {
                Rect boundingBox = visualDetectionHandlerFace.getBoundingBox();
                VisualDetectionHandler visualDetectionHandler = INSTANCE;
                visualDetectionHandler.updateFaceSensorValues(visualDetectionHandler.translateToStageCoordinates(boundingBox.exactCenterX(), boundingBox.exactCenterY(), imageWidth, imageHeight), MathKt.roundToInt(((double) 100) * RangesKt.coerceAtMost(((double) boundingBox.height()) / ((double) imageHeight), 1.0d)), i2);
            }
            i++;
            i2 = i3;
        }
        ArraysKt.fill$default(facesForSensors, (Object) null, 0, 0, 6, (Object) null);
    }

    public final void handleAlreadyExistingFaces(List<VisualDetectionHandlerFace> faces) {
        Intrinsics.checkNotNullParameter(faces, "faces");
        for (VisualDetectionHandlerFace visualDetectionHandlerFace : faces) {
            int id2 = visualDetectionHandlerFace.getId();
            int[] iArr = faceIds;
            if (id2 == iArr[0]) {
                facesForSensors[0] = visualDetectionHandlerFace;
            } else if (id2 == iArr[1]) {
                facesForSensors[1] = visualDetectionHandlerFace;
            }
        }
    }

    public final void handleNewFaces(List<VisualDetectionHandlerFace> faces) {
        Intrinsics.checkNotNullParameter(faces, "faces");
        VisualDetectionHandlerFace[] visualDetectionHandlerFaceArr = facesForSensors;
        int length = visualDetectionHandlerFaceArr.length;
        int i = 0;
        int i2 = 0;
        while (i < length) {
            int i3 = i2 + 1;
            if (visualDetectionHandlerFaceArr[i] == null) {
                INSTANCE.attachNewFaceIfExisting(faces, i2);
            }
            i++;
            i2 = i3;
        }
    }

    private final void attachNewFaceIfExisting(List<VisualDetectionHandlerFace> faces, int index) {
        for (VisualDetectionHandlerFace visualDetectionHandlerFace : faces) {
            if (!ArraysKt.contains(faceIds, visualDetectionHandlerFace.getId())) {
                faceIds[index] = visualDetectionHandlerFace.getId();
                facesForSensors[index] = visualDetectionHandlerFace;
                return;
            }
        }
    }

    public final void updateFaceDetectionStatusSensorValues() {
        VisualDetectionHandlerFace[] visualDetectionHandlerFaceArr = facesForSensors;
        double d = visualDetectionHandlerFaceArr[0] != null ? 1.0d : 0.0d;
        double d2 = visualDetectionHandlerFaceArr[1] == null ? 0.0d : 1.0d;
        for (SensorCustomEventListener sensorCustomEventListener : sensorListeners) {
            VisualDetectionHandler visualDetectionHandler = INSTANCE;
            visualDetectionHandler.writeToSensor(sensorCustomEventListener, Sensors.FACE_DETECTED, Double.valueOf(d));
            visualDetectionHandler.writeToSensor(sensorCustomEventListener, Sensors.SECOND_FACE_DETECTED, Double.valueOf(d2));
        }
    }

    public final void updateFaceSensorValues(Point facePosition, int faceSize, int faceNumber) {
        Triple triple;
        Intrinsics.checkNotNullParameter(facePosition, "facePosition");
        Set<SensorCustomEventListener> set = sensorListeners;
        ArrayList<SensorCustomEventListener> arrayList = new ArrayList();
        for (Object obj : set) {
            if (faceNumber >= 0 && faceNumber < 2) {
                arrayList.add(obj);
            }
        }
        for (SensorCustomEventListener sensorCustomEventListener : arrayList) {
            if (faceNumber == 1) {
                triple = new Triple(Sensors.SECOND_FACE_X, Sensors.SECOND_FACE_Y, Sensors.SECOND_FACE_SIZE);
            } else {
                triple = new Triple(Sensors.FACE_X, Sensors.FACE_Y, Sensors.FACE_SIZE);
            }
            VisualDetectionHandler visualDetectionHandler = INSTANCE;
            visualDetectionHandler.writePositionAccordingToRotationToSensor(sensorCustomEventListener, (Sensors) triple.getFirst(), (Sensors) triple.getSecond(), visualDetectionHandler.toPosition(facePosition));
            visualDetectionHandler.writeToSensor(sensorCustomEventListener, (Sensors) triple.getThird(), Double.valueOf(faceSize));
        }
    }

    public final void updateAllPoseSensorValues(Pose pose, int imageWidth, int imageHeight) {
        List<PoseLandmark> allPoseLandmarks = pose != null ? pose.getAllPoseLandmarks() : null;
        List<PoseLandmark> list = allPoseLandmarks;
        if (list == null || list.isEmpty()) {
            return;
        }
        for (PoseLandmark poseLandmark : allPoseLandmarks) {
            if (poseLandmark != null) {
                Intrinsics.checkNotNull(poseLandmark);
                VisualDetectionHandler visualDetectionHandler = INSTANCE;
                visualDetectionHandler.updatePoseSensorValues(poseLandmark, visualDetectionHandler.translateToStageCoordinates(poseLandmark.getPosition().x, poseLandmark.getPosition().y, imageWidth, imageHeight));
            }
        }
    }

    private final void updatePoseSensorValues(PoseLandmark poseLandmark, Point position) {
        for (SensorCustomEventListener sensorCustomEventListener : sensorListeners) {
            VisualDetectionHandler visualDetectionHandler = INSTANCE;
            visualDetectionHandler.updateHeadPoseSensorValues(poseLandmark.getLandmarkType(), sensorCustomEventListener, position);
            visualDetectionHandler.updateUpperBodyPoseSensorValues(poseLandmark.getLandmarkType(), sensorCustomEventListener, position);
            visualDetectionHandler.updateLowerBodyPoseSensorValues(poseLandmark.getLandmarkType(), sensorCustomEventListener, position);
        }
    }

    private final void updateHeadPoseSensorValues(int poseLandmarkType, SensorCustomEventListener sensorListener, Point position) {
        Pair pair;
        switch (poseLandmarkType) {
            case 0:
                pair = new Pair(Sensors.NOSE_X, Sensors.NOSE_Y);
                break;
            case 1:
                pair = new Pair(Sensors.LEFT_EYE_INNER_X, Sensors.LEFT_EYE_INNER_Y);
                break;
            case 2:
                pair = new Pair(Sensors.LEFT_EYE_CENTER_X, Sensors.LEFT_EYE_CENTER_Y);
                break;
            case 3:
                pair = new Pair(Sensors.LEFT_EYE_OUTER_X, Sensors.LEFT_EYE_OUTER_Y);
                break;
            case 4:
                pair = new Pair(Sensors.RIGHT_EYE_INNER_X, Sensors.RIGHT_EYE_INNER_Y);
                break;
            case 5:
                pair = new Pair(Sensors.RIGHT_EYE_CENTER_X, Sensors.RIGHT_EYE_CENTER_Y);
                break;
            case 6:
                pair = new Pair(Sensors.RIGHT_EYE_OUTER_X, Sensors.RIGHT_EYE_OUTER_Y);
                break;
            case 7:
                pair = new Pair(Sensors.LEFT_EAR_X, Sensors.LEFT_EAR_Y);
                break;
            case 8:
                pair = new Pair(Sensors.RIGHT_EAR_X, Sensors.RIGHT_EAR_Y);
                break;
            case 9:
                pair = new Pair(Sensors.MOUTH_LEFT_CORNER_X, Sensors.MOUTH_LEFT_CORNER_Y);
                break;
            case 10:
                pair = new Pair(Sensors.MOUTH_RIGHT_CORNER_X, Sensors.MOUTH_RIGHT_CORNER_Y);
                break;
            default:
                pair = null;
                break;
        }
        if (pair != null) {
            VisualDetectionHandler visualDetectionHandler = INSTANCE;
            visualDetectionHandler.writePositionAccordingToRotationToSensor(sensorListener, (Sensors) pair.getFirst(), (Sensors) pair.getSecond(), visualDetectionHandler.toPosition(position));
        }
    }

    private final void updateUpperBodyPoseSensorValues(int poseLandmarkType, SensorCustomEventListener sensorListener, Point position) {
        Pair pair;
        switch (poseLandmarkType) {
            case 11:
                pair = new Pair(Sensors.LEFT_SHOULDER_X, Sensors.LEFT_SHOULDER_Y);
                break;
            case 12:
                pair = new Pair(Sensors.RIGHT_SHOULDER_X, Sensors.RIGHT_SHOULDER_Y);
                break;
            case 13:
                pair = new Pair(Sensors.LEFT_ELBOW_X, Sensors.LEFT_ELBOW_Y);
                break;
            case 14:
                pair = new Pair(Sensors.RIGHT_ELBOW_X, Sensors.RIGHT_ELBOW_Y);
                break;
            case 15:
                pair = new Pair(Sensors.LEFT_WRIST_X, Sensors.LEFT_WRIST_Y);
                break;
            case 16:
                pair = new Pair(Sensors.RIGHT_WRIST_X, Sensors.RIGHT_WRIST_Y);
                break;
            case 17:
                pair = new Pair(Sensors.LEFT_PINKY_X, Sensors.LEFT_PINKY_Y);
                break;
            case 18:
                pair = new Pair(Sensors.RIGHT_PINKY_X, Sensors.RIGHT_PINKY_Y);
                break;
            case 19:
                pair = new Pair(Sensors.LEFT_INDEX_X, Sensors.LEFT_INDEX_Y);
                break;
            case 20:
                pair = new Pair(Sensors.RIGHT_INDEX_X, Sensors.RIGHT_INDEX_Y);
                break;
            case 21:
                pair = new Pair(Sensors.LEFT_THUMB_X, Sensors.LEFT_THUMB_Y);
                break;
            case 22:
                pair = new Pair(Sensors.RIGHT_THUMB_X, Sensors.RIGHT_THUMB_Y);
                break;
            default:
                pair = null;
                break;
        }
        if (pair != null) {
            VisualDetectionHandler visualDetectionHandler = INSTANCE;
            visualDetectionHandler.writePositionAccordingToRotationToSensor(sensorListener, (Sensors) pair.getFirst(), (Sensors) pair.getSecond(), visualDetectionHandler.toPosition(position));
        }
    }

    private final void updateLowerBodyPoseSensorValues(int poseLandmarkType, SensorCustomEventListener sensorListener, Point position) {
        Pair pair;
        switch (poseLandmarkType) {
            case 23:
                pair = new Pair(Sensors.LEFT_HIP_X, Sensors.LEFT_HIP_Y);
                break;
            case 24:
                pair = new Pair(Sensors.RIGHT_HIP_X, Sensors.RIGHT_HIP_Y);
                break;
            case 25:
                pair = new Pair(Sensors.LEFT_KNEE_X, Sensors.LEFT_KNEE_Y);
                break;
            case 26:
                pair = new Pair(Sensors.RIGHT_KNEE_X, Sensors.RIGHT_KNEE_Y);
                break;
            case 27:
                pair = new Pair(Sensors.LEFT_ANKLE_X, Sensors.LEFT_ANKLE_Y);
                break;
            case 28:
                pair = new Pair(Sensors.RIGHT_ANKLE_X, Sensors.RIGHT_ANKLE_Y);
                break;
            case 29:
                pair = new Pair(Sensors.LEFT_HEEL_X, Sensors.LEFT_HEEL_Y);
                break;
            case 30:
                pair = new Pair(Sensors.RIGHT_HEEL_X, Sensors.RIGHT_HEEL_Y);
                break;
            case 31:
                pair = new Pair(Sensors.LEFT_FOOT_INDEX_X, Sensors.LEFT_FOOT_INDEX_Y);
                break;
            case 32:
                pair = new Pair(Sensors.RIGHT_FOOT_INDEX_X, Sensors.RIGHT_FOOT_INDEX_Y);
                break;
            default:
                pair = null;
                break;
        }
        if (pair != null) {
            VisualDetectionHandler visualDetectionHandler = INSTANCE;
            visualDetectionHandler.writePositionAccordingToRotationToSensor(sensorListener, (Sensors) pair.getFirst(), (Sensors) pair.getSecond(), visualDetectionHandler.toPosition(position));
        }
    }

    public final void updateAllPoseSensorValuesHuawei(List<? extends MLSkeleton> skeletonList, int imageWidth, int imageHeight) {
        Intrinsics.checkNotNullParameter(skeletonList, "skeletonList");
        if (skeletonList.isEmpty()) {
            return;
        }
        List<MLJoint> joints = skeletonList.get(0).getJoints();
        Intrinsics.checkNotNullExpressionValue(joints, "getJoints(...)");
        for (MLJoint mLJoint : joints) {
            if (mLJoint != null) {
                Intrinsics.checkNotNull(mLJoint);
                VisualDetectionHandler visualDetectionHandler = INSTANCE;
                visualDetectionHandler.updatePoseSensorValuesHuawei(mLJoint.getType(), visualDetectionHandler.translateToStageCoordinates(mLJoint.getPointX(), mLJoint.getPointY(), imageWidth, imageHeight));
            }
        }
    }

    private final void updatePoseSensorValuesHuawei(int jointType, Point position) {
        Pair pair;
        for (SensorCustomEventListener sensorCustomEventListener : sensorListeners) {
            switch (jointType) {
                case 101:
                    pair = new Pair(Sensors.RIGHT_SHOULDER_X, Sensors.RIGHT_SHOULDER_Y);
                    break;
                case 102:
                    pair = new Pair(Sensors.RIGHT_ELBOW_X, Sensors.RIGHT_ELBOW_Y);
                    break;
                case 103:
                    pair = new Pair(Sensors.RIGHT_WRIST_X, Sensors.RIGHT_WRIST_Y);
                    break;
                case 104:
                    pair = new Pair(Sensors.LEFT_SHOULDER_X, Sensors.LEFT_SHOULDER_Y);
                    break;
                case 105:
                    pair = new Pair(Sensors.LEFT_ELBOW_X, Sensors.LEFT_ELBOW_Y);
                    break;
                case 106:
                    pair = new Pair(Sensors.LEFT_WRIST_X, Sensors.LEFT_WRIST_Y);
                    break;
                case 107:
                    pair = new Pair(Sensors.RIGHT_HIP_X, Sensors.RIGHT_HIP_Y);
                    break;
                case 108:
                    pair = new Pair(Sensors.RIGHT_KNEE_X, Sensors.RIGHT_KNEE_Y);
                    break;
                case 109:
                    pair = new Pair(Sensors.RIGHT_ANKLE_X, Sensors.RIGHT_ANKLE_Y);
                    break;
                case 110:
                    pair = new Pair(Sensors.LEFT_HIP_X, Sensors.LEFT_HIP_Y);
                    break;
                case 111:
                    pair = new Pair(Sensors.LEFT_KNEE_X, Sensors.LEFT_KNEE_Y);
                    break;
                case 112:
                    pair = new Pair(Sensors.LEFT_ANKLE_X, Sensors.LEFT_ANKLE_Y);
                    break;
                case 113:
                    pair = new Pair(Sensors.HEAD_TOP_X, Sensors.HEAD_TOP_Y);
                    break;
                case 114:
                    pair = new Pair(Sensors.NECK_X, Sensors.NECK_Y);
                    break;
                default:
                    pair = null;
                    break;
            }
            if (pair != null) {
                VisualDetectionHandler visualDetectionHandler = INSTANCE;
                visualDetectionHandler.writePositionAccordingToRotationToSensor(sensorCustomEventListener, (Sensors) pair.getFirst(), (Sensors) pair.getSecond(), visualDetectionHandler.toPosition(position));
            }
        }
    }

    private final Position toPosition(Point point) {
        return new Position(point.x, point.y);
    }

    private final Point translateToStageCoordinates(double x, double y, int imageWidth, int imageHeight) {
        boolean zIsCameraFacingFront = StageActivity.getActiveCameraManager().isCameraFacingFront();
        double d = imageWidth;
        double d2 = imageHeight;
        double d3 = d / d2;
        if (ProjectManager.getInstance().isCurrentProjectLandscapeMode()) {
            double d4 = x / d;
            double d5 = 1;
            double d6 = d5 - (y / d2);
            double width = ((double) ScreenValues.currentScreenResolution.getWidth()) / d3;
            if (!zIsCameraFacingFront) {
                d4 = d5 - d4;
            }
            return coordinatesFromRelativePosition(d6, width, d4, ScreenValues.currentScreenResolution.getWidth());
        }
        double d7 = x / d2;
        if (zIsCameraFacingFront) {
            d7 = ((double) 1) - d7;
        }
        return coordinatesFromRelativePosition(d7, ((double) ScreenValues.currentScreenResolution.getHeight()) / d3, ((double) 1) - (y / d), ScreenValues.currentScreenResolution.getHeight());
    }

    public final Point coordinatesFromRelativePosition(double relativeX, double width, double relativeY, double height) {
        return new Point(MathKt.roundToInt(width * (relativeX - 0.5d)), MathKt.roundToInt(height * (relativeY - 0.5d)));
    }

    private final void writePositionAccordingToRotationToSensor(SensorCustomEventListener sensorCustomEventListener, Sensors sensors, Sensors sensors2, Position position) {
        Position positionAccordingToRotation = SensorHandler.getPositionAccordingToRotation(position);
        writeToSensor(sensorCustomEventListener, sensors, Double.valueOf(positionAccordingToRotation.getX()));
        writeToSensor(sensorCustomEventListener, sensors2, Double.valueOf(positionAccordingToRotation.getY()));
    }

    private final void writeToSensor(SensorCustomEventListener sensorCustomEventListener, Sensors sensors, Object obj) {
        sensorCustomEventListener.onCustomSensorChanged(new SensorCustomEvent(sensors, obj));
    }
}

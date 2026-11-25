package org.catrobat.catroid.formulaeditor.common;

import com.reandroid.apk.XmlHelper;
import java.util.Set;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.catroid.formulaeditor.Functions;
import org.catrobat.catroid.formulaeditor.Sensors;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/formulaeditor/common/FormulaElementResources.class
  classes14.dex
 */
/* JADX INFO: compiled from: FormulaElementResources.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010#\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\"\u0010\u0003\u001a\u00020\u00042\u000e\u0010\u0005\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00070\u00062\b\u0010\b\u001a\u0004\u0018\u00010\tH\u0003J\"\u0010\n\u001a\u00020\u00042\u000e\u0010\u0005\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00070\u00062\b\u0010\u000b\u001a\u0004\u0018\u00010\fH\u0003J\"\u0010\r\u001a\u00020\u00042\u000e\u0010\u0005\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00070\u00062\b\u0010\u000b\u001a\u0004\u0018\u00010\fH\u0003J\"\u0010\u000e\u001a\u00020\u00042\u000e\u0010\u0005\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00070\u00062\b\u0010\u000b\u001a\u0004\u0018\u00010\fH\u0003J\"\u0010\u000f\u001a\u00020\u00042\u000e\u0010\u0005\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00070\u00062\b\u0010\u000b\u001a\u0004\u0018\u00010\fH\u0003J\"\u0010\u0010\u001a\u00020\u00042\u000e\u0010\u0005\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00070\u00062\b\u0010\u0011\u001a\u0004\u0018\u00010\tH\u0007J\"\u0010\u0012\u001a\u00020\u00042\u000e\u0010\u0005\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00070\u00062\b\u0010\u000b\u001a\u0004\u0018\u00010\fH\u0003J\"\u0010\u0013\u001a\u00020\u00042\u000e\u0010\u0005\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00070\u00062\b\u0010\u000b\u001a\u0004\u0018\u00010\fH\u0007¨\u0006\u0014"}, d2 = {"Lorg/catrobat/catroid/formulaeditor/common/FormulaElementResources;", "", "()V", "addAIExtensionFunctionsResources", "", XmlHelper.RESOURCES_TAG, "", "", "function", "Lorg/catrobat/catroid/formulaeditor/Functions;", "addAIExtensionSensorsResources", "sensor", "Lorg/catrobat/catroid/formulaeditor/Sensors;", "addCollisionSensorsResources", "addDeviceSensorsResources", "addExtensionSensorsResources", "addFunctionResources", "functions", "addPoseDetectionSensorsResources", "addSensorsResources", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FormulaElementResources {
    public static final FormulaElementResources INSTANCE = new FormulaElementResources();

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/formulaeditor/common/FormulaElementResources$WhenMappings.class
      classes14.dex
     */
    /* JADX INFO: compiled from: FormulaElementResources.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;
        public static final /* synthetic */ int[] $EnumSwitchMapping$1;

        static {
            int[] iArr = new int[Sensors.values().length];
            try {
                iArr[Sensors.FACE_DETECTED.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[Sensors.FACE_SIZE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[Sensors.FACE_X.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[Sensors.FACE_Y.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr[Sensors.SECOND_FACE_DETECTED.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                iArr[Sensors.SECOND_FACE_SIZE.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                iArr[Sensors.SECOND_FACE_X.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                iArr[Sensors.SECOND_FACE_Y.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                iArr[Sensors.TEXT_FROM_CAMERA.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                iArr[Sensors.TEXT_BLOCKS_NUMBER.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                iArr[Sensors.TEXT_BLOCK_X.ordinal()] = 11;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                iArr[Sensors.TEXT_BLOCK_Y.ordinal()] = 12;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                iArr[Sensors.TEXT_BLOCK_SIZE.ordinal()] = 13;
            } catch (NoSuchFieldError unused13) {
            }
            try {
                iArr[Sensors.TEXT_BLOCK_FROM_CAMERA.ordinal()] = 14;
            } catch (NoSuchFieldError unused14) {
            }
            try {
                iArr[Sensors.TEXT_BLOCK_LANGUAGE_FROM_CAMERA.ordinal()] = 15;
            } catch (NoSuchFieldError unused15) {
            }
            try {
                iArr[Sensors.SPEECH_RECOGNITION_LANGUAGE.ordinal()] = 16;
            } catch (NoSuchFieldError unused16) {
            }
            try {
                iArr[Sensors.HEAD_TOP_X.ordinal()] = 17;
            } catch (NoSuchFieldError unused17) {
            }
            try {
                iArr[Sensors.HEAD_TOP_Y.ordinal()] = 18;
            } catch (NoSuchFieldError unused18) {
            }
            try {
                iArr[Sensors.NECK_X.ordinal()] = 19;
            } catch (NoSuchFieldError unused19) {
            }
            try {
                iArr[Sensors.NECK_Y.ordinal()] = 20;
            } catch (NoSuchFieldError unused20) {
            }
            try {
                iArr[Sensors.NOSE_X.ordinal()] = 21;
            } catch (NoSuchFieldError unused21) {
            }
            try {
                iArr[Sensors.NOSE_Y.ordinal()] = 22;
            } catch (NoSuchFieldError unused22) {
            }
            try {
                iArr[Sensors.LEFT_EYE_INNER_X.ordinal()] = 23;
            } catch (NoSuchFieldError unused23) {
            }
            try {
                iArr[Sensors.LEFT_EYE_INNER_Y.ordinal()] = 24;
            } catch (NoSuchFieldError unused24) {
            }
            try {
                iArr[Sensors.LEFT_EYE_CENTER_X.ordinal()] = 25;
            } catch (NoSuchFieldError unused25) {
            }
            try {
                iArr[Sensors.LEFT_EYE_CENTER_Y.ordinal()] = 26;
            } catch (NoSuchFieldError unused26) {
            }
            try {
                iArr[Sensors.LEFT_EYE_OUTER_X.ordinal()] = 27;
            } catch (NoSuchFieldError unused27) {
            }
            try {
                iArr[Sensors.LEFT_EYE_OUTER_Y.ordinal()] = 28;
            } catch (NoSuchFieldError unused28) {
            }
            try {
                iArr[Sensors.RIGHT_EYE_INNER_X.ordinal()] = 29;
            } catch (NoSuchFieldError unused29) {
            }
            try {
                iArr[Sensors.RIGHT_EYE_INNER_Y.ordinal()] = 30;
            } catch (NoSuchFieldError unused30) {
            }
            try {
                iArr[Sensors.RIGHT_EYE_CENTER_X.ordinal()] = 31;
            } catch (NoSuchFieldError unused31) {
            }
            try {
                iArr[Sensors.RIGHT_EYE_CENTER_Y.ordinal()] = 32;
            } catch (NoSuchFieldError unused32) {
            }
            try {
                iArr[Sensors.RIGHT_EYE_OUTER_X.ordinal()] = 33;
            } catch (NoSuchFieldError unused33) {
            }
            try {
                iArr[Sensors.RIGHT_EYE_OUTER_Y.ordinal()] = 34;
            } catch (NoSuchFieldError unused34) {
            }
            try {
                iArr[Sensors.LEFT_EAR_X.ordinal()] = 35;
            } catch (NoSuchFieldError unused35) {
            }
            try {
                iArr[Sensors.LEFT_EAR_Y.ordinal()] = 36;
            } catch (NoSuchFieldError unused36) {
            }
            try {
                iArr[Sensors.RIGHT_EAR_X.ordinal()] = 37;
            } catch (NoSuchFieldError unused37) {
            }
            try {
                iArr[Sensors.RIGHT_EAR_Y.ordinal()] = 38;
            } catch (NoSuchFieldError unused38) {
            }
            try {
                iArr[Sensors.MOUTH_LEFT_CORNER_X.ordinal()] = 39;
            } catch (NoSuchFieldError unused39) {
            }
            try {
                iArr[Sensors.MOUTH_LEFT_CORNER_Y.ordinal()] = 40;
            } catch (NoSuchFieldError unused40) {
            }
            try {
                iArr[Sensors.MOUTH_RIGHT_CORNER_X.ordinal()] = 41;
            } catch (NoSuchFieldError unused41) {
            }
            try {
                iArr[Sensors.MOUTH_RIGHT_CORNER_Y.ordinal()] = 42;
            } catch (NoSuchFieldError unused42) {
            }
            try {
                iArr[Sensors.LEFT_SHOULDER_X.ordinal()] = 43;
            } catch (NoSuchFieldError unused43) {
            }
            try {
                iArr[Sensors.LEFT_SHOULDER_Y.ordinal()] = 44;
            } catch (NoSuchFieldError unused44) {
            }
            try {
                iArr[Sensors.RIGHT_SHOULDER_X.ordinal()] = 45;
            } catch (NoSuchFieldError unused45) {
            }
            try {
                iArr[Sensors.RIGHT_SHOULDER_Y.ordinal()] = 46;
            } catch (NoSuchFieldError unused46) {
            }
            try {
                iArr[Sensors.LEFT_ELBOW_X.ordinal()] = 47;
            } catch (NoSuchFieldError unused47) {
            }
            try {
                iArr[Sensors.LEFT_ELBOW_Y.ordinal()] = 48;
            } catch (NoSuchFieldError unused48) {
            }
            try {
                iArr[Sensors.RIGHT_ELBOW_X.ordinal()] = 49;
            } catch (NoSuchFieldError unused49) {
            }
            try {
                iArr[Sensors.RIGHT_ELBOW_Y.ordinal()] = 50;
            } catch (NoSuchFieldError unused50) {
            }
            try {
                iArr[Sensors.LEFT_WRIST_X.ordinal()] = 51;
            } catch (NoSuchFieldError unused51) {
            }
            try {
                iArr[Sensors.LEFT_WRIST_Y.ordinal()] = 52;
            } catch (NoSuchFieldError unused52) {
            }
            try {
                iArr[Sensors.RIGHT_WRIST_X.ordinal()] = 53;
            } catch (NoSuchFieldError unused53) {
            }
            try {
                iArr[Sensors.RIGHT_WRIST_Y.ordinal()] = 54;
            } catch (NoSuchFieldError unused54) {
            }
            try {
                iArr[Sensors.LEFT_PINKY_X.ordinal()] = 55;
            } catch (NoSuchFieldError unused55) {
            }
            try {
                iArr[Sensors.LEFT_PINKY_Y.ordinal()] = 56;
            } catch (NoSuchFieldError unused56) {
            }
            try {
                iArr[Sensors.RIGHT_PINKY_X.ordinal()] = 57;
            } catch (NoSuchFieldError unused57) {
            }
            try {
                iArr[Sensors.RIGHT_PINKY_Y.ordinal()] = 58;
            } catch (NoSuchFieldError unused58) {
            }
            try {
                iArr[Sensors.LEFT_INDEX_X.ordinal()] = 59;
            } catch (NoSuchFieldError unused59) {
            }
            try {
                iArr[Sensors.LEFT_INDEX_Y.ordinal()] = 60;
            } catch (NoSuchFieldError unused60) {
            }
            try {
                iArr[Sensors.RIGHT_INDEX_X.ordinal()] = 61;
            } catch (NoSuchFieldError unused61) {
            }
            try {
                iArr[Sensors.RIGHT_INDEX_Y.ordinal()] = 62;
            } catch (NoSuchFieldError unused62) {
            }
            try {
                iArr[Sensors.LEFT_THUMB_X.ordinal()] = 63;
            } catch (NoSuchFieldError unused63) {
            }
            try {
                iArr[Sensors.LEFT_THUMB_Y.ordinal()] = 64;
            } catch (NoSuchFieldError unused64) {
            }
            try {
                iArr[Sensors.RIGHT_THUMB_X.ordinal()] = 65;
            } catch (NoSuchFieldError unused65) {
            }
            try {
                iArr[Sensors.RIGHT_THUMB_Y.ordinal()] = 66;
            } catch (NoSuchFieldError unused66) {
            }
            try {
                iArr[Sensors.LEFT_HIP_X.ordinal()] = 67;
            } catch (NoSuchFieldError unused67) {
            }
            try {
                iArr[Sensors.LEFT_HIP_Y.ordinal()] = 68;
            } catch (NoSuchFieldError unused68) {
            }
            try {
                iArr[Sensors.RIGHT_HIP_X.ordinal()] = 69;
            } catch (NoSuchFieldError unused69) {
            }
            try {
                iArr[Sensors.RIGHT_HIP_Y.ordinal()] = 70;
            } catch (NoSuchFieldError unused70) {
            }
            try {
                iArr[Sensors.LEFT_KNEE_X.ordinal()] = 71;
            } catch (NoSuchFieldError unused71) {
            }
            try {
                iArr[Sensors.LEFT_KNEE_Y.ordinal()] = 72;
            } catch (NoSuchFieldError unused72) {
            }
            try {
                iArr[Sensors.RIGHT_KNEE_X.ordinal()] = 73;
            } catch (NoSuchFieldError unused73) {
            }
            try {
                iArr[Sensors.RIGHT_KNEE_Y.ordinal()] = 74;
            } catch (NoSuchFieldError unused74) {
            }
            try {
                iArr[Sensors.LEFT_ANKLE_X.ordinal()] = 75;
            } catch (NoSuchFieldError unused75) {
            }
            try {
                iArr[Sensors.LEFT_ANKLE_Y.ordinal()] = 76;
            } catch (NoSuchFieldError unused76) {
            }
            try {
                iArr[Sensors.RIGHT_ANKLE_X.ordinal()] = 77;
            } catch (NoSuchFieldError unused77) {
            }
            try {
                iArr[Sensors.RIGHT_ANKLE_Y.ordinal()] = 78;
            } catch (NoSuchFieldError unused78) {
            }
            try {
                iArr[Sensors.LEFT_HEEL_X.ordinal()] = 79;
            } catch (NoSuchFieldError unused79) {
            }
            try {
                iArr[Sensors.LEFT_HEEL_Y.ordinal()] = 80;
            } catch (NoSuchFieldError unused80) {
            }
            try {
                iArr[Sensors.RIGHT_HEEL_X.ordinal()] = 81;
            } catch (NoSuchFieldError unused81) {
            }
            try {
                iArr[Sensors.RIGHT_HEEL_Y.ordinal()] = 82;
            } catch (NoSuchFieldError unused82) {
            }
            try {
                iArr[Sensors.LEFT_FOOT_INDEX_X.ordinal()] = 83;
            } catch (NoSuchFieldError unused83) {
            }
            try {
                iArr[Sensors.LEFT_FOOT_INDEX_Y.ordinal()] = 84;
            } catch (NoSuchFieldError unused84) {
            }
            try {
                iArr[Sensors.RIGHT_FOOT_INDEX_X.ordinal()] = 85;
            } catch (NoSuchFieldError unused85) {
            }
            try {
                iArr[Sensors.RIGHT_FOOT_INDEX_Y.ordinal()] = 86;
            } catch (NoSuchFieldError unused86) {
            }
            try {
                iArr[Sensors.X_ACCELERATION.ordinal()] = 87;
            } catch (NoSuchFieldError unused87) {
            }
            try {
                iArr[Sensors.Y_ACCELERATION.ordinal()] = 88;
            } catch (NoSuchFieldError unused88) {
            }
            try {
                iArr[Sensors.Z_ACCELERATION.ordinal()] = 89;
            } catch (NoSuchFieldError unused89) {
            }
            try {
                iArr[Sensors.X_INCLINATION.ordinal()] = 90;
            } catch (NoSuchFieldError unused90) {
            }
            try {
                iArr[Sensors.Y_INCLINATION.ordinal()] = 91;
            } catch (NoSuchFieldError unused91) {
            }
            try {
                iArr[Sensors.COMPASS_DIRECTION.ordinal()] = 92;
            } catch (NoSuchFieldError unused92) {
            }
            try {
                iArr[Sensors.LATITUDE.ordinal()] = 93;
            } catch (NoSuchFieldError unused93) {
            }
            try {
                iArr[Sensors.LONGITUDE.ordinal()] = 94;
            } catch (NoSuchFieldError unused94) {
            }
            try {
                iArr[Sensors.LOCATION_ACCURACY.ordinal()] = 95;
            } catch (NoSuchFieldError unused95) {
            }
            try {
                iArr[Sensors.ALTITUDE.ordinal()] = 96;
            } catch (NoSuchFieldError unused96) {
            }
            try {
                iArr[Sensors.NFC_TAG_MESSAGE.ordinal()] = 97;
            } catch (NoSuchFieldError unused97) {
            }
            try {
                iArr[Sensors.NFC_TAG_ID.ordinal()] = 98;
            } catch (NoSuchFieldError unused98) {
            }
            try {
                iArr[Sensors.LOUDNESS.ordinal()] = 99;
            } catch (NoSuchFieldError unused99) {
            }
            try {
                iArr[Sensors.NXT_SENSOR_1.ordinal()] = 100;
            } catch (NoSuchFieldError unused100) {
            }
            try {
                iArr[Sensors.NXT_SENSOR_2.ordinal()] = 101;
            } catch (NoSuchFieldError unused101) {
            }
            try {
                iArr[Sensors.NXT_SENSOR_3.ordinal()] = 102;
            } catch (NoSuchFieldError unused102) {
            }
            try {
                iArr[Sensors.NXT_SENSOR_4.ordinal()] = 103;
            } catch (NoSuchFieldError unused103) {
            }
            try {
                iArr[Sensors.EV3_SENSOR_1.ordinal()] = 104;
            } catch (NoSuchFieldError unused104) {
            }
            try {
                iArr[Sensors.EV3_SENSOR_2.ordinal()] = 105;
            } catch (NoSuchFieldError unused105) {
            }
            try {
                iArr[Sensors.EV3_SENSOR_3.ordinal()] = 106;
            } catch (NoSuchFieldError unused106) {
            }
            try {
                iArr[Sensors.EV3_SENSOR_4.ordinal()] = 107;
            } catch (NoSuchFieldError unused107) {
            }
            try {
                iArr[Sensors.PHIRO_FRONT_LEFT.ordinal()] = 108;
            } catch (NoSuchFieldError unused108) {
            }
            try {
                iArr[Sensors.PHIRO_FRONT_RIGHT.ordinal()] = 109;
            } catch (NoSuchFieldError unused109) {
            }
            try {
                iArr[Sensors.PHIRO_SIDE_LEFT.ordinal()] = 110;
            } catch (NoSuchFieldError unused110) {
            }
            try {
                iArr[Sensors.PHIRO_SIDE_RIGHT.ordinal()] = 111;
            } catch (NoSuchFieldError unused111) {
            }
            try {
                iArr[Sensors.PHIRO_BOTTOM_LEFT.ordinal()] = 112;
            } catch (NoSuchFieldError unused112) {
            }
            try {
                iArr[Sensors.PHIRO_BOTTOM_RIGHT.ordinal()] = 113;
            } catch (NoSuchFieldError unused113) {
            }
            try {
                iArr[Sensors.GAMEPAD_A_PRESSED.ordinal()] = 114;
            } catch (NoSuchFieldError unused114) {
            }
            try {
                iArr[Sensors.GAMEPAD_B_PRESSED.ordinal()] = 115;
            } catch (NoSuchFieldError unused115) {
            }
            try {
                iArr[Sensors.GAMEPAD_DOWN_PRESSED.ordinal()] = 116;
            } catch (NoSuchFieldError unused116) {
            }
            try {
                iArr[Sensors.GAMEPAD_UP_PRESSED.ordinal()] = 117;
            } catch (NoSuchFieldError unused117) {
            }
            try {
                iArr[Sensors.GAMEPAD_LEFT_PRESSED.ordinal()] = 118;
            } catch (NoSuchFieldError unused118) {
            }
            try {
                iArr[Sensors.GAMEPAD_RIGHT_PRESSED.ordinal()] = 119;
            } catch (NoSuchFieldError unused119) {
            }
            try {
                iArr[Sensors.COLLIDES_WITH_EDGE.ordinal()] = 120;
            } catch (NoSuchFieldError unused120) {
            }
            try {
                iArr[Sensors.COLLIDES_WITH_FINGER.ordinal()] = 121;
            } catch (NoSuchFieldError unused121) {
            }
            $EnumSwitchMapping$0 = iArr;
            int[] iArr2 = new int[Functions.values().length];
            try {
                iArr2[Functions.ID_OF_DETECTED_OBJECT.ordinal()] = 1;
            } catch (NoSuchFieldError unused122) {
            }
            try {
                iArr2[Functions.OBJECT_WITH_ID_VISIBLE.ordinal()] = 2;
            } catch (NoSuchFieldError unused123) {
            }
            try {
                iArr2[Functions.ARDUINOANALOG.ordinal()] = 3;
            } catch (NoSuchFieldError unused124) {
            }
            try {
                iArr2[Functions.ARDUINODIGITAL.ordinal()] = 4;
            } catch (NoSuchFieldError unused125) {
            }
            try {
                iArr2[Functions.RASPIDIGITAL.ordinal()] = 5;
            } catch (NoSuchFieldError unused126) {
            }
            $EnumSwitchMapping$1 = iArr2;
        }
    }

    private FormulaElementResources() {
    }

    @JvmStatic
    public static final void addSensorsResources(Set<Integer> resources, Sensors sensor) {
        Intrinsics.checkNotNullParameter(resources, "resources");
        addDeviceSensorsResources(resources, sensor);
        addExtensionSensorsResources(resources, sensor);
        addAIExtensionSensorsResources(resources, sensor);
        addCollisionSensorsResources(resources, sensor);
    }

    @JvmStatic
    private static final void addAIExtensionSensorsResources(Set<Integer> resources, Sensors sensor) {
        int i;
        addPoseDetectionSensorsResources(resources, sensor);
        switch (sensor == null ? -1 : WhenMappings.$EnumSwitchMapping$0[sensor.ordinal()]) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
                i = 4;
                break;
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
                i = 5;
                break;
            case 16:
                i = 23;
                break;
            default:
                return;
        }
        resources.add(Integer.valueOf(i));
    }

    @JvmStatic
    private static final void addAIExtensionFunctionsResources(Set<Integer> resources, Functions function) {
        int i = function == null ? -1 : WhenMappings.$EnumSwitchMapping$1[function.ordinal()];
        if (i == 1 || i == 2) {
            resources.add(28);
        }
    }

    @JvmStatic
    private static final void addPoseDetectionSensorsResources(Set<Integer> resources, Sensors sensor) {
        switch (sensor == null ? -1 : WhenMappings.$EnumSwitchMapping$0[sensor.ordinal()]) {
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
            case 24:
            case 25:
            case 26:
            case 27:
            case 28:
            case 29:
            case 30:
            case 31:
            case 32:
            case 33:
            case 34:
            case 35:
            case 36:
            case 37:
            case 38:
            case 39:
            case 40:
            case 41:
            case 42:
            case 43:
            case 44:
            case 45:
            case 46:
            case 47:
            case 48:
            case 49:
            case 50:
            case 51:
            case 52:
            case 53:
            case 54:
            case 55:
            case 56:
            case 57:
            case 58:
            case 59:
            case 60:
            case 61:
            case 62:
            case 63:
            case 64:
            case 65:
            case 66:
            case 67:
            case 68:
            case 69:
            case 70:
            case 71:
            case 72:
            case 73:
            case 74:
            case 75:
            case 76:
            case 77:
            case 78:
            case 79:
            case 80:
            case 81:
            case 82:
            case 83:
            case 84:
            case 85:
            case 86:
                resources.add(27);
                break;
        }
    }

    @JvmStatic
    private static final void addDeviceSensorsResources(Set<Integer> resources, Sensors sensor) {
        int i;
        switch (sensor == null ? -1 : WhenMappings.$EnumSwitchMapping$0[sensor.ordinal()]) {
            case 87:
            case 88:
            case 89:
                i = 13;
                break;
            case 90:
            case 91:
                i = 14;
                break;
            case 92:
                i = 15;
                break;
            case 93:
            case 94:
            case 95:
            case 96:
                i = 18;
                break;
            case 97:
            case 98:
                i = 16;
                break;
            case 99:
                i = 24;
                break;
            default:
                return;
        }
        resources.add(Integer.valueOf(i));
    }

    @JvmStatic
    private static final void addExtensionSensorsResources(Set<Integer> resources, Sensors sensor) {
        int i;
        switch (sensor == null ? -1 : WhenMappings.$EnumSwitchMapping$0[sensor.ordinal()]) {
            case 100:
            case 101:
            case 102:
            case 103:
                i = 2;
                break;
            case 104:
            case 105:
            case 106:
            case 107:
                i = 20;
                break;
            case 108:
            case 109:
            case 110:
            case 111:
            case 112:
            case 113:
                i = 10;
                break;
            case 114:
            case 115:
            case 116:
            case 117:
            case 118:
            case 119:
                i = 22;
                break;
            default:
                return;
        }
        resources.add(Integer.valueOf(i));
    }

    @JvmStatic
    private static final void addCollisionSensorsResources(Set<Integer> resources, Sensors sensor) {
        int i = sensor == null ? -1 : WhenMappings.$EnumSwitchMapping$0[sensor.ordinal()];
        if (i == 120 || i == 121) {
            resources.add(19);
        }
    }

    @JvmStatic
    public static final void addFunctionResources(Set<Integer> resources, Functions functions) {
        int i;
        Intrinsics.checkNotNullParameter(resources, "resources");
        addAIExtensionFunctionsResources(resources, functions);
        int i2 = functions == null ? -1 : WhenMappings.$EnumSwitchMapping$1[functions.ordinal()];
        if (i2 == 3 || i2 == 4) {
            i = 6;
        } else if (i2 != 5) {
            return;
        } else {
            i = 7;
        }
        resources.add(Integer.valueOf(i));
    }
}

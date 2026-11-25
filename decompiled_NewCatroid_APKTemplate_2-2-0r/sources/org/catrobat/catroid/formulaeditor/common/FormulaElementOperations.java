package org.catrobat.catroid.formulaeditor.common;

import android.content.res.Resources;
import com.badlogic.gdx.math.Rectangle;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.CatroidApplication;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.common.LookData;
import org.catrobat.catroid.content.GroupSprite;
import org.catrobat.catroid.content.Look;
import org.catrobat.catroid.content.Project;
import org.catrobat.catroid.content.Scene;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.formulaeditor.FormulaElement;
import org.catrobat.catroid.formulaeditor.SensorHandler;
import org.catrobat.catroid.formulaeditor.Sensors;
import org.catrobat.catroid.formulaeditor.UserData;
import org.catrobat.catroid.formulaeditor.UserList;
import org.catrobat.catroid.formulaeditor.UserVariable;
import org.catrobat.catroid.nfc.NfcHandler;
import org.catrobat.catroid.sensing.CollisionDetection;
import org.catrobat.catroid.stage.StageActivity;
import org.catrobat.catroid.stage.StageListener;
import org.catrobat.catroid.utils.NumberFormats;
import org.catrobat.catroid.utils.TouchUtil;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/formulaeditor/common/FormulaElementOperations.class
  classes14.dex
 */
/* JADX INFO: compiled from: FormulaElementOperations.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000\u008c\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0006\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0002J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0007J\u0018\u0010\u000b\u001a\u00020\u00062\u0006\u0010\f\u001a\u00020\b2\u0006\u0010\r\u001a\u00020\bH\u0007J \u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00100\u000f2\u0006\u0010\u0011\u001a\u00020\u00102\b\u0010\u0012\u001a\u0004\u0018\u00010\u0013H\u0007J\u0010\u0010\u0014\u001a\u00020\u00152\b\u0010\u0016\u001a\u0004\u0018\u00010\u0017J*\u0010\u0018\u001a\u00020\b2\b\u0010\u0011\u001a\u0004\u0018\u00010\u00102\u0006\u0010\t\u001a\u00020\n2\u000e\u0010\u0019\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00100\u000fH\u0007J\u0012\u0010\u001a\u001a\u00020\u00042\b\u0010\u0016\u001a\u0004\u0018\u00010\u0017H\u0007J\u0010\u0010\u001b\u001a\u00020\u00152\b\u0010\u0016\u001a\u0004\u0018\u00010\u0017J \u0010\u001c\u001a\u00020\u00152\b\u0010\u0016\u001a\u0004\u0018\u00010\u00172\f\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u00170\u000fH\u0007J,\u0010\u001e\u001a\u00020\b2\u0006\u0010\u001f\u001a\u00020\n2\b\u0010 \u001a\u0004\u0018\u00010\u00042\u0006\u0010!\u001a\u00020\"2\b\u0010\u0012\u001a\u0004\u0018\u00010\u0013H\u0007J\u001e\u0010#\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\f\u0010$\u001a\b\u0012\u0004\u0012\u00020\n0\u000fH\u0007J\u0016\u0010%\u001a\u00020\u00012\f\u0010&\u001a\b\u0012\u0004\u0012\u00020\u00010\u000fH\u0002J*\u0010'\u001a\u00020\u00012\b\u0010(\u001a\u0004\u0018\u00010)2\u0006\u0010\u0011\u001a\u00020\u00102\u0006\u0010*\u001a\u00020\"2\u0006\u0010+\u001a\u00020,H\u0007J\u0018\u0010-\u001a\u00020\u00062\u0006\u0010\f\u001a\u00020\u00012\u0006\u0010\r\u001a\u00020\u0001H\u0007J*\u0010.\u001a\u00020\u00012\u0006\u0010\u0011\u001a\u00020\u00102\u0006\u0010*\u001a\u00020\"2\u0006\u0010+\u001a\u00020,2\b\u0010\u0005\u001a\u0004\u0018\u00010\u0004H\u0007J\u0018\u0010/\u001a\u00020\u00012\u000e\u00100\u001a\n\u0012\u0004\u0012\u00020\u0001\u0018\u000101H\u0007J\u0012\u00102\u001a\u00020\u00012\b\u00103\u001a\u0004\u0018\u000104H\u0007J\u0012\u00105\u001a\u00020\u00012\b\u00106\u001a\u0004\u0018\u000107H\u0007J\u0010\u00108\u001a\u00020\u00062\u0006\u0010\u0005\u001a\u00020\bH\u0007J\u0016\u00109\u001a\u00020\u00062\f\u0010:\u001a\b\u0012\u0004\u0012\u00020\u00040\u000fH\u0002J\u0012\u0010;\u001a\u00020\u00012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0001H\u0007J$\u0010<\u001a\u0010\u0012\f\u0012\n =*\u0004\u0018\u00010\n0\n0\u000f2\f\u0010>\u001a\b\u0012\u0004\u0012\u00020\u00100\u000fH\u0007J$\u0010?\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\b\u0010\u0012\u001a\u0004\u0018\u00010\u00132\b\u0010@\u001a\u0004\u0018\u00010AH\u0007J\u001c\u0010B\u001a\u0004\u0018\u00010\u00102\u0006\u0010C\u001a\u00020\"2\b\u0010D\u001a\u0004\u0018\u00010\u0004H\u0007J \u0010E\u001a\u00020\b2\b\u0010\u0016\u001a\u0004\u0018\u00010\u00172\f\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u00170\u000fH\u0007J,\u0010F\u001a\u00020\b2\u0006\u0010\u001f\u001a\u00020\n2\b\u0010 \u001a\u0004\u0018\u00010\u00042\u0006\u0010!\u001a\u00020\"2\b\u0010\u0012\u001a\u0004\u0018\u00010\u0013H\u0007J\u0010\u0010G\u001a\u00020\b2\u0006\u0010H\u001a\u00020\u0001H\u0007J\u001a\u0010I\u001a\u00020\u00012\u0006\u0010J\u001a\u00020K2\b\u0010L\u001a\u0004\u0018\u00010MH\u0007J\u0010\u0010N\u001a\u00020\u00152\u0006\u0010\u0005\u001a\u00020\u0001H\u0007J\u0010\u0010O\u001a\u00020\u00152\u0006\u0010\u0005\u001a\u00020\u0004H\u0003¨\u0006P"}, d2 = {"Lorg/catrobat/catroid/formulaeditor/common/FormulaElementOperations;", "", "()V", "booleanToLocalizedString", "", "value", "", "calculateCollidesWithFinger", "", "look", "Lorg/catrobat/catroid/content/Look;", "equalsDoubleIEEE754", "left", "right", "getAllClones", "", "Lorg/catrobat/catroid/content/Sprite;", "sprite", "stageListener", "Lorg/catrobat/catroid/stage/StageListener;", "getLookHeight", "", "lookData", "Lorg/catrobat/catroid/common/LookData;", "getLookLayerIndex", "spriteList", "getLookName", "getLookWidth", "getNumberOfLooks", "lookDataList", "interpretCollision", "firstLook", "secondSpriteName", "currentlyPlayingScene", "Lorg/catrobat/catroid/content/Scene;", "interpretLookCollision", "looks", "interpretMultipleItemsUserList", "userListValues", "interpretObjectSensor", "sensor", "Lorg/catrobat/catroid/formulaeditor/Sensors;", "currentlyEditedScene", "currentProject", "Lorg/catrobat/catroid/content/Project;", "interpretOperatorEqual", "interpretSensor", "interpretUserDefinedBrickInput", "userDefinedBrickInput", "Lorg/catrobat/catroid/formulaeditor/UserData;", "interpretUserList", "userList", "Lorg/catrobat/catroid/formulaeditor/UserList;", "interpretUserVariable", "userVariable", "Lorg/catrobat/catroid/formulaeditor/UserVariable;", "isInteger", "listConsistsOfSingleCharacters", "userListStringValues", "normalizeDegeneratedDoubleValues", "toLooks", JvmProtoBufUtil.PLATFORM_TYPE_ID, "sprites", "tryCalculateCollidesWithEdge", "screen", "Lcom/badlogic/gdx/math/Rectangle;", "tryFindSprite", "scene", "spriteName", "tryGetLookNumber", "tryInterpretCollision", "tryInterpretDoubleValue", "obj", "tryInterpretElementRecursive", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/catrobat/catroid/formulaeditor/FormulaElement;", "scope", "Lorg/catrobat/catroid/content/Scope;", "tryParseIntFromObject", "tryParseIntFromString", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FormulaElementOperations {
    public static final FormulaElementOperations INSTANCE = new FormulaElementOperations();

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/formulaeditor/common/FormulaElementOperations$WhenMappings.class
      classes14.dex
     */
    /* JADX INFO: compiled from: FormulaElementOperations.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[Sensors.values().length];
            try {
                iArr[Sensors.OBJECT_BRIGHTNESS.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[Sensors.OBJECT_COLOR.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[Sensors.OBJECT_TRANSPARENCY.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[Sensors.OBJECT_LAYER.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr[Sensors.MOTION_DIRECTION.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                iArr[Sensors.LOOK_DIRECTION.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                iArr[Sensors.OBJECT_SIZE.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                iArr[Sensors.OBJECT_HEIGHT.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                iArr[Sensors.OBJECT_WIDTH.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                iArr[Sensors.OBJECT_X.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                iArr[Sensors.OBJECT_Y.ordinal()] = 11;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                iArr[Sensors.OBJECT_ANGULAR_VELOCITY.ordinal()] = 12;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                iArr[Sensors.OBJECT_X_VELOCITY.ordinal()] = 13;
            } catch (NoSuchFieldError unused13) {
            }
            try {
                iArr[Sensors.OBJECT_Y_VELOCITY.ordinal()] = 14;
            } catch (NoSuchFieldError unused14) {
            }
            try {
                iArr[Sensors.OBJECT_DISTANCE_TO.ordinal()] = 15;
            } catch (NoSuchFieldError unused15) {
            }
            try {
                iArr[Sensors.OBJECT_LOOK_NUMBER.ordinal()] = 16;
            } catch (NoSuchFieldError unused16) {
            }
            try {
                iArr[Sensors.OBJECT_BACKGROUND_NUMBER.ordinal()] = 17;
            } catch (NoSuchFieldError unused17) {
            }
            try {
                iArr[Sensors.OBJECT_NUMBER_OF_LOOKS.ordinal()] = 18;
            } catch (NoSuchFieldError unused18) {
            }
            try {
                iArr[Sensors.OBJECT_LOOK_NAME.ordinal()] = 19;
            } catch (NoSuchFieldError unused19) {
            }
            try {
                iArr[Sensors.OBJECT_BACKGROUND_NAME.ordinal()] = 20;
            } catch (NoSuchFieldError unused20) {
            }
            try {
                iArr[Sensors.OBJECT_LOOK_WIDTH.ordinal()] = 21;
            } catch (NoSuchFieldError unused21) {
            }
            try {
                iArr[Sensors.OBJECT_LOOK_HEIGHT.ordinal()] = 22;
            } catch (NoSuchFieldError unused22) {
            }
            try {
                iArr[Sensors.NFC_TAG_MESSAGE.ordinal()] = 23;
            } catch (NoSuchFieldError unused23) {
            }
            try {
                iArr[Sensors.NFC_TAG_ID.ordinal()] = 24;
            } catch (NoSuchFieldError unused24) {
            }
            try {
                iArr[Sensors.OBJECT_NAME.ordinal()] = 25;
            } catch (NoSuchFieldError unused25) {
            }
            try {
                iArr[Sensors.COLLIDES_WITH_EDGE.ordinal()] = 26;
            } catch (NoSuchFieldError unused26) {
            }
            try {
                iArr[Sensors.COLLIDES_WITH_FINGER.ordinal()] = 27;
            } catch (NoSuchFieldError unused27) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    private FormulaElementOperations() {
    }

    @JvmStatic
    public static final double getLookLayerIndex(Sprite sprite, Look look, List<? extends Sprite> spriteList) {
        Intrinsics.checkNotNullParameter(look, "look");
        Intrinsics.checkNotNullParameter(spriteList, "spriteList");
        int zIndex = look.getZIndex();
        if (zIndex == 0) {
            return 0.0d;
        }
        return zIndex < 0 ? spriteList.indexOf(sprite) : ((double) zIndex) - ((double) 2);
    }

    @JvmStatic
    public static final boolean equalsDoubleIEEE754(double left, double right) {
        return (Double.isNaN(left) && Double.isNaN(right)) || (!Double.isNaN(left) && !Double.isNaN(right) && left >= right && left <= right);
    }

    @JvmStatic
    public static final boolean interpretOperatorEqual(Object left, Object right) {
        Intrinsics.checkNotNullParameter(left, "left");
        Intrinsics.checkNotNullParameter(right, "right");
        String string = left.toString();
        String string2 = right.toString();
        try {
            return equalsDoubleIEEE754(Double.parseDouble(string), Double.parseDouble(string2));
        } catch (NumberFormatException unused) {
            return Intrinsics.areEqual(string, string2);
        }
    }

    @JvmStatic
    public static final double tryInterpretDoubleValue(Object obj) {
        Double dValueOf;
        Intrinsics.checkNotNullParameter(obj, "obj");
        if (obj instanceof String) {
            try {
                dValueOf = Double.valueOf((String) obj);
            } catch (NumberFormatException unused) {
                dValueOf = Double.valueOf(Double.NaN);
            }
            Intrinsics.checkNotNull(dValueOf);
            return dValueOf.doubleValue();
        }
        return ((Double) obj).doubleValue();
    }

    @JvmStatic
    public static final Object normalizeDegeneratedDoubleValues(Object value) {
        double dDoubleValue;
        if (value instanceof String ? true : value instanceof Character) {
            return value;
        }
        if (value instanceof Boolean) {
            return Double.valueOf(Conversions.booleanToDouble(((Boolean) value).booleanValue()));
        }
        if (value instanceof Integer) {
            return Double.valueOf(((Number) value).intValue());
        }
        if (!(value instanceof Double)) {
            return Double.valueOf(0.0d);
        }
        Number number = (Number) value;
        double dDoubleValue2 = number.doubleValue();
        if (dDoubleValue2 == Double.NEGATIVE_INFINITY) {
            dDoubleValue = -1.7976931348623157E308d;
        } else {
            dDoubleValue = dDoubleValue2 == Double.POSITIVE_INFINITY ? Double.MAX_VALUE : number.doubleValue();
        }
        return Double.valueOf(dDoubleValue);
    }

    @JvmStatic
    public static final boolean isInteger(double value) {
        return (Double.isInfinite(value) || Double.isNaN(value) || value != Math.rint(value)) ? false : true;
    }

    @JvmStatic
    public static final double tryGetLookNumber(LookData lookData, List<? extends LookData> lookDataList) {
        Intrinsics.checkNotNullParameter(lookDataList, "lookDataList");
        if (lookData != null) {
            return 1.0d + ((double) lookDataList.indexOf(lookData));
        }
        return 1.0d;
    }

    @JvmStatic
    public static final String getLookName(LookData lookData) {
        String str = lookData != null ? lookData.f512name : null;
        return str == null ? "" : str;
    }

    @JvmStatic
    public static final int getNumberOfLooks(LookData lookData, List<? extends LookData> lookDataList) {
        Intrinsics.checkNotNullParameter(lookDataList, "lookDataList");
        if (lookData != null && lookData.getIsWebRequest()) {
            return lookDataList.size() + 1;
        }
        return lookDataList.size();
    }

    @JvmStatic
    public static final double tryCalculateCollidesWithEdge(Look look, StageListener stageListener, Rectangle screen) {
        Intrinsics.checkNotNullParameter(look, "look");
        if (stageListener == null || !stageListener.firstFrameDrawn) {
            return 0.0d;
        }
        return Conversions.booleanToDouble(CollisionDetection.collidesWithEdge(look.getCurrentCollisionPolygon(), screen));
    }

    @JvmStatic
    public static final double calculateCollidesWithFinger(Look look) {
        Intrinsics.checkNotNullParameter(look, "look");
        return CollisionDetection.collidesWithFinger(look.getCurrentCollisionPolygon(), TouchUtil.getCurrentTouchingPoints());
    }

    public final int getLookWidth(LookData lookData) {
        if (lookData == null) {
            return 0;
        }
        int[] measure = lookData.getMeasure();
        Intrinsics.checkNotNullExpressionValue(measure, "getMeasure(...)");
        return measure[0];
    }

    public final int getLookHeight(LookData lookData) {
        if (lookData == null) {
            return 0;
        }
        int[] measure = lookData.getMeasure();
        Intrinsics.checkNotNullExpressionValue(measure, "getMeasure(...)");
        return measure[1];
    }

    @JvmStatic
    public static final Object interpretObjectSensor(Sensors sensor, Sprite sprite, Scene currentlyEditedScene, Project currentProject) {
        Object objValueOf;
        Intrinsics.checkNotNullParameter(sprite, "sprite");
        Intrinsics.checkNotNullParameter(currentlyEditedScene, "currentlyEditedScene");
        Intrinsics.checkNotNullParameter(currentProject, "currentProject");
        Look look = sprite.look;
        List<LookData> lookList = sprite.getLookList();
        LookData lookData = look.getLookData();
        if (lookData == null) {
            Intrinsics.checkNotNull(lookList);
            lookData = !lookList.isEmpty() ? lookList.get(0) : null;
        }
        switch (sensor == null ? -1 : WhenMappings.$EnumSwitchMapping$0[sensor.ordinal()]) {
            case 1:
                objValueOf = Double.valueOf(look.getBrightnessInUserInterfaceDimensionUnit());
                break;
            case 2:
                objValueOf = Double.valueOf(look.getColorInUserInterfaceDimensionUnit());
                break;
            case 3:
                objValueOf = Double.valueOf(look.getTransparencyInUserInterfaceDimensionUnit());
                break;
            case 4:
                Intrinsics.checkNotNull(look);
                List<Sprite> spriteList = currentlyEditedScene.getSpriteList();
                Intrinsics.checkNotNullExpressionValue(spriteList, "getSpriteList(...)");
                objValueOf = Double.valueOf(getLookLayerIndex(sprite, look, spriteList));
                break;
            case 5:
                objValueOf = Double.valueOf(look.getMotionDirectionInUserInterfaceDimensionUnit());
                break;
            case 6:
                objValueOf = Double.valueOf(look.getLookDirectionInUserInterfaceDimensionUnit());
                break;
            case 7:
                objValueOf = Double.valueOf(look.getSizeInUserInterfaceDimensionUnit());
                break;
            case 8:
                try {
                    objValueOf = Double.valueOf((((double) look.getHeight()) * ((double) 100)) / ((double) INSTANCE.getLookHeight(lookData)));
                } catch (Exception unused) {
                    return 0;
                }
                break;
            case 9:
                try {
                    objValueOf = Double.valueOf((((double) look.getWidth()) * ((double) 100)) / ((double) INSTANCE.getLookWidth(lookData)));
                } catch (Exception unused2) {
                    return 0;
                }
                break;
            case 10:
                objValueOf = Double.valueOf(look.getXInUserInterfaceDimensionUnit());
                break;
            case 11:
                objValueOf = Double.valueOf(look.getYInUserInterfaceDimensionUnit());
                break;
            case 12:
                objValueOf = Double.valueOf(look.getAngularVelocityInUserInterfaceDimensionUnit());
                break;
            case 13:
                objValueOf = Double.valueOf(look.getXVelocityInUserInterfaceDimensionUnit());
                break;
            case 14:
                objValueOf = Double.valueOf(look.getYVelocityInUserInterfaceDimensionUnit());
                break;
            case 15:
                objValueOf = Double.valueOf(look.getDistanceToTouchPositionInUserInterfaceDimensions());
                break;
            case 16:
            case 17:
                Intrinsics.checkNotNull(lookList);
                objValueOf = Double.valueOf(tryGetLookNumber(lookData, lookList));
                break;
            case 18:
                Intrinsics.checkNotNull(lookList);
                objValueOf = Double.valueOf(getNumberOfLooks(lookData, lookList));
                break;
            case 19:
            case 20:
                objValueOf = getLookName(lookData);
                break;
            case 21:
                objValueOf = Integer.valueOf(INSTANCE.getLookWidth(lookData));
                break;
            case 22:
                objValueOf = Integer.valueOf(INSTANCE.getLookHeight(lookData));
                break;
            case 23:
                objValueOf = NfcHandler.getLastNfcTagMessage();
                break;
            case 24:
                objValueOf = NfcHandler.getLastNfcTagId();
                break;
            case 25:
                objValueOf = sprite.getName();
                break;
            case 26:
                Intrinsics.checkNotNull(look);
                StageActivity stageActivity = StageActivity.activeStageActivity.get();
                objValueOf = Double.valueOf(tryCalculateCollidesWithEdge(look, stageActivity != null ? stageActivity.stageListener : null, currentProject.getScreenRectangle()));
                break;
            case 27:
                Intrinsics.checkNotNull(look);
                objValueOf = Double.valueOf(calculateCollidesWithFinger(look));
                break;
            default:
                objValueOf = Double.valueOf(0.0d);
                break;
        }
        Intrinsics.checkNotNull(objValueOf);
        return objValueOf;
    }

    @JvmStatic
    public static final List<Sprite> getAllClones(Sprite sprite, StageListener stageListener) {
        Intrinsics.checkNotNullParameter(sprite, "sprite");
        if (stageListener != null) {
            List listListOf = CollectionsKt.listOf(sprite);
            List<Sprite> allClonesOfSprite = stageListener.getAllClonesOfSprite(sprite);
            Intrinsics.checkNotNullExpressionValue(allClonesOfSprite, "getAllClonesOfSprite(...)");
            List<Sprite> listPlus = CollectionsKt.plus((Collection) listListOf, (Iterable) allClonesOfSprite);
            if (listPlus != null) {
                return listPlus;
            }
        }
        return CollectionsKt.listOf(sprite);
    }

    @JvmStatic
    public static final Object interpretUserList(UserList userList) {
        Object objInterpretMultipleItemsUserList;
        if (userList == null) {
            objInterpretMultipleItemsUserList = null;
        } else if (userList.getValue().isEmpty()) {
            objInterpretMultipleItemsUserList = "";
        } else if (userList.getValue().size() == 1) {
            objInterpretMultipleItemsUserList = userList.getValue().get(0);
        } else {
            FormulaElementOperations formulaElementOperations = INSTANCE;
            List<? extends Object> value = userList.getValue();
            Intrinsics.checkNotNullExpressionValue(value, "getValue(...)");
            objInterpretMultipleItemsUserList = formulaElementOperations.interpretMultipleItemsUserList(value);
        }
        return objInterpretMultipleItemsUserList == null ? Double.valueOf(0.0d) : objInterpretMultipleItemsUserList;
    }

    private final String booleanToLocalizedString(boolean value) {
        if (value) {
            String string = CatroidApplication.getAppContext().getString(R.string.formula_editor_true);
            Intrinsics.checkNotNull(string);
            return string;
        }
        String string2 = CatroidApplication.getAppContext().getString(R.string.formula_editor_false);
        Intrinsics.checkNotNull(string2);
        return string2;
    }

    private final Object interpretMultipleItemsUserList(List<? extends Object> userListValues) {
        String string;
        ProjectManager projectManager = ProjectManager.getInstance();
        Project currentProject = projectManager != null ? projectManager.getCurrentProject() : null;
        List<? extends Object> list = userListValues;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        for (Object obj : list) {
            if (currentProject != null) {
                string = currentProject.formatValue(obj);
            } else if (obj instanceof Integer) {
                string = obj.toString();
            } else if (obj instanceof Double) {
                string = obj.toString();
            } else if (obj instanceof Boolean) {
                string = INSTANCE.booleanToLocalizedString(((Boolean) obj).booleanValue());
            } else if (obj instanceof Character) {
                string = obj.toString();
            } else {
                Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.String");
                string = (String) obj;
            }
            arrayList.add(NumberFormats.INSTANCE.trimTrailingCharacters(string));
        }
        ArrayList arrayList2 = arrayList;
        StringBuilder sb = new StringBuilder(arrayList2.size());
        String str = listConsistsOfSingleCharacters(arrayList2) ? "" : " ";
        Iterator<String> it = arrayList2.iterator();
        while (it.hasNext()) {
            sb.append(NumberFormats.INSTANCE.trimTrailingCharacters(it.next()));
            sb.append(str);
        }
        String string2 = sb.toString();
        Intrinsics.checkNotNullExpressionValue(string2, "toString(...)");
        String str2 = string2;
        int length = str2.length() - 1;
        int i = 0;
        boolean z = false;
        while (i <= length) {
            boolean z2 = Intrinsics.compare((int) str2.charAt(!z ? i : length), 32) <= 0;
            if (z) {
                if (!z2) {
                    break;
                }
                length--;
            } else if (z2) {
                i++;
            } else {
                z = true;
            }
        }
        return str2.subSequence(i, length + 1).toString();
    }

    private final boolean listConsistsOfSingleCharacters(List<String> userListStringValues) {
        List<String> list = userListStringValues;
        if ((list instanceof Collection) && list.isEmpty()) {
            return true;
        }
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            if (((String) it.next()).length() > 1) {
                return false;
            }
        }
        return true;
    }

    @JvmStatic
    public static final Object interpretUserVariable(UserVariable userVariable) {
        Object value = userVariable != null ? userVariable.getValue() : null;
        return value == null ? Double.valueOf(0.0d) : value;
    }

    @JvmStatic
    public static final Object interpretUserDefinedBrickInput(UserData<Object> userDefinedBrickInput) {
        Object objInterpretUserList;
        if (userDefinedBrickInput != null) {
            if (userDefinedBrickInput instanceof UserVariable) {
                objInterpretUserList = interpretUserVariable((UserVariable) userDefinedBrickInput);
            } else {
                objInterpretUserList = userDefinedBrickInput instanceof UserList ? interpretUserList((UserList) userDefinedBrickInput) : 0;
            }
            if (objInterpretUserList != null) {
                return objInterpretUserList;
            }
        }
        return Double.valueOf(0.0d);
    }

    @JvmStatic
    public static final double interpretLookCollision(Look look, List<? extends Look> looks) {
        Intrinsics.checkNotNullParameter(look, "look");
        Intrinsics.checkNotNullParameter(looks, "looks");
        List<? extends Look> list = looks;
        boolean z = false;
        if (!(list instanceof Collection) || !list.isEmpty()) {
            for (Look look2 : list) {
                if (!Intrinsics.areEqual(look, look2) && CollisionDetection.checkCollisionBetweenLooks(look, look2)) {
                    z = true;
                    break;
                }
            }
        }
        return Conversions.booleanToDouble(z);
    }

    @JvmStatic
    public static final List<Look> toLooks(List<? extends Sprite> sprites) {
        Intrinsics.checkNotNullParameter(sprites, "sprites");
        List<? extends Sprite> list = sprites;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(((Sprite) it.next()).look);
        }
        return arrayList;
    }

    @JvmStatic
    public static final Object interpretSensor(Sprite sprite, Scene currentlyEditedScene, Project currentProject, String value) {
        Intrinsics.checkNotNullParameter(sprite, "sprite");
        Intrinsics.checkNotNullParameter(currentlyEditedScene, "currentlyEditedScene");
        Intrinsics.checkNotNullParameter(currentProject, "currentProject");
        Sensors sensorByValue = Sensors.getSensorByValue(value);
        if (sensorByValue.isObjectSensor) {
            return interpretObjectSensor(sensorByValue, sprite, currentlyEditedScene, currentProject);
        }
        Object sensorValue = SensorHandler.getSensorValue(sensorByValue);
        Intrinsics.checkNotNull(sensorValue);
        return sensorValue;
    }

    @JvmStatic
    public static final Sprite tryFindSprite(Scene scene, String spriteName) {
        Intrinsics.checkNotNullParameter(scene, "scene");
        try {
            return scene.getSprite(spriteName);
        } catch (Resources.NotFoundException unused) {
            return null;
        }
    }

    @JvmStatic
    public static final double interpretCollision(Look firstLook, String secondSpriteName, Scene currentlyPlayingScene, StageListener stageListener) {
        List<Sprite> allClones;
        Intrinsics.checkNotNullParameter(firstLook, "firstLook");
        Intrinsics.checkNotNullParameter(currentlyPlayingScene, "currentlyPlayingScene");
        Sprite spriteTryFindSprite = tryFindSprite(currentlyPlayingScene, secondSpriteName);
        if (spriteTryFindSprite == null) {
            return 0.0d;
        }
        if (spriteTryFindSprite instanceof GroupSprite) {
            allClones = GroupSprite.getSpritesFromGroupWithGroupName(secondSpriteName, currentlyPlayingScene.getSpriteList());
        } else {
            allClones = getAllClones(spriteTryFindSprite, stageListener);
        }
        Intrinsics.checkNotNull(allClones);
        return interpretLookCollision(firstLook, toLooks(allClones));
    }

    @JvmStatic
    public static final double tryInterpretCollision(Look firstLook, String secondSpriteName, Scene currentlyPlayingScene, StageListener stageListener) {
        Intrinsics.checkNotNullParameter(firstLook, "firstLook");
        Intrinsics.checkNotNullParameter(currentlyPlayingScene, "currentlyPlayingScene");
        try {
            return interpretCollision(firstLook, secondSpriteName, currentlyPlayingScene, stageListener);
        } catch (Exception unused) {
            return 0.0d;
        }
    }

    @JvmStatic
    public static final Object tryInterpretElementRecursive(FormulaElement element, Scope scope) {
        Object objValueOf;
        Intrinsics.checkNotNullParameter(element, "element");
        try {
            objValueOf = element.interpretRecursive(scope);
        } catch (NumberFormatException unused) {
            objValueOf = Double.valueOf(Double.NaN);
        }
        Intrinsics.checkNotNull(objValueOf);
        return objValueOf;
    }

    @JvmStatic
    public static final int tryParseIntFromObject(Object value) {
        Intrinsics.checkNotNullParameter(value, "value");
        return value instanceof String ? tryParseIntFromString((String) value) : (int) ((Double) value).doubleValue();
    }

    @JvmStatic
    private static final int tryParseIntFromString(String value) {
        try {
            return (int) Double.valueOf(value).doubleValue();
        } catch (NumberFormatException unused) {
            return 0;
        }
    }
}

package org.catrobat.catroid.utils.lunoscript;

import androidx.exifinterface.media.ExifInterface;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KClass;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/utils/lunoscript/InterpreterKt.class
  classes14.dex
 */
/* JADX INFO: compiled from: Interpreter.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u00004\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0002\u001a.\u0010\u0000\u001a\u0002H\u0001\"\n\b\u0000\u0010\u0001\u0018\u0001*\u00020\u0002*\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H\u0086\b¢\u0006\u0002\u0010\b*v\u0010\t\"8\u0012\u0013\u0012\u00110\u000b¢\u0006\f\b\f\u0012\b\b\r\u0012\u0004\b\b(\u000e\u0012\u0019\u0012\u0017\u0012\u0004\u0012\u00020\u00030\u000f¢\u0006\f\b\f\u0012\b\b\r\u0012\u0004\b\b(\u0010\u0012\u0004\u0012\u00020\u00030\n28\u0012\u0013\u0012\u00110\u000b¢\u0006\f\b\f\u0012\b\b\r\u0012\u0004\b\b(\u000e\u0012\u0019\u0012\u0017\u0012\u0004\u0012\u00020\u00030\u000f¢\u0006\f\b\f\u0012\b\b\r\u0012\u0004\b\b(\u0010\u0012\u0004\u0012\u00020\u00030\n¨\u0006\u0011"}, d2 = {"asSpecificKotlinType", ExifInterface.GPS_DIRECTION_TRUE, "", "Lorg/catrobat/catroid/utils/lunoscript/LunoValue;", "functionNameForError", "", "argPositionForError", "", "(Lorg/catrobat/catroid/utils/lunoscript/LunoValue;Ljava/lang/String;I)Ljava/lang/Object;", "RawNativeLunoFunction", "Lkotlin/Function2;", "Lorg/catrobat/catroid/utils/lunoscript/Interpreter;", "Lkotlin/ParameterName;", "name", "interpreter", "", "arguments", "catroid_apktemplateTemplateRelease"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class InterpreterKt {
    /* JADX WARN: Code duplicated, block: B:10:0x0046  */
    public static final /* synthetic */ <T> T asSpecificKotlinType(LunoValue lunoValue, String functionNameForError, int i) {
        T t;
        Class<?> cls;
        Intrinsics.checkNotNullParameter(lunoValue, "<this>");
        Intrinsics.checkNotNullParameter(functionNameForError, "functionNameForError");
        Intrinsics.reifiedOperationMarker(4, ExifInterface.GPS_DIRECTION_TRUE);
        KClass orCreateKotlinClass = Reflection.getOrCreateKotlinClass(Object.class);
        String simpleName = null;
        if (Intrinsics.areEqual(orCreateKotlinClass, Reflection.getOrCreateKotlinClass(Double.TYPE))) {
            if (lunoValue instanceof LunoValue.Number) {
                t = (T) Double.valueOf(((LunoValue.Number) lunoValue).getValue());
            } else if (lunoValue instanceof LunoValue.Float) {
                t = (T) Double.valueOf(((LunoValue.Float) lunoValue).getValue());
            } else {
                t = null;
            }
        } else if (Intrinsics.areEqual(orCreateKotlinClass, Reflection.getOrCreateKotlinClass(Float.TYPE))) {
            if (lunoValue instanceof LunoValue.Float) {
                t = (T) Float.valueOf(((LunoValue.Float) lunoValue).getValue());
            } else if (lunoValue instanceof LunoValue.Number) {
                t = (T) Float.valueOf((float) ((LunoValue.Number) lunoValue).getValue());
            } else {
                t = null;
            }
        } else if (Intrinsics.areEqual(orCreateKotlinClass, Reflection.getOrCreateKotlinClass(Integer.TYPE))) {
            if (lunoValue instanceof LunoValue.Number) {
                t = (T) Integer.valueOf((int) ((LunoValue.Number) lunoValue).getValue());
            } else if (lunoValue instanceof LunoValue.Float) {
                t = (T) Integer.valueOf((int) ((LunoValue.Float) lunoValue).getValue());
            } else {
                t = null;
            }
        } else if (Intrinsics.areEqual(orCreateKotlinClass, Reflection.getOrCreateKotlinClass(String.class))) {
            LunoValue.String string = lunoValue instanceof LunoValue.String ? (LunoValue.String) lunoValue : null;
            if (string != null) {
                t = (T) string.getValue();
            } else {
                t = null;
            }
        } else if (Intrinsics.areEqual(orCreateKotlinClass, Reflection.getOrCreateKotlinClass(Boolean.TYPE))) {
            LunoValue.Boolean r2 = lunoValue instanceof LunoValue.Boolean ? (LunoValue.Boolean) lunoValue : null;
            if (r2 != null) {
                t = (T) Boolean.valueOf(r2.getValue());
            } else {
                t = null;
            }
        } else {
            LunoValue.NativeObject nativeObject = lunoValue instanceof LunoValue.NativeObject ? (LunoValue.NativeObject) lunoValue : null;
            if (nativeObject != null) {
                t = (T) nativeObject.getObj();
            } else {
                t = null;
            }
        }
        Intrinsics.reifiedOperationMarker(3, ExifInterface.GPS_DIRECTION_TRUE);
        if (t instanceof Object) {
            return t;
        }
        if (lunoValue instanceof LunoValue.NativeObject) {
            Object obj = ((LunoValue.NativeObject) lunoValue).getObj();
            if (obj != null && (cls = obj.getClass()) != null) {
                simpleName = cls.getName();
            }
            if (simpleName == null) {
                simpleName = "null native object";
            }
        } else {
            simpleName = Reflection.getOrCreateKotlinClass(lunoValue.getClass()).getSimpleName();
        }
        Intrinsics.reifiedOperationMarker(4, ExifInterface.GPS_DIRECTION_TRUE);
        throw new LunoRuntimeError(functionNameForError + ": Argument " + i + " expected to contain a Kotlin type '" + Object.class.getSimpleName() + "', but got '" + simpleName + "'.", -1, null, 4, null);
    }
}

package org.jetbrains.kotlin.load.kotlin;

import androidx.exifinterface.media.ExifInterface;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.jvm.JavaToKotlinClassMap;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqNameUnsafe;
import org.jetbrains.kotlin.resolve.jvm.JvmClassName;

/* JADX INFO: compiled from: SignatureBuildingComponents.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0000\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a0\u0010\u0005\u001a\u0002H\u0006\"\u0004\b\u0000\u0010\u00062\u0017\u0010\u0007\u001a\u0013\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u0002H\u00060\b¢\u0006\u0002\b\nH\u0086\bø\u0001\u0000¢\u0006\u0002\u0010\u000b\"\u0015\u0010\u0000\u001a\u00020\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006\f"}, d2 = {"internalName", "", "Lorg/jetbrains/kotlin/name/ClassId;", "getInternalName", "(Lorg/jetbrains/kotlin/name/ClassId;)Ljava/lang/String;", "signatures", ExifInterface.GPS_DIRECTION_TRUE, "block", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/load/kotlin/SignatureBuildingComponents;", "Lkotlin/ExtensionFunctionType;", "(Lkotlin/jvm/functions/Function1;)Ljava/lang/Object;", "compiler.common.jvm"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class SignatureBuildingComponentsKt {
    public static final <T> T signatures(Function1<? super SignatureBuildingComponents, ? extends T> block) {
        Intrinsics.checkNotNullParameter(block, "block");
        return block.invoke(SignatureBuildingComponents.INSTANCE);
    }

    public static final String getInternalName(ClassId classId) {
        Intrinsics.checkNotNullParameter(classId, "<this>");
        JavaToKotlinClassMap javaToKotlinClassMap = JavaToKotlinClassMap.INSTANCE;
        FqNameUnsafe unsafe = classId.asSingleFqName().toUnsafe();
        Intrinsics.checkNotNullExpressionValue(unsafe, "toUnsafe(...)");
        ClassId classIdMapKotlinToJava = javaToKotlinClassMap.mapKotlinToJava(unsafe);
        if (classIdMapKotlinToJava != null) {
            classId = classIdMapKotlinToJava;
        }
        String internalName = JvmClassName.byClassId(classId).getInternalName();
        Intrinsics.checkNotNullExpressionValue(internalName, "getInternalName(...)");
        return internalName;
    }
}

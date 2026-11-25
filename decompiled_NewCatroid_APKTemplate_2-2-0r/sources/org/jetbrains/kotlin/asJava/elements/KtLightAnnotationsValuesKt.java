package org.jetbrains.kotlin.asJava.elements;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.jvm.JavaToKotlinClassMap;
import org.jetbrains.kotlin.com.intellij.psi.PsiArrayType;
import org.jetbrains.kotlin.com.intellij.psi.PsiClassType;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiPrimitiveType;
import org.jetbrains.kotlin.com.intellij.psi.PsiType;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.FqNameUnsafe;

/* JADX INFO: compiled from: KtLightAnnotationsValues.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\u001a \u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u0007¨\u0006\b"}, d2 = {"psiType", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiType;", "kotlinFqName", "", "context", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "boxPrimitiveType", "", "light-classes-base"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class KtLightAnnotationsValuesKt {
    public static /* synthetic */ PsiType psiType$default(String str, PsiElement psiElement, boolean z, int i, Object obj) {
        if ((i & 4) != 0) {
            z = false;
        }
        return psiType(str, psiElement, z);
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    public static final PsiType psiType(String kotlinFqName, PsiElement context, boolean z) {
        FqName fqNameAsSingleFqName;
        String strAsString;
        Intrinsics.checkNotNullParameter(kotlinFqName, "kotlinFqName");
        Intrinsics.checkNotNullParameter(context, "context");
        if (!z) {
            switch (kotlinFqName.hashCode()) {
                case -2133280414:
                    if (kotlinFqName.equals("kotlin.Int")) {
                        PsiPrimitiveType INT = PsiType.INT;
                        Intrinsics.checkNotNullExpressionValue(INT, "INT");
                        return INT;
                    }
                    break;
                case -1707381259:
                    if (kotlinFqName.equals("kotlin.Byte")) {
                        PsiPrimitiveType BYTE = PsiType.BYTE;
                        Intrinsics.checkNotNullExpressionValue(BYTE, "BYTE");
                        return BYTE;
                    }
                    break;
                case -1707368381:
                    if (kotlinFqName.equals("kotlin.Char")) {
                        PsiPrimitiveType CHAR = PsiType.CHAR;
                        Intrinsics.checkNotNullExpressionValue(CHAR, "CHAR");
                        return CHAR;
                    }
                    break;
                case -1707093143:
                    if (kotlinFqName.equals("kotlin.Long")) {
                        PsiPrimitiveType LONG = PsiType.LONG;
                        Intrinsics.checkNotNullExpressionValue(LONG, "LONG");
                        return LONG;
                    }
                    break;
                case -1385909489:
                    if (kotlinFqName.equals("kotlin.Float")) {
                        PsiPrimitiveType FLOAT = PsiType.FLOAT;
                        Intrinsics.checkNotNullExpressionValue(FLOAT, "FLOAT");
                        return FLOAT;
                    }
                    break;
                case -1374022353:
                    if (kotlinFqName.equals("kotlin.Short")) {
                        PsiPrimitiveType SHORT = PsiType.SHORT;
                        Intrinsics.checkNotNullExpressionValue(SHORT, "SHORT");
                        return SHORT;
                    }
                    break;
                case -67829378:
                    if (kotlinFqName.equals("kotlin.Double")) {
                        PsiPrimitiveType DOUBLE = PsiType.DOUBLE;
                        Intrinsics.checkNotNullExpressionValue(DOUBLE, "DOUBLE");
                        return DOUBLE;
                    }
                    break;
                case 411999259:
                    if (kotlinFqName.equals("kotlin.Boolean")) {
                        PsiPrimitiveType BOOLEAN = PsiType.BOOLEAN;
                        Intrinsics.checkNotNullExpressionValue(BOOLEAN, "BOOLEAN");
                        return BOOLEAN;
                    }
                    break;
            }
        }
        switch (kotlinFqName.hashCode()) {
            case -1581820393:
                if (kotlinFqName.equals("kotlin.IntArray")) {
                    PsiArrayType psiArrayTypeCreateArrayType = PsiType.INT.createArrayType();
                    Intrinsics.checkNotNullExpressionValue(psiArrayTypeCreateArrayType, "createArrayType(...)");
                    return psiArrayTypeCreateArrayType;
                }
                break;
            case -1577754486:
                if (kotlinFqName.equals("kotlin.FloatArray")) {
                    PsiArrayType psiArrayTypeCreateArrayType2 = PsiType.FLOAT.createArrayType();
                    Intrinsics.checkNotNullExpressionValue(psiArrayTypeCreateArrayType2, "createArrayType(...)");
                    return psiArrayTypeCreateArrayType2;
                }
                break;
            case -1163761552:
                if (kotlinFqName.equals("kotlin.LongArray")) {
                    PsiArrayType psiArrayTypeCreateArrayType3 = PsiType.LONG.createArrayType();
                    Intrinsics.checkNotNullExpressionValue(psiArrayTypeCreateArrayType3, "createArrayType(...)");
                    return psiArrayTypeCreateArrayType3;
                }
                break;
            case -993017989:
                if (kotlinFqName.equals("kotlin.DoubleArray")) {
                    PsiArrayType psiArrayTypeCreateArrayType4 = PsiType.DOUBLE.createArrayType();
                    Intrinsics.checkNotNullExpressionValue(psiArrayTypeCreateArrayType4, "createArrayType(...)");
                    return psiArrayTypeCreateArrayType4;
                }
                break;
            case 270963670:
                if (kotlinFqName.equals("kotlin.CharArray")) {
                    PsiArrayType psiArrayTypeCreateArrayType5 = PsiType.CHAR.createArrayType();
                    Intrinsics.checkNotNullExpressionValue(psiArrayTypeCreateArrayType5, "createArrayType(...)");
                    return psiArrayTypeCreateArrayType5;
                }
                break;
            case 951944548:
                if (kotlinFqName.equals("kotlin.ByteArray")) {
                    PsiArrayType psiArrayTypeCreateArrayType6 = PsiType.BYTE.createArrayType();
                    Intrinsics.checkNotNullExpressionValue(psiArrayTypeCreateArrayType6, "createArrayType(...)");
                    return psiArrayTypeCreateArrayType6;
                }
                break;
            case 1005081194:
                if (kotlinFqName.equals("kotlin.ShortArray")) {
                    PsiArrayType psiArrayTypeCreateArrayType7 = PsiType.SHORT.createArrayType();
                    Intrinsics.checkNotNullExpressionValue(psiArrayTypeCreateArrayType7, "createArrayType(...)");
                    return psiArrayTypeCreateArrayType7;
                }
                break;
            case 1980710654:
                if (kotlinFqName.equals("kotlin.BooleanArray")) {
                    PsiArrayType psiArrayTypeCreateArrayType8 = PsiType.BOOLEAN.createArrayType();
                    Intrinsics.checkNotNullExpressionValue(psiArrayTypeCreateArrayType8, "createArrayType(...)");
                    return psiArrayTypeCreateArrayType8;
                }
                break;
        }
        ClassId classIdMapKotlinToJava = JavaToKotlinClassMap.INSTANCE.mapKotlinToJava(new FqNameUnsafe(kotlinFqName));
        if (classIdMapKotlinToJava != null && (fqNameAsSingleFqName = classIdMapKotlinToJava.asSingleFqName()) != null && (strAsString = fqNameAsSingleFqName.asString()) != null) {
            kotlinFqName = strAsString;
        }
        PsiClassType typeByName = PsiType.getTypeByName(kotlinFqName, context.getProject(), context.getResolveScope());
        Intrinsics.checkNotNullExpressionValue(typeByName, "getTypeByName(...)");
        return typeByName;
    }
}

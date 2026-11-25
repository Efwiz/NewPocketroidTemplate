package org.jetbrains.kotlin.fir.deserialization;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.name.CallableId;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: FirConstDeserializer.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000,\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a,\u0010\u0000\u001a\u0004\u0018\u00010\u00012\b\u0010\u0002\u001a\u0004\u0018\u00010\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t\u001a\u0012\u0010\n\u001a\u00020\u000b*\u00020\u000b2\u0006\u0010\f\u001a\u00020\r¨\u0006\u000e"}, d2 = {"buildFirConstant", "Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "protoValue", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Annotation$Argument$Value;", "sourceValue", "", "constKind", "", "nameResolver", "Lorg/jetbrains/kotlin/metadata/deserialization/NameResolver;", "replaceName", "Lorg/jetbrains/kotlin/name/CallableId;", "newName", "Lorg/jetbrains/kotlin/name/Name;", "fir-deserialization"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class FirConstDeserializerKt {
    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:100:0x0228, code lost:
    
        r2 = r9;
     */
    /* JADX WARN: Code restructure failed: missing block: B:101:0x022a, code lost:
    
        kotlin.jvm.internal.Intrinsics.checkNotNull(r10, "null cannot be cast to non-null type kotlin.String");
        r2 = (java.lang.String) r10;
     */
    /* JADX WARN: Code restructure failed: missing block: B:105:?, code lost:
    
        return org.jetbrains.kotlin.fir.expressions.builder.FirConstExpressionBuilderKt.buildConstExpression$default(null, r1, java.lang.Boolean.valueOf(!kotlin.jvm.internal.Intrinsics.areEqual(r10, (java.lang.Object) 0)), null, true, 8, null);
     */
    /* JADX WARN: Code restructure failed: missing block: B:106:?, code lost:
    
        return org.jetbrains.kotlin.fir.expressions.builder.FirConstExpressionBuilderKt.buildConstExpression$default(null, r3, java.lang.Short.valueOf(((java.lang.Number) r10).shortValue()), null, true, 8, null);
     */
    /* JADX WARN: Code restructure failed: missing block: B:107:?, code lost:
    
        return org.jetbrains.kotlin.fir.expressions.builder.FirConstExpressionBuilderKt.buildConstExpression$default(null, r1, java.lang.Long.valueOf(r9), null, true, 8, null);
     */
    /* JADX WARN: Code restructure failed: missing block: B:108:?, code lost:
    
        return org.jetbrains.kotlin.fir.expressions.builder.FirConstExpressionBuilderKt.buildConstExpression$default(null, r1, java.lang.Integer.valueOf(r9), null, true, 8, null);
     */
    /* JADX WARN: Code restructure failed: missing block: B:109:?, code lost:
    
        return org.jetbrains.kotlin.fir.expressions.builder.FirConstExpressionBuilderKt.buildConstExpression$default(null, r1, java.lang.Float.valueOf(r9), null, true, 8, null);
     */
    /* JADX WARN: Code restructure failed: missing block: B:110:?, code lost:
    
        return org.jetbrains.kotlin.fir.expressions.builder.FirConstExpressionBuilderKt.buildConstExpression$default(null, r1, java.lang.Double.valueOf(r9), null, true, 8, null);
     */
    /* JADX WARN: Code restructure failed: missing block: B:111:?, code lost:
    
        return org.jetbrains.kotlin.fir.expressions.builder.FirConstExpressionBuilderKt.buildConstExpression$default(null, r3, java.lang.Character.valueOf((char) ((java.lang.Number) r10).intValue()), null, true, 8, null);
     */
    /* JADX WARN: Code restructure failed: missing block: B:112:?, code lost:
    
        return org.jetbrains.kotlin.fir.expressions.builder.FirConstExpressionBuilderKt.buildConstExpression$default(null, r3, java.lang.Byte.valueOf(((java.lang.Number) r10).byteValue()), null, true, 8, null);
     */
    /* JADX WARN: Code restructure failed: missing block: B:113:?, code lost:
    
        return org.jetbrains.kotlin.fir.expressions.builder.FirConstExpressionBuilderKt.buildConstExpression$default(null, r1, r2, null, true, 8, null);
     */
    /* JADX WARN: Code restructure failed: missing block: B:12:0x002f, code lost:
    
        if (r11.equals("BOOLEAN") == false) goto L103;
     */
    /* JADX WARN: Code restructure failed: missing block: B:15:0x0039, code lost:
    
        if (r11.equals("SHORT") == false) goto L103;
     */
    /* JADX WARN: Code restructure failed: missing block: B:18:0x0043, code lost:
    
        if (r11.equals("FLOAT") == false) goto L103;
     */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x004d, code lost:
    
        if (r11.equals("LONG") == false) goto L103;
     */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x0057, code lost:
    
        if (r11.equals("CHAR") == false) goto L103;
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x0061, code lost:
    
        if (r11.equals("BYTE") == false) goto L103;
     */
    /* JADX WARN: Code restructure failed: missing block: B:30:0x006b, code lost:
    
        if (r11.equals("INT") == false) goto L103;
     */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x0075, code lost:
    
        if (r11.equals("Z") == false) goto L103;
     */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x0079, code lost:
    
        r1 = org.jetbrains.kotlin.types.ConstantValueKind.Boolean.INSTANCE;
     */
    /* JADX WARN: Code restructure failed: missing block: B:36:0x007e, code lost:
    
        if (r9 == null) goto L38;
     */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x0080, code lost:
    
        r10 = java.lang.Integer.valueOf((int) r9.getIntValue());
     */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x00ac, code lost:
    
        if (r11.equals(androidx.exifinterface.media.ExifInterface.LATITUDE_SOUTH) == false) goto L103;
     */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x00b0, code lost:
    
        r3 = org.jetbrains.kotlin.types.ConstantValueKind.Short.INSTANCE;
     */
    /* JADX WARN: Code restructure failed: missing block: B:43:0x00b5, code lost:
    
        if (r9 == null) goto L45;
     */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x00b7, code lost:
    
        r10 = java.lang.Long.valueOf(r9.getIntValue());
     */
    /* JADX WARN: Code restructure failed: missing block: B:45:0x00bf, code lost:
    
        kotlin.jvm.internal.Intrinsics.checkNotNull(r10, "null cannot be cast to non-null type kotlin.Number");
     */
    /* JADX WARN: Code restructure failed: missing block: B:47:0x00e0, code lost:
    
        if (r11.equals("J") == false) goto L103;
     */
    /* JADX WARN: Code restructure failed: missing block: B:49:0x00e4, code lost:
    
        r1 = org.jetbrains.kotlin.types.ConstantValueKind.Long.INSTANCE;
     */
    /* JADX WARN: Code restructure failed: missing block: B:50:0x00e9, code lost:
    
        if (r9 == null) goto L52;
     */
    /* JADX WARN: Code restructure failed: missing block: B:51:0x00eb, code lost:
    
        r9 = r9.getIntValue();
     */
    /* JADX WARN: Code restructure failed: missing block: B:52:0x00f0, code lost:
    
        kotlin.jvm.internal.Intrinsics.checkNotNull(r10, "null cannot be cast to non-null type kotlin.Long");
        r9 = ((java.lang.Long) r10).longValue();
     */
    /* JADX WARN: Code restructure failed: missing block: B:55:0x0113, code lost:
    
        if (r11.equals("I") == false) goto L103;
     */
    /* JADX WARN: Code restructure failed: missing block: B:57:0x0117, code lost:
    
        r1 = org.jetbrains.kotlin.types.ConstantValueKind.Int.INSTANCE;
     */
    /* JADX WARN: Code restructure failed: missing block: B:58:0x011c, code lost:
    
        if (r9 == null) goto L60;
     */
    /* JADX WARN: Code restructure failed: missing block: B:59:0x011e, code lost:
    
        r9 = (int) r9.getIntValue();
     */
    /* JADX WARN: Code restructure failed: missing block: B:60:0x0124, code lost:
    
        kotlin.jvm.internal.Intrinsics.checkNotNull(r10, "null cannot be cast to non-null type kotlin.Int");
        r9 = ((java.lang.Integer) r10).intValue();
     */
    /* JADX WARN: Code restructure failed: missing block: B:63:0x0147, code lost:
    
        if (r11.equals("F") == false) goto L103;
     */
    /* JADX WARN: Code restructure failed: missing block: B:65:0x014b, code lost:
    
        r1 = org.jetbrains.kotlin.types.ConstantValueKind.Float.INSTANCE;
     */
    /* JADX WARN: Code restructure failed: missing block: B:66:0x0150, code lost:
    
        if (r9 == null) goto L68;
     */
    /* JADX WARN: Code restructure failed: missing block: B:67:0x0152, code lost:
    
        r9 = r9.getFloatValue();
     */
    /* JADX WARN: Code restructure failed: missing block: B:68:0x0157, code lost:
    
        kotlin.jvm.internal.Intrinsics.checkNotNull(r10, "null cannot be cast to non-null type kotlin.Float");
        r9 = ((java.lang.Float) r10).floatValue();
     */
    /* JADX WARN: Code restructure failed: missing block: B:6:0x001b, code lost:
    
        if (r11.equals("DOUBLE") == false) goto L103;
     */
    /* JADX WARN: Code restructure failed: missing block: B:71:0x017a, code lost:
    
        if (r11.equals("D") == false) goto L103;
     */
    /* JADX WARN: Code restructure failed: missing block: B:73:0x017e, code lost:
    
        r1 = org.jetbrains.kotlin.types.ConstantValueKind.Double.INSTANCE;
     */
    /* JADX WARN: Code restructure failed: missing block: B:74:0x0183, code lost:
    
        if (r9 == null) goto L76;
     */
    /* JADX WARN: Code restructure failed: missing block: B:75:0x0185, code lost:
    
        r9 = r9.getDoubleValue();
     */
    /* JADX WARN: Code restructure failed: missing block: B:76:0x018a, code lost:
    
        kotlin.jvm.internal.Intrinsics.checkNotNull(r10, "null cannot be cast to non-null type kotlin.Double");
        r9 = ((java.lang.Double) r10).doubleValue();
     */
    /* JADX WARN: Code restructure failed: missing block: B:79:0x01ad, code lost:
    
        if (r11.equals("C") == false) goto L103;
     */
    /* JADX WARN: Code restructure failed: missing block: B:81:0x01b1, code lost:
    
        r3 = org.jetbrains.kotlin.types.ConstantValueKind.Char.INSTANCE;
     */
    /* JADX WARN: Code restructure failed: missing block: B:82:0x01b6, code lost:
    
        if (r9 == null) goto L84;
     */
    /* JADX WARN: Code restructure failed: missing block: B:83:0x01b8, code lost:
    
        r10 = java.lang.Long.valueOf(r9.getIntValue());
     */
    /* JADX WARN: Code restructure failed: missing block: B:84:0x01c0, code lost:
    
        kotlin.jvm.internal.Intrinsics.checkNotNull(r10, "null cannot be cast to non-null type kotlin.Number");
     */
    /* JADX WARN: Code restructure failed: missing block: B:86:0x01e1, code lost:
    
        if (r11.equals("B") == false) goto L103;
     */
    /* JADX WARN: Code restructure failed: missing block: B:88:0x01e4, code lost:
    
        r3 = org.jetbrains.kotlin.types.ConstantValueKind.Byte.INSTANCE;
     */
    /* JADX WARN: Code restructure failed: missing block: B:89:0x01e9, code lost:
    
        if (r9 == null) goto L91;
     */
    /* JADX WARN: Code restructure failed: missing block: B:90:0x01eb, code lost:
    
        r10 = java.lang.Long.valueOf(r9.getIntValue());
     */
    /* JADX WARN: Code restructure failed: missing block: B:91:0x01f3, code lost:
    
        kotlin.jvm.internal.Intrinsics.checkNotNull(r10, "null cannot be cast to non-null type kotlin.Number");
     */
    /* JADX WARN: Code restructure failed: missing block: B:93:0x0213, code lost:
    
        if (r11.equals("STRING") == false) goto L103;
     */
    /* JADX WARN: Code restructure failed: missing block: B:95:0x0216, code lost:
    
        r1 = org.jetbrains.kotlin.types.ConstantValueKind.String.INSTANCE;
     */
    /* JADX WARN: Code restructure failed: missing block: B:96:0x021b, code lost:
    
        if (r9 == null) goto L101;
     */
    /* JADX WARN: Code restructure failed: missing block: B:97:0x021d, code lost:
    
        r9 = r12.getString(r9.getStringValue());
     */
    /* JADX WARN: Code restructure failed: missing block: B:98:0x0225, code lost:
    
        if (r9 != null) goto L100;
     */
    /* JADX WARN: Code restructure failed: missing block: B:9:0x0025, code lost:
    
        if (r11.equals("Ljava/lang/String;") == false) goto L103;
     */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static final org.jetbrains.kotlin.fir.expressions.FirExpression buildFirConstant(org.jetbrains.kotlin.metadata.ProtoBuf.Annotation.Argument.Value r9, java.lang.Object r10, java.lang.String r11, org.jetbrains.kotlin.metadata.deserialization.NameResolver r12) {
        /*
            Method dump skipped, instruction units count: 652
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.jetbrains.kotlin.fir.deserialization.FirConstDeserializerKt.buildFirConstant(org.jetbrains.kotlin.metadata.ProtoBuf$Annotation$Argument$Value, java.lang.Object, java.lang.String, org.jetbrains.kotlin.metadata.deserialization.NameResolver):org.jetbrains.kotlin.fir.expressions.FirExpression");
    }

    public static final CallableId replaceName(CallableId callableId, Name newName) {
        Intrinsics.checkNotNullParameter(callableId, "<this>");
        Intrinsics.checkNotNullParameter(newName, "newName");
        return new CallableId(callableId.getPackageName(), callableId.getClassName(), newName, null, 8, null);
    }
}

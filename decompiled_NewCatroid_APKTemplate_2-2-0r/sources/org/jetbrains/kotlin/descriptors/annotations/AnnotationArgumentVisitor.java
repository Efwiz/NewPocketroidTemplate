package org.jetbrains.kotlin.descriptors.annotations;

import org.jetbrains.kotlin.resolve.constants.AnnotationValue;
import org.jetbrains.kotlin.resolve.constants.ArrayValue;
import org.jetbrains.kotlin.resolve.constants.BooleanValue;
import org.jetbrains.kotlin.resolve.constants.ByteValue;
import org.jetbrains.kotlin.resolve.constants.CharValue;
import org.jetbrains.kotlin.resolve.constants.DoubleValue;
import org.jetbrains.kotlin.resolve.constants.EnumValue;
import org.jetbrains.kotlin.resolve.constants.ErrorValue;
import org.jetbrains.kotlin.resolve.constants.FloatValue;
import org.jetbrains.kotlin.resolve.constants.IntValue;
import org.jetbrains.kotlin.resolve.constants.KClassValue;
import org.jetbrains.kotlin.resolve.constants.LongValue;
import org.jetbrains.kotlin.resolve.constants.NullValue;
import org.jetbrains.kotlin.resolve.constants.ShortValue;
import org.jetbrains.kotlin.resolve.constants.StringValue;
import org.jetbrains.kotlin.resolve.constants.UByteValue;
import org.jetbrains.kotlin.resolve.constants.UIntValue;
import org.jetbrains.kotlin.resolve.constants.ULongValue;
import org.jetbrains.kotlin.resolve.constants.UShortValue;

/* JADX INFO: loaded from: classes4.dex */
public interface AnnotationArgumentVisitor<R, D> {
    R visitAnnotationValue(AnnotationValue annotationValue, D d);

    R visitArrayValue(ArrayValue arrayValue, D d);

    R visitBooleanValue(BooleanValue booleanValue, D d);

    R visitByteValue(ByteValue byteValue, D d);

    R visitCharValue(CharValue charValue, D d);

    R visitDoubleValue(DoubleValue doubleValue, D d);

    R visitEnumValue(EnumValue enumValue, D d);

    R visitErrorValue(ErrorValue errorValue, D d);

    R visitFloatValue(FloatValue floatValue, D d);

    R visitIntValue(IntValue intValue, D d);

    R visitKClassValue(KClassValue kClassValue, D d);

    R visitLongValue(LongValue longValue, D d);

    R visitNullValue(NullValue nullValue, D d);

    R visitShortValue(ShortValue shortValue, D d);

    R visitStringValue(StringValue stringValue, D d);

    R visitUByteValue(UByteValue uByteValue, D d);

    R visitUIntValue(UIntValue uIntValue, D d);

    R visitULongValue(ULongValue uLongValue, D d);

    R visitUShortValue(UShortValue uShortValue, D d);
}

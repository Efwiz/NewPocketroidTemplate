package org.jetbrains.kotlin.constant;

import kotlin.Metadata;

/* JADX INFO: compiled from: AnnotationArgumentVisitor.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000¨\u0001\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b&\u0018\u0000*\u0004\b\u0000\u0010\u0001*\u0004\b\u0001\u0010\u00022\u00020\u0003B\u0005¢\u0006\u0002\u0010\u0004J\u001d\u0010\u0005\u001a\u00028\u00002\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00028\u0001H&¢\u0006\u0002\u0010\tJ\u001d\u0010\n\u001a\u00028\u00002\u0006\u0010\u0006\u001a\u00020\u000b2\u0006\u0010\b\u001a\u00028\u0001H&¢\u0006\u0002\u0010\fJ\u001d\u0010\r\u001a\u00028\u00002\u0006\u0010\u0006\u001a\u00020\u000e2\u0006\u0010\b\u001a\u00028\u0001H&¢\u0006\u0002\u0010\u000fJ\u001d\u0010\u0010\u001a\u00028\u00002\u0006\u0010\u0006\u001a\u00020\u00112\u0006\u0010\b\u001a\u00028\u0001H&¢\u0006\u0002\u0010\u0012J\u001d\u0010\u0013\u001a\u00028\u00002\u0006\u0010\u0006\u001a\u00020\u00142\u0006\u0010\b\u001a\u00028\u0001H&¢\u0006\u0002\u0010\u0015J\u001d\u0010\u0016\u001a\u00028\u00002\u0006\u0010\u0006\u001a\u00020\u00172\u0006\u0010\b\u001a\u00028\u0001H&¢\u0006\u0002\u0010\u0018J\u001d\u0010\u0019\u001a\u00028\u00002\u0006\u0010\u0006\u001a\u00020\u001a2\u0006\u0010\b\u001a\u00028\u0001H&¢\u0006\u0002\u0010\u001bJ\u001d\u0010\u001c\u001a\u00028\u00002\u0006\u0010\u0006\u001a\u00020\u001d2\u0006\u0010\b\u001a\u00028\u0001H&¢\u0006\u0002\u0010\u001eJ\u001d\u0010\u001f\u001a\u00028\u00002\u0006\u0010\u0006\u001a\u00020 2\u0006\u0010\b\u001a\u00028\u0001H&¢\u0006\u0002\u0010!J\u001d\u0010\"\u001a\u00028\u00002\u0006\u0010\u0006\u001a\u00020#2\u0006\u0010\b\u001a\u00028\u0001H&¢\u0006\u0002\u0010$J\u001d\u0010%\u001a\u00028\u00002\u0006\u0010\u0006\u001a\u00020&2\u0006\u0010\b\u001a\u00028\u0001H&¢\u0006\u0002\u0010'J\u001d\u0010(\u001a\u00028\u00002\u0006\u0010\u0006\u001a\u00020)2\u0006\u0010\b\u001a\u00028\u0001H&¢\u0006\u0002\u0010*J\u001d\u0010+\u001a\u00028\u00002\u0006\u0010\u0006\u001a\u00020,2\u0006\u0010\b\u001a\u00028\u0001H&¢\u0006\u0002\u0010-J\u001d\u0010.\u001a\u00028\u00002\u0006\u0010\u0006\u001a\u00020/2\u0006\u0010\b\u001a\u00028\u0001H&¢\u0006\u0002\u00100J\u001d\u00101\u001a\u00028\u00002\u0006\u0010\u0006\u001a\u0002022\u0006\u0010\b\u001a\u00028\u0001H&¢\u0006\u0002\u00103J\u001d\u00104\u001a\u00028\u00002\u0006\u0010\u0006\u001a\u0002052\u0006\u0010\b\u001a\u00028\u0001H&¢\u0006\u0002\u00106J\u001d\u00107\u001a\u00028\u00002\u0006\u0010\u0006\u001a\u0002082\u0006\u0010\b\u001a\u00028\u0001H&¢\u0006\u0002\u00109J\u001d\u0010:\u001a\u00028\u00002\u0006\u0010\u0006\u001a\u00020;2\u0006\u0010\b\u001a\u00028\u0001H&¢\u0006\u0002\u0010<J\u001d\u0010=\u001a\u00028\u00002\u0006\u0010\u0006\u001a\u00020>2\u0006\u0010\b\u001a\u00028\u0001H&¢\u0006\u0002\u0010?¨\u0006@"}, d2 = {"Lorg/jetbrains/kotlin/constant/AnnotationArgumentVisitor;", "R", "D", "", "()V", "visitAnnotationValue", "value", "Lorg/jetbrains/kotlin/constant/AnnotationValue;", "data", "(Lorg/jetbrains/kotlin/constant/AnnotationValue;Ljava/lang/Object;)Ljava/lang/Object;", "visitArrayValue", "Lorg/jetbrains/kotlin/constant/ArrayValue;", "(Lorg/jetbrains/kotlin/constant/ArrayValue;Ljava/lang/Object;)Ljava/lang/Object;", "visitBooleanValue", "Lorg/jetbrains/kotlin/constant/BooleanValue;", "(Lorg/jetbrains/kotlin/constant/BooleanValue;Ljava/lang/Object;)Ljava/lang/Object;", "visitByteValue", "Lorg/jetbrains/kotlin/constant/ByteValue;", "(Lorg/jetbrains/kotlin/constant/ByteValue;Ljava/lang/Object;)Ljava/lang/Object;", "visitCharValue", "Lorg/jetbrains/kotlin/constant/CharValue;", "(Lorg/jetbrains/kotlin/constant/CharValue;Ljava/lang/Object;)Ljava/lang/Object;", "visitDoubleValue", "Lorg/jetbrains/kotlin/constant/DoubleValue;", "(Lorg/jetbrains/kotlin/constant/DoubleValue;Ljava/lang/Object;)Ljava/lang/Object;", "visitEnumValue", "Lorg/jetbrains/kotlin/constant/EnumValue;", "(Lorg/jetbrains/kotlin/constant/EnumValue;Ljava/lang/Object;)Ljava/lang/Object;", "visitErrorValue", "Lorg/jetbrains/kotlin/constant/ErrorValue;", "(Lorg/jetbrains/kotlin/constant/ErrorValue;Ljava/lang/Object;)Ljava/lang/Object;", "visitFloatValue", "Lorg/jetbrains/kotlin/constant/FloatValue;", "(Lorg/jetbrains/kotlin/constant/FloatValue;Ljava/lang/Object;)Ljava/lang/Object;", "visitIntValue", "Lorg/jetbrains/kotlin/constant/IntValue;", "(Lorg/jetbrains/kotlin/constant/IntValue;Ljava/lang/Object;)Ljava/lang/Object;", "visitKClassValue", "Lorg/jetbrains/kotlin/constant/KClassValue;", "(Lorg/jetbrains/kotlin/constant/KClassValue;Ljava/lang/Object;)Ljava/lang/Object;", "visitLongValue", "Lorg/jetbrains/kotlin/constant/LongValue;", "(Lorg/jetbrains/kotlin/constant/LongValue;Ljava/lang/Object;)Ljava/lang/Object;", "visitNullValue", "Lorg/jetbrains/kotlin/constant/NullValue;", "(Lorg/jetbrains/kotlin/constant/NullValue;Ljava/lang/Object;)Ljava/lang/Object;", "visitShortValue", "Lorg/jetbrains/kotlin/constant/ShortValue;", "(Lorg/jetbrains/kotlin/constant/ShortValue;Ljava/lang/Object;)Ljava/lang/Object;", "visitStringValue", "Lorg/jetbrains/kotlin/constant/StringValue;", "(Lorg/jetbrains/kotlin/constant/StringValue;Ljava/lang/Object;)Ljava/lang/Object;", "visitUByteValue", "Lorg/jetbrains/kotlin/constant/UByteValue;", "(Lorg/jetbrains/kotlin/constant/UByteValue;Ljava/lang/Object;)Ljava/lang/Object;", "visitUIntValue", "Lorg/jetbrains/kotlin/constant/UIntValue;", "(Lorg/jetbrains/kotlin/constant/UIntValue;Ljava/lang/Object;)Ljava/lang/Object;", "visitULongValue", "Lorg/jetbrains/kotlin/constant/ULongValue;", "(Lorg/jetbrains/kotlin/constant/ULongValue;Ljava/lang/Object;)Ljava/lang/Object;", "visitUShortValue", "Lorg/jetbrains/kotlin/constant/UShortValue;", "(Lorg/jetbrains/kotlin/constant/UShortValue;Ljava/lang/Object;)Ljava/lang/Object;", "compiler.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class AnnotationArgumentVisitor<R, D> {
    public abstract R visitAnnotationValue(AnnotationValue value, D data);

    public abstract R visitArrayValue(ArrayValue value, D data);

    public abstract R visitBooleanValue(BooleanValue value, D data);

    public abstract R visitByteValue(ByteValue value, D data);

    public abstract R visitCharValue(CharValue value, D data);

    public abstract R visitDoubleValue(DoubleValue value, D data);

    public abstract R visitEnumValue(EnumValue value, D data);

    public abstract R visitErrorValue(ErrorValue value, D data);

    public abstract R visitFloatValue(FloatValue value, D data);

    public abstract R visitIntValue(IntValue value, D data);

    public abstract R visitKClassValue(KClassValue value, D data);

    public abstract R visitLongValue(LongValue value, D data);

    public abstract R visitNullValue(NullValue value, D data);

    public abstract R visitShortValue(ShortValue value, D data);

    public abstract R visitStringValue(StringValue value, D data);

    public abstract R visitUByteValue(UByteValue value, D data);

    public abstract R visitUIntValue(UIntValue value, D data);

    public abstract R visitULongValue(ULongValue value, D data);

    public abstract R visitUShortValue(UShortValue value, D data);
}

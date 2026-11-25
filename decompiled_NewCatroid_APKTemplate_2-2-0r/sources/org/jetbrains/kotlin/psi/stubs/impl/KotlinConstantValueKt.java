package org.jetbrains.kotlin.psi.stubs.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IntIterator;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.ranges.IntRange;
import kotlin.ranges.RangesKt;
import kotlin.reflect.KDeclarationContainer;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubInputStream;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubOutputStream;
import org.jetbrains.kotlin.constant.AnnotationValue;
import org.jetbrains.kotlin.constant.ArrayValue;
import org.jetbrains.kotlin.constant.BooleanValue;
import org.jetbrains.kotlin.constant.ByteValue;
import org.jetbrains.kotlin.constant.CharValue;
import org.jetbrains.kotlin.constant.ConstantValue;
import org.jetbrains.kotlin.constant.DoubleValue;
import org.jetbrains.kotlin.constant.EnumValue;
import org.jetbrains.kotlin.constant.FloatValue;
import org.jetbrains.kotlin.constant.IntValue;
import org.jetbrains.kotlin.constant.KClassValue;
import org.jetbrains.kotlin.constant.LongValue;
import org.jetbrains.kotlin.constant.NullValue;
import org.jetbrains.kotlin.constant.ShortValue;
import org.jetbrains.kotlin.constant.StringValue;
import org.jetbrains.kotlin.constant.UByteValue;
import org.jetbrains.kotlin.constant.UIntValue;
import org.jetbrains.kotlin.constant.ULongValue;
import org.jetbrains.kotlin.constant.UShortValue;
import org.jetbrains.kotlin.metadata.ProtoBuf;
import org.jetbrains.kotlin.metadata.deserialization.Flags;
import org.jetbrains.kotlin.metadata.deserialization.NameResolver;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.psi.stubs.StubUtils;

/* JADX INFO: compiled from: KotlinConstantValue.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000<\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u0014\u0010\u0000\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u00012\u0006\u0010\u0002\u001a\u00020\u0003\u001a\u0014\u0010\u0000\u001a\u0006\u0012\u0002\b\u00030\u00012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u001a\u001a\u0010\u0000\u001a\u0006\u0012\u0002\b\u00030\u00012\u0006\u0010\u0004\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b\u001a\u001a\u0010\t\u001a\u00020\n2\n\u0010\u000b\u001a\u0006\u0012\u0002\b\u00030\u00012\u0006\u0010\u0002\u001a\u00020\f\u001a\u0014\u0010\r\u001a\u00020\u000e*\u00020\b2\u0006\u0010\u000f\u001a\u00020\u0010H\u0002\u001a\u0014\u0010\u0011\u001a\u00020\u0012*\u00020\b2\u0006\u0010\u000f\u001a\u00020\u0010H\u0002¨\u0006\u0013"}, d2 = {"createConstantValue", "Lorg/jetbrains/kotlin/constant/ConstantValue;", "dataStream", "Lorg/jetbrains/kotlin/com/intellij/psi/stubs/StubInputStream;", "value", "", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Annotation$Argument$Value;", "nameResolver", "Lorg/jetbrains/kotlin/metadata/deserialization/NameResolver;", "serialize", "", "constantValue", "Lorg/jetbrains/kotlin/com/intellij/psi/stubs/StubOutputStream;", "getClassId", "Lorg/jetbrains/kotlin/name/ClassId;", "index", "", "getName", "Lorg/jetbrains/kotlin/name/Name;", "psi"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class KotlinConstantValueKt {

    /* JADX INFO: compiled from: KotlinConstantValue.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;
        public static final /* synthetic */ int[] $EnumSwitchMapping$1;

        static {
            int[] iArr = new int[KotlinConstantValueKind.values().length];
            try {
                iArr[KotlinConstantValueKind.NULL.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[KotlinConstantValueKind.BOOLEAN.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[KotlinConstantValueKind.CHAR.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[KotlinConstantValueKind.BYTE.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr[KotlinConstantValueKind.SHORT.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                iArr[KotlinConstantValueKind.INT.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                iArr[KotlinConstantValueKind.LONG.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                iArr[KotlinConstantValueKind.DOUBLE.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                iArr[KotlinConstantValueKind.FLOAT.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                iArr[KotlinConstantValueKind.ENUM.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                iArr[KotlinConstantValueKind.KCLASS.ordinal()] = 11;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                iArr[KotlinConstantValueKind.STRING.ordinal()] = 12;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                iArr[KotlinConstantValueKind.ARRAY.ordinal()] = 13;
            } catch (NoSuchFieldError unused13) {
            }
            try {
                iArr[KotlinConstantValueKind.UBYTE.ordinal()] = 14;
            } catch (NoSuchFieldError unused14) {
            }
            try {
                iArr[KotlinConstantValueKind.USHORT.ordinal()] = 15;
            } catch (NoSuchFieldError unused15) {
            }
            try {
                iArr[KotlinConstantValueKind.UINT.ordinal()] = 16;
            } catch (NoSuchFieldError unused16) {
            }
            try {
                iArr[KotlinConstantValueKind.ULONG.ordinal()] = 17;
            } catch (NoSuchFieldError unused17) {
            }
            try {
                iArr[KotlinConstantValueKind.ANNO.ordinal()] = 18;
            } catch (NoSuchFieldError unused18) {
            }
            $EnumSwitchMapping$0 = iArr;
            int[] iArr2 = new int[ProtoBuf.Annotation.Argument.Value.Type.values().length];
            try {
                iArr2[ProtoBuf.Annotation.Argument.Value.Type.BYTE.ordinal()] = 1;
            } catch (NoSuchFieldError unused19) {
            }
            try {
                iArr2[ProtoBuf.Annotation.Argument.Value.Type.CHAR.ordinal()] = 2;
            } catch (NoSuchFieldError unused20) {
            }
            try {
                iArr2[ProtoBuf.Annotation.Argument.Value.Type.SHORT.ordinal()] = 3;
            } catch (NoSuchFieldError unused21) {
            }
            try {
                iArr2[ProtoBuf.Annotation.Argument.Value.Type.INT.ordinal()] = 4;
            } catch (NoSuchFieldError unused22) {
            }
            try {
                iArr2[ProtoBuf.Annotation.Argument.Value.Type.LONG.ordinal()] = 5;
            } catch (NoSuchFieldError unused23) {
            }
            try {
                iArr2[ProtoBuf.Annotation.Argument.Value.Type.FLOAT.ordinal()] = 6;
            } catch (NoSuchFieldError unused24) {
            }
            try {
                iArr2[ProtoBuf.Annotation.Argument.Value.Type.DOUBLE.ordinal()] = 7;
            } catch (NoSuchFieldError unused25) {
            }
            try {
                iArr2[ProtoBuf.Annotation.Argument.Value.Type.BOOLEAN.ordinal()] = 8;
            } catch (NoSuchFieldError unused26) {
            }
            try {
                iArr2[ProtoBuf.Annotation.Argument.Value.Type.STRING.ordinal()] = 9;
            } catch (NoSuchFieldError unused27) {
            }
            try {
                iArr2[ProtoBuf.Annotation.Argument.Value.Type.CLASS.ordinal()] = 10;
            } catch (NoSuchFieldError unused28) {
            }
            try {
                iArr2[ProtoBuf.Annotation.Argument.Value.Type.ENUM.ordinal()] = 11;
            } catch (NoSuchFieldError unused29) {
            }
            try {
                iArr2[ProtoBuf.Annotation.Argument.Value.Type.ANNOTATION.ordinal()] = 12;
            } catch (NoSuchFieldError unused30) {
            }
            try {
                iArr2[ProtoBuf.Annotation.Argument.Value.Type.ARRAY.ordinal()] = 13;
            } catch (NoSuchFieldError unused31) {
            }
            $EnumSwitchMapping$1 = iArr2;
        }
    }

    public static final ConstantValue<?> createConstantValue(StubInputStream dataStream) throws IOException {
        Intrinsics.checkNotNullParameter(dataStream, "dataStream");
        int i = dataStream.readInt();
        if (i == -1) {
            return null;
        }
        switch (WhenMappings.$EnumSwitchMapping$0[KotlinConstantValueKind.values()[i].ordinal()]) {
            case 1:
                return NullValue.INSTANCE;
            case 2:
                return new BooleanValue(dataStream.readBoolean());
            case 3:
                return new CharValue(dataStream.readChar());
            case 4:
                return new ByteValue(dataStream.readByte());
            case 5:
                return new ShortValue(dataStream.readShort());
            case 6:
                return new IntValue(dataStream.readInt());
            case 7:
                return new LongValue(dataStream.readLong());
            case 8:
                return new DoubleValue(dataStream.readDouble());
            case 9:
                return new FloatValue(dataStream.readFloat());
            case 10:
                ClassId classIdDeserializeClassId = StubUtils.deserializeClassId(dataStream);
                Intrinsics.checkNotNull(classIdDeserializeClassId);
                String nameString = dataStream.readNameString();
                Intrinsics.checkNotNull(nameString);
                Name nameIdentifier = Name.identifier(nameString);
                Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
                return new EnumValue(classIdDeserializeClassId, nameIdentifier);
            case 11:
                ClassId classIdDeserializeClassId2 = StubUtils.deserializeClassId(dataStream);
                Intrinsics.checkNotNull(classIdDeserializeClassId2);
                return new KClassValue(classIdDeserializeClassId2, dataStream.readInt());
            case 12:
                String nameString2 = dataStream.readNameString();
                Intrinsics.checkNotNull(nameString2);
                return new StringValue(nameString2);
            case 13:
                IntRange intRange = new IntRange(0, dataStream.readInt() - 1);
                ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(intRange, 10));
                Iterator<Integer> it = intRange.iterator();
                while (it.hasNext()) {
                    ((IntIterator) it).nextInt();
                    ConstantValue<?> constantValueCreateConstantValue = createConstantValue(dataStream);
                    Intrinsics.checkNotNull(constantValueCreateConstantValue);
                    arrayList.add(constantValueCreateConstantValue);
                }
                return new ArrayValue(arrayList);
            case 14:
                return new UByteValue(dataStream.readByte());
            case 15:
                return new UShortValue(dataStream.readShort());
            case 16:
                return new UIntValue(dataStream.readInt());
            case 17:
                return new ULongValue(dataStream.readLong());
            case 18:
                ClassId classIdDeserializeClassId3 = StubUtils.deserializeClassId(dataStream);
                Intrinsics.checkNotNull(classIdDeserializeClassId3);
                int i2 = dataStream.readInt() - 1;
                AnnotationValue.Companion companion = AnnotationValue.INSTANCE;
                KotlinClassTypeBean kotlinClassTypeBean = new KotlinClassTypeBean(classIdDeserializeClassId3, CollectionsKt.emptyList(), false);
                IntRange intRange2 = new IntRange(0, i2);
                LinkedHashMap linkedHashMap = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(intRange2, 10)), 16));
                Iterator<Integer> it2 = intRange2.iterator();
                while (it2.hasNext()) {
                    ((IntIterator) it2).nextInt();
                    String nameString3 = dataStream.readNameString();
                    Intrinsics.checkNotNull(nameString3);
                    Name nameIdentifier2 = Name.identifier(nameString3);
                    ConstantValue<?> constantValueCreateConstantValue2 = createConstantValue(dataStream);
                    Intrinsics.checkNotNull(constantValueCreateConstantValue2);
                    Pair pair = TuplesKt.to(nameIdentifier2, constantValueCreateConstantValue2);
                    linkedHashMap.put(pair.getFirst(), pair.getSecond());
                }
                return companion.create(kotlinClassTypeBean, linkedHashMap);
            default:
                throw new NoWhenBranchMatchedException();
        }
    }

    public static final void serialize(ConstantValue<?> constantValue, StubOutputStream dataStream) {
        Intrinsics.checkNotNullParameter(constantValue, "constantValue");
        Intrinsics.checkNotNullParameter(dataStream, "dataStream");
        constantValue.accept(new KotlinConstantValueSerializationVisitor(dataStream), null);
    }

    public static final ConstantValue<?> createConstantValue(Object obj) {
        if (obj instanceof Byte) {
            return new ByteValue(((Number) obj).byteValue());
        }
        if (obj instanceof Short) {
            return new ShortValue(((Number) obj).shortValue());
        }
        if (obj instanceof Integer) {
            return new IntValue(((Number) obj).intValue());
        }
        if (obj instanceof Long) {
            return new LongValue(((Number) obj).longValue());
        }
        if (obj instanceof Character) {
            return new CharValue(((Character) obj).charValue());
        }
        if (obj instanceof Float) {
            return new FloatValue(((Number) obj).floatValue());
        }
        if (obj instanceof Double) {
            return new DoubleValue(((Number) obj).doubleValue());
        }
        if (obj instanceof Boolean) {
            return new BooleanValue(((Boolean) obj).booleanValue());
        }
        if (obj instanceof String) {
            return new StringValue((String) obj);
        }
        int i = 0;
        if (!(obj instanceof byte[])) {
            if (!(obj instanceof short[])) {
                if (!(obj instanceof int[])) {
                    if (!(obj instanceof long[])) {
                        if (!(obj instanceof char[])) {
                            if (!(obj instanceof float[])) {
                                if (!(obj instanceof double[])) {
                                    if (!(obj instanceof boolean[])) {
                                        if (!(obj instanceof Object[])) {
                                            if (obj instanceof EnumData) {
                                                EnumData enumData = (EnumData) obj;
                                                return new EnumValue(enumData.getEnumClassId(), enumData.getEnumEntryName());
                                            }
                                            if (obj instanceof KClassData) {
                                                KClassData kClassData = (KClassData) obj;
                                                return new KClassValue(kClassData.getClassId(), kClassData.getArrayNestedness());
                                            }
                                            if (obj instanceof AnnotationData) {
                                                AnnotationData annotationData = (AnnotationData) obj;
                                                return AnnotationValue.INSTANCE.create(new KotlinClassTypeBean(annotationData.getAnnoClassId(), CollectionsKt.emptyList(), false), annotationData.getArgs());
                                            }
                                            if (obj == null) {
                                                return NullValue.INSTANCE;
                                            }
                                            throw new IllegalStateException(("Unsupported value " + obj).toString());
                                        }
                                        Object[] objArr = (Object[]) obj;
                                        ArrayList arrayList = new ArrayList(objArr.length);
                                        int length = objArr.length;
                                        while (i < length) {
                                            arrayList.add(createConstantValue(objArr[i]));
                                            i++;
                                        }
                                        return new ArrayValue(CollectionsKt.toList(arrayList));
                                    }
                                    boolean[] zArr = (boolean[]) obj;
                                    ArrayList arrayList2 = new ArrayList(zArr.length);
                                    int length2 = zArr.length;
                                    while (i < length2) {
                                        arrayList2.add(createConstantValue(Boolean.valueOf(zArr[i])));
                                        i++;
                                    }
                                    return new ArrayValue(CollectionsKt.toList(arrayList2));
                                }
                                double[] dArr = (double[]) obj;
                                ArrayList arrayList3 = new ArrayList(dArr.length);
                                int length3 = dArr.length;
                                while (i < length3) {
                                    arrayList3.add(createConstantValue(Double.valueOf(dArr[i])));
                                    i++;
                                }
                                return new ArrayValue(CollectionsKt.toList(arrayList3));
                            }
                            float[] fArr = (float[]) obj;
                            ArrayList arrayList4 = new ArrayList(fArr.length);
                            int length4 = fArr.length;
                            while (i < length4) {
                                arrayList4.add(createConstantValue(Float.valueOf(fArr[i])));
                                i++;
                            }
                            return new ArrayValue(CollectionsKt.toList(arrayList4));
                        }
                        char[] cArr = (char[]) obj;
                        ArrayList arrayList5 = new ArrayList(cArr.length);
                        int length5 = cArr.length;
                        while (i < length5) {
                            arrayList5.add(createConstantValue(Character.valueOf(cArr[i])));
                            i++;
                        }
                        return new ArrayValue(CollectionsKt.toList(arrayList5));
                    }
                    long[] jArr = (long[]) obj;
                    ArrayList arrayList6 = new ArrayList(jArr.length);
                    int length6 = jArr.length;
                    while (i < length6) {
                        arrayList6.add(createConstantValue(Long.valueOf(jArr[i])));
                        i++;
                    }
                    return new ArrayValue(CollectionsKt.toList(arrayList6));
                }
                int[] iArr = (int[]) obj;
                ArrayList arrayList7 = new ArrayList(iArr.length);
                int length7 = iArr.length;
                while (i < length7) {
                    arrayList7.add(createConstantValue(Integer.valueOf(iArr[i])));
                    i++;
                }
                return new ArrayValue(CollectionsKt.toList(arrayList7));
            }
            short[] sArr = (short[]) obj;
            ArrayList arrayList8 = new ArrayList(sArr.length);
            int length8 = sArr.length;
            while (i < length8) {
                arrayList8.add(createConstantValue(Short.valueOf(sArr[i])));
                i++;
            }
            return new ArrayValue(CollectionsKt.toList(arrayList8));
        }
        byte[] bArr = (byte[]) obj;
        ArrayList arrayList9 = new ArrayList(bArr.length);
        int length9 = bArr.length;
        while (i < length9) {
            arrayList9.add(createConstantValue(Byte.valueOf(bArr[i])));
            i++;
        }
        return new ArrayValue(CollectionsKt.toList(arrayList9));
    }

    public static final ConstantValue<?> createConstantValue(ProtoBuf.Annotation.Argument.Value value, NameResolver nameResolver) {
        Intrinsics.checkNotNullParameter(value, "value");
        Intrinsics.checkNotNullParameter(nameResolver, "nameResolver");
        Boolean bool = Flags.IS_UNSIGNED.get(value.getFlags());
        Intrinsics.checkNotNullExpressionValue(bool, "get(...)");
        boolean zBooleanValue = bool.booleanValue();
        ProtoBuf.Annotation.Argument.Value.Type type = value.getType();
        switch (type == null ? -1 : WhenMappings.$EnumSwitchMapping$1[type.ordinal()]) {
            case 1:
                return (ConstantValue) createConstantValue$letIf(Byte.valueOf((byte) value.getIntValue()), zBooleanValue, AnonymousClass12.INSTANCE, AnonymousClass13.INSTANCE);
            case 2:
                return new CharValue((char) value.getIntValue());
            case 3:
                return (ConstantValue) createConstantValue$letIf(Short.valueOf((short) value.getIntValue()), zBooleanValue, AnonymousClass14.INSTANCE, AnonymousClass15.INSTANCE);
            case 4:
                return (ConstantValue) createConstantValue$letIf(Integer.valueOf((int) value.getIntValue()), zBooleanValue, AnonymousClass16.INSTANCE, AnonymousClass17.INSTANCE);
            case 5:
                return (ConstantValue) createConstantValue$letIf(Long.valueOf(value.getIntValue()), zBooleanValue, AnonymousClass18.INSTANCE, AnonymousClass19.INSTANCE);
            case 6:
                return new FloatValue(value.getFloatValue());
            case 7:
                return new DoubleValue(value.getDoubleValue());
            case 8:
                return new BooleanValue(value.getIntValue() != 0);
            case 9:
                return new StringValue(nameResolver.getString(value.getStringValue()));
            case 10:
                return new KClassValue(getClassId(nameResolver, value.getClassId()), value.getArrayDimensionCount());
            case 11:
                return new EnumValue(getClassId(nameResolver, value.getClassId()), getName(nameResolver, value.getEnumValueId()));
            case 12:
                List<ProtoBuf.Annotation.Argument> argumentList = value.getAnnotation().getArgumentList();
                Intrinsics.checkNotNullExpressionValue(argumentList, "getArgumentList(...)");
                List<ProtoBuf.Annotation.Argument> list = argumentList;
                LinkedHashMap linkedHashMap = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(list, 10)), 16));
                for (ProtoBuf.Annotation.Argument argument : list) {
                    Name name2 = getName(nameResolver, argument.getNameId());
                    ProtoBuf.Annotation.Argument.Value value2 = argument.getValue();
                    Intrinsics.checkNotNullExpressionValue(value2, "getValue(...)");
                    Pair pair = TuplesKt.to(name2, createConstantValue(value2, nameResolver));
                    linkedHashMap.put(pair.getFirst(), pair.getSecond());
                }
                return AnnotationValue.INSTANCE.create(new KotlinClassTypeBean(getClassId(nameResolver, value.getAnnotation().getId()), CollectionsKt.emptyList(), false), linkedHashMap);
            case 13:
                List<ProtoBuf.Annotation.Argument.Value> arrayElementList = value.getArrayElementList();
                Intrinsics.checkNotNullExpressionValue(arrayElementList, "getArrayElementList(...)");
                List<ProtoBuf.Annotation.Argument.Value> list2 = arrayElementList;
                ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list2, 10));
                for (ProtoBuf.Annotation.Argument.Value value3 : list2) {
                    Intrinsics.checkNotNull(value3);
                    arrayList.add(createConstantValue(value3, nameResolver));
                }
                return new ArrayValue(arrayList);
            default:
                throw new IllegalStateException(("Unsupported annotation argument type: " + value.getType()).toString());
        }
    }

    private static final <T, R> R createConstantValue$letIf(T t, boolean z, Function1<? super T, ? extends R> function1, Function1<? super T, ? extends R> function2) {
        return z ? function1.invoke(t) : function2.invoke(t);
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.psi.stubs.impl.KotlinConstantValueKt$createConstantValue$12, reason: invalid class name */
    /* JADX INFO: compiled from: KotlinConstantValue.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    /* synthetic */ class AnonymousClass12 extends FunctionReference implements Function1<Byte, UByteValue> {
        public static final AnonymousClass12 INSTANCE = new AnonymousClass12();

        AnonymousClass12() {
            super(1);
        }

        @Override // kotlin.jvm.internal.CallableReference
        public final String getSignature() {
            return "<init>(B)V";
        }

        @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
        public final String getName() {
            return "<init>";
        }

        @Override // kotlin.jvm.internal.CallableReference
        public final KDeclarationContainer getOwner() {
            return Reflection.getOrCreateKotlinClass(UByteValue.class);
        }

        @Override // kotlin.jvm.functions.Function1
        public /* bridge */ /* synthetic */ UByteValue invoke(Byte b) {
            return invoke(b.byteValue());
        }

        public final UByteValue invoke(byte b) {
            return new UByteValue(b);
        }
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.psi.stubs.impl.KotlinConstantValueKt$createConstantValue$13, reason: invalid class name */
    /* JADX INFO: compiled from: KotlinConstantValue.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    /* synthetic */ class AnonymousClass13 extends FunctionReference implements Function1<Byte, ByteValue> {
        public static final AnonymousClass13 INSTANCE = new AnonymousClass13();

        AnonymousClass13() {
            super(1);
        }

        @Override // kotlin.jvm.internal.CallableReference
        public final String getSignature() {
            return "<init>(B)V";
        }

        @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
        public final String getName() {
            return "<init>";
        }

        @Override // kotlin.jvm.internal.CallableReference
        public final KDeclarationContainer getOwner() {
            return Reflection.getOrCreateKotlinClass(ByteValue.class);
        }

        @Override // kotlin.jvm.functions.Function1
        public /* bridge */ /* synthetic */ ByteValue invoke(Byte b) {
            return invoke(b.byteValue());
        }

        public final ByteValue invoke(byte b) {
            return new ByteValue(b);
        }
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.psi.stubs.impl.KotlinConstantValueKt$createConstantValue$14, reason: invalid class name */
    /* JADX INFO: compiled from: KotlinConstantValue.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    /* synthetic */ class AnonymousClass14 extends FunctionReference implements Function1<Short, UShortValue> {
        public static final AnonymousClass14 INSTANCE = new AnonymousClass14();

        AnonymousClass14() {
            super(1);
        }

        @Override // kotlin.jvm.internal.CallableReference
        public final String getSignature() {
            return "<init>(S)V";
        }

        @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
        public final String getName() {
            return "<init>";
        }

        @Override // kotlin.jvm.internal.CallableReference
        public final KDeclarationContainer getOwner() {
            return Reflection.getOrCreateKotlinClass(UShortValue.class);
        }

        @Override // kotlin.jvm.functions.Function1
        public /* bridge */ /* synthetic */ UShortValue invoke(Short sh) {
            return invoke(sh.shortValue());
        }

        public final UShortValue invoke(short s) {
            return new UShortValue(s);
        }
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.psi.stubs.impl.KotlinConstantValueKt$createConstantValue$15, reason: invalid class name */
    /* JADX INFO: compiled from: KotlinConstantValue.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    /* synthetic */ class AnonymousClass15 extends FunctionReference implements Function1<Short, ShortValue> {
        public static final AnonymousClass15 INSTANCE = new AnonymousClass15();

        AnonymousClass15() {
            super(1);
        }

        @Override // kotlin.jvm.internal.CallableReference
        public final String getSignature() {
            return "<init>(S)V";
        }

        @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
        public final String getName() {
            return "<init>";
        }

        @Override // kotlin.jvm.internal.CallableReference
        public final KDeclarationContainer getOwner() {
            return Reflection.getOrCreateKotlinClass(ShortValue.class);
        }

        @Override // kotlin.jvm.functions.Function1
        public /* bridge */ /* synthetic */ ShortValue invoke(Short sh) {
            return invoke(sh.shortValue());
        }

        public final ShortValue invoke(short s) {
            return new ShortValue(s);
        }
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.psi.stubs.impl.KotlinConstantValueKt$createConstantValue$16, reason: invalid class name */
    /* JADX INFO: compiled from: KotlinConstantValue.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    /* synthetic */ class AnonymousClass16 extends FunctionReference implements Function1<Integer, UIntValue> {
        public static final AnonymousClass16 INSTANCE = new AnonymousClass16();

        AnonymousClass16() {
            super(1);
        }

        @Override // kotlin.jvm.internal.CallableReference
        public final String getSignature() {
            return "<init>(I)V";
        }

        @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
        public final String getName() {
            return "<init>";
        }

        @Override // kotlin.jvm.internal.CallableReference
        public final KDeclarationContainer getOwner() {
            return Reflection.getOrCreateKotlinClass(UIntValue.class);
        }

        @Override // kotlin.jvm.functions.Function1
        public /* bridge */ /* synthetic */ UIntValue invoke(Integer num) {
            return invoke(num.intValue());
        }

        public final UIntValue invoke(int i) {
            return new UIntValue(i);
        }
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.psi.stubs.impl.KotlinConstantValueKt$createConstantValue$17, reason: invalid class name */
    /* JADX INFO: compiled from: KotlinConstantValue.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    /* synthetic */ class AnonymousClass17 extends FunctionReference implements Function1<Integer, IntValue> {
        public static final AnonymousClass17 INSTANCE = new AnonymousClass17();

        AnonymousClass17() {
            super(1);
        }

        @Override // kotlin.jvm.internal.CallableReference
        public final String getSignature() {
            return "<init>(I)V";
        }

        @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
        public final String getName() {
            return "<init>";
        }

        @Override // kotlin.jvm.internal.CallableReference
        public final KDeclarationContainer getOwner() {
            return Reflection.getOrCreateKotlinClass(IntValue.class);
        }

        @Override // kotlin.jvm.functions.Function1
        public /* bridge */ /* synthetic */ IntValue invoke(Integer num) {
            return invoke(num.intValue());
        }

        public final IntValue invoke(int i) {
            return new IntValue(i);
        }
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.psi.stubs.impl.KotlinConstantValueKt$createConstantValue$18, reason: invalid class name */
    /* JADX INFO: compiled from: KotlinConstantValue.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    /* synthetic */ class AnonymousClass18 extends FunctionReference implements Function1<Long, ULongValue> {
        public static final AnonymousClass18 INSTANCE = new AnonymousClass18();

        AnonymousClass18() {
            super(1);
        }

        @Override // kotlin.jvm.internal.CallableReference
        public final String getSignature() {
            return "<init>(J)V";
        }

        @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
        public final String getName() {
            return "<init>";
        }

        @Override // kotlin.jvm.internal.CallableReference
        public final KDeclarationContainer getOwner() {
            return Reflection.getOrCreateKotlinClass(ULongValue.class);
        }

        @Override // kotlin.jvm.functions.Function1
        public /* bridge */ /* synthetic */ ULongValue invoke(Long l) {
            return invoke(l.longValue());
        }

        public final ULongValue invoke(long j) {
            return new ULongValue(j);
        }
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.psi.stubs.impl.KotlinConstantValueKt$createConstantValue$19, reason: invalid class name */
    /* JADX INFO: compiled from: KotlinConstantValue.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    /* synthetic */ class AnonymousClass19 extends FunctionReference implements Function1<Long, LongValue> {
        public static final AnonymousClass19 INSTANCE = new AnonymousClass19();

        AnonymousClass19() {
            super(1);
        }

        @Override // kotlin.jvm.internal.CallableReference
        public final String getSignature() {
            return "<init>(J)V";
        }

        @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
        public final String getName() {
            return "<init>";
        }

        @Override // kotlin.jvm.internal.CallableReference
        public final KDeclarationContainer getOwner() {
            return Reflection.getOrCreateKotlinClass(LongValue.class);
        }

        @Override // kotlin.jvm.functions.Function1
        public /* bridge */ /* synthetic */ LongValue invoke(Long l) {
            return invoke(l.longValue());
        }

        public final LongValue invoke(long j) {
            return new LongValue(j);
        }
    }

    private static final ClassId getClassId(NameResolver nameResolver, int i) {
        ClassId classIdFromString = ClassId.fromString(nameResolver.getQualifiedClassName(i), nameResolver.isLocalClassName(i));
        Intrinsics.checkNotNullExpressionValue(classIdFromString, "fromString(...)");
        return classIdFromString;
    }

    private static final Name getName(NameResolver nameResolver, int i) {
        Name nameGuessByFirstCharacter = Name.guessByFirstCharacter(nameResolver.getString(i));
        Intrinsics.checkNotNullExpressionValue(nameGuessByFirstCharacter, "guessByFirstCharacter(...)");
        return nameGuessByFirstCharacter;
    }
}

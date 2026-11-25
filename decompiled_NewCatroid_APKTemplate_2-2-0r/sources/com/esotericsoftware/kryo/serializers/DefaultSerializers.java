package com.esotericsoftware.kryo.serializers;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.KryoException;
import com.esotericsoftware.kryo.KryoSerializable;
import com.esotericsoftware.kryo.Registration;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.esotericsoftware.kryo.util.Util;
import java.lang.reflect.Constructor;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Currency;
import java.util.Date;
import java.util.EnumSet;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.TimeZone;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentSkipListMap;

/* JADX INFO: loaded from: classes8.dex */
public class DefaultSerializers {

    public static class VoidSerializer extends ImmutableSerializer {
        @Override // com.esotericsoftware.kryo.Serializer
        public Object read(Kryo kryo, Input input, Class cls) {
            return null;
        }

        @Override // com.esotericsoftware.kryo.Serializer
        public void write(Kryo kryo, Output output, Object obj) {
        }
    }

    public static class BooleanSerializer extends ImmutableSerializer<Boolean> {
        @Override // com.esotericsoftware.kryo.Serializer
        public /* bridge */ /* synthetic */ Object read(Kryo kryo, Input input, Class cls) {
            return read(kryo, input, (Class<? extends Boolean>) cls);
        }

        @Override // com.esotericsoftware.kryo.Serializer
        public void write(Kryo kryo, Output output, Boolean bool) {
            output.writeBoolean(bool.booleanValue());
        }

        @Override // com.esotericsoftware.kryo.Serializer
        public Boolean read(Kryo kryo, Input input, Class<? extends Boolean> cls) {
            return Boolean.valueOf(input.readBoolean());
        }
    }

    public static class ByteSerializer extends ImmutableSerializer<Byte> {
        @Override // com.esotericsoftware.kryo.Serializer
        public /* bridge */ /* synthetic */ Object read(Kryo kryo, Input input, Class cls) {
            return read(kryo, input, (Class<? extends Byte>) cls);
        }

        @Override // com.esotericsoftware.kryo.Serializer
        public void write(Kryo kryo, Output output, Byte b) {
            output.writeByte(b.byteValue());
        }

        @Override // com.esotericsoftware.kryo.Serializer
        public Byte read(Kryo kryo, Input input, Class<? extends Byte> cls) {
            return Byte.valueOf(input.readByte());
        }
    }

    public static class CharSerializer extends ImmutableSerializer<Character> {
        @Override // com.esotericsoftware.kryo.Serializer
        public /* bridge */ /* synthetic */ Object read(Kryo kryo, Input input, Class cls) {
            return read(kryo, input, (Class<? extends Character>) cls);
        }

        @Override // com.esotericsoftware.kryo.Serializer
        public void write(Kryo kryo, Output output, Character ch) {
            output.writeChar(ch.charValue());
        }

        @Override // com.esotericsoftware.kryo.Serializer
        public Character read(Kryo kryo, Input input, Class<? extends Character> cls) {
            return Character.valueOf(input.readChar());
        }
    }

    public static class ShortSerializer extends ImmutableSerializer<Short> {
        @Override // com.esotericsoftware.kryo.Serializer
        public /* bridge */ /* synthetic */ Object read(Kryo kryo, Input input, Class cls) {
            return read(kryo, input, (Class<? extends Short>) cls);
        }

        @Override // com.esotericsoftware.kryo.Serializer
        public void write(Kryo kryo, Output output, Short sh) {
            output.writeShort(sh.shortValue());
        }

        @Override // com.esotericsoftware.kryo.Serializer
        public Short read(Kryo kryo, Input input, Class<? extends Short> cls) {
            return Short.valueOf(input.readShort());
        }
    }

    public static class IntSerializer extends ImmutableSerializer<Integer> {
        @Override // com.esotericsoftware.kryo.Serializer
        public /* bridge */ /* synthetic */ Object read(Kryo kryo, Input input, Class cls) {
            return read(kryo, input, (Class<? extends Integer>) cls);
        }

        @Override // com.esotericsoftware.kryo.Serializer
        public void write(Kryo kryo, Output output, Integer num) {
            output.writeInt(num.intValue(), false);
        }

        @Override // com.esotericsoftware.kryo.Serializer
        public Integer read(Kryo kryo, Input input, Class<? extends Integer> cls) {
            return Integer.valueOf(input.readInt(false));
        }
    }

    public static class LongSerializer extends ImmutableSerializer<Long> {
        @Override // com.esotericsoftware.kryo.Serializer
        public /* bridge */ /* synthetic */ Object read(Kryo kryo, Input input, Class cls) {
            return read(kryo, input, (Class<? extends Long>) cls);
        }

        @Override // com.esotericsoftware.kryo.Serializer
        public void write(Kryo kryo, Output output, Long l) {
            output.writeVarLong(l.longValue(), false);
        }

        @Override // com.esotericsoftware.kryo.Serializer
        public Long read(Kryo kryo, Input input, Class<? extends Long> cls) {
            return Long.valueOf(input.readVarLong(false));
        }
    }

    public static class FloatSerializer extends ImmutableSerializer<Float> {
        @Override // com.esotericsoftware.kryo.Serializer
        public /* bridge */ /* synthetic */ Object read(Kryo kryo, Input input, Class cls) {
            return read(kryo, input, (Class<? extends Float>) cls);
        }

        @Override // com.esotericsoftware.kryo.Serializer
        public void write(Kryo kryo, Output output, Float f) {
            output.writeFloat(f.floatValue());
        }

        @Override // com.esotericsoftware.kryo.Serializer
        public Float read(Kryo kryo, Input input, Class<? extends Float> cls) {
            return Float.valueOf(input.readFloat());
        }
    }

    public static class DoubleSerializer extends ImmutableSerializer<Double> {
        @Override // com.esotericsoftware.kryo.Serializer
        public /* bridge */ /* synthetic */ Object read(Kryo kryo, Input input, Class cls) {
            return read(kryo, input, (Class<? extends Double>) cls);
        }

        @Override // com.esotericsoftware.kryo.Serializer
        public void write(Kryo kryo, Output output, Double d) {
            output.writeDouble(d.doubleValue());
        }

        @Override // com.esotericsoftware.kryo.Serializer
        public Double read(Kryo kryo, Input input, Class<? extends Double> cls) {
            return Double.valueOf(input.readDouble());
        }
    }

    public static class StringSerializer extends ImmutableSerializer<String> {
        public StringSerializer() {
            setAcceptsNull(true);
        }

        @Override // com.esotericsoftware.kryo.Serializer
        public /* bridge */ /* synthetic */ Object read(Kryo kryo, Input input, Class cls) {
            return read(kryo, input, (Class<? extends String>) cls);
        }

        @Override // com.esotericsoftware.kryo.Serializer
        public void write(Kryo kryo, Output output, String str) {
            output.writeString(str);
        }

        @Override // com.esotericsoftware.kryo.Serializer
        public String read(Kryo kryo, Input input, Class<? extends String> cls) {
            return input.readString();
        }
    }

    public static class BigIntegerSerializer extends ImmutableSerializer<BigInteger> {
        public BigIntegerSerializer() {
            setAcceptsNull(true);
        }

        @Override // com.esotericsoftware.kryo.Serializer
        public /* bridge */ /* synthetic */ Object read(Kryo kryo, Input input, Class cls) {
            return read(kryo, input, (Class<? extends BigInteger>) cls);
        }

        @Override // com.esotericsoftware.kryo.Serializer
        public void write(Kryo kryo, Output output, BigInteger bigInteger) {
            if (bigInteger == null) {
                output.writeByte((byte) 0);
                return;
            }
            if (bigInteger == BigInteger.ZERO) {
                output.writeByte(2);
                output.writeByte(0);
            } else {
                byte[] byteArray = bigInteger.toByteArray();
                output.writeVarInt(byteArray.length + 1, true);
                output.writeBytes(byteArray);
            }
        }

        @Override // com.esotericsoftware.kryo.Serializer
        public BigInteger read(Kryo kryo, Input input, Class<? extends BigInteger> cls) {
            int varInt = input.readVarInt(true);
            if (varInt == 0) {
                return null;
            }
            byte[] bytes = input.readBytes(varInt - 1);
            if (cls != BigInteger.class && cls != null) {
                try {
                    Constructor<? extends BigInteger> constructor = cls.getConstructor(byte[].class);
                    if (!constructor.isAccessible()) {
                        try {
                            constructor.setAccessible(true);
                        } catch (SecurityException unused) {
                        }
                    }
                    return constructor.newInstance(bytes);
                } catch (Exception e) {
                    throw new KryoException(e);
                }
            }
            if (varInt == 2) {
                byte b = bytes[0];
                if (b == 0) {
                    return BigInteger.ZERO;
                }
                if (b == 1) {
                    return BigInteger.ONE;
                }
                if (b == 10) {
                    return BigInteger.TEN;
                }
            }
            return new BigInteger(bytes);
        }
    }

    public static class BigDecimalSerializer extends ImmutableSerializer<BigDecimal> {
        private final BigIntegerSerializer bigIntegerSerializer = new BigIntegerSerializer();

        public BigDecimalSerializer() {
            setAcceptsNull(true);
        }

        @Override // com.esotericsoftware.kryo.Serializer
        public /* bridge */ /* synthetic */ Object read(Kryo kryo, Input input, Class cls) {
            return read(kryo, input, (Class<? extends BigDecimal>) cls);
        }

        @Override // com.esotericsoftware.kryo.Serializer
        public void write(Kryo kryo, Output output, BigDecimal bigDecimal) {
            if (bigDecimal == null) {
                output.writeByte((byte) 0);
            } else if (bigDecimal == BigDecimal.ZERO) {
                this.bigIntegerSerializer.write(kryo, output, BigInteger.ZERO);
                output.writeInt(0, false);
            } else {
                this.bigIntegerSerializer.write(kryo, output, bigDecimal.unscaledValue());
                output.writeInt(bigDecimal.scale(), false);
            }
        }

        @Override // com.esotericsoftware.kryo.Serializer
        public BigDecimal read(Kryo kryo, Input input, Class<? extends BigDecimal> cls) {
            BigInteger bigInteger = this.bigIntegerSerializer.read(kryo, input, BigInteger.class);
            if (bigInteger == null) {
                return null;
            }
            int i = input.readInt(false);
            if (cls != BigDecimal.class && cls != null) {
                try {
                    Constructor<? extends BigDecimal> constructor = cls.getConstructor(BigInteger.class, Integer.TYPE);
                    if (!constructor.isAccessible()) {
                        try {
                            constructor.setAccessible(true);
                        } catch (SecurityException unused) {
                        }
                    }
                    return constructor.newInstance(bigInteger, Integer.valueOf(i));
                } catch (Exception e) {
                    throw new KryoException(e);
                }
            }
            if (bigInteger == BigInteger.ZERO && i == 0) {
                return BigDecimal.ZERO;
            }
            return new BigDecimal(bigInteger, i);
        }
    }

    public static class ClassSerializer extends ImmutableSerializer<Class> {
        public ClassSerializer() {
            setAcceptsNull(true);
        }

        @Override // com.esotericsoftware.kryo.Serializer
        public /* bridge */ /* synthetic */ Object read(Kryo kryo, Input input, Class cls) {
            return read(kryo, input, (Class<? extends Class>) cls);
        }

        @Override // com.esotericsoftware.kryo.Serializer
        public void write(Kryo kryo, Output output, Class cls) {
            kryo.writeClass(output, cls);
            if (cls != null) {
                if (cls.isPrimitive() || Util.isWrapperClass(cls)) {
                    output.writeBoolean(cls.isPrimitive());
                }
            }
        }

        @Override // com.esotericsoftware.kryo.Serializer
        public Class read(Kryo kryo, Input input, Class<? extends Class> cls) {
            Registration registration = kryo.readClass(input);
            if (registration == null) {
                return null;
            }
            Class type = registration.getType();
            return (!type.isPrimitive() || input.readBoolean()) ? type : Util.getWrapperClass(type);
        }
    }

    public static class DateSerializer extends Serializer<Date> {
        private Date create(Kryo kryo, Class<? extends Date> cls, long j) throws KryoException {
            if (cls == Date.class || cls == null) {
                return new Date(j);
            }
            if (cls == Timestamp.class) {
                return new Timestamp(j);
            }
            if (cls == java.sql.Date.class) {
                return new java.sql.Date(j);
            }
            if (cls == Time.class) {
                return new Time(j);
            }
            try {
                Constructor<? extends Date> constructor = cls.getConstructor(Long.TYPE);
                if (!constructor.isAccessible()) {
                    try {
                        constructor.setAccessible(true);
                    } catch (SecurityException unused) {
                    }
                }
                return constructor.newInstance(Long.valueOf(j));
            } catch (Exception unused2) {
                Date date = (Date) kryo.newInstance(cls);
                date.setTime(j);
                return date;
            }
        }

        @Override // com.esotericsoftware.kryo.Serializer
        public void write(Kryo kryo, Output output, Date date) {
            output.writeVarLong(date.getTime(), true);
        }

        @Override // com.esotericsoftware.kryo.Serializer
        public Date read(Kryo kryo, Input input, Class<? extends Date> cls) {
            return create(kryo, cls, input.readVarLong(true));
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // com.esotericsoftware.kryo.Serializer
        public Date copy(Kryo kryo, Date date) {
            return create(kryo, date.getClass(), date.getTime());
        }
    }

    public static class EnumSerializer extends ImmutableSerializer<Enum> {
        private Object[] enumConstants;

        @Override // com.esotericsoftware.kryo.Serializer
        public /* bridge */ /* synthetic */ Object read(Kryo kryo, Input input, Class cls) {
            return read(kryo, input, (Class<? extends Enum>) cls);
        }

        public EnumSerializer(Class<? extends Enum> cls) {
            setAcceptsNull(true);
            Object[] enumConstants = cls.getEnumConstants();
            this.enumConstants = enumConstants;
            if (enumConstants == null && !Enum.class.equals(cls)) {
                throw new IllegalArgumentException("The type must be an enum: " + cls);
            }
        }

        @Override // com.esotericsoftware.kryo.Serializer
        public void write(Kryo kryo, Output output, Enum r3) {
            if (r3 == null) {
                output.writeVarInt(0, true);
            } else {
                output.writeVarInt(r3.ordinal() + 1, true);
            }
        }

        @Override // com.esotericsoftware.kryo.Serializer
        public Enum read(Kryo kryo, Input input, Class<? extends Enum> cls) {
            int varInt = input.readVarInt(true);
            if (varInt == 0) {
                return null;
            }
            int i = varInt - 1;
            if (i >= 0) {
                Object[] objArr = this.enumConstants;
                if (i <= objArr.length - 1) {
                    return (Enum) objArr[i];
                }
            }
            throw new KryoException("Invalid ordinal for enum \"" + cls.getName() + "\": " + i);
        }
    }

    public static class EnumSetSerializer extends Serializer<EnumSet> {
        @Override // com.esotericsoftware.kryo.Serializer
        public void write(Kryo kryo, Output output, EnumSet enumSet) {
            Serializer serializer;
            if (enumSet.isEmpty()) {
                EnumSet enumSetComplementOf = EnumSet.complementOf(enumSet);
                if (enumSetComplementOf.isEmpty()) {
                    throw new KryoException("An EnumSet must have a defined Enum to be serialized.");
                }
                serializer = kryo.writeClass(output, enumSetComplementOf.iterator().next().getClass()).getSerializer();
            } else {
                serializer = kryo.writeClass(output, enumSet.iterator().next().getClass()).getSerializer();
            }
            output.writeVarInt(enumSet.size(), true);
            Iterator it = enumSet.iterator();
            while (it.hasNext()) {
                serializer.write(kryo, output, it.next());
            }
        }

        @Override // com.esotericsoftware.kryo.Serializer
        public EnumSet read(Kryo kryo, Input input, Class<? extends EnumSet> cls) {
            Registration registration = kryo.readClass(input);
            EnumSet enumSetNoneOf = EnumSet.noneOf(registration.getType());
            Serializer serializer = registration.getSerializer();
            int varInt = input.readVarInt(true);
            for (int i = 0; i < varInt; i++) {
                enumSetNoneOf.add(serializer.read(kryo, input, null));
            }
            return enumSetNoneOf;
        }

        @Override // com.esotericsoftware.kryo.Serializer
        public EnumSet copy(Kryo kryo, EnumSet enumSet) {
            return EnumSet.copyOf(enumSet);
        }
    }

    public static class CurrencySerializer extends ImmutableSerializer<Currency> {
        public CurrencySerializer() {
            setAcceptsNull(true);
        }

        @Override // com.esotericsoftware.kryo.Serializer
        public /* bridge */ /* synthetic */ Object read(Kryo kryo, Input input, Class cls) {
            return read(kryo, input, (Class<? extends Currency>) cls);
        }

        @Override // com.esotericsoftware.kryo.Serializer
        public void write(Kryo kryo, Output output, Currency currency) {
            output.writeString(currency == null ? null : currency.getCurrencyCode());
        }

        @Override // com.esotericsoftware.kryo.Serializer
        public Currency read(Kryo kryo, Input input, Class<? extends Currency> cls) {
            String string = input.readString();
            if (string == null) {
                return null;
            }
            return Currency.getInstance(string);
        }
    }

    public static class StringBufferSerializer extends Serializer<StringBuffer> {
        public StringBufferSerializer() {
            setAcceptsNull(true);
        }

        @Override // com.esotericsoftware.kryo.Serializer
        public void write(Kryo kryo, Output output, StringBuffer stringBuffer) {
            output.writeString(stringBuffer == null ? null : stringBuffer.toString());
        }

        @Override // com.esotericsoftware.kryo.Serializer
        public StringBuffer read(Kryo kryo, Input input, Class<? extends StringBuffer> cls) {
            String string = input.readString();
            if (string == null) {
                return null;
            }
            return new StringBuffer(string);
        }

        @Override // com.esotericsoftware.kryo.Serializer
        public StringBuffer copy(Kryo kryo, StringBuffer stringBuffer) {
            return new StringBuffer(stringBuffer);
        }
    }

    public static class StringBuilderSerializer extends Serializer<StringBuilder> {
        public StringBuilderSerializer() {
            setAcceptsNull(true);
        }

        @Override // com.esotericsoftware.kryo.Serializer
        public void write(Kryo kryo, Output output, StringBuilder sb) {
            output.writeString(sb == null ? null : sb.toString());
        }

        @Override // com.esotericsoftware.kryo.Serializer
        public StringBuilder read(Kryo kryo, Input input, Class<? extends StringBuilder> cls) {
            return input.readStringBuilder();
        }

        @Override // com.esotericsoftware.kryo.Serializer
        public StringBuilder copy(Kryo kryo, StringBuilder sb) {
            return new StringBuilder(sb);
        }
    }

    public static class KryoSerializableSerializer extends Serializer<KryoSerializable> {
        @Override // com.esotericsoftware.kryo.Serializer
        public void write(Kryo kryo, Output output, KryoSerializable kryoSerializable) {
            kryoSerializable.write(kryo, output);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // com.esotericsoftware.kryo.Serializer
        public KryoSerializable read(Kryo kryo, Input input, Class<? extends KryoSerializable> cls) {
            KryoSerializable kryoSerializable = (KryoSerializable) kryo.newInstance(cls);
            kryo.reference(kryoSerializable);
            kryoSerializable.read(kryo, input);
            return kryoSerializable;
        }
    }

    public static class CollectionsEmptyListSerializer extends ImmutableSerializer<Collection> {
        @Override // com.esotericsoftware.kryo.Serializer
        public void write(Kryo kryo, Output output, Collection collection) {
        }

        @Override // com.esotericsoftware.kryo.Serializer
        public /* bridge */ /* synthetic */ Object read(Kryo kryo, Input input, Class cls) {
            return read(kryo, input, (Class<? extends Collection>) cls);
        }

        @Override // com.esotericsoftware.kryo.Serializer
        public Collection read(Kryo kryo, Input input, Class<? extends Collection> cls) {
            return Collections.EMPTY_LIST;
        }
    }

    public static class CollectionsEmptyMapSerializer extends ImmutableSerializer<Map> {
        @Override // com.esotericsoftware.kryo.Serializer
        public void write(Kryo kryo, Output output, Map map) {
        }

        @Override // com.esotericsoftware.kryo.Serializer
        public /* bridge */ /* synthetic */ Object read(Kryo kryo, Input input, Class cls) {
            return read(kryo, input, (Class<? extends Map>) cls);
        }

        @Override // com.esotericsoftware.kryo.Serializer
        public Map read(Kryo kryo, Input input, Class<? extends Map> cls) {
            return Collections.EMPTY_MAP;
        }
    }

    public static class CollectionsEmptySetSerializer extends ImmutableSerializer<Set> {
        @Override // com.esotericsoftware.kryo.Serializer
        public void write(Kryo kryo, Output output, Set set) {
        }

        @Override // com.esotericsoftware.kryo.Serializer
        public /* bridge */ /* synthetic */ Object read(Kryo kryo, Input input, Class cls) {
            return read(kryo, input, (Class<? extends Set>) cls);
        }

        @Override // com.esotericsoftware.kryo.Serializer
        public Set read(Kryo kryo, Input input, Class<? extends Set> cls) {
            return Collections.EMPTY_SET;
        }
    }

    public static class CollectionsSingletonListSerializer extends Serializer<List> {
        @Override // com.esotericsoftware.kryo.Serializer
        public void write(Kryo kryo, Output output, List list) {
            kryo.writeClassAndObject(output, list.get(0));
        }

        @Override // com.esotericsoftware.kryo.Serializer
        public List read(Kryo kryo, Input input, Class<? extends List> cls) {
            return Collections.singletonList(kryo.readClassAndObject(input));
        }

        @Override // com.esotericsoftware.kryo.Serializer
        public List copy(Kryo kryo, List list) {
            return Collections.singletonList(kryo.copy(list.get(0)));
        }
    }

    public static class CollectionsSingletonMapSerializer extends Serializer<Map> {
        @Override // com.esotericsoftware.kryo.Serializer
        public void write(Kryo kryo, Output output, Map map) {
            Map.Entry entry = (Map.Entry) map.entrySet().iterator().next();
            kryo.writeClassAndObject(output, entry.getKey());
            kryo.writeClassAndObject(output, entry.getValue());
        }

        @Override // com.esotericsoftware.kryo.Serializer
        public Map read(Kryo kryo, Input input, Class<? extends Map> cls) {
            return Collections.singletonMap(kryo.readClassAndObject(input), kryo.readClassAndObject(input));
        }

        @Override // com.esotericsoftware.kryo.Serializer
        public Map copy(Kryo kryo, Map map) {
            Map.Entry entry = (Map.Entry) map.entrySet().iterator().next();
            return Collections.singletonMap(kryo.copy(entry.getKey()), kryo.copy(entry.getValue()));
        }
    }

    public static class CollectionsSingletonSetSerializer extends Serializer<Set> {
        @Override // com.esotericsoftware.kryo.Serializer
        public void write(Kryo kryo, Output output, Set set) {
            kryo.writeClassAndObject(output, set.iterator().next());
        }

        @Override // com.esotericsoftware.kryo.Serializer
        public Set read(Kryo kryo, Input input, Class<? extends Set> cls) {
            return Collections.singleton(kryo.readClassAndObject(input));
        }

        @Override // com.esotericsoftware.kryo.Serializer
        public Set copy(Kryo kryo, Set set) {
            return Collections.singleton(kryo.copy(set.iterator().next()));
        }
    }

    public static class TimeZoneSerializer extends ImmutableSerializer<TimeZone> {
        @Override // com.esotericsoftware.kryo.Serializer
        public /* bridge */ /* synthetic */ Object read(Kryo kryo, Input input, Class cls) {
            return read(kryo, input, (Class<? extends TimeZone>) cls);
        }

        @Override // com.esotericsoftware.kryo.Serializer
        public void write(Kryo kryo, Output output, TimeZone timeZone) {
            output.writeString(timeZone.getID());
        }

        @Override // com.esotericsoftware.kryo.Serializer
        public TimeZone read(Kryo kryo, Input input, Class<? extends TimeZone> cls) {
            return TimeZone.getTimeZone(input.readString());
        }
    }

    public static class CalendarSerializer extends Serializer<Calendar> {
        private static final long DEFAULT_GREGORIAN_CUTOVER = -12219292800000L;
        TimeZoneSerializer timeZoneSerializer = new TimeZoneSerializer();

        @Override // com.esotericsoftware.kryo.Serializer
        public void write(Kryo kryo, Output output, Calendar calendar) {
            this.timeZoneSerializer.write(kryo, output, calendar.getTimeZone());
            output.writeVarLong(calendar.getTimeInMillis(), true);
            output.writeBoolean(calendar.isLenient());
            output.writeInt(calendar.getFirstDayOfWeek(), true);
            output.writeInt(calendar.getMinimalDaysInFirstWeek(), true);
            if (calendar instanceof GregorianCalendar) {
                output.writeVarLong(((GregorianCalendar) calendar).getGregorianChange().getTime(), false);
            } else {
                output.writeVarLong(DEFAULT_GREGORIAN_CUTOVER, false);
            }
        }

        @Override // com.esotericsoftware.kryo.Serializer
        public Calendar read(Kryo kryo, Input input, Class<? extends Calendar> cls) {
            Calendar calendar = Calendar.getInstance(this.timeZoneSerializer.read(kryo, input, TimeZone.class));
            calendar.setTimeInMillis(input.readVarLong(true));
            calendar.setLenient(input.readBoolean());
            calendar.setFirstDayOfWeek(input.readInt(true));
            calendar.setMinimalDaysInFirstWeek(input.readInt(true));
            long varLong = input.readVarLong(false);
            if (varLong != DEFAULT_GREGORIAN_CUTOVER && (calendar instanceof GregorianCalendar)) {
                ((GregorianCalendar) calendar).setGregorianChange(new Date(varLong));
            }
            return calendar;
        }

        @Override // com.esotericsoftware.kryo.Serializer
        public Calendar copy(Kryo kryo, Calendar calendar) {
            return (Calendar) calendar.clone();
        }
    }

    public static class TreeMapSerializer extends MapSerializer<TreeMap> {
        @Override // com.esotericsoftware.kryo.serializers.MapSerializer
        protected /* bridge */ /* synthetic */ Map create(Kryo kryo, Input input, Class cls, int i) {
            return create(kryo, input, (Class<? extends TreeMap>) cls, i);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.esotericsoftware.kryo.serializers.MapSerializer
        public void writeHeader(Kryo kryo, Output output, TreeMap treeMap) {
            kryo.writeClassAndObject(output, treeMap.comparator());
        }

        @Override // com.esotericsoftware.kryo.serializers.MapSerializer
        protected TreeMap create(Kryo kryo, Input input, Class<? extends TreeMap> cls, int i) {
            return createTreeMap(cls, (Comparator) kryo.readClassAndObject(input));
        }

        /* JADX INFO: Access modifiers changed from: protected */
        /* JADX WARN: Multi-variable type inference failed */
        @Override // com.esotericsoftware.kryo.serializers.MapSerializer
        public TreeMap createCopy(Kryo kryo, TreeMap treeMap) {
            return createTreeMap(treeMap.getClass(), treeMap.comparator());
        }

        private TreeMap createTreeMap(Class<? extends TreeMap> cls, Comparator comparator) {
            if (cls == TreeMap.class || cls == null) {
                return new TreeMap(comparator);
            }
            try {
                Constructor<? extends TreeMap> constructor = cls.getConstructor(Comparator.class);
                if (!constructor.isAccessible()) {
                    try {
                        constructor.setAccessible(true);
                    } catch (SecurityException unused) {
                    }
                }
                return constructor.newInstance(comparator);
            } catch (Exception e) {
                throw new KryoException(e);
            }
        }
    }

    public static class ConcurrentSkipListMapSerializer extends MapSerializer<ConcurrentSkipListMap> {
        @Override // com.esotericsoftware.kryo.serializers.MapSerializer
        protected /* bridge */ /* synthetic */ Map create(Kryo kryo, Input input, Class cls, int i) {
            return create(kryo, input, (Class<? extends ConcurrentSkipListMap>) cls, i);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.esotericsoftware.kryo.serializers.MapSerializer
        public void writeHeader(Kryo kryo, Output output, ConcurrentSkipListMap concurrentSkipListMap) {
            kryo.writeClassAndObject(output, concurrentSkipListMap.comparator());
        }

        @Override // com.esotericsoftware.kryo.serializers.MapSerializer
        protected ConcurrentSkipListMap create(Kryo kryo, Input input, Class<? extends ConcurrentSkipListMap> cls, int i) {
            return createConcurrentSkipListMap(cls, (Comparator) kryo.readClassAndObject(input));
        }

        /* JADX INFO: Access modifiers changed from: protected */
        /* JADX WARN: Multi-variable type inference failed */
        @Override // com.esotericsoftware.kryo.serializers.MapSerializer
        public ConcurrentSkipListMap createCopy(Kryo kryo, ConcurrentSkipListMap concurrentSkipListMap) {
            return createConcurrentSkipListMap(concurrentSkipListMap.getClass(), concurrentSkipListMap.comparator());
        }

        private ConcurrentSkipListMap createConcurrentSkipListMap(Class<? extends ConcurrentSkipListMap> cls, Comparator comparator) {
            if (cls == ConcurrentSkipListMap.class || cls == null) {
                return new ConcurrentSkipListMap(comparator);
            }
            try {
                Constructor<? extends ConcurrentSkipListMap> constructor = cls.getConstructor(Comparator.class);
                if (!constructor.isAccessible()) {
                    try {
                        constructor.setAccessible(true);
                    } catch (SecurityException unused) {
                    }
                }
                return constructor.newInstance(comparator);
            } catch (Exception e) {
                throw new KryoException(e);
            }
        }
    }

    public static class TreeSetSerializer extends CollectionSerializer<TreeSet> {
        @Override // com.esotericsoftware.kryo.serializers.CollectionSerializer
        protected /* bridge */ /* synthetic */ Collection create(Kryo kryo, Input input, Class cls, int i) {
            return create(kryo, input, (Class<? extends TreeSet>) cls, i);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.esotericsoftware.kryo.serializers.CollectionSerializer
        public void writeHeader(Kryo kryo, Output output, TreeSet treeSet) {
            kryo.writeClassAndObject(output, treeSet.comparator());
        }

        @Override // com.esotericsoftware.kryo.serializers.CollectionSerializer
        protected TreeSet create(Kryo kryo, Input input, Class<? extends TreeSet> cls, int i) {
            return createTreeSet(cls, (Comparator) kryo.readClassAndObject(input));
        }

        /* JADX INFO: Access modifiers changed from: protected */
        /* JADX WARN: Multi-variable type inference failed */
        @Override // com.esotericsoftware.kryo.serializers.CollectionSerializer
        public TreeSet createCopy(Kryo kryo, TreeSet treeSet) {
            return createTreeSet(treeSet.getClass(), treeSet.comparator());
        }

        private TreeSet createTreeSet(Class<? extends Collection> cls, Comparator comparator) {
            if (cls == TreeSet.class || cls == null) {
                return new TreeSet(comparator);
            }
            try {
                Constructor<? extends Collection> constructor = cls.getConstructor(Comparator.class);
                if (!constructor.isAccessible()) {
                    try {
                        constructor.setAccessible(true);
                    } catch (SecurityException unused) {
                    }
                }
                return (TreeSet) constructor.newInstance(comparator);
            } catch (Exception e) {
                throw new KryoException(e);
            }
        }
    }

    public static class PriorityQueueSerializer extends CollectionSerializer<PriorityQueue> {
        @Override // com.esotericsoftware.kryo.serializers.CollectionSerializer
        protected /* bridge */ /* synthetic */ Collection create(Kryo kryo, Input input, Class cls, int i) {
            return create(kryo, input, (Class<? extends PriorityQueue>) cls, i);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.esotericsoftware.kryo.serializers.CollectionSerializer
        public void writeHeader(Kryo kryo, Output output, PriorityQueue priorityQueue) {
            kryo.writeClassAndObject(output, priorityQueue.comparator());
        }

        @Override // com.esotericsoftware.kryo.serializers.CollectionSerializer
        protected PriorityQueue create(Kryo kryo, Input input, Class<? extends PriorityQueue> cls, int i) {
            return createPriorityQueue(cls, i, (Comparator) kryo.readClassAndObject(input));
        }

        /* JADX INFO: Access modifiers changed from: protected */
        /* JADX WARN: Multi-variable type inference failed */
        @Override // com.esotericsoftware.kryo.serializers.CollectionSerializer
        public PriorityQueue createCopy(Kryo kryo, PriorityQueue priorityQueue) {
            return createPriorityQueue(priorityQueue.getClass(), priorityQueue.size(), priorityQueue.comparator());
        }

        private PriorityQueue createPriorityQueue(Class<? extends Collection> cls, int i, Comparator comparator) {
            if (cls == PriorityQueue.class || cls == null) {
                return new PriorityQueue(i, comparator);
            }
            try {
                Constructor<? extends Collection> constructor = cls.getConstructor(Integer.TYPE, Comparator.class);
                if (!constructor.isAccessible()) {
                    try {
                        constructor.setAccessible(true);
                    } catch (SecurityException unused) {
                    }
                }
                return (PriorityQueue) constructor.newInstance(comparator);
            } catch (Exception e) {
                throw new KryoException(e);
            }
        }
    }

    public static class LocaleSerializer extends ImmutableSerializer<Locale> {
        public static final Locale SPANISH = new Locale("es", "", "");
        public static final Locale SPAIN = new Locale("es", "ES", "");

        @Override // com.esotericsoftware.kryo.Serializer
        public /* bridge */ /* synthetic */ Object read(Kryo kryo, Input input, Class cls) {
            return read(kryo, input, (Class<? extends Locale>) cls);
        }

        protected Locale create(String str, String str2, String str3) {
            Locale locale = Locale.getDefault();
            if (isSameLocale(locale, str, str2, str3)) {
                return locale;
            }
            if (locale != Locale.US && isSameLocale(Locale.US, str, str2, str3)) {
                return Locale.US;
            }
            if (isSameLocale(Locale.ENGLISH, str, str2, str3)) {
                return Locale.ENGLISH;
            }
            if (isSameLocale(Locale.GERMAN, str, str2, str3)) {
                return Locale.GERMAN;
            }
            Locale locale2 = SPANISH;
            if (isSameLocale(locale2, str, str2, str3)) {
                return locale2;
            }
            if (isSameLocale(Locale.FRENCH, str, str2, str3)) {
                return Locale.FRENCH;
            }
            if (isSameLocale(Locale.ITALIAN, str, str2, str3)) {
                return Locale.ITALIAN;
            }
            if (isSameLocale(Locale.JAPANESE, str, str2, str3)) {
                return Locale.JAPANESE;
            }
            if (isSameLocale(Locale.KOREAN, str, str2, str3)) {
                return Locale.KOREAN;
            }
            if (isSameLocale(Locale.SIMPLIFIED_CHINESE, str, str2, str3)) {
                return Locale.SIMPLIFIED_CHINESE;
            }
            if (isSameLocale(Locale.CHINESE, str, str2, str3)) {
                return Locale.CHINESE;
            }
            if (isSameLocale(Locale.TRADITIONAL_CHINESE, str, str2, str3)) {
                return Locale.TRADITIONAL_CHINESE;
            }
            if (isSameLocale(Locale.UK, str, str2, str3)) {
                return Locale.UK;
            }
            if (isSameLocale(Locale.GERMANY, str, str2, str3)) {
                return Locale.GERMANY;
            }
            Locale locale3 = SPAIN;
            if (isSameLocale(locale3, str, str2, str3)) {
                return locale3;
            }
            if (isSameLocale(Locale.FRANCE, str, str2, str3)) {
                return Locale.FRANCE;
            }
            if (isSameLocale(Locale.ITALY, str, str2, str3)) {
                return Locale.ITALY;
            }
            if (isSameLocale(Locale.JAPAN, str, str2, str3)) {
                return Locale.JAPAN;
            }
            if (isSameLocale(Locale.KOREA, str, str2, str3)) {
                return Locale.KOREA;
            }
            if (isSameLocale(Locale.CANADA, str, str2, str3)) {
                return Locale.CANADA;
            }
            return isSameLocale(Locale.CANADA_FRENCH, str, str2, str3) ? Locale.CANADA_FRENCH : new Locale(str, str2, str3);
        }

        @Override // com.esotericsoftware.kryo.Serializer
        public void write(Kryo kryo, Output output, Locale locale) {
            output.writeAscii(locale.getLanguage());
            output.writeAscii(locale.getCountry());
            output.writeString(locale.getVariant());
        }

        @Override // com.esotericsoftware.kryo.Serializer
        public Locale read(Kryo kryo, Input input, Class<? extends Locale> cls) {
            return create(input.readString(), input.readString(), input.readString());
        }

        protected static boolean isSameLocale(Locale locale, String str, String str2, String str3) {
            return locale.getLanguage().equals(str) && locale.getCountry().equals(str2) && locale.getVariant().equals(str3);
        }
    }

    public static class CharsetSerializer extends ImmutableSerializer<Charset> {
        @Override // com.esotericsoftware.kryo.Serializer
        public /* bridge */ /* synthetic */ Object read(Kryo kryo, Input input, Class cls) {
            return read(kryo, input, (Class<? extends Charset>) cls);
        }

        @Override // com.esotericsoftware.kryo.Serializer
        public void write(Kryo kryo, Output output, Charset charset) {
            output.writeString(charset.name());
        }

        @Override // com.esotericsoftware.kryo.Serializer
        public Charset read(Kryo kryo, Input input, Class<? extends Charset> cls) {
            return Charset.forName(input.readString());
        }
    }

    public static class URLSerializer extends ImmutableSerializer<URL> {
        @Override // com.esotericsoftware.kryo.Serializer
        public /* bridge */ /* synthetic */ Object read(Kryo kryo, Input input, Class cls) {
            return read(kryo, input, (Class<? extends URL>) cls);
        }

        @Override // com.esotericsoftware.kryo.Serializer
        public void write(Kryo kryo, Output output, URL url) {
            output.writeString(url.toExternalForm());
        }

        @Override // com.esotericsoftware.kryo.Serializer
        public URL read(Kryo kryo, Input input, Class<? extends URL> cls) {
            try {
                return new URL(input.readString());
            } catch (MalformedURLException e) {
                throw new KryoException(e);
            }
        }
    }

    public static class ArraysAsListSerializer extends CollectionSerializer<List> {
        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.esotericsoftware.kryo.serializers.CollectionSerializer
        public List create(Kryo kryo, Input input, Class cls, int i) {
            return new ArrayList(i);
        }

        @Override // com.esotericsoftware.kryo.serializers.CollectionSerializer, com.esotericsoftware.kryo.Serializer
        public List read(Kryo kryo, Input input, Class cls) {
            List list = (List) super.read(kryo, input, cls);
            if (list == null) {
                return null;
            }
            return Arrays.asList(list.toArray());
        }

        @Override // com.esotericsoftware.kryo.serializers.CollectionSerializer
        public List copy(Kryo kryo, List list) {
            Object[] objArr = new Object[list.size()];
            List listAsList = Arrays.asList(objArr);
            kryo.reference(listAsList);
            for (int i = 0; i < list.size(); i++) {
                objArr[i] = kryo.copy(list.get(i));
            }
            return listAsList;
        }
    }

    public static class BitSetSerializer extends Serializer<BitSet> {
        @Override // com.esotericsoftware.kryo.Serializer
        public void write(Kryo kryo, Output output, BitSet bitSet) {
            long[] longArray = bitSet.toLongArray();
            output.writeVarInt(longArray.length, true);
            output.writeLongs(longArray, 0, longArray.length);
        }

        @Override // com.esotericsoftware.kryo.Serializer
        public BitSet read(Kryo kryo, Input input, Class<? extends BitSet> cls) {
            return BitSet.valueOf(input.readLongs(input.readVarInt(true)));
        }

        @Override // com.esotericsoftware.kryo.Serializer
        public BitSet copy(Kryo kryo, BitSet bitSet) {
            return BitSet.valueOf(bitSet.toLongArray());
        }
    }
}

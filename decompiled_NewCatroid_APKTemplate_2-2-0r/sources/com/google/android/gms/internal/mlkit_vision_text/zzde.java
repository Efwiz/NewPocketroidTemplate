package com.google.android.gms.internal.mlkit_vision_text;

import com.google.firebase.encoders.EncodingException;
import com.google.firebase.encoders.FieldDescriptor;
import com.google.firebase.encoders.ObjectEncoder;
import com.google.firebase.encoders.ObjectEncoderContext;
import com.google.firebase.encoders.ValueEncoder;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.Charset;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import org.antlr.stringtemplate.language.ASTExpr;

/* JADX INFO: compiled from: com.google.android.gms:play-services-mlkit-text-recognition@@16.1.3 */
/* JADX INFO: loaded from: classes9.dex */
final class zzde implements ObjectEncoderContext {
    private static final Charset zza = Charset.forName("UTF-8");
    private static final FieldDescriptor zzf;
    private static final FieldDescriptor zzg;
    private static final ObjectEncoder<Map.Entry<Object, Object>> zzh;
    private OutputStream zzb;
    private final Map<Class<?>, ObjectEncoder<?>> zzc;
    private final Map<Class<?>, ValueEncoder<?>> zzd;
    private final ObjectEncoder<Object> zze;

    static {
        FieldDescriptor.Builder builder = FieldDescriptor.builder(ASTExpr.DEFAULT_MAP_KEY_NAME);
        zzcy zzcyVar = new zzcy();
        zzcyVar.zza(1);
        zzf = builder.withProperty(zzcyVar.zzb()).build();
        FieldDescriptor.Builder builder2 = FieldDescriptor.builder("value");
        zzcy zzcyVar2 = new zzcy();
        zzcyVar2.zza(2);
        zzg = builder2.withProperty(zzcyVar2.zzb()).build();
        zzh = zzdd.zza;
    }

    zzde(OutputStream outputStream, Map<Class<?>, ObjectEncoder<?>> map, Map<Class<?>, ValueEncoder<?>> map2, ObjectEncoder<Object> objectEncoder) {
        this.zzb = outputStream;
        this.zzc = map;
        this.zzd = map2;
        this.zze = objectEncoder;
    }

    static final /* synthetic */ void zze(Map.Entry entry, ObjectEncoderContext objectEncoderContext) throws IOException {
        objectEncoderContext.add(zzf, entry.getKey());
        objectEncoderContext.add(zzg, entry.getValue());
    }

    private final <T> zzde zzf(ObjectEncoder<T> objectEncoder, FieldDescriptor fieldDescriptor, T t) throws IOException {
        long jZzg = zzg(objectEncoder, t);
        if (jZzg == 0) {
            return this;
        }
        zzl((zzj(fieldDescriptor) << 3) | 2);
        zzm(jZzg);
        objectEncoder.encode(t, this);
        return this;
    }

    private final <T> long zzg(ObjectEncoder<T> objectEncoder, T t) throws IOException {
        zzcz zzczVar = new zzcz();
        try {
            OutputStream outputStream = this.zzb;
            this.zzb = zzczVar;
            try {
                objectEncoder.encode(t, this);
                this.zzb = outputStream;
                long jZza = zzczVar.zza();
                zzczVar.close();
                return jZza;
            } catch (Throwable th) {
                this.zzb = outputStream;
                throw th;
            }
        } catch (Throwable th2) {
            try {
                zzczVar.close();
            } catch (Throwable th3) {
                zzcw.zza(th2, th3);
            }
            throw th2;
        }
    }

    private final <T> zzde zzh(ValueEncoder<T> valueEncoder, FieldDescriptor fieldDescriptor, T t) throws IOException {
        valueEncoder.encode(t, new zzdi(fieldDescriptor, this));
        return this;
    }

    private static ByteBuffer zzi(int i) {
        return ByteBuffer.allocate(i).order(ByteOrder.LITTLE_ENDIAN);
    }

    private static int zzj(FieldDescriptor fieldDescriptor) {
        zzdc zzdcVar = (zzdc) fieldDescriptor.getProperty(zzdc.class);
        if (zzdcVar != null) {
            return zzdcVar.zza();
        }
        throw new EncodingException("Field has no @Protobuf config");
    }

    private static zzdc zzk(FieldDescriptor fieldDescriptor) {
        zzdc zzdcVar = (zzdc) fieldDescriptor.getProperty(zzdc.class);
        if (zzdcVar != null) {
            return zzdcVar;
        }
        throw new EncodingException("Field has no @Protobuf config");
    }

    private final void zzl(int i) throws IOException {
        while ((i & (-128)) != 0) {
            this.zzb.write((i & 127) | 128);
            i >>>= 7;
        }
        this.zzb.write(i & 127);
    }

    private final void zzm(long j) throws IOException {
        while (((-128) & j) != 0) {
            this.zzb.write((((int) j) & 127) | 128);
            j >>>= 7;
        }
        this.zzb.write(((int) j) & 127);
    }

    @Override // com.google.firebase.encoders.ObjectEncoderContext
    public final ObjectEncoderContext add(FieldDescriptor fieldDescriptor, double d) throws IOException {
        if (d == 0.0d) {
            return this;
        }
        zzl((zzj(fieldDescriptor) << 3) | 1);
        this.zzb.write(zzi(8).putDouble(d).array());
        return this;
    }

    @Override // com.google.firebase.encoders.ObjectEncoderContext
    public final /* bridge */ /* synthetic */ ObjectEncoderContext add(FieldDescriptor fieldDescriptor, int i) throws IOException {
        zza(fieldDescriptor, i);
        return this;
    }

    @Override // com.google.firebase.encoders.ObjectEncoderContext
    public final /* bridge */ /* synthetic */ ObjectEncoderContext add(FieldDescriptor fieldDescriptor, long j) throws IOException {
        zzb(fieldDescriptor, j);
        return this;
    }

    @Override // com.google.firebase.encoders.ObjectEncoderContext
    public final /* bridge */ /* synthetic */ ObjectEncoderContext add(FieldDescriptor fieldDescriptor, boolean z) throws IOException {
        zzc(fieldDescriptor, z);
        return this;
    }

    @Override // com.google.firebase.encoders.ObjectEncoderContext
    public final ObjectEncoderContext inline(Object obj) throws IOException {
        zzd(obj);
        return this;
    }

    @Override // com.google.firebase.encoders.ObjectEncoderContext
    public final ObjectEncoderContext nested(FieldDescriptor fieldDescriptor) throws IOException {
        throw new EncodingException("nested() is not implemented for protobuf encoding.");
    }

    public final zzde zza(FieldDescriptor fieldDescriptor, int i) throws IOException {
        if (i == 0) {
            return this;
        }
        zzdc zzdcVarZzk = zzk(fieldDescriptor);
        zzdb zzdbVar = zzdb.DEFAULT;
        int iOrdinal = zzdcVarZzk.zzb().ordinal();
        if (iOrdinal == 0) {
            zzl(zzdcVarZzk.zza() << 3);
            zzl(i);
        } else if (iOrdinal == 1) {
            zzl(zzdcVarZzk.zza() << 3);
            zzl((i + i) ^ (i >> 31));
        } else if (iOrdinal == 2) {
            zzl((zzdcVarZzk.zza() << 3) | 5);
            this.zzb.write(zzi(4).putInt(i).array());
        }
        return this;
    }

    public final zzde zzb(FieldDescriptor fieldDescriptor, long j) throws IOException {
        if (j == 0) {
            return this;
        }
        zzdc zzdcVarZzk = zzk(fieldDescriptor);
        zzdb zzdbVar = zzdb.DEFAULT;
        int iOrdinal = zzdcVarZzk.zzb().ordinal();
        if (iOrdinal == 0) {
            zzl(zzdcVarZzk.zza() << 3);
            zzm(j);
        } else if (iOrdinal == 1) {
            zzl(zzdcVarZzk.zza() << 3);
            zzm((j >> 63) ^ (j + j));
        } else if (iOrdinal == 2) {
            zzl((zzdcVarZzk.zza() << 3) | 1);
            this.zzb.write(zzi(8).putLong(j).array());
        }
        return this;
    }

    public final zzde zzc(FieldDescriptor fieldDescriptor, boolean z) throws IOException {
        if (!z) {
            return this;
        }
        zza(fieldDescriptor, 1);
        return this;
    }

    final zzde zzd(Object obj) throws IOException {
        if (obj == null) {
            return this;
        }
        ObjectEncoder<?> objectEncoder = this.zzc.get(obj.getClass());
        if (objectEncoder != null) {
            objectEncoder.encode(obj, this);
            return this;
        }
        String strValueOf = String.valueOf(obj.getClass());
        StringBuilder sb = new StringBuilder(String.valueOf(strValueOf).length() + 15);
        sb.append("No encoder for ");
        sb.append(strValueOf);
        throw new EncodingException(sb.toString());
    }

    @Override // com.google.firebase.encoders.ObjectEncoderContext
    public final ObjectEncoderContext nested(String str) throws IOException {
        return nested(FieldDescriptor.of(str));
    }

    @Override // com.google.firebase.encoders.ObjectEncoderContext
    public final ObjectEncoderContext add(FieldDescriptor fieldDescriptor, float f) throws IOException {
        if (f == 0.0f) {
            return this;
        }
        zzl((zzj(fieldDescriptor) << 3) | 5);
        this.zzb.write(zzi(4).putFloat(f).array());
        return this;
    }

    @Override // com.google.firebase.encoders.ObjectEncoderContext
    public final ObjectEncoderContext add(FieldDescriptor fieldDescriptor, Object obj) throws IOException {
        if (obj == null) {
            return this;
        }
        if (obj instanceof CharSequence) {
            CharSequence charSequence = (CharSequence) obj;
            if (charSequence.length() == 0) {
                return this;
            }
            zzl((zzj(fieldDescriptor) << 3) | 2);
            byte[] bytes = charSequence.toString().getBytes(zza);
            zzl(bytes.length);
            this.zzb.write(bytes);
            return this;
        }
        if (obj instanceof Collection) {
            Iterator it = ((Collection) obj).iterator();
            while (it.hasNext()) {
                add(fieldDescriptor, it.next());
            }
            return this;
        }
        if (obj instanceof Map) {
            Iterator it2 = ((Map) obj).entrySet().iterator();
            while (it2.hasNext()) {
                zzf(zzh, fieldDescriptor, (Map.Entry) it2.next());
            }
            return this;
        }
        if (obj instanceof Double) {
            add(fieldDescriptor, ((Double) obj).doubleValue());
            return this;
        }
        if (obj instanceof Float) {
            add(fieldDescriptor, ((Float) obj).floatValue());
            return this;
        }
        if (obj instanceof Number) {
            zzb(fieldDescriptor, ((Number) obj).longValue());
            return this;
        }
        if (obj instanceof Boolean) {
            zzc(fieldDescriptor, ((Boolean) obj).booleanValue());
            return this;
        }
        if (obj instanceof byte[]) {
            byte[] bArr = (byte[]) obj;
            int length = bArr.length;
            if (length == 0) {
                return this;
            }
            zzl((zzj(fieldDescriptor) << 3) | 2);
            zzl(length);
            this.zzb.write(bArr);
            return this;
        }
        ObjectEncoder<?> objectEncoder = this.zzc.get(obj.getClass());
        if (objectEncoder != null) {
            zzf(objectEncoder, fieldDescriptor, obj);
            return this;
        }
        ValueEncoder<?> valueEncoder = this.zzd.get(obj.getClass());
        if (valueEncoder != null) {
            zzh(valueEncoder, fieldDescriptor, obj);
            return this;
        }
        if (obj instanceof zzda) {
            zza(fieldDescriptor, ((zzda) obj).zza());
            return this;
        }
        if (obj instanceof Enum) {
            zza(fieldDescriptor, ((Enum) obj).ordinal());
            return this;
        }
        zzf(this.zze, fieldDescriptor, obj);
        return this;
    }

    @Override // com.google.firebase.encoders.ObjectEncoderContext
    public final ObjectEncoderContext add(String str, double d) throws IOException {
        add(FieldDescriptor.of(str), d);
        return this;
    }

    @Override // com.google.firebase.encoders.ObjectEncoderContext
    public final ObjectEncoderContext add(String str, int i) throws IOException {
        zza(FieldDescriptor.of(str), i);
        return this;
    }

    @Override // com.google.firebase.encoders.ObjectEncoderContext
    public final ObjectEncoderContext add(String str, long j) throws IOException {
        zzb(FieldDescriptor.of(str), j);
        return this;
    }

    @Override // com.google.firebase.encoders.ObjectEncoderContext
    public final ObjectEncoderContext add(String str, Object obj) throws IOException {
        add(FieldDescriptor.of(str), obj);
        return this;
    }

    @Override // com.google.firebase.encoders.ObjectEncoderContext
    public final ObjectEncoderContext add(String str, boolean z) throws IOException {
        zzc(FieldDescriptor.of(str), z);
        return this;
    }
}

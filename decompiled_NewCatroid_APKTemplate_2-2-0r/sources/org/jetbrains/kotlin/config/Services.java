package org.jetbrains.kotlin.config;

import androidx.exifinterface.media.ExifInterface;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import java.util.HashMap;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: Services.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\b\b\u0018\u0000 \u000b2\u00020\u0001:\u0002\n\u000bB\u001f\b\u0002\u0012\u0016\u0010\u0002\u001a\u0012\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u0004\u0012\u0004\u0012\u00020\u00010\u0003¢\u0006\u0002\u0010\u0005J$\u0010\u0006\u001a\u0004\u0018\u0001H\u0007\"\u0004\b\u0000\u0010\u00072\f\u0010\b\u001a\b\u0012\u0004\u0012\u0002H\u00070\u0004H\u0086\u0002¢\u0006\u0002\u0010\tR\u001e\u0010\u0002\u001a\u0012\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u0004\u0012\u0004\u0012\u00020\u00010\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/config/Services;", "", "map", "", "Ljava/lang/Class;", "(Ljava/util/Map;)V", "get", ExifInterface.GPS_DIRECTION_TRUE, "interfaceClass", "(Ljava/lang/Class;)Ljava/lang/Object;", "Builder", "Companion", "util"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class Services {
    public static final Services EMPTY = new Builder().build();
    private final Map<Class<?>, Object> map;

    public /* synthetic */ Services(Map map, DefaultConstructorMarker defaultConstructorMarker) {
        this(map);
    }

    private Services(Map<Class<?>, ? extends Object> map) {
        this.map = map;
    }

    public final <T> T get(Class<T> interfaceClass) {
        Intrinsics.checkNotNullParameter(interfaceClass, "interfaceClass");
        return (T) this.map.get(interfaceClass);
    }

    /* JADX INFO: compiled from: Services.kt */
    @Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0006\u0010\u0006\u001a\u00020\u0007J+\u0010\b\u001a\u00020\u0000\"\b\b\u0000\u0010\t*\u00020\u00012\f\u0010\n\u001a\b\u0012\u0004\u0012\u0002H\t0\u00052\u0006\u0010\u000b\u001a\u0002H\t¢\u0006\u0002\u0010\fR\u001e\u0010\u0003\u001a\u0012\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u0005\u0012\u0004\u0012\u00020\u00010\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/config/Services$Builder;", "", "()V", "map", "Ljava/util/HashMap;", "Ljava/lang/Class;", JsonPOJOBuilder.DEFAULT_BUILD_METHOD, "Lorg/jetbrains/kotlin/config/Services;", "register", ExifInterface.GPS_DIRECTION_TRUE, "interfaceClass", "implementation", "(Ljava/lang/Class;Ljava/lang/Object;)Lorg/jetbrains/kotlin/config/Services$Builder;", "util"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Builder {
        private final HashMap<Class<?>, Object> map = new HashMap<>();

        public final <T> Builder register(Class<T> interfaceClass, T implementation) {
            Intrinsics.checkNotNullParameter(interfaceClass, "interfaceClass");
            Intrinsics.checkNotNullParameter(implementation, "implementation");
            this.map.put(interfaceClass, implementation);
            return this;
        }

        public final Services build() {
            return new Services(this.map, null);
        }
    }
}

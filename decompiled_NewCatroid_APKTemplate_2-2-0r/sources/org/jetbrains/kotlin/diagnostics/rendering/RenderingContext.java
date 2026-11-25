package org.jetbrains.kotlin.diagnostics.rendering;

import androidx.exifinterface.media.ExifInterface;
import java.util.Collection;
import java.util.LinkedHashMap;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.antlr.stringtemplate.language.ASTExpr;

/* JADX INFO: compiled from: RenderingContext.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u0000 \b2\u00020\u0001:\u0004\b\t\n\u000bB\u0007\b\u0004¢\u0006\u0002\u0010\u0002J\"\u0010\u0003\u001a\u0002H\u0004\"\u0004\b\u0000\u0010\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u0002H\u00040\u0006H¦\u0002¢\u0006\u0002\u0010\u0007\u0082\u0001\u0002\f\r¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/diagnostics/rendering/RenderingContext;", "", "()V", "get", ExifInterface.GPS_DIRECTION_TRUE, ASTExpr.DEFAULT_MAP_KEY_NAME, "Lorg/jetbrains/kotlin/diagnostics/rendering/RenderingContext$Key;", "(Lorg/jetbrains/kotlin/diagnostics/rendering/RenderingContext$Key;)Ljava/lang/Object;", "Companion", "Empty", "Impl", "Key", "Lorg/jetbrains/kotlin/diagnostics/rendering/RenderingContext$Empty;", "Lorg/jetbrains/kotlin/diagnostics/rendering/RenderingContext$Impl;", "frontend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class RenderingContext {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);

    public /* synthetic */ RenderingContext(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    @JvmStatic
    public static final RenderingContext of(Object... objArr) {
        return INSTANCE.of(objArr);
    }

    public abstract <T> T get(Key<? extends T> key);

    private RenderingContext() {
    }

    /* JADX INFO: compiled from: RenderingContext.kt */
    @Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\u001e\n\u0002\b\u0002\b&\u0018\u0000*\u0006\b\u0000\u0010\u0001 \u00012\u00020\u0002B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J\u001d\u0010\b\u001a\u00028\u00002\u000e\u0010\t\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\nH&¢\u0006\u0002\u0010\u000bR\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/diagnostics/rendering/RenderingContext$Key;", ExifInterface.GPS_DIRECTION_TRUE, "", "name", "", "(Ljava/lang/String;)V", "getName", "()Ljava/lang/String;", "compute", "objectsToRender", "", "(Ljava/util/Collection;)Ljava/lang/Object;", "frontend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static abstract class Key<T> {
        private final String name;

        public abstract T compute(Collection<? extends Object> objectsToRender);

        public Key(String name2) {
            Intrinsics.checkNotNullParameter(name2, "name");
            this.name = name2;
        }

        public final String getName() {
            return this.name;
        }
    }

    /* JADX INFO: compiled from: RenderingContext.kt */
    @Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\u0015\u0012\u000e\u0010\u0002\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00040\u0003¢\u0006\u0002\u0010\u0005J\"\u0010\n\u001a\u0002H\u000b\"\u0004\b\u0000\u0010\u000b2\f\u0010\f\u001a\b\u0012\u0004\u0012\u0002H\u000b0\bH\u0096\u0002¢\u0006\u0002\u0010\rR6\u0010\u0006\u001a*\u0012\b\u0012\u0006\u0012\u0002\b\u00030\b\u0012\u0006\u0012\u0004\u0018\u00010\u00040\u0007j\u0014\u0012\b\u0012\u0006\u0012\u0002\b\u00030\b\u0012\u0006\u0012\u0004\u0018\u00010\u0004`\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\u0002\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00040\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/diagnostics/rendering/RenderingContext$Impl;", "Lorg/jetbrains/kotlin/diagnostics/rendering/RenderingContext;", "objectsToRender", "", "", "(Ljava/util/Collection;)V", "data", "Ljava/util/LinkedHashMap;", "Lorg/jetbrains/kotlin/diagnostics/rendering/RenderingContext$Key;", "Lkotlin/collections/LinkedHashMap;", "get", ExifInterface.GPS_DIRECTION_TRUE, ASTExpr.DEFAULT_MAP_KEY_NAME, "(Lorg/jetbrains/kotlin/diagnostics/rendering/RenderingContext$Key;)Ljava/lang/Object;", "frontend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Impl extends RenderingContext {
        private final LinkedHashMap<Key<?>, Object> data;
        private final Collection<Object> objectsToRender;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public Impl(Collection<? extends Object> objectsToRender) {
            super(null);
            Intrinsics.checkNotNullParameter(objectsToRender, "objectsToRender");
            this.objectsToRender = objectsToRender;
            this.data = new LinkedHashMap<>();
        }

        @Override // org.jetbrains.kotlin.diagnostics.rendering.RenderingContext
        public <T> T get(Key<? extends T> key) {
            Intrinsics.checkNotNullParameter(key, "key");
            T t = (T) this.data.get(key);
            if (t == null) {
                t = null;
            }
            if (t != null) {
                return t;
            }
            T tCompute = key.compute(this.objectsToRender);
            this.data.put(key, tCompute);
            return tCompute;
        }
    }

    /* JADX INFO: compiled from: RenderingContext.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\"\u0010\u0003\u001a\u0002H\u0004\"\u0004\b\u0000\u0010\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u0002H\u00040\u0006H\u0096\u0002¢\u0006\u0002\u0010\u0007¨\u0006\b"}, d2 = {"Lorg/jetbrains/kotlin/diagnostics/rendering/RenderingContext$Empty;", "Lorg/jetbrains/kotlin/diagnostics/rendering/RenderingContext;", "()V", "get", ExifInterface.GPS_DIRECTION_TRUE, ASTExpr.DEFAULT_MAP_KEY_NAME, "Lorg/jetbrains/kotlin/diagnostics/rendering/RenderingContext$Key;", "(Lorg/jetbrains/kotlin/diagnostics/rendering/RenderingContext$Key;)Ljava/lang/Object;", "frontend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Empty extends RenderingContext {
        public static final Empty INSTANCE = new Empty();

        private Empty() {
            super(null);
        }

        @Override // org.jetbrains.kotlin.diagnostics.rendering.RenderingContext
        public <T> T get(Key<? extends T> key) {
            Intrinsics.checkNotNullParameter(key, "key");
            return key.compute(CollectionsKt.emptyList());
        }
    }

    /* JADX INFO: compiled from: RenderingContext.kt */
    @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J%\u0010\u0003\u001a\u00020\u00042\u0016\u0010\u0005\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010\u00010\u0006\"\u0004\u0018\u00010\u0001H\u0007¢\u0006\u0002\u0010\u0007¨\u0006\b"}, d2 = {"Lorg/jetbrains/kotlin/diagnostics/rendering/RenderingContext$Companion;", "", "()V", "of", "Lorg/jetbrains/kotlin/diagnostics/rendering/RenderingContext;", "objectsToRender", "", "([Ljava/lang/Object;)Lorg/jetbrains/kotlin/diagnostics/rendering/RenderingContext;", "frontend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        @JvmStatic
        public final RenderingContext of(Object... objectsToRender) {
            Intrinsics.checkNotNullParameter(objectsToRender, "objectsToRender");
            return new Impl(ArraysKt.toList(objectsToRender));
        }
    }
}

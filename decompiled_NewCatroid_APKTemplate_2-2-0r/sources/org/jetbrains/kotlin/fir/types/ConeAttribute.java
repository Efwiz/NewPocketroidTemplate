package org.jetbrains.kotlin.fir.types;

import androidx.exifinterface.media.ExifInterface;
import com.google.android.gms.analytics.ecommerce.ProductAction;
import kotlin.Metadata;
import kotlin.reflect.KClass;
import org.antlr.stringtemplate.language.ASTExpr;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.jetbrains.kotlin.fir.types.ConeAttribute;
import org.jetbrains.kotlin.types.model.AnnotationMarker;

/* JADX INFO: compiled from: ConeAttributes.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\b&\u0018\u0000*\u0010\b\u0000\u0010\u0001 \u0001*\b\u0012\u0004\u0012\u0002H\u00010\u00002\u00020\u0002B\u0005¢\u0006\u0002\u0010\u0003J\u0019\u0010\b\u001a\u0004\u0018\u00018\u00002\b\u0010\t\u001a\u0004\u0018\u00018\u0000H&¢\u0006\u0002\u0010\nJ\u0019\u0010\u000b\u001a\u0004\u0018\u00018\u00002\b\u0010\t\u001a\u0004\u0018\u00018\u0000H&¢\u0006\u0002\u0010\nJ\u0017\u0010\f\u001a\u00020\r2\b\u0010\t\u001a\u0004\u0018\u00018\u0000H&¢\u0006\u0002\u0010\u000eJ\b\u0010\u000f\u001a\u00020\u0010H&J\u0019\u0010\u0011\u001a\u0004\u0018\u00018\u00002\b\u0010\t\u001a\u0004\u0018\u00018\u0000H&¢\u0006\u0002\u0010\nR\u001a\u0010\u0004\u001a\n\u0012\u0006\b\u0001\u0012\u00028\u00000\u0005X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/fir/types/ConeAttribute;", ExifInterface.GPS_DIRECTION_TRUE, "Lorg/jetbrains/kotlin/types/model/AnnotationMarker;", "()V", ASTExpr.DEFAULT_MAP_KEY_NAME, "Lkotlin/reflect/KClass;", "getKey", "()Lkotlin/reflect/KClass;", ProductAction.ACTION_ADD, "other", "(Lorg/jetbrains/kotlin/fir/types/ConeAttribute;)Lorg/jetbrains/kotlin/fir/types/ConeAttribute;", "intersect", "isSubtypeOf", "", "(Lorg/jetbrains/kotlin/fir/types/ConeAttribute;)Z", "toString", "", SchemaSymbols.ATTVAL_UNION, "cones"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class ConeAttribute<T extends ConeAttribute<? extends T>> implements AnnotationMarker {
    public abstract T add(T other);

    public abstract KClass<? extends T> getKey();

    public abstract T intersect(T other);

    public abstract boolean isSubtypeOf(T other);

    public abstract String toString();

    public abstract T union(T other);
}

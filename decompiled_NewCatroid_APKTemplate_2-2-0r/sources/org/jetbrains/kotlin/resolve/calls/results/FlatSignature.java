package org.jetbrains.kotlin.resolve.calls.results;

import androidx.exifinterface.media.ExifInterface;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.bytebuddy.implementation.auxiliary.TypeProxy;
import org.jetbrains.kotlin.types.model.KotlinTypeMarker;
import org.jetbrains.kotlin.types.model.TypeParameterMarker;

/* JADX INFO: compiled from: FlatSignature.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0011\u0018\u0000 #*\u0006\b\u0000\u0010\u0001 \u00012\u00020\u0002:\u0001#B]\b\u0016\u0012\u0006\u0010\u0003\u001a\u00028\u0000\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u0012\u000e\u0010\u0007\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\t0\b\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\u0006\u0010\f\u001a\u00020\r\u0012\u0006\u0010\u000e\u001a\u00020\u000b\u0012\u0006\u0010\u000f\u001a\u00020\r\u0012\u0006\u0010\u0010\u001a\u00020\u000b\u0012\u0006\u0010\u0011\u001a\u00020\u000b¢\u0006\u0002\u0010\u0012B[\u0012\u0006\u0010\u0003\u001a\u00028\u0000\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\u0006\u0010\f\u001a\u00020\r\u0012\u0006\u0010\u000e\u001a\u00020\u000b\u0012\u0006\u0010\u000f\u001a\u00020\r\u0012\u0006\u0010\u0010\u001a\u00020\u000b\u0012\u0006\u0010\u0011\u001a\u00020\u000b\u0012\u000e\u0010\u0007\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00130\b¢\u0006\u0002\u0010\u0014R\u0011\u0010\f\u001a\u00020\r¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u0011\u0010\n\u001a\u00020\u000b¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u0011\u0010\u000e\u001a\u00020\u000b¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u0018R\u0011\u0010\u0010\u001a\u00020\u000b¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0018R\u0011\u0010\u001a\u001a\u00020\u000b¢\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u0018R\u0011\u0010\u0011\u001a\u00020\u000b¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0018R\u0011\u0010\u000f\u001a\u00020\r¢\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u0016R\u0013\u0010\u0003\u001a\u00028\u0000¢\u0006\n\n\u0002\u0010\u001e\u001a\u0004\b\u001c\u0010\u001dR\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010 R\u0019\u0010\u0007\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00130\b¢\u0006\b\n\u0000\u001a\u0004\b!\u0010\"¨\u0006$"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/results/FlatSignature;", ExifInterface.GPS_DIRECTION_TRUE, "", "origin", "typeParameters", "", "Lorg/jetbrains/kotlin/types/model/TypeParameterMarker;", "valueParameterTypes", "", "Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;", "hasExtensionReceiver", "", "contextReceiverCount", "", "hasVarargs", "numDefaults", "isExpect", "isSyntheticMember", "(Ljava/lang/Object;Ljava/util/Collection;Ljava/util/List;ZIZIZZ)V", "Lorg/jetbrains/kotlin/resolve/calls/results/TypeWithConversion;", "(Ljava/lang/Object;Ljava/util/Collection;ZIZIZZLjava/util/List;)V", "getContextReceiverCount", "()I", "getHasExtensionReceiver", "()Z", "getHasVarargs", "isGeneric", "getNumDefaults", "getOrigin", "()Ljava/lang/Object;", TypeProxy.SilentConstruction.Appender.JAVA_LANG_OBJECT_DESCRIPTOR, "getTypeParameters", "()Ljava/util/Collection;", "getValueParameterTypes", "()Ljava/util/List;", "Companion", "resolution.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FlatSignature<T> {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final int contextReceiverCount;
    private final boolean hasExtensionReceiver;
    private final boolean hasVarargs;
    private final boolean isExpect;
    private final boolean isGeneric;
    private final boolean isSyntheticMember;
    private final int numDefaults;
    private final T origin;
    private final Collection<TypeParameterMarker> typeParameters;
    private final List<TypeWithConversion> valueParameterTypes;

    /* JADX WARN: Multi-variable type inference failed */
    public FlatSignature(T t, Collection<? extends TypeParameterMarker> typeParameters, boolean z, int i, boolean z2, int i2, boolean z3, boolean z4, List<TypeWithConversion> valueParameterTypes) {
        Intrinsics.checkNotNullParameter(typeParameters, "typeParameters");
        Intrinsics.checkNotNullParameter(valueParameterTypes, "valueParameterTypes");
        this.origin = t;
        this.typeParameters = typeParameters;
        this.hasExtensionReceiver = z;
        this.contextReceiverCount = i;
        this.hasVarargs = z2;
        this.numDefaults = i2;
        this.isExpect = z3;
        this.isSyntheticMember = z4;
        this.valueParameterTypes = valueParameterTypes;
        this.isGeneric = !typeParameters.isEmpty();
    }

    public final T getOrigin() {
        return this.origin;
    }

    public final Collection<TypeParameterMarker> getTypeParameters() {
        return this.typeParameters;
    }

    public final boolean getHasExtensionReceiver() {
        return this.hasExtensionReceiver;
    }

    public final int getContextReceiverCount() {
        return this.contextReceiverCount;
    }

    public final boolean getHasVarargs() {
        return this.hasVarargs;
    }

    public final int getNumDefaults() {
        return this.numDefaults;
    }

    /* JADX INFO: renamed from: isExpect, reason: from getter */
    public final boolean getIsExpect() {
        return this.isExpect;
    }

    /* JADX INFO: renamed from: isSyntheticMember, reason: from getter */
    public final boolean getIsSyntheticMember() {
        return this.isSyntheticMember;
    }

    public final List<TypeWithConversion> getValueParameterTypes() {
        return this.valueParameterTypes;
    }

    /* JADX INFO: renamed from: isGeneric, reason: from getter */
    public final boolean getIsGeneric() {
        return this.isGeneric;
    }

    public FlatSignature(T t, Collection<? extends TypeParameterMarker> typeParameters, List<? extends KotlinTypeMarker> valueParameterTypes, boolean z, int i, boolean z2, int i2, boolean z3, boolean z4) {
        Intrinsics.checkNotNullParameter(typeParameters, "typeParameters");
        Intrinsics.checkNotNullParameter(valueParameterTypes, "valueParameterTypes");
        List<? extends KotlinTypeMarker> list = valueParameterTypes;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(new TypeWithConversion((KotlinTypeMarker) it.next(), null, 2, null));
        }
        this(t, typeParameters, z, i, z2, i2, z3, z4, arrayList);
    }

    /* JADX INFO: compiled from: FlatSignature.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/results/FlatSignature$Companion;", "", "()V", "resolution.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }
}

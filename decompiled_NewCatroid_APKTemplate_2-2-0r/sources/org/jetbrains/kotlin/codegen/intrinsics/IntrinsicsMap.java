package org.jetbrains.kotlin.codegen.intrinsics;

import com.reandroid.dex.sections.Marker;
import java.util.HashMap;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.ReceiverParameterDescriptor;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.FqNameUnsafe;
import org.jetbrains.kotlin.resolve.DescriptorUtils;

/* JADX INFO: compiled from: IntrinsicsMap.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\b\u0000\u0018\u00002\u00020\u0001:\u0001\u0019B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0007\u001a\u0004\u0018\u00010\u00062\u0006\u0010\b\u001a\u00020\tJ\u0010\u0010\n\u001a\u00020\u00052\u0006\u0010\b\u001a\u00020\tH\u0002J\u0012\u0010\u000b\u001a\u0004\u0018\u00010\f2\u0006\u0010\b\u001a\u00020\tH\u0002J0\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\f2\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0006J\u0010\u0010\u0017\u001a\u00020\u00152\u0006\u0010\u0018\u001a\u00020\tH\u0002R\u001a\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001a"}, d2 = {"Lorg/jetbrains/kotlin/codegen/intrinsics/IntrinsicsMap;", "", "()V", "intrinsicsMap", "Ljava/util/HashMap;", "Lorg/jetbrains/kotlin/codegen/intrinsics/IntrinsicsMap$Key;", "Lorg/jetbrains/kotlin/codegen/intrinsics/IntrinsicMethod;", "getIntrinsic", "descriptor", "Lorg/jetbrains/kotlin/descriptors/CallableMemberDescriptor;", "getKey", "getReceiverParameterFqName", "Lorg/jetbrains/kotlin/name/FqNameUnsafe;", "registerIntrinsic", "", "owner", "Lorg/jetbrains/kotlin/name/FqName;", "receiverParameter", "name", "", "valueParameterCount", "", "impl", "valueParameterCountForKey", "member", "Key", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class IntrinsicsMap {
    private final HashMap<Key, IntrinsicMethod> intrinsicsMap = new HashMap<>();

    /* JADX INFO: compiled from: IntrinsicsMap.kt */
    @Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0082\b\u0018\u00002\u00020\u0001B'\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tJ\t\u0010\n\u001a\u00020\u0003HÂ\u0003J\u000b\u0010\u000b\u001a\u0004\u0018\u00010\u0003HÂ\u0003J\t\u0010\f\u001a\u00020\u0006HÂ\u0003J\t\u0010\r\u001a\u00020\bHÂ\u0003J3\u0010\u000e\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00062\b\b\u0002\u0010\u0007\u001a\u00020\bHÆ\u0001J\u0013\u0010\u000f\u001a\u00020\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0012\u001a\u00020\bHÖ\u0001J\t\u0010\u0013\u001a\u00020\u0006HÖ\u0001R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0004\u001a\u0004\u0018\u00010\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0014"}, d2 = {"Lorg/jetbrains/kotlin/codegen/intrinsics/IntrinsicsMap$Key;", "", "owner", "Lorg/jetbrains/kotlin/name/FqNameUnsafe;", "receiverParameter", "name", "", "valueParameterCount", "", "(Lorg/jetbrains/kotlin/name/FqNameUnsafe;Lorg/jetbrains/kotlin/name/FqNameUnsafe;Ljava/lang/String;I)V", "component1", "component2", "component3", "component4", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "toString", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final /* data */ class Key {
        private final String name;
        private final FqNameUnsafe owner;
        private final FqNameUnsafe receiverParameter;
        private final int valueParameterCount;

        public static /* synthetic */ Key copy$default(Key key, FqNameUnsafe fqNameUnsafe, FqNameUnsafe fqNameUnsafe2, String str, int i, int i2, Object obj) {
            if ((i2 & 1) != 0) {
                fqNameUnsafe = key.owner;
            }
            if ((i2 & 2) != 0) {
                fqNameUnsafe2 = key.receiverParameter;
            }
            if ((i2 & 4) != 0) {
                str = key.name;
            }
            if ((i2 & 8) != 0) {
                i = key.valueParameterCount;
            }
            return key.copy(fqNameUnsafe, fqNameUnsafe2, str, i);
        }

        public final Key copy(FqNameUnsafe owner, FqNameUnsafe receiverParameter, String name2, int valueParameterCount) {
            Intrinsics.checkNotNullParameter(owner, "owner");
            Intrinsics.checkNotNullParameter(name2, "name");
            return new Key(owner, receiverParameter, name2, valueParameterCount);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof Key)) {
                return false;
            }
            Key key = (Key) other;
            return Intrinsics.areEqual(this.owner, key.owner) && Intrinsics.areEqual(this.receiverParameter, key.receiverParameter) && Intrinsics.areEqual(this.name, key.name) && this.valueParameterCount == key.valueParameterCount;
        }

        public int hashCode() {
            int iHashCode = this.owner.hashCode() * 31;
            FqNameUnsafe fqNameUnsafe = this.receiverParameter;
            return ((((iHashCode + (fqNameUnsafe == null ? 0 : fqNameUnsafe.hashCode())) * 31) + this.name.hashCode()) * 31) + Integer.hashCode(this.valueParameterCount);
        }

        public String toString() {
            return "Key(owner=" + this.owner + ", receiverParameter=" + this.receiverParameter + ", name=" + this.name + ", valueParameterCount=" + this.valueParameterCount + ')';
        }

        public Key(FqNameUnsafe owner, FqNameUnsafe fqNameUnsafe, String name2, int i) {
            Intrinsics.checkNotNullParameter(owner, "owner");
            Intrinsics.checkNotNullParameter(name2, "name");
            this.owner = owner;
            this.receiverParameter = fqNameUnsafe;
            this.name = name2;
            this.valueParameterCount = i;
        }
    }

    private final int valueParameterCountForKey(CallableMemberDescriptor member) {
        if (member instanceof PropertyDescriptor) {
            return -1;
        }
        return member.getValueParameters().size();
    }

    public final void registerIntrinsic(FqName owner, FqNameUnsafe receiverParameter, String name2, int valueParameterCount, IntrinsicMethod impl) {
        Intrinsics.checkNotNullParameter(owner, "owner");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(impl, "impl");
        HashMap<Key, IntrinsicMethod> map = this.intrinsicsMap;
        FqNameUnsafe unsafe = owner.toUnsafe();
        Intrinsics.checkNotNullExpressionValue(unsafe, "toUnsafe(...)");
        map.put(new Key(unsafe, receiverParameter, name2, valueParameterCount), impl);
    }

    public final IntrinsicMethod getIntrinsic(CallableMemberDescriptor descriptor) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        IntrinsicMethod intrinsicMethod = this.intrinsicsMap.get(getKey(descriptor));
        if (intrinsicMethod == null || !intrinsicMethod.isApplicableToOverload(descriptor.getOriginal())) {
            return null;
        }
        return intrinsicMethod;
    }

    private final Key getKey(CallableMemberDescriptor descriptor) {
        FqNameUnsafe fqName = DescriptorUtils.getFqName(descriptor.getContainingDeclaration());
        Intrinsics.checkNotNullExpressionValue(fqName, "getFqName(...)");
        FqNameUnsafe receiverParameterFqName = getReceiverParameterFqName(descriptor);
        String strAsString = descriptor.getShortName().asString();
        Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
        return new Key(fqName, receiverParameterFqName, strAsString, valueParameterCountForKey(descriptor));
    }

    private final FqNameUnsafe getReceiverParameterFqName(CallableMemberDescriptor descriptor) {
        ClassifierDescriptor this$0;
        ReceiverParameterDescriptor extensionReceiverParameter = descriptor.getExtensionReceiverParameter();
        if (extensionReceiverParameter == null || (this$0 = extensionReceiverParameter.getType().getConstructor().getThis$0()) == null) {
            return null;
        }
        if (this$0 instanceof TypeParameterDescriptor) {
            return IntrinsicMethods.RECEIVER_PARAMETER_FQ_NAME;
        }
        return DescriptorUtils.getFqName(this$0);
    }
}

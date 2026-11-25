package org.jetbrains.kotlin.load.kotlin;

import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.metadata.deserialization.NameResolver;
import org.jetbrains.kotlin.metadata.jvm.JvmProtoBuf;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmMemberSignature;

/* JADX INFO: compiled from: MemberSignature.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\b\u0086\b\u0018\u0000 \u000f2\u00020\u0001:\u0001\u000fB\u000f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\t\u0010\u0007\u001a\u00020\u0003HÆ\u0003J\u0013\u0010\b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\f\u001a\u00020\rHÖ\u0001J\t\u0010\u000e\u001a\u00020\u0003HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/load/kotlin/MemberSignature;", "", "signature", "", "(Ljava/lang/String;)V", "getSignature", "()Ljava/lang/String;", "component1", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "Companion", "deserialization.common.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class MemberSignature {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final String signature;

    public /* synthetic */ MemberSignature(String str, DefaultConstructorMarker defaultConstructorMarker) {
        this(str);
    }

    public static /* synthetic */ MemberSignature copy$default(MemberSignature memberSignature, String str, int i, Object obj) {
        if ((i & 1) != 0) {
            str = memberSignature.signature;
        }
        return memberSignature.copy(str);
    }

    @JvmStatic
    public static final MemberSignature fromFieldNameAndDesc(String str, String str2) {
        return INSTANCE.fromFieldNameAndDesc(str, str2);
    }

    @JvmStatic
    public static final MemberSignature fromJvmMemberSignature(JvmMemberSignature jvmMemberSignature) {
        return INSTANCE.fromJvmMemberSignature(jvmMemberSignature);
    }

    @JvmStatic
    public static final MemberSignature fromMethod(NameResolver nameResolver, JvmProtoBuf.JvmMethodSignature jvmMethodSignature) {
        return INSTANCE.fromMethod(nameResolver, jvmMethodSignature);
    }

    @JvmStatic
    public static final MemberSignature fromMethodNameAndDesc(String str, String str2) {
        return INSTANCE.fromMethodNameAndDesc(str, str2);
    }

    @JvmStatic
    public static final MemberSignature fromMethodSignatureAndParameterIndex(MemberSignature memberSignature, int i) {
        return INSTANCE.fromMethodSignatureAndParameterIndex(memberSignature, i);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final String getSignature() {
        return this.signature;
    }

    public final MemberSignature copy(String signature) {
        Intrinsics.checkNotNullParameter(signature, "signature");
        return new MemberSignature(signature);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        return (other instanceof MemberSignature) && Intrinsics.areEqual(this.signature, ((MemberSignature) other).signature);
    }

    public int hashCode() {
        return this.signature.hashCode();
    }

    public String toString() {
        return "MemberSignature(signature=" + this.signature + ')';
    }

    private MemberSignature(String str) {
        this.signature = str;
    }

    /* JADX INFO: compiled from: MemberSignature.kt */
    @Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0018\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u0006H\u0007J\u0010\u0010\b\u001a\u00020\u00042\u0006\u0010\t\u001a\u00020\nH\u0007J\u0018\u0010\u000b\u001a\u00020\u00042\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\t\u001a\u00020\u000eH\u0007J\u0018\u0010\u000f\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u0006H\u0007J\u0018\u0010\u0010\u001a\u00020\u00042\u0006\u0010\t\u001a\u00020\u00042\u0006\u0010\u0011\u001a\u00020\u0012H\u0007¨\u0006\u0013"}, d2 = {"Lorg/jetbrains/kotlin/load/kotlin/MemberSignature$Companion;", "", "()V", "fromFieldNameAndDesc", "Lorg/jetbrains/kotlin/load/kotlin/MemberSignature;", "name", "", "desc", "fromJvmMemberSignature", "signature", "Lorg/jetbrains/kotlin/metadata/jvm/deserialization/JvmMemberSignature;", "fromMethod", "nameResolver", "Lorg/jetbrains/kotlin/metadata/deserialization/NameResolver;", "Lorg/jetbrains/kotlin/metadata/jvm/JvmProtoBuf$JvmMethodSignature;", "fromMethodNameAndDesc", "fromMethodSignatureAndParameterIndex", "index", "", "deserialization.common.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        @JvmStatic
        public final MemberSignature fromMethod(NameResolver nameResolver, JvmProtoBuf.JvmMethodSignature signature) {
            Intrinsics.checkNotNullParameter(nameResolver, "nameResolver");
            Intrinsics.checkNotNullParameter(signature, "signature");
            return fromMethodNameAndDesc(nameResolver.getString(signature.getName()), nameResolver.getString(signature.getDesc()));
        }

        @JvmStatic
        public final MemberSignature fromMethodNameAndDesc(String name2, String desc) {
            Intrinsics.checkNotNullParameter(name2, "name");
            Intrinsics.checkNotNullParameter(desc, "desc");
            return new MemberSignature(name2 + desc, null);
        }

        @JvmStatic
        public final MemberSignature fromFieldNameAndDesc(String name2, String desc) {
            Intrinsics.checkNotNullParameter(name2, "name");
            Intrinsics.checkNotNullParameter(desc, "desc");
            return new MemberSignature(name2 + '#' + desc, null);
        }

        @JvmStatic
        public final MemberSignature fromJvmMemberSignature(JvmMemberSignature signature) {
            Intrinsics.checkNotNullParameter(signature, "signature");
            if (signature instanceof JvmMemberSignature.Method) {
                return fromMethodNameAndDesc(signature.getName(), signature.getDesc());
            }
            if (signature instanceof JvmMemberSignature.Field) {
                return fromFieldNameAndDesc(signature.getName(), signature.getDesc());
            }
            throw new NoWhenBranchMatchedException();
        }

        @JvmStatic
        public final MemberSignature fromMethodSignatureAndParameterIndex(MemberSignature signature, int index) {
            Intrinsics.checkNotNullParameter(signature, "signature");
            return new MemberSignature(signature.getSignature() + '@' + index, null);
        }
    }

    public final String getSignature() {
        return this.signature;
    }
}

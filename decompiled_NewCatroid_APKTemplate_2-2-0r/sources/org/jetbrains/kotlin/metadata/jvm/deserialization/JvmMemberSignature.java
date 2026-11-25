package org.jetbrains.kotlin.metadata.jvm.deserialization;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: JvmMemberSignature.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001:\u0002\u000b\fB\u0007\b\u0004¢\u0006\u0002\u0010\u0002J\b\u0010\t\u001a\u00020\u0004H&J\u0006\u0010\n\u001a\u00020\u0004R\u0012\u0010\u0003\u001a\u00020\u0004X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006R\u0012\u0010\u0007\u001a\u00020\u0004X¦\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\u0006\u0082\u0001\u0002\r\u000e¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/metadata/jvm/deserialization/JvmMemberSignature;", "", "()V", "desc", "", "getDesc", "()Ljava/lang/String;", "name", "getName", "asString", "toString", "Field", "Method", "Lorg/jetbrains/kotlin/metadata/jvm/deserialization/JvmMemberSignature$Field;", "Lorg/jetbrains/kotlin/metadata/jvm/deserialization/JvmMemberSignature$Method;", "metadata.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class JvmMemberSignature {
    public /* synthetic */ JvmMemberSignature(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    public abstract String asString();

    public abstract String getDesc();

    public abstract String getName();

    private JvmMemberSignature() {
    }

    /* JADX INFO: compiled from: JvmMemberSignature.kt */
    @Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\n\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003¢\u0006\u0002\u0010\u0005J\b\u0010\t\u001a\u00020\u0003H\u0016J\t\u0010\n\u001a\u00020\u0003HÆ\u0003J\t\u0010\u000b\u001a\u00020\u0003HÆ\u0003J\u001d\u0010\f\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\r\u001a\u00020\u000e2\b\u0010\u000f\u001a\u0004\u0018\u00010\u0010HÖ\u0003J\t\u0010\u0011\u001a\u00020\u0012HÖ\u0001R\u0014\u0010\u0004\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0007¨\u0006\u0013"}, d2 = {"Lorg/jetbrains/kotlin/metadata/jvm/deserialization/JvmMemberSignature$Method;", "Lorg/jetbrains/kotlin/metadata/jvm/deserialization/JvmMemberSignature;", "name", "", "desc", "(Ljava/lang/String;Ljava/lang/String;)V", "getDesc", "()Ljava/lang/String;", "getName", "asString", "component1", "component2", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "", "metadata.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final /* data */ class Method extends JvmMemberSignature {
        private final String desc;
        private final String name;

        public static /* synthetic */ Method copy$default(Method method, String str, String str2, int i, Object obj) {
            if ((i & 1) != 0) {
                str = method.name;
            }
            if ((i & 2) != 0) {
                str2 = method.desc;
            }
            return method.copy(str, str2);
        }

        /* JADX INFO: renamed from: component1, reason: from getter */
        public final String getName() {
            return this.name;
        }

        /* JADX INFO: renamed from: component2, reason: from getter */
        public final String getDesc() {
            return this.desc;
        }

        public final Method copy(String name2, String desc) {
            Intrinsics.checkNotNullParameter(name2, "name");
            Intrinsics.checkNotNullParameter(desc, "desc");
            return new Method(name2, desc);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof Method)) {
                return false;
            }
            Method method = (Method) other;
            return Intrinsics.areEqual(this.name, method.name) && Intrinsics.areEqual(this.desc, method.desc);
        }

        public int hashCode() {
            return (this.name.hashCode() * 31) + this.desc.hashCode();
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public Method(String name2, String desc) {
            super(null);
            Intrinsics.checkNotNullParameter(name2, "name");
            Intrinsics.checkNotNullParameter(desc, "desc");
            this.name = name2;
            this.desc = desc;
        }

        @Override // org.jetbrains.kotlin.metadata.jvm.deserialization.JvmMemberSignature
        public String getDesc() {
            return this.desc;
        }

        @Override // org.jetbrains.kotlin.metadata.jvm.deserialization.JvmMemberSignature
        public String getName() {
            return this.name;
        }

        @Override // org.jetbrains.kotlin.metadata.jvm.deserialization.JvmMemberSignature
        public String asString() {
            return getName() + getDesc();
        }
    }

    /* JADX INFO: compiled from: JvmMemberSignature.kt */
    @Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\n\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003¢\u0006\u0002\u0010\u0005J\b\u0010\t\u001a\u00020\u0003H\u0016J\t\u0010\n\u001a\u00020\u0003HÆ\u0003J\t\u0010\u000b\u001a\u00020\u0003HÆ\u0003J\u001d\u0010\f\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\r\u001a\u00020\u000e2\b\u0010\u000f\u001a\u0004\u0018\u00010\u0010HÖ\u0003J\t\u0010\u0011\u001a\u00020\u0012HÖ\u0001R\u0014\u0010\u0004\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0007¨\u0006\u0013"}, d2 = {"Lorg/jetbrains/kotlin/metadata/jvm/deserialization/JvmMemberSignature$Field;", "Lorg/jetbrains/kotlin/metadata/jvm/deserialization/JvmMemberSignature;", "name", "", "desc", "(Ljava/lang/String;Ljava/lang/String;)V", "getDesc", "()Ljava/lang/String;", "getName", "asString", "component1", "component2", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "", "metadata.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final /* data */ class Field extends JvmMemberSignature {
        private final String desc;
        private final String name;

        public static /* synthetic */ Field copy$default(Field field, String str, String str2, int i, Object obj) {
            if ((i & 1) != 0) {
                str = field.name;
            }
            if ((i & 2) != 0) {
                str2 = field.desc;
            }
            return field.copy(str, str2);
        }

        /* JADX INFO: renamed from: component1, reason: from getter */
        public final String getName() {
            return this.name;
        }

        /* JADX INFO: renamed from: component2, reason: from getter */
        public final String getDesc() {
            return this.desc;
        }

        public final Field copy(String name2, String desc) {
            Intrinsics.checkNotNullParameter(name2, "name");
            Intrinsics.checkNotNullParameter(desc, "desc");
            return new Field(name2, desc);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof Field)) {
                return false;
            }
            Field field = (Field) other;
            return Intrinsics.areEqual(this.name, field.name) && Intrinsics.areEqual(this.desc, field.desc);
        }

        public int hashCode() {
            return (this.name.hashCode() * 31) + this.desc.hashCode();
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public Field(String name2, String desc) {
            super(null);
            Intrinsics.checkNotNullParameter(name2, "name");
            Intrinsics.checkNotNullParameter(desc, "desc");
            this.name = name2;
            this.desc = desc;
        }

        @Override // org.jetbrains.kotlin.metadata.jvm.deserialization.JvmMemberSignature
        public String getDesc() {
            return this.desc;
        }

        @Override // org.jetbrains.kotlin.metadata.jvm.deserialization.JvmMemberSignature
        public String getName() {
            return this.name;
        }

        @Override // org.jetbrains.kotlin.metadata.jvm.deserialization.JvmMemberSignature
        public String asString() {
            return getName() + ':' + getDesc();
        }
    }

    public final String toString() {
        return asString();
    }
}

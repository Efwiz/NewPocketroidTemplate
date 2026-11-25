package org.jetbrains.kotlin.constant;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.resolve.constants.ClassLiteralValue;
import org.jetbrains.kotlin.types.model.KotlinTypeMarker;

/* JADX INFO: compiled from: ConstantValue.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0001\u0013B\u000f\b\u0016\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005B\u0017\b\u0016\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\nB\u000f\b\u0002\u0012\u0006\u0010\u0003\u001a\u00020\u0002¢\u0006\u0002\u0010\u000bJ5\u0010\f\u001a\u0002H\r\"\u0004\b\u0000\u0010\r\"\u0004\b\u0001\u0010\u000e2\u0012\u0010\u000f\u001a\u000e\u0012\u0004\u0012\u0002H\r\u0012\u0004\u0012\u0002H\u000e0\u00102\u0006\u0010\u0011\u001a\u0002H\u000eH\u0016¢\u0006\u0002\u0010\u0012¨\u0006\u0014"}, d2 = {"Lorg/jetbrains/kotlin/constant/KClassValue;", "Lorg/jetbrains/kotlin/constant/ConstantValue;", "Lorg/jetbrains/kotlin/constant/KClassValue$Value;", "value", "Lorg/jetbrains/kotlin/resolve/constants/ClassLiteralValue;", "(Lorg/jetbrains/kotlin/resolve/constants/ClassLiteralValue;)V", "classId", "Lorg/jetbrains/kotlin/name/ClassId;", "arrayDimensions", "", "(Lorg/jetbrains/kotlin/name/ClassId;I)V", "(Lorg/jetbrains/kotlin/constant/KClassValue$Value;)V", "accept", "R", "D", "visitor", "Lorg/jetbrains/kotlin/constant/AnnotationArgumentVisitor;", "data", "(Lorg/jetbrains/kotlin/constant/AnnotationArgumentVisitor;Ljava/lang/Object;)Ljava/lang/Object;", "Value", "compiler.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KClassValue extends ConstantValue<Value> {

    /* JADX INFO: compiled from: ConstantValue.kt */
    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001:\u0002\u0003\u0004B\u0007\b\u0004¢\u0006\u0002\u0010\u0002\u0082\u0001\u0002\u0005\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/constant/KClassValue$Value;", "", "()V", "LocalClass", "NormalClass", "Lorg/jetbrains/kotlin/constant/KClassValue$Value$LocalClass;", "Lorg/jetbrains/kotlin/constant/KClassValue$Value$NormalClass;", "compiler.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static abstract class Value {
        public /* synthetic */ Value(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        /* JADX INFO: compiled from: ConstantValue.kt */
        @Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\t\u0010\u000f\u001a\u00020\u0003HÆ\u0003J\u0013\u0010\u0010\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\u0011\u001a\u00020\u00122\b\u0010\u0013\u001a\u0004\u0018\u00010\u0014HÖ\u0003J\t\u0010\u0015\u001a\u00020\u0006HÖ\u0001J\t\u0010\u0016\u001a\u00020\u0017HÖ\u0001R\u0011\u0010\u0005\u001a\u00020\u00068F¢\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\t\u001a\u00020\n8F¢\u0006\u0006\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e¨\u0006\u0018"}, d2 = {"Lorg/jetbrains/kotlin/constant/KClassValue$Value$NormalClass;", "Lorg/jetbrains/kotlin/constant/KClassValue$Value;", "value", "Lorg/jetbrains/kotlin/resolve/constants/ClassLiteralValue;", "(Lorg/jetbrains/kotlin/resolve/constants/ClassLiteralValue;)V", "arrayDimensions", "", "getArrayDimensions", "()I", "classId", "Lorg/jetbrains/kotlin/name/ClassId;", "getClassId", "()Lorg/jetbrains/kotlin/name/ClassId;", "getValue", "()Lorg/jetbrains/kotlin/resolve/constants/ClassLiteralValue;", "component1", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "toString", "", "compiler.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final /* data */ class NormalClass extends Value {
            private final ClassLiteralValue value;

            public static /* synthetic */ NormalClass copy$default(NormalClass normalClass, ClassLiteralValue classLiteralValue, int i, Object obj) {
                if ((i & 1) != 0) {
                    classLiteralValue = normalClass.value;
                }
                return normalClass.copy(classLiteralValue);
            }

            /* JADX INFO: renamed from: component1, reason: from getter */
            public final ClassLiteralValue getValue() {
                return this.value;
            }

            public final NormalClass copy(ClassLiteralValue value) {
                Intrinsics.checkNotNullParameter(value, "value");
                return new NormalClass(value);
            }

            public boolean equals(Object other) {
                if (this == other) {
                    return true;
                }
                return (other instanceof NormalClass) && Intrinsics.areEqual(this.value, ((NormalClass) other).value);
            }

            public int hashCode() {
                return this.value.hashCode();
            }

            public String toString() {
                return "NormalClass(value=" + this.value + ')';
            }

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public NormalClass(ClassLiteralValue value) {
                super(null);
                Intrinsics.checkNotNullParameter(value, "value");
                this.value = value;
            }

            public final ClassLiteralValue getValue() {
                return this.value;
            }

            public final ClassId getClassId() {
                return this.value.getClassId();
            }

            public final int getArrayDimensions() {
                return this.value.getArrayNestedness();
            }
        }

        private Value() {
        }

        /* JADX INFO: compiled from: ConstantValue.kt */
        @Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\t\u0010\u0007\u001a\u00020\u0003HÆ\u0003J\u0013\u0010\b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\fHÖ\u0003J\t\u0010\r\u001a\u00020\u000eHÖ\u0001J\t\u0010\u000f\u001a\u00020\u0010HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/constant/KClassValue$Value$LocalClass;", "Lorg/jetbrains/kotlin/constant/KClassValue$Value;", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;", "(Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;)V", "getType", "()Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;", "component1", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "", "toString", "", "compiler.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final /* data */ class LocalClass extends Value {
            private final KotlinTypeMarker type;

            public static /* synthetic */ LocalClass copy$default(LocalClass localClass, KotlinTypeMarker kotlinTypeMarker, int i, Object obj) {
                if ((i & 1) != 0) {
                    kotlinTypeMarker = localClass.type;
                }
                return localClass.copy(kotlinTypeMarker);
            }

            /* JADX INFO: renamed from: component1, reason: from getter */
            public final KotlinTypeMarker getType() {
                return this.type;
            }

            public final LocalClass copy(KotlinTypeMarker type) {
                Intrinsics.checkNotNullParameter(type, "type");
                return new LocalClass(type);
            }

            public boolean equals(Object other) {
                if (this == other) {
                    return true;
                }
                return (other instanceof LocalClass) && Intrinsics.areEqual(this.type, ((LocalClass) other).type);
            }

            public int hashCode() {
                return this.type.hashCode();
            }

            public String toString() {
                return "LocalClass(type=" + this.type + ')';
            }

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public LocalClass(KotlinTypeMarker type) {
                super(null);
                Intrinsics.checkNotNullParameter(type, "type");
                this.type = type;
            }

            public final KotlinTypeMarker getType() {
                return this.type;
            }
        }
    }

    private KClassValue(Value value) {
        super(value, null);
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public KClassValue(ClassLiteralValue value) {
        this(new Value.NormalClass(value));
        Intrinsics.checkNotNullParameter(value, "value");
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public KClassValue(ClassId classId, int i) {
        this(new ClassLiteralValue(classId, i));
        Intrinsics.checkNotNullParameter(classId, "classId");
    }

    @Override // org.jetbrains.kotlin.constant.ConstantValue
    public <R, D> R accept(AnnotationArgumentVisitor<R, D> visitor, D data) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        return visitor.visitKClassValue(this, data);
    }
}

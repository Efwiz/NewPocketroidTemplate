package org.jetbrains.kotlin.util;

import com.google.android.gms.cast.MediaTrack;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.SimpleType;

/* JADX INFO: compiled from: modifierChecks.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001:\u0003\u0014\u0015\u0016B(\b\u0004\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0017\u0010\u0004\u001a\u0013\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00070\u0005¢\u0006\u0002\b\b¢\u0006\u0002\u0010\tJ\u0010\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0013H\u0016R\u0014\u0010\n\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\fR\"\u0010\u0004\u001a\u0013\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00070\u0005¢\u0006\u0002\b\b¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000f\u0082\u0001\u0003\u0017\u0018\u0019¨\u0006\u001a"}, d2 = {"Lorg/jetbrains/kotlin/util/ReturnsCheck;", "Lorg/jetbrains/kotlin/util/Check;", "name", "", ModuleXmlParser.TYPE, "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/builtins/KotlinBuiltIns;", "Lorg/jetbrains/kotlin/types/KotlinType;", "Lkotlin/ExtensionFunctionType;", "(Ljava/lang/String;Lkotlin/jvm/functions/Function1;)V", MediaTrack.ROLE_DESCRIPTION, "getDescription", "()Ljava/lang/String;", "getName", "getType", "()Lkotlin/jvm/functions/Function1;", "check", "", "functionDescriptor", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "ReturnsBoolean", "ReturnsInt", "ReturnsUnit", "Lorg/jetbrains/kotlin/util/ReturnsCheck$ReturnsBoolean;", "Lorg/jetbrains/kotlin/util/ReturnsCheck$ReturnsInt;", "Lorg/jetbrains/kotlin/util/ReturnsCheck$ReturnsUnit;", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class ReturnsCheck implements Check {
    private final String description;
    private final String name;
    private final Function1<KotlinBuiltIns, KotlinType> type;

    public /* synthetic */ ReturnsCheck(String str, Function1 function1, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, function1);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private ReturnsCheck(String str, Function1<? super KotlinBuiltIns, ? extends KotlinType> function1) {
        this.name = str;
        this.type = function1;
        this.description = "must return " + str;
    }

    public final String getName() {
        return this.name;
    }

    public final Function1<KotlinBuiltIns, KotlinType> getType() {
        return this.type;
    }

    @Override // org.jetbrains.kotlin.util.Check
    public String invoke(FunctionDescriptor functionDescriptor) {
        return Check.DefaultImpls.invoke(this, functionDescriptor);
    }

    @Override // org.jetbrains.kotlin.util.Check
    public String getDescription() {
        return this.description;
    }

    @Override // org.jetbrains.kotlin.util.Check
    public boolean check(FunctionDescriptor functionDescriptor) {
        Intrinsics.checkNotNullParameter(functionDescriptor, "functionDescriptor");
        return Intrinsics.areEqual(functionDescriptor.getReturnType(), this.type.invoke(DescriptorUtilsKt.getBuiltIns(functionDescriptor)));
    }

    /* JADX INFO: compiled from: modifierChecks.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/util/ReturnsCheck$ReturnsBoolean;", "Lorg/jetbrains/kotlin/util/ReturnsCheck;", "()V", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class ReturnsBoolean extends ReturnsCheck {
        public static final ReturnsBoolean INSTANCE = new ReturnsBoolean();

        private ReturnsBoolean() {
            super("Boolean", new Function1<KotlinBuiltIns, KotlinType>() { // from class: org.jetbrains.kotlin.util.ReturnsCheck.ReturnsBoolean.1
                @Override // kotlin.jvm.functions.Function1
                public final KotlinType invoke(KotlinBuiltIns kotlinBuiltIns) {
                    Intrinsics.checkNotNullParameter(kotlinBuiltIns, "$this$null");
                    SimpleType booleanType = kotlinBuiltIns.getBooleanType();
                    Intrinsics.checkNotNullExpressionValue(booleanType, "getBooleanType(...)");
                    return booleanType;
                }
            }, null);
        }
    }

    /* JADX INFO: compiled from: modifierChecks.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/util/ReturnsCheck$ReturnsInt;", "Lorg/jetbrains/kotlin/util/ReturnsCheck;", "()V", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class ReturnsInt extends ReturnsCheck {
        public static final ReturnsInt INSTANCE = new ReturnsInt();

        private ReturnsInt() {
            super("Int", new Function1<KotlinBuiltIns, KotlinType>() { // from class: org.jetbrains.kotlin.util.ReturnsCheck.ReturnsInt.1
                @Override // kotlin.jvm.functions.Function1
                public final KotlinType invoke(KotlinBuiltIns kotlinBuiltIns) {
                    Intrinsics.checkNotNullParameter(kotlinBuiltIns, "$this$null");
                    SimpleType intType = kotlinBuiltIns.getIntType();
                    Intrinsics.checkNotNullExpressionValue(intType, "getIntType(...)");
                    return intType;
                }
            }, null);
        }
    }

    /* JADX INFO: compiled from: modifierChecks.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/util/ReturnsCheck$ReturnsUnit;", "Lorg/jetbrains/kotlin/util/ReturnsCheck;", "()V", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class ReturnsUnit extends ReturnsCheck {
        public static final ReturnsUnit INSTANCE = new ReturnsUnit();

        private ReturnsUnit() {
            super("Unit", new Function1<KotlinBuiltIns, KotlinType>() { // from class: org.jetbrains.kotlin.util.ReturnsCheck.ReturnsUnit.1
                @Override // kotlin.jvm.functions.Function1
                public final KotlinType invoke(KotlinBuiltIns kotlinBuiltIns) {
                    Intrinsics.checkNotNullParameter(kotlinBuiltIns, "$this$null");
                    SimpleType unitType = kotlinBuiltIns.getUnitType();
                    Intrinsics.checkNotNullExpressionValue(unitType, "getUnitType(...)");
                    return unitType;
                }
            }, null);
        }
    }
}

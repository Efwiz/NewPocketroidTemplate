package org.jetbrains.kotlin.idea;

import java.util.List;
import kotlin.Deprecated;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.MutablePropertyReference1;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;
import kotlin.reflect.KMutableProperty1;
import org.jetbrains.kotlin.cli.common.arguments.K2JVMCompilerArguments;
import org.jetbrains.kotlin.config.JvmTarget;

/* JADX INFO: compiled from: explicitDefaultSubstitutors.kt */
/* JADX INFO: loaded from: classes4.dex */
@Deprecated(message = "Minimal supported jvmTarget version is 1.8")
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0004\bÇ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0016\u0010\u0014\u001a\u00020\u00152\f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00040\bH\u0016J\u0016\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00040\b2\u0006\u0010\u0018\u001a\u00020\u0004H\u0002R\u0014\u0010\u0003\u001a\u00020\u00048BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006R\u001a\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00040\b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\nR\u0014\u0010\u000b\u001a\u00020\u00048BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\u0006R\u001a\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00040\b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000e\u0010\nR\"\u0010\u000f\u001a\u0010\u0012\u0004\u0012\u00020\u0011\u0012\u0006\u0012\u0004\u0018\u00010\u00040\u00108VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0012\u0010\u0013¨\u0006\u0019"}, d2 = {"Lorg/jetbrains/kotlin/idea/JvmTargetDefaultSubstitutor;", "Lorg/jetbrains/kotlin/idea/ExplicitDefaultSubstitutor;", "()V", "newDefault", "", "getNewDefault", "()Ljava/lang/String;", "newSubstitution", "", "getNewSubstitution", "()Ljava/util/List;", "oldDefault", "getOldDefault", "oldSubstitution", "getOldSubstitution", "substitutedProperty", "Lkotlin/reflect/KMutableProperty1;", "Lorg/jetbrains/kotlin/cli/common/arguments/K2JVMCompilerArguments;", "getSubstitutedProperty", "()Lkotlin/reflect/KMutableProperty1;", "isSubstitutable", "", "args", "prepareSubstitution", "default", "kotlin-build-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JvmTargetDefaultSubstitutor extends ExplicitDefaultSubstitutor {
    public static final JvmTargetDefaultSubstitutor INSTANCE = new JvmTargetDefaultSubstitutor();

    private JvmTargetDefaultSubstitutor() {
        super(null);
    }

    @Override // org.jetbrains.kotlin.idea.ExplicitDefaultSubstitutor
    public KMutableProperty1<K2JVMCompilerArguments, String> getSubstitutedProperty() {
        return new MutablePropertyReference1() { // from class: org.jetbrains.kotlin.idea.JvmTargetDefaultSubstitutor$substitutedProperty$1
            @Override // kotlin.jvm.internal.CallableReference
            public String getSignature() {
                return "getJvmTarget()Ljava/lang/String;";
            }

            @Override // kotlin.reflect.KProperty1
            public Object get(Object obj) {
                return ((K2JVMCompilerArguments) obj).getJvmTarget();
            }

            @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
            public String getName() {
                return "jvmTarget";
            }

            @Override // kotlin.jvm.internal.CallableReference
            public KDeclarationContainer getOwner() {
                return Reflection.getOrCreateKotlinClass(K2JVMCompilerArguments.class);
            }

            @Override // kotlin.reflect.KMutableProperty1
            public void set(Object obj, Object obj2) {
                ((K2JVMCompilerArguments) obj).setJvmTarget((String) obj2);
            }
        };
    }

    private final String getOldDefault() {
        return JvmTarget.JVM_1_6.getDescription();
    }

    private final String getNewDefault() {
        return JvmTarget.JVM_1_8.getDescription();
    }

    private final List<String> prepareSubstitution(String str) {
        return CollectionsKt.listOf((Object[]) new String[]{getArgument().value(), str});
    }

    @Override // org.jetbrains.kotlin.idea.ExplicitDefaultSubstitutor
    public List<String> getOldSubstitution() {
        return prepareSubstitution(getOldDefault());
    }

    @Override // org.jetbrains.kotlin.idea.ExplicitDefaultSubstitutor
    public List<String> getNewSubstitution() {
        return prepareSubstitution(getNewDefault());
    }

    @Override // org.jetbrains.kotlin.idea.ExplicitDefaultSubstitutor
    public boolean isSubstitutable(List<String> args) {
        Intrinsics.checkNotNullParameter(args, "args");
        return !args.contains(getArgument().value());
    }
}

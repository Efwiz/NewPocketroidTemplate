package org.jetbrains.kotlin.idea;

import java.lang.reflect.Field;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.reflect.KProperty1;
import kotlin.reflect.jvm.ReflectJvmMapping;
import org.jetbrains.kotlin.cli.common.arguments.Argument;
import org.jetbrains.kotlin.cli.common.arguments.CommonToolArguments;

/* JADX INFO: compiled from: explicitDefaultSubstitutors.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001B\u0007\b\u0004¢\u0006\u0002\u0010\u0002J\u0016\u0010\u0015\u001a\u00020\u00162\f\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u000b0\nH&R\u001b\u0010\u0003\u001a\u00020\u00048DX\u0084\u0084\u0002¢\u0006\f\n\u0004\b\u0007\u0010\b\u001a\u0004\b\u0005\u0010\u0006R\u0018\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\nX¦\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\rR\u0018\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u000b0\nX¦\u0004¢\u0006\u0006\u001a\u0004\b\u000f\u0010\rR\"\u0010\u0010\u001a\u0012\u0012\u0006\b\u0001\u0012\u00020\u0012\u0012\u0006\u0012\u0004\u0018\u00010\u000b0\u0011X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0013\u0010\u0014\u0082\u0001\u0001\u0018¨\u0006\u0019"}, d2 = {"Lorg/jetbrains/kotlin/idea/ExplicitDefaultSubstitutor;", "", "()V", "argument", "Lorg/jetbrains/kotlin/cli/common/arguments/Argument;", "getArgument", "()Lorg/jetbrains/kotlin/cli/common/arguments/Argument;", "argument$delegate", "Lkotlin/Lazy;", "newSubstitution", "", "", "getNewSubstitution", "()Ljava/util/List;", "oldSubstitution", "getOldSubstitution", "substitutedProperty", "Lkotlin/reflect/KProperty1;", "Lorg/jetbrains/kotlin/cli/common/arguments/CommonToolArguments;", "getSubstitutedProperty", "()Lkotlin/reflect/KProperty1;", "isSubstitutable", "", "args", "Lorg/jetbrains/kotlin/idea/JvmTargetDefaultSubstitutor;", "kotlin-build-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class ExplicitDefaultSubstitutor {

    /* JADX INFO: renamed from: argument$delegate, reason: from kotlin metadata */
    private final Lazy argument;

    public /* synthetic */ ExplicitDefaultSubstitutor(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    public abstract List<String> getNewSubstitution();

    public abstract List<String> getOldSubstitution();

    public abstract KProperty1<? extends CommonToolArguments, String> getSubstitutedProperty();

    public abstract boolean isSubstitutable(List<String> args);

    private ExplicitDefaultSubstitutor() {
        this.argument = LazyKt.lazy(new Function0<Argument>() { // from class: org.jetbrains.kotlin.idea.ExplicitDefaultSubstitutor$argument$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Argument invoke() {
                Field javaField = ReflectJvmMapping.getJavaField(this.this$0.getSubstitutedProperty());
                Argument argument = javaField != null ? (Argument) javaField.getAnnotation(Argument.class) : null;
                if (argument != null) {
                    return argument;
                }
                throw new IllegalStateException(("Property \"" + this.this$0.getSubstitutedProperty().getName() + "\" has no Argument annotation").toString());
            }
        });
    }

    protected final Argument getArgument() {
        return (Argument) this.argument.getValue();
    }
}

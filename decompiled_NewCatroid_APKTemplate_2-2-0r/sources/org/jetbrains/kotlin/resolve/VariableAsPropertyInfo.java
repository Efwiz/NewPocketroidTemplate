package org.jetbrains.kotlin.resolve;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.psi.KtProperty;
import org.jetbrains.kotlin.psi.KtPropertyAccessor;
import org.jetbrains.kotlin.types.KotlinType;

/* JADX INFO: compiled from: VariableAsPropertyInfo.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\f\u0018\u0000 \u00132\u00020\u0001:\u0001\u0013B3\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\b¢\u0006\u0002\u0010\nR\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\t\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\fR\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u000fR\u0013\u0010\u0005\u001a\u0004\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012¨\u0006\u0014"}, d2 = {"Lorg/jetbrains/kotlin/resolve/VariableAsPropertyInfo;", "", "propertyGetter", "Lorg/jetbrains/kotlin/psi/KtPropertyAccessor;", "propertySetter", "variableType", "Lorg/jetbrains/kotlin/types/KotlinType;", "hasBody", "", "hasDelegate", "(Lorg/jetbrains/kotlin/psi/KtPropertyAccessor;Lorg/jetbrains/kotlin/psi/KtPropertyAccessor;Lorg/jetbrains/kotlin/types/KotlinType;ZZ)V", "getHasBody", "()Z", "getHasDelegate", "getPropertyGetter", "()Lorg/jetbrains/kotlin/psi/KtPropertyAccessor;", "getPropertySetter", "getVariableType", "()Lorg/jetbrains/kotlin/types/KotlinType;", "Companion", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class VariableAsPropertyInfo {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final boolean hasBody;
    private final boolean hasDelegate;
    private final KtPropertyAccessor propertyGetter;
    private final KtPropertyAccessor propertySetter;
    private final KotlinType variableType;

    public VariableAsPropertyInfo(KtPropertyAccessor ktPropertyAccessor, KtPropertyAccessor ktPropertyAccessor2, KotlinType kotlinType, boolean z, boolean z2) {
        this.propertyGetter = ktPropertyAccessor;
        this.propertySetter = ktPropertyAccessor2;
        this.variableType = kotlinType;
        this.hasBody = z;
        this.hasDelegate = z2;
    }

    public final KtPropertyAccessor getPropertyGetter() {
        return this.propertyGetter;
    }

    public final KtPropertyAccessor getPropertySetter() {
        return this.propertySetter;
    }

    public final KotlinType getVariableType() {
        return this.variableType;
    }

    public final boolean getHasBody() {
        return this.hasBody;
    }

    public final boolean getHasDelegate() {
        return this.hasDelegate;
    }

    /* JADX INFO: compiled from: VariableAsPropertyInfo.kt */
    @Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006J\u000e\u0010\u0007\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\t¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/resolve/VariableAsPropertyInfo$Companion;", "", "()V", "createFromDestructuringDeclarationEntry", "Lorg/jetbrains/kotlin/resolve/VariableAsPropertyInfo;", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/types/KotlinType;", "createFromProperty", "property", "Lorg/jetbrains/kotlin/psi/KtProperty;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final VariableAsPropertyInfo createFromDestructuringDeclarationEntry(KotlinType type) {
            Intrinsics.checkNotNullParameter(type, "type");
            return new VariableAsPropertyInfo(null, null, type, false, false);
        }

        public final VariableAsPropertyInfo createFromProperty(KtProperty property) {
            Intrinsics.checkNotNullParameter(property, "property");
            return new VariableAsPropertyInfo(property.getGetter(), property.getSetter(), null, property.hasBody(), property.hasDelegate());
        }
    }
}

package org.jetbrains.kotlin.resolve.scopes.receivers;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.types.KotlinType;

/* JADX INFO: compiled from: Qualifier.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u00002\u00020\u0001B#\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0000¢\u0006\u0002\u0010\u0007J\b\u0010\u000e\u001a\u00020\u0000H\u0016J\b\u0010\u000f\u001a\u00020\u0005H\u0016J\u0010\u0010\u0010\u001a\u00020\u00002\u0006\u0010\u0011\u001a\u00020\u0005H\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0014\u0010\n\u001a\u00020\u000b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\rR\u000e\u0010\u0006\u001a\u00020\u0000X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/resolve/scopes/receivers/ClassValueReceiver;", "Lorg/jetbrains/kotlin/resolve/scopes/receivers/ExpressionReceiver;", "classQualifier", "Lorg/jetbrains/kotlin/resolve/scopes/receivers/ClassifierQualifier;", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/types/KotlinType;", "original", "(Lorg/jetbrains/kotlin/resolve/scopes/receivers/ClassifierQualifier;Lorg/jetbrains/kotlin/types/KotlinType;Lorg/jetbrains/kotlin/resolve/scopes/receivers/ClassValueReceiver;)V", "getClassQualifier", "()Lorg/jetbrains/kotlin/resolve/scopes/receivers/ClassifierQualifier;", "expression", "Lorg/jetbrains/kotlin/psi/KtExpression;", "getExpression", "()Lorg/jetbrains/kotlin/psi/KtExpression;", "getOriginal", "getType", "replaceType", "newType", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ClassValueReceiver implements ExpressionReceiver {
    private final ClassifierQualifier classQualifier;
    private final ClassValueReceiver original;
    private final KotlinType type;

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public ClassValueReceiver(ClassifierQualifier classQualifier, KotlinType type) {
        this(classQualifier, type, null, 4, null);
        Intrinsics.checkNotNullParameter(classQualifier, "classQualifier");
        Intrinsics.checkNotNullParameter(type, "type");
    }

    public ClassValueReceiver(ClassifierQualifier classQualifier, KotlinType type, ClassValueReceiver classValueReceiver) {
        Intrinsics.checkNotNullParameter(classQualifier, "classQualifier");
        Intrinsics.checkNotNullParameter(type, "type");
        this.classQualifier = classQualifier;
        this.type = type;
        this.original = classValueReceiver == null ? this : classValueReceiver;
    }

    public /* synthetic */ ClassValueReceiver(ClassifierQualifier classifierQualifier, KotlinType kotlinType, ClassValueReceiver classValueReceiver, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(classifierQualifier, kotlinType, (i & 4) != 0 ? null : classValueReceiver);
    }

    public final ClassifierQualifier getClassQualifier() {
        return this.classQualifier;
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.receivers.ReceiverValue
    public KotlinType getType() {
        return this.type;
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.receivers.ExpressionReceiver
    public KtExpression getExpression() {
        return QualifierKt.getExpression(this.classQualifier);
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.receivers.ReceiverValue
    /* JADX INFO: renamed from: replaceType */
    public ClassValueReceiver mo7466replaceType(KotlinType newType) {
        Intrinsics.checkNotNullParameter(newType, "newType");
        return new ClassValueReceiver(this.classQualifier, newType, this.original);
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.receivers.ReceiverValue
    public ClassValueReceiver getOriginal() {
        return this.original;
    }
}

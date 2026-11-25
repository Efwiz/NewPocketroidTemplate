package org.jetbrains.kotlin.resolve.calls.tower;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.psi.KtTypeProjection;
import org.jetbrains.kotlin.resolve.calls.model.SimpleTypeArgument;
import org.jetbrains.kotlin.types.UnwrappedType;

/* JADX INFO: compiled from: NewCallArguments.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006R\u0014\u0010\u0004\u001a\u00020\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/tower/SimpleTypeArgumentImpl;", "Lorg/jetbrains/kotlin/resolve/calls/model/SimpleTypeArgument;", "typeProjection", "Lorg/jetbrains/kotlin/psi/KtTypeProjection;", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/types/UnwrappedType;", "(Lorg/jetbrains/kotlin/psi/KtTypeProjection;Lorg/jetbrains/kotlin/types/UnwrappedType;)V", "getType", "()Lorg/jetbrains/kotlin/types/UnwrappedType;", "getTypeProjection", "()Lorg/jetbrains/kotlin/psi/KtTypeProjection;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SimpleTypeArgumentImpl implements SimpleTypeArgument {
    private final UnwrappedType type;
    private final KtTypeProjection typeProjection;

    public SimpleTypeArgumentImpl(KtTypeProjection typeProjection, UnwrappedType type) {
        Intrinsics.checkNotNullParameter(typeProjection, "typeProjection");
        Intrinsics.checkNotNullParameter(type, "type");
        this.typeProjection = typeProjection;
        this.type = type;
    }

    public final KtTypeProjection getTypeProjection() {
        return this.typeProjection;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.SimpleTypeArgument
    public UnwrappedType getType() {
        return this.type;
    }
}

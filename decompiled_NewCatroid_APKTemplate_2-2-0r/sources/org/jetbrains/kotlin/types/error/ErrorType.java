package org.jetbrains.kotlin.types.error;

import java.util.Arrays;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.StringCompanionObject;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.load.java.JvmAbi;
import org.jetbrains.kotlin.resolve.scopes.MemberScope;
import org.jetbrains.kotlin.types.SimpleType;
import org.jetbrains.kotlin.types.TypeAttributes;
import org.jetbrains.kotlin.types.TypeConstructor;
import org.jetbrains.kotlin.types.TypeProjection;
import org.jetbrains.kotlin.types.checker.KotlinTypeRefiner;

/* JADX INFO: compiled from: ErrorType.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0013\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\u00020\u0001BM\b\u0001\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u000e\b\u0002\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t\u0012\b\b\u0002\u0010\u000b\u001a\u00020\f\u0012\u0012\u0010\r\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u000f0\u000e\"\u00020\u000f¢\u0006\u0002\u0010\u0010J\u0010\u0010$\u001a\u00020\u00012\u0006\u0010%\u001a\u00020\fH\u0016J\u0010\u0010&\u001a\u00020\u00002\u0006\u0010'\u001a\u00020(H\u0017J\u0014\u0010)\u001a\u00020\u00002\f\u0010*\u001a\b\u0012\u0004\u0012\u00020\n0\tJ\u0010\u0010+\u001a\u00020\u00012\u0006\u0010,\u001a\u00020\u0014H\u0016R\u001a\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\tX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0014\u0010\u0013\u001a\u00020\u00148VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0015\u0010\u0016R\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u0011\u0010\u0019\u001a\u00020\u000f¢\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u001bR\u001b\u0010\r\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u000f0\u000e¢\u0006\n\n\u0002\u0010\u001e\u001a\u0004\b\u001c\u0010\u001dR\u0014\u0010\u000b\u001a\u00020\fX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\u001fR\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b \u0010!R\u0014\u0010\u0004\u001a\u00020\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\"\u0010#¨\u0006-"}, d2 = {"Lorg/jetbrains/kotlin/types/error/ErrorType;", "Lorg/jetbrains/kotlin/types/SimpleType;", JvmAbi.ERASED_INLINE_CONSTRUCTOR_NAME, "Lorg/jetbrains/kotlin/types/TypeConstructor;", "memberScope", "Lorg/jetbrains/kotlin/resolve/scopes/MemberScope;", Namer.METADATA_CLASS_KIND, "Lorg/jetbrains/kotlin/types/error/ErrorTypeKind;", "arguments", "", "Lorg/jetbrains/kotlin/types/TypeProjection;", "isMarkedNullable", "", "formatParams", "", "", "(Lorg/jetbrains/kotlin/types/TypeConstructor;Lorg/jetbrains/kotlin/resolve/scopes/MemberScope;Lorg/jetbrains/kotlin/types/error/ErrorTypeKind;Ljava/util/List;Z[Ljava/lang/String;)V", "getArguments", "()Ljava/util/List;", "attributes", "Lorg/jetbrains/kotlin/types/TypeAttributes;", "getAttributes", "()Lorg/jetbrains/kotlin/types/TypeAttributes;", "getConstructor", "()Lorg/jetbrains/kotlin/types/TypeConstructor;", "debugMessage", "getDebugMessage", "()Ljava/lang/String;", "getFormatParams", "()[Ljava/lang/String;", "[Ljava/lang/String;", "()Z", "getKind", "()Lorg/jetbrains/kotlin/types/error/ErrorTypeKind;", "getMemberScope", "()Lorg/jetbrains/kotlin/resolve/scopes/MemberScope;", "makeNullableAsSpecified", "newNullability", "refine", "kotlinTypeRefiner", "Lorg/jetbrains/kotlin/types/checker/KotlinTypeRefiner;", "replaceArguments", "newArguments", "replaceAttributes", "newAttributes", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ErrorType extends SimpleType {
    private final List<TypeProjection> arguments;
    private final TypeConstructor constructor;
    private final String debugMessage;
    private final String[] formatParams;
    private final boolean isMarkedNullable;
    private final ErrorTypeKind kind;
    private final MemberScope memberScope;

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public ErrorType(TypeConstructor constructor, MemberScope memberScope, ErrorTypeKind kind, List<? extends TypeProjection> arguments, String... formatParams) {
        this(constructor, memberScope, kind, arguments, false, formatParams, 16, null);
        Intrinsics.checkNotNullParameter(constructor, "constructor");
        Intrinsics.checkNotNullParameter(memberScope, "memberScope");
        Intrinsics.checkNotNullParameter(kind, "kind");
        Intrinsics.checkNotNullParameter(arguments, "arguments");
        Intrinsics.checkNotNullParameter(formatParams, "formatParams");
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public ErrorType(TypeConstructor constructor, MemberScope memberScope, ErrorTypeKind kind, String... formatParams) {
        this(constructor, memberScope, kind, null, false, formatParams, 24, null);
        Intrinsics.checkNotNullParameter(constructor, "constructor");
        Intrinsics.checkNotNullParameter(memberScope, "memberScope");
        Intrinsics.checkNotNullParameter(kind, "kind");
        Intrinsics.checkNotNullParameter(formatParams, "formatParams");
    }

    @Override // org.jetbrains.kotlin.types.UnwrappedType, org.jetbrains.kotlin.types.KotlinType
    public ErrorType refine(KotlinTypeRefiner kotlinTypeRefiner) {
        Intrinsics.checkNotNullParameter(kotlinTypeRefiner, "kotlinTypeRefiner");
        return this;
    }

    @Override // org.jetbrains.kotlin.types.KotlinType
    public TypeConstructor getConstructor() {
        return this.constructor;
    }

    @Override // org.jetbrains.kotlin.types.KotlinType
    public MemberScope getMemberScope() {
        return this.memberScope;
    }

    public final ErrorTypeKind getKind() {
        return this.kind;
    }

    public /* synthetic */ ErrorType(TypeConstructor typeConstructor, MemberScope memberScope, ErrorTypeKind errorTypeKind, List list, boolean z, String[] strArr, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(typeConstructor, memberScope, errorTypeKind, (i & 8) != 0 ? CollectionsKt.emptyList() : list, (i & 16) != 0 ? false : z, strArr);
    }

    @Override // org.jetbrains.kotlin.types.KotlinType
    public List<TypeProjection> getArguments() {
        return this.arguments;
    }

    @Override // org.jetbrains.kotlin.types.KotlinType
    /* JADX INFO: renamed from: isMarkedNullable, reason: from getter */
    public boolean getIsMarkedNullable() {
        return this.isMarkedNullable;
    }

    public final String[] getFormatParams() {
        return this.formatParams;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public ErrorType(TypeConstructor constructor, MemberScope memberScope, ErrorTypeKind kind, List<? extends TypeProjection> arguments, boolean z, String... formatParams) {
        Intrinsics.checkNotNullParameter(constructor, "constructor");
        Intrinsics.checkNotNullParameter(memberScope, "memberScope");
        Intrinsics.checkNotNullParameter(kind, "kind");
        Intrinsics.checkNotNullParameter(arguments, "arguments");
        Intrinsics.checkNotNullParameter(formatParams, "formatParams");
        this.constructor = constructor;
        this.memberScope = memberScope;
        this.kind = kind;
        this.arguments = arguments;
        this.isMarkedNullable = z;
        this.formatParams = formatParams;
        StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
        String debugMessage = kind.getDebugMessage();
        Object[] objArrCopyOf = Arrays.copyOf(formatParams, formatParams.length);
        String str = String.format(debugMessage, Arrays.copyOf(objArrCopyOf, objArrCopyOf.length));
        Intrinsics.checkNotNullExpressionValue(str, "format(...)");
        this.debugMessage = str;
    }

    public final String getDebugMessage() {
        return this.debugMessage;
    }

    @Override // org.jetbrains.kotlin.types.KotlinType
    public TypeAttributes getAttributes() {
        return TypeAttributes.INSTANCE.getEmpty();
    }

    @Override // org.jetbrains.kotlin.types.UnwrappedType
    public SimpleType replaceAttributes(TypeAttributes newAttributes) {
        Intrinsics.checkNotNullParameter(newAttributes, "newAttributes");
        return this;
    }

    public final ErrorType replaceArguments(List<? extends TypeProjection> newArguments) {
        Intrinsics.checkNotNullParameter(newArguments, "newArguments");
        TypeConstructor constructor = getConstructor();
        MemberScope memberScope = getMemberScope();
        ErrorTypeKind errorTypeKind = this.kind;
        boolean isMarkedNullable = getIsMarkedNullable();
        String[] strArr = this.formatParams;
        return new ErrorType(constructor, memberScope, errorTypeKind, newArguments, isMarkedNullable, (String[]) Arrays.copyOf(strArr, strArr.length));
    }

    @Override // org.jetbrains.kotlin.types.UnwrappedType
    public SimpleType makeNullableAsSpecified(boolean newNullability) {
        TypeConstructor constructor = getConstructor();
        MemberScope memberScope = getMemberScope();
        ErrorTypeKind errorTypeKind = this.kind;
        List<TypeProjection> arguments = getArguments();
        String[] strArr = this.formatParams;
        return new ErrorType(constructor, memberScope, errorTypeKind, arguments, newNullability, (String[]) Arrays.copyOf(strArr, strArr.length));
    }
}

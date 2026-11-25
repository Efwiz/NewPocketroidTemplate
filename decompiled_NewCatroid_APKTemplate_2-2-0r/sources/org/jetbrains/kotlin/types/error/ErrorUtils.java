package org.jetbrains.kotlin.types.error;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.ReceiverParameterDescriptor;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.KotlinTypeKt;
import org.jetbrains.kotlin.types.TypeConstructor;
import org.jetbrains.kotlin.types.TypeProjection;
import org.jetbrains.kotlin.types.typeUtil.TypeUtilsKt;

/* JADX INFO: compiled from: ErrorUtils.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u008a\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0017\u001a\u00020\u00182\b\u0010\u0019\u001a\u0004\u0018\u00010\u0012J\u000e\u0010\u001a\u001a\u00020\u00182\u0006\u0010\u001b\u001a\u00020\u001cJ\u000e\u0010\u001d\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u0012J)\u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020!2\u0012\u0010\"\u001a\n\u0012\u0006\b\u0001\u0012\u00020$0#\"\u00020$H\u0007¢\u0006\u0002\u0010%J1\u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020!2\u0006\u0010&\u001a\u00020\u00182\u0012\u0010\"\u001a\n\u0012\u0006\b\u0001\u0012\u00020$0#\"\u00020$H\u0007¢\u0006\u0002\u0010'J)\u0010(\u001a\u00020)2\u0006\u0010 \u001a\u00020*2\u0012\u0010\"\u001a\n\u0012\u0006\b\u0001\u0012\u00020$0#\"\u00020$H\u0007¢\u0006\u0002\u0010+J/\u0010(\u001a\u00020)2\u0006\u0010 \u001a\u00020*2\u0006\u0010,\u001a\u00020-2\u0012\u0010\"\u001a\n\u0012\u0006\b\u0001\u0012\u00020$0#\"\u00020$¢\u0006\u0002\u0010.J'\u0010/\u001a\u0002002\u0006\u0010 \u001a\u00020*2\u0012\u0010\"\u001a\n\u0012\u0006\b\u0001\u0012\u00020$0#\"\u00020$¢\u0006\u0002\u00101J5\u00102\u001a\u00020)2\u0006\u0010 \u001a\u00020*2\f\u00103\u001a\b\u0012\u0004\u0012\u000205042\u0012\u0010\"\u001a\n\u0012\u0006\b\u0001\u0012\u00020$0#\"\u00020$¢\u0006\u0002\u00106J=\u00102\u001a\u00020)2\u0006\u0010 \u001a\u00020*2\f\u00103\u001a\b\u0012\u0004\u0012\u000205042\u0006\u0010,\u001a\u00020-2\u0012\u0010\"\u001a\n\u0012\u0006\b\u0001\u0012\u00020$0#\"\u00020$¢\u0006\u0002\u00107J\u0012\u00108\u001a\u00020\u00182\b\u00109\u001a\u0004\u0018\u00010:H\u0007J\u0012\u0010;\u001a\u00020\u00182\b\u00109\u001a\u0004\u0018\u00010:H\u0002J\u0012\u0010<\u001a\u00020\u00182\b\u0010\u0019\u001a\u0004\u0018\u00010\u0012H\u0007J\u000e\u0010=\u001a\u00020$2\u0006\u0010\u0019\u001a\u00020\u0012R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u0017\u0010\r\u001a\b\u0012\u0004\u0012\u00020\f0\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0011\u0010\u0011\u001a\u00020\u0012¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0011\u0010\u0015\u001a\u00020\u0012¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0014¨\u0006>"}, d2 = {"Lorg/jetbrains/kotlin/types/error/ErrorUtils;", "", "()V", "errorClass", "Lorg/jetbrains/kotlin/types/error/ErrorClassDescriptor;", "getErrorClass", "()Lorg/jetbrains/kotlin/types/error/ErrorClassDescriptor;", "errorModule", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "getErrorModule", "()Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "errorProperty", "Lorg/jetbrains/kotlin/descriptors/PropertyDescriptor;", "errorPropertyGroup", "", "getErrorPropertyGroup", "()Ljava/util/Set;", "errorPropertyType", "Lorg/jetbrains/kotlin/types/KotlinType;", "getErrorPropertyType", "()Lorg/jetbrains/kotlin/types/KotlinType;", "errorTypeForLoopInSupertypes", "getErrorTypeForLoopInSupertypes", "containsErrorType", "", ModuleXmlParser.TYPE, "containsErrorTypeInParameters", "function", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "containsUninferredTypeVariable", "createErrorScope", "Lorg/jetbrains/kotlin/types/error/ErrorScope;", Namer.METADATA_CLASS_KIND, "Lorg/jetbrains/kotlin/types/error/ErrorScopeKind;", "formatParams", "", "", "(Lorg/jetbrains/kotlin/types/error/ErrorScopeKind;[Ljava/lang/String;)Lorg/jetbrains/kotlin/types/error/ErrorScope;", "throwExceptions", "(Lorg/jetbrains/kotlin/types/error/ErrorScopeKind;Z[Ljava/lang/String;)Lorg/jetbrains/kotlin/types/error/ErrorScope;", "createErrorType", "Lorg/jetbrains/kotlin/types/error/ErrorType;", "Lorg/jetbrains/kotlin/types/error/ErrorTypeKind;", "(Lorg/jetbrains/kotlin/types/error/ErrorTypeKind;[Ljava/lang/String;)Lorg/jetbrains/kotlin/types/error/ErrorType;", "typeConstructor", "Lorg/jetbrains/kotlin/types/TypeConstructor;", "(Lorg/jetbrains/kotlin/types/error/ErrorTypeKind;Lorg/jetbrains/kotlin/types/TypeConstructor;[Ljava/lang/String;)Lorg/jetbrains/kotlin/types/error/ErrorType;", "createErrorTypeConstructor", "Lorg/jetbrains/kotlin/types/error/ErrorTypeConstructor;", "(Lorg/jetbrains/kotlin/types/error/ErrorTypeKind;[Ljava/lang/String;)Lorg/jetbrains/kotlin/types/error/ErrorTypeConstructor;", "createErrorTypeWithArguments", "arguments", "", "Lorg/jetbrains/kotlin/types/TypeProjection;", "(Lorg/jetbrains/kotlin/types/error/ErrorTypeKind;Ljava/util/List;[Ljava/lang/String;)Lorg/jetbrains/kotlin/types/error/ErrorType;", "(Lorg/jetbrains/kotlin/types/error/ErrorTypeKind;Ljava/util/List;Lorg/jetbrains/kotlin/types/TypeConstructor;[Ljava/lang/String;)Lorg/jetbrains/kotlin/types/error/ErrorType;", "isError", "candidate", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "isErrorClass", "isUninferredTypeVariable", "unresolvedTypeAsItIs", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ErrorUtils {
    private static final ErrorClassDescriptor errorClass;
    private static final PropertyDescriptor errorProperty;
    private static final Set<PropertyDescriptor> errorPropertyGroup;
    private static final KotlinType errorPropertyType;
    private static final KotlinType errorTypeForLoopInSupertypes;
    public static final ErrorUtils INSTANCE = new ErrorUtils();
    private static final ModuleDescriptor errorModule = ErrorModuleDescriptor.INSTANCE;

    private ErrorUtils() {
    }

    static {
        String str = String.format(ErrorEntity.ERROR_CLASS.getDebugText(), Arrays.copyOf(new Object[]{"unknown class"}, 1));
        Intrinsics.checkNotNullExpressionValue(str, "format(...)");
        Name nameSpecial = Name.special(str);
        Intrinsics.checkNotNullExpressionValue(nameSpecial, "special(...)");
        errorClass = new ErrorClassDescriptor(nameSpecial);
        errorTypeForLoopInSupertypes = createErrorType(ErrorTypeKind.CYCLIC_SUPERTYPES, new String[0]);
        errorPropertyType = createErrorType(ErrorTypeKind.ERROR_PROPERTY_TYPE, new String[0]);
        ErrorPropertyDescriptor errorPropertyDescriptor = new ErrorPropertyDescriptor();
        errorProperty = errorPropertyDescriptor;
        errorPropertyGroup = SetsKt.setOf(errorPropertyDescriptor);
    }

    public final ModuleDescriptor getErrorModule() {
        return errorModule;
    }

    public final ErrorClassDescriptor getErrorClass() {
        return errorClass;
    }

    public final KotlinType getErrorTypeForLoopInSupertypes() {
        return errorTypeForLoopInSupertypes;
    }

    public final KotlinType getErrorPropertyType() {
        return errorPropertyType;
    }

    public final Set<PropertyDescriptor> getErrorPropertyGroup() {
        return errorPropertyGroup;
    }

    public final boolean containsErrorTypeInParameters(FunctionDescriptor function) {
        Intrinsics.checkNotNullParameter(function, "function");
        ReceiverParameterDescriptor extensionReceiverParameter = function.getExtensionReceiverParameter();
        if (extensionReceiverParameter != null && containsErrorType(extensionReceiverParameter.getType())) {
            return true;
        }
        Iterator<ValueParameterDescriptor> it = function.getValueParameters().iterator();
        while (it.hasNext()) {
            if (containsErrorType(it.next().getType())) {
                return true;
            }
        }
        Iterator<TypeParameterDescriptor> it2 = function.getTypeParameters().iterator();
        while (it2.hasNext()) {
            Iterator<KotlinType> it3 = it2.next().getUpperBounds().iterator();
            while (it3.hasNext()) {
                if (containsErrorType(it3.next())) {
                    return true;
                }
            }
        }
        return false;
    }

    @JvmStatic
    public static final ErrorScope createErrorScope(ErrorScopeKind kind, String... formatParams) {
        Intrinsics.checkNotNullParameter(kind, "kind");
        Intrinsics.checkNotNullParameter(formatParams, "formatParams");
        return createErrorScope(kind, false, (String[]) Arrays.copyOf(formatParams, formatParams.length));
    }

    @JvmStatic
    public static final ErrorScope createErrorScope(ErrorScopeKind kind, boolean throwExceptions, String... formatParams) {
        Intrinsics.checkNotNullParameter(kind, "kind");
        Intrinsics.checkNotNullParameter(formatParams, "formatParams");
        return throwExceptions ? new ThrowingScope(kind, (String[]) Arrays.copyOf(formatParams, formatParams.length)) : new ErrorScope(kind, (String[]) Arrays.copyOf(formatParams, formatParams.length));
    }

    @JvmStatic
    public static final ErrorType createErrorType(ErrorTypeKind kind, String... formatParams) {
        Intrinsics.checkNotNullParameter(kind, "kind");
        Intrinsics.checkNotNullParameter(formatParams, "formatParams");
        return INSTANCE.createErrorTypeWithArguments(kind, CollectionsKt.emptyList(), (String[]) Arrays.copyOf(formatParams, formatParams.length));
    }

    public final ErrorType createErrorType(ErrorTypeKind kind, TypeConstructor typeConstructor, String... formatParams) {
        Intrinsics.checkNotNullParameter(kind, "kind");
        Intrinsics.checkNotNullParameter(typeConstructor, "typeConstructor");
        Intrinsics.checkNotNullParameter(formatParams, "formatParams");
        return createErrorTypeWithArguments(kind, CollectionsKt.emptyList(), typeConstructor, (String[]) Arrays.copyOf(formatParams, formatParams.length));
    }

    public final ErrorType createErrorTypeWithArguments(ErrorTypeKind kind, List<? extends TypeProjection> arguments, String... formatParams) {
        Intrinsics.checkNotNullParameter(kind, "kind");
        Intrinsics.checkNotNullParameter(arguments, "arguments");
        Intrinsics.checkNotNullParameter(formatParams, "formatParams");
        return createErrorTypeWithArguments(kind, arguments, createErrorTypeConstructor(kind, (String[]) Arrays.copyOf(formatParams, formatParams.length)), (String[]) Arrays.copyOf(formatParams, formatParams.length));
    }

    public final ErrorType createErrorTypeWithArguments(ErrorTypeKind kind, List<? extends TypeProjection> arguments, TypeConstructor typeConstructor, String... formatParams) {
        Intrinsics.checkNotNullParameter(kind, "kind");
        Intrinsics.checkNotNullParameter(arguments, "arguments");
        Intrinsics.checkNotNullParameter(typeConstructor, "typeConstructor");
        Intrinsics.checkNotNullParameter(formatParams, "formatParams");
        return new ErrorType(typeConstructor, createErrorScope(ErrorScopeKind.ERROR_TYPE_SCOPE, typeConstructor.toString()), kind, arguments, false, (String[]) Arrays.copyOf(formatParams, formatParams.length));
    }

    public final ErrorTypeConstructor createErrorTypeConstructor(ErrorTypeKind kind, String... formatParams) {
        Intrinsics.checkNotNullParameter(kind, "kind");
        Intrinsics.checkNotNullParameter(formatParams, "formatParams");
        return new ErrorTypeConstructor(kind, (String[]) Arrays.copyOf(formatParams, formatParams.length));
    }

    public final boolean containsErrorType(KotlinType type) {
        if (type == null) {
            return false;
        }
        if (KotlinTypeKt.isError(type)) {
            return true;
        }
        for (TypeProjection typeProjection : type.getArguments()) {
            if (!typeProjection.isStarProjection() && containsErrorType(typeProjection.getType())) {
                return true;
            }
        }
        return false;
    }

    @JvmStatic
    public static final boolean isError(DeclarationDescriptor candidate) {
        if (candidate != null) {
            ErrorUtils errorUtils = INSTANCE;
            if (errorUtils.isErrorClass(candidate) || errorUtils.isErrorClass(candidate.getModuleDescriptor()) || candidate == errorModule) {
                return true;
            }
        }
        return false;
    }

    private final boolean isErrorClass(DeclarationDescriptor candidate) {
        return candidate instanceof ErrorClassDescriptor;
    }

    @JvmStatic
    public static final boolean isUninferredTypeVariable(KotlinType type) {
        if (type == null) {
            return false;
        }
        TypeConstructor constructor = type.getConstructor();
        return (constructor instanceof ErrorTypeConstructor) && ((ErrorTypeConstructor) constructor).getKind() == ErrorTypeKind.UNINFERRED_TYPE_VARIABLE;
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.types.error.ErrorUtils$containsUninferredTypeVariable$1, reason: invalid class name */
    /* JADX INFO: compiled from: ErrorUtils.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    /* synthetic */ class AnonymousClass1 extends FunctionReference implements Function1<KotlinType, Boolean> {
        AnonymousClass1(Object obj) {
            super(1, obj);
        }

        @Override // kotlin.jvm.internal.CallableReference
        public final String getSignature() {
            return "isUninferredTypeVariable(Lorg/jetbrains/kotlin/types/KotlinType;)Z";
        }

        @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
        public final String getName() {
            return "isUninferredTypeVariable";
        }

        @Override // kotlin.jvm.internal.CallableReference
        public final KDeclarationContainer getOwner() {
            return Reflection.getOrCreateKotlinClass(ErrorUtils.class);
        }

        @Override // kotlin.jvm.functions.Function1
        public final Boolean invoke(KotlinType kotlinType) {
            return Boolean.valueOf(ErrorUtils.isUninferredTypeVariable(kotlinType));
        }
    }

    public final boolean containsUninferredTypeVariable(KotlinType type) {
        Intrinsics.checkNotNullParameter(type, "type");
        return TypeUtilsKt.contains(type, new AnonymousClass1(INSTANCE));
    }

    public final String unresolvedTypeAsItIs(KotlinType type) {
        Intrinsics.checkNotNullParameter(type, "type");
        TypeUtilsKt.isUnresolvedType(type);
        TypeConstructor constructor = type.getConstructor();
        Intrinsics.checkNotNull(constructor, "null cannot be cast to non-null type org.jetbrains.kotlin.types.error.ErrorTypeConstructor");
        return ((ErrorTypeConstructor) constructor).getParam(0);
    }
}

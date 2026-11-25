package org.jetbrains.kotlin.types;

import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.BindingTrace;
import org.jetbrains.kotlin.storage.NotNullLazyValue;
import org.jetbrains.kotlin.storage.StorageManager;
import org.jetbrains.kotlin.types.checker.KotlinTypeRefiner;
import org.jetbrains.kotlin.types.error.ErrorTypeKind;
import org.jetbrains.kotlin.types.model.KotlinTypeMarker;
import org.jetbrains.kotlin.util.Box;
import org.jetbrains.kotlin.util.ReenteringLazyValueComputationException;

/* JADX INFO: loaded from: classes6.dex */
public class DeferredType extends WrappedType {
    private static final Function1<Boolean, KotlinType> RECURSION_PREVENTER = new Function1() { // from class: org.jetbrains.kotlin.types.DeferredType$$ExternalSyntheticLambda0
        @Override // kotlin.jvm.functions.Function1
        public final Object invoke(Object obj) {
            return DeferredType.lambda$static$0((Boolean) obj);
        }
    };
    private final NotNullLazyValue<KotlinType> lazyValue;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 3 || i == 7 || i == 10 || i == 11) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 3 || i == 7 || i == 10 || i == 11) ? 2 : 3];
        switch (i) {
            case 1:
            case 5:
                objArr[0] = "trace";
                break;
            case 2:
            case 6:
                objArr[0] = "compute";
                break;
            case 3:
            case 7:
            case 10:
            case 11:
                objArr[0] = "org/jetbrains/kotlin/types/DeferredType";
                break;
            case 4:
            default:
                objArr[0] = "storageManager";
                break;
            case 8:
                objArr[0] = "lazyValue";
                break;
            case 9:
                objArr[0] = "kotlinTypeRefiner";
                break;
        }
        if (i == 3) {
            objArr[1] = CoroutineCodegenUtilKt.SUSPEND_FUNCTION_CREATE_METHOD_NAME;
        } else if (i == 7) {
            objArr[1] = "createRecursionIntolerant";
        } else if (i == 10) {
            objArr[1] = "getDelegate";
        } else if (i != 11) {
            objArr[1] = "org/jetbrains/kotlin/types/DeferredType";
        } else {
            objArr[1] = "toString";
        }
        switch (i) {
            case 3:
            case 7:
            case 10:
            case 11:
                break;
            case 4:
            case 5:
            case 6:
                objArr[2] = "createRecursionIntolerant";
                break;
            case 8:
                objArr[2] = "<init>";
                break;
            case 9:
                objArr[2] = "refine";
                break;
            default:
                objArr[2] = CoroutineCodegenUtilKt.SUSPEND_FUNCTION_CREATE_METHOD_NAME;
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 3 && i != 7 && i != 10 && i != 11) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    static /* synthetic */ KotlinType lambda$static$0(Boolean bool) {
        if (bool.booleanValue()) {
            throw new ReenteringLazyValueComputationException();
        }
        return org.jetbrains.kotlin.types.error.ErrorUtils.createErrorType(ErrorTypeKind.RECURSIVE_TYPE, new String[0]);
    }

    static DeferredType create(StorageManager storageManager, BindingTrace bindingTrace, Function0<KotlinType> function0) {
        if (storageManager == null) {
            $$$reportNull$$$0(0);
        }
        if (bindingTrace == null) {
            $$$reportNull$$$0(1);
        }
        if (function0 == null) {
            $$$reportNull$$$0(2);
        }
        DeferredType deferredType = new DeferredType(storageManager.createLazyValue(function0));
        bindingTrace.record(BindingContext.DEFERRED_TYPE, new Box(deferredType));
        return deferredType;
    }

    static DeferredType createRecursionIntolerant(StorageManager storageManager, BindingTrace bindingTrace, Function0<KotlinType> function0) {
        if (storageManager == null) {
            $$$reportNull$$$0(4);
        }
        if (bindingTrace == null) {
            $$$reportNull$$$0(5);
        }
        if (function0 == null) {
            $$$reportNull$$$0(6);
        }
        DeferredType deferredType = new DeferredType(storageManager.createLazyValue(function0, RECURSION_PREVENTER));
        bindingTrace.record(BindingContext.DEFERRED_TYPE, new Box(deferredType));
        return deferredType;
    }

    private DeferredType(NotNullLazyValue<KotlinType> notNullLazyValue) {
        if (notNullLazyValue == null) {
            $$$reportNull$$$0(8);
        }
        this.lazyValue = notNullLazyValue;
    }

    @Override // org.jetbrains.kotlin.types.KotlinType
    public KotlinType refine(final KotlinTypeRefiner kotlinTypeRefiner) {
        if (kotlinTypeRefiner == null) {
            $$$reportNull$$$0(9);
        }
        return new DeferredType(new NotNullLazyValue<KotlinType>() { // from class: org.jetbrains.kotlin.types.DeferredType.1
            private static /* synthetic */ void $$$reportNull$$$0(int i) {
                throw new IllegalStateException(String.format("@NotNull method %s.%s must not return null", "org/jetbrains/kotlin/types/DeferredType$1", "renderDebugInformation"));
            }

            @Override // org.jetbrains.kotlin.storage.NotNullLazyValue
            public String renderDebugInformation() {
                String strRenderDebugInformation = DeferredType.this.lazyValue.renderDebugInformation();
                if (strRenderDebugInformation == null) {
                    $$$reportNull$$$0(0);
                }
                return strRenderDebugInformation;
            }

            @Override // org.jetbrains.kotlin.storage.NotNullLazyValue
            public boolean isComputed() {
                return DeferredType.this.lazyValue.isComputed();
            }

            @Override // org.jetbrains.kotlin.storage.NotNullLazyValue
            public boolean isComputing() {
                return DeferredType.this.lazyValue.isComputing();
            }

            @Override // kotlin.jvm.functions.Function0
            public KotlinType invoke() {
                return kotlinTypeRefiner.refineType((KotlinTypeMarker) DeferredType.this.lazyValue.invoke());
            }
        });
    }

    public boolean isComputing() {
        return this.lazyValue.isComputing();
    }

    @Override // org.jetbrains.kotlin.types.WrappedType
    public boolean isComputed() {
        return this.lazyValue.isComputed();
    }

    @Override // org.jetbrains.kotlin.types.WrappedType
    public KotlinType getDelegate() {
        KotlinType kotlinTypeInvoke = this.lazyValue.invoke();
        if (kotlinTypeInvoke == null) {
            $$$reportNull$$$0(10);
        }
        return kotlinTypeInvoke;
    }

    @Override // org.jetbrains.kotlin.types.WrappedType
    public String toString() {
        try {
            if (this.lazyValue.isComputed()) {
                String string = getDelegate().toString();
                if (string == null) {
                    $$$reportNull$$$0(11);
                }
                return string;
            }
            return "<Not computed yet>";
        } catch (ReenteringLazyValueComputationException unused) {
            return "<Failed to compute this type>";
        }
    }
}

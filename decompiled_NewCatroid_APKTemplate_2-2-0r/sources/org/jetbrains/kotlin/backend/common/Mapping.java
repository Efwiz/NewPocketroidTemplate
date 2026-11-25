package org.jetbrains.kotlin.backend.common;

import androidx.exifinterface.media.ExifInterface;
import com.google.firebase.crashlytics.internal.metadata.UserMetadata;
import java.util.Collection;
import java.util.Set;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KProperty;
import org.antlr.stringtemplate.language.ASTExpr;
import org.apache.commons.beanutils.FluentPropertyBeanIntrospector;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrField;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;

/* JADX INFO: compiled from: Mappings.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\r\bf\u0018\u00002\u00020\u0001:\u0001\u001eR\u001e\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00040\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006R$\u0010\u0007\u001a\u0014\u0012\u0004\u0012\u00020\b\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\t0\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\u0006R\u001e\u0010\f\u001a\u000e\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\r0\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u0006R\u001e\u0010\u000f\u001a\u000e\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\r0\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u0006R\u001e\u0010\u0011\u001a\u000e\u0012\u0004\u0012\u00020\u0012\u0012\u0004\u0012\u00020\u00120\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0013\u0010\u0006R\u001e\u0010\u0014\u001a\u000e\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\u00120\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0015\u0010\u0006R\u001e\u0010\u0016\u001a\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\n0\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0017\u0010\u0006R\u001e\u0010\u0018\u001a\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\u00120\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0019\u0010\u0006R\u001e\u0010\u001a\u001a\u000e\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\u00040\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u001b\u0010\u0006R\u001e\u0010\u001c\u001a\u000e\u0012\u0004\u0012\u00020\u0012\u0012\u0004\u0012\u00020\u00120\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u001d\u0010\u0006ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u001fÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/Mapping;", "", "capturedConstructors", "Lorg/jetbrains/kotlin/backend/common/Mapping$Delegate;", "Lorg/jetbrains/kotlin/ir/declarations/IrConstructor;", "getCapturedConstructors", "()Lorg/jetbrains/kotlin/backend/common/Mapping$Delegate;", "capturedFields", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "", "Lorg/jetbrains/kotlin/ir/declarations/IrField;", "getCapturedFields", "defaultArgumentsDispatchFunction", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "getDefaultArgumentsDispatchFunction", "defaultArgumentsOriginalFunction", "getDefaultArgumentsOriginalFunction", "functionWithContinuationsToSuspendFunctions", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "getFunctionWithContinuationsToSuspendFunctions", "inlineClassMemberToStatic", "getInlineClassMemberToStatic", "lateInitFieldToNullableField", "getLateInitFieldToNullableField", "reflectedNameAccessor", "getReflectedNameAccessor", "suspendFunctionToCoroutineConstructor", "getSuspendFunctionToCoroutineConstructor", "suspendFunctionsToFunctionWithContinuations", "getSuspendFunctionsToFunctionWithContinuations", "Delegate", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface Mapping {
    Delegate<IrConstructor, IrConstructor> getCapturedConstructors();

    Delegate<IrClass, Collection<IrField>> getCapturedFields();

    Delegate<IrFunction, IrFunction> getDefaultArgumentsDispatchFunction();

    Delegate<IrFunction, IrFunction> getDefaultArgumentsOriginalFunction();

    Delegate<IrSimpleFunction, IrSimpleFunction> getFunctionWithContinuationsToSuspendFunctions();

    Delegate<IrFunction, IrSimpleFunction> getInlineClassMemberToStatic();

    Delegate<IrField, IrField> getLateInitFieldToNullableField();

    Delegate<IrClass, IrSimpleFunction> getReflectedNameAccessor();

    Delegate<IrFunction, IrConstructor> getSuspendFunctionToCoroutineConstructor();

    Delegate<IrSimpleFunction, IrSimpleFunction> getSuspendFunctionsToFunctionWithContinuations();

    /* JADX INFO: compiled from: Mappings.kt */
    @Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0005\b&\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u0002*\u0004\b\u0001\u0010\u00032\u00020\u0004B\u0005¢\u0006\u0002\u0010\u0005J\u0018\u0010\n\u001a\u0004\u0018\u00018\u00012\u0006\u0010\u000b\u001a\u00028\u0000H¦\u0002¢\u0006\u0002\u0010\fJ$\u0010\r\u001a\u0004\u0018\u00018\u00012\u0006\u0010\u000e\u001a\u00028\u00002\n\u0010\u000f\u001a\u0006\u0012\u0002\b\u00030\u0010H\u0086\u0002¢\u0006\u0002\u0010\u0011J \u0010\u0012\u001a\u00020\u00132\u0006\u0010\u000b\u001a\u00028\u00002\b\u0010\u0014\u001a\u0004\u0018\u00018\u0001H¦\u0002¢\u0006\u0002\u0010\u0015J,\u0010\u0016\u001a\u00020\u00132\u0006\u0010\u000e\u001a\u00028\u00002\n\u0010\u000f\u001a\u0006\u0012\u0002\b\u00030\u00102\b\u0010\u0014\u001a\u0004\u0018\u00018\u0001H\u0086\u0002¢\u0006\u0002\u0010\u0017R\u0018\u0010\u0006\u001a\b\u0012\u0004\u0012\u00028\u00000\u0007X¦\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\t¨\u0006\u0018"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/Mapping$Delegate;", "K", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", ExifInterface.GPS_MEASUREMENT_INTERRUPTED, "", "()V", UserMetadata.KEYDATA_FILENAME, "", "getKeys", "()Ljava/util/Set;", "get", ASTExpr.DEFAULT_MAP_KEY_NAME, "(Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;)Ljava/lang/Object;", "getValue", "thisRef", "desc", "Lkotlin/reflect/KProperty;", "(Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;Lkotlin/reflect/KProperty;)Ljava/lang/Object;", FluentPropertyBeanIntrospector.DEFAULT_WRITE_METHOD_PREFIX, "", "value", "(Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;Ljava/lang/Object;)V", "setValue", "(Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;Lkotlin/reflect/KProperty;Ljava/lang/Object;)V", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static abstract class Delegate<K extends IrDeclaration, V> {
        public abstract V get(K key);

        public abstract Set<K> getKeys();

        public abstract void set(K key, V value);

        public final V getValue(K thisRef, KProperty<?> desc) {
            Intrinsics.checkNotNullParameter(thisRef, "thisRef");
            Intrinsics.checkNotNullParameter(desc, "desc");
            return get(thisRef);
        }

        public final void setValue(K thisRef, KProperty<?> desc, V value) {
            Intrinsics.checkNotNullParameter(thisRef, "thisRef");
            Intrinsics.checkNotNullParameter(desc, "desc");
            set(thisRef, value);
        }
    }
}

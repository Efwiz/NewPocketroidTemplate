package org.jetbrains.kotlin.codegen.intrinsics;

import com.reandroid.dex.sections.Marker;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.StandardNames;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.codegen.AsmUtil;
import org.jetbrains.kotlin.codegen.Callable;
import org.jetbrains.kotlin.codegen.CallableMethod;
import org.jetbrains.kotlin.fileClasses.JvmFileClassUtilKt;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.resolve.jvm.AsmTypes;
import org.jetbrains.kotlin.resolve.jvm.JvmPrimitiveType;
import org.jetbrains.org.objectweb.asm.Type;
import org.jetbrains.org.objectweb.asm.commons.InstructionAdapter;
import org.mozilla.javascript.ES6Iterator;

/* JADX INFO: compiled from: IteratorNext.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 \u00072\u00020\u0001:\u0001\u0007B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0014¨\u0006\b"}, d2 = {"Lorg/jetbrains/kotlin/codegen/intrinsics/IteratorNext;", "Lorg/jetbrains/kotlin/codegen/intrinsics/IntrinsicMethod;", "()V", "toCallable", "Lorg/jetbrains/kotlin/codegen/Callable;", "method", "Lorg/jetbrains/kotlin/codegen/CallableMethod;", "Companion", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class IteratorNext extends IntrinsicMethod {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);

    @Override // org.jetbrains.kotlin.codegen.intrinsics.IntrinsicMethod
    protected Callable toCallable(CallableMethod method) {
        Intrinsics.checkNotNullParameter(method, "method");
        Type typeUnboxType = AsmUtil.unboxType(method.getReturnType());
        Intrinsics.checkNotNullExpressionValue(typeUnboxType, "unboxType(...)");
        return new IntrinsicCallable(typeUnboxType, CollectionsKt.emptyList(), AsmTypes.OBJECT_TYPE) { // from class: org.jetbrains.kotlin.codegen.intrinsics.IteratorNext.toCallable.1
            @Override // org.jetbrains.kotlin.codegen.intrinsics.IntrinsicCallable
            public void invokeIntrinsic(InstructionAdapter v) {
                Intrinsics.checkNotNullParameter(v, "v");
                Name kotlinPrimitiveClassName = IteratorNext.INSTANCE.getKotlinPrimitiveClassName(getReturnType());
                v.invokevirtual(IteratorNext.INSTANCE.getPrimitiveIteratorType(kotlinPrimitiveClassName).getInternalName(), ES6Iterator.NEXT_METHOD + kotlinPrimitiveClassName.asString(), "()" + getReturnType().getDescriptor(), false);
            }
        };
    }

    /* JADX INFO: compiled from: IteratorNext.kt */
    @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0002J\u000e\u0010\u0007\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\u0004¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/codegen/intrinsics/IteratorNext$Companion;", "", "()V", "getKotlinPrimitiveClassName", "Lorg/jetbrains/kotlin/name/Name;", ModuleXmlParser.TYPE, "Lorg/jetbrains/org/objectweb/asm/Type;", "getPrimitiveIteratorType", "primitiveClassName", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final Name getKotlinPrimitiveClassName(Type type) {
            return JvmPrimitiveType.get(type.getClassName()).getPrimitiveType().getTypeName();
        }

        public final Type getPrimitiveIteratorType(Name primitiveClassName) {
            Intrinsics.checkNotNullParameter(primitiveClassName, "primitiveClassName");
            Name nameIdentifier = Name.identifier(primitiveClassName.asString() + "Iterator");
            Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
            FqName fqNameChild = StandardNames.COLLECTIONS_PACKAGE_FQ_NAME.child(nameIdentifier);
            Intrinsics.checkNotNullExpressionValue(fqNameChild, "child(...)");
            Type objectType = Type.getObjectType(JvmFileClassUtilKt.getInternalNameWithoutInnerClasses(fqNameChild));
            Intrinsics.checkNotNullExpressionValue(objectType, "getObjectType(...)");
            return objectType;
        }
    }
}

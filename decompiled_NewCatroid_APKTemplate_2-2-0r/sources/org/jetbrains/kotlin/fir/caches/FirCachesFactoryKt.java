package org.jetbrains.kotlin.fir.caches;

import androidx.exifinterface.media.ExifInterface;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KProperty;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.util.AbstractArrayMapOwner;
import org.jetbrains.kotlin.util.ArrayMapAccessor;
import org.jetbrains.kotlin.util.TypeRegistry;

/* JADX INFO: compiled from: FirCachesFactory.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000&\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0001\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\u001aJ\u0010\u0007\u001a\u0016\u0012\u0004\u0012\u0002H\t\u0012\u0004\u0012\u0002H\n\u0012\u0006\u0012\u0004\u0018\u00010\u000b0\b\"\b\b\u0000\u0010\t*\u00020\f\"\u0004\b\u0001\u0010\n*\u00020\u00012\u0014\b\u0004\u0010\r\u001a\u000e\u0012\u0004\u0012\u0002H\t\u0012\u0004\u0012\u0002H\n0\u000eH\u0086\bø\u0001\u0000\"\u001f\u0010\u0000\u001a\u00020\u0001*\u00020\u00028FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u0005\u0010\u0006\u001a\u0004\b\u0003\u0010\u0004\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006\u000f"}, d2 = {"firCachesFactory", "Lorg/jetbrains/kotlin/fir/caches/FirCachesFactory;", "Lorg/jetbrains/kotlin/fir/FirSession;", "getFirCachesFactory", "(Lorg/jetbrains/kotlin/fir/FirSession;)Lorg/jetbrains/kotlin/fir/caches/FirCachesFactory;", "firCachesFactory$delegate", "Lorg/jetbrains/kotlin/util/ArrayMapAccessor;", "createCache", "Lorg/jetbrains/kotlin/fir/caches/FirCache;", "K", ExifInterface.GPS_MEASUREMENT_INTERRUPTED, "", "", "createValue", "Lkotlin/Function1;", Constants.TYPE_TREE}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class FirCachesFactoryKt {
    static final /* synthetic */ KProperty<Object>[] $$delegatedProperties = {Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinPackage(FirCachesFactoryKt.class, Constants.TYPE_TREE), "firCachesFactory", "getFirCachesFactory(Lorg/jetbrains/kotlin/fir/FirSession;)Lorg/jetbrains/kotlin/fir/caches/FirCachesFactory;"))};
    private static final ArrayMapAccessor firCachesFactory$delegate = TypeRegistry.generateAccessor$default(FirSession.INSTANCE, Reflection.getOrCreateKotlinClass(FirCachesFactory.class), (Object) null, 2, (Object) null);

    public static final FirCachesFactory getFirCachesFactory(FirSession firSession) {
        Intrinsics.checkNotNullParameter(firSession, "<this>");
        return (FirCachesFactory) firCachesFactory$delegate.getValue((AbstractArrayMapOwner) firSession, $$delegatedProperties[0]);
    }

    public static final <K, V> FirCache createCache(FirCachesFactory firCachesFactory, final Function1<? super K, ? extends V> createValue) {
        Intrinsics.checkNotNullParameter(firCachesFactory, "<this>");
        Intrinsics.checkNotNullParameter(createValue, "createValue");
        return firCachesFactory.createCache(new Function2() { // from class: org.jetbrains.kotlin.fir.caches.FirCachesFactoryKt.createCache.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            {
                super(2);
            }

            /* JADX WARN: Type inference failed for: r1v1, types: [V, java.lang.Object] */
            /* JADX WARN: Type inference fix 'apply assigned field type' failed
            java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$UnknownArg
            	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:596)
            	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
            	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
            	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
            	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
            	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
            	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
             */
            @Override // kotlin.jvm.functions.Function2
            public final V invoke(K key, Void r2) {
                Intrinsics.checkNotNullParameter(key, "key");
                return createValue.invoke(key);
            }
        });
    }
}

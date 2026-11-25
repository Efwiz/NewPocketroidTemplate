package org.jetbrains.kotlin.fir.resolve.providers.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.resolve.FirQualifierResolver;
import org.jetbrains.kotlin.fir.resolve.providers.FirSymbolProvider;
import org.jetbrains.kotlin.fir.resolve.providers.FirSymbolProviderKt;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassLikeSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassifierSymbol;
import org.jetbrains.kotlin.fir.types.FirQualifierPart;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: FirQualifierResolverImpl.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u001c\u0010\u0007\u001a\b\u0012\u0002\b\u0003\u0018\u00010\b2\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\nH\u0016J$\u0010\f\u001a\b\u0012\u0002\b\u0003\u0018\u00010\b2\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\n2\u0006\u0010\r\u001a\u00020\u000eH\u0016J\u001a\u0010\u000f\u001a\n \u0011*\u0004\u0018\u00010\u00100\u0010*\b\u0012\u0004\u0012\u00020\u000b0\nH\u0002R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/providers/impl/FirQualifierResolverImpl;", "Lorg/jetbrains/kotlin/fir/resolve/FirQualifierResolver;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "(Lorg/jetbrains/kotlin/fir/FirSession;)V", "getSession", "()Lorg/jetbrains/kotlin/fir/FirSession;", "resolveSymbol", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassifierSymbol;", "parts", "", "Lorg/jetbrains/kotlin/fir/types/FirQualifierPart;", "resolveSymbolWithPrefix", "prefix", "Lorg/jetbrains/kotlin/name/ClassId;", "toFqName", "Lorg/jetbrains/kotlin/name/FqName;", JvmProtoBufUtil.PLATFORM_TYPE_ID, "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirQualifierResolverImpl extends FirQualifierResolver {
    private final FirSession session;

    public FirQualifierResolverImpl(FirSession session) {
        Intrinsics.checkNotNullParameter(session, "session");
        this.session = session;
    }

    public final FirSession getSession() {
        return this.session;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.FirQualifierResolver
    public FirClassifierSymbol<?> resolveSymbolWithPrefix(List<? extends FirQualifierPart> parts, ClassId prefix) {
        Intrinsics.checkNotNullParameter(parts, "parts");
        Intrinsics.checkNotNullParameter(prefix, "prefix");
        FirSymbolProvider symbolProvider = FirSymbolProviderKt.getSymbolProvider(this.session);
        FqName packageFqName = prefix.getPackageFqName();
        List listDrop = CollectionsKt.drop(parts, 1);
        FqName relativeClassName = prefix.getRelativeClassName();
        Iterator it = listDrop.iterator();
        while (it.hasNext()) {
            relativeClassName = relativeClassName.child(((FirQualifierPart) it.next()).getName());
        }
        return symbolProvider.getClassLikeSymbolByClassId(new ClassId(packageFqName, relativeClassName, false));
    }

    @Override // org.jetbrains.kotlin.fir.resolve.FirQualifierResolver
    public FirClassifierSymbol<?> resolveSymbol(List<? extends FirQualifierPart> parts) {
        Name name2;
        Intrinsics.checkNotNullParameter(parts, "parts");
        FirQualifierPart firQualifierPart = (FirQualifierPart) CollectionsKt.firstOrNull((List) parts);
        if (Intrinsics.areEqual((firQualifierPart == null || (name2 = firQualifierPart.getName()) == null) ? null : name2.asString(), "_root_ide_package_")) {
            return resolveSymbol(CollectionsKt.drop(parts, 1));
        }
        FirSymbolProvider symbolProvider = FirSymbolProviderKt.getSymbolProvider(this.session);
        List<? extends FirQualifierPart> list = parts;
        if (!list.isEmpty()) {
            ArrayList arrayList = new ArrayList();
            List<? extends FirQualifierPart> mutableList = CollectionsKt.toMutableList((Collection) list);
            while (!mutableList.isEmpty()) {
                arrayList.add(0, CollectionsKt.last((List) mutableList));
                mutableList.remove(CollectionsKt.getLastIndex(mutableList));
                FirClassLikeSymbol<?> classLikeSymbolByClassId = symbolProvider.getClassLikeSymbolByClassId(new ClassId(toFqName(mutableList), toFqName(arrayList), false));
                if (classLikeSymbolByClassId != null) {
                    return classLikeSymbolByClassId;
                }
            }
        }
        return null;
    }

    private final FqName toFqName(List<? extends FirQualifierPart> list) {
        FqName fqNameChild = FqName.ROOT;
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            fqNameChild = fqNameChild.child(((FirQualifierPart) it.next()).getName());
        }
        return fqNameChild;
    }
}

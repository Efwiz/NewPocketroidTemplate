package org.jetbrains.kotlin.fir.serialization;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.UtilsKt;
import org.jetbrains.kotlin.fir.declarations.FirClass;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirFile;
import org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirProperty;
import org.jetbrains.kotlin.fir.declarations.FirSimpleFunction;
import org.jetbrains.kotlin.fir.declarations.FirTypeAlias;
import org.jetbrains.kotlin.fir.resolve.ScopeSession;
import org.jetbrains.kotlin.fir.serialization.constant.ConstValueProvider;
import org.jetbrains.kotlin.fir.symbols.FirBasedSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassifierSymbol;
import org.jetbrains.kotlin.library.metadata.KlibMetadataSerializationUtilsKt;
import org.jetbrains.kotlin.metadata.ProtoBuf;
import org.jetbrains.kotlin.name.FqName;

/* JADX INFO: compiled from: firKlibSerialization.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a>\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u000e\u0010\b\u001a\n\u0012\u0004\u0012\u00020\n\u0018\u00010\t2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e¨\u0006\u000f"}, d2 = {"serializeSingleFirFile", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$PackageFragment;", "file", "Lorg/jetbrains/kotlin/fir/declarations/FirFile;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "scopeSession", "Lorg/jetbrains/kotlin/fir/resolve/ScopeSession;", "actualizedExpectDeclarations", "", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;", "serializerExtension", "Lorg/jetbrains/kotlin/fir/serialization/FirKLibSerializerExtension;", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "fir-serialization"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class FirKlibSerializationKt {
    public static final ProtoBuf.PackageFragment serializeSingleFirFile(FirFile file, FirSession session, ScopeSession scopeSession, Set<? extends FirDeclaration> set, FirKLibSerializerExtension serializerExtension, LanguageVersionSettings languageVersionSettings) throws Throwable {
        FirFile firFile;
        boolean z;
        Intrinsics.checkNotNullParameter(file, "file");
        Intrinsics.checkNotNullParameter(session, "session");
        Intrinsics.checkNotNullParameter(scopeSession, "scopeSession");
        Intrinsics.checkNotNullParameter(serializerExtension, "serializerExtension");
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        TypeApproximatorForMetadataSerializer typeApproximatorForMetadataSerializer = new TypeApproximatorForMetadataSerializer(session);
        FirKLibSerializerExtension firKLibSerializerExtension = serializerExtension;
        ProtoBuf.Package packageBuild = FirElementSerializer.INSTANCE.createTopLevel(session, scopeSession, firKLibSerializerExtension, typeApproximatorForMetadataSerializer, languageVersionSettings).packagePartProto(UtilsKt.getPackageFqName(file), CollectionsKt.listOf(file), set).build();
        ArrayList arrayList = new ArrayList();
        ConstValueProvider constValueProvider = firKLibSerializerExtension.getConstValueProvider();
        FirFile processingFirFile = constValueProvider != null ? constValueProvider.getProcessingFirFile() : null;
        ConstValueProvider constValueProvider2 = firKLibSerializerExtension.getConstValueProvider();
        if (constValueProvider2 != null) {
            constValueProvider2.setProcessingFirFile(file);
        }
        try {
            List<FirDeclaration> declarations = file.getDeclarations();
            ArrayList arrayList2 = new ArrayList();
            Iterator<T> it = declarations.iterator();
            while (it.hasNext()) {
                FirBasedSymbol<? extends FirDeclaration> symbol = ((FirDeclaration) it.next()).getSymbol();
                FirClassSymbol firClassSymbol = symbol instanceof FirClassSymbol ? (FirClassSymbol) symbol : null;
                if (firClassSymbol != null) {
                    arrayList2.add(firClassSymbol);
                }
            }
            firFile = processingFirFile;
            try {
                serializeSingleFirFile$makeClassesProtoWithNested(arrayList2, session, scopeSession, serializerExtension, typeApproximatorForMetadataSerializer, languageVersionSettings, arrayList, set);
                Unit unit = Unit.INSTANCE;
                ConstValueProvider constValueProvider3 = firKLibSerializerExtension.getConstValueProvider();
                if (constValueProvider3 != null) {
                    constValueProvider3.setProcessingFirFile(firFile);
                }
                List<FirDeclaration> declarations2 = file.getDeclarations();
                if ((declarations2 instanceof Collection) && declarations2.isEmpty()) {
                    z = false;
                } else {
                    Iterator<T> it2 = declarations2.iterator();
                    while (true) {
                        if (it2.hasNext()) {
                            FirDeclaration firDeclaration = (FirDeclaration) it2.next();
                            if ((firDeclaration instanceof FirMemberDeclaration) && SerializationUtilKt.shouldBeSerialized((FirMemberDeclaration) firDeclaration, set) && ((firDeclaration instanceof FirProperty) || (firDeclaration instanceof FirSimpleFunction) || (firDeclaration instanceof FirTypeAlias))) {
                                z = true;
                            }
                        } else {
                            z = false;
                        }
                    }
                }
                Intrinsics.checkNotNull(packageBuild);
                FqName packageFqName = UtilsKt.getPackageFqName(file);
                boolean z2 = z && arrayList.isEmpty();
                FirElementAwareSerializableStringTable stringTable = serializerExtension.getStringTable();
                Intrinsics.checkNotNull(stringTable, "null cannot be cast to non-null type org.jetbrains.kotlin.serialization.SerializableStringTable");
                return KlibMetadataSerializationUtilsKt.buildKlibPackageFragment(packageBuild, arrayList, packageFqName, z2, stringTable);
            } catch (Throwable th) {
                th = th;
                ConstValueProvider constValueProvider4 = firKLibSerializerExtension.getConstValueProvider();
                if (constValueProvider4 != null) {
                    constValueProvider4.setProcessingFirFile(firFile);
                }
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
            firFile = processingFirFile;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public static final void serializeSingleFirFile$makeClassesProtoWithNested(List<? extends FirClassSymbol<?>> list, FirSession firSession, ScopeSession scopeSession, FirKLibSerializerExtension firKLibSerializerExtension, TypeApproximatorForMetadataSerializer typeApproximatorForMetadataSerializer, LanguageVersionSettings languageVersionSettings, List<Pair<ProtoBuf.Class, Integer>> list2, Set<? extends FirDeclaration> set) {
        ArrayList arrayList = new ArrayList();
        for (Object obj : list) {
            if (SerializationUtilKt.shouldBeSerialized((FirMemberDeclaration) ((FirClassSymbol) obj).getFir(), set)) {
                arrayList.add(obj);
            }
        }
        for (FirClassSymbol<?> firClassSymbol : CollectionsKt.sortedWith(arrayList, new Comparator() { // from class: org.jetbrains.kotlin.fir.serialization.FirKlibSerializationKt$serializeSingleFirFile$makeClassesProtoWithNested$$inlined$sortedBy$1
            /* JADX WARN: Multi-variable type inference failed */
            @Override // java.util.Comparator
            public final int compare(T t, T t2) {
                return ComparisonsKt.compareValues(((FirClassSymbol) t).getClassId().asFqNameString(), ((FirClassSymbol) t2).getClassId().asFqNameString());
            }
        })) {
            FirClass firClass = (FirClass) firClassSymbol.getFir();
            FirElementSerializer firElementSerializerCreate = FirElementSerializer.INSTANCE.create(firSession, scopeSession, firClass, firKLibSerializerExtension, null, typeApproximatorForMetadataSerializer, languageVersionSettings);
            list2.add(TuplesKt.to(firElementSerializerCreate.classProto(firClass).build(), Integer.valueOf(firElementSerializerCreate.getStringTable().getFqNameIndex(firClass))));
            List<FirClassifierSymbol<?>> listComputeNestedClassifiersForClass = firElementSerializerCreate.computeNestedClassifiersForClass(firClassSymbol);
            ArrayList arrayList2 = new ArrayList();
            for (Object obj2 : listComputeNestedClassifiersForClass) {
                if (obj2 instanceof FirClassSymbol) {
                    arrayList2.add(obj2);
                }
            }
            serializeSingleFirFile$makeClassesProtoWithNested(arrayList2, firSession, scopeSession, firKLibSerializerExtension, typeApproximatorForMetadataSerializer, languageVersionSettings, list2, set);
        }
    }
}

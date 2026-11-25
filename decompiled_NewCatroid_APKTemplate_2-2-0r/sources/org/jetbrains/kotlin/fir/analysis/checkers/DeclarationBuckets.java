package org.jetbrains.kotlin.fir.analysis.checkers;

import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import org.jetbrains.kotlin.fir.declarations.FirClassLikeDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirConstructor;
import org.jetbrains.kotlin.fir.declarations.FirProperty;
import org.jetbrains.kotlin.fir.declarations.FirSimpleFunction;

/* JADX INFO: compiled from: FirConflictsHelpers.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002R#\u0010\u0003\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00070\u00050\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR#\u0010\n\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\u00070\u00050\u0004¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\tR#\u0010\r\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u00070\u00050\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\tR#\u0010\u0010\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u00070\u00050\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\tR#\u0010\u0012\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0013\u0012\u0004\u0012\u00020\u00070\u00050\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\t¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/DeclarationBuckets;", "", "()V", "classLikes", "", "Lkotlin/Pair;", "Lorg/jetbrains/kotlin/fir/declarations/FirClassLikeDeclaration;", "", "getClassLikes", "()Ljava/util/List;", "constructors", "Lorg/jetbrains/kotlin/fir/declarations/FirConstructor;", "getConstructors", "extensionProperties", "Lorg/jetbrains/kotlin/fir/declarations/FirProperty;", "getExtensionProperties", "properties", "getProperties", "simpleFunctions", "Lorg/jetbrains/kotlin/fir/declarations/FirSimpleFunction;", "getSimpleFunctions", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class DeclarationBuckets {
    private final List<Pair<FirSimpleFunction, String>> simpleFunctions = new ArrayList();
    private final List<Pair<FirConstructor, String>> constructors = new ArrayList();
    private final List<Pair<FirClassLikeDeclaration, String>> classLikes = new ArrayList();
    private final List<Pair<FirProperty, String>> properties = new ArrayList();
    private final List<Pair<FirProperty, String>> extensionProperties = new ArrayList();

    public final List<Pair<FirSimpleFunction, String>> getSimpleFunctions() {
        return this.simpleFunctions;
    }

    public final List<Pair<FirConstructor, String>> getConstructors() {
        return this.constructors;
    }

    public final List<Pair<FirClassLikeDeclaration, String>> getClassLikes() {
        return this.classLikes;
    }

    public final List<Pair<FirProperty, String>> getProperties() {
        return this.properties;
    }

    public final List<Pair<FirProperty, String>> getExtensionProperties() {
        return this.extensionProperties;
    }
}

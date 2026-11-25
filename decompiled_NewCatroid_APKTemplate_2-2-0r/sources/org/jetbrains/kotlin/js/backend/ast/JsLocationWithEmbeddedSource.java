package org.jetbrains.kotlin.js.backend.ast;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.io.Reader;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: JsLocation.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\t\n\u0002\u0010\b\n\u0002\b\u0006\u0018\u00002\u00020\u0001B'\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\u000e\u0010\u0006\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\b0\u0007¢\u0006\u0002\u0010\tJ\t\u0010\u001a\u001a\u00020\u0003H\u0096\u0001R\u0012\u0010\n\u001a\u00020\u000bX\u0096\u0005¢\u0006\u0006\u001a\u0004\b\f\u0010\rR\u0016\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0010\u001a\u0004\u0018\u00010\u000bX\u0096\u0005¢\u0006\u0006\u001a\u0004\b\u0011\u0010\rR\u001c\u0010\u0006\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\b0\u0007X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u0012\u0010\u0014\u001a\u00020\u0015X\u0096\u0005¢\u0006\u0006\u001a\u0004\b\u0016\u0010\u0017R\u0012\u0010\u0018\u001a\u00020\u0015X\u0096\u0005¢\u0006\u0006\u001a\u0004\b\u0019\u0010\u0017¨\u0006\u001b"}, d2 = {"Lorg/jetbrains/kotlin/js/backend/ast/JsLocationWithEmbeddedSource;", "Lorg/jetbrains/kotlin/js/backend/ast/JsLocationWithSource;", FirebaseAnalytics.Param.LOCATION, "Lorg/jetbrains/kotlin/js/backend/ast/JsLocation;", "fileIdentity", "", "sourceProvider", "Lkotlin/Function0;", "Ljava/io/Reader;", "(Lorg/jetbrains/kotlin/js/backend/ast/JsLocation;Ljava/lang/Object;Lkotlin/jvm/functions/Function0;)V", "file", "", "getFile", "()Ljava/lang/String;", "getFileIdentity", "()Ljava/lang/Object;", "name", "getName", "getSourceProvider", "()Lkotlin/jvm/functions/Function0;", "startChar", "", "getStartChar", "()I", "startLine", "getStartLine", "asSimpleLocation", "js.ast"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JsLocationWithEmbeddedSource implements JsLocationWithSource {
    private final Object fileIdentity;
    private final JsLocation location;
    private final Function0<Reader> sourceProvider;

    @Override // org.jetbrains.kotlin.js.backend.ast.JsLocationWithSource
    public JsLocation asSimpleLocation() {
        return this.location.asSimpleLocation();
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsLocationWithSource
    public String getFile() {
        return this.location.getFile();
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsLocationWithSource
    public String getName() {
        return this.location.getName();
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsLocationWithSource
    public int getStartChar() {
        return this.location.getStartChar();
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsLocationWithSource
    public int getStartLine() {
        return this.location.getStartLine();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public JsLocationWithEmbeddedSource(JsLocation location, Object obj, Function0<? extends Reader> sourceProvider) {
        Intrinsics.checkNotNullParameter(location, "location");
        Intrinsics.checkNotNullParameter(sourceProvider, "sourceProvider");
        this.location = location;
        this.fileIdentity = obj;
        this.sourceProvider = sourceProvider;
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsLocationWithSource
    public Object getFileIdentity() {
        return this.fileIdentity;
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsLocationWithSource
    public Function0<Reader> getSourceProvider() {
        return this.sourceProvider;
    }
}

package org.jetbrains.kotlin.js.backend.ast;

import java.io.Reader;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;

/* JADX INFO: compiled from: JsLocation.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\b\u0010\u0016\u001a\u00020\u0017H&R\u0012\u0010\u0002\u001a\u00020\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005R\u0014\u0010\u0006\u001a\u0004\u0018\u00010\u0001X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u0014\u0010\t\u001a\u0004\u0018\u00010\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\u0005R\u001a\u0010\u000b\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\r0\fX¦\u0004¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u000fR\u0012\u0010\u0010\u001a\u00020\u0011X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0012\u0010\u0013R\u0012\u0010\u0014\u001a\u00020\u0011X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0015\u0010\u0013ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0018À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/js/backend/ast/JsLocationWithSource;", "", "file", "", "getFile", "()Ljava/lang/String;", "fileIdentity", "getFileIdentity", "()Ljava/lang/Object;", "name", "getName", "sourceProvider", "Lkotlin/Function0;", "Ljava/io/Reader;", "getSourceProvider", "()Lkotlin/jvm/functions/Function0;", "startChar", "", "getStartChar", "()I", "startLine", "getStartLine", "asSimpleLocation", "Lorg/jetbrains/kotlin/js/backend/ast/JsLocation;", "js.ast"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface JsLocationWithSource {
    JsLocation asSimpleLocation();

    String getFile();

    Object getFileIdentity();

    String getName();

    Function0<Reader> getSourceProvider();

    int getStartChar();

    int getStartLine();
}

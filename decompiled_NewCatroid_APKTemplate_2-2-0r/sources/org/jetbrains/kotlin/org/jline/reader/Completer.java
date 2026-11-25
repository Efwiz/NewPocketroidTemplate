package org.jetbrains.kotlin.org.jline.reader;

import java.util.List;

/* JADX INFO: loaded from: classes4.dex */
public interface Completer {
    void complete(LineReader lineReader, ParsedLine parsedLine, List<Candidate> list);
}

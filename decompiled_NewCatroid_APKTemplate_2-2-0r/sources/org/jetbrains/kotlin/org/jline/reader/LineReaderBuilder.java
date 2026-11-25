package org.jetbrains.kotlin.org.jline.reader;

import java.io.IOError;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.jetbrains.kotlin.org.jline.reader.impl.LineReaderImpl;
import org.jetbrains.kotlin.org.jline.reader.impl.history.DefaultHistory;
import org.jetbrains.kotlin.org.jline.terminal.Terminal;
import org.jetbrains.kotlin.org.jline.terminal.TerminalBuilder;

/* JADX INFO: loaded from: classes4.dex */
public final class LineReaderBuilder {
    String appName;
    Completer completer;
    Expander expander;
    Highlighter highlighter;
    History history;
    History memoryHistory;
    Parser parser;
    Terminal terminal;
    Map<String, Object> variables;

    public static LineReaderBuilder builder() {
        return new LineReaderBuilder();
    }

    private LineReaderBuilder() {
    }

    public LineReaderBuilder terminal(Terminal terminal) {
        this.terminal = terminal;
        return this;
    }

    public LineReaderBuilder appName(String str) {
        this.appName = str;
        return this;
    }

    public LineReaderBuilder variables(Map<String, Object> map) {
        Map<String, Object> map2 = this.variables;
        this.variables = map;
        if (map2 != null) {
            map.putAll(map2);
        }
        return this;
    }

    public LineReaderBuilder variable(String str, Object obj) {
        if (this.variables == null) {
            this.variables = new HashMap();
        }
        this.variables.put(str, obj);
        return this;
    }

    public LineReaderBuilder history(History history) {
        this.history = history;
        return this;
    }

    public LineReaderBuilder completer(Completer completer) {
        this.completer = completer;
        return this;
    }

    public LineReaderBuilder highlighter(Highlighter highlighter) {
        this.highlighter = highlighter;
        return this;
    }

    public LineReaderBuilder parser(Parser parser) {
        this.parser = parser;
        return this;
    }

    public LineReaderBuilder expander(Expander expander) {
        this.expander = expander;
        return this;
    }

    public LineReader build() {
        Terminal terminal = this.terminal;
        if (terminal == null) {
            try {
                terminal = TerminalBuilder.terminal();
            } catch (IOException e) {
                throw new IOError(e);
            }
        }
        LineReaderImpl lineReaderImpl = new LineReaderImpl(terminal, this.appName, this.variables);
        History history = this.history;
        if (history != null) {
            lineReaderImpl.setHistory(history);
        } else {
            if (this.memoryHistory == null) {
                this.memoryHistory = new DefaultHistory();
            }
            lineReaderImpl.setHistory(this.memoryHistory);
        }
        Completer completer = this.completer;
        if (completer != null) {
            lineReaderImpl.setCompleter(completer);
        }
        Highlighter highlighter = this.highlighter;
        if (highlighter != null) {
            lineReaderImpl.setHighlighter(highlighter);
        }
        Parser parser = this.parser;
        if (parser != null) {
            lineReaderImpl.setParser(parser);
        }
        Expander expander = this.expander;
        if (expander != null) {
            lineReaderImpl.setExpander(expander);
        }
        return lineReaderImpl;
    }
}

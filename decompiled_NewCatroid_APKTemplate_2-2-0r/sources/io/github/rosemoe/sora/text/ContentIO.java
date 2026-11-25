package io.github.rosemoe.sora.text;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.Charset;

/* JADX INFO: loaded from: classes11.dex */
public class ContentIO {
    private static final int BUFFER_SIZE = 16384;

    public static Content createFrom(InputStream inputStream) throws IOException {
        return createFrom(inputStream, Charset.defaultCharset());
    }

    public static Content createFrom(InputStream inputStream, Charset charset) throws IOException {
        return createFrom(new InputStreamReader(inputStream, charset));
    }

    public static Content createFrom(Reader reader) throws IOException {
        Content content = new Content();
        content.setUndoEnabled(false);
        char[] cArr = new char[16384];
        CharArrayWrapper charArrayWrapper = new CharArrayWrapper(cArr, 0);
        while (true) {
            int i = reader.read(cArr);
            if (i == -1) {
                reader.close();
                content.setUndoEnabled(true);
                return content;
            }
            if (i > 0) {
                int i2 = i - 1;
                if (cArr[i2] == '\r') {
                    int i3 = reader.read();
                    if (i3 == 10) {
                        charArrayWrapper.setDataCount(i2);
                        int lineCount = content.getLineCount() - 1;
                        content.insert(lineCount, content.getColumnCount(lineCount), charArrayWrapper);
                        int lineCount2 = content.getLineCount() - 1;
                        content.insert(lineCount2, content.getColumnCount(lineCount2), "\r\n");
                    } else if (i3 != -1) {
                        charArrayWrapper.setDataCount(i);
                        int lineCount3 = content.getLineCount() - 1;
                        content.insert(lineCount3, content.getColumnCount(lineCount3), charArrayWrapper);
                        int lineCount4 = content.getLineCount() - 1;
                        content.insert(lineCount4, content.getColumnCount(lineCount4), String.valueOf((char) i3));
                    }
                }
                charArrayWrapper.setDataCount(i);
                int lineCount5 = content.getLineCount() - 1;
                content.insert(lineCount5, content.getColumnCount(lineCount5), charArrayWrapper);
            }
        }
    }

    public static void writeTo(Content content, OutputStream outputStream, boolean z) throws IOException {
        writeTo(content, outputStream, Charset.defaultCharset(), z);
    }

    public static void writeTo(Content content, OutputStream outputStream, Charset charset, boolean z) throws IOException {
        writeTo(content, new OutputStreamWriter(outputStream, charset), z);
    }

    public static void writeTo(Content content, Writer writer, boolean z) throws IOException {
        final BufferedWriter bufferedWriter = writer instanceof BufferedWriter ? (BufferedWriter) writer : new BufferedWriter(writer, 16384);
        try {
            content.runReadActionsOnLines(0, content.getLineCount() - 1, new Content.ContentLineConsumer2() { // from class: io.github.rosemoe.sora.text.ContentIO$$ExternalSyntheticLambda0
                @Override // io.github.rosemoe.sora.text.Content.ContentLineConsumer2
                public final void accept(int i, ContentLine contentLine, Content.ContentLineConsumer2.AbortFlag abortFlag) {
                    ContentIO.lambda$writeTo$0(bufferedWriter, i, contentLine, abortFlag);
                }
            });
            bufferedWriter.flush();
            if (z) {
                bufferedWriter.close();
            }
        } catch (RuntimeException e) {
            Throwable cause = e.getCause();
            if (cause instanceof IOException) {
                throw ((IOException) cause);
            }
            throw e;
        }
    }

    static /* synthetic */ void lambda$writeTo$0(BufferedWriter bufferedWriter, int i, ContentLine contentLine, Content.ContentLineConsumer2.AbortFlag abortFlag) {
        try {
            bufferedWriter.write(contentLine.value, 0, contentLine.length());
            bufferedWriter.write(contentLine.getLineSeparator().getChars());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

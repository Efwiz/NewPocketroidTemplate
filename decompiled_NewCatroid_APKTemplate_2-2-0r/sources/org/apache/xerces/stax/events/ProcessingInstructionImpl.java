package org.apache.xerces.stax.events;

import java.io.IOException;
import java.io.Writer;
import javax.xml.stream.Location;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.ProcessingInstruction;

/* JADX INFO: loaded from: classes13.dex */
public final class ProcessingInstructionImpl extends XMLEventImpl implements ProcessingInstruction {
    private final String fData;
    private final String fTarget;

    public ProcessingInstructionImpl(String str, String str2, Location location) {
        super(3, location);
        this.fTarget = str == null ? "" : str;
        this.fData = str2;
    }

    @Override // javax.xml.stream.events.ProcessingInstruction
    public String getData() {
        return this.fData;
    }

    @Override // javax.xml.stream.events.ProcessingInstruction
    public String getTarget() {
        return this.fTarget;
    }

    @Override // javax.xml.stream.events.XMLEvent
    public void writeAsEncodedUnicode(Writer writer) throws XMLStreamException {
        try {
            writer.write("<?");
            writer.write(this.fTarget);
            String str = this.fData;
            if (str != null && str.length() > 0) {
                writer.write(32);
                writer.write(this.fData);
            }
            writer.write("?>");
        } catch (IOException e) {
            throw new XMLStreamException(e);
        }
    }
}

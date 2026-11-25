package antlr.build;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/* JADX INFO: loaded from: classes.dex */
class StreamScarfer extends Thread {
    InputStream is;
    Tool tool;
    String type;

    StreamScarfer(InputStream inputStream, String str, Tool tool) {
        this.is = inputStream;
        this.type = str;
        this.tool = tool;
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public void run() {
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(this.is));
            while (true) {
                String line = bufferedReader.readLine();
                if (line == null) {
                    return;
                }
                String str = this.type;
                if (str == null || str.equals("stdout")) {
                    this.tool.stdout(line);
                } else {
                    this.tool.stderr(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

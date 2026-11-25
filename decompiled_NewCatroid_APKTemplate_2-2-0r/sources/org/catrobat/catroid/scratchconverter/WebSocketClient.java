package org.catrobat.catroid.scratchconverter;

import android.util.Log;
import com.google.android.gms.common.images.WebImage;
import com.google.common.base.Preconditions;
import com.huawei.hms.ml.language.common.utils.Constant;
import com.koushikdutta.async.callback.CompletedCallback;
import com.koushikdutta.async.http.AsyncHttpClient;
import com.koushikdutta.async.http.WebSocket;
import com.koushikdutta.async.http.WebSocket.StringCallback;
import org.catrobat.catroid.common.Constants;
import org.catrobat.catroid.scratchconverter.protocol.BaseMessageHandler;
import org.catrobat.catroid.scratchconverter.protocol.Job;
import org.catrobat.catroid.scratchconverter.protocol.MessageListener;
import org.catrobat.catroid.scratchconverter.protocol.command.AuthenticateCommand;
import org.catrobat.catroid.scratchconverter.protocol.command.Command;
import org.catrobat.catroid.scratchconverter.protocol.command.RetrieveInfoCommand;
import org.catrobat.catroid.scratchconverter.protocol.command.ScheduleJobCommand;
import org.catrobat.catroid.scratchconverter.protocol.message.base.BaseMessage;
import org.catrobat.catroid.scratchconverter.protocol.message.base.ClientIDMessage;
import org.catrobat.catroid.scratchconverter.protocol.message.base.ErrorMessage;
import org.catrobat.catroid.scratchconverter.protocol.message.base.InfoMessage;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/scratchconverter/WebSocketClient.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class WebSocketClient<T extends MessageListener & WebSocket.StringCallback> implements Client, BaseMessageHandler, CompletedCallback {
    private static final String TAG = "WebSocketClient";
    private long clientID;
    private Client.ConnectAuthCallback connectAuthCallback;
    private Client.ConvertCallback convertCallback;
    private final T messageListener;
    private WebSocket webSocket;
    private AsyncHttpClient asyncHttpClient = AsyncHttpClient.getDefaultInstance();
    private Client.State state = Client.State.NOT_CONNECTED;

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/scratchconverter/WebSocketClient$ConnectCallback.class
      classes14.dex
     */
    private interface ConnectCallback {
        void onFailure(ClientException ex);

        void onSuccess();
    }

    public WebSocketClient(final long clientID, final T messageListener) {
        this.clientID = clientID;
        messageListener.setBaseMessageHandler(this);
        this.messageListener = messageListener;
        this.webSocket = null;
        this.connectAuthCallback = null;
        this.convertCallback = null;
    }

    public boolean isConnected() {
        return this.state == Client.State.CONNECTED || this.state == Client.State.CONNECTED_AUTHENTICATED;
    }

    @Override // org.catrobat.catroid.scratchconverter.Client
    public boolean isClosed() {
        return this.state == Client.State.NOT_CONNECTED;
    }

    @Override // org.catrobat.catroid.scratchconverter.Client
    public boolean isAuthenticated() {
        return this.state == Client.State.CONNECTED_AUTHENTICATED;
    }

    public void setAsyncHttpClient(final AsyncHttpClient asyncHttpClient) {
        this.asyncHttpClient = asyncHttpClient;
    }

    @Override // org.catrobat.catroid.scratchconverter.Client
    public void setConvertCallback(final Client.ConvertCallback callback) {
        this.convertCallback = callback;
    }

    private void connect(final ConnectCallback connectCallback) {
        if (this.state == Client.State.CONNECTED) {
            connectCallback.onSuccess();
            return;
        }
        Preconditions.checkState(this.webSocket == null);
        Preconditions.checkState(this.asyncHttpClient != null);
        this.asyncHttpClient.websocket(Constants.SCRATCH_CONVERTER_WEB_SOCKET, (String) null, new AsyncHttpClient.WebSocketConnectCallback() { // from class: org.catrobat.catroid.scratchconverter.WebSocketClient.1
            @Override // com.koushikdutta.async.http.AsyncHttpClient.WebSocketConnectCallback
            public void onCompleted(Exception ex, final WebSocket newWebSocket) {
                Preconditions.checkState(WebSocketClient.this.state != Client.State.CONNECTED && WebSocketClient.this.webSocket == null);
                if (ex != null) {
                    connectCallback.onFailure(new ClientException(ex));
                    return;
                }
                WebSocketClient.this.state = Client.State.CONNECTED;
                WebSocketClient.this.webSocket = newWebSocket;
                WebSocketClient.this.webSocket.setStringCallback((WebSocket.StringCallback) WebSocketClient.this.messageListener);
                WebSocketClient.this.webSocket.setClosedCallback(this);
                connectCallback.onSuccess();
            }
        });
    }

    @Override // com.koushikdutta.async.callback.CompletedCallback
    public void onCompleted(Exception ex) {
        this.state = Client.State.NOT_CONNECTED;
        this.connectAuthCallback.onConnectionClosed(new ClientException(ex));
    }

    @Override // org.catrobat.catroid.scratchconverter.Client
    public void close() {
        Preconditions.checkState(this.state != Client.State.NOT_CONNECTED);
        Preconditions.checkState(this.webSocket != null);
        Preconditions.checkState(this.connectAuthCallback != null);
        this.state = Client.State.NOT_CONNECTED;
        this.webSocket.close();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void authenticate() {
        Preconditions.checkState(this.state == Client.State.CONNECTED);
        Preconditions.checkState(this.webSocket != null);
        sendCommand(new AuthenticateCommand(this.clientID));
    }

    @Override // org.catrobat.catroid.scratchconverter.Client
    public void connectAndAuthenticate(final Client.ConnectAuthCallback connectAuthCallback) {
        this.connectAuthCallback = connectAuthCallback;
        int i = AnonymousClass3.$SwitchMap$org$catrobat$catroid$scratchconverter$Client$State[this.state.ordinal()];
        if (i == 1) {
            connect(new ConnectCallback() { // from class: org.catrobat.catroid.scratchconverter.WebSocketClient.2
                @Override // org.catrobat.catroid.scratchconverter.WebSocketClient.ConnectCallback
                public void onSuccess() {
                    Log.i(WebSocketClient.TAG, "Successfully connected to WebSocket server");
                    WebSocketClient.this.authenticate();
                }

                @Override // org.catrobat.catroid.scratchconverter.WebSocketClient.ConnectCallback
                public void onFailure(ClientException ex) {
                    connectAuthCallback.onConnectionFailure(ex);
                }
            });
            return;
        }
        if (i == 2) {
            Log.i(TAG, "Already connected to WebSocket server!");
            authenticate();
        } else {
            if (i != 3) {
                return;
            }
            Log.i(TAG, "Already authenticated!");
            connectAuthCallback.onSuccess(this.clientID);
        }
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/scratchconverter/WebSocketClient$3.class
      classes14.dex
     */
    /* JADX INFO: renamed from: org.catrobat.catroid.scratchconverter.WebSocketClient$3, reason: invalid class name */
    static /* synthetic */ class AnonymousClass3 {
        static final /* synthetic */ int[] $SwitchMap$org$catrobat$catroid$scratchconverter$Client$State;

        static {
            int[] iArr = new int[Client.State.values().length];
            $SwitchMap$org$catrobat$catroid$scratchconverter$Client$State = iArr;
            try {
                iArr[Client.State.NOT_CONNECTED.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$scratchconverter$Client$State[Client.State.CONNECTED.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$scratchconverter$Client$State[Client.State.CONNECTED_AUTHENTICATED.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    @Override // org.catrobat.catroid.scratchconverter.Client
    public void retrieveInfo() {
        Preconditions.checkState(this.state == Client.State.CONNECTED_AUTHENTICATED);
        Preconditions.checkState(this.clientID != -1);
        sendCommand(new RetrieveInfoCommand());
    }

    @Override // org.catrobat.catroid.scratchconverter.Client
    public boolean isJobInProgress(long jobID) {
        return this.messageListener.isJobInProgress(jobID);
    }

    @Override // org.catrobat.catroid.scratchconverter.Client
    public int getNumberOfJobsInProgress() {
        return this.messageListener.getNumberOfJobsInProgress();
    }

    @Override // org.catrobat.catroid.scratchconverter.Client
    public void convertProgram(final long jobID, final String title, final WebImage image, final boolean verbose, final boolean force) {
        Preconditions.checkState(this.state == Client.State.CONNECTED_AUTHENTICATED);
        Preconditions.checkState(this.clientID != -1);
        Job job = new Job(jobID, title, image);
        if (this.state != Client.State.CONNECTED_AUTHENTICATED || this.webSocket == null) {
            this.convertCallback.onConversionFailure(job, new ClientException("Not connected!"));
        } else if (!this.messageListener.scheduleJob(job, force, this.convertCallback)) {
            Log.e(TAG, "Cannot schedule job since another job of the same Scratch program is already running (job ID is: " + jobID + Constant.AFTER_QUTO);
            this.convertCallback.onConversionFailure(job, new ClientException("Cannot start this job since the job already exists and is in progress! Set force-flag to true to restart the conversion while it is running!"));
        } else {
            Log.i(TAG, "Scheduling new job with ID: " + jobID);
            sendCommand(new ScheduleJobCommand(jobID, force, verbose));
        }
    }

    @Override // org.catrobat.catroid.scratchconverter.Client
    public void onUserCanceledConversion(long jobID) {
        Preconditions.checkState(this.state == Client.State.CONNECTED_AUTHENTICATED);
        Preconditions.checkState(this.clientID != -1);
        this.messageListener.onUserCanceledConversion(jobID);
    }

    @Override // org.catrobat.catroid.scratchconverter.protocol.BaseMessageHandler
    public void onBaseMessage(BaseMessage baseMessage) {
        if (baseMessage instanceof InfoMessage) {
            InfoMessage infoMessage = (InfoMessage) baseMessage;
            this.convertCallback.onInfo(infoMessage.getCatrobatLanguageVersion(), infoMessage.getJobList());
            for (Job job : infoMessage.getJobList()) {
                Client.ProjectDownloadCallback projectDownloadCallbackRestoreJobIfRunning = this.messageListener.restoreJobIfRunning(job, this.convertCallback);
                if (projectDownloadCallbackRestoreJobIfRunning != null) {
                    this.convertCallback.onConversionAlreadyFinished(job, projectDownloadCallbackRestoreJobIfRunning, job.getDownloadURL());
                }
            }
            return;
        }
        if (baseMessage instanceof ErrorMessage) {
            ErrorMessage errorMessage = (ErrorMessage) baseMessage;
            Log.e(TAG, errorMessage.getMessage());
            if (this.state == Client.State.CONNECTED) {
                Preconditions.checkState(this.connectAuthCallback != null);
                this.connectAuthCallback.onAuthenticationFailure(new ClientException(errorMessage.getMessage()));
                return;
            } else if (this.state == Client.State.CONNECTED_AUTHENTICATED) {
                this.convertCallback.onConversionFailure(null, new ClientException(errorMessage.getMessage()));
                return;
            } else {
                this.convertCallback.onError(errorMessage.getMessage());
                return;
            }
        }
        if (baseMessage instanceof ClientIDMessage) {
            Preconditions.checkState(this.state == Client.State.CONNECTED);
            long j = this.clientID;
            long clientID = ((ClientIDMessage) baseMessage).getClientID();
            this.clientID = clientID;
            if (clientID != j) {
                Log.d(TAG, "New Client ID: " + this.clientID);
            }
            this.state = Client.State.CONNECTED_AUTHENTICATED;
            this.connectAuthCallback.onSuccess(this.clientID);
            return;
        }
        Log.e(TAG, "No handler implemented for base message: " + baseMessage);
    }

    private void sendCommand(final Command command) {
        Preconditions.checkArgument(command != null);
        Preconditions.checkState(this.state == Client.State.CONNECTED || this.state == Client.State.CONNECTED_AUTHENTICATED);
        Preconditions.checkState(this.webSocket != null);
        String string = command.toJson().toString();
        Log.d(TAG, "Sending: " + string);
        this.webSocket.send(string);
    }
}

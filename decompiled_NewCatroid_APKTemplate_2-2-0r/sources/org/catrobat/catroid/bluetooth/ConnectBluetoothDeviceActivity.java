package org.catrobat.catroid.bluetooth;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Set;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.bluetooth.base.BluetoothConnection;
import org.catrobat.catroid.bluetooth.base.BluetoothConnectionFactory;
import org.catrobat.catroid.bluetooth.base.BluetoothDevice;
import org.catrobat.catroid.bluetooth.base.BluetoothDeviceFactory;
import org.catrobat.catroid.bluetooth.base.BluetoothDeviceService;
import org.catrobat.catroid.common.CatroidService;
import org.catrobat.catroid.common.ServiceProvider;
import org.catrobat.catroid.common.SharedPreferenceKeys;
import org.catrobat.catroid.devices.mindstorms.MindstormsException;
import org.catrobat.catroid.devices.multiplayer.Multiplayer;
import org.catrobat.catroid.utils.ToastUtil;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/bluetooth/ConnectBluetoothDeviceActivity.class
  classes12.dex
 */
/* JADX INFO: loaded from: classes12.dex */
public class ConnectBluetoothDeviceActivity extends AppCompatActivity {
    public static final String DEVICE_TO_CONNECT = "org.catrobat.catroid.bluetooth.DEVICE";
    private static final int REQUEST_BLUETOOTH_PERMISSIONS = 101;
    public static final String TAG = "ConnectBluetoothDeviceActivity";
    private static BluetoothConnectionFactory btConnectionFactory;
    private static BluetoothDeviceFactory btDeviceFactory;
    protected BluetoothDevice btDevice;
    private BluetoothManager btManager;
    private Handler handler;
    private ArrayAdapter<Pair<Pair<String, String>, Integer>> newDevicesArrayAdapter;
    private ArrayAdapter<Pair<String, String>> pairedDevicesArrayAdapter;
    FloatingActionButton scanButton;
    Boolean isDiscovering = false;
    private AdapterView.OnItemClickListener deviceClickListener = new AdapterView.OnItemClickListener() { // from class: org.catrobat.catroid.bluetooth.ConnectBluetoothDeviceActivity.1
        private String getSelectedBluetoothAddress(View view) {
            return ((TextView) view.findViewById(R.id.bluetooth_address)).getText().toString();
        }

        @Override // android.widget.AdapterView.OnItemClickListener
        public void onItemClick(AdapterView<?> av, View view, int position, long id2) {
            String selectedBluetoothAddress = getSelectedBluetoothAddress(view);
            Pair pair = !ConnectBluetoothDeviceActivity.this.newDevicesArrayAdapter.isEmpty() ? (Pair) ConnectBluetoothDeviceActivity.this.newDevicesArrayAdapter.getItem(position) : null;
            if (pair == null || pair.second.equals(1)) {
                ConnectBluetoothDeviceActivity.this.connectDevice(selectedBluetoothAddress);
            }
        }
    };
    private final BroadcastReceiver receiver = new BroadcastReceiver() { // from class: org.catrobat.catroid.bluetooth.ConnectBluetoothDeviceActivity.2
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            android.bluetooth.BluetoothDevice bluetoothDevice;
            String action = intent.getAction();
            if ("android.bluetooth.device.action.FOUND".equals(action)) {
                if (Build.VERSION.SDK_INT >= 33) {
                    bluetoothDevice = (android.bluetooth.BluetoothDevice) intent.getParcelableExtra("android.bluetooth.device.extra.DEVICE", android.bluetooth.BluetoothDevice.class);
                } else {
                    bluetoothDevice = (android.bluetooth.BluetoothDevice) intent.getParcelableExtra("android.bluetooth.device.extra.DEVICE");
                }
                if (bluetoothDevice == null) {
                    return;
                }
                if (ConnectBluetoothDeviceActivity.this.checkAndRequestBluetoothPermissions()) {
                    try {
                        if (bluetoothDevice.getBondState() != 12) {
                            String name2 = bluetoothDevice.getName() == null ? "Unknown Device" : bluetoothDevice.getName();
                            int type = bluetoothDevice.getType();
                            if (type == 1 || type == 3) {
                                Pair pair = new Pair(new Pair(name2, bluetoothDevice.getAddress()), 1);
                                if (ConnectBluetoothDeviceActivity.this.newDevicesArrayAdapter.getPosition(pair) < 0) {
                                    ConnectBluetoothDeviceActivity.this.newDevicesArrayAdapter.add(pair);
                                }
                            }
                            if (type == 2 || type == 3) {
                                Pair pair2 = new Pair(new Pair("BLE - " + name2, bluetoothDevice.getAddress()), 2);
                                if (ConnectBluetoothDeviceActivity.this.newDevicesArrayAdapter.getPosition(pair2) < 0) {
                                    ConnectBluetoothDeviceActivity.this.newDevicesArrayAdapter.add(pair2);
                                }
                            }
                        }
                    } catch (SecurityException e) {
                        Log.e(ConnectBluetoothDeviceActivity.TAG, "Bluetooth permission missing for device discovery", e);
                    }
                }
            } else if ("android.bluetooth.adapter.action.DISCOVERY_FINISHED".equals(action)) {
                ConnectBluetoothDeviceActivity.this.isDiscovering = false;
                ConnectBluetoothDeviceActivity.this.scanButton.setImageResource(R.drawable.ic_search);
                ConnectBluetoothDeviceActivity.this.findViewById(R.id.device_list_progress_bar).setVisibility(8);
                if (ConnectBluetoothDeviceActivity.this.newDevicesArrayAdapter.isEmpty()) {
                    ConnectBluetoothDeviceActivity.this.newDevicesArrayAdapter.add(new Pair(new Pair(ConnectBluetoothDeviceActivity.this.getResources().getString(R.string.none_found), ""), 0));
                }
            }
            ConnectBluetoothDeviceActivity.setDynamicListViewHeight((ListView) ConnectBluetoothDeviceActivity.this.findViewById(R.id.new_devices));
        }
    };

    private static BluetoothDeviceFactory getDeviceFactory() {
        if (btDeviceFactory == null) {
            btDeviceFactory = new BluetoothDeviceFactoryImpl();
        }
        return btDeviceFactory;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static BluetoothConnectionFactory getConnectionFactory() {
        if (btConnectionFactory == null) {
            btConnectionFactory = new BluetoothConnectionFactoryImpl();
        }
        return btConnectionFactory;
    }

    public static void setDeviceFactory(BluetoothDeviceFactory deviceFactory) {
        btDeviceFactory = deviceFactory;
    }

    public static void setConnectionFactory(BluetoothConnectionFactory connectionFactory) {
        btConnectionFactory = connectionFactory;
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/bluetooth/ConnectBluetoothDeviceActivity$ConnectDeviceTask.class
      classes12.dex
     */
    private class ConnectDeviceTask extends AsyncTask<String, Void, BluetoothConnection.State> {
        BluetoothConnection btConnection;
        private ProgressDialog connectingProgressDialog;

        private ConnectDeviceTask() {
        }

        @Override // android.os.AsyncTask
        protected void onPreExecute() {
            ConnectBluetoothDeviceActivity connectBluetoothDeviceActivity = ConnectBluetoothDeviceActivity.this;
            this.connectingProgressDialog = ProgressDialog.show(connectBluetoothDeviceActivity, "", connectBluetoothDeviceActivity.getResources().getString(R.string.connecting_please_wait), true);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public BluetoothConnection.State doInBackground(String... addresses) {
            if (ConnectBluetoothDeviceActivity.this.btDevice == null) {
                Log.e(ConnectBluetoothDeviceActivity.TAG, "Try connect to device which is not implemented!");
                return BluetoothConnection.State.NOT_CONNECTED;
            }
            BluetoothConnection bluetoothConnectionCreateBTConnectionForDevice = ConnectBluetoothDeviceActivity.getConnectionFactory().createBTConnectionForDevice(ConnectBluetoothDeviceActivity.this.btDevice.getDeviceType(), addresses[0], ConnectBluetoothDeviceActivity.this.btDevice.getBluetoothDeviceUUID(), ConnectBluetoothDeviceActivity.this.getApplicationContext());
            this.btConnection = bluetoothConnectionCreateBTConnectionForDevice;
            return bluetoothConnectionCreateBTConnectionForDevice.connect();
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public void onPostExecute(BluetoothConnection.State connectionState) {
            int i;
            this.connectingProgressDialog.dismiss();
            if (connectionState == BluetoothConnection.State.CONNECTED) {
                ConnectBluetoothDeviceActivity.this.btDevice.setConnection(this.btConnection);
                try {
                    ConnectBluetoothDeviceActivity.this.setDeviceConnected(this.btConnection.getInputStream(), this.btConnection.getOutputStream());
                } catch (IOException e) {
                    Log.e(ConnectBluetoothDeviceActivity.TAG, e.getMessage(), e);
                }
                i = -1;
            } else {
                ToastUtil.showError(ConnectBluetoothDeviceActivity.this, R.string.bt_connection_failed);
                i = 0;
            }
            ConnectBluetoothDeviceActivity.this.setResult(i);
            ConnectBluetoothDeviceActivity.this.finish();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setDeviceConnected(InputStream inputStream, OutputStream outputStream) {
        BluetoothDeviceService bluetoothDeviceService = (BluetoothDeviceService) ServiceProvider.getService(CatroidService.BLUETOOTH_DEVICE_SERVICE);
        try {
            BluetoothDevice bluetoothDevice = this.btDevice;
            if (bluetoothDevice instanceof Multiplayer) {
                ((Multiplayer) bluetoothDevice).setStreams(inputStream, outputStream);
            }
            if (bluetoothDeviceService != null) {
                bluetoothDeviceService.deviceConnected(this.btDevice);
            }
        } catch (MindstormsException unused) {
            ToastUtil.showError(this, R.string.bt_connection_failed);
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createAndSetDeviceService();
        setContentView(R.layout.device_list);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setTitle(R.string.bluetooth_connection_title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setResult(0);
        FloatingActionButton floatingActionButton = (FloatingActionButton) findViewById(R.id.bluetooth_scan);
        this.scanButton = floatingActionButton;
        floatingActionButton.setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.catroid.bluetooth.ConnectBluetoothDeviceActivity$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.m4815xca08faa9(view);
            }
        });
        this.handler = new Handler();
        int i = R.layout.bluetooth_connection_screen;
        this.pairedDevicesArrayAdapter = new ArrayAdapter<Pair<String, String>>(this, i) { // from class: org.catrobat.catroid.bluetooth.ConnectBluetoothDeviceActivity.3
            @Override // android.widget.ArrayAdapter, android.widget.Adapter
            public View getView(int position, View convertView, ViewGroup parent) {
                if (convertView == null) {
                    convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.bluetooth_connection_screen, parent, false);
                }
                TextView textView = (TextView) convertView.findViewById(R.id.bluetooth_device);
                TextView textView2 = (TextView) convertView.findViewById(R.id.bluetooth_address);
                textView.setText((CharSequence) getItem(position).first);
                textView2.setText((CharSequence) getItem(position).second);
                return convertView;
            }
        };
        this.newDevicesArrayAdapter = new ArrayAdapter<Pair<Pair<String, String>, Integer>>(this, i) { // from class: org.catrobat.catroid.bluetooth.ConnectBluetoothDeviceActivity.4
            @Override // android.widget.ArrayAdapter, android.widget.Adapter
            public View getView(int position, View convertView, ViewGroup parent) {
                if (convertView == null) {
                    convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.bluetooth_connection_screen, parent, false);
                }
                TextView textView = (TextView) convertView.findViewById(R.id.bluetooth_device);
                TextView textView2 = (TextView) convertView.findViewById(R.id.bluetooth_address);
                textView.setText((CharSequence) ((Pair) getItem(position).first).first);
                textView2.setText((CharSequence) ((Pair) getItem(position).first).second);
                return convertView;
            }
        };
        ListView listView = (ListView) findViewById(R.id.paired_devices);
        listView.setAdapter((ListAdapter) this.pairedDevicesArrayAdapter);
        listView.setOnItemClickListener(this.deviceClickListener);
        ListView listView2 = (ListView) findViewById(R.id.new_devices);
        listView2.setAdapter((ListAdapter) this.newDevicesArrayAdapter);
        listView2.setOnItemClickListener(this.deviceClickListener);
        registerReceiver(this.receiver, new IntentFilter("android.bluetooth.device.action.FOUND"));
        registerReceiver(this.receiver, new IntentFilter("android.bluetooth.adapter.action.DISCOVERY_FINISHED"));
        boolean z = PreferenceManager.getDefaultSharedPreferences(this).getBoolean(SharedPreferenceKeys.SHOW_MULTIPLAYER_BLUETOOTH_DIALOG_KEY, true);
        if ((this.btDevice instanceof Multiplayer) && z) {
            showMultiplayerInformationDialog();
        } else {
            initBluetooth();
        }
    }

    /* JADX INFO: renamed from: lambda$onCreate$0$org-catrobat-catroid-bluetooth-ConnectBluetoothDeviceActivity, reason: not valid java name */
    /* synthetic */ void m4815xca08faa9(View view) {
        handleScanButtonClicked();
    }

    @Override // android.app.Activity
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_bluetooth_connection, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override // android.app.Activity
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.skip_bluetooth).setVisible(true);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override // android.app.Activity
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == 16908332) {
            onBackPressed();
        } else if (itemId == R.id.skip_bluetooth) {
            setResult(-1);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void showMultiplayerInformationDialog() {
        new AlertDialog.Builder(this).setCancelable(false).setNegativeButton(getString(R.string.got_it), new DialogInterface.OnClickListener() { // from class: org.catrobat.catroid.bluetooth.ConnectBluetoothDeviceActivity$$ExternalSyntheticLambda1
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                this.f$0.m4816xc64a0d26(dialogInterface, i);
            }
        }).setView(View.inflate(this, R.layout.dialog_multiplayer_bluetooth, null)).show();
    }

    /* JADX INFO: renamed from: lambda$showMultiplayerInformationDialog$1$org-catrobat-catroid-bluetooth-ConnectBluetoothDeviceActivity, reason: not valid java name */
    /* synthetic */ void m4816xc64a0d26(DialogInterface dialogInterface, int i) {
        PreferenceManager.getDefaultSharedPreferences(this).edit().putBoolean(SharedPreferenceKeys.SHOW_MULTIPLAYER_BLUETOOTH_DIALOG_KEY, false).apply();
        initBluetooth();
    }

    protected void initBluetooth() {
        if (activateBluetooth() == 1 && checkAndRequestBluetoothPermissions()) {
            listAndSelectDevices();
            startAcceptThread();
            activateBluetoothVisibility();
        }
    }

    private void handleScanButtonClicked() {
        if (this.isDiscovering.booleanValue()) {
            cancelDiscovery();
        } else if (checkAndRequestBluetoothPermissions()) {
            doDiscovery();
        }
    }

    private void activateBluetoothVisibility() {
        if ((this.btDevice instanceof Multiplayer) && checkAndRequestBluetoothPermissions()) {
            try {
                startActivity(new Intent("android.bluetooth.adapter.action.REQUEST_DISCOVERABLE"));
            } catch (SecurityException e) {
                Log.e(TAG, "Bluetooth permission missing to request discoverable.", e);
            }
        }
    }

    private void startAcceptThread() {
        if (this.btDevice instanceof Multiplayer) {
            new AcceptThread().start();
        }
    }

    private void listAndSelectDevices() {
        this.pairedDevicesArrayAdapter.clear();
        try {
            Set<android.bluetooth.BluetoothDevice> bondedDevices = this.btManager.getBluetoothAdapter().getBondedDevices();
            if (bondedDevices.size() > 0) {
                findViewById(R.id.bluetooth_paired_section).setVisibility(0);
                for (android.bluetooth.BluetoothDevice bluetoothDevice : bondedDevices) {
                    this.pairedDevicesArrayAdapter.add(new Pair<>(bluetoothDevice.getName() == null ? "Paired Device" : bluetoothDevice.getName(), bluetoothDevice.getAddress()));
                }
                setDynamicListViewHeight((ListView) findViewById(R.id.paired_devices));
            }
        } catch (SecurityException e) {
            Log.e(TAG, "Bluetooth permission missing for listing paired devices.", e);
        }
    }

    protected void createAndSetDeviceService() {
        this.btDevice = getDeviceFactory().createDevice((Class) getIntent().getSerializableExtra(DEVICE_TO_CONNECT), getApplicationContext());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void connectDevice(String address) {
        cancelDiscovery();
        new ConnectDeviceTask().execute(address);
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onDestroy() {
        cancelDiscovery();
        unregisterReceiver(this.receiver);
        super.onDestroy();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean checkAndRequestBluetoothPermissions() {
        if (Build.VERSION.SDK_INT < 31) {
            return true;
        }
        ArrayList arrayList = new ArrayList();
        if (ContextCompat.checkSelfPermission(this, "android.permission.BLUETOOTH_SCAN") != 0) {
            arrayList.add("android.permission.BLUETOOTH_SCAN");
        }
        if (ContextCompat.checkSelfPermission(this, "android.permission.BLUETOOTH_CONNECT") != 0) {
            arrayList.add("android.permission.BLUETOOTH_CONNECT");
        }
        if (ContextCompat.checkSelfPermission(this, "android.permission.BLUETOOTH_ADVERTISE") != 0) {
            arrayList.add("android.permission.BLUETOOTH_ADVERTISE");
        }
        if (arrayList.isEmpty()) {
            return true;
        }
        ActivityCompat.requestPermissions(this, (String[]) arrayList.toArray(new String[0]), 101);
        return false;
    }

    protected void doDiscovery() {
        try {
            this.isDiscovering = true;
            this.scanButton.setImageResource(R.drawable.ic_close);
            this.newDevicesArrayAdapter.clear();
            findViewById(R.id.device_list_progress_bar).setVisibility(0);
            if (this.btManager.getBluetoothAdapter().isDiscovering()) {
                this.btManager.getBluetoothAdapter().cancelDiscovery();
            }
            this.btManager.getBluetoothAdapter().startDiscovery();
        } catch (SecurityException e) {
            Log.e(TAG, "Bluetooth permission missing for discovery.", e);
            Toast.makeText(this, "Bluetooth permission required.", 0).show();
        }
    }

    private void cancelDiscovery() {
        try {
            BluetoothManager bluetoothManager = this.btManager;
            if (bluetoothManager != null && bluetoothManager.getBluetoothAdapter() != null && this.btManager.getBluetoothAdapter().isDiscovering()) {
                this.btManager.getBluetoothAdapter().cancelDiscovery();
            }
            this.isDiscovering = false;
            this.scanButton.setImageResource(R.drawable.ic_search);
        } catch (SecurityException e) {
            Log.e(TAG, "Bluetooth permission missing for cancelling discovery.", e);
        }
    }

    private int activateBluetooth() {
        BluetoothManager bluetoothManager = new BluetoothManager(this);
        this.btManager = bluetoothManager;
        int iActivateBluetooth = bluetoothManager.activateBluetooth();
        if (iActivateBluetooth == -1) {
            ToastUtil.showError(this, R.string.notification_blueth_err);
            setResult(0);
            finish();
        }
        return iActivateBluetooth;
    }

    public static void setDynamicListViewHeight(ListView listView) {
        ListAdapter adapter = listView.getAdapter();
        if (adapter == null) {
            return;
        }
        int iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), 0);
        int measuredHeight = 0;
        for (int i = 0; i < adapter.getCount(); i++) {
            View view = adapter.getView(i, null, listView);
            view.measure(iMakeMeasureSpec, 0);
            measuredHeight += view.getMeasuredHeight();
        }
        ViewGroup.LayoutParams layoutParams = listView.getLayoutParams();
        layoutParams.height = measuredHeight + (listView.getDividerHeight() * (adapter.getCount() - 1));
        listView.setLayoutParams(layoutParams);
        listView.requestLayout();
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i(TAG, "Bluetooth activation activity returned");
        if (resultCode == -1) {
            listAndSelectDevices();
            startAcceptThread();
            activateBluetoothVisibility();
        } else {
            if (resultCode != 0) {
                return;
            }
            ToastUtil.showError(this, R.string.notification_blueth_err);
            setResult(0);
            finish();
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 101) {
            for (int i : grantResults) {
                if (i != 0) {
                    Toast.makeText(this, R.string.bt_permissions_denied, 1).show();
                    return;
                }
            }
            Log.d(TAG, "All Bluetooth permissions granted.");
            if (!this.isDiscovering.booleanValue()) {
                doDiscovery();
            }
            listAndSelectDevices();
        }
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/bluetooth/ConnectBluetoothDeviceActivity$AcceptThread.class
      classes12.dex
     */
    public class AcceptThread extends Thread {
        private BluetoothServerSocket serverSocket;

        AcceptThread() {
            try {
                this.serverSocket = BluetoothAdapter.getDefaultAdapter().listenUsingRfcommWithServiceRecord(ConnectBluetoothDeviceActivity.this.getString(R.string.app_name), ConnectBluetoothDeviceActivity.this.btDevice.getBluetoothDeviceUUID());
            } catch (IOException e) {
                Log.e(ConnectBluetoothDeviceActivity.TAG, "Creating ServerSocket failed due to IOException!", e);
            } catch (SecurityException e2) {
                Log.e(ConnectBluetoothDeviceActivity.TAG, "Creating ServerSocket failed due to missing BLUETOOTH_CONNECT permission!", e2);
            }
            ((Multiplayer) ConnectBluetoothDeviceActivity.this.btDevice).setAcceptThread(this);
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            BluetoothSocket bluetoothSocketAccept = null;
            while (!isInterrupted()) {
                try {
                    bluetoothSocketAccept = this.serverSocket.accept();
                    BluetoothServerSocket bluetoothServerSocket = this.serverSocket;
                    if (bluetoothServerSocket != null) {
                        bluetoothServerSocket.close();
                    }
                } catch (IOException e) {
                    Log.d(ConnectBluetoothDeviceActivity.TAG, e.getMessage(), e);
                }
                if (bluetoothSocketAccept != null) {
                    ConnectBluetoothDeviceActivity.this.handler.post(new Runnable() { // from class: org.catrobat.catroid.bluetooth.ConnectBluetoothDeviceActivity$AcceptThread$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f$0.m4817xfe6d7ef7();
                        }
                    });
                    try {
                        ((Multiplayer) ConnectBluetoothDeviceActivity.this.btDevice).setBluetoothSocket(bluetoothSocketAccept);
                        ConnectBluetoothDeviceActivity.this.setDeviceConnected(bluetoothSocketAccept.getInputStream(), bluetoothSocketAccept.getOutputStream());
                        return;
                    } catch (IOException e2) {
                        Log.e(ConnectBluetoothDeviceActivity.TAG, e2.getMessage(), e2);
                        return;
                    }
                }
            }
        }

        /* JADX INFO: renamed from: lambda$run$0$org-catrobat-catroid-bluetooth-ConnectBluetoothDeviceActivity$AcceptThread, reason: not valid java name */
        /* synthetic */ void m4817xfe6d7ef7() {
            ConnectBluetoothDeviceActivity.this.setResult(-1);
            ConnectBluetoothDeviceActivity.this.finish();
        }

        public void cancel() {
            try {
                this.serverSocket.close();
                this.serverSocket = null;
            } catch (IOException e) {
                Log.d(ConnectBluetoothDeviceActivity.TAG, e.getMessage(), e);
            }
        }
    }
}

package com.example.dawson.mountgoodcontrol;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    public static String newSongName;
    public static String newSongArtist;
    public static String newSongVolume;

    private class NoBluetoothException extends Exception {
        private NoBluetoothException() { super(); }
    }

    private class PiNotPairedException extends Exception {
        private PiNotPairedException() { super(); }
    }

    private class PiNotConnectedException extends Exception {
        private PiNotConnectedException() { super(); }
    }

    @Override
    protected void onDestroy() {
        ConnectedThread.cancel();
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            ConnectThread con = new ConnectThread(startBluetooth());
            if (con.mmSocket == null) { throw new PiNotConnectedException(); }
            con.start();
            startActivity(new Intent(this, LightsActivity.class));
        }
        catch (NoBluetoothException e) {
            setContentView(R.layout.activity_main);
            TextView textError = (TextView) findViewById(R.id.textError);
            textError.setText(R.string.textNoBluetooth);
            textError.setTextColor(Color.rgb(255,0,0));
        }
        catch (PiNotPairedException e) {
            setContentView(R.layout.activity_main);
            TextView textError = (TextView) findViewById(R.id.textError);
            textError.setText(R.string.textPiNotPaired);
            textError.setTextColor(Color.rgb(255,0,0));
        }
        catch (PiNotConnectedException e) {
            setContentView(R.layout.activity_main);
            TextView textError = (TextView) findViewById(R.id.textError);
            textError.setText(R.string.textPiNotConnected);
            textError.setTextColor(Color.rgb(255,0,0));
        }
    }

    private BluetoothDevice startBluetooth() throws NoBluetoothException,PiNotPairedException {
        BluetoothDevice mDevice = null;
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) { throw new NoBluetoothException(); }
        if (!mBluetoothAdapter.isEnabled()) { startActivityForResult(new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE), 1); }

        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();

        if (pairedDevices.size() > 0) {
            for (BluetoothDevice device : pairedDevices) {
                if (device.getAddress().equals("B8:27:EB:73:A6:5D")) { mDevice = device; }
            }
        }

        if (mDevice == null) { throw new PiNotPairedException(); }

        return mDevice;
    }

    public class ConnectThread extends Thread {
        private static final String TAG = "MY_APP_DEBUG_TAG";
        private static final String MY_UUID = "00001101-0000-1000-8000-00805F9B34FB";
        private final BluetoothSocket mmSocket;
        private final BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        ConnectThread(BluetoothDevice device) {
            BluetoothSocket tmp = null;

            try { tmp = device.createRfcommSocketToServiceRecord(java.util.UUID.fromString(MY_UUID)); }
            catch (IOException e) {
                Log.e(TAG, "Socket's create() method failed", e);
            }
            mmSocket = tmp;
        }

        public void run() {
            mBluetoothAdapter.cancelDiscovery();

            try { mmSocket.connect(); }
            catch (IOException e) {
                cancel();
                return;
            }

            ConnectedThread con = new ConnectedThread(mmSocket);
            con.start();
        }

        private void cancel() {
            try { mmSocket.close(); }
            catch (IOException e) { Log.e(TAG, "Could not close the client socket", e); }
        }
    }

    public static class ConnectedThread extends Thread {
        private static final String TAG = "MY_APP_DEBUG_TAG";
        private static BluetoothSocket mmSocket;
        private static InputStream mmInStream;
        private static OutputStream mmOutStream;
        private static byte[] mmBuffer; // mmBuffer store for the stream

        ConnectedThread(BluetoothSocket socket) {
            mmSocket = socket;
            InputStream tmpIn = null;
            OutputStream tmpOut = null;

            try { tmpIn = socket.getInputStream(); }
            catch (IOException e) { Log.e(TAG, "Error occurred when creating input stream", e); }

            try { tmpOut = socket.getOutputStream(); }
            catch (IOException e) { Log.e(TAG, "Error occurred when creating output stream", e); }

            mmInStream = tmpIn;
            mmOutStream = tmpOut;
        }

        static void write(byte[] bytes) {
            try {
                if (mmOutStream == null) { return; }
                mmOutStream.write(bytes);
            }
            catch (IOException e) { Log.e(TAG, "Error occurred when sending data", e); }
        }

        static void cancel() {
            try {
                if (mmSocket == null) { return; }
                mmSocket.close();
            }
            catch (IOException e) { Log.e(TAG, "Could not close the connect socket", e); }
        }

        public void run() {
            mmBuffer = new byte[1024];

            while (true) {
                try {
                    //java.util.Arrays.fill(mmBuffer, (byte) 0);
                    int numBytes = mmInStream.read(mmBuffer);
                    parseData(numBytes);

                    if (MusicActivity.mn != null) { MusicActivity.runUpdate(); }
                }
                catch (IOException e) {
                    Log.d(TAG, "Input stream was disconnected", e);
                    break;
                }
            }
        }

        private void parseData(int numBytes) {
            String buffer = new String(mmBuffer, 0,  numBytes);
            int pos1 = buffer.indexOf("!");
            int pos2 = buffer.indexOf("@");
            int pos3 = buffer.indexOf("#");
            String songName = buffer.substring(pos1+1, pos2);
            String songArtist = buffer.substring(pos2+1, pos3);
            String songVolume = buffer.substring(pos3+1);

            if (songName.contains("media player")) { songName = "No song"; }
            else if (songName.equals("1 ")) { songName = "No song"; }
            newSongName = songName;

            if (songArtist.contains("media player")) { songArtist = "is playing"; }
            else if (songArtist.contains("playlist")) { songArtist = "is playing"; }

            if (songArtist.contains("is playing")) { newSongArtist = songArtist; }
            else { newSongArtist = "by " + songArtist; }

            newSongVolume = "Volume: " + songVolume;
        }
    }
}

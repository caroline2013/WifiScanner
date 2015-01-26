package cn.xmyoula.wifiscanner;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Environment;
import android.widget.Toast;

public class Wifi_Result {
	public static final int WIFI_STATE_DISABING = 0;// �����ر���
	public static final int WIFI_STATE_DISABLED = 1;// Wifi����������
	public static final int WIFI_STATE_ENABLING = 2;// �������ڴ�
	public static final int WIFI_STATE_ENABLED = 3;// ��������
	public static final int WIFI_STATE_UNKNOWN = 4;// ����δ֪״̬

	public int WifiResult(WifiManager wm) {

		// //log.i("xiaosu","����1");
		int i = 0;
		switch (wm.getWifiState()) {
		case WIFI_STATE_DISABING:
			return -1;
		case WIFI_STATE_DISABLED:
			return -5;
		case WIFI_STATE_ENABLING:
			return -2;
		case WIFI_STATE_ENABLED:
			// wifi�����Ѵ�
			break;
		case WIFI_STATE_UNKNOWN:
			return -6;
		default:// δ֪����
			break;

		}
		if (wm.getWifiState() == 3) {
			////log.i("wifistate", String.valueOf(wm.getWifiState()));
			WifiInfo info = wm.getConnectionInfo();
			int strength = info.getRssi();
			int speed = info.getLinkSpeed();
			String units = WifiInfo.LINK_SPEED_UNITS;
			String ssid = info.getSSID();
			////log.i("xiaosu", "����2");
			List<ScanResult> results = wm.getScanResults(); // �Ӽ���Ƿ��wifi
			String otherwifi = "The existing network is: \n\n";
			////log.i("xiaosu", "����3");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy_MM_dd_hh_mm_ss");
			String date = sdf.format(new java.util.Date());
			otherwifi += "The time is :" + date + "\n\n";
			////log.i("time", String.valueOf(date));
			//String wifi_txt = "xiaosu.txt";
			for (ScanResult result : results) {
				otherwifi += result.SSID + ":" + result.BSSID + ":"
						+ result.level + "\n";
				////log.i("result", String.valueOf(result));
			
			}
			////log.i("du", "wenjian");

			String status = Environment.getExternalStorageState();
			String sDir;
			// File file=new
			if (status.equals(Environment.MEDIA_MOUNTED)) {
				sDir = Environment.getExternalStorageDirectory().getPath()
						+ "/wifi_scanner";
			} else {
				sDir = Environment.getRootDirectory().getPath()
						+ "/data/wifi_scanner";
			}

			File dir = new File(sDir);

			if (!dir.exists()) {

				try {

					// ����ָ����·�������ļ���

					dir.mkdirs();

				} catch (Exception e) {

					// TODO: handle exception

				}

			}

			File file = new File(sDir, date + ".txt");

			if (!file.exists()) {

				try {

					// ��ָ�����ļ����д����ļ�

					file.createNewFile();

				} catch (Exception e) {

				}

			}

			try {
				FileOutputStream outputStream = new FileOutputStream(file);
				outputStream.write(otherwifi.getBytes());
				outputStream.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

			//log.i("xiaosu", "����4");
			return 1;
		} else {
			return 0;
		}

	}

}

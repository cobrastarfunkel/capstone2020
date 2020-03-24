package conditions;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;


public class MacFinder {

	public static boolean isVMMac() {
		try {
			InetAddress ip;
			ip = InetAddress.getLocalHost();
			NetworkInterface network = NetworkInterface.getByInetAddress(ip);
			byte[] mac = network.getHardwareAddress();
			if (null == mac)
				return false;
			byte vmMacs[][] = { { 0x00, 0x05, 0x69 }, // VMWare
					{ 0x00, 0x1C, 0x14 }, // VMWare
					{ 0x00, 0x0C, 0x29 }, // VMWare
					{ 0x00, 0x50, 0x56 }, // VMWare
					{ 0x08, 0x00, 0x27 }, // Virtualbox
					{ 0x0A, 0x00, 0x27 }, // Virtualbox
					{ 0x00, 0x03, (byte) 0xFF }, // Virtual-PC
					{ 0x00, 0x15, 0x5D } // Hyper-V
			};
			for (byte[] vm : vmMacs) {
				if (vm[0] == mac[0] && vm[1] == mac[1] && vm[2] == mac[2])
					return true;
			}

			return false;

		} catch (UnknownHostException e) {
			e.printStackTrace();

		} catch (SocketException e) {

			e.printStackTrace();
		}

		return false;
	}


	/*
	 * public static void main(String[] args) {
	 * 
	 * InetAddress ip; try {
	 * 
	 * ip = InetAddress.getLocalHost(); System.out.println("Current IP address : " +
	 * ip.getHostAddress());
	 * 
	 * NetworkInterface network = NetworkInterface.getByInetAddress(ip);
	 * 
	 * byte[] mac = network.getHardwareAddress();// Move this line up
	 * 
	 * System.out.print("Current MAC address : ");
	 * 
	 * StringBuilder sb = new StringBuilder(); for (int i = 0; i < mac.length; i++)
	 * { sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" :
	 * "")); } System.out.println(sb.toString()); if (isVMMac()) {
	 * 
	 * System.out.println("This program is in a VM."); } else {
	 * System.out.println("This program is not in a VM."); }
	 * 
	 * } catch (UnknownHostException e) {
	 * 
	 * e.printStackTrace();
	 * 
	 * } catch (SocketException e) {
	 * 
	 * e.printStackTrace();
	 * 
	 * }
	 * 
	 * }
	 */

}
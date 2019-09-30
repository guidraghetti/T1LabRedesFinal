package view;

import java.io.*;
import java.net.*;

import com.sun.tools.javac.Main;

class ClientUDP {
	public static void main(String args[]) throws Exception {
		while (true) {
			byte[] sendData = new byte[1024];
			byte[] receiveData = new byte[1024];
			System.out.println("BEM VINDO AO GAME!");
			System.out.println("-------------------------");
			System.out.println("Para jogar digite um dos comandos do jogo abaixo");
			System.out.println("Lista de comandos do jogo:\n" + "examinar\n" + "mover\n" + "pegar\n" + "largar\n"
					+ "mostrarInventario\n" + "usar\n" + "falar\n" + "cochichar\n");
			BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
			sendData = selecionaMetodo(inFromUser.readLine()).getBytes();
			DatagramSocket clientSocket = new DatagramSocket();
			InetAddress IPAddress = InetAddress.getByName("127.0.0.1");
			DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 9876);
			clientSocket.send(sendPacket);
			DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
			clientSocket.receive(receivePacket);
			String modifiedSentence = new String(receivePacket.getData());
			System.out.println("FROM SERVER:" + modifiedSentence);
			clientSocket.close();
		}
	}

	public static String selecionaMetodo(String s) throws Exception {
		switch (s) {
		case "login":
			return login();
		case "falar":
			return msgSala();
		case "cochicar":
			return msgUsuario();
		case "ajuda":
			return ajuda();
		case "examinar":
			return examinar();
		case "usar":
			return usar();
		case "largar":
			return largar();
		case "mostrarInventario":
			return inventario();
		case "pegar":
			return pegar();
		case "mover":
			return mover();
		}
		return "";

	}

	public static String ajuda() {
		return "ajuda:";
	}

	public static String usar() {
		return "usar:";
	}

	public static String examinar() {
		return "examinar:";
	}

	public static String largar() {
		return "largar:";
	}

	public static String inventario() {
		return "mostrarInventario:";
	}

	public static String pegar() {
		return "pegar:";
	}

	public static String mover() throws Exception {
		System.out.println("Escolha uma direção: N, S, L, O");
		BufferedReader msgFromUser = new BufferedReader(new InputStreamReader(System.in));
		String msg = msgFromUser.readLine();
		return "mover: " + msg;
	}

	public static String msgSala() throws Exception {
		System.out.println("Digite aqui sua mensagem:");
		BufferedReader msgFromUser = new BufferedReader(new InputStreamReader(System.in));
		String msg = msgFromUser.readLine();
		return "falar: "+ msg;
	}

	public static String msgUsuario() throws IOException {
		System.out.println("Digite aqui o nickname do destinatário:");
		BufferedReader msgFromUserNick = new BufferedReader(new InputStreamReader(System.in));
		String nick = msgFromUserNick.readLine();
		System.out.println("Digite aqui sua mensagem:");
		BufferedReader msgFromUser = new BufferedReader(new InputStreamReader(System.in));
		String msg = msgFromUser.readLine();
		return "cochichar: " + nick + "," + msg;
	}

	public static String login() throws Exception {
		System.out.println("Digite aqui seu nickname:");
		BufferedReader msgFromUser = new BufferedReader(new InputStreamReader(System.in));
		String msg = msgFromUser.readLine();
		System.out.println("Digite aqui seu IP:");
		BufferedReader msgFromUserIP = new BufferedReader(new InputStreamReader(System.in));
		String IP = msgFromUserIP.readLine();
		return "login: "+ IP + "," + msg;
	}
}
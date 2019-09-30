package view;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

import controller.AcoesController;
import model.Objeto;
import model.Sala;
import model.SimulaBanco;
import model.Usuario;

public class ServerUDP {
	
	AcoesController acao = new AcoesController();
	static String ipAlvo = "";
	public static void main(String args[]) throws Exception {
		List<Usuario> lstUser = new ArrayList<Usuario>();
		ServerUDP server = new ServerUDP();
		SimulaBanco smlBank = new SimulaBanco();

		//startBanco(smlBank);
		// selecionar ação ao receber string
		DatagramSocket serverSocket = new DatagramSocket(9876);
		byte[] receiveData = new byte[1024];
		byte[] sendData = new byte[1024];
		while (true) {
			DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
			serverSocket.receive(receivePacket);
			String sentence = new String(receivePacket.getData());
			//damos split nessa string, pois a metodo[0] nos dará qual metodo da controller deve ser chamado;
			String[] metodo = sentence.split(":");
			//caso login commita novo usuario
			InetAddress IPAddress = receivePacket.getAddress();
			int port = receivePacket.getPort();
			String aux =server.selecionaAcao(metodo, smlBank, IPAddress.toString());
			InetAddress IPDestino = InetAddress.getByName(ipAlvo);
			sendData = aux.getBytes();
			DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPDestino, port);
			serverSocket.send(sendPacket);
		}
	}
	
	public static void startBanco(SimulaBanco banco) {
		Sala root = new Sala();
		root.isLocked = false;
		
		//cria objetos
		List<Objeto> lstObjetos = new ArrayList<Objeto>();
		Objeto obj = new Objeto();
		obj.tipo = "key";
		Objeto obj1 = new Objeto();
		obj1.tipo = "door";
		lstObjetos.add(obj);
		obj.lstAdjascentes.add(obj1);
		root.lstObjetos = lstObjetos;  
		
		//
		banco.linkedSala = root;
		//create fist sala root
		
		Sala child = new Sala();
		child.isLocked = false;
		
		//cria objetos
		lstObjetos = new ArrayList<Objeto>();
		obj = new Objeto();
		obj.tipo = "key";
		obj = new Objeto();
		obj1.tipo = "door";
		
		obj.lstAdjascentes.add(obj1);
		
		lstObjetos.add(obj);
		child.lstObjetos = lstObjetos;  
		
		
		
		banco.linkedSala.norte = child;
		
		child = new Sala();
		child.isLocked = true;
		
		//cria objetos
		lstObjetos = new ArrayList<Objeto>();
		obj = new Objeto();
		obj.tipo = "key";
		obj = new Objeto();
		obj1.tipo = "door";
		
		obj.lstAdjascentes.add(obj1);
		
		lstObjetos.add(obj);
		child.lstObjetos = lstObjetos;  
		
		
		
		banco.linkedSala.leste = child;
		
		child = new Sala();
		child.isLocked = true;
		
		//cria objetos
		lstObjetos = new ArrayList<Objeto>();
		obj = new Objeto();
		obj.tipo = "key";
		obj = new Objeto();
		obj1.tipo = "door";
		
		obj.lstAdjascentes.add(obj1);
		
		lstObjetos.add(obj);
		child.lstObjetos = lstObjetos;  
		
		
		banco.linkedSala.oeste = child;
	
		child = new Sala();
		child.isLocked = true;
		
		//cria objetos
		lstObjetos = new ArrayList<Objeto>();
		obj = new Objeto();
		obj.tipo = "key";
		obj = new Objeto();
		obj1.tipo = "door";
		
		obj.lstAdjascentes.add(obj1);
		
		lstObjetos.add(obj);
		child.lstObjetos = lstObjetos;  
		
		banco.linkedSala.sul = child;
	}
	
	//nos metodos de {LOGIN, FALAR, COCHICHAR E MOVER}, passo uma string inteira e temos que transformá-la em objeto dentro do metodo no controller
	public  String selecionaAcao(String[] metodo, SimulaBanco banco, String ip) {
		switch (metodo[0]) {
		case "login":
			return login(metodo[1], banco);
		case "falar":
			return acao.falar(metodo[1]);
		case "cochicar":
			return findUser(metodo[1], banco, ip);
		case "ajuda":
			 return acao.ajuda();
		case "examinar":
			return examinar(banco, ip);
		case "usar":
			return usar(banco, ip);
		case "largar":
			return largar(banco, ip);
		case "mostrarInventario":
			return mostrarInventario (banco, ip);
		case "pegar":
			return pegar(banco, ip);
		case "mover":
			return mover(metodo[1], banco, ip);
		default:
			return "método não encontrado";
		}

	}

	public String login (String login, SimulaBanco banco) {
		System.out.println(banco.lstUsuario);
		return acao.Login(login, banco);
		
	
	}
	public String findUser(String string, SimulaBanco banco, String ip) {
		String[] separaUsuarioMensagem = string.split(",");
		Usuario alvo = banco.findUserByNickname(separaUsuarioMensagem[0]);
		ipAlvo = alvo.IP;
		Usuario remetente = banco.findUserByIP(ip);
		System.out.println(remetente.toString());
		return acao.cochichar(separaUsuarioMensagem[1], alvo, remetente);
		
		
	}
	public String examinar (SimulaBanco banco, String ip) {
		Usuario u = banco.findUserByIP(ip);
		return acao.examinar(u.sala);
	}
	
	public String usar (SimulaBanco banco, String ip) {
		Usuario u = banco.findUserByIP(ip);
		return acao.usar(u.lstObjetos.get(0), u.sala);
	}
	public String largar (SimulaBanco banco, String ip) {
		Usuario u = banco.findUserByIP(ip);
		return acao.largar(u, u.lstObjetos.get(0));
	}
	
	public String mostrarInventario (SimulaBanco banco, String ip) {
		Usuario u = banco.findUserByIP(ip);
		System.out.println(u.toString());
		return acao.mostrarInventario(u);
		
	}
	
	public String pegar (SimulaBanco banco, String ip) {
		Usuario u = banco.findUserByIP(ip);
		return acao.pegar(u);
	}
	public String mover (String direcao, SimulaBanco banco, String ip) {
		Usuario u = banco.findUserByIP(ip);
		u.sala = acao.mover(direcao, u.sala);
		return "Usuário moveu-se";
		
	}
}
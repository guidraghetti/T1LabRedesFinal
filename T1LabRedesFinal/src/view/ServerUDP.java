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
	public static void main(String args[]) throws Exception {
		List<Usuario> lstUser = new ArrayList<Usuario>();
		ServerUDP server = new ServerUDP();
		SimulaBanco smlBank = new SimulaBanco();
		startBanco(smlBank);
		// selecionar ação ao receber string
		DatagramSocket serverSocket = new DatagramSocket(9876);
		byte[] receiveData = new byte[1024];
		byte[] sendData = new byte[1024];
		while (true) {
			DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
			serverSocket.receive(receivePacket);
			String sentence = new String(receivePacket.getData());
			//damos split nessa string, pois a metodo[0] nos dará qual metodo da controller deve ser chamado;
			String[] metodo = sentence.split(":", 1);
			//caso login commita novo usuario
			server.selecionaAcao(metodo);
			InetAddress IPAddress = receivePacket.getAddress();
			
			int port = receivePacket.getPort();
			String capitalizedSentence = sentence.toUpperCase();
			sendData = capitalizedSentence.getBytes();
			DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
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
		
		obj.lstAdjascentes.add(obj1);
		
		lstObjetos.add(obj);
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
	public  void selecionaAcao(String[] metodo) {
		switch (metodo[0]) {
		case "login":
			acao.Login(metodo[1]);
		case "falar":
			acao.falar(metodo[1]);
		case "cochicar":
			acao.c(metodo[1]);
		case "ajuda":
			 acao.ajuda();
		case "examinar":
			acao.examinar(obj);
		case "usar":
			acao.usar(obj, sala)
		case "largar":
			acao.largar(metodo[1]);
		case "mostrarInventário":
			acao.mostrarInventario(u);
		case "pegar":
			acao.pegar(u, obj);
		case "mover":
			acao.mover(metodo[1], s);

		}
		

	}
}
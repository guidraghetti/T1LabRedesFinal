package model;

import java.util.ArrayList;
import java.util.List;

public class SimulaBanco {
	public Sala linkedSala;
	public List<Usuario> lstUsuario;
	public List<Objeto> lstObjeto;
	
	
	public SimulaBanco() {
		this.linkedSala = new Sala();
		this.lstUsuario = new ArrayList<Usuario>();
		this.lstObjeto = new ArrayList<Objeto>();
	}
	
	public Sala getLstSala() {
		return linkedSala;
	}
	public List<Usuario> getLstUsuario() {
		return lstUsuario;
	}
	public List<Objeto> getLstObjeto() {
		return lstObjeto;
	}
	
	public void addObjeto(Objeto o) {
		this.lstObjeto.add(o);
	}
	
	public Usuario findUserByIP(String IP) {
		for (Usuario usuario : lstUsuario) {
			if(usuario.IP.equals(IP)) {
				return usuario;
			}
		}
		return null;
	}
	
	public Usuario findUserByNickname (String nick) {
		for (Usuario usuario: lstUsuario) {
			if (usuario.nick.equals(nick)) {
				return usuario;
			}
		} return null;
	}
	public void addUsuario(Usuario u) {
		this.lstUsuario.add(u);
	}
	
	public void removeObjeto(Objeto u) {
		this.lstObjeto.remove(u);
	}
	
}

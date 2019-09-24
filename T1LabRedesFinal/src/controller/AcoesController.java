package controller;

import model.Sala;

import java.util.ArrayList;
import java.util.List;

import model.Objeto;
import model.Usuario;
import model.Objeto;

public class AcoesController{
    
    List<Usuario> lstUser = new ArrayList<>();

    public String examinar(Object obj){
        if(obj instanceof Sala && ((Sala)obj).isLocked){
            return "Trancado filhão";
        }
        if(obj instanceof Sala || obj instanceof Objeto){
            return obj.toString();
        }else{
            return "Só podemos examinar objetos e salas";
        }
    }

    public Sala mover(String c, Sala salaAtual){
        Sala aux = new Sala();
        if(c == "N"){
            examinar(salaAtual.norte);
            aux = salaAtual.norte;
        }else if(c == "L"){
            examinar(salaAtual.leste);
            aux = salaAtual.leste;
        }else if(c == "O"){
            examinar(salaAtual.oeste);
            aux = salaAtual.oeste;
        }else if(c == "S"){
            examinar(salaAtual.sul);
            aux = salaAtual.sul;
        }

        return aux;
    }

    public String pegar(Usuario u, Objeto obj){
        String msg = "";
        if(u.sala.lstObjetos.size() != 0){
            u.lstObjetos.add(u.sala.lstObjetos.get(0));
            u.sala.lstObjetos.remove(0);
            msg = "Objeto Encontrado";
        }else{
            msg = "Nenhum objeto encontrado!";
        }
        return msg;
    }

    public String largar(Usuario u, Objeto obj){
        String msg = "";
        if(u.lstObjetos.size() == 0){
            msg = "Nenhum Objeto encontrado!";
        }else{
            u.sala.lstObjetos.add(u.sala.lstObjetos.get(0));
            u.lstObjetos.remove(0);
            msg = "O Objeto " + u.sala.lstObjetos.get(0).toString() + "foi largado!";
        }
        return msg;
    }

    public String mostrarInventario(Usuario u ){
        String msg = "Os objetos de " + u.nick + " : ";

        if(u.lstObjetos.size() == 0){
            msg = "Nenhum objeto em seu inventário!";
            return msg;
        }

        for (Objeto obj : u.lstObjetos) {
            msg = msg + obj.toString() + " ";
        }

        return msg;
    }

    public String usar(Objeto obj, Sala sala){
        if(sala.lstObjetos.size() == 0){
            return "não existe nenhum alvo na sala";
        }
        for(int i = 0; i < sala.lstObjetos.size(); i++){
            if(obj.lstAdjascentes.contains(sala.lstObjetos.get(i))){
                return obj.toString() + " usado com sucesso";
            }
        }
        return "Seu objeto não pode ser usado na sala";
       
    }

    public String falar(String msg){
        return msg;
    }

    public String cochichar(String msg, Usuario alvo, Usuario remetente){
        String msgErro = "";
        if(alvo.sala == remetente.sala){
            return msg;
        }else{
            msgErro = "Usuario " + alvo.nick + " não encontrado na sua sala";
        }

        return msgErro;
    }

    public String Login(String login){
    	String[] lstLogin = login.split(",");
        for(Usuario user : lstUser) {
            if(user.nick == lstLogin[1]){
                return "Nick já existente";
            }
        }
        Usuario userNew = new Usuario();
        userNew.nick = lstLogin[1];
        userNew.IP = lstLogin[0];
        lstUser.add(userNew);

        return "Usuario logado com sucesso";

    }

    public String ajuda(){
        return 
        "Examinar [sala/objeto]" +
        "o Retorna a descrição da sala atual (sala) ou objeto mencionado. " +
        "o A descrição da sala também deve listar as salas adjacentes e suas " +
        "respectivas direções, objetos e demais jogadores presentes no " +
        "local. " +
        " Mover [N/S/L/O] " +
        "o O jogador deve mover-se para a direção indicada (norte, sul, leste " +
        "ou oeste). " +
        "o Ao entrar numa nova sala, o jogo deve executar automaticamente " +
        "o comando “examinar sala, que descreve o novo ambiente ao " +
        "jogador. " +
        " Pegar [objeto] " +
        "o O jogador coleta um objeto que está na sala atual. " +
        "o Alguns objetos não podem ser coletados, como no caso de porta. " +
        " Largar [objeto] " +
        "o O jogador larga um objeto que está no seu inventório, na sala atual. " +
        " Inventório " +
        "o O jogo lista todos os objetos carregados atualmente pelo jogador. " +
        " Usar [objeto] {alvo} " +
        "o O jogador usa o objeto mencionado; " +
        "o Em alguns casos específicos, o objeto indicado necessitará de outro " +
        "(alvo) para ser ativado (ex: usar chave porta). " +
        " Falar [texto] " +
        "o O jogador envia um texto que será retransmitido para todos os " +
        "jogadores presentes na sala atual. " + 
        " Cochichar [texto] [jogador]" +
        "o O jogador envia uma mensagem de texto apenas para o jogador" +
        "especificado, se ambos estiverem na mesma sala. ";
    }
}

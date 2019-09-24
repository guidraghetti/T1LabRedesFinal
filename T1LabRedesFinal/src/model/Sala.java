package model;

import java.util.ArrayList;
import java.util.*;

public class Sala{
    public String nome;
    public Sala norte;
    public Sala sul;
    public Sala leste;
    public Sala oeste;
    public Boolean isLocked = false;
    public List<Objeto> lstObjetos = new ArrayList<>();
    
    
}
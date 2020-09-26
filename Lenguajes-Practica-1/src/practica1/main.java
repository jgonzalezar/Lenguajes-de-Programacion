/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practica1;


import java.util.*;


public class main {
    private static String lexema = "";
   /*public static HashMap<String, Character> sim_simple = new HashMap<>();
   public static HashMap<String, String> sim_simple_rechazado = new HashMap<>();
   public static HashMap<String, String> sim_compuesto = new HashMap<>();
   public static ArrayList<String> reservadas = new ArrayList<>();
   public main(){
       // Lista de simbolos simples válidos
        sim_simple.put("tk_mayor",'>');
        sim_simple.put("tk_menor",'<');
        sim_simple.put("tk_llave_izq",'{');
        sim_simple.put("tk_llave_der",'}');
        sim_simple.put("tk_par_izq",'(');
        sim_simple.put("tk_par_der",')');
        sim_simple.put("tk_mas",'+');
        sim_simple.put("tk_menos",'-');
        sim_simple.put("tk_mul",'*');
        sim_simple.put("tk_div",'/');
        sim_simple.put("tk_mod",'%');
        sim_simple.put("tk_coma",',');
        sim_simple.put("tk_puntoycoma",';');
        sim_simple.put("tk_dospuntos",':');
        //
        
        //Lista de Simbolos simples invalidos
        sim_simple_rechazado.put("tk_igual","=");        
        sim_simple_rechazado.put("tk_admiración","!");
        //
        
        //lista de simbolos compuestos válidos
        sim_compuesto.put("tk_mayor_igual",">=");
        sim_compuesto.put("tk_menor_igual","<=");
        sim_compuesto.put("tk_asignacion",":=");
        sim_compuesto.put("tk_sum_asig","+=");
        sim_compuesto.put("tk_res_asig","-=");
        sim_compuesto.put("tk_mul_asig","*=");
        sim_compuesto.put("tk_div_asig","/=");
        sim_compuesto.put("tk_mod_asig","%=");
        sim_compuesto.put("tk_igualdad","==");
        sim_compuesto.put("tk_diferente","!=");
        sim_compuesto.put("tk_incremento","++");
        sim_compuesto.put("tk_decremento","--");
        //

        //Lista de palabras reservadas
        reservadas.add("function");
        reservadas.add("var");
        reservadas.add("num");
        reservadas.add("bool");
        reservadas.add("true");
        reservadas.add("false");
        reservadas.add("print");
        reservadas.add("input");
        reservadas.add("when");
        reservadas.add("do");
        reservadas.add("if");
        reservadas.add("unless");
        reservadas.add("while");
        reservadas.add("return");
        reservadas.add("until");
        reservadas.add("loop");
        reservadas.add("repeat num");//duda
        reservadas.add("for");
        reservadas.add("end");
        reservadas.add("next");
        reservadas.add("break");
        reservadas.add("and");
        reservadas.add("or");
   }
    public class Token{
        public int fila;
        public int columna;
        public String lexema;
        public static final int
            ID =1,
            NUM =2,
            FID =3,
            RESERVADA =4;


    }*/
    
    public static boolean esDigito(char x){
        if ((int)x >=48 && (int)x <=57){
            return  true;
        }
        return false;
    }

    public static boolean esLetra(char x){
        if ((int)x >=97 && (int)x <=122){
            return  true;
        }
        return false;
    }
    
    public static boolean esNumero(String s){
        int ac=0;
        for(int i=0;i<s.length();i++){
            if(esDigito(s.charAt(i))) ac++;
        }
        if(ac==s.length())return true;
        return false;
    }
    
    public static boolean esPalabra(String s){
        if(esLetra(s.charAt(0))) return true;
        return false;
    }
    
    public static int deltaFunction(int estado,char c, HashMap<String,String> sim_simple, HashMap<String, String> sim_simple_rechazado,HashMap<String, String> sim_compuesto){
        int retorno = 0; // -1 = error, 0 = aceptacion
        switch(estado){
            case 0:
                if(esLetra(c))return 1;
                else if(c=='@')return 2;
                else if(sim_simple.containsValue(Character.toString(c))){
                    lexema += c;
                    return 5;
                }
                else if(sim_simple_rechazado.containsValue(Character.toString(c))){
                    lexema +=c;
                    return 8;
                }
                else if(esDigito(c))return 10;
                else retorno=-1; //error
            break;
            case 1:
                if(esLetra(c) || esDigito(c))return 1;
                else retorno = 0; //aceptacion
            break;
            case 2:
                if(esLetra(c))return 3;
                else retorno = -1;
            break;
            case 3:
                if(esLetra(c) || esDigito(c))return 3;
                else retorno =4;
            break;
            case 4:
                retorno = 0;
            break;
            case 5:
                if(sim_compuesto.containsValue(lexema+c))return 6;
                else retorno = 7;
                //
            break;
            case 6:
                lexema="";
                retorno=0;
            break;
            case 7:
                lexema="";
                retorno=0;
            break;
            case 8:
                if(sim_compuesto.containsValue(lexema+c)) return 9;
                else retorno =-1;
            break;
            case 9:
                lexema="";
                retorno = 0;
            break;
            case 10:
                if(esDigito(c))return 10;
                else if(c=='.')return 12;
                else retorno=11;
            break;
            case 11:
                retorno =0;
            break;
            case 12:
                if(esDigito(c))return 13;
                else retorno=-1;
            break;
            case 13:
                if(esDigito(c))return 13;
                else retorno=14;
                break;
            case 14:
                retorno=0;
                break;
            default:
            //error
            
        }
        return retorno;
    }

    public static boolean esPalabraReservada(ArrayList<String> reservadas, String palabra){
        for(int i=0;i<reservadas.size();i++){
            if(reservadas.get(i).equals(palabra))return  true;
        }
        return false;
    }

    public static void sendError(int fila, int col){
        System.out.println(">>>Error lexico(linea:"+fila+",posicion:"+col);
    }
    public static String getKey(HashMap<String,String> map, String value){
        String keyReturn="";
        for(String key : map.keySet()){
            if(value.equals(map.get(key)))keyReturn=key;
        }
        return  keyReturn;
    }

    public static void makeToken(String cadena, HashMap<String,String> sim_simple, HashMap<String, String> sim_simple_rechazado,HashMap<String, String> sim_compuesto, ArrayList<String> reservadas){
        int columna = 0;
        String token = "";
        int ac=0;
       for(int i=0;i<cadena.length();i++){
           int estado = deltaFunction(0,cadena.charAt(i), sim_simple, sim_simple_rechazado, sim_compuesto);
           switch(estado){
               case 1:
                   token +=cadena.charAt(i);
                   ac = i+1;
                   while(ac<cadena.length() && (deltaFunction(estado,cadena.charAt(ac), sim_simple, sim_simple_rechazado, sim_compuesto) ==1)){
                        token +=cadena.charAt(ac);
                        ac++;
                   }
                   i=ac-1;
                   if(esPalabraReservada(reservadas,token)) System.out.println("<"+ token +">");
                   else System.out.println("<id,"+token+">");
                   token="";
                   break;
               case 2:
                   token +=cadena.charAt(i);
                   ac = i+1;
                   if(deltaFunction(estado,cadena.charAt(ac), sim_simple, sim_simple_rechazado, sim_compuesto) ==3){
                       while(ac<cadena.length() && (deltaFunction(estado+1,cadena.charAt(ac), sim_simple, sim_simple_rechazado, sim_compuesto) ==3)){
                           token +=cadena.charAt(ac);
                           ac++;
                       }
                       i=ac-1;
                       System.out.println("<fid,"+token+">");
                       token="";
                   }else{
                       sendError(0,0);
                   }
                   //duda
                   break;
               case 5:
                   token+=cadena.charAt(i);
                   ac=i+1;
                   if(ac<cadena.length() && deltaFunction(estado,cadena.charAt(ac), sim_simple, sim_simple_rechazado, sim_compuesto) ==6){
                       token+=cadena.charAt(ac);
                       System.out.println("<"+getKey(sim_compuesto,token)+">");
                       i=ac-1;
                   }
                   token="";
                   break;
           }
       }
    }

    public static void main(String[] args) {
        HashMap<String, String> sim_simple = new HashMap<>();
        HashMap<String, String> sim_simple_rechazado = new HashMap<>();
        HashMap<String, String> sim_compuesto = new HashMap<>();
        ArrayList<String> reservadas = new ArrayList<>();
        // Lista de simbolos simples válidos
        sim_simple.put("tk_mayor",">");
        sim_simple.put("tk_menor","<");
        sim_simple.put("tk_llave_izq","{");
        sim_simple.put("tk_llave_der","}");
        sim_simple.put("tk_par_izq","(");
        sim_simple.put("tk_par_der",")");
        sim_simple.put("tk_mas","+");
        sim_simple.put("tk_menos","-");
        sim_simple.put("tk_mul","*");
        sim_simple.put("tk_div","/");
        sim_simple.put("tk_mod","%");
        sim_simple.put("tk_coma",",");
        sim_simple.put("tk_puntoycoma",";");
        sim_simple.put("tk_dospuntos",":");
        //
        
        //Lista de Simbolos simples invalidos
        sim_simple_rechazado.put("tk_igual","=");        
        sim_simple_rechazado.put("tk_admiración","!");
        //
        
        //lista de simbolos compuestos válidos
        sim_compuesto.put("tk_mayor_igual",">=");
        sim_compuesto.put("tk_menor_igual","<=");
        sim_compuesto.put("tk_asignacion",":=");
        sim_compuesto.put("tk_sum_asig","+=");
        sim_compuesto.put("tk_res_asig","-=");
        sim_compuesto.put("tk_mul_asig","*=");
        sim_compuesto.put("tk_div_asig","/=");
        sim_compuesto.put("tk_mod_asig","%=");
        sim_compuesto.put("tk_igualdad","==");
        sim_compuesto.put("tk_diferente","!=");
        sim_compuesto.put("tk_incremento","++");
        sim_compuesto.put("tk_decremento","--");
        //

        //Lista de palabras reservadas
        reservadas.add("function");
        reservadas.add("var");
        reservadas.add("num");
        reservadas.add("bool");
        reservadas.add("true");
        reservadas.add("false");
        reservadas.add("print");
        reservadas.add("input");
        reservadas.add("when");
        reservadas.add("do");
        reservadas.add("if");
        reservadas.add("unless");
        reservadas.add("while");
        reservadas.add("return");
        reservadas.add("until");
        reservadas.add("loop");
        reservadas.add("repeat");//duda
        reservadas.add("for");
        reservadas.add("end");
        reservadas.add("next");
        reservadas.add("break");
        reservadas.add("and");
        reservadas.add("or");
        reservadas.add("not");
        Scanner x= new Scanner(System.in);
        Object linea = x.nextLine();
        int fila = 0;
        int col = 0;
        while(linea != null){
            if(!linea.equals("")) {
                makeToken(linea.toString(), sim_simple, sim_simple_rechazado, sim_compuesto, reservadas);
            }
            fila++;
            linea = x.nextLine();
            }
            /*System.out.println(deltaFunction(0,'+',sim_simple,sim_simple_rechazado,sim_compuesto));
            System.out.println(deltaFunction(5,'+',sim_simple,sim_simple_rechazado,sim_compuesto))*/;
        }

}

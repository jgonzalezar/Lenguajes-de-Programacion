/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practica1;


import java.util.*;

public class main {
    /*public class Token{
        public int fila;
        public int columna;
        public String lexema;
        public static final int
            ID =1,
            NUM =2,
            FID =3,
            RESERVADA =4;


    }*/

    public static int deltaFunction(int estado,char c){
        return 0;
    }

    public static boolean esNumero(char x){
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

    public static boolean esPalabraReservada(ArrayList<String> reservadas, String palabra){
        for(int i=0;i<reservadas.size();i++){
            if(reservadas.get(i).equals(palabra))return  true;
        }
        return false;
    }

    public static void sendError(int fila, int col){
        System.out.println(">>>Error lexico(linea:"+fila+",posicion:"+col);
    }

    public static void makeToken(String cadena){
        String palabra = "";
        String numero = "";
        int ac = 0;
        /*for(int i=0;i<cadena.length();i++){
            if(esNumero(cadena.charAt(i))){
                ac= i;
                while (ac<cadena.length() && esNumero(cadena.charAt(ac)) && cadena.charAt(ac)!=' ' && cadena.charAt(ac)!='\n'){
                    System.out.println(ac);
                    numero+=cadena.charAt(ac);
                    ac++;
                }
                i=ac;
                ac = 0;
            }else if(esLetra(cadena.charAt(i))){
                ac= i;
                while (ac<cadena.length() && esLetra(cadena.charAt(ac)) && cadena.charAt(ac)!=' ' && cadena.charAt(ac)!='\n'){
                    System.out.println(ac);
                    palabra+=cadena.charAt(ac);
                    ac++;
                }
                i=ac;
                ac = 0;
            }
        }
        System.out.println(numero);
        System.out.println(palabra);
        */
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
        reservadas.add("repeat num");//duda
        reservadas.add("for");
        reservadas.add("end");
        reservadas.add("next");
        reservadas.add("break");
        reservadas.add("and");
        reservadas.add("or");
        Scanner x= new Scanner(System.in);
        Object linea = x.nextLine();
        int fila = 0;
        int col = 0;
        while(linea != null){
            if(!linea.equals("")) {
                makeToken(linea.toString());
                /*for(int i=0;i<linea.toString().length();i++){
                    System.out.println(linea.toString().toCharArray()[i]);
                }
                System.out.println(linea.toString().length());*/
            }
            fila++;
            linea = x.nextLine();
            }
        }

}

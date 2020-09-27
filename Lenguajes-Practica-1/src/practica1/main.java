/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practica1;

import java.util.*;

public class main {

    private static String lexema = "";

  /*
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

    public static boolean esDigito(char x) {
        if ((int) x >= 48 && (int) x <= 57) {
            return true;
        }
        return false;
    }

    public static boolean esLetra(char x) {
        if (((int) x >= 97 && (int) x <= 122) || ((int) x >=65 && (int) x <= 90)) {
            return true;
        }
        return false;
    }

    public static boolean esNumero(String s) {
        int ac = 0;
        for (int i = 0; i < s.length(); i++) {
            if (esDigito(s.charAt(i))) {
                ac++;
            }
        }
        if (ac == s.length()) {
            return true;
        }
        return false;
    }

    public static boolean esPalabra(String s) {
        if (esLetra(s.charAt(0))) {
            return true;
        }
        return false;
    }

    public static int deltaFunction(int estado, char c, HashMap<String, String> sim_simple, HashMap<String, String> sim_simple_rechazado, HashMap<String, String> sim_compuesto) {
        int retorno = 0; // -1 = error, 0 = aceptacion
        switch (estado) {
            case 0:
                if (esLetra(c)) {
                    return 1;
                } else if (c == '@') {
                    return 2;
                } else if (sim_simple.containsValue(Character.toString(c))) {
                    lexema += c;
                    return 5;
                } else if (sim_simple_rechazado.containsValue(Character.toString(c))) {
                    lexema += c;
                    return 8;
                } else if (esDigito(c)) {
                    return 10;
                } else {
                    retorno = -1; //error
                }
                break;
            case 1:
                if (esLetra(c) || esDigito(c)) {
                    return 1;
                } else {
                    retorno = 0; //aceptacion
                }
                break;
            case 2:
                if (esLetra(c)) {
                    return 3;
                } else {
                    retorno = -1;
                }
                break;
            case 3:
                if (esLetra(c) || esDigito(c)) {
                    return 3;
                } else {
                    retorno = 4;
                }
                break;
            case 4:
                retorno = 0;
                break;
            case 5:
                if (sim_compuesto.containsValue(lexema + c)) {
                    lexema += c;
                    return 6;
                } else {
                    retorno = 7;
                }
                //
                break;
            case 6:
                lexema = "";
                retorno = 0;
                break;
            case 7:
                lexema = "";
                retorno = 0;
                break;
            case 8:
                if (sim_compuesto.containsValue(lexema + c)) {
                    return 9;
                } else {
                    retorno = -1;
                }
                break;
            case 9:
                lexema = "";
                retorno = 0;
                break;
            case 10:
                if (esDigito(c)) {
                    return 10;
                } else if (c == '.') {
                    return 12;
                } else {
                    retorno = 11;
                }
                break;
            case 11:
                retorno = 0;
                break;
            case 12:
                if (esDigito(c)) {
                    return 13;
                } else {
                    retorno = -1;
                }
                break;
            case 13:
                if (esDigito(c)) {
                    return 13;
                } else {
                    retorno = 14;
                }
                break;
            case 14:
                retorno = 0;
                break;
            default:
            //error

        }
        return retorno;

    }

    public static boolean esPalabraReservada(ArrayList<String> reservadas, String palabra) {
        for (int i = 0; i < reservadas.size(); i++) {
            if (reservadas.get(i).equals(palabra)) {
                return true;
            }
        }
        return false;
    }

    public static void sendError(int fila, int col) {
        System.out.println(">>>Error léxico(linea:" + fila + ",posición:" + col+")");
        System.exit(0);
    }

    public static String getKey(HashMap<String, String> map, String value) {
        String keyReturn = "";
        for (String key : map.keySet()) {
            if (value.equals(map.get(key))) {
                keyReturn = key;
            }
        }
        return keyReturn;
    }

    public static void makeToken(String cadena, HashMap<String, String> sim_simple, HashMap<String, String> sim_simple_rechazado, HashMap<String, String> sim_compuesto, ArrayList<String> reservadas, int fila) {
        int columna = 0;
        String token = "";
        int ac = 0;
        for (int i = 0; i < cadena.length(); i++) {
            columna= i+1;
            switch(cadena.charAt(i)){
                case ' ':
                    columna++;
                    break;
                case '\t':
                    columna += 4;
                    break;
                case '#':
                    i = cadena.length();
                    break;
                case '\n':
                    System.out.println("prueba");
                    break;
                default:
                    int estado = deltaFunction(0, cadena.charAt(i), sim_simple, sim_simple_rechazado, sim_compuesto);
                    switch (estado) {
                        case 1:
                            token += cadena.charAt(i);
                            ac = i + 1;
                            while (ac < cadena.length() && (deltaFunction(estado, cadena.charAt(ac), sim_simple, sim_simple_rechazado, sim_compuesto) == 1)) {
                                System.out.println("prueba1");
                                token += cadena.charAt(ac);

                                ac++;
                            }
                            i = ac - 1;
                            if (esPalabraReservada(reservadas, token)) {

                                System.out.println("<" + token + ","+fila+","+columna+">");
                            } else {
                                System.out.println("<id," + token + ","+fila+","+columna+">");
                            }
                            token = "";
                            break;
                        case 2:
                            token += cadena.charAt(i);
                            ac = i + 1;
                            if (deltaFunction(estado, cadena.charAt(ac), sim_simple, sim_simple_rechazado, sim_compuesto) == 3) {
                                while (ac < cadena.length() && (deltaFunction(estado + 1, cadena.charAt(ac), sim_simple, sim_simple_rechazado, sim_compuesto) == 3)) {
                                    System.out.println("prueba2");
                                    token += cadena.charAt(ac);
                                    ac++;
                                }
                                i = ac - 1;
                                System.out.println("<fid," + token +","+fila+","+columna+ ">");
                                token = "";
                            } else {
                                sendError(fila, columna);
                            }
                            //duda
                            break;
                        case 5:
                            token += cadena.charAt(i);
                            ac = i+ 1;
                            if (ac < cadena.length() && deltaFunction(estado, cadena.charAt(ac), sim_simple, sim_simple_rechazado, sim_compuesto) == 6) {
                                token += cadena.charAt(ac);
                                deltaFunction(estado+1, cadena.charAt(ac), sim_simple, sim_simple_rechazado, sim_compuesto);
                                System.out.println("<" + getKey(sim_compuesto, token) +","+fila+","+columna+ ">");
                                //System.out.println("lexema"+lexema);

                            }else if (ac < cadena.length() && deltaFunction(estado, cadena.charAt(ac), sim_simple, sim_simple_rechazado, sim_compuesto) == 7) {
                                deltaFunction(estado+2, cadena.charAt(ac), sim_simple, sim_simple_rechazado, sim_compuesto);
                                System.out.println("<" + getKey(sim_simple, token) +","+fila+","+columna+ ">");
                                //System.out.println("lexema"+lexema);
                                ac--;
                                token="";

                            }else if(ac== cadena.length()){
                                deltaFunction(estado+2, cadena.charAt(ac-1), sim_simple, sim_simple_rechazado, sim_compuesto);
                                //System.out.println(token);
                                System.out.println("<" + getKey(sim_simple, token) + ","+fila+","+columna+">");
                                //System.out.println("lexema"+lexema);
                                i= ac;
                            }else{
                                sendError(fila, columna);
                            }
                            i = ac;
                            token="";
                            break;
                        case 8:
                            token += cadena.charAt(i);
                            ac = i + 1;
                            if (ac < cadena.length() && deltaFunction(estado, cadena.charAt(ac), sim_simple, sim_simple_rechazado, sim_compuesto) == 9) {
                                token += cadena.charAt(ac);
                                deltaFunction(estado+1, cadena.charAt(ac), sim_simple, sim_simple_rechazado, sim_compuesto);
                                System.out.println("<" + getKey(sim_compuesto, token) +","+fila+","+columna+ ">");
                            }else{
                                sendError(fila, columna);
                            }
                            i = ac;
                            token = "";
                            break;
                        case 10:
                            token += cadena.charAt(i);
                            ac = i + 1;
                            while (ac < cadena.length() && deltaFunction(estado, cadena.charAt(ac), sim_simple, sim_simple_rechazado, sim_compuesto) == 10) {
                                System.out.println("prueba3");
                                token += cadena.charAt(ac);
                                ac++;
                            }
                            if(ac < cadena.length() && deltaFunction(estado, cadena.charAt(ac), sim_simple, sim_simple_rechazado, sim_compuesto) == 11){
                                deltaFunction(estado+1, cadena.charAt(ac), sim_simple, sim_simple_rechazado, sim_compuesto);
                                System.out.println("<tk_num," + token +","+fila+","+columna+ ">");
                                ac--;
                            }else if(ac < cadena.length() && deltaFunction(estado, cadena.charAt(ac), sim_simple, sim_simple_rechazado, sim_compuesto) == 12){
                                token += cadena.charAt(ac);
                                if(ac < cadena.length() && deltaFunction(estado+2, cadena.charAt(ac+1), sim_simple, sim_simple_rechazado, sim_compuesto) == 13){
                                    deltaFunction(estado+2, cadena.charAt(ac+1), sim_simple, sim_simple_rechazado, sim_compuesto);
                                    token += cadena.charAt(ac+1);
                                    while (ac+1 < cadena.length() && deltaFunction(estado+3, cadena.charAt(ac+1), sim_simple, sim_simple_rechazado, sim_compuesto) == 13) {
                                        System.out.println("prueba4");
                                        token += cadena.charAt(ac+1);
                                        ac++;
                                    }
                                    deltaFunction(estado+4, cadena.charAt(ac), sim_simple, sim_simple_rechazado, sim_compuesto);
                                    System.out.println("<tk_num," + token +","+fila+","+columna+ ">");

                                }
                            }else{
                                sendError(fila, columna);
                            }
                    /*if(ac==cadena.length()){
                        System.out.println("<tk_num," + token + ">");
                        token="";
                    }*/

                            i = ac ;
                            token = "";
                            break;
                        default:
                            sendError(fila, columna);

                    }
                    token="";
            }

        }
    }

    public static void main(String[] args) {
        HashMap<String, String> sim_simple = new HashMap<>();
        HashMap<String, String> sim_simple_rechazado = new HashMap<>();
        HashMap<String, String> sim_compuesto = new HashMap<>();
        ArrayList<String> reservadas = new ArrayList<>();
        // Lista de simbolos simples válidos
        sim_simple.put("tk_mayor", ">");
        sim_simple.put("tk_menor", "<");
        sim_simple.put("tk_llave_izq", "{");
        sim_simple.put("tk_llave_der", "}");
        sim_simple.put("tk_par_izq", "(");
        sim_simple.put("tk_par_der", ")");
        sim_simple.put("tk_mas", "+");
        sim_simple.put("tk_menos", "-");
        sim_simple.put("tk_mul", "*");
        sim_simple.put("tk_div", "/");
        sim_simple.put("tk_mod", "%");
        sim_simple.put("tk_coma", ",");
        sim_simple.put("tk_puntoycoma", ";");
        sim_simple.put("tk_dospuntos", ":");
        //

        //Lista de Simbolos simples invalidos
        sim_simple_rechazado.put("tk_igual", "=");
        sim_simple_rechazado.put("tk_admiración", "!");
        //

        //lista de simbolos compuestos válidos
        sim_compuesto.put("tk_mayor_igual", ">=");
        sim_compuesto.put("tk_menor_igual", "<=");
        sim_compuesto.put("tk_asignacion", ":=");
        sim_compuesto.put("tk_sum_asig", "+=");
        sim_compuesto.put("tk_res_asig", "-=");
        sim_compuesto.put("tk_mul_asig", "*=");
        sim_compuesto.put("tk_div_asig", "/=");
        sim_compuesto.put("tk_mod_asig", "%=");
        sim_compuesto.put("tk_igualdad", "==");
        sim_compuesto.put("tk_diferente", "!=");
        sim_compuesto.put("tk_incremento", "++");
        sim_compuesto.put("tk_decremento", "--");
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
        reservadas.add("repeat");
        reservadas.add("for");
        reservadas.add("end");
        reservadas.add("next");
        reservadas.add("break");
        reservadas.add("and");
        reservadas.add("or");
        reservadas.add("not");
        Scanner x = new Scanner(System.in);
        Object linea = x.nextLine();
        int fila = 1;
        while (linea != null && x.hasNextLine()) {
            if (!linea.equals("")) {
                makeToken(linea.toString(), sim_simple, sim_simple_rechazado, sim_compuesto, reservadas,fila);
                //System.out.println("prueba");
            }else{
                System.exit(0);
            }
            fila++;
            if(x.hasNext())linea = x.nextLine();

        }
        /*System.out.println(deltaFunction(0,'+',sim_simple,sim_simple_rechazado,sim_compuesto));
            System.out.println(deltaFunction(5,'+',sim_simple,sim_simple_rechazado,sim_compuesto))*/;
    }

}

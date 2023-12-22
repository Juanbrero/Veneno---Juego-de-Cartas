package modelo.serializacion;

import java.io.*;
import java.util.ArrayList;

public class Serializador {

    private String nomArch;

    public Serializador (String nomArch) {
        super();
        this.nomArch = nomArch;
    }

    public boolean writeOneObject(Object obj) {
        boolean respuesta = false;

        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(nomArch));
            oos.writeObject(obj);
            oos.close();
            respuesta = true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return respuesta;
    }

    public boolean addOneObject(Object obj) {
        boolean respuesta = false;

        try {
            AddableObjectOutputStream oos = new AddableObjectOutputStream (new FileOutputStream(nomArch,true));
            oos.writeObject(obj);
            oos.close();
            respuesta = true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();

        }catch (IOException e) {
            e.printStackTrace();
        }
        return respuesta;
    }

    public Object[] readObjects() {
        Object[] respuesta;
        ArrayList<Object> listaObjetos = new ArrayList<Object>();

        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(nomArch));

            Object r = ois.readObject();
            while (r != null) {
                listaObjetos.add(r);
                r = ois.readObject();
            }
            ois.close();
        } catch (EOFException e) {
            System.out.println("\nLectura completada\n");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (!listaObjetos.isEmpty()) {
            System.out.println("serializador > la lista tiene " + listaObjetos.size() + " elementos");
            respuesta = new Object[listaObjetos.size()];
            int count = 0;
            for (Object o : listaObjetos) {
                respuesta[count ++] = o;
                System.out.println("serializador > " + count);
            }
        }
        else {
            System.out.println("serializador > no hay datos");
            respuesta = null;
        }

        return respuesta;
    }

    public void borrarArchivo() {

        File arch = new File(nomArch);
        if (arch.exists()) {
            arch.delete();
        }


//        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(nomArch))) {
//            // Sobrescribir el archivo con un objeto vacío o null
//            oos.writeObject(null);
//
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

}

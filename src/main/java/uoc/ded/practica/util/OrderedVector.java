package main.java.uoc.ded.practica.util;


import uoc.ei.tads.ContenidorAfitat;
import uoc.ei.tads.Iterador;
import uoc.ei.tads.IteradorVectorImpl;

import java.lang.reflect.Array;
import java.util.Comparator;

/**
 * TAD que implementa un vector ordenat. L'ordenació del vector
 * es determina amb el comparador.
 */
public class OrderedVector<E> implements ContenidorAfitat<E> {


    private Comparator<E> comparator;

    private E[] data;
    private int len;

    @SuppressWarnings("unchecked")
    public OrderedVector(int max, Comparator<E> comparator) {
        this.comparator = comparator;
        this.data = (E[]) Array.newInstance(Object.class, max);
        this.len = 0;
    }

    public E elementAt(int i) {
        return this.data[i];
    }

    /**
     * mètode que indica si un element és igual que el segon
     *
     * @param elem1
     * @param elem2
     * @return
     */
    @SuppressWarnings("unchecked")
    private boolean compare(E elem1, E elem2) {
        boolean res = ((Comparable<E>) elem1).compareTo(elem2) == 0;
        return res;

    }

    public void rshift(int i) {
        // desplaçament de tots els elements una posició
        int p = this.len - 1;
        while (p >= i) {
            this.data[p + 1] = this.data[p];
            p--;
        }
    }

    public void lshift(int i) {
        // desplaçament de tots els elements una posició
        int p = i;
        while (p < this.len - 1) {
            this.data[p] = this.data[p + 1];
            p++;
        }
    }


    public void update(E vIn) {
        int i = 0;
        boolean end = false;
        E v = null;

        if (this.estaPle()) {
            E pOut = this.last();
            if (comparator.compare(pOut, vIn) < 0) {
                this.delete(pOut);
                this.update(vIn);
                return;

            } else {
                return;
            }
        }

        // fem un recorregut per determinar la posició a inserir

        while (i < this.len && this.data[i] != null && this.comparator.compare(this.data[i], vIn) >= 0)
            i++;

        // desplaçament cap a la dreta de tots els elements
        rshift(i);

        // s'insereix l'element a la posició
        this.data[i] = vIn;
        this.len++;

    }

    public void delete(E elem) {
        int i = 0;
        boolean found = false;

        while (!found && i < this.len)
            found = (compare(elem, this.data[i++]));

        if (found) {
            if (i < this.len) {

                lshift(i - 1);
            } else lshift(i);
            this.len--;
        }

    }

    public Iterador<E> elements() {
        // TODO Auto-generated method stub
        return new IteradorVectorImpl<E>(this.data, this.len, 0);

    }


    public boolean estaBuit() {
        return this.len == 0;
    }

    public int nombreElems() {
        return this.len;
    }

    public boolean estaPle() {
        // TODO Auto-generated method stub
        return this.len == this.data.length;
    }


    /**
     * mètode de prova
     *
     * @param args
     */
    public static void main(String[] args) {
        Comparator<Integer> cmp = new Comparator<Integer>() {

            public int compare(Integer arg0, Integer arg1) {
                // TODO Auto-generated method stub
                return arg0.compareTo(arg1);
            }

        };
        OrderedVector<Integer> v = new OrderedVector<Integer>(10, cmp);

        v.update(7);
        v.update(9);
        v.update(5);
        v.update(2);
        v.update(3);
        v.update(1);
        v.update(4);
        v.update(6);
        v.update(11);
        v.update(12);

        System.out.println("estaPle " + v.estaPle());


        for (Iterador<Integer> it = v.elements(); it.hiHaSeguent(); ) {
            System.out.println(it.seguent());
        }

        System.out.println("Delete 1");
        v.delete(1);

        for (Iterador<Integer> it = v.elements(); it.hiHaSeguent(); ) {
            System.out.println(it.seguent());
        }

        System.out.println("add 8  and full vector");
        v.update(8);

        System.out.println("estaPle " + v.estaPle());

        for (Iterador<Integer> it = v.elements(); it.hiHaSeguent(); ) {
            System.out.println(it.seguent());
        }

        System.out.println("estaPle " + v.estaPle());

        System.out.println("delete 3");
        v.delete(3);

        for (Iterador<Integer> it = v.elements(); it.hiHaSeguent(); ) {
            System.out.println(it.seguent());
        }

        System.out.println("estaPle " + v.estaPle());

        System.out.println("add 25!");
        v.update(25);

        for (Iterador<Integer> it = v.elements(); it.hiHaSeguent(); ) {
            System.out.println(it.seguent());
        }

        System.out.println("estaPle " + v.estaPle());

        System.out.println("add 32");
        v.update(32);

        for (Iterador<Integer> it = v.elements(); it.hiHaSeguent(); ) {
            System.out.println(it.seguent());
        }

        System.out.println("add 15");
        v.update(15);


        for (Iterador<Integer> it = v.elements(); it.hiHaSeguent(); ) {
            System.out.println(it.seguent());
        }

        System.out.println("add 3");
        v.update(3);

        for (Iterador<Integer> it = v.elements(); it.hiHaSeguent(); ) {
            System.out.println(it.seguent());
        }


        System.out.println("add 40");
        v.update(40);

        for (Iterador<Integer> it = v.elements(); it.hiHaSeguent(); ) {
            System.out.println(it.seguent());
        }

    }

    public E last() {
        // TODO Auto-generated method stub
        return this.data[this.len - 1];
    }


}
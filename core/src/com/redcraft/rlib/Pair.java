package com.redcraft.rlib;

public class Pair<A,B> {

    private A a;
    private B b;

    public Pair() {
    }

    public Pair(A a, B b) {
        this.a = a;
        this.b = b;
    }

    public A getA() {
        return a;
    }
    public void setA(A a) {
        this.a = a;
    }
    public B getB() {
        return b;
    }
    public void setB(B b) {
        this.b = b;
    }
    public boolean hasOne(Class<?> type) {
        return type.isInstance(a) || type.isInstance(b);
    }
    public boolean equals(Object other) {
        if(other == null) return false;
        if(!(other instanceof Pair)) return false;
        Pair<?,?> pair = (Pair<?,?>) other;
        if(pair.a==null || pair.b==null) return false;
        return (pair.a.equals(a) && pair.b.equals(b)) ||
               (pair.a.equals(b) && pair.b.equals(a));
    }

    public int hashCode() {
        return a.hashCode() ^ b.hashCode();
    }
    public String toString() {
        return "<"+a+" | "+b+">";
    }
}

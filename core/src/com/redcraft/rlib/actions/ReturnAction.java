package com.redcraft.rlib.actions;

public interface ReturnAction extends Action {
    interface n0<R> extends ReturnAction {
        R run();
    }
    interface n1<A,R> extends ReturnAction {
        R run(A a);
    }
    interface n2<A,B,R> extends ReturnAction {
        R run(A a, B b);
    }
    interface n3<A,B,C,R> extends ReturnAction {
        R run(A a, B b, C c);
    }
    interface n4<A,B,C,D,R> extends ReturnAction {
        R run(A a, B b, C c, D d);
    }
    interface n5<A,B,C,D,E,R> extends ReturnAction {
        R run(A a, B b, C c, D d, E e);
    }
    interface n6<A,B,C,D,E,F,R> extends ReturnAction {
        R run(A a, B b, C c, D d, E e, F f);
    }
    interface n7<A,B,C,D,E,F,G,R> extends ReturnAction {
        R run(A a, B b, C c, D d, E e, F f, G g);
    }
    interface n8<A,B,C,D,E,F,G, H,R> extends ReturnAction {
        R run(A a, B b, C c, D d, E e, F f, G g, H h);
    }
}

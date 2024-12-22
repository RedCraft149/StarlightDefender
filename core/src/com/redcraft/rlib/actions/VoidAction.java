package com.redcraft.rlib.actions;

public interface VoidAction extends Action{
    interface n0 extends VoidAction {
        void run();
    }
    interface n1<A> extends VoidAction {
        void run(A a);
    }
    interface n2<A,B> extends VoidAction {
        void run(A a, B b);
    }
    interface n3<A,B,C> extends VoidAction {
        void run(A a, B b, C c);
    }
    interface n4<A,B,C,D> extends VoidAction {
        void run(A a, B b, C c, D d);
    }
    interface n5<A,B,C,D,E> extends VoidAction {
        void run(A a, B b, C c, D d, E e);
    }
    interface n6<A,B,C,D,E,F> extends VoidAction {
        void run(A a, B b, C c, D d, E e, F f);
    }
    interface n7<A,B,C,D,E,F,G> extends VoidAction {
        void run(A a, B b, C c, D d, E e, F f, G g);
    }
    interface n8<A,B,C,D,E,F,G,H> extends VoidAction {
        void run(A a, B b, C c, D d, E e, F f, G g, H h);
    }
}

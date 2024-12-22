package com.redcraft.rlib.actions;

/**
 * <p>Actions allow for dynamic method execution without using reflection.
 * This class is a collection of utility methods to use with {@link Action}.</p>
 * <p>
 *     <b>Creating actions: </b>
 *     {@code Actions.create(object::method)}
 *     or<br>
 *     {@code Actions.create(class::method)}
 * </p>
 * <p>
 *     <b>Running actions: </b>
 *     {@code Actions.run(action,[arguments],[return type])}
 *     or<br>
 *     {@code Actions.runExact(object::method,[arguments])}
 * </p>
 *
 * Note that use of Actions#run(...) can produce ClassCastExceptions if
 * the input arguments don't match.
 *
 */
public final class Actions {

    private Actions() {
        throw new UnsupportedOperationException("Actions cannot be initialized");
    }

    public static void runExact(VoidAction.n0 action) {
        action.run();
    }
    public static <A> void runExact(VoidAction.n1<A> action, A a) {
        action.run(a);
    }
    public static <A,B> void runExact(VoidAction.n2<A,B> action, A a, B b) {
        action.run(a,b);
    }
    public static <A,B,C> void runExact(VoidAction.n3<A,B,C> action, A a, B b, C c) {
        action.run(a,b,c);
    }
    public static <A,B,C,D> void runExact(VoidAction.n4<A,B,C,D> action, A a, B b, C c, D d) {
        action.run(a,b,c,d);
    }
    public static <A,B,C,D,E> void runExact(VoidAction.n5<A,B,C,D,E> action, A a, B b, C c, D d, E e) {
        action.run(a,b,c,d,e);
    }
    public static <A,B,C,D,E,F> void runExact(VoidAction.n6<A,B,C,D,E,F> action, A a, B b, C c, D d, E e, F f) {
        action.run(a,b,c,d,e,f);
    }
    public static <A,B,C,D,E,F,G> void runExact(VoidAction.n7<A,B,C,D,E,F,G> action, A a, B b, C c, D d, E e, F f, G g) {
        action.run(a,b,c,d,e,f,g);
    }
    public static <A,B,C,D,E,F,G,H> void runExact(VoidAction.n8<A,B,C,D,E,F,G,H> action, A a, B b, C c, D d, E e, F f, G g, H h) {
        action.run(a,b,c,d,e,f,g,h);
    }

    public static <R> R runExact(ReturnAction.n0<R> action) {
        return action.run();
    }
    public static <A,R> R runExact(ReturnAction.n1<A,R> action, A a) {
        return action.run(a);
    }
    public static <A,B,R> R runExact(ReturnAction.n2<A,B,R> action, A a, B b) {
        return action.run(a,b);
    }
    public static <A,B,C,R> R runExact(ReturnAction.n3<A,B,C,R> action, A a, B b, C c) {
        return action.run(a,b,c);
    }
    public static <A,B,C,D,R> R runExact(ReturnAction.n4<A,B,C,D,R> action, A a, B b, C c, D d) {
        return action.run(a,b,c,d);
    }
    public static <A,B,C,D,E,R> R runExact(ReturnAction.n5<A,B,C,D,E,R> action, A a, B b, C c, D d, E e) {
        return action.run(a,b,c,d,e);
    }
    public static <A,B,C,D,E,F,R> R runExact(ReturnAction.n6<A,B,C,D,E,F,R> action, A a, B b, C c, D d, E e, F f) {
        return action.run(a,b,c,d,e,f);
    }
    public static <A,B,C,D,E,F,G,R> R runExact(ReturnAction.n7<A,B,C,D,E,F,G,R> action, A a, B b, C c, D d, E e, F f, G g) {
        return action.run(a,b,c,d,e,f,g);
    }
    public static <A,B,C,D,E,F,G,H,R> R runExact(ReturnAction.n8<A,B,C,D,E,F,G,H,R> action, A a, B b, C c, D d, E e, F f, G g, H h) {
        return action.run(a,b,c,d,e,f,g,h);
    }

    @SuppressWarnings("unchecked")
    public static <R> R run(Action action) throws ClassCastException {
        if(action instanceof VoidAction.n0) runExact((VoidAction.n0) action);
        if(action instanceof ReturnAction.n0<?>) return runExact((ReturnAction.n0<R>) action);
        return null;
    }

    @SuppressWarnings("unchecked")
    public static <A,R> R run(Action action, A a) throws ClassCastException {
        if(action instanceof VoidAction.n1) runExact((VoidAction.n1<A>) action,a);
        if(action instanceof ReturnAction.n1<?,?>) return runExact((ReturnAction.n1<A,R>) action,a);
        return null;
    }

    @SuppressWarnings("unchecked")
    public static <A,B,R> R run(Action action, A a, B b) throws ClassCastException {
        if(action instanceof VoidAction.n2) runExact((VoidAction.n2<A,B>) action,a,b);
        if(action instanceof ReturnAction.n2<?,?,?>) return runExact((ReturnAction.n2<A,B,R>) action,a,b);
        return null;
    }

    @SuppressWarnings("unchecked")
    public static <A,B,C,R> R run(Action action, A a, B b, C c) throws ClassCastException {
        if(action instanceof VoidAction.n3) runExact((VoidAction.n3<A,B,C>) action,a,b,c);
        if(action instanceof ReturnAction.n3<?,?,?,?>) return runExact((ReturnAction.n3<A,B,C,R>) action,a,b,c);
        return null;
    }

    @SuppressWarnings("unchecked")
    public static <A,B,C,D,R> R run(Action action, A a, B b, C c, D d) throws ClassCastException {
        if(action instanceof VoidAction.n4) runExact((VoidAction.n4<A,B,C,D>) action,a,b,c,d);
        if(action instanceof ReturnAction.n4<?,?,?,?,?>) return runExact((ReturnAction.n4<A,B,C,D,R>) action,a,b,c,d);
        return null;
    }
    @SuppressWarnings("unchecked")
    public static <A,B,C,D,E,R> R run(Action action, A a, B b, C c, D d, E e) throws ClassCastException {
        if(action instanceof VoidAction.n5) runExact((VoidAction.n5<A,B,C,D,E>) action,a,b,c,d,e);
        if(action instanceof ReturnAction.n5<?,?,?,?,?,?>) return runExact((ReturnAction.n5<A,B,C,D,E,R>) action,a,b,c,d,e);
        return null;
    }
    @SuppressWarnings("unchecked")
    public static <A,B,C,D,E,F,R> R run(Action action, A a, B b, C c, D d, E e, F f) throws ClassCastException {
        if(action instanceof VoidAction.n6) runExact((VoidAction.n6<A,B,C,D,E,F>) action,a,b,c,d,e,f);
        if(action instanceof ReturnAction.n6<?,?,?,?,?,?,?>) return runExact((ReturnAction.n6<A,B,C,D,E,F,R>) action,a,b,c,d,e,f);
        return null;
    }
    @SuppressWarnings("unchecked")
    public static <A,B,C,D,E,F,G,R> R run(Action action, A a, B b, C c, D d, E e, F f, G g) throws ClassCastException {
        if(action instanceof VoidAction.n7) runExact((VoidAction.n7<A,B,C,D,E,F,G>) action,a,b,c,d,e,f,g);
        if(action instanceof ReturnAction.n7<?,?,?,?,?,?,?,?>) return runExact((ReturnAction.n7<A,B,C,D,E,F,G,R>) action,a,b,c,d,e,f,g);
        return null;
    }
    @SuppressWarnings("unchecked")
    public static <A,B,C,D,E,F,G,H,R> R run(Action action, A a, B b, C c, D d, E e, F f, G g, H h) throws ClassCastException {
        if(action instanceof VoidAction.n8) runExact((VoidAction.n8<A,B,C,D,E,F,G,H>) action,a,b,c,d,e,f,g,h);
        if(action instanceof ReturnAction.n8<?,?,?,?,?,?,?,?,?>) return runExact((ReturnAction.n8<A,B,C,D,E,F,G,H,R>) action,a,b,c,d,e,f,g,h);
        return null;
    }
    public static <R> R run(Action action, Object... args) throws ClassCastException {
        switch (args.length) {
            case 0: return run(action);
            case 1: return run(action,args[0]);
            case 2: return run(action,args[0],args[1]);
            case 3: return run(action,args[0],args[1],args[2]);
            case 4: return run(action,args[0],args[1],args[2],args[3]);
            case 5: return run(action,args[0],args[1],args[2],args[3],args[4]);
            case 6: return run(action,args[0],args[1],args[2],args[3],args[4],args[5]);
            case 7: return run(action,args[0],args[1],args[2],args[3],args[4],args[5],args[6]);
            case 8: return run(action,args[0],args[1],args[2],args[3],args[4],args[5],args[6],args[7]);
            default: return null;
        }
    }

    public static <R> R run(Action action, Class<R> rtype) throws ClassCastException {
        return run(action);
    }
    public static <A,R> R run(Action action, A a, Class<R> rtype) throws ClassCastException {
        return run(action,a);
    }
    public static <A,B,R> R run(Action action, A a, B b, Class<R> rtype) throws ClassCastException {
        return run(action,a,b);
    }
    public static <A,B,C,R> R run(Action action, A a, B b, C c, Class<R> rtype) throws ClassCastException {
        return run(action,a,b,c);
    }
    public static <A,B,C,D,R> R run(Action action, A a, B b, C c, D d, Class<R> rtype) throws ClassCastException {
        return run(action,a,b,c,d);
    }
    public static <A,B,C,D,E,R> R run(Action action, A a, B b, C c, D d, E e, Class<R> rtype) throws ClassCastException {
        return run(action,a,b,c,d,e);
    }
    public static <A,B,C,D,E,F,R> R run(Action action, A a, B b, C c, D d, E e, F f, Class<R> rtype) throws ClassCastException {
        return run(action,a,b,c,d,e,f);
    }
    public static <A,B,C,D,E,F,G,R> R run(Action action, A a, B b, C c, D d, E e, F f, G g, Class<R> rtype) throws ClassCastException {
        return run(action,a,b,c,d,e,f,g);
    }
    public static <A,B,C,D,E,F,G,H,R> R run(Action action, A a, B b, C c, D d, E e, F f, G g, H h, Class<R> rtype) throws ClassCastException {
        return run(action,a,b,c,d,e,f,g,h);
    }

    public static VoidAction.n0 create(VoidAction.n0 action) {
        return action;
    }
    public static <A> VoidAction.n1<A> create(VoidAction.n1<A> action) {
        return action;
    }
    public static <A,B> VoidAction.n2<A,B> create(VoidAction.n2<A,B> action) {
        return action;
    }
    public static <A,B,C> VoidAction.n3<A,B,C> create(VoidAction.n3<A,B,C> action) {
        return action;
    }
    public static <A,B,C,D> VoidAction.n4<A,B,C,D> create(VoidAction.n4<A,B,C,D> action) {
        return action;
    }
    public static <A,B,C,D,E> VoidAction.n5<A,B,C,D,E> create(VoidAction.n5<A,B,C,D,E> action) {
        return action;
    }
    public static <A,B,C,D,E,F> VoidAction.n6<A,B,C,D,E,F> create(VoidAction.n6<A,B,C,D,E,F> action) {
        return action;
    }
    public static <A,B,C,D,E,F,G> VoidAction.n7<A,B,C,D,E,F,G> create(VoidAction.n7<A,B,C,D,E,F,G> action) {
        return action;
    }
    public static <A,B,C,D,E,F,G,H> VoidAction.n8<A,B,C,D,E,F,G,H> create(VoidAction.n8<A,B,C,D,E,F,G,H> action) {
        return action;
    }

    public static <R> ReturnAction.n0<R> create(ReturnAction.n0<R> action) {
        return action;
    }
    public static <A,R> ReturnAction.n1<A,R> create(ReturnAction.n1<A,R> action) {
        return action;
    }
    public static <A,B,R> ReturnAction.n2<A,B,R> create(ReturnAction.n2<A,B,R> action) {
        return action;
    }
    public static <A,B,C,R> ReturnAction.n3<A,B,C,R> create(ReturnAction.n3<A,B,C,R> action) {
        return action;
    }
    public static <A,B,C,D,R> ReturnAction.n4<A,B,C,D,R> create(ReturnAction.n4<A,B,C,D,R> action) {
        return action;
    }
    public static <A,B,C,D,E,R> ReturnAction.n5<A,B,C,D,E,R> create(ReturnAction.n5<A,B,C,D,E,R> action) {
        return action;
    }
    public static <A,B,C,D,E,F,R> ReturnAction.n6<A,B,C,D,E,F,R> create(ReturnAction.n6<A,B,C,D,E,F,R> action) {
        return action;
    }
    public static <A,B,C,D,E,F,G,R> ReturnAction.n7<A,B,C,D,E,F,G,R> create(ReturnAction.n7<A,B,C,D,E,F,G,R> action) {
        return action;
    }
    public static <A,B,C,D,E,F,G,H,R> ReturnAction.n8<A,B,C,D,E,F,G,H,R> create(ReturnAction.n8<A,B,C,D,E,F,G,H,R> action) {
        return action;
    }
}

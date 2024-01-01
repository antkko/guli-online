package com.macro;

/**
 * @auther macro
 * @description
 * @date 2023/12/12 12:04
 */
public class JavaCase {
    public static class Dog {
        public void run(final Integer x) {
            System.out.println("1");
        }

        public void run(final Number x) {
            System.out.println("2");
        }

        public void run(final Object x) {
            System.out.println("3");
        }
    }

    public static void main(final String[] args) {
        final Dog dog = new Dog();
        dog.run(1); // 1
        dog.run(1.0); // 2
        dog.run("1"); // 3

        dog.run((int) 1.0); // 1

        final Integer a = 1;
        final Object x = a;
        dog.run(x); // 3
    }
}

/**
 * public static class Base { static { System.out.println("bse static"); }
 *
 * <p>public Base() { System.out.println("base constructor"); }
 *
 * <p>public void show() { System.out.println("base show"); } }
 *
 * <p>public static class Sub extends Base { static { System.out.println("sub static"); }
 *
 * <p>public Sub() { System.out.println("sub constructor"); } @Override public void show() {
 * System.out.println("sub show"); } }
 *
 * <p>public static void main(final String[] args){ final Sub sub = new Sub(); sub.show(); }
 *
 * <p>public static class Count {
 *
 * <p>private Integer c;
 *
 * <p>public Count(final Integer c) { this.c = c; }
 *
 * <p>public Integer getC() { return this.c; }
 *
 * <p>public void setC(final Integer c) { this.c = c; } }
 *
 * <p>public static void change1(Integer a) { a = 2; }
 *
 * <p>public static void change2(Count c) { c = new Count(2); }
 *
 * <p>public static void change3(final Count c) { c.setC(2); }
 *
 * <p>public static void main(final String[] args) { final Integer a = 1; change1(a);
 * System.out.println(a); // 1-----1
 *
 * <p>final Count b = new Count(1); change2(b); System.out.println(b.getC()); // 2----1
 *
 * <p>final Count c = new Count(1); change3(c); System.out.println(c.getC()); // 3-----2
 *
 * <p>final Integer c1 = 254; final Integer c2 = 254;
 *
 * <p>System.out.println(c1 == c2); // 4------false System.out.println(c1.equals(c2)); // 5-----true
 * }
 */

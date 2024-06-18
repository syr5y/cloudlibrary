package test;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

public class Tset2 {
    public static void main(String[] args) {
        List<Integer> list = new ArrayList<Integer>();
        for (int i = 0; i <20; i++)
        {
            if(i%2==0)
            {
                list.add(i);
            }
            else
            {
                list.add(0);
            }
        }
        System.out.println(list);
        List<Integer> distinctList = new ArrayList<>(new LinkedHashSet<>(list));
        System.out.println(distinctList);
        String str = "Hello, World!";
        String substr = "World";

        if (substr.contains(str)) {
            System.out.println("String");
        } else {
            System.out.println(" not ");
        }
        System.out.println(false||false);
    }
}

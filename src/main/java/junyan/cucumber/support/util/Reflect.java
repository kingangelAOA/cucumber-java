package junyan.cucumber.support.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by kingangelTOT on 15/10/1.
 */
public class Reflect {
    /**
     * 根据类路径获取生成该类的对象
     */
    public static Object instantiate(String className, Object[] objects) {
        Object object = null;
        try {
            Class<?> classType = Class.forName(className);
            Constructor<?> constructor = getRightConstructor(classType, objects);
//            puts(constructor);
            object = constructor.newInstance(objects);
        } catch (ClassNotFoundException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return object;
    }

    /**
     * 根据入参获取构造函数
     */
    private static Constructor getRightConstructor(Class<?> classType, Object[] objects){
        Constructor reConstructor = null;
        LinkedList<Object> linkList = Common.toLinkedList(objects);
        for (Constructor constructor:classType.getConstructors()){
            if (constructor.getParameters().length != objects.length)
                continue;
            List<Boolean> flagList = new LinkedList<>();
            for (Parameter parameter : constructor.getParameters()) {
                boolean flag = false;
                for (Object object : linkList) {
                    if (parameter.getType() == object.getClass()) {
                        flag = true;
                        linkList.remove(object);
                        break;
                    } else {
                        for (Class<?> clazz : object.getClass().getInterfaces()) {
                            Class<?> targetClazz = parameter.getType();
                            if (clazz == targetClazz) {
                                flag = true;
                                linkList.remove(object);
                                break;
                            }
                            if (convert(object.getClass()) == convert(parameter.getType())){
                                flag = true;
                                break;
                            }
                        }
                    }
                }
                flagList.add(flag);
            }

            if (!flagList.contains(false))
                reConstructor = constructor;
        }
        return reConstructor;
    }

    /**
     * 根据对象动态调用该对象的方法
     */
    public static Object execMethod(Object Object, String method, Object[] args){
        Class<?> clazz = Object.getClass();
        Object object = null;
        Method getMethod;
        try {

            getMethod = selectMethod(clazz, method, args);
            object = getMethod.invoke(Object, args);
        } catch (InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return object;
    }

    /**
     * 动态获取method对象
     */
    private static Method selectMethod(Class<?> clazz, String name, Object[] args){
        Method method = null;
        LinkedList<Object> linkList = Common.toLinkedList(args);
        for (Method remethod:clazz.getMethods()){
            if (!remethod.getName().equals(name))
                continue;
            if (remethod.getParameters().length != args.length)
                continue;
            List<Boolean> flagList = new LinkedList<>();
            for (Parameter parameter : remethod.getParameters()) {
                boolean flag = false;
                for (Object object : linkList) {
                    if (parameter.getType() == object.getClass()) {
                        flag = true;
                        linkList.remove(object);
                        break;
                    } else{
                        for (Class<?> reClazz : object.getClass().getInterfaces()) {
                            Class<?> targetClazz = parameter.getType();
                            if (reClazz == targetClazz) {
                                flag = true;
                                linkList.remove(object);
                                break;
                            }
                            if (convert(object.getClass()) == convert(parameter.getType())){
                                flag = true;
                                break;
                            }
                        }
                    }
                }
                flagList.add(flag);
            }

            if (!flagList.contains(false))
                method = remethod;
        }
        return method;
    }

    public static int convert(Class<?> target) {
        if (target == Object.class || target == String.class) {
            return 1;
        }
        if (target == Character.class || target == char.class) {
            return 2;
        }
        if (target == Byte.class || target == byte.class) {
            return 3;
        }
        if (target == Short.class || target == short.class) {
            return 4;
        }
        if (target == Integer.class || target == int.class) {
            return 5;
        }
        if (target == Long.class || target == long.class) {
            return 6;
        }
        if (target == Float.class || target == float.class) {
            return 7;
        }
        if (target == Double.class || target == double.class) {
            return 8;
        }
        if (target == Boolean.class || target == boolean.class) {
            return 9;
        }
        return 0;
    }
}

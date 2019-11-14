package com.titansoft.utils.util;

/**
 * @Author: Kevin
 * @Date: 2019/9/24 16:40
 */
public class ClassUtil  extends ClassLoader  {
    private ClassLoader parent;

    public ClassUtil(ClassLoader parent){
        this.parent = parent;
    }

    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        return this.loadClass(name, false);
    }

    @Override
    protected synchronized Class<?> loadClass(String name, boolean resolve)
            throws ClassNotFoundException {
        Class<?> clazz = this.findLoadedClass(name);
        if(null != parent){
            clazz = parent.loadClass(name);
        }
        if(null == clazz){
            this.findSystemClass(name);
        }

        if(null == clazz){
            throw new ClassNotFoundException();
        }
        if(null != clazz && resolve){
            this.resolveClass(clazz);
        }

        return clazz;
    }

    /**
     * @param args
     */
    public static void main(String[] args) {

    }

}

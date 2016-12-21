package me.mdbell.bf.util;

/**
 * @author matt123337
 */
public class VerifyException extends RuntimeException{

    public VerifyException(String str){
        super(str);
    }

    public VerifyException(String pattern,Object... params){
        this(String.format(pattern,params));
    }
}

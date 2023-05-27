package com.driver.exception;

public class NotFoundException extends RuntimeException{
   public NotFoundException(String str){
       super(str);
   }
}

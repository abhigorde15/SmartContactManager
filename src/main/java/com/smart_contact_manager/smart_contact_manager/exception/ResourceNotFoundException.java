package com.smart_contact_manager.smart_contact_manager.exception;

public class ResourceNotFoundException extends RuntimeException {
   public ResourceNotFoundException(String msg) {
	   super(msg);
   }
   public ResourceNotFoundException() {
	   super("Resource Not Found");
   }
}

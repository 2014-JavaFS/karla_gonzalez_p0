package com.revature.bankingapp.util.interfaces;

public interface Crudable<o> extends Serviceable<o>{
    boolean update(o updatedObject);
    boolean delete(int number);
}

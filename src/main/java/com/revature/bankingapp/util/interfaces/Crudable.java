package com.revature.bankingapp.util.interfaces;

public interface Crudable<o> extends Serviceable<o>{
    boolean delete(int number);
}

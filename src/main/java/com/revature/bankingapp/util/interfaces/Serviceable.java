package com.revature.bankingapp.util.interfaces;


import com.revature.bankingapp.util.exceptions.InvalidInputException;

public interface Serviceable<o> {
    o create(o obj) throws InvalidInputException;
    o findById(int number);
}

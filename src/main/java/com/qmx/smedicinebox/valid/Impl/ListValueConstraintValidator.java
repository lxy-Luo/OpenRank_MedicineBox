package com.qmx.smedicinebox.valid.Impl;

import com.qmx.smedicinebox.valid.ListValue;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.HashSet;
import java.util.Set;

public class ListValueConstraintValidator implements ConstraintValidator<ListValue,Integer> {
    private Set<Integer> set=new HashSet<>();
    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext constraintValidatorContext) {
        if(set.contains(value)){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public void initialize(ListValue constraintAnnotation) {
        int[] vals = constraintAnnotation.vals();
        for(int val:vals){
            set.add(val);
        }
    }
}

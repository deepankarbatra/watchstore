package com.nagarro.watchstore.validator;

import java.util.function.Function;

import org.springframework.stereotype.Component;

import com.nagarro.watchstore.constants.Constant;
import com.nagarro.watchstore.exception.BadRequestException;


@Component
public class StringToIntParser implements Function<String, Integer> {

    @Override
    public Integer apply(String source) {
        try {
            return Integer.parseInt(source);
        } catch (NumberFormatException exception) {
            throw new BadRequestException("Cart Id",Constant.CART_NOT_FOUND+source);
        }
    }
}


package com.ikun.cm.pojo.enums;

/**
 * @author: HeKun
 * @date: 2024/11/5 23:17
 * @description: A base enum class to provide common functionality for enums.
 */
public interface BaseEnum {

    int getCode();

    String getMarks();

    /**
     * A method of the enum to be found.
     * @param enumClass the class of your enum
     * @param code the code of the enum
     * @param <E> the enum type
     * @return
     */
    static <E extends  Enum<E> & BaseEnum> E findByCode(Class<E> enumClass, int code){
        for (E enumConstant : enumClass.getEnumConstants()) {
            if (enumConstant.getCode() == code) {
                return enumConstant;
            }
        }
        return null;
    }


    /**
     * A method of the enum's marks to be found.
     * @param enumClass the class of your enum
     * @param code the code of the enum
     * @param <E>  the enum type
     * @return
     */
    static <E extends  Enum<E> & BaseEnum> String findMarksByCode(Class<E> enumClass, int code){
        for (E enumConstant : enumClass.getEnumConstants()) {
            if (enumConstant.getCode() == code) {
                return enumConstant.getMarks();
            }
        }
        return null;
    }

}

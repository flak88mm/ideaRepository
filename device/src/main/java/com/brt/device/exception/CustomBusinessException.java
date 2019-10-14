package com.brt.device.exception;

/**
 * @ClassName: CustomBusinessException 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author Zeng Nan
 * @date 2019年10月14日 下午10:17:33
 */
public class CustomBusinessException extends RuntimeException {
    public CustomBusinessException(){
    }

    public CustomBusinessException(String info){
        super(info);
    }

}


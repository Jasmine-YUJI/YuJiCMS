package com.yuji.common.exception.user;

import com.yuji.common.exception.base.BaseException;

/**
 * 用户信息异常类
 * 
 * @author Liguoqiang
 */
public class UserException extends BaseException
{
    private static final long serialVersionUID = 1L;

    public UserException(String code, Object[] args)
    {
        super("user", code, args, null);
    }
}

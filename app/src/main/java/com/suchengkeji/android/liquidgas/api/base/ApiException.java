package com.suchengkeji.android.liquidgas.api.base;

/**
 * @aboutContent: 统一错误返回码处理
 * @author： An
 * @crateTime: 2018/1/24 16:04
 * @mailBox: an.****.life@gmail.com
 * @company: 东莞速成科技有限公司
 */

public class ApiException extends RuntimeException {

    public static final int USER_NOT_EXIST = 301;//该用户不存在
    public static final int WRONG_PASSWORD = 302;//密码错误

    public ApiException(int resultCode) {
        this(getApiExceptionMessage(resultCode));
    }

    public ApiException(String detailMessage) {
        super(detailMessage);
    }

    /**
     * 由于服务器传递过来的错误信息直接给用户看的话，用户未必能够理解
     * 需要根据错误码对错误信息进行一个转换，在显示给用户
     *
     * @param code
     * @return
     */
    private static String getApiExceptionMessage(int code) {
        String message = "";
        switch (code) {
            case USER_NOT_EXIST:
                message = "该用户不存在";
                break;
            case WRONG_PASSWORD:
                message = "密码错误";
                break;
            default:
                message = "未知错误";

        }
        return message;
    }
}
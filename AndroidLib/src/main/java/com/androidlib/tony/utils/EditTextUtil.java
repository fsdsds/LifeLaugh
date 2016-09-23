package com.androidlib.tony.utils;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.EditText;

/**
 * Created by tony on 2016/8/1.
 */
public class EditTextUtil {

    private static EditTextUtil mEditTextUtil;
    private boolean isPwdEdt = false;
    private int limit = 0;

    public static EditTextUtil newInstence() {

        mEditTextUtil = new EditTextUtil();
        return mEditTextUtil;
    }

    //验证内容是否为邮箱格式\手机号格式
    public int isContentPhoneOrEmail(String s) {
        //TODO 1为手机号、2为邮箱、-1格式错误，都不是
        //TODO 先判断是否为手机号，满足11位或者8位，则可能是大陆手机或者香港手机号
        int result = -1;
        boolean isphone;
        boolean isHongKongPhone;
        boolean isEmail;
        switch (s.length()) {

            case 8://TODO 判断是有含有@没有判断是否满足香港号码
                if (!s.contains("@")) {
                    String regexp1 = "^([9]|[6]|[5]|[3]|[2])[0-9]{7}$";
                    isHongKongPhone = s.matches(regexp1);
                    return result = isHongKongPhone ? 1 : -1;
                }
                break;
            case 11://TODO 判断是否含有@没有则判断是否满足内地号码
                if (!s.contains("@")) {
                    String regexp = "^[1]([3]|[5]|[8]|[6])[0-9]{9}$";
                    isphone = s.matches(regexp);
                    return result = isphone ? 1 : -1;
                }
                break;

        }

        //TODO 判断是否为邮箱，如果字符中包含@就匹配正则
        String regexEmail = "^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\\.[a-zA-Z0-9_-]{2,3}){1,2})$";
        isEmail = s.matches(regexEmail);

        return result = isEmail ? 2 : -1;
    }

    public void setEdtChangeListener(final EditText edt) {
        TextWatcher watcher = new TextWatcher() {
            String str = "";

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                str = s.toString();
            }

            @Override
            public void onTextChanged(final CharSequence s, int start, int before, int count) {

                //TODO 判断是否是手机号或邮箱
                final int a = isContentPhoneOrEmail(s.toString());
                mOnEdtContentListener.isPhoneOrEmail(a);
                //TODO 判断输入的字符长度超过限制停留光标到最后
                if(s==" "){
                    edt.setText("");
                }
                if(s.toString().length()>limit && isPwdEdt){
                    edt.setText(str);

                    if(edt.getText().length()>0 && edt.getText().length()<limit){
                        edt.setSelection(edt.getText().length());
                    }else if(edt.getText().length()>=limit){
                        edt.setSelection(limit);
                    }
                }
                if(TextUtils.isEmpty(s.toString())){
                    //TODO 为空传falses
                    mOnEdtContentListener.isNull(0,false);
                }else{
                    //TODO 不为空传true
                    mOnEdtContentListener.isNull(s.toString().length(),true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
        edt.addTextChangedListener(watcher);

    }

    public void setOnEdtContentListener(boolean isPwd, int limit, EditText edt, OnEdtContentListener mOnEdtContentListener) {
        setEdtChangeListener(edt);
        isPwdEdt = isPwd;
        this.limit = limit;
        this.mOnEdtContentListener = mOnEdtContentListener;
    }

    public OnEdtContentListener mOnEdtContentListener;

    public interface OnEdtContentListener {
        void isNull(int length, boolean flag);
        void isPhoneOrEmail(int a);
    }

}

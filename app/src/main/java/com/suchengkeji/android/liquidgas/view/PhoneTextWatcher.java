package com.suchengkeji.android.liquidgas.view;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

/**
 * @aboutContent: EditText监听----输入电话自动添加 -
 * @author： 安
 * @crateTime: 2018/1/3 09:56
 * @mailBox: an.****.life@gmail.com
 * @company: 东莞速成科技有限公司
 */

public class PhoneTextWatcher implements TextWatcher {

    private EditText _text;

    public PhoneTextWatcher(EditText _text) {
        this._text = _text;
    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

        String str1 = "";
        if (s.toString().contains(" ")) {
            String[] str = s.toString().split(" ");
            for (int i = 0; i < str.length; i++) {
                str1 += str[i];
            }
            _text.setText(str1);
            _text.setSelection(start);
        } else {
            str1 += s.toString();
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < str1.length(); i++) {
            if (i != 3 && i != 8 && str1.charAt(i) == '-') {
                continue;
            } else {
                sb.append(str1.charAt(i));
                if ((sb.length() == 4 || sb.length() == 9)
                        && sb.charAt(sb.length() - 1) != '-') {
                    sb.insert(sb.length() - 1, '-');
                }
            }
        }

        if (!sb.toString().equals(str1.toString())) {
            int index = start + 1;
            if (sb.charAt(start) == '-') {
                if (before == 0) {
                    index++;
                } else {
                    index--;
                }
            } else {
                if (before == 1) {
                    index--;
                }
            }
            _text.setText(sb.toString());
            _text.setSelection(index);
        }

    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}

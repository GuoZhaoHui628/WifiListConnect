package guo.wifilistconnect.dialog;

import android.app.Dialog;
import android.content.Context;
import android.net.wifi.WifiConfiguration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import guo.wifilistconnect.R;
import guo.wifilistconnect.WifiSupport;


/**
 * Created by John on 2017/4/7.
 */

public class WifiLinkDialog extends Dialog implements View.OnClickListener{


    private TextView text_name;

    private EditText password_edit;

    private Button cancel_button;

    private Button cofirm_button;

    private String text_nameString = null;

    private String capabilities;

    private Context mContext;


    public WifiLinkDialog(@NonNull Context context, @StyleRes int themeResId, String text_nameString, String capabilities) {
        super(context, themeResId);
        this.text_nameString = text_nameString;
        this.capabilities = capabilities;

        mContext = context;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = LayoutInflater.from(mContext).inflate(R.layout.setting_wifi_link_dialog, null);
        setContentView(view);
        initView(view);
        text_name.setText(text_nameString);
        initListener();
    }



    private void initListener() {
        cancel_button.setOnClickListener(this);
        cofirm_button.setOnClickListener(this);
        password_edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if((capabilities.contains("WPA") || capabilities.contains("WPA2") || capabilities.contains("WPS"))){
                    if(password_edit.getText() == null && password_edit.getText().toString().length() < 8){
                        cofirm_button.setClickable(false);
                    }else{
                        cofirm_button.setClickable(true);
                    }
                }else if(capabilities.contains("WEP")){
                    if(password_edit.getText() == null && password_edit.getText().toString().length() < 8){
                        cofirm_button.setClickable(false);
                    }else{
                        cofirm_button.setClickable(true);
                    }
                }
            }
        });
    }

    private void initView(View view) {
        text_name = (TextView) view.findViewById(R.id.wifi_title);
        password_edit = (EditText) view.findViewById(R.id.password_edit);
        cancel_button = (Button) view.findViewById(R.id.cancel_button);
        cofirm_button = (Button)view.findViewById(R.id.cofirm_button);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.cofirm_button:{
                WifiConfiguration wifiConfiguration =  WifiSupport.createWifiConfig(text_nameString,password_edit.getText().toString(),WifiSupport.getWifiCipher(capabilities));
                WifiSupport.addNetWork(wifiConfiguration,getContext());
                dismiss();
                break;
            }
            case R.id.cancel_button:{
                dismiss();
                break;
            }
        }
    }
}

package com.android.yummobilestone.yumstone.InterfaceCallBack;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SharedPreferencesClass {
	private Context context;
	
	public void SharedPreferencesClass(Context context)
	{
		this.context=context;
	}
	
	//savaValues需要保存的名称
	public void sendSharedValues(String saveValues,String name)
	{
		SharedPreferences  preferences  =  context .getSharedPreferences(saveValues,Context.MODE_PRIVATE);
		Editor editor=preferences.edit();
		editor.putString("keyvalue",name);  
		editor.commit();
	}
	
	public String getSharedValues(String savaValues,String name)
	{
        SharedPreferences  preferences  =  context .getSharedPreferences(savaValues,Context.MODE_PRIVATE);    	
        return preferences.getString("keyvalue", "");
	}
	

}

package com.example.administrator.znethttpdemo

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.zyl.znet.iterface.ZCallBack
import com.zyl.znet.util.ZNetUtil
import java.io.*
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : AppCompatActivity(), ZCallBack {
    override fun onResult(result: String) {
        Log.i("sss","res 000------------->"+result)
    }

    override fun onError(error: String) {
        Log.i("sss","res 111------------->"+error)

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //doGet()
        doGet()



    }
    fun doGet(){
        Thread(Runnable {



        var url=URL("http://119.1.224.184:8090/newlovenote/deleteNote?noteid=6")

        var urlConnection = url.openConnection() as HttpURLConnection
           try {
               urlConnection.requestMethod="GET"
//                urlConnection.setDoOutput(true);
//                urlConnection.setChunkedStreamingMode(0)
//                var out =BufferedOutputStream(urlConnection
//                        .getOutputStream())
                urlConnection.connect()
                var code=urlConnection.responseCode
               Log.i("sss","coder-------------->"+code)

               //writeStream(out);
                var inputStream = InputStreamReader(urlConnection.getInputStream())
                var res=inputStream.readText()



               Log.i("sss","buffer-------------->"+res)

               } finally {
                 urlConnection.disconnect();
               }
        }).start()
    }

    fun posttest(){
        Thread(Runnable {
            var url=URL("http://119.1.224.184:8090/newlovenote/getnotelist")
            var conn=url.openConnection() as HttpURLConnection
            conn.connectTimeout=5000
            conn.requestMethod="POST"
            conn.doOutput=true

            var outputStream=conn.outputStream
            var jsonString="uid=4"
            outputStream.write(jsonString.toByteArray())
            conn.connect()
            Log.i("sss","conn.responseCode---------->"+conn.responseCode)
            var responseCode=conn.responseCode
            if(responseCode==200){
                var stream=conn.inputStream
                var instreamReader=InputStreamReader(stream)
                var text=instreamReader.readText()
                Log.i("sss","text------------->"+text)

            }else if(responseCode==500){
                Log.i("sss","text------------->服务器无响应")

            }else if(responseCode==405){
                Log.i("sss","text------------->不支持的请求方法")

            }

        }).start()

    }



}

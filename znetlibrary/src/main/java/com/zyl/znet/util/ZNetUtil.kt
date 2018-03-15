package com.zyl.znet.util

import android.text.TextUtils
import android.util.Log
import com.zyl.znet.iterface.ZCallBack
import com.zyl.znet.iterface.ZError
import org.json.JSONObject
import java.io.InputStreamReader
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL

/**
 * Created by Administrator on 2018/3/15.
 */
class ZNetUtil {

    companion object {
        fun get(url: String, call: ZCallBack) {
            Thread(Runnable {
                var url = URL(url)
                var urlConnection = url.openConnection() as HttpURLConnection
                try {
                    urlConnection.requestMethod = "GET"
//                    urlConnection.setDoOutput(true);
//                    urlConnection.setChunkedStreamingMode(0)
                    urlConnection.connect()
                    var resCode = urlConnection.responseCode
                    when (resCode) {
                        200 -> {
                            var inputStream = InputStreamReader(urlConnection.getInputStream())
                            var res = inputStream.readText()
                            call.onResult(res)
                        }
                        500 -> {
                            call.onError(ZError.ERROR_500)
                        }
                        405 -> {
                            call.onError(ZError.ERROR_405)
                        }
                        404 -> {
                            call.onError(ZError.ERROR_404)

                        }

                    }


            }catch (e: Exception){
                    call.onError(e.toString())

                } finally {
                urlConnection.disconnect()
            }
        }).start()

    }



        fun post(url: String,jsonString:String, call: ZCallBack) {
            Thread(Runnable {
                var url = URL(url)
                var urlConnection = url.openConnection() as HttpURLConnection
                try {
                    /**
                     * 此方法用于启用HTTP请求体的流。
                     *没有内部缓冲，当内容长度不
                     *提前知道。在这种模式下，分块传输编码。
                     *用于发送请求体。注意，不是所有的HTTP服务器。
                     *支持这种模式。
                     */
                    urlConnection.setChunkedStreamingMode(0)
                    urlConnection.requestMethod = "POST"
                    urlConnection.setDoOutput(true)
                    var out=urlConnection.outputStream
                    var buffer=StringBuffer()

                     if(!TextUtils.isEmpty(jsonString)){
                         var json=JSONObject(jsonString)
                         var it=json.keys()
                         var i=0
                         while (it.hasNext()){
                             var key=it.next()
                             if(i==0){
                                 buffer.append(key+"="+json.get(key))
                             }else{
                                 buffer.append("&"+key+"="+json.get(key))

                             }
                             i++

                         }
                         out.write(buffer.toString().toByteArray())
                     }

                   // urlConnection.connect()
                    var resCode = urlConnection.responseCode
                    when (resCode) {
                        200 -> {
                            var inputStream = InputStreamReader(urlConnection.getInputStream())
                            var res = inputStream.readText()
                            call.onResult(res)
                        }
                        500 -> {
                            call.onError(ZError.ERROR_500)
                        }
                        405 -> {
                            call.onError(ZError.ERROR_405)
                        }
                        404 -> {
                            call.onError(ZError.ERROR_404)

                        }

                    }


                }catch (e:Exception){
                    call.onError(e.toString())

                } finally {
                    Log.i("sss","disconnect----------------")
                    urlConnection.disconnect()
                }
            }).start()

        }
}
}
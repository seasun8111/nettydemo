package com.vast8.nettydemo.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.vast8.nettydemo.pojo.BaseResponse;
import com.vast8.nettydemo.pojo.StatusCode;
import com.vast8.nettydemo.serialize.JSONSerializer;
import com.vast8.nettydemo.service.EventQueue;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.AsciiString;
import org.apache.commons.codec.CharEncoding;
import org.apache.commons.codec.Charsets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;



import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;


/**
 * @author Dingyuan
 * @version 1.0
 * @date 2019/10/24 16:46
 */
public class HttpHandler extends SimpleChannelInboundHandler {

    private static final Logger logger = LoggerFactory.getLogger(HttpHandler.class);
    private HttpHeaders headers;
    private HttpRequest request;
    private FullHttpRequest fullRequest;


    private static final String FAVICON_ICO = "/favicon.ico";
    private static final AsciiString CONTENT_TYPE = AsciiString.cached("Content-Type");
    private static final AsciiString CONTENT_LENGTH = AsciiString.cached("Content-Length");
    private static final AsciiString CONNECTION = AsciiString.cached("Connection");
    private static final AsciiString KEEP_ALIVE = AsciiString.cached("keep-alive");


    @Override
    public void  channelRead0 (ChannelHandlerContext ctx, Object msg) throws Exception {

        if (msg instanceof HttpRequest){
            request = (HttpRequest) msg;
            headers = request.headers();
            String uri = request.uri();
            logger.info("http uri: "+ uri);
            if (uri.equals(FAVICON_ICO)){
                return;
            }
            HttpMethod method = request.method();
            if (method.equals(HttpMethod.GET)){
                QueryStringDecoder queryDecoder = new QueryStringDecoder(uri, Charsets.toCharset(CharEncoding.UTF_8));
//                Map<String, List<String>> uriAttributes = queryDecoder.parameters();
//                for (Map.Entry<String, List<String>> attr : uriAttributes.entrySet()) {
//                    for (String attrVal : attr.getValue()) {
//                        logger.info(attr.getKey() + "=" + attrVal);
//                    }
//                }
            }else if (method.equals(HttpMethod.POST)){
                //POST请求,由于你需要从消息体中获取数据,因此有必要把msg转换成FullHttpRequest
                fullRequest = (FullHttpRequest)msg;
                //根据不同的Content_Type处理body数据
                dealWithContentType();

            }

            JSONSerializer jsonSerializer = new JSONSerializer();
//            byte[] content = jsonSerializer.serialize(user);

            BaseResponse baseResponse = new BaseResponse<String>();
            baseResponse.setCode(StatusCode.SUCCESS.getCode());
            baseResponse.setInfo(StatusCode.SUCCESS);
            baseResponse.setData("{}");
            byte[] content =  jsonSerializer.serialize(baseResponse);

            FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, OK, Unpooled.wrappedBuffer(content));
            response.headers().set(CONTENT_TYPE, "application/json");
            response.headers().setInt(CONTENT_LENGTH, response.content().readableBytes());

            boolean keepAlive = HttpUtil.isKeepAlive(request);
            if (!keepAlive) {
                ctx.write(response).addListener(ChannelFutureListener.CLOSE);
            } else {
                response.headers().set(CONNECTION, KEEP_ALIVE);
                ctx.write(response);
            }
        }

    }


    /**
     * 简单处理常用几种 Content-Type 的 POST 内容（可自行扩展）
     * @throws Exception
     */
    private void dealWithContentType() throws Exception{
        String contentType = getContentType();
        //可以使用HttpJsonDecoder
        if(contentType.equals("application/json")){
            String jsonStr = fullRequest.content().toString(Charsets.toCharset(CharEncoding.UTF_8));
            JSONObject obj = JSON.parseObject(jsonStr);
            //处理事件，如果队列已满，打印到本地日志
            boolean offerSuccess = EventQueue.getInstance().getQueue().offer(obj);
            if(Boolean.FALSE.equals(offerSuccess)) {
                logger.warn("QUEUE FULL->"+obj.toJSONString());
            }
//            for(Map.Entry<String, Object> item : obj.entrySet()){
//                logger.info(item.getKey()+"="+item.getValue().toString());
//            }

        }else if(contentType.equals("application/x-www-form-urlencoded")){
            //方式一：使用 QueryStringDecoder
            String jsonStr = fullRequest.content().toString(Charsets.toCharset(CharEncoding.UTF_8));
            QueryStringDecoder queryDecoder = new QueryStringDecoder(jsonStr, false);
            Map<String, List<String>> uriAttributes = queryDecoder.parameters();
            for (Map.Entry<String, List<String>> attr : uriAttributes.entrySet()) {
                for (String attrVal : attr.getValue()) {
                    logger.info(attr.getKey() + "=" + attrVal);
                }
            }

        }else if(contentType.equals("multipart/form-data")){
            //TODO 用于文件上传
        }else{
            //do nothing...
        }
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    private String getContentType(){
        String typeStr = headers.get("Content-Type").toString();
        String[] list = typeStr.split(";");
        return list[0];
    }



}

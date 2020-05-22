package com.qfedu.fallback;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.cloud.netflix.zuul.filters.route.FallbackProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @auther: Zhangbo
 * @date: 2020/5/16 19:07
 * @Description:   路由到微服务超时的回调方法
 *  zuul网关容错(与Hystrix的无缝结合):
 *      定义:在spring cloud中，Zuul(依赖)启动器中包含了Hystrix相关依赖，
 *           在Zuul网关工程中，默认是提供了Hystrix Dashboard服务监控数据的(hystrix.stream)，但是不会提供监控面板的界面展示。
 *      1,zuul的服务降级处理:(只针对timeout[超时]异常处理)
 *          定义:只要服务有返回（包括异常），都不会触发Zuul的fallback容错逻辑。
 *              为什么只针对timeout做降级处理呢??
 *                 答:因为对于Zuul网关来说，做请求路由分发的时候，结果由远程服务运算的。
 *                 那么远程服务反馈了异常信息，Zuul网关不会处理异常，因为无法确定这个错误是否是应用真实想要反馈给客户端的。
 *     2,zuul降级的实现:
 *          1,自定义服务降级类,实现FallbackProvider接口
 *          2,必须将当前类,交个spring管理
 *          3,必须实现2个方法;(接口的方法必须实现)
 *                  getRoute:配置处理那些微服务(可以使用通配符)
 *                  fallbackResponse:回调的代码
 *      拓展测试:
 *          1,服务降级回调,捕捉的异常,error类型过滤器不在走;
 *          2,服务降级的顺序:微服务端--zuul路由
 */
@Component
public class HttpTimeoutFallback implements FallbackProvider {

    /**
     * 配置回调哪些微服务
     *      推荐:指定微服务个性化超时回调
     *      推荐:处理所有微服务超时回调
     * @return  指定的那些微服务
     */
    @Override
    public String getRoute() {
        return "*";
    }

    /**
     * 根据异常类型,动态决定回调的方式
     * @param route
     * @param cause
     * @return
     */
    @Override
    public ClientHttpResponse fallbackResponse(String route, Throwable cause) {
        System.out.println("zuul网关回调启用了:优先于error过滤器");

        /**
         * 拓展知识点:
         *  instanceof是Java中的一个双目运算符，用来测试一个对象是否为一个类的实例
         *  NullPointerException(空指针异常)
         *       1. 字符串变量未初始化；
         *       2. 接口类型的对象没有用具体的类初始化
         *   ObjMapper类:
         *          定义: 是一个使用 Swift 语言编写的数据模型转换框架，我们可以方便的将模型对象转换为JSON，或者JSON生成相应的模型类
         *
         *
         */
        //根据异常类型处理
        //判断是否空指针异常
        if(cause instanceof ZuulException){
            System.out.println("ZuulException,Zuul服务降级方法启动");

            //组装处理的数据
            List<Map<String, Object>> result = new ArrayList<>();
            Map<String, Object> data = new HashMap<>();
            data.put("message", "网关超时，请稍后重试");
            result.add(data);

            //将result对象转换成Json数据
            ObjectMapper mapper = new ObjectMapper();

            String msg = "";
            try {
                msg = mapper.writeValueAsString(result);
                System.out.println(msg);
            } catch (JsonProcessingException e) {
                msg = "";
            }

            //返回具体的响应内容
            return this.executeFallback(HttpStatus.GATEWAY_TIMEOUT, msg, "application", "json", "utf-8");
        }else{
            System.out.println("其他异常,Zuul服务降级方法启动:");
            ////////////////其他类型异常的处理逻辑:自己书写同上/////////////////////////
            ////////////////其他类型异常的处理逻辑:自己书写/////////////////////////
            ////////////////其他类型异常的处理逻辑:自己书写/////////////////////////
            ////////////////其他类型异常的处理逻辑:自己书写/////////////////////////
            return this.executeFallback(HttpStatus.GATEWAY_TIMEOUT, "其他异常,Zuul服务降级方法启动", "application", "json", "utf-8");
        }
    }

    /**
     * 组装响应结果    (逻辑方法的提取)。
     * @param status 自定义容错处理后的返回状态码，如200正常GET请求结果，201正常POST请求结果，404资源找不到错误等。
     *               使用spring提供的枚举类型对象实现。HttpStatus,(这里返回504超时)
     * @param contentMsg   自定义的响应内容。  作用:反馈给客户端的数据。
     * @param mediaType    响应类型，是响应的主类型， 如： application、text、media。
     * @param subMediaType 响应类型，是响应的子类型， 如： json、stream、html、plain、jpeg、png等。
     * @param charsetName  响应结果的字符集。这里只传递字符集名称，如： utf-8、gbk、big5等。
     * @return ClientHttpResponse 就是响应具体内容的响应体。
     *  相当于一个HttpServletResponse。
     */
    private final ClientHttpResponse executeFallback(final HttpStatus status,
                                                     String contentMsg, String mediaType, String subMediaType, String charsetName) {
        return new ClientHttpResponse() {

            /**
             * 设置响应头的类型
             * @return
             */
            @Override
            public HttpHeaders getHeaders() {
                //获取响应头对象
                HttpHeaders headers = new HttpHeaders();

                //配置响应头
                MediaType type = new MediaType(mediaType,subMediaType,Charset.forName(charsetName));
                headers.setContentType(type);

                return headers;
            }

            /**
             * 设置响应体
             *    作用:zuul会将本方法返回的输入流数据读取，
             *         并通过HttpServletResponse的输出流输出到客户端。
             * @return   返回客户端的数据
             * @throws IOException
             */
            @Override
            public InputStream getBody() throws IOException {
                return new ByteArrayInputStream(contentMsg.getBytes());
            }

            /**
             *  设置响应的状态码 返回HttpStatus枚举类型
             * @return
             * @throws IOException
             */
            @Override
            public HttpStatus getStatusCode() throws IOException {
                return status;
            }

            /**
             * 将ClientHttpResponse下getStatusCode()的返回状态码,转换为int类型
             * @return
             * @throws IOException
             */
            @Override
            public int getRawStatusCode() throws IOException {
                return this.getStatusCode().value();
            }

            /**
             * 将ClientHttpResponse下getStatusCode()的返回状态码,转换为String类型
             * @return
             * @throws IOException
             */
            @Override
            public String getStatusText() throws IOException {
                return this.getStatusCode().getReasonPhrase();
            }

            /**
             * 关闭服务降级使用的资源对象
             */
            @Override
            public void close() {

            }
        };
    }
}

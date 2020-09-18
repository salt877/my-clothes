//package jp.co.example.my.clothes;
//
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//public class WebMvcConfig implements WebMvcConfigurer { // Spring 4.3.x利用者はWebMvcConfigurerAdapterを継承すると良い
//	
//@Override
//public void addInterceptors(InterceptorRegistry registry) {
//    registry.addInterceptor(new CustomHandlerInterceptor())
//            .addPathPatterns("/**") // 適用対象のパス(パターン)を指定する
//            .excludePathPatterns("/static/**"); // 除外するパス(パターン)を指定する
//}
//}

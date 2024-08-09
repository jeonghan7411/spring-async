package dev.be.async.service;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AsyncService {

    private final EmailService emailService;

    // 이렇게 사용해야함!!! 빈주입 *****
    // 접근자는 항상 public !!
    public void asyncCall_1(){
        //각각의 스레드가 처리하게 됨
        System.out.println("[asyncCall_1] ::" + Thread.currentThread().getName());
        emailService.sendMail();
        emailService.sendMailWithCustomThreadPool();
        /**
         * - 비동기로 동작할 수 있게 Sub Thread에게 위임
         */
    }

    public void asyncCall_2(){
        // 동일한 스레드가 처리함
        // 스프링에서 비동기 처리하기 위해서는 스프링의 도움이 필요하기 때문에 (프록시 객체로 한번 래핑해서 빈을 돌려줌)
        // bean으로 등록된것을 통해 사용해야함
        System.out.println("[asyncCall_2] ::" + Thread.currentThread().getName());
        EmailService emailService = new EmailService();
        emailService.sendMail();
        emailService.sendMailWithCustomThreadPool();
    }

    public void asyncCall_3(){
        // 동일한 스레드가 처리함
        System.out.println("[asyncCall_3] ::" + Thread.currentThread().getName());
        sendMail();
    }


    // @Async 어노테이션이 없는 것이나 똑같음 내부에서 메서드 선언해서 사용하는것은 비동기 동작 x
    // 실제로 비동기 상황이 왔을때 에러가 발생한다.
    @Async
    public void sendMail(){
        System.out.println("[sendMail] ::" + Thread.currentThread().getName());
    }
}

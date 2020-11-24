package com.annakhuseinova.completablefuture;

import com.annakhuseinova.service.HelloWorldService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class CompletableFutureHelloWorldExceptionTest {

    @Mock
    private HelloWorldService helloWorldService;

    @InjectMocks
    private CompletableFutureHelloWorldException completableFutureHelloWorldException;

    @Test
    void helloWorld_3_async_calls() {
        when(helloWorldService.hello()).thenThrow(new RuntimeException("Exception occurred"));
        when(helloWorldService.world()).thenCallRealMethod();

        String result = completableFutureHelloWorldException.helloWorld_3_async_calls_handle_exception();

        assertEquals(" WORLD! HI COMPLETABLE FUTURE!", result);
    }

    @Test
    void helloWorld_3_async_calls_2() {
        when(helloWorldService.hello()).thenThrow(new RuntimeException("Exception occurred"));
        when(helloWorldService.world()).thenCallRealMethod();

        String result = completableFutureHelloWorldException.helloWorld_3_async_calls_handle_exception();

        assertEquals(" WORLD! HI COMPLETABLE FUTURE!", result);
    }

    @Test
    void helloWorld_3_async_calls_2_exceptionally() {
        when(helloWorldService.hello()).thenThrow(new RuntimeException("Exception occurred"));
        when(helloWorldService.world()).thenCallRealMethod();

        String result = completableFutureHelloWorldException.helloWorld_3_async_calls_handle_exception_exceptionally() ;

        assertEquals(" WORLD! HI COMPLETABLE FUTURE!", result);
    }

    @Test
    void helloWorld_3_async_calls_2_whenComplete() {
        when(helloWorldService.hello()).thenThrow(new RuntimeException("Exception occurred"));
        when(helloWorldService.world()).thenCallRealMethod();

        String result = completableFutureHelloWorldException.helloWorld_3_async_calls_handle_exception_whenComplete() ;

        assertEquals(" WORLD! HI COMPLETABLE FUTURE!", result);
    }


}
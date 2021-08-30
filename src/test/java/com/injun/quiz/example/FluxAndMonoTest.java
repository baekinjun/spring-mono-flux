package com.injun.quiz.example;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Signal;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;
//읽어보기 플럭스 모노 둘다 읽어보기!

public class FluxAndMonoTest {
    //flux 는 여러개 복수형  mono 는 단수형
    @DisplayName("Flux just() sample")
    @Test
    void main() {
        //blocking 상태!
        List<String> names = new ArrayList<>();
        //물을 흐르는 형태 flux 는계속 돌고 있기 떄문에 가다찍고 가다 찍고 한다.
        //매핑으로 돈다. just
        Flux<String> flux = Flux.just("김숙", "윤봉길", "유관순").log();
        flux.subscribe(names::add);
        assertThat(names, is(equalTo(Arrays.asList("김숙", "윤봉길", "유관순"))));

    }

    @DisplayName("range sample")
    @Test
    void rangeTest() {
        //blocking 상태!
        List<Integer> list = new ArrayList<>();
        //물을 흐르는 형태 flux 는계속 돌고 있기 떄문에 가다찍고 가다 찍고 한다.
        //매핑으로 돈다.
        Flux<Integer> flux = Flux.range(1, 5).log();
        flux.subscribe(list::add);
        assertThat(list.size(), is(5));
        assertThat(list.get(2), is(3));
        assertThat(list.size(), not(6));

    }

    @DisplayName("from array() sample")
    @Test
    void fromArrayTest() {
        //blocking 상태!
        List<String> list = new ArrayList<>();
        Flux<String> flux = Flux.fromArray(new String[]{"김숙", "윤봉길", "유관순"}).log();
        flux.subscribe(list::add);
        assertThat(list, is(equalTo(Arrays.asList("김숙", "윤봉길", "유관순"))));

    }

    @DisplayName("from iterable sample")
    @Test
    void fromAIterableTest() {
        //blocking 상태!
        List<String> list = new ArrayList<>();
        //기존 코드에 씌우기만 하면 된다.
        Flux<String> flux = Flux.fromIterable(Arrays.asList("김숙", "윤봉길", "유관순")).log();
        flux.subscribe(list::add);
        assertThat(list, is(equalTo(Arrays.asList("김숙", "윤봉길", "유관순"))));

    }

    @DisplayName("fromStream sample")
    @Test
    void fromStreamTest() {
        //blocking 상태!
        List<String> list = new ArrayList<>();
        //기존 코드에 씌우기만 하면 된다.
        //stream 계속 흐르는것 stream 을 붙여야한다.
        Flux<String> flux = Flux.fromStream(Stream.of("김숙", "윤봉길", "유관순")).log();
        flux.subscribe(list::add);
        assertThat(list, is(equalTo(Arrays.asList("김숙", "윤봉길", "유관순"))));
    }

    @DisplayName("generateTest sample")
    @Test
    void generatorTest() {
        //비동기 와 스트림 같이 처리한다. 먼저 처리된것을 먼저먼저 처리시켜서 리스폰값을 내보낸다.
        Flux<String> flux = Flux.generate(
                () -> {
                    return 0;
                },
                (state, sink) -> {
                    sink.next("3 X " + state + "=" + 3 * state);
                    if (state == 10) {
                        sink.complete();
                    }
                    return state + 1;
                }
        );
        flux.subscribe(System.out::println);
    }

    @DisplayName("flux create() sample")
    @Test
    void createTest() {
        /**
         * Flux.create()와 배압
         * Subscriber로부터 요청이 왔을 때(FluxSink#onRequest) 데이터를 전송하거나(pull 방식)
         * Subscriber의 요청에 상관없이 데이터를 전송하거나(push 방식)
         * 두 방식 모두 Subscriber가 요청한 개수보다 더 많은 데이터를 발생할 수 있다.
         * 이 코드는 Subscriber가 요청한 개수보다 3개 데이터를 더 발생한다. 이 경우 어떻게 될까?
         * 기본적으로 Flux.create()로 생성한 Flux는 초과로 발생한 데이터를 버퍼에 보관한다.
         * 버퍼에 보관된 데이터는 다음에 Subscriber가 데이터를 요청할 때 전달된다.
         * 요청보다 발생한 데이터가 많을 때 선택할 수 있는 처리 방식은 다음과 같다.
         * IGNORE : Subscriber의 요청 무시하고 발생(Subscriber의 큐가 다 차면 IllegalStateException 발생)
         * ERROR : 익셉션(IllegalStateException) 발생
         * DROP : Subscriber가 데이터를 받을 준비가 안 되어 있으면 데이터 발생 누락
         * LATEST : 마지막 신호만 Subscriber에 전달
         * BUFFER : 버퍼에 저장했다가 Subscriber 요청시 전달. 버퍼 제한이 없으므로 OutOfMemoryError 발생 가능
         * Flux.create()의 두 번째 인자로 처리 방식을 전달하면 된다.
         * */
        Flux<Integer> flux = Flux.create((FluxSink<Integer> sink) -> {
            sink.onRequest(request -> {
                for (int i = 1; i < request + 3; i++) { //subscriber 가 요청한것 보다 3개 더 발생
                    sink.next(i);
                }
            });
        });
        flux.subscribe(System.out::println);
    }

    @DisplayName("flux empty sample")
    @Test
    void emptyTest() {
        //이것은 리스트 기본 만드는것
        List<String> list = new ArrayList<>();
        //flux 에서 비어있는 초기화 값
        Flux<String> flux = Flux.empty();
        flux.subscribe(list::add);
        assertThat(list.size(), is(0));
    }

    @DisplayName("mono just sample")
    @Test
    void monoTest() {
        /**
         * Reactive Stream 에서는 Data, Event, Signal 중에서 Signal 을 사용한다.
         * onNext, onComplete, onError
         * */

        List<Signal<Integer>> list = new ArrayList<>(5);
        //flux 에서 비어있는 초기화 값
        //just 는 속성값 집어넣어야 한다.!
        final Integer[] result = new Integer[1];
        Mono<Integer> mono = Mono.just(1).log()
                .doOnEach(i -> {
                    list.add(i);
                    System.out.println(i);
                });
        mono.subscribe(i -> result[0] = i);
        assertThat(list.size(), is(2));
        assertThat(list.get(0).getType().name(), is(equalTo("ON_NEXT")));
        assertThat(list.get(1).getType().name(), is(equalTo("ON_COMPLETE")));
        assertThat(result[0].intValue(), is(1));
    }

    @DisplayName("mono empty sample")
    @Test
    void monoEmptyTest() {
        //이것은 리스트 기본 만드는것
        Mono<String> result = Mono.empty();
        assertThat(result, is(equalTo("")));
    }

    @DisplayName("mono just() sample")
    @Test
    void monoJustTest() {
        System.out.println("Empty Mono");
        Mono.empty().subscribe(System.out::print);
        System.out.println("---mono.just()------");
        Mono.just("Java")
                .map(item -> "Mono item" + item)
                .subscribe(System.out::println);
        Flux.empty().subscribe(System.out::println);
        System.out.println("----------empty flux-----------");
        Flux.just("python", "java", "go")
                .map(items -> items.toUpperCase())
                .subscribe(System.out::println);

    }
}

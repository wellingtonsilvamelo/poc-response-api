package br.com.tomwell.poc_response_api.service;

import br.com.tomwell.poc_response_api.dto.QuoteRequest;
import br.com.tomwell.poc_response_api.dto.QuoteResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class SseService {

//    private final CopyOnWriteArrayList<SseEmitter> emitters = new CopyOnWriteArrayList<>();
    private final Map<String, SseEmitter> emitters = new ConcurrentHashMap<>();

    public SseEmitter subscribe(String clientId) {
        SseEmitter emitter = new SseEmitter();

        this.emitters.put(clientId, emitter);

        emitter.onCompletion(() -> this.emitters.remove(clientId));
        emitter.onTimeout(() -> this.emitters.remove(clientId));
        return emitter;
    }

    /*public void emitEvent(QuoteResponse response) {
        for (SseEmitter emitter : emitters) {
            try {
                emitter.send(SseEmitter.event().name("response").data(response));
            } catch (IOException e) {
                emitters.remove(emitter);
            }
        }
    }*/

    public void emitEvent(String clientId, QuoteResponse response) {
        SseEmitter emitter = emitters.get(clientId);
        if (emitter != null) {
            try {
                emitter.send(response);
                emitter.complete();
            } catch (IOException e) {
                emitters.remove(clientId);
                emitter.completeWithError(e);
            }
        }
    }

}
